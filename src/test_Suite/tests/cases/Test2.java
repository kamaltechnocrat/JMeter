package test_Suite.tests.cases;

import static watij.finders.SymbolFactory.*;
import static watij.finders.FinderFactory.*;

import java.sql.ResultSet;
import java.text.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;

import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.users.*;
import test_Suite.constants.workflow.*;
import test_Suite.constants.authentications.IAuthenConst;
import test_Suite.constants.authentications.IAuthenConst.EAuthProviderTypes;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.eForms.*;
import test_Suite.constants.eForms.ILBFunctionConst.*;
import test_Suite.constants.preTest.*;
import test_Suite.constants.reporting_Functions.*;
import test_Suite.constants.reporting_Functions.IEtlConst.*;
import test_Suite.constants.workflow.*;
import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.constants.workflow.IProgramsConst.*;
import test_Suite.constants.workflow.IStepsConst.*;
import test_Suite.lib.reporting_Functions.*;
import test_Suite.lib.users.*;
import test_Suite.lib.eForms.*;
import test_Suite.lib.workflow.*;
import test_Suite.lib.authentications.Authens;
import test_Suite.lib.cases.Lookups;
import test_Suite.lib.eForms.LBF;
import test_Suite.utils.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.AmendmentsUtil.EArrParams;
import watij.dialogs.ConfirmDialog;
import watij.elements.*;
import watij.finders.*;
import watij.runtime.ie.IE;

import org.apache.commons.logging.*;
import org.testng.Assert;

@SuppressWarnings({ "unused" })
public class Test2 {

	private static Log log = LogFactory.getLog(Test2.class);
	private static Table table;
	private static Tables tables;
	private static TableCells tableCells;
	private static TableCell tableCell;
	private static TableRows tableRows;
	private static TableRow tRow;
	private static Checkbox checkBox;
	private static Checkboxes checkBoxes;
	private static Images images;
	private static Image image;
	private static Spans spans;
	private String approvalProcess = "Approval Process";

	boolean isSQLDB = false;

	IRptFuncConst.EReportingFunctions funcName;
	RptFuncs reportFunc;
	private ResultSet rsResult;
	private int numProjs = 5;
	private String projType = "-arcTest";
	private int numArchs;
	LBF lbf;
	FundingOpportunity fopp;

	
	
	@Test(groups = { "tempTest" })
	public void test2() throws Exception {
		
		
	
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

		IE ie = IEUtil.getActiveIE();
		
		fopp = new FundingOpportunity("A", "-Gnrl-PA-","");
		
//		fopp.openFundingOppPlanner();
////		fopp.openFundingOppDetails();
//		fopp.openAdminEForm();
//		fopp.openPublicationEForm();
////		fopp.expandAnObject("Funding Opportunity Staff");
////		
//		
//		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");
//
//		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.closedProj_dd,
//				IGeneralConst.da_projectName_lbl, "-arcTest", "Contains"), "Fail: Couldn't filter by 'Closed Projects'!");
//					
//		Assert.assertTrue(GeneralUtil.da_selectAndProcessProjs(2, IClicksConst.changeToReadyForArchive_Id), "Fail: Couldn't convert project(s) from 'Closed' to 'Ready for Archive'");
//		if (row.span(attribute("class", IGeneralConst.newChkBx_off_Span)).exists()){
//			row.span(attribute("class", IGeneralConst.newChkBx_off_Span));
//		}
//		
//		ClicksUtil.clickLinks("Funding Opportunities");
//		
//		FiltersUtil.filterList(IFiltersConst.gpa_FundingOppIdent_Lbl, fopp.getFoppFullIdent(), "Contains");
//		
		EForm form;
		form = new EForm(IEFormsConst.submission_FormTypeName,IEFormsConst.applicantsubmission_FormTypeName,"ProfileData-");
		form.setEFormTitle(form.getEFormFullId().replace('-', ' '));			
		Formlet formlet = new Formlet(form, IFormletsConst.formletTypeName_SubmissionScheduleList);
//
//		ClicksUtil.clickLinks(IClicksConst.openOrganizationsList);
//		ClicksUtil.clickLinks("G3");
//
//
		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
		FiltersUtil.filterListByLabel(IFiltersConst.gpa_FormIdent_Lbl, "A-Fetch-Data-Applicant-Submission","Exact");
		ClicksUtil.clickLinks("A-Fetch-Data-Applicant-Submission");
		formlet.expandFormletNode();
//
//		HtmlElement ele = ie.htmlElement(id, IClicksConst.plannerExpanderTopSpan);
//		if (ele.span(attribute("class", IClicksConst.collapsedSpan)).exists())
//			ele.span(1).click();
//		
//		GeneralUtil.expandAllSpans();
//		reportFunc = GeneralUtil.connectToDB(isSQLDB, funcName, reportFunc);
//
//		Assert.assertTrue(GeneralUtil.runCloseAndAgeAll_Queries(isSQLDB, rsResult, reportFunc, 7, fopp.getFoppFullIdent()),
//				"Fail: Couldn't run queries!");
//		Assert.assertTrue(GeneralUtil.navigate_ManageArchive_Filter(fopp.getFoppFullIdent()), "Fail: Could not open FOPP in Manage Archive page!");
//
//		numArchs = TablesUtil.manageArchive_VerifyNumberinTable(ITablesConst.dataArchive_ManageArchiveFOPPList_Id, 7);
//		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), "Fail: Couldn't click on the Quartz Control Panel link");
//		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzArchJob_lbl), "Fail: Couldn't run quartz job!");	

		}
}
