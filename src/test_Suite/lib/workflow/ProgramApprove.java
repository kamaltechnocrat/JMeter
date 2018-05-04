/**
 * 
 */
package test_Suite.lib.workflow;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author apankov
 *
 */
@SuppressWarnings("rawtypes")
public class ProgramApprove {

	//Form fields
	private String progApproveFullId;
	private String progApproveId;
	private String progApproveForm;
	private String progApproveStartDate;
	private String progApproveEndDate;
	private String progApproveOfficer;
	private String progApproveStatus;
	private String progApprovePrOrg;
	private String progApproveOrgAccess;
	private String progApproveName;
	private String [] progApproveNames;
	private String progApproveBaseLetter;
	private String progApprovePrefix;
	private String progApprovePostfix;
	
	//Additional Objects
	private String [] progApproveAdmin;
	private Hashtable progApproveStaff;
	private ArrayList<ProgramSteps> progApproveSteps;
	private int       createOrUpdate;
	private String progApproveProjPrefix;
	/**
	 * @return the progApproveProjPrefix
	 */
	public String getProgApproveProjPrefix() {
		return progApproveProjPrefix;
	}

	/**
	 * @param progApproveProjPrefix the progApproveProjPrefix to set
	 */
	public void setProgApproveProjPrefix(String progApproveProjPrefix) {
		this.progApproveProjPrefix = progApproveProjPrefix;
	}

	/**
	 * ProgramApprove class to handle Programs to Approve Programs
	 */
	public ProgramApprove() {

	}
	
	public String getProgApproveEndDate() {
		return progApproveEndDate;
	}

	public void setProgApproveEndDate(String progApproveEndDate) {
		this.progApproveEndDate = progApproveEndDate;
	}

	public String getProgApproveForm() {
		return progApproveForm;
	}

	public void setProgApproveForm(String progApproveForm) {
		this.progApproveForm = progApproveForm;
	}

	public String getProgApproveId() {
		return progApproveId;
	}

	public void setProgApproveId(String progApproveId) {
		this.progApproveId = progApproveId;
	}

	public String[] getProgApproveNames() {
		return progApproveNames;
	}

	public void setProgApproveNames(String[] progApproveNames) {
		this.progApproveNames = progApproveNames;
	}

	public String getProgApproveOfficer() {
		return progApproveOfficer;
	}

	public void setProgApproveOfficer(String progApproveOfficer) {
		this.progApproveOfficer = progApproveOfficer;
	}

	public String getProgApproveOrgAccess() {
		return progApproveOrgAccess;
	}

	public void setProgApproveOrgAccess(String progApproveOrgAccess) {
		this.progApproveOrgAccess = progApproveOrgAccess;
	}

	public String getProgApprovePrOrg() {
		return progApprovePrOrg;
	}

	public void setProgApprovePrOrg(String progApprovePrOrg) {
		this.progApprovePrOrg = progApprovePrOrg;
	}

	public String getProgApproveStartDate() {
		return progApproveStartDate;
	}

	public void setProgApproveStartDate(String progApproveStartDate) {
		this.progApproveStartDate = progApproveStartDate;
	}

	public String getProgApproveStatus() {
		return progApproveStatus;
	}

	public void setProgApproveStatus(String progApproveStatus) {
		this.progApproveStatus = progApproveStatus;
	}


	public ArrayList<ProgramSteps> getProgApproveSteps() {
		return progApproveSteps;
	}

	public void setProgApproveSteps(ArrayList<ProgramSteps> progApproveSteps) {
		this.progApproveSteps = progApproveSteps;
	}

	public String[] getProgApproveAdmin() {
		return progApproveAdmin;
	}

	public void setProgApproveAdmin(String[] progApproveAdmin) {
		this.progApproveAdmin = progApproveAdmin;
	}

	public Hashtable getProgApproveStaff() {
		return progApproveStaff;
	}

	public void setProgApproveStaff(Hashtable progApproveStaff) {
		this.progApproveStaff = progApproveStaff;
	}

	/**
	 * @return the progApproveBaseLetter
	 */
	public String getProgApproveBaseLetter() {
		return progApproveBaseLetter;
	}

	/**
	 * @param progApproveBaseLetter the progApproveBaseLetter to set
	 */
	public void setProgApproveBaseLetter(String progApproveBaseLetter) {
		this.progApproveBaseLetter = progApproveBaseLetter;
	}

	/**
	 * @return the progApprovePostfix
	 */
	public String getProgApprovePostfix() {
		return progApprovePostfix;
	}

	/**
	 * @param progApprovePostfix the progApprovePostfix to set
	 */
	public void setProgApprovePostfix(String progApprovePostfix) {
		this.progApprovePostfix = progApprovePostfix;
	}

	/**
	 * @return the progApprovePrefix
	 */
	public String getProgApprovePrefix() {
		return progApprovePrefix;
	}

	/**
	 * @param progApprovePrefix the progApprovePrefix to set
	 */
	public void setProgApprovePrefix(String progApprovePrefix) {
		this.progApprovePrefix = progApprovePrefix;
	}

	/**
	 * @return
	 */
	public int getCreateOrUpdate() {
		return createOrUpdate;
	}

	/**
	 * @param createOrUpdate
	 */
	public void setCreateOrUpdate(int createOrUpdate) {
		this.createOrUpdate = createOrUpdate;
	}

	/**
	 * @return the progApproveFullId
	 */
	public String getProgApproveFullId() {
		return progApproveFullId;
	}

	/**
	 * @param progApproveFullId the progApproveFullId to set
	 */
	public void setProgApproveFullId(String progApproveFullId) {
		this.progApproveFullId = progApproveFullId;
	}

	/**
	 * @return the progApproveName
	 */
	public String getProgApproveName() {
		return progApproveName;
	}

	/**
	 * @param progApproveName the progApproveName to set
	 */
	public void setProgApproveName(String progApproveName) {
		this.progApproveName = progApproveName;
	}

}
