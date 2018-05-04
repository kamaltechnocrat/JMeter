/**
 * 
 */
package test_Suite.tests.r5_2.DataArchive.PhaseI_ReadyForArchive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.reporting_Functions.RptFuncUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestingDbConnection {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	RptFuncs reportFunc;
	
	IRptFuncConst.EReportingFunctions funcName;
	
	boolean isSQLDB = false;
	
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToFO();
			
			GeneralUtil.loginAnyFO("front");
		
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", "-Gnrl-PA-","");

			foProj = new FOProject(fopp, "", true, 1, EFormsUtil.createAnyRandomString(5));
			
			reportFunc = new RptFuncs(isSQLDB);
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;
		
//     	GeneralUtil.Logoff();
//    	IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	
	@Test(groups = { "WorkflowNG" })
	public void createNewProjectAndSubmit() throws Exception {
		
		Assert.assertTrue(foProj.createFOProjectNewNew(), "Fail: Couldn't create FO Project");
		
		foProj.getProjFOFullName();
		
        foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()), "Fail: Could't open Submission-A");
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData), "Fail: Couldn't add text to text field");
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createNewProjectAndSubmit")
	public void connect_to_Database() throws Exception {

		try {
			
			 RptFuncUtil.run_DataArchive_Queries(reportFunc,foProj);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

	}
	

}
