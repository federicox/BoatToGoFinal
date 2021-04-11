package logic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.bean.BoatShopBean;
import logic.model.BoatShop;

public class BoatShopDAOImpl implements BoatShopDAO {
	/** Query for creating a new boat shop. */
	private static final String CREATE_QUERY = "INSERT INTO boatShop (name, address, city, description, owner) VALUE (?, ?, ?, ?, ?)";
	/** Query for reading one boatShop. */
	private static final String READ_QUERY = "SELECT * FROM boatShop WHERE id = ?";
	/** Query for reading all boatShop placed in a city. */
	private static final String READ_BY_CITY_QUERY = "SELECT * FROM boatShop WHERE city = ?";
	/** Query for reading all the boatShop of an owner. */
	private static final String READ_BY_OWNER_QUERY = "SELECT * FROM boatShop WHERE owner = ?";
	/** Query for updating the fields of an boatShop. */
	private static final String UPDATE_QUERY = "UPDATE boatShop SET name = ?, address = ?, city = ?, description = ?, owner = ? WHERE id = ?";

	@Override
	public List<BoatShopBean> getAllBoatShopByCity(String city) {
		List<BoatShopBean> boatShopsGABS = new ArrayList<>();
		BoatShopBean boatShopGABS = null;
		Connection connectionGABS = null;
		PreparedStatement preparedStatementGABS = null;
		ResultSet resultSetGABS = null;

		try {
			connectionGABS = DatabaseConnection.getInstance().getConnection();
			preparedStatementGABS = connectionGABS.prepareStatement(READ_BY_CITY_QUERY);
			preparedStatementGABS.setString(1, city);
			preparedStatementGABS.execute();
			resultSetGABS = preparedStatementGABS.getResultSet();

			while (resultSetGABS.next()) {
				boatShopGABS = new BoatShopBean(resultSetGABS.getString(1), resultSetGABS.getString(2), resultSetGABS.getString(3),
						resultSetGABS.getString(4), resultSetGABS.getString(5), resultSetGABS.getInt(6));
				boatShopsGABS.add(boatShopGABS);
			}
		} catch (SQLException eGABS) {
			eGABS.printStackTrace();

		} finally {
			try {
				if (resultSetGABS != null)
					resultSetGABS.close();
			} catch (Exception rseGABS) {
				rseGABS.printStackTrace();
			}
			try {
				if (preparedStatementGABS != null)
					preparedStatementGABS.close();
			} catch (Exception sseGABS) {
				sseGABS.printStackTrace();
			}

		}

		return boatShopsGABS;
	}

	@Override
	public BoatShopBean getBoatShop(int id) {
		BoatShopBean boatShopGBS = null;
		Connection connectionGBS = null;
		PreparedStatement preparedStatementGBS = null;
		ResultSet resultSetGBS = null;

		try {
			connectionGBS = DatabaseConnection.getInstance().getConnection();
			preparedStatementGBS = connectionGBS.prepareStatement(READ_QUERY);
			preparedStatementGBS.setInt(1, id);
			preparedStatementGBS.execute();
			resultSetGBS = preparedStatementGBS.getResultSet();

			if (resultSetGBS.next()) {
				boatShopGBS = new BoatShopBean(resultSetGBS.getString(1), resultSetGBS.getString(2), resultSetGBS.getString(3),
						resultSetGBS.getString(4), resultSetGBS.getString(5), resultSetGBS.getInt(6));
			}
		} catch (SQLException eGBS) {
			eGBS.printStackTrace();

		} finally {
			try {
				if (resultSetGBS != null)
					resultSetGBS.close();
			} catch (Exception rseGBS) {
				rseGBS.printStackTrace();
			}
			try {
				if (preparedStatementGBS != null)
					preparedStatementGBS.close();
			} catch (Exception sseGBS) {
				sseGBS.printStackTrace();
			}
		}

		return boatShopGBS;
	}

