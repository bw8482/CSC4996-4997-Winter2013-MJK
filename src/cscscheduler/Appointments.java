package cscscheduler;

// Generated Mar 20, 2013 4:04:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Appointments generated by hbm2java
 */
public class Appointments implements java.io.Serializable {

	private AppointmentsId id;
	private Integer reason;
	private String comments;
	private Date apptTime;
	private String advisorAccessId;
	private Date tsCreated;
	private Integer attendance;
	private Integer cancelled;

	public Appointments() {
	}

	public Appointments(AppointmentsId id, Date apptTime, Date tsCreated) {
		this.id = id;
		this.apptTime = apptTime;
		this.tsCreated = tsCreated;
	}

	public Appointments(AppointmentsId id, Integer reason, String comments,
			Date apptTime, String advisorAccessId, Date tsCreated,
			Integer attendance, Integer cancelled) {
		this.id = id;
		this.reason = reason;
		this.comments = comments;
		this.apptTime = apptTime;
		this.advisorAccessId = advisorAccessId;
		this.tsCreated = tsCreated;
		this.attendance = attendance;
		this.cancelled = cancelled;
	}

	public AppointmentsId getId() {
		return this.id;
	}

	public void setId(AppointmentsId id) {
		this.id = id;
	}

	public Integer getReason() {
		return this.reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getApptTime() {
		return this.apptTime;
	}

	public void setApptTime(Date apptTime) {
		this.apptTime = apptTime;
	}

	public String getAdvisorAccessId() {
		return this.advisorAccessId;
	}

	public void setAdvisorAccessId(String advisorAccessId) {
		this.advisorAccessId = advisorAccessId;
	}

	public Date getTsCreated() {
		return this.tsCreated;
	}

	public void setTsCreated(Date tsCreated) {
		this.tsCreated = tsCreated;
	}

	public Integer getAttendance() {
		return this.attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	public Integer getCancelled() {
		return this.cancelled;
	}

	public void setCancelled(Integer cancelled) {
		this.cancelled = cancelled;
	}

}