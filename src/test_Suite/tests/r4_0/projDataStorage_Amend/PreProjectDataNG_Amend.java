/**
 * 
 */
package test_Suite.tests.r4_0.projDataStorage_Amend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class PreProjectDataNG_Amend {

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
			
			fopp = new FundingOpportunity("A", IFoppConst.projData_PA_Prefix, IFoppConst.projData_pushBack_Postfix);

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
			
			Assert.assertTrue(GeneralUtil.setTextById(IFoppConst.commBal_Budget_InitialBal_Ttl, "90000000000.00" ), "FAIL: Could not enter Text!");
			
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
				
				{"Payment", GeneralUtil.getTodayDate(), fopp.getEndDate(),"PEF-Post Award Submission-PushBack", "true", "false"},
				
				{"Quarter Report", GeneralUtil.getTodayDate(), fopp.getEndDate(),"PEF-Post Award Submission-PushBack", "true", "false"},
				
				{"Annual Report", GeneralUtil.getTodayDate(), fopp.getEndDate(),"PEF-Post Award Submission-PushBack", "true", "false"}
			};
			
			FOPPUtil.manageSchedules(scheduleData);
			
			ClicksUtil.clickLinks("Formlet Assoc");
			
			Assert.assertTrue(GeneralUtil.setTextById("Enter Any Text", "3500000.00" ), "FAIL: Could not enter Text!");
			
			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"enterAwardAmountToAdminNG", "enterInitialBalanceNG"})
	public void RegisterToFoppNG() throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			
			GeneralUtil.loginAnyFO("front3");
			
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
			
			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(),"Ouia 3"),
					"FAIL: Could not register to Funding Opp.!");
			
			fopp = new FundingOpportunity("A", IFoppConst.projData_PA_Prefix, IFoppConst.projData_LBF_Postfix);			

			
			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(),"Ouia 3"),
					"FAIL: Could not register to Funding Opp.!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
