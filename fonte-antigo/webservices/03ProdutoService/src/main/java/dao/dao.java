package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	public Connection connection;

	public DAO() {
		try {
			// Carrega o driver JDBC para o PostgreSQL
			Class.forName("org.postgresql.Driver");

			// Conecta ao banco de dados usando suas credenciais
			// SUBSTITUA 'sua_base', 'seu_usuario' e 'sua_senha' pelos seus dados
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sua_base", "seu_usuario", "sua_senha");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC do PostgreSQL não encontrado!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Erro ao conectar com o banco de dados!");
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("Erro ao fechar a conexão com o banco de dados!");
			e.printStackTrace();
		}
	}
}