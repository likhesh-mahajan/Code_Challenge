package com.csv.entity;


// Program inputs are and constructors are declared here.

public class Inputs {
	private String accountId;
	private String from;
	private String to;
	public Inputs(String accountId, String from, String to) {
		super();
		this.accountId = accountId;
		this.from = from;
		this.to = to;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
}
