CREATE TABLE IF NOT EXISTS Livro (
    livro_id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    autores VARCHAR(255),
    editora VARCHAR(255),
    preco FLOAT
);
CREATE TABLE IF NOT EXISTS Impresso(
    livro_id INT PRIMARY KEY,
    frete FLOAT,
    estoque INT,
    FOREIGN KEY (livro_id) REFERENCES Livro(livro_id)
);

CREATE TABLE IF NOT EXISTS Eletronico(
    livro_id INT PRIMARY KEY,
    tamanho INT,
    FOREIGN KEY (livro_id) REFERENCES Livro(livro_id)
);

CREATE TABLE IF NOT EXISTS Venda (
    venda_id INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL,
    valor FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS Livro_Venda(
    livro_id INT NOT NULL,
    venda_id INT NOT NULL,
    PRIMARY KEY (livro_id, venda_id),
    FOREIGN KEY (livro_id) REFERENCES Livro(livro_id),
    FOREIGN KEY (venda_id) REFERENCES Venda(venda_id)
);