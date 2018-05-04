package test_Suite.tests.r3_4.referenceTable;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ReferenceTablesUtil;

@GUITest
@Test(singleThreaded = true)
public class test_SingleFilter_eList 
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
		public void testSingleFilter_eLists() throws Exception{
			
			ClicksUtil.clickLinks(IClicksConst.diagEFormsLnk);
			
			Assert.assertTrue(EFormsUtil.openEFormPreviewInList("Reference-Single-Filter-eList",
					"Review"));
            int valuesCount = TablesUtil.howManyEntriesInTable(IRefTablesConst.budget_Limit_Table_Id);
			
			if(valuesCount <= 0)
			{
			
			Assert.assertTrue(ReferenceTablesUtil.insertDataToSingleFilterEList(), "Fail: Couldnot insert data to Single Filter EList");
			
			}
			
		}
		
		
		@Test(groups = { "WorkflowNG" }, dependsOnMethods = "testSingleFilter_eLists", dataProvider="BudgetData")
		public void verifySingleFilterEList( String[] strsToFind) throws Exception
		{
			
			Assert.assertTrue(ReferenceTablesUtil.verify_singleFilterEList_data(strsToFind), "Fail: Couldnot verify single filter eList data");
			
		}

		
		
		@DataProvider(name = "BudgetData")
		public Object[][] generateFormletTypes(Method method)
		{
			Object[][] result = null;

			if ( method.getName() == "verifySingleFilterEList")
				
			{
				result = new Object[][] {
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[0]},
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[1]},
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[4]},
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[3]},
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[6]},
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[5]},
						
						{IRefTablesConst.verify_yearlyLimit_Fields_eList[8]},
						
						 };
			}

			return result;
		}
	
		
}