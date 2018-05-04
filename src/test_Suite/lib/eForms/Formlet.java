/**
 * 
 */
package test_Suite.lib.eForms;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.elements.*;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 * 
 */
public class Formlet extends EForm {

	private static Log log = LogFactory.getLog(Formlet.class);

	protected String formletType = null;

	protected String formletId = null;

	protected String parentFormletId = null;

	protected String formletTitleText = null;

	protected String formletMenuText = null;

	protected String formPreFix = null;

	protected Integer formletCounter = 0;

	protected Integer formletIndex = 0;

	protected Integer eFormFieldIndex = 0;

	protected String expandFormletStartId = "/planner:0_";

	protected String expandFormletEndId = "";

	protected String expandFormletId = null;

	// private String imgSrcArray[] = null;

	// private String imgSrc = null;

	protected boolean retValue;

	protected String defaultFormlet = null;
	
	protected List<EFormField> lstFields;

	/**
	 * Constractors
	 */

	public Formlet(EForm form, String formletType, String eFormType,
			String preFix) {

		super(form.getEFormType(), form.getEFormSubType(), form.getPreFix());

		this.formletType = formletType;

		this.formPreFix = form.preFix;

		form.setFormletCounter(form.getFormletCounter() + 1);

		this.setFormletCounter(form.getFormletCounter());

		this.eFormFieldIndex = 0;

		this.setFormletId(this.formPreFix + preFix + this.formletType);

	}

	public Formlet(EForm form, String formletType) {

		super(form.getEFormType(), form.getEFormSubType(), form.getPreFix());

		this.formletType = formletType;

		this.formPreFix = form.getPreFix();

		form.setFormletCounter(form.getFormletCounter() + 1);

		this.setFormletCounter(form.getFormletCounter());

		this.eFormFieldIndex = 0;

		this.setFormletId(this.formPreFix + this.formletType);

	}

	public Formlet() {}

	public boolean initFormletWithDetails(String formletTitle,
			String formletType, String formleTitlePostFix,
			String formleIdentPostFix) throws Exception {

		String formletIdent = formletTitle.replace(" ", "-");

		this.setFormletTitleText(formletTitle + formleTitlePostFix);

		this.setFormletMenuText(formletTitle + formleTitlePostFix);

		this.setFormletId(formletIdent + formleIdentPostFix);

		return this.createFormlet(true, true);
	}
	
	public boolean initFormletWithDetails(int formletTypes,
			int syncTypeOrdinal) throws Exception {

		String formletTitle = ILBFunctionConst.formletTypes[formletTypes];
		
		String formletIdent = formletTitle.replace(" ", "-");

		this.setFormletTitleText(formletTitle + ILBFunctionConst.formletTitlePostFix);

		this.setFormletMenuText(formletTitle + ILBFunctionConst.formletTitlePostFix);

		this.setFormletId(formletIdent + ILBFunctionConst.formletIdentPostFix);

		return this.createFormlet(true, true);
	}

	public void expandFormletNode() throws Exception {

		IE ie = IEUtil.getActiveIE();

		expandFormletId = expandFormletStartId + formletCounter.toString() + "/";

		System.out.println(formletCounter);

		HtmlElement ele = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElement(id, expandFormletId);

		Spans sp =  ele.spans();

		if (!isExpanded(ele)){

			sp.span(1).click();		

		}	else {
			log.warn("Object " + sp.span(1).innerText() + " already expanded!");
		}		
	}
	
	

	public boolean isExpanded(HtmlElement ele) throws Exception {

		Spans sp =  ele.spans(attribute("class", IClicksConst.expandedSpan));

		for (Span span : sp) {

			if (span.exists())

				return true;
		}

		return false;
	}

	public boolean createMenuTypeFormlet() throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinksByTitle("Add Formlet");

		ie.textField(id, IFormletsConst.formletIdentifier_TextField_Id).set(
				this.formletId);

		ie.selectList(id, IFormletsConst.formletType_SelectList_Id).select(
				IFormletsConst.formletTypeName_MenuItemOnly);

		ie.checkbox(id,
				IFormletsConst.formletDisplayInSubmissionSummary_CheckBox_Id)
				.set(true);

//		ie.textField(id,
//				"/0:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
//				.set("Menu");

		ie.textField(id,
				"/0:" + IFormletsConst.formletMenuText_TextField_Id + "/").set(
				"Menu");

