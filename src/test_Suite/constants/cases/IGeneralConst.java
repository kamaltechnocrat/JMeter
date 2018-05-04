package test_Suite.constants.cases;


public interface IGeneralConst {
	public static final String baseLetters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	public static final String licenceKey = "194-36-52-201-68-238-77-193"; //License Key for 1000 Users
	//##***** General **************
	
	public static final String login_UserName_TxtId = "left_bar:frmLogin:userName";
	public static final String login_Password_TxtId = "left_bar:frmLogin:password";
	public static final String login_Locale_DdId = "left_bar:frmLogin:locale_input";
	
	public static final String foLogin_UserName_TxtId = "g3-form:login-userName";
	public static final String foLogin_Password_TxtId = "g3-form:login-password";
	
	public static final String submissionName_Id = "g3-form:eFormFieldList:0:textBox";
	public static final String publicationDate_textField_Id = "g3-form:eFormFieldList:1:datePicker_input";
	public static final String scheduleDate_textField_Id = "g3-form:eFormFieldList:2:datePicker_input";
	public static final String publicationEndDate_textField_Id = "g3-form:eFormFieldList:3:datePicker_input";
    
	
	public static final String menuLeft_SpanId ="left_bar:menu:menuForm:menu";
	public static final String evalStaffSummary_And_Buttons_DivId = "g3-form:j_id_ft";
	public static final String evalStaffSummary_ApprovalShared_Text = "This is a shared Approval form. This Approval form can be submitted by any of the assigned evaluators.";
	public static final String evalStaffSummary_ReviewShared_Text = "This is a shared Review form. This Review form can be submitted by any of the assigned evaluators.";
	public static final String evalStaffSummary_Shared_Text = "There are 5 assigned evaluators:";
	
	//Steps Values
	public static final String submissionStep = "com.infoterra.grantium.model.program.step.ApplicantSingleSubmissionStep";
	public static final String programOfficeSubmissionStep = "com.infoterra.grantium.model.program.step.ProgramOfficeSingleSubmissionStep";
	public static final String reviewStep = "com.infoterra.grantium.model.program.step.ReviewStep";
	public static final String approvalStep = "com.infoterra.grantium.model.program.step.ApprovalStep";
	public static final String awardStep = "com.infoterra.grantium.model.program.step.AwardStep";
	public static final String postAwardStep = "com.infoterra.grantium.model.program.step.PostAwardStep";
	public static final String decisionStep = "com.infoterra.grantium.model.program.step.DecisionStep";
	
	public static final String uap_checkBox_disable_src = "/uap_toggle/disable.gif/";
	public static final String uap_checkBox_off_src = "/uap_toggle/off.gif/";
	public static final String uap_checkBox_on_src = "/uap_toggle/on.gif/";
	
	public static final String newChkBx_on_Span = "ui-chkbox-icon ui-icon ui-c ui-icon-check";
	public static final String newChkBx_off_Span = "ui-chkbox-icon ui-icon ui-icon-blank ui-c";
	public static final String planner_div_class = "ui-tree ui-widget ui-widget-content ui-corner-all";
	
	public static final String gnrl_ProgPrefix = "-Gnrl-";
	
	public static final String allowAmendNow = "Allow Amend Now";
	
	public static final String submissionA = "Submission A";
	
	public static final String approvalCRQA = "Approval CRQA";
	
	public static final String amendmentReason = "Test Issue Amendment with Amend In Place";
	
	public static final String amendmentComment = "This a Comment";
	
	public static final String amendmentCorrective = "Corrective";
	
	public static final String bulkEvaluation = "Bulk Evaluation";
	
	public static final String archiveLog = "Archive Log";
	
	public static final String gnrl_ProgName  = "Prog";
	public static final String gnrl_ProjPrefix = "\"gnrl.\"+%{G3Utils}.leftPadString(#{projectID}, 6, \"0\")";
	public static final String gnrl_OrgName    = "Org";
	public static final String gnrl_OrgNumber  = "7899";
	public static final String gnrl_ProjName   = "Proj";
	public static final String gnrl_PAPName   = "PAP";
	public static final String gnrl_FndOppName = "FndOpp";

