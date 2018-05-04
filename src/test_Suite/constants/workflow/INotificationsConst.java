package test_Suite.constants.workflow;


public interface INotificationsConst {
	
	/*********************************************
    #         Progam, Projects Org etc
    #*********************************************/
	public static final String notif_Prefix = "-Notif-";

	//###*****Post award ********************
	public static final String notif_PA_Prefix = "-Notif-PA-";


	/****************************************************
    #             Notifications Steps
    #***************************************************/


	public static final boolean stepAllCheckBoxValues[][] = {{true, true, true,true,true,true},//0
		{true, true, false,false,false, true},//1
		{true, false, true,false,false, true},//2
		{true, false, false,true,false, true},	//3
		
		{true, true, true,true,true, false},//4
		{true, true, false,false,false, false},//5
		{true, false, true,false,false, false},//6
		{true, false, false,false,false, false},//7
		
		{false, true, true,true,true, true},//8
		
		{true, false, false,false,true,true},//9
		{true, true, true,false,false, true},//10
		{true, false, false,true,true, true},//11
		{true, false, false,false,false, true},//12
		
		{true, false, false,false,true,false},//13
		{true, true, true,false,false, false},//14
		{true, false, false,true,true, false},//15
		{true, false, false,false,false, false},//16
		{true, false, false,true,false, false}//17
		
	};

	public static final String stepEntryValues[] = {"Step Entry", "Step Entry", "Step entry to ",
	"Step Entry Notification to let you know that the step is started"};

	public static final String stepExitValues[] = {"Step Exit", "Step Exit", "Step exit from ",
	"Step Exit Notification to let you know that the step is Done "};
	
	public static final String stepEntryFromPausedValues[] = {"Step Entry from a Paused Step", "Step Entry from a Paused Step", "Step Entry from a Paused Step ",
	"Step Entry from a Paused Step Notification to let you know that the step now is paused "};

	public static final String stepRejectedValues[] = {"Rejected", "Approval Rejected", "Rejected Approval Step ",
	"Rejection Notification to let you know that the Submission has been Rejected "};

	public static final String stepAmendmentDueValues[] = {"Amendment Due", "Amendment Due", "Amendment is Due for ",
	"Notification to let you know that Amedment is Due now "};

	public static final String stepDueValues[] = {"Step Due", "Step Due", "Step is Due for ",
	"Notification to let you know that the step is Due now"};

	public static final String stepEvaluatorsNeededValues[] = {"Evaluator Needed", "Evaluators Needed", "Evaluators needed for ",
	"Notification to let you know that the step Needs Evalutors"};

	public static final String stepAmendmentRequestValues[] = {"Amendment", "Amendment Request", "Amendment requested on ",
	"Amendment Request Notification to let you know that Amedment has been issued"};

	public static final String stepEvaluatorAssignedValues[] = {"Evaluator Assigned", "Evaluator Assigned", "You have been Assigned to Evaluate ",
	"Notification to let you know that the Evaluator Has being Assigned"};
	
	public static final String stepPASubmissionEntry[] = {"Post-Award Submission Entry", "PA Submission Entry", "PA Step Entry to ",
	"Step Entry Notification to let you know that Post Award Step  has started"};

	public static final String stepPASubmissionExit[] = {"Post-Award Submission Exit", "PA Submission Exit", "PA Step Exit from ",
	"PA Submission Step Exit Notification to let you know that Post Award Step has Existed"};

	public static final String stepPASubmissionDue[] = {"Post-Award Submission Due", "PA Submission Due", "PA Step Due at ",
	"PA Submission Step Due Notification to let you know that Post Award Step is Due"};
	
	public static final String messageBody[][] = {{"Submission has been Started", "Submission is Done", "Submission has been Amended"},
		{"Review has been Started", "Review is Done", "No Rejection", "Review has been Amended"},
		{"Approval has been Started", "Approval is Done", "Approval has been Rejected", "Approval has been Amended"},
		{"Award has been Started", "Award is Done", "No Rejection", "Award has been Amended"},
		{"Post-Award has been Started", "Post-Award is Done", "No Rejection", "No Amendment", "Post-Award is Due Soon"}};
	
