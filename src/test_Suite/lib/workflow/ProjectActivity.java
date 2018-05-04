/**
 * 
 */
package test_Suite.lib.workflow;

/**
 * @author mshakshouki
 *
 */
public class ProjectActivity extends ProjectsList {
	
	protected String activityType;
	
	protected String activityStatus;
	
	protected String activityDate;
	
	protected String activityDueDate;
	
	protected String activityPurpose;
	
	protected String activityDescription;
	
	protected boolean activityCompleted;
	
	protected boolean activityNotifActive;
	
	protected String activityNotifDaysBefore;
	
	protected boolean activityNotifRepeat;
	
	protected String activityNotifDaysUntil;
	
	protected String[] activityNotifRecipients;
	
	protected String activityNotifMessageLocale;
	
	protected String activityNotifExternalRecipients;
	
	protected String activityNotifMessageSubjet;
	
	protected String activityNotifMessageBody;
	
	protected String activityNotifName;
	

	public boolean isActivityCompleted() {
		return activityCompleted;
	}



	public void setActivityCompleted(boolean activityCompleted) {
		this.activityCompleted = activityCompleted;
	}



	public String getActivityDate() {
		return activityDate;
	}



	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}



	public String getActivityDescription() {
		return activityDescription;
	}



	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}



	public String getActivityDueDate() {
		return activityDueDate;
	}



	public void setActivityDueDate(String activityDueDate) {
		this.activityDueDate = activityDueDate;
	}



	public String getActivityNotifDaysBefore() {
		return activityNotifDaysBefore;
	}



	public void setActivityNotifDaysBefore(String activityNotifDaysBefore) {
		this.activityNotifDaysBefore = activityNotifDaysBefore;
	}



	public String getActivityNotifDaysUntil() {
		return activityNotifDaysUntil;
	}



	public void setActivityNotifDaysUntil(String activityNotifDaysUntil) {
		this.activityNotifDaysUntil = activityNotifDaysUntil;
	}



	public String getActivityNotifExternalRecipients() {
		return activityNotifExternalRecipients;
	}



	public void setActivityNotifExternalRecipients(
			String activityNotifExternalRecipients) {
		this.activityNotifExternalRecipients = activityNotifExternalRecipients;
	}



	public String getActivityNotifMessageLocale() {
		return activityNotifMessageLocale;
	}



	public void setActivityNotifMessageLocale(String activityNotifMessageLocale) {
		this.activityNotifMessageLocale = activityNotifMessageLocale;
	}



	public String[] getActivityNotifRecipients() {
		return activityNotifRecipients;
	}



	public void setActivityNotifRecipients(String[] activityNotifRecipients) {
		this.activityNotifRecipients = activityNotifRecipients;
	}



	public boolean isActivityNotifRepeat() {
		return activityNotifRepeat;
	}



	public void setActivityNotifRepeat(boolean activityNotifRepeat) {
		this.activityNotifRepeat = activityNotifRepeat;
	}



	public String getActivityPurpose() {
		return activityPurpose;
	}



	public void setActivityPurpose(String activityPurpose) {
		this.activityPurpose = activityPurpose;
	}



	public String getActivityStatus() {
		return activityStatus;
	}



	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}



	public String getActivityType() {
		return activityType;
	}



	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}



	public ProjectActivity() {
		
	}



	public boolean isActivityNotifActive() {
		return activityNotifActive;
	}



	public void setActivityNotifActive(boolean activityNotifActive) {
		this.activityNotifActive = activityNotifActive;
	}



	/**
	 * @return the activityNotifMessageBody
	 */
	public String getActivityNotifMessageBody() {
		return activityNotifMessageBody;
	}



	/**
	 * @param activityNotifMessageBody the activityNotifMessageBody to set
	 */
	public void setActivityNotifMessageBody(String activityNotifMessageBody) {
		this.activityNotifMessageBody = activityNotifMessageBody;
	}



	/**
	 * @return the activityNotifMessageSubjet
	 */
	public String getActivityNotifMessageSubjet() {
		return activityNotifMessageSubjet;
	}



	/**
	 * @param activityNotifMessageSubjet the activityNotifMessageSubjet to set
	 */
	public void setActivityNotifMessageSubjet(String activityNotifMessageSubjet) {
		this.activityNotifMessageSubjet = activityNotifMessageSubjet;
	}



	/**
	 * @return the activityNotifName
	 */
	public String getActivityNotifName() {
		return activityNotifName;
	}



	/**
	 * @param activityNotifName the activityNotifName to set
	 */
	public void setActivityNotifName(String activityNotifName) {
		this.activityNotifName = activityNotifName;
	}	

}
