/*
 * VarPreTest.java
 *
 * Created on January 26, 2007, 8:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test_Suite.constants.preTest;

/**
 *
 * @author mshakshouki
 */
public interface IPreTestConst {
    /*********************************************
    #   PO, FO users to used through out testing
    #*********************************************/
    
    public static final String PO_UsrName = "shak";
    public static final String PO_FName = "Mustafa";
    public static final String PO_LName = "Shakshouki";	
    public static final String PO_Email = "mshakshouki@g3-qa-autobuild.csdc-lan.csdcsystems.com";	
    public static final String PO_Organization = "Grantium Inc.";
    public static final String PO_Position = "QA";        
        
    public static final String fo_AmenderNameInList = "Applicant:Ouia 1";
    public static final String FO_FName = "Front";
    public static final String FO_LName = "LRegistrant";
    public static final String FO_OrgShortName = "Ouia ";
    public static final String FO_OrgName = "Applicant ";
    public static final String FO_OrgNumber = "Applicant-123-";
    public static final String FO_OrgDUNS = "FO-DUNS";
    public static final String FO_OrgEIN = "FO-1234567";	
    public static final String FO_Email = "Registrant";
    public static final String email_Domain = "@g3-qa-autobuild.csdc-lan.csdcsystems.com";
    public static final String FO_UsrName = "front";
    public static final String FO_Province = "Ontario";
       
    public static final String AdminUsrName = "admin";
    public static final String AdminPwd = "password";
    public static final String adminGroup = "Super";
    public static final String adminEmailAddress = "qa_Admin@g3-qa-autobuild.csdc-lan.csdcsystems.com";
    public static final String Salutation = "/Mr/";
    public static final String Language = "English (Canada)";
    public static final String English = "English" ;                  
    public static final String Pwd = "a11";
    public static final String Question = "a11";
    public static final String Answer = "a11";
    public static final String Address = "279 Laurier Ave. Suite 200";
    public static final String City = "Ottawa";
    public static final String Province = "ON.";
    public static final String Country = "Canada";
    public static final String PCode = "K1P-5J9";
    public static final String Phone = "613-230-7890";
    public static final String AppGroupName = "Staff";
    public static final String AppAccessName = "U: Shakshouki, Mustafa (mshakshouki@g3-qa-autobuild.csdc-lan.csdcsystems.com)";
    public static final String ProgPOfficer = "Shakshouki, Mustafa (mshakshouki@g3-qa-autobuild.csdc-lan.csdcsystems.com)";
    public static final String adminProgPOOfficer = "Admin, Grantium (admin@g3-qa-autobuild.csdc-lan.csdcsystems.com)";
        

    
    public static final String FrontUsers[][]  = {{"Applicant", "Front-", "LApplicant-", "@g3-qa-autobuild.csdc-lan.csdcsystems.com", "Applicant-123-"},
                                                  {"qa_Registrant", "Front-", "LRegistrant-", "@g3-qa-autobuild.csdc-lan.csdcsystems.com", "front"}};
                        
