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

import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author k.sharma
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Test_ImportedEformField {

	
		// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
		Class<? extends Test_ImportedEformField> ownClass = this.getClass();

		private Log log = LogFactory.getLog(ownClass);
       
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
			
			try {
				
				
				GeneralUtil.Logoff();
				IEUtil.closeBrowser();
				
				log.warn("Ending: " + this.getClass().getSimpleName());
				
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
			
			
		}

		
		@Test(groups = { "MultiCurrencyNG" }, enabled = true)
		public void testImportedEform() throws Exception {
			try {
				
				//****** below three lines are commented for the time being*********
				log.debug("importing Org Xml File...");
				GeneralUtil.newImportForms(new String[]{"MultipleCurrency.xml"});
				log.debug("importing Org Xml OK");
				
				Assert.assertTrue(EFormsUtil.searchEforms(IeFormFieldsConst.EformName[0], IeFormFieldsConst.eformType),"FAIL: Searched eform doesn't exist");
				
				Assert.assertTrue(EFormsUtil.expandChild(IFormletsConst.formletfieldexpandnode_value),"FAIL: formlet doesn't exist");
				
				Assert.assertTrue(EFormsUtil.openFormletField(IFormletsConst.openformletfield_value),"FAIL:Formlet field doesn't exist");
				
				Assert.assertTrue(EFormsUtil.searchDefaultSymbol(),
						"FAIL: Default Dollar symbol is not found");
				
				ClicksUtil.clickButtons(IClicksConst.backBtn);
				
				

			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
		
		
		
			
		}

	

