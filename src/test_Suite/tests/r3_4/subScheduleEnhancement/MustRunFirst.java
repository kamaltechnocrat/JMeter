/**
 * 
 */
package test_Suite.tests.r3_4.subScheduleEnhancement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author k.sharma
 *
 */
public class MustRunFirst {
	
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Gnrl-PA-Notification-","");
			
			foProj = new FOProject(fopp,"Project-Schedule-Notification", true,1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"NotificationNG"})
	public void registerToFopp() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			
//			GeneralUtil.switchToPO();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}	
	
//	@Test(groups = {"NotificationNG"}, dependsOnMethods="registerToFopp")
//	public void activateAmendmentCategory() throws Exception {
//		
//		ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
//		
//		LookupUtil.taggleLookupActiveness(IAmendmentsConst.amendCategory_LookupName, ITablesConst.lookupListTableId);
//			
//	}


}
