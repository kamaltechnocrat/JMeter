package test_Suite.tests.stories.release_2_0.iter_8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @Story #2631. Activity List Filtering
 */

/**
 * 
 * @author mshakshouki
 *
 */
@Test(singleThreaded = true)
public class S2631_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;

	Program prog;

	FOProject foProj;

	Project proj;

	FOUsers foUser;

	boolean retValue;

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// ----------------------------------

		initializeProgram();
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		proj = null;
		foProj = null;
		foUser = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "NotificationsNG" })
	public void startFOProject() throws Exception {
		
		try {

			GeneralUtil.switchToFO();

			Assert.assertTrue(submitFOProject(),"FAILED: could not submit FO Project");

			GeneralUtil.switchToPO();

			assignProjOfficers();
			
		} catch (Exception e) {
			log.error("Unexpected Exception", e);
		}
	}
	
	@Test(groups = { "NotificationsNG" })
	public void startPOProject() throws Exception {

		createPOProject();

		assignProjOfficers();		
	}
	
	

//	@Test(groups = { "NotificationsNG" })
//	public void testActivityListFiltering_NotCompleteNG() throws Exception {
//
//		try {
//				TODO: rename the class to the above method name
//
//		} catch (Exception e) {
//			log.error("Unexpected Exception", e);
//
//			throw new RuntimeException("Unexpected Exception", e);
//
//		}
//	}

	private void initializeProgram() throws Exception {

		prog = new Program();

		prog.setProgPreFix(IGeneralConst.gnrl_ProgPrefix);

		prog.setProgPortal('F');

		prog.setNewProgram(newProgram);

		prog.setProgForm(programForm);

		prog.setPublicationForm(false);

		prog.setProgIdent(prog.getProgPreFix() + IFoppConst.fundingOpp_Name);

		prog.setProgPostfix(""); //("-S2631_01");

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
	}

	private boolean submitFOProject() throws Exception {

		retValue = false;

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		if (FrontOfficeUtil.registerApplicantToProgram(prog.getProgFullName(),
				foProj.getFoOrgName())) {

			foProj.createFOProject();

			foProj.submitFOProject(submissionStep[0][0], true);

			retValue = true;
		}

		return retValue;
	}

	private void assignProjOfficers() throws Exception {

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });

	}

	private void createPOProject() throws Exception {

		foProj = new FOProject(prog);

		boolean newOrg = true;

		foProj.createPOProject(newOrg);

		foProj.submitProject(true);
	}

}
