package pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	private Integer id = null;
	private String userName = null;
	private String password = null;
	
	public User(Integer id, String userName, String password){
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
	
	public User(ResultSet rs) {
		try {
			this.id = rs.getInt("id");
			this.userName = rs.getString("user_name");
			this.password = rs.getString("user_password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+" "+userName+" "+password;
	}
}
