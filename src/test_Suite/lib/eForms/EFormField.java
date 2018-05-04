/**
 * 
 */
package test_Suite.lib.eForms;

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.title;
import static watij.finders.SymbolFactory.value;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.cases.ILookupsConst.CurrencySymbol;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */

public class EFormField extends Formlet {
	
	private static Log log = LogFactory.getLog(EFormField.class);
	
	public String eFormFieldLabel = "";
	
	public String eFormFieldDescription = "";
	
	public String eFormFieldTooltip = "";
	
	protected String eFormFieldColumnTitle = "";
	
	protected String eFormFieldEmptyColumnText = "";

	protected String eFormFieldType = null;
	
	protected String eFormFieldId = null;
	
	protected String questionLabel = null;
	
	protected String description = null;
	
	protected String popupHelpText = null;
	
	protected String eFormFieldPrefix = null;
	
	protected String scrumble = null;
	
	protected String addeFormFieldlinkStartId = "/planner:0_";
	
	//protected String addeFormFieldLinkEndId = "";
	
	protected String addEFormFieldLinkId = null;
	
	protected Integer eFormFieldIndex = 0;
	
	protected Integer formletCounter;
	
	private Enumeration<Integer> keysEnum;
	
	private Enumeration<String> elementsEnum;
	
	private boolean retValue;
	
	protected String tempFormlet;
	
	protected List<Object> lstProp = null;

	/**
	 * Constractore
	 */
		
	public EFormField() {
	}
	
	public EFormField(Formlet formlet, String eFormFieldType) {
	
		this.eFormFieldType = eFormFieldType;
		
		this.eFormFieldPrefix = formlet.getPreFix();
		
		this.tempFormlet = formlet.getFormletId();
		
		this.formletCounter = formlet.getFormletCounter();
		
		this.addEFormFieldLinkId = this.addeFormFieldlinkStartId + this.formletCounter.toString();
		
		formlet.eFormFieldIndex += 1;
		
		this.eFormFieldIndex = formlet.eFormFieldIndex;
		
		this.setEFormFieldId(this.eFormFieldPrefix + this.eFormFieldType + "-" + RandomStringUtils.randomAlphabetic(5));
	}
	
	public EFormField(EForm eForm, Formlet formlet, String eFormFieldType) {
	
		this.eFormFieldType = eFormFieldType;
		
		this.eFormFieldPrefix = eForm.preFix;
		
		this.tempFormlet = formlet.getFormletId();
		
		this.formletCounter = formlet.getFormletCounter();
		
		this.addEFormFieldLinkId = this.addeFormFieldlinkStartId + this.formletCounter.toString();
		
		formlet.eFormFieldIndex += 1;
		
		this.eFormFieldIndex = formlet.eFormFieldIndex;
		
		this.setEFormFieldId(this.eFormFieldPrefix + this.eFormFieldType + "-" + RandomStringUtils.randomAlphabetic(5));
	}
	
	public EFormField(EForm eForm, Formlet formlet, String eFormFieldType, String postFix) {
	
		this.eFormFieldType = eFormFieldType;
		
		this.eFormFieldPrefix = eForm.preFix;
		
		this.tempFormlet = formlet.getFormletId();
		
		this.formletCounter = formlet.getFormletCounter();
		
		this.addEFormFieldLinkId = this.addeFormFieldlinkStartId + this.formletCounter.toString();
		
		formlet.eFormFieldIndex += 1;
		
		this.eFormFieldIndex = formlet.eFormFieldIndex;
		
		this.setEFormFieldId(this.eFormFieldPrefix + this.eFormFieldType + "-" + RandomStringUtils.randomAlphabetic(5));
	}


