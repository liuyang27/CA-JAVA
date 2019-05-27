package sg.edu.nus.ca.javabean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class LeaveReport {
	//private String id;
	
	private String employeeid;

	private String category;

	private String start;
	
	private String end;
	
	private double numOfDays;

	private String leaveStatus;

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public double getNumOfDays() {
		return numOfDays;
	}

	public void setNumOfDays(double numOfDays) {
		this.numOfDays = numOfDays;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public LeaveReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
