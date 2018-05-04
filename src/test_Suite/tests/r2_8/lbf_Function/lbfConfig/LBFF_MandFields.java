/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfConfig;

import java.util.ArrayList;

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
import test_Suite.constants.eForms.IFormletsFunctionsConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.FormletFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBFF_MandFields {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	EForm subForm;
	EForm adminForm;
	Formlet formlet;
	Formlet subFormlet;
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;
	FormletFunctions formletFunc;

	String strFormletFuncProp[];

	String tempEFormFieldId = null;
	String tempFormletId = null;
	String tempEFormId = null;

	String tempDefaultFormlet = null;

	String formleTitlePostFix = " eList BF";
	String formleIdentPostFix = "-eList-BF";

	String subFormleTitlePostFix = " Sub eList BF";
	String subFormleIdentPostFix = "-Sub-eList-BF";

	ArrayList<String> formletIdents;
	
	ArrayList<String> formletFuncData;

	String funcType = IFormletsFunctionsConst.formlet_ListBringForward_FunctionType;
	String invokeWhen = IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Values_Array[1];
	String invokeWhenFirst = IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Values_Array[0];
	String sync = "yes";
	String syncNo = "no";
	String filter = "true";
	String sysVar = IFormletsFunctionsConst.formletCalculated_FuncProp_SystemVariablesRef_AgreementNumber;
	String dataObject = IFormletsFunctionsConst.formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotal_Group1;

	String preFix = "LBF-";
	String postFix = "";

	int formletIndex = -1;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		subForm = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		subForm.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		subForm.setEFormTitle("Applicant Submission LBF");

		Assert
				.assertTrue(subForm.createEForm(),
						"FAIL: Could not Create eForm");

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails(
				"Submission Schedule List",
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

		formletFunc = new FormletFunctions(subForm, formlet.getFormletType(),
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		EFormsUtil.expandFormPlannerNode(formlet.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		Assert.assertTrue(
				EFormsUtil.openNewOpjectDetailInFormlet(formlet,
						"Formlet Functions",
						EFormsUtil.EAddObjectsFormPlanner.addFormletFunction
								.ordinal()), "Something wrong");

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		EFormsUtil.returnToPlanner();

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = { "EFormsNG" }, dataProvider="LBF-FieldsData")
	public void testMandatoryFieldsValidationNG(String _FuncType,
			String _InvokeWhen, String _FormletId, String _Sync,
			String _Filter, String _SysVar, String DataObject, boolean _Expected)
			throws Exception {
		
		formletFuncData = new ArrayList<String>();
		
		formletFuncData.add(0, _FuncType);
		formletFuncData.add(1, _InvokeWhen);
		formletFuncData.add(2, _FormletId);
		formletFuncData.add(3, _Sync);
		formletFuncData.add(4, _Filter);
		formletFuncData.add(5, _SysVar);
		formletFuncData.add(6, DataObject);
		
		Assert.assertEquals(formletFunc.fillAndTestAnyFunctionDetails(formletFuncData,formlet), _Expected);

	}

	@DataProvider(name = "LBF-FieldsData")
	public Object[][] generateLBFFieldsData() throws Exception {

		return new Object[][] {

				new Object[] { funcType, invokeWhenFirst, formlet.getFormletId(), sync,
						filter, sysVar, dataObject, false },
				new Object[] { "", invokeWhen, formlet.getFormletId(), syncNo, filter, sysVar,
						dataObject, true },
				new Object[] { "", invokeWhen, "", sync, filter, sysVar,
						dataObject, false },
				new Object[] { "", invokeWhen, formlet.getFormletId(),
						"", filter, sysVar, dataObject, true },
				new Object[] { "", invokeWhen, formlet.getFormletId(),
						sync, "", "", "", true },
				new Object[] { "", invokeWhen, formlet.getFormletId(),
						sync, filter, "", dataObject, true },
				new Object[] {"", invokeWhen, formlet.getFormletId(),
						sync, filter, sysVar, "", true },
				new Object[] { "", invokeWhen, formlet.getFormletId(),
						sync, filter, sysVar, dataObject, true }

		};

	}


}
