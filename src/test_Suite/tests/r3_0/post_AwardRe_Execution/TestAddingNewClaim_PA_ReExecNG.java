/**
 * 
 */
package test_Suite.tests.r3_0.post_AwardRe_Execution;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestAddingNewClaim_PA_ReExecNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front2");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-ReExecution-PA-","");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void createFOProjectNG() throws Exception {
		try {

			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 2, EFormsUtil.createRandomString(5));

			foProj.createFOProject();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG")
	public void submitAward() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			foProj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);
			
			FiltersUtil.filterListByProject(foProj);
			
			foProj.setClaimNumber(1);
			
			foProj.changeClaimCriteria(true, false, true, ILBFunctionConst.lbf_IPASSource[1][0], "PA Submission Schedule Fields LBF");
			
			SubAccessUtil.continuePAWorkflow(foProj, 1, false, true, IFiltersConst.submissionsStatus_Ready_StatusSubView, "1", IFiltersConst.versionNumber_EqualTo);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="submitAward")
	public void requestAmendmentOnAward() throws Exception {
		try {
		
			foProj.initializeStep("Closing-Step");
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			String[] amenders = new String []{"Submission", "PO: ".concat(IPreTestConst.Groups[6][0][0])};
	
			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,true, ""));
			
			List<Hashtable<String,Boolean>> hashList;
			
			hashList = new ArrayList<Hashtable<String,Boolean>>();
			
			hashList.add(0,SubAccessUtil.initializePA_Optional(foProj,true,true));
			
			AmendmentsUtil.configurePASubStepsOptionaRe_Exec(hashList);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="requestAmendmentOnAward")
	public void reSubmitAwardAndClaim() throws Exception {
		
		try {
			
			LBF lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 1, 1, EeFormsIdentifier.standardAgreement);
			
			foProj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			ProjectUtil.openAwardInList(foProj);
			
			foProj.setClaimNumber(0);
			
			LBFunctionUtil.insertDataTo_ELists(lbf);
			
			foProj.setClaimNumber(2);
			
			ClicksUtil.returnFromAnyForm();
			
			foProj.changeClaimCriteria(true, false, true, ILBFunctionConst.lbf_IPASSource[1][0], "PA Submission Schedule Fields LBF");
			
			SubAccessUtil.continuePAWorkflow(foProj, 1, false, false, IFiltersConst.submissionsStatus_InProgress_StatusSubView, "2", IFiltersConst.versionNumber_EqualTo);
			
			SubAccessUtil.continuePAWorkflow(foProj, 2, false, true, IFiltersConst.submissionsStatus_Ready_StatusSubView, "1", IFiltersConst.versionNumber_EqualTo);
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
