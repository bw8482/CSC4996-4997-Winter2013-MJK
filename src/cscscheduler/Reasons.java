package cscscheduler;

// Generated Mar 20, 2013 4:04:02 PM by Hibernate Tools 3.4.0.CR1

/**
 * Reasons generated by hbm2java
 */
public class Reasons implements java.io.Serializable {

	private Integer reasonId;
	private String reasonText;
	private String reasonFeedback;

	public Reasons() {
	}

	public Reasons(String reasonText, String reasonFeedback) {
		this.reasonText = reasonText;
		this.reasonFeedback = reasonFeedback;
	}

	public Integer getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonText() {
		return this.reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getReasonFeedback() {
		return this.reasonFeedback;
	}

	public void setReasonFeedback(String reasonFeedback) {
		this.reasonFeedback = reasonFeedback;
	}

}
