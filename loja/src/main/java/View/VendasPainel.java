package View;

import java.util.List;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import Controller.CarrosControl;
import Controller.CarrosDAO;
import Controller.ClientesDAO;
import Controller.VendasControl;
import Controller.VendasDAO;
import Model.Carros;
import Model.Clientes;
import Model.Vendas;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class VendasPainel extends JPanel {
    private JButton cadastrar, apagar, editar;
    private JTextField clienteNomeField, carroVendidoField, dataVendidoField, valorField;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;
    private List<Vendas> vendas;
    JComboBox<String> carrosComboBox;
    List<Carros> carros;

    JComboBox<String> clientesComboBox;
    List<Clientes> clientes;

    public VendasPainel() {
        super();
        // entrada de dados
         carrosComboBox = new JComboBox<>();
        // Preencha o JComboBox com os carros
        carros = new CarrosDAO().listarTodos();
        carrosComboBox.addItem("Selecione o Carro");
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca()
                    + " " + carro.getModelo()
                    + " " + carro.getPlaca());
        }
        add(carrosComboBox);

        clientesComboBox = new JComboBox<>();
        // Preencha o JComboBox com os carros
        clientes = new ClientesDAO().listarTodos();
        clientesComboBox.addItem("Selecione o Cliente");
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome()
                    + " " + cliente.getCpf());
        }
        add(clientesComboBox);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.add(clientesComboBox);
        inputPanel.add(carrosComboBox);
        inputPanel.add(new JLabel("Data da Venda:")).setFont(new Font("Arial", Font.PLAIN, 16));
        dataVendidoField = new JTextField(20);
        inputPanel.add(dataVendidoField);
        inputPanel.add(new JLabel("Valor da Venda:")).setFont(new Font("Arial", Font.PLAIN, 16));
        valorField = new JTextField(20);
        inputPanel.add(valorField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar")).setBackground((Color.CYAN));
        botoes.add(editar = new JButton("Editar")).setBackground((Color.GREEN));
        botoes.add(apagar = new JButton("Apagar")).setBackground((Color.RED));
        add(botoes);
        inputPanel.setVisible(true);
        botoes.setVisible(true);

        // tabela de clientes
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Carro", "Data", "Cliente", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        jSPane.setVisible(true);

        new VendasDAO().criaTabela();

        atualizarTabela();

        // botões de eventos
        // tratamento de eventos(construtor)
        // tratamento de Eventos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    dataVendidoField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    valorField.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        VendasControl operacoes = new VendasControl(vendas, tableModel, table);

        cadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método "cadastrar" do objeto operacoes com os valores dos
                String data = dataVendidoField.getText();
                String valor = valorField.getText();
                String cliente = (String) clientesComboBox.getSelectedItem();
                String carro = (String) carrosComboBox.getSelectedItem();
                // campos de entrada

                if (data.isEmpty() || valor.isEmpty() || cliente.equals("Selecione um cliente")
                        || carro.equals("Selecione um carro")) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                } else if (!valor.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "O campo 'Valor' deve conter apenas números.");
                } else {
                    int confirmacaoVendas = JOptionPane.showConfirmDialog(null,
                            "Tem certeza de que deseja registrar a venda?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirmacaoVendas == JOptionPane.YES_NO_OPTION) {
                        operacoes.cadastrar(cliente, carro, data, valor);
                        // Limpa os campos de entrada após a operação de cadastro
                        clientesComboBox.setSelectedIndex(0);
                        carrosComboBox.setSelectedIndex(0);
                        dataVendidoField.setText("");
                        valorField.setText("");
                        JOptionPane.showMessageDialog(null, "Venda cadastrada com sucesso!");
                    }
                    else if (confirmacaoVendas == JOptionPane.NO_OPTION) {
                        clientesComboBox.setSelectedIndex(0);
                        carrosComboBox.setSelectedIndex(0);
                        dataVendidoField.setText("");
                        valorField.setText("");
                        JOptionPane.showMessageDialog(null, "Registro da venda cancelada!");
                    }

                }
            }
        });

        // Configura a ação do botão "apagar" para excluir um registro no banco de dados
        apagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = dataVendidoField.getText();
                String valor = valorField.getText();
                String carro = (String) carrosComboBox.getSelectedItem();
                String cliente = (String) clientesComboBox.getSelectedItem();

                if (valor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione algo para apagar");
                } else {
                    int confirmacaoApagar = JOptionPane.showConfirmDialog(null,
                            "Tem certeza de que deseja apagar o registro da venda?", "Confirmação",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmacaoApagar == JOptionPane.YES_NO_OPTION) {
                        operacoes.apagar(carro);
                        // Limpa os campos de entrada após a operação de exclusão
                        clientesComboBox.setSelectedIndex(0);
                        carrosComboBox.setSelectedIndex(0);
                        dataVendidoField.setText("");
                        valorField.setText("");
                        JOptionPane.showMessageDialog(null, "VENDA DELETADA COM SUCESSO!");
                    }
                     else if (confirmacaoApagar == JOptionPane.NO_OPTION) {
                        clientesComboBox.setSelectedIndex(0);
                        carrosComboBox.setSelectedIndex(0);
                        dataVendidoField.setText("");
                        valorField.setText("");
                        JOptionPane.showMessageDialog(null, "EXCLUSÃO DA VENDA CANCELADA COM SUCESSO!");
                    }
                    
                }
            }
        });

        editar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String data = dataVendidoField.getText();
                String valor = valorField.getText();
                String carro = (String) carrosComboBox.getSelectedItem();
                String cliente = (String) clientesComboBox.getSelectedItem();
            if (valor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione algo para editar");
            } else {
                operacoes.atualizar(dataVendidoField.getText(), valorField.getText(), carro, cliente);
                atualizarComboBoxClientes();
            atualizarComboBoxCarros();
                // Limpa os campos de entrada após a operação de atualização
               clientesComboBox.setSelectedIndex(0);
                        carrosComboBox.setSelectedIndex(0);
                        dataVendidoField.setText("");
                        valorField.setText("");
                JOptionPane.showMessageDialog(null, "Cliente editado com sucesso!");
            }
        }
    });

    }

    private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas vendas : vendas) {
            tableModel.addRow(new Object[] { vendas.getCliente(), vendas.getCarroVendido(), vendas.getDataVenda(),
                    vendas.getValor() });
        }
    }

    // Método para atualizar ComboBox de Clientes
    private void atualizarComboBoxClientes() {
        clientesComboBox.removeAllItems();
        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome());
        }
    }

    // Método para atualizar ComboBox de Carros
    private void atualizarComboBoxCarros() {
        carrosComboBox.removeAllItems();
        carrosComboBox.addItem("Selecione um Carro");
        carros = new CarrosDAO().listarTodos();
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca() + " " + carro.getModelo());
        }
    }
    

}
