/**
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IErrorConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.Notifications;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

/**
 * @author apankov
 *
 */
@Test(singleThreaded = true)
public class S2625_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	IE ie;

	private String programName = IGeneralConst.baseLetters[0]+IGeneralConst.gnrl_ProgPrefix+IGeneralConst.gnrl_ProgName;
	private String [] notificationSubjects = {this.getClass().getName(), this.getClass().getName(), 
			this.getClass().getName()};
	private String [] notificationBodies   = {"Message body", "Message body", "Message body"};
	private String [] notificationIntRecip = {"Super", "Staff"};
	private String [] notificationExtRecip = {"qa_admin@grant-nds-06.grantium.com"};

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		ie = IEUtil.getActiveIE();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
		// ----------------------------------
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "NotificationsNG" }, enabled=false)
	/**
	 * Parent Method
	 */
	public void s2625_01NG() throws Exception 
	{
		
		navigateToNotifications();
		closeBrowser();
	}
	
	private void navigateToNotifications()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programName);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programName).exists())
			{				
				ClicksUtil.clickLinks(programName);	
				int progStepsIconId = HtmlFormsUtil.findProgStepsNodeId();
				log.info("Steps Icon id for this program: " + progStepsIconId);
				String plannerFirstPortion = IClicksConst.plannerExpanderLeftPortion+":"+progStepsIconId;
//				//check if nodes expanded already, and if not - expand, then take first step to look for notifications
				if(!ie.image(alt, INotificationsConst.ntfManageNotifications).exists())
					ie.image(id, plannerFirstPortion+":0"+IClicksConst.plannerExpanderRightPortion).click();
				ClicksUtil.clickImageByAlt(INotificationsConst.ntfManageNotifications);
				
				//verify existence of new controls
				if(ie.selectList(id, INotificationsConst.ntfLocaleSelect).exists())//locale select
					log.info(IErrorConst.tstTestPassed + " control " + 
							INotificationsConst.ntfLocaleSelect + " exists");
				else
					log.warn(IErrorConst.tstTestFailed + " control " + 
							INotificationsConst.ntfLocaleSelect + " does not exist");
				if(ie.textField(id, INotificationsConst.ntfExternalRecipients).exists())//external recipients
					log.info(IErrorConst.tstTestPassed + " control " + 
							INotificationsConst.ntfExternalRecipients + " exists");
				else
					log.warn(IErrorConst.tstTestFailed + " control " + 
							INotificationsConst.ntfExternalRecipients + " does not exist");
							
			}
			else
				log.warn("WARNING: Program required for test case does not exist " + programName  
						+ " make sure that preTest runs before the test case");
			//create new notification
			if(Ntf() != null)
				HtmlFormsUtil.fillNotificationsDetails(Ntf());
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in navigateToNotifications() " + ex.getMessage());
		}
	}
	
	private Notifications Ntf()
	{
		try
		{
			Notifications ntf = new Notifications();
			ntf.setNtfIsActive(true); //Otherwise it fill be set to false by default if no value provided
			ntf.setNtfName(this.getClass().getName() + "-" + String.valueOf((int)(Math.random() * 100)));
			ntf.setNtfMessageSubject(notificationSubjects);
			ntf.setNtfMessageBody(notificationBodies);
			ntf.setNtfInternalRecipients(notificationIntRecip);
			ntf.setNtfExternalRecepients(notificationExtRecip);
			return ntf;
		}
		catch(Exception ex)
		{
			log.debug("ERROR in Ntf() " + ex.getMessage());
			return null;
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