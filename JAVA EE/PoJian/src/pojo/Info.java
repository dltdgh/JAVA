package pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Info {
	private Integer id = null;
	private Integer infoType = null;
	private String infoTitle = null;
	private String infoLinkman = null;
	private String infoPhone = null;
	private Date infoDate = null;
	private String infoState = null;
	private String infoContent = null;
	private String infoEmail = null;
	private String infoPayfor = null;

	
	public Info(Integer id, Integer infoType, String infoTitle,
			String infoLinkman, String infoPhone, Date infoDate,
			String infoState, String infoContent, String infoEmail,
			String infoPayfor) {
		this.id = id;
		this.infoType = infoType;
		this.infoTitle = infoTitle;
		this.infoLinkman = infoLinkman;
		this.infoPhone = infoPhone;
		this.infoDate = infoDate;
		this.infoState = infoState;
		this.infoContent = infoContent;
		this.infoEmail = infoEmail;
		this.infoPayfor = infoPayfor;
	}
	
	public Info(){
		this.id = 0;
		this.infoType = 0;
		this.infoTitle = "无";
		this.infoLinkman = "无";
		this.infoPhone = "无";
		this.infoDate = new Date(0);
		this.infoState = "无";
		this.infoContent = "无";
		this.infoEmail = "无";
		this.infoPayfor = "无";
	}
	
	public Info(ResultSet rs) {
		try {
			this.id = rs.getInt("id");
			this.infoType = rs.getInt("info_type");
			this.infoTitle = rs.getString("info_title");
			this.infoLinkman = rs.getString("info_linkman");
			this.infoPhone = rs.getString("info_phone");
			this.infoDate = rs.getTimestamp("info_date");
			this.infoState = rs.getString("info_state");
			this.infoContent = rs.getString("info_content");
			this.infoEmail = rs.getString("info_email");
			this.infoPayfor = rs.getString("info_payfor");
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
	public Integer getInfoType() {
		return infoType;
	}
	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getInfoLinkman() {
		return infoLinkman;
	}
	public void setInfoLinkman(String infoLinkman) {
		this.infoLinkman = infoLinkman;
	}
	public String getInfoPhone() {
		return infoPhone;
	}
	public void setInfoPhone(String infoPhone) {
		this.infoPhone = infoPhone;
	}
	public Date getInfoDate() {
		return infoDate;
	}
	public void setInfoDate(Date infoDate) {
		this.infoDate = infoDate;
	}
	public String getInfoState() {
		return infoState;
	}
	public void setInfoState(String infoState) {
		this.infoState = infoState;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getInfoEmail() {
		return infoEmail;
	}
	public void setInfoEmail(String infoEmail) {
		this.infoEmail = infoEmail;
	}
	public String getInfoPayfor() {
		return infoPayfor;
	}
	public void setInfoPayfor(String infoPayfor) {
		this.infoPayfor = infoPayfor;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return super.toString();
		return this.id+" "+this.infoType+" "+this.infoTitle+" "+this.infoLinkman+" "+this.infoPhone+" "+this.infoDate.toString()+" "+this.infoState+" "+this.infoContent+" "+this.infoEmail+" "+this.infoPayfor;
	}
}