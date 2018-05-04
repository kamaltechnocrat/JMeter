/**
 * 
 */
package test_Suite.tests.r4_0.projDataStorage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FOPPUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CreateNewProjEFormNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formletMenu;
	Formlet formletQH;
	Formlet formletSS;
	EFormField eFormField;
	EFormField eFormFieldNum;
	EFormField eFormFieldDD;
	
	String projectFormId = null;

	String tempDefaultFormlet = null;
	
	static String[] eFildsIdent;

	String preFix = "";
	String postFix = "";
	
	FundingOpportunity fopp;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.fundingOpp_Prefix, "");

			form = new EForm(IEFormsConst.projectEForm_FormTypeName, preFix);

			form.setEFormSubType(IEFormsConst.projectEForm_FormTypeName);

			form.setEFormTitle("Project eForm");

			form.setEFormId("Project-e.Form");

			//Initialize Menu Only Formlet
			formletMenu = new Formlet(form,
					IFormletsConst.formletTypeName_MenuItemOnly,
					IEFormsConst.projectEForm_FormTypeName, preFix);

			//Initialize Question Holder Formlet
			formletQH = new Formlet(form,
					IFormletsConst.formletTypeName_eFormQuestionHolder,
					IEFormsConst.projectEForm_FormTypeName, preFix);

			formletQH.setFormletTitleText("General Formlet");

			formletQH.setFormletMenuText("Formlet");

			//Initialize eFormFields
			eFormField = new EFormField(form, formletQH,
					IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

			eFormField.setEFormFieldLabel("Enter Any Text:");

			eFormField.setEFormFieldDescription("");

			eFormField.setEFormFieldTooltip("");
			
			eFormFieldNum = new EFormField(form, formletQH,
					IeFormFieldsConst.eFormFieldType_NumericField_Name);

			eFormFieldNum.setEFormFieldLabel("Enter Any Number:");

			eFormFieldNum.setEFormFieldDescription("");

			eFormFieldNum.setEFormFieldTooltip("");
			
			eFormFieldDD = new EFormField(form, formletQH,
					IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

			eFormFieldDD.setEFormFieldLabel("Select a Decision:");

			eFormFieldDD.setEFormFieldDescription("");

			eFormFieldDD.setEFormFieldTooltip("");

			//Initialize Submission Summary Formlet
			formletSS = new Formlet(form,
					IFormletsConst.formletTypeName_SubmissionSummary,
					IEFormsConst.projectEForm_FormTypeName, preFix);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form = null;
		formletMenu = null;
		formletQH = null;
		formletSS = null;
		eFormField = null;
		eFormFieldNum = null;
		eFormFieldDD = null;
		fopp = null;
		
	  GeneralUtil.Logoff();
	  IEUtil.closeBrowser();
	
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void createNewForm() throws Exception {

		Assert.assertTrue(form.createEForm(), "FAIL: Could not create Project e.Form!");
		
		projectFormId = form.getEFormId();

		Assert.assertTrue(formletMenu.createMenuTypeFormlet(), "FAIL: Could add Menu Type Formlet");

		Assert.assertTrue(formletQH.createFormlet(true, true), "FAIL: Could not add Question Holder Formlet!");

		tempDefaultFormlet = formletQH.getFormletId();

		Assert.assertTrue(eFormField.addeFormField(true, false, false, false), "FAIL: Could not add Short Text eField to QH formlet!");
		
		Assert.assertTrue(eFormFieldNum.addeFormField(true, false, false, false), "FAIL: Could not add Numeric eField to QH formlet!");
		
		Assert.assertTrue(eFormFieldDD.addeFormField(true, false, false, false), "FAIL: Could not add Approval eField to QH formlet!");

		Assert.assertTrue(formletSS.createSubmissionSummaryTypeFormlet(), "FAIL: Could not add Submission Summary Type Formlet!");

		Assert.assertTrue(form.publishEFormAndCheckErrors(tempDefaultFormlet), "FAIL: Could not Publish Project e.Form!");		
	}


	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"createNewForm"})
	public void verifyNewProjFormAvailabilityNG() throws Exception {
		
		Assert.assertTrue(FOPPUtil.configureProjectEForm(fopp, form), "FAIL: Could not select Project e.Form!");		
	}	


	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"verifyNewProjFormAvailabilityNG"})
	public void verifyEFieldsAvailabilityNG() throws Exception {
		
		eFildsIdent = new String[] {eFormField.getEFormFieldId(),eFormFieldNum.getEFormFieldId(),eFormFieldDD.getEFormFieldId()};
		
		Assert.assertTrue(FOPPUtil.configureEvaluateProjectList(fopp, form, eFildsIdent), "FAIL: Could not select fields");		
	}	


	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"verifyEFieldsAvailabilityNG"})
	public void verifyCloningFoppAfterProjConfigNG() throws Exception {
		
		Assert.assertTrue(FOPPUtil.cloneFOPP(fopp), "FAIL: Could not Cloned FOPP!");
		
		String cloned = FOPPUtil.verifyClonedFOPP(fopp);
		
		Assert.assertTrue(cloned.startsWith(fopp.getFoppFullIdent().concat(" Clone shak")), 
				"FAIL: the Cloned Fopp Ident is not what expected!");
		
		fopp.setFoppFullIdent(cloned);
		
		Assert.assertTrue(FOPPUtil.openProjectConfigOnly(fopp), "FAIL: Could not open Cloned FOPP!");
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IFoppConst.fundingOpp_ProjConfig_ProjectEForm_DropDown_Id), form.getEFormFullId(), 
				"FAIL: The Selected Project e.Form in the Cloned Fopp is not Correct!");
		
		Assert.assertTrue(FOPPUtil.openProjectListConfigDetailsOnly(fopp), "FAIL: Could not Open Project Config List!");		
	}	


	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"verifyCloningFoppAfterProjConfigNG"}, dataProvider="eFilds-Ident")
	public void verifySelectedEFiledsInClonedFoppNG(String ef) throws Exception {
		
		Assert.assertTrue(GeneralUtil.findInM2MListById(IFoppConst.fundingOpp_ProjListConfig_EvalProjLst_M2MSelected_Id, ef));
		
	}
	
	
	
	@DataProvider(name = "eFilds-Ident")
	public static Object[][] generateRegLoginData() throws Exception{
		
		return new Object[][] {
				{eFildsIdent[0]},
				{eFildsIdent[1]},
				{eFildsIdent[2]}				
		};
		
	}

}
