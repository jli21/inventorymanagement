package store;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComputerDAO {
	private final String url;
	private final String username;
	private final String password;
	
	public ComputerDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Computer getComputer(int id) throws SQLException {
		final String sql = "SELECT * FROM computers WHERE computer_id = ?";
		
		Computer computer = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String name = rs.getString("name");
			String type = rs.getString("type");
			int quantity = rs.getInt("quantity");
			int price = rs.getInt("price");
			
			computer = new Computer(id, name, type, quantity, price);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return computer;
	}
	
	public List<Computer> getComputers() throws SQLException {
		final String sql = "SELECT * FROM computers ORDER BY computer_id ASC";
		
		List<Computer> computers = new ArrayList<Computer>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("computer_id");
			String name = rs.getString("name");
			String type = rs.getString("type");
			int quantity = rs.getInt("quantity");
			int price = rs.getInt("price");
			
			computers.add(new Computer(id, name, type, quantity, price));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return computers;
	}
	
	public boolean insertComputer(String name, String type, int quantity, int price) throws SQLException {       
		final String sql = "INSERT INTO computers (name, type, quantity, price) " +
			"VALUES (?, ?, ?, ?)";
		
        Connection conn = getConnection();        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, name);
        pstmt.setString(2, type);
        pstmt.setInt(3, quantity);
        pstmt.setInt(4, price);
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
	
    public boolean updateComputer(Computer computer) throws SQLException {
    	final String sql = "UPDATE computers SET name = ?, type= ?, quantity = ?, price = ? " +
    		"WHERE computer_id = ?";
    			
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
                
        pstmt.setString(1, computer.getName());
        pstmt.setString(2, computer.getType());
        pstmt.setInt(3, computer.getQuantity());
        pstmt.setInt(4, computer.getPrice());
        pstmt.setInt(5, computer.getId());
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
	
    public boolean deleteComputer(Computer computer) throws SQLException {
    	final String sql = "DELETE FROM computers WHERE computer_id = ?";
    	
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setInt(1, computer.getId());
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
    
    public List<Computer> searchComputer(String category, String query) throws SQLException {
    	String sql = "";
    	Connection conn = getConnection();
    	PreparedStatement pstmt = null;
    	System.out.print(category);
    	System.out.print(query);
    	if (category.equals("price")) {
    		int amount = 0;
    		try {
    			amount = Integer.parseInt(query);
    			System.out.print(query);
    		} catch (NumberFormatException e) {
    			
    		}
    		sql = "SELECT * FROM computers WHERE price = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1,  amount);
    		
    	} else if (category.equals("description")) {
    		sql = "SELECT * FROM computers WHERE type = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, query);
    		
    	} else if (category.equals("name")) {
    		sql = "SELECT * FROM computers WHERE name = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, query);
    	}
    	
    	List<Computer> computers = new ArrayList<Computer>();
    	ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.print("aaa");
			int id = rs.getInt("computer_id");
			String name = rs.getString("name");
			String type = rs.getString("type");
			int price = rs.getInt("price");
			int quantity = rs.getInt("quantity");
			
			computers.add(new Computer(id, name, type, quantity, price));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return computers;
    	
    }
    
	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}
}