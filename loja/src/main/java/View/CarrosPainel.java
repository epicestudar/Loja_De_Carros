package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.Carros;

public class CarrosPainel extends JPanel{
    // Atributos(componentes)
    private JButton cadastrar, apagar, editar, atualizar;
    private JTextField carMarcaField, carModeloField, carAnoField, carPlacaField, carValorField;
    private int linhaSelecionada = -1;
    ArrayList<Carros> listaCarros = new ArrayList<Carros>();
    public JTable table;
    // construtor(GUI - Jpanel)
    public CarrosPainel() {
        super();
        // entrada de dados
         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro Carros"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Marca"));
        carMarcaField = new JTextField(20);
        inputPanel.add(carMarcaField);
        inputPanel.add(new JLabel("Modelo"));
        carModeloField = new JTextField(20);
        inputPanel.add(carModeloField);
        inputPanel.add(new JLabel("Ano"));
        carAnoField = new JTextField(20);
        inputPanel.add(carAnoField);
        inputPanel.add(new JLabel("Placa"));
        carPlacaField = new JTextField(20);
        inputPanel.add(carPlacaField);
        inputPanel.add(new JLabel("Valor"));
        carValorField = new JTextField(20);
        inputPanel.add(carValorField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        botoes.add(atualizar = new JButton("Atualizar"));

        add(botoes);
        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "Marca", "Modelo", "Ano", "Placa", "Valor" }));
        jSPane.setViewportView(table);


        // botões de eventos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputPanel.setToolTipText((String) table.getValueAt(linhaSelecionada, 0));
                    inputPanel.setToolTipText((String) table.getValueAt(linhaSelecionada, 1));
                    inputPanel.setToolTipText(table.getValueAt(linhaSelecionada, 2).toString());
                    inputPanel.setToolTipText((String) table.getValueAt(linhaSelecionada, 3));
                    inputPanel.setToolTipText(table.getValueAt(linhaSelecionada, 4).toString());
                }
            }
        });
    }
}
