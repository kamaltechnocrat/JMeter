package test_Suite.constants.ui;


public interface IConfigConst {
	
	/**************************************************
	 * Final IDs Index of Configration List TextFileds
	 **************************************************/
	
	public static final String administratorEmail_Lbl = "ADMINISTRATOR_EMAIL";
	
	public static final String associate_applicant_access_url_Lbl = "ASSOCIATE_APPLICANTS_ACCESS_URL";
	
	public static final String auditReortingExportDirectory_Lbl = "AUDIT_REPORTING_EXPORT_DIRECTORY";
	
	public static final String automatedEmail_Lbl = "AUTOMATED_EMAIL";
	
	public static final String automatedNotificationEmail_Lbl = "AUTOMATED_NOTIFICATION_EMAIL";
	
	public static final String changePwdOnLoginFO_Lbl = "CHANGE_PWD_ON_LOGIN_FO";
	
	public static final String changePwdOnLoginFODay_Lbl = "CHANGE_PWD_ON_LOGIN_FO_DAY";
	
	public static final String changePwdOnLoginPO_Lbl = "CHANGE_PWD_ON_LOGIN_PO";
	
	public static final String changePwdOnLoginPODay_Lbl = "CHANGE_PWD_ON_LOGIN_PO_DAY";
	
	public static final String emailErrorMsgVerbosity_Lbl = "EMAIL_ERROR_MSG_VERBOSITY";
	
	public static final String etlFilePath_Lbl = "ETL_FILE_PATH";

	public static final String externalServerUri_Lbl = "EXTERNAL_SERVER_URI";
	
	public static final String grantsGovZipLocation_Lbl = "GRANTS_GOV_ZIP_LOCATION";
	
	public static final String licenseKey_Lbl = "LICENSE_KEY";
	
	public static final String listRowLimit_Lbl = "LIST_ROW_LIMIT";
	
	public static final String loginLockoutCount_Lbl = "LOGIN_LOCKOUT_COUNT";
	
	public static final String passwordMaxLength_Lbl = "PASSWORD_MAX_LENGTH (max. 100)";
	
	public static final String passwordMinLength_Lbl = "PASSWORD_MIN_LENGTH";
	
	public static final String pdfViewerUrl_Lbl = "PDF_VIEWER_URL";
	
	public static final String reportServerIntegrationUrl_Lbl = "REPORT_SERVER_INTEGRATION_URL";
	
	public static final String reportServerUrl_Lbl = "REPORT_SERVER_URL";
	
	public static final String sqlSpecialChars_Lbl = "SQL_SPECIAL_CHARS";
	
	public static final String uploadDocumentationPath_Lbl = "UPLOAD_DOCUMENTATION_PATH";
	
	public static final String uploadFormMapping_Lbl = "UPLOAD_FORM_MAPPING";
	
	public static final String usernameMaxLength_Lbl = "USERNAME_MAX_LENGTH";
	
	public static final String usernameMinLength_Lbl = "USERNAME_MIN_LENGTH";

	public static final String currentVersion_Lbl = "CURRENT_VERSION";
	
	public static final String grantiumCodeVersion_Lbl = "G3™ Code Version";
	
	public static final String bi_URL = "http://192.168.0.124:9300/p2pd";
	
	
	/**************************************************************
	 * Keys Generated Manually to be used in License Keys Feature
	 **************************************************************/
	
	public static final String licenseKey_10_Users = "185-222-54-19-57-101-193-61-34-3-132-185-219-57-4-20";
	
	public static final String licenseKey_15_Users = "185-222-54-19-57-101-193-61-237-102-71-238-161-147-51-81";
	
	public static final String licenseKey_20_Users = "185-222-54-19-57-101-193-61-204-221-169-169-173-169-61-50";
	
	public static final String licenseKey_62_Users = "23-204-89-242-255-69-1-136-202-87-38-69-10-133-192-190";
	
	public static final String licenseKey_80_Users = "211-155-69-252-83-133-129-51";
	
	public static final String licenseKey_90_Users = "85-191-199-7-131-50-20-125";
	
	public static final String licenseKey_95_Users = "236-79-89-81-226-147-113-121";
	
	public static final String licenseKey_100_Users = "254-120-193-24-10-255-22-136";
	
	public static final String licenseKey_150_Users = "211-98-213-213-59-146-53-112";
	
	public static final String licenseKey_200_Users = "46-91-191-176-19-153-17-18-244-205-125-175-64-136-122-73";
		
	public static final String licenseKey_1000_Users = "194-36-52-201-68-238-77-193";
	
	public static final String licenseKey_Invalid = "0";
	
