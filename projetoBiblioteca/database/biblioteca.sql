DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

CREATE TABLE IF NOT EXISTS usuario (
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
	email VARCHAR(150) NOT NULL UNIQUE,
	telefone VARCHAR(20) UNIQUE,
    data_cadastro DATE NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    ativo BOOLEAN DEFAULT TRUE,
    cargo VARCHAR(20) NOT NULL,
    senha INT NOT NULL
);

CREATE TABLE IF NOT EXISTS livros (
	id_livro INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL UNIQUE,
	autor VARCHAR(100) NOT NULL,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    ano_publicacao DATE NOT NULL,
    quantidade_estoque INT NOT NULL DEFAULT 0 CHECK (quantidade_estoque >= 0)
);

CREATE TABLE IF NOT EXISTS emprestimo(
	id_emprestimo INT PRIMARY KEY AUTO_INCREMENT,
    data_emprestimo date NOT NULL,
    data_prevista_devolucao date NOT NULL,
    data_devolucao date,
    id_usuario INT NOT NULL,
    id_livro INT NOT NULL,
    status_devolucao VARCHAR(20) DEFAULT "ATIVO",
    FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY(id_livro) REFERENCES livros(id_livro)
);

CREATE TABLE IF NOT EXISTS livros_emprestados(
	id_livro INT NOT NULL,
    id_emprestimo INT NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    
    PRIMARY KEY(id_livro, id_emprestimo),
    FOREIGN KEY(id_livro) REFERENCES livros(id_livro),
    FOREIGN KEY(id_emprestimo) REFERENCES emprestimo(id_emprestimo)

);

INSERT INTO usuario (nome, email, telefone, data_cadastro, cpf, cargo, senha)
VALUES('Hugo', 'hugo@gmail.com','11998206651', '2026-02-18', '54231218846', "ADMIN", "151107"),
('Juan', 'juan@gmail.com', '11922892111', '2026-02-18', '12345678912', "CLIENTE", "158202")

ON DUPLICATE KEY UPDATE
nome = VALUES(nome),
telefone = VALUES(telefone),
email = VALUES(email)
;

INSERT INTO livros (titulo, autor, isbn, ano_publicacao, quantidade_estoque)
VALUES('Dragon Ball VOL.1', 'Akira Toriyama', '9786555124644', '1985-09-10', 10),
('Clean Code', 'Robert C. Martin', '1234567890123', '2008-01-01', 5)	

ON DUPLICATE KEY UPDATE
titulo = VALUES(titulo)
;

INSERT INTO emprestimo (data_emprestimo, data_prevista_devolucao, data_devolucao, id_usuario, id_livro)
VALUES(CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), CURDATE() , 1, 1)

ON DUPLICATE KEY UPDATE
data_devolucao = VALUES(data_devolucao)
;

INSERT IGNORE INTO livros_emprestados(id_livro, id_emprestimo, quantidade)
VALUES(1, 1, 1);

SELECT * FROM usuario;
SELECT * FROM livros;
SELECT u.nome, u.email,l.titulo, e.data_emprestimo, e.data_prevista_devolucao FROM emprestimo e
JOIN usuario u ON e.id_usuario = u.id_usuario
JOIN livros l ON e.id_livro = l.id_livro;


DELIMITER //
CREATE PROCEDURE SP_listar_emprestimos()
BEGIN
	SELECT e.id_emprestimo,e.data_emprestimo, e.data_prevista_devolucao, e.data_devolucao, e.status_devolucao, u.nome, u.email, l.titulo FROM emprestimo e
	JOIN usuario u ON e.id_usuario = u.id_usuario
	JOIN livros l ON e.id_livro = l.id_livro;
END
//

DELIMITER //
CREATE PROCEDURE SP_realizar_devolucao(IN var_id_emprestimo INT)
BEGIN
	DECLARE var_id_livro INT;
	UPDATE emprestimo SET status_devolucao = 'DEVOLVIDO', data_devolucao = CURDATE() 
    WHERE id_emprestimo = var_id_emprestimo AND status_devolucao IN ('ATIVO', 'ATRASADO');
    
    SELECT id_livro FROM emprestimo WHERE id_emprestimo = var_id_emprestimo INTO var_id_livro;
    UPDATE livros SET quantidade_estoque = quantidade_estoque+1 WHERE id_livro = var_id_livro;

END
//

DELIMITER //
CREATE PROCEDURE SP_buscar_emprestimo (IN var_id_emprestimo INT)
BEGIN
	SELECT e.id_emprestimo,e.data_emprestimo, e.data_prevista_devolucao, e.data_devolucao, e.status_devolucao, u.nome, u.email, l.titulo FROM emprestimo e
	JOIN usuario u ON e.id_usuario = u.id_usuario
	JOIN livros l ON e.id_livro = l.id_livro
    WHERE id_emprestimo = var_id_emprestimo;
END
//

DROP PROCEDURE SP_realizar_devolucao;