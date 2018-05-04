/**
 * 
 */
package test_Suite.utils.users;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.title;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.elements.TableRow;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class ApplicantsUtil {
	private static Log log = LogFactory.getLog(ApplicantsUtil.class);

	public static boolean openNewPOUserProfile(String applicantName) throws Exception {


		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, applicantName,IFiltersConst.exact);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, applicantName);
		

		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1)
		{
			tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndex).cell(1).link(title, "Registrants List").click();

			ClicksUtil.clickImageByAlt("Create New Registrant for the Applicant");

			return true;
		}		

		return false;
	}

	public static boolean openFOApplicantProfile(String applicantName) throws Exception {

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.fOApplicantsTableId, applicantName);

		Div tDiv = TablesUtil.tableDiv();

		if(rowIndex > -1)
		{

			tDiv.body(id, ITablesConst.fOApplicantsTableId).row(rowIndex).link(title, "View Applicant Profile: ".concat(applicantName)).click();

			return true;
		}		

		return false;
	}

	public static boolean openFrontOfficeApplicantProfile(String applicantName) throws Exception {

		

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.fOApplicantsTableId, applicantName);
		
		Div tDiv = TablesUtil.tableDiv();

		if(rowIndex > -1)
		{

			tDiv.body(id, ITablesConst.fOApplicantsTableId).row(rowIndex).link(title, "View Applicant Profile: ".concat(applicantName)).click();

			if (GeneralUtil.isButtonExistsByValue(IClicksConst.editBtn))
			{
				ClicksUtil.clickButtons(IClicksConst.editBtn);
			}

			return true;
		}		

		return false;
	}

	public static boolean changeAutoGenNumber(boolean status, String appTypeName) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openApplicantTypesListsLnk);

		TableRow row = TablesUtil.getRowByLabel(ITablesConst.applicantsTypesTableId, appTypeName);

		if(!row.checkbox(1).isSet())
		{

			Thread dialogClicker = new Thread() {
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					try {
						IE ie = IEUtil.getActiveIE();
						ConfirmDialog dialog1 = ie.confirmDialog();
						while (dialog1 == null) {
							log.debug("can't yet get handle on confirm dialog1");
							this.sleep(250);
						}

						dialog1.ok();
						log.debug("got confirm Dialog1 and clicked OK.");
					} catch (Exception e) {
						throw new RuntimeException("Unexpected exception", e);
					}
				}
			};

			dialogClicker.start();
			log.debug("started dialog clicker thread");

			row.checkbox(1).set();

			GeneralUtil.takeANap(1.000);

			dialogClicker = null;
		}
		return true;
	}

	public static boolean addNewFOApplicant(FOUsers foUser) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);

		if(!GeneralUtil.isImageExistsBySrc(IClicksConst.newImg))
		{
			log.error("Could not find add new Icon on page!");
			return false;			
		}

		ClicksUtil.clickImageByAlt(IClicksConst.newApplicant);

		//ClicksUtil.clickImage(IClicksConst.newImg);

		if(!GeneralUtil.selectFullStringInDropdownList(IApplicantsConst.applicantTypeFO_DropDownId, foUser.getApplicantType()))
		{
			log.error("Could Not Find Applicant Type in Dropdown!");
			return false;
		}

		GeneralUtil.takeANap(1.0);

		GeneralUtil.setTextById(IApplicantsConst.applicantNameFO_FldID, foUser.getApplicantType());

		if(GeneralUtil.isTextFieldEmpty(IApplicantsConst.applicantNumberFO_FldID))
		{
			GeneralUtil.setTextById(IApplicantsConst.applicantNumber_FldID, foUser.getApplicantType());
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		if((GeneralUtil.checkForErrorMessages().isEmpty()) ||(GeneralUtil.checkForErrorMessages() == null))		
		{
			ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);
			return true;
		}

		return false;

	}

}
