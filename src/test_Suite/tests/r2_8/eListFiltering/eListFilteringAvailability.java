/**
 * 
 */
package test_Suite.tests.r2_8.eListFiltering;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEListFltrConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EListFltrUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class eListFilteringAvailability {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			form = EListFltrUtil.initEForm();
			
			EListFltrUtil.initSomeField(form.getLstFormlets().get(1),IEListFltrConst.some_elfFieldTypes);
			
			EListFltrUtil.initFormlet(form, "Prop-");
			
			EListFltrUtil.initSomeField(form.getLstFormlets().get(3),IEListFltrConst.some_elfFieldTypes_Props);
			
			EListFltrUtil.initSomeFieldsProps(form.getLstFormlets().get(3), IEListFltrConst.elfFieldTypes_Props);
			
			EListFltrUtil.createEListForms(form);			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() {
		
		form = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" })
	public void testConfiguringEFormWithELFNG() throws Exception {
		try {
				
				List<EFormField> lst = form.getLstFormlets().get(1).getLstFields();
				
				for (int i =0; i<lst.size(); i++ ) {
					
					EFormField field = lst.get(i);
					
					Assert.assertTrue(field.addeFormField(true, false, true, true), "FAIL: could not add Field: " + field.getEFormFieldId());					
				}
				
				lst = form.getLstFormlets().get(3).getLstFields();
				
				for (int i =0; i<lst.size(); i++ ) {
					
					EFormField field = lst.get(i);
					
					Assert.assertTrue(field.addeFormField(true, true, true, true),"FAIL: could not add Field: " + field.getEFormFieldId());
					
					Assert.assertTrue(field.addDropdwonFieldProperties(IEListFltrConst.elfFieldTypes_Props[i]), "FAIL: could not set field Properties: " + IEListFltrConst.elfFieldTypes_Props[i] + "for: " + field.getEFormFieldId());					
				}
				
				ClicksUtil.clickButtons(IClicksConst.previewFormBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="testConfiguringEFormWithELFNG", dataProvider="fieldLabels")
	public void testElfAvailabilityInPreview(String fieldLabel) throws Exception {
		
		EListFltrUtil.openFormletAndShowFilter(form.getLstFormlets().get(0).getFormletMenuText());
		
		GeneralUtil.takeANap(1.0);
		
		Assert.assertTrue(EListFltrUtil.verifyFilterElementExists(fieldLabel),"FAIL: could not find Filter Element! " + fieldLabel);
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="testElfAvailabilityInPreview", dataProvider="fieldLabels")
	public void testPropElfAvailabilityInPreview(String fieldLabel) throws Exception {
		
		EListFltrUtil.openFormletAndShowFilter(form.getLstFormlets().get(2).getFormletMenuText());
		
		GeneralUtil.takeANap(1.0);
		
		Assert.assertTrue(EListFltrUtil.verifyFilterElementExists(fieldLabel),"FAIL: could not find Filter Element! " + fieldLabel);
	}
	
	@DataProvider(name="fieldLabels")
	public Object[][] generateFieldLabels(Method method) {	
		
		Object[][] result = null;
		
		if(method.getName().equals("testElfAvailabilityInPreview")) {
			
			result = new Object[IEListFltrConst.some_elfFieldTypes.length][];
			
			for (int i = 0; i < IEListFltrConst.some_elfFieldTypes.length; i++) {
				
				result[i] = new Object[] {IEListFltrConst.some_elfFieldTypes[i].replace('-', ' ')};
				
			}
		} else if(method.getName().equals("testPropElfAvailabilityInPreview")) {
			
			result = new Object[IEListFltrConst.some_elfFieldTypes_Props.length][];
			
			for (int i = 0; i < IEListFltrConst.some_elfFieldTypes_Props.length; i++) {
				
				result[i] = new Object[] {IEListFltrConst.some_elfFieldTypes_Props[i].replace('-', ' ')};				
			}
		}
		return result;		
	}
}
