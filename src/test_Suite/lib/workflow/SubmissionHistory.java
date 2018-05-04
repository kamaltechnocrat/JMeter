/**
 * 
 */
package test_Suite.lib.workflow;

import org.testng.Assert;

/**
 * @author mshakshouki
 *
 */
public class SubmissionHistory extends ProjectsList {
	
	protected String subProjectName;
	
	protected String stepName;
	
	protected String historyStatus;
	
	protected String historySubVersion;
	
	protected String postAwardSubmissionName;
	
	protected String associatedUser;
	
	protected String overrideDate;
	
	protected String statusDate;
	
	protected String overrideComments;
	
	
	/***************************
	 *		Constructor
	 **************************/
	public SubmissionHistory() {
		super();
	}
	
	public SubmissionHistory(Project proj) {
		super(proj);
	}
	
	public SubmissionHistory(Project proj, String stepName) {
		
		super(proj);
		
		try {
			
			proj.initializeStep(stepName);
			
			this.setStepName(proj.getCurrentStepName());
			
		} catch (Exception e) {
			Assert.fail("Unexpected error", e);
		}
	}

	/************************
	 * Getters and Setters
	 ************************/

	/**
	 * @return the associatedUser
	 */
	public String getAssociatedUser() {
		return associatedUser;
	}

	/**
	 * @param associatedUser the associatedUser to set
	 */
	public void setAssociatedUser(String associatedUser) {
		this.associatedUser = associatedUser;
	}

	/**
	 * @return the historyStatus
	 */
	public String getHistoryStatus() {
		return historyStatus;
	}

	/**
	 * @param historyStatus the historyStatus to set
	 */
	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}

	/**
	 * @return the postAwardSubmissionName
	 */
	public String getPostAwardSubmissionName() {
		return postAwardSubmissionName;
	}

	/**
	 * @param postAwardSubmissionName the postAwardSubmissionName to set
	 */
	public void setPostAwardSubmissionName(String postAwardSubmissionName) {
		this.postAwardSubmissionName = postAwardSubmissionName;
	}


	/**
	 * @return the subProjectName
	 */
	public String getSubProjectName() {
		return subProjectName;
	}

	/**
	 * @param subProjectName the subProjectName to set
	 */
	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}


	/**
	 * @return the historySubVersion
	 */
	public String getHistorySubVersion() {
		return historySubVersion;
	}


	/**
	 * @param historySubVersion the historySubVersion to set
	 */
	public void setHistorySubVersion(String historySubVersion) {
		this.historySubVersion = historySubVersion;
	}

	/**
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * @return the overrideDate
	 */
	public String getOverrideDate() {
		return overrideDate;
	}

	/**
	 * @param overrideDate the overrideDate to set
	 */
	public void setOverrideDate(String overrideDate) {
		this.overrideDate = overrideDate;
	}

	/**
	 * @return the statusDate
	 */
	public String getStatusDate() {
		return statusDate;
	}

	/**
	 * @param statusDate the statusDate to set
	 */
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	/**
	 * @return the overrideComments
	 */
	public String getOverrideComments() {
		return overrideComments;
	}

	/**
	 * @param overrideComments the overrideComments to set
	 */
	public void setOverrideComments(String overrideComments) {
		this.overrideComments = overrideComments;
	}


}
