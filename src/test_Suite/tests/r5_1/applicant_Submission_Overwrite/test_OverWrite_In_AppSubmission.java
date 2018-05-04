package test_Suite.tests.r5_1.applicant_Submission_Overwrite;

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
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.StepsUtil;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class test_OverWrite_In_AppSubmission {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
				log.warn("Starting: " + this.getClass().getSimpleName());

				IEUtil.openNewBrowser();
			
				GeneralUtil.navigateToFO();
			
				GeneralUtil.loginAnyFO("front");
				// -----------------------------------
			
				fopp = new FundingOpportunity(IFoppConst.fopp_Letter, IFoppConst.bfFOPP_prefix, IFoppConst.fopp_postFix);
			
				foProj = new FOProject(fopp, "", true, 1, "");
			

		   } catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() 
	{
		
		fopp = null;
		foProj = null;
		
	    GeneralUtil.Logoff();
	    IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	
	@Test(groups = { "WorkflowNG" })
	public void createNewProjectAndSubmit() throws Exception 
	{
		
		Assert.assertTrue(foProj.createFOProjectNewNew());
		
        foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IFoppConst.contactName, IFoppConst.textToEnter));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IFoppConst.equipment, IFoppConst.equipmentExpense));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IFoppConst.operational, IFoppConst.operationalExpense));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createNewProjectAndSubmit")
	public void submitReview1() throws Exception{
		
		GeneralUtil.switchToPOOnly();
			
		GeneralUtil.loginReviewer("1");
		
		foProj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);
			
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.reviewQuoCritAuto[0][2],
				
				      IRefTablesConst.statusReady, foProj.getCurrentStepName(), false, IRefTablesConst.versionNumber1, ""));
		
	    Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectYes, 
	    		
	    		        IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score));
	
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
	    
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview1")
	public void submitAward() throws Exception{
		
        GeneralUtil.switchToPO();
        
        foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);
		
	    Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(foProj,IGeneralConst.gnrl_AwardCrit[0][2], foProj.getCurrentStepName()));
		
	    Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.agreementTypeTtl, IRefTablesConst.agreementTypeTxt));
		
        Assert.assertTrue(StepsUtil.insertTo_SubScheduleListFormlet1(IGeneralConst.awardFormName,ILBFunctionConst.lbf_SubSchedules_Fields_eList[0]));
                                                                      
	    ClicksUtil.clickButtons(IClicksConst.submitBtn);
	
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "submitAward" })
	public void amendmentRequestByPO() throws Exception {
		try {

			GeneralUtil.switchToPO();
			
			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.pa_AwardCrit[0][0],
							IRefTablesConst.openProjects),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.pa_AwardCrit[0][0],
									IPreTestConst.Groups[0][1][0], dd,
									IGeneralConst.amendmentReason,
									IGeneralConst.amendmentComment }, foProj, IGeneralConst.amendmentCorrective),
					"FAIL: Could not fill out amendments!");
			
			ClicksUtil.clickButtons(IClicksConst.nextBtn);

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendmentRequestByPO")
	public void amendAward() throws Exception{
		
        GeneralUtil.switchToPO();
        
        foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);
		
	    Assert.assertTrue(ProjectUtil.openPOAwardFormletInList_New(foProj,IGeneralConst.gnrl_AwardCrit[0][2], foProj.getCurrentStepName(),false, IRefTablesConst.versionNumber2,""));
		
	    Assert.assertTrue(GeneralUtil.setTextByTitle(IRefTablesConst.requestedEquipmentFundingTtl, IRefTablesConst.equipmentTxt));
	
	    Assert.assertTrue(GeneralUtil.setTextByTitle(IRefTablesConst.operationalBFTtl,IRefTablesConst.operationalTxt));
	    
	    Assert.assertTrue(GeneralUtil.setTextByTitle(IRefTablesConst.requestedTotalTtl, IRefTablesConst.totalTxt));
		
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn));
	    
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn));
                                                                      
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
	
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendAward")
	public void openApplicantSubmission() throws Exception 
	{
		GeneralUtil.switchToFO();
		
        foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()));
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "openApplicantSubmission", dataProvider = "ExpenseData")
	public void verifyAppSubmissionData(String[] strToFind) throws Exception
	{
	
		Assert.assertTrue(LBFunctionUtil.verify_ApplicantSubmissionFields(strToFind));
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"verifyAppSubmissionData"})
	public void initialClaimSubmit() throws Exception
	{
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
		foProj.initializeStep(IGeneralConst.initialClaim[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj,foProj.getCurrentStepName()));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn), "Fail: Mandatory fields are not filled in, data not brought forward");
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "initialClaimSubmit" })
	public void initialClaimAmendment() throws Exception {
		try {

			GeneralUtil.switchToPO();
			
			foProj.setClaimNumber(1);
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyProjectSubmissionsList(foProj,IGeneralConst.initialClaim[0][0],IRefTablesConst.openProjects),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);
			
			Assert.assertTrue(AmendmentsUtil.fillOutAmendments(new String[] { foProj.getProjFullName(),
					                IGeneralConst.initialClaim[0][0],
									IPreTestConst.FrontUsers[1][2], dd,
									IGeneralConst.amendmentReason,
									IGeneralConst.amendmentComment }, foProj, IGeneralConst.amendmentCorrective),
					                "FAIL: Could not fill out amendments!");
			
			ClicksUtil.clickButtons(IClicksConst.nextBtn);

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"initialClaimAmendment"})
	public void modifyInitialClaim() throws Exception
	{
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
		foProj.initializeStep(IGeneralConst.initialClaim[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj,foProj.getCurrentStepName()));
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IRefTablesConst.equipmentExpenseTtl, IRefTablesConst.equipmentTxt));
			
		Assert.assertTrue(GeneralUtil.setTextByTitle(IRefTablesConst.operationalExpenseTtl,IRefTablesConst.operationalTxt));
		  
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "modifyInitialClaim")
	public void open_App_Submission() throws Exception 
	{
         GeneralUtil.switchToFO();
		
        foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()));
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "open_App_Submission", dataProvider = "ExpenseData")
	public void verify_AppSubmission_data(String[] strToFind) throws Exception
	{
	
		Assert.assertTrue(LBFunctionUtil.verify_ApplicantSubmissionFields(strToFind));
	}
	
	
	
	
	@DataProvider(name = "ExpenseData")
	public Object[][] generateFormletTypes(Method method)
	{
		Object[][] result = null;

		if ( method.getName() == "verifyAppSubmissionData"
			|| method.getName() == "verify_AppSubmission_data")
			
		{
			result = new Object[][] 
					
					{
					
					{ILBFunctionConst.applicantSubmissionFields[0]}
		
					};
		}

		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	