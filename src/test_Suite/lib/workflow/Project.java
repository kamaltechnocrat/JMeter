package test_Suite.lib.workflow;

import static watij.finders.SymbolFactory.*;
import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.elements.TableBody;
import watij.elements.TableRow;
import watij.runtime.ie.IE;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.ui.*;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.cases.*;
import test_Suite.lib.cases.Documents;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.eForms.EFormsUtil;

/**
 * @author mshakshouki
 * 
 */
public class Project {

	// ------------------- Class Fields -------------------------------------

	private char projectPortal;
	private String orgLetter;
	private String orgName;
	private String orgFullName;
	private String orgNumber;
	private String projLetter;
	private String projName;
	private String projFullName;
	private String subProjName;
	protected String subProjFullName;
	private String startDate = null;
	private String endDate = null;
	private String dueDate = null;
	protected Integer claimNumber = 0;

	// -- Protected to be used in Sub-class
	protected FundingOpportunity fopp;
	protected String progLetter;
	protected String progFullName;
	protected String progFullIdent;
	protected String progPreFix;
	protected String postfix;
	protected String projPrefix;
	protected String projPostfix;
	protected String currentStep;
	protected String currentStepIdent;
	protected String currentStepName;
	protected boolean newProject;
	protected boolean newOrg;
	protected String currentStepEFormName;
	protected String currentStepEFormIdent;
	protected String projNumber;
	protected String projFullNameWithProjNumber;

	protected String srcProjectFullNameWithNumber;	
	protected String sourceProjectFullName;
	protected boolean importDataFromProject;
	protected String trnsfrToOrgFullName;

	private static final String comment = "Conduct Evaluation";
	private static final String commentAmend = ", Again";
	private static final String score = "5";
	private Boolean retValue;
	static TableBody tBody;

	private static Log log = LogFactory.getLog(Project.class);

	// ------------------- End of Class Fields ------------------------------

	// ------------------- Start Constructors
	// -------------------------------------

