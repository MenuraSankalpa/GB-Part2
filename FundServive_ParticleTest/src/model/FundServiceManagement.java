package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FundServiceManagement {

	private Connection connect(){
		
		Connection con = null;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fundingservice", "root", "admin");
		}catch (Exception e){
			e.printStackTrace();
		}
			return con;
	}
	
	
	public String insertFund(String fTitle,String fDesc,String fPrice,String fuName,String date) {
		String output ="";
		
		try
		{
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			String query = "insert into fund(`fId`,`fTitle`,`fDesc`,`fPrice`,`fuName`,`date`) values (?,?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, fTitle);
			preparedStmt.setString(3, fDesc);
			preparedStmt.setDouble(4, Double.parseDouble(fPrice));
			preparedStmt.setString(5,fuName);
			preparedStmt.setString(6,date);
			
			preparedStmt.execute();
			con.close();
			
			
			String newItems = readFunds();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}";
			
			
		}
		catch(Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
					 System.err.println(e.getMessage()); 
		}
		
		return output;
	}
	
	public String readFunds()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Fund Title</th><th>Fund Description</th>" +
	 "<th>Fund Price</th>" +
	 "<th>Funder Name</th>" +
	 "<th>Date</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 String query = "select * from fund";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String fId = Integer.toString(rs.getInt("fId"));
	 String fTitle = rs.getString("fTitle");
	 String fDesc = rs.getString("fDesc");
	 String fPrice = Double.toString(rs.getDouble("fPrice"));
	 String fuName = rs.getString("fuName");
	 String date = rs.getString("date");
	 
	 // Add into the html table
	
	 output += "<tr><td>" + fTitle + "</td>";
	 output += "<td>" + fDesc + "</td>";
	 output += "<td>" + fPrice + "</td>";
	 output += "<td>" + fuName + "</td>";
	 output += "<td>" + date + "</td>";
	 // buttons
	 
	 output += "<td><input name='btnUpdate' type='button' value='Update' "
			 + "class='btnUpdate btn btn-secondary' data-itemid='" + fId + "'></td>"
			 + "<td><input name='btnRemove' type='button' value='Remove' "
			 + "class='btnRemove btn btn-danger' data-itemid='" + fId + "'></td></tr>";
			  } 
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	public String updateFund(String fId, String fTitle, String fDesc, String fPrice, String fuName, String date)
	
	{
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for updating."; }
		 // create a prepared statement
		 String query = "UPDATE fund SET fTitle=?,fDesc=?,fPrice=?,fuName=?,date=? WHERE fId=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, fTitle);
		 preparedStmt.setString(2, fDesc);
		 preparedStmt.setDouble(3, Double.parseDouble(fPrice));
		 preparedStmt.setString(4, fuName);
		 preparedStmt.setString(5, date);
		 preparedStmt.setInt(6, Integer.parseInt(fId));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newItems = readFunds();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newItems + "\"}";
		 }
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
					 System.err.println(e.getMessage()); 
		 }
		 return output;
		 }
		public String deleteFund(String fId)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for deleting."; }
		 // create a prepared statement
		 String query = "delete from fund where fId=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(fId));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newItems = readFunds();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newItems + "\"}"; 
		 }
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
					 System.err.println(e.getMessage()); 
		 }
		 return output;
		 }
		
} 

	

