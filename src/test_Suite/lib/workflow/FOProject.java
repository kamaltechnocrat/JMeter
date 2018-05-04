package test_Suite.lib.workflow;

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IProjectsConst.EWizardParams;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.runtime.ie.IE;

public class FOProject extends Project {
	
	private static Log log = LogFactory.getLog(FOProject.class);
	
	//	------------------- Class Fields -------------------------------------	
	
	private String projFOLetter = null;
	private String projFOName = null;
	private String projFOFullName = null;
	private String foOrgName = null;
	private String foOrgNumber = null;
	private String searchWord = null;
	private String foOrgIndex;
	
	protected LinkedHashMap<EWizardParams, Object> wizardParamsLHM;
	protected Set<Map.Entry<EWizardParams, Object>> wizardParamsSet;

	Hashtable<Integer, String> drpDownHash;
	
	Hashtable<Integer, String> fieldsHash;
	
	boolean retValue;	
	
	//------------------- Constructors ---------------------
	
	public FOProject(){
		super();
	}
	
	public FOProject(Program foProg) {		
		
		super(foProg, true);
		
		this.foOrgName = IPreTestConst.FO_OrgShortName + "1";
		
		super.setOrgFullName(this.foOrgName);
		
		this.foOrgNumber = IPreTestConst.FO_OrgNumber + "1";
	}
	
	public FOProject(Program foProg, String applicantIndex) {		
		
		super(foProg, true);
		
		this.setFoOrgIndex(applicantIndex);		
		
		this.foOrgName = IPreTestConst.FO_OrgShortName + applicantIndex;
		
		super.setOrgFullName(this.foOrgName);
		
		this.foOrgNumber = IPreTestConst.FO_OrgNumber + applicantIndex;
	}
	
	public FOProject(FundingOpportunity fopp, String foPrefix, boolean isNewProject, Integer OrgIndex, String projLetter) {
		
		super(fopp,foPrefix,isNewProject);
		
		this.foOrgIndex = OrgIndex.toString();		
		
		this.foOrgName = IPreTestConst.FO_OrgShortName + OrgIndex.toString();
		
		super.setOrgFullName(this.foOrgName);
		
		this.foOrgNumber = IPreTestConst.FO_OrgNumber + OrgIndex.toString();
		
		this.projFOLetter = projLetter;
		
		this.projFOName = super.getProjName();
		
		this.projFOFullName = (this.projFOName) + this.projFOLetter;
		
		super.setProjFullName(this.getProjFOFullName());
		
	}
	
	public FOProject(FundingOpportunity fopp, String foPrefix, boolean isNewProject, Integer OrgIndex) {
		
		super(fopp,foPrefix,isNewProject);
		
		this.foOrgIndex = OrgIndex.toString();		
		
		this.foOrgName = IPreTestConst.FO_OrgShortName + OrgIndex.toString();
		
		super.setOrgFullName(this.foOrgName);
		
		this.foOrgNumber = IPreTestConst.FO_OrgNumber + OrgIndex.toString();
		
		this.projFOLetter = EFormsUtil.createAnyRandomString(5);
		
		this.projFOName = super.getProjName();
		
		this.projFOFullName = this.projFOLetter.concat(this.projFOName);
		
		super.setProjFullName(this.getProjFOFullName());
		
	}
	
	//------------------ End of Constractors -----------------
	
	
	//---------------- Public Methods ----------------------
	
	public void initilizeWizard(Object[] obj) throws Exception {
		
		LinkedHashMap<EWizardParams, Object> lhm = new LinkedHashMap<EWizardParams, Object>();
		
		lhm.put(EWizardParams.FOPP_NAME, super.getProgFullName());
		
		for(int x=0; x<obj.length; x++) {
			
			lhm.put(EWizardParams.values()[x+1], obj[x]);
		}		
		
		this.setWizardParamsLHM(lhm);
		this.setWizardParamsSet(lhm.entrySet());
		
	}
	
	public void initializStep(String stepName) throws Exception
	{
		this.setCurrentStepIdent(this.progLetter + this.progPreFix + stepName + this.postfix);
		
		this.setCurrentStepName(this.getCurrentStepIdent().replace('-', ' '));	
		
		log.info("Step Initialized");
	}	
	
