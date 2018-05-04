package test_Suite.utils.ui;

import static watij.finders.SymbolFactory.id;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.IFiltersConst.EProjStdrdFltrLbls;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import watij.elements.*;
import watij.runtime.ie.IE;

public class FiltersUtil {

	private static Log log = LogFactory.getLog(FiltersUtil.class);

	/*
	 * public static boolean filterListWithMultibleValls(String lblStr,String
	 * ddVal, String ver1, String ver2) throws Exception { if (
	 * !TablesUtil.isTableExists(ITablesConst.poFilterTableId) ) {
	 * log.error("Could not find table body (" +
	 * ITablesConst.poFilterTableBodyId + ")."); return false; }
	 * 
	 * Assert.assertTrue(findAndFilterDropdown(ITablesConst.poFilterTableId,
	 * lblStr, ddVal));
	 * 
	 * Assert.assertTrue(findAndEnterVal(lblStr, ver1, 0));
	 * 
	 * if ( null != ver2 && ddVal.equals(IFiltersConst.versionNumber_Between) )
	 * { Assert.assertTrue(findAndEnterVal(lblStr, ver2, 1)); }
	 * 
	 * if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) ) {
	 * log.error("Unable to click filter button."); return false; } return true;
	 * }
	 */

	public static void filterList(String lblString, String txtValue,
			String ddValue) throws Exception {

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, lblString);

		for (TableRow row : table.rows()) {

			if (row.innerText().split(":")[0].startsWith(lblString)) {

				if (!txtValue.trim().equals("")) {
					row.textField(0).set(txtValue.trim());
				}

				if (!ddValue.trim().equals("")) {

					row.selectList(0).select(ddValue.trim());
				}
				break;
			}
		}

