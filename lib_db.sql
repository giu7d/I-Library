-- phpMyAdmin SQL Dump
-- version 4.4.15.9
-- https://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 05-Jul-2017 às 17:50
-- Versão do servidor: 5.7.18-0ubuntu0.16.04.1
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lib_db`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `curso_tb`
--

CREATE TABLE IF NOT EXISTS `curso_tb` (
  `id_curso` int(3) NOT NULL,
  `nome_curso` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `curso_tb`
--

INSERT INTO `curso_tb` (`id_curso`, `nome_curso`) VALUES
(1, 'Ciência da Computação'),
(2, 'Eng. Ambiental'),
(3, 'Eng. Elétrica'),
(4, 'Eng. de Produção'),
(5, 'Eng. de Alimentos');

-- --------------------------------------------------------

--
-- Estrutura da tabela `emp_tb`
--

CREATE TABLE IF NOT EXISTS `emp_tb` (
  `id_emp` int(11) NOT NULL,
  `data_ret_emp` date NOT NULL,
  `data_dev_emp` date DEFAULT NULL,
  `data_val_emp` date NOT NULL,
  `user_emp` int(5) NOT NULL,
  `livro_emp` int(5) NOT NULL,
  `st_emp` int(1) NOT NULL,
  `st_rm_emp` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `emp_tb`
--

INSERT INTO `emp_tb` (`id_emp`, `data_ret_emp`, `data_dev_emp`, `data_val_emp`, `user_emp`, `livro_emp`, `st_emp`, `st_rm_emp`) VALUES
(11, '2017-06-28', '2017-06-28', '2017-07-05', 16, 2, 2, 1),
(12, '2017-06-28', '2017-06-28', '2017-06-26', 16, 3, 2, 1),
(13, '2017-06-30', '2017-06-30', '2017-07-01', 16, 3, 2, 1),
(14, '2017-06-30', '2017-06-30', '2017-06-21', 16, 5, 2, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `livro_tb`
--

CREATE TABLE IF NOT EXISTS `livro_tb` (
  `id_livro` int(5) NOT NULL,
  `titulo_livro` varchar(255) NOT NULL,
  `autor_livro` varchar(255) NOT NULL,
  `edicao_livro` int(4) NOT NULL,
  `isbn_livro` varchar(17) NOT NULL,
  `qtd_livro` int(4) NOT NULL,
  `st_livro` int(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `livro_tb`
--

INSERT INTO `livro_tb` (`id_livro`, `titulo_livro`, `autor_livro`, `edicao_livro`, `isbn_livro`, `qtd_livro`, `st_livro`) VALUES
(2, 'JavaScript & JQuery', 'Jon Duckett', 1, '978-85-7608-945-2', 5, 1),
(3, 'Java Threads', 'Scott Oaks', 3, '978-0-596-00782-9', 3, 1),
(4, 'Elementos de Eletrônica Digital', 'Ivan Valeije', 35, '85-7197-019-2', 10, 1),
(5, 'C# 6.0. Com Visual Studio. Curso Completo', 'Henrique Loureiro', 1, '978-9727228058', 7, 1),
(6, 'Python', 'David Beazley', 5, '978-85-7522-332-1', 2, 1),
(7, 'Android 6 - Para Iniciantes', 'Deitel', 1, '123-2342-234-5', 0, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `uf_tb`
--

CREATE TABLE IF NOT EXISTS `uf_tb` (
  `sg_uf` varchar(2) NOT NULL,
  `nome_uf` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `uf_tb`
--

INSERT INTO `uf_tb` (`sg_uf`, `nome_uf`) VALUES
('AC', 'Acre'),
('AL', 'Alagoas'),
('Ap', 'Amapá'),
('BH', 'Bahia'),
('CE', 'Ceará'),
('DF', 'Distrito Federal'),
('ES', 'Espírito Santo'),
('GO', 'Goias'),
('MA', 'Maranhão'),
('MG', 'Minas Gerais'),
('MS', 'Mato Grosso do Sul'),
('MT', 'Mato Grosso'),
('PR', 'Paraná'),
('RJ', 'Rio de Janeiro'),
('RS', 'Rio Grande do Sul'),
('SP', 'São Paulo');

-- --------------------------------------------------------

--
-- Estrutura da tabela `user_tb`
--

CREATE TABLE IF NOT EXISTS `user_tb` (
  `id_user` int(7) NOT NULL,
  `ra_user` int(7) NOT NULL,
  `nome_user` varchar(60) NOT NULL,
  `email_user` varchar(60) DEFAULT NULL,
  `passwd_user` varchar(35) DEFAULT NULL,
  `st_user` int(1) NOT NULL,
  `adm_user` int(1) NOT NULL DEFAULT '0',
  `curso_user` int(3) NOT NULL,
  `uf_user` varchar(2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `user_tb`
--

INSERT INTO `user_tb` (`id_user`, `ra_user`, `nome_user`, `email_user`, `passwd_user`, `st_user`, `adm_user`, `curso_user`, `uf_user`) VALUES
(15, 0, 'Administrador', 'root@root.com', 'ivAM?gsKo*H', 2, 1, 1, 'PR'),
(16, 1868233, 'Giuseppe Setem', 'giuseppe@email.com', 'i?2*@~W{&tO?', 2, 0, 1, 'SP'),
(17, 1435930, 'Lucas Felipe', NULL, NULL, 1, 0, 1, 'PR'),
(18, 1234567, 'Jonas', 'ex@ex.com.br', 'KnE#b''^Z?\0%M?|', 2, 0, 2, 'GO');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `curso_tb`
--
ALTER TABLE `curso_tb`
  ADD PRIMARY KEY (`id_curso`);

--
-- Indexes for table `emp_tb`
--
ALTER TABLE `emp_tb`
  ADD PRIMARY KEY (`id_emp`),
  ADD KEY `user_emp` (`user_emp`),
  ADD KEY `livro_emp` (`livro_emp`);

--
-- Indexes for table `livro_tb`
--
ALTER TABLE `livro_tb`
  ADD PRIMARY KEY (`id_livro`);

--
-- Indexes for table `uf_tb`
--
ALTER TABLE `uf_tb`
  ADD PRIMARY KEY (`sg_uf`);

--
-- Indexes for table `user_tb`
--
ALTER TABLE `user_tb`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `id_user` (`id_user`),
  ADD UNIQUE KEY `ra_user` (`ra_user`),
  ADD KEY `curso_user` (`curso_user`),
  ADD KEY `uf_user` (`uf_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `curso_tb`
--
ALTER TABLE `curso_tb`
  MODIFY `id_curso` int(3) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `emp_tb`
--
ALTER TABLE `emp_tb`
  MODIFY `id_emp` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `livro_tb`
--
ALTER TABLE `livro_tb`
  MODIFY `id_livro` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `user_tb`
--
ALTER TABLE `user_tb`
  MODIFY `id_user` int(7) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `emp_tb`
--
ALTER TABLE `emp_tb`
  ADD CONSTRAINT `livro_emp_fk_id_livro` FOREIGN KEY (`livro_emp`) REFERENCES `livro_tb` (`id_livro`),
  ADD CONSTRAINT `user_emp_fk_id_user` FOREIGN KEY (`user_emp`) REFERENCES `user_tb` (`id_user`);

--
-- Limitadores para a tabela `user_tb`
--
ALTER TABLE `user_tb`
  ADD CONSTRAINT `user_tb_ibfk_1` FOREIGN KEY (`curso_user`) REFERENCES `curso_tb` (`id_curso`),
  ADD CONSTRAINT `user_tb_ibfk_2` FOREIGN KEY (`uf_user`) REFERENCES `uf_tb` (`sg_uf`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
