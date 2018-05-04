/**
 * Steps:
 * 1. Open G3 PO, log in as admin user
 * 2. Navigate to Organizations
 * 3. Select "G3" in the list of Organizations
 * 4. Click Image "Add Sub-Org"
 * 5. Create Sub Organization for G3
 * 6. Associate Objects (users, forms, lookups etc. with the new Organization)
 * 7. Check from Organization planner screen if all Objects have 'child' organization
 *     as their Primary Organization
 * 8. Create New Form type of Organization and associate it with 'child' organization
 *    and continue with the data created on steps 1-8 with another test cases.   
 */
package test_Suite.tests.stories.release_2_0.iter_5;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static watij.finders.SymbolFactory.*;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.cases.IOrgConst;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.constants.cases.IGeneralConst;
//import test_Suite.lib.cases.Lookups;
import test_Suite.lib.cases.Organizations;
//import test_Suite.lib.cases.Reports;
import test_Suite.lib.eForms.EForm;
/**
 * @author apankov
 *
 */
@Test(singleThreaded = true)
public class S2577_01NG {
	private static Log log = LogFactory.getLog(S2577_01NG.class);
	IE ie;
	
	//Test case setup variables (modify if necessary)
	private String frmId              = "A-OrgForm-A";
	private String frmTitles         []  = {frmId, frmId, frmId};
	
	private String orgIdent       []       = {"G3", "G3-1"};
	private String orgFullNames   [][]     = {{"Grantium G3","Grantium G3","Grantium G3"},
											  {"SubOrg G3-1","SubOrg G3-1","SubOrg G3-1"}};
	private String orgShortNames  [][]     = {{"G3","G3","G3"}, 
			                                  {"G3-1","G3-1","G3-1"}};
	
	private String reportIdentifier        =  "Programs_Listing_Report";
	
	//private String reportName              =  "Programs_Listing_Report.rpt";
	
	String lookupConstant                  =  "aaaa"; 
	String lookupName                      =  "aaaa";
	
	String lookupValues 			[][][] = 	{
												{{"a-1", "a-1", "a-1", "1"},
												{"a-2", "a-2", "a-2", "1"},
												{"a-3", "a-3", "a-3", "0"}},
								 
												{{"b-1", "b-1", "b-1", "1"},
												{"b-2", "b-2", "b-2", "1"},
												{"b-3", "b-3", "b-3", "0"}}
									};
	
	private String formName             = "Applicant Form Clone Admin [1]";
	
	private String programName       = IGeneralConst.baseLetters[0]+IGeneralConst.gnrl_ProgPrefix+
														IGeneralConst.gnrl_ProgName;
		
	
	@BeforeClass  
	public void setUp() {

	} 
	/**
	 * Parent method
	 */
	@Test(groups = { "Iter_25" })
	public void s2577_01NG() throws Exception
	{
		openPO();
		createOrgForm();
		createOrgTree();
		
		assignProgram();
		createOrgNodes();
		assessOrgNodes();

		assignOrgForm();
		verifyOrgFormAssignment();
		closeBrowser();
	}