	/**
	 * @param fopp
	 * @param pPreFix
	 * @param isNewProject
	 * @param orgStatus
	 * @param orgLetter
	 */
	public Project(FundingOpportunity fopp, String pPreFix, boolean isNewProject,boolean orgStatus, String orgLetter) {
		try {

			this.fopp = fopp;

			this.newProject = isNewProject;

			this.newOrg = orgStatus;

			this.orgLetter = orgLetter;

			this.projPrefix = pPreFix;

			this.progLetter = fopp.getFoppLetter();

			this.progPreFix = fopp.getFoppPreFix();

			this.postfix = fopp.getFoppPostfix();

			this.projPostfix = "";

			this.progFullName = fopp.getFoppFullName();

			this.progFullIdent = fopp.getFoppFullIdent();

			this.startDate = fopp.getStartDate();

			this.endDate = fopp.getEndDate();

			this.dueDate = GeneralUtil.setDayofMonth(2);

			this.orgName = this.progPreFix + this.projPrefix + IGeneralConst.gnrl_OrgName + this.projPostfix;

			this.orgFullName = this.orgLetter + this.orgName;

			this.orgNumber = this.orgLetter + this.projPrefix + IGeneralConst.gnrl_OrgNumber + this.projPostfix;

			this.projName = this.progPreFix + this.projPrefix + IGeneralConst.gnrl_ProjName + this.projPostfix + "-" + this.orgLetter;

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * @param fopp
	 * @param pPreFix
	 * @param isNewProject
	 */
	public Project(FundingOpportunity fopp, String pPreFix, boolean isNewProject){

		try {

			this.fopp = fopp;

			this.newProject = isNewProject;

			this.projPrefix = pPreFix;

			this.progLetter = fopp.getFoppLetter();

			this.progPreFix = fopp.getFoppPreFix();

			this.postfix = fopp.getFoppPostfix();

			this.progFullName = fopp.getFoppFullName();

			this.progFullIdent = fopp.getFoppFullIdent();

			this.startDate = fopp.getStartDate();

			this.endDate = fopp.getEndDate();

			this.dueDate = GeneralUtil.setDayofMonth(2);

			this.projName = this.progPreFix.concat(this.projPrefix).concat(IGeneralConst.gnrl_ProjName).concat(this.postfix);

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);
		}


	}

	/**
	 * @param poProg
	 * @param isNewProj
	 * @param projPostfix
	 */
	public Project(Program poProg, boolean isNewProj, String projPostfix) {

		try {

			this.newProject = isNewProj;

			this.progLetter = poProg.getProgLetter();

			this.progPreFix = poProg.getProgPreFix();

			this.postfix = poProg.getProgPostfix() + projPostfix;

			this.progFullName = poProg.getProgFullName();

			this.progFullIdent = poProg.getProgFullIdent();

			this.startDate = poProg.getStartDate();

			this.endDate = poProg.getEndDate();

			this.dueDate = GeneralUtil.setDayofMonth(2);

			this.orgName = this.progPreFix + IGeneralConst.gnrl_OrgName
					+ poProg.getProgPostfix();

			this.projName = this.progPreFix + IGeneralConst.gnrl_ProjName
					+ this.postfix;

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * @param poProg
	 * @param isNewProj
	 */
	public Project(Program poProg, boolean isNewProj) {

		try {

			this.newProject = isNewProj;

			this.progLetter = poProg.getProgLetter();

			this.progPreFix = poProg.getProgPreFix();

			this.postfix = poProg.getProgPostfix();

			this.progFullName = poProg.getProgFullName();

			this.progFullIdent = poProg.getProgFullIdent();

			this.startDate = poProg.getStartDate();

			this.endDate = poProg.getEndDate();

			this.dueDate = GeneralUtil.setDayofMonth(2);

			this.orgName = this.progPreFix + IGeneralConst.gnrl_OrgName
					+ this.postfix;

			this.projName = this.progPreFix + IGeneralConst.gnrl_ProjName
					+ this.postfix;

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	public Project() {

	}

	// ------------------ End of Constractors --------------------------------

	/**
	 * @throws Exception
	 */
	public void initializeProject() throws Exception {
		try {

			if (this.isNewProject()) {

			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	// ----------------- Public Methods --------------------------------------

	/**
	 * @param stepName
	 * @throws Exception
	 */
	public void initializeStep(String stepName) throws Exception {

		this.setCurrentStepIdent(this.progLetter + this.progPreFix + stepName + this.postfix);

		this.setCurrentStepName(this.getCurrentStepIdent().replace('-', ' '));
	}
	
	/**
	 * @param subProj
	 * @throws Exception
	 */
	public void initializeSubProjectFullName(String subProj) throws Exception {

		this.setSubProjFullName(this.getProjFullName().concat(" - ").concat(subProj));
	}

	/**
	 * @param newOrg
	 * @throws Exception
	 */
	public void createPOProject(boolean newOrg) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		if (newOrg) {			
			
			this.orgLetter = EFormsUtil.createAnyRandomString(5);

			this.orgFullName = this.orgLetter + this.orgName;	
			
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl,this.orgFullName,IFiltersConst.exact);

			ClicksUtil.clickImageByAlt(IClicksConst.addNewApplicant_ImgAlt);

			ie.selectList(id, IApplicantsConst.applicantType_DropDownId)
			.select(IApplicantsConst.applicantType_Organization);

			ie.textField(id, IApplicantsConst.applicantName_FldID).set( orgLetter + this.orgName);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);
		} else {
			this.orgLetter = FiltersUtil.getUsedBaseLetter(ITablesConst.applicantsTableId, IFiltersConst.grantManagement_ApplicantName_Lbl,this.orgName,IFiltersConst.exact);

			this.orgFullName = this.orgLetter + this.orgName;
		}
		
		Div tDiv = TablesUtil.tableDiv();

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, this.orgFullName);

		tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndx).link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

		if (this.isNewProject()) {

			this.projLetter = EFormsUtil.createAnyRandomString(5);

			this.projFullName = this.projLetter + this.projName + "-" + this.orgLetter;
			
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl,this.projFullName,IFiltersConst.exact);

			ClicksUtil.clickImageByAlt(IClicksConst.addNewProject_ImgAlt);

			ie.selectList(name, "submissionApplicant:submissionAppForm:submissionAppProgramMenu_input").select( this.progFullName);

			ie.textField(name, "submissionApplicant:submissionAppForm:poProjectName").set( this.projFullName);

			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);

		} else {
			this.projLetter = FiltersUtil.getUsedBaseLetter( ITablesConst.applicantSubmissionTableId, IFiltersConst.grantManagement_ProjectName_Lbl, this.projName + "-" + this.orgLetter, IFiltersConst.exact);

			this.projFullName = this.projLetter + this.projName + "-" + this.orgLetter;
		}

	}

	/**
	 * @param doSelectFopp
	 * @return boolean
	 * @throws Exception
	 */
	public boolean createNewPOProjectOnly(boolean doSelectFopp) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		if(!FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.orgFullName, IFiltersConst.exact))
		{
			log.error("Could not Filter Applicants List with Applicant Name!");

			return false;
		}

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, this.orgFullName);

		Div tDiv = TablesUtil.tableDiv();
		
		tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndx).link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

		this.projLetter = FiltersUtil.getNewBaseLetter(
				ITablesConst.applicantSubmissionTableId,
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projName + "-" + this.orgLetter, IFiltersConst.exact);

		this.projFullName = this.projLetter + this.projName + "-"
				+ this.orgLetter;

		if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewProject_ImgAlt))
		{
			log.error("Could not click Add new Project by its Alt Img!");
			return false;
		}

		if(doSelectFopp)
		{			
			if(!GeneralUtil.isObjectExistsInList("submissionApplicant:submissionAppForm:submissionAppProgramMenu_input", this.progFullName))
			{
				log.error("FOPP is not available in Dropdown!");
				return false;
			}

			log.info(ie.selectList(name, "submissionApplicant:submissionAppForm:submissionAppProgramMenu_input").option(0).toString());

			ie.selectList(name, "submissionApplicant:submissionAppForm:submissionAppProgramMenu_input").select(
					this.progFullName);			
		}

		ie.textField(name, "submissionApplicant:submissionAppForm:poProjectName").set(
				this.projFullName);

		if(!ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn))
		{
			log.error("could not click Save & Back Button!");
			return false;
		}

		return true;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean canCreatePOProjectOnly() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				this.orgFullName);

		if(rowIndx < 0)
		{
			log.error("Could not find Applicant in PO List");
			return false;
		}

		Div tDiv = TablesUtil.tableDiv();
		
		tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndx).link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

		if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewProject_ImgAlt))
		{
			log.error("Could not click Add new Project by its Alt Img!");
			return false;
		}

		if(!GeneralUtil.isObjectExistsInList("/submissionAppProgramMenu/", this.progFullName))
		{
			log.error("FOPP is not available in Dropdown!");
			return false;
		}

		ie.selectList(name, "/submissionAppProgramMenu/").select(
				this.progFullName);

		ie.textField(name, "/submissionAppForm:poProjectName/").set(
				this.projFullName);

		if(!ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn))
		{
			log.error("could not click Save & Back Button!");
			return false;
		}

		return true;
	}

	/**
	 * @param newOrg
	 * @return boolean
	 * @throws Exception
	 */
	public boolean createPOProjectOnly(boolean newOrg) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		if (!newOrg) {
			this.orgLetter = FiltersUtil.getUsedBaseLetter(ITablesConst.applicantsTableId, IFiltersConst.grantManagement_ApplicantName_Lbl,this.orgName,IFiltersConst.exact);

			this.orgFullName = this.orgLetter + this.orgName;
		}

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				this.orgFullName);
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndx).link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

		this.projLetter = FiltersUtil.getNewBaseLetter(
				ITablesConst.applicantSubmissionTableId,
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projName + "-" + this.orgLetter, IFiltersConst.exact);

		this.projFullName = this.projLetter + this.projName + "-"
				+ this.orgLetter;

		if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewProject_ImgAlt))
		{
			log.error("Could not click Add new Project by its Alt Img!");
			return false;
		}

		ie.selectList(name, "/submissionAppProgramMenu/").select(
				this.progFullName);

		ie.textField(name, "/submissionAppForm:poProjectName/").set(
				this.projFullName);

		if(!ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn))
		{
			log.error("could not click Save & Back Button!");
			return false;
		}

		return true;

	}

	/*******************************************
	 * These Methods to be used with 
	 * new setting for BF from FOPP
	 * Useful if data exists
	 * @return boolean
	 * @throws Exception
	 *******************************************/
	public boolean newCreateNewOrg() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.orgFullName, "Exact");

		if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewApplicant_ImgAlt))
		{
			log.error("Could not click New Applicant Image by its Alt!");
			return false;
		}

		editOrg();

		List<String> lst = GeneralUtil.checkForErrorMessages();

		if(null != lst && !lst.isEmpty()) {		

			ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);

			return true;
		}		

		ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);	

		return false;
	}

	/**
	 * @return boolean
	 * @throws Exception
	 */
	public boolean openPOOrgDetails() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.orgFullName, "Exact");

		if(this.isNewOrg()) {

			if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewApplicant_ImgAlt))
			{
				log.error("Could not click New Applicant Image by its Alt!");
				return false;
			}

			return true;			
		} else if(GeneralUtil.isLinkExistsByTxt(this.orgFullName)) {
			ClicksUtil.clickLinks(this.orgFullName);

			return true;
		}		

		return false;
	}

	/**
	 * @throws Exception
	 */
	public void editOrg() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.selectList(id, IApplicantsConst.applicantType_DropDownId).select(IApplicantsConst.applicantType_Organization);

		GeneralUtil.takeANap(1.5);

		ie.textField(id, IApplicantsConst.applicantName_FldID).set(this.orgFullName);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	/**
	 * @return boolean
	 * @throws Exception
	 */
	public boolean openApplicantSubList() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.orgFullName, "Exact");

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, this.orgFullName);

		Div tDiv = TablesUtil.tableDiv();

		if(rowIndx >= 0) {

			tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndx).link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

			return true;
		}		
		return false;
	}

	/**
	 * @throws Exception
	 */
	public boolean newCreateNewPOProject() throws Exception {

		boolean retV = true;

		Assert.assertTrue(openApplicantSubList(), "FAIL: could not open Submission List for Applicant: " + this.orgFullName);

//		this.projLetter = FiltersUtil.getNewBaseLetter(
//				ITablesConst.applicantSubmissionTableId,
//				IFiltersConst.grantManagement_ProjectName_Lbl, this.projName, IFiltersConst.exact);
		
		this.projLetter = EFormsUtil.createAnyRandomString(5);  

		this.projFullName = this.projLetter + this.projName;

		if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewProject_ImgAlt))
		{
			log.error("Could not click Add new Project by its Alt Img!");
			return false;
		}

		editProject();

		GeneralUtil.takeANap(1.500);

		if(this.isImportDataFromProject()) {

			if(!selectImportDataFromProject()) {
				retV = false;
			}
		}

		if(!ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn))
		{
			log.error("could not click Save & Back Button!");
			return false;
		}

		if(!this.setTheProjectNumber())
		{
			log.error("Failed to get the Project Number");
			return false;
		}

		return retV;
	}

	/**
	 * @return boolean
	 * @throws Exception
	 */
	public boolean setTheProjectNumber() throws Exception {

		int indx = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.applicantSubmissionTableId, this.getProjFullName());

		if(indx < -1)
		{
			log.error("Could not find project in List");
			return false;
		}

		String retVal = TablesUtil.getCellContent(ITablesConst.applicantSubmissionTableId, indx, 1, 1);

		if(retVal.trim() == null || retVal.trim().equals(""))
		{
			log.error("could not find Project Number in List!");
			return false;
		}

		this.setProjNumber(retVal.trim());

		this.setProjFullNameWithProjNumber(this.getProjFullName().concat(" (").concat(this.getProjNumber()).concat(")"));

		return true;

	}

	/**
	 * @throws Exception
	 */
	public void editProject() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.selectList(name, "/submissionAppProgramMenu/").select(this.progFullName);

		GeneralUtil.takeANap(1.500);

		ie.textField(name, "/submissionAppForm:poProjectName/").set(this.projFullName);		
	}

	/**
	 * @return boolean
	 * @throws Exception
	 */
	public boolean selectImportDataFromProject() throws Exception {

		IE ie = IEUtil.getActiveIE();

		GeneralUtil.takeANap(2.000);

		if(!ie.selectList(id,"submissionApplicant:submissionAppForm:ImportDataFromProject_input").exists()) {	

			log.error("Import Data from Project Dropdown does not exists");
			return false;
		}

		List<String> lst = ie.selectList(id,"submissionApplicant:submissionAppForm:ImportDataFromProject_input").getAllContents();

		for (String string : lst) {

			if(string.equals(this.getSrcProjectFullNameWithNumber())) {

				ie.selectList(id,"submissionApplicant:submissionAppForm:ImportDataFromProject_input").select(this.getSrcProjectFullNameWithNumber());

				return true;
			}			
		}
		log.error("The Project is not in the Dropdown");
		return false;		
	}

	//------------ the end of methods for BF FOPP Projects -----------------------

	/**
	 * @param newOrg
	 * @return boolean
	 * @throws Exception
	 */
	public boolean createOrgFullName(boolean newOrg) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		if (newOrg) {

			this.orgLetter =  EFormsUtil.createAnyRandomString(5);			

			this.orgFullName = this.orgLetter + this.orgName;

			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.orgFullName, "Exact");

			if(!ClicksUtil.clickImageByAlt(IClicksConst.addNewApplicant_ImgAlt))
			{
				log.error("Could not click New Applicant Image by its Alt!");
				return false;
			}

			ie.selectList(id, IApplicantsConst.applicantType_DropDownId)
			.select(IApplicantsConst.applicantType_Organization);

			ie.textField(id, IApplicantsConst.applicantName_FldID).set(
					orgLetter + this.orgName);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);
		} else {
			orgLetter = FiltersUtil.getUsedBaseLetter(ITablesConst.applicantsTableId, IFiltersConst.grantManagement_ApplicantName_Lbl, this.orgName,IFiltersConst.exact);

			this.orgFullName = this.orgLetter + this.orgName;
		}

		return true;

	}

	/**
	 * @param submit
	 * @throws Exception
	 */
	public void submitProject(boolean submit) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		int rowIndx = TablesUtil.getRowIndex(
				ITablesConst.applicantSubmissionTableId, this.projFullName);

		Div tDiv = TablesUtil.tableDiv();
		
		this.setCurrentStepName(tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndx).cell(3).innerText());

		tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndx).image(0).click();

		if (this.progPreFix == "-Sanity-")
			GeneralUtil.fillGrid();
		else
			GeneralUtil.SetAnyText();

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		if (submit) {
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

		ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);

	}

	/**
	 * @param stepName
	 * @param submit
	 * @return  boolean
	 * @throws Exception
	 */
	public boolean submitFromApplicantSubList(String stepName, boolean submit)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		int rowIndx = -1;

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName,IFiltersConst.exact);

		rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				this.orgFullName);

		Div tDiv = TablesUtil.tableDiv();
		
		if (rowIndx > -1) {
			tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndx).link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

			String subProject = "";

			if (stepName.contains("Initial-Claim")) {
				subProject = " - Claim " + this.getClaimNumber().toString();
			}

			subProject = this.projFullName.concat(subProject);

			initializeStep(stepName);

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(
					IFiltersConst.grantManagement_ProjectName_Lbl,
					this.projFullName);
			hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
					this.progFullName);
			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					this.getCurrentStepName());

			FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

			String[] lstTable = { this.getCurrentStepName(), this.progFullName,
					subProject };

			rowIndx = -1;
			
			tDiv = TablesUtil.tableDiv();

			rowIndx = TablesUtil.findInTable(
					ITablesConst.applicantSubmissionTableId, lstTable);
			
			if (rowIndx > -1) {
				tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndx).image(0).click();

				if (ie.textField(0).exists()) {
					GeneralUtil.setTextByIndex(0, "1000000");

					ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

					if (submit) {
						ClicksUtil.clickButtons(IClicksConst.submitBtn);
					}

					ClicksUtil
					.clickLinks(IClicksConst.backToApplicantSubListLnk);

					retValue = true;
				}

			}

		}

		return retValue;

	}

	/**
	 * @param stepName
	 * @param enteredValue
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitFromMyAssignedSubListNew(String stepName, String enteredValue,boolean submit) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		this.initializeStep(stepName);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		String subProject = "";

		if (this.getClaimNumber() > 0) {
			subProject = " - Claim " + this.getClaimNumber().toString();
		}

		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		hashTable.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.getProjFullName()+ subProject);

		hashTable.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.getOrgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		String[] lstSearch = { this.getProjFullName() + subProject, this.getCurrentStepName()};

		int rowIndx = TablesUtil.findInTable(ITablesConst.fundingOpp_myAssignedSubmissionsTableId,lstSearch);

		Div tDiv = TablesUtil.tableDiv();
		
		if (rowIndx > -1) {

			tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndx).image(0).click();

			GeneralUtil.AppendToText(enteredValue);

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);

			if (submit) {
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

			return true;

		}

		return false;

	}

	/**
	 * @param projFullName
	 * @param stepType
	 * @param stepName
	 * @param submit
	 * @param enteredValue
	 * @param proj
	 * @throws Exception
	 */
	public void submitFromMyAssignedSubList(String projFullName,
			String stepType, String stepName, boolean submit,
			String enteredValue, Project proj) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		proj.initializeStep(stepName);

		String subProject = "";

		if (stepType == "Initial-Claim") {
			subProject = " - Claim " + this.getClaimNumber().toString();
		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		//Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable
		.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				projFullName + subProject);
		hashTable
		.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,proj.getCurrentStepName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		String[] lstSearch = { projFullName + subProject, proj.getCurrentStepName()};

		int rowIndx = TablesUtil
				.findInTable(
						ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
						lstSearch);
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndx).image(0).click();

		GeneralUtil.AppendToText(enteredValue);

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		if (submit) {
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

	}

	/**
	 * @param officers
	 * @return boolean
	 * @throws Exception
	 */
	public boolean assignOfficersBySteps(Hashtable<String,String[]> officers) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Enumeration<String> keyStr;
		Enumeration<String[]> elemStr;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,this.projFullName);

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,this.orgFullName);

		FiltersUtil.filterListByLabel(hashTable,null,false);

		ClicksUtil.clickLinks(this.projFullName);

		ClicksUtil.clickLinks(IClicksConst.projectOfficerLnk);

		if(null != officers && !officers.isEmpty()) {	

			keyStr = officers.keys();
			elemStr = officers.elements();

			while(keyStr.hasMoreElements()) {

				String stepName = keyStr.nextElement();
				String[] grpOfficers = elemStr.nextElement();

				GeneralUtil.takeANap(1.500);

				if(ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).exists()) {

					ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).clear();

					GeneralUtil.takeANap(2.000);

					ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).set();

					GeneralUtil.takeANap(2.000);

					ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).clear();

					GeneralUtil.takeANap(2.000);
				}
				else {

					return false;
				}

				int rowIndex = TablesUtil.getRowIndex(ITablesConst.projectOfficerAssignmentTableId, stepName);

				Div tDiv = TablesUtil.tableDiv();
				
				if(rowIndex > -1) {

					TableRow row = tDiv.body(id,ITablesConst.projectOfficerAssignmentTableId).row(rowIndex);

					row.checkbox(0).set();
					GeneralUtil.takeANap(2.000);

					if(grpOfficers.length == 1) {

						ClicksUtil.clickButtons(IClicksConst.assignGroupBtn);
						GeneralUtil.takeANap(2.000);
					}
					else {
						ie.selectList(id,IProjectsConst.projectOfficers_AssignUser_Dropdown_Id).select(grpOfficers[1]);

						ClicksUtil.clickButtons(IClicksConst.assignUserBtn);
						GeneralUtil.takeANap(2.000);
					}
				}

			}

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			return true;
		}		

		return false;
	}

	/**
	 * @param officers
	 * @return boolean
	 * @throws Exception
	 */
	public boolean assignOfficers(String[][] officers) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk))
		{
			log.error("Could not assign Project Officers!");
			return false;
		}		


		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(this.projFullName);

		ClicksUtil.clickLinks(IClicksConst.projectOfficerLnk);

		if (officers.length > 1) {

			for (int i = 0; i < officers.length; i++) {

				doAssignOfficers(officers[i]);
			}			
		}

		else {
			doAssignAllOfficers(officers[0]);

		}

		return true;
	}

	/**
	 * @param officer
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doAssignAllOfficers(String[] officer) throws Exception {

		IE ie = IEUtil.getActiveIE();

		GeneralUtil.takeANap(1.500);

		if(ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).exists()) {

			ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).set();

			GeneralUtil.takeANap(2.500);

			if(officer.length > 1) {

				if(ie.selectList(id,IProjectsConst.projectOfficers_AssignUser_Dropdown_Id).exists()) {

					ie.selectList(id,IProjectsConst.projectOfficers_AssignUser_Dropdown_Id).select(officer[1]);

					ClicksUtil.clickButtons(IClicksConst.assignUserBtn);

					GeneralUtil.takeANap(2.000);

					ClicksUtil.clickButtons(IClicksConst.saveBtn);
				}
				else {
					return false;
				}

			}
			else {
				ClicksUtil.clickButtons(IClicksConst.assignGroupBtn);

				GeneralUtil.takeANap(2.000);

				ClicksUtil.clickButtons(IClicksConst.saveBtn);				
			}
		}
		else {

			return false;
		}		

		return true;
	}

	/**
	 * @param officer
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doAssignOfficers(String[] officer) throws Exception {

		IE ie = IEUtil.getActiveIE();

		GeneralUtil.takeANap(1.500);

		if(ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).exists()) {

			ie.checkbox(id,IProjectsConst.projectOfficers_SelectAll_Checkbox_Id).clear();

			GeneralUtil.takeANap(2.000);
		}
		else {

			return false;
		}
		
		Div tDiv = TablesUtil.tableDiv();

		for (TableRow row : tDiv.body(id, ITablesConst.projectOfficerAssignmentTableId).rows()) {

			if(row.cell(3).innerText().equalsIgnoreCase(officer[0])) {

				row.checkbox(0).set();

				GeneralUtil.takeANap(2.000);

				if(ie.selectList(id,IProjectsConst.projectOfficers_AssignUser_Dropdown_Id).exists()) {

					ie.selectList(id,IProjectsConst.projectOfficers_AssignUser_Dropdown_Id).select(officer[1]);

					ClicksUtil.clickButtons(IClicksConst.assignUserBtn);

					GeneralUtil.takeANap(2.000);
				}
				else {
					return false;
				}
			}

		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		return true;
	}

	/**
	 * @return boolean
	 * @throws Exception
	 */
	public boolean openProjectDetailPage() throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(GeneralUtil.isLinkExistsByTxt(this.projFullName)) {

			ClicksUtil.clickLinks(this.projFullName);
			return true;
		}	
		return false;
	}

	/**
	 * @param newName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeProjectName(String newName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(id,"caseTabbedForm:editCaseTabbedPane:poProjectName").set(newName);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.5);
		
		if(GeneralUtil.findTextInTextFieldById("caseTabbedForm:editCaseTabbedPane:poProjectName", newName)) {
			return true;
		}

		return false;
	}

	/**
	 * @param newNumber
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeProjectNumber(String newNumber) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(id,"caseTabbedForm:editCaseTabbedPane:caseNumber").set(newNumber);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.5);

		if(GeneralUtil.findTextInTextFieldById("caseTabbedForm:editCaseTabbedPane:caseNumber", newNumber)) {
			return true;
		}

		return false;
	}

	/**
	 * @param newDate
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeProjectStartDate(String newDate) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(id,"caseTabbedForm:editCaseTabbedPane:caseStartDate_input").set(newDate);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.5);

		if(GeneralUtil.findTextInTextFieldById("caseTabbedForm:editCaseTabbedPane:caseStartDate_input", newDate)) {
			return true;
		}

		return false;
	}

	/**
	 * @param newDate
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeProjectEndDate(String newDate) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(id,"caseTabbedForm:editCaseTabbedPane:caseEndDate_input").set(newDate);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.5);

		if(GeneralUtil.findTextInTextFieldById("caseTabbedForm:editCaseTabbedPane:caseEndDate_input", newDate)) {
			return true;
		}

		return false;
	}

	/**
	 * @param newOrg
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeProjectOrg(String newOrg) throws Exception {
		
		GeneralUtil.selectFullStringInDropdownList("caseTabbedForm:editCaseTabbedPane:projectOrganization_input", newOrg);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.5);

		if(GeneralUtil.getSelectedItemValueInDropdwonById("caseTabbedForm:editCaseTabbedPane:projectOrganization_input").equals(newOrg)) {

			return true;
		}

		return false;
	}

	/**
	 * @param action
	 * @return boolean
	 * @throws Exception
	 */
	public boolean closeOrOpenProject(String action) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(!TablesUtil.findInTable(ITablesConst.fundingOpp_IntakeTableId, this.projFullName))
		{
			log.error("Project not Listed In InTake InBasket");
			return false;
		}

		ClicksUtil.clickLinks(this.projFullName);

		ClicksUtil.clickLinks(IClicksConst.projectDetailsLnk);

		if(!GeneralUtil.isLinkExistsByTxt(action))
		{
			log.error("The Action is not Available!");
			return false;
		}

		ClicksUtil.clickLinks(action);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return true;
	}

	/**
	 * @param action
	 * @return boolean
	 * @throws Exception
	 */
	public boolean closeOrOpenProject_dataArchive(String action) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(!TablesUtil.findInTable(ITablesConst.dataArchive_SubmissionsTable_Id, this.projFullName))
		{
			log.error("Project not Listed In Project List");
			return false;
		}

		ClicksUtil.clickLinks(this.projFullName);

		ClicksUtil.clickLinks(IClicksConst.projectDetailsLnk);

		if(!GeneralUtil.isLinkExistsByTxt(action))
		{
			log.error("The Action is not Available!");
			return false;
		}

		ClicksUtil.clickLinks(action);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return true;
	}

		
	/**
	 * @param proj
	 * @param subStatus
	 * @return
	 * @throws Exception
	 */
	public boolean test_ReadyForArchive_ReadOnlyProperty(Project proj,String subStatus) throws Exception{

		FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, subStatus,
				IGeneralConst.da_projectName_lbl, proj.getProjFullName(), IGeneralConst.da_foppIdentifier_dropDown_value);

		if(!TablesUtil.findInTable(ITablesConst.da_ProjTableList_Id, this.projFullName))
		{
			log.error("Project not Listed In Project List");

			return false;
		}


		ClicksUtil.toggleCheckBox(1, true);

		if(GeneralUtil.isImageExistsByID(IClicksConst.processArchive_Id)){

			log.error("Process Archive Image should either be disabled or should not be present");

			return false;

		}

		if(GeneralUtil.isImageExistsByID(IClicksConst.changeToClosed_Id)){

			log.error("Change to Closed Image should either be disabled or should not be present");

			return false;

		}

		if(GeneralUtil.isImageExistsByID(IClicksConst.cancelArchive_Id)){

			log.error("Cancel Archive Image should either be disabled or should not be present");

			return false;
		}
		return true;
	}

	
	/**
	 * @param proj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInBasket(String proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.dataArchive_InBasket_SubmissionsList_Table_Id, proj))
		{
			log.error("Project should not be Listed in In Basket List");
			return false;
		}

		return true;
	}
	/**
	 * @param proj
	 * @param subVersion
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInAmendmentLink(Project proj,String subVersion) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAmendmentsLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		hashTableDropdown.put(IFiltersConst.grantManagement_SubmissionVersion_Lbl,
				subVersion);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.da_Amendments_SubmissionsList_Table_Id, this.projFullName))
		{
			log.error("Project should not be Listed in Amendments List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInProjectList(String proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.dataArchive_SubmissionsTable_Id, proj))
		{
			log.error("Project should not be Listed In Project List");
			return false;
		}

		return true;
	}
	

	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProject_ListedInBulkEvaluationList(Project proj) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IGeneralConst.bulkEvaluation);

		ClicksUtil.clickLinksById(ITablesConst.bulkEvalStepsView);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(!TablesUtil.findInTable(ITablesConst.bulkEvaluation_ID, this.projFullName))
		{
			log.error("Project should be Listed In Bulk Evaluation List");
			return false;
		}

		return true;
	}

	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProject_ListedInBulkEvaluationList_pa(Project proj) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IGeneralConst.bulkEvaluation);

		ClicksUtil.clickLinksById(ITablesConst.bulkEvalStepsView);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(!TablesUtil.findInTable(ITablesConst.bulkEvaluation_ID, this.projFullName))
		{
			log.error("Project should be Listed In Bulk Evaluation List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProject_notListedInBulkEvaluationList(Project proj) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IGeneralConst.bulkEvaluation);

		ClicksUtil.clickLinksById(ITablesConst.bulkEvalStepsView);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.bulkEvaluation_ID, this.projFullName))
		{
			log.error("Project should not be Listed In Bulk Evaluation List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProject_notListedInBulkEvaluationList_pa(Project proj) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IGeneralConst.bulkEvaluation);
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_FundingOppName_Lbl, proj.getProgFullName(), "Starts With");

		if (!ClicksUtil.clickLinksById(proj.getCurrentStepName())) {
			
			return true;
		}
		
			hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
					proj.getProjFullName());

			FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);
			
		if(TablesUtil.findInTable(ITablesConst.bulkEvaluation_ID, this.projFullName))
		{
			log.error("Project should not be Listed In Bulk Evaluation List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInEvaluateProjectsList(Project proj) throws Exception{

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openEvaluateProjectsLnk);

		ie.selectList(id, IProjectsConst.poProject_FundingOppName_DropdownId).select("A PEF PA FOPP Pushback QH");

		if(TablesUtil.findInTable(ITablesConst.evaluateProjectsTable_Id, this.projFullName))
		{
			log.error("Project should not be Listed In Evaluate Projects List");
			return false;
		}

		return true;
	}

	/**
	 * @param foundProj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInMyProjectSubmissionsList(String proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.da_MyProjectSubmissionsList_Table_Id, proj))
		{
			log.error("Project should not be Listed In My Project Submissions List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInMyAssignedSubmissionsList(Project proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.myAssignedSubmissions_Table_Id, this.projFullName))
		{
			log.error("Project should not be Listed In Assigned Submissions List");
			return false;
		}
		return true;
	}

	/**
	 * @param proj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInEvaluateSubmissionsList(String proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.evaluateSubmissions_Table_ID, proj))
		{
			log.error("Project should not be Listed In Evaluate Submissions List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @param subStatus
	 * @param assignmentType
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInAssignEvaluatorLink(String proj,String subStatus, String assignmentType) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		hashTableDropdown.put(IFiltersConst.grantManagement_assignmentType_Lbl,
				assignmentType);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.assignEvaluator_Table_ID, proj))
		{
			log.error("Project should not be Listed In Assign Evaluator List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInAwardLink(String proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.assignEvaluator_Table_ID, proj))
		{
			log.error("Project should not be Listed In Awards List");
			return false;
		}

		return true;
	}

	/**
	 * @param proj
	 * @param subStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectInMyAssignedSubmissionsLink(String proj,String subStatus) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.myAssignedSubmissions_Table_Id, proj))
		{
			log.error("Project should not be Listed In My Assigned Submissions List");
			return false;
		}

		return true;
	}

	/**
	 * @param proj
	 * @param applicantName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterapplicantIn_ApplicantList(Project proj,String applicantName) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.applicants), "Fail: Couldn't click Applicants link");

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				applicantName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(!TablesUtil.findInTable(ITablesConst.dataArchive_applicantSubmissionsList_Table_Id, applicantName))
		{
			log.error("Failed to find Applicant in the List");
			return false;
		}

		Assert.assertTrue(ClicksUtil.clickImage(IClicksConst.imageIcon_src), "Fail: Couldnt click Applicant Submission List image");

		return true;

	}

	/**
	 * @param foProj
	 * @param subStatus
	 * @param submissionVersion
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectIn_ApplicantSubmissionsList(String foProj,String subStatus, String submissionVersion) throws Exception{	

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				foProj);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				subStatus);

		hashTableDropdown.put(IFiltersConst.grantManagement_SubmissionVersion_Lbl,
				submissionVersion);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(TablesUtil.findInTable(ITablesConst.applicantSubmissions_Table_Id, foProj))
		{
			log.error("Project should not be Listed In Applicant Submissions List");
			return false;
		}

		return true;

	}

	/**
	 * @param proj
	 * @param projStatus
	 * @param fopp
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectIn_FrontOfficeProjectsList(String proj, String projStatus, String fopp) throws Exception{	

		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);

		FiltersUtil.filterFOListByDiv(IFiltersConst.fo_Submissions_ProjectStatus_Lbl, "", projStatus);

		FiltersUtil.filterFOListByDiv(IFiltersConst.fo_Projects_FundingOppName_Lbl, "",fopp);

		if(TablesUtil.findInTable(ITablesConst.foProjects_Table_ID, proj))
		{
			log.error("Project should not be Listed In FO Projects List");
			return false;
		}

		return true;
	}

	/**
	 * @param proj
	 * @param projStatus
	 * @param version
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProjectIn_FrontOfficeSubmissionsList(String proj,String projStatus, String version) throws Exception{	

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		Assert.assertTrue(FiltersUtil.filterFOSubListByLabel_New(IFiltersConst.fo_Submissions_ProjectName_Lbl, "", proj));

		FiltersUtil.filterFOSubListByLabel(IFiltersConst.fo_Submissions_ProjectStatus_Lbl, "",projStatus);

		FiltersUtil.filterFOSubListByLabel(IFiltersConst.fo_Submissions_SubmissionVersion_Lbl, "",version );

		if(TablesUtil.findInTable(ITablesConst.foSubmissions_TableId, proj))
		{
			log.error("Project should not be Listed In FO Submissions List");
			return false;
		}
		return true;
	}

	/**
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean assignAllAvailableEvaluator(String stepName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		int rowIndex = -1;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		String subProject = "";

		if (this.claimNumber > 0) {

			subProject = " - Claim " + this.claimNumber.toString();
		}

		initializeStep(stepName);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, this.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, this.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, this.projFullName + subProject);

		ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk);

		FiltersUtil.filterListByLabel(hashTable,null,false);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.fundingOpp_AssignEvaluatorsTableId, this.getCurrentStepName());

		String errMsg = "Could not find the Step in Assign Evaluator List!";

		Div tDiv = TablesUtil.tableDiv();
		
		if(rowIndex > -1){

			tDiv.body(id, ITablesConst.fundingOpp_AssignEvaluatorsTableId).row(rowIndex).link(title, "Assign Evaluators").click();

			if(ie.selectList(id, IFoppConst.m2mAvailStaff_listId).getAllContents().size() > 0 || ie.selectList(id,IFoppConst.m2mSelectStaff_listId).getAllContents().size() > 0) {

				ClicksUtil.clickButtons(IClicksConst.m2mAddAllBtn);

				GeneralUtil.takeANap(0.500);

				ClicksUtil.clickButtons(IClicksConst.saveBtn);	

				Assert.assertFalse(GeneralUtil.FindTextOnPage("An Unexpected Error has Occurred"), "FAIL: An Exception has occured ");

				return true;
			}

			errMsg = "Both M2M are Empty!";			
		}		

		log.error(errMsg);

		return false;
	}

	/**
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean assignAllAvailableEvaluator_Claim1(String stepName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		int rowIndex = -1;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		initializeStep(stepName);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, this.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, this.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, this.projFullName + " - Claim 1");

		ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk);

		FiltersUtil.filterListByLabel(hashTable,null,false);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.fundingOpp_AssignEvaluatorsTableId, this.getCurrentStepName());

		String errMsg = "Could not find the Step in Assign Evaluator List!";

		Div tDiv = TablesUtil.tableDiv();

		if(rowIndex > -1){

			tDiv.body(id, ITablesConst.fundingOpp_AssignEvaluatorsTableId).row(rowIndex).link(title, "Assign Evaluators").click();
			
			GeneralUtil.takeANap(1.0);

			if(ie.selectList(id, "/availableStaff/").getAllContents().size() > 0 || ie.selectList(id,"/selectedStaff/").getAllContents().size() > 0) {

				ClicksUtil.clickButtons(IClicksConst.m2mAddAllBtn);

				GeneralUtil.takeANap(0.500);

				ClicksUtil.clickButtons(IClicksConst.saveBtn);	

				Assert.assertFalse(GeneralUtil.FindTextOnPage("An Unexpected Error has Occurred"), "FAIL: An Exception has occured ");

				return true;
			}

			errMsg = "Both M2M are Empty!";			
		}		

		log.error(errMsg);

		return false;
	}

	/**
	 * @param evaluationParams
	 * @return boolean
	 * @throws Exception
	 */
	public boolean assignEvaluators(String[] evaluationParams) throws Exception {

		int rowIndex = -1;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		if (!ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk))
		{
			log.error("Could not assign step staff!");
			return false;
		}

		String subProject = "";

		if (this.claimNumber > 0) {
			subProject = " - Claim " + this.claimNumber.toString();
		}

		initializeStep(evaluationParams[0]);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName + subProject);
		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		if(!FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false))
		{
			log.error("Could not filter evaluation List!");
			return false;

		}

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_AssignEvaluatorsTableId, this
				.getCurrentStepName());

		if(rowIndex < 0)
		{
			log.error("Could not find evaluation step in List!");
			return false;
		}
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.fundingOpp_AssignEvaluatorsTableId).row(rowIndex).link(title, "Assign Evaluators").click();

		for (int i = 1; i < evaluationParams.length; i++) {
			
			log.warn(i);

			if(!GeneralUtil.selectfromM2M_New(IProjectsConst.projectAssignEval_AvailableEvals_M2MId, IProjectsConst.projectAssignEval_SelectedEvals_M2MId, evaluationParams[i]))
			{
				log.error("Could not select from the available list! ".concat(evaluationParams[i]));
				return false;
			}

			//ie.selectList(id,IProjectsConst.projectAssignEval_AvailableEvals_M2MId).select(evaluationParams[i]);
			//ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);

		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.evaluatorAssignmentListBtn);

		return true;
	}

	/**
	 * @param fopp
	 * @param docs
	 * @return boolean
	 * @throws Exception
	 */
	public boolean filterProject_ArchiveLog(String fopp, Documents docs) throws Exception{

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		Assert.assertTrue(ClicksUtil.clickLinks(IGeneralConst.archiveLog), "Fail: Couldnot click Archive Log link");

		//ClicksUtil.clickLinksById(ITablesConst.bulkEvalStepsView_pa);

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				fopp);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if(!TablesUtil.findInTable(ITablesConst.archiveLog_TableID, fopp))
		{
			log.error("Funding Opportunity should be Listed In Archive Log List");
			return false;
		}

		if(!ClicksUtil.clickLinksById(IGeneralConst.downloadArchiveLog_Id)){

			log.error("Couldnot click the Archive File name link");

			return false;
		}

		if(!DocumentsUtil.saveDownloadDocument(docs)){

			log.error("Couldnot save the document");
			return false;
		}

		return true;

	}

	/**
	 * @param stepName
	 * @param submit
	 * @param reviewVal
	 * @param isNotUsed
	 * @param subViewStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reviewSubmission(String stepName, boolean submit,
			String reviewVal, boolean isNotUsed, String subViewStatus) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (this.claimNumber > 0) {
			subProject = " - Claim " + this.claimNumber.toString();
		}

		initializeStep(stepName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName + subProject);
		hashTable
		.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);
		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		hashTableDropdown.put(IFiltersConst.grantManagement_SubmissionStatus_Lbl, subViewStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,isNotUsed);

		Div tDiv = TablesUtil.tableDiv();
		
		//Make Sure the Submission is in the List

		if(tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").click();

			//Make sure is not a readOnly Mode
			if(ie.textField(name, IProjectsConst.reviewSubCommentLongText).readOnly()) {

				return false;
			}

			ie.textField(name, IProjectsConst.reviewSubDatePicker).set(GeneralUtil.getTodayDate());
			ie.textField(name, IProjectsConst.reviewSubCommentLongText).set(comment);
			ie.textField(name, IProjectsConst.reviewSubScoreTextbox).set(score);
			ie.selectList(name, IProjectsConst.reviewSubRevDropdown).select(reviewVal);
			
					GeneralUtil.takeANap(1.5);
			

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (submit) {

				//Make sure it's submitable
				if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

					return false;

				}
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			return true;			
		}

		return false;
	}
	/**
	 * @param stepName
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reviewAmendedSubmission(String stepName, boolean submit)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (this.claimNumber > 0) {
			subProject = " - Claim " + this.claimNumber.toString();
		}

		initializeStep(stepName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName + subProject);
		hashTable
		.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);
		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		hashTableDropdown.put(IFiltersConst.grantManagement_SubmissionStatus_Lbl, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);
		
		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").click();

			if(ie.textField(name, IProjectsConst.reviewSubCommentLongText).readOnly()) {

				return false;

			}

			ie.textField(name, IProjectsConst.reviewSubDatePicker).set(GeneralUtil.getTodayDate());
			ie.textField(name, IProjectsConst.reviewSubCommentLongText).append(commentAmend);
			ie.textField(name, IProjectsConst.reviewSubScoreTextbox).set(score);
			ie.selectList(name, IProjectsConst.reviewSubRevDropdown).select("Yes");

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (submit) {

				if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

					return false;

				}
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			return true;			
		}

		return false;
	}

	/**
	 * @param stepName
	 * @param submit
	 * @param stepStatus
	 * @param hasSummary
	 * @param isNotUsed
	 * @return boolean
	 * @throws Exception
	 */
	public boolean approveSubmission(String stepName, boolean submit,
			String stepStatus, boolean hasSummary, boolean isNotUsed) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (this.claimNumber > 0) {
			subProject = " - Claim " + this.claimNumber.toString();
		}

		initializeStep(stepName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName + subProject);
		hashTable
		.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);
		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());
		hashTableDropdown
		.put(
				IFiltersConst.grantManagement_SubmissionStatus_Lbl,
				stepStatus);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, isNotUsed);

		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").click();
			
			GeneralUtil.takeANap(1.0);
			
			
			//New Prime Faces, changes the id when Approval Limit used
			
			if(ie.textField(id,"g3-form:eFormFieldList:3:textArea_Below").exists())
			{
				if(ie.textField(id,"g3-form:eFormFieldList:3:textArea_Below").readOnly())
				{
					return false;
				}
				else
				{
					ie.textField(id,"g3-form:eFormFieldList:3:textArea_Below").set(comment);
					ie.textField(id, "g3-form:eFormFieldList:2:datePicker_input").set(GeneralUtil.getTodayDate());
					ie.selectList(id, "g3-form:eFormFieldList:1:numericDropdown_input").select("Approved");
				}				
			}
			else if(ie.textField(name, IProjectsConst.approvalSubCommentLongText).exists()) 
			{
				if(ie.textField(name, IProjectsConst.approvalSubCommentLongText).readOnly()) 
				{
					return false;				
				}
				else
				{
					ie.textField(name, IProjectsConst.approvalSubCommentLongText).set(comment);
					ie.textField(name, IProjectsConst.approvalSubDatePicker).set(GeneralUtil.getTodayDate());
					ie.selectList(name, IProjectsConst.approvalSubApproveDropdown).select("Approved");
				}
			}
			
			GeneralUtil.takeANap(1.5);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (hasSummary) {
				ClicksUtil.clickButtons(IClicksConst.nextBtn);
			}

			if (submit) {

				if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

					return false;

				}
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			return true;

		}		

		return false;
	}
	
	
	

	/**
	 * @param stepName
	 * @param submit
	 * @param stepStatus
	 * @return boolean
	 * @throws Exception
	 */
	public boolean approveAmendedSubmission(String stepName, boolean submit,
			String stepStatus) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (this.claimNumber > 0) {
			subProject = " - Claim " + this.claimNumber.toString();
		}

		initializeStep(stepName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName + subProject);
		hashTable
		.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);
		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		hashTableDropdown
		.put(
				IFiltersConst.grantManagement_SubmissionStatus_Lbl,
				stepStatus);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").click();


			if(ie.textField(name, IProjectsConst.approvalSubCommentLongText).readOnly()) {

				log.error("The Form is in Read Only Mode!");

				return false;				
			}

			ie.textField(name, IProjectsConst.approvalSubDatePicker).set(GeneralUtil.getTodayDate());
			ie.textField(name, IProjectsConst.approvalSubCommentLongText).append(commentAmend);
			ie.selectList(name, IProjectsConst.approvalSubApproveDropdown).select("Approved");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (submit) {

				if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

					log.error("The Submit Button is disabled!");

					return false;
				}
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			return true;

		}	

		log.error("Could not find the Submission");

		return false;

	}

	/**
	 * @param stepName
	 * @throws Exception
	 */
	public boolean rejectSubmission(String stepName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		initializeStep(stepName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable
		.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);
		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown, false);

		Div tDiv = TablesUtil.tableDiv();

		if(!tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists())
		{
			return false;

		}

		tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").click();


		ie.selectList(name, "/:0:numericDropdown/").select("Rejected");
		GeneralUtil.takeANap(1.0);
		ie.textField(name, "/:1:datePicker/").set(GeneralUtil.getTodayDate());
		ie.textField(name, "/:2:textArea_Below/").set(comment);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

		return true;

	}

	/**
	 * @param stepName
	 * @throws Exception
	 */
	public boolean pendingSubmission(String stepName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		initializeStep(stepName);

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable
		.put(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				this.orgFullName);
		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				this.getCurrentStepName());

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown, false);

		Div tDiv = TablesUtil.tableDiv();

		if(!tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists())
		{
			return false;

		}

		tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").click();


		ie.selectList(name, "/:0:numericDropdown/").select("Pending Approval");
		
		GeneralUtil.takeANap(1.0);
		
		ie.textField(name, "/:1:datePicker/").set(GeneralUtil.getTodayDate());
		ie.textField(name, "/:2:textArea_Below/").set(comment);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

		return true;

	}

	/**
	 * @param isRequired
	 * @param isProgramOfficeOnly
	 * @param isSubmittible
	 * @param paSubmissionForm
	 * @param formletName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeClaimCriteria(boolean isRequired,
			boolean isProgramOfficeOnly, boolean isSubmittible,
			String paSubmissionForm, String formletName) throws Exception {

		retValue = false;

		int rowIndex = -1;

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		ClicksUtil.clickLinks(formletName);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.eFormListDataWithLetters, "Claim " + this.getClaimNumber());

		if(rowIndex <= -1)
		{
			log.error("No Items Find in the Submission Schedule Tables!");
			return false;
		}
		
		tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.eFormListDataWithLetters).row(rowIndex).link(title, "View this list item").click();

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.endDate),"FAIL: Could not set Due Date");

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FIAL: Could not set End Date");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, paSubmissionForm),"FAIL: Could not select PA Submission Form!");

		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, isRequired), "FAIL: Could not set Required checkbox!");

		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, isProgramOfficeOnly), "FAIL: Could not set PO Only checkbox!");

		//ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		Assert.assertTrue(ie.textField(id,IGeneralConst.publicationDate_textField_Id).get().equals(this.startDate), "Fail: Modified Publication Start Date was not saved");

		Assert.assertTrue(ie.textField(id,IGeneralConst.scheduleDate_textField_Id).get().equals(this.endDate), "Fail: Couldnot find Schedule Due Date");

		Assert.assertTrue(ie.textField(id,IGeneralConst.publicationEndDate_textField_Id).get().equals( this.endDate), "Fail: Couldnot find Publication End Date");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn));

		if(isSubmittible)
		{
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);

			if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("Submitting Button is Disabled!");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);			
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);


		return true;
	}

	/**
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean openStandardAgreement(String stepName) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		this.initializeStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);
		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, this.getCurrentStepName());

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award Form"));

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);		

		return true;
	}

	/**
	 * @param claimForm
	 * @param claimCount
	 * @param doSubmit
	 * @param doReturn
	 * @param claimName
	 * @param claimStart
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addSchedules(String claimForm, Integer claimCount, boolean doSubmit, boolean doReturn, String claimName, Integer claimStart) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (int i = 0; i < claimCount; i++) {

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,claimName.concat(Integer.toString(i + claimStart))),"FAIL: could not set Claim Name!");

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.endDate),"FAIL: Could not set Due Date");

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FIAL: Could not set End Date");

			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, claimForm),"FAIL: Could not select PA Submission Form!");


			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

		if (doSubmit) {
			ClicksUtil.clickLinks(IClicksConst.openAgreementSummaryLnk);

			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		if(doReturn)
		{
			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);			
		}

		return true;
	}

	/**
	 * @param stepName
	 * @param doSubmit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reSubmitAnyStandardAgreement(String stepName, boolean doSubmit) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		this.initializeStep(stepName);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);
		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, 
				this.getCurrentStepName());

		hashTableDropdown.put(IFiltersConst.grantManagement_Version_Lbl, IFiltersConst.submissionVersion_LatestVersion);

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		if(!FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false))
		{
			log.error("Could not filter Award List!");
			return false;
		}

		if(!TablesUtil.openInTableByImageAlt(ITablesConst.awardsTableId, this.getCurrentStepName(), IClicksConst.awardListView_Alt))
		{
			log.error("Could not Open Re-Executed Award submission!");
			return false;
		}


		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IProjectsConst.gps_AgreementDetails_TextFldTtl, ", Re-executed"));

		ClicksUtil.clickButtons(IClicksConst.nextBtn);

		if(!doSubmit)
		{
			ClicksUtil.returnFromAnyForm();
			return true;
		}

		ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);

		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.returnFromAnyForm();

		return true;
	}

	/**
	 * @param claimForm
	 * @param claimCount
	 * @param doSubmit
	 * @param claimName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitStandardAgreement(String claimForm, Integer claimCount, boolean doSubmit, String claimName) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		GeneralUtil.takeANap(1.000);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award Form"));

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (int i = 1; i <= claimCount; i++) {

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IGeneralConst.initialClaim[1][1].concat(Integer.toString(i))),"FAIL: could not set Claim Name!");

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.endDate),"FAIL: Could not set Due Date");

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FIAL: Could not set End Date");

			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, claimForm),"FAIL: Could not select PA Submission Form!");

			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

		ClicksUtil.clickButtons(IClicksConst.nextBtn);

		if (doSubmit) 		
		{
			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return retValue;
	}

	/**
	 * @param awardType
	 * @param claimNum
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitAward(String awardType, Integer claimNum,
			boolean submit) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		GeneralUtil.takeANap(1.000);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);
		
		Div tDiv = TablesUtil.tableDiv();

		if (!tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").exists()){
			log.error("Could not find project in Awards table!");
			return false;
		}
		
		tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();
		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award Form"), "FAIL: Could not set text filed in Agreement Details");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		if (awardType == "Standard") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IGeneralConst.initialClaim[1][1].concat(Integer.toString(i))),"FAIL: could not set Claim Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.dueDate),"FAIL: Could not set Due Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FIAL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, IGeneralConst.initialClaim[1][0]),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");


				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}

		if (submit) 		
		{
			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}

	/**
	 * @param awardType
	 * @param claimNum
	 * @param submit
	 * @param postFormName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitAward(String awardType, Integer claimNum,
			boolean submit,String postFormName) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		GeneralUtil.takeANap(1.000);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();
		GeneralUtil.takeANap(1.0);

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award Form"), "FAIL: Could not set text filed in Agreement Details");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		if (awardType == "Standard") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IGeneralConst.initialClaim[1][1].concat(Integer.toString(i))),"FAIL: could not set Claim Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.dueDate),"FAIL: Could not set Due Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FAIL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, postFormName),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");


				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}

		if (submit) 		
		{
			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}
	
	/**
	 * @param awardType
	 * @param claimNum
	 * @param submit
	 * @param postFormName
	 * @param required
	 * @param pOOnly 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitAward_ODSS(String awardType, Integer claimNum,
			boolean submit,String postFormName, boolean required, boolean pOOnly, boolean active) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		GeneralUtil.takeANap(1.000);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		if (awardType == "ODSS-Standard-Award") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IGeneralConst.initialClaim[1][1].concat(Integer.toString(i))),"FAIL: Could not set Submission Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.dueDate),"FAIL: Could not set Due Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FAIL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, postFormName),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");


				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}
		
		ClicksUtil.clickLinks(IClicksConst.openPAOnDemandSubmissionLnk);

		if (awardType == "ODSS-Standard-Award") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IProjectsConst.odss_Payment_ddVal),"FAIL: Could not set Submission Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FAIL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, postFormName),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, required), "FAIL: Could not set Required checkbox!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, pOOnly), "FAIL: Could not set Required checkbox!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_ActiveTtl, active), "FAIL: Could not set Required checkbox!");

				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}
		
		

		if (submit) 		
		{
			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}

	public boolean submitAward_ODSSPast(String awardType, Integer claimNum,
			boolean submit,String postFormName, boolean required) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		GeneralUtil.takeANap(1.000);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		if (awardType == "ODSS-Standard-Award") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IGeneralConst.initialClaim[1][1].concat(Integer.toString(i))),"FAIL: Could not set Submission Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, this.dueDate),"FAIL: Could not set Due Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FAIL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, postFormName),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");


				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}
		
		ClicksUtil.clickLinks(IClicksConst.openPAOnDemandSubmissionLnk);

		if (awardType == "ODSS-Standard-Award") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IProjectsConst.odss_Payment_ddVal),"FAIL: Could not set Submission Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, GeneralUtil.getPastDate(-3)), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getPastDate(-1)),"FAIL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, postFormName),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, required), "FAIL: Could not set Required checkbox!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_ActiveTtl, true), "FAIL: Could not set Required checkbox!");

				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}
		
		

		if (submit) 		
		{
			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}
	/**
	 * @param awardType
	 * @param claimNum
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitAwardWithNoScheduleDate(String awardType, Integer claimNum,
			boolean submit) throws Exception {

		retValue = true;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		GeneralUtil.takeANap(1.000);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award Form"), "FAIL: Could not set text filed in Agreement Details");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		if (awardType == "Standard") {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,IGeneralConst.initialClaim[1][1].concat(Integer.toString(i))),"FAIL: could not set Claim Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, this.startDate), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, this.endDate),"FIAL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, IGeneralConst.initialClaim[1][0]),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(IGeneralConst.initialClaim[1][2])), "FAIL: Could not set Required checkbox!");


				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}

		if (submit) 		
		{
			if (!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn))
			{
				log.error("The Submit Button is disabled");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}

	/**
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitAmendedBasicAward(boolean submit) throws Exception {

		retValue = true;

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		// ###****Fill Agreement Details ********
		ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0));

		ie.textField(0).append(", Accessed Again");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		if (submit) {
			ClicksUtil.clickLinks("/Summary/");

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			if (GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {
				retValue = false;
			}
		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return retValue;

	}

	/**
	 * @param stepName
	 * @param submit
	 * @param generateSchedule
	 * @return
	 * @throws Exception
	 */
	public boolean removeScheduleEntry(String stepName, boolean submit,
			boolean generateSchedule) throws Exception {
		boolean retValue = false;

		int rowIndex = -1;

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		rowIndex = TablesUtil.getRowIndex("/:data/", "Claim "
				+ this.getClaimNumber());

		if (rowIndex > -1) {
			Thread dialogClicker = new Thread() {
				@Override
				public void run() {
					try {
						IE ie = IEUtil.getActiveIE();

						ConfirmDialog dialog1 = ie.confirmDialog();

						while (dialog1 == null) {

							Thread.sleep(250);

						}

						dialog1.ok();

					} catch (Exception e) {
						throw new RuntimeException("Unexpected exception", e);
					}
				}
			};

			dialogClicker.start();

			tDiv.body(id, "/:data/").row(rowIndex).image(src,	IClicksConst.deleteImg).click();

			GeneralUtil.takeANap(1.000);

			dialogClicker = null;

			if (generateSchedule) {
				ClicksUtil.clickButtons(IClicksConst.generatePaymentScheduleBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.openBudgetFormletLnk);
			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			if (ie.link(text, "Result Formlet").exists()) {
				ClicksUtil.clickLinks("Result Formlet");

				Assert.assertEquals(ie.textField(0).getContents(), "2,000.00");

				Assert.assertEquals(ie.textField(1).getContents(), "6,000.00");

				Assert.assertEquals(ie.textField(2).getContents(), "$2,000.00");

				Assert.assertEquals(ie.textField(3).getContents(), "$4,000.00");

			}

			if (submit) {
				ClicksUtil.clickLinks("Summary");

				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);
			retValue = true;

		}

		return retValue;

	}

	/**
	 * @param generateSchedule
	 * @return boolean
	 * @throws Exception
	 */
	public boolean removeScheduleEntryFromMilestones(boolean generateSchedule)
			throws Exception {

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		rowIndex = TablesUtil.getRowIndex("/:data/", "Claim "
				+ this.getClaimNumber());

		if (rowIndex > -1) {
			Thread dialogClicker = new Thread() {
				@Override
				public void run() {
					try {
						IE ie = IEUtil.getActiveIE();

						ConfirmDialog dialog1 = ie.confirmDialog();

						while (dialog1 == null) {

							Thread.sleep(250);

						}

						dialog1.ok();

					} catch (Exception e) {
						throw new RuntimeException("Unexpected exception", e);
					}
				}
			};

			dialogClicker.start();

			Div tDiv = TablesUtil.tableDiv();

			tDiv.body(id, "/:data/").row(rowIndex).image(src, IClicksConst.deleteImg).click();

			GeneralUtil.takeANap(1.000);

			dialogClicker = null;

			if (generateSchedule) {
				ClicksUtil
				.clickButtons(IClicksConst.generatePaymentScheduleBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.openBudgetFormletLnk);
			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			return true;
		}

		return false;
	}

	/**
	 * @param stepName
	 * @param startDate
	 * @param submit
	 * @param generateSchedule
	 * @param listFormlet
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeClaimStartDate(String stepName, String startDate,
			boolean submit, boolean generateSchedule, String listFormlet)
					throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		int rowIndex = -1;

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		if (listFormlet.matches("Milestone")) {
			ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);
		} else {
			ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);
		}

		rowIndex = TablesUtil.getRowIndex("g3-form:data_withLetterFilter_data", "Claim "
				+ this.getClaimNumber());

		if (rowIndex > -1) {
			ie.body(id, "g3-form:data_withLetterFilter_data").row(rowIndex).link(title, "View this list item").click();

			Assert.assertTrue(GeneralUtil.setTextById("g3-form:eFormFieldList:1:datePicker_input", startDate), "FAIL: Could not set the Start Date");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			if (generateSchedule) {
				ClicksUtil.clickButtons(IClicksConst.generatePaymentScheduleBtn);
			}

			if (submit) {
				ClicksUtil.clickLinks("/Summary/");

				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

			return true;
		}
		return false;
	}

	/**
	 * @param startDate
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean changeClaimStartDate(String startDate, boolean submit)
			throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		int rowIndex = -1;

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.projFullName);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.progFullName);

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		rowIndex = TablesUtil.getRowIndex("g3-form:data_withLetterFilter_data", "Claim "
				+ this.getClaimNumber());

		if (rowIndex > -1) {
			ie.body(id, "g3-form:data_withLetterFilter_data").row(rowIndex).image(alt,"View this list item").click();

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, startDate), "FAIL: Could not set the Start Date");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			if (submit) {
				ClicksUtil.clickLinks("/Summary/");

				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

			return true;

		}

		return false;
	}

	/**
	 * 
	 * @param stepName
	 * @param submit
	 * @return boolean
	 * @throws Exception
	 */
	public boolean removeClaimEntry(String stepName, boolean submit)
			throws Exception {
		boolean retValue = false;
		
		IE ie = IEUtil.getActiveIE();

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.awardsTableId).row(0).image(0).click();

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		rowIndex = TablesUtil.getRowIndex("g3-form:data_withLetterFilter_data", "Claim "
				+ this.getClaimNumber());

		if (rowIndex > -1) {
			Thread dialogClicker = new Thread() {
				@Override
				public void run() {
					try {
						IE ie = IEUtil.getActiveIE();

						ConfirmDialog dialog1 = ie.confirmDialog();

						while (dialog1 == null) {
							GeneralUtil.takeANap(0.250);
						}

						dialog1.ok();

					} catch (Exception e) {
						throw new RuntimeException("Unexpected exception", e);
					}
				}
			};

			dialogClicker.start();

			ie.body(id, "g3-form:data_withLetterFilter_data").row(rowIndex).link(title, "Delete this list item").click();

			GeneralUtil.takeANap(1.000);

			dialogClicker = null;

			if (submit) {
				ClicksUtil.clickLinks(IClicksConst.openAgreementSummaryLnk);

				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		}
		return retValue;
	}

	// ----------------- End of Public Methods -------------------------------

	// ------------------ Getters And Setters --------------------------------

	/**
	 * @return projFullName
	 */
	public String getProjFullName() {
		return projFullName;
	}

	/**
	 * @param projFullName the projFullName to set 
	 */
	public void setProjFullName(String projFullName) {
		this.projFullName = projFullName;
	}

	/**
	 * @return progLetter
	 */
	public String getProgLetter() {
		return progLetter;
	}

	/**
	 * @param progLetter the progLetter to set
	 */
	public void setProgLetter(String progLetter) {
		this.progLetter = progLetter;
	}

	/**
	 * @return projLetter
	 */
	public String getProjLetter() {
		return projLetter;
	}

	/**
	 * @param projLetter the projLetter to set
	 */
	public void setProjLetter(String projLetter) {
		this.projLetter = projLetter;
	}

	/**
	 * @return projName
	 */
	public String getProjName() {
		return projName;
	}

	/**
	 * @param projName the projName to Set
	 */
	public void setProjName(String projName) {
		this.projName = projName;
	}

	/**
	 * @return subProjName
	 */
	public String getSubProjName() {
		return subProjName;
	}

	/**
	 * @param subProjName the subProjName to set
	 */
	public void setSubProjName(String subProjName) {
		this.subProjName = subProjName;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * @return progFullName
	 */
	public String getProgFullName() {
		return progFullName;
	}

	/**
	 * @param progFullName the progFullName to set
	 */
	public void setProgFullName(String progFullName) {
		this.progFullName = progFullName;
	}

	/**
	 * @return orgLetter
	 */
	public String getOrgLetter() {
		return orgLetter;
	}

	/**
	 * @param orgLetter the orgLetter to set
	 */
	public void setOrgLetter(String orgLetter) {
		this.orgLetter = orgLetter;
	}

	/**
	 * @return orgFullName
	 */
	public String getOrgFullName() {
		return orgFullName;
	}

	/**
	 * @param orgFullName the orgFullName to set
	 */
	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	/**
	 * @return claimNumber
	 */
	public Integer getClaimNumber() {
		return claimNumber;
	}

	/**
	 * @param claimNumber the claimNumber to set
	 */
	public void setClaimNumber(Integer claimNumber) {
		this.claimNumber = claimNumber;
	}

	/**
	 * @return endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return postFix
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * @param postfix the postfix to set
	 */
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * @return progPreFix
	 */
	public String getProgPreFix() {
		return progPreFix;
	}

	/**
	 * @param progPreFix the progPreFix to set
	 */
	public void setProgPreFix(String progPreFix) {
		this.progPreFix = progPreFix;
	}

	/**
	 * @return the comment
	 */
	public static String getComment() {
		return comment;
	}

	/**
	 * @return the commentAmend
	 */
	public static String getCommentAmend() {
		return commentAmend;
	}

	/**
	 * @return the currentStep
	 */
	public String getCurrentStep() {
		return currentStep;
	}

	/**
	 * @param currentStep
	 *            the currentStep to set
	 */
	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	/**
	 * @return the projectPortal
	 */
	public char getProjectPortal() {
		return projectPortal;
	}

	/**
	 * @param projectPortal
	 *            the projectPortal to set
	 */
	public void setProjectPortal(char projectPortal) {
		this.projectPortal = projectPortal;
	}

	/**
	 * @return the newProject
	 */
	public boolean isNewProject() {
		return newProject;
	}

	/**
	 * @param newProject
	 *            the newProject to set
	 */
	public void setNewProject(boolean newProject) {
		this.newProject = newProject;
	}

	public String getCurrentStepEFormName() {
		return currentStepEFormName;
	}

	public void setCurrentStepEFormName(String currentStepEFormName) {
		this.currentStepEFormName = currentStepEFormName;
	}

	/**
	 * @return the progFullIdent
	 */
	public String getProgFullIdent() {
		return progFullIdent;
	}

	/**
	 * @param progFullIdent
	 *            the progFullIdent to set
	 */
	public void setProgFullIdent(String progFullIdent) {
		this.progFullIdent = progFullIdent;
	}

	/**
	 * @return the currentStepIdent
	 */
	public String getCurrentStepIdent() {
		return currentStepIdent;
	}

	/**
	 * @param currentStepIdent
	 *            the currentStepIdent to set
	 */
	public void setCurrentStepIdent(String currentStepIdent) {
		this.currentStepIdent = currentStepIdent;
	}

	/**
	 * @return the currentStepName
	 */
	public String getCurrentStepName() {
		return currentStepName;
	}

	/**
	 * @param currentStepName
	 *            the currentStepName to set
	 */
	public void setCurrentStepName(String currentStepName) {
		this.currentStepName = currentStepName;
	}
	/**
	 * @return the fopp
	 */
	public FundingOpportunity getFopp() {
		return fopp;
	}

	/**
	 * @param fopp the fopp to set
	 */
	public void setFopp(FundingOpportunity fopp) {
		this.fopp = fopp;
	}

	/**
	 * @return the projPrefix
	 */
	public String getProjPrefix() {
		return projPrefix;
	}

	/**
	 * @param projPrefix the projPrefix to set
	 */
	public void setProjPrefix(String projPrefix) {
		this.projPrefix = projPrefix;
	}

	/**
	 * @return the newOrg
	 */
	public boolean isNewOrg() {
		return newOrg;
	}

	/**
	 * @param newOrg the newOrg to set
	 */
	public void setNewOrg(boolean newOrg) {
		this.newOrg = newOrg;
	}

	/**
	 * @return the sourceProjectFullName
	 */
	public String getSourceProjectFullName() {
		return sourceProjectFullName;
	}

	/**
	 * @param sourceProjectFullName the sourceProjectFullName to set
	 */
	public void setSourceProjectFullName(String sourceProjectFullName) {
		this.sourceProjectFullName = sourceProjectFullName;
	}

	/**
	 * @return the importDataFromProject
	 */
	public boolean isImportDataFromProject() {
		return importDataFromProject;
	}

	/**
	 * @param importDataFromProject the importDataFromProject to set
	 */
	public void setImportDataFromProject(boolean importDataFromProject) {
		this.importDataFromProject = importDataFromProject;
	}

	/**
	 * @return the trnsfrToOrgFullName
	 */
	public String getTrnsfrToOrgFullName() {
		return trnsfrToOrgFullName;
	}

	/**
	 * @param trnsfrToOrgFullName the trnsfrToOrgFullName to set
	 */
	public void setTrnsfrToOrgFullName(String trnsfrToOrgFullName) {
		this.trnsfrToOrgFullName = trnsfrToOrgFullName;
	}

	/**
	 * @return the projPostfix
	 */
	public String getProjPostfix() {
		return projPostfix;
	}

	/**
	 * @param projPostfix the projPostfix to set
	 */
	public void setProjPostfix(String projPostfix) {
		this.projPostfix = projPostfix;
	}

	/**
	 * @return the orgNumber
	 */
	public String getOrgNumber() {
		return orgNumber;
	}

	/**
	 * @param orgNumber the orgNumber to set
	 */
	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}
	/**
	 * @return the subProjFullName
	 */
	public String getSubProjFullName() {
		return subProjFullName;
	}

	/**
	 * @param subProjFullName the subProjFullName to set
	 */
	public void setSubProjFullName(String subProjFullName) {
		this.subProjFullName = subProjFullName;
	}

	/**
	 * @return the projNumber
	 */
	public String getProjNumber() {
		return projNumber;
	}

	/**
	 * @param projNumber the projNumber to set
	 */
	public void setProjNumber(String projNumber) {
		this.projNumber = projNumber;
	}

	/**
	 * @return the projFullNameWithProjNumber
	 */
	public String getProjFullNameWithProjNumber() {
		return projFullNameWithProjNumber;
	}

	/**
	 * @param projFullNameWithProjNumber the projFullNameWithProjNumber to set
	 */
	public void setProjFullNameWithProjNumber(String projFullNameWithProjNumber) {
		this.projFullNameWithProjNumber = projFullNameWithProjNumber;
	}

	/**
	 * @return the srcProjectFullNameWithNumber
	 */
	public String getSrcProjectFullNameWithNumber() {
		return srcProjectFullNameWithNumber;
	}

	/**
	 * @param srcProjectFullNameWithNumber the srcProjectFullNameWithNumber to set
	 */
	public void setSrcProjectFullNameWithNumber(String srcProjectFullNameWithNumber) {
		this.srcProjectFullNameWithNumber = srcProjectFullNameWithNumber;
	}

	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;

	}


	// ----------------- End Of Getters and Setters --------------------------

}
