/**
 * 
 */
package test_Suite.tests.r3_4.amendCategory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.LookupUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AddMoreAmendmentCategoriesNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	String[] values = new String[] {"QA","Test", "Another Value", "Just One More"};

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Gnrl-PA-","");
			
			foProj = new FOProject(fopp,"Amend-Category-", true,1,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void AddNewCategories() throws Exception {
		try {
			
			Assert.assertTrue(LookupUtil.addLookupValues(values, IAmendmentsConst.amendCategory_LookupName), "Could Not Add more Values!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="AddNewCategories")
	public void createAndSubmiProj() throws Exception {
		try {
			
			GeneralUtil.switchToFO();
			
			Assert.assertTrue(FrontOfficeUtil.createAndSubmitStandardAward(fopp, foProj, 1), "FAIL: could not complete workflow!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="createAndSubmiProj")
	public void TestNewAddCategories() throws Exception {
		try {

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(AmendmentsUtil
					.issueAmendment(new String[] {
							foProj.getProjFullName(), IGeneralConst.gnrl_SubmissionA[0][0],
							IPreTestConst.Groups[0][1][0], dd,
							"Test Amendment Categories",
							"This a Comment" }, foProj));
			
			Assert.assertTrue(AmendmentsUtil.openAmendmentListAndFilter(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Amendments List and Filter!");
			
			Assert.assertTrue(AmendmentsUtil.openAmendmentDetailsNew("", IFiltersConst.evaluateSubmissions_InProgress_StatusSubView), "Could not Open Amendment Details");
			
			for (String string : values) {
				
				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.amendmentDetails_Category_DropDwnFld_Id, string), "FIAL: could not find a Value: ".concat(string));
				
			}
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

}
