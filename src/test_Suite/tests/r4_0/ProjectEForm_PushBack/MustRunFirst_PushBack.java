package test_Suite.tests.r4_0.ProjectEForm_PushBack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
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
public class MustRunFirst_PushBack
{

	//***********************************************************************
	//* Set up the Global Parameters Name Variables
	//***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp1;
	
	//LBF lbf;
	
	EForm form;
	
	String preFix = "PEF-PA-";
	
	String postFix = "-PushBack-QH";
	
	String eFormName = "Project-Taget";
	
	

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
			
			 fopp1 = new FundingOpportunity("A-",preFix, postFix);
			 
			 form = new EForm("PEF-", eFormName, postFix, false);
			
		    
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = {"ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception
	{	
		try
		{
			fopp1 = null;
			form = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	@Test(groups = { "ProjectsNG" })
	public void configurePEF_FOPP() throws Exception
	{
		try
		{
			
			Assert.assertTrue(FOPPUtil.configureProjectEForm(fopp1, form), "FAIL: Could not configure Project eForm!");			
		}
		
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	
	@Test(groups = {"ProjectsNG"}, dependsOnMethods={"configurePEF_FOPP"})
	public void registerAppToFOPP() throws Exception{
		
		GeneralUtil.switchToFO();

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
		
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp1.getFoppFullName(), "Ouia 1"),"FAIL: Could not Register to FOPP");

		
	}
}