	public static final String gnrl_FO_ProgName   = "FO-Prog";
	public static final String gnrl_FO_ProjPrefix = "GnrlF.";
	public static final String gnrl_FO_OrgName    = "FO-Org";
	public static final String gnrl_FO_OrgNumber  = "FO-7890-";
	public static final String gnrl_FO_ProjName   = "FO-Proj";

	//##***** Post-Award ****************
	public static final String pa_ProgPrefix = "-Gnrl-PA-";	
	public static final String pA_ProjPrefix  = "Pa.";
	
	public static final String pA_FO_ProjPrefix = "Fpa.";	

	//###**** Post-Award Bring Forward ******
	public static final String pa_BF_ProgPrefix = "-Gnrl-PA-BF-";
	
	public static final String pA_BF_ProjPrefix = "-Gnrl-PA-BF.";
	public static final String pA_BF_FO_ProjPrefix = "-Gnrl-FPA-BF.";
	
//	###***** Forms ******
	public static final String gnrl_SubmissionA[][] = {{"Submission-A", "General Submission", "Applicant Submission", "true", "No"}, {"Sub", "General Submission"}};
	public static final String gnrl_SubmissionB[][] = {{"Submission-B", "General Submission", "Applicant Submission", "true", "No"}, {"Sub", "General Submission"}};
	
	public static final String gnrl_ReviewA[][] = {{"Review-A", "Review Form", "Review", "true", "No"}, {"Project Review Form", "Quorum", "1", "false"}};
	public static final String gnrl_ApprovA[][] = {{"Approval-A", "Approval Form", "Approval", "true", "No"}, {"Project Approval Form", "Quorum", "1", "false"}};
	
	public static final String gnrl_ReviewB[][] = {{"Review-B", "Review Form", "Review", "true", "No"}, {"Project Review Form", "Quorum", "1", "false"}};
	public static final String gnrl_ApprovB[][] = {{"Approval-B", "Approval Form", "Approval", "true", "No"}, {"Project Approval Form", "Quorum", "1", "false"}};

	public static final String gnrl_AwardCrit[][] = {{"Award", "Award Form", "Award", "true", "Yes"}, {"Basic Award", "Award Form"}};
	public static final String gnrl_AwardNon[][] = {{"Award", "Award Form", "Award", "false", "No"}, {"Basic Award", "Award Form"}};
	
//	###***** Post-Award Type Forms ******