	// Fields on Notifications HTML page
	public static final String ntfIsActive                = "/activeCheck/";
	
	public static final String ntfName                    = "/notificationName/";
	
	public static final String ntfEvent                   = "/notification:eventSelect/";
	
	public static final String ntfInsertProjectActivities = "/insertProjectActivity/";
	
	public static final String ntfNotifyEvery             = "/notifyEvery/";
	
	public static final String ntfRepeatable              = "/repeatableCheck/";
	
	public static final String ntfRepeatEvery             = "/repeatEvery/";
	
	public static final String ntfNotifyApplicants        = "/notifyApplicantsCheck/";
	
	public static final String ntfDefineSendCondition = "/conditionalSendCheck/";
	
	public static final String ntfExpressionValue = "${Submission_Name}[%{AssociatedAwardSubmissionScheduleRow}|C]==" + "\"Claim 1;\"";
	
	public static final String ntfExpressionValue1 = "${Submission_Name}[%{AssociatedAwardSubmissionScheduleRow}|C]==" + "\"Payment;\"";
	
	public static final String ntfDatafromStep_Dropdwn_Id= "main:editNotification:notification:dataFromStep";
	
	public static final String ntfExpressionId="expressionBody";

	
	public static final String ntfNotifyProjOfficerForThisStep   = "main:editNotification:notification:notifyProjectOfficerSettingSelect";
	
	public static final String ntfNotifyAllProjOffForOtherSteps   = "main:editNotification:notification:notifyOtherProjectOfficerSettingSelect";
	
	
	public static final String ntfNotifyProjectOfficer   = "/notifyProjectOfficerCheck/";
	
	public static final String ntfNotifyAllProjectOfficers   = "/notifyAllProjectOfficersCheck/";
	
	public static final String ntfNotifyAsignedStepEvaluators   = "main:editNotification:notification:notifyAssignedStepEvaluatorsCheck";
	
	public static final String ntfSubject                 = "subject/";
	
	public static final String ntfBody                    = "txtBody/";
	
	public static final String ntfAvailableRecipients     = "/availableRecipients/";
	
	public static final String ntfSelectedRecipients      = "/recipients/";
	
	public static final String ntfLocaleSelect            = "/notification:localeSelect/";
	
	public static final String ntfExternalRecipients      = "freeFormEmails";
	
	
	
	// Other
	public static final String ntfManageNotifications    = "Manage Notification";
	
	public static final String ntfEditNotification       = "Edit Notification";
	
	// FOPP's Notification Name
	
	public static final String gnrlNotifFOPPNotificationsName []={"Notification Flag Disabled","Group Assignment Notification","Amendment - Project Officers Assigned",
		                                                           "Evaluator Needed","Amendment - Evaluator Needed","Step Entry - Immediate","Step Exit - Immediate",
		                                                           "Amendment - Step Entry","Amendment - Step Exit"};

	public static final String decisionNotifFOPPNotificationName []={"Applicant Amendment Requested","Applicant Amendment Request Rejected","Approval Step Notification",
		                                                            "Approval Step Exit","Approval Step Amendment","Award Step Notification","Award Step Amendment",
		                                                            "Step Exit Notification"};
    public static final String assocPAFOPPNotificationName []= {"Submission Created","Submission Completed","Deadline Reminder","Submission Completed"};
    
    public static final String notifyEvalPAFOPPNotificationName []={"Evaluator Assigned","Step Entry","Step Exit","Amendment","Step Due",
    	                                                            "Amendment Due","Step Entry from a Paused Step"};
    
    public static final String notifAmendFOPPNotificationName []={"Evaluator Assigned","Review Step Entry","Review Step Amendment","Step Entry from a Paused Step",
    	                                                          "Approval Step Amendment"};
    public static final String dueNotifPAFOPPNotificationName []={"Amendment Due","Step Due - A Notif Approval","Step Due Amendment","Amendment Alone",
    	                                                          "Post Award Submission Due","Post Award Submission Entry","Post Award Submission Exit",
    	                                                          "Post Award Submission Amendment"};

