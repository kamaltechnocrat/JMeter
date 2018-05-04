/*
*
*/
package test_Suite.constants.users;

public interface IUsersConst {
	
	/***************************************
	 * Group Detail HTML Tags Fields Id
	 ***************************************/
	public static final String grp_Group_Name_Id = "groupTabbedForm:manageGroupTabbedPane:access_name";
	
	public static final String grpType_drpDown_id = "groupTabbedForm:manageGroupTabbedPane:accessType_input";
	
//	public static final String grpAssocApp_CheckboxId = "manageGroupView:groupTabbedForm:editGroupView:editGroupTab:chebox_for_associate_applicants";
	public static final String grpAssocApp_CheckboxId = "groupTabbedForm:manageGroupTabbedPane:chebox_for_associate_applicants_input";
	
//	public static final String grp_Primary_Org_Id = "manageGroupView:groupTabbedForm:editGroupView:editGroupTab:primaryOrganization";
	public static final String grp_Primary_Org_Id = "groupTabbedForm:manageGroupTabbedPane:primaryOrganization_input";

//	public static final String grp_Avail_Users_Groups_Id = "manageGroupView:groupTabbedForm:editGroupView:editGroupTab:availableAppAccess";
	public static final String grp_Avail_Users_Groups_Id = "groupTabbedForm:manageGroupTabbedPane:availableAppAccess";
	
//	public static final String grp_Selected_Users_Groups_Id = "manageGroupView:groupTabbedForm:editGroupView:editGroupTab:selectedAppAccess";
	public static final String grp_Selected_Users_Groups_Id = "groupTabbedForm:manageGroupTabbedPane:selectedAppAccess";
	
//	public static final String grp_Avail_Name_Search_Id = "manageGroupView:groupTabbedForm:editGroupView:editGroupTab:accessSearch";
	public static final String grp_Avail_Name_Search_Id = "groupTabbedForm:manageGroupTabbedPane:accessSearch";

	//Group Labels (instead of Ids)
	public static final String grp_GroupName_lbl = "Group Name";
	public static final String grp_GroupType_lbl = "Group Type";
	public static final String grp_PrimaryOrg_lbl = "Primary Organization";
	
	//Group ddValues
	public static final String grp_FOType_dd = "Front Office";
	public static final String grp_POType_dd = "Program Office";
	public static final String grp_ERType_dd = "External Reviewer";

	
	//----------------End Of Fields Id--------------------------
	
	public static final String powerUser[][] = {
		{"shak", "Mustafa", "Shakshouki", "mshakshouki@g3-qa-autobuild.csdc-lan.csdcsystems.com"},  {"Staff"}};

