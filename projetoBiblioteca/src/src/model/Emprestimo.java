package model;

import java.time.LocalDate;

public class Emprestimo {
	private int idEmprestimo;
	private LocalDate dataEmprestimo;
	private LocalDate dataPrevistaDevolucao;
	private LocalDate dataDevolucao;
	private status statusDevolucao;
	private Usuario usuario;
	private Livro livro;
	
	public enum status{
		ATIVO,
		DEVOLVIDO,
		ATRASADO
	}
	
	public Emprestimo(LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao, Usuario usuario, Livro livro) {
		this.dataEmprestimo = dataEmprestimo;
		this.dataPrevistaDevolucao = dataPrevistaDevolucao;
		this.dataDevolucao = dataDevolucao;
		this.usuario = usuario;
		this.livro = livro;
	}
	
	public Emprestimo(int idEmprestimo, LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao, status statusDevolucao, Usuario usuario, Livro livro) {
		this.idEmprestimo = idEmprestimo;
		this.dataEmprestimo = dataEmprestimo;
		this.dataPrevistaDevolucao = dataPrevistaDevolucao;
		this.dataDevolucao = dataDevolucao;
		this.statusDevolucao = statusDevolucao;
		this.usuario = usuario;
		this.livro = livro;
	}
	
	public void setDataDevolucao(LocalDate dataDevolucao) {this.dataDevolucao = dataDevolucao;}
	public void setStatusDevolucao(status statusDevolucao) {this.statusDevolucao = statusDevolucao;}
	
	public int getIdEmprestimo() {return idEmprestimo;}
	public LocalDate getDataEmprestimo() {return dataEmprestimo;}
	public LocalDate getDataPrevistaDevolucao() {return dataPrevistaDevolucao;}
	public LocalDate getDataDevolucao() {return dataDevolucao;}
	public status getStatusDevolucao() {return statusDevolucao;}
	public Usuario getUsuario() {return usuario;}
	public Livro getLivro() {return livro;}
}