    public static final String notifPAFOPPS1905_01_NotifName []= {"Step Entry - A Notif PA Submission A S1905_01",
    		                                                      "Step Exit - A Notif PA Submission A S1905_01",
    		                                                      "Step Entry - A Notif PA Review CRQM S1905_01",
    		                                                      "Step Exit - A Notif PA Review CRQM S1905_01",
    		                                                      "Step Entry - A Notif PA Approval CRQA S1905_01",
                                                                  "Step Exit - A Notif PA Approval CRQA S1905_01",
                                                                  "Step Entry - A Notif PA Award Crit S1905_01",
                                                                  "Step Exit - A Notif PA Award Crit S1905_01",
                                                                  "PA Sub Entry - A Notif PA Initial Claim pa S1905_01",
                                                                  "PA Sub Exit - A Notif PA Initial Claim pa S1905_01",
                                                                  "Step Entry - A Notif PA Review CRQA pa S1905_01",
                                                                  "Step Exit - A Notif PA Review CRQA pa S1905_01",
                                                                  "Step Entry - A Notif PA Approval CRQM pa S1905_01",
                                                                  "Step Exit - A Notif PA Approval CRQM pa S1905_01",
                                                                  "Step Entry - A Notif PA Post Award S1905_01",
                                                                  "Step Exit - A Notif PA Post Award S1905_01",
                                                                  "Step Entry - A Notif PA Submission pa S1905_01",
                                                                  "Step Exit - A Notif PA Submission pa S1905_01"};
    
   public static final String notifPAFOPPS1905_02_NotifName []=  {"Amendment Request - A Notif PA Submission A S1905_02",
	                                                              "Amendment Due - A Notif PA Submission A S1905_02",
	                                                              "Amendment Request - A Notif PA Review CRQM S1905_02",
	                                                              "Amendment Due - A Notif PA Review CRQM S1905_02",
	                                                              "Amendment Request - A Notif PA Approval CRQA S1905_02",
	                                                              "Amendment Due - A Notif PA Approval CRQA S1905_02",
	                                                              "Amendment Request - A Notif PA Award Crit S1905_02",
                                                                  "Amendment Due - A Notif PA Award Crit S1905_02",
                                                                  "Amendment Request - A Notif PA Post Award S1905_02",
                                                                  "Amendment Due - A Notif PA Post Award S1905_02",
                                                                  "Amendment Request - A Notif PA Initial Claim pa S1905_02",
                                                                  "Amendment Due - A Notif PA Initial Claim pa S1905_02",
                                                                  "App Amend Requested -  A Notif PA Initial Claim pa S1905_02",
                                                                  "App Amend Req Rejected - A Notif PA Initial Claim pa S1905_02",
                                                                  "Amendment Request - A Notif PA Review CRQA pa S1905_02",
                                                                  "Amendment Due - A Notif PA Review CRQA pa S1905_02",
                                                                  "Amendment Request - A Notif PA Approval CRQM pa S1905_02",
                                                                  "Amendment Due - A Notif PA Approval CRQM pa S1905_02",
                                                                  "Amendment Request - A Notif PA Submission pa S1905_02",
                                                                  "Amendment Due - A Notif PA Submission pa S1905_02",
                                                                  "App Amend Requested -  A Notif PA Submission pa S1905_02",
                                                                  "App Amend Req Rejected - A Notif PA Submission pa S1905_02"};
    
   public static final String notifPAFOPPS1905_03_NotifName []=  {"Evaluators Needed - A Notif PA Review CRQM S1905_03",
    	                                                           "Evaluator Assigned - A Notif PA Review CRQM S1905_03",
    	                                                           "Evaluators Needed - A Notif PA Approval CRQA S1905_03",
    	                                                           "Evaluator Assigned - A Notif PA Approval CRQA S1905_03",
    	                                                           "Approval Rejected - A Notif PA Approval CRQA S1905_03",
    	                                                           "Evaluators Needed - A Notif PA Review CRQA pa S1905_03",
    	                                                           "Evaluator Assigned - A Notif PA Review CRQA pa S1905_03",
    	                                                           "Evaluators Needed - A Notif PA Approval CRQM pa S1905_03",
    	                                                           "Evaluator Assigned - A Notif PA Approval CRQM pa S1905_03",
    	                                                           "Approval Rejected - A Notif PA Approval CRQM pa S1905_03"};

