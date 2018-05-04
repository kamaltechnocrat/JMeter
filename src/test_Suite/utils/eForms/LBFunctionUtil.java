/**
 * 
 */
package test_Suite.utils.eForms;

import static watij.finders.SymbolFactory.*;
import static watij.finders.SymbolFactory.text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.elements.Divs;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 * 
 */
public class LBFunctionUtil implements ILBFunctionConst {

	private static Log log = LogFactory.getLog(LBFunctionUtil.class);

	private static final int DECORATION_DOLLARSIGN = 0;

	private static final int DECORATION_PERCENTSIGN = 1;

	public static String[] eFormFieldValues;

	public static ArrayList<String[][]> FormletFieldsValues;
	
	public static EDocumentsFormat docFormat;

	private static boolean retValue;

	public static boolean isFOApplicantProfileFilled = false;

	public static boolean isEqualAdminFilled = false;

	public static boolean isLessAdminFilled = false;

	public static boolean isMoreAdminFilled = false;

	public static boolean isNoSyncAdminFilled = false;

	public static boolean isFilteringAdminFilled = false;

	/****************** End of Variables ***********************/

	public static void openApplicantSubmissionList(Project proj) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName(),IFiltersConst.exact);

		TablesUtil.openInTableByImage(ITablesConst.applicantsTableId, proj
				.getOrgFullName(), 0);
	}

	public static LinkedHashMap<EFormletTypes, List<String[]>> initilizeMasterList(
			LBF lbf) throws Exception {
		LinkedHashMap<EFormletTypes, List<String[]>> masterLHM = new LinkedHashMap<EFormletTypes, List<String[]>>();

		for (EFormletTypes type : EFormletTypes.values()) {

			if (type != EFormletTypes.subSummary) {
				masterLHM.put(type, initializeAnyList(lbf, type));
			}
		}
		return masterLHM;
	}
	
	public static LinkedHashMap<EFormletTypes, List<String[]>> initilizeMasterListUpdated(
			LBF lbf) throws Exception {
		LinkedHashMap<EFormletTypes, List<String[]>> masterLHM = new LinkedHashMap<EFormletTypes, List<String[]>>();

		for (EFormletTypes type : EFormletTypes.values()) {

			if (type != EFormletTypes.subSummary) {
				masterLHM.put(type, initializeAnyListAmended(lbf, type));
			}
		}
		return masterLHM;
	}


	public static List<String[]> initializeAnyList(LBF lbf, EFormletTypes type)
			throws Exception {

		List<String[]> lst = new ArrayList<String[]>();

		int arrIndex = lbf.getStartIndex();

		for (int x = 0; x < lbf.getRowsCount(); x++) {

			switch (type) {

			case noProp: {

				lst.add(x, lbf_NoProperties_Fields_eList[arrIndex]);
				break;
			}
			case typeProp: {

				lst.add(x, lbf_TypeProperties_Fields_eList[arrIndex]);
				break;
			}
			case minMaxProp: {

				lst.add(x, lbf_MinMaxProperties_Fields_eList[arrIndex]);
				break;
			}
			case dGrid: {

				lst.add(x, lbf_DataGrid_Fields_eList[arrIndex]);
				break;
			}
			case attchments: {

				lst.add(x, lbf_attachments_Fields_eList[arrIndex]);
				break;
			}
			case subSchedule: {

				lst.add(x, lbf_SubSchedules_Fields_eList[arrIndex]);
				break;

			}
			default:
				break;

			}
			arrIndex++;
		}

		return lst;
	}
	
	public static List<String[]> initializeAnyListAmended(LBF lbf, EFormletTypes type)
			throws Exception {

		List<String[]> lst = new ArrayList<String[]>();

		int arrIndex = lbf.getStartIndex();

		for (int x = 0; x < lbf.getRowsCount(); x++) {

			switch (type) {

			case noProp: {

				lst.add(x, lbf_NoProperties_Fields_eList_Amended[arrIndex]);
				break;
			}
			case typeProp: {

				lst.add(x, lbf_TypeProperties_Fields_eList_Amended[arrIndex]);
				break;
			}
			case minMaxProp: {

				lst.add(x, lbf_MinMaxProperties_Fields_eList_Amended[arrIndex]);
				break;
			}
			case dGrid: {

				lst.add(x, lbf_DataGrid_Fields_eList_Amended[arrIndex]);
				break;
			}
			case attchments: {

			//	lst.add(x, lbf_Attachments_eList_Amended[arrIndex]);
				break;
			}
			case subSchedule: {

				lst.add(x, lbf_SubSchedules_Fields_eList_Amended[arrIndex]);
				break;

			}
			default:
				break;

			}
			arrIndex++;
		}

		return lst;
	}
	
	
	public static List<String[]> initializeAttachment() throws Exception {
		
		List<String[]> attachLst = new ArrayList<String[]>();

		int arrayIndex = 0;

		for (int x = 0; x < 5; x++) {

			attachLst.add(x, lbf_attachments_Fields_eList[arrayIndex]);

			arrayIndex++;
		}

		return attachLst;

	}

	public static boolean addAttachments(Formlet formlet) throws Exception {

		IE ie = IEUtil.getActiveIE();

		List<String[]> lst = initializeAttachment();

		EFormsUtil.expandFormPlannerNode(formlet.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		for (String[] vals : lst) {

			ClicksUtil.clickLinksByTitle("Add Attachment Document");

			ie.textField(id, "/documentIdentifier_rw/").set(vals[0]);

			ie.checkbox(id, "/documentRequired/").set(true);

			ie.selectList(id, "/maximumSize/").select(vals[1]);

			ie.textField(id, "/locales:0:documentType/").set(vals[2]);

			ie.textField(id, "/locales:0:instructions/").set(vals[3]);

			if (ie.textField(id, "/locales:1:documentType/").exists()) {
				ie.textField(id, "/locales:1:documentType/").set(vals[2]);
				ie.textField(id, "/locales:1:instructions/").set(vals[3]);
			}

			if (ie.textField(id, "/locales:2:documentType/").exists()) {
				ie.textField(id, "/locales:2:documentType/").set(vals[2]);
				ie.textField(id, "/locales:2:instructions/").set(vals[3]);
			}

			ie.selectList(id, "/M2M_AvailableItems/").select(vals[4]);

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

		}

		return true;
	}

	public static void addDataGridFormletAndFields(EFormField eFormField)
			throws Exception {

		// Data Grid Numeric Entry Cells//
		// Data Grid Fixed Text Cells//
		// Data Grid Text Entry Cell//
		// Data Grid Dropdown from Lookup Cell

		/***** start adding eFormFields ****/

		eFormField.addeFormField(true, true, true, false);

		eFormField.configureDataGridEFormField("2", "2");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_NumericEntryCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		eFormField.addeFormField(true, true, true, false);

		eFormField.configureDataGridEFormField("2", "2");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_TextEntryCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		eFormField.addeFormField(false, true, false, false);

		eFormField.configureDataGridEFormField("1", "1");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_FixedTextCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		eFormField.addeFormField(true, true, true, false);

		eFormField.configureDataGridEFormField("2", "3");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField
				.setDataGridCellType_DropdownFromLookupCell("Canadian Provinces");

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

	}

	public static boolean initListFormlet(Formlet formlet, Formlet subFormlet,
			int syncType, int formletTypeOrdinal) throws Exception {

		String formletTitle = ILBFunctionConst.formletTypes[formletTypeOrdinal];

		String formletIdent = formletTitle.replace(" ", "-");

		formlet.setFormletTitleText(formletTitle
				+ ILBFunctionConst.formletTitlePostFix);

		formlet.setFormletMenuText(formletTitle
				+ ILBFunctionConst.formletTitlePostFix);

		formlet.setFormletId(formletIdent
				+ ILBFunctionConst.formletIdentPostFix);

		retValue = formlet.createFormlet(true, true);

		subFormlet.setFormletTitleText(formletTitle
				+ ILBFunctionConst.subFormletTitlePostFix);

		subFormlet.setFormletId(formletIdent
				+ ILBFunctionConst.subFormletIdentPostFix);

		subFormlet.setParentFormletId(formlet.getFormletId());

		retValue = subFormlet.createSubFormlet();

		return retValue;
	}

	public static boolean initListFormlet(Formlet formlet, Formlet subFormlet,
			ILBFunctionConst.ESyncTypes syncType,
			ILBFunctionConst.EFormletTypes formletTypeOrdinal) throws Exception {

		String formletTitle = ILBFunctionConst.formletTypes[formletTypeOrdinal
				.ordinal()];

		String formletIdent = formletTitle.replace(" ", "-");

		formlet.setFormletTitleText(formletTitle
				+ ILBFunctionConst.formleTitlePostFix[syncType.ordinal()]);

		formlet.setFormletMenuText(formletTitle
				+ ILBFunctionConst.formleTitlePostFix[syncType.ordinal()]);

		formlet.setFormletId(formletIdent
				+ ILBFunctionConst.formleIdentPostFix[syncType.ordinal()]);

		retValue = formlet.createFormlet(true, true);

		subFormlet.setFormletTitleText(formletTitle
				+ ILBFunctionConst.subFormleTitlePostFix[syncType.ordinal()]);

		subFormlet.setFormletId(formletIdent
				+ ILBFunctionConst.subFormleIdentPostFix[syncType.ordinal()]);

		subFormlet.setParentFormletId(formlet.getFormletId());

		retValue = subFormlet.createSubFormlet();

		return retValue;
	}
	
	public static int verifyFormletAlreadyFilled() throws Exception {	
		
		if (GeneralUtil.isLinkExistsByTxt(formletTypes[EFormletTypes.noProp.ordinal()] + formletTitlePostFix)) {

			ClicksUtil.clickLinks(formletTypes[EFormletTypes.noProp.ordinal()] + formletTitlePostFix);
			
			return TablesUtil.howManyEntriesInTable(ILBFunctionConst.lbf_FormletList_Table_Id);
		}

		return -1;

	}

	public static int verifyRowsEntriesInEList() throws Exception {

		return TablesUtil
				.howManyEntriesInTable(ILBFunctionConst.lbf_FormletList_Table_Id);
	}

	public static int verifyRowsEntriesInEList(
			LinkedHashMap<String, EfieldDataTypes> lhm) throws Exception {

		return TablesUtil
				.howManyEntriesInTable(ILBFunctionConst.lbf_FormletList_Table_Id);
	}

	public static int verifyRowsEntriesInAttachmentList() throws Exception {

		ClicksUtil.clickLinks(formletTypes[EFormletTypes.attchments.ordinal()]
				+ formletTitlePostFix);

		return TablesUtil
				.testHowManyEntriesInAttachmentList(ILBFunctionConst.lbf_AttachmentList_Table_Id);
	}

	public static boolean verifyFieldsAnswer(LBF lbf) throws Exception {

		if (GeneralUtil.isLinkExistsByTxt(formletTypes[lbf.getEFormletsName()
				.ordinal()]
				+ formletTitlePostFix)) {

			ClicksUtil
					.clickLinks(formletTypes[lbf.getEFormletsName().ordinal()]
							+ formletTitlePostFix);

			switch (lbf.getEFormletsName()) {

			case noProp: {

				return verify_NoPropFieldsAnswers(initializeAnyList(lbf, lbf.getEFormletsName()));
			}

			case typeProp: {

				return verify_TypePropFieldsAnswers(initializeAnyList(lbf, lbf.getEFormletsName()));
			}

			case minMaxProp: {

				return verify_MinMaxPropFieldsAnswers(initializeAnyList(lbf, lbf.getEFormletsName()));
			}

			case dGrid: {

				return verify_DataGridListFieldsAnswers(initializeAnyList(lbf, lbf.getEFormletsName()));
			}

			case attchments: {

			return verify_AttachmentsFieldsAnswers(initializeAnyListAmended(lbf, lbf.getEFormletsName()));
			}

			case subSchedule: {

				return verify_SchedulesFieldsAnswers("",initializeAnyList(lbf, lbf.getEFormletsName()));

			}
			default:
				break;

			}
		}

		return false;
	}
	
	
	
	
	
	public static boolean verifyFieldsAnswerAmended(LBF lbf) throws Exception {

		if (GeneralUtil.isLinkExistsByTxt(formletTypes[lbf.getEFormletsName()
				.ordinal()]
				+ formletTitlePostFix)) {

			ClicksUtil
					.clickLinks(formletTypes[lbf.getEFormletsName().ordinal()]
							+ formletTitlePostFix);

			switch (lbf.getEFormletsName()) {

			case noProp: {
				
			

				return verify_NoPropFieldsAnswers(initializeAnyListAmended(lbf, lbf.getEFormletsName()));
			}

			case typeProp: {

			
				
				return verify_TypePropFieldsAnswers(initializeAnyListAmended(lbf, lbf.getEFormletsName()));
			}

			case minMaxProp: {
				
				

				return verify_MinMaxPropFieldsAnswers(initializeAnyListAmended(lbf, lbf.getEFormletsName()));
			}

			case dGrid: {
				
			

				return verify_DataGridListFieldsAnswers(initializeAnyListAmended(lbf, lbf.getEFormletsName()));
			}

			case attchments: {

				return verify_AttachmentsFieldsAnswers(initializeAnyListAmended(lbf, lbf.getEFormletsName()));
			}

			case subSchedule: {

				return verify_SchedulesFieldsAnswers("",initializeAnyListAmended(lbf, lbf.getEFormletsName()));

			}
			default:
				break;

			}
		}

		return false;
	}

	public static boolean verifyRowsCount(LBF lbf) throws Exception {

		List<String[]> lst = null;
		
		if (GeneralUtil.isLinkExistsByTxt(formletTypes[lbf.getEFormletsName()
				.ordinal()]
				+ formletTitlePostFix)) {

			ClicksUtil
					.clickLinks(formletTypes[lbf.getEFormletsName().ordinal()]
							+ formletTitlePostFix);
			switch (lbf.getEFormletsName()) {

			case noProp: {
				
				lst = initializeAnyList(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case typeProp: {
				
				lst = initializeAnyList(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case minMaxProp: {
				
				lst = initializeAnyList(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case dGrid: {
				
				lst = initializeAnyList(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case attchments: {
				
				lst = initializeAnyList(lbf, lbf.getEFormletsName());
				
				log.debug("Admin Form Att size: " + lst.size());

				return (verifyRowsEntriesInAttachmentList() == lst.size());

			}

			case subSchedule: {
				
				lst = initializeAnyList(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}
			default:
				break;

			}
		}

		return false;
	}

	
	public static boolean verifyRowsCountAmended(LBF lbf) throws Exception {

		List<String[]> lst = null;
		
		if (GeneralUtil.isLinkExistsByTxt(formletTypes[lbf.getEFormletsName()
				.ordinal()]
				+ formletTitlePostFix)) {

			ClicksUtil
					.clickLinks(formletTypes[lbf.getEFormletsName().ordinal()]
							+ formletTitlePostFix);
			switch (lbf.getEFormletsName()) {

			case noProp: {
				
				lst = initializeAnyListAmended(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case typeProp: {
				
				lst = initializeAnyListAmended(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case minMaxProp: {
				
				lst = initializeAnyListAmended(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case dGrid: {
				
				lst = initializeAnyListAmended(lbf, lbf.getEFormletsName());

				return (verifyRowsEntriesInEList() == lst.size());

			}

			case attchments: {
				
//				lst = initializeAnyListAmended(lbf, lbf.getEFormletsName());
//				
//				log.debug("Admin Form Att size: " + lst.size());
//
//				return (verifyRowsEntriesInAttachmentList() == lst.size());

			}

			case subSchedule: {
				
//				lst = initializeAnyListAmended(lbf, lbf.getEFormletsName());
//
//				return (verifyRowsEntriesInEList() == lst.size());

			}
			default:
				break;

			}
		}

		return false;
	}

	
	
	public static boolean verify_NoPropFieldsAnswers(List<String[]> lst) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();
		
		int start = 0;

		for (String[] arr : lst) {
			
			GeneralUtil.takeANap(1.5);

			tDiv.body(id,lbf_FormletList_TableBody_Id).row(start).link(1).click();
				
				if(!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_NoProp_Approval_DropdownId).equals(arr[0]))
			{
				log.error("Could Not find: ".concat(arr[0]));
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				return false;
			}

			if (!(ie.checkbox(id, lbf_NoProp_Checkbox_FieldId).checked() == (Boolean.parseBoolean(arr[1])))) 
			{
				log.error("Could Not find: ".concat(arr[1]));
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				return false;
			}

			if (!ie.textField(id, lbf_NoProp_Date_FieldId).get().equals((GeneralUtil.getTodayDate()))) 
			{
				log.error("Could Not find: ".concat(GeneralUtil.getTodayDate()));
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				return false;
				
			}

			if (!ie.textField(id, lbf_NoProp_EINumber_FieldId).get().equals(arr[3])) 
			{
				log.error("Could Not find: ".concat(arr[3]));
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				return false;
			}

			if (!ie.textField(id, lbf_NoProp_EmailAddress_FieldId).get().equals(arr[4])) 
			{
				log.error("Could Not find: ".concat(arr[4]));
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				return false;
			}

			if (!ie.textField(id, lbf_NoProp_WebAddress_FieldId).get().equals(arr[5])) 
			{
				log.error("Could Not find: ".concat(arr[5]));
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			start++;
		}

		return true;

	}
	
	
	public static boolean verify_ApplicantSubmissionFields(String[] arr) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if (!ie.textField(id,ILBFunctionConst.equipmentTxtBox_Id).get().equals(arr[0])) 
		{
			log.error("Could Not find: ".concat(arr[0]));
			
			ClicksUtil.clickButtons(IClicksConst.backToSubmissionListBtn);
			
			return false;
		}
		
		
		if (!ie.textField(id,ILBFunctionConst.operationalTxtBox_Id).get().equals(arr[1])) 
		{
			log.error("Could Not find: ".concat(arr[1]));
			
			ClicksUtil.clickButtons(IClicksConst.backToSubmissionListBtn);
			
			return false;
		}
		
		
		return true;
	
	}
	

	public static boolean verify_TypePropFieldsAnswers(List<String[]> lst) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();
		
		int start = 0;

		for (String[] arr : lst) {

			tDiv.body(id,lbf_FormletList_TableBody_Id).row(start).link(1).click();

			if (!ie.textField(id, lbf_TypeProp_PhoneNumber_FieldId).get()
					.equals(arr[0])) {
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!ie.textField(id, lbf_TypeProp_PostalCode_FieldId).get()
					.equals(arr[1])) {
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			start++;
		}
		return true;
	}

	public static boolean verify_MinMaxPropFieldsAnswers(List<String[]> lst) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		int start = 0;

		for (String[] arr : lst) {

			tDiv.body(id,lbf_FormletList_TableBody_Id).row(start).link(1).click();

			if (!ie.textField(id, lbf_MinMaxProp_LongText_FieldId).get()
					.equals(
							EFormsUtil.createRandomString(arr[0], Integer
									.parseInt(arr[1])))) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}
			
			if (!ie.textField(id, lbf_MinMaxProp_Numeric_FieldId).get().equals(
					formatNumber(Integer.parseInt(arr[1]), 4,
							DECORATION_DOLLARSIGN, false))) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!ie.textField(id, lbf_MinMaxProp_ShortText_FieldId).get()
					.equals(EFormsUtil.createRandomString(arr[3], 1500))) {
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!ie.textField(id, lbf_MinMaxProp_ReviewScore_FieldId).get()
					.equals(arr[4])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			start++;
		}

		return true;
	}

	public static boolean verify_DataGridListFieldsAnswers(List<String[]> lst) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		int start = 0;

		for (String[] arr : lst) {

			String frmt = formatNumber(Integer.parseInt(arr[0]), 2, DECORATION_DOLLARSIGN, false);
			
			tDiv.body(id,lbf_FormletList_TableBody_Id).row(start).link(1).click();

			if (!ie.textField(id, lbf_DataGrids_NumericCell_A1_Id).get().equals(frmt)) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_NumericCell_B2_Id).get().equals(frmt)) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_NumericCell_C3_Id).get().equals(frmt)) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_TextCell_A1_Id).get().equals(arr[1])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_TextCell_B2_Id).get().equals(arr[1])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_TextCell_C3_Id).get().equals(arr[1])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}
				
				if(!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_DataGrids_DropdownCell_A1_Id).equals(arr[2])){

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}
				
				if(!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_DataGrids_DropdownCell_B2_Id).equals(arr[2])){

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}
			
			if(!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_DataGrids_DropdownCell_C3_Id).equals(arr[2]))
			{

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			start++;
		}

		return true;

	}

	public static boolean verify_AttachmentsFieldsAnswers(List<String[]> lst) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		for (String[] arr : lst) {

			tDiv.body(id, lbf_AttachmentList_TableBody_Id).link(text,arr[0]).click();
			
			if(!ie.div(id,IClicksConst.divAttrb_Id).exists())
			{
				log.error("Could not find form element in Attchment Details!");
				return false;
			}
			
			Divs divs = ie.div(id,IClicksConst.divAttrb_Id).divs();
			
			Div div2 = null;

			for (Div div : divs) {

				if (div.innerText().startsWith("File Name")) {
					div2 = div;
					break;
				}
			}

			if (!ie.textField(id, lbf_AttachmentDetails_DocDescription_FieldId)
					.get().equals(arr[2])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}
			
			Divs divs2 = div2.divs().div(1).divs();
				
			log.warn(divs2.div(2).innerText());
			
			if(!divs2.div(2).innerText().endsWith(arr[5]))
			{
				
				log.error("No Matching for: ".concat(arr[5]));
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
				
			}
		
			
			divs2 = div2.divs().div(4).divs();
				
			log.warn(divs2.div(2).innerText());
			
			if(!divs2.div(2).innerText().startsWith(arr[0]))
			{
				
				log.error("No Matching for: ".concat(arr[0]));
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
				
			}
			
			divs2 = div2.divs().div(7).divs();
				
			log.warn(divs2.div(2).innerText());
			
			if(!divs2.div(2).innerText().startsWith(arr[1]))
			{
				
				log.error("No Matching for: ".concat(arr[1]));
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
				
			}
			
			divs2 = div2.divs().div(10).divs();
			
			log.warn(divs2.length());
				
			log.warn(divs2.div(2).innerText());
			
			if(!divs2.div(2).innerText().startsWith(arr[4]))
			{
				
				log.error("No Matching for: ".concat(arr[4]));
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
				
			}
			
			divs2 = div2.divs().div(13).divs();
				
			log.warn(divs2.div(2).innerText());
			
			if(!divs2.div(2).innerText().startsWith(arr[3]))
			{
				
				log.error("No Matching for: ".concat(arr[3]));
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
				
			}
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		}

		return true;

	}

	public static boolean verify_SchedulesFieldsAnswers(String ipasForm,List<String[]> lst)
			throws Exception {
		
		Div tDiv = TablesUtil.tableDiv();

		int start = 0;

		for (String[] arr : lst) {
			
			GeneralUtil.takeANap(1.5);

			tDiv.body(id,lbf_FormletList_TableBody_Id).row(start).link(title,"View this list item").click();

			if (!(GeneralUtil.getTextFieldContentsByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl).equals(arr[0])))
			{
				log.error("Submission Names are not Equal");
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!(GeneralUtil.getTextFieldContentsByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl).equals(GeneralUtil.getTodayDate())))
			{
				log.error("Start Dates are Not Equal");
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!(GeneralUtil.getTextFieldContentsByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl).equals(GeneralUtil.getNextYear())))
			{
				log.error("End Dates are not equal!");
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			// For non Award eForm
			if (!ipasForm.contains(""))
			{
				if(!(GeneralUtil.getSelectedValueInDropDownByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl).equals(ipasForm)))
				{
					log.error("Submission Forms are not Equal!");
					ClicksUtil.clickButtons(IClicksConst.backToListBtn);
					return false;
				}

			}

			if (!(GeneralUtil.isCheckboxCheckedByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl) == (Boolean.parseBoolean(arr[4]))))
			{
				log.warn(GeneralUtil.isCheckboxCheckedByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl));
				log.warn(arr[4]);
				log.error("Required checkbox is not equal!");
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!(GeneralUtil.isCheckboxCheckedByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl) == (Boolean.parseBoolean(arr[5]))))
			{
				log.warn(GeneralUtil.isCheckboxCheckedByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl));
				log.warn(arr[5]);
				log.error("PO Only are not equal!");
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			start++;
		}

		return true;
	}

	public static void deleteRowFromSchedulesList(LBF lbf)
			throws Exception {
		
		LinkedHashMap<EFormletTypes, List<String[]>> ml = initilizeMasterList(lbf);

		Set<Map.Entry<EFormletTypes, List<String[]>>> setData = ml.entrySet();

		for (Map.Entry<EFormletTypes, List<String[]>> entry : setData) {

			if (entry.getKey() == EFormletTypes.subSchedule) {
				if (GeneralUtil.isLinkExistsByTxt(formletTypes[entry.getKey()
						.ordinal()]
						+ formletTitlePostFix)) {

					ClicksUtil
							.clickLinks(formletTypes[entry.getKey().ordinal()]
									+ formletTitlePostFix);

					deleteRows(lbf.getDeleteRowIndex());

					return;
				}
			}
		}

	}

	public static void deleteRowFromAttachmentList(int rowIndex)
			throws Exception {

		ClicksUtil.clickLinks(formletTypes[EFormletTypes.attchments.ordinal()]
				+ formletTitlePostFix);

		deleteRows(rowIndex);

	}

	public static void deleteRowFromLists(LBF lbf) throws Exception {
		
		LinkedHashMap<EFormletTypes, List<String[]>> ml = initilizeMasterList(lbf);
		Set<Map.Entry<EFormletTypes, List<String[]>>> setData = ml.entrySet();

		for (Map.Entry<EFormletTypes, List<String[]>> entry : setData) {

			if (GeneralUtil.isLinkExistsByTxt(formletTypes[entry.getKey()
					.ordinal()]
					+ formletTitlePostFix)) {

				ClicksUtil.clickLinks(formletTypes[entry.getKey().ordinal()]
						+ formletTitlePostFix);

				switch (entry.getKey()) {

				case noProp: {
					deleteRows(lbf.getDeleteRowIndex());
					break;
				}

				case typeProp: {
					deleteRows(lbf.getDeleteRowIndex());
					break;
				}

				case minMaxProp: {
					deleteRows(lbf.getDeleteRowIndex());
					break;
				}

				case dGrid: {
					deleteRows(lbf.getDeleteRowIndex());
					break;
				}

				case attchments: {
							
						deleteRows(getAttachmentIndex(entry.getValue().get(1)[0]));

					break;
				}

				case subSchedule: {
					if (lbf.getEFormType() == EeFormsIdentifier.admin
							|| lbf.getEFormType() == EeFormsIdentifier.standardAgreement
							|| lbf.getEFormType() == EeFormsIdentifier.poSubmission) {
						deleteRows(lbf.getDeleteRowIndex());
					}

					break;
				}
				default:
					break;

				}

			}
		}
	}
	
	public static Integer getAttachmentIndex(String attachType) throws Exception {
		
		Integer idx= -1;
		
		idx = TablesUtil.getRowIndex(lbf_AttachmentList_Table_Id, attachType);
		
		return idx;
	}

	public static void deleteRowFromELists(LBF lbf) throws Exception {
		
		LinkedHashMap<EFormletTypes, List<String[]>> ml = initilizeMasterList(lbf); 

		Set<Map.Entry<EFormletTypes, List<String[]>>> setData = ml.entrySet();

		for (Map.Entry<EFormletTypes, List<String[]>> entry : setData) {
			if (GeneralUtil.isLinkExistsByTxt(formletTypes[entry.getKey()
					.ordinal()]
					+ formletTitlePostFix)) {

				ClicksUtil.clickLinks(formletTypes[entry.getKey().ordinal()]
						+ formletTitlePostFix);

				deleteRows(lbf.getDeleteRowIndex());

				break;
			}
		}
	}

	public static void deleteRows(int rowIndex) throws Exception {
		
		Div tDiv = TablesUtil.tableDiv();
		
		log.debug("started dialog clicker thread");
		
		if(tDiv.body(id, lbf_AttachmentList_Table_Id).exists())
		{
			tDiv.body(id,lbf_AttachmentList_TableBody_Id).row(rowIndex).image(0).click();
		}
		else if(tDiv.body(id, lbf_FormletList_Table_Id).exists())
		{
			tDiv.body(id,lbf_FormletList_TableBody_Id).row(rowIndex).image(0).click();
		}
		else
		{
			log.error("Could not find the List to delete from!");
		}	
		
		GeneralUtil.takeANap(1.000);	
		
		ClicksUtil.clickButtons("Ok");
	}

	public static void insertDataTo_ELists(LBF lbf) throws Exception {

		LinkedHashMap<EFormletTypes, List<String[]>> ml = initilizeMasterList(lbf);

		Set<Map.Entry<EFormletTypes, List<String[]>>> setData = ml.entrySet();

		for (Map.Entry<EFormletTypes, List<String[]>> entry : setData) {

			log.warn(formletTypes[entry.getKey()
					.ordinal()]
					+ formletTitlePostFix);
			
			if (GeneralUtil.isLinkExistsByTxt(formletTypes[entry.getKey().ordinal()] + formletTitlePostFix)) {

				ClicksUtil.clickLinks(formletTypes[entry.getKey().ordinal()] + formletTitlePostFix);

				switch (entry.getKey()) {

				case noProp: {

					insertTo_NoPropListFormlet(entry);

					break;
				}

				case typeProp: {

					insertTo_TypePropListFormlet(entry);

					break;
				}

				case minMaxProp: {

					insertTo_MinMaxPropListFormlet(entry);

					break;
				}

				case dGrid: {

					insertTo_DataGridListFormlet(entry);

					break;
				}

				case subSchedule: {

					insertTo_SubScheduleListFormlet1("", entry);
					break;

				}

			    case attchments: {
					
					upload_Attachments(entry);
				}

				default: {

					break;
				}

				}
			}
		}
		
		ClicksUtil.clickLinks("/Summary/");
		
		if(GeneralUtil.isButtonExistsByValue(IClicksConst.completeBtn))
		{
			if(GeneralUtil.isButtonEnabled(IClicksConst.completeBtn))
			{
				ClicksUtil.clickButtons(IClicksConst.completeBtn);
			}
		}
		return;
	}

	public static void updateDataTo_ELists(LBF lbf) throws Exception {

		LinkedHashMap<EFormletTypes, List<String[]>> ml = initilizeMasterListUpdated(lbf);

		Set<Map.Entry<EFormletTypes, List<String[]>>> setData = ml.entrySet();

		for (Map.Entry<EFormletTypes, List<String[]>> entry : setData) {

			log.warn(formletTypes[entry.getKey()
					.ordinal()]
					+ formletTitlePostFix);
			
			if (GeneralUtil.isLinkExistsByTxt(formletTypes[entry.getKey().ordinal()] + formletTitlePostFix)) {

				ClicksUtil.clickLinks(formletTypes[entry.getKey().ordinal()] + formletTitlePostFix);

				switch (entry.getKey()) {

				case noProp: {

					
					update_NoPropListFormlet(entry);

					break;
				}

				case typeProp: {

					update_TypePropListFormlet(entry);

					break;
				}

				case minMaxProp: {

					update_MinMaxPropListFormlet(entry);

					break;
				}

				case dGrid: {

					update_DataGridListFormlet(entry);

					break;
				}

				case subSchedule: {

					update_SubScheduleListFormlet1("", entry);
					break;

				}

				case attchments: {
					
					//update_Attachments(entry);
				}

				default: {

					break;
				}

				}
			}
		}
		
		ClicksUtil.clickLinks("/Summary/");
		if(GeneralUtil.isButtonExistsByValue(IClicksConst.completeBtn))
		{
			if(GeneralUtil.isButtonEnabled(IClicksConst.completeBtn))
			{
				ClicksUtil.clickButtons(IClicksConst.completeBtn);
			}
		}
		return;
	}
	
	
	public static void insertTo_NoPropListFormlet(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (String[] arr : entry.getValue()) {
			
			GeneralUtil.selectFullStringInDropdownListByTitle(lbf_NoProp_Approval_DropdownTtl, arr[0]);
			
			GeneralUtil.toggleCheckBoxByTitle(lbf_NoProp_Checkbox_FieldTtl, Boolean.parseBoolean(arr[1]));
			
			GeneralUtil.setTextByTitle(lbf_NoProp_Date_FieldTtl, GeneralUtil.getTodayDate());
			
			GeneralUtil.setTextByTitle(lbf_NoProp_EINumber_FieldTtl, arr[3]);
			
			GeneralUtil.setTextByTitle(lbf_NoProp_EmailAddress_FieldTtl, arr[4]);
			
			GeneralUtil.setTextByTitle(lbf_NoProp_WebAddress_FieldTtl, arr[5]);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

		}
		
	
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

	}
	
	
	public static boolean update_NoPropListFormlet(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {
		
		int index = TablesUtil.getRowIndex(ITablesConst.eFormListDataWithLetters,entry.getValue().get(0)[3] );
		
		if(index < 0)
		{
			log.error("Can't find the index ");
			return false;
		}
		
		
		if(!TablesUtil.openInTableByImageLinkAndIndex(ITablesConst.eFormListDataWithLetters, index, 1,0))
		{
			log.error("Can't find the image");
			return false;
		}
		
			
		for (String[] arr : entry.getValue()) {

		
		
            GeneralUtil.selectFullStringInDropdownListByTitle(lbf_NoProp_Approval_DropdownTtl,arr[0] );
			
			GeneralUtil.toggleCheckBoxByTitle(lbf_NoProp_Checkbox_FieldTtl, Boolean.parseBoolean(arr[1]));
			
			GeneralUtil.setTextByTitle(lbf_NoProp_Date_FieldTtl, GeneralUtil.getTodayDate());
			
			GeneralUtil.setTextByTitle(lbf_NoProp_EINumber_FieldTtl, arr[3]);
			
			GeneralUtil.setTextByTitle(lbf_NoProp_EmailAddress_FieldTtl, arr[4]);
			
			GeneralUtil.setTextByTitle(lbf_NoProp_WebAddress_FieldTtl, arr[5]);
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		}
		     
		    return true;
		    
		    }
		
		
	public static void insertTo_TypePropListFormlet(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (String[] arr : entry.getValue()) {
			
			GeneralUtil.setTextByTitle(lbf_TypeProp_PhoneNumber_FieldTtl, arr[0]);
			
			GeneralUtil.setTextByTitle(lbf_TypeProp_PostalCode_FieldTtl, arr[1]);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

		}

		
		
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

	}
	
	public static boolean update_TypePropListFormlet(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		
        int index = TablesUtil.getRowIndex(ITablesConst.eFormListDataWithLetters,entry.getValue().get(0)[1] );
		
		if(index < 0)
		{
			log.error("Can't find the index ");
			return false;
		}
		
		
		if(!TablesUtil.openInTableByImageLinkAndIndex(ITablesConst.eFormListDataWithLetters, index, 1,0))
		{
			log.error("Can't find the image");
			return false;
		}

		for (String[] arr : entry.getValue()) {
			
			GeneralUtil.setTextByTitle(lbf_TypeProp_PhoneNumber_FieldTtl, arr[0]);
			
			GeneralUtil.setTextByTitle(lbf_TypeProp_PostalCode_FieldTtl, arr[1]);

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		}
	
	    return true;
	    
	    }
	

	public static void insertTo_MinMaxPropListFormlet(Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (String[] arr : entry.getValue()) {
			
			ie.textField(id, lbf_MinMaxProp_LongText_FieldId).set(
					
					EFormsUtil.createRandomString(arr[0], Integer
							
							.parseInt(arr[1])));
			
			GeneralUtil.setTextById(lbf_MinMaxProp_Numeric_FieldId, arr[1]);
			
			GeneralUtil.setTextById(lbf_MinMaxProp_Password_FieldId, arr[2]);
			
			
			GeneralUtil.setTextById(lbf_MinMaxProp_ShortText_FieldId, EFormsUtil.createRandomString(arr[3], 1500));
			
			GeneralUtil.setTextById(lbf_MinMaxProp_ReviewScore_FieldId, arr[4]);
			
			GeneralUtil.takeANap(1.0);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
		}	
		
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
	}
	
	public static boolean update_MinMaxPropListFormlet(Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();

		 int index = TablesUtil.getRowIndex(ITablesConst.eFormListDataWithLetters,entry.getValue().get(0)[1] );
			
			if(index < 0)
			{
				log.error("Can't find the index ");
				return false;
			}
			
			
			if(!TablesUtil.openInTableByImageLinkAndIndex(ITablesConst.eFormListDataWithLetters, index, 1,0))
			{
				log.error("Can't find the image");
				return false;
			}


		for (String[] arr : entry.getValue()) {
			
			ie.textField(id, lbf_MinMaxProp_LongText_FieldId).set(
					
					EFormsUtil.createRandomString(arr[0], Integer
							
							.parseInt(arr[1])));
			
			GeneralUtil.setTextById(lbf_MinMaxProp_Numeric_FieldId, arr[1]);
			
			GeneralUtil.setTextById(lbf_MinMaxProp_Password_FieldId, arr[2]);
			
			GeneralUtil.setTextById(lbf_MinMaxProp_ShortText_FieldId, EFormsUtil.createRandomString(arr[3], 1500));
			
			GeneralUtil.setTextById(lbf_MinMaxProp_ReviewScore_FieldId, arr[4]);
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
			
			ClicksUtil.clickButtons(IClicksConst.nextBtn);

		}
		
		
	
	    return true;
	    
	    }
	
	public static void insertTo_DataGridListFormlet(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickImage(IClicksConst.newImg);
		
		GeneralUtil.takeANap(0.5);

		for (String[] arr : entry.getValue()) {
			
			ie.selectList(id, lbf_DataGrids_DropdownCell_A1_Id).select(arr[2]);
			
			ie.selectList(id, lbf_DataGrids_DropdownCell_B2_Id).select(arr[2]);
			
			ie.selectList(id, lbf_DataGrids_DropdownCell_C3_Id).select(arr[2]);
			
			GeneralUtil.takeANap(0.5);
			
			ie.textField(id, lbf_DataGrids_NumericCell_A1_Id).set(arr[0]);	
			
			ie.textField(id, lbf_DataGrids_NumericCell_B2_Id).set(arr[0]);	
			
			ie.textField(id, lbf_DataGrids_NumericCell_C3_Id).set(arr[0]);
			
			GeneralUtil.takeANap(0.5);
			
			ie.textField(id, lbf_DataGrids_TextCell_A1_Id).set(arr[1]);
			
			ie.textField(id, lbf_DataGrids_TextCell_B2_Id).set(arr[1]);
			
			ie.textField(id, lbf_DataGrids_TextCell_C3_Id).set(arr[1]);
			
			GeneralUtil.takeANap(1.0);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);			
			
		}
		
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
	}
	
	public static boolean update_DataGridListFormlet(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();

		int index = TablesUtil.getRowIndex(ITablesConst.eFormListDataWithLetters,entry.getValue().get(0)[1] );
		
		if(index < 0)
		{
			log.error("Can't find the index ");
			return false;
		}
		
		
		if(!TablesUtil.openInTableByImageLinkAndIndex(ITablesConst.eFormListDataWithLetters, index, 1, 0))
		{
			log.error("Can't find the image");
			return false;
		}

		for (String[] arr : entry.getValue()) {
			
			ie.textField(id, lbf_DataGrids_NumericCell_A1_Id).set(arr[0]);	
			
			ie.textField(id, lbf_DataGrids_NumericCell_B2_Id).set(arr[0]);	
			
			ie.textField(id, lbf_DataGrids_NumericCell_C3_Id).set(arr[0]);
			
			ie.textField(id, lbf_DataGrids_TextCell_A1_Id).set(arr[1]);
			
			ie.textField(id, lbf_DataGrids_TextCell_B2_Id).set(arr[1]);
			
			ie.textField(id, lbf_DataGrids_TextCell_C3_Id).set(arr[1]);
			
//			GeneralUtil.selectFullStringInDropdownList(lbf_DataGrids_DropdownCell_A1_Id,arr[2]);
//			GeneralUtil.selectFullStringInDropdownList(lbf_DataGrids_DropdownCell_B2_Id,arr[2]);
//			GeneralUtil.selectFullStringInDropdownList(lbf_DataGrids_DropdownCell_C3_Id,arr[2]);
			
			ie.selectList(id, lbf_DataGrids_DropdownCell_A1_Id).select(arr[2]);
			
			ie.selectList(id, lbf_DataGrids_DropdownCell_B2_Id).select(arr[2]);
			
			ie.selectList(id, lbf_DataGrids_DropdownCell_C3_Id).select(arr[2]);

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		}
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
	
	    return true;
	    
	    }

	public static void insertTo_SubScheduleListFormlet(String ipasForm, LBF lbf)
			throws Exception {
		
		LinkedHashMap<EFormletTypes, List<String[]>> ml = initilizeMasterList(lbf);

		Set<Map.Entry<EFormletTypes, List<String[]>>> setData = ml.entrySet();
		
		for (Map.Entry<EFormletTypes, List<String[]>> entry : setData) {

			if (entry.getKey() == EFormletTypes.subSchedule) {
				if (GeneralUtil.isLinkExistsByTxt(formletTypes[entry.getKey()
						.ordinal()]
						+ formletTitlePostFix)) {

					ClicksUtil
							.clickLinks(formletTypes[entry.getKey().ordinal()]
									+ formletTitlePostFix);

					insertTo_SubScheduleListFormlet1("", entry);
					return;
				}
			}
		}
	}
	
	

	public static void insertTo_SubScheduleListFormlet1(String ipasForm,
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {
		
		//

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (String[] arr : entry.getValue()) {
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,arr[0]),"FAIL: could not set Claim Name!");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, GeneralUtil.getTodayDate()), "FAIL: Could not set the Start Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, GeneralUtil.getNextYear()),"FAIL: Could not set Due Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()),"FIAL: Could not set End Date");
			
			// For non Award eForm
			if (!ipasForm.contains(""))
			{				
				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, ipasForm),"FAIL: Could not select PA Submission Form!");

			}			

			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl,Boolean.parseBoolean(arr[4])), "FAIL: Could not set Required checkbox!");
			
			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, Boolean.parseBoolean(arr[5])), "FAIL: Could not set Program Office Only checkbox!");

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
		}
		

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

	}

	
	public static void update_SubScheduleListFormlet1(String ipasForm,
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {
		
		//

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (String[] arr : entry.getValue()) {
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,arr[0]),"FAIL: could not set Claim Name!");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, GeneralUtil.getTodayDate()), "FAIL: Could not set the Start Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, GeneralUtil.getNextYear()),"FAIL: Could not set Due Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()),"FIAL: Could not set End Date");
			
			// For non Award eForm
			if (!ipasForm.contains(""))
			{				
				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, ipasForm),"FAIL: Could not select PA Submission Form!");

			}			

			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl,Boolean.parseBoolean(arr[4])), "FAIL: Could not set Required checkbox!");
			
			Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, Boolean.parseBoolean(arr[5])), "FAIL: Could not set Program Office Only checkbox!");

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

	}
	public static void upload_Attachments(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		for (String[] arr : entry.getValue()) {
			
			tDiv = TablesUtil.tableDiv();
			
			if(tDiv.body(id,lbf_FormletList_Table_Id).exists())
			{
				tDiv.body(id, lbf_FormletList_Table_Id).link(text, arr[0]).click();
			}
			else if(tDiv.body(id,lbf_AttachmentList_Table_Id).exists())
			{
				tDiv.body(id, lbf_AttachmentList_Table_Id).link(text, arr[0]).click();
			}
			else
			{
				log.error("Could not find the Attachment List!");
				return;
			}			

			ie.textField(id, lbf_AttachmentDetails_DocDescription_FieldId).set(
					arr[2]);

			ie.fileField(id, lbf_AttachmentDetails_FileUpload_FieldId).set(
					"\"" + GeneralUtil.getWorkspacePath() + lbf_DocsFilesPath
							+ arr[5] + "\"");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		}

	}
	
	public static boolean update_Attachments(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		for (String[] arr : entry.getValue()) {
			
			tDiv = TablesUtil.tableDiv();
			
			if(tDiv.body(id,lbf_FormletList_Table_Id).exists())
			{
				tDiv.body(id, lbf_FormletList_Table_Id).link(text, arr[0]).click();
			}
			else if(tDiv.body(id,lbf_AttachmentList_Table_Id).exists())
			{
				tDiv.body(id, lbf_AttachmentList_Table_Id).link(text, arr[0]).click();
			}
			else
			{
				log.error("Could not find the Attachment List!");
				return false;
			}			

			ie.textField(id, lbf_AttachmentDetails_DocDescription_FieldId).set(
					arr[2]);

			ie.fileField(id, lbf_AttachmentDetails_FileUpload_FieldId).set(
					"\"" + GeneralUtil.getWorkspacePath() + lbf_DocsFilesPath
							+ arr[5] + "\"");

			ClicksUtil.clickButtons(IClicksConst.submissionSummaryFieldsLnk);

		}
	
	    return true;
	    
	    }


	/*
	 * public static boolean download_Attachments() throws Exception {
	 * 
	 * IE ie = IEUtil.getActiveIE();
	 * 
	 * set = attachmentHT.entrySet();
	 * 
	 * ClicksUtil.clickLinks(formletTypes[EFormletTypes.attchments.ordinal()] +
	 * formletTitlePostFix);
	 * 
	 * int start = 0;
	 * 
	 * for (String[] arr : attachmentDataLst) {
	 * 
	 * ie.table(id, lbf_FormletList_Table_Id).row(start).link(1).click();
	 * 
	 * if (!ie.textField(id, lbf_AttachmentDetails_DocDescription_FieldId)
	 * .get().equals(arr[2])) {
	 * 
	 * ClicksUtil.clickButtons(IClicksConst.backToListBtn); return false;
	 * 
	 * }
	 * 
	 * if (!ie.span(attribute("class", "mainTable")).row(0).cell(1)
	 * .innerText().equals(arr[5])) {
	 * 
	 * ClicksUtil.clickButtons(IClicksConst.backToListBtn); return false;
	 * 
	 * }
	 * 
	 * if (!ie.span(attribute("class", "mainTable")).row(2).cell(1)
	 * .innerText().equals(arr[0])) {
	 * 
	 * ClicksUtil.clickButtons(IClicksConst.backToListBtn); return false;
	 * 
	 * }
	 * 
	 * if (!ie.span(attribute("class", "mainTable")).row(3).cell(1)
	 * .innerText().equals(arr[1])) {
	 * 
	 * ClicksUtil.clickButtons(IClicksConst.backToListBtn); return false;
	 * 
	 * }
	 * 
	 * if (!ie.span(attribute("class", "mainTable")).row(4).cell(1)
	 * .innerText().equals(arr[4])) {
	 * 
	 * ClicksUtil.clickButtons(IClicksConst.backToListBtn); return false;
	 * 
	 * }
	 * 
	 * if (!ie.span(attribute("class", "mainTable")).row(5).cell(1)
	 * .innerText().equals(arr[3])) {
	 * 
	 * ClicksUtil.clickButtons(IClicksConst.backToListBtn); return false;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return true;
	 * 
	 * }
	 */

	/**
	 * Gets the number formatter.
	 * 
	 * @param cellType
	 *            the cell type
	 * 
	 * @return the number formatter
	 */
	public static NumberFormat getNumberFormatter(int numDecimals,
			
			int decoration, boolean groupDigits) {

		java.util.Locale javaLocale = GeneralUtil.currentLocale;
		
		NumberFormat formatter;
		
		if (DECORATION_DOLLARSIGN == decoration) {
			
			formatter = NumberFormat.getCurrencyInstance(javaLocale);
			
		} else if (DECORATION_PERCENTSIGN == decoration) {
			
			formatter = NumberFormat.getPercentInstance(javaLocale);
			
		} else {
			
			formatter = (0 == numDecimals) ? NumberFormat
					
					.getIntegerInstance(javaLocale) : NumberFormat
					
					.getInstance(javaLocale);
		}
		formatter.setGroupingUsed(groupDigits);
		
		formatter.setMaximumFractionDigits(numDecimals);
		
		formatter.setMinimumFractionDigits(numDecimals);
		
		return (formatter);
	}

	public static String formatNumber(Integer number, int numDecimals,
			int decoration, boolean groupDigits) throws Exception {
		
		// temp solution to overcome English (UK)
		
		if(!GeneralUtil.currentLocale.getCountry().equalsIgnoreCase("US"))
		{			
			GeneralUtil.setCurrentLocale("English (Canada)");
		}

		NumberFormat formatter = getNumberFormatter(numDecimals, decoration,
				groupDigits);
		return formatter.format(number);

	}

	// String formattedNumber = formatter.format(number);

}
