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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author s.fatima
 *
 */
@GUITest
@Test(singleThreaded = true)
public class TestClass {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;
	String postFix = "";
	char portaltype = 'P';

	Program prog;
	FOProject foProj;

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

	@Test(groups = { "WorkflowNG" })
	public void initializeFundingOppObject() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
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
