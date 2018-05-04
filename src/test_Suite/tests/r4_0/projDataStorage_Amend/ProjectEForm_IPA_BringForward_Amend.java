package test_Suite.tests.r4_0.projDataStorage_Amend;

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
import test_Suite.constants.preTest.IPreTestConst;
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
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ProjectEForm_IPA_BringForward_Amend{

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
					EProjectType.pre_Award, 1, 0, EeFormsIdentifier.proj);

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
	public void submitAndOpenAwardForm() throws Exception
	{
	
		lbf.createFOProjectAndOpenSubmission("A LBF PA Submission","PO Submission");
		
		lbf.submitFOSubmission("A LBF PA Submission B", "Award");
		
		GeneralUtil.switchToPO();
		
		lbf.foProj.assignOfficers(new String[][] {
   				{ IPreTestConst.Groups[0][0][0],
   					IPreTestConst.Groups[0][1][0] }});
   		
		
		lbf.fillProjectEForm(foProj, "Standard Award", "Open Projects" );
		
		lbf.foProj.submitStandardAgreement(ILBFunctionConst.lbf_IPASEqualElists[1][0], 1, true, "");
		
	}

	@Test(groups = { "ProjectsNG" }, dependsOnMethods =  {"submitAndOpenAwardForm" })
	public void openClaimInFO() throws Exception
	{
		GeneralUtil.switchToFO();
		
		FrontOfficeUtil.openFO_SubmissionWithStepFullName(lbf.foProj, "A LBF PA Initial Claim Equal eLists");
 
	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "openClaimInFO")
	public void openFormInMyAssignedSubmissionsNSubmit() throws Exception
	{
		GeneralUtil.switchToPO();
	
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(
				lbf.foProj,ILBFunctionConst.lbf_IPASEqualElists[0][0], "Open Projects");
	
	}

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "openFormInMyAssignedSubmissionsNSubmit")
	public void requestAmendmentFromPO()
	
	{
		try {
			
			GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(lbf.foProj,
							IGeneralConst.pa_AwardCrit[1][0], "Open Projects"),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);
			

			Assert.assertTrue(
					AmendmentsUtil.fillAmendments(
							new String[] {lbf.foProj.getProjFullName(),"","",
						                  dd,"Test Issue Amendment with Amend In Place",
									      "This a Comment" }, lbf.foProj),
					               "FAIL: Could not fill out amendments!");
			 

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
		
	}
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = { "requestAmendmentFromPO" })
	public void changeDataInOrg() throws Exception{
		
		try
		{
			GeneralUtil.switchToPO();
			
			lbf.updateProjectEForm();
		}
		catch (Exception e)
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	
	}
	
	 @Test(groups = { "ProjectsNG" }, dependsOnMethods = { "changeDataInOrg" })
	   public void justToSwitch() throws Exception {
			
			GeneralUtil.switchToFO();
			
			ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
		}
	
	 @Test(groups = { "ProjectsNG" }, dependsOnMethods = { "justToSwitch" })
		public void submitForm() throws Exception {
		 
		 lbf.foProj.setClaimNumber(1);
			
		 FrontOfficeUtil.openFO_SubmissionWithStepFullName(lbf.foProj,"A LBF PA Initial Claim Equal eLists");
		 
		}

		
		@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"submitForm"}, dataProvider = "formletTypes")
		public void verifyFieldsAnswers(ILBFunctionConst.EFormletTypes eTypes) throws Exception
		{
			lbf.setEFormletsName(eTypes);

			lbf.verifyFieldsAnswerAmended();
			
		}
	
		
		@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"verifyFieldsAnswers"}, dataProvider = "formletTypes")
		public void  verifyTestRowsEntriesOnSubmissionEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception
		{
			lbf.setEFormletsName(eTypes);

			lbf.verifyRowsAmended();
		}
		
		
		@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"verifyTestRowsEntriesOnSubmissionEFormNG"})
		public void  submitEForm() throws Exception
		
		{
			 ClicksUtil.clickButtons(IClicksConst.nextBtn);
			
			 ClicksUtil.clickButtons(IClicksConst.submitBtn);
			 
			 
		}


		@DataProvider(name = "formletTypes")
		public Object[][] generateFormletTypes(Method method)
		{
			Object[][] result = null;

			if ( 
					method.getName() == "verifyFieldsAnswers"
				
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
	