/**
 * 
 */
package test_Suite.lib.eForms;

import static watij.finders.SymbolFactory.id;

import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.eForms.IAutoSaveConst;
import test_Suite.constants.eForms.IAutoSaveConst.EFormletType;
import test_Suite.constants.eForms.IAutoSaveConst.eSelectable;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ProjectUtil;
import watij.elements.Div;
import watij.elements.Divs;
import watij.elements.Link;
import watij.elements.Links;
import watij.elements.Radio;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class AutoSave {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends AutoSave> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	private EForm form;
	private EForm subForm;

	private Formlet formlet;
	private Formlet subFormlet;

	private EFormField eFormField;

	private Program fopp;
	private Project proj;
	private FOProject foProj;
	
	String spanId = "main:dFo:_idJsp94:fi";

	private boolean newOrg;
	
	private int charLength;
	/*-------------------------------------------------------------------*/

	/**
	 * 
	 */
	public AutoSave() {
	}

	/*************************************
	 * Initializing eForms, Formlets, etc
	 *************************************/

	/************* End of eForms, Formlets, etc **************************/

	/*********************************************
	 * Initializing Funding Opp, PO Projects, etc
	 *********************************************/

	public void initFopp(String foppPostFix) throws Exception {

		this.fopp = new Program(IAutoSaveConst.autoSave_FoppPreFix,
				foppPostFix, IAutoSaveConst.autoSave_Fopp_name, 'P',
				Boolean.FALSE, "", Boolean.FALSE,
				Boolean.TRUE, "Publication Form");

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		this.fopp.initProgram();
		
		log.info("FOPP initialized!");

	}

	public void initializeProjAndCreateOrg(String projPostFix,boolean isNewProj,
			boolean isNewApplicant) throws Exception {

		this.proj = new Project(this.fopp, isNewProj,projPostFix);

		this.setNewOrg(isNewApplicant);

		this.proj.createOrgFullName(this.isNewOrg());
	}

	public void fillApplicantProfile() throws Exception {

		if (this.isNewOrg()) {

			Assert.assertTrue(ProjectUtil
					.openApplicantProfileFormInPO(this.proj),
					"FAIL: could not open Org - " + this.proj.getOrgFullName());
			
			insertDataTo_ELists();
			
			submitForm();
		}
	}
	
	public void insertDataTo_ELists() throws Exception {
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickImage(IClicksConst.newImg);
		
		List<String> arrOptions = ie.selectList(0).getAllContents();
		
		for (String string : arrOptions) {
			
			ie.selectList(0).select(string);
			GeneralUtil.takeANap(0.300);
			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);			
		}
	}

	public void submitProject(String goToStep, String status) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> dropdownHash = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				this.proj.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				this.proj.getProgFullName());

		dropdownHash
				.put(
						IFiltersConst.grantManagement_SubmissionStatus_Lbl,
						status);

		FiltersUtil.filterListByLabel(hashTable, dropdownHash, false);

		int rowIndx = TablesUtil.getRowIndex(
				ITablesConst.applicantSubmissionTableId, this.proj
						.getProjFullName());
		
		Div tDiv = TablesUtil.tableDiv();

		this.proj.setCurrentStepName(tDiv.body(id,ITablesConst.applicantSubmissionTableId).row(rowIndx).cell(4).innerText());

		tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndx).image(0).click();

		ie.selectList(0).select(goToStep);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);
		
		return;

	}

	public void openSubmissionForm() throws Exception {

		ProjectUtil.openPOSubmissionForm(this.proj,
				"Submission", "Open Projects",
				"Latest Version", "Ready");
	}

	public void submitForm() throws Exception {

		ClicksUtil.clickLinks("Submission Summary");
		
		if(GeneralUtil.isButtonExistsByValue(IClicksConst.submitBtn)) {
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			
		}else if(GeneralUtil.isButtonExistsByValue(IClicksConst.completeBtn)) {
			ClicksUtil.clickButtons(IClicksConst.completeBtn);	
		}
		returnFromAnyList();
	}

	public void returnFromAnyList() throws Exception {

		if (GeneralUtil
				.isLinkExistsByTxt(IClicksConst.backToMyAssignedSubmissionsListLnk)) {
			ClicksUtil
					.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToApplicantSubListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToAwardListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToSubListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

			return;
		}

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.backToApplicantsListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.backToApplicantsListLnk);

			return;
		}
	}
	
	public Divs getAllDivsInFormlet() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.div(id,IProjectsConst.frm_FormletDetailsDivId).exists())
		{
			log.error("Could not find form element in Formlet Details!");
			return null;
		}
		
		return ie.div(id,IProjectsConst.frm_FormletDetailsDivId).divs();
		
	}
	
	public boolean doesFieldVisible() throws Exception {
		
		if(null == getAllDivsInFormlet())
		{
			log.error("Could find the Formlet Details!");
			return false;
		}
		
		Divs divs = getAllDivsInFormlet();
		
		for (Div div : divs) {
			
			if(div.innerText().startsWith("Visibility:"))
			{
				return true;
			}
			
		}
		
		log.error("Could not find the Control on the page!");
		
		return false;
	}
	
	public boolean doesFieldReadOnly() throws Exception {
		
		if(null == getAllDivsInFormlet())
		{
			log.error("Could find the Formlet Details!");
			return false;
		}
		
		Divs divs = getAllDivsInFormlet();
		
		for (Div div : divs) {
			
			if(div.innerText().startsWith("Readability:"))
			{
				return true;
			}			
		}
		
		log.error("Could not find the Control on the page!");
		
		return false;
	}
	
	public boolean doesFieldContain(eSelectable string) throws Exception {
		
		if(null == getAllDivsInFormlet())
		{
			log.error("Could find the Formlet Details!");
			return false;
		}
		
		Divs divs = getAllDivsInFormlet();
		
		for (Div div : divs) {
			
			if(div.innerText().startsWith("* Calculated Value:"))
			{
				return true;
			}
			
		}
		
		log.error("Could not find the Control on the page!");
		
		return false;
	}
	
	public boolean isFieldReadOnlyInFormlet(EFormletType name) throws Exception {
		
		ClicksUtil.clickLinks(IAutoSaveConst.formletName[name.ordinal()] + "Formlet");
		
		if(null == getAllDivsInFormlet())
		{
			log.error("Could find the Formlet Details!");
			return false;
		}
		
		Divs divs = getAllDivsInFormlet();
		
		for (Div div : divs) {
			
			if(div.innerText().startsWith("Short Text"))
			{
				return true;
			}
			
		}
		
		log.error("Could not find the Control on the page!");
		
		return false;
	}
	
	public boolean isFormletVisible(EFormletType name) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.div(id,"g3-form:formletIDs").exists())
		{
			log.error("Could not find Menu Form tag!");
			return false;
		}
		
		Links links = ie.div(id,"g3-form:formletIDs").links();
		
		for (Link link : links) {
			
			//log.warn(link.text());
			
			if(link.innerText().equals(IAutoSaveConst.formletName[name.ordinal()] + "Formlet")) {
				return true;
			}			
		}
		
		return false;
	}
	
	public boolean isFormletReadOnly(EFormletType name) throws Exception {
		
		ClicksUtil.clickLinks(IAutoSaveConst.formletName[name.ordinal()] + "Formlet");
		
		return GeneralUtil.isImageExistsBySrc(IClicksConst.newImg);
	}
	
	public void fillTextField(int charCount) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ie.textField(0).set(EFormsUtil.createAnyRandomString(charCount));
		
		return;
		
	}
	
	public void selectFromDropdown(eSelectable eSelect, EFormletType eFormletName, boolean isInEList, boolean fillMandField) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		int x= 0;
		
		ClicksUtil.clickLinks(IAutoSaveConst.formletName[eFormletName.ordinal()] + "eFormField");
		
		if(isInEList) {
			ClicksUtil.clickImage(IClicksConst.newImg);
		}
		
		if(fillMandField) {
			
			fillTextField(this.getCharLength());
		}

		GeneralUtil.takeANap(2.0);
		
		if(eFormletName == EFormletType.child) {
			switch (eSelect) {
			
			case inivisible: {

				ie.selectList(x).select("Visibility");
				//ClicksUtil.clickButtons(IClicksConst.setBtn);
				GeneralUtil.takeANap(2.0);
				x++;
				break;
			}
			
			case readOnly: {

				ie.selectList(x).select("Readability");
				//ClicksUtil.clickButtons(IClicksConst.setBtn);
				GeneralUtil.takeANap(2.0);
				x++;
				break;
			}
			
			case writable: {

				ie.selectList(x).select("Readability");
				//ClicksUtil.clickButtons(IClicksConst.setBtn);
				GeneralUtil.takeANap(2.0);
				x++;
				break;
			}
			
			case visible: {

				ie.selectList(x).select("Visibility");
				//ClicksUtil.clickButtons(IClicksConst.setBtn);
				GeneralUtil.takeANap(2.0);
				x++;
				break;
			}
			default:
				break;
			}
		}		
		ie.selectList(x).select(IAutoSaveConst.selectableValues[eSelect.ordinal()]);
		
		GeneralUtil.takeANap(2.0);
		
		return;		
	}
	
	public void selectRadioButton(eSelectable eSelect, boolean isInEList, boolean fillMandField) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IAutoSaveConst.formletName[EFormletType.radio.ordinal()] + "eFormField");
		
		if(isInEList) {
			ClicksUtil.clickImage(IClicksConst.newImg);
		}
		
		if(fillMandField) {
			
			fillTextField(this.getCharLength());
		}
		
		TableRows tableRows = ie.table(id,"g3-form:eFormFieldList:0:radioButtonSet").rows();
		
		Radio radioButton;
		
		for (TableRow tableRow : tableRows) {
			
			if(tableRow.innerText().endsWith(IAutoSaveConst.selectableValues[eSelect.ordinal()])) {
				radioButton = tableRow.radio(0);
				
				radioButton.set();
				
				GeneralUtil.takeANap(2.0);
				
				return;
			}			
		}
		
		return;
	}
	
	public void selectCheckbox(boolean checkboxStatus, EFormletType eFormletName, boolean isInEList, boolean fillMandField) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IAutoSaveConst.formletName[eFormletName.ordinal()] + "eFormField");
		
		if(isInEList) {
			ClicksUtil.clickImage(IClicksConst.newImg);
		}
		
		if(fillMandField) {
			
			fillTextField(this.getCharLength());
		}
		
		ie.checkbox(0).set();
		
		GeneralUtil.takeANap(2.0);
		
		return;		
	}

	/**
	 * @return the form
	 */
	public EForm getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(EForm form) {
		this.form = form;
	}

	/**
	 * @return the subForm
	 */
	public EForm getSubForm() {
		return subForm;
	}

	/**
	 * @param subForm the subForm to set
	 */
	public void setSubForm(EForm subForm) {
		this.subForm = subForm;
	}

	/**
	 * @return the formlet
	 */
	public Formlet getFormlet() {
		return formlet;
	}

	/**
	 * @param formlet the formlet to set
	 */
	public void setFormlet(Formlet formlet) {
		this.formlet = formlet;
	}

	/**
	 * @return the subFormlet
	 */
	public Formlet getSubFormlet() {
		return subFormlet;
	}

	/**
	 * @param subFormlet the subFormlet to set
	 */
	public void setSubFormlet(Formlet subFormlet) {
		this.subFormlet = subFormlet;
	}

	/**
	 * @return the eFormField
	 */
	public EFormField getEFormField() {
		return eFormField;
	}

	/**
	 * @param formField the eFormField to set
	 */
	public void setEFormField(EFormField formField) {
		eFormField = formField;
	}

	/**
	 * @return the fopp
	 */
	public Program getFopp() {
		return fopp;
	}

	/**
	 * @param fopp the fopp to set
	 */
	public void setFopp(Program fopp) {
		this.fopp = fopp;
	}

	/**
	 * @return the proj
	 */
	public Project getProj() {
		return proj;
	}

	/**
	 * @param proj the proj to set
	 */
	public void setProj(Project proj) {
		this.proj = proj;
	}

	/**
	 * @return the foProj
	 */
	public FOProject getFoProj() {
		return foProj;
	}

	/**
	 * @param foProj the foProj to set
	 */
	public void setFoProj(FOProject foProj) {
		this.foProj = foProj;
	}

	/**
	 * @return the newOrg
	 */
	public boolean isNewOrg() {
		return newOrg;
	}

	/**
	 * @param newOrg the newOrg to set
	 */
	public void setNewOrg(boolean newOrg) {
		this.newOrg = newOrg;
	}

	/**
	 * @return the charLength
	 */
	public int getCharLength() {
		return charLength;
	}

	/**
	 * @param charLength the charLength to set
	 */
	public void setCharLength(int charLength) {
		this.charLength = charLength;
	}

}
