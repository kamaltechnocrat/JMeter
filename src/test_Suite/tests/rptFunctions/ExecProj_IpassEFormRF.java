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
public class ExecProj_IpassEFormRF {

	private static Log log = LogFactory
			.getLog(ExecProj_IpassEFormRF.class);

	// Change the boolean value for database server//
	boolean isSQLDB = true;

	IRptFuncConst.EeFormsIdentifier eFormsIdentifier;

	IRptFuncConst.EReportingFunctions funcName;

	String subCode = IRptFuncConst.ReportFunc_LatestSubmittedProject;

	String eFormIdent = IRptFuncConst.reportFunc_eFormsIdent[EeFormsIdentifier.claim
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
	public void executeProject_TypesProp_ReportingFunctionNG() throws Exception {

		try {
			setTypePropFormlet();

			RptFuncUtil.initLinkedHashMapFormlet();

			log.info(RptFuncUtil.hashList.size());

			RptFuncUtil.runTheQueries(reportFunc);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

	}

	@Test(groups = { "ReportingFunctionsNG" })
	public void executeProject_EListDropdown_ReportingFunctionNG()
			throws Exception {

		try {
			setEListDropdownFormlet();

			RptFuncUtil.initLinkedHashMapFormlet();

			log.info(RptFuncUtil.hashList.size());

			RptFuncUtil.runTheQueries(reportFunc);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

	}

	public void setTypePropFormlet() throws Exception {

		RptFuncUtil.oneFormletHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		RptFuncUtil.oneFormletHT.put("Type-Properties-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		RptFuncUtil.oneFormletHT.put("Phone Number",
				IRptFuncConst.EfieldDataTypes.stringTP);
		RptFuncUtil.oneFormletHT.put("Postal Code",
				IRptFuncConst.EfieldDataTypes.stringTP);
	}

	public void setEListDropdownFormlet() throws Exception {

		RptFuncUtil.oneFormletHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		RptFuncUtil.oneFormletHT.put("eList Dropdown",
				IRptFuncConst.EfieldDataTypes.formlet);
		RptFuncUtil.oneFormletHT.put("Parent DD",
				IRptFuncConst.EfieldDataTypes.numericTP);
		RptFuncUtil.oneFormletHT.put("Child DD",
				IRptFuncConst.EfieldDataTypes.numericTP);
	}


}
