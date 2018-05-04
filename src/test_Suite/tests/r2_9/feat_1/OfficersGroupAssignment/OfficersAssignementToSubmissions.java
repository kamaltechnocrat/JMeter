/**
 * 
 */
package test_Suite.tests.r2_9.feat_1.OfficersGroupAssignment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class OfficersAssignementToSubmissions {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	Project proj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginProjOfficer("1");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-PA-","");
			
			proj = new Project(fopp,"Assignment-", true,true,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() {
		
		proj = null;
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void createOrgAndProject() throws Exception {
		try {
			
			proj.newCreateNewOrg();
			proj.newCreateNewPOProject();
			proj.submitProject(true);
			
			Assert.assertTrue(ProjectUtil.openProjectOfficerTabInBasket(proj));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="createOrgAndProject")
	public void testOfficersGroupAutoAssignment() throws Exception {
		
		Assert.assertTrue(ProjectUtil.verifyOfficersAssignment(ProjectUtil.initializeOfficersStepAndGroupName()));
		
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="createOrgAndProject")
	public void testOverrideAutoAssignmentUAPs() throws Exception {
		
		Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.assignGroupBtn), "FAIL: Assign Group Button should be enabled!");
		
		Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.assignUserBtn), "FAIL: Assign User Button should be enabled!");
		
		Assert.assertTrue(GeneralUtil.isCheckboxExistsOrEnabledInTable(ITablesConst.projectOfficerAssignmentTableId,0,0), "FAIL: CheckBoxes should be Present and Enabled!");		
	}

}
