package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class BEDatosRSAP implements Serializable {
 
	private static final long serialVersionUID = 4212848570693359358L;
	
	
	private Integer idBEDatosRSAP;
	private Object todo;
	
	private String DESCMERC;
	private Integer NUMOPE;
	private Integer NROCONT;
	private String CCNBNS;
	private Integer NROOI;
	
	public Integer getIdBEDatosRSAP() {
		return idBEDatosRSAP;
	}
	public void setIdBEDatosRSAP(Integer idBEDatosRSAP) {
		this.idBEDatosRSAP = idBEDatosRSAP;
	}
	public Object getTodo() {
		return todo;
	}
	public void setTodo(Object todo) {
		this.todo = todo;
	}
	public String getDESCMERC() {
		return DESCMERC;
	}
	public void setDESCMERC(String dESCMERC) {
		DESCMERC = dESCMERC;
	}
	public Integer getNUMOPE() {
		return NUMOPE;
	}
	public void setNUMOPE(Integer nUMOPE) {
		NUMOPE = nUMOPE;
	}
	public Integer getNROCONT() {
		return NROCONT;
	}
	public void setNROCONT(Integer nROCONT) {
		NROCONT = nROCONT;
	}
	public String getCCNBNS() {
		return CCNBNS;
	}
	public void setCCNBNS(String cCNBNS) {
		CCNBNS = cCNBNS;
	}
	public Integer getNROOI() {
		return NROOI;
	}
	public void setNROOI(Integer nROOI) {
		NROOI = nROOI;
	}
	
	 

}
