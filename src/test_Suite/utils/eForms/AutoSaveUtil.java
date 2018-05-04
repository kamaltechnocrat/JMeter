/**
 * 
 */
package test_Suite.utils.eForms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.eForms.IAutoSaveConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IAutoSaveConst.EFieldType;
import test_Suite.constants.eForms.IAutoSaveConst.EFormletsPostfix;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;

/**
 * @author mshakshouki
 *
 */
public class AutoSaveUtil {

	private static Log log = LogFactory.getLog(AutoSaveUtil.class);
	
	public static IAutoSaveConst.EFormletsPostfix formletPostFix;

	/****************** End of Variables ***********************/
	
	public static Formlet initializeSubFormlet(EFormletsPostfix formletPostfix,boolean createForm) throws Exception {
		EForm form = new EForm(IEFormsConst.submission_FormTypeName, "AutoSave-");
		
		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);
		
		form.setEFormTitle("AutoSave Applicant Submission");

		Formlet formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.applicantsubmission_FormTypeName, "AutoSave-");

		Formlet subFormlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder);

		String formletIdent = formlet.getFormletId().replace(" ", "-");
		
		String formletTitle = formlet.getFormletId().replace("-"," ");
		

		formlet.setFormletTitleText(formletTitle + IAutoSaveConst.formleTitlePostFix[formletPostfix.ordinal()]);

		formlet.setFormletMenuText(formletTitle + IAutoSaveConst.formleTitlePostFix[formletPostfix.ordinal()]);

		formlet.setFormletId(formletIdent + IAutoSaveConst.formleIdentPostFix[formletPostfix.ordinal()]);

		subFormlet.setFormletTitleText(formletTitle
				+ IAutoSaveConst.subFormleTitlePostFix[formletPostfix.ordinal()]);

		subFormlet.setFormletId(formletIdent
				+ IAutoSaveConst.subFormleTitlePostFix[formletPostfix.ordinal()]);

		subFormlet.setParentFormletId(formlet.getFormletId());
		
		if(createForm) {
			Assert.assertTrue(form.createEForm(),"FAIL: Could not Create eForm");
			
		}		

		Assert.assertTrue(formlet.createFormlet(true, true));

		Assert.assertTrue(subFormlet.createSubFormlet());
		
		return subFormlet;
	}
	
	public static Formlet initializeEForm(EFormletsPostfix formletPostfix,boolean createForm) throws Exception {
		
		log.info("Initializing eForm!");
		EForm form = new EForm(IEFormsConst.submission_FormTypeName, "AutoSave-");
		
		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);
		
		form.setEFormTitle("AutoSave Applicant Submission");
		
		Formlet formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.applicantsubmission_FormTypeName, "AutoSave-");

		String formletIdent = formlet.getFormletId().replace(" ", "-");
		
		String formletTitle = formlet.getFormletId().replace("-"," ");
		

		formlet.setFormletTitleText(formletTitle + IAutoSaveConst.formleTitlePostFix[formletPostfix.ordinal()]);

		formlet.setFormletMenuText(formletTitle + IAutoSaveConst.formleTitlePostFix[formletPostfix.ordinal()]);

		formlet.setFormletId(formletIdent + IAutoSaveConst.formleIdentPostFix[formletPostfix.ordinal()]);
		
		if(createForm) {
			Assert.assertTrue(form.createEForm(),"FAIL: Could not Create eForm");
			
		}
		
		

		formlet.createFormlet(true, true);
		
		return formlet;
	}
	
	public static EFormField initializeEFormField(Formlet formlet,EFieldType fieldType) throws Exception {

		
		EFormField eFormField = new EFormField(formlet, IAutoSaveConst.fieldsType[fieldType.ordinal()]);

		eFormField.setEFormFieldLabel(IAutoSaveConst.fieldsType[fieldType.ordinal()]);

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");
		
		return eFormField;
	}

}
