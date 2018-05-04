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
public class ExecProfileEFormRF {

	private static Log log = LogFactory
			.getLog(ExecProfileEFormRF.class);

	// Change the boolean value for database server//
	boolean isSQLDB = false;

	IRptFuncConst.EeFormsIdentifier eFormsIdentifier;

	IRptFuncConst.EReportingFunctions funcName;

	String eFormIdent = IRptFuncConst.reportingFunc_NonProject_eFormsIdent[EeFormsIdentifier.profile
			.ordinal()];

	RptFuncs reportFunc;

	@BeforeClass(groups = { "ReportingFunctionsNG" })
	public void setUp() {

		try {

			if (isSQLDB) {
				funcName = IRptFuncConst.EReportingFunctions.sqlApplicant;
			} else {
				funcName = IRptFuncConst.EReportingFunctions.oraApplicant;
			}

			reportFunc = new RptFuncs(isSQLDB);

			reportFunc.setFunctionName(funcName);
			reportFunc.setEFormIdent(eFormIdent);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "ReportingFunctionsNG" })
	public void allFormlets_ApplicantEForm() throws Exception {

		try {

			RptFuncUtil.initLinkedHashMap();

			log.info(RptFuncUtil.hashList.size());

			RptFuncUtil.runTheQueries(reportFunc);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "ReportingFunctionsNG" })
	public void oneFormelt_ApplicantEForm() throws Exception {

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
		RptFuncUtil.oneFormletHT.put("eList Data Grid",
				IRptFuncConst.EfieldDataTypes.formlet);
		RptFuncUtil.oneFormletHT.put("eList DG",
				IRptFuncConst.EfieldDataTypes.stringTP);
	}


}