    public static final String MultiUsers[][][]  = {{{"Approver", "FApprover", "LApprover", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G03 Approvers"}},
                                                  {{"Reviewer", "FReviewer", "LReviewer", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G02 Reviewers"}},
                                                  {{"eastuser", "FEastUser", "LEastUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_East Intake"}},
                                                  {{"westuser", "FWestUser", "LWestUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_West Clerks"}},
                                                  {{"ProgramOfficer", "FProgfficer", "LProgOfficer", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G10 Program Officers"}},
                                                  {{"ProjectOfficer", "FProjOfficer", "LProjOfficer", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G06 Project Officers"}},
                                                  {{"northuser", "FNorthUser", "LNorthUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_North Financial Officers"}},
                                                  {{"ReportOfficer", "FReportOfficer", "LReportOfficer", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G11 Report Officers"}},
                                                  {{"sant", "Test", "Sanity", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Sanity"}},
                                                  {{"southuser", "FSouthUser", "LSouthUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"}, {"G3_South Financial Approvers"}},
                                                  {{"northwesternuser", "FNorthWesternUser", "LNorthWesternUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_North Western"}},
                                                  {{"northeasternuser", "FNorthEasternUser", "LNorthEasternUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_North Eastern"}},
                                                  {{"southwesternuser", "FSouthWesternUser", "LSouthWesternUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_South Western"}},
                                                  {{"southeasternuser", "FSouthEasternUser", "LSouthEasternUser", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G3_South Eastern"}},
                                                  {{"FinancialOfficer", "FFinanOfficer", "LFinanOfficer", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G19 Financial Officers"}},
                                                  {{"Registrant","Front-", "LRegistrant-", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Ouia "}},
                                                  {{"Clerk", "FClerk", "LClerk", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G20 Submissions Clerks"}},
                                                  {{"SubClerk", "FSubClerk", "LSubClerk", "@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"G20 Submissions SubClerks"}}};
                       
    public static final String SingleUsers[][][] = {{{"shak", "Mustafa", "Shakshouki", "mshakshouki@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Staff"}},
                                                  {{"ReadApprov", "FReadApprover", "LReadApprover", "ReadApprov@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Read Only Approvers"}},
                                                  {{"ReadReview", "FReadReviewer", "LReadReviewer", "ReadReview@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Read Only Reviewers"}},
                                                  {{"ImpAdmin", "FImportAdmin", "LImportAdmin", "ImpAdmin@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Import Admin"}},
                                                  {{"ImpClerk", "FImportClerk", "LImportClerk", "ImpClerk@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Import Clerks"}},
                                                  {{"ProgAdmin", "FProgAdmin", "LProgAdmin", "ProgAdmin@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Program Admin"}},
                                                  {{"admin", "Grantium", "/Admin/", "qa_Admin@g3-qa-autobuild.csdc-lan.csdcsystems.com"},{"Super"}}};
                        
  
        public static final String Groups[][][] = {{{"Staff"}, {"/Shakshouki/"}, SingleUsers[0][0]},
                                                   {{"G01 Read Only Evals"}, {"/LReadApprover/", "/LReadReviewer/"},SingleUsers[1][0]},
                                                   {{"G02 Reviewers"}, {"/LReviewer1/", "/LReviewer2/", "/LReviewer3/", "/LReviewer4/", "/LReviewer5/"}, MultiUsers[0][0]},
                                                   {{"G03 Approvers"}, {"/LApprover1/", "/LApprover2/", "/LApprover3/", "/LApprover4/", "/LApprover5/"}, MultiUsers[1][0]},
                                                   {{"G04 One Reviewer"}, {"/LReviewer1/"},MultiUsers[1][0]},
                                                   {{"G05 One Approver"}, {"/LApprover1/"},MultiUsers[0][0]},
                                                   {{"G06 Project Officers"}, {"/LProjOfficer1/", "/LProjOfficer2/", "/LProjOfficer3/","LProjOfficer4","LProjOfficer5"}, MultiUsers[5][0]},
                                                   {{"G3_East Intake"}, {"/LEastUser1/", "/LEastUser2/", "/LEastUser3/", "/LEastUser4/", "/LEastUser5/"},MultiUsers[2][0]},
                                                   {{"G3_West Clerks"}, {"/LWestUser1/","/LWestUser2/","/LWestUser3/","/LWestUser4/","/LWestUser5/"},MultiUsers[3][0]},
                                                   {{"G3_North Financial Officers"}, {"/LNorthUser1/","/LNorthUser2/","/LNorthUser3/","/LNorthUser4/","/LNorthUser5/"},MultiUsers[6][0]},
                                                   {{"G10 Program Officers"}, {"/LProgOfficer1/","/LProgOfficer2/","/LProgOfficer3/","LProgOfficer4","LProgOfficer5"}, MultiUsers[4][0]},
                                                   {{"G11 Report Officers"}, {"/LReportOfficer1/","/LReportOfficer2/","/LReportOfficer3/"},MultiUsers[7][0]},
                                                   {{"Sanity"}, {"/Sanity1/","/Sanity2/"},MultiUsers[8][0]},
                                                   {{"Super"}, {"/Admin/"}, SingleUsers[6][0]},
        										   {{"G3_South FinancialApprover"},{"/LSouthUser1/","/LSouthUser2/","/LSouthUser3/","/LSouthUser4/","/LSouthUser5/"}, MultiUsers[9][0]},
        										   {{"G3_North Western"},{"/LNorthWesternUser1/","/LNorthWesternUser2/","/LNorthWesternUser3/","/LNorthWesternUser4/","/LNorthWesternUser5/"}, MultiUsers[10][0]},
        										   {{"G3_North Eastern"},{"/LNorthEasternUser1/","/LNorthEasternUser2/","/LNorthEasternUser3/","/LNorthEasternUser4/","/LNorthEasternUser5/"}, MultiUsers[11][0]},
        										   {{"G3_South Western"},{"/LSouthWesternUser1/","/LSouthWesternUser2/","/LSouthWesternUser3/","/LSouthWesternUser4/","/LSouthWesternUser5/"}, MultiUsers[12][0]},
        										   {{"G3_South Eastern"},{"/LSouthEasternUser1/","/LSouthEasternUser2/","/LSouthEasternUser3/","/LSouthEasternUser4/","/LSouthEasternUser5/"}, MultiUsers[13][0]},
                                                   {{"G19 Financial Officers"}, {"/LFinanOfficer1/","/LFinanOfficer2/","/LFinanOfficer3/","/LFinanOfficer4/","/LFinanOfficer5/"},MultiUsers[14][0]},
                                                   {{"G20 Submissions Clerks"}, {"/LClerk1/","/LClerk2/","/LClerk3/","/LClerk4/","/LClerk5/"},MultiUsers[16][0]},
                                                   {{"G20 Submissions SubClerks"}, {"/LSubClerk1/","/LSubClerk2/","/LSubClerk3/","/LSubClerk4/","/LSubClerk5/"},MultiUsers[17][0]}};
  

      /*******************************************
      #      Form General Test and Fields
      #*******************************************/
    
        /***Form Properties for eForms
        #If you change the Form Identifier,
        #you need to change the FormLetter as well***/
        public static final String FormLetterGene      = "S";
        public static final String FormIdentGene       = "sub";
        public static final String FormTypeGene        = "Application";
        public static final String FormDescGene        = "Submission Form for General testing without any eForm Fields";
        public static final String FormletDefaultGene  = "";
        public static final String FormTitleGene       = "General Submission";
    
        public static final String EFieldIdentGene     = "General eFormField";
        public static final String QuestionLabelGene   = "Enter Any Text";
        public static final String EFieldTypeGene      = "Short Text Field";
        
      //#************************************************************
        
      /*******************************************
      #
      #   3 Formlets Properties, Used for GENERAL
      #
      #*******************************************/
 
        public static final String FormletIdentGene[] = {"-General Menu", "-General Formlet","-General SS"};
        public static final String FormletTypeGene[] = {"Menu Item Only", "e.Form Question Holder", "Submission Summary"};
        public static final int MenuIndentGene[] = {0, 1, 0};
        public static final boolean SS_DisplayGene[] = {true, true, true};
        public static final String TitleTextGene[] = {"-General Menu", "-General Formlet", "-General Submission Summary"};
        public static final String MenuTextGene[] = {"Menu", "Formlet", "Submission Summary"};
        
        //###***PDF Export
        public static final String PdfIncludeInExport[] = {"Never", "If Formlet is visible to User", "Always"};
        public static final String PdfPageBreak[] = {"None", "Before Formlet", "Full"};
        public static final String PdfPageOrientation[] = {"Portrait", "Landscape"};
  
        //###***eFormField Properties
        public static final boolean MandatoryGene = true;
        public static final String ForceValueGene = "No";
  
      //#******************************************** 
        
        /******************************************
         *  eForms to Imports
         ******************************************/
        
        public static final String formsToImport[] = {"Approval Form.xml","Applicant Workspace.xml","sub.xml","Review Form.xml","Standard Agreement.xml","Basic Agreement.xml","Post Award Submission.xml"};
        
        public static final String groupsToImport[] = {"ExportUsersGroups.xml"};
    
}
