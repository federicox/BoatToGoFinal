package logic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.model.Person;

public class PersonDAOImpl implements PersonDAO {

	private static final String CREATE_QUERY = "INSERT INTO person (fiscal_code, first_name, last_name, booking_id) VALUES (?, ?, ?, ?)";
	/** Query for reading all people contained in a booking. */
	private static final String READ_ALL_PEOPLE_BY_BOOKING_ID_QUERY = "SELECT p.fiscal_code, p.first_name, p.last_name, p.id "
			+ "FROM person p INNER JOIN booking b ON p.booking_id = b.id" + " WHERE b.id = ?";

	@Override
	public List<Person> getAllPeopleOfABooking(int bookingId) {
		List<Person> peoplePB = new ArrayList<>();
		Person personPB = null;
		Connection connectionPB = null;
		PreparedStatement preparedStatementPB = null;
		ResultSet resultSetPB = null;

		try {
			connectionPB = DatabaseConnection.getInstance().getConnection();
			preparedStatementPB = connectionPB.prepareStatement(READ_ALL_PEOPLE_BY_BOOKING_ID_QUERY);
			preparedStatementPB.setInt(1, bookingId);
			preparedStatementPB.execute();
			resultSetPB = preparedStatementPB.getResultSet();

			while (resultSetPB.next()) {
				personPB = new Person(resultSetPB.getString(1), resultSetPB.getString(2), resultSetPB.getString(3),
						resultSetPB.getInt(4));
				peoplePB.add(personPB);
			}
		} catch (SQLException ePB) {
			ePB.printStackTrace();

		} finally {
			try {
				if (resultSetPB != null)
					resultSetPB.close();
			} catch (Exception rsePB) {
				rsePB.printStackTrace();
			}
			try {
				if (preparedStatementPB != null)
					preparedStatementPB.close();
			} catch (Exception ssePB) {
				ssePB.printStackTrace();
			}

		}

		return peoplePB;
	}

	@Override
	public int createPerson(Person person, int idBooking) {
		Connection connectionCP = null;
		PreparedStatement preparedStatementCP = null;
		ResultSet resultSetCP = null;
		try {
			connectionCP = DatabaseConnection.getInstance().getConnection();
			preparedStatementCP = connectionCP.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatementCP.setString(1, person.getFiscalCode());
			preparedStatementCP.setString(2, person.getName());
			preparedStatementCP.setString(3, person.getLastname());
			preparedStatementCP.setInt(4, idBooking);
			preparedStatementCP.execute();
			resultSetCP = preparedStatementCP.getGeneratedKeys();

			if (resultSetCP.next()) {
				return resultSetCP.getInt(1);
			} else {
				return -1;
			}
		} catch (SQLException eCP) {
			eCP.printStackTrace();
		} finally {
			try {
				if (resultSetCP != null)
					resultSetCP.close();
			} catch (Exception rseCP) {
				rseCP.printStackTrace();
			}
			try {
				if (preparedStatementCP != null)
					preparedStatementCP.close();
			} catch (Exception sseCP) {
				sseCP.printStackTrace();
			}

		}

		return -1;
	}

}
