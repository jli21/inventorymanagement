package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws SQLException {
		final String url = "jdbc:mysql://localhost:3306/compstore?serverTimezone=EST";
		final String username = "root";
		final String password = "jaju2002";
		
		Connection conn = DriverManager.getConnection(url, username, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM computers");
		
		System.out.println("Computers in Computer Store : \n");
		while (rs.next()) {
			String name = rs.getString("name");
			String type = rs.getString("type");
			int quantity = rs.getInt("quantity");
			int price = rs.getInt(("price"));
			
			System.out.println(" --> " + name + " by " + type + " (" + quantity + " of " + price + ")");
		}
		
		rs.close();
		stmt.close();
		conn.close();

	}

}