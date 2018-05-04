/**
 * 
 */
package test_Suite.tests.r2_8.eListFiltering;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.lib.eForms.EForm;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.EListFltrUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class EFieldInListColumnConfig {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			EForm form = EListFltrUtil.initEForm();
			
			EListFltrUtil.createEListForms(form);			
			
			EFormsUtil.openNewOpjectDetail(form.getLstFormlets().get(1).getFormletId(), EFormsUtil.EAddObjectsFormPlanner.addEFormField.ordinal());			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" }, dataProvider="fieldTypes")
	public void testELFCheckBoxAvailabilityNG(String fieldType) throws Exception {
		try {
			
			Assert.assertTrue(EListFltrUtil.verifyElfCheckboxExists(fieldType), "FAIL: the EformField Should be Filtered by in the List: " + fieldType);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@DataProvider(name="fieldTypes")
	public Object[][] generateFieldTypes() throws Exception {
		
		List<String> lst = EListFltrUtil.getAllELFFieldTypes();
		
		Object[][] result = new Object[lst.size()][];
		
		for(int i =0; i<lst.size();i++) {
			result[i] = new Object[] {lst.get(i)};
		}			
		
		return result;
	}
}
