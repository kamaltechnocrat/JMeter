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

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * steps 1. Create the class in any package 2. copy the import (more imports
 * should required) add them as well 3. Copy configurations @BeforeClass and
 * @AfterClass) and @Test 4. change groups, test signeture and log class
 * 
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class templateClass {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	String preFix = IGeneralConst.gnrl_ProgPrefix;
	String postFix = "";
	char portaltype = 'P';
	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };


	Program prog;
	
	FOProject foProj;
	
	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "TestNGTemplate" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "TestNGTemplate" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		try {
			
			prog = null;
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
	@Test(groups = { "TestNGTemplate" }, enabled = false)
	public void testCaseTemplateNG() throws Exception {
		try {

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	
	@DataProvider(name = "Applicants-Data")
	public static Object[][] generateRegLoginData() throws Exception{
		
		return new Object[][] {
				{"front"},
				{"front2"},
				{"front3"}
				
		};
		
	}

}
