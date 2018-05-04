/**
 * 
 */
package test_Suite.lib.eForms;

import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EFormletMainTypes;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.eForms.ILBFunctionConst.EfieldDataTypes;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.constants.workflow.IProgramsConst.EstepManagment;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.FormletFunctions;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.OrgUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.users.ApplicantsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProgramUtil;
import test_Suite.utils.workflow.ProjectUtil;
import watij.elements.Div;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 * 
 */
public class LBF {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends LBF> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	public EForm form;
	public EForm subForm;
	public EForm adminForm;

	public Formlet formlet;
	public Formlet subFormlet;

	public EFormField eFormField;
	public EFormFieldFunctions eFormFieldFunc;
	public FormletFunctions formletFunc;

	public String strFormletFuncProp[];

	public String tempEFormFieldId = null;
	public String tempFormletId = null;
	public String tempEFormId = null;

	public String tempDefaultFormlet = null;

	public String formleTitlePostFix = " eList BF";
	public String formleIdentPostFix = "-eList-BF";

	public String subFormleTitlePostFix = " Sub eList BF";
	public String subFormleIdentPostFix = "-Sub-eList-BF";

	public ArrayList<String> formletIdents;

	public String preFix = "LBF-";
	public String postFix = "";

	public int formletIndex = -1;

	public Program fopp;
	public Project proj;
	public FOProject foProj;

	boolean hasAdminForm = true;
	boolean hasPubForm = true;
	boolean isNewFopp = false;

	public String stepFullIdent;

	String adminFormIdent = "LBF-Administration-Source-eLists";
	String pubFormIdent = "Publication Form";

	private int syncType;

	private ILBFunctionConst.EFormletMainTypes eFormletsTypes;
	private ILBFunctionConst.EFormletTypes eFormletsName;
	private ILBFunctionConst.EeFormsIdentifier sourceForm;
	private ILBFunctionConst.EeFormsIdentifier eFormType;
	private IProgramsConst.EProjectType eProjType;
	private int rowsCount;
	private int totalRows;
	private int startIndex;
	private int deleteRowIndex;

	private boolean newOrg;

	public static List<LinkedHashMap<String, EfieldDataTypes>> hashList;
	public static List<LinkedHashMap<String, EfieldDataTypes>> scheduleList;

	/*-------------------------------------------------------------------*/

	public LBF() {
	};

	public LBF(int syncType) {
		this.syncType = syncType;
	};

	public LBF(EFormletMainTypes frmltMainTypes) {
		this.eFormletsTypes = frmltMainTypes;

	};

	public LBF(int syncType, EProjectType projType, int rows, int start,
			EeFormsIdentifier srcForm) {

		this.syncType = syncType;
		this.eProjType = projType;
		this.rowsCount = rows;
		this.startIndex = start;
		this.totalRows = rows;
		this.sourceForm = srcForm;
		
		log.info("LBF Initialized!");
		
	};

	/*************************************
	 * Initializing eForms, Formlets, etc
	 *************************************/

	/************* End of eForms, Formlets, etc **************************/

	/*********************************************
	 * Initializing Funding Opp, PO Projects, etc
	 *********************************************/

	public void initFopp(String foppPostFix) throws Exception {

		this.fopp = new Program(ILBFunctionConst.lbf_PA_Fopp_PreFix,
				foppPostFix, ILBFunctionConst.lbf_Fopp_name, 'P',
				this.hasAdminForm, this.adminFormIdent, this.isNewFopp,
				this.hasPubForm, this.pubFormIdent);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		this.fopp.initProgram();

	}
	

	public void initFopp(String foppPrefix, String foppPostFix) throws Exception {

		this.fopp = new Program(foppPrefix,
				foppPostFix, ILBFunctionConst.lbf_Fopp_name, 'P',
				this.hasAdminForm, this.adminFormIdent, this.isNewFopp,
				this.hasPubForm, this.pubFormIdent);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		this.fopp.initProgram();

	}

