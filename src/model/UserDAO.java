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

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public void create(User u) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO user_tb " +
                                                        "(nome_user, ra_user, curso_user, uf_user, st_user)" +
                                                        "VALUES(?,?,?,?,?)");


            statement.setString(1, u.getNome());
            statement.setInt(2, u.getRa());
            statement.setInt(3, u.getCurso().getId());
            statement.setString(4, u.getUf().getSigla());
            statement.setInt(5,1);

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

    public List<User> read() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<User> users = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT * FROM user_tb " +
                                                        "INNER JOIN curso_tb " +
                                                        "ON user_tb.curso_user = curso_tb.id_curso " +
                                                        "INNER JOIN uf_tb " +
                                                        "ON user_tb.uf_user = uf_tb.sg_uf " +
                                                        "WHERE st_user <> 0 AND adm_user = 0 ORDER BY id_user DESC");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                User user = new User();
                Uf uf = new Uf();
                Curso curso = new Curso();


                user.setId(resultSet.getInt("id_user"));
                user.setNome(resultSet.getString("nome_user"));
                user.setRa(resultSet.getInt("ra_user"));
                user.setSt(resultSet.getInt("st_user"));
                user.setAdm(resultSet.getInt("adm_user"));

                uf.setSigla(resultSet.getString("sg_uf"));
                uf.setNome(resultSet.getString("nome_uf"));

                user.setUf(uf);

                curso.setId(resultSet.getInt("id_curso"));
                curso.setNome(resultSet.getString("nome_curso"));

                user.setCurso(curso);

                users.add(user);

            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return users;

    }

    public User readSearch(Integer ra, String pass) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        User user = new User();

        try {
            statement = connection.prepareStatement("SELECT * FROM user_tb " +
                                                        "INNER JOIN curso_tb " +
                                                        "ON user_tb.curso_user = curso_tb.id_curso " +
                                                        "INNER JOIN uf_tb " +
                                                        "ON user_tb.uf_user = uf_tb.sg_uf " +
                                                        "WHERE ra_user LIKE ? AND passwd_user LIKE ? AND st_user = 2");

            statement.setString(1, "%"+ra+"%");
            statement.setString(2, "%"+pass+"%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Uf uf = new Uf();
                Curso curso = new Curso();


                user.setId(resultSet.getInt("id_user"));
                user.setNome(resultSet.getString("nome_user"));
                user.setEmail(resultSet.getString("email_user"));
                user.setRa(resultSet.getInt("ra_user"));
                user.setSt(resultSet.getInt("st_user"));
                user.setAdm(resultSet.getInt("adm_user"));

                uf.setSigla(resultSet.getString("sg_uf"));
                uf.setNome(resultSet.getString("nome_uf"));

                user.setUf(uf);

                curso.setId(resultSet.getInt("id_curso"));
                curso.setNome(resultSet.getString("nome_curso"));

                user.setCurso(curso);


            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return user;

    }


    public void update(User u) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE user_tb " +
                                                        "SET nome_user = ?, ra_user = ?, curso_user = ?, uf_user = ? " +
                                                        "WHERE id_user = ?");


            statement.setString(1, u.getNome());
            statement.setInt(2, u.getRa());
            statement.setInt(3, u.getCurso().getId());
            statement.setString(4, u.getUf().getSigla());
            statement.setInt(5, u.getId());

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

    public void delete(User u) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement( "UPDATE user_tb " +
                                                        "SET st_user = 0  " +
                                                        "WHERE id_user = ? AND adm_user = 0 ");

            statement.setInt(1, u.getId());

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

    public void updatePass(User u) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE user_tb " +
                    "SET email_user = ?, passwd_user = ?, st_user = ? " +
                    "WHERE ra_user = ? AND st_user = 1");


            statement.setString(1, u.getEmail());
            statement.setString(2, u.getPasswd());
            statement.setInt(3, 2);
            statement.setInt(4, u.getRa());

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

    public boolean login(Integer ra, String pass) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        boolean check = false;

        try {

            statement = connection.prepareStatement("SELECT * FROM user_tb WHERE ra_user = ? AND passwd_user = ? AND st_user = 2");
            statement.setInt(1, ra);
            statement.setString(2, pass);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                check = true;


            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return check;

    }

}