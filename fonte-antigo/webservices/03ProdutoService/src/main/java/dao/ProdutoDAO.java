package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends DAO {

	public ProdutoDAO() {
		super();
		// Certifica que a tabela existe no banco de dados.
		// É uma boa prática, mas você pode remover se já tiver criado a tabela manualmente.
		try (Statement st = connection.createStatement()) {
			st.execute("CREATE TABLE IF NOT EXISTS produto ("
					+ "id SERIAL PRIMARY KEY, "
					+ "descricao VARCHAR(255) NOT NULL, "
					+ "preco REAL NOT NULL, "
					+ "quantidade INTEGER NOT NULL, "
					+ "dataFabricacao TIMESTAMP NOT NULL, "
					+ "dataValidade DATE NOT NULL)");
		} catch (SQLException e) {
			System.out.println("Erro ao criar a tabela 'produto': " + e.getMessage());
		}
	}

	public void add(Produto produto) {
		String sql = "INSERT INTO produto (descricao, preco, quantidade, dataFabricacao, dataValidade) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, produto.getDescricao());
			st.setFloat(2, produto.getPreco());
			st.setInt(3, produto.getQuant());
			st.setTimestamp(4, Timestamp.valueOf(produto.getDataFabricacao()));
			st.setDate(5, java.sql.Date.valueOf(produto.getDataValidade()));
			
			st.executeUpdate();
			
			try (ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					produto.setId(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir produto: " + e.getMessage());
		}
	}

	public Produto get(int id) {
		Produto produto = null;
		String sql = "SELECT * FROM produto WHERE id = ?";
		try (PreparedStatement st = connection.prepareStatement(sql)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					produto = new Produto(
							rs.getInt("id"),
							rs.getString("descricao"),
							rs.getFloat("preco"),
							rs.getInt("quantidade"),
							rs.getTimestamp("dataFabricacao").toLocalDateTime(),
							rs.getDate("dataValidade").toLocalDate()
					);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar produto: " + e.getMessage());
		}
		return produto;
	}

	public void update(Produto p) {
		String sql = "UPDATE produto SET descricao = ?, preco = ?, quantidade = ?, dataFabricacao = ?, dataValidade = ? WHERE id = ?";
		try (PreparedStatement st = connection.prepareStatement(sql)) {
			st.setString(1, p.getDescricao());
			st.setFloat(2, p.getPreco());
			st.setInt(3, p.getQuant());
			st.setTimestamp(4, Timestamp.valueOf(p.getDataFabricacao()));
			st.setDate(5, java.sql.Date.valueOf(p.getDataValidade()));
			st.setInt(6, p.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar produto: " + e.getMessage());
		}
	}

	public void remove(Produto p) {
		String sql = "DELETE FROM produto WHERE id = ?";
		try (PreparedStatement st = connection.prepareStatement(sql)) {
			st.setInt(1, p.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao remover produto: " + e.getMessage());
		}
	}

	public List<Produto> getAll() {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produto ORDER BY id";
		try (Statement st = connection.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				produtos.add(new Produto(
						rs.getInt("id"),
						rs.getString("descricao"),
						rs.getFloat("preco"),
						rs.getInt("quantidade"),
						rs.getTimestamp("dataFabricacao").toLocalDateTime(),
						rs.getDate("dataValidade").toLocalDate()
				));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar produtos: " + e.getMessage());
		}
		return produtos;
	}
}