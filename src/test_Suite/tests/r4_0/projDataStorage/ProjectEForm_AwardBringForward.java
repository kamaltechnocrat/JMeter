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
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;


/**
 * @author sfatima
 *
 */


@GUITest
@Test(singleThreaded = true)
public class ProjectEForm_AwardBringForward{

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
					EProjectType.pre_Award, 4, 0, EeFormsIdentifier.proj);

		    lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);
				
				
		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
	}  
	
	
	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() 
	
	{
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
		
		lbf.submitFOSubmission("A LBF PA Submission B", "Standard Agreement");
		
		GeneralUtil.switchToPO();
		
		lbf.foProj.assignOfficers(new String[][] {
   				{ IPreTestConst.Groups[0][0][0],
   					IPreTestConst.Groups[0][1][0] }});
   		
		
		lbf.fillProjectEForm(lbf.foProj, "Standard Agreement", "Open Projects" );

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardEqualELists[0][0]);
		
	}
	

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "submitAndOpenAwardForm", dataProvider = "formletTypes")
	public void testRowsEntriesOnAwardEForm(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	
	{
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}
	

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testRowsEntriesOnAwardEForm", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnAwardEForm(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	
	{
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}
	
	

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes(Method method)
	
	{
		Object[][] result = null;

		if ( method.getName() == "testRowsEntriesOnAwardEForm"
			|| method.getName() == "verifyFieldsAnswersOnAwardEForm" )
		{
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid },};
		}

		return result;
	}
	
	
}
	