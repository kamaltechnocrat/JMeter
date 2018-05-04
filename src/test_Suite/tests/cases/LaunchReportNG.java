package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.cases.Reports;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class LaunchReportNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends LaunchReportNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Reports report;

	String[] userAcess = { "Super", "staff" };
	
	//String[] userAcess = { "Super"};

	static int letterIndex = 0;

	static String letterString = "";

	// ---------------- End of Global Params ---------------

	@BeforeClass(groups = { "ReportsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		//GeneralUtil.loginPO();
	}
	
	@AfterClass(groups = { "ReportsNG" })
	public void tearDown() throws Exception {
		
		report = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "ReportsNG" })
	public void launchReportNG() throws Exception {

		try {

			/****************************************************************
			 * Create Report
			 **************************************************************/

			report = new Reports();

			report.initReprots();

			report.setReportLetter(IGeneralConst.baseLetters[letterIndex]);

			// Comment out the below Line if no letter needed

			// letterString = report.getReportLetter() + "-";

			createWithParamsReports();
			createNoParamsReports();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 
	}

	private void createWithParamsReports() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openReportsList);

		for (int i = 0; i < IReportsConst.withParamsReportsNames.length; i++) {

			report.setReportName(letterString
					+ IReportsConst.withParamsReportsNames[i]);
			report.setReportTitle(letterString
					+ IReportsConst.withParamsReportsTitles[i]);

			report.setReportOrgName("G3");
			report.setReportOrgAccess("Public");

			report.addNewReport(i, IReportsConst.withParamsReportsNames);

			report.setAccessUser(userAcess);

			report
					.setParamName(new String[] { IReportsConst.reportParamName[i] });
			report
					.setParamType(new String[] { IReportsConst.withParamsReportsType[i] });
			report
					.setParamLabels(new String[] { IReportsConst.reportParamLabels[i] });

			report.addReportParamsAndAccess(true, true);

		}
	}

	private void createNoParamsReports() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openReportsList);

		for (int i = 0; i < IReportsConst.noParamsReportsNames.length; i++) {

			report.setReportName(letterString
					+ IReportsConst.noParamsReportsNames[i]);
			report.setReportTitle(letterString
					+ IReportsConst.noParamsReportsTitles[i]);

			report.setReportOrgName("G3");
			report.setReportOrgAccess("Public");

			report.addNewReport(i, IReportsConst.noParamsReportsNames);

			report.setAccessUser(userAcess);

			report.addReportParamsAndAccess(true, false);

		}
	}
}
