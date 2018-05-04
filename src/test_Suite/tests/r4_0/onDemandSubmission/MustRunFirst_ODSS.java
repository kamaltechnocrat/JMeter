package test_Suite.tests.r4_0.onDemandSubmission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;



/**
 * @author s.grobbelaar
 * 
 * Sets up A-ODSS-PA-FOPP for OnDemand Tests
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class MustRunFirst_ODSS {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	
	String eFormName = "A-ODSS-PA-Submission-A";
			
	private List<String> ss;

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();

			GeneralUtil.navigateToPO();

			GeneralUtil.logInSuper();

			// -----------------------------------
			ss = new ArrayList<String>();
			
			ss.add(IPreTestConst.Groups[0][0][0]);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		fopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize_ODSS() throws Exception{

		fopp = new FundingOpportunity("A", "-ODSS-PA-","");
			
		fopp.openFundingOppPlanner();
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize_ODSS")
	public void awardStepConfig() throws Exception{

		String step = "(Award)";
		
		fopp.manageEForm(step, "e.Form Access", eFormName);

		fopp.manageEForm(step, "e.Form Data", "Applicant Profile e.Form");


	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "awardStepConfig")
	public void postAwardConfig() throws Exception{

		fopp.expandInnerObj_eleReturn("(Post-Award)", "Steps");
		
		Assert.assertTrue(fopp.openStepDetails(false, "Closing pa Step"), "Fail: Could not open step to view details!");	
		
		fopp.aditFundingStepIdent(fopp.getFoppFullIdent().concat("-Closing-Step-pa"));
		
		fopp.aditFundingStepTitle(fopp.getFoppFullName().concat(" Closing Step pa"));
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "postAwardConfig")
	public void registerToFundingOpp() throws Exception{		
		
		GeneralUtil.switchToFO();
		
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
		
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), "Ouia 1"), "FAIL: could not register to FOPP: ".concat(fopp.getFoppFullName()));
		
	}
	
}