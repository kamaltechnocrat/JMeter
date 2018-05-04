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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.reporting_Functions.IEtlConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFoppFormTypes;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFormlets;
import test_Suite.constants.reporting_Functions.IRptFuncConst.EeFormsIdentifier;
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
public class ETL_SettingProj_EFormsFields {

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
			
			etl = EtlUtil.initializeProjectForm(IEtlConst.EetlEFormTypes.sub,EeFormsIdentifier.submission, EetlFormlets.ddFileds);
			
			etl.setFoppIdent("A-Project-eForm-Prog-Reporting-Function");

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
	@Test(groups = { "EtlNG" },dataProvider="formsTypes")
	public void testAddingFieldsAndVerify(EetlFoppFormTypes foppForm,EeFormsIdentifier formIdent, EetlFormlets formlet) throws Exception {
		try {
			
			etl.setEFoppTypes(foppForm);
			
			etl.setEFormIdent(IRptFuncConst.reportFunc_eFormsIdent[formIdent.ordinal()]);
			
			etl.setMapHash(EtlUtil.getHashedFormletAndFields(etl.getHashList(),formlet));
			
			etl.setFormletIdent(IEtlConst.etlNonProjetFormlets[formlet.ordinal()]);
			
			Assert.assertTrue(EtlUtil.addProjectFormletFields(etl), "FAIL: Could not add fields");
			
			Assert.assertTrue(EtlUtil.verifyReportingFieldInList(etl),"FAIL: Could not verify and Open the field");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	

	@DataProvider(name = "formsTypes")
	public Object[][] generateFormsTypes() {

		Object[][] result = null;

		result = new Object[][] {
				new Object[] {EetlFoppFormTypes.sub ,EeFormsIdentifier.submission,  EetlFormlets.ddFileds},
				new Object[] {EetlFoppFormTypes.review ,EeFormsIdentifier.review,  EetlFormlets.minMax },
				new Object[] {EetlFoppFormTypes.approval ,EeFormsIdentifier.approval,  EetlFormlets.noProp }
				//new Object[] {EetlFoppFormTypes.ipass ,EeFormsIdentifier.claim,  EetlFormlets.eListDD }
				
			};
		return result;
	}
}
