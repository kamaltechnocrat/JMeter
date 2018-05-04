package test_Suite.constants.cases;

public interface IReportsConst {
	
	public static final String reportId = "-g3_Reports";
	
	public static final String withParamsReportsNames[] = {"Evaluators_Workload_Report.rpt","Applicants_Registered_Funding_Opportunity.rpt","ProjectStatusListing.rpt",
															"CFDA_Application_Process.rpt","ApplicationSuccessful_Letter.rpt","ApplicationUnsuccessful_Letter.rpt",															
															"Projects_Processing_Report.rpt","Registrant_Data_Report.rpt", "Workflow_Projects_Data.rpt",
															"Total_Grants_Awarded_Report.rpt"};
	
	
	public static final String noParamsReportsNames[] = {"Applicants_By_Registrant_Bar_Chart.rpt","Crystal_Reports_Listing.rpt","Funding_Opportunities_Listing_Report.rpt",
														 "Projects_By_Funding_Opportunity_Bar_Chart.rpt","Projects_By_Funding_Opportunity_Pie_Chart.rpt","Registrants_Listing_Report.rpt",};
	
	public static final String withParamsReportsTitles[] = {"Evaluator Workload Report","Applicants Registered For Funding Opportunity","Project Status Listing",
															"CFDA Application Process","Application Successful Letter","Application Unsuccessful Letter",															
															"Projects Processing Report","Registrant Data Report", "Workflow Projects Data",
															"Total Grants Awarded Report"};


	public static final String noParamsReportsTitles[] = {"Applicants By Registrant Bar Chart","Crystal Reports Listing",
															"Funding Opportunities Listing Report", "Projects By Funding Opportunity Bar Chart",
															"Projects By Funding Opportunity Pie Chart","Registrants Listing Report",};

	public static final String reportTitle = "-g3 Report";
	
	public static final String reportType = "Crystal Reports XI";
	
	public static final String reportViewer = "G3ReportViewer.jsp";
	
	public static final String reportViewerPDF = "G3ReportViewerPDF.jsp";
	
	public static final String reportParamName[] = {"fund_opp", "fund_opp", "fund_opp",
													"fund_opp", "prj_id", "prj_id",
													"fund_opp", "user_name", "prj_id",
													"fund_opp"};
	
	public static final String reportParamLabels[] = {"Funding Opportnity", "Funding Opportunity", "Funding Opportunity",
														"Funding Opportunity", "Project Number", "Project Number",
														"Funding Opportunity", "Registrant Name (First Last)", "Project Number",
														"Funding Opportunity"};
	
	public static final String withParamsReportsType[] = {"Funding Opportunity","Funding Opportunity","Funding Opportunity",
														 	"Funding Opportunity","Projects","Projects",
														 	"Funding Opportunity","Text","Projects",
														 	"Funding Opportunity"};	
	
	public static final String report_Lookup[] = {"Activity Types", "Countries", "Languages","Salutations", "Prefixes", "Suffixes",
													"Provinces", "Canadian Provinces", "US States", "Yes/No", "Document Types",
													"Document Formats", "Document Sizes", "Agreement Status"};
	
	public static final String report_LocaleStart_Id = "/locales:";
	
	public static final String report_LocaleEnd_Id   = ":reportTitle/";
	
	public static final String report_URLStart_Id = "/properties:";
	
	public static final String report_URL_Id = "/properties:0:lengthlimitedtextfield/";
	
	public static final String report_Name_Id = "/properties:1:lengthlimitedtextfield/";
	
	public static final String report_URLEnd_Id   = ":lengthlimitedtextfield/";
	
	public static final String report_Ident = "/reportIdentifier/";
	
	public static final String report_Status = "/reportStatus/";
	
	public static final String report_Type   = "/reportType/";
	
	public static final String report_PrimOrg_Id = "/primaryOrganization/";
	
	public static final String report_OrgAccess_Id = "/organizationAccess/";
	
	public static final String report_Msg    = "main:reportDetailSubview:reportDetailForm:globalMessages";
	
	public static final String report_ManageReportAccess = "Manage Report Access";
	
	public static final String report_ReportExpirationError = "A general error has occurred.";
	
	public static final String report_CrystalReportViewer   = "Crystal Reports Viewer";
	
	public static final String report_Planner = "/reportPlanner/";
	
	public static final String reportDetailsOpen = "/Open/";
	
	public static final String recursiveExpandAndCollapse = "/recursiveExpandAndCollapse/";
	
	public static final String reports_PlusGif_Last_Id = "nav-plus-line-last.gif";
	public static final String reports_PlusGif_Middle_Id = "nav-plus-line-middle.gif";
	public static final String reports_MinusGif_Last_Id = "nav-minus-line-last.gif";
	public static final String reports_MinusGif_Middle_Id = "nav-minus-line-middle.gif";
	
	public enum eReportsGridFields
	{
		deleteIcon, // = 0
		reportTitle,
		reportStatus,
		dateAdded

	}
	
	public enum eMyReportsGridFields
	{
		reportTitle, // = 0
		dateAdded
	}
}
