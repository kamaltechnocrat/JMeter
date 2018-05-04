package test_Suite.utils.workflow;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.title;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.elements.Divs;
import watij.runtime.ie.IE;

public class NotificationsUtil {

	private static Log log = LogFactory.getLog(NotificationsUtil.class);

	public static String getPartNotificationId() throws Exception {

		IE ie = IEUtil.getActiveIE();
		String str = ie.htmlElement(title, "Manage Notification").id();
		String arr[] = str.split(":");

		return arr[arr.length - 1];

	}

	public static boolean navigateToNotifications(String programName) {
		try {
			IE ie = IEUtil.getActiveIE();

			if (!ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk)) {
				log.error("Could not click on link "
						.concat(IClicksConst.openFundingOppsListLnk));
				return false;
			}

			if (!ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/")
					.exists())
				return false;

			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(
					programName);

			ClicksUtil.clickButtons(IClicksConst.filterBtn);

			if (ie.link(text, programName).exists()) {
				ClicksUtil.clickLinks(programName);
				if (ie.image(alt, "/" + IProgramsConst.VIEW_STEP_DETAILS + "/")
						.exists() == false) // check if nodes expanded already
					ie.image(id, "/planner:0:2:t2/").click();
				if (!ie.image(alt, INotificationsConst.ntfManageNotifications)
						.exists())
					ie.image(id, "/planner:0:2:0:t2/").click();
			} else
				log.warn("WARNING: Program required for test case does not exist "
						+ programName
						+ " make sure that preTest runs before the test case");

		} catch (Exception ex) {
			log.debug("ERROR in navigateToNotifications() " + ex.getMessage());
		}
		return true;
	}

	public static boolean navigateToProjPlanner(String fundingOpportunityName) {
		try {
			IE ie = IEUtil.getActiveIE();

			if (!ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk)) {
				log.error("Could not click on link "
						.concat(IClicksConst.openFundingOppsListLnk));
				return false;
			}

			FiltersUtil.filterListByLabel(
					IFiltersConst.gpa_FundingOppIdent_Lbl,
					fundingOpportunityName, "Exact");

			if (ie.link(text, fundingOpportunityName).exists()) {

				ClicksUtil.clickLinks(fundingOpportunityName);
			}

			else {
				log.warn("WARNING: Program required for test case does not exist "
						+ fundingOpportunityName);
				return false;
			}

		}

		catch (Exception ex) {
			log.debug("ERROR in navigate to project planner " + ex.getMessage());
		}

		return true;
	}

	public static boolean openNotificationLogInProject(
			Project proj,String stepName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_NotificationLog_formId).exists()) {
			log.warn("I'm Already in Notificiations Log List!");
			
			return true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		if(stepName.equals("") || stepName.equals("---"))
		{
			proj.setCurrentStepIdent(stepName);
			
			proj.setCurrentStepName(stepName);
		}
		else
		{
			proj.initializeStep(stepName);
		}		
			
		
	  if (!TablesUtil.findInTable(ITablesConst.projectsTableId,	proj.getProjFullName())) 
	  {
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
		

//		if (!ie.body(id, ITablesConst.projectsTableId).row(index)
//				.link(title, IProjectsConst.project_NotificationLog_ImageAlt)
//				.exists()) {
//			
//			log.error("Could not find Image icon to click!");
//			return false;
//		}
		
		
		
		Divs divs = ie.divs(attribute("class", IClicksConst.projects_OptionsClick_DivClass));
		
		for (Div div : divs){
			if (div.id().equals("projectListForm:caseDataTable:"+index+":j_id_3r")){
				
				if (div.link(title,IProjectsConst.project_NotificationLog_ImageAlt).exists()){
					div.link(title,IProjectsConst.project_NotificationLog_ImageAlt).click();
					return true;
				}
			}
		}

		ie.body(id, ITablesConst.projectsTableId)
				.row(index)
				.link(title,
						IProjectsConst.project_NotificationLog_ImageAlt)
				.click();
		return true;
		
	}
	
	
	public static boolean openNotificationLogInProject_New(
			Project proj,String stepName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.form(id, IProjectsConst.project_NotificationLog_formId).exists()) {
			log.warn("I'm Already in Notificiations Log List!");
			
			return true;
		}

		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		if(stepName.equals("") || stepName.equals("---"))
		{
			proj.setCurrentStepIdent(stepName);
			
			proj.setCurrentStepName(stepName);
		}
		else
		{
			proj.initializeStep(stepName);
		}		
			
		
	  if (!TablesUtil.findInTable(ITablesConst.projectsTableId,	proj.getProjFullName())) 
	  {
			if (!FiltersUtil.filterListByProjectNew(proj)) {
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

		if (!ie.body(id, ITablesConst.projectsTableId).row(index)
				.link(title, IProjectsConst.project_NotificationLog_ImageAlt)
				.exists()) {
			
			log.error("Could not find Image icon to click!");
			return false;
		}

		ie.body(id, ITablesConst.projectsTableId)
				.row(index)
				.link(title,
						IProjectsConst.project_NotificationLog_ImageAlt)
				.click();
		return true;
	}
	
	public static int verifyNotificationLogEntries(
			String notificationName, String ddValue) throws Exception {

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTable.put(IFiltersConst.grantManagement_NotificationName_Lbl,
					notificationName);
			
			hashTableDropdown.put(IFiltersConst.grantManagement_NotificationName_Lbl,
					ddValue);

			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);
			
			return TablesUtil.howManyEntriesInTableWithPagenator(ITablesConst.notificationLogTableId);

	}
	
	
	public static int verifyNotificationLogEntries_SOV(
			String notificationName, String ddValue, String dateValue, int date) throws Exception {

			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();
			
			
			String dd = GeneralUtil.getTodayDate();
			
			GeneralUtil.setDayofMonth(date);

			hashTable.put(IFiltersConst.grantManagement_NotificationName_Lbl,
					notificationName);

			hashTable.put(IFiltersConst.grantManagement_DateSent_Lbl,
					dd);
			
			hashTableDropdown.put(IFiltersConst.grantManagement_NotificationName_Lbl,
					ddValue);
			
			hashTableDropdown.put(IFiltersConst.grantManagement_DateSent_Lbl,
					dateValue);
			
			
			
			FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);
			
			return TablesUtil.howManyEntriesInTableWithPagenator(ITablesConst.notificationLogTableId);

	}
	
	
}
