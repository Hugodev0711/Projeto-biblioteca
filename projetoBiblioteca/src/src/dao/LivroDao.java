package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Livro;

public class LivroDao {
	 private ConexaoDao con= new ConexaoDao();
	 private Connection conexao = con.conectar();
	
	public void cadastrarLivro(Livro livros) throws SQLException {
		String insert = "INSERT INTO livros (titulo, autor, isbn, ano_publicacao, quantidade_estoque) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = conexao.prepareStatement(insert);
		
		try {
			stmt.setString(1, livros.getTitulo());
			stmt.setString(2, livros.getAutor());
			stmt.setString(3, livros.getIsbn());
			stmt.setDate(4, java.sql.Date.valueOf(livros.getAnoPublicacao()));
			stmt.setInt(5, livros.getQuantidadeEstoque());
			
			stmt.executeUpdate();
			
			System.out.println("Livro cadastrado com sucesso!");
			
		}catch (SQLException e) {
			System.out.println("Erro ao cadastrar livro: " + e);
		}
		
	}
	
	public List<Livro> listarLivro() throws SQLException{
		String select = "SELECT * FROM livros";
		PreparedStatement stmt = conexao.prepareStatement(select);
		
		List<Livro> livros = new ArrayList<>();
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			livros.add(new Livro(rs.getInt("id_livro"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"), rs.getDate("ano_publicacao").toLocalDate(), rs.getInt("quantidade_estoque"), rs.getBoolean("ativo")));
		}
		
		return livros;
	}
	
	public List<Livro> filtrarLivro(String titulo) throws SQLException{
		String select = "SELECT * FROM livros WHERE titulo LIKE ?";
		PreparedStatement stmt = conexao.prepareStatement(select);
		stmt.setString(1, "%"+titulo+"%");
		
		List<Livro> livros = new ArrayList<>();
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			livros.add(new Livro(rs.getInt("id_livro"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"), rs.getDate("ano_publicacao").toLocalDate(), rs.getInt("quantidade_estoque"), rs.getBoolean("ativo")));
		}
		
		return livros;
	}
	
}
