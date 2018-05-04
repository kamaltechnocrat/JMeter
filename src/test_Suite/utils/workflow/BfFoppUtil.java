/**
 * 
 */
package test_Suite.utils.workflow;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EDocumentsFormat;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IBf_FoppConst;
import test_Suite.constants.workflow.IBf_FoppConst.EPostFix;
import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IStepsConst.EStepsType;
import test_Suite.lib.workflow.FOPPSteps;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantsUtil;
import watij.elements.Table;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class BfFoppUtil {
	
	public static FundingOpportunity initFopp(EPostFix eFix) throws Exception {
		
		FundingOpportunity fopp = new FundingOpportunity(eFix);
		
		LinkedHashMap<EStepsType,FOPPSteps> steps = new LinkedHashMap<EStepsType,FOPPSteps>();
		
		steps.put(EStepsType.SUB, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Submission,EStepsType.SUB));
		steps.put(EStepsType.REVIEW, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Review,EStepsType.REVIEW));
		steps.put(EStepsType.POSS, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_PO_Submission,EStepsType.POSS));
		steps.put(EStepsType.APPROVAL, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Approval,EStepsType.APPROVAL));
		steps.put(EStepsType.AWARD, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_AwardCrit,EStepsType.AWARD));
		
		fopp.setFoppSteps(steps);
		fopp.setSetSteps(steps.entrySet());
		
		return fopp;
		
	}
	
	public static FundingOpportunity initializeFopp(EPostFix eFix) throws Exception {
		
		FundingOpportunity fopp = new FundingOpportunity(EFormsUtil.createAnyRandomString(5),IBf_FoppConst.preFix,IBf_FoppConst.postFix[eFix.ordinal()]);
		
		LinkedHashMap<EStepsType,FOPPSteps> steps = new LinkedHashMap<EStepsType,FOPPSteps>();
		
		steps.put(EStepsType.SUB, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Submission,EStepsType.SUB));
		steps.put(EStepsType.REVIEW, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Review,EStepsType.REVIEW));
		steps.put(EStepsType.POSS, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_PO_Submission,EStepsType.POSS));
		steps.put(EStepsType.APPROVAL, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Approval,EStepsType.APPROVAL));
		steps.put(EStepsType.AWARD, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_AwardCrit,EStepsType.AWARD));
		
		fopp.setFoppSteps(steps);
		fopp.setSetSteps(steps.entrySet());
		
		return fopp;
		
	}
	
	public static FundingOpportunity initializeLessStepsFopp(EPostFix eFix) throws Exception {
		
		FundingOpportunity fopp = new FundingOpportunity(EFormsUtil.createAnyRandomString(5),IBf_FoppConst.preFix,IBf_FoppConst.postFix[eFix.ordinal()]);
		
		LinkedHashMap<EStepsType,FOPPSteps> steps = new LinkedHashMap<EStepsType,FOPPSteps>();
		
		steps.put(EStepsType.SUB, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Submission,EStepsType.SUB));
		steps.put(EStepsType.REVIEW, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Review,EStepsType.REVIEW));
		steps.put(EStepsType.APPROVAL, FOPPUtil.getStep(fopp,IBf_FoppConst.bf_FOPP_Approval,EStepsType.APPROVAL));
		//steps.put(EStepsType.POSS, FOPPUtil.getStep(fopp,IBF_FromFOPPConst.bf_FOPP_PO_Submission,EStepsType.POSS));
		//steps.put(EStepsType.AWARD, FOPPUtil.getStep(fopp,IBF_FromFOPPConst.bf_FOPP_AwardCrit,EStepsType.AWARD));
		
		fopp.setFoppSteps(steps);
		fopp.setSetSteps(steps.entrySet());
		
		return fopp;
		
	}
	
	public static boolean doesAwardedStepAvailable(FundingOpportunity fopp) throws Exception {
		
		if(fopp.selectAwardedStep()) {
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			return true;
		}
		
		return false;
	}
	
	public static boolean cloneAndOpenClonedFOPP(FundingOpportunity fopp) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		Assert.assertTrue(FOPPUtil.cloneFOPP(fopp),"FAIL: could not cloned FOPP " + fopp.getFoppFullIdent());
		
		String foppIdent = FOPPUtil.verifyClonedFOPP(fopp);
		
		if(!foppIdent.contentEquals("")) {
			ClicksUtil.clickLinks(foppIdent);
			
			return true;
		}		
		return false;
	}
	
	public static Project initializeAndCreateNewProject(FundingOpportunity fopp, EProjNames eProjName) throws Exception {	
	
		Project proj = new Project(fopp,IBf_FoppConst.projNames[eProjName.ordinal()], IBf_FoppConst.NEW_PROJ,IBf_FoppConst.NEW_ORG,EFormsUtil.createAnyRandomString(5));
		
		proj.newCreateNewOrg();
		completePOWorkspace(proj);
		proj.newCreateNewPOProject();		
		
		return proj;		
	}
	
	public static Project initializeTargetProject(FundingOpportunity fopp,Project srcProj,boolean isImportData, EProjNames eProjName) throws Exception {
		
		Project proj = new Project(fopp,IBf_FoppConst.projNames[eProjName.ordinal()], IBf_FoppConst.NEW_PROJ,IBf_FoppConst.EX_ORG,srcProj.getOrgLetter());
		
		if(isImportData) {
			
			proj.setImportDataFromProject(true);
			proj.setSourceProjectFullName(srcProj.getProjFullName());
			proj.setSrcProjectFullNameWithNumber(srcProj.getProjFullNameWithProjNumber());
		}		
		return proj;		
	}
	
	public static boolean createFOProject(FOProject foProj) throws Exception {
		
		return foProj.createFOProjectNew();
	}
	
	public static boolean createProject(Project proj) throws Exception {
		
		return proj.newCreateNewPOProject();
	}
	
	public static FOProject initializeFOSourceProject(FundingOpportunity fopp,EProjNames eProjName, Integer orgIndex) throws Exception {
		
		FOProject foProj = new FOProject(fopp,IBf_FoppConst.projNames[eProjName.ordinal()],IBf_FoppConst.NEW_PROJ,orgIndex,EFormsUtil.createAnyRandomString(5));
		
		foProj.initilizeWizard(getWizardParamsObjects());
		
		return foProj;
	}
	
	public static FOProject initializeFOTargetProject(FundingOpportunity fopp,Project srcProj,boolean isImportData, EProjNames eProjName, Integer orgIndex) throws Exception {
		
		FOProject foProj = new FOProject(fopp,IBf_FoppConst.projNames[eProjName.ordinal()],IBf_FoppConst.NEW_PROJ,orgIndex,srcProj.getOrgLetter());
		
		foProj.initilizeWizard(getWizardParamsObjects());
		
		if(isImportData) {
			
			foProj.setImportDataFromProject(true);
			foProj.setSourceProjectFullName(srcProj.getProjFullName());
			foProj.setSrcProjectFullNameWithNumber(srcProj.getProjFullNameWithProjNumber());
		}
		
		return foProj;
	}
	
	public static Object[] getWizardParamsObjects() throws Exception {
		
		return new Object[] {"the",Boolean.FALSE,"front",Boolean.FALSE,"Organization","Ouia 1","Applicant-123-1",Boolean.FALSE,Boolean.TRUE,Boolean.FALSE};
	}
	
	public static List<String[]> initializeAttachment() throws Exception {
		
		List<EDocumentsFormat> eDocFormat = new ArrayList<EDocumentsFormat>();
		
		eDocFormat.add(0, EDocumentsFormat.doc);
		eDocFormat.add(1, EDocumentsFormat.pdf);
		eDocFormat.add(2, EDocumentsFormat.rtf);
		/*eDocFormat.add(3, EDocumentsFormat.xlsx);
		eDocFormat.add(4, EDocumentsFormat.any);*/
		
		List<String[]> attachLst = new ArrayList<String[]>();
		
		for (EDocumentsFormat eFormat : eDocFormat) {
			
			attachLst.add(ILBFunctionConst.lbf_attachments_Fields_eList[eFormat.ordinal()]);			
		}

		return attachLst;
	}	
	
	public static Project prepareSourceData(EProjNames epNames) throws Exception {
		
		FundingOpportunity fopp = initFopp(EPostFix.SRC);
		
		Project Proj = initializeAndCreateNewProject(fopp,epNames);
		
		return Proj;
	}	
	
	public static Project prePareTarget(Project srcProj,EProjNames epNames) throws Exception {
		
		FundingOpportunity fopp = initFopp(EPostFix.TRGT);
	
		Project proj = initializeTargetProject(fopp,srcProj,true,epNames);
		
		return proj;		
	}	
	
	public static Project prePareAwardedTarget(Project srcProj,EProjNames epNames) throws Exception {
		
		FundingOpportunity fopp = initFopp(EPostFix.AWARDED);
	
		Project proj = initializeTargetProject(fopp,srcProj,true,epNames);
		
		return proj;		
	}
	
	public static FOProject prepareFOSourceData(EProjNames epNames, Integer orgIndex) throws Exception {
		
		FundingOpportunity fopp = initFopp(EPostFix.SRC);
		
		FOProject foProj = initializeFOSourceProject(fopp,epNames,orgIndex);
		
		Assert.assertTrue(registerToFopp(fopp,foProj),"FAIL: Could not register to Funding Opp.! " + fopp.getFoppFullName());
		
		Assert.assertTrue(createFOProject(foProj), "FAIL: could not create Source Project! " + foProj.getProjFOFullName());
		
		return foProj;
	}	
	
	public static FOProject prePareFOTarget(FOProject srcFOProj,EProjNames epNames, Integer orgIndex) throws Exception {
		
		FundingOpportunity fopp = initFopp(EPostFix.TRGT);
	
		FOProject foProj = initializeFOTargetProject(fopp,srcFOProj,true,epNames,orgIndex);	
		
		Assert.assertTrue(registerToFopp(fopp,foProj),"FAIL: Could not register to Funding Opp.! " + fopp.getFoppFullName());
		
		return foProj;		
	}	
	
	public static FOProject prePareAwardedFOTarget(FOProject srcFOProj,EProjNames epNames, Integer orgIndex) throws Exception {
		
		FundingOpportunity fopp = initFopp(EPostFix.AWARDED);
	
		FOProject foProj = initializeFOTargetProject(fopp,srcFOProj,true,epNames,orgIndex);	
		
		Assert.assertTrue(registerToFopp(fopp,foProj),"FAIL: Could not register to Funding Opp.! " + fopp.getFoppFullName());
		
		return foProj;		
	}
	
	public static boolean registerToFopp(FundingOpportunity fopp,FOProject foProj) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		return FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName());
	}
	
	public static boolean submitFOApplication(FOProject foProj) throws Exception {
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj,IBf_FoppConst.bf_FOPP_Submission[0][0]));
		
		upload_Attachments();
		
		if(submitForm()) {
			returnFromAnyList();
			
			return true;
			
		}
		returnFromAnyList();		
		
		return false;
	}
	
	public static boolean submitFOTargetApplication(FOProject foProj) throws Exception {
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj,IBf_FoppConst.bf_FOPP_Submission[0][0]), "FAIL: Could not open fo submission Form!");
		
		if(submitForm()) {
			returnFromAnyList();
			
			return true;
			
		}
		returnFromAnyList();		
		
		return false;
	}
	
	

	public static void upload_Attachments() throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		List<String[]> lst = initializeAttachment();

		for (String[] arr : lst) {

			ie.body(id, ILBFunctionConst.lbf_AttachmentList_Table_Id).link(text, arr[0])
					.click();

			ie.textField(id, ILBFunctionConst.lbf_AttachmentDetails_DocDescription_FieldId).set(
					arr[2]);

			ie.fileField(id, ILBFunctionConst.lbf_AttachmentDetails_FileUpload_FieldId).set(
					"\"" + GeneralUtil.getWorkspacePath() + ILBFunctionConst.lbf_DocsFilesPath
							+ arr[5] + "\"");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		}
	}
	
	public static void completePOWorkspace(Project proj) throws Exception {
		
		ProjectUtil.openApplicantProfileFormInPO(proj);
		
		String[] strArr = IRptFuncConst.reportFunc_eListFormlet[0];
		
		editWorkspace(strArr);

		//ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		
		GeneralUtil.takeANap(1.0);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.0);
		
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		
		submitForm();
		returnFromAnyList();		
	}
	
	public static void completeFOWorkspace(FOProject foProj) throws Exception {
		
		ApplicantsUtil.openFOApplicantProfile(foProj.getOrgFullName());
		
		String[] strArr = IRptFuncConst.reportFunc_eListFormlet[0];
		
		editWorkspace(strArr);
		
		GeneralUtil.takeANap(1.0);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		GeneralUtil.takeANap(1.0);
		
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

		//ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		
		submitForm();
		returnFromAnyList();		
	}
	
	public static void editWorkspace(String[] strArr) throws Exception {		

		ClicksUtil.clickLinks("eList eFormField");
		
		if (GeneralUtil.isImageExistsBySrc(IClicksConst.newImg)){

		     ClicksUtil.clickImage(IClicksConst.newImg);	
		     
		EFormsUtil.enterTextToTextField(0,strArr[0]);
		EFormsUtil.enterTextToTextField(1,strArr[1]);
		EFormsUtil.enterTextToTextField(2,strArr[2]);
		EFormsUtil.enterTextToTextField(3,strArr[3]);
		EFormsUtil.enterTextToTextField(4,strArr[4]);
		EFormsUtil.enterTextToTextField(5,strArr[5]);

		EFormsUtil.selectFromDropDown(0,strArr[6]);
		EFormsUtil.selectFromDropDown(1,strArr[7]);		
		
		}
		
		else ClicksUtil.clickLinks("Back to Applicants List");
	}
	
	public static void openAndFillAdminEForm(FundingOpportunity fopp) throws Exception {
		
		Assert.assertTrue(fopp.openFundingOppPlanner(), "FAIL: Could not open FOPP planner for: " + fopp.getFoppFullIdent());
		
		Assert.assertTrue(fopp.openAdminEForm(), "FAIL: could not open Admin eForm for FOPP: " + fopp.getFoppFullIdent());
		
		for (int x = 1; x < 12; x += 2) {
			
			EFormsUtil.taggleCheckbox(x,true);
		}

		EFormsUtil.taggleRadioButton(0,true);

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		returnFromAnyList();
	}
	