	public void initProj(boolean isNewProj, boolean isNewApplicant)
			throws Exception {

		this.proj = new Project(this.fopp, isNewProj);

		this.setNewOrg(isNewApplicant);

		this.proj.createOrgFullName(this.isNewOrg());

		this.proj.createNewPOProjectOnly(true);

	}

	public void initializeProjAndCreateOrg(boolean isNewProj,
			boolean isNewApplicant) throws Exception {

		this.proj = new Project(this.fopp, isNewProj);

		this.setNewOrg(isNewApplicant);

		this.proj.createOrgFullName(this.isNewOrg());
	}

	public void submitProject(String goToStep, String status) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> dropdownHash = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.proj.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.proj.getProgFullName());

		dropdownHash
				.put(
						IFiltersConst.grantManagement_SubmissionStatus_Lbl,
						status);

		FiltersUtil.filterListByLabel(hashTable, dropdownHash, false);

		int rowIndx = TablesUtil.getRowIndex(
				ITablesConst.applicantSubmissionTableId, this.proj
						.getProjFullName());
		
		Div tDiv  = TablesUtil.tableDiv();
		
		this.proj.setCurrentStepName(tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndx).cell(3).span(1).innerText().trim());

		tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndx).image(0).click();
		
		GeneralUtil.takeANap(1.0);

		ie.selectList(0).select(goToStep);
		
		GeneralUtil.takeANap(1.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn), "Could Not Save Formlet!");
		
		GeneralUtil.takeANap(1.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Could Not Submit Form!");

		//ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);
		
		return;

	}

	/**************** End of Funding Opp, Projects, etc ****************************/

	/*********************************************
	 * Initializing FO Projects, etc
	 *********************************************/

	public void registerAndCreateFoProjAndOpenSubmission(String stepName)
			throws Exception {

 		returnFromAnyList();

		this.foProj = new FOProject(this.fopp);

		GeneralUtil.switchToFO();

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(this.fopp
				.getProgFullName(), this.foProj.getFoOrgName()),
				"FAIL: Could not register to Funding Opp.!");

		foProj.createFOProject();

		submitFOSubmission("A LBF PA Submission", "Submission");

		FrontOfficeUtil.openFOSubmissionForm(this.foProj, stepName);
	}
	
	
	public void createFOProjectAndOpenSubmission(String stepFullName, String goToStep)throws Exception{
		
		this.foProj = new FOProject(this.fopp);
		
		GeneralUtil.switchToFO();
		
		foProj.createFOProject();

		//submitFOSubmission("A LBF PA Submission", "Submission");
		
		submitFOSubmission(stepFullName,goToStep);

		FrontOfficeUtil.openFOSubmissionForm(this.foProj, stepFullName);
		
	}
	
	public void registerAndCreateFoProjAndOpenPOSubmission(String stepName)
			throws Exception {

 		returnFromAnyList();

		this.foProj = new FOProject(this.fopp);

		GeneralUtil.switchToFO();

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(this.fopp
				.getProgFullName(), this.foProj.getFoOrgName()),
				"FAIL: Could not register to Funding Opp.!");

		foProj.createFOProject();

		submitFOSubmission("A LBF PA Submission", "PO Submission");

		FrontOfficeUtil.openFOSubmissionForm(this.foProj, stepName);
	}
	

	public void registerAndOpenApplicantProfile(String orgIndex)
			throws Exception {

		this.foProj = new FOProject(this.fopp, orgIndex);
		
		if(orgIndex.equals("1")) {
			
			GeneralUtil.switchToFO();
		} else {

			GeneralUtil.switchToFOOnly();
			
			GeneralUtil.loginAnyFO("front" + orgIndex);			
		}

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(this.fopp
				.getProgFullName(), this.foProj.getFoOrgName()),
				"FAIL: Could not register to Funding Opp.!");

		Assert.assertTrue(ApplicantsUtil.openFOApplicantProfile(this.foProj
				.getOrgFullName()), "FAIL: could not open Applicant - "
				+ this.foProj.getOrgFullName());
	}

	public void openFOSubmission(String stepName) throws Exception {

		FrontOfficeUtil.openFOSubmissionForm(this.foProj, stepName);
	}

	public void submitFOSubmission(String stepFullName, String goToStep) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(this.foProj, stepFullName), "FAIL: could not open FO Submission!");
		
		GeneralUtil.takeANap(1.0);

		ie.selectList(0).select(goToStep);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
	}

	/************ End FO Projects, etc ******************/

	/*************************************************
	 * Initializing Lists eForms and Formlets with Data
	 ***********************************************/

	public void setRowsAndIndexes(int rowCount, int startIndex)
			throws Exception {

		this.setRowsCount(rowCount);

		this.setStartIndex(startIndex);

		initLists();
	}

	public void initLists() throws Exception {

		LBFunctionUtil.initilizeMasterList(this);
	}

	
	 public void setAmendmentStatus(String amendmentTtl, String amendmentStatus) throws Exception {
			
		      
			 GeneralUtil.selectInDropdownListByLabel(amendmentTtl,amendmentStatus );			
			 
			 ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		
		}
	 
	 
	 
	public void setEFormData(String stepName) throws Exception {

		initializeStep(stepName);

		ProgramUtil.openStepManagmentDetails(this.eProjType.ordinal(),
				this.stepFullIdent, EstepManagment.formData.ordinal());

		switch (this.getSourceForm()) {

		case org: {
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			EFormsUtil.selectFromDropDown(0, "Organization e.Form");
			if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}

			break;
		}
		
		case proj:{
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			EFormsUtil.selectFromDropDown(0, "Project e.Form");
			if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}			
			break;
		}

		case admin: {
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			EFormsUtil.selectFromDropDown(0, "Administration e.Form");
			if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
			break;
		}

		case profile: {

			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			EFormsUtil.selectFromDropDown(0, "Applicant Profile e.Form");
			if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
			break;
		}

		case submission: {

			initializeStep(ILBFunctionConst.lbf_SubmissionSource[0][0]);
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			if (GeneralUtil.isObjectExistsInList("stepFormData:formDataForm:availableForms",
					this.stepFullIdent)) {
				EFormsUtil.selectFromDropDown(0, this.stepFullIdent);
				if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}
			}

			break;
		}

		case poSubmission: {

			initializeStep(ILBFunctionConst.lbf_POSubmissionSource[0][0]);
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			if (GeneralUtil.isObjectExistsInList("stepFormData:formDataForm:availableForms",
					this.stepFullIdent)) {
				EFormsUtil.selectFromDropDown(0, this.stepFullIdent);
				if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}
			}
			break;
		}

		case claim: {

			initializeStep(ILBFunctionConst.lbf_IPASSource[0][0]);
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			if (GeneralUtil.isObjectExistsInList("stepFormData:formDataForm:availableForms",
					this.stepFullIdent)) {
				EFormsUtil.selectFromDropDown(0, this.stepFullIdent);
				if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}
			}

			break;
		}

		case award: {

			initializeStep(ILBFunctionConst.lbf_AwardSource[0][0]);
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			if (GeneralUtil.isObjectExistsInList("stepFormData:formDataForm:availableForms",
					this.stepFullIdent)) {
				EFormsUtil.selectFromDropDown(0, this.stepFullIdent);
				if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}
			}
			break;
		}

		case standardAgreement: {

			initializeStep(ILBFunctionConst.lbf_StandardAwardSource[0][0]);
			if (!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn)){
				ClicksUtil.clickButtons(IClicksConst.m2MAllBackBtn);
			}
			if (GeneralUtil.isObjectExistsInList("stepFormData:formDataForm:availableForms",
					this.stepFullIdent)) {
				EFormsUtil.selectFromDropDown(0, this.stepFullIdent);
				if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}
			}
			break;
		}
		default:
			break;
		}
		
		GeneralUtil.takeANap(1.0);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);
	}

	/************ End Lists and Formlets with Data ********************/

	/**************************************
	 * Helper Methods
	 **************************************/

	public void initializeStep(String stepName) throws Exception {

		this.stepFullIdent = this.fopp.getProgLetter()
				+ this.fopp.getProgPreFix() + stepName
				+ this.fopp.getProgPostfix();
	}
	
	public void editList() throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks("No Propreties Fields LBF");
		
		TablesUtil.openInTableByImage(ILBFunctionConst.lbf_FormletList_Table_Id, "Approved", 1);
		
		ie.selectList(0).select("Pending Approval");
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickLinks("No Propreties Fields LBF");
		
		TablesUtil.openInTableByImage(ILBFunctionConst.lbf_FormletList_Table_Id, "Pending Approval", 1);
		
		 ie.selectList(0).select("Approved");
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public void submitForm() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.link(text,"/Submission Summary/").click();
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
		returnFromAnyList();

	}

	public void returnFromAnyList() throws Exception {

		if (GeneralUtil
				.isLinkExistsByTxt(IClicksConst.backToMyAssignedSubmissionsListLnk)) {
			ClicksUtil
					.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToApplicantSubListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToAwardListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToSubListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToApplicantsListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToApplicantsListLnk);

			return;
		}

	}

	public void assignOfficerFO() throws Exception {

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });
	}

	public void assignOfficer() throws Exception {

		proj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });
	}

	public void openAwardForm(String eFormName) throws Exception {

		if (proj != null) {
			ProjectUtil.openPOAwardFormletInList(this.proj, eFormName,
					"/Submission Summary/");
		} else {
			ProjectUtil.openPOAwardFormletInList(this.foProj, eFormName,
					"/Submission Summary/");
		}
	}

	public void openSubmissionForm() throws Exception {

		ProjectUtil.openPOSubmissionForm(this.proj,
				ILBFunctionConst.lbf_SubmissionSource[0][0], "Open Projects",
				"Latest Version", "Ready");
	}

	public void fillAndSubmitApplicantSubmission() throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		LBFunctionUtil.insertDataTo_ELists(this);

		ie.link(text,"/Submission Summary/").click();
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		returnFromAnyList();

	}

	public void fillAndSubmitStandardAward(String ipasForm)
			throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		LBFunctionUtil.insertDataTo_ELists(this);

		ie.link(text,"/Submission Summary/").click();
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);


		returnFromAnyList();

	}

	public void fillPOSSAndSubmit(String ipasForm) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		LBFunctionUtil.insertDataTo_ELists(this);

		ie.link(text,"/Submission Summary/").click();
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		returnFromAnyList();

	}
	
	public boolean fillOrgForm() throws Exception {

		if (LBFunctionUtil.verifyFormletAlreadyFilled() < 1) {
			
			LBFunctionUtil.insertDataTo_ELists(this);
		}
		
		
		return true;
	}	
	
	public void fillAdminForm(String ipasForm) throws Exception {

		ClicksUtil.clickLinks(fopp.getProgFullIdent());

		Assert.assertTrue(fopp.openAdminEForm(),
				"FAIL: Could not open Admin eForm!");

		if (LBFunctionUtil.verifyFormletAlreadyFilled() < 1) {
			
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			
			LBFunctionUtil.insertDataTo_ELists(this);
		}

		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);

	}

	public void fillApplicantProfile() throws Exception {

		if (this.isNewOrg()) {

			Assert.assertTrue(ProjectUtil
					.openApplicantProfileFormInPO(this.proj),
					"FAIL: could not open Org - " + this.proj.getOrgFullName());
			
			LBFunctionUtil.insertDataTo_ELists(this);

			ClicksUtil.clickLinks(IClicksConst.backToApplicantsListLnk);

		}

	}

	public void fill_FO_ApplicantProfile() throws Exception {

		if (!LBFunctionUtil.isFOApplicantProfileFilled) {
			
			LBFunctionUtil.insertDataTo_ELists(this);

			LBFunctionUtil.isFOApplicantProfileFilled = true;
		}

		ClicksUtil.clickLinks(IClicksConst.backToApplicantsListLnk);

	}
	
	public boolean fillProjectEForm(Project proj, String stepName, String projectStatus ) throws Exception{
		
		Assert.assertTrue(ProjectUtil.openPOProjectList(this.foProj, stepName,projectStatus));
		
		if ( LBFunctionUtil.verifyFormletAlreadyFilled() < 1)
		{			
			LBFunctionUtil.insertDataTo_ELists(this);
		}

		if ( !ClicksUtil.clickLinks(IClicksConst.stepAmendBackToProjectsListBtn) )
		{
			log.error("Could not click on the link ".concat(IClicksConst.stepAmendBackToProjectsListBtn));
			return false;
		}

		return true;
	}
	
	public boolean updateProjectEForm() throws Exception{
		
		Assert.assertTrue(ProjectUtil.openPOProjectList(this.foProj, "Standard Award","Open Projects"));
		
		
		ClicksUtil.clickButtons(IClicksConst.editBtn);
		
		if ( LBFunctionUtil.verifyFormletAlreadyFilled() >= 1)
		{			
			
			LBFunctionUtil.updateDataTo_ELists(this);
		}

		if ( !ClicksUtil.clickLinks(IClicksConst.stepAmendBackToProjectsListBtn) )
		{
			log.error("Could not click on the link ".concat(IClicksConst.stepAmendBackToProjectsListBtn));
			return false;
		}
		
		return true;
			
	}
	
	
       public boolean createFOProjectNFillProjectEForm(String stepFullName, String gotoStep) throws Exception{
    	   
    	   createFOProjectAndOpenSubmission(stepFullName,"Submission");
    	   
    	   GeneralUtil.switchToPO();
   		
   		   foProj.assignOfficers(new String[][] {
   				{ IPreTestConst.Groups[0][0][0],
   					IPreTestConst.Groups[0][1][0] }});
   		
   		   fillProjectEForm(this.foProj, "Submission", "Open Projects");
		
       
		   return true;
	
	}

	
	

	public boolean fillOrganizationForm(String orgIdent, String orgEFormIdent) throws Exception {

		Assert.assertTrue(OrgUtil.selectAndOpenOrgEForm(orgIdent, orgEFormIdent));

		if ( LBFunctionUtil.verifyFormletAlreadyFilled() < 1)
		{			
			LBFunctionUtil.insertDataTo_ELists(this);
		}

		if ( !ClicksUtil.clickLinks(IClicksConst.orgBackToOrganizationPlanner) )
		{
			log.error("Could not click on the link ".concat(IClicksConst.orgBackToOrganizationPlanner));
			return false;
		}

		return true;
	}
	
	
	
	public boolean updateOrganizationForm(String orgIdent, String orgEFormIdent) throws Exception {

		Assert.assertTrue(OrgUtil.openOrgEForm(orgIdent));

		if ( LBFunctionUtil.verifyFormletAlreadyFilled() >= 1)
		{			
			LBFunctionUtil.updateDataTo_ELists(this);
		}

		if ( !ClicksUtil.clickLinks(IClicksConst.orgBackToOrganizationPlanner) )
		{
			log.error("Could not click on the link ".concat(IClicksConst.orgBackToOrganizationPlanner));
			return false;
		}

		return true;
	}
	
	public void deleteRowsFromAdminForm() throws Exception {

		Assert
				.assertTrue(fopp.openFundingOppPlanner(),
						"FAIL: Could not Open Funding Opp - "
								+ fopp.getProgFullIdent());

		Assert.assertTrue(fopp.openAdminEForm(),
				"FAIL: Could not open Admin eForm!");
		
		LBFunctionUtil.deleteRowFromLists(this);

	}

	public void deleteRowsFromAnySubmission() throws Exception {
		
		LBFunctionUtil.deleteRowFromLists(this);

	}

	public void deleteRowsFromPOApplicantProfile() throws Exception {

		Assert.assertTrue(ProjectUtil.openApplicantProfileFormInPO(this.proj),
				"FAIL: could not open Org - " + this.proj.getOrgFullName());
		
		LBFunctionUtil.deleteRowFromLists(this);

	}

	public boolean verifyFieldsAnswer() throws Exception {

		return LBFunctionUtil.verifyFieldsAnswer(this);
		
		
	}
	
	public boolean verifyFieldsAnswerAmended() throws Exception {

		return LBFunctionUtil.verifyFieldsAnswerAmended(this);
		
		
	}
	
	

	public boolean verifyRows() throws Exception {

		return LBFunctionUtil.verifyRowsCount(this);
	}
	
	public boolean verifyRowsAmended() throws Exception{
		
		
		return LBFunctionUtil.verifyRowsCountAmended(this);
	}

	/******************** end Helper Methods ************/

	/***************************
	 * Getter and Setter
	 **************************/

	/**
	 * @return the eProjType
	 */
	public IProgramsConst.EProjectType getEProjType() {
		return eProjType;
	}

	/**
	 * @param projType
	 *            the eProjType to set
	 */
	public void setEProjType(IProgramsConst.EProjectType projType) {
		eProjType = projType;
	}

	/**
	 * @return the rowsCount
	 */
	public int getRowsCount() {
		return rowsCount;
	}

	/**
	 * @param rowsCount
	 *            the rowsCount to set
	 */
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows
	 *            the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the syncType
	 */
	public int getSyncType() {
		return syncType;
	}

	/**
	 * @param syncType
	 *            the syncType to set
	 */
	public void setSyncType(int syncType) {
		this.syncType = syncType;
	}

	/**
	 * @return the eFormletsName
	 */
	public ILBFunctionConst.EFormletTypes getEFormletsName() {
		return eFormletsName;
	}

	/**
	 * @param formletsName
	 *            the eFormletsName to set
	 */
	public void setEFormletsName(ILBFunctionConst.EFormletTypes formletsName) {
		eFormletsName = formletsName;
	}

	/**
	 * @return the sourceForm
	 */
	public ILBFunctionConst.EeFormsIdentifier getSourceForm() {
		return sourceForm;
	}

	/**
	 * @param sourceForm
	 *            the sourceForm to set
	 */
	public void setSourceForm(ILBFunctionConst.EeFormsIdentifier sourceForm) {
		this.sourceForm = sourceForm;
	}

	/**
	 * @return the newOrg
	 */
	public boolean isNewOrg() {
		return newOrg;
	}

	/**
	 * @param newOrg
	 *            the newOrg to set
	 */
	public void setNewOrg(boolean newOrg) {
		this.newOrg = newOrg;
	}

	/**
	 * @return the eFormletsTypes
	 */
	public ILBFunctionConst.EFormletMainTypes getEFormletsTypes() {
		return eFormletsTypes;
	}

	/**
	 * @param formletsTypes
	 *            the eFormletsTypes to set
	 */
	public void setEFormletsTypes(
			ILBFunctionConst.EFormletMainTypes formletsTypes) {
		eFormletsTypes = formletsTypes;
	}

	/**
	 * @return the deleteRowIndex
	 */
	public int getDeleteRowIndex() {
		return deleteRowIndex;
	}

	/**
	 * @param deleteRowIndex
	 *            the deleteRowIndex to set
	 */
	public void setDeleteRowIndex(int deleteRowIndex) {
		this.deleteRowIndex = deleteRowIndex;
	}

	/**
	 * @return the eFormType
	 */
	public ILBFunctionConst.EeFormsIdentifier getEFormType() {
		return eFormType;
	}

	/**
	 * @param formType
	 *            the eFormType to set
	 */
	public void setEFormType(ILBFunctionConst.EeFormsIdentifier formType) {
		eFormType = formType;
	}
	
   
}
