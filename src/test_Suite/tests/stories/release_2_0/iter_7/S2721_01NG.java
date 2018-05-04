/**
 * Case 01 for Story 2721 Add UAP for Optional Step Re-execute
 * Steps:
 * 1. Create Program with Optional Re-Execute set
 * 	1.1 Sub
 * `1.2 Approval Optional Re-execute
 * 	1.3 Review (Closing Step) * 
 * 2. Submit Submission and Approval
 * 
 * Test 1
 * 1. Remove user Optional Step Re-execute CRUD.
 * 2. Request an Amendment
 * Result:
 * 		Not able to go through Optional Re-excute UI.
 * 
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_7;

/**
 * @author mshakshouki
 *
 */

import java.util.ArrayList;

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
import test_Suite.constants.users.IUAPConst;
import test_Suite.lib.users.UAPs;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2721_01NG {

	/***************************************************************************
	 * To set up the Global Params Name Vars
	 **************************************************************************/
	Class<? extends S2721_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Program prog;

	ProgramSteps progStep;

	Project proj;
	
	FOProject foProj;

	String preFix = "-Amendment_UAP-";

	String postFix = "-S2721_1";

	boolean newProgram = false;
	boolean programForm = false;
	boolean publicationForm = false;
	boolean newOrg = true;

	boolean[] uapsRights;

	static String optionalStepRe_ExecName;

	Users user;
	UAPs uaps;
	ArrayList<String[]> uapList;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String approvStep[][] = {
			{ "Approval-A", "/approvalStep/", "Approval", "true",
					"Optional (No)" },
			{ "Project Approval Form", "Quorum", "1", "true" } };
	String reviewStep[][] = IGeneralConst.gnrl_ReviewA;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// --------------------------

		initializeProgram();

		GeneralUtil.switchToFO();

		submitFOProject();

		GeneralUtil.switchToPO();

		assignOfficers();
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		changeUserUAP(IUAPConst.allRightsTrue);
		
		prog = null;
		foProj = null;
		progStep = null;
		proj = null;
		user = null;
		uaps = null;
		uapList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "ProjectsNG" })
	public void testOverrideDefaultOptionalStepReExecuteUAPExistance()
			throws Exception {

		changeUserUAP(IUAPConst.allRightsFalse);

	}
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testOverrideDefaultOptionalStepReExecuteUAPExistance")
	public void testAmendmentIconExistanceWith_No_ReExecuteUAPNG() throws Exception {
		
		GeneralUtil.switchToPO();

		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil
				.doesAmendmentRequestIconExists(new String[] {
						foProj.getProjFOFullName(), submissionStep[0][0],
						IPreTestConst.Groups[0][1][0], dd,
						"Test Optioanl Re-Exec Previous Amendment",
						"This is a Comment" }, foProj),
				"Failed: Should be an Amendment Icon!");
		
	}

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testAmendmentIconExistanceWith_No_ReExecuteUAPNG")
	public void testAmendmentWith_No_ReExecuteUAPNG() throws Exception {

		try {

			String dd = GeneralUtil.setDayofMonth(3);
			Assert.assertTrue(AmendmentsUtil
					.issueAmendment(new String[] { foProj.getProjFOFullName(),
							submissionStep[0][0], IPreTestConst.Groups[0][1][0],
							dd, "Test Optioanl Re-Exec Previous Amendment",
							"This is a Comment" }, foProj), "Failed to issue an Amendment");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	private void changeUserUAP(boolean[] uapsRights) throws Exception {
		
		try {

			user = new Users(1, IPreTestConst.SingleUsers[0], "Group", "Program Office Users");

			uaps = new UAPs();
			uapList = new ArrayList<String[]>();

			uapList.add(0, IUAPConst.manageProjectsUAP_1st);
			uapList.add(1, IUAPConst.manageProjectsUAP_AccessMyInBasket_2nd);
			uapList.add(2, new String[] { IUAPConst.manageProjectsUAP_MyProjectSubmissions_3rd[0], "Override Default Optional Step Re-execution" });

			Assert.assertTrue(user.createGroup(), "Fail: to Create a Group");

			UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);

			log.info("Saving shak effective UAP");
			UsersAndGroupsUtil.saveUserByUserName(IPreTestConst.PO_UsrName);
			
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void initializeProgram() throws Exception {

		prog = new Program();

		prog.setProgPreFix(preFix);

		prog.setProgPostfix(postFix);

		prog.setNewProgram(newProgram);

		prog.setProgPortal('F');

		prog.setPublicationForm(publicationForm);

		prog.setProgForm(programForm);

		prog.setProgIdent(prog.getProgPreFix() + IGeneralConst.gnrl_ProgName);

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		prog.setProgramOfficer(IPreTestConst.ProgPOfficer);

		prog.setStepOfficer(IPreTestConst.AppGroupName);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.setEndDate(GeneralUtil.getNextYear());

		prog.initProgram();

	}

	private void submitFOProject() throws Exception {

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"Fail: Could not Register To " + prog.getProgFullName());

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");

	}

	private void assignOfficers() throws Exception {

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

	}

}