	/**
	 * Open Browser and open G3 PO
	 * Log in as admin user
	 */
	private void openPO()
	{
		try
		{
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
		}
		catch (Exception e) {	
			
			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
	
	/**
	 * Method to Associate a Form with Organization
	 * Note: form type to be selected = 'Organization'
	 */
	private void createOrgForm()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			ie.textField(id, "/"+IFiltersConst.txtFilterItem+"/").set(frmId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(!ie.link(text, frmId).exists())
			{
				ClicksUtil.clickImage(IClicksConst.newImg);
				EForm frm = new EForm();
				frm.setEFormId(frmId);
				frm.setEFormType(IEFormsConst.organization_FormTypeName);
				frm.setPrimaryOrg(IGeneralConst.primG3_OrgRoot);
				frm.setOrgAccess(IGeneralConst.org_Access_Public);
				frm.setEFormTitles(frmTitles);
				frm.createOrUpdateForm();
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createOrgForms() " + ex.getMessage());
		}
	}
	
	/**
	 * Creates Sub org from Parent Organization
	 *
	 */
	private void createOrgTree()
	{
		try
		{
			//Navigate to Organizations screen
			ClicksUtil.clickLinks(IClicksConst.openOrganizationsList);
			ArrayList<Organizations> orgLevel1Arr = new ArrayList<Organizations>();
			//Create child Organization
			Organizations orgChild = new Organizations();
			orgChild.setOrgId(orgIdent[1]);
			orgChild.setOrgForm(frmId);
			orgChild.setInheritParentChanges(false);
			orgChild.setOrgType(IOrgConst.ORG_TYPE_DEFAULT);
			orgChild.setOrgStatus(IGeneralConst.statusActive);
			orgChild.setOrgDefLoc(IPreTestConst.Language);
			orgChild.setOrgOfficer(IPreTestConst.adminProgPOOfficer);
			orgChild.setOrgFullNames(orgFullNames[1]);
			orgChild.setOrgShortName(orgShortNames[1]);
			orgChild.setParentOrg(orgIdent[0]);
			orgChild.setUpdateOrCreate(HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());
			orgLevel1Arr.add(orgChild);

			Organizations orgRoot = new Organizations();
			orgRoot.setOrgId(orgIdent[0]);
			orgRoot.setOrgOfficer(IPreTestConst.adminProgPOOfficer);
			orgRoot.setOrgFullNames(orgFullNames[0]);
			orgRoot.setOrgShortName(orgShortNames[0]);
			orgRoot.setChildOrganizations(orgLevel1Arr);
			orgRoot.setUpdateOrCreate(HtmlFormsUtil.ECreateUpdate.objectUpdate.ordinal());
			HtmlFormsUtil.manageOrg(orgRoot);
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in createOrg() " + ex.getMessage());
		}
	}
	/**
	 * Create or Modify Objects to Have Child Organization as Primary Organization
	 *
	 */
	private void createOrgNodes()
	{
		try
		{
			//createLookups();
			//createReports();
			//assignForms();
			assignProgram();
			
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in createOrgNodes() " + ex.getMessage());
		}
	}
	
	private void assessOrgNodes()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);
			ClicksUtil.clickLinks(orgIdent[1]);
			//check Programs
			checkObjects(IGeneralConst.objPrograms, programName, IOrgConst.ORG_PROGRAMS_LIST);
			//check Forms
			checkObjects(IGeneralConst.objForms, formName, IOrgConst.ORG_FORMS_LIST);
			//check Reports
			checkObjects(IGeneralConst.objReports, reportIdentifier, IOrgConst.ORG_REPORTS_LIST);
			//check Lookups
			checkObjects(IGeneralConst.objLookups, lookupName, IOrgConst.ORG_LOOKUPS_LIST);

		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in assessOrgNodes() " + ex.getMessage());
		}
	}
	

	
	//*******************************************************************************
	//******************Creating/Modifying Organization's Objects********************
	//*******************************************************************************
	
	/*private void createLookups()
	{
		try
		{
			Lookups lookup;
			for(int i=0; i<lookupValues.length; i++)
			{
				ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 		
				log.info("Navigating to Lookups screen");
				lookup = new Lookups();
				lookup.setLookupIdent(lookupConstant);
				lookup.setLookupName(lookupName);
				lookup.setLookupPrimeOrg(orgIdent[1]);
				lookup.setLookupOrgAccess(IGeneralConst.org_Access_Public);
				lookup.setLookupEntries(lookupValues[i]);
				lookup.createNewLookup();
			}
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in createLookup() " + ex.getMessage());
		}
	}*/
	
	/*private void createReports()
	{
		try
		{	
			String reportViewerUrl  = null;


				String [] reportTitles = {"Report", "Report", "Report"};
				String [] reportUsers  = {"Super"};
				Reports report;
				ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
				report = new Reports();
				report.initReprots();
				reportViewerUrl = report.getReportViewerFullPath();
				report.setReportIdent(reportIdentifier);
				report.setReportOrgName(orgIdent[1]);
				report.setReportOrgAccess(IGeneralConst.org_Access_Public);
				report.setReportName(reportName);
				report.setReportTitles(reportTitles);
				report.setAccessUser(reportUsers);
				report.addOneNewReport();

			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createReports() " + ex.getMessage());
		}
	}*/
	
	/*private void assignForms()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			if(ie.link(text, IEFormsConst.applicantWorkspace_FormTypeName).exists())
				ClicksUtil.clickImageByAlt("Clone " + IEFormsConst.applicantWorkspace_FormTypeName);

			if(ie.link(text, "Applicant Form Clone Admin [1]").exists())
			{
				ClicksUtil.clickLinks("Applicant Form Clone Admin [1]");
				ClicksUtil.clickImageByAlt("View Form Applicant Form Clone Admin [1]");
				ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(orgIdent[1]);
				ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(IGeneralConst.org_Access_Public);
				if(ie.button(IClicksConst.publishFormBtn).exists())
					ClicksUtil.clickButtons(IClicksConst.publishFormBtn);
				ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			}
			if ((ie.link(text, IEFormsConst.approval_FormTypeName2).exists()))
				ClicksUtil.clickImageByAlt("Clone " + IEFormsConst.approval_FormTypeName2);
			if(ie.link(text, "Approval Form Clone Admin [1]").exists())
			{
				ClicksUtil.clickLinks("Approval Form Clone Admin [1]");
				ClicksUtil.clickImageByAlt("View Form Approval Form Clone Admin [1]");
				ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(orgIdent[1]);
				ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(IGeneralConst.org_Access_Shared);
				if(ie.button(IClicksConst.publishFormBtn).exists())
					ClicksUtil.clickButtons(IClicksConst.publishFormBtn);
				ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			}
			ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in assignForms() " + ex.getMessage());
		}
	}*/
	
	private void assignProgram()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programName);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programName).exists())
			{
				ClicksUtil.clickLinks(programName);
				ClicksUtil.clickImageByAlt("/" + IClicksConst.openBtn + " " + programName + "/"); //ERROR: extra space in HTML here

				ie.selectList(name, IProgramsConst.fundingOpp_PROG_FORM_SELECT).select("Program Form");
				ie.selectList(id, IProgramsConst.fundingOpp_PUBL_FORM_SELECT).select("Publication Form");
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_MANAGER_MENU).select(IPreTestConst.adminProgPOOfficer);
				ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(IGeneralConst.org_Access_Public);

				new Thread(new Runnable() { 
					public void run() { 
						try { 
								ie = IEUtil.getActiveIE();
								ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(orgIdent[1]);
 
			    			} 
							catch (Exception e) { 
							} 
						} 
				}).start();
	
				GeneralUtil.takeANap(2.0);
				ie.sendKeys("Microsoft Internet Explorer", " ", false); 
							
				for(int j=0; j<orgFullNames[1].length; j++)
				{
					if(ie.textField(id, "/" + j + ":" + "programText/").exists())
						ie.textField(id, "/" + j + ":" + "programText/").set(programName);
									
				}
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ie.selectList(id, "/programStatusMenu/").select("Active");
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in assignPrograms() " + ex.getMessage());
		}
	}
	
	//*******************************************************************************
	//******************Checking for Organization's Objects**************************
	//*******************************************************************************
	
	private void checkObjects(String objectType, String objName, String ddlOrgConst)
	{
		try
		{
			ClicksUtil.clickImageByAlt(IClicksConst.orgViewOrg + " " + objectType);

				String selectName = "/" + ddlOrgConst + IOrgConst.DD_ROW + 0 + ":" + IOrgConst.ORG_ACCESS + "/";
				if((ie.selectList(id, selectName).exists()) && 
					(GeneralUtil.getSelectedItemValueInDropdwonById(selectName).equals(orgIdent[1])))
					log.info("TEST PASSED: " + objName + " has Pr. Org. = " + orgIdent[1]);
				else
					log.warn("TEST FAILED: entry is missing or incorrect for " + objName + 
							" that has Pr. Org. = " + orgIdent[1]);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in checkPrograms() " + ex.getMessage());
		}
	}

	/**
	 * Assign Organization Form to Child Organization
	 *
	 */
	private void assignOrgForm()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);
			ClicksUtil.clickLinks(orgIdent[1]);
			
			String altTag = IClicksConst.orgViewOrg + " " + orgIdent[1];
			log.info("ALT TAG = " + altTag);
				ClicksUtil.clickImageByAlt(altTag);
				//Create new Organization object
				Organizations org = new Organizations();
				org.setOrgForm(frmId);
				//org.selectOrgForm(); //Select Organization Form
				org = null;
		}
		catch(Exception ex)
		{
			log.debug("ERROR in assignOrgForm() " + ex.getMessage());
		}
	}
	
	private void verifyOrgFormAssignment()
	{
		try
		{
			ie = IEUtil.getActiveIE();
			ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);
			ClicksUtil.clickLinks(orgIdent[1]);

			//here should be a portion of code to verify that Org Form node is OK, currently ALT TAG is missing
		}
		catch(Exception ex)
		{
			log.debug("ERROR in verifyOrgFormAssignment() " + ex.getMessage());
		}
	}
	/**
	 * Close PO application and close web browser
	 *
	 */
	private void closeBrowser()
	{
		try
		{
			ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}
}
