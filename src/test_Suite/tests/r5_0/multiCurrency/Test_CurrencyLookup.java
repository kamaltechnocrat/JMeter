/**
 * 
 */
package test_Suite.tests.r5_0.multiCurrency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.LookupUtil;
import test_Suite.utils.ui.IEUtil;
//import test_Suite.constants.cases.ILookupsConst;

/**
 * @author k.sharma
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Test_CurrencyLookup {
	Class<?> ownClass = this.getClass();

	private final Log log = LogFactory.getLog(ownClass);
	
	// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
	
		/*------ End of Global Vars --------------*/

	    @BeforeClass(groups = { "MultiCurrencyNG" })
		public void setUp() throws Exception {
			
			try {
				
				log.warn("Starting: " + this.getClass().getSimpleName());

				IEUtil.openNewBrowser();
				GeneralUtil.navigateToPO();
				GeneralUtil.logInSuper();
				// -----------------------------------

			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}

		@AfterClass(groups = { "MultiCurrencyNG" }, alwaysRun=true)
		public void tearDown() throws Exception {			
		
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}

		/**
		 * 
		 * @throws Exception
		 */
		@Test(groups = { "MultiCurrencyNG" }, enabled = true)
		public void testMultiCurrencyLookup() throws Exception {
			try {
				
				Assert.assertTrue(LookupUtil.openLookupValueList(ILookupsConst.lookupName),"FAIL: Could not found lookup");	
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			};
		}
		
		@Test(groups = { "MultiCurrencyNG" }, dependsOnMethods = "testMultiCurrencyLookup", enabled=true)
		public void addMultiCurrencyLookupsvalues() throws Exception {
			try {
					
	//				//ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
	//								
	//				Assert.assertTrue(LookupUtil.taggleLookupActiveness("currencies",ITablesConst.lookupListTableId), "FAIL: could not found Lookup!");
	//				//LookupUtil.findlookupInList("currencies", ITablesConst.lookupListTableId);
	//				
				Assert.assertTrue(LookupUtil.addLookupValues(ILookupsConst.currencyLookupValues,ILookupsConst.lookupName), "FAIL: No Lookups exist");
				
			} 
			
			
			catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			};
		}
		
		@Test(groups = { "MultiCurrencyNG" }, dependsOnMethods = "testMultiCurrencyLookup",enabled= false)
		public void taggleMultiCurrencyLookupsvalues() throws Exception {
			try {
						    
				Assert.assertTrue(LookupUtil.taggleLookupActiveness(ILookupsConst.currencySymbol[0], ITablesConst.lookupValueTableId), "FAIL: No Lookup value");
				
				} 
			
				catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			};
		
		}
		
		@Test(groups = { "MultiCurrencyNG" },dependsOnMethods = "testMultiCurrencyLookup")
		public void searchLookupValues() throws Exception {
			try {
	
				Assert.assertTrue(LookupUtil.searchAllMultiCurrencyLookupValues(ITablesConst.lookupValueTableId), "FAIL: No Lookups values available");
				
			} 
			
			
			catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			};
			
		}
		
		
	

}