	public static final String licenseKey_SystemDefault = "243-67-228-63-64-125-140-113-6-75-225-43-0-159-202-254";
	
	public static final String licenseKey_ReportViewer = "128-17-197-12-87-88-84-74-45-211-195-106-193-14-91-234";
	
	public static final String licenseKey_AdHocQuery = "128-17-197-12-87-88-84-74-41-79-153-162-18-218-224-227";
	
	public static final String licenseKey_ReportDesigner = "128-17-197-12-87-88-84-74-101-236-45-208-149-81-55-144";
	
	public static final String licenseKey_BiAdmin = "128-17-197-12-87-88-84-74-132-168-4-201-214-124-94-54";
	
	public static final String licenseKey_AuditReport = "79-67-123-193-251-18-65-194";
	
	public static final String[] licenseKey_Names = {"Grantium","IBM Cognos Consumer","IBM Cognos Business Author","IBM Cognos Professional Author","IBM Cognos Administrator", "External Reviewer Portal", "Audit Reporting"};
	
	public static final String[] licenseKey_Values = {
		"194-36-52-201-68-238-77-193",
		"128-17-197-12-87-88-84-74-45-211-195-106-193-14-91-234",
		"128-17-197-12-87-88-84-74-41-79-153-162-18-218-224-227",
		"128-17-197-12-87-88-84-74-101-236-45-208-149-81-55-144",
		"128-17-197-12-87-88-84-74-132-168-4-201-214-124-94-54",
		"239-186-2-114-108-138-43-68-159-106-251-95-180-181-43-101",
		"79-67-123-193-251-18-65-194"
		};
	
	public static enum licenseKeyNames {
		
		G3, VIEWER, ADHOC, DESIGNER, BIADMIN 
	}
	
	
	
	/***********************************************************
	 *  Front Office Configuration Ids
	 ************************************************************/	
	
	public static final String associateOrgToAppId = "/optionView:frontOfficeOption:enableOrgAppAssoc/";
	
	public static final String enablePublicationPortalId = "/optionView:frontOfficeOption:enablePublication/";
	
	public static final String enableFOUserProfileCreationId = "/optionView:frontOfficeOption:enableProfile/";
	
	/*************************************************************
	 ***	Application Settings Fields and Dropdwons IDs
	 *************************************************************/
	
	public static final String appSettings_ApplicantProfile_DropDownID = "applicationSettings:applicationSettingsForm:applicantWorkspaceForm_input";
	public static final String appSettings_UserProfile_DropDownID = "applicationSettings:applicationSettingsForm:userProfileForm_input";
	public static final String appSettings_BFUserProfToAppProf_DropDownID = "applicationSettings:applicationSettingsForm:userProfileBfToApplicantProfile_input";
	public static final String appSettings_UserProfileBF_ToApplicantProfile_DropDownID = "applicationSettings:applicationSettingsForm:userProfileBfToApplicantProfile_input";
	public static final String appSettings_AutoGenApplicantNumber_DropDownID = "applicationSettings:applicationSettingsForm:autoGen_input";
	public static final String appSettings_DefaultLocale_DropDownID = "applicationSettings:applicationSettingsForm:defaultLocale_input";
	public static final String appSettings_FOPPApprovalReq_DropDownID = "applicationSettings:applicationSettingsForm:papRequired_input";
	public static final String appSettings_ProjectOfficerAssignment_DropDownID = "applicationSettings:applicationSettingsForm:defaultShowAllProjectOfficerGroupUsers_input";
	public static final String appSettings_AvailableEvaluators_DropDownID = "applicationSettings:applicationSettingsForm:defaultShowAllAvailableEvaluatorsGroupUsers_input";
	public static final String appSettings_EnableEFormLocking_DropDownID = "applicationSettings:applicationSettingsForm:enforceFormLocking";
	
	public static final String appSettings_ApplicantNumberLength_TextFieldID = "applicationSettings:applicationSettingsForm:numberDigit";
	public static final String appSettings_ApplicantNumberPrefix_TextFieldID = "applicationSettings:applicationSettingsForm:numberPattern";
	
	public static final String appSettings_ApplicantStartNumebr_FldID = "applicationSettings:applicationSettingsForm:startingNumber";
	public static final String appSettings_ApplicantNumberPattern_FldID = "applicationSettings:applicationSettingsForm:numberPattern";
	
	public static final String appSettings_GrantStepStaffAccesstoAmendingSubmissions = "applicationSettings:applicationSettingsForm:stepStaffOnBehalfSetting";
	
	public static final String appSettings_UpdateEFormsWhenOpened = "applicationSettings:applicationSettingsForm:updateFormsOnOpen";
	
	
	


}
