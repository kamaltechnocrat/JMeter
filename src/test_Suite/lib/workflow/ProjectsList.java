/**
 * 
 */
package test_Suite.lib.workflow;

import java.util.Hashtable;

import org.testng.Assert;

import static watij.finders.SymbolFactory.*;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class ProjectsList {
	
	protected String projectName;
	
	protected String applicantName;
	
	protected String programName;
	
	protected String currentStepName;
	
	protected String projectStatus;
	
	protected String projectNumber;
	
	int rowIndex;
	

	/***************************
	 *		Constructor
	 **************************/
	public ProjectsList() {
		
	}
	
	public ProjectsList(Project proj) {
		
		this.setApplicantName(proj.getOrgFullName());
		this.setProgramName(proj.getProgFullName());
		this.setProjectName(proj.getProjFullName());
		
	}
	
	public boolean initializeProjectList() throws Exception {
		
		try {
			
			IE ie = IEUtil.getActiveIE();
			
			rowIndex = -1;
			
			ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
			
			Hashtable<String, String> hashTable = new Hashtable<String, String>();			
			Hashtable<String, String> hashDropDown = new Hashtable<String, String>();
			
			hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, this.getProgramName());
			hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl, this.getApplicantName());
			hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, this.getProjectName());
			
			hashDropDown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl, IFiltersConst.allProjView);
			
			FiltersUtil.filterListByLabel(hashTable, hashDropDown, false);
			
			rowIndex = TablesUtil.getRowIndex(ITablesConst.projectsTableId, this.getProjectName());
			
			Div tDiv = TablesUtil.tableDiv();

			if(rowIndex > -1)
			{
				this.setProjectNumber(tDiv.body(id,ITablesConst.projectsTableId).row(rowIndex).cell(1).innerText());
				
				this.setCurrentStepName(tDiv.body(id,ITablesConst.projectsTableId).row(rowIndex).cell(5).innerText());
				
				return true;
			}		
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Error: " + e);
		}
		
		return false;
	}
	
	/************************
	 * Getters and Setters
	 ************************/

	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return applicantName;
	}

	/**
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * @return the currentStepName
	 */
	public String getCurrentStepName() {
		return currentStepName;
	}

	/**
	 * @param currentStepName the currentStepName to set
	 */
	public void setCurrentStepName(String currentStepName) {
		this.currentStepName = currentStepName;
	}
	
	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectNumber
	 */
	public String getProjectNumber() {
		return projectNumber;
	}

	/**
	 * @param projectNumber the projectNumber to set
	 */
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	/**
	 * @return the projectStatus
	 */
	public String getProjectStatus() {
		return projectStatus;
	}

	/**
	 * @param projectStatus the projectStatus to set
	 */
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}


}
