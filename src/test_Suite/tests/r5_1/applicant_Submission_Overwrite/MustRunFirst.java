package test_Suite.tests.r5_1.applicant_Submission_Overwrite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author SFatima
 *
 */
public class MustRunFirst
{

	//***********************************************************************
	//* Set up the Global Parameters Name Variables
	//***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() throws Exception
	{
		try
		{
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.logInSuper();
			
			fopp = new FundingOpportunity(IFoppConst.fopp_Letter, IFoppConst.bfFOPP_prefix, IFoppConst.fopp_postFix);

			
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = {"WorkflowNG" }, alwaysRun=true)
	public void tearDown() throws Exception
	{	
		try
		{
			fopp = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	@Test(groups = {"WorkflowNG"})
	public void registerAppToFOPP() throws Exception{
		
		GeneralUtil.switchToFO();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk));
		
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), "Ouia 1"), "Fail:Couldn't register to funding oppurtunity ");

		
	}
}
