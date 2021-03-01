package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gson.Gson;

public class Dal {
	
	private Gson gson;
	private Connection conn;
	private String dbUrl = "jdbc:postgresql://localhost:5432/sq-injection-kitty-pg";
	
	
	public Dal() {	
		this.gson = new Gson();
		try {
			this.conn = DriverManager.getConnection(dbUrl, "postgres", "postgres");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
