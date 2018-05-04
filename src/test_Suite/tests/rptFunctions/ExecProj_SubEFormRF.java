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
public class ExecProj_SubEFormRF {

	private static Log log = LogFactory.getLog(ExecProj_SubEFormRF.class);

	// Change the boolean value for database server//
	boolean isSQLDB = false;

	IRptFuncConst.EeFormsIdentifier eFormsIdentifier;

	IRptFuncConst.EReportingFunctions funcName;

	String subCode = IRptFuncConst.ReportFunc_LatestSubmittedProject;

	String eFormIdent = IRptFuncConst.reportFunc_eFormsIdent[EeFormsIdentifier.submission
			.ordinal()];

	RptFuncs reportFunc;

	@BeforeClass(groups = { "ReportingFunctionsNG" })
	public void setUp() {

		try {

			if (isSQLDB) {
				funcName = IRptFuncConst.EReportingFunctions.sqlProject;
			} else {
				funcName = IRptFuncConst.EReportingFunctions.oraProject;
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
	public void executeProject_SubmissionEForm_ReportingFunctionNG()
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
		RptFuncUtil.oneFormletHT.put("Dropdown-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		RptFuncUtil.oneFormletHT.put("Dropdown",
				IRptFuncConst.EfieldDataTypes.numericTP);
		RptFuncUtil.oneFormletHT.put("Child Dropdown",
				IRptFuncConst.EfieldDataTypes.numericTP);
		RptFuncUtil.oneFormletHT.put(
				"Dropdown From List (Text Value from Applicant Profile)",
				IRptFuncConst.EfieldDataTypes.stringTP);
	}
}
