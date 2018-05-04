/**
 * 
 */
package test_Suite.tests.r3_3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantTypesUtils;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class MustRunFirst {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	String foUserBeat = "";
	
	String appType = "";
	
	String typeIdent = "";
	
	String groupXML = "Export_3_FO_Groups.xml";	

	String userXML = "Export_7_FO_Users.xml";

	String foppXml = "A-Assoc-PA-FOPP.xml";
	
	String xmlFile = "ExportApplicantTypes.xml";
	
	String eFormsXmlFile = "ExportAssociateApplicantsForms.xml";
	
	String eFormXmlFile = "ExportForm_Assoc-Applicant-Submission-B.xml";
	
	String refTableEForm = "Assoc-Universities-Reference-Table";
	
	String dataFile = "Assoc_Universities_Reference_Table.xml";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			foUserBeat = EFormsUtil.createRandomNumber(3);
			
			appType = "student".concat(foUserBeat);
			
			typeIdent = "G".concat(foUserBeat).concat("_APPLICANT_TYPE_STUDENT");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void importXMLFiles() throws Exception {
		
		try {
			//GeneralUtil.newImportUsersAndGroups(groupXML);
			
			//GeneralUtil.newImportUsersAndGroups(userXML);
			
			Assert.assertTrue(ApplicantTypesUtils.exportApplicantType(xmlFile), "FAIL: could not import Applicant Types");
			
			//Assert.assertTrue(EFormsUtil.exportEForms(eFormsXmlFile));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="importXMLFiles")
	public void addRefTables() throws Exception {
		
		try {
			
			Assert.assertTrue(GeneralUtil.addNewRefTable(refTableEForm, "G3"), "FAIL: To Add new Ref Table");
			
			Assert.assertTrue(GeneralUtil.importDataToRefTable(refTableEForm.replace("-", " "), dataFile, false), "FAIL: to Import Data to Ref Table");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="addRefTables")
	public void importMoreXMLFiles() throws Exception {
		try {
			
			Assert.assertTrue(EFormsUtil.importEForms(eFormXmlFile), "FAIL: to Import eForm");
			
			Assert.assertTrue(GeneralUtil.newImportFOPP(foppXml),"FAIL: to Import Funding Opp!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
