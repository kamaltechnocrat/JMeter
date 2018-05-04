/**
 * 
 */
package test_Suite.tests.r4_0.projDataStorage;

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
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestEvaluateProjectsListNG {

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
			
			GeneralUtil.loginAnyFO("front3");
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

	/**
	 * 
	 * @throws Exception
	 */
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

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"createNewProjectAndSubmit"})
	public void reviewAndSubmit() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
		
		ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Review", "Ready", "Min And Max Propreties Fields PEF", false, "1", "");
		
		StepsUtil.insertTo_MinMaxPropListFormlet(ILBFunctionConst.lbf_MinMaxProperties_Fields_eList[0]);
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"reviewAndSubmit"})
	public void awardAndSubmit() throws Exception {
		
		GeneralUtil.switchToPO();
		
		ProjectUtil.openPOAwardFormletInList(foProj, "Standard Award", "Type Propreties Fields PEF");
		
		StepsUtil.insertTo_TypePropListFormlet(ILBFunctionConst.lbf_TypeProperties_Fields_eList[0]);
		
		StepsUtil.insertTo_SubScheduleListFormlet1("PEF-Post Award Submission-PushBack",ILBFunctionConst.lbf_SubSchedules_Fields_eList[0]);
		
		
	}

}