	public static final String reviewQuoCritAuto[][] = {{"Review-CRQA", "Review Form", "Review", "true", "Optional (Yes)"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewMajoCritAuto[][] = {{"Review-CRMA", "Review Form", "Review", "true", "Yes"}, {"Project Review Form", "Majority", "", "true"}};   
    public static final String reviewUnanCritAuto[][] = {{"Review-CRUA", "Review Form", "Review", "true", "Yes"}, {"Project Review Form", "Unanimous", "", "true"}};
    
    public static final String pa_Submission[][] = {{"Submission", "PA Submission", "Applicant Submission", "true", "No"}, {"Sub", "PA Submission"}};
    
    public static final String reviewAQuoCritAuto[][] = {{"Review-A-CRQA", "Review Form", "Review", "true", "Optional (Yes)"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewBQuoCritAuto[][] = {{"Review-B-CRQA", "Review Form", "Review", "true", "Optional (Yes)"}, {"Project Review Form", "Quorum", "1", "true"}};

    
    public static final String reviewQuoCritAutoA[][] = {{"Review-CRQA-A", "Review Form", "Review", "true", "Optional (Yes)"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewQuoCritAutoB[][] = {{"Review-CRQA-B", "Review Form", "Review", "true", "Optional (Yes)"}, {"Project Review Form", "Quorum", "1", "true"}};

    public static final String reviewQuoNonAutoA[][] = {{"Review-NNQA-A", "Review Form", "Review", "false", "No"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewQuoNonAutoB[][] = {{"Review-NNQA-B", "Review Form", "Review", "false", "Yes"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewQuoNonAutoC[][] = {{"Review-NNQA-C", "Review Form", "Review", "false", "No"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewQuoNonAutoD[][] = {{"Review-NNQA-D", "Review Form", "Review", "false", "Yes"}, {"Project Review Form", "Quorum", "1", "true"}};

    public static final String reviewQuoNonAuto[][] = {{"Review-NNQA", "Review Form", "Review", "false", "Optional (No)"}, {"Project Review Form", "Quorum", "1", "true"}};
    public static final String reviewMajoNonAuto[][] = {{"Review-NNMA", "Review Form", "Review", "false", "No"}, {"Project Review Form", "Majority", "", "true"}};   
    public static final String reviewUnanNonAuto[][] = {{"Review-NNUA", "Review Form", "Review", "false", "No"}, {"Project Review Form", "Unanimous", "", "true"}};

    public static final String approvQuoNonAuto[][] = {{"Approval-NNQA", "Approval Form", "Approval", "false", "Optional (No)"}, {"Project Approval Form", "Quorum", "1", "true"}};
    public static final String approvNonRexQuoAuto[][] = {{"Approval-NRQA", "Approval Form", "Approval", "false", "Yes"}, {"Project Approval Form", "Quorum", "1", "true"}};
    public static final String approvMajoNonAuto[][] = {{"Approval-NNMA", "Approval Form", "Approval", "false", "No"}, {"Project Approval Form", "Majority", "", "true"}};   
    public static final String approvUnanNonAuto[][] = {{"Approval-NNUA", "Approval Form", "Approval", "false", "No"}, {"Project Approval Form", "Unanimous", "", "true"}};
    
    public static final String approvQuoCritAuto[][] = {{"Approval-CRQA", "Approval Form", "Approval", "true", "Optional (Yes)"}, {"Project Approval Form", "Quorum", "1", "true"}};   
    public static final String approvMajoCritAuto[][] = {{"Approval-CRMA", "Approval Form", "Approval", "true", "Yes"}, {"Project Approval Form", "Majority", "", "true"}};   
    public static final String approvUnanCritAuto[][] = {{"Approval-CRUA", "Approval Form", "Approval", "true", "Yes"}, {"Project Approval Form", "Unanimous", "", "true"}};


    //###**** Post Award *****
      
	public static final String pa_AwardCrit[][] = {{"Award-Crit", "Award Form", "Award", "true", "Yes"}, {"Standard Award", "Award Form"}};
	public static final String pa_AwardCritB[][] = {{"Award-Crit-B", "Award Form", "Award", "true", "Optional (No)"}, {"Standard Award", "Award Form"}};
	
	public static final String pa_GPS_AwardCrit[][] = {{"Award-Crit", "Award Form", "Award", "true", "Yes"}, {"/Standard Payment Schedule/"}};
	public static final String pa_GPS_AwardNon[][] = {{"Award-Non", "Award Form", "Award", "true", "No"}, {"/Standard Payment Schedule/"}};

	public static final String initialClaim[][] = {{"Initial-Claim", "/Initial Post Award/", "Initial Post Award Submission", "true", "Optional (Yes)"}, {"Post Award Submission", "Claim ", "true"}};
	public static final String initialClaimB[][] = {{"Initial-Claim-B", "/Initial Post Award/", "Initial Post Award Submission", "true", "Optional (Yes)"}, {"Post Award Submission", "Claim ", "true"}};

	public static final String postAwardCritB[][] = {{"Post-Award-B", "/postAwardStep/", "Post-Award", "true", "No"}, {pa_AwardCritB[0][0], initialClaimB[1][0]}};
	public static final String postAwardCrit[][] = {{"Post-Award", "/postAwardStep/", "Post-Award", "true", "Yes"}, {pa_AwardCrit[0][0], initialClaim[1][0]}};
	
    public static final String reviewQuoCritManu[][] = {{"Review-CRQM", "Review Form", "Review", "true", "Optional (Yes)"}, {"Project Review Form", "Quorum", "1", "false"}};
    public static final String reviewMajoCritManu[][] = {{"Review-CRMM", "Review Form", "Review", "true", "Yes"}, {"Project Review Form", "Majority", "", "false"}};
    public static final String reviewUnanCritManu[][] = {{"Review-CRUM", "Review Form", "Review", "true", "Yes"}, {"Project Review Form", "Unanimous", "", "false"}};
    
    public static final String reviewQuoNonManu[][] = {{"Review-NNQM", "Review Form", "Review", "false", "Optional (No)"}, {"Project Review Form", "Quorum", "1", "false"}};
    public static final String reviewMajoNonManu[][] = {{"Review-NNMM", "Review Form", "Review", "false", "No"}, {"Project Review Form", "Majority", "", "false"}};
    public static final String reviewUnanNonManu[][] = {{"Review-NNUM", "Review Form", "Review", "false", "No"}, {"Project Review Form", "Unanimous", "", "false"}};
    
    public static final String approvQuoCritManu[][] = {{"Approval-CRQM", "Approval Form", "Approval", "true", "Optional (Yes)"}, {"Project Approval Form", "Quorum", "1", "false"}};   
    public static final String approvMajoCritManu[][] = {{"Approval-CRMM", "Approval Form", "Approval", "true", "Yes"}, {"Project Approval Form", "Majority", "", "false"}};   
    public static final String approvUnanCritManu[][] = {{"Approval-CRUM", "Approval Form", "Approval", "true", "Yes"}, {"Project Approval Form", "Unanimous", "", "false"}};   

    public static final String approvQuoNonManu[][] = {{"Approval-NNQM", "Approval Form", "Approval", "false", "Optional (No)"}, {"Project Approval Form", "Quorum", "1", "false"}};
    public static final String approvMajoNonManu[][] = {{"Approval-NNMM", "Approval Form", "Approval", "false", "No"}, {"Project Approval Form", "Majority", "", "false"}};
    public static final String approvUnanNonManu[][] = {{"Approval-NNUM", "Approval Form", "Approval", "false", "No"}, {"Project Approval Form", "Unanimous", "", "false"}};

    //#******************************************************************************

	//******####****************************
	public static final String gnrl_Closing_Step[][]={{"Closing-Step","Closing-Form"},{"Closing-Form"}};
	
	
	//###****** Post-Award Bring Forward *********
	public static final String pA_BF_Submission[][] = {{"Submission", "/ApplicantSingleSubmissionStep/", "Applicant Single Submission Step", "true", "No"}, {"Simple BF Submission"}};
	public static final String pA_BF_InitialClaim[][] = {{"Initial-Claim", "/ApplicantSingleSubmissionStep/", "Initial Claim Step", "true", "No"}, {"PA BF Claim Submission", "Claim BF Submission ", "true"}};


	//########***************************************
	public static final String PO_Submission[][] = {{"PO-Submission", "POSS Submission", "Program Office Submission", "true", "Optional (Yes)"}, {"Program Office Sub","POSS Submission"}};
	
	public static final String PO_SubmissionA[][] = {{"PO-Submission-A", "POSS Submission", "Program Office Submission", "true", "Optional (Yes)"}, {"Program Office Sub","POSS Submission"}};
	public static final String PO_SubmissionB[][] = {{"PO-Submission-B", "POSS Submission", "Program Office Submission", "true", "Optional (No)"}, {"Program Office Sub","POSS Submission"}};
	public static final String PO_SubmissionC[][] = {{"PO-Submission-C", "POSS Submission Non", "Program Office Submission", "false", "Yes"}, {"Program Office Sub Non", "POSS Submission Non"}};
	public static final String PO_Submission_NonD[][] = {{"PO-Submission-Non-D", "POSS Submission Non", "Program Office Submission", "false", "No"}, {"Program Office Sub Non", "POSS Submission Non"}};
	public static final String PO_Submission_NonC[][] = {{"PO-Submission-Non-C", "POSS Submission Non", "Program Office Submission", "false", "No"}, {"Program Office Sub Non", "POSS Submission Non"}};
	
	public static final String PO_Submission_Non[][] = {{"PO-Submission-Non", "POSS Submission Non", "Program Office Submission", "false", "No"}, {"Program Office Sub Non", "POSS Submission Non"}};

	
	//##########***************************
	public static final String workflowSuspensionA[] = {"Workflow Suspension-A","","Workflow Suspension", "true","Yes"};
	public static final String workflowSuspensionB[] = {"Workflow Suspension-B","","Workflow Suspension", "true","Yes"};
	public static final String workflowSuspensionC[] = {"Workflow Suspension-Non-C","","Workflow Suspension", "false","Yes"};
	public static final String workflowSuspensionD[] = {"Workflow Suspension-Non-D","","Workflow Suspension", "false","Yes"};
	
	//##########***************************
	public static final String decisionA[] = {"Decision Step-A","","Decision", "true","Yes"};
	public static final String decisionB[] = {"Decision Step-B","","Decision", "true","Yes"};
	public static final String decisionC[] = {"Decision Step-Non-C","","Decision", "false","Yes"};
	public static final String decisionD[] = {"Decision Step-Non-D","","Decision", "false","Yes"};
	//##########***************************
	//--- Specialty Steps
	public static final String gnrl_Closing_Approval_Step [][] = {{"Approval Closing Step", "Approval Form", "Approval", "true", "No"}, {"Project Approval Form", "Unanimous", "", "false"}};
	public static final String gnrl_Closing_POSS_Step[][] = {{"POSS Closing Step", "POSS Submission", "Program Office Submission", "true", "Optional (No)"}, {"Program Office Sub","POSS Submission"}};
	public static final String gnrl_RegionalSubA[][] = {{"Submission-A", "Regional Submission", "Applicant Submission", "true", "No"}, {"Sub Regions", "Regional Submission"}};
	public static final String gnrl_RegionalSubB[][] = {{"Submission-B", "Regional Submission", "Applicant Submission", "true", "Optional (No)"}, {"Sub Regions", "Regional Submission"}};



	//###*******To Fill a Grid Form (13 X 4)
	public static final int arr13X4Grid[][]  = {{11, 12, 13, 14}, 
		{21, 22, 23, 24}, 
		{31, 32, 33, 34}, 
		{41, 42, 43, 44},
		{51, 52, 53, 54},
		{61, 62, 63, 64},
		{71, 72, 73, 74},
		{81, 82, 83, 84},
		{91, 92, 93, 94},
		{101, 102, 103, 104},
		{111, 112, 113, 114},
		{121, 122, 123, 124},
		{131, 132, 133, 134},
		{141, 142, 143, 144}};  

	/****************************************************
        #             Lookup List
        #****************************************************/ 

	public static final String lookupLists = "-Gnrl-Lookup-List";
	public static final String lookupValues[] = {"-Gnrl-Second-Value", "-Gnrl-First-Value", "-Gnrl-Third-Value"};

	//#*****************************************************

	//###******Organization Profile  
	public static final String legalName   = "Test Case Inc";
	public static final String partner      = "Ouia Inc";
	public static final String eIN         = "TC-1234567";
	public static final String dUNS        = "-Duns";
	public static final String shortName   = "TCase";
	public static final String partnerType = "A. State Government";
	public static final String address     = "1 King St";
	public static final String streetNo    = "279";
	public static final String streetName  = "Laurier Ave. West";
	public static final String city        = "Ottawa";
	public static final String province    = "Ontario";
	public static final String state       = "Alabama";
	public static final String postalCode  = "K1P 5J9";
	public static final String country     = "Canada";
	public static final String applicant    = "Ouia 1";
	public static final String allVersions = "All Versions";

	//***Contact Detail  
	public static final String language    = "English";
	public static final String salutation  = "Mr";
	public static final String phone       = "123-123-1234";

	public static final String firstNameA  = "Mabb";
	public static final String lastNameA   = "Shak";
	public static final String emailA      = "mabb@grantium.com";

	public static final String firstNameB  = "Shak";
	public static final String lastNameB   = "Mabb";
	public static final String emailB      = "shak@grantium.com";

	public static final String firstNameC  = "Mustafa";
	public static final String lastNameC   = "Shakshouki";
	public static final String emailC      = "mustafa@grantium.com";
	
	public static final String searchWords = "General Words to search for in the Funding Opp";
	
	public static final String wizardsWords[] = {"General","Words","to","search","for","in","the","Funding", "Opp"};

    public static final String primG3_OrgRoot = "G3";
    
    public static final String org_Access_Public = "Public";
    public static final String org_Access_Shared = "Shared";
    public static final String org_Acceess_Restricted = "Restricted";
    
    public static final String dASH         = " - ";
    
    public static final String FILE_EXT_XML = ".xml";
    
    public static final String objPrograms    = "Programs";
    
    public static final String objForms       = "Forms";
    
    public static final String objReports     = "Reports";
    
    public static final String objLookups     = "Lookups";
    
    public static final String objUsers       = "Users";
    
    public static final String objGroups      = "Groups";
    
    // ###****** Statuses *********
    public static final String statusAll = "All";
    
    public static final String statusActive   = "Active";
    
    public static final String statusInactive = "Inactive";
    
    public static final String xmlFilesPath = "src\\test_Suite\\xml_Files\\";
    
    public static final String pngFilesPath = "src\\test_Suite\\temp_downloads\\png\\";
    
    public static final String pdfFilesPath = "src\\test_Suite\\temp_downloads\\pdf\\";
    
    public static final String docsFilesPath = "src\\test_Suite\\temp_downloads\\docs\\";
    
    public static final String formsFilesPath = "src\\test_Suite\\temp_downloads\\eForms\\";
    
    public static final String foppFilesPath = "src\\test_Suite\\temp_downloads\\fopp\\";
    
    //###********** Front Office ****************
    
    public static enum EFOLists {
    	
    	ACCOUNT,PASSWORD,APPLICANTS,FOPP,PROJECTS,SUBMISSIONS
    }
    
    public static final String fo_ApplicantType_LblID = "g3-form:applicantTypeLabel";
    
    //###******************* Reference Tables *******************************###//
    
    public static final String refTableIdent_TxtFieldId = "RefTableDetailsForm:refTableIdentifier";
    
    public static final String refTableEForm_DropdownId = "RefTableDetailsForm:eForm_input";
    
    public static final String refTableOrg_DropdownId = "RefTableDetailsForm:organizationAccess_input";
    
    public static final String refTableDeleteAllContent_ChkboxId = "importReferenceTable:importReferenceTableForm:deleteAllRows";
    
    
    /******************************************
     *** Application Settings Fields IDs
     *******************************************/
    
    public static final String appSettings_GSSAAS_DropdownId = "applicationSettings:applicationSettingsForm:stepStaffOnBehalfSetting_input";
    
    //************************************
    
    
    public static final String textBoxId = "/textBox/";
    
    public static final String textBox0Id = "/0textBox/";
    
    public static final String class_g3mainContainer = "g3-mainContainer";
    
    public static final String class_g3errorSmall = "g3-errorSmall";
    
    public static final String class_errorSmall = "errorSmall";
    
    public static final String class_listFooterSelected = "listFooterSelected";
    
    public static final String locale = "/:locale/";
    
    public static final String select_English_CA = "English (Canada)";
    
    public static final String select_English_US = "English (U.S.)";
    
    public static final String select_English_UK = "English (UK)";
    
    public static final String selectNo = "No";
    
    public static final String selectYes = "Yes";
    
    public static final String selectApproved = "Approved";
    
    public static final String selectPendingApproval = "Pending Approval";
    
    public static final String comment_Ttl = "Comments:";
    
    public static final String commentId = "g3-form:eFormFieldList:2:textArea_Below";
    
    public static final String score_Ttl = "Score:";
    
    public static final String score = "5";
    
    public static final String comment = "This is a comment";
    
    public static final String requirementFulfilled_Ttl = "Requirement Fulfilled::";
    
    public static final String status = "Status:";
    
//    public static final String FOdefaultLocale = "left_bar:foLogin:defaultLocale";
    
//    public static final String menuForm_approval = "/left_bar:menu:menuForm:menu:0:0:0:0/";
    
//    public static final String menuFormImage_approval = "left_bar:menu:menuForm:menu:0:0:0:t2";
    
    public static final String url_externalReview = "externalReview.jsf";
    
    public static final String url_programOffice = "programOffice.jsf";
    
    public static final String url_frontOffice = "frontOffice.jsf";
    
    public static final String polink_Logout = "Logout";
    
    public static final String polink_here = "here";
    
    public static final String polink_DataLoader = "Data Loader";
    
    public static final String decadePaginator = "decadePaginator";
    
    public static final String drillDown = "/nav-plus-line/";
    
    public static final String radioButtonSet = "g3-form:eFormFieldList:0:radioButtonSet";
    
    public static final String auditReportingDirPath = "\\audit_reporting_data\\export";
    
    public static final String system_reportServerIntegration = "report_server_integration_url";
    
    public static final String system_reportServer = "report_server_url";
  
    public static final String propertiesFile = "src/test_Suite/deployment_path.properties";
  
    public static final String notifMatrixPropFile = "src/test_Suite/notification_Matrix.properties";
    
    public static final String IE_Version = "ie_version";
    
    public static final String IEVersion = "ieVersion";
    
    public static final String awardFormName = "BF-Award-eForm";
    
    public static final String postAwardFormName = "Post Award Submission";
    
    
    
  //************************************Data Archive****************************************************************//
    
    public static final String da_foppIdentifier_lbl = "Funding Opportunity Identifier";
     
    public static final String da_foppIdentifier_txtField_value = "A-Gnrl-PA-FOPP";
    
    public static final String da_foppIdentifier_value = "A-PEF-PA-FOPP-Pushback-QH";
        
    public static final String da_foppIdentifier_dropDown_value = "Starts with";
    
    public static final String da_foppName_lbl = "Funding Opportunity Name";
    		
    public static final String da_foppStartDate_lbl = "Funding Opportunity Start Date";
            
    public static final String da_foppEndDate_lbl = "Funding Opportunity End Date";    
    
    public static final String da_projectName_lbl = "Project Name";
                                                                        
    public static final String initialClaim_pa = "A Gnrl PA Initial Claim";
    
    public static final String da_projectNumber_lbl = "Project Number";
        
    public static final String da_projectStatus_lbl ="Project Status";
        
    public static final String da_closedProjStatus_value = "Closed Projects";
    
    public static final String da_openProjStatus_value = "Open Projects";
    
    public static final String da_allProjStatus_value = "All Projects";
    
    
    public static final String archiveYears_Lbl = "DATA_ARCHIVE_YEARS";
    
    public static final String txtField_value = "0";
    
    public static final String newPASuffix = "-pa";
    
    public static final String cancelArchive_ttl = "Cancel Archive";
                          
    public static final String downloadArchiveLog_Id ="archiveLogForm:archiveLogTable:0:downloadArchivedFile";
    
    public static final String archiveConfig_ContentInfo_Id = "archiveConfigurationSubView:archiveConfigurationForm:contentInformation";
   
    public static final String archiveConfig_Disclaimer_Id = "archiveConfigurationSubView:archiveConfigurationForm:disclaimer";
    
    public static final String da_submissionVersion_lbl = "Submission Version";
    
    //Drop down filters:    
	public static final String closedProj_dd = "Closed Projects";
	
	public static final String readyArchive_dd = "Ready for Archive";
	
	public static final String schedArchive_dd = "Scheduled for Archive";
	
	public static final String archived_dd = "Archived";
	
	public static final String schedDeletion_dd = "Scheduled for Deletion";


	
}
