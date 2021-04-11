package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.BookingBean;
import logic.model.bookingstate.StateEnum;

public class BookingDAOImpl implements BookingDAO {

	private static final Logger LOGGER = Logger.getLogger( BookingDAOImpl.class.getName() );
	
	/** Query for creating a new booking. */
	private static final String CREATE_QUERY = "INSERT INTO booking (check_in, check_out, state, user,  boat_id) VALUES (?, ?, ?, ?, ?)";
	/** Query for reading the booking. */
	private static final String READ_QUERY = "SELECT check_in, check_out, state, user, id FROM booking WHERE id = ?";
	/** Query for reading all booking of a specific boat. */
	private static final String READ_ALL_BY_BOAT_ID_QUERY = "SELECT h.name, " + "b.check_in, " + "b.check_out, "
			+ "b.state, " + "b.user, " + "b.id " + "FROM boatShop h " + "INNER JOIN boat r " + "ON h.id = r.boatShop_id "
			+ "INNER JOIN booking b " + "ON r.id = b.boat_id " + "WHERE b.boat_id = ?";
	/** Query for reading all booking of an user. */
	private static final String READ_ALL_BY_USER_QUERY = "SELECT h.name, " + "b.check_in, " + "b.check_out, "
			+ "b.state, " + "b.user, " + "b.id " + "FROM boatShop h " + "INNER JOIN boat r " + "ON h.id = r.boatShop_id "
			+ "INNER JOIN booking b " + "ON r.id = b.boat_id " + "WHERE b.user = ?";
	/** Query for updating the booking. */
	private static final String UPDATE_QUERY = "UPDATE booking SET state = ? WHERE id = ?";

	@Override
	public List<BookingBean> getAllBookingOfABoat(int boatId) {
		List<BookingBean> bookingsBKB = new ArrayList<>();
		BookingBean bookingBKB = null;
		Connection connectionBKB = null;
		PreparedStatement preparedStatementBKB = null;
		ResultSet resultSetBKB = null;

		try {
			connectionBKB = DatabaseConnection.getInstance().getConnection();
			preparedStatementBKB = connectionBKB.prepareStatement(READ_ALL_BY_BOAT_ID_QUERY);
			preparedStatementBKB.setInt(1, boatId);
			preparedStatementBKB.execute();
			resultSetBKB = preparedStatementBKB.getResultSet();

			while (resultSetBKB.next()) {
				bookingBKB = new BookingBean(resultSetBKB.getString(1), resultSetBKB.getDate(2).toLocalDate(),
						resultSetBKB.getDate(3).toLocalDate(), StateEnum.valueOf(resultSetBKB.getString(4)),
						resultSetBKB.getString(5), resultSetBKB.getInt(6));
				bookingsBKB.add(bookingBKB);
			}
		} catch (SQLException eBKB) {
			LOGGER.log( Level.SEVERE, eBKB.toString(), eBKB );

		} finally {
			try {
				if (resultSetBKB != null)
					resultSetBKB.close();
			} catch (Exception rseBKB) {
				LOGGER.log( Level.SEVERE, rseBKB.toString(), rseBKB );
			}
			try {
				if (preparedStatementBKB != null)
					preparedStatementBKB.close();
			} catch (Exception sseBKB) {
				LOGGER.log( Level.SEVERE, sseBKB.toString(), sseBKB );
			}

		}

		return bookingsBKB;
	}

	@Override
	public List<BookingBean> getAllBookingOfAUser(String username) {
		List<BookingBean> bookingsBKU = new ArrayList<>();
		BookingBean bookingBKU = null;
		Connection connectionBKU = null;
		PreparedStatement preparedStatementBKU = null;
		ResultSet resultSetBKU = null;

		try {
			connectionBKU = DatabaseConnection.getInstance().getConnection();
			preparedStatementBKU = connectionBKU.prepareStatement(READ_ALL_BY_USER_QUERY);
			preparedStatementBKU.setString(1, username);
			preparedStatementBKU.execute();
			resultSetBKU = preparedStatementBKU.getResultSet();

			while (resultSetBKU.next()) {
				bookingBKU = new BookingBean(resultSetBKU.getString(1), resultSetBKU.getDate(2).toLocalDate(),
						resultSetBKU.getDate(3).toLocalDate(), StateEnum.valueOf(resultSetBKU.getString(4)),
						resultSetBKU.getString(5), resultSetBKU.getInt(6));
				bookingsBKU.add(bookingBKU);
			}
		} catch (SQLException eBKU) {
			LOGGER.log( Level.SEVERE, eBKU.toString(), eBKU );

		} finally {
			try {
				if (resultSetBKU != null)
					resultSetBKU.close();
			} catch (Exception rseBKU) {
				LOGGER.log( Level.SEVERE, rseBKU.toString(), rseBKU );
			}
			try {
				if (preparedStatementBKU != null)
					preparedStatementBKU.close();
			} catch (Exception sseBKU) {
				LOGGER.log( Level.SEVERE, sseBKU.toString(), sseBKU );
			}

		}

		return bookingsBKU;
	}

