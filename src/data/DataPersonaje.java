package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.ApplicationException;
import business.entities.BusinessEntity.States;
import business.entities.Personaje;

public class DataPersonaje {

	public DataPersonaje() {
	}

	public List<Personaje> getAll() throws ApplicationException {
		ArrayList<Personaje> personajes = new ArrayList<>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = ConnectionFactory.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(
					"select p.id, p.nombre, p.vida, p.energia, p.defensa, p.evasion, p.puntos_iniciales + " +
					"    sum(ifnull(c.puntos,0)) as puntos_totales from personajes p " +
					"left join combates c on p.id = c.id_ganador " +
					"group by p.id, p.nombre, p.vida, p.energia, p.defensa, p.evasion, p.puntos_iniciales");
			while (rs != null && rs.next()) {
				Personaje p = new Personaje();
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setVida(rs.getInt("vida"));
				p.setEnergia(rs.getInt("energia"));
				p.setDefensa(rs.getInt("defensa"));
				p.setEvasion(rs.getInt("evasion"));
				p.setPuntosTotales(rs.getInt("puntos_totales"));
				personajes.add(p);
			}
		}
		catch (SQLException e) {
			throw new ApplicationException("Error obteniendo la lista de personajes", e);
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

		return personajes;
	}

	public Personaje getById(Personaje per) throws ApplicationException {
		Personaje p = null;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"select p.id, p.nombre, p.vida, p.energia, p.defensa, p.evasion, p.puntos_iniciales + " +
					"    sum(ifnull(c.puntos,0)) as puntos_totales from personajes p " +
					"left join combates c on p.id = c.id_ganador where p.id = ? " +
					"group by p.id, p.nombre, p.vida, p.energia, p.defensa, p.evasion, p.puntos_iniciales");
			stmt.setInt(1, per.getId());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Personaje();
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setVida(rs.getInt("vida"));
				p.setEnergia(rs.getInt("energia"));
				p.setDefensa(rs.getInt("defensa"));
				p.setEvasion(rs.getInt("evasion"));
				p.setPuntosTotales(rs.getInt("puntos_totales"));
			}
		}
		catch (SQLException e) {
			throw new ApplicationException("Error obteniendo personaje", e);
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

		return p;
	}

	public Personaje getByNombre(Personaje per) throws ApplicationException {
		Personaje p = null;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"select p.id, p.nombre, p.vida, p.energia, p.defensa, p.evasion, p.puntos_iniciales + " +
					"    sum(ifnull(c.puntos,0)) as puntos_totales from personajes p " +
					"left join combates c on p.id = c.id_ganador " +
					"group by p.id, p.nombre, p.vida, p.energia, p.defensa, p.evasion, p.puntos_iniciales " +
					"where p.nombre = ?");
			stmt.setString(1, per.getNombre());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Personaje();
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setVida(rs.getInt("vida"));
				p.setEnergia(rs.getInt("energia"));
				p.setDefensa(rs.getInt("defensa"));
				p.setEvasion(rs.getInt("evasion"));
				p.setPuntosTotales(rs.getInt("puntos_totales"));
			}
		}
		catch (SQLException e) {
			throw new ApplicationException("Error obteniendo personaje", e);
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

		return p;
	} 

	public void save(Personaje per) throws ApplicationException {
		switch (per.getState()) {
			case NEW:
				add(per);
				break;
			case MODIFIED:
				update(per);
				break;
			case DELETED:
				delete(per);
				break;
			default:
				break;
		}
		per.setState(States.UNMODIFIED);
	}

	private void add(Personaje per) throws ApplicationException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"insert into personajes (nombre,vida,energia,defensa,evasion,puntos_iniciales) " +
					"values (?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			// PreparedStatement.RETURN_GENERATED_KEYS to be able to retrieve id generated on the db
			// by the autoincrement column. Otherwise don't use it

			stmt.setString(1, per.getNombre());
			stmt.setInt(2, per.getVida());
			stmt.setInt(3, per.getEnergia());
			stmt.setInt(4, per.getDefensa());
			stmt.setInt(5, per.getEvasion());
			stmt.setInt(6, Personaje.PUNTOS_INICIALES);
			stmt.execute();

			//after executing the insert use the following lines to retrieve the id
			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				per.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new ApplicationException("Error agregando personaje", e);
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

	private void update(Personaje per) throws ApplicationException {
		PreparedStatement stmt = null;

		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"update personajes set nombre = ?, vida = ?, energia = ?," +
					"defensa = ?, evasion = ? " +
					"where id = ?");
			stmt.setString(1, per.getNombre());
			stmt.setInt(2, per.getVida());
			stmt.setInt(3, per.getEnergia());
			stmt.setInt(4, per.getDefensa());
			stmt.setInt(5, per.getEvasion());
			stmt.setInt(6, per.getId());
			stmt.execute();
		} catch (SQLException e) {
			throw new ApplicationException("Error actualizando personaje", e);
		}
		finally {
			try {
				if (stmt != null) stmt.close();
				ConnectionFactory.getInstance().releaseConn();
			} catch (SQLException e) {
				throw new ApplicationException("Error cerrando la conexión", e);
			}
		}
	}

	private void delete(Personaje per) throws ApplicationException {
		PreparedStatement stmt = null;

		try {
			stmt = ConnectionFactory.getInstance().getConn().prepareStatement(
					"delete from personajes where id = ?");
			stmt.setInt(1, per.getId());
			int rowCount = stmt.executeUpdate();
			if (rowCount == 0) {
				throw new ApplicationException("no existe el personaje");
			}
		} catch (SQLException e) {
			throw new ApplicationException("Error eliminando personaje", e);
		}
		finally {
			try {
				if (stmt != null) stmt.close();
				ConnectionFactory.getInstance().releaseConn();
			} catch (SQLException e) {
				//TODO Auto-generated catch block
				throw new ApplicationException("Error cerrando la conexión", e);
			}
		}
	}
}
