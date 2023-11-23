package View;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import Controller.CarrosDAO;
import Controller.ClientesDAO;
import Model.Carros;
import Model.Clientes;

public class VendasPainel extends JPanel {
    JComboBox<String> carrosComboBox;
    List<Carros> carros;

    JComboBox<String> clientesComboBox;
    List<Clientes> clientes;

    public VendasPainel() {
        super();

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
                    + " " + cliente.getCidade()
                    + " " + cliente.getCpf());
        }
        add(clientesComboBox);
    }

    public void atualizarComboBox() {
        // Preencha o JComboBox com os carros
        carros = new CarrosDAO().listarTodos();
        carrosComboBox.removeAllItems();
        carrosComboBox.addItem("Selecione o Carro");
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca()
                    + " " + carro.getModelo()
                    + " " + carro.getPlaca());
        }

        clientes = new ClientesDAO().listarTodos();
        clientesComboBox.removeAllItems();
        clientesComboBox.addItem("Selecione o Cliente:");
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCidade()
                    + " " + cliente.getCpf());
        }
    }
}
