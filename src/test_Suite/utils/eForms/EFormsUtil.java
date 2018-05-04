/**
 * 
 */
package test_Suite.utils.eForms;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.src;
import static watij.finders.SymbolFactory.tag;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.title;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.dialogs.FileDownloadDialog;
import watij.elements.Div;
import watij.elements.Divs;
import watij.elements.HtmlElement;
import watij.elements.HtmlElements;
import watij.elements.Image;
import watij.elements.Images;
import watij.elements.Span;
import watij.elements.Spans;
import watij.elements.TableCell;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki Generic Utilities to manuplate eForm planner
 */
public class EFormsUtil {

	private static Log log = LogFactory.getLog(EFormsUtil.class);

	private static Images images;

	private static Image image;

	private static int retIndex;

	private static int retCounts;

	private static boolean retValue;

	private static boolean state;

	static ArrayList<String> errorSmall;

	public enum EManipulatePlannerNode {
		expandNode, collapseNode, deleteNode

	}

	public enum EAddObjectsFormPlanner {
		addFormlet, addEFormField, addSubFormlet, addFormFunction, addFormletFunction, addEformFieldFunction
	}

	public enum EAddFunctionsFormPlanner {
		addFormFunction, addFormletFunction, addEformFieldFunction
	}

	/**
	 * 
	 * @param formType
	 * @param formSubType
	 * @param preFix
	 * @return
	 * @throws Exception
	 */
	public static EForm initializeBasicEForm(String formType,
			String formSubType, String preFix) throws Exception {

		EForm form = new EForm(formType, formSubType, preFix);

		form.setEFormFullId(form.getEFormFullId().replace(" ", "-"));

		form.setEFormTitle(form.getEFormFullId().replace('-', ' '));

		Formlet formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder);
		formlet.setFormletId(formlet.getFormletId().replace(" ", "-"));
		formlet.setFormletTitleText(formlet.getFormletId().replace('-', ' '));
		formlet.setFormletMenuText(formlet.getFormletTitleText());

		EFormField field = null;

		if (formType.equalsIgnoreCase("Approval")) {

			field = new EFormField(formlet,
					IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		} else {
			field = new EFormField(formlet,
					IeFormFieldsConst.eFormFieldType_ShortTextField_Name);
		}

		String fullName = field.getEFormFieldId();

		field.setEFormFieldId(fullName.replace(" ", "-"));

		field.setEFormFieldLabel(fullName.replace("-", " "));

		field.setEFormFieldDescription("");

		field.setEFormFieldTooltip("");

		List<EFormField> lstField = new ArrayList<EFormField>();
		lstField.add(0, field);

		formlet.setLstFields(lstField);

		List<Formlet> lstFrmlt = new ArrayList<Formlet>();

		lstFrmlt.add(0, formlet);

		form.setLstFormlets(lstFrmlt);

		return form;
	}

	/**
	 * 
	 * @param form
	 * @throws Exception
	 */
	public static void createBasicEForm(EForm form) throws Exception {

		Assert.assertTrue(form.createEForm(), "FAIL: could not create eForm");

		List<EFormField> lst = null;

		for (int i = 0; i < form.getLstFormlets().size(); i++) {

			Assert.assertTrue(
					form.getLstFormlets().get(i).createFormlet(true, true),
					"FAIL: could not create Formlet");

			lst = form.getLstFormlets().get(i).getLstFields();
		}

		for (int x = 0; x < lst.size(); x++) {

			EFormField field = lst.get(x);

			Assert.assertTrue(field.addeFormField(true, false, false, false),
					"FAIL: could not add Field: " + field.getEFormFieldId());
		}

		return;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public static void returnToPlanner() throws Exception {

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.backBtn)) {
			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
	}

