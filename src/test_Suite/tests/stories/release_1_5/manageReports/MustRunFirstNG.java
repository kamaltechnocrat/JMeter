/**
 * 
 */
package test_Suite.tests.stories.release_1_5.manageReports;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.users.IUAPConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.users.UAPs;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class MustRunFirstNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	int rowIndx = -1;
	
	int UserIndex;
	
	int[] arrUserIndex = {17,21,22,27};
	
	Users user;
	
	UAPs uaps;
	
	boolean[] uapsRights;
	
	ArrayList<String[]> uapList;

	boolean newProgram = true;
	
	boolean programForm = false;
	
	boolean newOrg = true;
	
	String preFix = "-S2071_xx-";
	
	char portaltype = 'P';
	
	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	
	Program prog;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ReportsNG" })
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

	@AfterClass(groups = { "ReportsNG" }, alwaysRun=true)
	public void tearDown() {
		
		user = null;
		uaps = null;
		prog = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ReportsNG" })
	public void createProgram() throws Exception {
		try {

			prog = new Program(preFix, portaltype, programForm, newProgram, false);
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			prog.initProgram();
			if (newProgram) {
				prog.setProgAdmin(progAdmin);
				prog.setProgOfficers(progOfficers);
				ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
				prog.createProgram();
				prog.addStep(IGeneralConst.gnrl_SubmissionA);
				prog.manageStep(new String[][] { { progOfficers[0] }});
				prog.addStep(IGeneralConst.reviewQuoCritAuto);
				prog.manageStep(new String[][] { { progOfficers[0] }});

				prog.activateProgram("Active");

				GeneralUtil.switchToFO();

				ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

				Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog.getProgFullName(), "Ouia 1"));
				
				GeneralUtil.switchToPO();
			}
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ReportsNG" })
	public void createUserAndGroup24NG() throws Exception {
		try {
			
			user = new Users(1, IUsersConst.users[24], "Users", "Program Office Users");
			
			createUsers();
			UsersAndGroupsUtil.addAccessRightsUAPTopLevel(IUAPConst.myReportsUAP_1st[0], IUAPConst.readOnly);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ReportsNG" }, dataProvider="users-Index")
	public void createUserAndGroupNG(int uIndex,boolean[] rights) throws Exception {
		try {
			rowIndx++;
			
			user = new Users(1, IUsersConst.users[uIndex], "Users", "Program Office Users");
			
			createUsers();
			
			initializeUAPs(rights);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@DataProvider(name = "users-Index")
	public Object[][] generateUsersAndGroups() throws Exception {
		
		return new Object[][] {
				{17,IUAPConst.deleteOnly},
				{21,IUAPConst.createOnly},
				{22,IUAPConst.readOnly},
				{27,IUAPConst.createDelete}
		};		
	}
	
	private void initializeUAPs(boolean[] rights) throws Exception {
		uaps = new UAPs();
		
		uapList = new ArrayList<String[]>();
		
		uapList.add(0, IUAPConst.administerG3UAP_1st);
		uapList.add(1, IUAPConst.administerG3UAP_Manage3rdPartyReports_3rd);		
		uapList.add(2, IUAPConst.administerG3UAP_ReportsDetails_4th);
		
		uapsRights = rights;		
		
		UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);
	}
	
	private void createUsers() throws Exception {
		Assert.assertTrue(user.createUser(), "Fail: To Create a User");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(), "Fail: to Create a Group");		

		Assert.assertTrue(user.addUsersToGroup(),"Fail: Adding Users to Group");				
	}
}
