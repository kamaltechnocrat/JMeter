package test_Suite.tests.r4_0.projDataStorage_Amend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author SFatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ProjectEForm_MustRunFirst_Amended
{

	//***********************************************************************
	//* Set up the Global Parameters Name Variables
	//***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;
	
	FundingOpportunity fopp1;
	
	EForm form;
	
	String preFix = "LBF-";
	
	String postFix = "-eLists";
	
	String eFormName = "Project-Source";
	
	boolean isNew;
	
	

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() throws Exception
	{
		try
		{
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.logInSuper();

			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
				EProjectType.pre_Award, 4, 0, EeFormsIdentifier.proj);

			lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);
			
			fopp1 = new FundingOpportunity("A","-LBF-PA", "-eLists");
			
			form = new EForm(preFix, eFormName, postFix, false);
			
		
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception
	{	
		try
		{
			lbf = null;
			form = null;
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

	/**
	 * Test cases
	 */
	@Test(groups = { "ProjectsNG" })
	public void configureLBF_FOPP() throws Exception
	{
		try
		{
			Assert.assertTrue(ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent()));
			
			Assert.assertTrue(FOPPUtil.configureProjectEForm(fopp1, form));
			
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
	
	@Test(groups = { "ProjectsNG" },  dependsOnMethods = "configureLBF_FOPP")
	public void registerAppToFOPP() throws Exception{
		
		GeneralUtil.switchToFO();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk));
		
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(lbf.fopp.getProgFullName(), "Ouia 1"));
		
	}	
	
}