	@Override
	public int createBooking(BookingBean booking, int boatId) {
		Connection connectionCBK = null;
		PreparedStatement preparedStatementCBK = null;
		ResultSet resultSetCBK = null;
		try {
			connectionCBK = DatabaseConnection.getInstance().getConnection();
			preparedStatementCBK = connectionCBK.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatementCBK.setDate(1, Date.valueOf(booking.getCheckIn()));
			preparedStatementCBK.setDate(2, Date.valueOf(booking.getCheckOut()));
			preparedStatementCBK.setString(3, String.valueOf(booking.getState()));
			preparedStatementCBK.setString(4, booking.getUser());
			preparedStatementCBK.setInt(5, boatId);
			preparedStatementCBK.execute();
			resultSetCBK = preparedStatementCBK.getGeneratedKeys();

			if (resultSetCBK.next()) {
				return resultSetCBK.getInt(1);
			} else {
				return -1;
			}
		} catch (SQLException eCBK) {
			LOGGER.log( Level.SEVERE, eCBK.toString(), eCBK );
		} finally {
			try {
				if (resultSetCBK != null)
					resultSetCBK.close();
			} catch (Exception rseCBK) {
				LOGGER.log( Level.SEVERE, rseCBK.toString(), rseCBK );
			}
			try {
				if (preparedStatementCBK != null)
					preparedStatementCBK.close();
			} catch (Exception sseCBK) {
				LOGGER.log( Level.SEVERE, sseCBK.toString(), sseCBK );
			}

		}

		return -1;
	}

	@Override
	public boolean updateBooking(BookingBean booking) {
		Connection connectionUBK = null;
		PreparedStatement preparedStatementUBK = null;
		try {
			connectionUBK = DatabaseConnection.getInstance().getConnection();
			preparedStatementUBK = connectionUBK.prepareStatement(UPDATE_QUERY);
			preparedStatementUBK.setString(1, String.valueOf(booking.getState()));
			preparedStatementUBK.setInt(2, booking.getBookingId());

			preparedStatementUBK.execute();
			return true;
		} catch (SQLException eUBK) {
			LOGGER.log( Level.SEVERE, eUBK.toString(), eUBK );
		} finally {
			try {
				if (preparedStatementUBK != null)
					preparedStatementUBK.close();
			} catch (Exception sseUBK) {
				LOGGER.log( Level.SEVERE, sseUBK.toString(), sseUBK );
			}
		}
		return false;
	}

	@Override
	public BookingBean getBooking(int id) {
		BookingBean bookingGBK = null;
		Connection connectionGBK = null;
		PreparedStatement preparedStatementGBK = null;
		ResultSet resultSetGBK = null;

		try {
			connectionGBK = DatabaseConnection.getInstance().getConnection();
			preparedStatementGBK = connectionGBK.prepareStatement(READ_QUERY);
			preparedStatementGBK.setInt(1, id);
			preparedStatementGBK.execute();
			resultSetGBK = preparedStatementGBK.getResultSet();

			if (resultSetGBK.next()) {
				bookingGBK = new BookingBean();
				bookingGBK.setCheckIn(resultSetGBK.getDate(1).toLocalDate());
				bookingGBK.setCheckOut(resultSetGBK.getDate(2).toLocalDate());
				bookingGBK.setState(StateEnum.valueOf(resultSetGBK.getString(3)));
				bookingGBK.setUser(resultSetGBK.getString(4));
				bookingGBK.setBookingId(resultSetGBK.getInt(5));
			}
		} catch (SQLException eGBK) {
			LOGGER.log( Level.SEVERE, eGBK.toString(), eGBK );

		} finally {
			try {
				if (resultSetGBK != null)
					resultSetGBK.close();
			} catch (Exception rseGBK) {
				LOGGER.log( Level.SEVERE, rseGBK.toString(), rseGBK );
			}
			try {
				if (preparedStatementGBK != null)
					preparedStatementGBK.close();
			} catch (Exception sseGBK) {
				LOGGER.log( Level.SEVERE, sseGBK.toString(), sseGBK );
			}
		}

		return bookingGBK;
	}

}
