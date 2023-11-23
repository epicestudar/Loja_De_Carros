package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Clientes;
import Model.Vendas;

public class VendasControl {
    private List<Vendas> vendas;
    private DefaultTableModel tableModel;
    private JTable table;
    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }

     // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Vendas vendas : vendas) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { vendas.getCliente(), vendas.getCarroVendido(),
                    vendas.getDataVenda(), vendas.getValor() });
        }
    }

     // Método para cadastrar um novo carro no banco de dados
     public void cadastrar(String cliente, String carroVendido, String dataVenda, String valor) {
        new VendasDAO().cadastrar(cliente, carroVendido, dataVenda, valor);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

     // Método para atualizar os dados de um carro no banco de dados
     public void atualizar(String cliente, String idade, String dataVenda, String valor) {
        new VendasDAO().atualizar(cliente, idade, dataVenda, valor);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new VendasDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }
}
