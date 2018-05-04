/*
 * FormsHelper.java
 *
 * Created on February 1, 2007, 457 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test_Suite.lib.eForms;

/**
 * 
 * @author mshakshouki
 */

import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;
import java.util.List;

import watij.runtime.ie.IE;
import test_Suite.constants.eForms.*;
import test_Suite.constants.ui.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.lang.RandomStringUtils;

//import org.apache.commons.

public class EForm {

	private static Log log = LogFactory.getLog(EForm.class);

	protected String eFormType = null;

	protected String eFormName = null;

	protected String eFormSubType = null;

	protected String eFormId = null;

	protected String eFormFullId = null;

	protected String eFormTitle = null;

	private String eFormTitles[] = null;

	protected String scrumble = null;

	protected String preFix = null;

	protected String postFix = null;

	protected Integer formletCounter = 0;

	protected boolean retValue;

	protected String primaryOrg = null;

	protected String orgAccess = null;

	protected String defaultFormlet;

	protected String displayProjInfo = "Never";

	protected List<Formlet> lstFormlets;

	static ArrayList<String> errorSmall;

	/************* Constructors ****/

	public EForm(String preFix, String eFormName, String postFix, boolean isNew) {

		this.preFix = preFix;

		this.eFormName = eFormName;

		this.postFix = postFix;

		this.setEFormFullId(preFix + eFormName + postFix);

		this.setEFormId(preFix + eFormName);

	}

	public EForm() {

		this.setScrumble(RandomStringUtils.randomAlphabetic(5));

		this.setFormletCounter(0);

		// TODO: may change and be set in the test case

		this.setPrimaryOrg("G3");

		this.setOrgAccess("Public");
	}

	public EForm(String eFormType, String preFix) {

		this.eFormType = eFormType;

		this.setScrumble(RandomStringUtils.randomAlphabetic(5));

		this.setEFormId(preFix + eFormType);

		this.setEFormFullId(this.getEFormId() + "-" + this.getScrumble());

		this.setFormletCounter(0);

		this.preFix = preFix;

		// TODO: may change and be set in the test case

		this.setPrimaryOrg("G3");

		this.setOrgAccess("Public");

	}

	public EForm(String eFormType, String eFormSubType, String preFix) {

		this.eFormType = eFormType;

		this.eFormSubType = eFormSubType;

		this.setScrumble(RandomStringUtils.randomAlphabetic(5));

		this.setEFormId(preFix + eFormSubType);

		this.setEFormFullId(this.getEFormId() + "-" + this.getScrumble());

		this.setFormletCounter(0);

		this.preFix = preFix;

		// TODO: may change and be set in the test case

		this.setPrimaryOrg("G3");

		this.setOrgAccess("Public");

	}

	public EForm(String eFormType, String eFormSubType, String preFix,
			String postFix) {

		this.eFormType = eFormType;

		this.eFormSubType = eFormSubType;

		this.preFix = preFix;

		this.postFix = postFix;

		this.setScrumble(RandomStringUtils.randomAlphabetic(5));

		this.setEFormId(this.getPreFix() + this.getEFormSubType());

		this.setEFormFullId(this.getEFormId() + this.getPostFix());

		this.setFormletCounter(0);

		// TODO: may change and be set in the test case

		this.setPrimaryOrg("G3");

		this.setOrgAccess("Public");

	}

	public boolean createEForm() throws Exception {

		log.info("Start Creating eForm Type " + this.eFormType);
		retValue = false;

		IE ie = IEUtil.getActiveIE();

		log.info("Opening Forms List");
		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.gpa_FormIdent_Lbl, this.eFormFullId,"Exact");

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.textField(id, IEFormsConst.formIdentifier_TextField_Id).set(
				this.getEFormFullId());

		ie.selectList(id, IEFormsConst.formType_SelectList_Id).select(
				this.eFormType);

		GeneralUtil.takeANap(0.5);

		ie.selectList(id, IEFormsConst.formSubType_SelectList_Id).select(
				this.eFormSubType);

		ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(
				this.getPrimaryOrg());

		ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(

				this.getOrgAccess());
		if (ie.selectList(id,IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id).exists())
		{
			ie.selectList(id,IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id).select(this.displayProjInfo);
		}

		ie.textField(id, "/0:" + IEFormsConst.formTitle_TextField_Id + "/")
		.set(this.getEFormTitle());

