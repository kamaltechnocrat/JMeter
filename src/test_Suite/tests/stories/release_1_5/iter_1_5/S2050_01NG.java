

package test_Suite.tests.stories.release_1_5.iter_1_5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;


/*
 * Test Case# 01 for Story 2050 - Create a "Registration Wizard"
 * Steps:
 * 1. Create a Program - Two steps -
 * 2. Add Publication Form
 * 3. Add Search Words - Use "General"
 * 4. Switch to FO
 * 5. Login Find an Org Letter then Log out
 * 6. Use the Search Wizard to Register, Create and Submit Project
 */

@GUITest
@Test(singleThreaded = true)
public class S2050_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	boolean programForm = false;
	boolean publicationForm = true;
	boolean newOrg = false;

	String preFix = IGeneralConst.gnrl_ProgPrefix;
	String postFix = "-S2050_01";
	char portaltype = 'F';

	static String projFOLetter = null;
	
	String progAdmin[] = { IPreTestConst.adminGroup };	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String approvStep[][] = IGeneralConst.approvQuoCritAuto;

	Program prog;
	FOProject foProj;
	
	Object[] wizardObj;
	

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = {"PublicationsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@AfterClass(groups = {"PublicationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"PublicationsNG" })
	public void initializeFoppAndObjectNG() throws Exception {

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
		
		projFOLetter = EFormsUtil.createAnyRandomString(5);
		
		wizardObj = new Object[6];

		wizardObj[0] = prog.getProgFullName();
		
		wizardObj[1] = projFOLetter 
				+ preFix 
				+ IGeneralConst.gnrl_FO_ProjName 
				+ postFix;
		
		wizardObj[2] = (Boolean) true; // Do you want to create a new profile?
		
		wizardObj[3] = (Boolean) true; // Do you want to Create new Organization?
		
		wizardObj[4] = (Integer) 3; // Org and Registrant sequence
		
		wizardObj[5] = (Boolean) false; // Do you want to Fill Applicant workspace?
	}

	@Test(groups = {"PublicationsNG" }, dependsOnMethods = {"initializeFoppAndObjectNG"})
	public void testApplicantCanRegisterThroughWizardAndSubmitNG() throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();

			FrontOfficeUtil.searchForProgram(IGeneralConst.wizardsWords[6]);

			Assert.assertTrue(FrontOfficeUtil.registerThroughWizard(wizardObj),
					"Fail: To Register through the Wizard");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}
	
	@Test(groups = {"PublicationsNG" }, dependsOnMethods = {"testApplicantCanRegisterThroughWizardAndSubmitNG"})
	public void testSubmissionAvailableInPOWhenWizardUsed() throws Exception {
		
		try {
			
			GeneralUtil.switchToPO();
			
			foProj = new FOProject(prog);
			
			foProj.setProjFullName(projFOLetter + preFix + IGeneralConst.gnrl_FO_ProjName
					+ postFix);
			
			foProj.setOrgFullName(IPreTestConst.FO_OrgShortName + wizardObj[4]);
			
			ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);
			
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, foProj.getProjFullName(), IFiltersConst.exact);
			
			Assert.assertTrue(TablesUtil.findInTable(ITablesConst.fundingOpp_IntakeTableId, foProj.getProjFullName()), "FAIL: Project not Available in In Basket when Wizard used");
			
			foProj.assignOfficers(new String[][] {
					{ IPreTestConst.Groups[0][0][0],
							IPreTestConst.Groups[0][1][0]}});
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}	
}
