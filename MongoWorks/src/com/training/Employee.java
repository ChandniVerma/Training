package com.training;

import com.mongodb.BasicDBObject;

public class Employee extends BasicDBObject{
	private int empId;
	private String empName;
	private double empSal;
	private String empEmail;
	public int getEmpId() {
		
		return empId;
	}
	public void setEmpId(int empId) {
		this.put("empid", empId);
		this.empId = empId;
	}
	public String getEmpName() {
		
		return empName;
	}
	public void setEmpName(String empName) {
		this.put("empname", empName);
		this.empName = empName;
	}
	public double getEmpSal() {
		
		return empSal;
	}
	public void setEmpSal(double empSal) {
		this.put("empsale", empSal);
		this.empSal = empSal;
	}
	public String getEmpEmail() {
		
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.put("empemail", empEmail);
		this.empEmail = empEmail;
	}
	public Employee(int empId, String empName, double empSal, String empEmail) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empSal = empSal;
		this.empEmail = empEmail;
	}
	@Override
	public String toString() {
		return "Employee []";
	}
	public Employee() {
		super();
	}
	
	

}
