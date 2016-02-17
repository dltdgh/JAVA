package com.bbs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;;


public class Article {
	private int id;
	private int pId;
	private int rootId;
	private String title;
	private String content;
	private Date pDate;
	private boolean isLeaf;
	private int grade;
	private int replyNumber;
	private int userId;
	private int readNumber;
	
	public void initFromRs(ResultSet rs){
		try {
			setId(rs.getInt("id"));
			setpId(rs.getInt("pid"));
			setRootId(rs.getInt("rootid"));
			setTitle(rs.getString("title"));
			setContent(rs.getString("content"));
			setLeaf(rs.getInt("isleaf") == 0 ? true : false);
			setpDate(rs.getTimestamp("pdata"));
			setReplyNumber(rs.getInt("replynumber"));
			setUserId(rs.getInt("userid"));
			setReadNumber(rs.getInt("readnumber"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getRootId() {
		return rootId;
	}
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getpDate() {
		return pDate;
	}
	public void setpDate(Date pDate) {
		this.pDate = pDate;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getReplyNumber() {
		return replyNumber;
	}
	public void setReplyNumber(int replyNumber) {
		this.replyNumber = replyNumber;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getReadNumber() {
		return readNumber;
	}
	public void setReadNumber(int readNumber) {
		this.readNumber = readNumber;
	}
}
