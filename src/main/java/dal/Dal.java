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
	
	public String persistEntry(LogEntry submission) throws SQLException {
		
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
	private String persistWithParameterizedQuery(LogEntry submission) throws SQLException {
		
		String name = submission.getName();
		String message = submission.getMessage();
		
		String sql = "INSERT INTO log_entries (name, message) VALUES (?, ?, ?)";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, name);
		statement.setString(2, message);
		
		
		
		
		return "string";
	}
	
	private String persistWithoutParameterizedQuery(LogEntry submission) throws SQLException {
		
		
		String name = submission.getName();
		String message = submission.getMessage();
		
		String sql = "INSERT INTO log_entries (name, message) VALUES ('" + name + "', '" + message + "')";
		
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		
		
		return "string";
		
	}
	
	
	

}
