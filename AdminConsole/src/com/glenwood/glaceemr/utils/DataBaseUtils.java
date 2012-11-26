/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author gsivashanmugam
 */
public class DataBaseUtils {

	private DataSource dataSource;
	private Connection connection;
	private Statement statement;

	public DataBaseUtils(String connectionString) throws NamingException,
			Exception {
		this.dataSource = createConnection(connectionString);
		this.connection = this.dataSource.getConnection();
		this.statement = this.connection.createStatement();
	}

	private DataSource createConnection(String connectionString)
			throws NamingException, Exception {
		Context initCtx = new InitialContext();
		return (DataSource) initCtx.lookup(connectionString);
	}

	public Statement getStatement() throws SQLException, Exception {
		return this.statement;
	}

	public void destroy() throws Exception {
		this.statement.close();
		this.connection.close();
		this.dataSource = null;
	}
}
