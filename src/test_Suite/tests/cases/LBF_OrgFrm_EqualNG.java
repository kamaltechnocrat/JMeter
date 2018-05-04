/**
 * 
 */
package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.reporting_Functions.IEtlConst;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFormlets;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlRptFldTypes;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.reporting_Functions.ETL;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.OrgUtil;
import test_Suite.utils.reporting_Functions.EtlUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */
public class LBF_OrgFrm_EqualNG {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formlet;
	EFormField eFormField;	

	LBF lbf;
	
	ETL etl;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			etl = EtlUtil.initializeNonProjectForm(IEtlConst.EetlEFormTypes.org, EetlFormlets.noProp,EetlRptFldTypes.ORG);
			
			etl.setOrgName("G3");

			form = new EForm();

			form.setEFormType(IEFormsConst.organization_FormTypeName);

			form.setEFormSubType(IEFormsConst.organization_FormTypeName);

			form.setEFormTitle("LBF Organization Source eLists");

			form.setEFormId("LBF-Organization-Source-eLists");

			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.org);

			lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

			ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());
			
			lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);
			
			lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardSource[0][0]);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		try {
			
			form = null;
			formlet = null;
			eFormField = null;
			lbf = null;
			etl = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "EFormsNG" })
	public void submitAndOpenApplicantSubmission() throws Exception {

		lbf.returnFromAnyList();

		lbf.initProj(true, true);

		lbf.submitProject("Submission", "Ready");

		lbf.openSubmissionForm();

	}

	@Test(groups = { "EFormsNG" })
	public void fillOrganization() throws Exception {
		try {

			Assert.assertTrue(OrgUtil.selectAndOpenOrgEForm(etl.getOrgName(), form.getEFormId()));

			lbf.fillOrgForm();

			ClicksUtil.clickLinks("Submission Summary");

			ClicksUtil.clickLinks(IClicksConst.orgBackToOrganizationPlanner);

			log.info("Organization Form complete!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
