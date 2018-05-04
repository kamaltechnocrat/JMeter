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
public class CloningFoppBF {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends CloningFoppBF> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		fopp = BfFoppUtil.initializeLessStepsFopp(EPostFix.TRGT);
		
		fopp.setAwardedStepIdent(fopp.getFoppSteps().get(IStepsConst.EStepsType.APPROVAL).getStepFullIdent());
		
		fopp.setBfFromFOPPIdent("A-BF-FOPP-FOPP-Source");
		
		fopp.setBfAwardedProject(true);
		
		fopp.createNewFundingOpp();	
		
		fopp.addNewStep();
		
		fopp.openFundingOppDetails();
		
		Assert.assertEquals(BfFoppUtil.doesAwardedStepAvailable(fopp), true, "FAIL: could not select awarded Step: " + fopp.getAwardedStepIdent());
		
		Assert.assertTrue(BfFoppUtil.cloneAndOpenClonedFOPP(fopp),"FAIL:  Could not clone or Open fopp Planer " + fopp.getFoppFullIdent());
		
		fopp.openFundingOppDetails();
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG",dataProvider="bfParams")
	public void testDropdownConfigurationAfterClonningNG(String fieldId, String expected) throws Exception {
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(fieldId), expected, "FAIL: value not in list anymore");
	}
	
	@Test(groups="EFormsNG")
	public void testCheckboxConfigurationAfterClonningNG() throws Exception {
		
		Assert.assertTrue(GeneralUtil.isCheckboxCheckedById("/bringForwardOnlyIfAwarded/"), "FAIL: value not in list anymore");
	}
	
	@DataProvider(name="bfParams")
	public Object[][] generateBFParma() {
		
		return new Object[][] {
				new Object[] {"/considerAwardedAtStep/",fopp.getAwardedStepIdent()},
				new Object[] {"/bringForwardFromFundingOpp/",fopp.getBfFromFOPPIdent()}
		};
	}

}
