/**
 * 
 */
package test_Suite.utils.workflow;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.src;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.title;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjActivConst;
import test_Suite.lib.workflow.Project;
import test_Suite.lib.workflow.ProjectActivity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Link;
import watij.elements.Links;
import watij.elements.TableBody;
import watij.elements.TableRow;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class ProjActivUtil {
	
	private static Log log = LogFactory.getLog(ProjActivUtil.class);
	
	static boolean retValue;
	
	static int rowIndex;
	
	/**
	 * @param projName
	 * @param projStatus
	 * @return
	 * @throws Exception
	 */
	public static boolean openProjectActivity(String projName, String projStatus) throws Exception{
		
		IE ie = IEUtil.getActiveIE();
		
		log.info("Open Project Activity");
		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		rowIndex = getProjectRowIndex(projName,projStatus);

		if (rowIndex > -1)
		{
			ie.link(title, "View Project Activities").click();
			
			// since 6.0 can't click on images in some icons, must use link
			
			retValue = true;	
		}		
		
		return retValue;		
	}
	
public static boolean CompleteProjectActivityNotifDetails(ProjectActivity projActivity) throws Exception{
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		ClicksUtil.clickButtons(IClicksConst.projectActivityDetailsBtn);
						
		ie.checkbox(id, IProjActivConst.projActivity_Details_CompletedChkboxId).set(projActivity.isActivityCompleted());
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.projectListBackToProjectActivitiesBtn);
		
		retValue = true;
		
		return retValue;
		
	}
	
	public static int getProjectRowIndex(String projName, String projStatus) throws Exception {
		
		int retIndex = -1;
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDownHashTable = new Hashtable<String, String>();
		
		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, projName);
		drpDownHashTable.put(IFiltersConst.grantManagement_ProjectStatus_Lbl, projStatus);
		
		FiltersUtil.filterListByLabel(hashTable,drpDownHashTable, false);
		
		retIndex = TablesUtil.findInTable(ITablesConst.projectsTableId, new String[] {projName});
		
		return retIndex;
	}
	
	
	public static boolean openProjActivityNotificationLog(String projName, String projStatus) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		rowIndex = getProjectRowIndex(projName,projStatus);

		if (rowIndex > -1)
		{
			ie.table(id, ITablesConst.projectsTableId).body(id,"/tbody_element/").row(rowIndex).image(alt, "View Notification Log - " + projName).click();
			ClicksUtil.checkForErrorMessages();
			retValue = true;	
		}
		return retValue;
	}
	
	public static void transferProject(int applicantIndex) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
//		Thread dialogClicker = new Thread()
//		{
//			@Override
//			public void run() {
//				try
//				{
//					IE ie = IEUtil.getActiveIE();
//					
//					ConfirmDialog dialog1 = ie.confirmDialog();
//					
//					while (dialog1==null)
//					{
//						GeneralUtil.takeANap(0.250);
//					}
//					
//					dialog1.ok();				
//					
//				}
//				catch (Exception e)
//				{
//					throw new RuntimeException("Unexpected exception",e);
//				}
//			}
//		};
		
//		dialogClicker.start();
		
		ie.body(id,ITablesConst.transferProjectTableId).row(applicantIndex).image(src, "/move.gif/").click();
		
		GeneralUtil.takeANap(1.5);
		
		ie.button(attribute("class", IClicksConst.dialogConfirm_btnClass)).click();
		
		GeneralUtil.takeANap(1.5);
		