	public boolean canCreateFOProject() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
		
		if(GeneralUtil.selectFullStringInDropdownList(IProjectsConst.foProject_FundingOppName_DropdownId, this.getProgFullName()))
		{
			if(ie.image(alt, IClicksConst.addProject_ImgAlt).exists())
			{
				return true;
			}
		}		
		
		return false;
	}
	
	public boolean createFOProjectSimple() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IProjectsConst.foProject_FundingOppName_DropdownId, this.progFullName))
		{
			log.error("Could not find FOPP in Dropdown");
			return false;
		}
		
		if(!ClicksUtil.clickImageByAlt(IClicksConst.addProject_ImgAlt))
		{
			log.error("Could not click New Icon to create new FO Project");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IProjectsConst.foProject_ProjectName_TxtFldId, this.projFOFullName))
		{
			log.error("fail to add new FO Project");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);			
		
		
		return true;
	}
	
	public void createFOProject() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
		
		ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select("All Funding Opportunities");
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		this.projFOLetter = EFormsUtil.createAnyRandomString(5); 
		
		super.setProjLetter(this.projFOLetter);
		
		super.setOrgLetter(this.projFOLetter);
		
		this.projFOFullName = this.projFOLetter + this.progPreFix + IGeneralConst.gnrl_FO_ProjName + this.postfix;		
		
		super.setProjFullName(this.projFOFullName);
		
		ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select(this.progFullName);
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.addProject_ImgAlt), "FAIL: could not Click Add Project Icon!");
		
		ie.textField(id,IProjectsConst.foProject_ProjectName_TxtFldId).set(this.projFOFullName);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);			
	}	
	
	public boolean createFOProjectNew() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		boolean retV = true;
		
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
		
		ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select("All Funding Opportunities");
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		this.projFOLetter = EFormsUtil.createAnyRandomString(5); 
		
		super.setProjLetter(this.projFOLetter);
		
		super.setOrgLetter(this.projFOLetter);
		
		this.projFOFullName = this.projFOLetter + this.progPreFix + this.projPrefix + IGeneralConst.gnrl_FO_ProjName + this.postfix;		
		
		super.setProjFullName(this.projFOFullName);
		
		ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select(this.progFullName);
		
		GeneralUtil.takeANap(2.0);
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.addProject_ImgAlt), "FAIL: could not Click Add Project Icon!");
		
		//New changes for 3.3 Registrant Security
		//Selecting Applicant Name is not required
		
		//ie.selectList(0).select("/" + this.foOrgName + "/");
		
		ie.textField(id,IProjectsConst.foProject_ProjectName_TxtFldId).set(this.projFOFullName);
		
		if(this.isImportDataFromProject()) {
			
			if(!selectImportDataFromFOProject()) {
				retV = false;
			}
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		if(!this.setTheProjectNumber())
		{
			log.error("Failed to get the Project Number");
			return false;
		}
		
		return retV;
	}	
	
	public boolean setTheProjectNumber() throws Exception {
		
		int indx = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.fOProjectsTableId, this.getProjFOFullName());
		
		if(indx < -1)
		{
			log.error("Could not find project in List");
			return false;
		}
		
		
		GeneralUtil.takeANap(5.0);

		String retVal = TablesUtil.getCellContent(ITablesConst.fOProjectsTableId, indx, 3, 0);
		
		if(retVal.trim() == null || retVal.trim().equals(""))
		{
			log.error("could not find Project Number in List!");
			return false;
		}
		
		this.setProjNumber(retVal.trim());
		
		this.setProjFullNameWithProjNumber(this.getProjFOFullName().concat(" (").concat(this.getProjNumber()).concat(")"));
		
		return true;
		
	}	
	
	public boolean setTheApplicantNumber() throws Exception {
		
		int indx = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.fOProjectsTableId, this.getProjFOFullName());
		
		if(indx < -1)
		{
			log.error("Could not find project in List");
			return false;
		}
		
		String retVal = TablesUtil.getCellContent(ITablesConst.fOProjectsTableId, indx, 6, 0);
		
		if(retVal.trim() == null || retVal.trim().equals(""))
		{
			log.error("could not find Project Number in List!");
			return false;
		}
		
		this.setFoOrgNumber(retVal.trim());
		
		log.warn(this.getFoOrgNumber());
		
		//this.setProjFullNameWithProjNumber(this.getProjFOFullName().concat(" (").concat(this.getProjNumber()).concat(")"));
		
		return true;
		
	}
	
	public boolean createFOProjectNewNew() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		boolean retV = true;
		
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
		
		ie.selectList(id, IProjectsConst.foProject_ProjectsStatus_DropdownId).select("All Projects");
		
		ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select("All Funding Opportunities");
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		this.projFOFullName = EFormsUtil.createAnyRandomString(5).concat("-").concat(this.projFOFullName);		
		
		super.setProjFullName(this.projFOFullName);
		
		ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select(this.progFullName);
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.addProject_ImgAlt), "FAIL: could not Click Add Project Icon!");
		
		//New changes for 3.3 Registrant Security
		//Selecting Applicant Name is not required
		
		//ie.selectList(0).select("/" + this.foOrgName + "/");
		
		ie.textField(id,IProjectsConst.foProject_ProjectName_TxtFldId).set(this.projFOFullName);
		
		if(this.isImportDataFromProject()) {
			
			if(!selectImportDataFromFOProject()) {
				retV = false;
			}
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		
		if(!this.setTheProjectNumber())
		{
			log.error("Failed to get the Project Number");
			return false;
		}
		
		if(!this.setTheApplicantNumber())
		{
			log.error("Failed to get the Applicant Number");
			return false;
		}
		
		return retV;
	}
	
	public boolean foProj_fillAllOnDemands() throws Exception {

		try {
			IE ie = IEUtil.getActiveIE();

			Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk), "Fail: Couldn't open Projects Link!");

			ie.selectList(id, IProjectsConst.foProject_ProjectsStatus_DropdownId).select("Open Projects");

			ie.selectList(id, IProjectsConst.foProject_FundingOppName_DropdownId).select(this.progFullName);

			if (!TablesUtil.openInTableByImageAlt(ITablesConst.foProjects_Table_ID, projFOFullName, 
					"Add a new On-Demand Form")){
				
				log.warn("Couldn't open On Demand form by Alt!");
				return false;
			}

		
			if (!TablesUtil.openInTableByImageAltAndIndex(ITablesConst.fo_onDemandSub_tableID, 
					0, "open a new submission")){
				
				log.warn("No Submission Types found in table!");
				return false;
			}

				Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, 
						IRefTablesConst.textFieldData), "Fail: Couldn't fill in Textfield!");
			
				Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit!");
				

		} catch (Exception e) {
			
			log.error("Unexpected Exception: " + e);
			return false;
		}
		return true;
	}
	
	
	public boolean openProjectDetailsInFO(String projStatus) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
		
		ie.selectList(id,IProjectsConst.foProject_ProjectsStatus_DropdownId).select(projStatus);
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		ie.selectList(id,IProjectsConst.foProject_FundingOppName_DropdownId).select(this.progFullName);
		
		//ClicksUtil.clickButtons(IClicksConst.setBtn);
		
		char tmpLetter = this.projFOLetter.charAt(0);
		
		Div tDiv = TablesUtil.tableDiv(); 
		
		tDiv.body(id,"alphaFilterTable").link(text,String.valueOf(tmpLetter)).click();
		
		GeneralUtil.takeANap(0.500);
		
		return TablesUtil.openInTableByImage(ITablesConst.fOProjectsTableId, this.projFOFullName, 0);
	}
	
	public String getProjNumberForStep(String stepName) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
		
		this.initializeStep(stepName);
		
		FiltersUtil.filterFOSubListByLabel(IFiltersConst.fo_Submissions_ProjectName_Lbl,"", this.projFOFullName);
		
		int rowIndex = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId, this.currentStepName);
		
		if(rowIndex > -1) {
			
			return TablesUtil.getCellContent(ITablesConst.fOSubmissionsTableId, rowIndex, 1,1);
		}
		
		return "";		
	}
	
	public boolean selectImportDataFromFOProject() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.selectList(id,"g3-form:ImportDataFromProject_input").exists()) {	
			
			return false;
		}
		
		GeneralUtil.takeANap(1.0);
		
		List<String> lst = ie.selectList(id,"g3-form:ImportDataFromProject_input").getAllContents();
				
		for (String string : lst) {
			
			if(string.equalsIgnoreCase(this.getSrcProjectFullNameWithNumber())) {
				
				ie.selectList(id,"g3-form:ImportDataFromProject_input").select(this.getSrcProjectFullNameWithNumber());
				
				return true;
			}			
		}
		return false;		
	}

	public boolean submitFOProject(String stepName, boolean submit) throws Exception {
				
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
		
		String subProject = "";
		
		int rowIndx = -1;
		
		FiltersUtil.filterFOSubListByLabel(IFiltersConst.fo_Submissions_ProjectName_Lbl, "", this.projFOFullName);
		
		this.initializeStep(stepName);
		
		Div tDiv = TablesUtil.tableDiv();
		
		if (stepName.contains("Initial-Claim"))
		{
			subProject = " - Claim " + super.getClaimNumber().toString();	
			
			rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId, this.projFOFullName + subProject);
			
		}
		else{
			
			rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId, this.getCurrentStepName());
		}
		
		if(rowIndx > -1)
		{
			tDiv.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndx).image(alt, "Open e.Form").click();
			
			if (this.progPreFix == "-Sanity-")
				GeneralUtil.fillGrid();
			else
				GeneralUtil.SetAnyText();

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			
			if (submit)
			{
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			
			//ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			
			retValue = true;
		}

			return retValue;
	}
	
	
	public boolean submitAmendedFOProject(String stepName, boolean submit) throws Exception {
			
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
		
		String subProject = "";
		
		int rowIndx = -1;
		
		if (stepName.contains("Initial-Claim"))
		{
			subProject = " - Claim " + super.getClaimNumber().toString();
		}
		this.initializStep(stepName);	
		
		filterSubmissionList(this.getCurrentStepName(), subProject);
		
		//rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId, this.getProjFOFullName() + subProject);
		
		rowIndx = TablesUtil.getRowIndexInFOSubmissionWithDiv(this.getProjFOFullName() + subProject, this.getCurrentStepName());
				
		Div tDiv =TablesUtil.tableDiv();
		
		if(rowIndx > -1)
		{
			tDiv.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndx).image(alt, "Open e.Form").click();
			
			if (this.progPreFix == "-Sanity-")
				GeneralUtil.fillGrid();
			else
				GeneralUtil.AppendToText("Changed");

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			
			if (submit)
			{
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			
			retValue = true;
		}

			return retValue;
	}
	
	
	public void doNotSubmitFOProject(String stepName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		ie.selectList(1).select(projFOFullName);
		
		String subProject = "";
		
		if (stepName.contains("Initial-Claim"))
		{
			subProject = " - Claim " + super.getClaimNumber().toString();
		}
		
		this.initializStep(stepName);
		
		filterSubmissionList(this.getCurrentStepName(), subProject);
		
		int rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId, this.getCurrentStepName());
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndx).image(alt, "Open e.Form").click();
		
		if (this.progPreFix == "-Sanity-")
		{
			GeneralUtil.fillGrid();
		}
		else
		{
			GeneralUtil.SetAnyText();
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);		
	}	
	
	public void createAndSubmitRegistrationWizrd(boolean submit) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		int rowIndex = - 1 ;
		
		ClicksUtil.clickLinks(IClicksConst.searchProgramsLnk);
		
		ie.textField(id, "/txtKeywordSearch/").set(getSearchWord());
		
		ClicksUtil.clickButtons(IClicksConst.searchBtn);
		
		rowIndex = TablesUtil.getRowIndexByTBody(ITablesConst.foSearchProgramsResultTBodyId, this.progFullName);
		
		if (rowIndex > - 1)
		{
			ie.body(id, ITablesConst.foSearchProgramsResultTBodyId).row(rowIndex).cell(0).click();
			
			GeneralUtil.LoginFO();
			
			ie.selectList(1).select(this.progFullName);
			
			ie.selectList(0).select("/" + this.foOrgName + "/");
			
			ie.textField(0).set(projFOFullName);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			
			if (submit)
			{
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}
			
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);			
		}		
	}
	
	public void filterSubmissionList(String stepName, String subProject) throws Exception {
		
		FiltersUtil.filterFOListByDiv(IFiltersConst.fo_Submissions_ProjectName_Lbl,"",this.projFOFullName);
	}
	
	
	 public String getFoApplicantName(String userName) throws Exception{
		 
		    GeneralUtil.switchToFOOnly();
		    
		    GeneralUtil.loginAnyFO(userName);
		 	
			ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);
			
			return TablesUtil.getCellContent(ITablesConst.fOApplicantsTableId, 0, 4, 4);
		}
		
	    public String getFoAppNumber() throws Exception{
	    	
			ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);
			
			return TablesUtil.getCellContent(ITablesConst.fOApplicantsTableId, 0, 5, 5);
		}
	    
	    
	//---------------- Getters and Setters --------------------
	
	public String getSearchWord() {
		return searchWord;
	}
	
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getFoOrgName() {
		return foOrgName;
	}
	
	public void setFoOrgName(String foOrgName) {
		this.foOrgName = foOrgName;
	}
	
	
	public String getFoOrgNumber() {
		return foOrgNumber;
	}
	
	public void setFoOrgNumber(String foOrgNumber) {
		this.foOrgNumber = foOrgNumber;
	}
	
