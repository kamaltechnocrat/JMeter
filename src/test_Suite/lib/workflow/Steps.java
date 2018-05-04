/**
 * Steps Class will Contain All Aspects to Create a Step
 */
package test_Suite.lib.workflow;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.util.calendar.BaseCalendar.Date;
import test_Suite.lib.cases.Documents;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.users.Users;

/**
 * @author mshakshouki
 *
 */
public class Steps extends Program {
	
	public Steps()
	{
		
	}
	
	public Steps(String ident)
	{
		this.stepFullIdent = super.getProgLetter() + super.getProgPreFix() + ident + super.getProgPostfix();
		this.stepFullName = removeDashes(this.stepFullIdent);
	}
	
	protected String removeDashes(String string)
	{
		return string.replace("-", " ");
	}
	
	private String stepIdent;
	private String stepName;
	private String stepFullIdent;
	private String stepFullName;
	private String stepType;
	private Date  stepStartDate;
	private Date stepEndDate;
	private boolean stepCritical;
	private String stepReExecute;
	private String stepEvalType;
	private boolean stepAutoAssign;
	private boolean stepCOIRules;
	
	private LinkedHashMap<String, Notifications> lhmNotif;
	private Set<Map.Entry<String, Notifications>> setNotif;
	
	private LinkedHashMap<String, EForm> eForm;
	private Set<Map.Entry<String, EForm>> setEForm;
	
	private LinkedHashMap<String, Steps> lhmSteps;
	private Set<Map.Entry<String, Steps>> setSteps;
	
	private List<String> stepEFormAccess;
	private List<String> stepEFormData;

