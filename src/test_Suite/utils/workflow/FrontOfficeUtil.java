package test_Suite.utils.workflow;

import static watij.finders.SymbolFactory.*;

import java.util.*;

import org.apache.commons.logging.*;
import org.testng.Assert;

import test_Suite.constants.cases.*;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.constants.workflow.*;
import test_Suite.constants.workflow.IProjectsConst.*;
import test_Suite.lib.cases.*;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.*;
import watij.elements.*;
import watij.runtime.ie.IE;

public class FrontOfficeUtil {

	private static Log log = LogFactory.getLog(FrontOfficeUtil.class);

	private static boolean retValue;

	static int rowIndex;

	static Table table;

	public static ArrayList<String> cfgLstReportNames;

	/**
	 * @param fopp
	 * @param foProj
	 * @param claims
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean createAndSubmitStandardAward(FundingOpportunity fopp,
			FOProject foProj, int claims) throws Exception {

		if (!foProj.createFOProjectSimple()) {
			log.error("Could Not Create Front Office Project!");
			return false;
		}

		if (!foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true)) {
			log.error("Could not Find Project In FO Submission List!");
		}

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("5");

		if (!foProj.pendingSubmission(IGeneralConst.approvQuoCritAuto[0][0])) {
			log.error("Could not Pend Submission");
		}

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("4");

		if (!foProj.rejectSubmission(IGeneralConst.approvQuoCritAuto[0][0])) {
			log.error("Could not Reject Submission");
			return false;
		}

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("3");

		if (!foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0],
				true, "Ready", false, false)) {
			log.error("Could Not approve submission!");
			return false;
		}

		GeneralUtil.switchToPO();

		if (!foProj.submitAward("Standard", claims, true)) {
			log.error("Could not Submit Award Form");
			return false;
		}

		return true;

	}

	public static boolean openFO_SubmissionWithStepOrProjectFullName(
			String foProjName, String objToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		FiltersUtil.filterFOListByDiv(
				IFiltersConst.fo_Submissions_ProjectName_Lbl, "", foProjName);

		FiltersUtil.filterFOListByDiv(
				IFiltersConst.fo_Submissions_SubmissionVersion_Lbl, "",
				IFiltersConst.submissionVersion_LatestVersion);

		rowIndex = TablesUtil
				.getRowIndexInFOSubmissionWithExactMatch(objToFind);

		if (rowIndex > -1) {
			ie.table(id, ITablesConst.fOSubmissionsTableId)
					.body(id, "/tbody_element/").row(rowIndex)
					.image(alt, "Open e.Form").click();

			return true;
		}

		return false;
	}

	public static boolean startOrContinueSubProject(FundingOpportunity fopp,
			FOProject foProj, int claims, boolean continues) throws Exception {
		if (!continues) {

			if (!foProj.createFOProjectSimple()) {
				log.error("Could Not Create Front Office Project!");
				return false;
			}

			if (!foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0],
					true)) {
				log.error("Could not Find Project In FO Submission List!");
			}

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			if (!foProj.approveSubmission(
					IGeneralConst.approvQuoCritAuto[0][0], true, "Ready",
					false, false)) {
				log.error("Could Not approve submission!");
				return false;
			}

			GeneralUtil.switchToPO();

			if (!foProj.submitAward("Standard", claims, true)) {
				log.error("Could not Submit Award Form");
				return false;
			}

		}

		String reviewName = IGeneralConst.reviewQuoCritManu[0][0].concat("-pa");
		String ApprovalName = IGeneralConst.approvQuoCritAuto[0][0]
				.concat("-pa");

		GeneralUtil.switchToFO();

		for (int i = 1; i <= claims; i++) {

			foProj.setClaimNumber(i);

			if (!foProj.submitFOProject(IGeneralConst.initialClaim[0][0], true)) {
				log.error("Could not Find Project In FO Submission List! with Claim No: "
						+ i);

				return false;
			}

		}

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("1");

		for (int i = 1; i <= claims; i++) {

			foProj.setClaimNumber(i);

			if (!foProj.assignEvaluators(new String[] { reviewName,
					IPreTestConst.Groups[2][1][0],
					IPreTestConst.Groups[2][1][1],
					IPreTestConst.Groups[2][1][2] })) {
				log.error("Could not Assign Reviewer to Claim: " + i);

				return false;
			}
		}

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("3");

		for (int i = 1; i <= claims; i++) {

			foProj.setClaimNumber(i);

			if (!foProj.reviewSubmission(reviewName, true, "Yes", false,
					IFiltersConst.submissionsStatus_Ready_StatusSubView)) {
				log.error("Could Not Review Claim: " + i);

				return false;
			}
		}

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("3");

		for (int i = 1; i <= claims; i++) {

			foProj.setClaimNumber(i);

			if (!foProj.approveSubmission(ApprovalName, true,
					IFiltersConst.submissionsStatus_Ready_StatusSubView, false,
					false)) {
				log.error("Could Not Approve claim: " + i);
				return false;
			}

		}

		return true;
	}

	public static boolean openRegistrantsList(String orgName) throws Exception {

		retValue = false;

		GeneralUtil.takeANap(3.0);

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.fOApplicantsTableId,
				orgName);

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndx > -1) {
			tDiv.body(id, ITablesConst.fOApplicantsTableId).row(rowIndx)
					.image(0).click();
			retValue = true;
		}

		return retValue;
	}

	public static boolean viewApplicant(String orgName) throws Exception {
		Div tDiv = TablesUtil.tableDiv();

		retValue = false;

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.fOApplicantsTableId,
				orgName);

		if (rowIndx > -1) {

			tDiv.body(id, ITablesConst.fOApplicantsTableId).row(rowIndx)
					.image(1).click();
			retValue = true;
		}
		return retValue;
	}

	/**
	 * @param progName
	 * @param orgName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean registerApplicantToProgram(String progName,
			String orgName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = true;

		Integer pageNumber = 1;

		Div divFooter = ie.div(id, ITablesConst.paginationDivId);

		if (!TablesUtil.isTableExists(ITablesConst.fORegistrationTableId)) {
			log.error("Could not Find the FOPP Registration List");

			return false;
		}

		do {

			if (pageNumber > 1) {
				divFooter.span(text, pageNumber.toString()).click();
			} else if (pageNumber == 1
					&& divFooter.span(text, pageNumber.toString()).exists()) {
				divFooter.span(text, pageNumber.toString()).click();
			}

			GeneralUtil.takeANap(1.5);

			int rowIndx = TablesUtil.getRowIndex(
					ITablesConst.fORegistrationTableId, progName);

			Div tDiv = TablesUtil.tableDiv();

			if (rowIndx > -1) {

				if (tDiv.body(id, ITablesConst.fORegistrationTableId)
						.row(rowIndx).image(0).exists()) {
					tDiv.body(id, ITablesConst.fORegistrationTableId)
							.row(rowIndx).image(0).click();
				} else {
					log.error("Could not click 'Register' image for "
							+ progName);
					return false;
				}

				GeneralUtil.takeANap(0.5);

				if (GeneralUtil.isButtonExistsByValue(IClicksConst.yesBtn)) {
					ClicksUtil.clickButtons(IClicksConst.yesBtn);
				}

				ClicksUtil.clickButtons(IClicksConst.backBtn);
				return true;

			} else {
				pageNumber += 1;
			}

			log.warn("Current Page Number: ".concat(pageNumber.toString()));

		} while (divFooter.span(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static boolean registerApplicantToProgram_RightIcon(String progName,
			String orgName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = true;

		Integer pageNumber = 1;

		if (!TablesUtil
				.isTableExists(ITablesConst.fORegistrationTableId_rightIcon)) {
			log.error("Could not Find the FOPP Registration List");

			return false;
		}

		ie.link(text, progName.substring(0, 1)).click();

		do {

			if (pageNumber > 1) {
				ie.link(text, pageNumber.toString()).click();
			} else if (pageNumber == 1
					&& ie.link(text, pageNumber.toString()).exists()) {
				ie.link(text, pageNumber.toString()).click();
			}

			int rowIndx = TablesUtil.getRowIndex(
					ITablesConst.fORegistrationTableId_rightIcon, progName);

			if (rowIndx > -1) {

				ie.table(id, ITablesConst.fORegistrationTableId_rightIcon)
						.body(id, "/tbody_element/").row(rowIndx).image(0)
						.click();
				log.warn("Couldnt click on image");

				if (GeneralUtil.isButtonExistsByValue(IClicksConst.yesBtn)) {
					ClicksUtil.clickButtons(IClicksConst.yesBtn);
				}

				ClicksUtil.clickButtons(IClicksConst.backBtn);

			} else {
				pageNumber += 1;
			}

			log.warn("Current Page Number: ".concat(pageNumber.toString()));

		} while (ie.link(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static boolean isFoppInRegistrationList(String progName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Integer pageNumber = 1;

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		if (!TablesUtil.isTableExists(ITablesConst.fORegistrationTableId)) {
			log.error("Could not Find the FOPP Registration List");

			return false;
		}

		// ie.link(text, progName.substring(0, 1)).click();

		do {

			if (pageNumber > 1) {
				ie.link(text, pageNumber.toString()).click();
			}

			int rowIndx = TablesUtil.getRowIndex(
					ITablesConst.fORegistrationTableId, progName);

			if (rowIndx > -1) {

				return true;

			} else {
				pageNumber += 1;
			}

		} while (ie.link(text, pageNumber.toString()).exists());

		return false;
	}

	public static int findFOPPInRegistrationList(String progName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		rowIndex = -1;

		retValue = true;

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		// ie.link(text, progName.substring(0, 1)).click();

		do {
			table = ie.table(id, ITablesConst.fORegistrationTableId);

			for (TableRow row : table.body(id, "/tbody_element/").rows()) {

				rowIndex += 1;
				if (row.innerText().equals(progName)) {
					retValue = false;
					break;
				}

			}

		} while (GeneralUtil.goToNextPage());

		if (retValue) {
			rowIndex = -1;
		}

		return rowIndex;
	}

	public static boolean unRegisterApplicantFromProgram(int foppIndex,
			String orgName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.body(id, ITablesConst.fORegistrantsTableId).row(foppIndex).image(0)
				.click();

		if (GeneralUtil.findInM2MList(1, orgName)) {

			ie.selectList(1).select("/" + orgName + "/");

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			return true;
		}

		return false;
	}

	public static void associateRigestrant(String userName, String emailAddress)
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.textField(id, IApplicantsConst.foRegistrantUserName).set(userName);

		ie.textField(id, IApplicantsConst.foRegistrantEmailAdd).set(
				emailAddress);
	}

	public static void setApplicant(String orgName, String applicanNumber,
			boolean autoGenNumber) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.selectList(id, IApplicantsConst.applicantTypeFO_DropDownId).select(
				"Organization");

		// ClicksUtil.clickButtons(IClicksConst.setBtn);

		GeneralUtil.takeANap(3.0);

		ie.textField(id, IApplicantsConst.applicantNameFO_FldID).set(orgName);

		GeneralUtil.takeANap(1.0);

		if (!autoGenNumber) {

			ie.textField(id, IApplicantsConst.applicantNumberFO_FldID).set(
					applicanNumber);
		}
	}

	public static boolean searchForProgram(String searchWord) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (!ie.link(text, IClicksConst.searchProgramsLnk).exists()) {
			log.error("Could Not find Search Funding Opp Link in FO!");
			return false;
		}

		ie.link(text, IClicksConst.searchProgramsLnk).click();

		GeneralUtil.takeANap(2.00);

		if (!ie.textField(id, "/txtKeywordSearch/").exists()) {

			log.error("Could not find the Search textbox After clicking Search Funding Opp Link!");
			return false;
		}

		ie.textField(id, "/txtKeywordSearch/").set(searchWord);

		ClicksUtil.clickButtons(IClicksConst.searchBtn);

		return true;
	}

	public static boolean registerThroughWizard(Object[] obj) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		int rowIndex = -1;

		rowIndex = TablesUtil.getRowIndexByTBody(
				ITablesConst.foSearchProgramsResultTBodyId, obj[0].toString());

		System.out.println(rowIndex);

		if (rowIndex > -1) {
			ie.body(id, ITablesConst.foSearchProgramsResultTBodyId)
					.row(rowIndex).image(0).click();

			if ((Boolean) obj[2]) {

				// Test if this script ran before.
				
				if(ie.textField(id,IProjectsConst.foWizardUserName).exists())
				{
					ie.textField(id, IProjectsConst.foWizardUserName).set(
							IPreTestConst.FO_UsrName + obj[4].toString());

					ie.textField(id, IProjectsConst.foWizardPwd).set(
							IPreTestConst.Pwd);

					ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

					if (ie.text().contains(
							"Invalid user name or password. Please try again.")) {
						ClicksUtil.clickButtons(IClicksConst.createProfileBtn);

						ie.textField(id, IProjectsConst.fo_UserAccount_FName_TxtId)
								.set(IPreTestConst.FO_FName + obj[4].toString());

						ie.textField(id, IProjectsConst.fo_UserAccount_LName_TxtId)
								.set(IPreTestConst.FO_LName + obj[4].toString());

						ie.textField(id, IProjectsConst.fo_UserAccount_Email_TxtId)
								.set(IPreTestConst.FO_Email + obj[4].toString()
										+ IPreTestConst.email_Domain);

						ie.textField(id,
								IProjectsConst.fo_UserAccount_EmailConf_TxtId).set(
								IPreTestConst.FO_Email + obj[4].toString()
										+ IPreTestConst.email_Domain);

						ie.selectList(id,
								IProjectsConst.fo_UserAccount_Language_TxtId)
								.select("/English/");

						ie.textField(id, IProjectsConst.fo_UserAccount_UName_TxtId)
								.set(IPreTestConst.FO_UsrName + obj[4].toString());

						ie.textField(id, IProjectsConst.fo_UserAccount_Pwd_TxtId)
								.set(IPreTestConst.Pwd);

						ie.textField(id,
								IProjectsConst.fo_UserAccount_PwdConf_TxtId).set(
								IPreTestConst.Pwd);

						ie.textField(id,
								IProjectsConst.fo_UserAccount_Question_TxtId).set(
								IPreTestConst.Question);

						ie.textField(id, IProjectsConst.fo_UserAccount_Answer_TxtId)
								.set(IPreTestConst.Answer);

						ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
					} else {
						// to make sure if the Registrant already associated with
						// Applicant
						if (ie.selectList(id, IApplicantsConst.foApplicantSelection).exists()) {
							obj[3] = (Boolean) false;
						} else {
							obj[3] = (Boolean) true;
						}

					}
				}
			} else {
				ie.textField(id, IProjectsConst.foWizardUserName).set(
						IPreTestConst.FO_UsrName + obj[4].toString());

				ie.textField(id, IProjectsConst.foWizardPwd).set(
						IPreTestConst.Pwd);

				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			}

			if ((Boolean) obj[3]) {
				ie.selectList(id, IApplicantsConst.applicantTypeFO_DropDownId)
						.select("Organization");
				GeneralUtil.takeANap(1.0);
				ie.textField(id, IApplicantsConst.applicantNameFO_FldID).set(
						IPreTestConst.FO_OrgShortName + obj[4].toString());

				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			} else {
				GeneralUtil.selectInDropdownList(
						IApplicantsConst.foApplicantSelection,
						IPreTestConst.FO_OrgShortName + obj[4].toString());
				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			}

			if ((Boolean) obj[5]) {
				ClicksUtil.clickButtons(IClicksConst.editBtn);
				ie.textField(0).set("Just to Fill out");
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickButtons(IClicksConst.completeBtn);
				ClicksUtil
						.clickLinks(IClicksConst.returnToRegistrationWizardLnk);
			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);

			ie.textField(0).set(obj[1].toString());

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ie.textField(0).set("Test Wizard");

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			retValue = true;

		}

		return retValue;
	}

	public static boolean registerThroughWizard(Object[] obj, FOUsers foUser)
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		retValue = false;

		int rowIndex = -1;

		rowIndex = TablesUtil.getRowIndexByTBody(
				ITablesConst.foSearchProgramsResultTBodyId, obj[0].toString());

		System.out.println(rowIndex);

		if (rowIndex > -1) {
			ie.body(id, ITablesConst.foSearchProgramsResultTBodyId)
					.row(rowIndex).image(0).click();

			if ((Boolean) obj[2]) {
				ClicksUtil.clickButtons(IClicksConst.createProfileBtn);

				ie.textField(0).set(
						foUser.getFoRegistrants()[1] + obj[4].toString());

				ie.textField(1).set(
						foUser.getFoRegistrants()[2] + obj[4].toString());

				ie.textField(2).set(
						foUser.getFoRegistrants()[0] + obj[4].toString()
								+ IPreTestConst.email_Domain);

				ie.textField(3).set(
						foUser.getFoRegistrants()[0] + obj[4].toString()
								+ IPreTestConst.email_Domain);

				ie.selectList(0).select(IPreTestConst.Language);

				if (foUser.getStartIndex() == 1) {
					ie.textField(4).set(foUser.getFoRegistrants()[4]);
				} else {
					ie.textField(4).set(
							foUser.getFoRegistrants()[4] + obj[4].toString());
				}

				ie.textField(5).set(IPreTestConst.Pwd);

				ie.textField(6).set(IPreTestConst.Pwd);

				ie.textField(7).set(IPreTestConst.Question);

				ie.textField(8).set(IPreTestConst.Answer);

				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			} else {
				if (foUser.getStartIndex() == 1) {
					ie.textField(id, "/userName/").set(
							foUser.getFoRegistrants()[4]);
				} else {
					ie.textField(id, "/userName/").set(
							foUser.getFoRegistrants()[4] + obj[4].toString());
				}

				ie.textField(id, "/password/").set(IPreTestConst.Pwd);

				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			}

			if ((Boolean) obj[3]) {
				ie.textField(0).set(foUser.getFoOrgName() + obj[4].toString());

				ie.textField(1).set(
						IPreTestConst.FO_OrgNumber + obj[4].toString());

				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			} else {
				log.debug(foUser.getFoOrgName());
				GeneralUtil.selectInDropdownList("/applicantSelection/",
						foUser.getFoOrgName() + obj[4].toString());
				ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			}

			if ((Boolean) obj[5]) {
				ClicksUtil.clickButtons(IClicksConst.editBtn);
				ie.textField(0).set("Just to Fill out");
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickButtons(IClicksConst.completeBtn);
				ClicksUtil
						.clickLinks(IClicksConst.returnToRegistrationWizardLnk);
			}

			ClicksUtil.clickButtons(IClicksConst.nextBtn);

			ie.textField(0).set(obj[1].toString());

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ie.textField(0).set("Test Wizard");

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			retValue = true;

		}

		return retValue;
	}

	/*********************************** new Methods for the Wizard ****************/

