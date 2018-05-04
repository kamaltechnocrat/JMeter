package test_Suite.tests.r3_4.referenceTable;

import static watij.finders.SymbolFactory.id;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.ReferenceTablesUtil;
import test_Suite.utils.workflow.StepsUtil;
import watij.runtime.ie.IE;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class test_ReferenceTables_Amendments {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	LBF lbf;
	
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToFO();
			
			GeneralUtil.loginAnyFO("front");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", "-Reference-PA-","");

			foProj = new FOProject(fopp, "", true, 1, EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;
		lbf = null;
		
	GeneralUtil.Logoff();
	IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	
	@Test(groups = { "WorkflowNG" })
	public void createNewProjectAndSubmit() throws Exception {
		
		foProj.createFOProjectNewNew();
		
        foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createNewProjectAndSubmit")
	public void submitReview1() throws Exception{
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
		foProj.initializeStep(IGeneralConst.reviewAQuoCritAuto[0][0]);
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewAQuoCritAuto[0][2],IRefTablesConst.statusReady,foProj.getCurrentStepName(), false,IRefTablesConst.versionNumber1, ""));
		
		ReferenceTablesUtil.insertDataToSingleFilterEList();
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
			
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview1")
	public void submitReview2() throws Exception{
		
        foProj.initializeStep(IGeneralConst.reviewBQuoCritAuto[0][0]);
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.reviewBQuoCritAuto[0][2],IRefTablesConst.statusReady,foProj.getCurrentStepName(), false,IRefTablesConst.versionNumber1 , ""));
	
		Assert.assertTrue(ReferenceTablesUtil.insertDataToDoubleFilterEList());
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
       
	}
	

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview2")
	public void submitAward() throws Exception{
		
        GeneralUtil.switchToPO();
		
        foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);
		
	    Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(foProj,IGeneralConst.gnrl_AwardCrit[0][2], foProj.getCurrentStepName()));
		
	    Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData));
		
		Assert.assertTrue(StepsUtil.insertTo_SubScheduleListFormlet1("PEF-Post Award Submission-PushBack",ILBFunctionConst.lbf_SubSchedules_Fields_eList[0]));
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
		
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"submitAward"})
	public void initialClaimSubmit() throws Exception{
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
        foProj.initializeStep(IGeneralConst.initialClaim[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj,  foProj.getCurrentStepName()));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"initialClaimSubmit"})
	public void postAwardReview1() throws Exception{
		
        GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
        foProj.initializeStep(IGeneralConst.reviewQuoCritAutoA[0][0]);
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewQuoCritAutoA[0][2],IRefTablesConst.statusReady, foProj.getCurrentStepName() , false,IRefTablesConst.versionNumber1, ""));
		
        Assert.assertTrue(ReferenceTablesUtil.insertDataToSingleFilterAndVerify());
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"postAwardReview1"})
	public void postAwardReview2() throws Exception{
		
        GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
        foProj.initializeStep(IGeneralConst.reviewQuoCritAutoB[0][0]);
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewQuoCritAutoB[0][2],IRefTablesConst.statusReady, foProj.getCurrentStepName(), false,IRefTablesConst.versionNumber1, ""));
		
        Assert.assertTrue(ReferenceTablesUtil.insertDataToDoubleFilterAndVerify());
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	

	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "postAwardReview2")
	public void openFormInMyAssignedSubmissionsNSubmit() throws Exception
	{
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList_New(
				
				foProj,IGeneralConst.gnrl_Closing_Step[0][0], IRefTablesConst.openProjects));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData));
		
	   Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "openFormInMyAssignedSubmissionsNSubmit")
	public void requestAmendmentFromPO()
	
	{
		try {
			
			GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.submissionA, IRefTablesConst.openProjects),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);
			

			Assert.assertTrue(
					AmendmentsUtil.fillAmendments(
							new String[] {foProj.getProjFullName(),"","",
						                  dd,IGeneralConst.amendmentReason,
						                  IGeneralConst.amendmentComment}, foProj),
					               "FAIL: Could not fill out amendments!");
			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.postAwardDetailsBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);


			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
		
	}
	
	
	@Test(groups = { "WorkflowNG" },  dependsOnMethods = "requestAmendmentFromPO")
    public void amendFOSubmission() throws Exception
    
    {
   	 GeneralUtil.switchToFO();
   	 
   	foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
	
	Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()));
	
   	ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
   	 
   	 ClicksUtil.clickButtons(IClicksConst.submitBtn);
   	 
   	 ClicksUtil.clickButtons(IClicksConst.backToSubListLnk);
   	 
   	 
    }
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendFOSubmission")
	public void amendReview1() throws Exception{
		
        GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
		foProj.initializeStep(IGeneralConst.reviewAQuoCritAuto[0][0]);
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewAQuoCritAuto[0][2],IRefTablesConst.statusInProgress,foProj.getCurrentStepName(), false,IRefTablesConst.versionNumber2, ""));
		
		Assert.assertTrue(ReferenceTablesUtil.amend_singleFilter_data(IRefTablesConst.budgetYear_Fields_eList_Amend[0]));
			
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendReview1", dataProvider="BudgetData")
	public void verifySingleFilterEList( String[] strsToFind) throws Exception
	{
		
		Assert.assertTrue(ReferenceTablesUtil.verify_singleFilterEList_amendedData(strsToFind));
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifySingleFilterEList")
	public void amendReview2() throws Exception{
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
		
        foProj.initializeStep(IGeneralConst.reviewBQuoCritAuto[0][0]);
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewBQuoCritAuto[0][2],IRefTablesConst.statusInProgress,foProj.getCurrentStepName(), false,IRefTablesConst.versionNumber2, ""));
		
		Assert.assertTrue(ReferenceTablesUtil.amend_doubleFilter_data(IRefTablesConst.yearlyLimit_Fields_eList_Amend[0]));
	
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendReview2", dataProvider="BudgetData")
	public void verifyDoubleFilterEList(String[] strsToFind) throws Exception
	{

		Assert.assertTrue(ReferenceTablesUtil.verify_doubleFilterEList_amendedData(strsToFind));
		
	}

	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyDoubleFilterEList")
	public void amendAward() throws Exception{
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
		IE ie = IEUtil.getActiveIE();
		
        GeneralUtil.switchToPO();
        
        foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);
		
		Assert.assertTrue(ProjectUtil.openPOAwardFormletInList_New(foProj, IGeneralConst.gnrl_AwardCrit[0][0],  foProj.getCurrentStepName(), false,IRefTablesConst.versionNumber2,""));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData1));
		
		ie.table(id,IRefTablesConst.submissionSchedule_TableId).body(id,IRefTablesConst.submissionSchedule_TableId).row(0).link(0).click();
		
		Assert.assertTrue(StepsUtil.insertTo_SubScheduleListFormlet1_New("PEF-Post Award Submission-PushBack",ILBFunctionConst.lbf_SubSchedules_Fields_eList[1]));
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendAward")
	public void changeAmender() throws Exception {
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk));
		
		Assert.assertTrue(AmendmentsUtil.filterAmendmentList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.amendments_AllAmendmentsView));
		
		Assert.assertTrue(AmendmentsUtil.changeAmender(foProj,IRefTablesConst.changeAmender));
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "changeAmender")
	public void amend_InitialClaim() throws Exception
	{
		foProj.setClaimNumber(2);
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList_New(
				
		foProj,IGeneralConst.initialClaim[0][0], IRefTablesConst.openProjects));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData2));
		
	   Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"amend_InitialClaim"})
	public void amend_postAwardReview1() throws Exception{
		
        GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewQuoCritAutoA[0][2],IRefTablesConst.statusInProgress, IGeneralConst.reviewQuoCritAutoA[0][0], false,IRefTablesConst.versionNumber2, ""));
		
		Assert.assertTrue(ReferenceTablesUtil.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList_Amend[1]));
		
		Assert.assertTrue(ReferenceTablesUtil.verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList_Amend[1]));
	
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"amend_postAwardReview1"})
	public void amend_postAwardReview2() throws Exception{
		
        GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj,IGeneralConst.reviewQuoCritAutoB[0][2],IRefTablesConst.statusInProgress, IGeneralConst.reviewQuoCritAutoB[0][0], false, IRefTablesConst.versionNumber2, ""));
		
		Assert.assertTrue(ReferenceTablesUtil.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList_Amend[1]));
		
		Assert.assertTrue(ReferenceTablesUtil.verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields_eList_Amend[1]));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amend_postAwardReview2")
	public void amendFormInMyAssignedSubmissionsNSubmit() throws Exception
	{
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList_New(
				
				foProj,IGeneralConst.gnrl_Closing_Step[0][0],IRefTablesConst.openProjects));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData));
		
	   Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	@DataProvider(name = "BudgetData")
	public Object[][] generateFormletTypes(Method method)
	{
		Object[][] result = null;

		if ( method.getName() == "verifySingleFilterEList"
			|| method.getName() == "verifyDoubleFilterEList")
		{
			result = new Object[][] {
					
					{IRefTablesConst.verify_yearlyLimit_Fields_eList_Amend[0]}
		
					 };
		}

		return result;
	}
	
	
	
}