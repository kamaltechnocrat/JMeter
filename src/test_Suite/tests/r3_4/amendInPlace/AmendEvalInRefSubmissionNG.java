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
public class AmendEvalInRefSubmissionNG {

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
			
			Assert.assertTrue(FrontOfficeUtil.createAndSubmitStandardAward(fopp, foProj, 1), "FAIL: could not complete workflow!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods={"createAndSubmiProj"})
	public void AmendEvalInReferenceSub() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			ProjectUtil.openSubmissionInMyProjectSubmissionsList(foProj, 
					IGeneralConst.pa_AwardCrit[0][0], 
					"Open Projects");			
			
			String[] refParams = new String[3];
			
			refParams[IProjectsConst.ERefSub.STEP.ordinal()] = IGeneralConst.approvQuoCritAuto[0][0];
			refParams[IProjectsConst.ERefSub.USER.ordinal()] = "Approver4";
			refParams[IProjectsConst.ERefSub.SCORE.ordinal()] = "Rejected";
			
			Assert.assertTrue(ProjectUtil.openSubmissionInRefSubmission(foProj, refParams), "FAIL: Could not open the submission");
			
			Assert.assertTrue(GeneralUtil.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk), "FAIL: Request Amendment Link not available!");
			
			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);
			
			Assert.assertTrue(AmendmentsUtil.fillOutAmendments(new String[] {
							foProj.getProjFullName(), IGeneralConst.approvQuoCritAuto[0][0],
							IPreTestConst.Groups[3][1][0], dd,
							"Test Issue Amendment with Amend In Place",
							"This a Comment" }, foProj, "Corrective"), "FAIL: Could not fill out amendments!");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn), "FAIL: Button either does not exists or disabled!");
			
			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);	
			
			Assert.assertTrue(ClicksUtil.returnFromAnyForm(), "FAIL: Could not Return to the List!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods={"AmendEvalInReferenceSub"})
	public void TestAmendedEvalInReferenceSub() throws Exception {
		try {
			
			Assert.assertTrue(foProj.approveAmendedSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: Could not approve the amended Submissions!");
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(foProj.reSubmitAnyStandardAgreement(IGeneralConst.pa_AwardCrit[0][0], true), "FAIL: Could not reSubmit the Award!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

}
