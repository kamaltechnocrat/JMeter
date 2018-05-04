/**
 * 
 */
package test_Suite.utils.eForms;

import java.util.*;

import org.apache.commons.logging.*;
import org.testng.Assert;

import static watij.finders.SymbolFactory.*;
import test_Suite.constants.eForms.*;
import test_Suite.constants.ui.*;
import test_Suite.lib.eForms.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;
import watij.elements.*;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class EListFltrUtil {
	
	private static Log log = LogFactory.getLog(EListFltrUtil.class);
	public static List<String> getAllELFFieldTypes() throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		List<String> elfTypes = ie.selectList(id,IeFormFieldsConst.eFormFieldType_SelectList_Id).getAllContents();
		
		for (String string : IEListFltrConst.non_elfFieldTypes) {
			
			elfTypes.remove(string);			
		}	
		
		return elfTypes;		
	}
	
	public static boolean verifyElfCheckboxExists(String fieldType) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id,IeFormFieldsConst.eFormFieldType_SelectList_Id).select(fieldType);
		
		if(ie.checkbox(id,IeFormFieldsConst.eFormFieldInListColumn_Checkbox_Id).checked()) {
			
			ie.checkbox(id,IeFormFieldsConst.eFormFieldInListColumn_Checkbox_Id).clear();
		}
		
		ie.checkbox(id,IeFormFieldsConst.eFormFieldInListColumn_Checkbox_Id).set();
		
		GeneralUtil.takeANap(1.0);
		
		return ie.checkbox(id,IeFormFieldsConst.eFormFieldIncludeAsELF_Checkbox_Id).exists();
	}
	
	public static EForm initEForm() throws Exception {
		
		EForm form = new EForm(IEFormsConst.submission_FormTypeName,IEFormsConst.applicantsubmission_FormTypeName,"ELF-");
		
		form.setEFormTitle(form.getEFormFullId().replace('-', ' '));
		
		Formlet formlet = new Formlet(form,IFormletsConst.formletTypeName_eFormList);
		formlet.setFormletTitleText(formlet.getFormletId().replace('-', ' '));
		formlet.setFormletMenuText(formlet.getFormletTitleText());
		
		Formlet Subformlet = new Formlet(form,IFormletsConst.formletTypeName_eFormQuestionHolder);
		Subformlet.setFormletTitleText(Subformlet.getFormletId().replace('-', ' '));
		
		Subformlet.setParentFormletId(formlet.getFormletId());
		
		EFormField field = new EFormField(Subformlet,IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);
		
		List<EFormField> lstField = new ArrayList<EFormField>();
		lstField.add(0, field);
		
		Subformlet.setLstFields(lstField);
		
		List<Formlet> lstFrmlt = new ArrayList<Formlet>();
		
		lstFrmlt.add(formlet);
		lstFrmlt.add(Subformlet);
		
		form.setLstFormlets(lstFrmlt);
		
		return form;
	}
	
	public static void initFormlet(EForm form, String prefix) throws Exception {
		
		Formlet formlet = new Formlet(form,IFormletsConst.formletTypeName_eFormList, form.getEFormType(),prefix);
		formlet.setFormletTitleText(formlet.getFormletId().replace('-', ' '));
		formlet.setFormletMenuText(formlet.getFormletTitleText());
		
		Formlet subformlet = new Formlet(form,IFormletsConst.formletTypeName_eFormQuestionHolder, form.getEFormType(),prefix);
		subformlet.setFormletTitleText(subformlet.getFormletId().replace('-', ' '));
		
		subformlet.setParentFormletId(formlet.getFormletId());
		
		form.getLstFormlets().add(formlet);
		form.getLstFormlets().add(subformlet);
		
	}
	
	public static void initSomeField(Formlet formlet, String[] someFieldsArr) throws Exception {
		
		List<EFormField> lstFld = new ArrayList<EFormField>();
		
		for (int x=0; x < someFieldsArr.length; x++) {
			
			EFormField field = new EFormField(formlet,someFieldsArr[x]);
			
			String fullName = someFieldsArr[x] + IEListFltrConst.lbf_FoppPreFix;

			field.setEFormFieldId(fullName);

			field.setEFormFieldLabel(fullName.replace("-", " "));

			field.setEFormFieldDescription("");

			field.setEFormFieldTooltip("");

			field.setEFormFieldColumnTitle(fullName.replace("-", " "));

			field.setEFormFieldEmptyColumnText("---");
			
			//field.initListDetailsField(IEListFltrConst.some_elfFieldTypes[x], IEListFltrConst.lbf_FoppPreFix);
			
			log.debug(field.getEFormFieldId());
			
			lstFld.add(x,field);
		}
		
		formlet.setLstFields(lstFld);
	}
	
	public static void initSomeFieldsProps(Formlet formlet, String[][] someFieldsPropArr)throws Exception {
		
		for (int x=0; x < formlet.getLstFields().size(); x++) {
			List<Object> lstObj = new ArrayList<Object>();
			EFormField field = formlet.getLstFields().get(x);
			
			for (int i = 0; i < someFieldsPropArr[x].length; i++) {
				lstObj.add(i, someFieldsPropArr[x][i]);
			}
			
			field.setLstProp(lstObj);			
		}		
	}
	
	public static void createEListForms(EForm form) throws Exception {
		
		Assert.assertTrue(form.createEForm(), "FAIL: could not create eForm");
		
		for (int i = 0; i < form.getLstFormlets().size(); i+=2) {
			
			Assert.assertTrue(form.getLstFormlets().get(i).createFormlet(true, true), "FAIL: could not create Formlet");
			
			Assert.assertTrue(form.getLstFormlets().get(i+1).createSubFormlet(), "FAIL: could not create SubFormlet");
			
		}
	}
	
	public static boolean verifyFilterElementExists(String fieldLabel) throws Exception {

		Div tDiv = TablesUtil.tableDiv();
		
		TableBodies tBodies = tDiv.body(id, IFiltersConst.eListFiltersElement_Id).bodies();
		
		for (TableBody tBody : tBodies) {
			
			if (tBody.innerText().contains(fieldLabel)) {
				return true;
			}			
		}
		return false;
	}
	
	public static void openFormletAndShowFilter(String formletMenu) throws Exception {	
		
		ClicksUtil.clickLinks(formletMenu);
		
		if(!GeneralUtil.isLinkExistsByTxt(IClicksConst.hideFiltersLnk)) {
			ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
			
		} else if(GeneralUtil.isLinkExistsByTxt(IClicksConst.clearFiltersLnk)) {
			ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		}		
	}
}
