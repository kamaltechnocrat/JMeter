/**
 * 
 */
package test_Suite.tests.preTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.ErrorUtil;
import watij.runtime.ie.IE;

/**
 * @author apankov
 * 
 */
@Test(singleThreaded = true)
public class MenuItemsTest {
	private static Log log = LogFactory.getLog(MenuItemsTest.class);
	IE ie;
	private String approvalProcess = "Approval Process";

	@BeforeClass
	public void setUp() {

	}

	/**
	 * Parent method
	 * 
	 */
	@Test(groups = { "PreTest02NG" })
	public void menuItemsTest() throws Exception {
		ie = IEUtil.getActiveIE();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();

		selectMenus();
		closeBrowser();
	}

	private void selectMenus() {
		try {
			ClicksUtil.clickLinks(IClicksConst.openChangePasswordLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openChangePasswordLnk);

			// Approval Process
			ClicksUtil.clickLinkBySpanId(
					IClicksConst.menuPAP_ApprovalProcess_Span_Id,
					IClicksConst.openProgramApprovalProcess);
			ErrorUtil.checkForUnexpectedError(approvalProcess + " - "
					+ IClicksConst.openProgramApprovalProcess);

			ClicksUtil.clickLinkBySpanId(
					IClicksConst.menuPAP_ApprovalProcess_Span_Id,
					IClicksConst.openInBasketListLnk);
			ErrorUtil.checkForUnexpectedError(approvalProcess + " - "
					+ IClicksConst.openInBasketListLnk);

			// Approval Process/Evaluation
			ClicksUtil.clickLinkBySpanId(
					IClicksConst.menuPAP_Evaluation_Span_Id,
					IClicksConst.openEvaluatorListLnk);
			ErrorUtil.checkForUnexpectedError(approvalProcess + " - "
					+ IClicksConst.openEvaluatorListLnk);

			ClicksUtil.clickLinkBySpanId(
					IClicksConst.menuPAP_Evaluation_Span_Id,
					IClicksConst.openAssignedSubmissionListLnk);
			ErrorUtil.checkForUnexpectedError(approvalProcess + " - "
					+ IClicksConst.openAssignedSubmissionListLnk);

			// Approval Process/My In Basket
			ClicksUtil.clickLinkBySpanId(
					IClicksConst.menuPAP_MyInBasket_Span_Id,
					IClicksConst.openMyProjectSubmissionsLnk);
			ErrorUtil.checkForUnexpectedError(approvalProcess + " - "
					+ IClicksConst.openMyProjectSubmissionsLnk);

			ClicksUtil.clickLinkBySpanId(
					IClicksConst.menuPAP_MyInBasket_Span_Id,
					IClicksConst.openMyAssignedSubmissionListLnk);
			ErrorUtil.checkForUnexpectedError(approvalProcess + " - "
					+ IClicksConst.openMyAssignedSubmissionListLnk);

			// Program Planning menu
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openFundingOppsListLnk);

			ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openFormsListLnk);

			ClicksUtil.clickLinks(IClicksConst.openDocumentsListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openDocumentsListLnk);

			// Intake menu

			ClicksUtil.clickLinkBySpanId(IClicksConst.menuInTake_Span_Id,
					IClicksConst.openInBasketListLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openInBasketListLnk);

			// Projects menu
			ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openPOProjectListLnk);

			// Projects - My In Basket menu

			ClicksUtil.clickLinkBySpanId(IClicksConst.menuMyInBasket_Span_Id,
					IClicksConst.openMyProjectSubmissionsLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openMyProjectSubmissionsLnk);

			ClicksUtil.clickLinkBySpanId(IClicksConst.menuMyInBasket_Span_Id,
					IClicksConst.openMyAssignedSubmissionListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openMyAssignedSubmissionListLnk);

			ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openPOApplicantListLnk);

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openAmendmentListLnk);

			// Evaluation menu
			ClicksUtil.clickLinks(IClicksConst.openEvaluatorListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openEvaluatorListLnk);

			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openAssignedSubmissionListLnk);

			// Award menu
			ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openAwardListLnk);

			// My Reports menu
			ClicksUtil.clickLinks(IClicksConst.openMyReportsListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openMyReportsListLnk);

			// Administration menu

			// Administration - User Interface menu
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openLookupsListsLnk);

			// Administration - Users and Groups menu
			ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openUsersListLnk);

			ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openGroupListLnk);

			ClicksUtil.clickLinks(IClicksConst.openLockedOutUsersLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openLockedOutUsersLnk);

			// Organization Management menu
			ClicksUtil.clickLinks(IClicksConst.openOrganizationsList);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openOrganizationsList);

			// Report Administration menu
			ClicksUtil.clickLinks(IClicksConst.openReportsList);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openReportsList);

			// Configuration menu
			ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openSystemSettingListLnk);

			ClicksUtil.clickLinks(IClicksConst.openApplicationSettings);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openApplicationSettings);

			ClicksUtil.clickLinks(IClicksConst.openFrontOfficeConfiguration);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openFrontOfficeConfiguration);

			ClicksUtil.clickLinks(IClicksConst.openDataLoader);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openDataLoader);

			// Grants.gov menu
			ClicksUtil.clickLinkBySpanId(IClicksConst.menu_GrantsGov_Span_Id,
					IClicksConst.openFundingOppsListLnk);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.openFundingOppsListLnk);

			ClicksUtil.clickLinkBySpanId(IClicksConst.menu_GrantsGov_Span_Id,
					IClicksConst.openFormsListLnk);
			ErrorUtil.checkForUnexpectedError(IClicksConst.openFormsListLnk);

			ClicksUtil.clickLinkBySpanId(IClicksConst.menu_GrantsGov_Span_Id,
					IClicksConst.ggTransactionResults);
			ErrorUtil
					.checkForUnexpectedError(IClicksConst.ggTransactionResults);

			ClicksUtil.clickLinkBySpanId(IClicksConst.menu_GrantsGov_Span_Id,
					IClicksConst.ggCertificates);
			ErrorUtil.checkForUnexpectedError(IClicksConst.ggCertificates);

		} catch (Exception ex) {
			log.error("Unexpected Exception in selectMenus() "
					+ ex.getMessage());
		}
	}

	/**
	 * Close PO application and close web browser
	 * 
	 */
	private void closeBrowser() {
		try {
			ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		} catch (Exception ex) {
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}

}
