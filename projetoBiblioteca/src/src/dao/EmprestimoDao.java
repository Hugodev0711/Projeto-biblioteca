package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

public class EmprestimoDao {
	ConexaoDao con = new ConexaoDao();
	Connection conectar = con.conectar();
	
	public boolean emprestarLivro(int idUsuario, int idLivro) throws SQLException {
		String select = "SELECT * FROM livros WHERE id_livro = ?";
		PreparedStatement stmtSelect = conectar.prepareStatement(select);
		stmtSelect.setInt(1, idLivro);
		ResultSet rs = stmtSelect.executeQuery();
		
		if(rs.next()) {
			if(rs.getInt("quantidade_estoque") <= 0) {
				return false;
			}
			String insert = "INSERT INTO emprestimo (data_emprestimo, data_prevista_devolucao, data_devolucao, id_usuario, id_livro) VALUES (CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL, ?, ?)";
			PreparedStatement stmtInsert = conectar.prepareStatement(insert);
			try {
				stmtInsert.setInt(1, idUsuario);
				stmtInsert.setInt(2, idLivro);
				stmtInsert.executeUpdate();
			}catch(SQLException e) {
				System.out.println("Erro ao realizar empréstimo: " + e);
				return false;
			}
			String update = "UPDATE livros SET quantidade_estoque = quantidade_estoque - 1 WHERE id_livro = ? AND quantidade_estoque>0";
			PreparedStatement stmtUpdate = conectar.prepareStatement(update);
			
			try {
				stmtUpdate.setInt(1, idLivro);
				stmtUpdate.executeUpdate();
				return true;
			}catch(SQLException e) {
				System.out.println("Erro ao realizar empréstimo: " + e);
				return false;
			}
			
		}
		
		
		return false;
	}
	
	public List<Emprestimo> listarEmprestimo() throws SQLException {
		List<Emprestimo> emprestimos = new ArrayList<>();
		try {
			String call = "{CALL SP_listar_emprestimos()}";
			CallableStatement cs = conectar.prepareCall(call);
			ResultSet rs = cs.executeQuery();
			while(rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nome"), rs.getString("email"));
				Livro livro = new Livro(rs.getString("titulo"));
				Emprestimo.status statusEmprestimo = Emprestimo.status.valueOf(rs.getString("status_devolucao").toUpperCase());
				LocalDate dataDevolucao = rs.getDate("data_devolucao") != null
							? rs.getDate("data_devolucao").toLocalDate()
							: null;
							
				emprestimos.add(new Emprestimo(rs.getInt("id_emprestimo"),
						rs.getDate("data_emprestimo").toLocalDate(),
						rs.getDate("data_prevista_devolucao").toLocalDate(),
						dataDevolucao,
						statusEmprestimo,
						usuario,
						livro
						));
				}
		
		}catch(SQLException e) {
			System.out.println("Erro: " + e);
		}
		
		return emprestimos;
	}
	
	public boolean realizarDevolucao(int idDevolucao) throws SQLException {
		try {
			String call = "{CALL SP_realizar_devolucao(?)}";
			CallableStatement cs = conectar.prepareCall(call);
			cs.setInt(1, idDevolucao);
		
			cs.execute();
			return true;
			
		}catch(SQLException e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}
	
	public List<Emprestimo> buscarEmprestimo(int idDevolucao) throws SQLException{
		List<Emprestimo> emprestimos = new ArrayList<>();
		try {
			String call = "{CALL SP_buscar_emprestimo(?)}";
			CallableStatement cs = conectar.prepareCall(call);
			cs.setInt(1, idDevolucao);
			ResultSet rs = cs.executeQuery();
			
			while(rs.next()) {
				Usuario usuario = new Usuario(rs.getString("nome"), rs.getString("email"));
				Livro livro = new Livro(rs.getString("titulo"));
				Emprestimo.status statusEmprestimo = Emprestimo.status.valueOf(rs.getString("status_devolucao").toUpperCase());
				LocalDate dataDevolucao = rs.getDate("data_devolucao") != null
							? rs.getDate("data_devolucao").toLocalDate()
							: null;
							
				emprestimos.add(new Emprestimo(rs.getInt("id_emprestimo"),
						rs.getDate("data_emprestimo").toLocalDate(),
						rs.getDate("data_prevista_devolucao").toLocalDate(),
						dataDevolucao,
						statusEmprestimo,
						usuario,
						livro
						));
			}
			
		}catch(SQLException e) {
			System.out.println("erro: " + e);
		}
		
		return emprestimos;
	}
}

