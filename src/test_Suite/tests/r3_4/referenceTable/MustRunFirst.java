package test_Suite.tests.r3_4.referenceTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;



@GUITest
@Test(singleThreaded = true)
public class MustRunFirst 
{
	
	// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
		Class<?> ownClass = this.getClass();

		private Log log = LogFactory.getLog(ownClass);
		
		String refTableEForm = "Budget-Reference-Table";
		
		String refTableDataXML = "YearlyBudgets.xml";
		
		String zipFile = "RefTablesWorkFlow.zip";
		
		FundingOpportunity fopp;
		
		String preFix = "Reference-PA-";
		
		String postFix = "";

	
		@BeforeClass(groups = { "WorkflowNG" })
		public void setUp() throws Exception {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.loginPO();
			
			fopp = new FundingOpportunity("A-",preFix, postFix);
			
			
		}
		
		@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
		public void tearDown() throws Exception {
			
			fopp = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}
	
		@Test(groups = { "WorkflowNG" })
		public void configureAndImportDataInPO() throws Exception
		{
			
			log.debug("importing Associates Ref Table");
			
			Assert.assertTrue(GeneralUtil.addNewRefTable(refTableEForm, "G3"), "FAIL: To Add new Ref Table");
			
			log.debug("importing is OK!");
			
			log.debug("importing Data to Associate Reference Table!");
			
			Assert.assertTrue(GeneralUtil.importDataToRefTable(refTableEForm.replace("-", " "), refTableDataXML, false), "FAIL: to Import Data to Ref Table");
			
			log.debug("importing Data is OK!");

			log.debug("importing Other zip File...");
			
			Assert.assertTrue(GeneralUtil.newImportZipFile(zipFile));
			
			log.debug("importing zip File OK");
			
		}
		
		@Test(groups = { "WorkflowNG" }, dependsOnMethods = "configureAndImportDataInPO")
		public void registerToFundingOppurtunity() throws Exception
		{
			GeneralUtil.switchToFO();

			Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk));
			
			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), "Ouia 1"));
		
		}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
