package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDao {
	ConexaoDao con = new ConexaoDao();
	Connection conexao = con.conectar();
	
	public void criarUsuario(Usuario usuario) throws SQLException {
		String insert = "INSERT INTO usuario (nome, email, telefone, data_cadastro, cpf) VALUES(?, ?, ?, CURDATE(), ?)";
		PreparedStatement stmt = conexao.prepareStatement(insert);
		
		try {
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getTelefone());
			stmt.setString(4, usuario.getCpf());
			
			stmt.executeUpdate();
			
			System.out.println("Usuário cadastrado com sucesso!");
		} catch(SQLException e) {
			System.out.println("Erro ao cadastrar usuário: " + e);
		}
	}
	
	public List<Usuario> listarUsuario() throws SQLException {
		String select = "SELECT * FROM usuario WHERE ativo = TRUE";
		PreparedStatement stmt = conexao.prepareStatement(select);
		ResultSet rs = stmt.executeQuery();
		List<Usuario> usuario = new ArrayList<>();
		
		while(rs.next()) {
			
			usuario.add(new Usuario(rs.getInt("id_usuario"),rs.getString("nome"),rs.getString("email"),rs.getString("telefone"),rs.getDate("data_cadastro").toLocalDate(),rs.getString("cpf"),rs.getBoolean("ativo")));
		
		}
		return usuario;
	}
	
	public List<Usuario> filtrarUsuario(String nome) throws SQLException{
		String select = "SELECT * FROM usuario WHERE nome LIKE ? AND ativo = TRUE";
		PreparedStatement stmt = conexao.prepareStatement(select);
		stmt.setString(1, "%"+nome+"%");
		
		ResultSet rs = stmt.executeQuery();
		List<Usuario> usuario = new ArrayList<>();
		
		while(rs.next()) {
			
			usuario.add(new Usuario(rs.getInt("id_usuario"),rs.getString("nome"),rs.getString("email"),rs.getString("telefone"),rs.getDate("data_cadastro").toLocalDate(),rs.getString("cpf"),rs.getBoolean("ativo")));
			
		}
		
		return usuario;
	}
	
	public boolean inativarAtivarUsuario(int idUsuario) throws SQLException {
		String update = "UPDATE usuario SET ativo = NOT ativo WHERE id_usuario = ?";
		PreparedStatement stmt = conexao.prepareStatement(update);
		try {
			stmt.setInt(1, idUsuario);
			stmt.executeUpdate();
			
			String select = "SELECT * FROM usuario WHERE id_usuario = ?";
			PreparedStatement stmtSelect = conexao.prepareStatement(select);
			stmtSelect.setInt(1, idUsuario);
			
			ResultSet rs = stmtSelect.executeQuery();
			rs.next();
			
			boolean retorno = rs.getBoolean("ativo");
			return retorno;
			
		}catch(SQLException e){
			System.out.println("Erro ao buscar o cliente: " + e);
		}
		
		
		return false;
	}
}
