package test_Suite.utils.workflow;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.tag;
import static watij.finders.SymbolFactory.title;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst.EFOLists;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IAmendmentsConst.EAmendmentOptions;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.elements.Divs;
import watij.elements.HtmlElement;
import watij.elements.HtmlElements;
import watij.elements.TableBody;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.runtime.ie.IE;

public class AmendmentsUtil {

	private static Log log = LogFactory.getLog(AmendmentsUtil.class);
	
	
	public static boolean fillOutAmendments(String amendParams[], Project proj, String category) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if(GeneralUtil.isSelectListExists(IAmendmentsConst.amendRequest_Category_DropDwnFld_ID))
		{
			
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendRequest_Category_DropDwnFld_ID, category))
			{
				log.error("Could not select Amendment Category!");
				return false;
			}
			
		}
		
		if(!GeneralUtil.selectInDropdownList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amendParams[2].replace("/", "")))
		{
			log.error("Could not select Amendmer!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, amendParams[3]))
		{
			log.error("Could not set Required Date!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, amendParams[4]))
		{
			log.error("Could not set Required Date!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Comment_TxtFld_ID, amendParams[5]))
		{
			log.error("Could not set Required Date!");
			return false;
		}	
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		if(!ie.div(attribute("class", ITablesConst.widgetDiv_class)).exists())
		{
			log.error("Errors adding Amendment Parameters!");
			return false;
		}		
		
		return true;
	}
	
	
	public static boolean fillAmendments(String amendParams[], Project proj) throws Exception{
		
		IE ie = IEUtil.getActiveIE();
		
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, amendParams[3]))
		{
			log.error("Could not set Required Date!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, amendParams[4]))
		{
			log.error("Could not set reason field!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Comment_TxtFld_ID, amendParams[5]))
		{
			log.error("Could not set comments field!");
			return false;
		}	
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		return true;
		
		
	}
	
	public static boolean fillOutAmendments(String amendParams[], Project proj) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if(!GeneralUtil.selectInDropdownList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amendParams[2].replace("/", "")))
		{
			log.error("Could not select Amender!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, amendParams[3]))
		{
			log.error("Could not set Required Date!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, amendParams[4]))
		{
			log.error("Could not set Required Date!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Comment_TxtFld_ID, amendParams[5]))
		{
			log.error("Could not set Required Date!");
			return false;
		}	
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		if(!ie.form(id, IAmendmentsConst.amendmentRequest_StepReExec_FormId).exists())
		{
			log.error("Errors adding Amendment Parameters!");
			return false;
		}		
		
		return true;
	}
	
	/**
	 * @param amendParams
	 * @param foproj
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean issueAmendment(String amendParams[], Project foproj) throws Exception {


		IE ie = IEUtil.getActiveIE();

		boolean retValue = false;

		int rowIndex = -1;
		
		String includeClaim = "";
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		
		Hashtable<String, String> hashTableDropDown = new Hashtable<String, String>();
		
		if((amendParams[1].contains("Initial")) || (foproj.getClaimNumber() > 0)) {
			
			includeClaim = " - Claim " + foproj.getClaimNumber();
		}

		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);
		
		foproj.initializeStep(amendParams[1]);

		if (amendParams.length > 6) {			
			
			hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,amendParams[6]);	
			
			hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,amendParams[0] + includeClaim);
			
			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, foproj.getCurrentStepName());

		} else {	
			
			hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,amendParams[0] + includeClaim);
			
			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, foproj.getCurrentStepName()); 
		
		}
		
		FiltersUtil.filterListByLabel(hashTable,hashTableDropDown,false);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				new String[] { amendParams[0], foproj.getCurrentStepName() });

		log.info(rowIndex);

		if (rowIndex > -1) {
			
			ie.body(id, ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(rowIndex).cell(0).link(1).click();
			
			if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
			{
				ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
			}
			
			ie.selectList(id,
					IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID)
					.select(amendParams[2]);
			
			ie.textField(id,
					IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID)
					.set(amendParams[3]);
			
			ie.textField(id, IAmendmentsConst.amendRequest_Reason_TxtFld_ID)
					.set(amendParams[4]);
			
			ie.textField(id, IAmendmentsConst.amendRequest_Comment_TxtFld_ID)
					.set(amendParams[5]);
			
			if (GeneralUtil.isButtonExistsByValue(IClicksConst.nextBtn)) {
				ClicksUtil.clickButtons(IClicksConst.nextBtn);
			}
			
			if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
			{
				ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
			}

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

			retValue = true;
		}

		return retValue;

	}

	public static boolean issueAmendmentWithOptionalRe_Exec(
			String amendParams[], Hashtable<String, String> hashTable, Project proj)
			throws Exception {

		int rowIndex = -1;

		IE ie = IEUtil.getActiveIE();
		
		proj.initializeStep(amendParams[1]);
		
		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		FiltersUtil.filterListByLabel(
						IFiltersConst.grantManagement_ProjectName_Lbl,
						amendParams[0],IFiltersConst.exact);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				proj.getCurrentStepName());
		
		log.debug(rowIndex);
		
		
		if(rowIndex < 0)
		{
			log.error("Could Not fins step in List!");
			return false;
		}
		
		ie.body(id, ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(rowIndex).cell(0).link(1).click();
			
			
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		ie.selectList(id,
				IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID)
				.select(amendParams[2]);
		
		ie.textField(id,
				IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID)
				.set(amendParams[3]);
		
		ie.textField(id, IAmendmentsConst.amendRequest_Reason_TxtFld_ID)
				.set(amendParams[4]);
		
		ie.textField(id, IAmendmentsConst.amendRequest_Comment_TxtFld_ID)
				.set(amendParams[5]);

		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		TableRows rows = ie.div(attribute("class", ITablesConst.widgetDiv_class)).rows();

		for (TableRow tableRow : rows) {

			if (tableRow.checkbox(0).exists())
			{
				if(tableRow.checkbox(0).enabled())
				{
					if(hashTable.containsKey(tableRow.innerText()))
					{
						if(hashTable.get(tableRow.innerText()).equalsIgnoreCase("yes"))
						{
							tableRow.checkbox(0).set();						
						}
						else
						{
							tableRow.checkbox(0).clear();	
						}

					}
				}			
			}
		}
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
	public static boolean issueAmendInPlaceAmendmentWithOptionalRe_Exec(
			String amendParams[], Hashtable<String, String> hashTable, Project proj)
			throws Exception {

		int rowIndex = -1;

		IE ie = IEUtil.getActiveIE();
		
		proj.initializeStep(amendParams[1]);
		
		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		FiltersUtil.filterListByLabel(
						IFiltersConst.grantManagement_ProjectName_Lbl,
						amendParams[0],IFiltersConst.exact);

		ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				proj.getCurrentStepName());
		
		log.debug(rowIndex);
		
		if (rowIndex > -1) {
			ie.body(id, ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(rowIndex)
					.link(title, "/View Submission:/").click();
		} else {
			log.error("Could Not find step in List!");
			return false;
		}
				
		Assert.assertTrue(GeneralUtil.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk), "FAIL: Request Amendment Link not available!");
		
		ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);
		
			
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		ie.selectList(id,
				IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID)
				.select(amendParams[2]);
		
		ie.textField(id,
				IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID)
				.set(amendParams[3]);
		
		ie.textField(id, IAmendmentsConst.amendRequest_Reason_TxtFld_ID)
				.set(amendParams[4]);
		
		ie.textField(id, IAmendmentsConst.amendRequest_Comment_TxtFld_ID)
				.set(amendParams[5]);

		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		TableRows rows = ie.table(id,ITablesConst.fundingOpp_OptionalStepRe_ExecSelectionTableId).rows();
		
		for (TableRow tableRow : rows) {
			
			if(tableRow.checkbox(0).enabled())
			{
				if(hashTable.containsKey(tableRow.innerText()))
				{
					if(hashTable.get(tableRow.innerText()).equalsIgnoreCase("yes"))
					{
						tableRow.checkbox(0).set();						
					}
					else
					{
						tableRow.checkbox(0).clear();	
					}
				}
			}			
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}

	public static boolean doesAmendmentRequestIconExists(String amendParams[], Project proj)
			throws Exception {

		int rowIndex = -1;

		IE ie = IEUtil.getActiveIE();
		
		proj.initializeStep(amendParams[1]);

		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		FiltersUtil.filterListByLabel(
						IFiltersConst.grantManagement_ProjectName_Lbl,
						amendParams[0],IFiltersConst.exact);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				proj.getCurrentStepName());

		if (rowIndex > -1) {
			
			if (ie.body(id,
					ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(
					rowIndex).cell(0).image(1).exists()) {
				return true;
			}
		}

		return false;
	}

	public static boolean doesAmendmentRequestIconExists_New(Project proj,String stepName)
			throws Exception {

		int rowIndex = -1;

		IE ie = IEUtil.getActiveIE();
		
		proj.initializeStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		FiltersUtil
				.filterListByLabel(
						IFiltersConst.grantManagement_ProjectName_Lbl,
						proj.getProjFullName(),IFiltersConst.exact);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				proj.getCurrentStepName());

		if (rowIndex > -1) {
			
			if (ie.body(id,
					ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(
					rowIndex).cell(0).image(1).exists()) {
				return true;
			}
		}

		return false;
	}

	public static enum EArrParams {
		proj_Letter, proj_FullName, step_FormName, applicantName, prog_FullName
	}
	
	public static enum EStepAmendIcons {
		cancel, details, submission
	}
	
	public static enum EAmendListIcons {
		details, submission, cancel 
	}
	
	public static enum EAmendViews {
		all, progress, complete, cancel, trnsfr
	}
	
	public static boolean openAmendmentListAndFilter(Project proj, String stepName) throws Exception {

		if(!isAmendmentListOpen())
		{
			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);			
		}		
		
		proj.initializeStep(stepName);

		String[] lstTable = { proj.getProjFullName(), proj.getCurrentStepName(),
				proj.getOrgFullName(),proj.getProgFullName()};

		int rowIndex = TablesUtil.findInTable(ITablesConst.amendmentsTableId,
				lstTable);
		
		if(rowIndex != 0)
		{
			return filterAmendmentList(proj,stepName,IFiltersConst.amendments_AllAmendmentsView);
		}
		
		return true;
	}
	
	public static boolean openAmendedSubmission(Project proj,String stepName) throws Exception {
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.gpa_AmendmentStatus_Lbl, "", IFiltersConst.amendments_InProgressView);

		String[] lstTable = { proj.getProjFullName(), proj.getCurrentStepName(),
				proj.getOrgFullName(),proj.getProgFullName()};

		int rowIndex = TablesUtil.findInTable(ITablesConst.amendmentsTableId,
				lstTable);
		
		if(rowIndex < 0)
		{
			log.error("Could not find entry in List!");
			return false;
		}
		
		TablesUtil.openInTableByImageLinkAndIndex(ITablesConst.amendmentsTableId,rowIndex,0,1);		
		
		
		return true;
	}
	
	public static boolean isAmendmentListOpen() throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		return ie.body(id,ITablesConst.amendmentsTableId).exists();		
	}

	public static boolean isAmendmentCancelIconExists(String[] arrParams, Project proj)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
		
		if(filterAmendmentList(proj,arrParams[EArrParams.step_FormName.ordinal()], IFiltersConst.amendments_InProgressView)) {
			
			for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
			{
				if (row.image(alt, "/" + IAmendmentsConst.amendList_Cancel_LnkTitle + "/").exists()) {
					return true;
				}
			}
			log.error("FAIL: No Cancel Icon");
		}				

		return false;
	}
	
	public static boolean isStepAmendmentCancelIconExists(Project proj, String stepName, EAmendViews view, String version, String verCriteria) throws Exception {
		
		try {

			IE ie = IEUtil.getActiveIE();

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
			
			if(!filterAmendmentList(proj,stepName, IFiltersConst.amendments_Views[view.ordinal()]))
			{
				return false;
			}
			
			if(FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_SubmissionVersion_Lbl, "", verCriteria))
			{
				for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
				{
					if (row.image(alt, "/" + IAmendmentsConst.amendList_Cancel_LnkTitle + "/").exists()) {
						return true;
					}
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
		return false;
	}

	public static boolean isViewAmendmentIconExists(Project proj, String stepName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
		
		if(!filterAmendmentList(proj,stepName, IFiltersConst.amendments_AllAmendmentsView))
		{
			log.error("Could filter Amendment List!");
			return false;
		}
		
		
		for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
		{
			if (row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").exists()) {
				return true;
			}
		}
		log.error("No View Amendment Icon");
					

		return false;
	}

	public static boolean isViewAmendmentSubmissionIconExists(Project proj,String stepName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
		
		if(filterAmendmentList(proj,stepName, IFiltersConst.amendments_InProgressView)) {
			
			for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
			{
				if (row.link(title, "/" + IAmendmentsConst.amendList_Open_LnkTitle + proj.getProjFullName() + "/").exists()) {
					return true;
				}
			}
			log.error("No View Or Edit Submission Icon");
		}				

		return false;
	}
	
	public static boolean isDefaultPOAmendmentCategaoryExists(Project proj,
			String stepName) throws Exception {

		boolean retValue;

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

		if (!filterAmendmentList(proj, stepName,
				IFiltersConst.amendments_InProgressView)) {
			log.error("Could filter Amendment List!");
			return false;
		}

		retValue = TablesUtil.findInTable(ITablesConst.amendmentsTableId,
				IAmendmentsConst.defaultPO_AmendmentCategory);

		if (!retValue) {
			log.error("No Default PO Amendment Category in table row");

			retValue = false;
		}

		return retValue;
	}

	public static boolean isDefaultFOAmendmentCategaoryTypeExists(Project proj,
			String stepName) throws Exception {

		boolean retValue;

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

		if (!filterAmendmentList(proj, stepName,
				IFiltersConst.amendments_RequestedView)) {
			log.error("Could filter Amendment List!");
			return false;
		}

		retValue = TablesUtil.findInTable(ITablesConst.amendmentsTableId,
				IAmendmentsConst.defaultFO_AmendmentCategory);

		if (!retValue) {
			log.error("No Default FO Amendment Category in table row");

			retValue = false;
		}

		return retValue;
	}

	public static String[] initializeAmendmentParamsNoOptionalRex(Project proj, String amender,String stepName) throws Exception {
		String dd = GeneralUtil.setDayofMonth(3);
			
			return new String[] {proj.getProjFullName(),stepName,amender, dd,"Test Assign Group as an Amender", "This a Comment"};
	}
	
	public static boolean filterAmendmentList(Project proj, String stepName, String view) throws Exception {


		IE ie = IEUtil.getActiveIE();

		int rowIndex = -1;
		
		proj.initializeStep(stepName);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropDown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,proj.getProjFullName());

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,proj.getOrgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, proj.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,proj.getProgFullName());

		hashTableDropDown.put(IFiltersConst.gpa_AmendmentStatus_Lbl,
				view);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropDown, false);

		String[] lstTable = { proj.getProjFullName(), proj.getCurrentStepName(),
				proj.getOrgFullName(),proj.getProgFullName()};

		rowIndex = TablesUtil.findInTable(ITablesConst.amendmentsTableId,
				lstTable);
		
		if (rowIndex > -1) {
			
			for (TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows()) {
				
				if(row.innerText().contains(proj.getCurrentStepName())) {
					return true;
				}				
			}		
		}
		
		log.error("the Submission is not listed " + proj.getCurrentStepName());
		
		return false;
	}
	
	
	
	public static boolean changeAmenderForStepAmendment(Project proj, String stepName, Users fromUser, Users toUser) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.table(id,ITablesConst.amendedSubmissionsTableId).exists())
		{
			if(!openStepAmendmentDetails(proj,stepName,"",""))
			{
				log.error("could not open Amendment List");
				return false;
			}
		}
		
		if(!openAmendedSubmissionsIcon(proj,fromUser,EStepAmendIcons.details))
		{
			log.error("could not open Amendment Details");
			
			return false;
		}
		
		if(!selectNewAmender(toUser.getUserFullId()))
		{
			log.error("Could not find new Amender in Dropdown");
			return false;
		}
			 
		ie.textField(id,IAmendmentsConst.amendmentDetails_AmendmentComment_TextField_Id).set("Amender changed to: ".concat(toUser.getUserFullId()));
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		return true;
	}
	
	/**
	 * @param proj
	 * @param newAmender
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean changeAmender(Project proj,String newAmender) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
		{
			if (row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").exists()) {
				
				row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").click();
				
				if (GeneralUtil.selectInDropdownList(IAmendmentsConst.amendmentDetails_Amender_DropDwnFld_Id, newAmender)) {
					
					ie.textField(id,IAmendmentsConst.amendmentDetails_AmendmentComment_TextField_Id).set("Amender changed to: " + newAmender);
				}
				else {
					ie.textField(id,IAmendmentsConst.amendmentDetails_AmendmentComment_TextField_Id).set(newAmender + " Not in the list!");
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					return false;
				}				
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				
				ClicksUtil.clickButtons(IClicksConst.backToAmendListBtn);
				
				return true;
			}
		}
		log.error("Error finding submission in List");
		
		return false;
	}
	
	public static boolean checkAmender(Project proj,String newAmender) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();
		
		for(TableRow row : tDiv.body(id,ITablesConst.amendmentsTableId).rows())
		{
			if (row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").exists()) {
				
				row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").click();
				
				log.debug(GeneralUtil.getSelectedItemValueInDropdwonById(IAmendmentsConst.amendmentDetails_Amender_DropDwnFld_Id));
				
				if(GeneralUtil.getSelectedItemValueInDropdwonById(IAmendmentsConst.amendmentDetails_Amender_DropDwnFld_Id).startsWith(newAmender)) {
					return true;
				}
				else {
					ie.textField(id,IAmendmentsConst.amendmentDetails_AmendmentComment_TextField_Id).append("\n\n" + newAmender + " --- Not in the list!");
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					return false;
				}
			}
		}
		log.error("Error finding submission in List");
		
		return false;
	}
	
	public static boolean selectNewAmender(String newAmender) throws Exception {
		
		try {
			
			return GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Amender_DropDwnFld_Id, newAmender);		
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Error",e);
		}
		
		return true;
	}

	public static boolean openAmendmentDetails(String version, String verCriteria) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		//FiltersUtil.extraFilterListByLabel(IFiltersConst.gpa_AmendmentId_Lbl, version, verCriteria);
		
//		if(!GeneralUtil.selectFullStringInDropdownListByLbl(IFiltersConst.gpa_LastVersion_Lbl, verCriteria))
//		{
//			log.error("Could not find Filter by Label")
//		}
		
		for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
		{
			if (row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").exists()) {
				
				row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").click();
				
				return true;
			}
		}		
		return false;
	}

	public static boolean openAmendmentDetailsNew(String version,String status) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.gpa_AmendmentStatus_Lbl, "", status);
		
		for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
		{
			if (row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").exists()) {
				
				row.link(title, "/" + IAmendmentsConst.amendList_Transfer_LnkTitle + "/").click();
				
				return true;
			}
		}		
		return false;
	}
	
	public static boolean isAmendmentSubmisstionInReadOnlyMode(Project proj,String stepName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();	
		
		String subProject = proj.getProjFullName();
		
		if(stepName.contains("Initial"))
		{
			subProject = subProject.concat(" - Claim ".concat(proj.getClaimNumber().toString()));
		}
		
		if(filterAmendmentList(proj,stepName, IFiltersConst.amendments_InProgressView)) {
			
			for(TableRow row : ie.body(id,ITablesConst.amendmentsTableId).rows())
			{					
				if(row.link(title, "/" + IAmendmentsConst.amendList_Open_LnkTitle.concat(subProject) + "/").exists()) {
					
					row.link(title, "/" + IAmendmentsConst.amendList_Open_LnkTitle.concat(subProject) + "/").click();
					
					return ie.textField(0).readOnly();
				}	
				else {

					log.error("Error finding Open Form Icon for Submission");
					
					return false;
				}		
			}			
		}
		log.error("Error finding submission in List");
		
		return false;
	}
	
	public static boolean openStepAmendmentRequest(Project proj) throws Exception {
		
 		IE ie = IEUtil.getActiveIE();
		
		if(ie.form(id, IAmendmentsConst.stepAmend_AmendmentRequest_FormId).exists())
		{
			return true;
		}
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		if(!TablesUtil.findInTable(ITablesConst.projectsTableId, proj.getProjFullName()))
		{			
			if(!FiltersUtil.filterListByProject(proj))
			{
				log.error("Could not Filter Projects List!");
				
				return false;
			}
		}
		
		int index = TablesUtil.getRowIndex(ITablesConst.projectsTableId, proj.getProjFullName());
		
		if(index < 0)
		{
			log.error("Could not find Project in Project List after Filtering, the Project is: ".concat(proj.getProjFullName()));
			
			return false;
		}
		
		Divs divs = ie.divs(attribute("class", IClicksConst.projects_OptionsClick_DivClass));
		
		for (Div div : divs){
			if (div.id().contains("caseDataTable:"+index)){
				
				if (div.link(title,IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(proj.getProjFullName())).exists()){
					div.link(title,IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(proj.getProjFullName())).click();
					return true;
				}
			}
		}
		
		
		log.error("Could not click Request Amendment Image in Projects List!");
		
		return false;
		
	}
	
	public static boolean requestStepAmendment(Project proj, String[] amenders, String date, String reason, String comments, boolean isOptionalReExec, String paStartSchedule) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.form(id, IAmendmentsConst.stepAmend_AmendmentRequest_FormId).exists())
		{
			if(!openStepAmendmentRequest(proj))
			{
				return false;
			}
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, proj.getCurrentStepName()))
		{
			log.error("Could not find the Step to Amend: ".concat(proj.getCurrentStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		if((!paStartSchedule.equals("")) && (null != paStartSchedule))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_ClaimToStart_DropDownId, paStartSchedule))
			{
				log.error("Could not find the Claim to start Amendment with");
				return false;		
			}
			GeneralUtil.takeANap(2.0);
			
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(amenders[0].equals("Step"))
		{
			if(amenders[1].equals("All Step Staff"))
			{
				if(!ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).exists())
				{
					log.error("Could not find Check Box");
					return false;
				}
				
				ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).set();				
			}
			else
			{
				for(int x=1; x<amenders.length; x++)
				{
					log.debug(amenders[x]);
					if(!GeneralUtil.selectExactfromM2M(IAmendmentsConst.stepAmend_NonShared_AAFS_M2MId, IAmendmentsConst.stepAmend_NonShared_SAFS_M2MId, amenders[x]))
					{
						log.error("Could not find amender");
						return false;
					}
				}
			}
		}
		else if(amenders[0].equals("Submission"))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amenders[1]))
			{
				log.error("Could not select amender1 - ".concat(amenders[1]));
				return false;			
			}
		}
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).exists())
		{
			log.error("Could not find Date Field1");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).set(date);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).exists())
		{
			log.error("Could not find Reason text field");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).set(reason);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).exists())
		{
			log.error("could not find Comment text field!");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).set(comments);
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		if(isOptionalReExec)
		{
			return true;
		}
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
		}		
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			log.error("To Find enabled Issue Amendment Button");		
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
public static boolean requestPAStepAmendment(Project proj, String Claim, String[] amenders, String date, String reason, String comments, boolean isOptionalReExec, String paStartSchedule) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.form(id, IAmendmentsConst.stepAmend_AmendmentRequest_FormId).exists())
		{
			if(!openStepAmendmentRequest(proj))
			{
				return false;
			}
		}
		
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_RequestStepAmendmentAt_DropdownId, Claim))
		{
			log.error("Could not find the Step to Amend: ".concat(Claim));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, proj.getCurrentStepName()))
		{
			log.error("Could not find the Step to Amend: ".concat(proj.getCurrentStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		if((!paStartSchedule.equals("")) && (null != paStartSchedule))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_ClaimToStart_DropDownId, paStartSchedule))
			{
				log.error("Could not find the Claim to start Amendment with");
				return false;		
			}
			GeneralUtil.takeANap(2.0);
			
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(amenders[0].equals("Step"))
		{
			if(amenders[1].equals("All Step Staff"))
			{
				if(!ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).exists())
				{
					log.error("Could not find Check Box");
					return false;
				}
				
				ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).set();				
			}
			else
			{
				for(int x=1; x<amenders.length; x++)
				{
					log.debug(amenders[x]);
					if(!GeneralUtil.selectfromM2M_New(IAmendmentsConst.stepAmend_NonShared_AAFS_M2MId, IAmendmentsConst.stepAmend_NonShared_SAFS_M2MId, amenders[x]))
					{
						log.error("Could not find amender");
						return false;
					}
				}
			}
		}
		else if(amenders[0].equals("Submission"))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amenders[1]))
			{
				log.error("Could not select amender1 - ".concat(amenders[1]));
				return false;			
			}
		}
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).exists())
		{
			log.error("Could not find Date Field1");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).set(date);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).exists())
		{
			log.error("Could not find Reason text field");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).set(reason);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).exists())
		{
			log.error("could not find Comment text field!");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).set(comments);
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		if(isOptionalReExec)
		{
			return true;
		}
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
		}		
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			log.error("To Find enabled Issue Amendment Button");		
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
	public static boolean requestSubProjAmendment(Project proj, String[] amenders, String date, String reason, String comments, boolean isOptionalReExec, String paStartSchedule) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.form(id, IAmendmentsConst.stepAmend_AmendmentRequest_FormId).exists())
		{
			if(!openStepAmendmentRequest(proj))
			{
				return false;
			}
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_RequestStepAmendmentAt_DropdownId, paStartSchedule))
		{
			log.error("Could not find the Sub Project to Amend ".concat(paStartSchedule));
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, proj.getCurrentStepName()))
		{
			log.error("Could not find the Step to Amend ".concat(proj.getCurrentStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(amenders[0].equals("Step"))
		{
			if(amenders[1].equals("All Step Staff"))
			{
				if(!ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).exists())
				{
					log.error("Could not find Check Box");
					return false;
				}
				
				ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).set();				
			}
			else
			{
				for(int x=1; x<amenders.length; x++)
				{
					log.debug(amenders[x]);
					if(!GeneralUtil.selectfromM2M_New(IAmendmentsConst.stepAmend_NonShared_AAFS_M2MId, IAmendmentsConst.stepAmend_NonShared_SAFS_M2MId, amenders[x]))
					{
						log.error("Could not find amender");
						return false;
					}
				}
			}
		}
		else if(amenders[0].equals("Submission"))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amenders[1]))
			{
				log.error("Could not select amender1 - ".concat(amenders[1]));
				return false;			
			}
		}
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).exists())
		{
			log.error("Could not find Date Field1");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).set(date);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).exists())
		{
			log.error("Could not find Reason text field");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).set(reason);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).exists())
		{
			log.error("could not find Comment text field!");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).set(comments);
		
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		if(isOptionalReExec)
		{
			return true;
		}
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
		}		
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			log.error("To Find enabled Issue Amendment Button");		
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
	public static boolean requestStepAmendmentWithSubProjectReExecute(Project proj, String[] amenders, String date, String reason, String comments, boolean isOptionalReExec, String paStartSchedule) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.form(id, IAmendmentsConst.stepAmend_AmendmentRequest_FormId).exists())
		{
			if(!openStepAmendmentRequest(proj))
			{
				return false;
			}
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, proj.getCurrentStepName()))
		{
			log.error("Could not find the Step to Amend");
			return false;		
		}
		
		GeneralUtil.takeANap(2.5);
		
		if((!paStartSchedule.equals("")) && (null != paStartSchedule))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_ClaimToStart_DropDownId, paStartSchedule))
			{
				log.error("Could not find the Claim to start Amendment with");
				return false;		
			}
			GeneralUtil.takeANap(2.5);
			
		}
		
		GeneralUtil.takeANap(2.5);
		
		if(amenders[0].equals("Step"))
		{
			if(amenders[1].equals("All Step Staff"))
			{
				if(!ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).exists())
				{
					log.error("Could not find Check Box");
					return false;
				}
				
				ie.checkbox(id,IAmendmentsConst.stepAmend_AmendForAllStepStaff_CheckboxId).set();				
			}
			else
			{
				for(int x=1; x<amenders.length; x++)
				{
					log.debug(amenders[x]);
					if(!GeneralUtil.selectfromM2M_New(IAmendmentsConst.stepAmend_NonShared_AAFS_M2MId, IAmendmentsConst.stepAmend_NonShared_SAFS_M2MId, amenders[x]))
					{
						log.error("Could not find amender");
						return false;
					}
				}
			}
		}
		else if(amenders[0].equals("Submission"))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amenders[1]))
			{
				log.error("Could not select amender1 - ".concat(amenders[1]));
				return false;			
			}
		}
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).exists())
		{
			log.error("Could not find Date Field1");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID).set(date);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).exists())
		{
			log.error("Could not find Reason text field");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Reason_TxtFld_ID).set(reason);
		
		if(!ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).exists())
		{
			log.error("could not find Comment text field!");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendRequest_Comment_TxtFld_ID).set(comments);
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).exists())
		{
			ie.selectList(id,IAmendmentsConst.amendRequest_Category_DropDwnFld_ID).select("Administrative");
		}
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		if(isOptionalReExec)
		{
			return true;
		}
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
		}		
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			log.error("Can not Find enabled Issue Amendment Button");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
	public static boolean configureSubProjectsReExecute(Project proj,Hashtable<String,Boolean> subOptional) throws Exception {
		
		Enumeration<String> keyStr;
		Enumeration<Boolean> elemStr;
		
		Div div = TablesUtil.widgetDiv();
		
		//Div div = ie.div(id, "optionalReexecuteSelectionForm:optionalReexecuteTable:0:expandedSubProjectReexecutionPlanTable_content");
		
		keyStr = subOptional.keys();
		elemStr = subOptional.elements();	
		
		while(keyStr.hasMoreElements())
		{
			
			proj.setSubProjName(keyStr.nextElement());
			
			log.warn(proj.getSubProjName());
			
			Boolean bol = elemStr.nextElement();
			
			HtmlElements eles = div.htmlElements(tag, "dt");
			
			if (eles.length() == 2)
			{
				eles.get(0).checkbox(0).clear();
			}
			else
			{			
				for (HtmlElement ele : eles) {
					
					log.warn(ele.innerText());
					
					if(ele.innerText().equals(proj.getSubProjName()))
					{
						if(!ele.checkbox(0).enabled())
						{
							log.error("The CheckBox is disabled");						
						}
						if(bol)
						{
							ele.checkbox(0).set();
						}
						else
						{
							ele.checkbox(0).clear();						
						}
						
						break;
					}				
				}
			}
			
			GeneralUtil.takeANap(1.0);
		}		
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn) && !GeneralUtil.isButtonEnabled(IClicksConst.postAwardDetailsBtn))
		{
			log.error("To Find enabled Issue Amendment Button");	
			
			return false;
		}
		
		GeneralUtil.takeANap(1.0);
		
		if(GeneralUtil.isButtonEnabled(IClicksConst.postAwardDetailsBtn))
		{
			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
	public static boolean configureOptionalReExecute(Project proj, Hashtable<String,String> stepOptional, boolean isPaIncluded) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		Enumeration<String> keyStr;
		Enumeration<String> elemStr;		
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn))
		{
			log.error("To Find enabled Issue Amendment Button");
			return false;
		}
		
		TableRows rows = ie.tables(id,IAmendmentsConst.stepAmend_OptionalStepReexec_TableId).rows();
		
		keyStr = stepOptional.keys();
		elemStr = stepOptional.elements();		
		
		while(keyStr.hasMoreElements())
		{
			proj.setCurrentStepName(keyStr.nextElement());
			
			String val = elemStr.nextElement();
			
			for (TableRow tableRow : rows) {
				
				if(!tableRow.innerText().endsWith(proj.getCurrentStepName()))
				{
					log.error("could not find step in Optional Step re-execute");
				}
				
				if(tableRow.innerText().endsWith(proj.getCurrentStepName()))
				{
					if(!tableRow.selectList(0).enabled())
					{
						log.error("The List is disabled");
						return false;
					}
					tableRow.selectList(0).select(val);
					
					break;
				}				
			}			
		}
		
		if(isPaIncluded)
		{
			return true;
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		
		return true;
	}
	
	public static boolean expandPASubStepsOptional() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(ie.div(id,IAmendmentsConst.stepAmend_Optional_PA_StepReexec_DivId).exists())
		{			
			return true;
		}
		
		if(!GeneralUtil.isButtonExistsByValue(IClicksConst.postAwardDetailsBtn))
		{
			log.error("Could not get to post-award Optional Step Re-Execution Selection!");
			return false;
		}
		
		if(!GeneralUtil.isButtonEnabled(IClicksConst.postAwardDetailsBtn))
		{
			log.error("Can not click Post Award Details Button because it is Disabled!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);
		
		return true;
	}
	
	public static boolean configurePASubStepsOptionaRe_Exec(List<Hashtable<String,Boolean>> hashList) throws Exception {
		
		if(!expandPASubStepsOptional())
		{
			log.error("cound not find PA Sub Steps to Configure Optional Re-Execute");
			return false;
		}
		
		if(hashList.isEmpty() || null == hashList)
		{
			log.warn("The List contains no Hashtable<>!");
			
			return false;
		}
		
		for (Hashtable<String, Boolean> hashtable : hashList) {
			
			tooglePASupStepsRe_Execute(hashtable,hashList.indexOf(hashtable));
			
		}
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
		return true;
	}
	
	public static boolean tooglePASupStepsRe_Execute(Hashtable<String, Boolean> hashtable, int claimIndex) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		TableBody body = ie.div(id,IAmendmentsConst.stepAmend_Optional_PA_StepReexec_DivId).body(id,IAmendmentsConst.stepAmend_Optional_PA_StepReexec_TableId);
		
		for (TableRow tableRow : body.rows()) {
			
			log.warn(tableRow.innerText());
			
			if(tableRow.checkbox(claimIndex).enabled())
			{
				if(hashtable.containsKey(tableRow.innerText()))
				{
					if(hashtable.get(tableRow.innerText()))
					{
						tableRow.checkbox(claimIndex).set();
					}
					else if(!hashtable.get(tableRow.innerText()))
					{
						tableRow.checkbox(claimIndex).clear();
					}					
				}
			}			
		}
		
		return true;
	}
	
	public static int getEntryIndexFromAmendedSubs(Project proj, Users user) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(ie.body(id,ITablesConst.amendedSubmissionsTableId).exists())
		{
			return TablesUtil.getRowIndex(ITablesConst.amendedSubmissionsTableId, user.getUserFullId());
		}		
		
		return -1;		
	}
	
	public static boolean openStepAmendmentDetails(Project proj, String stepName,String version, String verCriteria) throws Exception {
		
		IE ie = IEUtil.getActiveIE();		
		
		if(!isViewAmendmentIconExists(proj,stepName))
		{
			log.error("FAIL: could not find View Detail Icon!");
			return false;
		}
		
		if(!openAmendmentDetails(version,verCriteria))
		{
			log.error("FAIL: Could not open Amendment Details");
			return false;
		}
		
		if(!ie.body(id,ITablesConst.amendedSubmissionsTableId).exists())
		{
			log.error("FAIL: did not get to Step Amendment Details");
			return false;
		}
		
		return true;
	}
	
public static boolean openAmendedStepAmendmentDetails(Project proj, String stepName,String version, String verCriteria) throws Exception {
		
		if(!isViewAmendmentIconExists(proj,stepName))
		{
			log.error("FAIL: could not find View Detail Icon!");
			return false;
		}
		
		if(!openAmendmentDetails(version,verCriteria))
		{
			log.error("FAIL: Could not open Amendment Details");
			return false;
		}
		
			
		return true;
	}
	
	public static boolean openAmendedSubmissionsIcon(Project proj, Users user, EStepAmendIcons icon) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		int index = getEntryIndexFromAmendedSubs(proj,user);
		
		if(index < 0)
		{
			return false;
		}
		
		if(!ie.body(id,ITablesConst.amendedSubmissionsTableId).row(index).cell(icon.ordinal()).link(0).exists())
		{
			log.error("FAIL: Error occur, Could not find Amended Submissions List!");	
			return false;
		}
		
		ie.body(id,ITablesConst.amendedSubmissionsTableId).row(index).cell(icon.ordinal()).link(0).click();
		
		switch (icon) {
		case cancel: {
			
			return GeneralUtil.isButtonExistsByValue(IClicksConst.cancelAmendBtn);
		}
		case details: {
			
			return GeneralUtil.isButtonExistsByValue(IClicksConst.saveBtn);
		}
		case submission: {
			
			return GeneralUtil.isLinkExistsByTxt(IClicksConst.backToStepAmendListLnk);
		}			
		
		}
		return false;
	}
	
	public static boolean openStepAmendIconFromAmendmentList(Project proj, String stepName, EStepAmendIcons icon, String amendView) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
		
		filterAmendmentList(proj, stepName, amendView);
		
		if(TablesUtil.findHowManyInTable(ITablesConst.amendmentsTableId, proj.getCurrentStepName()) < 1)
		{
			log.error("Zero entries in Table");
			return false;
		}
		
		if(!ie.body(id,ITablesConst.amendmentsTableId).row(0).cell(0).link(icon.ordinal()).exists())
		{
			log.error("FAIL: Error occur, Could not find Amended Submissions List!");	
			return false;
		}
		
		ie.body(id,ITablesConst.amendmentsTableId).row(0).cell(0).link(icon.ordinal()).click();
		
		switch (icon) {
		case cancel: {
			
			return GeneralUtil.isButtonExistsByValue(IClicksConst.cancelAmendBtn);
		}
		case details: {
			
			return GeneralUtil.isButtonExistsByValue(IClicksConst.saveBtn);
		}
		case submission: {
			
			return GeneralUtil.isLinkExistsByTxt(IClicksConst.backToStepAmendListLnk);
		}			
		
		}
		return false;
	}
	
	public static boolean openStepAmendIconFromAmendmentList(Project proj, String stepName, EAmendListIcons icon, String amendView) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
		
		filterAmendmentList(proj, stepName, amendView);
		
		if(TablesUtil.findHowManyInTable(ITablesConst.amendmentsTableId, proj.getCurrentStepName()) < 1)
		{
			log.error("Zero entries in Table");
			return false;
		}
		
		int count = ie.body(id,ITablesConst.amendmentsTableId).row(0).cell(0).links().length();
		
		if(count == 0)
		{
			log.error("FAIL: Error occur, Could not find Amended Submissions List!");	
			return false;
		}
		
		ie.body(id,ITablesConst.amendmentsTableId).row(0).cell(0).link(icon.ordinal()).click();
		
		switch (icon) {
		case cancel: {
			
			
			
			return GeneralUtil.isButtonExistsByValue(IClicksConst.cancelAmendBtn);
		}
		case details: {
			
			
			
			return GeneralUtil.isButtonExistsByValue(IClicksConst.saveBtn);
		}
		case submission: {
			
			
			
			return GeneralUtil.isLinkExistsByTxt(IClicksConst.backToStepAmendListLnk);
		}			
		
		}
		return false;
	}
	
	public static boolean cancelAmendedSubmission(Project proj, Users user, String stepName,String version, String verCriteria) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openStepAmendmentDetails(proj,stepName,version,verCriteria))
		{
			log.error("FAIL: could not open Amended Submissions Page");
			return false;
		}
		
		if(!openAmendedSubmissionsIcon(proj,user,EStepAmendIcons.cancel))
		{
			log.error("FAIL: could not open Cancel Amendment Page");
			return false;
		}
		
		ie.textField(id, IAmendmentsConst.stepAmend_CancelSub_Reason_TxtFld_ID).set("Cancel One Amendment for Step: " + proj.getCurrentStepName());
		
		ClicksUtil.clickButtons(IClicksConst.cancelAmendBtn);
		
		if(!openStepAmendmentDetails(proj,stepName,version,verCriteria))
		{
			log.error("FAIL: could not open Amended Submissions Page");
			return false;
		}
		
		if(openAmendedSubmissionsIcon(proj,user,EStepAmendIcons.cancel))
		{
			log.error("FAIL: should not open Cancel Amendment Page After cancel");
			return false;
		}
		
		return true;
	}
	
	public static boolean cancelStepAmendment(Project proj,String stepName,String version, String verCriteria) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

		
		filterAmendmentList(proj, stepName, IFiltersConst.amendments_InProgressView);
		
		if(TablesUtil.findHowManyInTable(ITablesConst.amendmentsTableId, proj.getCurrentStepName()) < 1)
		{
			log.error("Zero entries in Table");
			return false;
		}
		
//		if(!openStepAmendIconFromAmendmentList(proj,stepName,EAmendListIcons.cancel,IFiltersConst.amendments_InProgressView))
//		{
//			return false;
//		}
			
		ie.body(id,ITablesConst.amendmentsTableId).row(0).cell(0).link(id, "listAmendments:listAmendmentsTable:0:j_id_3u").click();		
		
		
		ie.textField(id, IAmendmentsConst.stepAmend_CancelSub_Reason_TxtFld_ID).set("Cancel One Amendment for Step: " + proj.getCurrentStepName());
		
		ClicksUtil.clickButtons(IClicksConst.cancelAmendBtn);
		
		return true;
	}
	
	public static boolean editStepAmendmentComment(Project proj, Users user, String stepName,String version, String verCriteria) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openStepAmendmentDetails(proj,stepName,version,verCriteria))
		{
			log.error("FAIL: could not open Amended Submissions Page");
			return false;
		}
		
		if(!openAmendedSubmissionsIcon(proj,user,EStepAmendIcons.details))
		{
			log.error("FAIL: could not open Edit Amendment Page");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.stepAmend_View_Comment_TxtFld_ID).set("Adding Comment: " + proj.getCurrentStepName());
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);		
		
		ClicksUtil.clickButtons(IClicksConst.backToStepAmendListBtn);
		
		int index = getEntryIndexFromAmendedSubs(proj,user);
		
		if(index < 0)
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean isStepAmendmentSubmissionIconAvailable(Project proj, Users user, String stepName,String version, String verCriteria) throws Exception {
		
		if(!openStepAmendmentDetails(proj,stepName,version,verCriteria))
		{
			log.error("FAIL: could not open Amended Submissions Page");
			return false;
		}
		
		return openAmendedSubmissionsIcon(proj,user,EStepAmendIcons.submission);		
	}
	
	public static boolean isStepAmendmentSubmissionReadWrite(Project proj, Users user, String stepName,String version, String verCriteria) throws Exception {
		
		if(!openStepAmendmentDetails(proj,stepName,version,verCriteria))
		{
			log.error("FAIL: could not open Amended Submissions Page");
			return false;
		}
		
		if(!openAmendedSubmissionsIcon(proj,user,EStepAmendIcons.submission))
		{
			log.error("FAIL: could not open Amended Submission e.Form");
			return false;
		}
		
		ClicksUtil.clickLinks("/Summary/");
		
		boolean retVal = GeneralUtil.isButtonEnabled(IClicksConst.submitBtn);
		
		ClicksUtil.clickLinks(IClicksConst.backToStepAmendListLnk);
		
		int index = getEntryIndexFromAmendedSubs(proj,user);
		
		if(index < 0)
		{
			Assert.fail("FAIL: Could not find entry after Check Submission ReadWrite!");
		}
		
		return retVal;
	}
	
	public static boolean amendNowApplicantAmendment(String obj, String amender,String reason) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openApplicantAmendment(obj,EAmendmentOptions.REQUEST))
		{
			log.error("failed to open Amendment Request");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.foAmendmentRequest_Amender_DropDwnFld_Id, amender))
		{
			log.error("Could not find the Amender: ".concat(amender));
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_AmendmentReason_TextField_Id).set(reason);
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_AmendmentComment_TextField_Id).set("This is a Comment field!");
		
		ClicksUtil.clickButtons(IClicksConst.amendNowBtn);
		
		ClicksUtil.returnFromAnyForm();
		
//		if(ie.span(id,IAmendmentsConst.foAmendmentRequest_GlobalMessages_Span_Id).exists())
//		{
//			if(!ie.span(id,IAmendmentsConst.foAmendmentRequest_GlobalMessages_Span_Id).innerText().equalsIgnoreCase(IAmendmentsConst.foAmendNow_SuccessMessage))
//			{
//				log.error("No Confirmation messege!");
//				return false;
//			}
//		}
//		else if(!ie.form(id,"main:dFo:_idJsp5").exists())
//		{
//			log.error("No Submission form opened!");
//			return false;
//			
//		}
		
		return true;		
	}
	
	public static boolean requestApplicantAmendment(String obj, String amender,String reason) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openApplicantAmendment(obj,EAmendmentOptions.REQUEST))
		{
			log.error("failed to open Amendment Request");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.foAmendmentRequest_Amender_DropDwnFld_Id, amender))
		{
			log.error("Could not find the Amender: ".concat(amender));
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_AmendmentReason_TextField_Id).set(reason);
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_AmendmentComment_TextField_Id).set("This is a Comment field!");
		
		ClicksUtil.clickButtons(IClicksConst.requestAmendmentBtn);
		
		if(!ie.htmlElement(attribute("class", IAmendmentsConst.foAmendmentRequest_GlobalMessages_Span_Class)).innerText().equalsIgnoreCase(IAmendmentsConst.foAmendmentRequest_SuccessfullMessage))
		{
			log.error("No Confirmation message!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.backToSubmissionListBtn);
		
		return true;
	}
	
    public static boolean issueApplicantAmendment(String obj, String amender,String reason) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openApplicantAmendment(obj,EAmendmentOptions.REQUEST))
		{
			log.error("failed to open applicant amendment");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.foAmendmentRequest_Amender_DropDwnFld_Id, amender))
		{
			log.error("Could not find the Amender: ".concat(amender));
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_AmendmentReason_TextField_Id).set(reason);
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_AmendmentComment_TextField_Id).set("This is a Comment field!");
		
		ClicksUtil.clickButtons(IClicksConst.issueAmendmentBtn);
		
		if(!ie.htmlElement(attribute("class", IAmendmentsConst.foAmendmentRequest_GlobalMessages_Span_Class)).innerText().equalsIgnoreCase(IAmendmentsConst.foAmendNow_SuccessMessage))
		{
			log.error("No Confirmation message!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.backToSubmissionListBtn);
		
		return true;
	}
	
	public static boolean cancelApplicantAmendmentRequest(String obj, String reason) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openApplicantAmendment(obj,EAmendmentOptions.CANCEL))
		{
			log.error("failed to open Cancel Amendment Request");
			return false;
		}
		
		if(!ie.textField(id,IAmendmentsConst.foAmendmentRequest_CancelReason_TextField_Id).exists())
		{
			log.error("could not find Cancellation Request Reason Textbox");
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.foAmendmentRequest_CancelReason_TextField_Id).set(reason);
		
		ClicksUtil.clickButtons(IClicksConst.cancelApplicantAmendmentRequestBtn);
		
		if(!ie.span(id,IAmendmentsConst.foAmendmentRequest_GlobalMessages_Span_Id).innerText().equalsIgnoreCase(IAmendmentsConst.foAmendmentRequest_CancelMessage))
		{
			log.error("No Confirmation message!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.backToSubmissionListBtn);
		
		return true;
	}
	
	public static boolean openApplicantAmendment(String obj,EAmendmentOptions option) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		openFoList(EFOLists.SUBMISSIONS);
		
		int index = TablesUtil.getRowIndexWithDiv(ITablesConst.fOSubmissionsTableId, obj);
		
		if(index < 0)
		{
			log.error("could not find the Step in FO Submissions List: ".concat(obj));
			return false;
		}
		
		String imageAlt = IAmendmentsConst.EAmendmentIconsAlts[option.ordinal()].toString().concat(" - ").concat(obj);
		
		log.warn(imageAlt);
		
		if(!ie.body(id,ITablesConst.fOSubmissionsTableId).row(index).image(alt, IAmendmentsConst.EAmendmentIconsAlts[option.ordinal()].toString()).exists())
		{
			log.error("could not find the Icon for: ".concat(option.name()));
			return false;
		}
		
		ie.body(id,ITablesConst.fOSubmissionsTableId).row(index).image(alt, IAmendmentsConst.EAmendmentIconsAlts[option.ordinal()].toString()).click();		
		
		return true;
	}
	
	public static boolean changeAmenderOnAmendNow(Project proj, String newAmender, String stepName) throws Exception {
		
		if(!openAmendmentListAndFilter(proj,stepName))
		{
			log.error("could not open Amendment List and filter");
			return false;
		}
		
		if(!openAmendmentDetailsNew("", "In Progress"))
		{
			log.error("could not open Amendment Details!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Amender_DropDwnFld_Id, newAmender))
		{
			log.error("could not select Amender: ".concat(newAmender));
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		if(!GeneralUtil.isButtonExistsByValue(IClicksConst.backToAmendListBtn))
		{
			log.error("Could not find Back to Button!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.backToAmendListBtn);		
		
		return true;
	}
	
	public static boolean amendmentIconTextFormat(String amendmentIcontext)
			throws Exception {

		String[] arr = amendmentIcontext.split("\\s+");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String todayDate = sdf.format(Calendar.getInstance().getTime());

		for (int i = 0; i < arr.length; i++) {

			switch (i) {

			case 1:
				Pattern pattern = Pattern.compile(IAmendmentsConst.amendmentID_Format);

				Matcher matcher = pattern.matcher(arr[1]);

				boolean isFound = matcher.find();

				Assert.assertEquals(isFound, true);

				break;
			case 2:

				Assert.assertEquals(IAmendmentsConst.Amendmentformat_HardCoded_Value.equals(arr[2]), true);

				break;

			case 3:
				
				Assert.assertEquals(IAmendmentsConst.Amendmentformat_HardCoded_Value1.equals(arr[3]), true);
					
				break;

			case 4:

				Assert.assertEquals(todayDate.equals(arr[4]), true);

				break;

			case 5:

				Pattern pattern1 = Pattern.compile(IAmendmentsConst.AmendmentTime_Format);

				Matcher matcher1 = pattern1.matcher(arr[5]);

				boolean isFound1 = matcher1.find();
				Assert.assertEquals(isFound1, true);
				
				break;

			case 6:
				Pattern pattern2 = Pattern.compile(IAmendmentsConst.AmendmentTime_Format1);

				Matcher matcher2 = pattern2.matcher(arr[6]);

				boolean isFound2 = matcher2.find();
				Assert.assertEquals(isFound2, true);
			default:
				
				break;
			}
		}

		return true;
	}
	
	public static boolean amendmentIconAutoReexexcuteTextFormat(
			String amendmentIcontext) throws Exception {

		String[] arr = amendmentIcontext.split("\\s+");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String todayDate = sdf.format(Calendar.getInstance().getTime());

		for (int i = 0; i < arr.length; i++) {

			switch (i) {

			case 1:
				Pattern pattern = Pattern
						.compile(IAmendmentsConst.amendmentID_Format);

				Matcher matcher = pattern.matcher(arr[1]);

				boolean isFound = matcher.find();

				Assert.assertEquals(isFound, true);

				break;
			case 2:

				Assert.assertEquals(
						IAmendmentsConst.Amendmentformat_HardCoded_Value
								.equals(arr[2]), true);

				break;

			case 3:

				Assert.assertEquals(
						IAmendmentsConst.Amendmentformat_Auto_ReExecuted_HardCoded_Value
								.equals(arr[3]), true);

				break;
			case 4:

				Assert.assertEquals(
						IAmendmentsConst.Amendmentformat_Auto_ReExecuted_HardCoded_Value1
								.equals(arr[4]), true);

				break;
			case 5:

				Assert.assertEquals(todayDate.equals(arr[5]), true);

				break;

			case 6:

				Pattern pattern1 = Pattern
						.compile(IAmendmentsConst.AmendmentTime_Format);

				Matcher matcher1 = pattern1.matcher(arr[6]);

				boolean isFound1 = matcher1.find();
				Assert.assertEquals(isFound1, true);

				break;

			case 7:
				Pattern pattern2 = Pattern
						.compile(IAmendmentsConst.AmendmentTime_Format1);

				Matcher matcher2 = pattern2.matcher(arr[7]);

				boolean isFound2 = matcher2.find();
				Assert.assertEquals(isFound2, true);
			default:

				break;
			}
		}

		return true;
	}
	public static String getAmendmentIDFromTextFormat(
			String amendmentIcontext) throws Exception {

		String[] arr = amendmentIcontext.split("\\s+");

		for (int i = 0; i < arr.length; i++) {

			switch (i) {

			case 1:
				Pattern pattern = Pattern
						.compile(IAmendmentsConst.amendmentID_Format);

				Matcher matcher = pattern.matcher(arr[1]);

				boolean isFound = matcher.find();

				Assert.assertEquals(isFound, true);
				
				String[] arr1 = arr[1].split("#");
				
				return arr1[1];
							
			default:

				break;
			}
		}

	 return null;
	}
	public static boolean cancelRequestAmendmentIconTextFormat(
			String amendmentIcontext) throws Exception {

		String[] arr = amendmentIcontext.split("\\s+");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String todayDate = sdf.format(Calendar.getInstance().getTime());

		for (int i = 0; i < arr.length; i++) {

			switch (i) {

			case 1:
				Pattern pattern = Pattern
						.compile(IAmendmentsConst.amendmentID_Format);

				Matcher matcher = pattern.matcher(arr[1]);

				boolean isFound = matcher.find();

				Assert.assertEquals(isFound, true);

				break;
			case 2:

				Assert.assertEquals(
						IAmendmentsConst.Amendmentformat_HardCoded_Value
								.equals(arr[2]), true);

				break;

			case 3:

				Assert.assertEquals(
						IAmendmentsConst.Amendmentformat_HardCoded_Value1
								.equals(arr[3]), true);

				break;

			case 4:

				Assert.assertEquals(todayDate.equals(arr[4]), true);

				break;

			case 5:

				Pattern pattern1 = Pattern
						.compile(IAmendmentsConst.AmendmentTime_Format);

				Matcher matcher1 = pattern1.matcher(arr[5]);

				boolean isFound1 = matcher1.find();
				Assert.assertEquals(isFound1, true);

				break;

			case 6:
				Pattern pattern2 = Pattern
						.compile(IAmendmentsConst.AmendmentTime_Format1);

				Matcher matcher2 = pattern2.matcher(arr[6]);

				boolean isFound2 = matcher2.find();
				Assert.assertEquals(isFound2, true);

			case 7:

				Assert.assertEquals(
						IAmendmentsConst.Amendmentformat_HardCoded_Value2
								.equals(arr[7]), true);

				break;

			case 8:

				Assert.assertEquals(todayDate.equals(arr[8]), true);

				break;

			case 9:
				Pattern pattern3 = Pattern
						.compile(IAmendmentsConst.AmendmentTime_Format);

				Matcher matcher3 = pattern3.matcher(arr[9]);

				boolean isFound3 = matcher3.find();
				Assert.assertEquals(isFound3, true);

				break;

			case 10:

				Pattern pattern4 = Pattern
						.compile(IAmendmentsConst.AmendmentTime_Format1);

				Matcher matcher4 = pattern4.matcher(arr[10]);

				boolean isFound4 = matcher4.find();
				Assert.assertEquals(isFound4, true);

				break;
			
			default:

				break;
			}
		}

		return true;
	}
	public static boolean approveOrRejectAmendmentRequest(Project proj, String[] obj, String stepName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openAmendmentListAndFilter(proj,stepName))
		{
			log.error("could not open Amendment List and filter");
			return false;
		}
		
		if(!openAmendmentDetailsNew("", "Requested"))
		{
			log.error("could not open Amendment Details!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Amender_DropDwnFld_Id, obj[0]))
		{
			log.error("could not select Amender: ".concat(obj[0]));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Approver_DropDwnFld_Id, obj[1]))
		{
			log.error("could not select Approver: ".concat(obj[1]));
			return false;
		}
		
		ie.textField(id, IAmendmentsConst.amendmentDetails_RequiredBy_Date_Id).set(obj[2]);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Decision_DropDwnFld_Id, obj[3]))
		{
			log.error("could not select the Decision: ".concat(obj[3]));
			return false;
		}
		
		ie.textField(id,IAmendmentsConst.amendmentDetails_ApprovalReason_TextField_Id).set(obj[4]);
		
		ie.textField(id,IAmendmentsConst.amendmentDetails_AmendmentReason_TextField_Id).set(obj[5]);
		
		ie.textField(id,IAmendmentsConst.amendmentDetails_AmendmentComment_TextField_Id).set(obj[6]);
		
		if(obj[3] == "Approved")
		{
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.nextBtn);
			
			if(!GeneralUtil.isButtonExistsByValue(IClicksConst.issueAmendBtn))
			{
				log.error("Could not find Issue Amendment Button");
				return false;
			}
			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
			return true;
			
		}
		
//		if(!GeneralUtil.isButtonDisabled(IClicksConst.nextBtn))
//		{
//			ClicksUtil.clickButtons(IClicksConst.nextBtn);
//			
//			if(!GeneralUtil.isButtonExistsByValue(IClicksConst.issueAmendBtn))
//			{
//				log.error("Could not find Issue Amendment Button");
//				return false;
//			}
//			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
//			return true;
//		}
		
		if(GeneralUtil.isButtonEnabled(IClicksConst.saveBtn))
		{
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
//			if(GeneralUtil.isButtonEnabled(IClicksConst.saveBtn))
//			{
//				log.error("could not Save the data!");
//				return false;
//			}
			
			ClicksUtil.clickButtons(IClicksConst.backToAmendListBtn);
			
			return true;
		}
		
		log.error("could not find the appropriate Button to click!");
		
		return false;
	}
	
	public static boolean changeApprover(Project proj, String stepName, String newApprover, String amendStatus) throws Exception {
		
		if(!openAmendmentListAndFilter(proj,stepName))
		{
			log.error("could not open Amendment List and filter");
			return false;
		}
		
		if(!openAmendmentDetailsNew("", amendStatus))
		{
			log.error("could not open Amendment Details!");
			return false;
		}	
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Approver_DropDwnFld_Id, newApprover))
		{
			log.error("could not select Approver: ".concat(newApprover));
			return false;
		}	
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backToAmendListBtn);
		
		return true;
	}
	
	public static boolean openFoList(EFOLists list) throws Exception {		
		
		switch (list) {
		case APPLICANTS:
			
			ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);
			
			break;
		
		case FOPP:
			
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
			
			break;
			
		case PROJECTS:
			
			ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);
			
			break;
			
		case SUBMISSIONS:
			
			ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
			
			break;
			
		case ACCOUNT:
			
			ClicksUtil.clickLinks(IClicksConst.openMyAccountLnk);
			
			break;
			
		case PASSWORD:
			
			ClicksUtil.clickLinks(IClicksConst.openChangePasswordLnk);
			
			break;

		default:
			break;
		}
		
		return true;
	}
	
	
}
