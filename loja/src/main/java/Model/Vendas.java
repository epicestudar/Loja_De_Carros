package Model;

public class Vendas {
    // atributos
    private String dataVenda;
    private String cliente; 
    private String carroVendido; // primary key
    private String valor;
    public Vendas(String dataVenda, String cliente, String carroVendido, String valor) {
        this.dataVenda = dataVenda;
        this.cliente = cliente;
        this.carroVendido = carroVendido;
        this.valor = valor;
    }
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
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

    
}