   public static final String notifPAFOPPS1905_04_NotifName []=   {"Step Due - A Notif PA Submission A S1905_04",
	                                                                "Step Due - A Notif PA Review CRQM S1905_04",
	                                                                "Step Due - A Notif PA Approval CRQA S1905_04",
	                                                                "Step Due - A Notif PA Award Crit S1905_04",
	                                                                "Step Due - A Notif PA Post Award S1905_04",
	                                                                "PA Sub Step Due - A Notif PA Initial Claim pa S1905_04",
	                                                                "Step Due - A Notif PA Review CRQA pa S1905_04",
	                                                                "Step Due - A Notif PA Approval CRQM pa S1905_04",
	                                                                "Step Due - A Notif PA Submission pa S1905_04"};

   public static final String projActivityNotifName []=   {"E-mail-Immediate","E-mail-One Day Before","E-mail-Two Day Before",
	                                                       "E-mail-Completed","E-mail","E-mail-Scheduled"};
   
   public static final String subScheduleEnhancemntNoitfName []={"PA Sub Due - A Notif PA Initial Claim Gnrl FOPP",
	                                                             "PA Sub Due - IPASS Conditional Sub Due","PA Sub Due - IPASS False Conditional Value",
	                                                             "PA Sub Due - No Sub Schedule Due Date",
	                                                             "Step Entry - A Gnrl PA Notif Approval CRQA",
	                                                             "Amendment Request - A Gnrl PA Notif Approval CRQA",
	                                                             "Approval Rejected - A Gnrl PA Notif Approval CRQA",
	                                                             "PA Approval Rejected - A Gnrl PA Notif Approval CRQA pa"};
   
   public static final String notifTechEdFy14_NotifName [] = {"Application Submitted",
	                                                          "Application re-opened",
	                                                          "Application Ready",
	                                                          "Project Ready For Allocation Entry",
	                                                          "Project Amended to Initial Review Step",
	                                                          "Project Amended to Team Review Step",
	                                                          "Project Amended to Grant Agreement Created Step",
	                                                          "Project Amended to Finance Grant Agreement Review Step",
	                                                          "Project Amended to Director Grant Agreement Review Step",
	                                                          "Project Amended to CFO Approval e.Sign Step",
	              	                                          //"Project Amended Back to Grant Award e.Sign Step",
	                                                          "Grant Award",
	                                                          "Project Amended Back to CFO Grant Agreement e.Sign Step",
	                                                          "Project Amended Back to Grant Agreement Approved Step"
   
   };
   
   
   public static final String notifTechEdFy13_NotifName [] = {"Application Submitted",
                                                              "Application re-opened",
                                                              "Application Ready",
                                                              "Project Ready For Allocation Entry",
                                                              "Project Amended Back to Initial Review Step",
                                                              "Project Amended Back to Team Review Step",
                                                              "Project Amended Back to Grant Agreement Created Step",
                                                              "Project Amended Back to Finance Grant Agreement Review Step",
                                                              "Project Amended Back to Director Grant Agreement Review Step",
                                                              "Project Amended Back to CFO Approval e.Sign Step",
                                                              "Grant Award",
                                                              "Project Amended Back to CFO Grant Agreement e.Sign Step",
                                                              "Project Amended Back to Grant Agreement Approved Step",
                                                              "Project Amended Back to Approved Award Step",
                                                              "Approved Grant Agreement Available",
                                                              "Amendment Issued to Submission Schedule Step",
                                                              "Schedule Submitted",
                                                              "Amend Quarterly Report",
                                                              "Payment Request",
                                                              "Amendment Issued to Finance Report Review Step",
                                                              "Amended to TIPGAP",
                                                              "Payment processed",
                                                              "Program Officer Notification - SDE Done"
                                                              
                                                              
};
   
   
   
   
}