//		dialogClicker = null;
	}
	
	public static int getApplicantIndexInTransferProjectList(String applicantName, String applicantNumber) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = true;
		
		Hashtable<String,String> ht = new Hashtable<String,String>();
		
		ht.put(IFiltersConst.grantManagement_ApplicantName_Lbl, applicantName);
		ht.put(IFiltersConst.grantManagement_ApplicantNumber_Lbl, applicantNumber);
		
		FiltersUtil.filterListByLabel(ht, null, false);
		
		do
		{	
			TableBody bodyTable = ie.body(id, ITablesConst.transferProjectTableId);	
			
			rowIndex = -1;
			
			for (TableRow row1 : bodyTable.rows()) {
				
				rowIndex +=1;
				
				if(row1.cell(2).innerText().equals(applicantName) && row1.cell(3).innerText().equals(applicantNumber))
				{
					retValue = false;
					
					break;
				}
				
			}
			
		}while(goToNextPage());
		
		if (retValue)
		{
			rowIndex = -1;
		}
		
		return rowIndex;
		
	}
	
	
	public static boolean goToNextPage() throws Exception {
		
		IE ie = IEUtil.getActiveIE();	
		
		Links links = ie.table(id, "decadePaginator").links();			
		
		int pageNum = Integer.parseInt(ie.span(attribute("class", "listFooterSelected")).innerText());
		
		for (Link link1 : links) {
			
			if(Integer.parseInt(link1.innerText()) > pageNum)
			{
				ie.link(text, link1.innerText()).click();
				
				return true;
			}
			
		}		
		return false;
	}
	
	public static boolean openInstantNotificationDetails(String projName, String projStatus) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		rowIndex = getProjectRowIndex(projName,projStatus);

		if (rowIndex > -1)
		{
			ie.link(title, "Create Instant Notification").click();
			
			retValue = true;	
		}
		return retValue;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public static boolean openNewActivity() throws Exception{
		
		return (ClicksUtil.clickImage(IClicksConst.newImg));
		
	}
	
	
	/**
	 * @param activityPurpose
	 * @return
	 * @throws Exception
	 */
	public static boolean openActivity(String activityPurpose) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDownHashTable = new Hashtable<String, String>();
		
		hashTable.put(IFiltersConst.grantManagement_ActivityPurpose_Lbl, activityPurpose);
		
		FiltersUtil.filterListByLabel(hashTable,drpDownHashTable,false);
		
		rowIndex = TablesUtil.findInTable(ITablesConst.projectActivitiesTableId, new String[] {activityPurpose});
		
		if (rowIndex > -1)
		{
			ie.body(id, ITablesConst.projectActivitiesTableId).row(rowIndex).image(alt,"View Project Activity").click();
			
			retValue = true;	
		}		
		
		return retValue;		
	}
	
	public static int getHowManyInteriesInNotificationLogList(String notificationName) throws Exception {
		
		int numOFEntries = 0;
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDownHashTable = new Hashtable<String, String>();
		
		hashTable.put(IFiltersConst.grantManagement_NotificationName_Lbl, notificationName);
		
		FiltersUtil.filterListByLabel(hashTable,drpDownHashTable,false);
		
		numOFEntries = TablesUtil.findHowManyInTable(ITablesConst.notificationLogTableId, notificationName);	
		
		
		return numOFEntries;
	}
	
	/**
	 * @param projActivity
	 * @return
	 * @throws Exception
	 */
	public static boolean addProjectActivityDetails(ProjectActivity projActivity) throws Exception{
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		ClicksUtil.clickButtons(IClicksConst.projectActivityDetailsBtn);
		
		ie.selectList(id, IProjActivConst.projActivity_Details_TypeDrpDwnId).select(projActivity.getActivityType());
		
		ie.selectList(id, IProjActivConst.projActivity_Details_StatusDrpDwnId).select(projActivity.getActivityStatus());
		
		ie.textField(id, IProjActivConst.projActivity_Details_DateFldId).set(projActivity.getActivityDate());
		
		ie.textField(id, IProjActivConst.projActivity_Details_DueDateFldId).set(projActivity.getActivityDueDate());
		
		ie.textField(id, IProjActivConst.projActivity_Details_PurposeFldId).set(projActivity.getActivityPurpose());
		
		ie.textField(id, IProjActivConst.projActivity_Details_DescriptionFldId).set(projActivity.getActivityDescription());
		
		ie.checkbox(id, IProjActivConst.projActivity_Details_CompletedChkboxId).set(projActivity.isActivityCompleted());
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.projectListBackToProjectActivitiesBtn);
		
		retValue = true;
		
		return retValue;
		
	}
	
	public static boolean AddProjectInstantNotif(ProjectActivity projActivity) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		ie.textField(id, IProjActivConst.projActivity_Notif_NameTxtId).set(projActivity.getActivityNotifName());

		ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "0" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
		
		ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageBody_StartFldId + "0" + IProjActivConst.projAddHoc_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());
		
		if (ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "1" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).exists())
		{
			ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "1" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
			
			ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageBody_StartFldId + "1" + IProjActivConst.projAddHoc_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());

		}
		
		if (ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "2" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).exists())
		{
			ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "2" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
			
			ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageBody_StartFldId + "2" + IProjActivConst.projAddHoc_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());
		}
		
		if (ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "3" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).exists())
		{
			ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageSubject_StartFldId + "3" + IProjActivConst.projAddHoc_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
			
			ie.textField(id,IProjActivConst.projAddHoc_Notif_MessageBody_StartFldId + "3" + IProjActivConst.projAddHoc_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());
		}
		
		String[] tempArray = projActivity.getActivityNotifRecipients();
		
		for(int i=0; i<projActivity.getActivityNotifRecipients().length; i++)
		{
			ie.selectList(id, IProjActivConst.projAddHoc_Notif_AvailableRecipientstSelectId).select(tempArray[i]);
			
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}
		
		ClicksUtil.clickButtons(IClicksConst.sendBtn);
		
		ClicksUtil.clickButtons(IClicksConst.projectListBackToProjectListBtn);
		
		
		return true;		
	}
	
	
	/**
	 * @param projActivity
	 * @return
	 * @throws Exception
	 */
	public static boolean addProjectActivityNotification(ProjectActivity projActivity) throws Exception{
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.projectActivityNotificationsBtn);
		
		if(projActivity.isActivityNotifActive())
		{
			ie.checkbox(id,IProjActivConst.projActivity_Notif_ActiveChkboxId).set();
		}else
		{			
			ie.checkbox(id,IProjActivConst.projActivity_Notif_ActiveChkboxId).clear();			
		}
		
		ie.textField(id, IProjActivConst.projActivity_Notif_DaysBeforeDueDateFldId).set(projActivity.getActivityNotifDaysBefore());
		
		ie.checkbox(id,IProjActivConst.projActivity_Notif_RepeatEveryChkboxId).set(projActivity.isActivityNotifRepeat());
		
		ie.textField(id,IProjActivConst.projActivity_Notif_RepeatAfterDueDateFldId).set(projActivity.getActivityNotifDaysUntil());
		
		
		ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "0" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
		
		ie.textField(id,IProjActivConst.projActivity_Notif_MessageBody_StartFldId + "0" + IProjActivConst.projActivity_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());
		
		if (ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "1" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).exists())
		{
			ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "1" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
			
			ie.textField(id,IProjActivConst.projActivity_Notif_MessageBody_StartFldId + "1" + IProjActivConst.projActivity_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());

		}
		
		if (ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "2" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).exists())
		{
			ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "2" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
			
			ie.textField(id,IProjActivConst.projActivity_Notif_MessageBody_StartFldId + "2" + IProjActivConst.projActivity_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());

		}
		
		if (ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "3" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).exists())
		{
			ie.textField(id,IProjActivConst.projActivity_Notif_MessageSubject_StartFldId + "3" + IProjActivConst.projActivity_Notif_MessageSubject_EndFldId).set(projActivity.getActivityNotifMessageSubjet());
			
			ie.textField(id,IProjActivConst.projActivity_Notif_MessageBody_StartFldId + "3" + IProjActivConst.projActivity_Notif_MessageBody_EndFldId).set(projActivity.getActivityNotifMessageBody());

		}
		
		String[] tempArray = projActivity.getActivityNotifRecipients();
		
		for(int i=0; i<projActivity.getActivityNotifRecipients().length; i++)
		{
			ie.selectList(id, IProjActivConst.projActivity_Notif_AvailableRecipientstSelectId).select(tempArray[i]);
			
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}
		
		ie.selectList(id, IProjActivConst.projActivity_Notif_MessageLocaleDrpDwnId).select(projActivity.getActivityNotifMessageLocale());
		
		ie.textField(id, IProjActivConst.projActivity_Notif_ExternalRecipientsFldId).set(projActivity.getActivityNotifExternalRecipients());
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.projectListBackToProjectActivitiesBtn);
		
		retValue = true;
		
		return retValue;		
	}
	
	/************* 
	 * new methods added
	 * June 08 2009 
	 * *************/
	
	public static boolean trnasferProjectToApplicant(Project proj, String projStatus) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);
		
		int indx = getProjectRowIndex(proj.getProjFullName(),projStatus);
		
		if(indx < 0) {
			
			return false;			
		}		
		
		if(!openTranferProjectList(indx,proj.getProjFullName()))
		{
			log.error("failed to Open Transfers List!");
			return false;
		}
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, proj.getTrnsfrToOrgFullName(), IFiltersConst.exact);
		
		indx = -1;
		
		indx = findTargetApplicantInList(proj.getTrnsfrToOrgFullName());
		
		if(indx < 0){
			return false;
		}	
		
		transferProject(indx);
		
		return true;
	}
	
	public static boolean openTranferProjectList(int indx, String projName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.link(title, "Transfer Project").exists())
		{
			log.error("Could Not Find Transfer Project Entry in Options List!");
			return false;
		}
		
		ie.link(title, "Transfer Project").click();
		
		return true;
	}
	
	public static int findTargetApplicantInList(String trgtApplicant) throws Exception {			
		
		TableBody listTable = TablesUtil.tableDiv().body(0);
		
		return TablesUtil.getRowIndexInTableWithPaginator(listTable.id(), trgtApplicant);		
	}

}
