package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.util.List;

public class JqGridDataTipoDocumental implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int page;
	private int total;
	private int records;
	private List<TipoDocumental> rows;
	private List<String> rowsHead;
	private List<Object> rowsM;
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
	public List<TipoDocumental> getRows() {
		return rows;
	}
	public void setRows(List<TipoDocumental> rows) {
		this.rows = rows;
	}
	public List<String> getRowsHead() {
		return rowsHead;
	}
	public void setRowsHead(List<String> rowsHead) {
		this.rowsHead = rowsHead;
	}
	public List<Object> getRowsM() {
		return rowsM;
	}
	public void setRowsM(List<Object> rowsM) {
		this.rowsM = rowsM;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	
	 

}