	public static String getFOProjectLetter(Program prog, String baseObjectName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		boolean loop = true;

		int arrIndex = 0;

		if (GeneralUtil.isLinkExistsByTxt(IClicksConst.openFOProjectListLnk)) {
			ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);

			ie.selectList(id,
					IProjectsConst.foProject_FundingOppName_DropdownId).select(
					"All Funding Opportunities");

			while (loop) {

				// ie.table(id, ITablesConst.foProject_AlphbitTableId)
				// .link(text, IGeneralConst.baseLetters[arrIndex])
				// .click();
				Div tDiv = TablesUtil.tableDiv();

				if (tDiv.body(id, ITablesConst.foProjectsTableBodyId)
						.rowCount() > 0) {
					if (doesProjectExists(baseObjectName, arrIndex,
							tDiv.body(id, ITablesConst.foProjectsTableBodyId)
									.rowCount())) {
						arrIndex += 1;
					} else {
						return IGeneralConst.baseLetters[arrIndex];
					}
				} else {
					return IGeneralConst.baseLetters[arrIndex];
				}
			}

		}

		return IGeneralConst.baseLetters[arrIndex];
	}

	public static boolean doesProjectExists(String baseObjectName,
			int arrIndex, int rowSize) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		for (int x = 0; x < rowSize; x++) {
			if (tDiv.body(id, ITablesConst.foProjectsTableBodyId)
					.row(x)
					.text()
					.contains(
							IGeneralConst.baseLetters[arrIndex]
									+ baseObjectName)) {
				return true;
			}
		}

		return false;
	}

	public static boolean openFOSubmissionAndFilter(FOProject foProj,
			String projStatus, String SubVersion) throws Exception {

		Hashtable<String, String> hashTableFields = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDD = new Hashtable<String, String>();

		hashTableDD.put(IFiltersConst.fo_Submissions_ProjectName_Lbl,
				foProj.getProjFOFullName());
		hashTableDD.put(IFiltersConst.fo_Submissions_ProjectStatus_Lbl,
				projStatus);
		hashTableDD.put(IFiltersConst.fo_Submissions_SubmissionVersion_Lbl,
				SubVersion);

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		FiltersUtil.filterFOSubListByLabel(hashTableFields, hashTableDD);

		return true;
	}

	public static boolean openFOSubmissionAndSubmit(FOProject foProj,
			String stepName) throws Exception {

		if (!openFOSubmissionForm(foProj, stepName)) {
			log.error("Could not Open Submission in FO Submission List!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);

		if (!ClicksUtil.submitOrComplete()) {
			log.error("Could not Submit Form in FO Submissions List");
			return false;
		}

		ClicksUtil.returnFromAnyForm();

		return true;
	}

	/**
	 * @param foProj
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openFOSubmissionForm(FOProject foProj, String stepName)
			throws Exception {

		String subProject = "";

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		if (!FiltersUtil.filterFOListByDiv(
				IFiltersConst.fo_Submissions_ProjectName_Lbl, "",
				foProj.getProjFOFullName())) {
			log.error("Could not filter FO list by Div!");
			return false;
		}

		rowIndex = -1;

		foProj.initializeStep(stepName);

		Div tDiv = TablesUtil.tableDiv();

		if (stepName.contains("Initial-Claim")) {
			subProject = " - Claim " + foProj.getClaimNumber().toString();

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.fOSubmissionsTableId,
					foProj.getProjFOFullName() + subProject);

		} else {

			rowIndex = TablesUtil.getRowIndex(
					ITablesConst.fOSubmissionsTableId,
					foProj.getCurrentStepName());
		}

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndex)
					.image(alt, "Open e.Form").click();

			return true;
		}

		log.error("FAIL: Submission not in List for Step: "
				+ foProj.getCurrentStepName());
		return false;
	}

	/**
	 * @param foProj
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openFO_SubprojectStep(FOProject foProj,
			String stepName) throws Exception {

		foProj.initializStep(stepName);

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		Assert.assertTrue(FiltersUtil.filterFOSubListByLabel(
				IFiltersConst.fo_Submissions_ProjectName_Lbl, "",
				foProj.getProjFOFullName()),
				"FAIL: Could not Filters in FO Submission");

		if (!TablesUtil.openInTableByImageAlt(
				ITablesConst.fOSubmissionsTableId, foProj.getSubProjFullName(),
				"Open e.Form")) {
			log.error("Could not Open Submission!");
			return false;
		}

		return true;

	}

	public static boolean openFO_SubmissionWithStepFullName(FOProject foProj,
			String stepFullName) throws Exception {

		rowIndex = -1;

		foProj.setCurrentStepName(stepFullName);

		foProj.setCurrentStepIdent(foProj.getCurrentStepName()
				.replace(' ', '-'));

		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

		Assert.assertTrue(FiltersUtil.filterFOListByDiv(
				IFiltersConst.fo_Submissions_ProjectName_Lbl, "",
				foProj.getProjFOFullName()),
				"Fail: Couldnot find project name in the dropdown");

		FiltersUtil.extraFilterListByLabel_fo(
				IFiltersConst.fo_Submissions_SubmissionVersion_Lbl, "",
				IFiltersConst.submissionVersion_LatestVersion);

		rowIndex = TablesUtil.getRowIndexInFOSubmissionWithExactMatch(foProj
				.getCurrentStepName());

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndex)
					.image(alt, "Open e.Form").click();

			return true;
		}

		return false;
	}

	public static boolean uploadAttachmentDoc(FOProject foProj,
			String stepName, String docType, String description,
			String formletMenu, Documents doc) throws Exception {

		IE ie = IEUtil.getActiveIE();

		rowIndex = -1;

		Assert.assertTrue(openFOSubmissionForm(foProj, stepName),
				"FAIL: Could not open Submission");

		ClicksUtil.clickLinks(formletMenu);

		rowIndex = TablesUtil.getRowIndex("g3-form:data_data", docType);

		if (rowIndex > -1) {
			ie.body(id, "g3-form:data_data").row(rowIndex)
					.link(text, docType).click();

			ie.textField(id, "g3-form:eFormFieldList:0:textBox").set(
					description);

			ie.fileField(id, "g3-form:uploadedDocument").set(
					"\"" + GeneralUtil.getWorkspacePath() + doc.getFilePath()
							+ doc.getFileName() + "\"");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			return true;
		}

		return false;
	}

	/**************************
	 * 
	 * TODO: new methods used FundingOpportunity class
	 * 
	 ***********************************/

	public static Integer searchForFOPP(String searchWord, String foppFullName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(ie.link(text, IClicksConst.searchProgramsLnk)
				.exists(), "FAIL: could not find the serch FOPP Link!");

		while (!ie.textField(id, "/txtKeywordSearch/").exists()) {

			ie.link(text, IClicksConst.searchProgramsLnk).click();
		}

		ie.textField(id, "/txtKeywordSearch/").set(searchWord);

		ClicksUtil.clickButtons(IClicksConst.searchBtn);

		Integer indx = rowIndex = TablesUtil.getRowIndexByTBody(
				ITablesConst.foSearchProgramsResultTBodyId, foppFullName);

		// indx = TablesUtil.getRowIndexInTableWithPaginator(listTable.id(),
		// foppFullName);

		return indx;
	}

	public static void openTheWizard(Integer foppIndex) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		//Table listTable = TablesUtil.getTable();

		ie.body(id, "g3-form:browseFO_data").row(foppIndex).link(title, "Register").click();
	}

	public static int whichStepInTheWizard() throws Exception {

		IE ie = IEUtil.getActiveIE();

		Table table = null;

		Links links = null;

		String linkText = "";

		TableRows rows = ie.form(id, "/left_bar:/").rows();

		for (TableRow tableRow : rows) {

			if (tableRow.innerText().contains("Step 1")) {

				table = tableRow.table(0);

				break;
			}
		}

		try {

			if (null != table) {

				links = table.links();

				for (Link link : links) {

					linkText = link.innerText();
				}
			}

			return Integer.parseInt(linkText.split(" ")[1].trim());

		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static boolean submitThroughWizard(FOProject foProj,
			String searchWord) throws Exception {

		Integer indx = -1;

		indx = searchForFOPP(searchWord, foProj.getFopp().getFoppFullName());

		if (indx < 0 || indx == null) {

			return false;
		}

		openTheWizard(indx);

		if (whichStepInTheWizard() == 1) {

			// loginInWizard();
		}

		return false;
	}

	public static void processWizard(FOProject foProj) throws Exception {

		LinkedHashMap<EWizardParams, Object> lhm = foProj.getWizardParamsLHM();

		Integer indx = searchForFOPP(lhm.get(EWizardParams.SEARCH_WORD)
				.toString(), lhm.get(EWizardParams.FOPP_NAME).toString());

		openTheWizard(indx);

		if ((Boolean) lhm.get(EWizardParams.IS_NEW_PROFILE)) {

			createProfileInWizard();

		} else {

			loginInWizard(lhm.get(EWizardParams.USER_NAME).toString());
		}

		if ((Boolean) lhm.get(EWizardParams.IS_NEW_APPLICANT)) {

			createApplicantInWizard(lhm.get(EWizardParams.ORG_TYPE),
					lhm.get(EWizardParams.ORG_NAME),
					lhm.get(EWizardParams.ORG_NUM));

		} else {

			String appFullName = lhm.get(EWizardParams.ORG_NAME).toString();// +
																			// " ("
																			// +
																			// lhm.get(EWizardParams.ORG_NUM).toString()
																			// +
																			// ")";

			selectApplicantInWizard(appFullName);

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		}

		if ((Boolean) lhm.get(EWizardParams.DO_FILL_WORKSPACE)) {

			completeApplicantInWizard();

		} else {
			ClicksUtil.clickButtons(IClicksConst.nextBtn);
		}

		if ((Boolean) lhm.get(EWizardParams.CREATE_PROJ)) {

			createProjectInWizard(foProj.isImportDataFromProject(),
					foProj.getSourceProjectFullName(),
					foProj.getProjFOFullName());

		}

		if ((Boolean) lhm.get(EWizardParams.SUBMIT_PROJ)) {

			submitGrantApplicantionInWizard();

		}

	}

	public static void loginInWizard(String usrName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.textField(name, IProjectsConst.foWizardUserName).set(usrName);
		ie.textField(name, IProjectsConst.foWizardPwd).set(IPreTestConst.Pwd);

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		return;
	}

	public static boolean createProfileInWizard() throws Exception {

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.createProfileBtn)) {

			ClicksUtil.clickButtons(IClicksConst.createProfileBtn);

			return true;
		}
		return false;
	}

	public static boolean createApplicantInWizard(Object orgType,
			Object orgName, Object orgNum) throws Exception {

		// FIXME: code needed

		return false;
	}

	public static boolean selectApplicantInWizard(String ApplicantFullName)
			throws Exception {

		return GeneralUtil.selectInDropdownList(
				IApplicantsConst.foApplicantSelection, ApplicantFullName);
	}

	public static boolean completeApplicantInWizard() throws Exception {

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.editBtn)) {

			ClicksUtil.clickButtons(IClicksConst.editBtn);

			return true;
		}
		return false;
	}

	public static boolean createProjectInWizard(boolean bfData,
			String srcProject, String trgtProj) throws Exception {

		GeneralUtil.setTextByIndex(0, trgtProj);

		if (bfData) {

			return GeneralUtil.selectFullStringInDropdownList(
					"g3-form:ImportDataFromProject_input", srcProject);

		} else {

			return true;
		}
	}

	public static boolean submitGrantApplicantionInWizard() throws Exception {

		return false;
	}

	/********* End of new Methods ******************/

	/********** Start of CFG methods *************/

	public static FundingOpportunity initializeCfgFOPP() throws Exception {

		return new FundingOpportunity(IProjectsConst.cfgPrefix,
				IProjectsConst.cfgFOPPIdent, "", "");
	}

	public static FOProject initializeCfgFOProj(FundingOpportunity fopp,
			IProjectsConst.EcfgProjectsName eProjName) throws Exception {

		return new FOProject(fopp, fopp.getFoppPreFix(), true, 3,
				IProjectsConst.cfgProjectsName[eProjName.ordinal()]);

	}

	public static boolean registerAndSubmitProject(FundingOpportunity fopp,
			FOProject foProj, String orgName) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		if (!registerApplicantToProgram(fopp.getFoppFullName(),
				foProj.getOrgName())) {
			log.error("Could not register to FOPP!");
			return false;
		}

		if (!foProj.createFOProjectNewNew()) {
			log.error("Error Occured during project creation");
			return false;
		}

		if (!foProj.submitFOProject(IProjectsConst.cfgAppSubStep, true)) {
			log.error("Error occured during FO Application Submission");
			return false;
		}

		GeneralUtil.switchToPO();

		if (!foProj.submitFromMyAssignedSubListNew(IProjectsConst.cfgPOSubStep,
				"Test", true)) {
			log.error("Error Occured during PO Submission");
			return false;
		}

		return true;
	}

	public static boolean createAndSubmitProject(FOProject foProj)
			throws Exception {

		if (!foProj.createFOProjectNewNew()) {
			log.error("Error Occured during project creation");
			return false;
		}

		if (!foProj.submitFOProject(IProjectsConst.cfgAppSubStep, true)) {
			log.error("Error occured during FO Application Submission");
			return false;
		}

		GeneralUtil.switchToPO();

		if (!foProj.submitFromMyAssignedSubListNew(IProjectsConst.cfgPOSubStep,
				"Test", true)) {
			log.error("Error Occured during PO Submission");
			return false;
		}

		return true;
	}

	/**
	 * @param foProj
	 * @param eCfgProjName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean continueCfgWorkflow(FOProject foProj,
			IProjectsConst.EcfgProjectsName eCfgProjName) throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.loginReviewer("1");

		if (eCfgProjName.name().toUpperCase()
				.equals(EcfgProjectsName.NOTAPPROVED.name())) {
			if (!foProj.reviewSubmission(IProjectsConst.cfgReviewStep, true,
					"No", false,
					IFiltersConst.evaluateSubmissions_Ready_StatusSubView)) {
				log.error("Error Occured during Review Grant Decision");
				return false;
			}

			return true;
		}

		if (!foProj.reviewSubmission(IProjectsConst.cfgReviewStep, true, "Yes",
				false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView)) {
			log.error("Could not Review Submission");

			return false;
		}

		GeneralUtil.switchToPO();

		if (!submitCFGAwardSchedule(foProj)) {
			log.error("Could Not submit the Award Step");
			return false;
		}

		if (eCfgProjName.name().toUpperCase()
				.equals(EcfgProjectsName.NOTSTARTED.name())) {
			return true;
		}

		if (!continueCfgPostAward(foProj, eCfgProjName)) {
			log.error("Could not Continue CFG post-award workflow");
			return false;
		}

		return true;
	}

	public static boolean submitCFGAwardSchedule(FOProject foProj)
			throws Exception {
		try {

			if (!foProj.openStandardAgreement(IProjectsConst.cfgAwardStep)) {
				log.error("Could not Open CFG Award step");
				return false;
			}

			for (int i = 0; i < IProjectsConst.cfgReportNames.length; i++) {

				boolean doSubmit = false;
				boolean doReturn = false;

				if (i == IProjectsConst.cfgReportNames.length - 1) {
					doSubmit = true;
					doReturn = true;
				}

				if (!foProj
						.addSchedules(IProjectsConst.cfgInitialStep, 1,
								doSubmit, doReturn,
								IProjectsConst.cfgReportNames[i], 1)) {
					return false;
				}
			}
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean continueCfgPostAward(FOProject foProj,
			IProjectsConst.EcfgProjectsName eCfgProjName) throws Exception {
		try {

			String projName = foProj.getProjFOFullName();

			for (String string : IProjectsConst.cfgReportNames) {

				foProj.setProjFullName(projName.concat(" - ".concat(string)
						.concat("1")));

				if (!submitCfgClaim(foProj, string)) {
					log.error("Could not Submit Claim of this Sub-Project: "
							.concat(string));

					return false;
				}

				reviewCfgClaim(foProj, string,
						IFiltersConst.evaluateSubmissions_Ready_StatusSubView);

				if (eCfgProjName.name().toUpperCase()
						.equals(EcfgProjectsName.COMPLETE.name())) {
					approveCfgClaim(
							foProj,
							string,
							IFiltersConst.evaluateSubmissions_Ready_StatusSubView);

				}
			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean continueReExecutedCfgPostAward(FOProject foProj,
			IProjectsConst.EcfgProjectsName eCfgProjName) throws Exception {
		try {

			String projName = foProj.getProjFOFullName();

			for (String string : IProjectsConst.cfgReportNames) {

				foProj.setProjFullName(projName.concat(" - ".concat(string)
						.concat("1")));

				reviewCfgClaim(
						foProj,
						string,
						IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);

				if (eCfgProjName.name().toUpperCase()
						.equals(EcfgProjectsName.COMPLETE.name())) {
					approveCfgClaim(
							foProj,
							string,
							IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);
				}
			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean reSubmitCfgSubProject(FOProject foProj,
			IProjectsConst.EcfgReportsTypes eCfgRepoType) throws Exception {
		try {

			reSubmitCfgClaim(foProj, eCfgRepoType);

			reviewCfgClaim(foProj, eCfgRepoType.name(),
					IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);
			approveCfgClaim(foProj, eCfgRepoType.name(),
					IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean reSubmitCfgSubProjectInPO(FOProject foProj,
			IProjectsConst.EcfgReportsTypes eCfgRepoType) throws Exception {
		try {

			String projName = foProj.getProjFOFullName();

			foProj.setProjFullName(projName.concat(" - ".concat(
					IProjectsConst.cfgReportNames[eCfgRepoType.ordinal()])
					.concat("1")));

			reSubmitCfgClaimInPO(foProj, eCfgRepoType);

			reviewCfgClaim(foProj, eCfgRepoType.name(),
					IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);
			approveCfgClaim(foProj, eCfgRepoType.name(),
					IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean openCfgClaim(
			IProjectsConst.EcfgReportsTypes repoTypes, FOProject foProj,
			String tableId) throws Exception {

		try {

			String projName = foProj.getProjFOFullName();

			foProj.setProjFullName(projName.concat(" - ".concat(
					IProjectsConst.cfgReportNames[repoTypes.ordinal()]).concat(
					"1")));

			int index = TablesUtil.getRowIndex(tableId,
					foProj.getProjFullName());

			if (index < 0) {
				log.error("Could Not find Sub-Project In List!");
				return false;
			}

			int cellIndx = 0;

			if (tableId.endsWith("submissionStepsTable1")) {
				cellIndx = 4;
			}

			Div tDiv = TablesUtil.tableDiv();

			if (!tDiv.body(id, tableId).row(index).cell(cellIndx).image(0)
					.exists()) {
				log.error("No Open Form Icon Found!");
				return false;
			}

			tDiv.body(id, tableId).row(index).cell(cellIndx).image(0).click();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean submitCfgClaim(FOProject foProj, String string)
			throws Exception {

		try {

			GeneralUtil.switchToPO();

			ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,
					"Post Award Submission", IFiltersConst.openProjView);

			GeneralUtil.selectRadioButton(string.split(" ")[0].toUpperCase());
			
			if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
			{
				log.error("Could Not click Save Button!");
				return false;
			}

			if(!ClicksUtil.clickLinks("Submission Summary"))
			{
				log.error("Could Not click Summary Link!");
				return false;
			}

			if(!ClicksUtil.submitOrComplete())
			{
				log.error("Could Not click Submit Button!");
				return false;
			}
				

			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	public static boolean reSubmitCfgClaim(FOProject foProj,
			IProjectsConst.EcfgReportsTypes repoType) throws Exception {

		try {

			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front3");

			ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);

			Assert.assertTrue(
					openCfgClaim(repoType, foProj,
							ITablesConst.fOSubmissionsTableId),
					"FAIL: Could Not Open Claim");

			GeneralUtil.selectRadioButton(repoType.name());

			if(!ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn))
			{
				log.error("Could Not click Save & Next Button!");
				return false;
			}

			if(!ClicksUtil.submitOrComplete())
			{
				log.error("Could Not click Submit Button!");
				return false;
			}

			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
		return true;
	}

	public static boolean reSubmitCfgClaimInPO(FOProject foProj,
			IProjectsConst.EcfgReportsTypes repoType) throws Exception {

		try {

			ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

			ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,
					"Post Award Submission", IFiltersConst.openProjView);

			GeneralUtil.selectRadioButton(repoType.name());

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ClicksUtil.submitOrComplete();

			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

	static void reviewCfgClaim(FOProject foProj, String string,
			String evalSubStatus) throws Exception {

		try {

			GeneralUtil.switchToPOOnly();

			GeneralUtil.loginReviewer("1");

			String stepName = IProjectsConst.cfgPA_ReviewSteps[IProjectsConst.EcfgReportsTypes
					.valueOf(string.split(" ")[0].toUpperCase()).ordinal()];

			foProj.reviewSubmission(stepName, true, "Yes", false, evalSubStatus);

			if (stepName.endsWith("2")) {
				GeneralUtil.switchToPOOnly();

				GeneralUtil.loginReviewer("2");

				stepName = IProjectsConst.cfgPA_ReviewSteps[IProjectsConst.EcfgReportsTypes
						.valueOf(string.split(" ")[0].toUpperCase()).ordinal()];

				foProj.reviewSubmission(stepName, true, "Yes", false,
						evalSubStatus);
			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	static void approveCfgClaim(FOProject foProj, String string,
			String evalSubStatus) throws Exception {

		try {

			GeneralUtil.switchToPOOnly();

			GeneralUtil.loginApprover("1");

			String stepName = IProjectsConst.cfgPA_ApprovalSteps[IProjectsConst.EcfgReportsTypes
					.valueOf(string.split(" ")[0].toUpperCase()).ordinal()];

			foProj.approveSubmission(stepName, true, evalSubStatus, false,
					false);

			if (stepName.endsWith("2")) {
				GeneralUtil.switchToPOOnly();

				GeneralUtil.loginApprover("2");

				foProj.approveSubmission(stepName, true, evalSubStatus, false,
						false);
			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	public static boolean createManyProjs_PreAwardAndSubmit(int numProjs,
			FundingOpportunity fopp, String projType) throws Exception {

		try {

			for (int i = 0; i < numProjs; i++) {

				FOProject foProj = new FOProject(fopp, "", true, 1, EFormsUtil
						.createAnyRandomString(5).concat(projType));

				log.warn("Creating project number " + (i + 1) + "...");

				foProj.createFOProjectNewNew();

				foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

				openFO_SubmissionWithStepFullName(foProj,
						foProj.getCurrentStepName());

				GeneralUtil.appendToTextFieldByTtl(
						IRefTablesConst.enterAnyTextFiledTtl,
						IRefTablesConst.textFieldData);

				Assert.assertTrue(
						ClicksUtil.clickButtons(IClicksConst.submitBtn),
						"Fail: Couldn't click Submit button!");
			}

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		return true;
	}

}