	public static HtmlElement getListItem(String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.div(
				attribute("class", IGeneralConst.planner_div_class))
				.htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if (ele.span(0).innerText().trim().contains(innerText)) {

				return ele;
			}
		}
		log.error("Could not find object " + innerText);
		return null;
	}

	/**
	 * 
	 * @param nodeLabel
	 * @param eManipulate
	 * @throws Exception
	 */
	public static void expandFormPlannerNode(String nodeLabel, int eManipulate)
			throws Exception {

		try {

			HtmlElement ele = getListItem(nodeLabel);

			Spans sp = ele.spans();

			if (!isExpanded(ele)) {

				sp.span(1).click();

			} else {
				log.warn("Object " + nodeLabel + " already expanded!");
			}

			// tables = ie.span(id, IEFormsConst.formPlanner_Span_Id).tables();
			//
			// for (Table table1 : tables) {
			//
			// if (table1.innerText().endsWith(nodeLabel)) {
			//
			// images = table1.images();
			//
			// for (Image image1 : images) {
			//
			// if ((image1.src().contains(IClicksConst.expandGeneralImg))&&
			// (eManipulate == EManipulatePlannerNode.expandNode.ordinal())) {
			//
			// image1.click();
			// break;
			//
			// } else if
			// ((image1.src().contains(IClicksConst.collapseGeneralImg)) &&
			// (eManipulate == EManipulatePlannerNode.collapseNode.ordinal())) {
			//
			// image1.click();
			// break;
			// }
			// }
			// break;
			// }
			// }
		} catch (Exception e) {
			log.error("Unexpected Exception: " + e);
		}

	}

	/**
	 * 
	 * @param fieldIndex
	 * @param stringTocompare
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean compareStringInEFormField(int fieldIndex,
			String stringTocompare) throws Exception {

		String sourceString = GeneralUtil.getTextInTextFieldByIndex(fieldIndex);

		return sourceString.equals(stringTocompare);

	}

	/**
	 * 
	 * @param parentNodeLabel
	 * @param childNodeLabel
	 * @return
	 * @throws Exception
	 */
	// public static boolean expandParentAndChildNode(String parentNodeLabel,
	// String childNodeLabel) throws Exception {
	// IE ie = IEUtil.getActiveIE();
	//
	// tables = ie.span(id, IEFormsConst.formPlanner_Span_Id).tables();
	//
	// for (Table table : tables) {
	//
	// if (table.innerText().endsWith(parentNodeLabel)) {
	// images = table.images();
	//
	// for (Image image : images) {
	//
	// if (image.src().endsWith(IClicksConst.expandNodeImg)) {
	// image.click();
	// return expandChild(childNodeLabel);
	//
	// }else if(image.src().endsWith(IClicksConst.collapseNodeImg)){
	//
	// return expandChild(childNodeLabel);
	// }
	//
	// }
	// }
	//
	// }
	//
	// return false;
	// }
	public static void expandAnObject(String objectInnerText) throws Exception {

		HtmlElement ele = getListItem(objectInnerText);

		if (!isExpanded(ele)) {

			Spans sp = ele.spans();

			sp.span(1).click();

		} else {
			log.warn("Object " + objectInnerText + " already expanded!");
		}
	}

	public static boolean expandParentAndChildNode(String parentNodeLabel,
			String childNodeLabel) throws Exception {

		try {

			expandAnObject(parentNodeLabel);

			HtmlElement ele = getListItem(childNodeLabel);

			if (!isExpanded(ele)) {

				Spans sp = ele.spans();

				sp.span(1).click();

			} else {
				log.warn("Object " + childNodeLabel + " already expanded!");
			}
		} catch (Exception e) {
			log.error("Unexpected Exception: " + e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param childNodeLabel
	 * @return
	 * @throws Exception
	 */
	// public static boolean expandChild(String childNodeLabel) throws Exception
	// {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// tables = ie.span(id, IEFormsConst.formPlanner_Span_Id).tables();
	// for (Table table : tables) {
	//
	// if (table.innerText().endsWith(childNodeLabel)) {
	// images = table.images();
	//
	// for (Image image : images) {
	//
	// if (image.src().endsWith(IClicksConst.expandNodeImg)) {
	// image.click();
	//
	// return true;
	//
	// } else if (image.src().endsWith(
	// IClicksConst.collapseNodeImg)) {
	// return true;
	// }
	//
	// }
	//
	// return false;
	// }
	//
	// }
	//
	// return false;
	// }

	public static boolean expandChild(String childNodeLabel) throws Exception {

		try {
			HtmlElement ele = getListItem(childNodeLabel);

			if (!isExpanded(ele)) {

				Spans sp = ele.spans();

				sp.span(1).click();

			} else {
				log.warn("Object " + childNodeLabel + " already expanded!");
			}

		} catch (Exception e) {
			log.error("Unexpected Exception: " + e);
			return false;
		}
		return true;
	}

	public static HtmlElement getListIdFromPlanner(String startId,
			String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.htmlElement(id, startId).htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if (ele.innerText().trim().startsWith(innerText)) {

				return ele;
			}
		}
		return null;
	}

	public static boolean isExpanded(HtmlElement ele) throws Exception {

		Spans sp = ele.spans(attribute("class", IClicksConst.expandedSpan));

		for (Span span : sp) {

			if (span.exists())

				return true;
		}

		return false;
	}

	/**
	 * This method will Expand the Sub Node i.e. Formlet and eFormField
	 * Functions
	 * 
	 * @param parentNodeLabel
	 * @param childNodeLabel
	 * @return
	 * @throws Exception
	 *             UNUSED
	 */
	// public static boolean expandFormPlannerChildNode(String parentNodeLabel,
	// String childNodeLabel) throws Exception {
	//
	// retValue = false;
	// IE ie = IEUtil.getActiveIE();
	//
	// tables = ie.span(id, IEFormsConst.formPlanner_Span_Id).tables();
	//
	// for (int x = 0; x < tables.length(); x++) {
	// table = tables.table(x);
	//
	// if (table.innerText().endsWith(parentNodeLabel)) {
	// images = tables.table(x + 1).images();
	//
	// for (int i = 0; i < images.length(); i++) {
	// image = images.image(i);
	//
	// if (image.src().endsWith(IClicksConst.expandNodeImg)) {
	// image.click();
	//
	// retValue = true;
	//
	// break;
	// } else {
	// if (image.src().endsWith(IClicksConst.collapseNodeImg)) {
	// retValue = true;
	//
	// break;
	// }
	// }
	// }
	// break;
	// }
	// }
	//
	// return retValue;
	//
	// }

	// public static void expandAnObject(String objectInnerText)throws Exception
	// {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// HtmlElement sId =
	// getListIdFromPlanner(IClicksConst.plannerExpanderTopSpan,
	// objectInnerText);
	//
	// if (!isExpanded(sId)){
	//
	// Spans sp = ie.htmlElement(id, sId.id()).spans();
	//
	// sp.span(1).click();
	//
	// } else {
	// log.warn("Object " + objectInnerText + " already expanded!");
	// }
	// }

	/**
	 * 
	 * @param parentNodeLabel
	 * @return
	 * @throws Exception
	 */
	public static boolean openFormletField(String parentNodeLabel)
			throws Exception {

		retValue = false;

		HtmlElement ele = getListItem(parentNodeLabel);

		if (ele.images() != null) {

			for (Image image : ele.images()) {

				if (image.src().contains(IClicksConst.PlannerViewField)) {
					image.click();

					retValue = true;
					return retValue;
				}
			}
		}
		log.error("Could not find Image in node " + parentNodeLabel);
		return retValue;

	}

	/**
	 * This method will Add A Node i.e. Formlet, eFormField and Function
	 * 
	 * @param formlet
	 * @param nodeLabel
	 * @param eAddObject
	 * @return
	 * @throws Exception
	 */
	public static boolean openNewOpjectDetailInFormlet(Formlet formlet,
			String nodeLabel, int eAddObject) throws Exception {

		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		HtmlElements lis = ie.div(id, IEFormsConst.formPlanner_Div_Id).htmlElements(tag, "li");
		
		for (HtmlElement lii : lis) {
			
			if(lii.innerText().startsWith("Formlet: " + formlet.getFormletId()))
			{
				HtmlElements liss2 = lii.htmlElements(tag, "li");
				
				for (HtmlElement liii : liss2) {
					
					if(liii.innerText().startsWith(nodeLabel))
					{
						
						if(liii.link(title, IEFormsConst.formPlannerFormsAddLinksTitles[eAddObject]).exists())
						{
							liii.link(title, IEFormsConst.formPlannerFormsAddLinksTitles[eAddObject]).click();
							
							return true;
							
						}
						
					}
					
				}
			}
			
		}
		
		log.error("Could not find the Object to open or click in eForm Planner!");

		return false;

	}

	/**
	 * This method will Add A Node i.e. Formlet, eFormField and Function
	 * 
	 * @param nodeLabel
	 * @param eAddObject
	 * @return
	 * @throws Exception
	 */
	public static boolean openNewOpjectDetail(String nodeLabel, int eAddObject)
			throws Exception {

		retValue = false;

		HtmlElement ele = getListItem(nodeLabel);

		if (ele.span(0).innerText().endsWith(nodeLabel)) {
			images = ele.images();

			for (int i = 0; i < images.length(); i++) {
				image = images.image(i);

				if ((image.alt().endsWith(IClicksConst.frmAddFormletAlt))
						&& (eAddObject == EAddObjectsFormPlanner.addFormlet
								.ordinal())) {

					image.click();
					retValue = true;
					break;

				} else if ((image.alt()
						.endsWith(IClicksConst.frmAddEFormFieldAlt))
						&& (eAddObject == EAddObjectsFormPlanner.addEFormField
								.ordinal())) {

					image.click();
					retValue = true;
					break;

				} else if ((image.alt()
						.endsWith(IClicksConst.frmAddSubFormletAlt))
						&& (eAddObject == EAddObjectsFormPlanner.addSubFormlet
								.ordinal())) {

					image.click();
					retValue = true;
					break;

				} else if ((image.alt()
						.endsWith(IClicksConst.frmAddFormFunctionAlt))
						&& (eAddObject == EAddObjectsFormPlanner.addFormFunction
								.ordinal())) {

					image.click();
					retValue = true;
					break;

				} else if ((image.alt()
						.endsWith(IClicksConst.frmAddFormletFunctionAlt))
						&& (eAddObject == EAddObjectsFormPlanner.addFormletFunction
								.ordinal())) {

					image.click();
					retValue = true;
					break;

				} else if ((image.alt()
						.endsWith(IClicksConst.frmAddEFormFieldFunctionAlt))
						&& (eAddObject == EAddObjectsFormPlanner.addEformFieldFunction
								.ordinal())) {

					image.click();
					retValue = true;
					break;
				}

			}
		}

		return retValue;

	}

	/**
	 * This method will delete A Node i.e. Formlet, eFormField and Function
	 * 
	 * @param startNodeLabel
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFormPlannerNode(String startNodeLabel)
			throws Exception {

		retValue = false;

		HtmlElement ele = getListItem(startNodeLabel);

		images = ele.images();

		for (int i = 0; i < images.length(); i++) {
			image = images.image(i);

			if (image.src().contains(IClicksConst.deleteImg)) {

				image.click();

				GeneralUtil.takeANap(1.000);

				if (!ClicksUtil.clickButtonsById("j_id_1v:j_id_4u")) {
					log.error("Could not find OK Butoon on Confirmation box!");
				}

				retValue = true;

				break;
			}
		}

		return retValue;

	}

	/**
	 * 
	 * @param startNodeLabel
	 * @return
	 * @throws Exception
	 */
	public static boolean openObjectDetail(String startNodeLabel)
			throws Exception {

		HtmlElement ele = getListItem(startNodeLabel);

		Images images = ele.images();

		for (int i = 0; i < images.length(); i++) {
			Image image = images.image(i);

			if (image.src().contains(IClicksConst.detailImg)) {
				image.click();
				return true;
			}
		}

		return false;

	}

	/**
	 * 
	 * @param eForm
	 * @return
	 * @throws Exception
	 */
	public static boolean openEFormPlanner(EForm eForm) throws Exception {

		retIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);

		retIndex = getEFormRowIndex(eForm.getEFormId(), eForm.getEFormType(),
				IFiltersConst.exact);

		Div tDiv = TablesUtil.tableDiv();

		if (retIndex > -1) {
			tDiv.body(id, ITablesConst.formsTableId).row(retIndex)
					.link(text, eForm.getEFormId()).click();

			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param eFormIdentifier
	 * @param eFormType
	 * @param eFormIdentMode
	 * @return
	 * @throws Exception
	 */
	public static int getEFormRowIndex(String eFormIdentifier,
			String eFormType, String eFormIdentMode) throws Exception {

		Hashtable<String, String> textHashTable = new Hashtable<String, String>();
		Hashtable<String, String> dropdownHashTable = new Hashtable<String, String>();

		textHashTable.put(IFiltersConst.gpa_FormIdent_Lbl, eFormIdentifier);

		dropdownHashTable.put(IFiltersConst.gpa_FormType_Lbl, eFormType);

		dropdownHashTable.put(IFiltersConst.gpa_FormIdent_Lbl, eFormIdentMode);

		FiltersUtil.filterListByLabel(textHashTable, dropdownHashTable, false);

		return TablesUtil.getRowIndex(ITablesConst.formsTableId,
				eFormIdentifier);
	}

	/**
	 * 
	 * @param eFormIdentifier
	 * @param eFormType
	 * @return
	 * @throws Exception
	 */
	public static boolean openEFormPreviewInList(String eFormIdentifier,
			String eFormType) throws Exception {

		retValue = false;

		retIndex = -1;

		retIndex = getEFormRowIndex(eFormIdentifier, eFormType,
				IFiltersConst.exact);

		Div tDiv = TablesUtil.tableDiv();

		if (retIndex > -1) {
			tDiv.body(id, ITablesConst.formsTableId)
					.row(retIndex)
					.image(alt,
							IClicksConst.frmPreviewEFormAlt + eFormIdentifier)
					.click();
			retValue = true;
		} else {
			log.error("The Row Index was less than Zero!");
		}

		return retValue;
	}

	/**
	 * 
	 * @param length
	 * @return String
	 */
	public static String createAnyRandomString(int length) {
		try {

			return RandomStringUtils.randomAlphabetic(length);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return "";

	}

	/**
	 * 
	 * @param length
	 * @return String
	 * @throws Exception
	 */
	public static String createRandomString(int length) throws Exception {

		return RandomStringUtils.random(length, "B");

	}

	/**
	 * 
	 * @param patern
	 * @param length
	 * @return String
	 * @throws Exception
	 */
	public static String createRandomString(String patern, int length)
			throws Exception {

		return RandomStringUtils.random(length, patern);

	}

	/**
	 * 
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String createRandomNumber(int length) throws Exception {

		return RandomStringUtils.random(length, false, true);

	}

	/**
	 * 
	 * @param ddIndex
	 * @param valueToSelect
	 * @throws Exception
	 */
	public static void selectFromDropDown(int ddIndex, String valueToSelect)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		GeneralUtil.takeANap(1.0);

		ie.selectList(ddIndex).select(valueToSelect);
	}

	/**
	 * 
	 * @param dateIndex
	 * @throws Exception
	 */
	public static void enterDateToDateField(int dateIndex) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(dateIndex).set(GeneralUtil.getTodayDate());
	}

	/**
	 * 
	 * @param fieldIndex
	 * @param TextToEnter
	 * @throws Exception
	 */
	public static void enterTextToTextField(int fieldIndex, String TextToEnter)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(fieldIndex).set(TextToEnter);
	}

	/**
	 * 
	 * @param fieldIndex
	 * @param val
	 * @throws Exception
	 */
	public static void taggleCheckbox(int fieldIndex, boolean val)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.checkbox(fieldIndex).set(val);
	}

	public static void taggleRadioButton(int fieldIndex, boolean val)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (val) {
			ie.radio(fieldIndex).set();
		} else {
			ie.radio(fieldIndex).clear();
		}
	}

	/**
	 * 
	 * @param eFormIdentifier
	 * @param eFormType
	 * @return
	 * @throws Exception
	 */
	public static int getEFormCounts(String eFormIdentifier, String eFormType)
			throws Exception {

		FiltersUtil.filterListByLabel(IFiltersConst.gpa_FormIdent_Lbl,
				eFormIdentifier, IFiltersConst.startsWith);

		return TablesUtil.howManyEntriesInTable(ITablesConst.formsTableId);

	}

	/**
	 * 
	 * @param eFormIdentifier
	 * @param eFormSubType
	 * @param clonedIndex
	 * @return
	 * @throws Exception
	 */
	public static boolean openClonedEForm(String eFormIdentifier,
			String eFormSubType, int clonedIndex) throws Exception {

		retIndex = -1;

		String clonedFormId = eFormIdentifier + " Clone shak ["
				+ Integer.toString(clonedIndex) + "]";

		retIndex = getEFormRowIndex(clonedFormId, eFormSubType,
				IFiltersConst.startsWith);

		Div tDiv = TablesUtil.tableDiv();

		if (retIndex > -1) {
			tDiv.body(id, ITablesConst.formsTableId).row(retIndex)
					.link(text, clonedFormId).click();

			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param eFormIdentifier
	 * @param eFormSubType
	 * @return
	 * @throws Exception
	 */
	public static boolean cloneEForm(String eFormIdentifier, String eFormSubType)
			throws Exception {

		retValue = false;

		retIndex = -1;

		retIndex = getEFormRowIndex(eFormIdentifier, eFormSubType,
				IFiltersConst.exact);
		Div tDiv = TablesUtil.tableDiv();

		if (retIndex > -1) {
			tDiv.body(id, ITablesConst.formsTableId)
					.row(retIndex)
					.image(alt, IClicksConst.frmCloneEFormAlt + eFormIdentifier)
					.click();
			retValue = true;
		} else {
			log.error("The Row Index was less than Zero!");
		}

		errorSmall = null;

		errorSmall = GeneralUtil.checkForErrorMessages();

		if (errorSmall != null && !errorSmall.isEmpty()) {
			for (String error : errorSmall) {

				log.error("Validation Error occured: " + error);

			}

			retValue = false;
		}

		return retValue;
	}

	/**
	 * 
	 * @param eFormIdent
	 * @param formletIdent
	 * @return
	 * @throws Exception
	 */
	public static boolean cloneFormlet(String eFormIdent, String formletIdent)
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		retValue = false;

		errorSmall = null;

		ie.image(src, IEFormsConst.CloneFormletImageSrc).click();

		ie.selectList(id, IEFormsConst.CloneFormlet_SourceEForm_Dropdown_Id)
				.select(eFormIdent);

		GeneralUtil.takeANap(1.0);

		ie.selectList(id, IEFormsConst.CloneFormlet_SourceFormlet_Dropdown_Id)
				.select(formletIdent);

		ClicksUtil.clickButtons(IClicksConst.cloneFormletBtn);

		GeneralUtil.takeANap(0.5);

		errorSmall = GeneralUtil.checkForInfoMessages("j_id_1v:Grantium");

		if (errorSmall != null && !errorSmall.isEmpty()) {
			for (String string : errorSmall) {

				retValue = string
						.equalsIgnoreCase("Formlet cloned successfully");

				log.error(formletIdent + " " + string);

			}
		}

		return retValue;
	}

	/**
	 * 
	 * @param eFormIdentifier
	 * @param eFormType
	 * @return
	 * @throws Exception
	 */
	public static int getEFormCountInList(String eFormIdentifier,
			String eFormType) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		retIndex = -1;

		retCounts = 0;

		retIndex = getEFormRowIndex(eFormIdentifier, eFormType,
				IFiltersConst.startsWith);

		if (retIndex > -1) {
			retCounts = tDiv.body(id, ITablesConst.formsTableId).rowCount();
		}

		return retCounts;
	}

	/**
	 * 
	 * @param formletMenu
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public static boolean isValueExistsOnSummaryFormlet(String formletMenu,
			String val) throws Exception {

		IE ie = IEUtil.getActiveIE();

		for (TableRow tableRow : ie.body(id,
				IProjectsConst.frm_SummaryFormlet_TableBodyId).rows()) {

			if (tableRow.innerText().startsWith(formletMenu)) {

				for (TableCell cell : tableRow.cells()) {

					if (cell.innerText().equals(val)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * @param eFormIdent
	 * @return
	 * @throws Exception
	 */
	public static boolean exportEforms(final String eFormIdent)
			throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);

		if (!FiltersUtil.extraFilterListByLabel(
				IFiltersConst.administration_EFormIdent_Lbl, eFormIdent,
				IFiltersConst.exact)) {
			log.error("Could not filter for the e.Form");
			return false;
		}

		Thread dialogClicker = new Thread() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					FileDownloadDialog dialog1 = ie.fileDownloadDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.save(GeneralUtil.getWorkspacePath()
							+ IGeneralConst.formsFilesPath + eFormIdent);
					log.debug("got confirm Dialog1 and clicked OK.");

				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClicker.start();

		tDiv.body(id, ITablesConst.formsTableId).row(0).cell(3).link(0).click();

		GeneralUtil.takeANap(1.000);

		dialogClicker = null;

		return true;
	}

	/**
	 * 
	 * @param xmlFile
	 * @return
	 * @throws Exception
	 */
	public static boolean importEForms(String xmlFile) throws Exception {
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);

		if (!ClicksUtil.clickImage(IClicksConst.imprtImg)) {
			log.error("Could not find the Import Icon");
			return false;
		}

		if (!ie.fileField(0).exists()) {
			log.error("Could not find File Field");
			return false;
		}

		ie.fileField(0).set(
				"\"".concat(GeneralUtil.getWorkspacePath())
						.concat(IGeneralConst.xmlFilesPath).concat(xmlFile)
						.concat("\""));

		ClicksUtil.clickButtons(IClicksConst.uploadBtn);

		return true;
	}

	/**
	 * 
	 * @param EformIdentifier
	 * @param EformType
	 * @return
	 * @throws Exception
	 */
	public static boolean searchEforms(String EformIdentifier, String EformType)
			throws Exception {

		retIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);

		retIndex = getEFormRowIndex(EformIdentifier, EformType,
				IFiltersConst.exact);

		GeneralUtil.takeANap(2.0);

		Div tDiv = TablesUtil.tableDiv();

		if (retIndex > -1) {
			tDiv.body(id, ITablesConst.formsTableId).row(retIndex)
					.link(text, EformIdentifier).click();

			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean searchDefaultSymbol() throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		Divs divs = ie.span(id, IeFormFieldsConst.eFormFieldProperties_Span_Id)
				.divs();

		for (Div div : divs) {

			if (div.innerText().trim().startsWith("Decoration:")) {
				retValue = true;

				if (!GeneralUtil.selectFullStringInDropdownList(
						IeFormFieldsConst.eFormField_NumericPropFields_Id[4],
						IeFormFieldsConst.decoration[1])) {

					log.error("Could not select Currency from the dropdown");

					retValue = false;
				}

			}

			if (retValue == true
					&& div.innerText().trim()
							.startsWith("Symbol for Currency Decoration:")) {
				retValue = true;

				if (!GeneralUtil.selectFullStringInDropdownList(
						IeFormFieldsConst.eFormField_NumericPropFields_Id[5],
						ILookupsConst.currencySymbol[0])) {
					log.error("Could not Find  $ Currency Symbol");

					retValue = false;

				}

			}
		}

		return retValue;

	}

	/**
	 * 
	 * @param fieldID
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public static boolean setFieldValues(String fieldID, Integer fieldValue)
			throws Exception {

		retValue = true;

		IE ie = IEUtil.getActiveIE();

		if (ie.textField(id, fieldID).exists()) {
			ie.textField(id, fieldID).set(fieldValue.toString());
			retValue = true;
		} else {
			log.error("efield doesn't exists");

			retValue = false;
		}

		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFieldValue() throws Exception {

		retValue = true;
		IE ie = IEUtil.getActiveIE();

		for (int i = 0; i < IeFormFieldsConst.eFormFiled_Numeric_fieldIds.length; i++) {
			if (!ie.textField(id,
					IeFormFieldsConst.eFormFiled_Numeric_fieldIds[i]).get()
					.equals(IeFormFieldsConst.eFormField_Value[i].toString())) {
				log.error("Value is not equals to :"
						.concat(IeFormFieldsConst.eFormField_Value[i]
								.toString()));

				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 
	 * @param fieldID
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	public static boolean setEformFieldValues(String fieldID, String fieldValue)
			throws Exception {
		//
		retValue = true;

		IE ie = IEUtil.getActiveIE();

		if (ie.textField(id, fieldID).exists()) {
			ie.textField(id, fieldID).set(fieldValue.toString());

		} else if (ie.selectList(id, fieldID).exists()) {
			if (fieldID == IeFormFieldsConst.available_Items)

			{
				retValue = GeneralUtil.selectfromM2M_NoSave(fieldID,
						IeFormFieldsConst.slected_Items, fieldValue.toString());
			} else

				ie.selectList(id, fieldID).select(fieldValue.toString());
			GeneralUtil.takeANap(1.000);

		} else if (ie.checkbox(id, fieldID).exists()) {
			ie.checkbox(id, fieldID).set();

		} else if (ie.table(id, "g3-form:eFormFieldList:8:radioButtonSet")
				.exists()) {
			TableRows rows = ie.table(id,
					"g3-form:eFormFieldList:8:radioButtonSet").rows();

			for (TableRow tableRow : rows) {

				if (tableRow.innerText().trim()
						.equalsIgnoreCase(fieldValue.toString())) {
					tableRow.radio(0).set();

				}
			}

		}

		else {
			log.error("efield doesn't exists");

			retValue = false;
		}

		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateAppFieldValue() throws Exception {

		retValue = true;
		IE ie = IEUtil.getActiveIE();

		for (int i = 0; i < IeFormFieldsConst.eFormField_IDs.length; i++) {
			log.warn(i);
			if (ie.textField(id, IeFormFieldsConst.eFormField_IDs[i]).exists()) {
				if (i == 4) {
					if (!ie.textField(id, IeFormFieldsConst.eFormField_IDs[i])
							.get()
							.equals(GeneralUtil.getTodayDate().toString())) {
						log.error("Value is not equals to :"
								.concat(IeFormFieldsConst.AppeFormField_Values[i]
										.toString()));

						retValue = false;
					}
				} else if (!ie
						.textField(id, IeFormFieldsConst.eFormField_IDs[i])
						.get()
						.equals(IeFormFieldsConst.AppeFormField_Values[i]
								.toString())) {
					log.error(ie
							.textField(id, IeFormFieldsConst.eFormField_IDs[i])
							.get().concat(" Source Value is not equals to :"
							.concat(IeFormFieldsConst.AppeFormField_Values[i]
									.toString())));

					retValue = false;
				}
			} else if (ie.checkbox(id, IeFormFieldsConst.eFormField_IDs[i])
					.exists()) {
				if (!ie.checkbox(id, IeFormFieldsConst.eFormField_IDs[i])
						.attribute("aria-checked")
						.equals(IeFormFieldsConst.AppeFormField_Values[i]
								.toString())) {
					log.error("Value is not equals to :"
							.concat(IeFormFieldsConst.AppeFormField_Values[i]
									.toString()));

					retValue = false;
				}
			} else if (ie.selectList(id, IeFormFieldsConst.eFormField_IDs[i])
					.exists())

			{
				boolean isMatched = false;

				for (String str : ie.selectList(id,
						IeFormFieldsConst.eFormField_IDs[i]).getAllContents()) {
					if (str.trim().equals(
							IeFormFieldsConst.AppeFormField_Values[i]
									.toString())) {
						isMatched = true;
						break;
					}
				}

				if (!isMatched) {
					log.error("Value is not equals to :"
							.concat(IeFormFieldsConst.AppeFormField_Values[i]
									.toString()));

					retValue = false;
				}

			}

			else if (ie.table(id, "g3-form:eFormFieldList:8:radioButtonSet")
					.exists()) {
				TableRows rows = ie.table(id,
						"g3-form:eFormFieldList:8:radioButtonSet").rows();

				for (TableRow tableRow : rows) {

					if (tableRow
							.innerText()
							.trim()
							.equalsIgnoreCase(
									IeFormFieldsConst.AppeFormField_Values[i]
											.toString())) {
						state = tableRow.radio(0).getState();
						if (!state == true) {

							retValue = false;

						}

					}

				}

				if (retValue == false) {
					log.error("State is not ture for:"
							.concat(IeFormFieldsConst.AppeFormField_Values[i]
									.toString()));

					retValue = false;
				}

			}

		}

		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateAppSubmissionFieldValue() throws Exception {

		retValue = true;
		IE ie = IEUtil.getActiveIE();

		for (int i = 0; i < IeFormFieldsConst.submissionFormFields_ID.length; i++) {
			if (ie.textField(id, IeFormFieldsConst.submissionFormFields_ID[i])
					.exists()) {

				if (i == 4 || i == 5) {
					if (i == 4) {

						Divs divs = ie.divs();

						for (Div div2 : divs) {

							if (div2.id()
									.equals(IeFormFieldsConst.appProfileFieldDescription_IDs[0])) {
								String text = div2.innerText();

								if (!text
										.equals(IeFormFieldsConst.appSubmissionValidateField_values[i]
												.toString())) {
									log.error("Value is not equals to :"
											.concat(IeFormFieldsConst.appSubmissionValidateField_values[i]
													.toString()));
									retValue = false;
								}
							}
						}
					}

					else {
						Divs divs = ie.divs();

						for (Div div2 : divs) {

							if (div2.id()
									.equals(IeFormFieldsConst.appProfileFieldDescription_IDs[1])) {
								String text = div2.innerText();

								if (!text
										.equals(IeFormFieldsConst.appSubmissionValidateField_values[i]
												.toString())) {
									log.error("Value is not equals to :"
											.concat(IeFormFieldsConst.appSubmissionValidateField_values[i]
													.toString()));
									retValue = false;
								}
							}
						}
					}
				}

				else if (i == 6 || i == 7) {

					if (i == 6)

					{
						Divs divs = ie.divs();

						for (Div div2 : divs) {

							if (div2.id()
									.equals(IeFormFieldsConst.appProfileMandatoryField_IDs[0])) {
								String text = div2.innerText();

								if (!text.equals("* ".concat("Field_7:")
										.toString())) {
									log.error(" Field_7"
											.concat(" is not a Mandatory field"));
									retValue = false;
								}
							}
						}

					}

					else {
						Divs divs = ie.divs();

						for (Div div2 : divs) {

							if (div2.id()
									.equals(IeFormFieldsConst.appProfileMandatoryField_IDs[1])) {
								String text = div2.innerText();

								if (!text.equals("* ".concat("Field_8:")
										.toString())) {
									log.error(" Field_8"
											.concat(" is not a Mandatory field"));
									retValue = false;
								}
							}
						}

					}

				}

				else if (i == 8 || i == 9) {
					boolean isTrue;
					isTrue = GeneralUtil
							.isTextFieldReadOnlyById(IeFormFieldsConst.submissionFormFields_ID[i]);
					if (retValue) {
						retValue = isTrue;
					}

				}

				else if (i == 10 || i == 11) {
					boolean isTrue;
					isTrue = GeneralUtil
							.isTextFieldExistsById(IeFormFieldsConst.submissionFormFields_ID[i]);
					if (retValue) {
						retValue = isTrue;
					}
				}

				else if (!ie
						.textField(id,
								IeFormFieldsConst.submissionFormFields_ID[i])
						.get()
						.equals(IeFormFieldsConst.appSubmissionValidateField_values[i]
								.toString())) {
					log.error("Value is not equals to :"
							.concat(IeFormFieldsConst.appSubmissionValidateField_values[i]
									.toString()));

					retValue = false;
				}
			}

		}

		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateAdminFieldValue() throws Exception {
		retValue = true;
		IE ie = IEUtil.getActiveIE();

		if (!ie.textField(id, IeFormFieldsConst.adminFormfield_ID).get()
				.equals(IeFormFieldsConst.adminFormfield_Values[1].toString())) {
			log.error("Value is not equals to :"
					.concat(IeFormFieldsConst.adminFormfield_Values[1]
							.toString()));

			retValue = false;
		}

		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateBudgetFieldValue() throws Exception {
		retValue = true;
		IE ie = IEUtil.getActiveIE();

		for (int i = 0; i < IeFormFieldsConst.budgetFormField_IDs.length; i++) {
			if (!ie.textField(id, IeFormFieldsConst.budgetFormField_IDs[i])
					.get()
					.equals(IeFormFieldsConst.budgetFormfield_Values[i]
							.toString())) {
				log.error("Value is not equals to :"
						.concat(IeFormFieldsConst.budgetFormfield_Values[i]
								.toString()));

				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateProjectFieldValue() throws Exception {
		retValue = true;
		IE ie = IEUtil.getActiveIE();

		for (int i = 0; i < IeFormFieldsConst.projectFormField_IDs.length; i++) {
			if (!ie.textField(id, IeFormFieldsConst.projectFormField_IDs[i])
					.get()
					.equals(IeFormFieldsConst.projectFormValidatefield_Values[i]
							.toString())) {
				log.error("Value is not equals to :"
						.concat(IeFormFieldsConst.projectFormValidatefield_Values[i]
								.toString()));

				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean validateOrgFieldValue() throws Exception {
		retValue = true;
		IE ie = IEUtil.getActiveIE();

		for (int i = 0; i < IeFormFieldsConst.organizationFormField_IDs.length; i++) {
			if (ie.textField(id, IeFormFieldsConst.organizationFormField_IDs[i])
					.exists()) {
				if (!ie.textField(id,
						IeFormFieldsConst.organizationFormField_IDs[i])
						.get()
						.equals(IeFormFieldsConst.organizationFormField_Value[i]
								.toString())) {
					log.error("Value is not equals to :"
							.concat(IeFormFieldsConst.organizationFormField_Value[i]
									.toString()));

					retValue = false;
				}

			} else if (ie.selectList(id,
					IeFormFieldsConst.organizationFormField_IDs[i]).exists()) {
				boolean isMatched = false;

				for (String str : ie.selectList(id,
						IeFormFieldsConst.organizationFormField_IDs[i])
						.getSelectedItems()) {
					if (str.equals(IeFormFieldsConst.organizationFormField_Value[i]
							.toString())) {
						isMatched = true;
						break;
					}
				}

				if (!isMatched) {
					log.error("Value is not equals to :"
							.concat(IeFormFieldsConst.organizationFormField_Value[i]
									.toString()));

					retValue = false;
				}
			}
		}

		return retValue;
	}

	/**
	 * 
	 * @param expensesAmount
	 * @param cashAmount
	 * @return
	 * @throws Exception
	 */
	public static boolean fillBudgetForm(String expensesAmount,
			String cashAmount) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		if (!tDiv.body(id, IProjectsConst.gps_Budget_DataGrid_TableId).exists()) {
			log.error("Could not find the Table for text fields!");
			return false;
		}

		TableRows rows = tDiv.body(id,
				IProjectsConst.gps_Budget_DataGrid_TableId).rows();

		TableRow row = null;

		for (TableRow tableRow : rows) {

			if (tableRow.innerText().contains("CAPITAL_EQUIPMENT")) {
				row = tableRow;

				for (int x = 1; x < 2; x++) {
					row.cell(x).textField(0).set(expensesAmount);
				}
			} else if (tableRow.innerText().contains("Cash")) {
				row = tableRow;

				for (int x = 1; x < 2; x++) {
					row.cell(x).textField(0).set(cashAmount);
				}
				break;
			}
		}

		return true;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean checkForErrorMessage() throws Exception {

		ClicksUtil.clickLinks("Submission Summary");

		ClicksUtil.clickButtons("Submit");

		if (!GeneralUtil.isButtonDisabled("Submit")) {

			log.error("Could not submit! Post submission function failed");

			return false;
		}
		return true;
	}

}
