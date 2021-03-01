package com.tj.SqlInjectionKitty.model;

public class LogEntry {
	
	String name;
	String message;
	int id;
	public enum SubmitType {prepared, injection};
	SubmitType submitType;
	
	//Constructor
	public LogEntry(String name, String message, int id, SubmitType submitType) {
		this.name = name;
		this.message = message;
		this.id = id;
		this.submitType = submitType;
	}
	
	
	//Getters & setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SubmitType getSubmitType() {
		return submitType;
	}
	public void setSubmitType(SubmitType submitType) {
		this.submitType = submitType;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
