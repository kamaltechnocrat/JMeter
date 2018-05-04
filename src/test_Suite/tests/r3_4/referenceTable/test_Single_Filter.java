package test_Suite.tests.r3_4.referenceTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ReferenceTablesUtil;

@GUITest
@Test(singleThreaded = true)
public class test_Single_Filter 
{
	
	// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
		Class<?> ownClass = this.getClass();

		private Log log = LogFactory.getLog(ownClass);
		
		@BeforeClass(groups = { "WorkflowNG" })
		public void setUp() throws Exception {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.loginPO();
			
		}
		
		@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
		public void tearDown() throws Exception {		
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}
		
		
		@Test(groups = { "WorkflowNG" })
		public void testSingleFilters_Capital_Equipment() throws Exception{
			
		
			ClicksUtil.clickLinks(IClicksConst.diagEFormsLnk);
			
			Assert.assertTrue(EFormsUtil.openEFormPreviewInList("Reference-Single-Filter",
					"Review"));
			
			Assert.assertTrue(ReferenceTablesUtil.insertDataToSingleFilterAndVerify());
		
		}
		
	
}