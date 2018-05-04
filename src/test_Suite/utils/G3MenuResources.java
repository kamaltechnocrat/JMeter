/**
 * 
 */
package test_Suite.utils;

/**
 * @author mshakshouki
 *
 */
public enum G3MenuResources {
	
	GPA_ProgramsList("GPA_ProgramsList"),
	GPA_InBasketList("GPA_InBasketList"),
	GPA_AssignEvalsList("GPA_AssignEvalsList"),
	GPA_EvaluateFundingOpportunitiesList("GPA_EvaluateFundingOpportunitiesList"),
	GPA_MyProjctSubmissionsList("GPA_MyProjctSubmissionsList"),
	GPA_MyAssignedSubmissionList("GPA_MyAssignedSubmissionList"),
	GPA_FundingOppsList("GPA_FundingOppsList"),
	GPA_EFormsList("GPA_EFormsList"),
	GPA_DocumentsList("GPA_DocumentsList"),
	GM_InBasketList("GM_InBasketList"),
	GM_ProjectsList("GM_ProjectsList"),
	GM_MyProjectSubmissionsList("GM_MyProjectSubmissionsList"),
	GM_MyAssignedSubmissionsList("GM_MyAssignedSubmissionsList"),
	GM_ApplicantsList("GM_ApplicantsList"),
	GM_AmendmentsList("GM_AmendmentsList"),
	GM_AssignEvalsList("GM_AssignEvalsList"),
	GM_EvaluateSubmissionsList("GM_EvaluateSubmissionsList"),
	GM_AwardsList("GM_AwardsList"),
	GM_MyReports("GM_MyReports"),
	Admin_LookupsList("Admin_LookupsList"),
	Admin_ApplicantTypesList("Admin_ApplicantTypesList"),
	Admin_UsersList("Admin_UsersList"),
	Admin_GroupsList("Admin_GroupsList"),
	Admin_LockedOutUsersList("Admin_LockedOutUsersList"),
	Admin_OrganizationsList("Admin_OrganizationsList"),
	Admin_ReportsList("Admin_ReportsList"),
	Admin_DataMartList("Admin_DataMartList"),
	Admin_ReportingFieldsList("Admin_ReportingFieldsList"),
	Admin_SystemSettingsList("Admin_SystemSettingsList"),
	Admin_ApplicationSettingsList("Admin_ApplicationSettingsList"),
	Admin_FrontOfficeList("Admin_FrontOfficeList"),
	Admin_DataLoaderList("Admin_DataLoaderList"),
	GG_FundingOppList("GG_FundingOppList"),
	GG_EFormsList("GG_EFormsList"),
	GG_TransactionsResultsList("GG_TransactionsResultsList"),
	GG_CertificatesList("GG_CertificatesList"),
	ChangePassword("ChangePassword"),
	MyUserProfile("MyUserProfile"),
	ConnectToGrantiumCommunities("ConnectToGrantiumCommunities"),
	FO_Profile_MyAccount("FO_Profile_MyAccount"),
	FO_Profile_MyProfile("FO_Profile_MyProfile"),
	FO_Workspace_ApplicantsList("FO_Workspace_ApplicantsList"),
	FO_Workspace_FundingOppRegistrationsList("FO_Workspace_FundingOppRegistrationsList"),
	FO_Workspace_ProjectsList("FO_Workspace_ProjectsList"),
	FO_Workspace_SubmissionsList("FO_Workspace_SubmissionsList");
	
	private String key;

	G3MenuResources(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