//	public static void openAndFillPublicationEForm(FundingOpportunity fopp) throws Exception {
//		
//		Assert.assertTrue(fopp.openFundingOppPlanner(), "FAIL: Could not open FOPP planner for: " + fopp.getFoppFullIdent());
//		
//		Assert.assertTrue(fopp.openPublicationEForm(), "FAIL: could not open Publication form for: " + fopp.getFoppFullIdent());		
//		
//		EFormsUtil.enterTextToTextField(0, "The BF from Funding Opp");
//		
//		ClicksUtil.clickButtons(IClicksConst.saveBtn);
//		
//		ClicksUtil.clickButtons(IClicksConst.completeBtn);
//		
//		returnFromAnyList();		
//	}
	
 public static void openAndFillPublicationEForm(FundingOpportunity fopp) throws Exception {
		
		Assert.assertTrue(fopp.openFundingOppPlanner(), "FAIL: Could not open FOPP planner for: " + fopp.getFoppFullIdent());
		
		Assert.assertTrue(fopp.openPublicationEForm(), "FAIL: could not open Publication form for: " + fopp.getFoppFullIdent());
		
		if(GeneralUtil.isTextFieldReadOnlyByTtl("Enter Any Text")){
			
			ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
			
		}
		else{
		
		EFormsUtil.enterTextToTextField(0, "The BF from Funding Opp");
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.completeBtn);
		
		returnFromAnyList();	
		
		}
	}
	
	public static boolean openAndSubmitTargetApplicantion(Project proj) throws Exception {
		
		Assert.assertTrue(ProjectUtil.openPOSubmissionForm(proj, IBf_FoppConst.bf_FOPP_Submission[0][0], "Open Projects","Latest Version", "Ready"),"FAIL: Could not open Submission");
			
			if(submitForm()) {
				returnFromAnyList();	
			
				assignOfficers(proj);
				
				return true;
			}

		returnFromAnyList();	
		
		return false;
	}
	
	public static boolean openAndSubmitTargetReviewSubmission(Project proj, String status) throws Exception {
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(proj, IBf_FoppConst.bf_FOPP_Review[0][0], status, "Min-And-Max-Properties-eFormFields", false));
		
		if(submitForm()) {
			
			returnFromAnyList();			
			return true;
		}

		returnFromAnyList();		
		
		return false;
	}
	
	public static boolean openAndSubmitTargetPO_Submission(Project proj)throws Exception {
		
		String eFormFullName = (IBf_FoppConst.bf_FOPP_PO_Submission[0][0]);
		
		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, eFormFullName, "Open Projects"));
		
		if(submitForm()) {
			
			returnFromAnyList();			
			return true;
		}
		
		return false;
	}
	
	public static boolean openAndSubmitTargetApprovalSubmission(Project proj, String status) throws Exception {
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(proj, IBf_FoppConst.bf_FOPP_Approval[0][0], status, "No-Properties-eList-eFormField", false));
		
		if(submitForm()) {
			
			returnFromAnyList();			
			return true;
		}		
		
		return false;
	}
	
	public static boolean openAndSubmitTargetAward(Project proj)throws Exception {		
		
		String eFormFullName = (IBf_FoppConst.bf_FOPP_AwardCrit[0][0]);
		
		Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(proj, eFormFullName, "Agreement Details eFormField"));
		
		if(submitForm()) {
			
			returnFromAnyList();			
			return true;
		}
		
		return false;		
	}
	
	public static void submitApplication(Project proj) throws Exception {
		
		ProjectUtil.openPOSubmissionForm(proj,
				IBf_FoppConst.bf_FOPP_Submission[0][0], "Open Projects",
			"Latest Version", "Ready");	
		
		upload_Attachments();
		
		submitForm();
		returnFromAnyList();	
		
		assignOfficers(proj);
		
	}
	
	public static void assignOfficers(Project proj) throws Exception {
		
		proj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[0][1][0] } });		
	}
	
	public static void submitReview(Project proj) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		String[] minMax = IRptFuncConst.reportFunc_MinAndMaxFormlet[0];
		
		ProjectUtil.openEvaluateSubmissionFormletInList(proj, IBf_FoppConst.bf_FOPP_Review[0][0], "Ready", "Min-And-Max-Properties-eFormFields", false);
		
		
		//the constant names does not reflect the actual field names, but their indexes in the formlet matches
		ie.textField(id,"/:0:textArea_Below/").set(EFormsUtil.createRandomString(minMax[0], Integer.parseInt(minMax[1])));
		ie.textField(id,"/:1:textBox/").set(minMax[1]);
		ie.textField(id,"/:2:textBox/").set(EFormsUtil.createRandomString(minMax[3], Integer.parseInt(minMax[4])));
		ie.textField(id,"/:3:textBox/").set(minMax[4]);
		ie.textField(id,"/:4:textBox/").set(minMax[5]);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		submitForm();
		returnFromAnyList();		
	}
	
	public static void submitPO_Submission(Project proj) throws Exception {
		
		String eFormFullName = (IBf_FoppConst.bf_FOPP_PO_Submission[0][0]);
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, eFormFullName, "Open Projects");
		
		for (int x = 0; x < 13; x += 2) {
			
			EFormsUtil.taggleCheckbox(x,true);
		}

		EFormsUtil.taggleRadioButton(7,true);

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);	
		
		submitForm();
		returnFromAnyList();		
	}
	
	public static void submitApproval(Project proj) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		List<String[]> lstArr = new ArrayList<String[]>();
		
		lstArr.add(0, ILBFunctionConst.lbf_NoProperties_Fields_eList[0]);
		lstArr.add(1, ILBFunctionConst.lbf_NoProperties_Fields_eList[1]);
		
		ProjectUtil.openEvaluateSubmissionFormletInList(proj, IBf_FoppConst.bf_FOPP_Approval[0][0], "Ready", "No-Properties-eList-eFormField", false);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		for (String[] strings : lstArr) {
			
			ie.checkbox(id, "/0:booleanProperty/").set(
					Boolean.parseBoolean(strings[1]));

			ie.textField(id, "/1:datePicker/").set(
					GeneralUtil.getTodayDate());

			ie.textField(id, "/2:textBox/").set(strings[3]);

			ie.textField(id, "/3:textBox/").set(strings[4]);

			ie.textField(id, "/4:textBox/").set(strings[5]);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

		}
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		
		ClicksUtil.clickLinks("Dropdown eFormFields");
		

		ie.selectList(id, "g3-form:eFormFieldList:0:numericDropdown_input").select("Approved");
		GeneralUtil.takeANap(2.5);
		ie.selectList(id,"g3-form:eFormFieldList:1:numericDropdown_input").select("E-mail");
		//ClicksUtil.clickButtons(IClicksConst.saveBtn);
		GeneralUtil.takeANap(2.5);
		ie.selectList(id,"g3-form:eFormFieldList:2:numericChildDropdown_input").select("Blue");
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		submitForm();
		returnFromAnyList();		
	}
	
	public static void submitAward(Project proj) throws Exception {
		
		String eFormFullName = (IBf_FoppConst.bf_FOPP_AwardCrit[0][0]);
		
		ProjectUtil.openPOAwardFormletInList(proj, eFormFullName, "Agreement Details eFormField");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award"), "FAIL: Could not set text filed in Agreement Details");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		ClicksUtil.clickLinks("PA Submission Schedule");
		
		List<String[]> lstArr = new ArrayList<String[]>();
		
		lstArr.add(0, ILBFunctionConst.lbf_SubSchedules_Fields_eList[0]);
		lstArr.add(1, ILBFunctionConst.lbf_SubSchedules_Fields_eList[1]);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		for (String[] arr : lstArr) {
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,arr[0]),"FAIL: could not set Claim Name!");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, GeneralUtil.getTodayDate()), "FAIL: Could not set the Start Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, GeneralUtil.getNextYear()),"FAIL: Could not set Due Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()),"FIAL: Could not set End Date");
			
			//Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, IGeneralConst.initialClaim[1][0]),"FAIL: Could not select PA Submission Form!");

			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(arr[4])), "FAIL: Could not set Required checkbox!");
			
			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, Boolean.parseBoolean(arr[5])), "FAIL: Could not set Program Office Only Checkbox");

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		
		submitForm();
		returnFromAnyList();
	}
	
	public static void processAmendmentAndReSubmit(Project proj) throws Exception {
		
		String eName = IBf_FoppConst.bf_FOPP_Submission[0][0].replace('-', ' ');


		String dd = GeneralUtil.setDayofMonth(3);
		
		proj.setClaimNumber(0);

		Assert.assertTrue(AmendmentsUtil
				.issueAmendment(new String[] {
						proj.getProjFullName(), eName,
						IPreTestConst.Groups[0][1][0], dd,
						"Test Re-Execute on Previous Amendment",
						"This a Comment" }, proj));
		
		BfFoppUtil.openApplicantSubmission(proj,false, "In Progress");
		
		LBFunctionUtil.deleteRows(3);
		BfFoppUtil.submitForm();
		BfFoppUtil.returnFromAnyList();
	}	
	
	public static void openWorkspace(Project proj) throws Exception {
		
	}
	
	public static void openFOApplicantSubmission(FOProject foProj, boolean submitForm) throws Exception {
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj,IBf_FoppConst.bf_FOPP_Submission[0][0]));
		
		ClicksUtil.clickLinks("Attachment List eFormField");		
	}
	
	public static void openApplicantSubmission(Project proj, boolean submitForm, String status) throws Exception {
		
		Assert.assertTrue(ProjectUtil.openPOSubmissionForm(proj, IBf_FoppConst.bf_FOPP_Submission[0][0], "Open Projects","Latest Version", status),"FAIL: Could not open Submission");
		
		ClicksUtil.clickLinks("Attachment List eFormField");		
	}
	
	public static int testHowManyEntriesInAttachmentList() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		//Table table = ie.table(id, "/data/");
		
		TableRows tableRows = ie.body(id,"g3-form:data_data").rows();
		
		int attCount = 0;
		
		for (TableRow tableRow : tableRows) {
			
			if(tableRow.cell(3).image(alt, "Download").exists()) {
				attCount++;
			}
		}
		
		
		return attCount;
	}
	
	public static void openEvaluation(Project proj, String stepType) throws Exception {
		
	}
	
	public static void openPO_Submission(Project proj) throws Exception {
		
	}
	
	public static void openAward(Project proj) throws Exception {
		
	}
	
	public static boolean submitForm() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
		
		if(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			return true;
		}
		
		if(GeneralUtil.isButtonEnabled(IClicksConst.completeBtn)) {
			ClicksUtil.clickButtons(IClicksConst.completeBtn);
			return true;
		}
		
		return false;
	}

	public static void returnFromAnyList() throws Exception {

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToMyAssignedSubmissionsListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

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
		
		if(GeneralUtil.isLinkExistsByTxt(IClicksConst.backToAssignedSubListLnk)) {

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);
			return;
		}
		
		if(GeneralUtil.isLinkExistsByTxt(IClicksConst.backToFundingOppPlannerLnk)) {

			ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
			return;
		}
		
		if(GeneralUtil.isLinkExistsByTxt(IClicksConst.backToSubListLnk)) {

			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			return;
		}
	}	
	
}