	public void initListDetailsField(String fieldType, String suffix) throws Exception {

		//eFormField = new EFormField(form, subFormlet, fieldType);
		
		String fullName = fieldType + suffix;

		this.setEFormFieldId(fullName);

		this.setEFormFieldLabel(fullName.replace("-", " "));

		this.setEFormFieldDescription("");

		this.setEFormFieldTooltip("");

		this.setEFormFieldColumnTitle(fullName.replace("-", " "));

		this.setEFormFieldEmptyColumnText("---");

	}
	protected String getIdLastPart(String htmlTitle) throws Exception
	{
		
		IE ie = IEUtil.getActiveIE();
		String str = ie.htmlElement(title, htmlTitle).id();
		String arr[] = str.split(":");
		
		return arr[arr.length - 1];
		
	}
	
	
	public boolean addeFormField(boolean isItMandatory, boolean doesItHasProperties, boolean isItColumnInList, boolean includeAsFilter) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();		
		
		EFormsUtil.openNewOpjectDetail(this.tempFormlet,EFormsUtil.EAddObjectsFormPlanner.addEFormField.ordinal());
		
		ie.textField(id, IeFormFieldsConst.eFormFieldIdentifier_TextField_Id).set(this.eFormFieldId);
		
		ie.selectList(id, IeFormFieldsConst.eFormFieldType_SelectList_Id).select(this.eFormFieldType);
		
		GeneralUtil.takeANap(1.0);
		
		if(ie.checkbox(id, IeFormFieldsConst.eFormFieldMandatory_Checkbox_Id).exists())
		{
			ie.checkbox(id, IeFormFieldsConst.eFormFieldMandatory_Checkbox_Id).set(isItMandatory);
		}
		
		if(ie.checkbox(id, IeFormFieldsConst.eFormFieldInListColumn_Checkbox_Id).exists() && isItColumnInList)
		{
			ie.checkbox(id, IeFormFieldsConst.eFormFieldInListColumn_Checkbox_Id).set();
			
			GeneralUtil.takeANap(1.0);
			
			if(includeAsFilter && ie.checkbox(id,IeFormFieldsConst.eFormFieldIncludeAsELF_Checkbox_Id).exists()) {
				
				ie.checkbox(id,IeFormFieldsConst.eFormFieldIncludeAsELF_Checkbox_Id).set();
			}			
		}
		
		if(ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").exists()){
			
			ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").set(this.getEFormFieldLabel());
		}
		
		
		ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(this.getEFormFieldDescription());
		
		ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(this.getEFormFieldTooltip());
		
		if(isItColumnInList)
		{
			ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldColumnTitleText_TextField_Id + "/").set(getEFormFieldColumnTitle());
			
			ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldEmptyColumnText_TextField_Id + "/").set(getEFormFieldEmptyColumnText());
			
			
		}
		
		if(ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").exists())
		{
			
			if(ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").exists()){
				
				ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").set(this.getEFormFieldLabel());
			}
			
			ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(getEFormFieldDescription());
			
			ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(getEFormFieldTooltip());
			
			if(isItColumnInList)
			{
				ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldColumnTitleText_TextField_Id + "/").set(getEFormFieldColumnTitle());
				
				ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldEmptyColumnText_TextField_Id + "/").set(getEFormFieldEmptyColumnText());
				
				
			}
		}
		
		if(ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").exists())
		{
			
			if(ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").exists()){
				
				ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").set(this.getEFormFieldLabel());
			}
			
			ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(getEFormFieldDescription());
			
			ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(getEFormFieldTooltip());
			
			if(isItColumnInList)
			{
				ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldColumnTitleText_TextField_Id + "/").set(getEFormFieldColumnTitle());
				
				ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldEmptyColumnText_TextField_Id + "/").set(getEFormFieldEmptyColumnText());
				
				
			}
		}
		
		if(ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").exists())
		{
			
			if(ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").exists()){
				
				ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").set(this.getEFormFieldLabel());
			}
			
			ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(getEFormFieldDescription());
			
			ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(getEFormFieldTooltip());
			
			if(isItColumnInList)
			{
				ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldColumnTitleText_TextField_Id + "/").set(getEFormFieldColumnTitle());
				
				ie.textField(id, "/3:" + IeFormFieldsConst.eFormFieldEmptyColumnText_TextField_Id + "/").set(getEFormFieldEmptyColumnText());
				
				
			}
		}
		
		if(doesItHasProperties)
		{
			
			retValue = true;
		}
		else
		{
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			if(GeneralUtil.isButtonExists(IClicksConst.previewFormBtn))
			{
				retValue = true;
			}
		}		
		
		return retValue;
	}
	
