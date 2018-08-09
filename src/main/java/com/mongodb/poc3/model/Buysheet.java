package com.mongodb.poc3.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
public class Buysheet implements Serializable {

	@Id
	private String buysheetId;
	private String sheetName;
	private Date dateCreated;
	private String createdBy;
	private List<DataPoints> rows;
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<DataPoints> getRows() {
		return rows;
	}
	public void setRows(List<DataPoints> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "BuysheetInfo [sheetName=" + sheetName + ", dateCreated=" + dateCreated + ", createdBy=" + createdBy
				+ ", rows=" + rows + "]";
	}
	
	
}
