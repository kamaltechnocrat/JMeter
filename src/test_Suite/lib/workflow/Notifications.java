package test_Suite.lib.workflow;

public class Notifications {

	private boolean    ntfIsActive;
	private String     ntfName;
	private String     ntfOnEvent;
	private boolean    ntfInsertProjectActivity;
	private int        ntfDaysAfter;
	private int        ntfDaysUntil;
	private boolean    ntfRepeatable;
	private boolean    ntfApplicants;
	private boolean    ntfProjectOfficers;
	private boolean	   ntfProjectOfficer;
	private boolean    ntfAssignedEvaluators;
	private String  [] ntfMessageSubject;
	private String  [] ntfMessageBody;
	private String  [] ntfInternalRecipients;
	private String     ntfExternalLocale;
	private String  [] ntfExternalRecepients;
	
	public Notifications() {
	}

	public boolean isNtfApplicants() {
		return ntfApplicants;
	}

	public void setNtfApplicants(boolean ntfApplicants) {
		this.ntfApplicants = ntfApplicants;
	}

	public int getNtfDaysAfter() {
		return ntfDaysAfter;
	}

	public void setNtfDaysAfter(int ntfDaysAfter) {
		this.ntfDaysAfter = ntfDaysAfter;
	}

	public int getNtfDaysUntil() {
		return ntfDaysUntil;
	}

	public void setNtfDaysUntil(int ntfDaysIntil) {
		this.ntfDaysUntil = ntfDaysIntil;
	}

	public String getNtfExternalLocale() {
		return ntfExternalLocale;
	}

	public void setNtfExternalLocale(String ntfExternalLocale) {
		this.ntfExternalLocale = ntfExternalLocale;
	}

	public String[] getNtfExternalRecepients() {
		return ntfExternalRecepients;
	}

	public void setNtfExternalRecepients(String[] ntfExternalRecepients) {
		this.ntfExternalRecepients = ntfExternalRecepients;
	}

	public boolean isNtfInsertProjectActivity() {
		return ntfInsertProjectActivity;
	}

	public void setNtfInsertProjectActivity(boolean ntfInsertProjectActivity) {
		this.ntfInsertProjectActivity = ntfInsertProjectActivity;
	}

	public String[] getNtfInternalRecipients() {
		return ntfInternalRecipients;
	}

	public void setNtfInternalRecipients(String[] ntfInternalRecipients) {
		this.ntfInternalRecipients = ntfInternalRecipients;
	}

	public boolean isNtfIsActive() {
		return ntfIsActive;
	}

	public void setNtfIsActive(boolean ntfIsActive) {
		this.ntfIsActive = ntfIsActive;
	}

	public String[] getNtfMessageBody() {
		return ntfMessageBody;
	}

	public void setNtfMessageBody(String[] ntfMessageBody) {
		this.ntfMessageBody = ntfMessageBody;
	}

	public String[] getNtfMessageSubject() {
		return ntfMessageSubject;
	}

	public void setNtfMessageSubject(String[] ntfMessageSubject) {
		this.ntfMessageSubject = ntfMessageSubject;
	}

	public String getNtfName() {
		return ntfName;
	}

	public void setNtfName(String ntfName) {
		this.ntfName = ntfName;
	}

	public String getNtfOnEvent() {
		return ntfOnEvent;
	}

	public void setNtfOnEvent(String ntfOnEvent) {
		this.ntfOnEvent = ntfOnEvent;
	}

	public boolean isNtfProjectOfficers() {
		return ntfProjectOfficers;
	}

	public void setNtfProjectOfficers(boolean ntfProjectOfficers) {
		this.ntfProjectOfficers = ntfProjectOfficers;
	}

	public boolean isNtfProjectOfficer() {
		return ntfProjectOfficer;
	}

	public void setNtfProjectOfficer(boolean ntfProjectOfficer) {
		this.ntfProjectOfficer = ntfProjectOfficer;
	}

	public boolean isNtfAssignedEvaluators() {
		return ntfAssignedEvaluators;
	}

	public void setNtfAssignedEvaluators(boolean ntfAssignedEvaluators) {
		this.ntfAssignedEvaluators = ntfAssignedEvaluators;
	}

	public boolean isNtfRepeatable() {
		return ntfRepeatable;
	}

	public void setNtfRepeatable(boolean ntfRepeatable) {
		this.ntfRepeatable = ntfRepeatable;
	}

}
