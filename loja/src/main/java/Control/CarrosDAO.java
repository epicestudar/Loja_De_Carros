package Control;

import java.sql.Connection;

import Connection.ConnectionFactory;

public class CarrosDAO {
    // códigos para o banco de dados
     // atributo
    private Connection connection;

    public CarrosDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
}
