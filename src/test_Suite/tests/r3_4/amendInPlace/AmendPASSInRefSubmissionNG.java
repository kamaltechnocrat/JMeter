/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AmendPASSInRefSubmissionNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Gnrl-PA-","");
			
			foProj = new FOProject(fopp,"Pre-Award-Issue-InRef-Approval-", true,1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void createAndSubmiProj() throws Exception {
		try {
			
			Assert.assertTrue(FrontOfficeUtil.startOrContinueSubProject(fopp, foProj, 2, false), "FAIL: could not complete workflow!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods={"createAndSubmiProj"})
	public void AmendSubInReferenceSub() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(foProj, 
					IGeneralConst.reviewQuoCritManu[0][0].concat("-pa"), 
					IFiltersConst.submissionsStatus_NotUsed_StatusSubView, "Review", false), "FIAL: Could not open Not used Review Step");
			

			
			String[] refParams = new String[3];
			
			refParams[IProjectsConst.ERefSub.STEP.ordinal()] = IGeneralConst.initialClaim[0][0];
			refParams[IProjectsConst.ERefSub.USER.ordinal()] = "front";
			refParams[IProjectsConst.ERefSub.SCORE.ordinal()] = "N/A";
			
			Assert.assertTrue(ProjectUtil.openSubmissionInRefSubmission(foProj, refParams), "FAIL: Could not open the submission");
			
			Assert.assertTrue(GeneralUtil.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk), "FAIL: Request Amendment Link not available!");
			
			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);
			
			Assert.assertTrue(AmendmentsUtil.fillOutAmendments(new String[] {
							foProj.getProjFullName(), IGeneralConst.initialClaim[0][0],
							IPreTestConst.Groups[10][1][0], dd,
							"Test Issue Amendment with Amend In Place",
							"This a Comment" }, foProj, "Corrective"), "FAIL: Could not fill out amendments!");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn), "FAIL: Button either does not exists or disabled!");
			
			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods={"AmendSubInReferenceSub"})
	public void TestAmendedSubInReferenceSub() throws Exception {
		try {
			
			Assert.assertTrue(ClicksUtil.returnFromAnyForm(), "FAIL: Could not go back to My Assigned Submissions!");
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.evaluateSubmissions_InProgress_StatusSubView), "FAIL: Could not Open Claim in Applicant Subs List");
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyByTtl(IProjectsConst.gps_AgreementDetails_TextFldTtl), "FAIL: The Form should be in editable mode");
			
			Assert.assertTrue(ClicksUtil.returnFromAnyForm(), "FAIL: Could not return from Claim!");
			
			Assert.assertTrue(foProj.submitFromMyAssignedSubListNew(IGeneralConst.initialClaim[0][0], "Amended", true), "FAIL: Could not resubmit Claim!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

}
