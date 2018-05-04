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
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class VerifySubAccessForInProgSubNG {

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
			
			foProj = new FOProject(fopp,"Pre-Award-VerifyAccess-InProg-", true,1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void createAndSubmiProj() throws Exception {
		try {
			
			Assert.assertTrue(foProj.createFOProjectSimple(),"FAIL: Could Not Create Front Office Project!");
	    	
	    	Assert.assertTrue(foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true),"FIAL: Could not Find Project In FO Submission List!");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("3");
			
			Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,"Ready", false, false), "FAIL: Could Not approve submission!");
						
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods={"createAndSubmiProj"})
	public void AmendSubInReferenceSub() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			ProjectUtil.openSubmissionInMyProjectSubmissionsList(foProj, 
					IGeneralConst.approvQuoCritAuto[0][0], 
					"Open Projects");
			
			String[] refParams = new String[3];
			
			refParams[IProjectsConst.ERefSub.STEP.ordinal()] = IGeneralConst.gnrl_SubmissionA[0][0];
			refParams[IProjectsConst.ERefSub.USER.ordinal()] = "front";
			refParams[IProjectsConst.ERefSub.SCORE.ordinal()] = "N/A";
			
			Assert.assertTrue(ProjectUtil.openSubmissionInRefSubmission(foProj, refParams), "FAIL: Could not open the submission");
			
			Assert.assertTrue(GeneralUtil.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk), "FAIL: Request Amendment Link not available!");
			
			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);
			
			Assert.assertTrue(AmendmentsUtil.fillOutAmendments(new String[] {
							foProj.getProjFullName(), IGeneralConst.gnrl_SubmissionA[0][0],
							IPreTestConst.Groups[0][1][0], dd,
							"Test Amend Now with Amend In Place",
							"This a Comment" }, foProj, "Corrective"), "FAIL: Could not fill out amendments!");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.amendNowBtn), "FAIL: Button either does not exists or disabled!");
			
			ClicksUtil.clickButtons(IClicksConst.amendNowBtn);	
			
			Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IProjectsConst.gps_AgreementDetails_TextFldTtl," Amend Now"), "FAIL: Could not append to Text");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.returnFromAnyForm();				
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods={"AmendSubInReferenceSub"})
	public void testInProgressSubmission() throws Exception {
		try {
			
			String[] refParams = new String[3];
			
			refParams[IProjectsConst.ERefSub.STEP.ordinal()] = IGeneralConst.gnrl_SubmissionA[0][0];
			refParams[IProjectsConst.ERefSub.USER.ordinal()] = "";
			refParams[IProjectsConst.ERefSub.SCORE.ordinal()] = "In Progress";
			
			Assert.assertFalse(ProjectUtil.openSubmissionInRefSubmission(foProj, refParams), "FAIL: Should not open the submission");
			
			//Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyByTtl(IProjectsConst.gps_AgreementDetails_TextFldTtl), "FAIL: Text Field should be Editable");
			
			//ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			//Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: should be able to submit the form");
			
			//Assert.assertTrue(ClicksUtil.returnFromAnyForm(),"FAIL: Could not return to the List!");
						
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
