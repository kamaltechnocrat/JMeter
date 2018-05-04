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
public class BfOnAmend {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends BfOnAmend> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project srcProj;
	
	Project trgtProj;
	
	EProjNames epNames = EProjNames.POAMEND;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();		
		//------------------------------------------
		
		srcProj = BfFoppUtil.prepareSourceData(epNames);
		
		trgtProj = BfFoppUtil.prePareTarget(srcProj,epNames);	
		
		trgtProj.setProjNumber(srcProj.getProjNumber());
		
		BfFoppUtil.submitApplication(srcProj);
		BfFoppUtil.submitReview(srcProj);
		BfFoppUtil.submitPO_Submission(srcProj);
		BfFoppUtil.submitApproval(srcProj);
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj));
		
		BfFoppUtil.openApplicantSubmission(trgtProj,false,"Ready");
		BfFoppUtil.returnFromAnyList();
		
		BfFoppUtil.processAmendmentAndReSubmit(srcProj);
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
	public void testAmendingSourceAfterOpeningTargetNG() throws Exception {
		
		BfFoppUtil.openApplicantSubmission(trgtProj,false,"In Progress");
		
		Assert.assertEquals(BfFoppUtil.testHowManyEntriesInAttachmentList(), 3,"FAIL: should be 3 entries Only!");
		
		BfFoppUtil.returnFromAnyList();
	}
	
	@Test(groups = "EFormsNG")
	public void testNotAwardedSourceDueToAmendmentProjectAvailabilityNG() throws Exception {
		
		Project tProj = BfFoppUtil.prePareAwardedTarget(srcProj,epNames);
		
		Assert.assertFalse(BfFoppUtil.createProject(tProj), "FAIL: able to select Un-Awarded Source Project: " + srcProj.getProjFullName());
	}
}
