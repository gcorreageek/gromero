package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;

public class Grilla implements Serializable{
	
	private int page;
	private int total;
	private int records; 
	private String success;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	 

}
