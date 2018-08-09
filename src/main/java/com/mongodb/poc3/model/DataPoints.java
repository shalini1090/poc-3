package com.mongodb.poc3.model;

import java.io.Serializable;
import java.util.HashMap;

public class DataPoints implements Serializable {

	private String cc;
	private String cl;
	private String week;
	private HashMap<String, Integer> hmMajor ;
	
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public HashMap<String, Integer> getHmMajor() {
		return hmMajor;
	}
	public void setHmMajor(HashMap<String, Integer> hmMajor) {
		this.hmMajor = hmMajor;
	}
	@Override
	public String toString() {
		return "DataPoint [cc=" + cc + ", cl=" + cl + ", week=" + week + ", hmMajor=" + hmMajor + "]";
	}
	
	
}
