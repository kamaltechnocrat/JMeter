/**
 * 
 */
package test_Suite.utils.workflow;

/**
 * @author mshakshouki
 * 
 */
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.name;
import static watij.finders.SymbolFactory.src;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.title;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.dialogs.FileDownloadDialog;
import watij.elements.Div;
import watij.elements.TableRow;
import watij.elements.TextFields;
import watij.runtime.ie.IE;

public class ProjectUtil {

	private static Log log = LogFactory.getLog(ProjectUtil.class);

	static boolean retValue;

	static int rowIndex;

	public static enum EListType {
		availabeList, selectedList
	}

	public static boolean openNotificationLogInProject(
			Project proj,String stepName) throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_NotificationLog_formId)
				.exists()) {
			retValue = true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		proj.initializeStep(stepName);	

		if (!TablesUtil.findInTable(ITablesConst.projectsTableId,
				proj.getProjFullName())) {
			if (!FiltersUtil.filterListByProject(proj)) {
				log.error("Could not Filter Projects List!");

				return false;
			}
		}

		int index = TablesUtil.getRowIndex(ITablesConst.projectsTableId,
				proj.getProjFullName());

		if (index < 0) {
			log.error("Could not find Project in Project List after Filtering, the Project is: "
					.concat(proj.getProjFullName()));

			return false;
		}

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.projectsTableId).row(index).image(alt, "Actions").click();

		if (ie.div(id, IProjectsConst.onClick_optionsDiv + index + "/").link(title, IProjectsConst.project_NotificationLog_ImageAlt).exists()) {

			ie.div(id, IProjectsConst.onClick_optionsDiv + index + "/").link(title,	IProjectsConst.project_NotificationLog_ImageAlt).click();
			retValue=true;
		}

		else {
			log.error("Cannot find alt " + IProjectsConst.project_NotificationLog_ImageAlt);
			retValue = false;
		}
		//		}
		return retValue;
	}

	public static boolean assignEvaluator(Project proj, int projType,
			String[] evalsNames) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		for (int i = 0; i < evalsNames.length; i++) {
			if (isEvaluatorsExist(proj, projType, evalsNames[i],
					EListType.availabeList.ordinal())) {
				ie.selectList(id, "/availableStaff/").select(
						"/" + evalsNames[i] + "/");
				ClicksUtil.clickButtons(IClicksConst.m2MSingleBackBtn);
			}
		}

		return retValue;
	}

	public static boolean isEvaluatorsExist(Project proj, int projType,
			String evalsName, int listType) throws Exception {
		retValue = false;

		openAssignEvaluators(proj, projType);

		log.debug(evalsName);

		if (listType == 0) {
			if (GeneralUtil.isObjectExistsInList("/availableStaff/", evalsName)) {
				return true;
			}
		} else {
			if (GeneralUtil.isObjectExistsInList("/selectedStaff/", evalsName)) {
				return true;
			}
		}

		return false;
	}

	public static boolean openApplicantProfileFormInPO(Project proj)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName(), IFiltersConst.exact);

		int indx = -1;
		indx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				proj.getOrgFullName());

		if (indx > -1) {
			ie.body(id, ITablesConst.applicantsTableId).row(indx)
			.link(title, IClicksConst.openApplicantProfileLnkTitle).click();

			return true;
		}

		return false;
	}

	public static boolean openSubmissionInApplicantSubmissionList(Project proj,
			String stepName, String subStatus) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDD = new Hashtable<String, String>();

		if (!openApplicantSubmissionList(proj)) {
			log.error("Could not open Applicant Submission List");
			return false;
		}

		proj.initializeStep(stepName);

		String subProject = "";

		if (stepName.contains("Initial-Claim")) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName() + subProject);

		hashTableDD.put(IFiltersConst.grantManagement_SubmissionStatus_Lbl,
				subStatus);

		if (!FiltersUtil.filterListByLabel(hashTable, hashTableDD, false)) {
			log.error("Could not Filter Applicant Submissions List!");
			return false;
		}

		int index = TablesUtil.getRowIndex(
				ITablesConst.applicantSubmissionTableId,
				proj.getCurrentStepName());

		if (index < 0) {
			log.error("Could not access Submission, No Submissions Available!");
			return false;
		}
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.applicantSubmissionTableId)
		.row(index)
		.link(title,
				"Open Submission Form ".concat(proj.getProjFullName()
						+ subProject)).click();

		return true;
	}

	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openApplicantSubmissionList(Project proj)
			throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		if (!FiltersUtil.filterListByLabel(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName(), IFiltersConst.exact)) {
			log.error("Could not Filter PO Applicants List!");
			return false;
		}

		int indx = -1;
		indx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				proj.getOrgFullName());

		if (indx < 0) {
			log.error("No Items in PO Applicants List!");
			return false;
		}
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.applicantsTableId).row(indx)
		.image(src, IClicksConst.openListImg_src).click();

		return true;
	}

	public static String getProjectStatusInList(String tableId, Project proj)
			throws Exception {

		int rowIndx = FiltersUtil.findProjectInList(proj, tableId);

		if (rowIndx > -1) {
			int cellIndx = TablesUtil.findCellIndex(tableId,
					"Submission Status");

			if (cellIndx > -1) {
				return TablesUtil.getCellContent(tableId, rowIndx, cellIndx, 0);
			}
		}

		return "error";
	}

	public static boolean openAssignEvaluators(Project proj, int projType)
			throws Exception {

		retValue = false;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		String subProject = "";

		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk);

		if (IProgramsConst.EProjectType.post_Award.ordinal() == projType) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}
		
		log.warn(proj.getCurrentStepName());
		
		log.warn(proj.getProjFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName() + subProject);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.fundingOpp_AssignEvaluatorsTableId,
				new String[] { proj.getProjFullName() + subProject,
						proj.getCurrentStepName() });
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {

			tDiv.body(id, ITablesConst.fundingOpp_AssignEvaluatorsTableId).row(rowIndex)
			.link(title, "Assign Evaluators").click();

			retValue = true;
		}

		return retValue;
	}



	public static boolean openAssignEvaluators_StepName(Project proj, String stepName)
			throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		retValue = false;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		String subProject = "";

		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName() + subProject);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				stepName);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.fundingOpp_AssignEvaluatorsTableId,
				new String[] { proj.getProjFullName() + subProject,
						stepName	 });

		if (rowIndex > -1) {

			tDiv.body(id, ITablesConst.fundingOpp_AssignEvaluatorsTableId).row(rowIndex)
			.link(title, "Assign Evaluators").click();

			retValue = true;
		}

		return retValue;
	}





	public static TextFields getAllTextFieldsInDivDG() throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		if (!tDiv.body(id, IProjectsConst.projectDataGridTableId).exists()) {
			log.error("Could not find the Div where the Grid is!");
			return null;
		}

		return tDiv.body(id, IProjectsConst.projectDataGridTableId).textFields();
	}

	public static void enterDataToGridCell(TextFields txtFields, int rowIndex,
			int columnIndex, String cellVal) throws Exception {

		txtFields.textField(
				id,
				"g3-form:eFormFieldList:0:dataGrid:"
				.concat(String.valueOf(rowIndex))
				.concat(":")
				.concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)
				.concat(":")
				.concat(String.valueOf(columnIndex).concat(":gridCell_NumericEntry")))
						.set(cellVal);

	}

	public static String getValueInDGCell(TextFields txtFields, int rowIndex,
			int columnIndex) throws Exception {

		return txtFields.textField(
				id,
				"g3-form:eFormFieldList:0:dataGrid:"
				.concat(String.valueOf(rowIndex))
				.concat(":")
				.concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)
				.concat(":")
				.concat(String.valueOf(columnIndex).concat(":gridCell_NumericEntry")))
						.getContents();

	}


	public static boolean fillReviewForm(String drpDwnTtl, String strToFind, String commentId, String comment, String score_Ttl, String score) throws Exception{

		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_NoProp_Date_FieldTtl, GeneralUtil.getTodayDate()));

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(drpDwnTtl, strToFind), "Fail: Couldn't find the value in dropdown list");

		Assert.assertTrue(GeneralUtil.setTextById(commentId,comment ));

		Assert.assertTrue(GeneralUtil.setTextByTitle(score_Ttl,score ));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn));

		return true;

	}


	public static boolean fillApprovalForm(String drpDwnTtl, String strToFind, String commentId, String comment) throws Exception{

		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_NoProp_Date_FieldTtl, GeneralUtil.getTodayDate()));

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(drpDwnTtl, strToFind), "Fail: Couldn't find the value in dropdown list");

		Assert.assertTrue(GeneralUtil.setTextById(commentId,comment ));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn));

		return true;

	}

	/**
	 * @param cellsIndex
	 * @param cellsValue
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean fillDataGrid(int[][] cellsIndex, String[][] cellsValue)
			throws Exception {

		if (null == getAllTextFieldsInDivDG()) {
			log.error("Could not Get the Text Fields where the Grid is!");
			return false;
		}

		TextFields txtFields = getAllTextFieldsInDivDG();

		for (int rowIndex = 0; rowIndex < cellsIndex.length; rowIndex++) {
			for (int columnIndex = 0; columnIndex < cellsIndex[rowIndex].length; columnIndex++) {
				if (cellsValue[rowIndex][columnIndex] != null) {

					enterDataToGridCell(txtFields, rowIndex, columnIndex,
							cellsValue[rowIndex][columnIndex]);
				}
			}
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return true;
	}

	/**
	 * @param foProj
	 * @param stepName
	 * @param viewStatus
	 * @param submissionVersion
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openSubmissionForm(FOProject foProj, String stepName,
			String viewStatus, String submissionVersion) throws Exception {
		
		retValue = false;

		foProj.initializStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		FiltersUtil.filterFOListByDiv(
				IFiltersConst.fo_Submissions_ProjectName_Lbl, "",
				foProj.getProjFOFullName());

		FiltersUtil.filterFOListByDiv(
				IFiltersConst.fo_Submissions_SubmissionVersion_Lbl, "",
				submissionVersion);

		ClicksUtil.clickButtons(IClicksConst.filterBtn);

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId,
				foProj.getCurrentStepName());
		


		Div tDiv = TablesUtil.tableDiv();


		if (rowIndx != -1) {
			tDiv.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndx)
			.image(alt, "Open e.Form").click();

			retValue = true;
		}

		return retValue;
	}

	/**
	 * @param proj
	 * @param stepName
	 * @param projectStatus
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openClaimInMyAssignedSubmissionList(Project proj,
			String stepName, String projectStatus) throws Exception {

		int rowIndex = -1;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		proj.initializeStep(stepName);

		String subProject = "";

		subProject = " - Claim " + proj.getClaimNumber().toString();

		subProject = proj.getProjFullName().concat(subProject);

		hashTable
		.put(IFiltersConst.grantManagement_ProjectName_Lbl, subProject);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
				subProject);
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {

			tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndex).image(0).click();

			return true;
		}

		return false;
	}

	public static boolean openInMyAssignedSubmissionList(Project proj, String category,
			String projectStatus) throws Exception {

		int rowIndex = -1;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		String subProject = "";

		subProject = " - " + category;

		subProject = proj.getProjFullName().concat(subProject);

		hashTable
		.put(IFiltersConst.grantManagement_ProjectName_Lbl, subProject);
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
				subProject);

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {

			tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndex).image(0).click();

			return true;
		}

		return false;
	}
	public static boolean openSubmissionInBasket(Project proj, String stepName)
			throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		proj.initializeStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName());

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		String logMessage = "No Entries in InTake List";

		Div tDiv = TablesUtil.tableDiv();

		if (TablesUtil
				.howManyEntriesInTable(ITablesConst.fundingOpp_IntakeTableId) > 0) {

			if (tDiv.body(id, ITablesConst.fundingOpp_IntakeTableId).row(0)
					.link(title, "View Submission").exists()) {

				tDiv.body(id, ITablesConst.fundingOpp_IntakeTableId).row(0)
				.link(title, "View Submission").click();

				return true;
			}
			logMessage = "Submission not found in InTake List";
		}

		log.error(logMessage);

		return false;
	}

	public static boolean openProjectOfficerTabInBasket(Project proj)
			throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName());

		FiltersUtil.filterListByLabel(hashTable, null, false);

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, ITablesConst.fundingOpp_IntakeTableId).row(0)
				.link(title, "View Submission").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_IntakeTableId).row(0)
			.link(text, proj.getProjFullName()).click();

			ClicksUtil.clickLinks(IClicksConst.projectOfficerLnk);

			return true;
		}

		log.error("FAIL: Could not find In List Project: "
				+ proj.getProjFullName());

		return false;
	}

	public static boolean verifyOfficersAssignment(
			Hashtable<String, String> officers) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		Enumeration<String> keyStr;
		Enumeration<String> elemStr;

		String stepType = "";
		String grpOfficers = "";

		keyStr = officers.keys();
		elemStr = officers.elements();

		while (keyStr.hasMoreElements()) {

			stepType = keyStr.nextElement();
			grpOfficers = elemStr.nextElement();

			for (TableRow row : tDiv.body(id, ITablesConst.projectOfficerAssignmentTableId).rows()) {

				if (row.cell(2).innerText().equals(stepType)) {

					if (!(row.cell(3).innerText().toString().trim().equals(row
							.cell(4).innerText().toString().trim()))) {

						log.error("FAIL: Group Auto Assignment failed for group: "
								+ grpOfficers + " for Step: " + stepType);

						return false;
					}

					break;
				}
			}
		}

		return true;
	}

	public static Hashtable<String, String> initializeOfficersStepAndGroupName()
			throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		hashTable.put(IGeneralConst.gnrl_SubmissionA[0][2],
				IPreTestConst.Groups[6][0][0]);
		hashTable.put(IGeneralConst.approvQuoCritManu[0][2],
				IPreTestConst.Groups[6][0][0]);
		hashTable.put(IGeneralConst.pa_AwardCrit[0][2],
				IPreTestConst.Groups[6][0][0]);
		hashTable.put(IGeneralConst.postAwardCrit[0][2],
				IPreTestConst.Groups[6][0][0]);
		hashTable.put(IGeneralConst.gnrl_Closing_POSS_Step[0][2],
				IPreTestConst.Groups[0][0][0]);

		return hashTable;
	}

	public static Hashtable<String, String> replaceValue(
			Hashtable<String, String> ht, String key, String newValue)
					throws Exception {

		if (!ht.containsKey(key)) {
			return null;
		}

		ht.put(key, newValue);

		return ht;
	}

	public static boolean downloadAttachment(String tableId,
			String formletMenu, String DocType, String fileName)
					throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		rowIndex = -1;

		ClicksUtil.clickLinks(formletMenu);

		rowIndex = TablesUtil.getRowIndex(tableId, DocType);

		if (rowIndex > -1) {
			if (GeneralUtil.isImageExistsInTableByAlt(tableId, rowIndex,
					"Download")) {
				Thread dialogClicker = new Thread() {
					@Override
					public void run() {
						try {
							IE ie = IEUtil.getActiveIE();

							FileDownloadDialog dialog1 = ie
									.fileDownloadDialog();
							while (dialog1 == null) {
								log.debug("can't yet get handle on confirm dialog1");
								GeneralUtil.takeANap(0.250);
							}

							dialog1.save(GeneralUtil.getWorkspacePath()
									+ IGeneralConst.docsFilesPath + "file1.doc");

						} catch (Exception e) {
							throw new RuntimeException("Unexpected exception",
									e);
						}
					}
				};
				dialogClicker.start();

				tDiv.body(id, tableId).row(rowIndex)
				.image(alt, "Download").click();

				GeneralUtil.takeANap(1.000);

				dialogClicker = null;

				ie.link(text, IClicksConst.backToSubListLnk);

				return true;
			}
		}

		return false;
	}

	public static int getEntriesInMyAssignedSubmissionList(Project proj,
			String stepName, String projectStatus) throws Exception {

		proj.initializeStep(stepName);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		return TablesUtil.findHowManyInTable(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
				proj.getCurrentStepName());

	}

	public static boolean openSubmissionInRefSubmission(Project proj,
			String[] subParams) throws Exception {

		proj.initializeStep(subParams[IProjectsConst.ERefSub.STEP.ordinal()]);

		int indx = -1;

		indx = TablesUtil.findInTable(ITablesConst.referenceSubmission_TableId,
				new String[] { proj.getCurrentStepName(),
				subParams[IProjectsConst.ERefSub.USER.ordinal()],
				subParams[IProjectsConst.ERefSub.SCORE.ordinal()] });

		if (indx < 0) {
			log.error("Could not find Submission in Ref Submissions List!");
			return false;
		}

		if (!TablesUtil.openInTableByImageLinkAndIndex(
				ITablesConst.referenceSubmission_TableId, indx, 0,0)) {
			log.error("Could Not Open Submission!");
			return false;
		}

		return true;
	}

	public static boolean openSubmissionInMyAssignedSubmissionList_New(
			Project proj, String stepName, String projectStatus)
					throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		//		String subProject = "";

		int rowIndex = -1;

		proj.initializeStep(stepName);

		//		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0)) {
		//			subProject = " - Claim " + proj.getClaimNumber().toString();
		//		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		// ---------

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.contains);

		hashTableDropdown.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.contains);

		hashTableDropdown.put(IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.contains);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
				proj.getProjFullName());

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndex)
			.link(title, "View Submission").click();

			return true;
		}

		return false;
	}


	/**
	 * @param proj
	 * @param stepName
	 * @param projectStatus
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openSubmissionInMyAssignedSubmissionList(
			Project proj, String stepName, String projectStatus)
					throws Exception {

		String subProject = "";

		int rowIndex = -1;

		proj.initializeStep(stepName);

		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0)) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName().concat(subProject));
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		// ---------

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
				proj.getProjFullName());

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndex)
			.link(title, "View Submission").click();

			return true;
		}

		return false;
	}


	public static boolean openSubmissionInMyAssignedSubmissionList_Claim1(
			Project proj, String stepName, String projectStatus)
					throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		//		String subProject = "";

		int rowIndex = -1;

		//proj.initializeStep(stepName);

		//		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0)) {
		//			subProject = " - Claim " + proj.getClaimNumber().toString();
		//		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName()+ " - Claim 1");

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		//		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
		//				proj.getCurrentStepName());

		// ---------

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId,
				proj.getProjFullName());

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fundingOpp_myAssignedSubmissionsTableId).row(rowIndex)
			.link(title, "View Submission").click();

			return true;
		}

		return false;
	}
	public static boolean openSubmissionInMyProjectSubmissionsList(
			Project proj, String stepName, String projectStatus)
					throws Exception {

		String subProject = "";

		int rowIndex = -1;

		proj.initializeStep(stepName);

		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0)) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName().concat(subProject));
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		// ---------

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				proj.getProjFullName());

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(rowIndex)
			.link(title, "/View Submission:/").click();

			return true;
		}

		return false;
	}


	public static boolean openSubmissionInMyProjectSubmissionsList_New(
			Project proj, String stepName, String projectStatus,  String verNum)
					throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		String subProject = "";

		int rowIndex = -1;

		proj.initializeStep(stepName);

		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0)) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName().concat(subProject));
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		// ---------

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openMyProjectSubmissionsLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_MyProjectSubmissionsTableId,
				proj.getProjFullName());

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fundingOpp_MyProjectSubmissionsTableId).row(rowIndex)
			.link(title, "/View Submission:/").click();

			return true;
		}

		return false;
	}


	public static boolean openPOprojectList(
			Project proj, String stepName, String projectStatus)
					throws Exception {

		String subProject = "";

		int rowIndex = -1;

		proj.initializeStep(stepName);

		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0))
		{
			subProject = " - Claim " + proj.getClaimNumber().toString();				
		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName().concat(subProject));
		hashTable.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		//---------

		hashTableDropdown.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.projectsTableId, proj
				.getProjFullName());

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.projectsTableId).row(rowIndex).link(title,
					"View Project Details").click();

			return true;
		}

		return false;
	}

	public static boolean openPOProjectList(
			Project proj, String stepName, String projectStatus)
					throws Exception {
		String subProject = "";

		int rowIndex = -1;

		proj.initializeStep(stepName);

		if (stepName.contains("Initial") || (proj.getClaimNumber() > 0))
		{
			subProject = " - Claim " + proj.getClaimNumber().toString();				
		}

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName().concat(subProject));
		hashTable.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		//---------

		hashTableDropdown.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.contains);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_ProjectStatus_Lbl,
				projectStatus);

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);
		
		GeneralUtil.takeANap(0.5);

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.projectsTableId, proj
				.getProjFullName());
		
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.projectsTableId).row(rowIndex).cell(1).link(title,
					"View Project Details").click();

			return true;
		}

		return false;
	}

	/**
	 * @param proj
	 * @param stepName
	 * @param projectStatus
	 * @param submissionVersion
	 * @param submissionStatus
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openPOSubmissionForm(Project proj, String stepName,
			String projectStatus, String submissionVersion,
			String submissionStatus) throws Exception {

		retValue = false;

		int rowIndex = -1;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		proj.initializeStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName(), IFiltersConst.exact);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				proj.getOrgFullName());

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {

			tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndex)
			.link(title, "View Applicant Submission List").click();

			hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
					proj.getProjFullName());

			hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
					proj.getProgFullName());

			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					proj.getCurrentStepName());

			hashTableDropdown.put(
					IFiltersConst.grantManagement_SubmissionStatus_Lbl,
					submissionStatus);

			hashTableDropdown.put(
					IFiltersConst.grantManagement_ProjectStatus_Lbl,
					projectStatus);

			hashTableDropdown.put(
					IFiltersConst.grantManagement_SubmissionVersion_Lbl,
					submissionVersion);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

			rowIndex = -1;

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.applicantSubmissionTableId,
					proj.getProjFullName());

			tDiv = TablesUtil.tableDiv();

			if (rowIndex > -1) {

				tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndex)
				.link(title, "Open Submission Form " + proj.getProjFullName()).click();

				retValue = true;
			}
		}

		return retValue;
	}

	public static boolean resubmitInitialClaimInPO(Project proj, String stepName,
			String projectStatus, String submissionVersion,
			String submissionStatus) throws Exception {

		if(!openInitialClaimForm(proj,stepName,projectStatus,submissionVersion,submissionStatus))
		{
			log.error("COuld not open Initial claim for project: ".concat(proj.getProjFullName()));

			return false;
		}

		if(!ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk))
		{
			log.error("Could not click or open Submission Summary Formlet!");

			return false;
		}


		if (!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit initial Claim for project: ".concat(proj.getProjFullName()));
			return false;
		}

		if(!TablesUtil.isTableExists(ITablesConst.applicantSubmissionTableId))
		{
			log.error("The view should be Back to Applicant Submssion List!");
			return false;
		}


		return true;
	}

	/**
	 * @param proj
	 * @param stepName
	 * @param projectStatus
	 * @param submissionVersion
	 * @param submissionStatus
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openInitialClaimForm(Project proj, String stepName,
			String projectStatus, String submissionVersion,
			String submissionStatus) throws Exception {

		int rowIndx = -1;

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(
				IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName(), IFiltersConst.exact);

		rowIndx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId,
				proj.getOrgFullName());
		
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndx > -1) {

			tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndex)
			.link(title, IApplicantsConst.VIEW_APPL_SUBM_LIST).click();

			String subProject = "";

			if (stepName.contains("Initial-Claim")) {
				subProject = " - Claim " + proj.getClaimNumber().toString();
			}

			subProject = proj.getProjFullName().concat(subProject);

			proj.initializeStep(stepName);

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
					subProject);

			hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
					proj.getProgFullName());

			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					proj.getCurrentStepName());

			hashTableDropdown.put(
					IFiltersConst.grantManagement_SubmissionStatus_Lbl,
					submissionStatus);

			hashTableDropdown.put(
					IFiltersConst.grantManagement_ProjectStatus_Lbl,
					projectStatus);

			hashTableDropdown.put(
					IFiltersConst.grantManagement_SubmissionVersion_Lbl,
					submissionVersion);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

			String[] lstTable = { proj.getCurrentStepName(),
					proj.getProgFullName(), subProject };

			rowIndx = -1;

			rowIndx = TablesUtil.findInTable(
					ITablesConst.applicantSubmissionTableId, lstTable);

			tDiv = TablesUtil.tableDiv();
			if (rowIndx > -1) {

				tDiv.body(id, ITablesConst.applicantSubmissionTableId).row(rowIndex)
				.link(title, "Open Submission Form " + subProject)
				.click();
				
				GeneralUtil.takeANap(0.5);

				return true;
			}
		}

		return false;
	}

	/**
	 * @param rowIndex
	 * @param cellIndex
	 * @param cellValue
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean checkCellValueInGrid(Integer rowIndex,
			Integer cellIndex, String cellValue) throws Exception {

		if (null == getAllTextFieldsInDivDG()) {
			log.error("Could not Get the Text Fields where the Grid is!");
			return false;
		}

		TextFields txtFields = getAllTextFieldsInDivDG();

		return getValueInDGCell(txtFields, rowIndex, cellIndex).equals(
				cellValue);
	}

	/**
	 * @param fieldIndex
	 * @param fieldValue
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean checkBFInTextField(Integer fieldIndex,
			String fieldValue) throws Exception {
		
		return GeneralUtil.getTextInTextFieldById(
				"/" + fieldIndex.toString()
				+ IProjectsConst.dgFieldEnd_TextField_Id).equals(
						fieldValue);
	}

	public static int getEvaluationsEntries(Project proj, String stepName,
			String submissionStatus, boolean checkNotUsed) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (proj.getClaimNumber() > 0) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}

		proj.initializeStep(stepName);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName() + subProject);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_SubmissionStatus_Lbl,
				submissionStatus);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown,
				checkNotUsed);

		return TablesUtil.findHowManyInTable(
				ITablesConst.fundingOpp_EvaluateSubmissionsTableId,
				proj.getCurrentStepName());
	}

	public static boolean openEvaluateSubmissionFormletInList_New(
			FOProject foProj, String stepName, String submissionStatus,
			String formletName, boolean checkNotUsed, String verNum,
			String verCriteria) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (foProj.getClaimNumber() > 0) {

			subProject = " - Claim " + foProj.getClaimNumber().toString();

			foProj.setSubProjName(subProject);

			foProj.setSubProjFullName(foProj.getProjFullName().concat(
					foProj.getSubProjName()));
		}

		foProj.initializeStep(stepName);

		FiltersUtil.filterListByProject(foProj);

		FiltersUtil.extraFilterListByLabel(
				IFiltersConst.grantManagement_SubmissionStatus_Lbl, "",
				submissionStatus);

		FiltersUtil.extraFilterListByLabel(IFiltersConst.gpa_Version_Lbl, "",
				"Latest Version");

		int retNumber = TablesUtil.findHowManyInTable(
				ITablesConst.fundingOpp_EvaluateSubmissionsTableId,
				foProj.getCurrentStepName());

		if (retNumber <= 0) {
			return false;
		}
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0)
		.link(title, "View Evaluation").click();

		ClicksUtil.clickLinks(formletName);

		return true;
	}




	public static boolean openPOAwardFormletInList_New(
			FOProject foProj, String stepName,
			String formletName, boolean checkNotUsed, String verNum,
			String verCriteria) throws Exception {

		retValue = false;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		foProj.initializeStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				foProj.getCurrentStepName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				foProj.getProgFullName());
		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				foProj.getProjFullName());

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		int retNumber;

		retNumber = TablesUtil.findHowManyInTable(ITablesConst.awardsTableId,
				foProj.getProjFullName());

		if (retNumber >= 1) {
			ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

			ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

			Div tDiv = TablesUtil.tableDiv();

			tDiv.body(id, ITablesConst.awardsTableId).row(0)
			.link(title, "View Award").click();

			ClicksUtil.clickLinks(formletName);

			retValue = true;
		}

		return retValue;
	}

	public static boolean getAmendedStepInProjectSubmissionHistory(
			Project proj, String stepStatus) throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_SubmissionHistory_formId)
				.exists()) {
			retValue = true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		if (!TablesUtil.findInTable(ITablesConst.projectsTableId,
				proj.getProjFullName())) {
			if (!FiltersUtil.filterListByProject(proj)) {
				log.error("Could not Filter Projects List!");

				return false;
			}
		}

		int index = TablesUtil.getRowIndex(ITablesConst.projectsTableId,
				proj.getProjFullName());

		if (index < 0) {
			log.error("Could not find Project in Project List after Filtering, the Project is: "
					.concat(proj.getProjFullName()));

			return false;
		}

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, ITablesConst.projectsTableId).row(index)
				.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
				.exists()) {

			tDiv.body(id, ITablesConst.projectsTableId).row(index)
			.link(title,IProjectsConst.project_SubmissionHistory_ImageAlt).click();

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					proj.getCurrentStepName());

			hashTableDropdown.put(IFiltersConst.grantManagement_Status_Lbl,
					stepStatus);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

			rowIndex = -1;

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.projectSubmissionHistoryTableId,
					proj.getCurrentStepName());

			if (rowIndex > -1) {
				
				tDiv = TablesUtil.tableDiv();

				if (tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
						.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
						.exists()) {

					return true;
				}
			}

			log.error("Could not click View Submission History Image in Projects List!");

			retValue = false;
		}

		return retValue;
	}

	public static boolean OpenAmendedStepAmendmentHistoryInProjectSubmissionHistory(
			Project proj, String stepStatus) throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_SubmissionHistory_formId)
				.exists()) {
			retValue = true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		if (!TablesUtil.findInTable(ITablesConst.projectsTableId,
				proj.getProjFullName())) {
			if (!FiltersUtil.filterListByProject(proj)) {
				log.error("Could not Filter Projects List!");

				return false;
			}
		}

		int index = TablesUtil.getRowIndex(ITablesConst.projectsTableId,
				proj.getProjFullName());

		if (index < 0) {
			log.error("Could not find Project in Project List after Filtering, the Project is: "
					.concat(proj.getProjFullName()));

			return false;
		}

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, ITablesConst.projectsTableId).row(index)
				.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
				.exists()) {

			tDiv.body(id, ITablesConst.projectsTableId).row(index)
			.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
			.click();

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					proj.getCurrentStepName());

			hashTableDropdown.put(IFiltersConst.grantManagement_Status_Lbl,
					stepStatus);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

			rowIndex = -1;

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.projectSubmissionHistoryTableId,
					proj.getCurrentStepName());
			
			tDiv = TablesUtil.tableDiv();

			if (rowIndex > -1) {

				if (tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
						.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
						.exists()) {
					tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
					.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
					.click();

					return true;
				}
			}

			log.error("Could not Open the amdended step for View Submission History Image in Projects List!");

			retValue = false;
		}

		return retValue;
	}

	public static boolean getAmendedStepInProjectSubmissionHistory(
			Project proj, String stepStatus, String stepName) throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_SubmissionHistory_formId)
				.exists()) {
			retValue = true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		proj.initializeStep(stepName);

		if (!TablesUtil.findInTable(ITablesConst.projectsTableId,
				proj.getProjFullName())) {
			if (!FiltersUtil.filterListByProject(proj)) {
				log.error("Could not Filter Projects List!");

				return false;
			}
		}

		int index = TablesUtil.getRowIndex(ITablesConst.projectsTableId,
				proj.getProjFullName());

		if (index < 0) {
			log.error("Could not find Project in Project List after Filtering, the Project is: "
					.concat(proj.getProjFullName()));

			return false;
		}

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, ITablesConst.projectsTableId).row(index)
				.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
				.exists()) {

			tDiv.body(id, ITablesConst.projectsTableId).row(index)
			.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
			.click();

			proj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					proj.getCurrentStepName());

			hashTableDropdown.put(IFiltersConst.grantManagement_Status_Lbl,
					stepStatus);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

			rowIndex = -1;

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.projectSubmissionHistoryTableId,
					proj.getCurrentStepName());
			
			tDiv = TablesUtil.tableDiv();

			if (rowIndex > -1) {

				if (tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
						.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
						.exists()) {

					return true;
				}
			}

			log.error("Could not click View Submission History Image in Projects List!");

			retValue = false;
		}

		return retValue;
	}

	public static boolean getAutoReexecutionAmendedStepInProjectSubmissionHistory(
			Project proj, String stepStatus, String stepName) throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_SubmissionHistory_formId)
				.exists()) {
			retValue = true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		proj.initializeStep(stepName);

		if (!TablesUtil.findInTable(ITablesConst.projectsTableId,
				proj.getProjFullName())) {
			if (!FiltersUtil.filterListByProject(proj)) {
				log.error("Could not Filter Projects List!");

				return false;
			}
		}

		int index = TablesUtil.getRowIndex(ITablesConst.projectsTableId,
				proj.getProjFullName());

		if (index < 0) {
			log.error("Could not find Project in Project List after Filtering, the Project is: "
					.concat(proj.getProjFullName()));

			return false;
		}

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, ITablesConst.projectsTableId).row(index)
				.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
				.exists()) {

			tDiv.body(id, ITablesConst.projectsTableId).row(index)
			.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
			.click();


			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
					proj.getCurrentStepName());

			hashTableDropdown.put(IFiltersConst.grantManagement_Status_Lbl,
					stepStatus);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

			rowIndex = -1;

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.projectSubmissionHistoryTableId,
					proj.getCurrentStepName());
			
			tDiv = TablesUtil.tableDiv();

			if (rowIndex > -1) {

				if (tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
						.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
						.exists()) {

					return true;
				}
			}

			log.error("Could not click View Submission History Image in Projects List!");

			retValue = false;
		}

		return retValue;
	}
	public static String getAmendedStepDetailInProjectSubmissionHistory(
			Project proj) throws Exception {

		rowIndex = -1;

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.projectSubmissionHistoryTableId,
				proj.getCurrentStepName());

		if (rowIndex > -1) {

			Div tDiv = TablesUtil.tableDiv();

			if (tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
					.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
					.exists()) {

				return tDiv.body(id, ITablesConst.projectSubmissionHistoryTableId).row(rowIndex)
						.link(id, IProjectsConst.project_SubmissionHistory_AmendmentIconId)
						.innerText();

			}
		}

		log.error("Could not fetch the innerText of Amendment Icon in Project Submission History");

		return null;

	}

	public static boolean isAmendmentHistoryTabExist() throws Exception {
		IE ie = IEUtil.getActiveIE();	

		retValue= false;


		if( GeneralUtil.isButtonExistsByValue("Submission History"))
		{
			ie.span(text, "Submission History").click();

			retValue= true;
		}

		return retValue;

	}
	public static boolean openBulkEvalStepsForEditing(FOProject foProj,
			String stepName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();	

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDD = new Hashtable<String, String>();

		foProj.initializeStep(stepName);

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				foProj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				foProj.getCurrentStepName());

		hashTableDD.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				IFiltersConst.exact);

		hashTableDD.put(IFiltersConst.grantManagement_StepName_Lbl,
				IFiltersConst.exact);

		ClicksUtil.clickLinks(IClicksConst.openBulkEvalLnk);

		FiltersUtil.filterListByLabel(hashTable, hashTableDD, false);

		int indx = -1;

		indx = TablesUtil.getRowIndex(
				ITablesConst.fundingOpp_BulkEvaluateStepsTableId,
				foProj.getCurrentStepName());

		if (indx > 0) {
			log.error("Can not find Step in List!");
			return false;
		}
		
		log.warn(foProj.getCurrentStepName());

		if(!ClicksUtil.clickLinks(foProj.getCurrentStepName()))
		{
			log.error("Could not find link for step name!");
			return false;
		}

		if(!ClicksUtil.clickButtons(IClicksConst.bulkEval_OpenAllFormsBtn))
		{
			log.error("Could not find Button for step details on Bulk Eval!");
			return false;
		}
		
		for (int i = 0; i < 10; i++) {
			
			if(ie.div(id, IProjectsConst.bulkEval_OptWarning_DivId).exists())
			{
				break;
			}
			
			GeneralUtil.takeANap(2.5);
			
		}

		while (!ie.div(id, IProjectsConst.bulkEval_OptWarning_DivId).innerText().trim().equals("Forms opened successfully")) {
			
			log.warn("Not Yet!");
			
			GeneralUtil.takeANap(1.5);			
		}
		
		GeneralUtil.takeANap(2.5);

		return true;
	}

	public static boolean closeBulkEvaluation() throws Exception {

		if (!GeneralUtil
				.isButtonExistsByValue(IClicksConst.bulkEval_CloseAllFormsBtn)) {
			log.error("Could not find: "
					.concat(IClicksConst.bulkEval_CloseAllFormsBtn));
			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.bulkEval_CloseAllFormsBtn);

		while (GeneralUtil
				.isSpanExistsById(IProjectsConst.bulkEval_BackgroundWarning_SpanId)) {
			log.warn("Not Yet!");
		}

		return true;
	}

	public static boolean saveBulkEvaluation() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		GeneralUtil.takeANap(3.5);

		if (!GeneralUtil
				.isButtonExistsByValue(IClicksConst.bulkEval_SaveAllFormsBtn)) {
			log.error("Could not find: "
					.concat(IClicksConst.bulkEval_SaveAllFormsBtn));
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.bulkEval_SaveAllFormsBtn);
		
		for (int i = 0; i < 10; i++) {
			
			if(ie.div(id, IProjectsConst.bulkEval_OptWarning_DivId).exists())
			{
				break;
			}
			
			GeneralUtil.takeANap(2.5);
			
		}
		
		int i = 0;

		while (!ie.div(id, IProjectsConst.bulkEval_OptWarning_DivId).innerText().trim().equals("Data saved successfully")) {
			
			log.warn("Not Yet!");
			GeneralUtil.takeANap(0.5);
			i++;
			
			if (i == 240){
				
				log.warn("Auto scoring took too long to release the page and Save is not saving after 2 minutes, clicking Save again!");

				ClicksUtil.clickButtons(IClicksConst.bulkEval_SaveAllFormsBtn);

			}

			if (i == 480){

				log.error("Auto scoring took too long to release the page and Save is not saving after 4 minutes!");
				
				return false;

			}
		}
		
		GeneralUtil.takeANap(2.5);

		return true;
	}

	public static boolean submitBulkEvaluation() throws Exception {
		
		GeneralUtil.takeANap(3.5);

		if (!GeneralUtil
				.isButtonExistsByValue(IClicksConst.bulkEval_SubmitAllFormsBtn)) {
			log.error("Could not find: "
					.concat(IClicksConst.bulkEval_SubmitAllFormsBtn));
			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.bulkEval_SubmitAllFormsBtn);
		
		

		while (GeneralUtil.isSpanExistsById(IProjectsConst.bulkEval_BackgroundWarning_SpanId)) {
			
			log.warn("Not Yet!");
			GeneralUtil.takeANap(0.5);
		}
		
		GeneralUtil.takeANap(2.5);

		return true;
	}

	public static boolean runScoringAlgorithm() throws Exception {
		
		IE ie = IEUtil.getActiveIE();	

		//GeneralUtil.takeANap(2.0);

		if (!GeneralUtil.isButtonExistsByValue(IClicksConst.bulkEval_RunAutoScoringBtn)) {
			log.error("Could not find Button: "
					.concat(IClicksConst.bulkEval_RunAutoScoringBtn));
			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.bulkEval_RunAutoScoringBtn);
		
		GeneralUtil.takeANap(1.0);
		
		ClicksUtil.clickButtons("Ok");

		int i = 0;

		while (!ie.div(id, IProjectsConst.bulkEval_OptWarning_DivId).innerText().trim().equals("Automated scoring completed successfully")) {
			
			log.warn("Not Yet!");

			GeneralUtil.takeANap(0.5);
			i++;

			if (i == 480){

				log.error("Auto scoring took too long to complete! (More than 4 minutes)");
				
				return false;

			}
		}
		
		return true;
	}

	public static boolean enterValueAndSubmitCommBal(String val, String txtTtl)
			throws Exception {

		if (!GeneralUtil.enterTextByTitle(txtTtl, val)) {
			log.error("Could not enter Values");
			return false;
		}

		if (!ClicksUtil.submitOrComplete()) {
			log.error("Could not Click Submit Button!");
			return false;
		}

		ClicksUtil.returnFromAnyForm();

		return true;
	}

	/**
	 * @param proj
	 * @param stepName
	 * @param submissionStatus
	 * @param formletName
	 * @param checkNotUsed
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openEvaluateSubmissionFormletInList(Project proj,
			String stepName, String submissionStatus, String formletName,
			boolean checkNotUsed) throws Exception {

		retValue = false;

		int retNumber;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		String subProject = "";

		if (proj.getClaimNumber() > 0) {
			subProject = " - Claim " + proj.getClaimNumber().toString();
		}

		proj.initializeStep(stepName);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ApplicantName_Lbl,
				proj.getOrgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName() + subProject);

		hashTableDropdown.put(
				IFiltersConst.grantManagement_SubmissionStatus_Lbl,
				submissionStatus);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown,
				checkNotUsed);

		retNumber = TablesUtil.findHowManyInTable(
				ITablesConst.fundingOpp_EvaluateSubmissionsTableId,
				proj.getCurrentStepName());

		if (retNumber >= 1) {
			ClicksUtil.clickLinks(IClicksConst.sortByDateSubmittedLnk);

			ClicksUtil.clickLinks(IClicksConst.sortByDateSubmittedLnk);

			Div tDiv = TablesUtil.tableDiv();

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0)
			.link(title, "View Evaluation").click();

			ClicksUtil.clickLinks(formletName);

			retValue = true;
		}

		return retValue;
	}



	/**
	 * @param proj
	 * @param stepName
	 * @param formletName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openPOAwardFormletInList(Project proj,
			String stepName, String formletName) throws Exception {

		

		retValue = false;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		proj.initializeStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());
		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		int retNumber;

		retNumber = TablesUtil.findHowManyInTable(ITablesConst.awardsTableId,
				proj.getProjFullName());
		
		Div tDiv = TablesUtil.tableDiv();

		if (retNumber >= 1) {
			ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

			ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk);

			tDiv.body(id, ITablesConst.awardsTableId).row(0)
			.link(title, "View Award").click();

			ClicksUtil.clickLinks(formletName);

			retValue = true;
		}

		return retValue;
	}

	public static boolean openAwardInList(Project proj) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDD = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTableDD.put(IFiltersConst.gpa_Version_Lbl,
				IFiltersConst.submissionVersion_LatestVersion);

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName());

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl,
				proj.getProjFullName());

		FiltersUtil.filterListByLabel(hashTable, hashTableDD, false);

		int retNumber = -1;

		retNumber = TablesUtil.findHowManyInTable(ITablesConst.awardsTableId,
				proj.getProjFullName());

		Div tDiv = TablesUtil.tableDiv();

		if (retNumber >= 1) {

			tDiv.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();

			return true;
		}

		log.error("FAIL: could not find award submission in List");

		return false;
	}

	public static boolean verifyNotificationLogEntries(
			String notificationName, String ddValue) throws Exception {

		GeneralUtil.takeANap(40.0);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_NotificationName_Lbl,
				notificationName);

		hashTableDropdown.put(IFiltersConst.grantManagement_NotificationName_Lbl,
				ddValue);

		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);

		rowIndex = -1;

		rowIndex = TablesUtil.getRowIndex(
				ITablesConst.notificationLogTableId,
				notificationName);

		if (rowIndex > -1) {

			GeneralUtil.takeANap(40.0);
			
			Div tDiv = TablesUtil.tableDiv();

			if (tDiv.body(id, ITablesConst.notificationLogTableId).row(0)
					.link(id, IProjectsConst.project_NotificationLog_NotificationId)
					.exists()) {

				return true;
			}
		}

		log.error("There is no notification entries for: "+ notificationName + " or notifications took longer than 20s to load!");

		return false;
	}

	public static boolean requestAmendmentFromPO(FOProject foProj) throws Exception {
		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.approvalCRQA, IRefTablesConst.openProjects),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);


			Assert.assertTrue(
					AmendmentsUtil.fillAmendments(
							new String[] {foProj.getProjFullName(),"","",
									dd,IGeneralConst.amendmentReason,
									IGeneralConst.amendmentComment}, foProj),
					"FAIL: Could not fill out amendments!");
			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.postAwardDetailsBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.postAwardDetailsBtn);


			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);
			return false;
		}

		return true;
	}


	public static boolean approveProject(String foundProj, boolean hasSummary, boolean submit) throws Exception{

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0)
			.link(title,"View Evaluation").click();


			if(ie.textField(name, "/textArea_Below/").readOnly()) {

				return false;				
			}

			ie.selectList(name, "/numericDropdown/").select("Approved");
			ie.textField(name, "/datePicker/").set(GeneralUtil.getTodayDate());
			ie.textField(name, "/textArea_Below/").set("Conduct Evaluation");

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (hasSummary) {
				ClicksUtil.clickButtons(IClicksConst.nextBtn);
			}

			if (submit) {

				if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

					return false;

				}
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			return true;

		}		

		return false;
	}

	public static boolean approveProject_Bulk(String projType, String foundProj, boolean hasSummary, boolean submit) throws Exception{

		IE ie = IEUtil.getActiveIE();

		FiltersUtil.filterListByLabel(IFiltersConst.projects_ProjectName_Lbl, projType, IFiltersConst.contains);
		
		//projType
		
		int index = -1;
		
		index = TablesUtil.getRowIndex("bulkEvalSubmissionsForm:evalSubmissionList_data", foundProj);
		
		if (index < 0)
		{
			log.error("Could not find Submission in Bulk Eval Lits!");
			return false;
		}
		
		if(!ie.body(id, "bulkEvalSubmissionsForm:evalSubmissionList_data").row(index).cell(0).link(0).exists())
		{
			log.error("Could not find Open Icon on Bulk Eval list!");
			return false;
		}
		
		ie.body(id, "bulkEvalSubmissionsForm:evalSubmissionList_data").row(index).cell(0).link(0).click();

		if(ie.textField(name, "/textArea_Below/").readOnly()) {

			return false;				
		}

		ie.selectList(name, "/numericDropdown/").select("Approved");
		GeneralUtil.takeANap(1.5);
		ie.textField(name, "/datePicker/").set(GeneralUtil.getTodayDate());
		ie.textField(name, "/textArea_Below/").set("Conduct Evaluation");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		if (hasSummary) {
			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}

		if (submit) {

			if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

				return false;

			}
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.bulkEval_BackToBulkEvalListBtn);

		return true;

	}

	public static boolean openEvalSubmitAll_Bulk(FOProject foProj, String stepName) throws Exception {

		try {
			openBulkEvalStepsForEditing(foProj, stepName);
			
			//FiltersUtil.filterListByLabel(IFiltersConst.projects_ProjectName_Lbl, projType, "Contains");

			//ClicksUtil.clickButtons(IClicksConst.bulkEval_OpenAllFormsBtn);

			runScoringAlgorithm();
			
			saveBulkEvaluation();
			
			submitBulkEvaluation();

//			ClicksUtil.clickButtons(IClicksConst.bulkEval_SaveAllFormsBtn);
//
//			ClicksUtil.clickButtons(IClicksConst.bulkEval_SubmitAllFormsBtn);

			ClicksUtil.clickButtons(IClicksConst.bulkEval_BackToBulkEvalListBtn);

		} catch (Exception e) {

			log.error("Unexpected exception: " + e);
			return false;
		}

		return true;
	}

	public static boolean openEvalSubmitAll_Bulk(String projType) throws Exception {

		try {
			FiltersUtil.filterListByLabel(IFiltersConst.projects_ProjectName_Lbl, projType, "Contains");

			ClicksUtil.clickButtons(IClicksConst.bulkEval_OpenAllFormsBtn);
			
			GeneralUtil.takeANap(3.0);

			runScoringAlgorithm();
			
			saveBulkEvaluation();
			
			submitBulkEvaluation();

//			ClicksUtil.clickButtons(IClicksConst.bulkEval_SaveAllFormsBtn);
//
//			ClicksUtil.clickButtons(IClicksConst.bulkEval_SubmitAllFormsBtn);

			ClicksUtil.clickButtons(IClicksConst.bulkEval_BackToBulkEvalListBtn);

		} catch (Exception e) {

			log.error("Unexpected exception: " + e);
			return false;
		}

		return true;
	}

	public static boolean checkIfProjsStillExist_BulkEval(FundingOpportunity fopp, String stepName, 
			String projType) throws Exception {

		try {

			FiltersUtil.filterByTwoFields(IFiltersConst.projects_FOPPName_Lbl, fopp.getFoppFullName(), 
					IFiltersConst.projects_StepName_Lbl, stepName, "Starts With");

			String noItems = "This list contains no items";

			if (ClicksUtil.clickLinks(stepName)){

				FiltersUtil.filterListByLabel(IFiltersConst.projects_ProjectName_Lbl, projType, "Contains");

				GeneralUtil.isStringExists(noItems);	

				return true;
			}

			GeneralUtil.isStringExists(noItems);

		} catch (Exception e) {

			log.error("Unexpected exception: " + e);
			return false;
		}

		return true;
	}

	public static boolean createOneProjectAndSubmit(FOProject foProj, FundingOpportunity fopp, String projType) throws Exception {

		try {

			foProj.createFOProjectNewNew();

			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

			FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj,  foProj.getCurrentStepName());

			GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData);

			if (!ClicksUtil.clickButtons(IClicksConst.submitBtn)){
				log.error("could not click Submit to submit project!");
			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
			return false;
		}

		return true;
	}


	public static boolean approveSubmission_submitAward(FOProject foProj) throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Approver1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false, false), "Fail: Couldn't submit Approval step ");

		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(
					foProj.submitAward("Standard", 1, true,IGeneralConst.postAwardFormName),
					"FAIL: Could not Submit Standard Award Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

			return false;
		}
		return true;
	}

	public static boolean initialClaimSubmit(FOProject foProj) throws Exception{

		try {

			GeneralUtil.switchToFOOnly();

			GeneralUtil.loginAnyFO("front");

			Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A Gnrl PA Initial Claim"), "Fail: Couldn't open FO Submission - A Gnrl PA Initial Claim");

			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn), "Fail: Couldn't click Next button");	

			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),  "Fail: Couldn't click Submit button");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

			return false;
		}
		return true;
	}



	public static boolean assignEvaluator_submitReview(FOProject foProj) throws Exception {

		try {

			GeneralUtil.switchToPOWithProjOfficer("1");

			Assert.assertTrue(foProj.assignAllAvailableEvaluator_Claim1(IGeneralConst.reviewQuoCritManu[0][0] + IGeneralConst.newPASuffix), "Fail: Failed to assign Evaluator");

			GeneralUtil.switchToPOOnly();

			GeneralUtil.LoginAny("Reviewer1");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Review", "Ready", "", false, "1", ""), "Fail: Failed to open Review form");

			Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectYes, IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score), "Fail: Failed to fill review form");

			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

			return false;
		}
		return true;
	}

	public static boolean approveSubmission_Amended(Project foProj) throws Exception {

		try {
			GeneralUtil.switchToPOOnly();

			GeneralUtil.LoginAny("Approver1");

			Assert.assertTrue(foProj.approveAmendedSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "In Progress"), 
					"Fail: Failed to submit amended Approval Submission");

		} catch (Exception e) {
			log.error("Unexpected exception: " + e);
			return false;
		}

		return true;
	}

	public static boolean checkClosingStepVisibility(FundingOpportunity fopp, FOProject foProj) throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.projectsList_lbl), "Fail: Couldn't click on projects List!");

		Assert.assertTrue(FiltersUtil.filterListByProject(foProj),
				"Fail: Couldn't filter on Projects page!"); 

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, ITablesConst.projectsTableId).row(0)
				.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
				.exists()) {

			tDiv.body(id, ITablesConst.projectsTableId).row(0)
			.link(title, IProjectsConst.project_SubmissionHistory_ImageAlt)
			.click();
		}

		else {

			log.error("Could not click Submission History on hover!");
			return false;
		}

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IFiltersConst.fo_Submissions_SubmissionVersion_Lbl, 
				IFiltersConst.submissionVersion_AllVersion, IFiltersConst.projects_StepName_Lbl, "Closing", "Contains"),
				"Fail: Couldn't filter on Projects page!"); 

		if (TablesUtil.getTotalRows(ITablesConst.projectSubmissionHistoryTableId) !=0) {

			log.warn("Closing Step is visible!");
			return false;
		}

		return true;

	}

	public static boolean approveOnDemandClaim(FOProject foProj, String stepStatus) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				foProj.getProjFOFullName().concat(" - Payment"));
		hashTable
		.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				foProj.getProgFullName());
		hashTableDropdown.put(
				IFiltersConst.grantManagement_Version_Lbl,
				IFiltersConst.submissionVersion_AllVersion);
		hashTableDropdown
		.put(
				IFiltersConst.grantManagement_SubmissionStatus_Lbl,
				stepStatus);

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0).link(title,"View Evaluation").exists()) {

			tDiv.body(id, ITablesConst.fundingOpp_EvaluateSubmissionsTableId).row(0)
			.link(title,"View Evaluation").click();


			if(ie.textField(name, "/textArea_Below/").readOnly()) {

				return false;				
			}

			ie.textField(name, "/datePicker/").set(GeneralUtil.getTodayDate());
			ie.textField(name, "/textArea_Below/").set("Comment");
			ie.selectList(name, "/numericDropdown/").select("Approved");
			
			GeneralUtil.takeANap(1.0);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if(!GeneralUtil.isButtonEnabled(IClicksConst.submitBtn)) {

				log.error("Submit button is not enabled!");
				return false;

			}
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
		}

		return true;
	}



	public static boolean verifyVisibilityProjList_ODSS(FOProject foProj) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				foProj.getProjFOFullName().concat(" - Payment"));
		hashTable
		.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				foProj.getProgFullName());
		hashTableDropdown
		.put(
				IFiltersConst.grantManagement_ProjectStatus_Lbl,
				"Open Projects");

		FiltersUtil.filterListByLabel(hashTable,hashTableDropdown,false);

		if (TablesUtil.getTotalRows(ITablesConst.poProjectsListTable_Id) == 0) {
			return false;
		}

		return true;
	}



}