//	public String getFoApplicantName() {
//		return foApplicantName;
//	}
//	
//	public void setFoApplicantName(String foApplicantName) {
//		this.foApplicantName = foApplicantName;
//	}
//	
////	public String getFoApplicantNumber() {
////		return foApplicantNumber;
////	}
//	
//	public void setFoApplicantNumber(String foApplicantNumber) {
//		this.foApplicantNumber = foApplicantNumber;
//	}

	/**
	 * @return the projFOFullName
	 */
	public String getProjFOFullName() {
		return projFOFullName;
	}

	/**
	 * @param projFOFullName the projFOFullName to set
	 */
	public void setProjFOFullName(String projFOFullName) {
		this.projFOFullName = projFOFullName;
	}

	/**
	 * @return the projFOLetter
	 */
	public String getProjFOLetter() {
		return projFOLetter;
	}

	/**
	 * @param projFOLetter the projFOLetter to set
	 */
	public void setProjFOLetter(String projFOLetter) {
		this.projFOLetter = projFOLetter;
	}

	/**
	 * @return the projFOName
	 */
	public String getProjFOName() {
		return projFOName;
	}

	/**
	 * @param projFOName the projFOName to set
	 */
	public void setProjFOName(String projFOName) {
		this.projFOName = projFOName;
	}		
	
	/**
	 * @return the foOrgIndex
	 */
	public String getFoOrgIndex() {
		return foOrgIndex;
	}

	/**
	 * @param foOrgIndex the foOrgIndex to set
	 */
	public void setFoOrgIndex(String foOrgIndex) {
		this.foOrgIndex = foOrgIndex;
	}

	/**
	 * @return the wizardParamsLHM
	 */
	public LinkedHashMap<EWizardParams, Object> getWizardParamsLHM() {
		return wizardParamsLHM;
	}

	/**
	 * @param wizardParamsLHM the wizardParamsLHM to set
	 */
	public void setWizardParamsLHM(
			LinkedHashMap<EWizardParams, Object> wizardParamsLHM) {
		this.wizardParamsLHM = wizardParamsLHM;
	}

	/**
	 * @return the wizardParamsSet
	 */
	public Set<Map.Entry<EWizardParams, Object>> getWizardParamsSet() {
		return wizardParamsSet;
	}

	/**
	 * @param wizardParamsSet the wizardParamsSet to set
	 */
	public void setWizardParamsSet(
			Set<Map.Entry<EWizardParams, Object>> wizardParamsSet) {
		this.wizardParamsSet = wizardParamsSet;
	}
	
	//-------------- End of Getters and Setters ---------------

}
