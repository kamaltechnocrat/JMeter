/*
 * Test Case# 02 for Story 2050 - Create a "Registration Wizard"
 * Steps:
 * 1. Create a Program - Two steps -
 * 2. Add Publication Form
 * 3. Do not Add Search Words -
 * 4. Switch to FO
 * 5. Login Find an Org Letter then Log out
 * 6. Use the Search Wizard to Register, The program should not be in the list
 */

package test_Suite.tests.stories.release_1_5.iter_1_5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;


@GUITest
@Test(singleThreaded = true)
public class S2050_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = true;

	boolean programForm = false;

	boolean publicationForm = true;

	boolean newOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;

	String postFix = "-S2050_02";

	char portaltype = 'F';

	static String projFOLetter = null;

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;

	String approvStep[][] = IGeneralConst.approvQuoCritAuto;

	Program prog;

	Object[] wizardObj;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "PublicationsNG" })
	public void setUp() {
		
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

	@AfterClass(groups = { "PublicationsNG" }, alwaysRun=true)
	public void tearDown() {
		
		prog = null;
		wizardObj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}	

	@Test(groups = { "PublicationsNG" })
	public void createProgram() throws Exception {

		prog = new Program(
				preFix, 
				portaltype, 
				programForm, 
				newProgram,
				publicationForm);
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		prog.setProgPostfix(postFix);
		
		prog.initProgram();
		
		prog.setEndDate(GeneralUtil.setDayofMonth(10));
		
		if (newProgram) {

			prog.setProgAdmin(progAdmin);
			
			prog.setProgOfficers(progOfficers);
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			
			prog.createProgram();

			// Setting the Steps start and End Dates 5 Days from Today
			prog.setStartDate(GeneralUtil.setDayofMonth(1));
			
			prog.setEndDate(GeneralUtil.setDayofMonth(5));
			
			prog.addStep(submissionStep);
			
			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });
			
			prog.addStep(approvStep);
			
			prog.manageStep(new String[][] { approversGrp });
			
			prog.activateProgram("Active");
		}
	}

	@Test(groups = { "PublicationsNG" },dependsOnMethods="createProgram")
	public void testFoWizardWithoutWordsInPublicationForm() throws Exception {
		try {
			
			initializeObject();
			
			GeneralUtil.switchToFOOnly();

			Assert.assertTrue(FrontOfficeUtil.searchForProgram(IGeneralConst.wizardsWords[6]), "FAIL: error occured while searching for Funding Opp!");

			Assert.assertFalse(FrontOfficeUtil.registerThroughWizard(wizardObj), 
					"Fail: the Publication Form is empty the program should not be listed");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void initializeObject() throws Exception {
		
		projFOLetter = EFormsUtil.createAnyRandomString(5);
		
		wizardObj = new Object[6];

		wizardObj[0] = prog.getProgFullName();
		
		wizardObj[1] = projFOLetter 
			+ preFix 
			+ IGeneralConst.gnrl_FO_ProjName
			+ postFix;
		
		wizardObj[2] = (Boolean) true; // Do you want to create a new profile?
		
		wizardObj[3] = (Boolean) true; // Do you want to Create new
										// Organization?
		wizardObj[4] = (Integer) 22; // Org and Registrant sequence
		
		wizardObj[5] = (Boolean) false; // Do you want to Fill Applicant
										// workspace?
	}
}
