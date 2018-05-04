/*
 * Test Case 01 for Story  #2049. Create a Publications Search result page
 * Steps
 * 1. PreTest02NG will import the eForm "Publication Form"
 * 2. Create a Program with Submission using "Publication Form"
 * 3. Enable Front Office applicant
 * 4. Switch to front office start search with words that is not listed
 * Result:
 * The Publication Search Result Appear with no result and a message
 */

package test_Suite.tests.stories.release_1_5.iter_1_5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.preTest.*;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class S2049_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;

	boolean programForm = false;

	boolean publicationForm = true;

	boolean newOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;

	String postFix = "-S2049_01";

	char portaltype = 'F';

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;

	String approvStep[][] = IGeneralConst.approvQuoCritAuto;

	Program prog;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "Iter_15", "PublicationsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			//----------------------------
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@AfterClass(groups = { "Iter_15", "PublicationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "PublicationsNG" })
	public void initializeFoppAndFillPublication() throws Exception {

		prog = new Program(
				preFix, 
				portaltype, 
				programForm, 
				newProgram,
				publicationForm);
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		prog.setProgPostfix(postFix);
		
		prog.initProgram();
		
		prog.setProgAdmin(progAdmin);
		
		prog.setProgOfficers(progOfficers);
		
		ClicksUtil.clickLinks(prog.getProgFullIdent());
		
		prog.managePublicantionForm();		
	}

	@Test(groups = { "PublicationsNG" },dependsOnMethods="initializeFoppAndFillPublication")
	public void testFoWizardWithWrongWordInPublicationForm() throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();

			Assert.assertTrue(FrontOfficeUtil.searchForProgram("none"), "FAIL: could do a search in FO!");

			int rowIndex = -1;

			Assert.assertEquals(TablesUtil.getRowIndexByTBody(
					ITablesConst.foSearchProgramsResultTBodyId, 
					prog.getProgFullName()), 
					rowIndex,
					"Pass: program does not Exists in Result Table!");
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}
}
