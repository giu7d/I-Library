package model;

import controller.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TakeDAO {

    private Connection connection;

    public TakeDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public void create(Take t) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO emp_tb " +
                                                        "(user_emp, livro_emp, data_ret_emp, data_val_emp, st_emp)" +
                                                        "VALUES(?,?,?,?,?)");

            statement.setInt(1, t.getUser().getId());
            statement.setInt(2, t.getBook().getId());
            statement.setDate(3, (Date) t.getRetDate());
            statement.setDate(4, (Date) t.getValDate());
            statement.setInt(5, t.getSt());

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

    public List<Take> read() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Take> takes = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT * FROM emp_tb  " +
                                                        "INNER JOIN user_tb " +
                                                        "ON emp_tb.user_emp = user_tb.id_user " +
                                                        "INNER JOIN livro_tb " +
                                                        "ON emp_tb.livro_emp = livro_tb.id_livro " +
                                                        "INNER JOIN uf_tb " +
                                                        "ON user_tb.uf_user = uf_tb.sg_uf "+
                                                        "INNER JOIN curso_tb " +
                                                        "ON user_tb.curso_user = curso_tb.id_curso " +"WHERE st_rm_emp = 1 "+
                                                        "ORDER BY id_emp DESC ");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Take take = new Take();
                User user = new User();
                Book book = new Book();


                take.setId(resultSet.getInt("id_emp"));
                take.setRetDate(resultSet.getDate("data_ret_emp"));
                take.setValDate(resultSet.getDate("data_val_emp"));
                take.setDevDate(resultSet.getDate("data_dev_emp"));
                take.setSt(resultSet.getInt("st_emp"));

                //User
                Uf uf = new Uf();
                Curso curso = new Curso();

                user.setId(resultSet.getInt("id_user"));
                user.setNome(resultSet.getString("nome_user"));
                user.setRa(resultSet.getInt("ra_user"));
                user.setSt(resultSet.getInt("st_user"));
                uf.setSigla(resultSet.getString("sg_uf"));
                uf.setNome(resultSet.getString("nome_uf"));
                user.setUf(uf);
                curso.setId(resultSet.getInt("id_curso"));
                curso.setNome(resultSet.getString("nome_curso"));
                user.setCurso(curso);

                take.setUser(user);

                //Book
                book.setId(resultSet.getInt("id_livro"));
                book.setTitulo(resultSet.getString("titulo_livro"));
                book.setAutor(resultSet.getString("autor_livro"));
                book.setIsbn(resultSet.getString("isbn_livro"));
                book.setEdicao(resultSet.getInt("edicao_livro"));
                book.setQtd(resultSet.getInt("qtd_livro"));

                take.setBook(book);

                takes.add(take);

            }
        }
        catch (SQLException ex) {
            Logger.getLogger(model.BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return takes;

    }



    public List<Take> readByUser(Integer num) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Take> takes = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT * FROM emp_tb  " +
                                                        "INNER JOIN user_tb " +
                                                        "ON emp_tb.user_emp = user_tb.id_user " +
                                                        "INNER JOIN livro_tb " +
                                                        "ON emp_tb.livro_emp = livro_tb.id_livro " +
                                                        "INNER JOIN uf_tb " +
                                                        "ON user_tb.uf_user = uf_tb.sg_uf "+
                                                        "INNER JOIN curso_tb " +
                                                        "ON user_tb.curso_user = curso_tb.id_curso " +
                                                        "WHERE st_rm_emp = 1 AND user_emp LIKE ?");

            statement.setString(1, "%"+num+"%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Take take = new Take();
                User user = new User();
                Book book = new Book();


                take.setId(resultSet.getInt("id_emp"));
                take.setRetDate(resultSet.getDate("data_ret_emp"));
                take.setValDate(resultSet.getDate("data_val_emp"));
                take.setDevDate(resultSet.getDate("data_dev_emp"));
                take.setSt(resultSet.getInt("st_emp"));

                //User
                Uf uf = new Uf();
                Curso curso = new Curso();

                user.setId(resultSet.getInt("id_user"));
                user.setNome(resultSet.getString("nome_user"));
                user.setRa(resultSet.getInt("ra_user"));
                user.setSt(resultSet.getInt("st_user"));
                uf.setSigla(resultSet.getString("sg_uf"));
                uf.setNome(resultSet.getString("nome_uf"));
                user.setUf(uf);
                curso.setId(resultSet.getInt("id_curso"));
                curso.setNome(resultSet.getString("nome_curso"));
                user.setCurso(curso);

                take.setUser(user);

                //Book
                book.setId(resultSet.getInt("id_livro"));
                book.setTitulo(resultSet.getString("titulo_livro"));
                book.setAutor(resultSet.getString("autor_livro"));
                book.setIsbn(resultSet.getString("isbn_livro"));
                book.setEdicao(resultSet.getInt("edicao_livro"));
                book.setQtd(resultSet.getInt("qtd_livro"));

                take.setBook(book);

                takes.add(take);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(model.TakeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return takes;

    }


    public void validate(Take t) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE emp_tb " +
                                                        "SET data_dev_emp = ?, st_emp = 2 " +
                                                        "WHERE id_emp = ?");

            //Date now = new Date( "");

            statement.setDate(1, (Date) t.getDevDate());
            statement.setInt(2, t.getId());


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

    public void delete(Take t) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement( "UPDATE emp_tb " +
                                                        "SET st_rm_emp = 0 WHERE id_emp = ?");

            statement.setInt(1, t.getId());

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
