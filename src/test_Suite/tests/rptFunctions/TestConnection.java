package test_Suite.tests.rptFunctions;


//import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.TablesUtil;

/**
 * FOR TESTING DATABASE CONNECTION 
 * SQL and Oracle
 * @author s.grobbelaar
 *
 */

public class TestConnection {

	private static Log log = LogFactory.getLog(TestConnection.class);

	// Change the boolean value for database server//
	boolean isSQLDB = false;

	IRptFuncConst.EReportingFunctions funcName;

	RptFuncs reportFunc;

//	private ResultSet rsUpdate;
//
//	private ResultSet rsResult;

	@BeforeClass(groups = {"TestNG"})
	public void setUp() throws SQLException {

		try {

			if (isSQLDB) {
				funcName = IRptFuncConst.EReportingFunctions.sqlProgram;
			} else {
				funcName = IRptFuncConst.EReportingFunctions.oraProgram;
			}

			reportFunc = new RptFuncs(isSQLDB);
			reportFunc.setFunctionName(funcName);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

		}
	}
/*
	@Test(groups = {"TestNG"})
	public void testConnections() throws SQLException {

		try {
			//updateQueryMethod(rsUpdate, reportFunc, IRptFuncConst.updateTestQuery);
			try {
				rsResult = reportFunc.executeQuery(RptFuncUtil.dataArchive_Ora_CloseProjs_Query("A-Gnrl-PA-FOPP"));
			} catch (Exception e) {
				log.error("Unexpected Exception", e);

			}
			
			log.warn("ResultSet: "+ rsResult);

			//updateQueryMethod(rsResult, reportFunc, IRptFuncConst.commitQuery);

			
	//		selectQueryMethod(rsResult, reportFunc, IRptFuncConst.selectTestQuery);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);
		}
	}

//	public void updateQueryMethod(ResultSet rsResult, RptFuncs reportFunc, String query) throws SQLException{
//
//		try {
//			rsResult = reportFunc.executeQuery(query);
//		} catch (Exception e) {
//			log.error("Unexpected Exception", e);
//
//		}
//	}
//	public void selectQueryMethod(ResultSet rsResult, RptFuncs reportFunc, String query) throws SQLException{
//		try {
//			rsResult = reportFunc.executeQuery(query);
//		} catch (Exception e) {
//			log.error("Unexpected Exception", e);
//
//		}
//
//		if (rsResult.next()) {
//			String	result = rsResult.getString(1);
//			System.out.println("\nANSWER is : "+ result);
//		}
//
//	}*/
	

	@Test(groups = { "WorkflowNG" })
	public void close_Age_Projects() throws Exception {

		GeneralUtil.switchToPO();

	//	GeneralUtil.runCloseAndAge_QueryStatements(isSQLDB, rsResult, reportFunc, 7, fopp.getFoppFullIdent());

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.manageArchive_lbl), "Fail: Couldn't find 'Manage Archive' Link");

		FiltersUtil.filterListByLabel(IGeneralConst.da_foppIdentifier_lbl, "A-Gnrl-PA-FOPP", 
				IGeneralConst.da_foppIdentifier_dropDown_value);

		//check to see if double clicking filter button is a bug??
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.filterBtn), "FAIL: Couldn't click the Filter button");
		
	//	Assert.assertTrue(ClicksUtil.clickImage(IClicksConst.imageIcon_src), "Fail: FOPP not present, projects not listed!");
		
		Assert.assertTrue(TablesUtil.findInTable(ITablesConst.dataArchive_ManageArchiveFOPPList_Id, "0"));

	}


}