public boolean addReadOnlyeFormField(boolean doesItHasProperties, String strProp) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();		
		
		EFormsUtil.openNewOpjectDetail(this.tempFormlet,EFormsUtil.EAddObjectsFormPlanner.addEFormField.ordinal());
		
		ie.textField(id, IeFormFieldsConst.eFormFieldIdentifier_TextField_Id).set(getEFormFieldId());
		
		ie.selectList(id, IeFormFieldsConst.eFormFieldType_SelectList_Id).select(this.eFormFieldType);		
		
		
		ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(getEFormFieldDescription());
		
		ie.textField(id, "/0:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(getEFormFieldTooltip());
		
		if(ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").exists())
		{
			ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(getEFormFieldDescription());
			
			ie.textField(id, "/1:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(getEFormFieldTooltip());
		}
		
		if(ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldQuestionLabel_LongField_Id + "/").exists())
		{
			ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldDescription_LongField_Id + "/").set(getEFormFieldDescription());
			
			ie.textField(id, "/2:" + IeFormFieldsConst.eFormFieldPopupHelpText_LongField_Id + "/").set(getEFormFieldTooltip());
		}
		
		if(doesItHasProperties)
		{
			ie.selectList(id, IeFormFieldsConst.eFormField_ReadOnlyPropFields_Id[0]).select(strProp);			
			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		if(GeneralUtil.isButtonExists(IClicksConst.previewFormBtn))
		{
			retValue = true;
		}				
		
		return retValue;
	}
	
	
	public boolean addeFormFieldProperties(Hashtable<Integer, String> textFieldsProperties) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		keysEnum = textFieldsProperties.keys();
		
		elementsEnum = textFieldsProperties.elements();
		
		while (keysEnum.hasMoreElements())
		{
			ie.textField(id, keysEnum.nextElement().toString()).set(elementsEnum.nextElement().toString());
		}
		
		//TODO: write the code
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		return retValue;
	}
	
	public boolean addReviewScoreFieldProperties(Object[] objReviewScore) throws Exception
	{
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (objReviewScore.length == 5)
		{
			ie.textField(id, IeFormFieldsConst.eFormField_ReviewScorePropFields_Id[0]).set(objReviewScore[0].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_ReviewScorePropFields_Id[1]).set(objReviewScore[1].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_ReviewScorePropFields_Id[2]).set(objReviewScore[2].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_ReviewScorePropFields_Id[4]).set(objReviewScore[4].toString());
			
			if(objReviewScore[3].toString().matches("true"))
			{
				ie.checkbox(id, IeFormFieldsConst.eFormField_ReviewScorePropFields_Id[3]).set(true);
			}
			else
			{
				ie.checkbox(id, IeFormFieldsConst.eFormField_ReviewScorePropFields_Id[3]).set(false);
			}
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			retValue = true;			
			
		}
		else
		{
			log.info("Must be 5 Values passed in the Array");
		}
		
		return retValue;
	}
	
	public boolean addNumericFieldProperties(Object[] objNumeric) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (objNumeric.length == 6)
		{
			ie.textField(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[0]).set(objNumeric[0].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[1]).set(objNumeric[1].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[2]).set(objNumeric[2].toString());
			
			if(objNumeric[3].toString().matches("true"))
			{
				ie.checkbox(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[3]).set(true);
			}
			else
			{
				ie.checkbox(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[3]).set(false);
			}
			
			ie.selectList(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[4]).select(objNumeric[4].toString());
			
			ie.selectList(id, IeFormFieldsConst.eFormField_NumericPropFields_Id[5]).select(objNumeric[5].toString());
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			retValue = true;
		}
		else
		{
			log.info("Must be 6 Values passed in the Array");
		}		
		
		return retValue;
	}
	
	public boolean addShortTextFieldProperties(Integer[] intShort) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (intShort.length == 2)
		{
			ie.textField(id, IeFormFieldsConst.eFormField_ShortTextPropFields_Id[0]).set(intShort[0].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_ShortTextPropFields_Id[1]).set(intShort[1].toString());
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			retValue = true;
		}
		else
		{
			log.info("Must be 2 Values passed in the Array");
		}
		return retValue;
	}
	
	public boolean addLongTextFieldProperties(Object[] objLongText) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if(objLongText.length == 4)
		{
			ie.textField(id, IeFormFieldsConst.eFormField_LongTextPropFields_Id[0]).set(objLongText[0].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_LongTextPropFields_Id[1]).set(objLongText[1].toString());
			
			ie.textField(id, IeFormFieldsConst.eFormField_LongTextPropFields_Id[2]).set(objLongText[2].toString());
			
			ie.selectList(id, IeFormFieldsConst.eFormField_LongTextPropFields_Id[3]).select(objLongText[3].toString());
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			retValue = true;
		}
		else
		{
			log.info("Must be 4 Values passed in the Array");
		}
		return retValue;
	}
	
	public boolean addDropdwonFieldProperties(String[] strDropdownValues) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if(strDropdownValues.length == 2)
		{
			if(ie.selectList(id, IeFormFieldsConst.eFormField_DropdownPropFields_Id[0]).exists())
			{
				ie.selectList(id, IeFormFieldsConst.eFormField_DropdownPropFields_Id[0]).select(strDropdownValues[0]);
			}
			else
			{
				ie.textField(id, IeFormFieldsConst.eFormField_DropdownPropFields_Id[3]).set(strDropdownValues[0]);
			}			
			
			ie.selectList(id, IeFormFieldsConst.eFormField_DropdownPropFields_Id[1]).select(strDropdownValues[1]);
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			retValue = true;
		}
		else
		{
			log.error("Error: array values must 2!");
		}
		
		return retValue;
	}
	
	public void addPropertiesForFieldsWithTypesOnly(String[] types) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_PostalCodePropFields_Id[0]).select(types[0]);
		
		ie.selectList(id,IeFormFieldsConst.eFormField_PostalCodePropFields_Id[1]).select(types[1]);
		
		if(types.length == 3)
		{
			ie.selectList(id,IeFormFieldsConst.eFormField_PostalCodePropFields_Id[2]).select(types[2]);
		}	
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
	}
	
	public boolean verifyAutoSaveTypePropertyExistsForFieldType(String valueToSelect,String fieldType) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormFieldType_SelectList_Id).select(fieldType);
		
		GeneralUtil.takeANap(0.5);
		
		if(ie.span(id,IeFormFieldsConst.eFormFieldProperties_Span_Id).exists()) {
			
			Tables tables = ie.span(id,IeFormFieldsConst.eFormFieldProperties_Span_Id).tables();
			
			for (Table table : tables) {
				
				if(table.innerText().startsWith("Auto-Save Formlet on Value change")) {
					table.selectList(0).select(valueToSelect);
					
					return Boolean.TRUE;
				}				
			}			
		}
		
		return Boolean.FALSE;
		
	}
	
	public void addPropertiesForFieldsWithDropdownOnly(String[] styles) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		for(int x=0; x<styles.length;x++)
		{
			ie.selectList(id,IeFormFieldsConst.eFormField_DropdownPropFields_Id[x]).select(styles[x]);
		}
			
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
	}
	
	public void addPropertiesForFieldsWithDropdownFromList(String[] styles) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ie.textField(id, IeFormFieldsConst.eFormField_DropdownFromListPropFields_Id[0]).set(styles[0]);
		
		for(int x=1; x<styles.length;x++)
		{
			ie.selectList(id,IeFormFieldsConst.eFormField_DropdownFromListPropFields_Id[x]).select(styles[x]);
		}
			
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
	}
	
	public void addPropertiesForFieldsWithRadioButton(String[] styles) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		for(int x=0; x<styles.length;x++)
		{
			ie.selectList(id,IeFormFieldsConst.eFormField_RadioButtonPropField_Id[x]).select(styles[x]);
		}
			
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
	}
	
	public void addPropertiesForFieldsWithCheckbox(String styles) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
			
		ie.selectList(id,IeFormFieldsConst.eFormField_CheckboxPropField_Id).select(styles);
					
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
	}
	
	public void addPropertiesForFieldsWithStylesOnly(String[] styles) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(styles.length == 2)
		{
			ie.selectList(id, IeFormFieldsConst.eFormField_WithStyleOnlyPropFields_Id[0]).select(styles[0]);
			ie.selectList(id,IeFormFieldsConst.eFormField_WithStyleOnlyPropFields_Id[1]).select(styles[1]);
		}
		else
		{
			ie.selectList(id, IeFormFieldsConst.eFormField_WithStyleOnlyPropFields_Id[0]).select(styles[0]);
		}
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
	}
	
	public boolean addPropertiesForEFormList(String[] eFormListProp) throws Exception{
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (2 == eFormListProp.length)
		{
			EFormsUtil.expandFormPlannerNode(this.tempFormlet, EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
			
			//EFormsUtil.openOpjectDetail(this.getEFormFieldId());
			
			EFormsUtil.openObjectDetail(this.eFormFieldType + ": " + this.getEFormFieldId());
			
			ie.checkbox(id, IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[0]).set();
			
			//Delay
			GeneralUtil.takeANap(2.0);
			
			ie.textField(id, "/0" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[1]).set(eFormListProp[0]);
			ie.textField(id, "/0" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[2]).set(eFormListProp[1]);
			
			if (ie.textField(id, "/1" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[1]).exists())
			{
				ie.textField(id, "/1" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[1]).set(eFormListProp[0]);
				ie.textField(id, "/1" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[2]).set(eFormListProp[1]);
			}
			
			if (ie.textField(id, "/2" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[1]).exists())
			{
				ie.textField(id, "/2" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[1]).set(eFormListProp[0]);
				ie.textField(id, "/2" + IeFormFieldsConst.eFormField_EFormListColumnsPropFields_Id[2]).set(eFormListProp[1]);
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			retValue = true;
		}
		
		
		return retValue;
	}
	
	public boolean addGridViewOfListFiledsProperties(Object[] objGridViewProps) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (objGridViewProps.length == 20) 
		{
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[0]).set(objGridViewProps[0].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[1]).set(objGridViewProps[1].toString());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[2]).select(objGridViewProps[2].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[3]).set(objGridViewProps[3].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[4]).set(objGridViewProps[4].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[5]).set(objGridViewProps[5].toString());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[6]).select(objGridViewProps[6].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[7]).set(objGridViewProps[7].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[8]).set(objGridViewProps[8].toString());
			ie.textField(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[9]).set(objGridViewProps[9].toString());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[10]).select(objGridViewProps[10].toString());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[11]).select(objGridViewProps[11].toString());
			ie.checkbox(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[12]).set(((Boolean)objGridViewProps[12]).booleanValue());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[13]).select(objGridViewProps[13].toString());
			ie.checkbox(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[14]).set(((Boolean)objGridViewProps[14]).booleanValue());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[15]).select(objGridViewProps[15].toString());
			ie.checkbox(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[16]).set(((Boolean)objGridViewProps[16]).booleanValue());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[17]).select(objGridViewProps[17].toString());
			ie.checkbox(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[18]).set(((Boolean)objGridViewProps[18]).booleanValue());
			ie.selectList(id, IeFormFieldsConst.eFormField_GridViewOfListPropFields_Id[19]).select(objGridViewProps[19].toString());
			retValue = true;
		}		
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		return retValue;
	}
	
	public boolean configureDataGridEFormField(String numOfColumns, String numOfRows) throws Exception {
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.configureDataGridEFormFieldBtn);
		
		ie.textField(id,IeFormFieldsConst.eFormField_DataGrid_Configure_NumberOfColumnsField_Id).set(numOfColumns);
		
		ie.image(alt, IeFormFieldsConst.eFormField_DataGrid_Configure_InsertColumns_PlusAlt).click();
		
		ie.waitUntilReady();
		
		ie.textField(id, IeFormFieldsConst.eFormField_DataGrid_Configure_NumberOfRowsField_Id).set(numOfRows);
		
		ie.image(alt, IeFormFieldsConst.eFormField_DataGrid_Configure_InsertRows_PlusAlt).click();
		retValue = true;
		
		return retValue;
	}
	
	
	public boolean selectDataGridCells(int [] gridEditorValues) throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		retValue = true;
		
		if(gridEditorValues.length == 1)
		{
			if (gridEditorValues[0] == IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll.ordinal())
			{
				ClicksUtil.clickButtons(IClicksConst.selectAllBtn);
				retValue = true;
			}
			else if (gridEditorValues[0] == IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectNone.ordinal())
			{
				ClicksUtil.clickButtons(IClicksConst.selectNoneBtn); //this is will clear the selection if any
				retValue = true;
			}
			
		}
		else if (gridEditorValues.length > 1)
		{
			ie.body(id,IeFormFieldsConst.eFormField_DataGrid_ConfigureGrid_TableBody_Id).row(gridEditorValues[0]).table(gridEditorValues[1]).checkbox(0).set(true);
			
			if (gridEditorValues.length > 3)
			{
				ie.body(id,IeFormFieldsConst.eFormField_DataGrid_ConfigureGrid_TableBody_Id).row(gridEditorValues[2]).table(gridEditorValues[3]).checkbox(0).set(true);
			}			
			
			ClicksUtil.clickButtons(IClicksConst.selectRangeBtn);
			retValue = true;
		}
		
		return retValue;
		
	}
	
	
	public boolean setDataGridCellType_NumericEntryCell() throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Numeric Entry Cell");
		
		ie.textField(id, "/numeric_Minimum/").set("");
		
		ie.textField(id, "/numeric_Maximum/").set("");
		
		ie.textField(id, "/numeric_numDecimals/").set("2");
		
		ie.checkbox(id, "/numeric_groupDigits/").set(true);
		
		ie.selectList(id, "/numeric_decoration/").select(IeFormFieldsConst.decoration[1]);
		
		ie.selectList(id,IeFormFieldsConst.eFormField_DataGrid_Configure_Currency_Symbol_Id).select(ILookupsConst.currencySymbol[0]);
		
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);
		
		ie.waitUntilReady();
		
		retValue = true;
		
		return retValue;
	}
	
	public boolean testDataGridCellType_NumericEntryCell() throws Exception {
		
		
		if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id, "Numeric Entry Cell"))
		{
			log.error("Could not find entry in Dropdown!");
			return false;
		}		
		
		return true;
	}
	
	
	public boolean setDataGridCellType_DropdownFromLookupCell() throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Dropdown From Lookup Cell");
		
		ie.selectList(id, "/dropdown_lookupID/").select("Activity Types");
		
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);	
		
		retValue = true;
		
		return retValue;
	}
	
	
	public boolean setDataGridCellType_DropdownFromLookupCell(String lookupName) throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Dropdown From Lookup Cell");
		
		ie.selectList(id, "/dropdown_lookupID/").select(lookupName);
		
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);	
		
		retValue = true;
		
		return retValue;
	}
	
	public boolean setDataGridCellType_FixedTextCell() throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Fixed Text Cell");
		
		ie.selectList(id, "main:EFormFieldDetails_ConfigureGrid:eformfielddetails_configureGrid:cell_alignment").select("Left");
		
		ie.textField(id, "/:0:fixedText/").set("Text");
		
		if (ie.textField(id, "/:1:fixedText/").exists())
		{
			ie.textField(id, "/:1:fixedText/").set("Text");
		}
		
		if (ie.textField(id, "/:2:fixedText/").exists())
		{
			ie.textField(id, "/:2:fixedText/").set("Text");
		}
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);
		retValue = true;
		
		return retValue;
	}
	
	
	public boolean setDataGridCellType_FixedTextCellFromLookup() throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Fixed Text Cell, imported from Lookup");
		
		ie.selectList(id, "/dropdown_lookupID/").select("/Activity Types/");
		
		ie.selectList(id, "/fixedText_alignment/").select("Left");
		
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);
		retValue = true;
		
		return retValue;
	}
	
	public boolean setDataGridCellType_TextEntryCell() throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Text Entry Cell");
		
		ie.textField(id, "/textEntry_MinLength/").set("");
		
		ie.textField(id, "/textEntry_MaxLength/").set("");
		
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);
		
		retValue = true;
		
		return retValue;
	}
	
	public boolean setDataGridCellType_DropdownFromListCell(String sourceField) throws Exception {
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.selectList(id, IeFormFieldsConst.eFormField_DataGrid_Configure_CellType_DrpDownField_Id).select("Dropdown From List Cell");
		
		ie.textField(id, "/sourceField/").set(sourceField);
		
		ClicksUtil.clickButtons(IClicksConst.setCellTypeBtn);
		
		retValue = true;
		
		return retValue;
	}
	
	public boolean searchCurrencySymbol() throws Exception{
		int count=0;
			
		if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_NumericPropFields_Id[4], IeFormFieldsConst.decoration[1]))
        	
        {
        	log.error("Could not selcect Currency from the dropdown");
                        return false;
        }

		CurrencySymbol []currencySymbol = CurrencySymbol.values();
		
		for (int i = 0; i < currencySymbol.length; i++)
		{
						          
			if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_NumericPropFields_Id[5], currencySymbol[i].getValue()))
                 {
                     log.error("Could not selcect Currency from the dropdown:".concat(currencySymbol[i].getValue()));
                               
                     return false;
                  }                                                                                              
		
			 count++;
		}
		
		if (count!=currencySymbol.length)
		{
			log.error("All deafault currency symbols are not available");
			return false;
		}
		
		return true;
	}
	
