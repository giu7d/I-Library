package model;

import controller.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {


    private Connection connection;

    public BookDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public void create(Book b) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO livro_tb " +
                                                        "(titulo_livro, autor_livro, edicao_livro, isbn_livro, qtd_livro, st_livro)" +
                                                        "VALUES(?,?,?,?,?,?)");

            statement.setString(1, b.getTitulo());
            statement.setString(2, b.getAutor());
            statement.setInt(3, b.getEdicao());
            statement.setString(4, b.getIsbn());
            statement.setInt(5, b.getQtd());
            statement.setInt(6, 1);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Book> read() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Book> books = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT * FROM livro_tb WHERE st_livro = 1  ORDER BY id_livro DESC  ");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Book book = new Book();

                book.setId(resultSet.getInt("id_livro"));
                book.setTitulo(resultSet.getString("titulo_livro"));
                book.setAutor(resultSet.getString("autor_livro"));
                book.setIsbn(resultSet.getString("isbn_livro"));
                book.setEdicao(resultSet.getInt("edicao_livro"));
                book.setQtd(resultSet.getInt("qtd_livro"));

                books.add(book);


            }
        }
        catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return books;

    }



    public List<Book> bookSearch(String field, String box) {

        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        System.out.println(box);

        switch (box){

            case "Título": sql = "SELECT * FROM livro_tb WHERE st_livro = 1 AND titulo_livro LIKE ?";
                break;
            case "Autor": sql = "SELECT * FROM livro_tb WHERE st_livro = 1 AND  autor_livro LIKE ?";
                break;
            case "ISBN": sql = "SELECT * FROM livro_tb WHERE st_livro = 1 AND  isbn_livro LIKE ?";
                break;
            case "Código": sql = "SELECT * FROM livro_tb WHERE st_livro = 1 AND  id_livro LIKE ?";
                break;
        }


        List<Book> books = new ArrayList<>();

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+field+"%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Book book = new Book();

                book.setId(resultSet.getInt("id_livro"));
                book.setTitulo(resultSet.getString("titulo_livro"));
                book.setAutor(resultSet.getString("autor_livro"));
                book.setIsbn(resultSet.getString("isbn_livro"));
                book.setEdicao(resultSet.getInt("edicao_livro"));
                book.setQtd(resultSet.getInt("qtd_livro"));

                System.out.println("Titulo: " + book.getTitulo() +  "   Isbn: " + book.getIsbn() + "  Id: " + book.getId());

                books.add(book);


            }
        }
        catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return books;

    }


    public void update(Book b) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE livro_tb " +
                                                        "SET titulo_livro = ?, autor_livro = ?, edicao_livro = ?,  " +
                                                        "isbn_livro = ?, qtd_livro = ? " +
                                                        "WHERE id_livro = ?");

            statement.setString(1, b.getTitulo());
            statement.setString(2, b.getAutor());
            statement.setInt(3, b.getEdicao());
            statement.setString(4, b.getIsbn());
            statement.setInt(5, b.getQtd());
            statement.setInt(6, b.getId());

            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        }

        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        }

        finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }
    public void delete(Book b) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement( "UPDATE livro_tb " +
                                                        "SET st_livro = 0  " +
                                                        "WHERE id_livro = ?");
            statement.setInt(1, b.getId());

            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        }
        catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

}
