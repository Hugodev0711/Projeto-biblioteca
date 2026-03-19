package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.ConexaoDao;
import dao.EmprestimoDao;
import dao.LivroDao;
import dao.UsuarioDao;
import model.Usuario;
import model.Emprestimo;
import model.Livro;

public class Main {
	
	public static void menuPrincipal(Scanner scanner, Connection conexao) throws SQLException {
		
		
		int escolha;
		do {
			System.out.println("====Menu Principal====");
			System.out.println("[1] - Usuario1\n"
					+ "[2] Livros\n"
					+ "[3] Empréstimo\n"
					+ "[0] Sair");
			escolha = scanner.nextInt();
			scanner.nextLine();
			switch(escolha) {
				case 0:
					return;
				case 1:
					menuUsuario(scanner, conexao);
					break;
				case 2:
					menuLivro(scanner, conexao);
					break;
				case 3:
					menuEmprestimo(scanner);
				default:
					System.out.println("Digite uma opção válida.\n");
			}
		}while(escolha != 0);
	}
	
	public static void menuUsuario(Scanner scanner, Connection conexao) throws SQLException {
		int escolha;
		do {
			System.out.println("----Menu usuário----");
			System.out.println("[1] - Cadastrar usuário\n"
					+ "[2] - Listar usuário\n"
					+ "[3] - Filtrar usuário\n"
					+ "[4] - Inativar/Ativar usuário\n"
					+ "[0] - Voltar ");
			
			escolha = scanner.nextInt();
			scanner.nextLine();
			
			switch (escolha) {
				case 0:
					return;
			
				case 1:
					criarUsuario(scanner);
					break;
					
				case 2:
					listarUsuario();
					break;
					
				case 3:
					filtrarUsuario(scanner);
					break;
					
				case 4:
					inativarAtivarUsuario(scanner);
					break;
					
				default:
					System.out.println("Escolha uma opção válida!");
			}
		}while(escolha != 0);
	}
	
	public static void criarUsuario(Scanner scanner) throws SQLException {
		UsuarioDao usuarioDao = new UsuarioDao();
		
		System.out.println("----Cadastro de Usuário----");
		System.out.println("Digite o nome do usuário: ");
		String nome = scanner.nextLine();
		
		System.out.println("Digite o email do usuário: ");
		String email = scanner.nextLine();
		
		System.out.println("Digite o telefone do usuário: ");
		String telefone = scanner.nextLine();
		
		System.out.println("Digite o CPF do usuário: ");
		String cpf = scanner.nextLine();
		
		Usuario usuario = new Usuario(nome, email, telefone, cpf);
		usuarioDao.criarUsuario(usuario);
		
	}
	
	public static void listarUsuario() throws SQLException{
		UsuarioDao usuarioDao = new UsuarioDao();
		List<Usuario> usuarios = usuarioDao.listarUsuario();
		
		for(Usuario usuario : usuarios) {
			System.out.println("ID: " + usuario.getId());
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("Cpf: " + usuario.getCpf());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Telefone: " + usuario.getTelefone());
			System.out.println("Data de cadastro: " + usuario.getDataCadastro());
			System.out.println("Ativo?: " + usuario.getAtivo());
			System.out.println();
			
		}	
	}
	
	public static void filtrarUsuario(Scanner scanner) throws SQLException{
		UsuarioDao usuarioDao = new UsuarioDao();
		
		System.out.println("----Filtrar Usuário----");
		System.out.println("Digite o nome do cliente que deseja buscar: ");
		String nome = scanner.nextLine();
		
		List<Usuario> usuarios = usuarioDao.filtrarUsuario(nome);
		
		System.out.println("\n----Usuarios encontrados0---- \n");
		
		for(Usuario usuario : usuarios) {
			System.out.println("ID: " + usuario.getId());
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("Cpf: " + usuario.getCpf());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Telefone: " + usuario.getTelefone());
			System.out.println("Data de cadastro: " + usuario.getDataCadastro());
			System.out.println("Ativo?: " + usuario.getAtivo());
			System.out.println();
		}
	}
	
