package test_Suite.tests.clients.sov.tech_ed_fy13.SOV_Notification_Amendments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.clients.ISovConst;
import test_Suite.constants.clients.ISovConst.ESovFOPPs;
import test_Suite.constants.clients.ISovConst.ESovTechEdApplicantsNum;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.clients.Sov;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.clients.SovUtils;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;

/**
 * @author sFatima
 *
 */
@GUITest
@Test(singleThreaded = true)
public class SubmissionSchedule_Amendment_StepExit_Day1{

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String propKey = "scenario_30";
	
	FundingOpportunity fopp;

	FOProject foProj;
	
	String marthaGagner = "MarthaGagner";
	
	String kathrynDaley = "KathrynDaley";
	
	String rickHilton = "RickHilton";
	
	String EdHaggett = "Ed.Haggett";
	
	String foUser = "ChristopherMasson";
	
	String poOfficer1 = "Jane.Murtagh";
	
	String poPAOfficer1 = "Mark.Lang";
	
	String poStaff1 = "Jay.Ramsey";
	
	String poStaff2 = "Bill.Talbott";
	
	String poStaff3 = "Doeadmin";
	
	String poStaff4 = "Tom.Alderman";
	
	String poStaff5 = "John.Leu";
	
	

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO(kathrynDaley);
			// -----------------------------------

			fopp = new FundingOpportunity("Tech Ed Equip FY13");

			foProj = new FOProject();
			
			GeneralUtil.switchToPOOnly();
			
			GeneralUtil.LoginAny(poStaff3);
			// -----------------------------------
			sov = SovUtils.techEdFy13_Initialization(ESovFOPPs.TechEdFY13, ESovTechEdApplicantsNum.U048, "Finance Submission Schedule", "TEE Nowest FY13");
			
			sov.setFoProjName(sov.getProjectName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName());

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "SovRegTest" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		try {
			sov = null;
			fopp = null;
			foProj = null;
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}
	
	
	
	@Test(groups = { "SovRegTest" }, dataProvider = "NotifNameIndex_Amend", enabled = true)
	public void verifySubmissionSchedule_Amendment(int NotifIndex, int entryCount,
			boolean resultExpected) throws Exception {
		try {
			
			String projFullName = GeneralUtil.getNotifMatrixPropValue(propKey);

			Assert.assertNotNull(projFullName,
					"FAIL: could not get the Value for Key: ".concat(propKey));

			Assert.assertNotSame(projFullName, "",
					"FAIL: this is an empty value for the Key: "
							.concat(propKey));
			
			GeneralUtil.switchToPOOnly();
			
			GeneralUtil.LoginAny(poStaff3);
			
			foProj.setProjFullName("TEE Nowest FY13");

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject_New(
					foProj, ISovConst.sovTechEdStepsNames[11]),
					"FAIL: Could not open the notification log");

			Assert.assertEquals(
					NotificationsUtil
							.verifyNotificationLogEntries_SOV(
									INotificationsConst.notifTechEdFy13_NotifName[NotifIndex],
									IFiltersConst.exact,IFiltersConst.date,0), entryCount,
					"FAIL: wrong Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "SovRegTest" }, dependsOnMethods = { "verifySubmissionSchedule_Amendment" })
	public void submitFinanceCreateSubmissionSchedule() throws Exception{
		
			GeneralUtil.switchToPOOnly();
	
			GeneralUtil.LoginAny(poPAOfficer1);

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[11]);
	
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule_New(sov), "FAIL: to Submit Finance Create Submission Schedule");

	
	}
	
	@Test(groups = { "SovRegTest" }, dataProvider = "NotifNameIndex_Submit", dependsOnMethods = { "submitFinanceCreateSubmissionSchedule" }, enabled = true)
	public void verifySubmissionSchedule_Submit(int NotifIndex, int entryCount,
			boolean resultExpected) throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			
			GeneralUtil.LoginAny(poStaff3);
			
			foProj.setProjFullName("TEE Nowest FY13");

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject_New(
					foProj, ISovConst.sovTechEdStepsNames[11]),
					"FAIL: Could not open the notification log");

			Assert.assertEquals(
					NotificationsUtil
							.verifyNotificationLogEntries_SOV(
									INotificationsConst.notifTechEdFy13_NotifName[NotifIndex],
									IFiltersConst.exact,IFiltersConst.date,0), entryCount,
					"FAIL: wrong Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "NotifNameIndex_Submit")
	public static Object[][] generateNotifNameIndex() throws Exception {

		return new Object[][] { { 16, 4, true }

		};
}
	
	
	
	@DataProvider(name = "NotifNameIndex_Amend")
	public static Object[][] generateNotifNameIndex_Amend() throws Exception {

		return new Object[][] { { 15, 0, true }

		};
}
	
	
	
	
}