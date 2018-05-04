/**
 * 
 */
package test_Suite.constants.workflow;

/**
 * @author mshakshouki
 *
 */
public interface IProjActivConst {
	
	/***************************************************************
	 * Project Activity Fields Id
	 **************************************************************/
	
	//************ Details *************
	
	public static final String projActivity_Details_TypeDrpDwnId = "/activityType/";
	
	public static final String projActivity_Details_StatusDrpDwnId = "/activityStatus/";
	
	public static final String projActivity_Details_DateFldId = "/caseActivityDate/";
	
	public static final String projActivity_Details_DueDateFldId = "/caseActivityDueDate/";
	
	public static final String projActivity_Details_PurposeFldId = "/caseActivityPurpose/";
	
	public static final String projActivity_Details_DescriptionFldId = "/caseActivityDescription/";
	
	public static final String projActivity_Details_CompletedChkboxId = "/completedCheck/";
	
	//*********** Notification ***********************
	
	//***********Instant
	
	public static final String projActivity_Notif_NameTxtId = "caseAdHocNotificationForm:notificationName";
	
	public static final String projAddHoc_Notif_MessageSubject_StartFldId = "caseAdHocNotificationForm:notificationTexts:";
	
	public static final String projAddHoc_Notif_MessageSubject_EndFldId = ":subject";
	
	public static final String projAddHoc_Notif_MessageBody_StartFldId = "caseAdHocNotificationForm:notificationTexts:";
	
	public static final String projAddHoc_Notif_MessageBody_EndFldId = ":txtBody";
	
	public static final String projAddHoc_Notif_AvailableRecipientstSelectId = "caseAdHocNotificationForm:availableRecipients_input";
	
	public static final String projAddHoc_Notif_RecipientstSelectId = "caseAdHocNotificationForm:recipients_input";
	
	//******************Activity***************
	
	public static final String projActivity_Notif_ActiveChkboxId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:activeCheck_input";
	
	public static final String projActivity_Notif_DaysBeforeDueDateFldId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:daysBeforeDueDate";
	
	public static final String projActivity_Notif_RepeatEveryChkboxId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:repeatAfterDueDateCheck_input";
	
	public static final String projActivity_Notif_RepeatAfterDueDateFldId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:repeatAfterDueDate";
	
	public static final String projActivity_Notif_AvailableRecipientstSelectId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:availableRecipients";
	
	public static final String projActivity_Notif_RecipientstSelectId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:recipients";
	
	public static final String projActivity_Notif_MessageLocaleDrpDwnId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:localeSelect_input";
	
	public static final String projActivity_Notif_ExternalRecipientsFldId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:freeFormEmails";
	
	public static final String projActivity_Notif_MessageSubject_StartFldId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:notificationTexts:";
	
	public static final String projActivity_Notif_MessageSubject_EndFldId = ":subject";
	
	public static final String projActivity_Notif_MessageBody_StartFldId = "caseActivityTabbedForm:manageCaseActivityTabbedPane:notificationTexts:";
	
	public static final String projActivity_Notif_MessageBody_EndFldId = ":txtBody";
	
	
	/************************************************
	 *  Constants to Use to Fill Activity Details
	 ************************************************/
	public static final String projActivity_Details_Type_Email = "E-mail";
	public static final String projActivity_Details_Type_Mail = "Mail";
	public static final String projActivity_Details_Type_SiteVisit = "Site Visit";
	
	public static final String projActivity_Details_Status_Caution = "Caution";
	public static final String projActivity_Details_Status_Critical = "Critical";
	public static final String projActivity_Details_Status_OK = "OK";	

	
	/***************************************************
	 *  Constnts to Use to Fill Activity Notifications
	 ***************************************************/
	
	public static final String projActivity_Notif_MessageSubject = "This is Grantium Inc ";
	
	public static final String projActivity_Notif_MessageBody = "This is Grantium ";
}
