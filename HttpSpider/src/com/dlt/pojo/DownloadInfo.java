package com.dlt.pojo;

public class DownloadInfo {
	private String uri;
	private String filePath;
	private Integer fileLength;
	
	public DownloadInfo(String uri, String filePath) {
		this.uri = uri;
		this.filePath = filePath;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Integer getFileLength() {
		return fileLength;
	}
	
	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}
}
