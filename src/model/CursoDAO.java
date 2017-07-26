package model;

import controller.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CursoDAO {

    private Connection connection;

    public CursoDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Curso> read() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Curso> cursos = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT * FROM curso_tb ");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Curso curso = new Curso();

                curso.setId(resultSet.getInt("id_curso"));
                curso.setNome(resultSet.getString("nome_curso"));

                cursos.add(curso);

            }
        }
        catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return cursos;

    }
}
