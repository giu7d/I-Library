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

public class UfDAO {

    private Connection connection;

    public UfDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Uf> read() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Uf> ufs = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT * FROM uf_tb ");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Uf uf = new Uf();

                uf.setSigla(resultSet.getString("sg_uf"));
                uf.setNome(resultSet.getString("nome_uf"));

                ufs.add(uf);

            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UfDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return ufs;

    }

}
