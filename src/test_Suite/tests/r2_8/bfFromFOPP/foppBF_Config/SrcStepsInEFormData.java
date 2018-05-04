package test_Suite.tests.r2_8.bfFromFOPP.foppBF_Config;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IStepsConst;
import test_Suite.constants.workflow.IBf_FoppConst.EPostFix;
import test_Suite.constants.workflow.IStepsConst.EStepParams;
import test_Suite.lib.workflow.FOPPSteps;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.BfFoppUtil;
import test_Suite.utils.workflow.FOPPStepsUtil;
import test_Suite.utils.workflow.FOPPUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SrcStepsInEFormData {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends SrcStepsInEFormData> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity srcFopp;
	
	FundingOpportunity trgtFopp;
	
	FOPPSteps step;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		srcFopp = BfFoppUtil.initFopp(EPostFix.SRC);
		
		trgtFopp = BfFoppUtil.initializeLessStepsFopp(EPostFix.TRGT);
		trgtFopp.setBfFromFOPPIdent(srcFopp.getFoppFullIdent());
		
		trgtFopp.createNewFundingOpp();
		trgtFopp.addNewStep();
		
		step = trgtFopp.getFoppSteps().get(IStepsConst.EStepsType.APPROVAL);
		
		trgtFopp.expandAnObject(FOPPStepsUtil.getStepParam(EStepParams.IDENT, step));
		
		trgtFopp.openStepManagementDetails(trgtFopp.getFullStepIdent(), "e.Form Data");		
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcFopp = null;
		trgtFopp = null;
		step = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG",dataProvider="stepNames")
	public void sourceFOPPEFormsDataAvailabilityNG(String stepName, boolean expected) throws Exception {
		
		FOPPUtil.initEFormData(step, srcFopp, new String[] {stepName}, new String[] {});
		
		String string = step.getStepEFormDataLst().get(0);
		
		Assert.assertTrue(trgtFopp.findInM2M("main:stepFormData:formDataForm:availableForms", string), "FAIL: eForm not Available: " + string);
	}
	
	@Test(groups="EFormsNG",dataProvider="stepNames")
	public void targetFOPPEFormsDataAvailabilityNG(String stepName, boolean expected) throws Exception {
		
		FOPPUtil.initEFormData(step, srcFopp, new String[] {}, new String[] {stepName});
		
		String string = step.getStepEFormDataLst().get(0);
		
		Assert.assertTrue(trgtFopp.findInM2M("main:stepFormData:formDataForm:availableForms", string), "FAIL: eForm not Available: " + string);
	}
	
	@DataProvider(name="stepNames")
	public Object[][] generateSourceSteps(Method method){
		
		Object[][] result = null;
		
		if(method.getName().equals("sourceFOPPEFormsDataAvailabilityNG")) {
			
			result =  new Object[][] {
						new Object[] {"Submission", true},
						new Object[] {"PO-Submission", true},
						new Object[] {"Approval",true},
						new Object[] {"Review", true},
						new Object[] {"Award", true},
					};
		}else if(method.getName().equals("targetFOPPEFormsDataAvailabilityNG")) {
			
			result =  new Object[][] {
						new Object[] {"Submission", true},
						new Object[] {"Review", true}
					};
		}
		
		return result;
	}

}
