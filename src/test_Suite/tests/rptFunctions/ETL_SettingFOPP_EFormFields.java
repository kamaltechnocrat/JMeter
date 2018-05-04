/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.reporting_Functions.IEtlConst;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFormlets;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlRptFldTypes;
import test_Suite.lib.reporting_Functions.ETL;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.reporting_Functions.EtlUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ETL_SettingFOPP_EFormFields {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	ETL etl;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EtlNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			etl = EtlUtil.initializeNonProjectForm(IEtlConst.EetlEFormTypes.fopp, EetlFormlets.propFields,EetlRptFldTypes.FOPP);
			
			etl.setFoppIdent("A-Admin-eForm-Prog-Reporting-Function");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EtlNG" }, alwaysRun=true)
	public void tearDown() {
		
		etl = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EtlNG" })
	public void testAddingReportingFields() throws Exception {
		try {
			
			Assert.assertTrue(EtlUtil.addFOPPEFormFields(etl), "FAIL: Could not add fields");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EtlNG" }, dependsOnMethods="testAddingReportingFields")	
	public void verifyReportingFieldsExistsInList() throws Exception {
		
		Assert.assertTrue(EtlUtil.verifyReportingFieldInList(etl),"FAIL: Could not verify and Open the field");
	}

}