		return;
	}

	// public static int findProjectInList(Project proj, String tableId) throws
	// Exception
	// {
	// String[] projValues = new String[3];
	//
	// String tmp = proj.getProjFullName();
	//
	// if ( proj.getCurrentStepName().contains("Initial") )
	// {
	// tmp = proj.getSubProjFullName();
	// }
	//
	// projValues[0] = tmp;
	// projValues[1] = proj.getProgFullName();
	// projValues[2] = proj.getCurrentStepName();
	//
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return -1;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// for (EProjStdrdFltrLbls lbl : EProjStdrdFltrLbls.values())
	// {
	// if (
	// !filterPOListByLabel(IFiltersConst.standardFilterLabels[lbl.ordinal()],
	// projValues[lbl.ordinal()], "") )
	// {
	// log.error("Unable to filter project list by label (" +
	// IFiltersConst.standardFilterLabels[lbl.ordinal()] + ")");
	// return -1;
	// }
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return -1;
	// }
	//
	// int projIndex = TablesUtil.getRowIndex(tableId,
	// proj.getCurrentStepName());
	// if ( -1 == projIndex )
	// {
	// log.error("Unable to get table index for project: " +
	// proj.getProgFullName());
	// }
	// return projIndex;
	//
	//
	// }

	public static int findProjectInList(Project proj, String tableId)
			throws Exception {
		String[] projValues = new String[3];

		String tmp = proj.getProjFullName();

		if (proj.getCurrentStepName().contains("Initial")) {
			tmp = proj.getSubProjFullName();
		}

		projValues[0] = tmp;
		projValues[1] = proj.getProgFullName();
		projValues[2] = proj.getCurrentStepName();

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return -1;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		for (EProjStdrdFltrLbls lbl : EProjStdrdFltrLbls.values()) {
			if (!filterPOListByLabel(
					IFiltersConst.standardFilterLabels[lbl.ordinal()],
					projValues[lbl.ordinal()], "")) {
				log.error("Unable to filter project list by label ("
						+ IFiltersConst.standardFilterLabels[lbl.ordinal()]
						+ ")");
				return -1;
			}
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return -1;
		}

		int projIndex = TablesUtil.getRowIndex(tableId,
				proj.getCurrentStepName());
		if (-1 == projIndex) {
			log.error("Unable to get table index for project: "
					+ proj.getProgFullName());
		}
		return projIndex;

	}

	/** UNUSED */
	// public static boolean findAndEnterVal(String lblString, String txtValue,
	// int textFiledIndex) throws Exception
	// {
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// TableBody tBody = ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId);
	// if ( null == tBody )
	// {
	// log.error("Could not access the table body!");
	// return false;
	// }
	//
	// for (TableRow row : tBody.rows())
	// {
	//
	// if ( row.innerText().split(":")[0].startsWith(lblString) )
	// {
	// row.textField(textFiledIndex).set(txtValue.trim());
	// return true;
	// }
	// }
	//
	// log.error("Unable to store value (" + txtValue + ") in field ("+
	// lblString + ")!");
	// return false;
	// }

	// public static boolean findAndFilterDropdown(String tableId, String
	// lblString, String ddValue) throws Exception
	// {
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find filter table!");
	// return false;
	// }
	// IE ie = IEUtil.getActiveIE();
	//
	// TableBody tBody = ie.table(id, tableId).body(0);
	// if ( null == tBody )
	// {
	// log.error("Could not access the table body!");
	// return false;
	// }
	//
	// for (TableRow row : tBody.rows())
	// {
	// if ( row.innerText().split(":")[0].startsWith(lblString) )
	// {
	// if ( findInDrpDwn(row,ddValue.trim()) )
	// {
	// row.selectList(0).select(ddValue.trim());
	// return true;
	// }
	// }
	// }
	//
	// log.error("Could not find item (" + ddValue + ") in dropdown ("+
	// lblString + ")!");
	// return false;
	// }

	public static boolean findAndFilterDropdown(String tableId,
			String lblString, String ddValue) throws Exception {
		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find filter table!");
			return false;
		}

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, lblString);

		for (TableRow row : table.rows()) {
			if (row.innerText().split(":")[0].startsWith(lblString)) {
				if (findInDrpDwn(row, ddValue.trim())) {
					row.selectList(0).select(ddValue.trim());
					return true;
				}
			}
		}

		log.error("Could not find item (" + ddValue + ") in dropdown ("
				+ lblString + ")!");
		return false;
	}

	public static boolean findAndSelectDropdown(String tableId,
			String lblString, String ddValue) throws Exception {
		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find span!");
			return false;
		}

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, lblString);

		for (TableRow row : table.rows()) {
			if (row.innerText().split(":")[0].startsWith(lblString)) {
				for (String str : row.selectList(0).getAllContents()) {
					log.debug(str.toString());

					if (str.startsWith(ddValue)) {
						row.selectList(0).select(str);
						return true;
					}
				}
			}
		}

		log.error("Unable to select value (" + ddValue + ") from dropdown ("
				+ lblString + ")!");
		return false;
	}

	public static boolean findInDrpDwn(TableRow row, String ddValue)
			throws Exception {
		for (String str : row.selectList(0).getAllContents()) {
			log.debug(str.toString());

			if (str.matches(ddValue)) {
				return true;
			}
		}

		log.error("Could not find item (" + ddValue + ") in dropdown");
		return false;
	}

	public static boolean extraFilterListByLabel(String lblString,
			String txtValue, String ddValue) throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);

		if (!filterPOListByLabel(lblString, txtValue, ddValue)) {
			log.error("Unable to filter list by label (" + lblString + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		return true;
	}

	public static boolean extraFilterListByLabel_fo(String lblString,
			String txtValue, String ddValue) throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);

		if (!filterFOListByDiv(lblString, txtValue, ddValue)) {
			log.error("Unable to filter list by label (" + lblString + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		return true;
	}

	// public static boolean filterListByProject(Project proj) throws Exception
	// {
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// String fullProj;
	//
	// if ( proj.getClaimNumber() > 0 )
	// {
	// fullProj =
	// proj.getProjFullName().concat(" - Claim ".concat(proj.getClaimNumber().toString())
	// );
	// }
	// else
	// {
	// fullProj = proj.getProjFullName();
	// }
	//
	// if ( !filterPOListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl,
	// fullProj, "") )
	// {
	// log.error("Unable to filter list by project name (" + fullProj + ").");
	// return false;
	// }
	//
	// if (
	// !filterPOListByLabel(IFiltersConst.grantManagement_FundingOppName_Lbl,
	// proj.getProgFullName(), "") )
	// {
	// log.error("Unable to filter list by funding opportunity name (" +
	// proj.getProgFullName() + ").");
	// return false;
	// }
	//
	// //due to a defect in Current Step listed in Projects list this filter is
	// commented out for now
	//
	// // if ( !filterPOListByLabel(IFiltersConst.grantManagement_StepName_Lbl,
	// proj.getCurrentStepName(), "") )
	// // {
	// // log.error("Unable to filter list by step name (" +
	// proj.getCurrentStepName() + ").");
	// // return false;
	// // }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return false;
	// }
	//
	// return true;
	// }

	public static boolean filterListByProject(Project proj) throws Exception {
		// if ( GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId) )
		// {
		// log.error("Could not find Span!");
		// return false;
		// }

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		String fullProj;

		if (proj.getClaimNumber() > 0) {
			fullProj = proj.getProjFullName().concat(
					" - Claim ".concat(proj.getClaimNumber().toString()));
		} else {
			fullProj = proj.getProjFullName();
		}

		if (!filterPOListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl,
				fullProj, "")) {
			log.error("Unable to filter list by project name (" + fullProj
					+ ").");
			return false;
		}

		if (!filterPOListByLabel(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName(), "")) {
			log.error("Unable to filter list by funding opportunity name ("
					+ proj.getProgFullName() + ").");
			return false;
		}

		// due to a defect in Current Step listed in Projects list this filter
		// is commented out for now

		// if ( !filterPOListByLabel(IFiltersConst.grantManagement_StepName_Lbl,
		// proj.getCurrentStepName(), "") )
		// {
		// log.error("Unable to filter list by step name (" +
		// proj.getCurrentStepName() + ").");
		// return false;
		// }

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return false;
		}

		return true;
	}

	// public static boolean filterListByProjectNew(Project proj) throws
	// Exception
	// {
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// String fullProj;
	//
	// if ( proj.getClaimNumber() > 0 )
	// {
	// fullProj =
	// proj.getProjFullName().concat(" - Claim ".concat(proj.getClaimNumber().toString())
	// );
	// }
	// else
	// {
	// fullProj = proj.getProjFullName();
	// }
	//
	// if ( !filterPOListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl,
	// fullProj, "") )
	// {
	// log.error("Unable to filter list by project name (" + fullProj + ").");
	// return false;
	// }
	//
	// if (
	// !filterPOListByLabel(IFiltersConst.grantManagement_FundingOppName_Lbl,
	// proj.getProgFullName(), "") )
	// {
	// log.error("Unable to filter list by funding opportunity name (" +
	// proj.getProgFullName() + ").");
	// return false;
	// }
	//
	// //due to a defect in Current Step listed in Projects list this filter is
	// commented out for now
	//
	// if ( !filterPOListByLabel(IFiltersConst.grantManagement_StepName_Lbl,
	// proj.getCurrentStepName(), "") )
	// {
	// log.error("Unable to filter list by step name (" +
	// proj.getCurrentStepName() + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return false;
	// }
	//
	// return true;
	// }

	public static boolean filterListByProjectNew(Project proj) throws Exception {
		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		String fullProj;

		if (proj.getClaimNumber() > 0) {
			fullProj = proj.getProjFullName().concat(
					" - Claim ".concat(proj.getClaimNumber().toString()));
		} else {
			fullProj = proj.getProjFullName();
		}

		if (!filterPOListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl,
				fullProj, "")) {
			log.error("Unable to filter list by project name (" + fullProj
					+ ").");
			return false;
		}

		if (!filterPOListByLabel(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				proj.getProgFullName(), "")) {
			log.error("Unable to filter list by funding opportunity name ("
					+ proj.getProgFullName() + ").");
			return false;
		}

		// due to a defect in Current Step listed in Projects list this filter
		// is commented out for now

		if (!filterPOListByLabel(IFiltersConst.grantManagement_StepName_Lbl,
				proj.getCurrentStepName(), "")) {
			log.error("Unable to filter list by step name ("
					+ proj.getCurrentStepName() + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return false;
		}

		return true;
	}

	/** UNUSED */
	// public static boolean filterProjects(Project proj) throws Exception
	// {
	//
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	//
	// if (
	// !filterPOListByLabel(IFiltersConst.grantManagement_FundingOppName_Lbl,
	// proj.getProgFullName(), "") )
	// {
	// log.error("Unable to filter list by funding opportunity name (" +
	// proj.getProgFullName() + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return false;
	// }
	//
	// return true;
	//
	// }
	/** UNUSED */
	// public static boolean filterListByLabel(G3MenuResources menuRes, String
	// lblString, String txtValue, String ddValue) throws Exception
	// {
	// ClicksUtil.clickLinks(menuRes);
	//
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// if ( !filterPOListByLabel(lblString, txtValue, ddValue) )
	// {
	// log.error("Unable to filter list by label (" + lblString + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return false;
	// }
	//
	// return true;
	// }
	//
	// public static boolean filterFOSubListByLabel(String lblString, String
	// txtValue, String ddValue) throws Exception
	// {
	// IE ie = IEUtil.getActiveIE();
	//
	// if ( !ie.div(id, IFiltersConst.fo_SubmissionFilterDiv).exists() )
	// {
	// log.error("Could not find the DIV that contains the submissions list filters in FO!");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// if ( !filterFOListByDiv(lblString, txtValue, ddValue) )
	// {
	// log.error("Unable to filter sub-list by label (" + lblString + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return false;
	// }
	// return true;
	// }

	public static boolean filterFOSubListByLabel(String lblString,
			String txtValue, String ddValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if (!ie.span(id, IFiltersConst.fo_SubmissionFilterSpan).exists()) {
			log.error("Could not find the Span that contains the submissions list filters in FO!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		if (!filterFOListByDiv(lblString, txtValue, ddValue)) {
			log.error("Unable to filter sub-list by label (" + lblString + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return false;
		}
		return true;
	}

	public static boolean filterFOSubListByLabel_New(String lblString,
			String txtValue, String ddValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if (!ie.div(id, IFiltersConst.fo_SubmissionFilterDiv).exists()) {
			log.error("Project not listed in FO submissions list!");
			return true;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return false;
		}
		return true;
	}

	/**
	 * @param lblString
	 * @param txtValue
	 * @param ddValue
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean filterListByLabel(String lblString, String txtValue,
			String ddValue) throws Exception {
		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		if (!filterPOListByLabel(lblString, txtValue, ddValue)) {
			log.error("Unable to filter list by label (" + lblString + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}
		return true;
	}

	// public static boolean filterListByLabel(String lblString, String
	// txtValue, String txtValue1,String ddValue) throws Exception
	// {
	// if ( !TablesUtil.isTableBodyExists(ITablesConst.poFilterTableId,
	// ITablesConst.poFilterTableBodyId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// if ( !filterPOListByLabel(lblString, txtValue, txtValue1, ddValue) )
	// {
	// log.error("Unable to filter list by label (" + lblString + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click button " .concat(IClicksConst.filterBtn));
	// return false;
	// }
	// return true;
	// }

	public static boolean filterListByLabel(String lblString, String txtValue,
			String txtValue1, String ddValue) throws Exception {
		// if ( GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId) )
		// {
		// log.error("Could not find Span!");
		// return false;
		// }

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		if (!filterPOListByLabel(lblString, txtValue, txtValue1, ddValue)) {
			log.error("Unable to filter list by label (" + lblString + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}
		return true;
	}

	public static boolean findInDropdown(SelectList slctList, String ddValue)
			throws Exception {
		List<String> list = slctList.getAllContents();

		for (String string : list) {
			if (string.equals(ddValue)) {
				return true;
			}
		}

		log.error("Could not find item (" + ddValue
				+ ") in dropdown in method findInDropdown");
		return false;
	}

	public static boolean filterProjListByNameAndStatus(String ddLblString,
			String ddLstVal, String lblString, String txtValue, String ddValue)
			throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		if (!filterPOListByLabel(lblString, txtValue, ddValue)) {
			log.error("Unable to filter list by label (" + lblString + ").");
			return false;
		}

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, ddLblString);

		boolean retValue = false;

		for (TableRow row : table.rows()) {

			log.debug(row.innerText());
			log.debug(row.innerText().split(":")[0]);

			if (row.innerText().split(":")[0].trim().startsWith(ddLblString)) {

				retValue = true;

				if (!ddLstVal.trim().equals("")) {
					if (!row.selectList(0).exists()) {
						log.error("Could not find Dropdown!");
						return false;
					}
					row.selectList(0).select(ddLstVal.trim());
				}
				break;
			}

		}
		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		return retValue;
	}

	// public static boolean filterByFopp(String lblString, String txtValue,
	// String ddValue) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if(!ie.table(id, ITablesConst.poTableBodyId).body(id,
	// ITablesConst.poTableBodyId).exists())
	// {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// if ( !filterPOListByLabel(lblString, txtValue, ddValue) )
	// {
	// log.error("Unable to filter list by label (" + lblString + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click button " .concat(IClicksConst.filterBtn));
	// return false;
	// }
	// return true;
	// }

	// public static boolean filterProjListByStatus(String ddLblString, String
	// ddLstVal) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if(!ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId).exists())
	// {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// TableBody tBody =
	// ie.table(id,ITablesConst.poFilterTableId).body(id,ITablesConst.poFilterTableBodyId);
	//
	// boolean retValue = false;
	//
	// for (TableRow row : tBody.rows()) {
	//
	// log.debug(row.innerText());
	// log.debug(row.innerText().split(":")[0]);
	//
	// if(row.innerText().split(":")[0].trim().startsWith(ddLblString)) {
	//
	// retValue = true;
	//
	// if(!ddLstVal.trim().equals(""))
	// {
	// if(!row.selectList(0).exists())
	// {
	// log.error("Could not find Dropdown!");
	// return false;
	// }
	// row.selectList(0).select(ddLstVal.trim());
	// }
	// break;
	// }
	//
	// }
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click button " .concat(IClicksConst.filterBtn));
	// return false;
	// }
	//
	// return retValue;
	// }

	public static boolean filterProjListByStatus(String ddLblString,
			String ddLstVal) throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, ddLblString);

		boolean retValue = false;

		for (TableRow row : table.rows()) {

			log.debug(row.innerText());
			log.debug(row.innerText().split(":")[0]);

			if (row.innerText().split(":")[0].trim().startsWith(ddLblString)) {

				retValue = true;

				if (!ddLstVal.trim().equals("")) {
					if (!row.selectList(0).exists()) {
						log.error("Could not find Dropdown!");
						return false;
					}
					row.selectList(0).select(ddLstVal.trim());
				}
				break;
			}

		}
		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		return retValue;
	}

	// public static boolean filterByTwoFields(String fieldLbl_1, String
	// fieldVar_1, String fieldLbl_2,
	// String fieldVar_2, String ddLstVal) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if(!ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId).exists())
	// {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// if ( !filterPOListByLabel(fieldLbl_1, fieldVar_1, ddLstVal) )
	// {
	// log.error("Unable to filter list by label (" + fieldVar_1 + ").");
	// return false;
	// }
	//
	// if ( !filterPOListByLabel(fieldLbl_2, fieldVar_2, ddLstVal) )
	// {
	// log.error("Unable to filter list by label (" + fieldVar_2 + ").");
	// return false;
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click button " .concat(IClicksConst.filterBtn));
	// return false;
	// }
	//
	// return true;
	// }

	public static boolean filterByTwoFields(String fieldLbl_1,
			String fieldVar_1, String fieldLbl_2, String fieldVar_2,
			String ddLstVal) throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		if (!filterPOListByLabel(fieldLbl_1, fieldVar_1, ddLstVal)) {
			log.error("Unable to filter list by label (" + fieldVar_1 + ").");
			return false;
		}

		if (!filterPOListByLabel(fieldLbl_2, fieldVar_2, ddLstVal)) {
			log.error("Unable to filter list by label (" + fieldVar_2 + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		return true;
	}

	// public static boolean filterByFopp_projName_subStatus(String fopp, String
	// projType, String ddLstVal, String ddSubVal) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if(!ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId).exists())
	// {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// if ( !filterPOListByLabel(IFiltersConst.projects_FOPPName_Lbl, fopp,
	// ddLstVal) )
	// {
	// log.error("Unable to filter list by label for (" + fopp + ").");
	// return false;
	// }
	//
	// if ( !filterPOListByLabel(IFiltersConst.projects_ProjectName_Lbl,
	// projType, ddLstVal) )
	// {
	// log.error("Unable to filter list by label for (" + projType + ").");
	// return false;
	// }
	//
	// TableBody tBody =
	// ie.table(id,ITablesConst.poFilterTableId).body(id,ITablesConst.poFilterTableBodyId);
	//
	// boolean retValue = false;
	//
	// for (TableRow row : tBody.rows()) {
	//
	// log.debug(row.innerText());
	// log.debug(row.innerText().split(":")[0]);
	//
	// if(row.innerText().split(":")[0].trim().startsWith(IFiltersConst.pap_SubmissionStatus_Lbl))
	// {
	//
	// retValue = true;
	//
	// if(!ddSubVal.trim().equals(""))
	// {
	// if(!row.selectList(0).exists())
	// {
	// log.error("Could not find Dropdown!");
	// return false;
	// }
	// row.selectList(0).select(ddSubVal.trim());
	// }
	// break;
	// }
	//
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click button " .concat(IClicksConst.filterBtn));
	// return false;
	// }
	//
	//
	// return true;
	// }
	public static boolean filterByFopp_projName_subStatus(String fopp,
			String projType, String ddLstVal, String ddSubVal) throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		if (!filterPOListByLabel(IFiltersConst.projects_FOPPName_Lbl, fopp,
				ddLstVal)) {
			log.error("Unable to filter list by label for (" + fopp + ").");
			return false;
		}

		if (!filterPOListByLabel(IFiltersConst.projects_ProjectName_Lbl,
				projType, ddLstVal)) {
			log.error("Unable to filter list by label for (" + projType + ").");
			return false;
		}

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId,
				IFiltersConst.pap_SubmissionStatus_Lbl);

		for (TableRow row : table.rows()) {

			log.debug(row.innerText());
			log.debug(row.innerText().split(":")[0]);

			if (row.innerText().split(":")[0].trim().startsWith(
					IFiltersConst.pap_SubmissionStatus_Lbl)) {

				if (!ddSubVal.trim().equals("")) {
					if (!row.selectList(0).exists()) {
						log.error("Could not find Dropdown!");
						return false;
					}
					row.selectList(0).select(ddSubVal.trim());
				}
				break;
			}

		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		return true;
	}

	public static boolean filterFOListByDiv(String lblString, String txtValue,
			String ddValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if (!ie.span(id, IFiltersConst.fo_SubmissionFilterSpan).exists()) {
			log.error("Could not find the DIV that contains the submissions list filters in FO!");
			return false;
		}

		Divs divs = ie.span(id, IFiltersConst.fo_SubmissionFilterSpan).divs();

		for (Div div : divs) {
			if (div.innerText().startsWith(lblString)) {
				if (div.textField(0).exists()) {
					div.textField(0).set(txtValue);
				}
				if (findInDropdown(div.selectList(0), ddValue)) {
					div.selectList(0).select(ddValue);

					if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
						log.error("Unable to click filter button.");
						return false;
					}

					GeneralUtil.takeANap(1.0);
					return true;
				}

				log.error("Could not find item (" + ddValue
						+ ") in dropdown in method filterFOListByDiv.");
				return false;
			}
		}

		log.error("Could not find div by label (" + lblString + ").");
		return false;
	}

	public static boolean filterRefTablesListByDiv(String lblString,
			String txtValue, String ddValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if (!ie.span(id, "g3-form:filterAndFilterControls").exists()) {
			log.error("Could not find the SPAN that contains the Ref Tables list filters in PO!");
			return false;
		}

		Divs divs = ie.span(id, "g3-form:filterAndFilterControls").divs();

		for (Div div : divs) {
			if (div.innerText().startsWith(lblString)) {
				if (!txtValue.trim().equals("")) {
					if (!div.textField(0).exists()) {
						log.error("Could not find Text field!");
						return false;
					}
					div.textField(0).set(txtValue.trim());
				}

				if (findInDropdown(div.selectList(0), ddValue)) {
					div.selectList(0).select(ddValue);

					if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
						log.error("Unable to click filter button.");
						return false;
					}

					return true;
				}

				log.error("Could not find item (" + ddValue + ") in dropdown.");
				return false;
			}
		}

		log.error("Could not find div by label (" + lblString + ").");
		return false;
	}

	// public static boolean filterPOListByLabel(String lblString, String
	// txtValue, String ddValue) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if(!ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId).exists())
	// {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// TableBody tBody =
	// ie.table(id,ITablesConst.poFilterTableId).body(id,ITablesConst.poFilterTableBodyId);
	//
	// boolean retValue = false;
	//
	// for (TableRow row : tBody.rows()) {
	//
	// log.debug(row.innerText());
	// log.debug(row.innerText().split(":")[0]);
	//
	// //String str = row.innerText().split(":")[0].trim();
	//
	// if(row.innerText().split(":")[0].trim().startsWith(lblString)) {
	//
	// retValue = true;
	//
	// if(!txtValue.trim().equals(""))
	// {
	// if(!row.textField(0).exists())
	// {
	// log.error("Could not find Text field!");
	// return false;
	// }
	// row.textField(0).set(txtValue.trim());
	// }
	//
	// if(!ddValue.trim().equals(""))
	// {
	// if(!row.selectList(0).exists())
	// {
	// log.error("Could not find Dropdown!");
	// return false;
	// }
	// row.selectList(0).select(ddValue.trim());
	// }
	// break;
	// }
	//
	// }
	//
	// return retValue;
	// }
	
	public static boolean filterAuditList(String lblString,
			String txtValue, String ddValue) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		TableRow row = ie.table(id, "main:auditReportingListEvents:auditReportListForm:j_id_27").row(2);
		
		Tables tables = row.tables();
		
		for (Table table : tables) {
			
			if(table.innerText().startsWith(lblString))
			{
				if((table.row(0).textField(0).exists()) && (!txtValue.isEmpty()))
				{
					table.row(0).textField(0).clear();
					table.row(0).textField(0).append(txtValue);
					
					txtValue = "";
					
					if(ddValue.isEmpty())
					{
						return true;
					}
					
				}
				
				if((table.row(0).selectList(0).exists() && (!ddValue.isEmpty())))
				{
					table.row(0).selectList(0).select(ddValue);
					
					ddValue = "";
					
					if(txtValue.isEmpty())
					{
						return true;
					}
				}
				
				break;
					
			}
			
		}		
		
		log.error("Could not find any component for the filter");
		
		return false;
	}

	public static boolean filterPOListByLabel(String lblString,
			String txtValue, String ddValue) throws Exception {

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, lblString);

		for (TableRow row : table.rows()) {

			log.warn(row.innerText());
			log.warn(row.innerText().split(":")[0]);			

			if (row.innerText().split(":")[0].trim().startsWith(lblString)); 
			{
				if (!txtValue.trim().equals("")) 
				{
					if (!row.textField(0).exists()) 
					{
						log.error("Could not find Text field!");
						return false;
					}
					row.textField(0).set(txtValue.trim());
				}

				if (!ddValue.trim().equals("")) 
				{

					if (!row.selectList(0).exists()) 
					{
						log.error("Could not find Dropdown!");
						return false;
					}
					row.selectList(0).select(ddValue.trim());
					break;
				} else 
				{
					break;
				}
			}
			
		}
		return true;
	}

	/** UNUSED */
	// public static boolean filterBudgetLimitListByLabel(String lblString,
	// String txtValue, String ddValue) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if(!ie.table(id, ITablesConst.foSubmissionFilterDiv).body(id,
	// ITablesConst.poFilterTableBodyId).exists())
	// {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// TableBody tBody =
	// ie.table(id,ITablesConst.poFilterTableId).body(id,ITablesConst.poFilterTableBodyId);
	//
	// boolean retValue = false;
	//
	// for (TableRow row : tBody.rows()) {
	//
	// log.debug(row.innerText());
	// log.debug(row.innerText().split(":")[0]);
	//
	// //String str = row.innerText().split(":")[0].trim();
	//
	// if(row.innerText().split(":")[0].trim().startsWith(lblString)) {
	//
	// retValue = true;
	//
	// if(!txtValue.trim().equals(""))
	// {
	// if(!row.textField(0).exists())
	// {
	// log.error("Could not find Text field!");
	// return false;
	// }
	// row.textField(0).set(txtValue.trim());
	// }
	//
	// if(!ddValue.trim().equals(""))
	// {
	// if(!row.selectList(0).exists())
	// {
	// log.error("Could not find Dropdown!");
	// return false;
	// }
	// row.selectList(0).select(ddValue.trim());
	// }
	// break;
	// }
	//
	// }
	//
	// return retValue;
	// }

	// public static boolean filterPOListByLabel(String lblString,
	// String txtValue, String txtValue1, String ddValue) throws Exception {
	//
	// IE ie = IEUtil.getActiveIE();
	//
	// if (!ie.table(id, ITablesConst.poFilterTableId)
	// .body(id, ITablesConst.poFilterTableBodyId).exists()) {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// TableBody tBody = ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId);
	//
	// boolean retValue = false;
	//
	// for (TableRow row : tBody.rows()) {
	//
	// if (row.innerText().split(":")[0].startsWith(lblString)) {
	//
	// retValue = true;
	//
	// if (!ddValue.trim().equals("")) {
	// if (!row.selectList(0).exists()) {
	// log.error("Could not find Dropdown!");
	// return false;
	// }
	// row.selectList(0).select(ddValue.trim());
	// }
	// if (!txtValue.trim().equals("")) {
	// if (!row.textField(0).exists()) {
	// log.error("Could not find Text field!");
	// return false;
	// }
	// row.textField(0).set(txtValue.trim());
	// }
	//
	// }
	// }
	// IE ie1 = IEUtil.getActiveIE();
	//
	// if (!ie1.table(id, ITablesConst.poFilterTableId)
	// .body(id, ITablesConst.poFilterTableBodyId).exists()) {
	// log.error("Could not find Filter Table Body!");
	// return false;
	// }
	//
	// TableBody tBody1 = ie1.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId);
	//
	// for (TableRow row1 : tBody1.rows()) {
	//
	// if (row1.innerText().split(":")[0].startsWith(lblString)) {
	//
	// retValue = true;
	//
	// if (!txtValue1.trim().equals("")) {
	// if (!row1.textField(1).exists()) {
	// log.error("Could not find Text field!");
	// return false;
	// }
	// row1.textField(1).set(txtValue1.trim());
	// }
	//
	// break;
	// }
	//
	// }
	//
	// return retValue;
	// }

	public static boolean filterPOListByLabel(String lblString,
			String txtValue, String txtValue1, String ddValue) throws Exception {

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		Table table = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, lblString);

		boolean retValue = false;

		for (TableRow row : table.rows()) {

			if (row.innerText().split(":")[0].startsWith(lblString)) {

				retValue = true;

				if (!ddValue.trim().equals("")) {
					if (!row.selectList(0).exists()) {
						log.error("Could not find Dropdown!");
						return false;
					}
					row.selectList(0).select(ddValue.trim());
				}
				if (!txtValue.trim().equals("")) {
					if (!row.textField(0).exists()) {
						log.error("Could not find Text field!");
						return false;
					}
					row.textField(0).set(txtValue.trim());
				}

			}
		}

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		Table table1 = TablesUtil.getTableByInnerTextInSpan(
				ITablesConst.poFilterSpanId, lblString);

		for (TableRow row1 : table1.rows()) {

			if (row1.innerText().split(":")[0].startsWith(lblString)) {

				retValue = true;

				if (!txtValue1.trim().equals("")) {
					if (!row1.textField(1).exists()) {
						log.error("Could not find Text field!");
						return false;
					}
					row1.textField(1).set(txtValue1.trim());
				}

				break;
			}

		}

		return retValue;
	}

	public static boolean filterFOSubListByLabel(
			Hashtable<String, String> stringsFilter,
			Hashtable<String, String> drpDwnFilter) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if (!ie.div(id, IFiltersConst.fo_SubmissionFilterDiv).exists()) {
			log.error("Could not find the DIV that contains the submissions list filters in FO!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		Enumeration<String> keyStr;
		Enumeration<String> elemStr;

		String fieldLabel = "";
		String txtString = "";
		String ddValue = "";

		if (null != drpDwnFilter && !drpDwnFilter.isEmpty()) {
			keyStr = drpDwnFilter.keys();
			elemStr = drpDwnFilter.elements();

			while (keyStr.hasMoreElements()) {
				fieldLabel = keyStr.nextElement();

				ddValue = elemStr.nextElement();

				log.debug(fieldLabel);
				log.debug(ddValue);

				if (!filterFOListByDiv(fieldLabel, txtString, ddValue)) {
					log.error("Unable to filter FO sub-list by label("
							+ fieldLabel + ").");
					return false;
				}
			}
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return false;
		}

		return true;
	}

	// public static boolean filterListByLabel(Hashtable<String, String>
	// stringsFilter,
	// Hashtable<String, String> drpDwnFilter, boolean isCheckBoxOff) throws
	// Exception
	// {
	// if ( !TablesUtil.isTableExists(ITablesConst.poFilterTableId) )
	// {
	// log.error("Could not find table body (" +
	// ITablesConst.poFilterTableBodyId + ").");
	// return false;
	// }
	//
	// ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
	// ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	//
	// Enumeration<String> keyStr;
	// Enumeration<String> elemStr;
	//
	// String fieldLabel ="";
	// String txtString = "";
	// String ddValue = "";
	//
	// if ( null != stringsFilter && !stringsFilter.isEmpty() )
	// {
	// keyStr = stringsFilter.keys();
	// elemStr = stringsFilter.elements();
	//
	// while ( keyStr.hasMoreElements() )
	// {
	// fieldLabel = keyStr.nextElement();
	//
	// txtString = elemStr.nextElement();
	//
	// log.debug(fieldLabel);
	// log.debug(txtString);
	//
	// if ( !filterPOListByLabel(fieldLabel, txtString, ddValue) )
	// {
	// log.error("Unable to filter list by label (" + fieldLabel + ")");
	// return false;
	// }
	// }
	// }
	//
	// fieldLabel ="";
	// txtString = "";
	//
	// if ( null != drpDwnFilter && !drpDwnFilter.isEmpty() )
	// {
	// keyStr = drpDwnFilter.keys();
	// elemStr = drpDwnFilter.elements();
	//
	// while ( keyStr.hasMoreElements() )
	// {
	// fieldLabel = keyStr.nextElement();
	//
	// ddValue = elemStr.nextElement();
	//
	// log.debug(fieldLabel);
	// log.debug(ddValue);
	//
	// if ( !filterPOListByLabel(fieldLabel, txtString, ddValue) )
	// {
	// log.error("Unable to filter list by label (" + fieldLabel + ")");
	// return false;
	// }
	// }
	// }
	//
	// if ( isCheckBoxOff )
	// {
	// //ie.table(id, ITablesConst.poFilterTableId).body(id,
	// ITablesConst.poFilterTableBodyId).checkbox(0).set(isCheckBoxOff);
	// }
	//
	// if ( !ClicksUtil.clickButtons(IClicksConst.filterBtn) )
	// {
	// log.error("Unable to click filter button.");
	// return false;
	// }
	//
	// return true;
	// }

	public static boolean filterListByLabel(
			Hashtable<String, String> stringsFilter,
			Hashtable<String, String> drpDwnFilter, boolean isCheckBoxOff)
			throws Exception {
		GeneralUtil.takeANap(1.0);

		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		Enumeration<String> keyStr;
		Enumeration<String> elemStr;

		String fieldLabel = "";
		String txtString = "";
		String ddValue = "";

		if (null != stringsFilter && !stringsFilter.isEmpty()) {
			keyStr = stringsFilter.keys();
			elemStr = stringsFilter.elements();

			while (keyStr.hasMoreElements()) {
				fieldLabel = keyStr.nextElement();

				txtString = elemStr.nextElement();

				log.debug(fieldLabel);
				log.debug(txtString);

				if (!filterPOListByLabel(fieldLabel, txtString, ddValue)) {
					log.error("Unable to filter list by label (" + fieldLabel
							+ ")");
					return false;
				}
			}
		}

		fieldLabel = "";
		txtString = "";

		if (null != drpDwnFilter && !drpDwnFilter.isEmpty()) {
			keyStr = drpDwnFilter.keys();
			elemStr = drpDwnFilter.elements();

			while (keyStr.hasMoreElements()) {
				fieldLabel = keyStr.nextElement();

				ddValue = elemStr.nextElement();

				log.debug(fieldLabel);
				log.debug(ddValue);

				if (!filterPOListByLabel(fieldLabel, txtString, ddValue)) {
					log.error("Unable to filter list by label (" + fieldLabel
							+ ")");
					return false;
				}
			}
		}

		if (isCheckBoxOff) {
			// ie.table(id, ITablesConst.poFilterTableId).body(id,
			// ITablesConst.poFilterTableBodyId).checkbox(0).set(isCheckBoxOff);
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn)) {
			log.error("Unable to click filter button.");
			return false;
		}

		return true;
	}

	public static String getNewBaseLetter(String tableId, String fieldLbel,
			String baseObject, String ddValue) throws Exception {
		String retValue = "";
		boolean bolloop = true;
		int AlphaIndex = 0;

		while (bolloop) {
			if (!filterListByLabel(fieldLbel,
					IGeneralConst.baseLetters[AlphaIndex] + baseObject, ddValue)) {
				log.error("Unable to filter list by label (" + fieldLbel + ").");
				return "";
			}

			if (!TablesUtil.isTableExists(tableId)) {
				return "";
			}

			if (!TablesUtil.findInTable(tableId,
					IGeneralConst.baseLetters[AlphaIndex] + baseObject)) {
				bolloop = false;

				retValue = IGeneralConst.baseLetters[AlphaIndex];
			}

			AlphaIndex += 1;
		}

		return retValue;
	}

	public static String getUsedBaseLetter(String tableId, String fieldLabel,
			String baseObject, String ddValue) throws Exception {
		String retValue = "";
		boolean bolloop = true;
		int AlphaIndex = 0;

		while (bolloop) {
			if (!filterListByLabel(fieldLabel,
					IGeneralConst.baseLetters[AlphaIndex] + baseObject, ddValue)) {
				log.error("Unable to filter list by label (" + fieldLabel
						+ ").");
				return "";
			}

			if (!TablesUtil.isTableExists(tableId)) {
				return "";
			}

			if (TablesUtil.findInTable(tableId,
					IGeneralConst.baseLetters[AlphaIndex] + baseObject)) {
				bolloop = false;

				retValue = IGeneralConst.baseLetters[AlphaIndex];
			}

			AlphaIndex += 1;
		}

		return retValue;
	}

	public static boolean searchListByLabel(String lblString, String txtValue,
			String ddValue) throws Exception {
		if (!GeneralUtil.isSpanExistsById(ITablesConst.poFilterSpanId)) {
			log.error("Could not find Span!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showSearchLnk);

		if (!FiltersUtil.filterPOListByLabel(lblString, txtValue, ddValue)) {
			log.error("Unable to search list by label (" + lblString + ").");
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.searchBtn)) {
			log.error("Unable to click button ".concat(IClicksConst.searchBtn));
			return false;
		}
		return true;
	}
}
