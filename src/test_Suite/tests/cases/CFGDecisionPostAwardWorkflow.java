/**
 * 
 */
package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
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
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CFGDecisionPostAwardWorkflow {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "cfgNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front3");
			// ----------------------------------- EFormsUtil.createAnyRandomString(5)
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "cfgNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"cfgNG"})
	public void initialize() throws Exception {

		fopp = new FundingOpportunity("", "PA-Step-Amandments-Re-Execute", "CFG-", "");
		
		foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "Re-Execute-PA-Demo-");

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
		
		FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), "Ouia 3");

		foProj.createFOProject();
		
		foProj.submitFOProject("Application", true);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "cfgNG" }, dependsOnMethods = "initialize")
	public void testCaseTemplateNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			foProj.submitFromMyAssignedSubListNew("Program-Officer-e.Sign", "Test", true);
			
//			foProj.submitAward("Standard", 5, true);
//			
//			for (int i = 1; i < 6; i++) {
//				
//				foProj.setClaimNumber(i);
//				
//				foProj.submitFromMyAssignedSubListNew("PA-Submission", "Test", true);
//				
//			}
//			
//			for (int i = 1; i < 6; i++) {
//				
//				foProj.setClaimNumber(i);
//				
//				foProj.reviewSubmission("PA-Review", true, "Yes", false, "Ready");
//				
//			}
//			
//			for (int i = 1; i < 6; i++) {
//				
//				foProj.setClaimNumber(i);
//				
//				foProj.submitFromMyAssignedSubListNew("PA-Close", "Test", true);
//				
//			}
//			
//			foProj.setClaimNumber(0);
			
			foProj.reviewSubmission("Finance-Grant-Agreement-Review", true, "Yes", false, "Ready");
			
			foProj.openStandardAgreement("Finance-Create-Submission-Schedule");
			
			foProj.addSchedules("CFG-Quarterly-Reports", 4, false, false, "Title I - Q", 1);
			
			foProj.addSchedules("CFG-Quarterly-Reports", 4, false, false, "Title II - Q", 1);
			
			foProj.addSchedules("CFG-Annual-Reports", 1, false, false, "Title I - Initial", 1);
			
			foProj.addSchedules("CFG-Annual-Reports", 1, false, false, "Title II - Initial", 1);
			
			foProj.addSchedules("CFG-Financial-Reports", 1, true, true, "Title I - Final", 1);
			
			String[] claims = {"Title I - Q1","Title I - Q2","Title I - Q3","Title I - Q4","Title II - Q1","Title II - Q2","Title II - Q3","Title II - Q4","Title I - Initial1","Title II - Initial1","Title I - Final1"};
			
			String projName = foProj.getProjFOFullName();
			
			//---- Submit Initial Claims -----			
			for (String string : claims) {
				
				foProj.setProjFullName(projName.concat(" - ".concat(string)));
				
				String val = "1.00";
				
				if(string.endsWith("Initial1"))
				{
					val = "2.00";
				}
				else if(string.endsWith("Final1"))
				{
					val = "3.00";
				}
				
				foProj.submitFromMyAssignedSubListNew("Initial-Claim-Report", val, true);
				
			}
			
			//------ Review Claims -----
			
			for (String string : claims) {
				
				foProj.setProjFullName(projName.concat(" - ".concat(string)));
				
				if(string.endsWith("Initial1"))
				{					
					foProj.reviewSubmission("Review-For-Annual-Reports", true, "Yes", false, "Ready");
				}
				else if(string.endsWith("Final1"))
				{					
					foProj.reviewSubmission("Review-For-Financial-Reports", true, "Yes", false, "Ready");
				}
				else
				{					
					foProj.reviewSubmission("Review-Quarterly-Reports", true, "Yes", false, "Ready");						
				}			
			}
			
			//------ Approve Claims -----
			
			for (String string : claims) {
				
				foProj.setProjFullName(projName.concat(" - ".concat(string)));
				
				if(string.endsWith("Initial1"))
				{
					foProj.approveSubmission("Approval-For-Annual-Reports", true, "Ready", false, false);
				}
				else if(string.endsWith("Final1"))
				{
					foProj.approveSubmission("Approval-For-Financial-Reports", true, "Ready", false, false);
				}
				else
				{					
					foProj.approveSubmission("Approval-Quarterly-Reports", true, "Ready", false, false);
				}				
			}	
			
			//------ Approve Second -----
			
			for (String string : claims) {
				
				foProj.setProjFullName(projName.concat(" - ".concat(string)));
				
				foProj.approveSubmission("Second-Approval", true, "Ready", false, false);
			}	
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
