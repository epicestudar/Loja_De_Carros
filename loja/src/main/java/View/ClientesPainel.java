package View;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import Controller.CarrosControl;
import Controller.CarrosDAO;
import Controller.ClientesControl;
import Controller.ClientesDAO;

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

import Model.Carros;
import Model.Clientes;

public class ClientesPainel extends JPanel {
    private JButton cadastrar, apagar, editar;
    private JTextField clienteNomeField, clienteIdadeField, clienteCidadeField, clienteCpfField,
            clienteTelefoneField;
    private List<Clientes> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    // construtor(GUI - Jpanel)
    public ClientesPainel() {
        super();

        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Nome")).setFont(new Font("Arial", Font.PLAIN, 16));
        clienteNomeField = new JTextField(20);
        inputPanel.add(clienteNomeField);
        inputPanel.add(new JLabel("Idade")).setFont(new Font("Arial", Font.PLAIN, 16));
        clienteIdadeField = new JTextField(20);
        inputPanel.add(clienteIdadeField);
        inputPanel.add(new JLabel("Cidade")).setFont(new Font("Arial", Font.PLAIN, 16));
        clienteCidadeField = new JTextField(20);
        inputPanel.add(clienteCidadeField);
        inputPanel.add(new JLabel("Cpf")).setFont(new Font("Arial", Font.PLAIN, 16));
        clienteCpfField = new JTextField(20);
        inputPanel.add(clienteCpfField);
        inputPanel.add(new JLabel("Telefone")).setFont(new Font("Arial", Font.PLAIN, 16));
        clienteTelefoneField = new JTextField(20);
        inputPanel.add(clienteTelefoneField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar")).setBackground((Color.CYAN));
        botoes.add(editar = new JButton("Editar")).setBackground((Color.GREEN));
        botoes.add(apagar = new JButton("Apagar")).setBackground((Color.RED));
        add(botoes);

        // tabela de clientes
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "Idade", "Cidade", "Cpf", "Telefone" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new ClientesDAO().criaTabela();

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
                    clienteIdadeField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    clienteCidadeField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    clienteCpfField.setText((String) table.getValueAt(linhaSelecionada, 3));
                    clienteTelefoneField.setText((String) table.getValueAt(linhaSelecionada, 4));
                }
            }
        });
        // Cria um objeto operacoes da classe ClientesControl para executar operações no
        // banco de dados
        ClientesControl operacoes = new ClientesControl(clientes, tableModel, table);
        // Configura a ação do botão "cadastrar" para adicionar um novo registro no
        // banco
        // de dados
        cadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clienteNomeField.getText().isEmpty() || clienteIdadeField.getText().isEmpty()
                        || clienteCidadeField.getText().isEmpty() || clienteCpfField.getText().isEmpty()
                        || clienteTelefoneField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de cadastrar o cliente");
                } else if (!clienteIdadeField.getText().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "O campo 'idade' deve conter apenas números");
                } else if (!clienteCpfField.getText().matches("[0-9]+([-.][0-9]+)?")) {
                    JOptionPane.showMessageDialog(null,
                            "O campo 'cpf' deve conter apenas números e caracteres especiais");
                } else {
                    int confirmacao1 = JOptionPane.showConfirmDialog(null,
                            "Tem certeza de que deseja cadastrar o cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirmacao1 == JOptionPane.YES_NO_OPTION) {
                        operacoes.cadastrar(clienteNomeField.getText(), clienteIdadeField.getText(),

                                clienteCidadeField.getText(), clienteCpfField.getText(),
                                clienteTelefoneField.getText());
                        // Limpa os campos de entrada após a operação de cadastro
                        clienteNomeField.setText("");
                        clienteIdadeField.setText("");
                        clienteCidadeField.setText("");
                        clienteCpfField.setText("");
                        clienteTelefoneField.setText("");
                        // JOptionPane.showMessageDialog(null, "O cliente  " + clienteNomeField.getText() + " de "
                        //         + clienteIdadeField.getText() + " foi cadastrado com sucesso!");

                         JOptionPane.showMessageDialog(null, "O cliente foi cadastrado com sucesso!");
                    }
                    else if(confirmacao1 == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "Cadastro do cliente cancelado!");
                        clienteNomeField.setText("");
                        clienteIdadeField.setText("");
                        clienteCidadeField.setText("");
                        clienteCpfField.setText("");
                        clienteTelefoneField.setText("");
                    }
                }
            }
        });

        editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clienteCpfField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione algo para editar");
                } else {
                    operacoes.atualizar(clienteNomeField.getText(), clienteIdadeField.getText(),

                            clienteCidadeField.getText(), clienteCpfField.getText(), clienteTelefoneField.getText());
                    // Limpa os campos de entrada após a operação de atualização
                    clienteNomeField.setText("");
                    clienteIdadeField.setText("");
                    clienteCidadeField.setText("");
                    clienteCpfField.setText("");
                    clienteTelefoneField.setText("");
                    JOptionPane.showMessageDialog(null, "Cliente editado com sucesso!");
                }
            }
        });

        // Configura a ação do botão "apagar" para excluir um registro no banco de dados
        apagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clienteCpfField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha com o cpf do cliente que deseja apagar");
                } else {
                    int confirmacaoApagar = JOptionPane.showConfirmDialog(null,
                            "Tem certeza de que deseja apagar o registro do cliente?", "Confirmação",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmacaoApagar == JOptionPane.YES_NO_OPTION) {
                        operacoes.apagar(clienteCpfField.getText());
                        // Limpa os campos de entrada após a operação de exclusão
                        clienteNomeField.setText("");
                        clienteIdadeField.setText("");
                        clienteCidadeField.setText("");
                        clienteCpfField.setText("");
                        clienteTelefoneField.setText("");
                        // JOptionPane.showMessageDialog(null, "O cliente " + clienteNomeField.getText() + " de "
                        //         + clienteIdadeField.getText() + " foi apagado com sucesso!");

                        JOptionPane.showMessageDialog(null, "O cliente foi apagado com sucesso!");
                    }
                    else if (confirmacaoApagar == JOptionPane.NO_OPTION) {
                         clienteNomeField.setText("");
                        clienteIdadeField.setText("");
                        clienteCidadeField.setText("");
                        clienteCpfField.setText("");
                        clienteTelefoneField.setText("");
                        // JOptionPane.showMessageDialog(null, "O carro do modelo " + carModeloField.getText()
                        //         + " da placa " + carPlacaField.getText() + " foi apagado com sucesso!");
                        JOptionPane.showMessageDialog(null, "Exclusão do cadastro do cliente cancelado!");
                    }
                }
            }
        });

    }

    private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        clientes = new ClientesDAO().listarTodos();
        for (Clientes clientes : clientes) {
            tableModel.addRow(
                    new Object[] { clientes.getNome(), clientes.getIdade(), clientes.getCidade(), clientes.getCpf(),
                            clientes.getTelefone() });
        }
    }
}