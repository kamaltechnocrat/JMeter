package test_Suite.tests.r4_0.ProjectEForm_PushBack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.StepsUtil;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Verify_NoProp_PushBack {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToFO();
			
			GeneralUtil.loginAnyFO("front");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.projData_PA_Prefix, IFoppConst.projData_pushBack_Postfix);
			
			foProj = new FOProject(fopp, "", true, 3, "-EvaluateProjectListTesting");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		    fopp = null;
		    foProj = null;
		
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		
			log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	
	@Test(groups = { "ProjectsNG" })
	public void createNewProjectAndSubmit() throws Exception {
		
		foProj.createFOProjectNewNew();
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Submission"));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl("Enter Any Text", "Submit"));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Submission B"));
		
		Assert.assertTrue(StepsUtil.insertTo_NoPropListFormlet(ILBFunctionConst.lbf_NoProperties_Fields_eList[0]));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));		
	}
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods =  {"createNewProjectAndSubmit" })
	public void verifyDataInProjectList() throws Exception{
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ProjectUtil.openPOProjectList(foProj,"Review", "Open Projects"));
		
		Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
		
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - No Propreties Fields PEF"));
		
	}
	

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "verifyDataInProjectList" )
	public void verifyFieldsAnswers() throws Exception
	{
		
		Assert.assertTrue(StepsUtil.verify_NO_Prop_Fields_Answers(ILBFunctionConst.lbf_NoProperties_Fields_eList[0]));
		
	}
	
	
}
