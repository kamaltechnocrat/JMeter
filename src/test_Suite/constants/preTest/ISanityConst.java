package test_Suite.constants.preTest;

public interface ISanityConst {
	//#*********************************************
	//#         New Sanity PO, FO users 
	//#*********************************************

	//#Program Office User
	public static final String sanityPOUser[] = {"sant1", "Test1", "Sanity", "@mail.com"};
	
	public static final String sanityFOuser[][] = {{"SanityApp","Sanity", "LApplicant","@mail.com", "Sanity-1234"},
													{"SanityRegi","Sanity","LRegistrant","@mail.com","fsant"}};

	public static final String santPO_UsrName = "sant1";
	public static final String santPO_FName	= "Test1";
	public static final String santPO_LName	= "Sanity";	
	public static final String santPO_Email	= "sant1@mail.com";	
	public static final String santPO_Organization	= "Sanity Inc.";
	public static final String santPO_Position		= "Sanity Test1";
	public static final String santProgPOfficer = "Sanity1, Test1 (sant1@mail.com)";	

	//#Front Office User, Added from Program Office
	public static final String sanityFOUser[] = {"sant2", "Test2", "Sanity", "@mail.com"};

	public static final String santFO_UsrName = "sant2";	
	public static final String santFO_FName = "Test2";
	public static final String santFO_LName = "Sanity";	
	public static final String santFO_Email = "sant2@mail.com";
	public static final String santFO_Organization = "Sanity Inc.";
	public static final String santFO_Position = "Sanity Test2";

	public static final String santGroupName = "Sanity";

	public static final String santFront_UsrName = "fsant";
	public static final String santFront_FName = "FSanity";
	public static final String santFront_LName = "LRegistrant";
	public static final String santFront_Email = "fsant@mail.com";
	public static final String santFront_OrgShortName = "Sanity";
	public static final String santFront_OrgName = "Sanity Applicant";
	public static final String santFront_OrgNumber = "Sanity-123";
	public static final String santFront_OrgDUNS = "Sanity-DUNS";
	public static final String santFront_OrgEIN = "SF-1234567";

	//#**********************************************


	//*********************************************
	//#         Progam, Projects Org etc
	//#*********************************************
	
	public static final String sanity_Prefix = "-Sanity-";
	public static final String sanity_ProgForm_Name = "/Basic Program Admin/";
	public static final String sanity_PublicationForm_Name = "/Publication Form/";
	

	//#*********************************************


	/******************************************
    //#      Form Sanity and Fields
    //#******************************************/
	//#Form Properties for Sanity
	public static final String formIdentSant = "-Sub Sanity Case";
	public static final String formTypeSant = "Application";
	public static final String formDescSant = "Bring Forward";
	public static final String formletDefaultSant = "";
	public static final String formTitleSant = "-Submission";

	//#eFormField Properties
	public static final String eFieldIdentSant = "-EField Sanity";
	public static final String questionLabelSant = "Enter Any Thing Numeric";
	public static final String eFieldTypeGridSant = "Numeric Data-Grid Field";
	public static final String xAxisLookupSant[] = {"Document Types", "Languages","Activity Types"};
	public static final String yAxisLookupSant[] = {"Activity Types" , "Salutations", "Canadian Provinces"};

	//#Simple Bring Forward Function Properties
	public static final String functionTypeSant = "Bring Forward";
	//SourceFieldSant    = EFieldIdent    
	//*******************************************


	/*******************************************
    //#   3 Formlets Properties, Used for SANITY
    //#*******************************************/

	public static final String formletIdentSant[] = {"-Sanity Menu", "-Sanity Formlet","-Sanity SS"};
	public static final String formletTypeSant[] = {"Menu Item Only", "e.Form Question Holder", "Submission Summary"};
	public static final int menuIndentSant[] = {0, 1, 0};
	public static final boolean ss_DisplaySant[] = {true, true, true};
	public static final String titleTextSant[] = {"-Sanity Menu", "-Sanity Formlet", "-Sanity Submission Summary"};
	public static final String menuTextSant[] = {"Menu", "Formlet 1", "Submission Summary"};

	//#EForm Field Properties
	public static final boolean mandatorySant = true;
	public static final String forceValueSant = "No"; 
	//#********************************************

	/****************************************************
    //#             Program Steps
    //#***************************************************/

	public static final String sanity_SubmissionA[][] = {{"Submission-A", "", "Applicant Submission", "true", "No"}, {"Sanity Sub A"}};
	public static final String sanity_SubmissionB[][] = {{"Submission-B", "", "Applicant Submission", "false", "No"}, {"Sanity Sub B"}};
	public static final String sanity_Review[][] = {{"Review", "", "Review", "true", "Optional (No)"}, {"/Review Form/", "Quorum", "1", "false"}};
	public static final String sanity_Approv[][] = {{"Approval", "", "Approval", "true", "Optional (Yes)"}, {"Sanity Approval BF", "Quorum", "1", "true"}};
	public static final String sanity_PA_Award[][] = {{"Award", "", "Award", "true", "Optional (Yes)"}, {"/Standard Agreement/"}};
	public static final String sanity_Award[][] = {{"Award", "", "Award", "true", "Optional (Yes)"}, {"/Basic Agreement/"}};

	public static final String sanity_InitialClaimA[][] = {{"Initial-Claim-A", "", "Initial Post Award Submission", "true", "Optional (Yes)"}, {"/Post Award Submission/", "Claim ", "true"}};
	public static final String sanity_InitialClaimB[][] = {{"Initial-Claim-B", "", "Initial Post Award Submission", "true", "Optional (No)"}, {"/Post Award Submission/", "Claim ", "true"}};

	public static final String sanity_PostAward[][] = {{"Post-Award", "", "Post-Award", "true", "Optional (No)"}, {sanity_PA_Award[0][0],sanity_InitialClaimA[1][0]}};        
	public static final String sanity_PA_Review[][] = {{"PA-Review", "", "Review", "true", "Optional (No)"}, {"/Review Form/", "Quorum", "1", "false"}};
	public static final String sanity_PA_Approv[][] = {{"PA-Approval", "", "Approval", "true", "Optional (Yes)"}, {"/Approval Form/", "Majority", "", "true"}};

	//#******************************************************


	/****************************************************
    //#             Lookup List
    //#****************************************************/

	public static final String lookups = "-Sanity-Lookup-List";
	public static final String values[] = {"-Sanity-Second-Value", "-Sanity-First-Value", "-Sanity-Third-Value"};

	//#*****************************************************



	/****************************************************
    //#             Documents Details
    //#****************************************************/
	
	public static final String docParams[] = {"Guidelines", "Guidelines", "Sanity Test for Importing Guidelines Document", "Guidelines.doc"};

	public static final String docNameSant[] = {"Frequently Asked Questions", "Guidelines",
												"Costing Memorandums", "Instructions"};
	
	public static final String docTypeSant[] = {"FAQ", "Guidelines", "Costing Memorandums",
												"Instructions"};
	
	public static final String docDescSant[] = {"Sanity Test for Importing Frequently Asked Questions Document",
												"Sanity Test for Importing Guidelines Document",
												"Sanity Test for Importing Costing Memorandums Document",
												"Sanity Test for Importing Instructions Document"};

	public static final String docPathSant[] = {"FAQ.doc",
												"Guidelines.doc",
												"CostingMemorandums.doc",
												"Instructions.doc"};

	//#**************************************************************



}
