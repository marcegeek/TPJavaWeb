package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import business.entities.Combate;
import business.entities.BusinessEntity.States;
import util.ApplicationException;

public class DataCombate {

	public void save(Combate com) throws ApplicationException {
		switch(com.getState()) {
			case NEW:
				add(com);
			default:
				break;
		}
		com.setState(States.UNMODIFIED);
	} 

	private void add(Combate com) throws ApplicationException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"insert into combates (id_ganador,id_perdedor,puntos) " +
					"values (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			// PreparedStatement.RETURN_GENERATED_KEYS to be able to retrieve id generated on the db
			// by the autoincrement column. Otherwise don't use it

			stmt.setInt(1, com.getGanador().getId());
			stmt.setInt(2, com.getPerdedor().getId());
			stmt.setInt(3, com.getPuntos());
			stmt.execute();

			//after executing the insert use the following lines to retrieve the id
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				com.setId(rs.getInt(1));
			}
			//la fecha y hora es seteada automáticamente por la DB
			com.setFechaHora(getFechaHoraById(com));
		} catch (SQLException e) {
			throw new ApplicationException("Error agregando combate", e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				ConnectionFactory.getInstance().releaseConn();
			} catch (SQLException e) {
				throw new ApplicationException("Error cerrando la conexión", e);
			}
		}
	}

	private java.util.Date getFechaHoraById(Combate com) throws ApplicationException {
		java.util.Date fechaHora = null;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"select fecha_hora from combates where id = ?");
			stmt.setInt(1, com.getId());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				Timestamp fechaHoraSQL = rs.getTimestamp(1);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(fechaHoraSQL.getTime());
				fechaHora = cal.getTime();
			}
		}
		catch (SQLException e) {
			throw new ApplicationException("Error obteniendo fecha del combate", e);
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				ConnectionFactory.getInstance().releaseConn();
			} catch (SQLException e) {
				throw new ApplicationException("Error cerrando la conexión", e);
			}
		}

		return fechaHora;
	}
}
