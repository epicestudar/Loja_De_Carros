package Model;

public class Clientes {
    // atributos
    private String cpf;
    private String nome;
    private String telefone;
    private String idade;
    private String cidade;
    public Clientes(String cpf, String nome, String telefone, String idade, String cidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.idade = idade;
        this.cidade = cidade;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getIdade() {
        return idade;
    }
    public void setIdade(String idade) {
        this.idade = idade;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    
}