/**
 * Class to navigate to the organization details and then to select a form and to populate the fields.
 */
package test_Suite.tests.r4_0.BFFromOrgForm.LBF_Amend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProgramUtil;

/**
 * @author SFatima
 *
 */
public class LBF_RunFirstAmended
{

	//***********************************************************************
	//* Set up the Global Parameters Name Variables
	//***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;
	
	FundingOpportunity fopp1;
	
	
	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception
	{
		try
		{
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.logInSuper();

			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					
				EProjectType.pre_Award, 4, 0, EeFormsIdentifier.org);

			lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);
			

		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() throws Exception
	{	
		try
		{
			
			lbf = null;
			fopp1 = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "EFormsNG" })
	public void configureFOPP_AmendmentStatus() throws Exception{
	
	    Assert.assertTrue(lbf.fopp.openFundingOppPlanner(),"FAIL: could not open FOPP Planner!");
	
		lbf.initializeStep("Submission");
		
		String currentStepIdent = "A-LBF-PA-Submission";
		
		lbf.fopp.setCurrentStepIdent(currentStepIdent );
	
	   Assert.assertTrue(ProgramUtil.openStepDetails(ProgramUtil.findStepInProgPlanner(lbf.fopp.getCurrentStepIdent(), 0)), "FAIL: Could not open Step Details");
	   
	  lbf.setAmendmentStatus( IEFormsConst.applicantAmendment_Ttl,IGeneralConst.allowAmendNow);
	   
	
		
	}

	/**
	 * Test cases
	 */
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "configureFOPP_AmendmentStatus")
	public void configureLBF_FOPP() throws Exception
	{
		try
		{
			
			lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);

			lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardSource[0][0]);

			lbf.setEProjType(EProjectType.post_Award);

			lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "configureLBF_FOPP")
	public void configureOrganizationFormNG() throws Exception
	{
		try
		{
			lbf.fillOrganizationForm(IGeneralConst.primG3_OrgRoot, "LBF-Organization-Source-eLists");
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	
	
}
