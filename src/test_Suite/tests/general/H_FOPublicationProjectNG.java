/**
 * 
 */
package test_Suite.tests.general;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
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
public class H_FOPublicationProjectNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isNewProgram = false;

	boolean hasProgramForm = true;

	boolean hasPublicationForm = true;

	boolean isNewOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;

	String postFix = "14";

	char portaltype = 'P';

	static String projFOLetter = null;

	Program prog;

	FOProject foProj;

	Object[] wizardObj;

	// ----------- End of Global Parameters

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
		foProj = null;
		wizardObj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}	

	@Test(groups = { "PublicationsNG" })
	public void initializeFundingOpp() throws Exception {

		prog = new Program(preFix, portaltype, hasProgramForm, isNewProgram,
				hasPublicationForm);

		prog.setProgPostfix("");

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
	}

	@Test(groups = { "PublicationsNG" },dependsOnMethods="initializeFundingOpp")
	public void fillPublicationForm() throws Exception {

		ClicksUtil.clickLinks(prog.getProgFullIdent());

		prog.managePublicantionForm();
		
		initializeSearchObject();
	}

	@Test(groups = { "PublicationsNG" },dependsOnMethods="fillPublicationForm")
	public void getOrgLetter() throws Exception {

		GeneralUtil.switchToFOOnly();

		GeneralUtil.loginAnyFO("front" + postFix);

		projFOLetter = FrontOfficeUtil.getFOProjectLetter(prog, preFix
				+ IGeneralConst.gnrl_FO_ProjName + postFix);

		wizardObj[1] = projFOLetter + preFix + IGeneralConst.gnrl_FO_ProjName
				+ postFix;
	}

	@Test(groups = { "PublicationsNG" },dependsOnMethods="getOrgLetter")
	public void serchForFOPP() throws Exception {

		FrontOfficeUtil.searchForProgram(IGeneralConst.wizardsWords[6]);
	}

	@Test(groups = { "PublicationsNG" },dependsOnMethods="serchForFOPP")
	public void wizardRegistration() throws Exception {

		Assert.assertTrue(FrontOfficeUtil.registerThroughWizard(wizardObj),
				"Fail: Could not Register through the Wizard");
		
	}
	
	private void initializeSearchObject() throws Exception {

		wizardObj = new Object[6];

		wizardObj[0] = prog.getProgFullName();

		wizardObj[2] = (Boolean) true; // Do you want to create a new profile?

		wizardObj[3] = (Boolean) true; // Do you want to Create new
										// Organization?

		wizardObj[4] = postFix; // Org and Registrant sequence

		wizardObj[5] = (Boolean) false; // Do you want to Fill Applicant
										// workspace?
	}

}
