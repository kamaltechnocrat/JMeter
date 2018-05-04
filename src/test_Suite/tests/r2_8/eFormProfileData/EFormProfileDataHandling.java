/**
 * 
 */
package test_Suite.tests.r2_8.eFormProfileData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IEFormsConst.EDisplayProjInfo;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class EFormProfileDataHandling {

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
	
	@Test(groups = {"EFormsNG" })
	public void initialize() throws Exception {
		
		form = EFormsUtil.initializeBasicEForm(IEFormsConst.submission_FormTypeName,IEFormsConst.applicantsubmission_FormTypeName,"ProfileData-");
		
		form.setDisplayProjInfo(IEFormsConst.displayProjInfoValues[EDisplayProjInfo.ANYFORMLET.ordinal()]);
		
		EFormsUtil.createBasicEForm(form);
		
		Assert.assertTrue(EFormsUtil.openObjectDetail(form.getEFormFullId()), "FAIL: Could not open e.Form Details!");		
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="initialize")
	public void testDisplayProjInfoSetting() throws Exception {
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id), IEFormsConst.displayProjInfoValues[EDisplayProjInfo.ANYFORMLET.ordinal()]);
		
	}
	
	@Test(groups = { "EFormsNG" },dependsOnMethods="testDisplayProjInfoSetting")
	public void testDisplayProjInfoSettingAfterCloning() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
		
		Assert.assertTrue(EFormsUtil.cloneEForm(form.getEFormFullId(), form.getEFormSubType()), "FAIL, Could not Clone e.Form " + form.getEFormFullId());
		
		Assert.assertTrue(EFormsUtil.openClonedEForm(form.getEFormFullId(), form.getEFormSubType(), 1), "FAIL: could not open Cloned e.Form");
		
		Assert.assertTrue(EFormsUtil.openObjectDetail(form.getEFormFullId()), "FAIL: Could not open e.Form Details!");
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id), IEFormsConst.displayProjInfoValues[EDisplayProjInfo.ANYFORMLET.ordinal()]);	
		
	}
}
