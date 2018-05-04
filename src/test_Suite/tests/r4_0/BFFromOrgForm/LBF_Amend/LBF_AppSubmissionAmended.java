
package test_Suite.tests.r4_0.BFFromOrgForm.LBF_Amend;

import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_AppSubmissionAmended{

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

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 1, 0, EeFormsIdentifier.org);

				lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);
				
				
		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
	}  
	
	
	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;
		prog = null;
		proj = null;
		lbf = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}


	@Test(groups = { "EFormsNG" })
	public void registerAndSubmitApplication() throws Exception {
		
		
		lbf.registerAndCreateFoProjAndOpenSubmission("Submission");

	}

	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "registerAndSubmitApplication", dataProvider = "formletTypes")
	public void testRowsEntriesOnSubmissionEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	{
				
		lbf.setEFormletsName(eTypes);

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnSubmissionEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnSubmissionEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	
	{
		lbf.setEFormletsName(eTypes);

	}
	
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "verifyFieldsAnswersOnSubmissionEFormNG" })
	public void applicantAmendment() throws Exception {
		try {
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryFieldsLnk);
			
			ClicksUtil.clickLinks(IClicksConst.backToSubmissionsListLnk);
			
			lbf.foProj.setCurrentStepName("A LBF PA Submission");			

			amender = "Applicant:".concat(lbf.foProj.getFoApplicantName("front")).concat("(")
					
					.concat(lbf.foProj.getFoAppNumber()).concat(")");
			
              Assert.assertTrue(AmendmentsUtil.issueApplicantAmendment(
				   
					lbf.foProj.getCurrentStepName(), amender, "This is a Reason"),
					
					"FAIL: Could not issue Applicant Amendment see previous error!");
              
             ClicksUtil.clickLinks(IClicksConst.backToSubmissionsListLnk);
              

		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
			
		}
	}
	
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "applicantAmendment" })
	public void changeDataInOrg() throws Exception{
		
		try
		{
			GeneralUtil.switchToPO();
			
			lbf.updateOrganizationForm(IGeneralConst.primG3_OrgRoot, "LBF-Organization-Source-eLists");
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	
	}
	
	   @Test(groups = { "EFormsNG" }, dependsOnMethods = { "changeDataInOrg" })
	   public void justToSwitch() throws Exception {
			
			GeneralUtil.switchToFO();
			
			ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
		}
		
		
		@Test(groups = { "EFormsNG" }, dependsOnMethods = { "justToSwitch" })
		public void submitForm() throws Exception {

			lbf.submitFOSubmission("A LBF PA Submission", "Submission");
			
			lbf.openFOSubmission("Submission");
			
			
		}

		
		@Test(groups = { "EFormsNG" }, dependsOnMethods = {"submitForm"}, dataProvider = "formletTypes")
		public void verifyFieldsAnswers(ILBFunctionConst.EFormletTypes eTypes) throws Exception
		{
			lbf.setEFormletsName(eTypes);

			lbf.verifyFieldsAnswerAmended();
			
		}
		

		@Test(groups = { "EFormsNG" }, dependsOnMethods = {"verifyFieldsAnswers"}, dataProvider = "formletTypes")
		public void  verifyTestRowsEntriesOnSubmissionEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception
		{
			lbf.setEFormletsName(eTypes);

			lbf.verifyRowsAmended();
		}
		
		
		@Test(groups = { "EFormsNG" }, dependsOnMethods = {"verifyTestRowsEntriesOnSubmissionEFormNG"})
		public void  submitEForm() throws Exception
		
		{
			 ClicksUtil.clickButtons(IClicksConst.nextBtn);
			
			 ClicksUtil.clickButtons(IClicksConst.submitBtn);
			 
			 
		}


		@DataProvider(name = "formletTypes")
		public Object[][] generateFormletTypes(Method method)
		{
			Object[][] result = null;

			if ( method.getName() == "testRowsEntriesOnSubmissionEFormNG"
					
				|| method.getName() == "verifyFieldsAnswersOnSubmissionEFormNG"
				
				|| 	method.getName() == "verifyFieldsAnswers"
				
				||  method.getName()  == "verifyTestRowsEntriesOnSubmissionEFormNG")
				
				
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
	
	
	