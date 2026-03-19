package model;

import java.time.LocalDate;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;
	private LocalDate dataCadastro;
	private boolean ativo;
	
	public Usuario(String nome, String email, String telefone, String cpf) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.cpf = cpf;
	}
	
	public Usuario(int id, String nome, String email, String telefone, LocalDate dataCadastro, String cpf, boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.cpf = cpf;
		this.dataCadastro = dataCadastro;
		this.ativo = ativo;
	}
	
	public Usuario(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}
	
	public void setNome(String nome) {this.nome = nome;}
	public void setEmail(String email) {this.email = email;}
	public void setTelefone(String telefone) {this.telefone = telefone;}
	public void setAtivo(boolean ativo) {this.ativo = ativo;}
	
	public int getId() {return id;}
	public String getNome() {return nome;}
	public String getEmail() {return email;}
	public String getTelefone() {return telefone;}
	public String getCpf () {return cpf;}
	public LocalDate getDataCadastro () {return dataCadastro;}
	public boolean getAtivo() {return ativo;}
}
