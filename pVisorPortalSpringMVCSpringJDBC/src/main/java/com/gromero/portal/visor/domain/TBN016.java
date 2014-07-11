package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class TBN016 implements Serializable {
 
	private static final long serialVersionUID = 7613446326943579597L;
//	FLGPEDI FLGPEDI IDOPEPED
	private Integer idTBN016;
	private String FLGPEDI;
	private String IDOPEPED; 
	private String NROPEDI;
	
	public Integer getIdTBN016() {
		return idTBN016;
	}
	public void setIdTBN016(Integer idTBN016) {
		this.idTBN016 = idTBN016;
	}
	public String getFLGPEDI() {
		return FLGPEDI;
	}
	public void setFLGPEDI(String fLGPEDI) {
		FLGPEDI = fLGPEDI;
	}
	public String getIDOPEPED() {
		return IDOPEPED;
	}
	public void setIDOPEPED(String iDOPEPED) {
		IDOPEPED = iDOPEPED;
	}
	public String getNROPEDI() {
		return NROPEDI;
	}
	public void setNROPEDI(String nROPEDI) {
		NROPEDI = nROPEDI;
	}
	
	
	

}
