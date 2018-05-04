/**
 * 
 */
package test_Suite.tests.clients.sov.cfp_fy14_aoe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.clients.ISovConst;
import test_Suite.constants.clients.ISovConst.ESovCfpFy14ApplicantsNum;
import test_Suite.constants.clients.ISovConst.ESovFOPPs;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.clients.Sov;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.clients.SovUtils;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Test_CFPfy14_SU061 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "JohnBacon";
	
	String poOfficer1 = "Ed.Haggett";
	
	String poOfficer2 = "Liz.Rand";
	
	String poPAOfficer1 = "John.Leu";
	
	String poPAOfficer2 = "Julie.Robinson";
	
	String poStaff1 = "Deb.Quackenbush";
	
	String poStaff2 = "Bill.Talbott";
	
	String poStaff3 = "Doeadmin2";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(poPAOfficer1);
			// -----------------------------------
			
			sov = SovUtils.cfpFy14_Initialization(ESovFOPPs.CFPFY14AOE, ESovCfpFy14ApplicantsNum.SU061, "CFP Application", "Title I - IN_4250");
			
			sov.setFoProjName(sov.getProjectName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName());

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
	public void testCaseTemplateNG() throws Exception {
		try {
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[16]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView), "FAIL: to Submit PA Tip Gap Payment");
			
		
	
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
