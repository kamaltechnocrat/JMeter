/**
 * 
 */
package test_Suite.tests.r2_8.bfFromFOPP.bfInFO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.lib.workflow.FOProject;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.BfFoppUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class BfOnAmend {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends BfOnAmend> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FOProject srcProj;
	
	FOProject trgtProj;
	
	EProjNames epNames = EProjNames.FOAMEND;
	
	Integer orgIndex = 1;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToFO();
		GeneralUtil.LoginFO();
		// -----------------------------------
		
		srcProj = BfFoppUtil.prepareFOSourceData(epNames, 1);
		
		trgtProj = BfFoppUtil.prePareFOTarget(srcProj,epNames, 1);	
		
		trgtProj.setProjNumber(srcProj.getProjNumber());
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
	public void startAndSubmitApplication() throws Exception {
		
		Assert.assertTrue(BfFoppUtil.submitFOApplication(srcProj), "FAIL: could Not Submit First project Applicant Submission!");
		
		Assert.assertTrue(BfFoppUtil.createFOProject(trgtProj), "FAIL: could not create Target Project! " + trgtProj.getProjFOFullName());
		
		BfFoppUtil.openFOApplicantSubmission(trgtProj,false);
		BfFoppUtil.returnFromAnyList();
		
		GeneralUtil.switchToPO();	
		
		BfFoppUtil.assignOfficers(srcProj);
		BfFoppUtil.submitReview(srcProj);
		BfFoppUtil.submitPO_Submission(srcProj);
		BfFoppUtil.submitApproval(srcProj);
		
		BfFoppUtil.processAmendmentAndReSubmit(srcProj);		
	}
	
	@Test(groups="EFormsNG", dependsOnMethods="startAndSubmitApplication")
	public void testAmendingSourceBeforeSubmittingTargetNG() throws Exception {
		
		GeneralUtil.switchToFO();
		
		BfFoppUtil.openFOApplicantSubmission(trgtProj,false);
		
		Assert.assertEquals(BfFoppUtil.testHowManyEntriesInAttachmentList(),3, "FAIL: should be 3 entries Only!");	

		BfFoppUtil.returnFromAnyList();
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="startAndSubmitApplication")
	public void testNotAwardedSourceDueToAmendmentProjectAvailabilityNG() throws Exception {
		
		GeneralUtil.switchToFO();
		
		FOProject tProj = BfFoppUtil.prePareAwardedFOTarget(srcProj,epNames, 1);
		
		Assert.assertFalse(BfFoppUtil.createFOProject(tProj), "FAIL: able to select Un-Awarded Source Project: " + srcProj.getProjFullName());
	}

}