	public static final String users[][][] = {
		{{"sant", "Test", "Sanity", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"Sanity"}},
		{{"usr01", "Usr01", "Usr01", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G01 Program Planning"}},
		{{"usr02", "Usr02", "Usr02", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G02 Program Form"}},
		{{"usr03", "Usr03", "Usr03", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G03 Program Publication"}},
		{{"usr04", "Usr04", "Usr04", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G04 Form Management"}},
		{{"usr05", "Usr05", "Usr05", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G05 Document Management"}},
		{{"usr06", "Usr06", "Usr06", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G06 Intake"}},
		{{"usr07", "Usr07", "Usr07", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G07 Project Management"}},
		{{"usr08", "Usr08", "Usr08", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G08 Project Clerks Management"}},
		{{"usr09", "Usr09", "Usr09", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G09 Project Activities"}},
		{{"usr10", "Usr10", "Usr10", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G10 Project Submissions"}},
		{{"usr11", "Usr11", "Usr11", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G11 Applicant Management"}},
		{{"usr12", "Usr12", "Usr12", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G12 Submit Form"}},
		{{"usr13", "Usr13", "Usr13", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G13 Request Amendment"}},
		{{"usr14", "Usr14", "Usr14", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G14 Amend Submission"}},
		{{"usr15", "Usr15", "Usr15", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G15 Assign Evaluators"}},
		{{"usr16", "Usr16", "Usr16", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G16 Evaluate Submission"}},
		{{"usr17", "Usr17", "Usr17", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G17 Launch Reports Officers"}},
		{{"usr18", "Usr18", "Usr18", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G18 Lookup Administration"}},
		{{"usr19", "Usr19", "Usr19", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G19 User Administration"}},
		{{"usr20", "Usr20", "Usr20", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G20 Group Administration"}},
		{{"usr21", "Usr21", "Usr21", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G21 Access Administration"}},
		{{"usr22", "Usr22", "Usr22", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G22 Report Administrator"}},
		{{"usr23", "Usr23", "Usr23", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G23 Grantium Zip Loader"}},
		{{"usr24", "Usr24", "Usr24", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G24 Report Access Officers"}},		
		{{"usr25", "Usr25", "Usr25", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G25 System Administration"}},
		{{"usr26", "Usr26", "Usr26", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G26 Integration Manager"}},
		{{"usr27", "Usr27", "Usr27", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G27 Report Parameters Officers"}}};
	
	
	public static final String externalUsers[][][] = {
		{{"NReviewer","FNReviewer","LNReviewer","@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G01 North Reviewer"}},
		{{"EReviewer","FEReviewer","LEReviewer","@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G02 East Reviewer"}},
		{{"WReviewer","FWNReviewer","LWReviewer","@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G03 West Reviewer"}},
		{{"SReviewer","FSNReviewer","LSReviewer","@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G04 South Reviewer"}}	
		
	};	
	
	public static final String externalGroups[][][] = {
		{{"G01 North Reviewer"}, {"NReviewer1","NReviewer2","NReviewer3","NReviewer4","NReviewer5"},externalUsers[0][0]},
		{{"G02 East Reviewer"}, {"EReviewer1","EReviewer2","EReviewer3","EReviewer4","EReviewer5"}, externalUsers[1][0]},
		{{"G03 West Reviewer"}, {"WReviewer1","WReviewer2","WReviewer3","WReviewer4","WReviewer5"}, externalUsers[2][0]},
		{{"G04 South Reviewer"}, {"SReviewer1","SReviewer2","SReviewer3","SReviewer4","SReviewer5"}, externalUsers[3][0]},
		
	};



	public static final String groups[][][] = {
		{{"Sanity"}, {"Sanity"},users[0][0]},
		{{"G01 Program Planning"}, {"Usr01"}, users[1][0]},	
		{{"G02 Program Form"}, {"Usr02"}, users[2][0]},
		{{"G03 Group Test"}, {"Usr03"}, users[3][0]},
		{{"G04 Form Management"}, {"Usr04"}, users[4][0]},
		{{"G05 Document Management"}, {"Usr05"}, users[5][0]},
		{{"G06 Intake"}, {"Usr06"}, users[6][0]},
		{{"G07 Project Management"}, {"Usr07"}, users[7][0]},
		{{"G08 Project Clerks Management"}, {"Usr08"}, users[8][0]},
		{{"G09 Project Activities"}, {"Usr09"}, users[9][0]},
		{{"G10 Project Submissions"}, {"Usr10"}, users[10][0]},
		{{"G11 Applicant Management"}, {"Usr11"}, users[11][0]},
		{{"G12 Submit Form"}, {"Usr12"}, users[12][0]},
		{{"G13 Request Amendment"}, {"Usr13"}, users[13][0]},
		{{"G14 Amend Submission"}, {"Usr14"}, users[14][0]},
		{{"G15 Assign Evaluators"}, {"Usr15"}, users[15][0]},
		{{"G16 Evaluate Submission"}, {"Usr16"}, users[16][0]},
		{{"G17 Launch Reports"}, {"Usr17"}, users[17][0]},
		{{"G18 Lookup Administration"}, {"Usr18"}, users[18][0]},
		{{"G19 User Administration"}, {"Usr19"}, users[19][0]},
		{{"G20 Group Administration"}, {"Usr20"}, users[20][0]},
		{{"G21 Access Administration"}, {"Usr21"}, users[21][0]},
		{{"G22 Report Administrator"}, {"Usr22"}, users[22][0]},
		{{"G23 Grantium Zip Loader"}, {"Usr23"}, users[23][0]},
		{{"G24 Assign Report Access"}, {"Usr24"}, users[24][0]},
		{{"G25 System Administration"}, {"Usr25"}, users[25][0]},
		{{"G26 Integration Manager"}, {"Usr26"}, users[26][0]},
		{{"G27 Add Report Parameters"}, {"Usr27"}, users[27][0]}};

	public static final String readOnlyGroups[][][] = {
		{{"R01 Program Planning", "R_Usr01"}},	
		{{"R02 Program Form", "R_Usr02"}},
		{{"R03 Group Test", "R_Usr03"}},
		{{"R04 Form Management", "R_Usr04"}},
		{{"R05 Document Management", "R_Usr05"}},
		{{"R06 Intake", "R_Usr06"}},
		{{"R07 Project Management", "R_Usr07"}},
		{{"R08 Project Clerks Management", "R_Usr08"}},
		{{"R09 Project Activities", "R_Usr09"}},
		{{"R10 Project Submissions", "R_Usr10"}},
		{{"R11 Applicant Management", "R_Usr11"}},
		{{"R12 Submit Form", "R_Usr12"}},
		{{"R13 Amend Submission", "R_Usr13"}},
		{{"R14 Assign Evaluators", "R_Usr14"}},
		{{"R15 Evaluate Submission", "R_Usr15"}},
		{{"R16 Lookup Administration", "R_Usr16"}},
		{{"R17 User Administration", "R_Usr17"}},
		{{"R18 Group Administration", "R_Usr18"}},
		{{"R19 Access Administration", "R_Usr19"}},
		{{"R20 System Administration", "R_Usr20"}},
		{{"R21 Integration Manager", "R_Usr21"}}};
	
	public static final String writeGroups[][][] = {
		{{"W01 Program Planning", "W_Usr01"}},	
		{{"W02 Program Form", "W_Usr02"}},
		{{"W03 Group Test", "W_Usr03"}},
		{{"W04 Form Management", "W_Usr04"}},
		{{"W05 Document Management", "W_Usr05"}},
		{{"W06 Intake", "W_Usr06"}},
		{{"W07 Project Management", "W_Usr07"}},
		{{"W08 Project Clerks Management", "W_Usr08"}},
		{{"W09 Project Activities", "W_Usr09"}},
		{{"W10 Project Submissions", "W_Usr10"}},
		{{"W11 Applicant Management", "W_Usr11"}},
		{{"W12 Submit Form", "W_Usr12"}},
		{{"W13 Amend Submission", "W_Usr13"}},
		{{"W14 Assign Evaluators", "W_Usr14"}},
		{{"W15 Evaluate Submission", "W_Usr15"}},
		{{"W16 Lookup Administration", "W_Usr16"}},
		{{"W17 User Administration", "W_Usr17"}},
		{{"W18 Group Administration", "W_Usr18"}},
		{{"W19 Access Administration", "W_Usr19"}},
		{{"W20 System Administration", "W_Usr20"}},
		{{"W21 Integration Manager", "W_Usr21"}}};            

	public static final String writeUsers[][][] = {
		{{"wusr01", "W_Usr01", "W_Usr01", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W01 Program Planning"}},
		{{"wusr02", "W_Usr02", "W_Usr02", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W02 Program Form"}},
		{{"wusr03", "W_Usr03", "W_Usr03", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W03 Program Publication"}},
		{{"wusr04", "W_Usr04", "W_Usr04", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W04 Form Management"}},
		{{"wusr05", "W_Usr05", "W_Usr05", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W05 Document Management"}},
		{{"wusr06", "W_Usr06", "W_Usr06", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W06 Intake"}},
		{{"wusr07", "W_Usr07", "W_Usr07", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W07 Project Management"}},
		{{"wusr08", "W_Usr08", "W_Usr08", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W08 Project Clerks Management"}},
		{{"wusr09", "W_Usr09", "W_Usr09", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W09 Project Activities"}},
		{{"wusr10", "W_Usr10", "W_Usr10", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W10 Project Submissions"}},
		{{"wusr11", "W_Usr11", "W_Usr11", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W11 Applicant Management"}},
		{{"wusr12", "W_Usr12", "W_Usr12", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W12 Submit Form"}},
		{{"wusr13", "W_Usr13", "W_Usr13", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W13 Amend Submission"}},
		{{"wusr14", "W_Usr14", "W_Usr14", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W14 Assign Evaluators"}},
		{{"wusr15", "W_Usr15", "W_Usr15", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W15 Evaluate Submission"}},
		{{"wusr16", "W_Usr16", "W_Usr16", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W16 Lookup Administration"}},
		{{"wusr17", "W_Usr17", "W_Usr17", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W17 User Administration"}},
		{{"wusr18", "W_Usr18", "W_Usr18", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W18 Group Administration"}},
		{{"wusr19", "W_Usr19", "W_Usr19", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W19 Access Administration"}},
		{{"wusr20", "W_Usr20", "W_Usr20", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W20 System Administration"}},
		{{"wusr21", "W_Usr21", "W_Usr21", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"W21 Integration Manager"}}};

	public static final String readOnlyUsers[][][] = {
		{{"rusr01", "R_Usr01", "R_Usr01", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R01 Program Planning"}},
		{{"rusr02", "R_Usr02", "R_Usr02", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R02 Program Form"}},
		{{"rusr03", "R_Usr03", "R_Usr03", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R03 Program Publication"}},
		{{"rusr04", "R_Usr04", "R_Usr04", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R04 Form Management"}},
		{{"rusr05", "R_Usr05", "R_Usr05", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R05 Document Management"}},
		{{"rusr06", "R_Usr06", "R_Usr06", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R06 Intake"}},
		{{"rusr07", "R_Usr07", "R_Usr07", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R07 Project Management"}},
		{{"rusr08", "R_Usr08", "R_Usr08", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R08 Project Clerks Management"}},
		{{"rusr09", "R_Usr09", "R_Usr09", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R09 Project Activities"}},
		{{"rusr10", "R_Usr10", "R_Usr10", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R10 Project Submissions"}},
		{{"rusr11", "R_Usr11", "R_Usr11", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R11 Applicant Management"}},
		{{"rusr12", "R_Usr12", "R_Usr12", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R12 Submit Form"}},
		{{"rusr13", "R_Usr13", "R_Usr13", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R13 Amend Submission"}},
		{{"rusr14", "R_Usr14", "R_Usr14", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R14 Assign Evaluators"}},
		{{"rusr15", "R_Usr15", "R_Usr15", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R15 Evaluate Submission"}},
		{{"rusr16", "R_Usr16", "R_Usr16", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R16 Lookup Administration"}},
		{{"rusr17", "R_Usr17", "R_Usr17", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R17 User Administration"}},
		{{"rusr18", "R_Usr18", "R_Usr18", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R18 Group Administration"}},
		{{"rusr19", "R_Usr19", "R_Usr19", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R19 Access Administration"}},
		{{"rusr20", "R_Usr20", "R_Usr20", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R20 System Administration"}},
		{{"rusr21", "R_Usr21", "R_Usr21", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"R21 Integration Manager"}}};
	
	
	public static final String poLoginErrorMessage = "Invalid user name or password. Please try again";
	
	public static final String poLockedOutUserMessage = "Your user ID has been locked out due to too many unsuccessful login attempts. Please see your system administrator.";
	
	public static final String poLockedOutUserHeader = "Locked Out";


}