		if (ie.textField(id, "/1:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/1:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		if (ie.textField(id, "/2:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/2:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		if (ie.textField(id, "/3:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/3:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		GeneralUtil.takeANap(0.5);

		if (ClicksUtil.clickButtons(IClicksConst.openFormPlannerBtn)){
			retValue = true;
		}

		return retValue;

	}

	public void editEFormDetails() throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.textField(id, IEFormsConst.formIdentifier_TextField_Id).set(
				this.getEFormFullId());

		if(ie.selectList(id, IEFormsConst.formType_SelectList_Id).exists()) {

			ie.selectList(id, IEFormsConst.formType_SelectList_Id).select(
					this.eFormType);

			GeneralUtil.takeANap(0.5);

			ie.selectList(id, IEFormsConst.formSubType_SelectList_Id).select(
					this.eFormSubType);
		}

		ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(
				this.getPrimaryOrg());

		ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(
				this.getOrgAccess());

		ie.selectList(id,IEFormsConst.formDeisplyProjectInfo_DropdwonField_Id).select(this.displayProjInfo);

		ie.textField(id, "/0:" + IEFormsConst.formTitle_TextField_Id + "/")
		.set(this.getEFormTitle());

		if (ie.textField(id, "/1:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/1:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		if (ie.textField(id, "/2:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/2:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		if (ie.textField(id, "/3:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/3:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		GeneralUtil.takeANap(0.5);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

	}

	/**
	 * Simplified method to create or update Form
	 * 
	 * @throws Exception
	 *             Added by Alex Pankov
	 */
	public void createOrUpdateForm() throws Exception {

		IE ie = IEUtil.getActiveIE();
		if (GeneralUtil.isButtonExists(IClicksConst.unPublishFormBtn))
			ClicksUtil.clickButtons(IClicksConst.unPublishFormBtn);
		ie.textField(id, IEFormsConst.formIdentifier_TextField_Id).set(
				this.eFormId);
		ie.selectList(id, IEFormsConst.formType_SelectList_Id).select(
				this.eFormType);
		ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(
				this.primaryOrg);
		ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(
				this.orgAccess);

		ie.textField(id, "/0:" + IEFormsConst.formTitle_TextField_Id + "/")
		.set(this.getEFormTitle());

		if (ie.textField(id, "/1:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/1:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		if (ie.textField(id, "/2:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/2:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}

		if (ie.textField(id, "/3:" + IEFormsConst.formTitle_TextField_Id + "/")
				.exists()) {
			ie.textField(id, "/3:" + IEFormsConst.formTitle_TextField_Id + "/")
			.set(this.getEFormTitle());
		}


		if (GeneralUtil.isButtonExists(IClicksConst.publishFormBtn)){
			
			ClicksUtil.clickButtons(IClicksConst.publishFormBtn);

			GeneralUtil.takeANap(0.5);

			ClicksUtil.clickButtons(IClicksConst.publishFormBtn);
	}
		else {
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			GeneralUtil.takeANap(0.5);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}
	}

	public boolean publishForm(String defaultFormlet) throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();

		// are you still in the Form planner?

		if (ie.link(title, IClicksConst.eForm_eFormDetails_Title_lnk).exists()
				|| ie.htmlElement(title,
						IClicksConst.eForm_eFormDetails_Title_lnk).exists()) {
			ClicksUtil
			.clickLinksByTitle(IClicksConst.eForm_eFormDetails_Title_lnk);

			retValue = true;
		} else {
			// Start from the Forms List
			ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);

			FiltersUtil.filterListByLabel(IFiltersConst.gpa_FormIdent_Lbl,
					this.eFormId,"Exact");

			// IF there is More pages
			for (Integer x = 2; x == 20; x++) {
				if (TablesUtil.findInTable(ITablesConst.formsTableId,
						this.eFormId)) {
					ClicksUtil.clickLinks(this.eFormId);

					ClicksUtil
					.clickLinksByTitle(IClicksConst.eForm_eFormDetails_Title_lnk);
					retValue = true;
					break;
				} else {
					if (ie.link(text, x.toString()).exists()) {
						ClicksUtil.clickLinks(x.toString());
					} else {
						log.debug("could not find Form");
						break;
					}
				}
			}

		}

		if (retValue) {
			ie.selectList(id, IEFormsConst.formDefaultFormlet_SelectList_Id)
			.select(defaultFormlet);

			if (!ClicksUtil.clickButtons(IClicksConst.publishFormBtn)){
				
				log.error("Form cannot be published");
				return false;
			}
			
			log.info("Form has been published");
		}

		return retValue;
	}

	public boolean publishEFormAndCheckErrors(String defaultFormlet)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		errorSmall = null;

		ClicksUtil.clickLinksByTitle(IClicksConst.eForm_eFormDetails_Title_lnk);

		ie.selectList(id, IEFormsConst.formDefaultFormlet_SelectList_Id)
		.select(defaultFormlet);

		ClicksUtil.clickButtons(IClicksConst.publishFormBtn);

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.unPublishFormBtn)) {
			return true;
		}

		errorSmall = GeneralUtil.checkForErrorMessages();

		if (errorSmall != null && !errorSmall.isEmpty()) {
			for (String string : errorSmall) {

				log.error("Validation error: " + string);

			}
		}

		return false;
	}

	public boolean unPublishForm() throws Exception {

		ClicksUtil.clickLinksByTitle("e.Form Details");

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.unPublishFormBtn)) {
			ClicksUtil.clickButtons(IClicksConst.unPublishFormBtn);

			if (GeneralUtil.isButtonExistsByValue(IClicksConst.publishFormBtn)) {
				ClicksUtil.clickButtons(IClicksConst.backBtn);
				return true;

			}
		}

		ClicksUtil.clickButtons(IClicksConst.backBtn);
		return false;

	}

