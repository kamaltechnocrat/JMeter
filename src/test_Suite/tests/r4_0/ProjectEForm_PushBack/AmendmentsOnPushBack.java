package test_Suite.tests.r4_0.ProjectEForm_PushBack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.StepsUtil;

/**
 * @author sfatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AmendmentsOnPushBack {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	LBF lbf;
	
	FOProject foProj;
	
	 String amender = "";

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToFO();
			
			GeneralUtil.loginAnyFO("front");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.projData_PA_Prefix, IFoppConst.projData_pushBack_Postfix);
			
			foProj = new FOProject(fopp, "", true, 3, "-EvaluateProjectListTesting");

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
	
	
	@Test(groups = { "ProjectsNG" })
	public void createNewProjectAndSubmit() throws Exception {
		
		foProj.createFOProjectNewNew();
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Submission"));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl("Enter Any Text", "Submit"));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Submission B"));
		
		Assert.assertTrue(StepsUtil.insertTo_NoPropListFormlet(ILBFunctionConst.lbf_NoProperties_Fields_eList[0]));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));		
	}
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"createNewProjectAndSubmit"})
	public void reviewAndSubmit() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.loginReviewer("1");
		
		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Review", "Ready", "Min And Max Propreties Fields PEF", false, "1", ""));
		
		Assert.assertTrue(StepsUtil.insertTo_MinMaxPropListFormlet(ILBFunctionConst.lbf_MinMaxProperties_Fields_eList[0]));
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"reviewAndSubmit"})
	public void awardAndSubmit() throws Exception {
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(foProj, "Standard Award", "Type Propreties Fields PEF"));
		
		Assert.assertTrue(StepsUtil.insertTo_TypePropListFormlet(ILBFunctionConst.lbf_TypeProperties_Fields_eList[0]));
		
		Assert.assertTrue(StepsUtil.insertTo_SubScheduleListFormlet1("PEF-Post Award Submission-PushBack",ILBFunctionConst.lbf_SubSchedules_Fields_eList[0]));
		
	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"awardAndSubmit"})
	public void initialClaimSubmit() throws Exception{
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Initial Claim PushBack QH"));
		
		Assert.assertTrue(StepsUtil.insertTo_DataGridListFormlet(ILBFunctionConst.lbf_DataGrid_Fields_eList[0]));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	}
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"initialClaimSubmit"})
	public void verifyDataInProjectList() throws Exception{
		
        GeneralUtil.switchToPO();
		
        Assert.assertTrue(ProjectUtil.openPOProjectList(foProj,"Post Award", "Open Projects"));
		
		Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
		
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - Scoring Propreties Sub Fields PEF"));
		                            
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtlAndReturn("Enter Number(1-100)", "78"));
	 	
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - Min And Max Propreties Fields PEF"));
		     
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl("Password", "abc"));
		
		Assert.assertTrue(ClicksUtil.clickButtons("Save"));
		
		Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
		
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - Attachment List Fields PEF"));
		
		DocumentsUtil.upload_Attachments("Picture", "How to...", "Picture.jpg");
		
		DocumentsUtil.upload_Attachments("Word", "How to..", "Word.doc");
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.completeBtn));
		
		
	}
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "verifyDataInProjectList")
	 public void amendmentOnSecondStep() throws Exception{
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
	
	      foProj.setCurrentStepName("A PEF PA Submission B");
	
	      amender = "Applicant:".concat(foProj.getFoApplicantName("front")).concat("(")
			
			.concat(foProj.getFoAppNumber()).concat(")");
	
          Assert.assertTrue(AmendmentsUtil.issueApplicantAmendment(
		   
			foProj.getCurrentStepName(), amender, "This is a Reason"),
			
			"FAIL: Could not issue Applicant Amendment see previous error!");
      
          ClicksUtil.clickLinks(IClicksConst.backToSubmissionsListLnk);
      
	
       }
	

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "amendmentOnSecondStep")
	 public void updateSecondStepData() throws Exception{
		 
		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Submission B"));
		 
        Assert.assertTrue(StepsUtil.insertTo_NoPropListFormlet(ILBFunctionConst.lbf_NoProperties_Fields_eList[1]));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));	
		
	 }
	
	
	 @Test(groups = { "ProjectsNG" }, dependsOnMethods = "updateSecondStepData")
	   public void openProjectEForm_Verify_NoPropFields() throws Exception{
		   
		GeneralUtil.switchToPO();
			
		Assert.assertTrue(ProjectUtil.openPOProjectList(foProj,"Review", "Open Projects"));
		
		Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
		
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - No Propreties Fields PEF"));
		
		Assert.assertTrue(StepsUtil.verify_NO_Prop_Fields_Answers(ILBFunctionConst.lbf_NoProperties_Fields_eList[1]));
		   
	   }
	
	
	   @Test(groups = { "ProjectsNG" }, dependsOnMethods = "openProjectEForm_Verify_NoPropFields")
	   public void updateReviewStep() throws Exception{
		   
		   GeneralUtil.switchToPOOnly();
			
			GeneralUtil.loginReviewer("1");
			
			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Review", "In Progress", "Min And Max Propreties Fields PEF", false, "2", ""));
			
			Assert.assertTrue(StepsUtil.insertTo_MinMaxPropListFormlet(ILBFunctionConst.lbf_MinMaxProperties_Fields_eList[1]));
			
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));
		   
	   }
	   
	   
	   @Test(groups = { "ProjectsNG" }, dependsOnMethods = "updateReviewStep")
	   public void openProjectEForm_Verify_Min_Max_Prop_Fields() throws Exception{
		   
		GeneralUtil.switchToPO();
			
		Assert.assertTrue(ProjectUtil.openPOProjectList(foProj,"Standard Award", "Open Projects"));
		
		Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
		
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - Min And Max Propreties Fields PEF"));

	   }
	   
       @Test(groups = { "ProjectsNG" }, dependsOnMethods = "openProjectEForm_Verify_Min_Max_Prop_Fields")
	   public void updateAwardStep() throws Exception{
			   
			GeneralUtil.switchToPO();
				
			Assert.assertTrue(ProjectUtil.openPOAwardFormletInList_New(foProj, "Standard Award", "Type Propreties Fields PEF", false, "2", ""));
				
			Assert.assertTrue(StepsUtil.insertTo_TypePropListFormlet(ILBFunctionConst.lbf_TypeProperties_Fields_eList[1]));
				
			Assert.assertTrue(StepsUtil.insertTo_SubScheduleListFormlet1("PEF-Post Award Submission-PushBack",ILBFunctionConst.lbf_SubSchedules_Fields_eList[1]));
			   
		   }
       
       @Test(groups = { "ProjectsNG" }, dependsOnMethods = "updateAwardStep")
	   public void openProjectEForm_Verify_Type_Prop_Fields() throws Exception{
		   
		GeneralUtil.switchToPO();
			
		Assert.assertTrue(ProjectUtil.openPOProjectList(foProj,"Post Award", "Open Projects"));
		
		Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
		
		Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - Type Propreties Fields PEF"));
		
		Assert.assertTrue(StepsUtil.verify_TypePropFieldsAnswers(ILBFunctionConst.lbf_TypeProperties_Fields_eList[1]));
		   
	   }
      
       
       
	   
	   @Test(groups = { "ProjectsNG" }, dependsOnMethods = "openProjectEForm_Verify_Type_Prop_Fields")
		   public void amendOnInitialClaim() throws Exception{
			   
			   GeneralUtil.navigateToFO();
				
				GeneralUtil.loginAnyFO("front");
				
			   foProj.setCurrentStepName("A PEF PA Initial Claim PushBack QH");
				
			      amender = "Applicant:".concat(foProj.getFoApplicantName("front")).concat("(")
					
					.concat(foProj.getFoAppNumber()).concat(")");
			
		          Assert.assertTrue(AmendmentsUtil.issueApplicantAmendment(
				   
					foProj.getCurrentStepName(), amender, "This is a Reason"),
					
					"FAIL: Could not issue Applicant Amendment see previous error!");
		      
		          ClicksUtil.clickLinks(IClicksConst.backToSubmissionsListLnk);
		      
			   
		   }
	   
		   @Test(groups = { "ProjectsNG" }, dependsOnMethods = "amendOnInitialClaim")
			 public void updateInitialClaimData() throws Exception{
			   
			   GeneralUtil.switchToFO();
				 
			   Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A PEF PA Initial Claim PushBack QH"));
				 
		        Assert.assertTrue(StepsUtil.insertTo_DataGridListFormlet(ILBFunctionConst.lbf_DataGrid_Fields_eList[1]));
				
				Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));		
				
			 }
		   
		   
		   @Test(groups = { "ProjectsNG" }, dependsOnMethods = "updateInitialClaimData")
		   public void openProjectEForm_Verify_DataGrid_Fields() throws Exception{
			   
			GeneralUtil.switchToPO();
				
			Assert.assertTrue(ProjectUtil.openPOProjectList(foProj,"Post Award", "Open Projects"));
			
			Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"));
			
			Assert.assertTrue(ClicksUtil.clickLinksByTitle("View formlet - Data Grid Fields PEF"));
			
			Assert.assertTrue(StepsUtil.verify_DataGridListFieldsAnswers(ILBFunctionConst.lbf_DataGrid_Fields_eList[1]));
			   
		   }
		   
		   
		   
		   
		  
		   
		   
	   
    	 
  }
	
	
	
	
	
	
	

	
	