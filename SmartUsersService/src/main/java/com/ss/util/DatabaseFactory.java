/**SmartSoftware User - Service */
/**
 * Description: Utility class for random verification code generation
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibatis.common.jdbc.ScriptRunner;

@Component
public class DatabaseFactory {
	
	private static final Logger LOGGER = Logger.getLogger(DatabaseFactory.class);
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${script.path}")
	private String scriptPath;
	

	@Value("${dbpath.dbcreation}")
	private String dbConnection;
	
	/*@Value("${dbpath.dbscriptcreate1}")
	private String dbScriptCreate1;*/
		
	/*@Value("${dbpath.dbscriptcreate2}")
	private String dbScriptCreate2;*/
	
	@Value("${dbpath.mysqlpath}")
	private String mysqlpath;
	
	private String jdbcDriver = "com.mysql.jdbc.Driver";

	public boolean createTenantDatabase(String tenant) {
		Connection conn = null;
		Statement s = null;
		String databaseName = tenant.replace("-", "");
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbConnection);
			s = conn.createStatement();
			s.executeUpdate("CREATE DATABASE " + databaseName);
			return createTenantDatabaseTable(databaseName,username,password,scriptPath);
			
		/*	conn = DriverManager.getConnection(dbScriptCreate1 + tenant.replace("-", "") + dbScriptCreate2);
			//ScriptRunner sr = new ScriptRunner(conn, false, false);
			// Give the input file to Reader
			
			//Reader reader = new InputStreamReader(new FileInputStream(scriptPath), "utf-8");
		 
			// Exctute script
			//sr.runScript(reader);
			//return true;
*/		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		finally{
			 //finally block used to close resources
			try {
				if (s != null)
					s.close();
				if (conn != null)
					conn.close();
			}catch(SQLException se){
		    	  LOGGER.error(se.getMessage());
		      }// do nothing
		}
	}
	 
	public boolean createTenantDatabaseTable(String dbName, String dbUserName, String dbPassword, String source) {
       
        LOGGER.info(source+"=========source======"+dbName.toLowerCase()+"=======mysqlPath====="+mysqlpath);
//        LOGGER.info(source+"=========username======"+dbUserName+"=======passwordd====="+dbPassword);
        String[] executeCmd = new String[]{mysqlpath, "--user=" + dbUserName, "--password=" + dbPassword, dbName,"-e", "source "+source.trim()};
//      String[]executeCmd = new String[]{mysqlpath, "-u " + dbUserName, "-p" + dbPassword, dbName," ", "<"+source.trim()};
		Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                return true;
            }  
        } catch (Exception ex) {
            LOGGER.info(Arrays.toString(ex.getStackTrace()));
        }
        return false;
    }
	public boolean createTenantDatabase(String tenant, String databaseIP, String port, String username2,
			String password2) {
		final String param1 = "jdbc:mysql://";
		final String param2 = "/?user=";
		final String param3 = "&password=";
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		try(Connection conn = DriverManager.getConnection(param1 + databaseIP + ":" + port + param2 + username2 + param3 + password2);
			Statement s = conn.createStatement();) {
			s.executeUpdate("CREATE DATABASE " + tenant.replace("-", ""));
			 return true;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return false;
		}  
	}
	 
	public boolean createTenantDatabaseTable(String tenant, String databaseIP, String port, String username2,
			String password2) {

		Reader reader = null;
		final String param1 = "jdbc:mysql://";
		final String param2 = "/?user=";
		final String param3 = "&password=";
		final String param4 = "?user=";
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		try (Connection conn = DriverManager.getConnection(param1 + databaseIP + ":" + port + "/"
				+ tenant.replace("-", "") + param4 + username2 + param3 + password2);) {
			ScriptRunner sr = new ScriptRunner(conn, false, false);
			try (FileReader fr = new FileReader(scriptPath)) {
				reader = new BufferedReader(fr);
			}
			sr.runScript(reader);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			try (Connection conn = DriverManager
					.getConnection(param1 + databaseIP + ":" + port + param2 + username2 + param3 + password2);
					Statement s = conn.createStatement();) {
				String sql = "DROP " + tenant.replace("-", "");
				s.executeUpdate(sql);
			} catch (SQLException e1) {
				LOGGER.error(e1.getMessage());
			}
			return false;
		} finally {
			// finally block used to close resources
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException se) {
				LOGGER.error(se.getMessage());
			} // do nothing
		}

	}
	
}
