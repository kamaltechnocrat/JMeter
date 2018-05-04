/**
 * 
 */
package test_Suite.constants.reporting_Functions;

/**
 * @author mshakshouki
 *
 */
public interface IRptFuncConst {
	
	public static enum EReportingFunctions {
		
		sqlApplicant,
		sqlProgram,
		sqlOrganization,
		sqlProject,
		oraApplicant,
		oraProgram,
		oraOrganization,
		oraProject,
	}

	public static enum EfieldDataTypes {
		formlet, stringTP, dateTP, booleanTP, numericTP,gridView, m2mTP, longTP
	}

	public static enum EeFormsIdentifier {
		org, admin, profile, submission, review, approval, award, awardGrid, claim, all
	}
	
	public static final String[] reportFunc_eFormsIdent = {
		"Test-Project-Organization-eForm-Reporting-Function",
		"Test-Project-Admin-eForm-Reporting-Function",
		"Test-Project-Applicant-Reporting-Function",
		"Test-Project-eForm-Submission-Reporting-Function",
		"Test-Project-eForm-Review-Reporting-Function",
		"Test-Project-eForm-ProjectApproval-Reporting-Function",
		"Test-Project-eForm-Award-Reporting-Function",
		"Test-Project-eForm-Grid-Award-Reporting-Function",
		"Test-Project-eForm-InitialClaim-Reporting-Function",
		"ALL"};
	
	public static final String[] reportingFunc_NonProject_eFormsIdent = {
		"Test-Organization-eForm-Reporting-Function",
		"Test-Admin-eForm-Reporting-Function",
		"Test-Applicant-Reporting-Function"};
	
	public static final String[][] reportFunc_eListFormlet = {{"A-First Name", "A-Last Name", "10", "A-Street Name", "(123)123-1234", "A1A 1A1", "Ontario", "Canada","A-Last Name, A-First Name Canada, A1A 1A1, (123) 123-1234"},
		//{"B-First Name", "B-Last Name", "11", "B-Street Name", "(123)123-1234", "B2B 2B2", "Alberta", "United States","B-Last Name, B-First Name United States, B2B 2B2, (123) 123-1234"},
		//{"C-First Name", "C-Last Name", "12", "C-Street Name", "(123)123-1234", "C3C 3C3", "Nova Scotia", "American Samoa","C-Last Name, C-First Name American Samoa, C3C 3C3, (123) 123-1234"},
		{"D-First Name", "D-Last Name", "13", "D-Street Name", "(123)123-1234", "D4D 4D4", "Quebec", "Canada","D-Last Name, D-First Name Canada, D4D 4D4, (123) 123-1234"}};
	
	public static final String[][] reportFunc_NoPropertiesFormlet = {{"true","Date","12-1234567","noMail@grantium.com","http://www.grantium.com"}};
	
	public static final String[][] reportFunc_LookupDropdownFormlet = {{"true","true"}};
	
	public static final String[][] reportFunc_DropdownFormlet = {{"Approved","E-mail","Green",reportFunc_eListFormlet[0][8], reportFunc_eListFormlet[1][8]}};
	
	public static final String[][] reportFunc_TypesPropertiesFormlet = {{"001613 2307890","K1P-5J9"}};
	
	public static final String[][] reportFunc_MinAndMaxFormlet = {{"A","4000","abcdefghi","B","2000","10"}};
	
	public static final String[][] reportFunc_eListDropdownFormlet = {{"Mail","Light Grey",reportFunc_eListFormlet[0][8]},
		{"Site Visit","Medium Grey",reportFunc_eListFormlet[1][8]}};
	
	public static final String[][] reportFunc_eListDropdownFormlet_USLocale = {{"Mail","Light Grey",reportFunc_eListFormlet[0][8]},
		{"Site Visit","Medium Grey",reportFunc_eListFormlet[1][8]}};
	
	public static final String[][] reportFunc_eListDataGridFormlet = {{"E-mail","Red",reportFunc_eListFormlet[0][8], "99","First String"},
		{"Site Visit","Red",reportFunc_eListFormlet[0][8], "100","Second String"}};
	
	
	
	public static final String reportFunc_SQL_DriverObject = "net.sourceforge.jtds.jdbc.Driver";
	
	public static final String reportFunc_Ora_DriverObject = "oracle.jdbc.driver.OracleDriver";
	
	public static final String reportFunc_SQL_DBConnection = "jdbc:jtds:sqlserver://";
	