	public static void inativarAtivarUsuario(Scanner scanner) throws SQLException {
		UsuarioDao usuarioDao = new UsuarioDao();
		System.out.println("----Inativar/Ativar Usuario----");
		
		System.out.println("Digite o ID do usuário a ser inativado/ativado:");
		int idUsuario = scanner.nextInt();
		scanner.nextLine();
		
		boolean retorno = usuarioDao.inativarAtivarUsuario(idUsuario);
		
		if(retorno == false) {
			System.out.println("Usuario inativado.");
		}else {
			System.out.println("Usuario ativado!");
		}
	}
	
	public static void menuLivro(Scanner scanner, Connection conexao) throws SQLException {
		int escolha;
		do {
			System.out.println("----Menu livros----");
			System.out.println("[1] - Cadastrar livro\n"
					+ "[2] - Listar livro\n"
					+ "[3] - Filtrar livro\n"
					+ "[0] - Voltar ");
			
			escolha = scanner.nextInt();
			scanner.nextLine();
			
			switch (escolha) {
				case 0:
					return;
			
				case 1:
					cadastrarLivro(scanner);
					break;
					
				case 2:
					listarLivros();
					break;
					
				case 3:
					filtrarLivros(scanner);
					
					break;
	
				default:
					System.out.println("Escolha uma opção válida!");
			}
		}while(escolha != 0);
	}
	
	public static void cadastrarLivro(Scanner scanner) throws SQLException {
		LivroDao livroDao = new LivroDao();
		
		System.out.println("----Cadastro de Livros---=");
		System.out.println("Digite o titulo do livro: ");
		String titulo = scanner.nextLine();
		
		System.out.println("Digite o autor do livro: ");
		String autor = scanner.nextLine();
		
		System.out.println("Digite o isbn do livro: ");
		String isbn = scanner.nextLine();
		
		System.out.println("Digite a data de publicação: ");
		String dataPublicacao = scanner.nextLine();
		LocalDate data = LocalDate.parse(dataPublicacao);
		
		System.out.println("Digite a quantidade do estoque do livro: ");
		int quantidadeEstoque = scanner.nextInt();
		
		Livro livros = new Livro(titulo, autor, isbn, data, quantidadeEstoque);
		livroDao.cadastrarLivro(livros);
		
	}
	
