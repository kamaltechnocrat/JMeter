/**
 * 
 */
package test_Suite.lib.workflow;


import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author apankov
 * ProgramSteps class to handle Program Steps
 */
@SuppressWarnings("rawtypes")
public class ProgramSteps {

	private String  baseLetter;
	private String  progId;
	private String  progName;
	private String  stepId;
	private String  stepNotes;
	private String  stepType;
	private String  stepProjOfficerGroup;
	private String  stepStartDate;
	private String  stepEndDate;
	private boolean stepIsCritical;
	private String  stepReExecute;
	//Step properties fields
	private String  stepForm;
	private String  stepEvaluationType;
	private String  stepQuorumAmount;
	private boolean stepAutoAssign;
	private String  stepAwardName;
	private String  stepPostAwardForms [];
	private String  stepNames [];
	private int     stepAction;
	private Hashtable stepStaff;
	private String  stepDocuments [] = null;
	private String stepFormAccess [] = null;
	private String stepFormData [] = null;
	private ArrayList<Notifications> stepNotifications;
	
	//Added by Mustafa
	private String stepDecisionDataFromStep;
	private String stepDecisionSkipToStep;
	private String stepDecisionExpression;
	private String stepFullNameInPlanner;

	
	private static Log log = LogFactory.getLog(ProgramSteps.class);

	
	/**
	 * Class default constructor method
	 */
	public ProgramSteps() {
		
		log.info("Program Step initialized");

	}

	public boolean isStepAutoAssign() {
		return stepAutoAssign;
	}

	public void setStepAutoAssign(boolean stepAutoAssign) {
		this.stepAutoAssign = stepAutoAssign;
	}

	public String getStepAwardName() {
		return stepAwardName;
	}

	public void setStepAwardName(String stepAwardName) {
		this.stepAwardName = stepAwardName;
	}

	public String getStepEndDate() {
		return stepEndDate;
	}

	public void setStepEndDate(String stepEndDate) {
		this.stepEndDate = stepEndDate;
	}

	public String getStepEvaluationType() {
		return stepEvaluationType;
	}

	public void setStepEvaluationType(String stepEvaluationType) {
		this.stepEvaluationType = stepEvaluationType;
	}

	public String getStepForm() {
		return stepForm;
	}

	public void setStepForm(String stepForm) {
		this.stepForm = stepForm;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public boolean isStepIsCritical() {
		return stepIsCritical;
	}

	public void setStepIsCritical(boolean stepIsCritical) {
		this.stepIsCritical = stepIsCritical;
	}

	public String[] getStepNames() {
		return stepNames;
	}

	public void setStepNames(String[] stepNames) {
		this.stepNames = stepNames;
	}

	public String getStepNotes() {
		return stepNotes;
	}

	public void setStepNotes(String stepNotes) {
		this.stepNotes = stepNotes;
	}

	public String[] getStepPostAwardForms() {
		return stepPostAwardForms;
	}

	public void setStepPostAwardForms(String[] stepPostAwardForms) {
		this.stepPostAwardForms = stepPostAwardForms;
	}

	public String getStepProjOfficerGroup() {
		return stepProjOfficerGroup;
	}

	public void setStepProjOfficerGroup(String stepProjOfficerGroup) {
		this.stepProjOfficerGroup = stepProjOfficerGroup;
	}

	public String getStepQuorumAmount() {
		return stepQuorumAmount;
	}

	public void setStepQuorumAmount(String stepQuorumAmount) {
		this.stepQuorumAmount = stepQuorumAmount;
	}

	public String getStepReExecute() {
		return stepReExecute;
	}

	public void setStepReExecute(String stepReExecute) {
		this.stepReExecute = stepReExecute;
	}

	public String getStepStartDate() {
		return stepStartDate;
	}

	public void setStepStartDate(String stepStartDate) {
		this.stepStartDate = stepStartDate;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getProgId() {
		return progId;
	}

	public void setProgId(String progId) {
		this.progId = progId;
	}

	public int getStepAction() {
		return stepAction;
	}

	public void setStepAction(int stepAction) {
		this.stepAction = stepAction;
	}
	
	public String getStepDecisionDataFromStep() {
		return stepDecisionDataFromStep;
	}

	public void setStepDecisionDataFromStep(String stepDecisionDataFromStep) {
		this.stepDecisionDataFromStep = stepDecisionDataFromStep;
	}

	public String getStepDecisionExpression() {
		return stepDecisionExpression;
	}

	public void setStepDecisionExpression(String stepDecisionExpression) {
		this.stepDecisionExpression = stepDecisionExpression;
	}

	public String getStepDecisionSkipToStep() {
		return stepDecisionSkipToStep;
	}

	public void setStepDecisionSkipToStep(String stepDecisionSkipToStep) {
		this.stepDecisionSkipToStep = stepDecisionSkipToStep;
	}	
	public String[] getStepDocuments() {
		return stepDocuments;
	}

	public void setStepDocuments(String[] stepDocuments) {
		this.stepDocuments = stepDocuments;
	}

	public ArrayList<Notifications> getStepNotifications() {
		return stepNotifications;
	}

	public void setStepNotifications(ArrayList<Notifications> stepNotifications) {
		this.stepNotifications = stepNotifications;
	}

	public Hashtable getStepStaff() {
		return stepStaff;
	}

	public void setStepStaff(Hashtable stepStaff) {
		this.stepStaff = stepStaff;
	}

	/**
	 * @return the stepFullNameInPlanner
	 */
	public String getStepFullNameInPlanner() {
		return stepFullNameInPlanner;
	}

	/**
	 * @param stepFullNameInPlanner the stepFullNameInPlanner to set
	 */
	public void setStepFullNameInPlanner(String stepFullNameInPlanner) {
		this.stepFullNameInPlanner = stepFullNameInPlanner;
	}

	/**
	 * @return the stepFormAccess
	 */
	public String[] getStepFormAccess() {
		return stepFormAccess;
	}

	/**
	 * @param stepFormAccess the stepFormAccess to set
	 */
	public void setStepFormAccess(String[] stepFormAccess) {
		this.stepFormAccess = stepFormAccess;
	}

	/**
	 * @return the stepFormData
	 */
	public String[] getStepFormData() {
		return stepFormData;
	}

	/**
	 * @param stepFormData the stepFormData to set
	 */
	public void setStepFormData(String[] stepFormData) {
		this.stepFormData = stepFormData;
	}

	/**
	 * @return the baseLetter
	 */
	public String getBaseLetter() {
		return baseLetter;
	}

	/**
	 * @param baseLetter the baseLetter to set
	 */
	public void setBaseLetter(String baseLetter) {
		this.baseLetter = baseLetter;
	}

	/**
	 * @return the progName
	 */
	public String getProgName() {
		return progName;
	}

	/**
	 * @param progName the progName to set
	 */
	public void setProgName(String progName) {
		this.progName = progName;
	}
	

}