		if (ie.textField(id,
				"/1:" + IFormletsConst.formletMenuText_TextField_Id + "/")
				.exists()) {
//			ie.textField(
//					id,
//					"/1:" + IFormletsConst.formletTitleBarText_TextField_Id
//							+ "/").set("Menu");

			ie.textField(id,
					"/1:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set("Menu");
		}

		if (ie.textField(id,
				"/2:" + IFormletsConst.formletMenuText_TextField_Id + "/")
				.exists()) {
//			ie.textField(
//					id,
//					"/2:" + IFormletsConst.formletTitleBarText_TextField_Id
//							+ "/").set("Menu");

			ie.textField(id,
					"/2:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set("Menu");
		}
		
		if (ie.textField(id,
				"/3:" + IFormletsConst.formletMenuText_TextField_Id + "/")
				.exists()) {
//			ie.textField(
//					id,
//					"/2:" + IFormletsConst.formletTitleBarText_TextField_Id
//							+ "/").set("Menu");

			ie.textField(id,
					"/3:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set("Menu");
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.previewFormBtn)) {
			retValue = true;
		}
		return retValue;
	}

	public boolean createSubmissionSummaryTypeFormlet() throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		this.setFormletTitleText("Summary");
		this.setFormletMenuText("Submission Summary");

		ClicksUtil.clickLinksByTitle("Add Formlet");
		
		ie.textField(id, IFormletsConst.formletIdentifier_TextField_Id).set(
				getFormletId());

		ie.selectList(id, IFormletsConst.formletType_SelectList_Id).select(
				IFormletsConst.formletTypeName_SubmissionSummary);

		ie.checkbox(id,
				IFormletsConst.formletDisplayInSubmissionSummary_CheckBox_Id)
				.set(true);

		ie.selectList(id,
				IFormletsConst.formletIncludeInPDFExport_SelectList_Id)
				.selectValue("2");

		ie.selectList(id, IFormletsConst.formletPdfPageBreak_SelectList_Id)
				.selectValue("0");
		
		ie.selectList(id,IFormletsConst.formletSummaryLastUpdated_SelectList_Id).select("Date and Time");
		
		ie.selectList(id,IFormletsConst.formletSummaryLastUpdatedBy_SelectList_Id).select("Last Name, First Name");

		ie
				.selectList(id,
						IFormletsConst.formletPdfPageOrentation_SelectList_Id)
				.selectValue("1");

		ie.textField(id,
				"/0:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.set(this.getFormletTitleText());

		ie.textField(id,
				"/0:" + IFormletsConst.formletMenuText_TextField_Id + "/").set(
				this.getFormletMenuText());

		if (ie.textField(id,
				"/1:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/1:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.getFormletTitleText());

			ie.textField(id,
					"/1:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set(this.getFormletMenuText());
		}

		if (ie.textField(id,
				"/2:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/2:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.getFormletTitleText());

			ie.textField(id,
					"/2:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set(this.getFormletMenuText());
		}
		
		if (ie.textField(id,
				"/3:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/3:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.getFormletTitleText());

			ie.textField(id,
					"/3:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set(this.getFormletMenuText());
		}
		
		

		//ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.previewFormBtn)) {
			retValue = true;
		}
		return retValue;
	}

	public boolean createSubFormlet() throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();

		EFormsUtil.expandFormPlannerNode(getParentFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		ClicksUtil.clickLinksByTitle("Add Sub-Formlet");

		ie.textField(id, IFormletsConst.formletIdentifier_TextField_Id).set(
				getFormletId());

		ie.selectList(id, IFormletsConst.formletType_SelectList_Id).select(
				this.formletType);

		ie.selectList(id,
				IFormletsConst.formletIncludeInPDFExport_SelectList_Id)
				.selectValue("2");

		ie.selectList(id, IFormletsConst.formletPdfPageBreak_SelectList_Id)
				.selectValue("0");

		ie
				.selectList(id,
						IFormletsConst.formletPdfPageOrentation_SelectList_Id)
				.selectValue("1");

		ie.textField(id,
				"/0:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.set(this.formletTitleText);

		if (ie.textField(id,
				"/1:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/1:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.formletTitleText);
		}

		if (ie.textField(id,
				"/2:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/2:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.formletTitleText);
		}

		if (ie.textField(id,
				"/3:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/3:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.formletTitleText);
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);

		// Checks if still in Formlet details
		if (GeneralUtil.isButtonExists(IClicksConst.previewFormBtn)) {
			log.info("Formlet Type " + this.formletType + " has being created");

			retValue = true;

			setFormletCounter(this.formletCounter += 1);
		} else {
			log.error("Failed to Create" + this.formletType);
		}

		this.formletCounter -= 1;

		return retValue;

	}

	public boolean createFormlet(boolean doesItDisplayInSummary,
			boolean isThisDefaultFormlet) throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinksByTitle("Add Formlet");

		if (isThisDefaultFormlet) {
			super.setDefaultFormlet(this.getFormletId());
		}

		ie.textField(id, IFormletsConst.formletIdentifier_TextField_Id).set(
				getFormletId());

		ie.selectList(id, IFormletsConst.formletType_SelectList_Id).select(
				this.formletType);

		ie.checkbox(id,
				IFormletsConst.formletDisplayInSubmissionSummary_CheckBox_Id)
				.set(doesItDisplayInSummary);

		ie.selectList(id,
				IFormletsConst.formletIncludeInPDFExport_SelectList_Id)
				.selectValue("2");

		ie.selectList(id, IFormletsConst.formletPdfPageBreak_SelectList_Id)
				.selectValue("0");

		ie
				.selectList(id,
						IFormletsConst.formletPdfPageOrentation_SelectList_Id)
				.selectValue("1");

		ie.textField(id,
				"/0:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.set(this.formletTitleText);

		ie.textField(id,
				"/0:" + IFormletsConst.formletMenuText_TextField_Id + "/").set(
				this.formletMenuText);

		if (ie.textField(id,
				"/1:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/1:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.formletTitleText);

			ie.textField(id,
					"/1:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set(this.formletMenuText);
		}

		if (ie.textField(id,
				"/2:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/2:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.formletTitleText);

			ie.textField(id,
					"/2:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set(this.formletMenuText);
		}

		if (ie.textField(id,
				"/3:" + IFormletsConst.formletTitleBarText_TextField_Id + "/")
				.exists()) {
			ie.textField(
					id,
					"/3:" + IFormletsConst.formletTitleBarText_TextField_Id
							+ "/").set(this.formletTitleText);

			ie.textField(id,
					"/3:" + IFormletsConst.formletMenuText_TextField_Id + "/")
					.set(this.formletMenuText);
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);

		// Checks if still in Formlet details
		if (GeneralUtil.isButtonExists(IClicksConst.previewFormBtn)) {
			log.info("Formlet Type " + this.formletType + " has being created");

			this.setParentFormletId(this.getFormletId());
			retValue = true;

		} else {
			log.error("Failed to Create " + this.formletType);
			retValue = false;
		}

		return retValue;
	}

	public boolean addAttachments(ArrayList<String> attachmentParms,
			boolean isRequired) throws Exception {

		IE ie = IEUtil.getActiveIE();

		EFormsUtil.expandFormPlannerNode(this.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		ClicksUtil.clickLinksByTitle("Add Attachment Document");

		ie.textField(id, "/documentIdentifier_rw/").set(attachmentParms.get(0));

		ie.checkbox(id, "/documentRequired/").set(isRequired);

		ie.selectList(id, "/maximumSize/").select(attachmentParms.get(1));

		ie.textField(id, "/locales:0:documentType/")
				.set(attachmentParms.get(2));

		ie.textField(id, "/locales:0:instructions/")
				.set(attachmentParms.get(3));

		if (ie.textField(id, "/locales:1:documentType/").exists()) {
			ie.textField(id, "/locales:1:documentType/").set(
					attachmentParms.get(2));
			ie.textField(id, "/locales:1:instructions/").set(
					attachmentParms.get(3));
		}

		if (ie.textField(id, "/locales:2:documentType/").exists()) {
			ie.textField(id, "/locales:2:documentType/").set(
					attachmentParms.get(2));
			ie.textField(id, "/locales:2:instructions/").set(
					attachmentParms.get(3));
		}

		if (ie.textField(id, "/locales:3:documentType/").exists()) {
			ie.textField(id, "/locales:3:documentType/").set(
					attachmentParms.get(2));
			ie.textField(id, "/locales:3:instructions/").set(
					attachmentParms.get(3));
		}

		ie.selectList(id, "/M2M_AvailableItems/")
				.select(attachmentParms.get(4));

		ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);

		return true;
	}

	// --------------- End of Constractors

	// ***************Getter And Setter *******************

	public String getFormletId() {
		return formletId;
	}

	public void setFormletId(String formletId) {
		this.formletId = formletId;
	}

	public String getFormletMenuText() {
		return formletMenuText;
	}

	public void setFormletMenuText(String formletMenuText) {
		this.formletMenuText = formletMenuText;
	}

	public String getFormletTitleText() {
		return formletTitleText;
	}

	public void setFormletTitleText(String formletTitleText) {
		this.formletTitleText = formletTitleText;
	}

	public String getFormletType() {
		return formletType;
	}

	public void setFormletType(String formletType) {
		this.formletType = formletType;
	}

	public Integer getFormletCounter() {
		return formletCounter;
	}

	public void setFormletCounter(Integer formletCounter) {
		this.formletCounter = formletCounter;
	}

	public String getDefaultFormlet() {
		return defaultFormlet;
	}

	public void setDefaultFormlet(String defaultFormlet) {
		this.defaultFormlet = defaultFormlet;
	}

	/**
	 * @return the parentFormletId
	 */
	public String getParentFormletId() {
		return parentFormletId;
	}

	/**
	 * @param parentFormletId
	 *            the parentFormletId to set
	 */
	public void setParentFormletId(String parentFormletId) {
		this.parentFormletId = parentFormletId;
	}

	/**
	 * @return the lstFields
	 */
	public List<EFormField> getLstFields() {
		return lstFields;
	}

	/**
	 * @param lstFields the lstFields to set
	 */
	public void setLstFields(List<EFormField> lstFields) {
		this.lstFields = lstFields;
	}

	// -------------- End of Getter and Setter --------------------

}
