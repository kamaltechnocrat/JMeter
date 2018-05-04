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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IEFormsConst.EDisplayProjInfo;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class EFormProfileDataConfig {

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
			
			form = new EForm(IEFormsConst.submission_FormTypeName,IEFormsConst.applicantsubmission_FormTypeName,"ProfileData-");
			
			form.setEFormTitle(form.getEFormFullId().replace('-', ' '));			
			
			ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			
			FiltersUtil.filterListByLabel(IFiltersConst.gpa_FormIdent_Lbl, form.getEFormFullId(),IFiltersConst.exact);

			ClicksUtil.clickImage(IClicksConst.newImg);
			

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
	
	@Test(groups = { "EFormsNG" })
	public void testDefaultValue() throws Exception {
		
		form.editEFormDetails();
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id), form.getDisplayProjInfo());
		
	}
	
	@Test(groups = { "EFormsNG" }, dataProvider="TombstoneCondition", dependsOnMethods="testDefaultValue")
	public void testProjInfoConditionsAvailability(String ProjInfCond) throws Exception {
		
		form.setDisplayProjInfo(ProjInfCond);
		
		Assert.assertTrue(GeneralUtil.selectInDropdownList(IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id, form.getDisplayProjInfo()));
		
	}
	
	@Test(groups = { "EFormsNG" }, dataProvider="TombstoneCondition", dependsOnMethods="testDefaultValue")
	public void testProjInfoConditionsChangable(String ProjInfCond) throws Exception {
		
		form.setDisplayProjInfo(ProjInfCond);
		
		Assert.assertTrue(GeneralUtil.selectInDropdownList(IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id, form.getDisplayProjInfo()));		
	}
	
	@DataProvider(name="TombstoneCondition")
	public Object[][] generateTombstoneCondition() throws Exception {
		
		return new Object[][] {
				new Object[] {IEFormsConst.displayProjInfoValues[EDisplayProjInfo.DEFFORMLET.ordinal()]},
				new Object[] {IEFormsConst.displayProjInfoValues[EDisplayProjInfo.NEVER.ordinal()]},
				new Object[] {IEFormsConst.displayProjInfoValues[EDisplayProjInfo.ANYFORMLET.ordinal()]}
		};
	}
}
