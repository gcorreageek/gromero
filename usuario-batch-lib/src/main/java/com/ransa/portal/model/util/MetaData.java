package com.ransa.portal.model.util;

import java.io.Serializable;

public class MetaData implements Displayable, Serializable {
	
	private static final long serialVersionUID = -7053515079697413022L;
	
	private String value;
	private String label;

	public MetaData() {
	}

	public MetaData(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[label=").append(label).append(", value=").append(value).append("]");
		return buffer.toString();
	}

}
