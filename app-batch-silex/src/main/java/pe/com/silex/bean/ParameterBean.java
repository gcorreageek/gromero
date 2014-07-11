package pe.com.silex.bean;

import java.io.Serializable;

public class ParameterBean implements Serializable{

	private static final long serialVersionUID = -6341555408294500007L;
	
	private int codParam;
	private String descParam;
	private String val1;
	private String val2;
	private String val3;
	
	public int getCodParam() {
		return codParam;
	}
	public void setCodParam(int codParam) {
		this.codParam = codParam;
	}
	public String getDescParam() {
		return descParam;
	}
	public void setDescParam(String descParam) {
		this.descParam = descParam;
	}
	public String getVal1() {
		return val1;
	}
	public void setVal1(String val1) {
		this.val1 = val1;
	}
	public String getVal2() {
		return val2;
	}
	public void setVal2(String val2) {
		this.val2 = val2;
	}
	public String getVal3() {
		return val3;
	}
	public void setVal3(String val3) {
		this.val3 = val3;
	}
	@Override
	public String toString() {
		return "ParameterBean [codParam=" + codParam + ", descParam="
				+ descParam + ", val1=" + val1 + ", val2=" + val2 + ", val3="
				+ val3 + "]";
	}
}
