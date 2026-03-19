package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoDao {
	
	private Connection con;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/biblioteca";
	private String usuario = "root";
	private String senha = "1234";
	
	public Connection conectar() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,usuario,senha);
			return con;
			
		}catch(Exception e) {
			System.out.println("Erro ao se conectar: " + e);
			return null;
		}
	}
	
}
