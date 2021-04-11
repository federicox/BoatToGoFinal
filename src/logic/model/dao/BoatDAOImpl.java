package logic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.bean.BoatBean;

public class BoatDAOImpl implements BoatDAO {
	/** Query for creating a new boat. */
	private static final String CREATE_QUERY = "INSERT INTO boat (description, seats, size, toilets, boatShop_id) VALUES (?, ?, ?, ?, ?) ";
	/** Query for reading all boats of a specific boat shop */
	private static final String READ_ALL_QUERY_BY_BOAT_SHOP_ID = "SELECT boat.description, boat.seats, boat.size, boat.toilets, boat.id"
			+ " FROM boat INNER JOIN boatShop ON boat.boatShop_id = boatShop.id WHERE boatShop.id = ?";
	/** Query for updating a boat. */
	private static final String UPDATE_QUERY = "UPDATE boat SET description = ?, seats = ?, size = ?, toilets = ? WHERE id = ?";

	@Override
	public List<BoatBean> getAllBoatOfABoatShop(int boatShopId) {
		List<BoatBean> boats = new ArrayList<>();
		BoatBean boat = null;
		Connection connectionGB = null;
		PreparedStatement preparedStatementGB = null;
		ResultSet resultSetGB = null;

		try {
			connectionGB = DatabaseConnection.getInstance().getConnection();
			preparedStatementGB = connectionGB.prepareStatement(READ_ALL_QUERY_BY_BOAT_SHOP_ID);
			preparedStatementGB.setInt(1, boatShopId);
			preparedStatementGB.execute();
			resultSetGB = preparedStatementGB.getResultSet();

			while (resultSetGB.next()) {
				boat = new BoatBean(resultSetGB.getString(1), resultSetGB.getInt(2), resultSetGB.getInt(3),
						resultSetGB.getInt(4), resultSetGB.getInt(5));
				boats.add(boat);
			}
		} catch (SQLException eGB) {
			eGB.printStackTrace();

		} finally {
			try {
				if (resultSetGB != null)
					resultSetGB.close();
			} catch (Exception rseGB) {
				rseGB.printStackTrace();
			}
			try {
				if (preparedStatementGB != null)
					preparedStatementGB.close();
			} catch (Exception sseGB) {
				sseGB.printStackTrace();
			}

		}

		return boats;
	}

	@Override
	public int createBoat(BoatBean boat, int boatShopId) {
		Connection connectionCB = null;
		PreparedStatement preparedStatementCB = null;
		ResultSet resultSetCB = null;
		try {
			connectionCB = DatabaseConnection.getInstance().getConnection();
			preparedStatementCB = connectionCB.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatementCB.setString(1, boat.getDescription());
			preparedStatementCB.setInt(2, boat.getSeats());
			preparedStatementCB.setInt(3, boat.getSize());
			preparedStatementCB.setInt(4, boat.getToilets());
			preparedStatementCB.setInt(5, boatShopId);
			preparedStatementCB.execute();
			resultSetCB = preparedStatementCB.getGeneratedKeys();

			if (resultSetCB.next()) {
				return resultSetCB.getInt(1);
			} else {
				return -1;
			}
		} catch (SQLException eCB) {
			eCB.printStackTrace();
		} finally {
			try {
				if (resultSetCB != null)
					resultSetCB.close();
			} catch (Exception rseCB) {
				rseCB.printStackTrace();
			}
			try {
				if (preparedStatementCB != null)
					preparedStatementCB.close();
			} catch (Exception sseCB) {
				sseCB.printStackTrace();
			}
		}

		return -1;
	}

	@Override
	public boolean updateBoat(BoatBean boat) {
		Connection connectionUB = null;
		PreparedStatement preparedStatementUB = null;
		try {
			connectionUB = DatabaseConnection.getInstance().getConnection();
			preparedStatementUB = connectionUB.prepareStatement(UPDATE_QUERY);
			preparedStatementUB.setString(1, boat.getDescription());
			preparedStatementUB.setInt(2, boat.getSeats());
			preparedStatementUB.setInt(3, boat.getSize());
			preparedStatementUB.setInt(4, boat.getToilets());
			preparedStatementUB.setInt(5, boat.getId());

			preparedStatementUB.execute();
			return true;
		} catch (SQLException eUB) {
			eUB.printStackTrace();
		} finally {
			try {
				if (preparedStatementUB != null)
					preparedStatementUB.close();
			} catch (Exception sseUB) {
				sseUB.printStackTrace();
			}
			try {
				if (connectionUB != null)
					connectionUB.close();
			} catch (Exception cseUB) {
				cseUB.printStackTrace();
			}
		}
		return false;
	}

}
