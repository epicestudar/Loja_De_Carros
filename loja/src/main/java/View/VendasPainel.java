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
        // add(carrosComboBox);

        clientesComboBox = new JComboBox<>();
        // Preencha o JComboBox com os carros
        clientes = new ClientesDAO().listarTodos();
        clientesComboBox.addItem("Selecione o Cliente");
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome()
                    + " " + cliente.getCpf());
        }
        // add(clientesComboBox);
        
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
                    clienteNomeField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    carroVendidoField.setText((String) table.getValueAt(linhaSelecionada, 1));
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
                String cliente = (String) clientesComboBox.getSelectedItem();
                String carro = (String) carrosComboBox.getSelectedItem();
                // campos de entrada

                if(cliente.isEmpty() || carro.isEmpty() || dataVendidoField.equals(null) || valorField.equals(null)) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                }
                else{
                    operacoes.cadastrar(cliente, carro,

                        dataVendidoField.getText(), valorField.getText());
                // Limpa os campos de entrada após a operação de cadastro
                clienteNomeField.setText("");
                carroVendidoField.setText("");
                dataVendidoField.setText("");
                valorField.setText("");
                }
            }
        });

         // Configura a ação do botão "apagar" para excluir um registro no banco de dados
         apagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String carro = (String) carrosComboBox.getSelectedItem();
                 String cliente = (String) clientesComboBox.getSelectedItem();
                // Chama o método "apagar" do objeto operacoes com o valor do campo de

                // entrada "placa"

                operacoes.apagar(carro);
                // Limpa os campos de entrada após a operação de exclusão
                clienteNomeField.setText("");
                carroVendidoField.setText("");
                dataVendidoField.setText("");
               valorField.setText("");
            }
        });

    }

     private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas vendas : vendas) {
            tableModel.addRow(new Object[] { vendas.getCliente(), vendas.getCarroVendido(), vendas.getDataVenda(), vendas.getValor() });
        }
    }
    }
