package Model;

public class Vendas {
    // atributos
    private String dataVenda;
    private String cliente;
    private String carroVendido;
    private double valor;

    // construtor
    public Vendas(String dataVenda, String cliente, String carroVendido, double valor) {
        this.dataVenda = dataVenda;
        this.cliente = cliente;
        this.carroVendido = carroVendido;
        this.valor = valor;
    }

    // gets and sets
    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCarroVendido() {
        return carroVendido;
    }

    public void setCarroVendido(String carroVendido) {
        this.carroVendido = carroVendido;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
