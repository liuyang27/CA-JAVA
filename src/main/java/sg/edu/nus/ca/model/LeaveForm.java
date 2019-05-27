package sg.edu.nus.ca.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="emp_leave")
public class LeaveForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id",length=10)
	private int id;
	
	
	@Column(name="Category",length=50,nullable=true)
	private String category;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="START",nullable=true)
	private Date start;
	

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="END",nullable=true)
	private Date end;
	
	@Column(name="NumOfDays",nullable=true)
	private double numOfDays;
	
	@Column(name="Reason",nullable=true)
	private String reason;
	
	@Column(name="WorkDissemination",nullable=true)
	private String workDissemination;
	
	@Column(name="ContactDetails",length=50,nullable=true)
	private String contactDetails;
	
	@Column(name="ManagerComments",nullable=true)
	private String managerComments;

	@Column(name="LeaveStatus",length=50,nullable=true)
	private String leaveStatus;

	@ManyToOne
	@JoinColumn(name="EmployeeId")
	private Employee employee;
	
	public LeaveForm() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date date) {
		this.start = date;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public double getNumOfDays() {
		return numOfDays;
	}

	public void setNumOfDays(double numOfDays) {
		this.numOfDays = numOfDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getWorkDissemination() {
		return workDissemination;
	}

	public void setWorkDissemination(String workDissemination) {
		this.workDissemination = workDissemination;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getManagerComments() {
		return managerComments;
	}

	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}



	@Override
	public String toString() {
		return "LeaveForm [id=" + id + ", category=" + category + ", start=" + start + ", end=" + end + ", numOfDays="
				+ numOfDays + ", reason=" + reason + ", workDissemination=" + workDissemination + ", contactDetails="
				+ contactDetails + ", managerComments=" + managerComments + ", leaveStatus=" + leaveStatus
				+ ", employee=" + employee + "]";
	}
	
	
	
}
