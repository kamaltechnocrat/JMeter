/**
 * 
 */
package test_Suite.tests.rptFunctions;

import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst.EeFormsIdentifier;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.utils.reporting_Functions.RptFuncUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ExecProj_AdminEFormRF {

	private static Log log = LogFactory
			.getLog(ExecProj_AdminEFormRF.class);

	// Change the boolean value for database server//
	boolean isSQLDB = true;

	IRptFuncConst.EeFormsIdentifier eFormsIdentifier;

	IRptFuncConst.EReportingFunctions funcName;

	String subCode = IRptFuncConst.ReportFunc_LatestSubmittedOrNotProject;

	String eFormIdent = IRptFuncConst.reportFunc_eFormsIdent[EeFormsIdentifier.admin
			.ordinal()];

	RptFuncs reportFunc;

	@BeforeClass(groups = { "ReportingFunctionsNG" })
	public void setUp() {

		try {

			if (isSQLDB) {
				funcName = IRptFuncConst.EReportingFunctions.sqlProgram;
			} else {
				funcName = IRptFuncConst.EReportingFunctions.oraProgram;
			}

			reportFunc = new RptFuncs(isSQLDB);

			reportFunc.setFunctionName(funcName);
			reportFunc.setSubmissionCode(subCode);
			reportFunc.setEFormIdent(eFormIdent);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "ReportingFunctionsNG" })
	public void executeProject_AdminEForm_ReportingFunctionNG()
			throws Exception {

		try {
			setOneFormlet();

			RptFuncUtil.initLinkedHashMapFormlet();

			log.info(RptFuncUtil.hashList.size());

			RptFuncUtil.runTheQueries(reportFunc);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

	}

	public void setOneFormlet() throws Exception {

		RptFuncUtil.oneFormletHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		RptFuncUtil.oneFormletHT.put(
				"Dropdown-Lookup-Properties-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		RptFuncUtil.oneFormletHT.put("Many-to-Many",
				IRptFuncConst.EfieldDataTypes.numericTP);
		RptFuncUtil.oneFormletHT.put("Radio Button",
				IRptFuncConst.EfieldDataTypes.numericTP);
	}


}
