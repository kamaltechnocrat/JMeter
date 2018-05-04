/**
 * 
 */
package test_Suite.tests.r2_8.bfFromFOPP.foppBF_Config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IStepsConst;
import test_Suite.constants.workflow.IBf_FoppConst.EPostFix;
import test_Suite.constants.workflow.IStepsConst.EStepsType;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.BfFoppUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class BFSettingsInFoppDetials {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends BFSettingsInFoppDetials> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	FundingOpportunity scndFopp;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		fopp = BfFoppUtil.initializeLessStepsFopp(EPostFix.SRC);
		scndFopp = BfFoppUtil.initializeLessStepsFopp(EPostFix.TRGT);
		
		scndFopp.createNewFundingOpp();
		
		fopp.createNewFundingOpp();
		fopp.addNewStep();
		fopp.openFundingOppDetails();
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		fopp = null;
		scndFopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG", dataProvider="eSteps")
	public void testAllStepsAvailabilityNG(EStepsType eStep, boolean expected) throws Exception {
		
		fopp.setAwardedStepIdent(fopp.getFoppSteps().get(eStep).getStepFullIdent());
		
		log.debug(fopp.getAwardedStepIdent());
		
		Assert.assertEquals(BfFoppUtil.doesAwardedStepAvailable(fopp), expected, "FAIL: could not select awarded Step: " + fopp.getAwardedStepIdent());
	}
	
	@Test(groups="EFormsNG")
	public void testBFAwardesProjectsCheckBoxAvailabilityNG() throws Exception {
		
		fopp.setBfAwardedProject(true);
		
		fopp.setBfFromFOPPIdent("A-BF-FOPP-FOPP-Target");
		
		Assert.assertTrue(fopp.selectBFFundingOpp(), "FAIL: could not select Source FOPP " + fopp.getBfFromFOPPIdent());
		
		Assert.assertTrue(fopp.toggleAwardedProjectCheckbox(), "FAIL: could Taggle");
	}
	
	@Test(groups="EFormsNG")
	public void testNoneBFAwardesProjectsCheckBoxAvailabilityNG() throws Exception {
		
		fopp.setBfAwardedProject(false);
		
		fopp.setBfFromFOPPIdent("None");
		
		Assert.assertTrue(fopp.selectBFFundingOpp(), "FAIL: could not select Source FOPP " + fopp.getBfFromFOPPIdent());
		
		Assert.assertFalse(fopp.toggleAwardedProjectCheckbox(), "FAIL: Awarded Project Checkbox should be available");
	}
	
	@Test(groups="EFormsNG")
	public void testSourceFOPPAvailabilityNG() throws Exception {
		
		fopp.setBfFromFOPPIdent("A-BF-FOPP-FOPP-Source");
		
		Assert.assertTrue(fopp.selectBFFundingOpp(), "FAIL: could not select Source FOPP " + fopp.getBfFromFOPPIdent());		
	}
	
	@Test(groups="EFormsNG")
	public void testInactiveSourceFOPPAvailabilityNG() throws Exception {
		
		fopp.setBfFromFOPPIdent(scndFopp.getFoppFullIdent());
		
		Assert.assertFalse(fopp.selectBFFundingOpp(), "FAIL: able to select Inactive Source FOPP " + fopp.getBfFromFOPPIdent());		
	}
	
	@DataProvider(name="eSteps")
	public Object[][] generateESteps() {
		
		return new Object[][] {
				new Object[] {IStepsConst.EStepsType.SUB, true},
				new Object[] {IStepsConst.EStepsType.REVIEW, true},
				new Object[] {IStepsConst.EStepsType.APPROVAL,true}		
		};
	}
}
