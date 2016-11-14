package data;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import util.ApplicationException;

public class ConnectionFactory {

	private String dbDriver="com.mysql.jdbc.Driver";
	private String host;
	private String port="3306";
	private String user;
	private String pass;
	private String db="turn_based_combat_web";
	private String dbType="mysql";

	private Connection conn;
	private int cantConn = 0;

	private ConnectionFactory() throws ApplicationException {
		try {
			Class.forName(dbDriver);

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("db.properties");
			Properties props = new Properties();
			props.load(input);

			host = props.getProperty("host");
			user = props.getProperty("user");
			pass = props.getProperty("pass");
		} catch (ClassNotFoundException e) {
			throw new ApplicationException("Error del Driver JDBC",e);
		} catch (Exception e) {
			throw new ApplicationException("Error recuperando configuración de la DB");
		}
	}

	private static ConnectionFactory instance;
	
	public static ConnectionFactory getInstance() throws ApplicationException{
		if (instance==null){
			instance = new ConnectionFactory();
		}
		return instance;
	}

	public Connection getConn() throws ApplicationException {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(
							"jdbc:"+dbType+"://"+host+":"+port+"/"+db+"?useSSL=false",user,pass);
				cantConn++;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Error al conectarse a la DB", e);
		}
		return conn;
	}

	public void releaseConn() throws ApplicationException {
		try {
			cantConn--;
			if (cantConn == 0) {
				conn.close();
			}
		}
		catch (SQLException e) {
			throw new ApplicationException("Error al cerrar la conexi�n", e);
		}
	}
}
