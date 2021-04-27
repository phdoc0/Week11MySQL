package dao;

import entity.Liquor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import deo.DBConnection;


public class LiquorDao {
    private Connection connection;
    private final String GET_ALL_LIQUOR_QUERY = "SELECT * FROM liquor";
    private final String GET_LIQUOR_BY_ID_QUERY = "SELECT * FROM liquor WHERE id = ?";
    private final String CREATE_NEW_LIQUOR_QUERY = "INSERT INTO liquor (name, quantity, price) VALUES (?, ?, ?)";
    private final String UPDATE_LIQUOR_QUERY = "UPDATE liquor SET name = ?, quantity = ?, price = ? WHERE id = ?";
    private final String DELETE_LIQUOR_QUERY = "DELETE FROM liquor WHERE id = ?";

    public LiquorDao() {
        connection = DBConnection.getConnection();
    }

    public List<Liquor> getAllLiquor() throws SQLException {
        ResultSet resultSet = connection.prepareStatement(GET_ALL_LIQUOR_QUERY).executeQuery();
        List<Liquor> liquorList = new ArrayList<Liquor>();

        while (resultSet.next()) {
            liquorList.add(generateLiquorItem(resultSet));
        }

        return liquorList;
    }

    public Liquor getLiquorById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LIQUOR_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return generateLiquorItem(resultSet);
        }

        return new Liquor(0, "", "", 0.0);
    }

    public void createNewLiquor(String name, String quantity, Double price) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_LIQUOR_QUERY);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, quantity);
        preparedStatement.setDouble(3, price);
        preparedStatement.executeUpdate();
    }

    public void updateLiquor(Liquor liquorItem) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LIQUOR_QUERY);

        preparedStatement.setString(1, liquorItem.getName());
        preparedStatement.setString(2, liquorItem.getQuantity());
        preparedStatement.setDouble(3, liquorItem.getPrice());
        preparedStatement.setInt(4, liquorItem.getId());

        preparedStatement.executeUpdate();
    }

    public void deleteLiquor(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LIQUOR_QUERY);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }

    private Liquor generateLiquorItem(ResultSet resultSet) throws SQLException {

        return new Liquor(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDouble(4)
        );
    }


}
