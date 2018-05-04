/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.GpsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class RF_PopProj_GridViewEForm {

	// *********** Variables Declaration Section ********************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Project proj;
	
	Program prog;

	String preFix = "-Project-eForm-Grid-";
	
	String postFix = "-Reporting-Function";

	boolean itItNewFundOpp = false;
	
	boolean hasProgForm = false;
	
	boolean isItNewOrg = true;
	
	boolean hasPubForm = false;

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String orgName = "-Project-eForm-Grid-Reporting-Function";

	@BeforeClass(groups = { "SetupReportingFunctionsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			prog = new Program(preFix, 'P', hasProgForm, itItNewFundOpp,
					hasPubForm);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.setProgPostfix(postFix);

			prog.initProgram();

			prog
					.setProgramFormName("Test-Project-Admin-eForm-Reporting-Function");

			log.info(prog.getProgFullName());

			proj = new Project(prog, true);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@AfterClass(groups = { "SetupReportingFunctionsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		proj = null;
		prog = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "SetupReportingFunctionsNG" })
	public void testProjectEFormReportFunctionNG() throws Exception {

		try {

			submitProject();

			assignOfficers();

			submitAward();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void submitProject() throws Exception {

		try {

			proj.createPOProject(isItNewOrg);

			proj.submitProject(true);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void assignOfficers() throws Exception {

		try {

			proj.assignOfficers(new String[][] { {
					IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void submitAward() throws Exception {

		Assert.assertTrue(GpsUtil
				.completeGPSPaymentScheduleAndSubmit(proj, 2, true),
				"FAIL: Could not submit Payment Schedule");
	}

}
