/**
 * Post Sanity Test will create all the usual Suspects to run a regression test
 */
package test_Suite.tests.sanity;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.preTest.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.users.*;
import test_Suite.lib.users.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;

/**
 * @author mshakshouki
 * 
 */
@SuppressWarnings("rawtypes")
@GUITest
@Test(singleThreaded = true)
public class PostSanityTestNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory.getLog(PostSanityTestNG.class);

	Users user;

	UAPs uaps;

	FOUsers foUser;

	ArrayList uapList;

	int UserIndex;

	boolean[] uapsRights = IUAPConst.allRightsTrue;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass
	public void setUp() {

		// code that will be invoked when this test is instantiated
	}

	@Test(groups = { "SanityNG" })
	public void postSanityTestNG() throws Exception {

		try {

			log.info("Opening New Window and Login as Admin");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			// GeneralUtil.loginPO();
			GeneralUtil.loginSanity("1");

			createProgramOfficers();

			createStaff();
			createProjectOfficers();
			createDelegates();
			createApprovers();
			createReviewers();

			OrgUtil.changeOrgOfficer(IOrgConst.org_G3Root_ShortName,
					IPreTestConst.PO_LName);

			createFOUsersandApplicants();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} finally {
			
			uaps = null;
			uapList = null;
			foUser = null;
			user = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}

	}

	private void createFOUsersandApplicants() throws Exception {

		int userBeat = 1;
		int startIndex = 1;
		int endIndex = 1;

		foUser = new FOUsers(userBeat, startIndex, endIndex);

		foUser.setFoApplicants(IPreTestConst.FrontUsers[0]);

		foUser.setFoRegistrants(IPreTestConst.FrontUsers[1]);

		foUser.setFoOrgName(IPreTestConst.FO_OrgShortName);

		GeneralUtil.setFOConfigOption(true, true);

		GeneralUtil.setApplicantWorkspace();
		log.debug("set applicant workspace OK");

		// ************* Switch to Front Office Portal ***********************
		log.debug("logging off");
		GeneralUtil.Logoff();
		log.debug("closing browser");
		IEUtil.closeBrowser();
		GeneralUtil.takeANap(0.5);

		IEUtil.openNewBrowser();
		log.debug("opened new browser");
		GeneralUtil.navigateToFO();
		log.debug("nav'd to FO");

		foUser.createProfiles();

		foUser.createFOApplicant();

		log.debug("logging off");
		GeneralUtil.Logoff();
		log.debug("closing browser");
		IEUtil.closeBrowser();
		GeneralUtil.takeANap(0.5);

	}

	private void createStaff() throws Exception {

		UserIndex = 0; // shak

		user = new Users(1, IPreTestConst.SingleUsers[0], "Users", "Program Office Users");

		uaps = new UAPs();

		uapList = new ArrayList();

//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.administerG3_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.intakeSubmissions_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.myReports_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageAwards_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageForms_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageDocuments_1st));
//		uapList.add(uaps
//				.initSecondLevelUAP(IUAPConst.GrantProgramAdministration_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageProjects_1st));
//		uapList.add(uaps
//				.initSecondLevelUAP(IUAPConst.manageProjectEvaluations_1st));

		log.info("Start Creating Shak!");

		Assert.assertTrue(user.createSingleUser(), "Fail: To Create Shak");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(), "Fail: To Create Staff");

		log.info("Setting up UAP to the Group!");

		//user.setGroupUAPRights(uapList, uapsRights);

		log.info("Associating Users with the Group!");

		Assert.assertTrue(user.setUserToGroup("", "Staff", "Shakshouki", true),
				"Fail: Adding Shak to Staff");

	}

	private void createProjectOfficers() throws Exception {

		user = new Users(3, IPreTestConst.MultiUsers[5], "Users", "Program Office Users");

		uapList = new ArrayList();

		uaps = new UAPs();

//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.intakeSubmissions_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageProjects_1st));
//		uapList.add(uaps
//				.initSecondLevelUAP(IUAPConst.manageProjectEvaluations_1st));
//		uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageAwards_1st));

		log.info("Start Creating Users!");

		Assert.assertTrue(user.createUser(), "Fail: Unable to Create Users");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(),
				"Fail: Unable to Create Group Project Officers");

		log.info("Setting up UAP to the Group!");

		//user.setGroupUAPRights(uapList, uapsRights);

		log.info("Associating Users with the Group!");
		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Unable to Add Officers to Group");

	}

	private void createProgramOfficers() throws Exception {

		user = new Users(3, IPreTestConst.MultiUsers[4], "Users", "Program Office Users");

		uapList = new ArrayList();

		uaps = new UAPs();

		//uapList.add(uaps.initSecondLevelUAP(IUAPConst.GrantProgramAdministration_1st));

		log.info("Start Creating Users!");

		Assert.assertTrue(user.createUser(), "Fail: Unable to Create Users");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(),
				"Fail: Unable to Create Group Program Officers");

		log.info("Setting up UAP to the Group!");

		//user.setGroupUAPRights(uapList, uapsRights);

		log.info("Associating Users with the Group!");
		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Unable to Add Officers to Group");

	}

	private void createReviewers() throws Exception {

		user = new Users(3, IPreTestConst.MultiUsers[1], "Users", "Program Office Users");

		uapList = new ArrayList();

		uaps = new UAPs();

		//uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageProjectEvaluations_1st));

		log.info("Start Creating Users!");

		Assert
				.assertTrue(user.createUser(),
						"Fail: Unable to Create Reviewers");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(),
				"Fail: Unable to Create Group Reviewers");

		log.info("Setting up UAP to the Group!");

		//user.setGroupUAPRights(uapList, uapsRights);

		log.info("Associating Users with the Group!");
		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Unable to Add Reviewers to Group");

	}

	private void createApprovers() throws Exception {

		user = new Users(3, IPreTestConst.MultiUsers[0], "Users", "Program Office Users");
		uaps = new UAPs();

		uapList = new ArrayList();

		//uapList.add(uaps.initSecondLevelUAP(IUAPConst.manageProjectEvaluations_1st));

		log.info("Start Creating Users!");

		Assert
				.assertTrue(user.createUser(),
						"Fail: Unable to Create Approvers");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(),
				"Fail: Unable to Create Group Approvers");

		log.info("Setting up UAP to the Group!");

		//user.setGroupUAPRights(uapList, uapsRights);

		log.info("Associating Users with the Group!");
		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Unable to Add Approvers to Group");

	}

	private void createDelegates() throws Exception {

		user = new Users(3, IPreTestConst.MultiUsers[2], "Users", "Program Office Users");
		uaps = new UAPs();

		uapList = new ArrayList();

		//uapList.add(uaps.initSecondLevelUAP(IUAPConst.intakeSubmissions_1st));

		log.info("Start Creating Users!");

		Assert.assertTrue(user.createUser(),
				"Fail: Unable to Create Intake Officers");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(),
				"Fail: Unable to Create Group Intake Officers");

		log.info("Setting up UAP to the Group!");

		//user.setGroupUAPRights(uapList, uapsRights);

		log.info("Associating Users with the Group!");
		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Unable to Add Intake Officers to Group");

	}
}
