/**
 * 
 */
package test_Suite.tests.r3_3_9;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class PrepareBudgetingFirst {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.commBal_PA_Prefix, "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void enterInitialBalanceNG() throws Exception {
		try {
			
			fopp.openFundingOppPlanner();
			
			fopp.openBudgetingEForm();
			
			Assert.assertTrue(GeneralUtil.enterTextByTitle(IFoppConst.commBal_Budget_InitialBal_Ttl, "90000000.00" ), "FAIL: Could not enter Text!");
			
			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void enterAwardAmountToAdminNG() throws Exception {
		try {
			
			fopp.openFundingOppPlanner();
			
			fopp.openAdminEForm();
			
			String[][] scheduleData = new String[][] {
				
				{"Payment", GeneralUtil.getTodayDate(), fopp.getEndDate(),"CommBal-Post-Award-Submission", "true", "false"},
				{"Quarter Report", GeneralUtil.getTodayDate(), fopp.getEndDate(),"CommBal-Post-Award-Submission", "true", "false"},
				{"Annual Report", GeneralUtil.getTodayDate(), fopp.getEndDate(),"CommBal-Post-Award-Submission", "true", "false"}
			};
			
			FOPPUtil.manageSchedules(scheduleData);
			
			ClicksUtil.clickLinks("Formlet Assoc");
			
			Assert.assertTrue(GeneralUtil.enterTextByTitle("Enter Any Text", "3000000.00" ), "FAIL: Could not enter Text!");
			
			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, enabled=false)
	public void reconfigureAlgorithmScoring() throws Exception {
		try {
			
			Assert.assertTrue(FOPPUtil.configureScoringAlgorithm(fopp, "A-CommBal-PA-Approval-CRQA", "%{CurrentDate|N}", "Date"),"FAIL: Could not configure a scoring Algorithm");
			
			Assert.assertTrue(FOPPUtil.configureScoringAlgorithm(fopp, "A-CommBal-PA-Approval-CRQA", "\"Approved\"", "Approved"),"FAIL: Could not configure a scoring Algorithm");
			
			Assert.assertTrue(FOPPUtil.configureScoringAlgorithm(fopp, "A-CommBal-PA-Approval-CRQA", "%{CurrentUserLastName}", "Long Text"),"FAIL: Could not configure a scoring Algorithm");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
			
			Assert.assertTrue(FOPPUtil.configureScoringAlgorithm(fopp, "A-CommBal-PA-Awarded-Review-CRQA", "3000000", "Awarded-Amount"),"FAIL: Could not configure a scoring Algorithm");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dataProvider = "Applicants-Data", dependsOnMethods = {"enterInitialBalanceNG","enterAwardAmountToAdminNG"})
	public void registerToFOPP(String usrName, String orgName) throws Exception {
		try {
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(usrName);

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
			
			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), orgName),
			"FAIL: Could not Register to Funding Opp.!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}
	
	
	@DataProvider(name = "Applicants-Data")
	public static Object[][] generateRegLoginData() throws Exception{
		
		return new Object[][] {
				{"front", "Ouia 1"},
				{"front2", "Ouia 2"},
				{"front3", "Ouia 3"}
				
		};
		
	}

}
