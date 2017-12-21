package tool.dto;

import java.util.List;

public class ResultDTO<T> {

	private List<T> rows;
	private int total;
	
	private String rtnCode;
	private String rtnMesg;
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getRtnCode() {
		return rtnCode;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	public String getRtnMesg() {
		return rtnMesg;
	}
	public void setRtnMesg(String rtnMesg) {
		this.rtnMesg = rtnMesg;
	}
}