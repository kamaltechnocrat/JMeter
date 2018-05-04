package test_Suite.tests.preTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantTypesUtils;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class PreTest_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	// All the XML Files in The Zip File Configured with All 3 Locales
	String zipFile = "g3_Watij_preTest_install.zip";
	
	String zipFile2 = "Assoc_Regoinal_Data_preTest.zip";

	String orgXML = "ExportOrganizationHierarchy.xml";
	
	String appTypeXML = "ExportApplicantTypes.xml";
	
	String refTableEForm = "Assoc-Universities-Reference-Table";
	
	String refTableDataXML = "Assoc_Universities_Reference_Table.xml";
	
	String groupXML = "ExportRegionsGroups.xml";
	
	String reviewersGrpsXML = "Export4ExternalReviewers.xml";

	String foppXml = "A-Regional-FO-Prog-Assignement.xml";
	
	String ExtFoppXml = "A-External-Reviewer-FO-FOPP.xml";
	
	String bfFoppXml = "A-BF-FOPP-FOPP-Awarded-SRC-Target.xml";

	// #####--------------- END OF GLOBAL PARAMS VARS
	// -----------------------------

	@BeforeClass(groups = { "PreTestNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
	}
	
	@AfterClass(groups = { "PreTestNG" }, alwaysRun=true)
	public void tearDown() throws Exception {		
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "PreTestNG" })
	public void preTest_02NG() throws Exception {			
			
		configureAndImportDataInPO();
		
		creatingFrontOfficeApplicantInPO();
		
		GeneralUtil.switchToFO();

		associateRegistrantWithApplicant();		
	}
	
	private void configureAndImportDataInPO() throws Exception {

		log.debug("Configure System Settings");
		GeneralUtil.configureSystemSetting();
		log.debug("Configuring System Setting OK");
		
		GeneralUtil.setLicenseManager();

		log.warn("importing zip File...");
		GeneralUtil.newImportZipFile(zipFile);
		log.warn("importing zip File OK");
		
		log.debug("changing Admin Email Address...");
		UsersAndGroupsUtil.changeUserEmail(IPreTestConst.AdminUsrName, IPreTestConst.adminEmailAddress);
		log.debug("changed Admin Email Address...");
		
		log.debug("setting applicant workspace");
		GeneralUtil.setApplicantWorkspace();
		log.debug("set applicant workspace OK");
		
		/* 
		 * User Profile Feature has been removed from
		 * G3 unless it's enabled in Limited Access Features.properties
		 *
		 */
		
		log.debug("setting user profile");
		GeneralUtil.setUserProfile("User-Profile",false);
		log.debug("set applicant profile OK");  
		
		log.debug("changing Applicnat Type Auto Gen Number...");
		GeneralUtil.setAutoGenAppNumber("Yes");			

		log.debug("importing Org Xml File...");
		GeneralUtil.newImportOrganizations(orgXML);
		log.debug("importing Org Xml OK");
		
		log.debug("importing Associates App type");
		ApplicantTypesUtils.exportApplicantType(appTypeXML);
		log.debug("importing Associate App Type is OK");	
		
		log.warn("importing Associates Ref Table");
		Assert.assertTrue(GeneralUtil.addNewRefTable(refTableEForm, "G3"), "FAIL: To Add new Ref Table");
		log.warn("importing is OK!");
		
		log.warn("importing Data to Associate Reference Table!");
		Assert.assertTrue(GeneralUtil.importDataToRefTable(refTableEForm.replace("-", " "), refTableDataXML, false), "FAIL: to Import Data to Ref Table");
		log.warn("importing Data is OK!");

		log.warn("importing Other zip File...");
		GeneralUtil.newImportZipFile(zipFile2);
		log.warn("importing zip File OK");
		
		//this is for Linux only

		log.debug("importing Other xml File...");
		GeneralUtil.newImportFOPP(bfFoppXml);
		log.debug("importing xml File OK");
	}

	private void creatingFrontOfficeApplicantInPO() throws Exception {

		FOUsers foUser = new FOUsers();
		foUser.setFoApplicants(IPreTestConst.FrontUsers[0]);
		foUser.setFoRegistrants(IPreTestConst.FrontUsers[1]);
		foUser.setFoOrgName(IPreTestConst.FO_OrgShortName);

		foUser.setStartIndex(2);
		foUser.setEndIndex(3);

		Assert.assertTrue(foUser.createPOApplicants(), "FAIL: Could not create an Applicant User!");

		Assert.assertTrue(foUser.addExistantRegistrantsInPO(), "FAIL: Could not Add Registrant!");
	}

	private void associateRegistrantWithApplicant() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);

		log.info("opened applicants link");
		
		int orgBeat = 1;
			
		Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.addNewApplicant_ImgAlt), "FAIL: Could not Add new Applicant");

		log.info("clicked new applicant");

		FrontOfficeUtil.setApplicant(IPreTestConst.FO_OrgShortName
				+ Integer.toString(orgBeat), IPreTestConst.FO_OrgNumber
				+ Integer.toString(orgBeat), true);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		log.info("saved applicant");

		ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);
		
		Assert.assertTrue(FrontOfficeUtil
				.openRegistrantsList(IPreTestConst.FO_OrgShortName
						+ Integer.toString(1)),	"FAIL:Applicant Could not be Found!");

		log.info("clicking new registrant");

		Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.associateRegistrant_ImgAlt),"FAIL: Could not Associate existant Registrant!");
		
		FrontOfficeUtil.associateRigestrant(IPreTestConst.FrontUsers[1][4]
						+ Integer.toString(4), IPreTestConst.FrontUsers[1][0]
						+ Integer.toString(4)
						+ IPreTestConst.FrontUsers[1][3]);

		ClicksUtil.clickButtons(IClicksConst.addRegistrantBtn);

		log.info("clicked add registrant button");

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
	}
}
