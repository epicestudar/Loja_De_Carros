package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.*;
public class FrameBase extends JFrame {
    // criação do tabbedPane para incluir as tabs
    private JTabbedPane jTPane;

    public FrameBase() {
        jTPane = new JTabbedPane();
        add(jTPane);
        // criandos as tabs
        // tab1 carros
        CarrosPainel tab1 = new CarrosPainel();
        ClientesPainel tab2 = new ClientesPainel();
        VendasPainel tab3 = new VendasPainel();
        jTPane.add("Carros", tab1);
        jTPane.add("Clientes", tab2);
        jTPane.add("Vendas", tab3);
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // métodos para tornar a janela visível
    public void run() {
        this.setVisible(true);
    }
}
