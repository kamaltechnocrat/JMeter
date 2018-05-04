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
public class QuarterlyReport_StepExit_StartDay {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String propKey = "scenario_35";
	
	FundingOpportunity fopp;

	FOProject foProj;
	
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
	
	String peterAmons = "PeterAmons";

	
	
	

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
			sov = SovUtils.techEdFy13_Initialization(ESovFOPPs.TechEdFY13, ESovTechEdApplicantsNum.T173, "Quarterly Financial Reports Loop", "TEE Q2_3330");
			
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
	
	
	@Test(groups = { "SovRegTest" })
	public void amendQuarterlyReports() throws Exception{
		
        foProj.setProjFullName("TEE Staf FY13 QuarterlyReport-35");
		
		GeneralUtil.setNotificationMtrixProp(propKey,
					foProj.getProjFullName());	
		
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.LoginAny(poStaff3);
		
		sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
		
		Assert.assertTrue(SovUtils.amendment_SOV_PA(sov,0,"TEE Q2_3330","Quarterly Financial Report"), "FAIL: to amend TEE Staf FY13 step");
		
	}
	
	@Test(groups = { "SovRegTest" },dependsOnMethods = { "amendQuarterlyReports" })
	public void submitQuarterlyReports() throws Exception{
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO(peterAmons);
		
		sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
		
		sov.setTmpCurrStepName(sov.getCurrStepName());
		
		sov.setFoProjAndSubProjName(sov.getFoProjName().concat(" - ").concat(sov.getSubProjectName()));
		
		sov.setCurrStepName(sov.getFoProjAndSubProjName());
		
		Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport_SOV(sov), "FAIL: to Submit PA Quarterly Financial Report");
	}
	
	
	@Test(groups = { "SovRegTest" }, dataProvider = "NotifNameIndex", dependsOnMethods = { "submitQuarterlyReports" }, enabled = true)
	public void verifyQuarterlyFinancialReport(int NotifIndex, int entryCount,
			boolean resultExpected) throws Exception {
		try {
			GeneralUtil.switchToPOOnly();
			
			GeneralUtil.LoginAny(poStaff3);
			
			foProj.setProjFullName("TEE Staf FY13");

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject_New(
					foProj, ISovConst.sovTechEdStepsNames[12]),
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

	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception {

		return new Object[][] { { 22, 4, true }

		};
}
	
	
	
}