/**
 * 
 */
package test_Suite.tests.r4_0.BFFromOrgForm.LBF;

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
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author lbarkhouse
 *
 */

@GUITest
@Test(singleThreaded = true)

public class LBF_IPASubmission
{
	//***********************************************************************
	//* Set up the Global Parameters Name Variables
	//***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;

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
	public void submitAwardAndOpenClaims() throws Exception
	{
		lbf.returnFromAnyList();

		lbf.initProj(true, true);

		lbf.submitProject("PO Submission", "Ready");

		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();

		lbf.proj.submitStandardAgreement(ILBFunctionConst.lbf_IPASEqualElists[1][0], 2, true, "");

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj, ILBFunctionConst.lbf_IPASEqualElists[0][0], "Open Projects",
				"Latest Version", "Ready");
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitAwardAndOpenClaims", dataProvider = "formletTypes")
	public void testRowsEntriesOnIPASEForm(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	{
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnIPASEForm", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnIPASEForm(ILBFunctionConst.EFormletTypes eTypes) throws Exception
	{
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes(Method method)
	{
		Object[][] result = null;

		if  ( method.getName() == "testRowsEntriesOnIPASEForm"
			|| method.getName() == "verifyFieldsAnswersOnIPASEForm" )
		{
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid },
					new Object[] { ILBFunctionConst.EFormletTypes.attchments } };
		}

		return result;
	}
}
