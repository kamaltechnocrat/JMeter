/**
 * 
 */
package test_Suite.tests.clients.sov;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.clients.ISovConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.clients.Sov;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.clients.SovUtils;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Sov_PreTestNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny("Admin");
			// -----------------------------------
			
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "SovRegTest" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		try {
			sov = null;
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void setApplicationSettingsNG() throws Exception {
		try {
			
			Assert.assertTrue(SovUtils.sovChangeApplicationsSetting(), "FAIL: could not complete Application Settings!");
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" }, dependsOnMethods = {"setApplicationSettingsNG"})
	public void setCfpFY13NG() throws Exception {
		try {
			
			sov = new Sov();
			
			sov.setFundingOppIdent(ISovConst.sovFoppsIdents[ISovConst.ESovFOPPs.CFPFY13DOE.ordinal()]);						
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);		
			
			
			Assert.assertTrue(SovUtils.sovUncheckPAReExecute(sov), "FAIL: Unable to reset Post-Award Re execution!");
			
			
			Assert.assertTrue(SovUtils.sovAllGroupsFromEvaluationSteps(sov, ISovConst.sovEvaluationSteps, ISovConst.sovEvaluationStepsPA, ISovConst.sovCfpFy13StepsNames, "21"));
			
			Assert.assertTrue(SovUtils.sovChangeAndActivateIPASSNotif(sov, "21"));			
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" }, dependsOnMethods = {"setApplicationSettingsNG"})
	public void setCfpFY14NG() throws Exception {
		try {
			
			sov = new Sov();
			
			sov.setFundingOppIdent(ISovConst.sovFoppsIdents[ISovConst.ESovFOPPs.CFPFY14AOE.ordinal()]);					
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);			
			
			
			Assert.assertTrue(SovUtils.sovUncheckPAReExecute(sov), "FAIL: Unable to reset Post-Award Re execution!");	
			
			Assert.assertTrue(SovUtils.sovChangeAwardsEForm(sov, "CFP_FY14_DOE_Grant_Agreement/Amendment 1.4 for 4.1x"));
			
			Assert.assertTrue(SovUtils.sovAllGroupsFromEvaluationSteps(sov, ISovConst.sovEvaluationSteps, ISovConst.sovEvaluationStepsPA, ISovConst.sovCfpFy14StepsNames, "24"));
			
			Assert.assertTrue(SovUtils.sovChangeAndActivateIPASSNotif(sov, "24"));
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" }, dependsOnMethods = {"setApplicationSettingsNG"})
	public void setTechEdFY13NG() throws Exception {
		try {
			
			sov = new Sov();
			
			sov.setFundingOppIdent(ISovConst.sovFoppsIdents[ISovConst.ESovFOPPs.TechEdFY13.ordinal()]);						
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);	
			
			Assert.assertTrue(SovUtils.sovChangePrimaryOrg(sov, "ISL DOE"));
			
			
			Assert.assertTrue(SovUtils.sovUncheckPAReExecute(sov), "FAIL: Unable to reset Post-Award Re execution!");
			
			
			Assert.assertTrue(SovUtils.sovAllGroupsFromEvaluationSteps(sov, ISovConst.sovTechEdEvaluationSteps, ISovConst.sovTechEdEvaluationStepsPA, ISovConst.sovTechEdStepsNames, "21"));
			
			Assert.assertTrue(SovUtils.sovChangeAndActivateIPASSNotif(sov, "21"));	
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" }, dependsOnMethods = {"setApplicationSettingsNG"})
	public void setNordFY14NG() throws Exception {
		try {
			
			sov = new Sov();		
			
			//This is to conform with SOV Doc and the script the Funding Opp "NorD FY13 DOE FUNDING_OPP" not part of the regression testing
			//but it's part of the script to change the or on the projects	
			
			
//			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);	
//			
//			sov.setFundingOppIdent("NorD FY13 DOE FUNDING_OPP");		
//			
//			Assert.assertTrue(SovUtils.sovChangePrimaryOrg(sov, "CFP DOE"));
			
			//the following part of the rergression	

			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);	
			
			sov.setFundingOppIdent(ISovConst.sovFoppsIdents[ISovConst.ESovFOPPs.NORDFY14.ordinal()]);
			
			Assert.assertTrue(SovUtils.sovChangePrimaryOrg(sov, "CFP DOE"));
			
			
			Assert.assertTrue(SovUtils.sovUncheckPAReExecute(sov), "FAIL: Unable to reset Post-Award Re execution!");
			
			
			Assert.assertTrue(SovUtils.sovAllGroupsFromEvaluationSteps(sov, ISovConst.sovNordEvaluationSteps, ISovConst.sovNordEvaluationStepsPA, ISovConst.sovNordFy14StepsNames, "22"));
			
			Assert.assertTrue(SovUtils.sovChangeAndActivateIPASSNotif(sov, "22"));	
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
