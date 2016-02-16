package pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Type {
	
	private Integer id = null;
	private Integer typeSign = null;
	private String typeName = null;
	private String typeIntro = null;
	
	public Type(Integer id, Integer typeSign, String typeName, String typeIntro){
		this.id = id;
		this.typeSign = typeSign;
		this.typeName = typeName;
		this.typeIntro = typeIntro;
	}
	
	public Type(ResultSet rs) {
		try {
			this.id = rs.getInt("id");
			this.typeSign = rs.getInt("type_sign");
			this.typeName = rs.getString("type_name");
			this.typeIntro = rs.getString("type_intro");
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
	public Integer getTypeSign() {
		return typeSign;
	}
	public void setTypeSign(Integer typeSign) {
		this.typeSign = typeSign;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeIntro() {
		return typeIntro;
	}
	public void setTypeIntro(String typeIntro) {
		this.typeIntro = typeIntro;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+" "+typeSign+" "+typeName+" "+typeIntro;
	}
}