	// #####*************End of etc****************************

	/**
	 * @return the eFormFullId
	 */
	public String getEFormFullId() {
		return eFormFullId;
	}

	/**
	 * @param formFullId
	 *            the eFormFullId to set
	 */
	public void setEFormFullId(String formFullId) {
		eFormFullId = formFullId;
	}

	public String getEFormName(){

		return eFormName;
	}


//	public void setEFormName(String eFormName){
//
//		eFormName = eFormName;
//	}

	/**
	 * @return the defaultFormlet
	 */
	public String getDefaultFormlet() {
		return defaultFormlet;
	}

	/**
	 * @param defaultFormlet
	 *            the defaultFormlet to set
	 */
	public void setDefaultFormlet(String defaultFormlet) {
		this.defaultFormlet = defaultFormlet;
	}

	/**
	 * @return the formletCounter
	 */
	public Integer getFormletCounter() {
		return formletCounter;
	}

	/**
	 * @param formletCounter
	 *            the formletCounter to set
	 */
	public void setFormletCounter(Integer formletCounter) {
		this.formletCounter = formletCounter;
	}

	public String getEFormId() {
		return eFormId;
	}

	public void setEFormId(String formId) {
		eFormId = formId;
	}

	public String getEFormType() {
		return eFormType;
	}

	public void setEFormType(String formType) {
		eFormType = formType;
	}

	public String getEFormTitle() {
		return eFormTitle;
	}

	public void setEFormTitle(String formTitle) {
		eFormTitle = formTitle;
	}

	public String getOrgAccess() {
		return orgAccess;
	}

	public void setOrgAccess(String orgAccess) {
		this.orgAccess = orgAccess;
	}

	public String getPrimaryOrg() {
		return primaryOrg;
	}

	public void setPrimaryOrg(String primaryOrg) {
		this.primaryOrg = primaryOrg;
	}

	public String[] getEFormTitles() {
		return eFormTitles;
	}

	public void setEFormTitles(String[] formTitles) {
		eFormTitles = formTitles;
	}

	/**
	 * @return the eFormSubType
	 */
	public String getEFormSubType() {
		return eFormSubType;
	}

	/**
	 * @param formSubType
	 *            the eFormSubType to set
	 */
	public void setEFormSubType(String formSubType) {
		eFormSubType = formSubType;
	}

	/**
	 * @return the scrumble
	 */
	public String getScrumble() {
		return scrumble;
	}

	/**
	 * @param scrumble
	 *            the scrumble to set
	 */
	public void setScrumble(String scrumble) {
		this.scrumble = scrumble;
	}

	/**
	 * @return the postFix
	 */
	public String getPostFix() {
		return postFix;
	}

	/**
	 * @param postFix
	 *            the postFix to set
	 */
	public void setPostFix(String postFix) {
		this.postFix = postFix;
	}

	/**
	 * @return the preFix
	 */
	public String getPreFix() {
		return preFix;
	}

	/**
	 * @param preFix
	 *            the preFix to set
	 */
	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}

	/**
	 * @return the lstFormlets
	 */
	public List<Formlet> getLstFormlets() {
		return lstFormlets;
	}

	/**
	 * @param lstFormlets the lstFormlets to set
	 */
	public void setLstFormlets(List<Formlet> lstFormlets) {
		this.lstFormlets = lstFormlets;
	}

	/**
	 * @return the displayProjInfo
	 */
	public String getDisplayProjInfo() {
		return displayProjInfo;
	}

	/**
	 * @param displayProjInfo the displayProjInfo to set
	 */
	public void setDisplayProjInfo(String displayProjInfo) {
		this.displayProjInfo = displayProjInfo;
	}

}
