package model;

import java.time.LocalDate;

public class Livro {
	private int id;
	private String titulo;
	private String autor;
	private String isbn;
	private LocalDate anoPublicacao;
	private int quantidadeEstoque;
	private boolean ativo;
	
	public Livro(String titulo, String autor, String isbn, LocalDate anoPublicacao, int quantidadeEstoque) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.anoPublicacao = anoPublicacao;
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	public Livro(int id,String titulo, String autor, String isbn, LocalDate anoPublicacao, int quantidadeEstoque, boolean ativo) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.anoPublicacao = anoPublicacao;
		this.quantidadeEstoque = quantidadeEstoque;
		this.ativo = ativo;
	}
	
	public Livro(String titulo) {
		this.titulo = titulo;
	}
	
	public void setTitulo (String titulo) {this.titulo = titulo;}
	public void setQuantidadeEstoque (int quantidadeEstoque) {this.quantidadeEstoque = quantidadeEstoque;}
	public void setAtivo (boolean ativo) {this.ativo = ativo;}
	
	public int getId() {return id;}
	public String getTitulo() {return titulo;}
	public String getAutor() {return autor;}
	public String getIsbn() {return isbn;}
	public LocalDate getAnoPublicacao() {return anoPublicacao;}
	public int getQuantidadeEstoque() {return quantidadeEstoque;}
	public boolean getAtivo() {return ativo;}
	
}
