package test_Suite.tests.r4_0.projDataStorage;

import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;


/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ProjectEForm_ApplicantSubmission_BringForward{

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	LBF lbf;
	
	Program prog;
	
	FOProject foProj;
	
    Project proj;
    
    FundingOpportunity fopp;
    
    String amender = "";
	
	String comment = "";
	
	String currentStepName = "A LBF PA Submission";
    

	// ----------- End of Global Parameters

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 4, 0, EeFormsIdentifier.proj);

			lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);
				
				
		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
	}  
	
	
	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		lbf = null;
		prog = null;
		foProj = null;
		proj = null;
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	
	
	@Test(groups = { "ProjectsNG" }) 
	public void registerAndSubmitApplication() throws Exception 
	
	{
	
		Assert.assertTrue(lbf.createFOProjectNFillProjectEForm("A LBF PA Submission", "Submission "));
	
	}
	
	
	@Test(groups = { "ProjectsNG" },  dependsOnMethods = "registerAndSubmitApplication")
     public void openFOSubmission() throws Exception
     
     {
		GeneralUtil.switchToFO();
    	 
    	Assert.assertTrue( FrontOfficeUtil.openFO_SubmissionWithStepFullName(lbf.foProj, "A LBF PA Submission Equal eLists"));
    	 
    	Assert.assertTrue(ClicksUtil.clickButtons("Submit"));
    	 
     }
	
	
	
	@Test(groups = { "ProjectsNG" },dependsOnMethods = "openFOSubmission")
	
	public void selectAwardFromFrontOffice() throws Exception
	
	{
		
		lbf.submitFOSubmission("A LBF PA Submission B", "Award");
		
	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "selectAwardFromFrontOffice")
	public void submitAward() throws Exception
	{
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(lbf.foProj.submitStandardAgreement(ILBFunctionConst.lbf_IPASEqualElists[1][0], 1, true, "")); 

	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"submitAward" })
	public void openClaimInFO() throws Exception
	{
		GeneralUtil.switchToFO();
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(lbf.foProj, "A LBF PA Initial Claim Equal eLists"));

         
	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "openClaimInFO", dataProvider = "formletTypes")
	public void testRowsEntriesOnSubmissionEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	{
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}
	

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testRowsEntriesOnSubmissionEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnSubmissionEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	{
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}
	

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes(Method method)
	{
		Object[][] result = null;

		if ( method.getName() == "testRowsEntriesOnSubmissionEFormNG"
			|| method.getName() == "verifyFieldsAnswersOnSubmissionEFormNG" )
		{
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid },
					
		            };
		}

		return result;
	}
	
	
}