	@Override
	public int createBoatShop(BoatShopBean boatShop) {
		Connection connectionCBS = null;
		PreparedStatement preparedStatementCBS = null;
		ResultSet resultSetCBS = null;
		try {
			connectionCBS = DatabaseConnection.getInstance().getConnection();
			preparedStatementCBS = connectionCBS.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatementCBS.setString(1, boatShop.getName());
			preparedStatementCBS.setString(2, boatShop.getAddress());
			preparedStatementCBS.setString(3, boatShop.getCity());
			preparedStatementCBS.setString(4, boatShop.getDescription());
			preparedStatementCBS.setString(5, boatShop.getOwner());
			preparedStatementCBS.execute();
			resultSetCBS = preparedStatementCBS.getGeneratedKeys();

			if (resultSetCBS.next()) {
				return resultSetCBS.getInt(1);
			} else {
				return -1;
			}
		} catch (SQLException eCBS) {
			eCBS.printStackTrace();
		} finally {
			try {
				if (resultSetCBS != null)
					resultSetCBS.close();
			} catch (Exception rseCBS) {
				rseCBS.printStackTrace();
			}
			try {
				if (preparedStatementCBS != null)
					preparedStatementCBS.close();
			} catch (Exception sseCBS) {
				sseCBS.printStackTrace();
			}
		}

		return -1;
	}

	@Override
	public boolean updateBoatShop(BoatShopBean boatShop) {
		Connection connectionUBS = null;
		PreparedStatement preparedStatementUBS = null;
		try {
			connectionUBS = DatabaseConnection.getInstance().getConnection();
			preparedStatementUBS = connectionUBS.prepareStatement(UPDATE_QUERY);
			preparedStatementUBS.setString(1, boatShop.getName());
			preparedStatementUBS.setString(2, boatShop.getAddress());
			preparedStatementUBS.setString(3, boatShop.getCity());
			preparedStatementUBS.setString(4, boatShop.getDescription());
			preparedStatementUBS.setString(5, boatShop.getOwner());
			preparedStatementUBS.setInt(6, boatShop.getId());

			preparedStatementUBS.execute();
			return true;
		} catch (SQLException eUBS) {
			eUBS.printStackTrace();
		} finally {
			try {
				if (preparedStatementUBS != null)
					preparedStatementUBS.close();
			} catch (Exception sseUBS) {
				sseUBS.printStackTrace();
			}
			try {
				if (connectionUBS != null)
					connectionUBS.close();
			} catch (Exception cseUBS) {
				cseUBS.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public BoatShop deleteBoatShop(BoatShop boatShop) {
		return null;
	}

	@Override
	public List<BoatShopBean> getAllBoatShopByOwner(String owner) {
		List<BoatShopBean> boatShopsGBSO = new ArrayList<>();
		BoatShopBean boatShopGBSO = null;
		Connection connectionGBSO = null;
		PreparedStatement preparedStatementGBSO = null;
		ResultSet resultSetGBSO = null;

		try {
			connectionGBSO = DatabaseConnection.getInstance().getConnection();
			preparedStatementGBSO = connectionGBSO.prepareStatement(READ_BY_OWNER_QUERY);
			preparedStatementGBSO.setString(1, owner);
			preparedStatementGBSO.execute();
			resultSetGBSO = preparedStatementGBSO.getResultSet();

			while (resultSetGBSO.next()) {
				boatShopGBSO = new BoatShopBean(resultSetGBSO.getString(1), resultSetGBSO.getString(2), resultSetGBSO.getString(3),
						resultSetGBSO.getString(4), resultSetGBSO.getString(5), resultSetGBSO.getInt(6));
				boatShopsGBSO.add(boatShopGBSO);
			}
		} catch (SQLException eGBSO) {
			eGBSO.printStackTrace();

		} finally {
			try {
				if (resultSetGBSO != null)
					resultSetGBSO.close();
			} catch (Exception rseGBSO) {
				rseGBSO.printStackTrace();
			}
			try {
				if (preparedStatementGBSO != null)
					preparedStatementGBSO.close();
			} catch (Exception sseGBSO) {
				sseGBSO.printStackTrace();
			}

		}

		return boatShopsGBSO;
	}

}
