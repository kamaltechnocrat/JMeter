/**
 * 
 */
package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class NewTest {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	String preFix = "-FetchData-PA-";
	
	String postFix = "-AppProfile";
	
	FundingOpportunity fopp;	
	
	FOProject foProj;
	

	private static final String newPASuffix = "-pa"; // You may not need this for the current FOPP. check closing step in the post award

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "TestNGTemplate" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			//this will go to Front Office and lognin as front,
			//The Applicant will be Ouia 1, also front4 can be used as well.

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", preFix,postFix);
			
			foProj = new FOProject(fopp, "", true, 1, "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "TestNGTemplate" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		try {
			
			fopp = null;
			foProj = null;
			
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
	@Test(groups = { "TestNGTemplate" })
	public void testCaseTemplateNG() throws Exception {
		try {
			
			//this will register to the FOPP and create a project against it...
			
			// to open the Applicant Submission, please look for methods in FrontOffice and Projects Util under under utils.workflows
			//then create one more method where you test the field and it data
				
				ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
				
				Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getOrgFullName()), "FAIL: Could not Register to Fopp!");				
				
				
				Assert.assertTrue(foProj.createFOProjectNewNew(), "FAIL: Could not create FO Project");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	
	@DataProvider(name = "Applicants-Data")
	public static Object[][] generateRegLoginData() throws Exception{
		
		return new Object[][] {
				{"field1", "data"},
				{"field2", "data"},
				{"field3", "data"}
				
		};
		
	}

}
