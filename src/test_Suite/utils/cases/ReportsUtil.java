package test_Suite.utils.cases;

import static watij.finders.SymbolFactory.*;
import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.elements.Image;
import watij.elements.Images;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.lib.cases.Reports;
import test_Suite.utils.ui.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

public class ReportsUtil {
	private static Log log = LogFactory.getLog(ReportsUtil.class);

	private static boolean retValue;

	static int rowIndex;

	static Tables tables;
	static Table table;
	
	public static void closeChildIE() throws Exception {

		IE child_ie = IEUtil.getActiveChildIE();
		child_ie.close();
	}

	public static boolean isReportInMyReportsList(Reports report) throws Exception {
		retValue = false;
		rowIndex = -1;
		
		if(!ClicksUtil.clickLinks(IClicksConst.openMyReportsListLnk))
		{
			log.error("could not click on link " .concat(IClicksConst.openMyReportsListLnk));
			return false;
		}
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ReportName_Lbl,
				report.getReportTitle(),IFiltersConst.exact);
		
		rowIndex = TablesUtil.getRowIndex(ITablesConst.myReprotsTableId, report
				.getReportTitle());

		if (rowIndex > -1) {
			retValue = true;
		}
		return retValue;
	}

	public static boolean isParameterExistsInReportParameters(String paramLabel)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		tables = ie
				.table(id, ITablesConst.reportParams_Id)
				.tables();

		if (tables != null) {
			for (Table table : tables) {

				if (table.innerText().startsWith("* " + paramLabel)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isReportInReportsList(Reports report)
			throws Exception {

		if(!ClicksUtil.clickLinks(IClicksConst.openReportsList))
		{
			log.error("could not click on link " .concat(IClicksConst.openReportsList));
			return false;
		}

		FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, report
				.getReportIdent(), IFiltersConst.exact);

		if (TablesUtil.getRowIndex(ITablesConst.reprotsTableId, report
				.getReportIdent()) > -1) {
			return true;
		}
	return false;
	}

	public static boolean addReport(Reports report) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openReportsList))
		{
			log.error("could not click on link " .concat(IClicksConst.openReportsList));
			return false;
		}
		
		if(!ClicksUtil.clickImage(IClicksConst.addItemImg))
		{
			log.error("could not click on link " .concat(IClicksConst.addItemImg));
			return false;
		}
		
		if(!GeneralUtil.isTextFieldExistsById(IReportsConst.report_Ident))
			return false;
		ie.textField(id, IReportsConst.report_Ident).set(report.getReportIdent());
		
		if(!GeneralUtil.isSelectListEnabledById(IReportsConst.report_Status))
			return false;
		ie.selectList(id, IReportsConst.report_Status).select(report.getReportStatus());
		
		if(!GeneralUtil.isSelectListEnabledById(IReportsConst.report_Type))
			return false;
		ie.selectList(id, IReportsConst.report_Type).select(IReportsConst.reportType);
		
		if(!GeneralUtil.isSelectListEnabledById(IReportsConst.report_PrimOrg_Id))
			return false;
		ie.selectList(id, IReportsConst.report_PrimOrg_Id).select(report.getReportOrgName());
		
		if(!GeneralUtil.isSelectListEnabledById(IReportsConst.report_OrgAccess_Id))
			return false;
		ie.selectList(id, IReportsConst.report_OrgAccess_Id).select(report.getReportOrgAccess());

		for (int i = 0; i < report.getReportTitles().length; i++) {
			if (ie.textField(
					id,
					IReportsConst.report_LocaleStart_Id + Integer.toString(i)
							+ IReportsConst.report_LocaleEnd_Id).exists()) {
				ie.textField(
						id,
						IReportsConst.report_LocaleStart_Id
								+ Integer.toString(i)
								+ IReportsConst.report_LocaleEnd_Id).set(
						report.getReportTitles()[i]);
			}
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		if (!GeneralUtil.isButtonExistsByValue(IClicksConst.reportPlannerBtn))
		return false;
		
		if(!ClicksUtil.clickButtons(IClicksConst.reportPlannerBtn))
		{
			log.error("could not click on button " .concat(IClicksConst.reportPlannerBtn));
			return false;
		}
		return true;
	}

	/**
	 * Delete Report by Report Title If any Title from Titles array are found
	 * then report with this title to be deleted This method should work with
	 * different locale
	 * 
	 * @param reportsList
	 */
	public static boolean deleteReport(String reportIdent) throws Exception {
		
		Div tDiv = TablesUtil.tableDiv();
		
		int rowIndx = -1;
		
		retValue = false;

		if(!ClicksUtil.clickLinks(IClicksConst.openReportsList))
		{
			log.error("could not click on link " .concat(IClicksConst.openReportsList));
			return false;
		}
		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, reportIdent, IFiltersConst.exact);

		rowIndx = TablesUtil.getRowIndex(ITablesConst.reprotsTableId,
				reportIdent);
		
		if (rowIndx > -1) {
			if (tDiv.body(id, ITablesConst.reprotsTableId).row(rowIndx).cell(IReportsConst.eReportsGridFields.deleteIcon.ordinal()).image(src, IClicksConst.deleteImg).exists())
			{
				Thread dialogClicker_2 = new Thread() {
					@Override
					public void run() {
						
						try {
							
							IE ie = IEUtil.getActiveIE();
							
							ConfirmDialog dialog1 = ie.confirmDialog();
							
							while (dialog1 == null) {
								
								log.debug("can't yet get handle on confirm dialog1");
								
								Thread.sleep(250);

							}

							dialog1.ok();
						} catch (Exception e) {
							
							throw new RuntimeException("Unexpected exception", e);
						}
					}
				};
				
				dialogClicker_2.start();
				
				tDiv.body(id, ITablesConst.reprotsTableId).row(rowIndx).cell(IReportsConst.eReportsGridFields.deleteIcon.ordinal()).image(src, IClicksConst.deleteImg).click();
				
				GeneralUtil.takeANap(1.000);
				
				dialogClicker_2 = null;
			}
			retValue = true;
		}

		return retValue;
	}
	
	public static boolean filter3rdPartyReports(String reportIdent) throws Exception {
		try {

			if(!ClicksUtil.clickLinks(IClicksConst.openReportsList))
			{
				log.error("could not click on link " .concat(IClicksConst.openReportsList));
				return false;
			}

			if(!FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, reportIdent, IFiltersConst.exact))
			{
				log.error("could not filter on " .concat(IFiltersConst.administration_ReportIdent_Lbl));
				return false;
			}
				

			} catch (Exception e) {
					Assert.fail("Unexpected Exception: ", e);
			}
		return true;
	}

	public static boolean expandPlannerView(boolean isFullExpnasion) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if (!ie.checkbox(id, IReportsConst.recursiveExpandAndCollapse).exists())
		{
			log.error("checkbox doesn't exist " .concat(IReportsConst.recursiveExpandAndCollapse));
			return false;
		}
					
		ie.checkbox(id, IReportsConst.recursiveExpandAndCollapse).set(true);
		
		if(!ie.span(id, IReportsConst.report_Planner).table(0).image(src,
				IReportsConst.reports_MinusGif_Last_Id).exists())
			return false;
		
		ie.span(id, IReportsConst.report_Planner).table(0).image(src,
				IReportsConst.reports_MinusGif_Last_Id).click();
		
		if(!ie.span(id, IReportsConst.report_Planner).table(0).image(src,
				IReportsConst.recursiveExpandAndCollapse).exists())
			return false;

		ie.checkbox(id, IReportsConst.recursiveExpandAndCollapse).set(isFullExpnasion);
		
		if(!ie.span(id, IReportsConst.reports_MinusGif_Last_Id).table(0).image(src,
				IReportsConst.recursiveExpandAndCollapse).exists())
			return false;

		ie.span(id, IReportsConst.report_Planner).table(0).image(src,
				IReportsConst.reports_MinusGif_Last_Id).click();
		
		return true;
	}

	public static Table getTableInPlanner(String suffix) throws Exception {

		IE ie = IEUtil.getActiveIE();

		expandPlannerView(true);

		Tables tables = ie.span(id, IReportsConst.report_Planner).tables();

		Table nullTable = null;

		for (Table table : tables) {

			if (table.innerText().endsWith(suffix)) {
				return table;
			}
		}
		return nullTable;
	}

	public static boolean openReportDetails() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (!ie.span(id, IReportsConst.report_Planner).table(0).image(alt, IReportsConst.reportDetailsOpen).exists()) 		
			return false;
			
			ie.span(id, IReportsConst.report_Planner).table(0).image(alt, IReportsConst.reportDetailsOpen).click();
		
		return true;
	}

	public static boolean openReportAccessDetails() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (!ie.span(id, IReportsConst.report_Planner).table(1).image(alt,
				"Manage Report Access").exists()) {
			return false;
		}
		ie.span(id, IReportsConst.report_Planner).table(1).image(alt,
				"Manage Report Access").click();
		return true;
	}

	public static boolean openReportParameter(String paramName)
			throws Exception {

		Table table = getTableInPlanner(paramName);

		if (null != table) {
			if (table.image(alt, "Edit Report Parameter " + paramName).exists()) {
				return false;
			}
			table.image(alt, "Edit Report Parameter " + paramName).click();
			}
		return true;
	}

	public static boolean doesParameterExists(String paramName)
			throws Exception {

		expandPlannerView(true);

		Table table = getTableInPlanner(paramName);

		if (!table.innerText().contains(paramName)) {
			return false;
		}
		return true;
	}

	public static boolean doesImageExists(Table table, String imageAlt)
			throws Exception {
		Images images;

		if (null != table) {
			images = table.images();

			for (Image image : images) {

				if (image.innerText().startsWith(imageAlt)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getNewBaseLetter(String baseObject) throws Exception {		
		
		return FiltersUtil.getNewBaseLetter(ITablesConst.reprotsTableId, IFiltersConst.administration_ReportIdent_Lbl, baseObject, IFiltersConst.exact);
	}
}
