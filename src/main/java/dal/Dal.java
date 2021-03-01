package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.tj.SqlInjectionKitty.model.LogEntry;
import com.tj.SqlInjectionKitty.model.LogEntry.SubmitType;

public class Dal {
	
	private Gson gson;
	private Connection conn;
	private String dbUrl = "jdbc:postgresql://localhost:5432/sql-injection-kitty-pg";
	
	
	public Dal() {	
		this.gson = new Gson();
		try {
			this.conn = DriverManager.getConnection(dbUrl, "postgres", "postgres");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getEntryById(int id) throws SQLException {
		
		String sql = "SELECT * FROM log_entries WHERE id = ?";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, String.valueOf(id));
		ResultSet result = statement.executeQuery(sql);
		
		LogEntry entry = null;
		while (result.next()) {
			String name = result.getString("name");
			String message = result.getString("message");
			String entryId = result.getString("id");
			entry = new LogEntry(name, message, Integer.parseInt(entryId), null);
		}
		
		return gson.toJson(entry);
		
	}
	
	public String getAllEntries() throws SQLException {
		
		String sql = "SELECT * FROM log_entries";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		ResultSet result = statement.executeQuery(sql);
		
		List<LogEntry> entry = new ArrayList<LogEntry>();
		while (result.next()) {
			String name = result.getString("name");
			String message = result.getString("message");
			String entryId = result.getString("id");
			entry.add(new LogEntry(name, message, Integer.parseInt(entryId), null));
		}
		
		return gson.toJson(entry);
		
	}
	
	public String persistEntry(LogEntry submission){
		
		String response = null;
		
		if (submission.getSubmitType().equals(SubmitType.Prepared)) {
			response = persistWithParameterizedQuery(submission);
		} else if (submission.getSubmitType().equals(SubmitType.Injection)) {
			response = persistWithoutParameterizedQuery(submission);
		} else {
			response = "Invalid submission type";
		}
			
		
		return response;
		
		
	}
	
	//https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
	//Refer to this documentation for retrieving the id of an isert: https://www.codejava.net/java-se/jdbc/get-id-of-inserted-record-in-database
	private String persistWithParameterizedQuery(LogEntry submission) {
		
		String name = submission.getName();
		String message = submission.getMessage();
		
		String sql = "INSERT INTO log_entries (name, message) VALUES (?, ?)";
		
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setString(2, message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			statement.execute();
//			ResultSet result = statement.executeQuery();
//			ResultSet result = statement.getGeneratedKeys();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "string";
	}
	
	private String persistWithoutParameterizedQuery(LogEntry submission) {
		
		
		String name = submission.getName();
		String message = submission.getMessage();
		
		String sql = "INSERT INTO log_entries (name, message) VALUES ('" + name + "', '" + message + "')";
		
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ResultSet result = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "string";
		
	}
	
	
	

}
