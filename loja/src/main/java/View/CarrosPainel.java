package View;

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
import Controller.CarrosDAO;
import Controller.CarrosControl;
import Model.Carros;
import Model.Clientes;

public class CarrosPainel extends JPanel {
    private JButton cadastrar, apagar, editar;
    private JTextField carMarcaField, carModeloField, carAnoField, carPlacaField,
            carValorField;
    private List<Carros> carros;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    // construtor(GUI - Jpanel)
    public CarrosPainel() {
        super();
        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Marca")).setFont(new Font("Arial", Font.PLAIN, 16));
        carMarcaField = new JTextField(20);
        inputPanel.add(carMarcaField);
        inputPanel.add(new JLabel("Modelo")).setFont(new Font("Arial", Font.PLAIN, 16));
        carModeloField = new JTextField(20);
        inputPanel.add(carModeloField);
        inputPanel.add(new JLabel("Ano")).setFont(new Font("Arial", Font.PLAIN, 16));
        carAnoField = new JTextField(20);
        inputPanel.add(carAnoField);
        inputPanel.add(new JLabel("Placa")).setFont(new Font("Arial", Font.PLAIN, 16));
        carPlacaField = new JTextField(20);
        inputPanel.add(carPlacaField);
        inputPanel.add(new JLabel("Valor")).setFont(new Font("Arial", Font.PLAIN, 16));
        carValorField = new JTextField(20);
        inputPanel.add(carValorField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar")).setBackground((Color.CYAN));
        botoes.add(editar = new JButton("Editar")).setBackground((Color.GREEN));
        botoes.add(apagar = new JButton("Apagar")).setBackground((Color.RED));
        add(botoes);

        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Marca", "Modelo", "Ano", "Placa", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        // botões de eventos
        // tratamento de eventos(construtor)
        // tratamento de Eventos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    carMarcaField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    carModeloField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    carAnoField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    carPlacaField.setText((String) table.getValueAt(linhaSelecionada, 3));
                    carValorField.setText((String) table.getValueAt(linhaSelecionada, 4));
                }
            }
        });
        // Cria um objeto operacoes da classe CarrosControl para executar operações no
        // banco de dados

        new CarrosDAO().criaTabela();

        atualizarTabela();

        CarrosControl operacoes = new CarrosControl(carros, tableModel, table);
        // Configura a ação do botão "cadastrar" para adicionar um novo registro no
        // banco
        // de dados
        cadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (carMarcaField.getText().isEmpty() || carModeloField.getText().isEmpty()
                        || carAnoField.getText().isEmpty() || carPlacaField.getText().isEmpty()
                        || carValorField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de cadastrar o carro");
                } else if (!carAnoField.getText().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "O campo 'ano' deve conter apenas números");
                } else if (!carValorField.getText().matches("[0-9]+([,.][0-9]+)?")) {
                    JOptionPane.showMessageDialog(null,
                            "O campo 'valor' deve conter apenas números e caracteres especiais");
                } else {
                    int confirmacao1 = JOptionPane.showConfirmDialog(null,
                            "Tem certeza de que deseja cadastrar o carro?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirmacao1 == JOptionPane.YES_NO_OPTION) {
                        operacoes.cadastrar(carMarcaField.getText(), carModeloField.getText(),

                                carAnoField.getText(), carPlacaField.getText(), carValorField.getText());
                        // Limpa os campos de entrada após a operação de cadastro
                        carMarcaField.setText("");
                        carModeloField.setText("");
                        carAnoField.setText("");
                        carPlacaField.setText("");
                        carValorField.setText("");
                        // JOptionPane.showMessageDialog(null, "O carro do modelo " + carModeloField.getText()
                        //         + " da placa " + carPlacaField.getText() + " foi cadastrado com sucesso!");
                        JOptionPane.showMessageDialog(null, "O carro foi cadastrado com sucesso!");
                    }
                }

            }
        });

        // Configura a ação do botão "editar" para atualizar um registro no banco de
        // dados
        editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (carPlacaField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione algo para editar");
                } else {
                    operacoes.atualizar(carMarcaField.getText(), carModeloField.getText(),

                            carAnoField.getText(), carPlacaField.getText(), carValorField.getText());
                    // Limpa os campos de entrada após a operação de atualização
                    carMarcaField.setText("");
                    carModeloField.setText("");
                    carAnoField.setText("");
                    carPlacaField.setText("");
                    carValorField.setText("");
                    JOptionPane.showMessageDialog(null, "Carro editado com sucesso!");
                }
            }
        });

        // Configura a ação do botão "apagar" para excluir um registro no banco de dados
        apagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (carPlacaField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha com a placa do carro que deseja apagar");
                } else {
                    int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar o carro?",
                            "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (confirmacao == JOptionPane.YES_NO_OPTION) {
                        operacoes.apagar(carPlacaField.getText());
                        // Limpa os campos de entrada após a operação de exclusão
                        carMarcaField.setText("");
                        carModeloField.setText("");
                        carAnoField.setText("");
                        carPlacaField.setText("");
                        carValorField.setText("");
                        // JOptionPane.showMessageDialog(null, "O carro do modelo " + carModeloField.getText()
                        //         + " da placa " + carPlacaField.getText() + " foi apagado com sucesso!");
                        JOptionPane.showMessageDialog(null, "O carro foi apagado com sucesso!");
                    }
                }
            }
        });

    }

    private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        carros = new CarrosDAO().listarTodos();
        for (Carros carros : carros) {
            tableModel.addRow(new Object[] { carros.getMarca(), carros.getModelo(), carros.getAno(), carros.getPlaca(),
                    carros.getValor() });
        }
    }
}