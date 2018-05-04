/**
 * 
 */
package test_Suite.tests.r3_2.hideAssignedEvaluators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IStepsConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.ProgramUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class VerifySettingAndSavingGroupsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project proj;
	
	FundingOpportunity fopp;
	
	String[] grpsArr = {"G03 Approvers","Staff","G06 Project Officers"};

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-","");
			proj = new Project(fopp,"",true,true,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		deSelectingTheGroupsNG();
		
		fopp = null;
		proj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void selectingTheGroupsNG() throws Exception {
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			
			Assert.assertTrue(fopp.openFundingOppPlanner(),"FAILE: could not open FOPP Planner!");
			
			proj.initializeStep(IGeneralConst.approvNonRexQuoAuto[0][0]);
			
			Assert.assertTrue(ProgramUtil.openStepDetails(ProgramUtil.findStepInProgPlanner(proj.getCurrentStepIdent(), 0)), "FAIL: could Open Step Details");
			
			Assert.assertTrue(FOPPUtil.selectEvaluationVisibilityGroups(grpsArr), "FAIL: could not select Groups");
			
			Assert.assertTrue(ProgramUtil.openStepDetails(ProgramUtil.findStepInProgPlanner(proj.getCurrentStepIdent(), 0)), "FAIL: could not Open Step Details");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dataProvider="groupNames", dependsOnMethods="selectingTheGroupsNG")
	public void verifySelectedGroupsNG(String strGroup) throws Exception {
		try {			
			
			Assert.assertTrue(GeneralUtil.isObjectExistsInList(IStepsConst.stepProp_EvalSumVisibleGroups_M2M_Selected_Id, strGroup),"FAIL: could not Verify the Group");
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@DataProvider(name="groupNames")
	public Object[][] generateGroups() {
		
		Object[][] result = null;
		
		result = new Object[][] {		
				new Object[] {grpsArr[0]},
				new Object[] {grpsArr[1]},
				new Object[] {grpsArr[2]}};
		
		return result;		
	}

	/**
	 * 
	 * @throws Exception
	 */	
	private void deSelectingTheGroupsNG() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			
			Assert.assertTrue(fopp.openFundingOppPlanner(),"FAILE: could not open FOPP Planner!");
			
			proj.initializeStep(IGeneralConst.approvNonRexQuoAuto[0][0]);
			
			Assert.assertTrue(ProgramUtil.openStepDetails(ProgramUtil.findStepInProgPlanner(proj.getCurrentStepIdent(), 0)), "FAIL: could Open Step Details");
			
			Assert.assertTrue(FOPPUtil.deSelectEvaluationVisibilityGroups(grpsArr), "FAIL: could not select Groups");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