	public static void listarLivros() throws SQLException{
		LivroDao livroDao = new LivroDao();
		List<Livro> livros = livroDao.listarLivro();
		
		System.out.println("----Livros cadastrados----\n");
		
		for(Livro livro : livros) {
			System.out.println("ID: " + livro.getId());
			System.out.println("Titulo: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor());
			System.out.println("ISBN" + livro.getIsbn());
			System.out.println("Data de publicação: " + livro.getAnoPublicacao());
			System.out.println("Quantiade no estoque: " + livro.getQuantidadeEstoque());
			System.out.println("Ativo?: " + livro.getAtivo());
			System.out.println();
		}
		
	}
	
	public static void filtrarLivros(Scanner scanner) throws SQLException {
		LivroDao livroDao = new LivroDao();
		
		System.out.println("----Filtrar Livros----");
		System.out.println("Digite o título do livro que deseja buscar:");
		String titulo = scanner.nextLine();
		
		List<Livro> livros = livroDao.filtrarLivro(titulo);
		
		System.out.println("----Livros encontrados----");
		
		for(Livro livro : livros) {
			System.out.println("ID: " + livro.getId());
			System.out.println("Titulo: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor());
			System.out.println("ISBN" + livro.getIsbn());
			System.out.println("Data de publicação: " + livro.getAnoPublicacao());
			System.out.println("Quantiade no estoque: " + livro.getQuantidadeEstoque());
			System.out.println("Ativo: " + livro.getAtivo());
			System.out.println();
		}
	}
	
	public static void menuEmprestimo(Scanner scanner) throws SQLException{
		int escolha;
		do {
			System.out.println("----Menu Empréstimos----");
			System.out.println("[1] - Realizar empréstimo\n"
					+ "[2] - Realizar devolução\n"
					+ "[3] - Listar empréstimos\n"
					+ "[4] - Buscar empréstimos\n"
					+ "[0] - Voltar ");
			
			escolha = scanner.nextInt();
			scanner.nextLine();
			
			switch (escolha) {
				case 0:
					return;

				case 1:
					cadastrarEmprestimo(scanner);
					break;
					
				case 2:
					realizarDevolucao(scanner);
					break;
					
				case 3:
					listarEmprestimo();
					break;
					
				case 4:
					buscarEmprestimo(scanner);
					break;
					
				default:
					System.out.println("Escolha uma opção válida!");
			}
		}while(escolha != 0);
	}
	
	public static void cadastrarEmprestimo(Scanner scanner) throws SQLException {
		EmprestimoDao emprestimoDao = new EmprestimoDao();
		System.out.println("----Realizar Empréstimo----");
		System.out.println("Digite o ID do usuário que vai receber o empréstimo:");
		int idUsuario = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("Digite o ID do livro que vai ser emprestado:");
		int idLivro = scanner.nextInt();
		scanner.nextLine();
				
		boolean retorno = emprestimoDao.emprestarLivro(idUsuario, idLivro); 
		
		if(retorno) {
			System.out.println("Empréstimo realizado!");
		}else {
			System.out.println("Erro ao realizar o empréstimo.");
		}
		
	}
	
	public static void listarEmprestimo()throws SQLException{
		EmprestimoDao emprestimoDao = new EmprestimoDao();
		System.out.println("Empréstimos encontrados: \n");
		
		List<Emprestimo> emprestimos = emprestimoDao.listarEmprestimo();
		for(Emprestimo emprestimo : emprestimos) {
			System.out.println("Id do empréstimo: " + emprestimo.getIdEmprestimo());
			System.out.println("Data do empréstimo: " + emprestimo.getDataEmprestimo());
			System.out.println("Data prevista de devolução: " + emprestimo.getDataPrevistaDevolucao());
			System.out.println("Data de devolução: " + emprestimo.getDataDevolucao());
			System.out.println("Status: " + emprestimo.getStatusDevolucao());
			System.out.println("Nome: " + emprestimo.getUsuario().getNome());
			System.out.println("Titulo: " + emprestimo.getLivro().getTitulo());
			System.out.print("\n");
		}
	}
	
	public static void realizarDevolucao(Scanner scanner) throws SQLException{
		EmprestimoDao emprestimoDao = new EmprestimoDao();
		System.out.println("----Realizar devolução----\n");
		
		System.out.println("Digite o id do empréstimo a ser finalizado:");
		int idEmprestimo = scanner.nextInt();
		
		boolean retorno = emprestimoDao.realizarDevolucao(idEmprestimo);
		
		if(retorno) {
			System.out.println("Devolução realizada, volte sempre!");
		}else {
			System.out.println("Erro ao realizar a devolução...");
		}
	}
	
	public static void buscarEmprestimo(Scanner scanner) throws SQLException{
		EmprestimoDao emprestimoDao = new EmprestimoDao();
		System.out.println("----Buscar empréstimo----\n");
		
		System.out.println("Digite o id do empréstimo a ser finalizado:");
		int idEmprestimo = scanner.nextInt();
		
		List<Emprestimo> emprestimos = emprestimoDao.buscarEmprestimo(idEmprestimo);
		for(Emprestimo emprestimo: emprestimos) {
			System.out.println("Id do empréstimo: " + emprestimo.getIdEmprestimo());
			System.out.println("Data do empréstimo: " + emprestimo.getDataEmprestimo());
			System.out.println("Data prevista de devolução: " + emprestimo.getDataPrevistaDevolucao());
			System.out.println("Data de devolução: " + emprestimo.getDataDevolucao());
			System.out.println("Status: " + emprestimo.getStatusDevolucao());
			System.out.println("Nome: " + emprestimo.getUsuario().getNome());
			System.out.println("Titulo: " + emprestimo.getLivro().getTitulo());
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args) throws SQLException {
		ConexaoDao con = new ConexaoDao();
		Scanner scanner = new Scanner(System.in);
		Connection conectar = con.conectar();
		
		if(conectar == null) {
			System.out.println("Erro ao se conectar.");
		}else {
			menuPrincipal(scanner, conectar);
		}
		scanner.close();
	}
}