public boolean searchDefaultSymbol() throws Exception{
		
				
		if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_NumericPropFields_Id[4], IeFormFieldsConst.decoration[1]))
        {
                        
        	log.error("Could not selcect Currency from the dropdown");
        	
                        return false;
        }
		
      			
		 if (!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_NumericPropFields_Id[5], ILookupsConst.currencySymbol[0]))
		 {
			 log.error("Could not Find  $ Currency Symbol");
	        	
             return false;
			 
		 }
		
			return true;
					
	}

public boolean searchDataGridCurrencySymbol() throws Exception{
	int count=0;
		
	if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_DataGrid_Configure_decoration, IeFormFieldsConst.decoration[1]))
    	
    {
    	log.error("Could not selcect Currency from the dropdown");
                    return false;
    }

	CurrencySymbol []currencySymbol = CurrencySymbol.values();
	
	for (int i = 0; i < currencySymbol.length; i++)
	{
					          
		if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_DataGrid_Configure_Currency_Symbol_Id,currencySymbol[i].getValue()))
             {
                 log.error("Could not selcect Currency from the dropdown:".concat(currencySymbol[i].getValue()));
                           
                 return false;
              }                                                                                              
	
		 count++;
	}
	
	if (count!=currencySymbol.length)
	{
		log.error("All deafault currency symbols are not available");
		return false;
	}
	
	return true;
}

