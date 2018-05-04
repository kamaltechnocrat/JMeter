package test_Suite.tests.users;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.users.IUAPConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.users.UAPs;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

@GUITest
@Test(singleThreaded = true)
public class CreateUsersNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	ArrayList<String[]> uapList;

	UAPs uaps;
	Users user;
	FOUsers foUser;
	Integer userBeat = 5;
	Integer startIndex;
	Integer endIndex;
	int UserIndex = 2;
	String group;
	String lastName;

	String primOrg = "G3";

	boolean[] uapsRights = IUAPConst.createDelete;

	// boolean[] uapsRights = IUAPConst.createRead;
	// boolean[] uapsRights = IUAPConst.readDelete;
	// boolean[] uapsRights = IUAPConst.createReadUpdate;

	// ---------------------------- End of Params ---------------------------------

	@BeforeClass(groups = { "UsersNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// GeneralUtil.loginPO();
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "UsersNG" }, alwaysRun=true)
	public void tearDown() {
		
		uaps = null;
		user  = null;
		foUser = null;
		
		
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "UsersNG" })
	public void createUsersNG() throws Exception {

		try {

			for (UserIndex = 16; UserIndex <= 16; UserIndex++) {
				initializeUsers();
				createUsers();
			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 
	}
	
	private void initializeUsers() throws Exception {
		
		user = new Users(5, IPreTestConst.MultiUsers[UserIndex], "Users", "Program Office Users");
		uaps = new UAPs();
		uapList = new ArrayList<String[]>();

		uapList.add(0, IUAPConst.manageProjectsUAP_1st);
		uapList.add(1, IUAPConst.manageProjectsUAP_AccessProjectsList_2nd);
		uapList.add(2, IUAPConst.manageProjectsUAP_SubmissionHistory_3rd);
		uapList.add(3, IUAPConst.manageProjectsUAP_AccessMyInBasket_2nd);
		uapList.add(4, IUAPConst.manageProjectsUAP_MyProjectSubmissions_3rd);
		uapList.add(5, IUAPConst.manageProjectsUAP_MyAssignedSubmissions_3rd);
		uapList.add(6, IUAPConst.manageProjectsUAP_AccessApplicantList_2nd);
		uapList.add(7, IUAPConst.manageProjectsUAP_ApplicantSubmissionsList_3rd);
		uapList.add(8, IUAPConst.manageProjectsUAP_RegistantsList_3rd);
		uapList.add(9, IUAPConst.manageProjectsUAP_ManageAmendments_2nd);
		uapList.add(10, IUAPConst.manageProjectsUAP_AccessAmendmentList_3rd);
		
		uapsRights = IUAPConst.createDelete;

	}
	
	private void createUsers() throws Exception {
		
		Assert.assertTrue(user.createUser(), "Fail: To Create a User");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(), "Fail: to Create a Group");

		UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);
		
		uapList = new ArrayList<String[]>();
		uapList.add(0, IUAPConst.intakeSubmissionsUAP_1st);
		uapList.add(1, IUAPConst.intakeSubmissionsUAP_IntakeInBasket_2nd);		
		UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);
		
		uapList = new ArrayList<String[]>();
		uapList.add(0, IUAPConst.manageAwardsUAP_1st);
		uapList.add(1, IUAPConst.manageAwardsUAP_Sub1st);		
		UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);
		
		uapList = new ArrayList<String[]>();
		uapList.add(0, IUAPConst.manageProjectEvaluationsUAP_1st);
		uapList.add(1, IUAPConst.manageProjectEvaluationsUAP_Sub1st);		
		UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);

		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Adding Users to Group");
	}
}