	private List<String> stepStatusLabel;
	private List<String> stepSubmissionStatusLable;
	private List<Documents> stepDocumentLst;
	
	
	private LinkedHashMap<String, Users> lhmStaff;
	private Set<Map.Entry<String, Users>> setStaff;
	/**
	 * @return the stepIdent
	 */
	public String getStepIdent() {
		return stepIdent;
	}
	/**
	 * @param stepIdent the stepIdent to set
	 */
	public void setStepIdent(String stepIdent) {
		this.stepIdent = stepIdent;
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
	 * @return the stepFullIdent
	 */
	public String getStepFullIdent() {
		return stepFullIdent;
	}
	/**
	 * @param stepFullIdent the stepFullIdent to set
	 */
	public void setStepFullIdent(String stepFullIdent) {
		this.stepFullIdent = stepFullIdent;
	}
	/**
	 * @return the stepFullName
	 */
	public String getStepFullName() {
		return stepFullName;
	}
	/**
	 * @param stepFullName the stepFullName to set
	 */
	public void setStepFullName(String stepFullName) {
		this.stepFullName = stepFullName;
	}
	/**
	 * @return the stepType
	 */
	public String getStepType() {
		return stepType;
	}
	/**
	 * @param stepType the stepType to set
	 */
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	
	/**
	 * @return the stepStartDate
	 */
	public Date getStepStartDate() {
		return stepStartDate;
	}
	/**
	 * @param stepStartDate the stepStartDate to set
	 */
	public void setStepStartDate(Date stepStartDate) {
		this.stepStartDate = stepStartDate;
	}
	/**
	 * @return the stepEndDate
	 */
	public Date getStepEndDate() {
		return stepEndDate;
	}
	/**
	 * @param stepEndDate the stepEndDate to set
	 */
	public void setStepEndDate(Date stepEndDate) {
		this.stepEndDate = stepEndDate;
	}
	/**
	 * @return the stepCritical
	 */
	public boolean isStepCritical() {
		return stepCritical;
	}
	/**
	 * @param stepCritical the stepCritical to set
	 */
	public void setStepCritical(boolean stepCritical) {
		this.stepCritical = stepCritical;
	}
	/**
	 * @return the stepReExecute
	 */
	public String getStepReExecute() {
		return stepReExecute;
	}
	/**
	 * @param stepReExecute the stepReExecute to set
	 */
	public void setStepReExecute(String stepReExecute) {
		this.stepReExecute = stepReExecute;
	}
	/**
	 * @return the stepEvalType
	 */
	public String getStepEvalType() {
		return stepEvalType;
	}
	/**
	 * @param stepEvalType the stepEvalType to set
	 */
	public void setStepEvalType(String stepEvalType) {
		this.stepEvalType = stepEvalType;
	}
	/**
	 * @return the stepAutoAssign
	 */
	public boolean isStepAutoAssign() {
		return stepAutoAssign;
	}
	/**
	 * @param stepAutoAssign the stepAutoAssign to set
	 */
	public void setStepAutoAssign(boolean stepAutoAssign) {
		this.stepAutoAssign = stepAutoAssign;
	}
	/**
	 * @return the stepCOIRules
	 */
	public boolean isStepCOIRules() {
		return stepCOIRules;
	}
	/**
	 * @param stepCOIRules the stepCOIRules to set
	 */
	public void setStepCOIRules(boolean stepCOIRules) {
		this.stepCOIRules = stepCOIRules;
	}
	/**
	 * @return the lhmNotif
	 */
	public LinkedHashMap<String, Notifications> getLhmNotif() {
		return lhmNotif;
	}
	/**
	 * @param lhmNotif the lhmNotif to set
	 */
	public void setLhmNotif(LinkedHashMap<String, Notifications> lhmNotif) {
		this.lhmNotif = lhmNotif;
	}
	/**
	 * @return the setNotif
	 */
	public Set<Map.Entry<String, Notifications>> getSetNotif() {
		return setNotif;
	}
	/**
	 * @param setNotif the setNotif to set
	 */
	public void setSetNotif(Set<Map.Entry<String, Notifications>> setNotif) {
		this.setNotif = setNotif;
	}
	/**
	 * @return the eForm
	 */
	public LinkedHashMap<String, EForm> getEForm() {
		return eForm;
	}
	/**
	 * @param form the eForm to set
	 */
	public void setEForm(LinkedHashMap<String, EForm> form) {
		eForm = form;
	}
	/**
	 * @return the setEForm
	 */
	public Set<Map.Entry<String, EForm>> getSetEForm() {
		return setEForm;
	}
	/**
	 * @param setEForm the setEForm to set
	 */
	public void setSetEForm(Set<Map.Entry<String, EForm>> setEForm) {
		this.setEForm = setEForm;
	}
	/**
	 * @return the lhmSteps
	 */
	public LinkedHashMap<String, Steps> getLhmSteps() {
		return lhmSteps;
	}
	/**
	 * @param lhmSteps the lhmSteps to set
	 */
	public void setLhmSteps(LinkedHashMap<String, Steps> lhmSteps) {
		this.lhmSteps = lhmSteps;
	}
	/**
	 * @return the setSteps
	 */
	public Set<Map.Entry<String, Steps>> getSetSteps() {
		return setSteps;
	}
	/**
	 * @param setSteps the setSteps to set
	 */
	public void setSetSteps(Set<Map.Entry<String, Steps>> setSteps) {
		this.setSteps = setSteps;
	}
	/**
	 * @return the stepEFormAccess
	 */
	public List<String> getStepEFormAccess() {
		return stepEFormAccess;
	}
	/**
	 * @param stepEFormAccess the stepEFormAccess to set
	 */
	public void setStepEFormAccess(List<String> stepEFormAccess) {
		this.stepEFormAccess = stepEFormAccess;
	}
	/**
	 * @return the stepEFormData
	 */
	public List<String> getStepEFormData() {
		return stepEFormData;
	}
	/**
	 * @param stepEFormData the stepEFormData to set
	 */
	public void setStepEFormData(List<String> stepEFormData) {
		this.stepEFormData = stepEFormData;
	}
	/**
	 * @return the stepStatusLabel
	 */
	public List<String> getStepStatusLabel() {
		return stepStatusLabel;
	}
	/**
	 * @param stepStatusLabel the stepStatusLabel to set
	 */
	public void setStepStatusLabel(List<String> stepStatusLabel) {
		this.stepStatusLabel = stepStatusLabel;
	}
	/**
	 * @return the stepSubmissionStatusLable
	 */
	public List<String> getStepSubmissionStatusLable() {
		return stepSubmissionStatusLable;
	}
	/**
	 * @param stepSubmissionStatusLable the stepSubmissionStatusLable to set
	 */
	public void setStepSubmissionStatusLable(List<String> stepSubmissionStatusLable) {
		this.stepSubmissionStatusLable = stepSubmissionStatusLable;
	}
	/**
	 * @return the lhmStaff
	 */
	public LinkedHashMap<String, Users> getLhmStaff() {
		return lhmStaff;
	}
	/**
	 * @param lhmStaff the lhmStaff to set
	 */
	public void setLhmStaff(LinkedHashMap<String, Users> lhmStaff) {
		this.lhmStaff = lhmStaff;
	}
	/**
	 * @return the setStaff
	 */
	public Set<Map.Entry<String, Users>> getSetStaff() {
		return setStaff;
	}
	/**
	 * @param setStaff the setStaff to set
	 */
	public void setSetStaff(Set<Map.Entry<String, Users>> setStaff) {
		this.setStaff = setStaff;
	}
	
	public List<Documents> getStepDocumentLst() {
		return stepDocumentLst;
	}

	public void setStepDocumentLst(List<Documents> stepDocumentLst) {
		this.stepDocumentLst = stepDocumentLst;
	}
}
