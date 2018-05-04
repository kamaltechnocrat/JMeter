/**
 * 
 */
package test_Suite.tests.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class UpdatingProjectNameAndNumber {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String preFix = IFoppConst.fundingOpp_PA_Prefix;
	String postFix = "";

	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String newProjNumber;

	FundingOpportunity fopp;
	FOProject foProj;

	// -------------- End of Global parameters ----------------------
	
	@BeforeClass(groups="WorkflowNG")
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToFO();
		GeneralUtil.LoginFO();

		// *******************************************

	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"WorkflowNG"})
	private void initialize() throws Exception {
		
		fopp = new FundingOpportunity("A", preFix,postFix);
	}

	@Test(groups="WorkflowNG", dependsOnMethods = "initialize")
	public void registerAndSubmitFOProject() throws Exception {

		foProj = new FOProject(fopp,"ProjUpdate-",true,1,EFormsUtil.createAnyRandomString(5));

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"FAIL: Could not Find Project In FO Submission List!");
	}

	@Test(groups="WorkflowNG", dependsOnMethods="registerAndSubmitFOProject")
	public void approveApplication() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
				"Ready", false, false));
	}
	
	@Test(groups="WorkflowNG", dependsOnMethods="approveApplication")
	public void submitAward() throws Exception {
		
		GeneralUtil.switchToPO();

		Assert.assertTrue(foProj.submitAward("Standard", 2, true),
				"FAIL: Could not Submit Award Form");
	}
	
	@Test(groups="WorkflowNG", dependsOnMethods="submitAward")
	public void changeInitialClaimProjectNumber() throws Exception
	{
		foProj.openProjectDetailPage();
		
		foProj.setProjFullName("New ProjectName " + foProj.getProjLetter());
		
		foProj.changeProjectName(foProj.getProjFullName());
		
		newProjNumber = "new.123456" + foProj.getProjLetter();
		
		foProj.changeProjectNumber(newProjNumber);		
	}
	
	@Test(groups="WorkflowNG", dependsOnMethods="changeInitialClaimProjectNumber")
	public void testInitialClaimProjectNumberChangedInFO() throws Exception {

		GeneralUtil.switchToFO();
		
		Assert.assertEquals(foProj.getProjNumberForStep(paInitialClaim[0][0]), newProjNumber.concat(" - Claim 1"));		
	}

}
