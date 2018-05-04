package test_Suite.tests.r5_1.statusNotUsedAfterAmendment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class testStatus {

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
			
				fopp = new FundingOpportunity(IFoppConst.decision_Letter, IFoppConst.decisionFOPP_Prefix, IFoppConst.decisionFOPP_Postfix);
			
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
			
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.reviewAQuoCritAuto[0][2],IRefTablesConst.statusReady,foProj.getCurrentStepName(), false, IRefTablesConst.versionNumber1, ""));
		
	    Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectYes, IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score));
	
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
	    
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview1")
	public void approveReview2() throws Exception{
		
		GeneralUtil.switchToPOOnly();
			
		GeneralUtil.loginApprover("1");
		
		foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][2],IRefTablesConst.statusReady,foProj.getCurrentStepName(), false, IRefTablesConst.versionNumber1, ""));
		
		Assert.assertTrue(ProjectUtil.fillApprovalForm(IGeneralConst.status,IGeneralConst.selectApproved, IGeneralConst.commentId,IGeneralConst.comment));
		
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn));

	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "approveReview2" })
	public void amendmentRequestByPO() throws Exception {
		try {

			GeneralUtil.switchToPO();
			
			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.reviewQuoCritAuto[0][0],
							"Open Projects"),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.reviewQuoCritAuto[0][0],
									IPreTestConst.Groups[2][1][0], dd,
									IGeneralConst.amendmentReason,
									IGeneralConst.amendmentComment}, foProj, IGeneralConst.amendmentCorrective),
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
	public void amendReview1() throws Exception{
		
		GeneralUtil.switchToPOOnly();
			
		GeneralUtil.loginReviewer("1");
		
		foProj.initializeStep(IGeneralConst.reviewAQuoCritAuto[0][0]);
			
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.reviewAQuoCritAuto[0][2],IRefTablesConst.statusInProgress,foProj.getCurrentStepName(), false, IRefTablesConst.versionNumber2, ""));
		
	    Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectNo, IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score));
	
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
	    
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "amendReview1" })
	public void amendmentRequest() throws Exception {
		
		try {

			GeneralUtil.switchToPO();
			
			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList_New(foProj,
							IGeneralConst.reviewQuoCritAuto[0][0],
							IRefTablesConst.openProjects, IRefTablesConst.versionNumber2 ),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.reviewQuoCritAuto[0][0],
									IPreTestConst.Groups[2][1][0], dd,
									IGeneralConst.amendmentReason,
									IGeneralConst.amendmentComment}, foProj, IGeneralConst.amendmentCorrective),
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
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendmentRequest")
	public void amendReview() throws Exception{
		
		GeneralUtil.switchToPOOnly();
			
		GeneralUtil.loginReviewer("1");
		
		foProj.initializeStep(IGeneralConst.reviewAQuoCritAuto[0][0]);
			
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.reviewAQuoCritAuto[0][2],IRefTablesConst.statusInProgress,foProj.getCurrentStepName(), false, IRefTablesConst.versionNumber2, ""));
		
	    Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectYes, IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score));
	
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
	    
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "amendReview")
	public void approveReview() throws Exception{
		
		GeneralUtil.switchToPOOnly();
			
		GeneralUtil.loginApprover("1");
		
		foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][2],IRefTablesConst.statusInProgress,foProj.getCurrentStepName(), false, IRefTablesConst.versionNumber1, ""));
		
		Assert.assertTrue(ProjectUtil.fillApprovalForm(IGeneralConst.status,IGeneralConst.selectPendingApproval, IGeneralConst.commentId,IGeneralConst.comment));
		
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn));
	    
	    Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));

	}

	
	}
	
	


                                   