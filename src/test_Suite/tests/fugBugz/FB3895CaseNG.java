package test_Suite.tests.fugBugz;

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.constants.cases.*;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.ui.*;
import test_Suite.lib.workflow.Notifications;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

public class FB3895CaseNG {
	
	private static Log log = LogFactory.getLog(FB3895CaseNG.class);
	IE ie;
	
	Notifications ntf;

	private String programName = IGeneralConst.baseLetters[0]+IGeneralConst.gnrl_ProgPrefix+IGeneralConst.gnrl_ProgName;
	
	private String [] notificationSubjects = {this.getClass().getName(), this.getClass().getName(), 
			this.getClass().getName()};
	
	private String notificationIntRecip = "Super";
	
	private String notificationName     = "FB3895";
	
	private String [] notificationBodies   = {"Message body", "Message body", "Message body"};
	
	private String [] notificationExtRecip = {"apankov@grantium.com", "alexpankov@hotmail.com"};
	
	@BeforeClass  
	public void setUp() {

	} 
	
	@Test(groups = { "FBCases" })
	/**
	 * Parent Method
	 */
	public void fB3895_01CaseNG() throws Exception 
	{
		IEUtil.openNewBrowser();
		ie = IEUtil.getActiveIE();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
		notificationName = notificationName + String.valueOf((int)(Math.random() * 100));
		NotificationsUtil.navigateToNotifications(programName);
		createNotification();
		openNotification();
		closeBrowser();
	}
	/**
	 * Creating new Notification 
	 * Using Notifications class and setting minimal - mandatory fields
	 */
	private void createNotification()
	{
		try
		{
			ClicksUtil.clickImageByAlt(INotificationsConst.ntfManageNotifications);
			ntf = new Notifications();
			ntf.setNtfIsActive(true); //Otherwise it fill be set to false by default if no value provided
			ntf.setNtfName(notificationName);
			ntf.setNtfMessageSubject(notificationSubjects);
			ntf.setNtfMessageBody(notificationBodies);
			ntf.setNtfExternalRecepients(notificationExtRecip);
			HtmlFormsUtil.fillNotificationsDetails(ntf);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createNotification()) " + ex.getMessage());

		}
	}
	/**
	 * Open Notification method
	 * Verifying that particular recipient of Notification is visible in ListBox
	 */
	private void openNotification()
	{
		try
		{
			if(!ie.image(alt, INotificationsConst.ntfEditNotification + " " + notificationName).exists())
				ie.image(id, "/planner:0:2:0:2:t2/").click();
			ClicksUtil.clickImageByAlt(INotificationsConst.ntfEditNotification + " " + notificationName);
			if(GeneralUtil.isObjectExistsInList(INotificationsConst.ntfAvailableRecipients, 
					notificationIntRecip) == true)
				log.info(IErrorConst.tstTestPassed);
			else
				log.warn(IErrorConst.tstTestFailed + 
						" Requested Internal Recepient does not exist: " + notificationIntRecip);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in openNotification() " + ex.getMessage());
		}
	}
	
	/**
	 * Close PO application and close web browser
	 *
	 */
	private void closeBrowser()
	{
		try
		{
			ntf = null;
			ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}
}