public boolean searchDataGridDefaultSymbol() throws Exception{
	
			
	if(!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_DataGrid_Configure_decoration, IeFormFieldsConst.decoration[1]))
    {
                    
    	log.error("Could not selcect Currency from the dropdown");
    	
                    return false;
    }
	
  		
	
	 if (!GeneralUtil.selectFullStringInDropdownList(IeFormFieldsConst.eFormField_DataGrid_Configure_Currency_Symbol_Id, ILookupsConst.currencySymbol[0]))
	 {
		 log.error("Could not Find  $ Currency Symbol");
        	
         return false;
		 
	 }
	
		return true;
	
		
}


public boolean validateValue() throws Exception
{  
	
	retValue= true;
	IE ie = IEUtil.getActiveIE();
	
	for(int i=0; i<3; i++)
	{
	if (!ie.textField(id, IeFormFieldsConst.eFormFiled_Numeric_fieldIds[i]).get().equals(IeFormFieldsConst.eFormField_Value[i].toString()))
			{
                 log.error("Value is not equals to :".concat(IeFormFieldsConst.eFormField_Value[i].toString()));
                 	
                  retValue= false;
			}
	}
	return retValue;
}
		//****** Getter and Setter ***************************

	public String getEFormFieldDescription() {
		return eFormFieldDescription;
	}

	public void setEFormFieldDescription(String formFieldDescription) {
		eFormFieldDescription = formFieldDescription;
	}

	public String getEFormFieldLabel() {
		return eFormFieldLabel;
	}

	public void setEFormFieldLabel(String formFieldLabel) {
		eFormFieldLabel = formFieldLabel;
	}

	public String getEFormFieldTooltip() {
		return eFormFieldTooltip;
	}

	public void setEFormFieldTooltip(String formFieldTooltip) {
		eFormFieldTooltip = formFieldTooltip;
	}

	public String getEFormFieldId() {
		return eFormFieldId;
	}

	public void setEFormFieldId(String formFieldId) {
		eFormFieldId = formFieldId;
	}

	/**
	 * @return the eFormFieldType
	 */
	public String getEFormFieldType() {
		return eFormFieldType;
	}

	/**
	 * @param formFieldType the eFormFieldType to set
	 */
	public void setEFormFieldType(String formFieldType) {
		eFormFieldType = formFieldType;
	}
	
	/**
	 * @return the eFormFieldColumnTitle
	 */
	public String getEFormFieldColumnTitle() {
		return eFormFieldColumnTitle;
	}

	/**
	 * @param formFieldColumnTitle the eFormFieldColumnTitle to set
	 */
	public void setEFormFieldColumnTitle(String formFieldColumnTitle) {
		eFormFieldColumnTitle = formFieldColumnTitle;
	}

	/**
	 * @return the eFormFieldEmptyColumnText
	 */
	public String getEFormFieldEmptyColumnText() {
		return eFormFieldEmptyColumnText;
	}

	/**
	 * @param formFieldEmptyColumnText the eFormFieldEmptyColumnText to set
	 */
	public void setEFormFieldEmptyColumnText(String formFieldEmptyColumnText) {
		eFormFieldEmptyColumnText = formFieldEmptyColumnText;
	}

	/**
	 * @return the tempFormlet
	 */
	public String getTempFormlet() {
		return tempFormlet;
	}

	/**
	 * @param tempFormlet the tempFormlet to set
	 */
	public void setTempFormlet(String tempFormlet) {
		this.tempFormlet = tempFormlet;
	}

	/**
	 * @return the lstProp
	 */
	public List<Object> getLstProp() {
		return lstProp;
	}

	/**
	 * @param lstProp the lstProp to set
	 */
	public void setLstProp(List<Object> lstProp) {
		this.lstProp = lstProp;
	}

}