	public static final String reportFunc_Ora_DBConnection = "jdbc:oracle:thin:@";
	
	public static final String reportFunc_Ora_AnyData_AccessDate = "AccessDate";
	
	public static final String reportFunc_Ora_AnyData_AccessNVarchar2 = "AccessNVarchar2";
	
	public static final String reportFunc_Ora_AnyData_AccessVarchar2= "AccessVarchar2";
	
	public static final String reportFunc_Ora_AnyData_AccessNumber= "AccessNumber";
	
	public static final String reportFunc_Ora_AnyData_AccessNClob = "AccessNCLOB";
	
	public static final String reportFunc_OrderBy_IdAndField = "ORDER BY the_ID DESC, the_Field ASC";
	
	public static final String reportFunc_OrderBy_Id = "ORDER BY the_ID DESC";
	
	public static final String ReportFunc_FirstSubmittedProject = "1";
	
	public static final String ReportFunc_LatestSubmittedProject = "2";
	
	public static final String ReportFunc_AllSubmittedProjects = "3";
	
	public static final String ReportFunc_LatestNonSubmittedProject = "4";
	
	public static final String ReportFunc_LatestSubmittedOrNotProject = "5";
	
	
	//****** Funding Opp. **********************//
	public static final String reportFunc_ProgPrefix = "-ReportFunc-";
	
	public static final String reportFunc_ProjPrefix = "p.";
	public static final String reportFunc_OrgName    = "Org";
	public static final String reportFunc_OrgNumber  = "7890";
	public static final String reportFunc_ProjName   = "Proj";
	public static final String reportFunc_PAPName   = "PAP";
	public static final String reportFunc_FndOppName = "FndOpp";

	public static final String reportFunc_FO_FndOppName   = "FO-FndOpp";
	public static final String reportFunc_FO_ProjPrefix = "ReportingFunc.";
	public static final String reportFunc_FO_OrgName    = "FO-Org";
	public static final String reportFunc_FO_OrgNumber  = "FO-7890-";
	public static final String reportFunc_FO_ProjName   = "FO-Proj";

	//##***** Post-Award ****************
	public static final String reportFunc_PA_ProgPrefix = "-Project-PA-";	
	public static final String reportFunc_PA_ProjPrefix  = "Pa.";
	
	public static final String reportFunc_PA_FO_ProjPrefix = "Fpa.";
	
	
	//************* Steps *************************//
	
	public static final String reportFunc_SubmissionA[][] = {{"Submission-A", "Test Project eForm Submission A Reporting Function", "Applicant Submission", "true", "No"}, {"Test-Project-eForm-Submission-A-Reporting-Function", "Test Project eForm Submission A Reporting Function"}};
	
	public static final String reportFunc_ReviewA[][] = {{"Review-A", "Test Project eForm ReviewReporting Function", "Review", "true", "No"}, {"Test-Project-eForm-Review-Reporting-Function", "Quorum", "1", "false"}};
	public static final String reportFunc_ApprovA[][] = {{"Approval-A", "Test Project eForm ProjectApproval Reporting Function", "Approval", "true", "No"}, {"Test-Project-eForm-ProjectApproval-Reporting-Function", "Quorum", "1", "false"}};
	
	public static final String reportFunc_AwardCrit[][] = {{"Award-A", "Test Project eForm Award Reporting Function", "Award", "true", "Yes"}, {"Test-Project-eForm-Award-Reporting-Function", "Test Project eForm Award Reporting Function"}};
	
	public static final String reportFunc_AwardGridCrit[][] = {{"Award-A", "Test Project eForm Grid Award Reporting Function", "Award", "true", "Yes"}, {"Test-Project-eForm-Grid-Award-Reporting-Function", "Test Project eForm Grid Award Reporting Function"}};
	
	public static final String reportFunc_initialClaim[][] = {{"Initial-Claim-A", "/Initial Post Award/", "Test Project eForm InitialClaim Reporting Function", "true", "Optional (Yes)"}, {"Test-Project-eForm-InitialClaim-Reporting-Function", "Claim ", "true"}};

	//**************Testing************************//
	public static String updateTestQuery = "UPDATE GRANTIUM_CONFIG SET VALUE = 'mai000l@mail.com' WHERE CONSTANT = 'AUTOMATED_EMAIL'";

	public static String selectTestQuery = "SELECT VALUE FROM GRANTIUM_CONFIG WHERE CONSTANT = 'AUTOMATED_EMAIL'";
	
	public static String commitQuery = "commit;"; 
}

