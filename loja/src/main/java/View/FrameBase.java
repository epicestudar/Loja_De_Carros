package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class FrameBase extends JFrame{
    public FrameBase() {
        super("Aplicação");
        setDefaultCloseOperation(2);
        JTabbedPane abas = new JTabbedPane();
        abas.add("Carros", new CarrosPainel());
        abas.add("Clientes", new ClientesPainel());
        abas.add("Vendas", new VendasPainel());
        add(abas);
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
