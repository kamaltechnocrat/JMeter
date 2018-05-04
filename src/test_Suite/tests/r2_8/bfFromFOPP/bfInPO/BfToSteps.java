/**
 * 
 */
package test_Suite.tests.r2_8.bfFromFOPP.bfInPO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.BfFoppUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class BfToSteps {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends BfToSteps> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project srcProj;
	
	Project trgtProj;
	
	EProjNames epNames = EProjNames.POSAME;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		srcProj = BfFoppUtil.prepareSourceData(epNames);
		
		trgtProj = BfFoppUtil.prePareTarget(srcProj,epNames);	
		
		trgtProj.setProjNumber(srcProj.getProjNumber());
		
		BfFoppUtil.submitApplication(srcProj);
		BfFoppUtil.submitReview(srcProj);
		BfFoppUtil.submitPO_Submission(srcProj);
		BfFoppUtil.submitApproval(srcProj);
		BfFoppUtil.submitAward(srcProj);
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj));
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcProj = null;
		trgtProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG")
	public void testBFDataToSubmissionNG() throws Exception {
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetApplicantion(trgtProj), "FAIL: No Data Brought Forward From Admin eForm or From Opposite Same Step");
	}
	
	@Test(groups="EFormsNG",dependsOnMethods="testBFDataToSubmissionNG")
	public void testBFDataToPOSubmissionNG() throws Exception {
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetReviewSubmission(trgtProj, "Ready"), "FAIL: No Data Brought Forward from Opposite Same Review Step");
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetPO_Submission(trgtProj), "FAIL: No Data Brought Forward from Opposite Same PO Submission Step");
		
	}
	
	@Test(groups="EFormsNG",dependsOnMethods="testBFDataToPOSubmissionNG")
	public void testBFDataToAwardNG() throws Exception {
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetApprovalSubmission(trgtProj, "Ready"), "FAIL: No Data Brought Forward from Opposite Same Approval Step");
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetAward(trgtProj), "FAIL: No Data Brought Forward from Opposite Same Award Step");
	}
	
	@Test(groups="EFormsNG",dependsOnMethods="testBFDataToAwardNG")
	public void testBFDataAfterSourceFOPPMadeInactiveNG() throws Exception {
		
	}

}
