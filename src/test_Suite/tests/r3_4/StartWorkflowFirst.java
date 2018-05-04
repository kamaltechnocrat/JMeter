/**
 * 
 */
package test_Suite.tests.r3_4;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.LookupUtil;
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
public class StartWorkflowFirst {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Gnrl-PA-","");
			
			foProj = new FOProject(fopp,"Pre-Award-", true,1,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void registerToFopp() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			
			GeneralUtil.switchToPO();		
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}	
	
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods="registerToFopp")
	public void addSchedulesToAdminForm() throws Exception {
		try {
			
			Assert.assertTrue(fopp.openFundingOppPlanner(), "FAIL: Could not find FOPP or Open it's Planner!");
			
			Assert.assertTrue(fopp.openAdminEForm(),"FAIL: Could not oepn Admin Form!");
			
			Assert.assertTrue(ClicksUtil.clickImage(IClicksConst.newImg), "FAIL: could not click New Icon!");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle("Submission Name", "Payment"), "FAIL: Could not find the text field");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
			
			Assert.assertTrue(GeneralUtil.setTextByTitle("Submission Name", "Report"), "FAIL: Could not find the text field");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
			
			Assert.assertTrue(ClicksUtil.returnFromAnyForm(), "FAIL: could not return to FOPP Planner!");
			
			Assert.assertTrue(fopp.openPublicationEForm(), "FAIL: Could Not open Publication Form!");
			
			Assert.assertTrue(GeneralUtil.setTextById(IEFormsConst.form_Publication_TextId, "Testing Amend In Place and Amendment Categories Features"), "FAIL: Could not add text to the Publication form");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: could not complete and publish Publication form");
			
			Assert.assertFalse(ClicksUtil.returnFromAnyForm(), "FAIL: could not return to FOPP Planner!");
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods="registerToFopp")
	public void activateAmendmentCategory() throws Exception {	
		
		ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
		
		LookupUtil.taggleLookupActiveness(IAmendmentsConst.amendCategory_LookupName, ITablesConst.lookupListTableId);			
	}
}
