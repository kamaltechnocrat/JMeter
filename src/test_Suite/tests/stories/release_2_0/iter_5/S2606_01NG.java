/**
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_5;

import java.util.ArrayList;
import static watij.finders.SymbolFactory.id;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IConfigConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.*;
import watij.runtime.ie.IE;
import test_Suite.lib.users.Applicants;
import test_Suite.lib.workflow.POProjects;

/**
 * @author apankov
 *
 */
@Test(singleThreaded = true)
public class S2606_01NG {
	private static Log log = LogFactory.getLog(S2606_01NG.class);
	IE ie;
	private String programName              = IGeneralConst.baseLetters[0]+IGeneralConst.pa_ProgPrefix+IGeneralConst.gnrl_ProgName;
	private String programApplicants   [][] = {
											 	{"Applicant15", "Appl15", "G3"},
											 	{"Applicant16", "Appl16", "G3-1"}
											  };
	private String poProjects           =  "POProject1-";
	private boolean   assocOrgsToAppl;
	@BeforeClass  
	public void setUp() {

	} 
	
	/**
	 * Parent Method
	 */
	@Test(groups = { "Iter_25" })
	public void s2606_01NG() throws Exception 
	{	
		openPO();
		GeneralUtil.setApplicantWorkspace();
		assocOrgsToAppl = true;
		setFrontOffice(); //checkbox is checked
		createApplicantsAndProjects();
		verifyApplicants();
		verifyProjects();
		//reset assocOrgsToAppl variable to false and retest
		assocOrgsToAppl = false;
		setFrontOffice();//checkbox is un-checked
		verifyApplicants();
		verifyProjects();
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
			GeneralUtil.loginPONew("shak", "a11");
		}
		catch (Exception e) {	
			
			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
	
	/**
	 * Required Record to be able to create or modify any existing Program
	 *
	 */
	/*
	private void createApproveProgram()
	{
		try
		{
			log.info("Creating Program Approvel Program");
			ClicksUtil.clickLinks(IClicksConst.openProgramApprovalProcess);
			if(!ie.link(IProgramsConst.OPEN_PROG_PLANNER + " - " + programApproveId).exists())
			{
				ArrayList apprProgs = new ArrayList();
				ProgramApprove pApprove = new ProgramApprove();
				pApprove.setProgApproveId(programApproveId);
				pApprove.setProgApproveForm(IUAPConst.programForm[0]);
				pApprove.setProgApproveStartDate(GeneralUtil.getTodayDate());
				pApprove.setProgApproveEndDate(GeneralUtil.getNextYear());
				pApprove.setProgApproveOfficer(IPreTestConst.adminProgPOOfficer);
				pApprove.setProgApproveStatus(IProgramsConst.PROG_STATUS_INACTIVE);
				pApprove.setProgApprovePrOrg(orgIdent[1]);
				pApprove.setProgApproveOrgAccess(IGeneralConst.ORG_ACCESS_PUBLIC);
				pApprove.setProgApproveNames(programApproveNames);
				pApprove.setProgApproveSteps(createProgramSteps());
				apprProgs.add(pApprove);
				
				FillFormsUtil.fillProgApprovForm(apprProgs, 
						FillFormsUtil.ECreateUpdate.objectCreate.ordinal());
			}
			else
				log.info("Link to Approve Program Process already exists: " + programApproveId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createApproveProgram() " + ex.getMessage());
		}
	}*/
	
	/*
	private ArrayList createProgramSteps() throws Exception
	{
		ArrayList programSteps = new ArrayList();
		//Create Submit step
		ProgramSteps stepSubmit = new ProgramSteps();
		
		//Create Approve step
		ProgramSteps stepApprove = new ProgramSteps();
		
		//Create Award Step
		ProgramSteps stepAwward  = new ProgramSteps();
		
		programSteps.add(stepSubmit);
		programSteps.add(stepApprove);
		programSteps.add(stepAwward);
		return programSteps;
	}
	*/
	
	/**
	 * Clone existing Program if required and Assign Parameters to it
	 * Program's attributes need to be modified in order to create Projects in PO
	 */
	/*
	private void assignPrograms()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openProgramsListLnk);
		
			for(int i=0; i<programNamesToClone.length; i++)
			{
				if(ie.link(text, programNames[i]).exists())
					ClicksUtil.clickLinks(programNames[i]);
				else if(ie.link(text, programNamesToClone[i]).exists())
				{
					ClicksUtil.clickImageByAlt("Clone " + programNamesToClone[i]);
					//Find a row with Program Status='Inactive' and click link to edit program
					int rowIndex = TablesUtil.getRowIndex(ITablesConst.programsTableId, IGeneralConst.statusInactive);
					if(rowIndex > -1)
					{
						ie.table(id, ITablesConst.programsTableId).row(rowIndex).cell(3).link(0).click();
						String altText = "Open " + programNamesToClone[i] + " Clone Admin [1]";
						log.info("ALT TEXT: " + altText);
						try
						{
							ClicksUtil.clickImageByAlt(altText);

						}
						catch(Exception ex)
						{
							ClicksUtil.clickImageByAlt(altText + " "); //in case here is an extra space
						}
						finally
						{
							ie.textField(id, IProgramsConst.PROG_IDENT).set(programNames[i]);
							ie.selectList(name, IProgramsConst.PROG_FORM_SELECT).select(IUAPConst.programForm[0]);
							ie.selectList(id, IProgramsConst.PUBL_FORM_SELECT).select(IUAPConst.publicationForm[0]);
							ie.selectList(id, IProgramsConst.PROG_MANAGER_MENU).select(IPreTestConst.adminProgPOOfficer);
							ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(IGeneralConst.ORG_ACCESS_PUBLIC);

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
								if(ie.textField(id, "/" + j + ":" + IProgramsConst.PROG_TEXT).exists())
									ie.textField(id, "/" + j + ":" + IProgramsConst.PROG_TEXT).set(programNames[i]);		
							}
							ClicksUtil.clickButtons(IClicksConst.saveBtn);
							ie.selectList(id, IProgramsConst.PROG_STATUS_MENU).select(IProgramsConst.PROG_STATUS_ACTIVE);
							ClicksUtil.clickButtons(IClicksConst.saveBtn);
							ClicksUtil.clickButtons(IClicksConst.backBtn);
							modifyProgramAdmin(programNamesToClone[i]);
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in assignPrograms() " + ex.getMessage());
		}
	}
	*/
	
	/*
	private void modifyProgramAdmin(String progName)
	{
		try
		{
			//Assign Admin user to reles in Program
			ClicksUtil.clickImageByAlt(IProgramsConst.MANAGE_PROG_STAFF);
			ClicksUtil.clickButtons(IClicksConst.m2MAllForBtn);
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			try
			{
				ClicksUtil.clickImageByAlt(IProgramsConst.VIEW_STEP_DETAILS + " " + progName + 
						" (" + IGeneralConst.gnrl_SubmissionA[0][2]+ ")");
				log.info("Tree expanded state");
			}
			catch(Exception e)
			{
				ClicksUtil.clickImage("/nav-plus-line-last.gif/");
				log.info("Tree collapsed state");
			}
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in assignPrograms() " + ex.getMessage());
		}
	}
	*/
	
	private void createApplicantsAndProjects()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
			ArrayList<Applicants> arrApplicants = new ArrayList<Applicants>();
			for(int i=0; i<programApplicants.length; i++)
			{
				Applicants applicant = new Applicants();
				applicant.setApplicantName(programApplicants[i][0]);
				applicant.setApplicantNumber(programApplicants[i][1]);
				applicant.setApplicantType(IEFormsConst.organization_FormTypeName);
				applicant.setApplicantOrg(programApplicants[i][2]);
				ArrayList<POProjects> applicantProjects = new ArrayList<POProjects>();
				POProjects project = new POProjects();
				project.setApplName(applicant.getApplicantName());
				project.setApplProgram(programName);
				project.setProjectName(poProjects + i);
				applicantProjects.add(project);
				applicant.setArrProjects(applicantProjects);
				arrApplicants.add(applicant);

			}
			if(arrApplicants.size() > 0)
				HtmlFormsUtil.fillApplicantsForm(arrApplicants, 
						HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());
			
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createApplicantsAndProjects() " + ex.getMessage());
		}
	}
	/**
	 * Method to set 'Associate Organizations to Applicants' to check
	 *
	 */
	private void setFrontOffice()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFrontOfficeConfiguration);
			if(assocOrgsToAppl == true)
				ie.checkbox(id, IConfigConst.associateOrgToAppId).set();
			else
				ie.checkbox(id, IConfigConst.associateOrgToAppId).clear();
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in setFrontOffice() " + ex.getMessage());
		}
	}
	
	private void verifyApplicants()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
			for(int i=0; i<programApplicants.length; i++)
			{
				String applicantName = programApplicants[i][0];
				String applicantOrg  = programApplicants[i][2];
				ie.textField(id, "/0" + IFiltersConst.txtFilterItem + "/").set(applicantName);
				ClicksUtil.clickButtons(IClicksConst.filterBtn);
				ClicksUtil.clickLinks(applicantName);
				if(assocOrgsToAppl == true)
				{
					if(ie.selectList(id, IApplicantsConst.APPLICANT_ORG).exists() &&
							GeneralUtil.getSelectedItemValueInDropdwonById(IApplicantsConst.APPLICANT_ORG).equals(applicantOrg))
						log.info("TEST PASSED for Applicant: " + applicantName + " and Org. = " + applicantOrg);
					else
						log.warn("TEST FAILED for Applicant: " + applicantName + " and Org. = " + applicantOrg);
				}
				else
				{
					if(ie.selectList(id, IApplicantsConst.APPLICANT_ORG).exists())
						log.info("TEST FAILED control must not be visible under this configuration");
					else
						log.warn("TEST PASSED control is invisible");
				}
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			}
			ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in verifyApplicants() " + ex.getMessage());
		}
	}
	
	private void verifyProjects()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);
			for(int i=0; i<programApplicants.length; i++)
			{
				String applicantProjectName = poProjects + i;
				String applicantOrg  = programApplicants[i][2];
				ie.textField(id, "/0" + IFiltersConst.txtFilterItem + "/").set(applicantProjectName);
				ClicksUtil.clickButtons(IClicksConst.filterBtn);
				ClicksUtil.clickLinks(applicantProjectName);
				if(assocOrgsToAppl == true)
				{
					if(GeneralUtil.getSelectedItemValueInDropdwonById(IProjectsConst.projectOrganization).equals(applicantOrg))
						log.info("TEST PASSED for Project: " + applicantProjectName + " and Org. = " + applicantOrg + "  " + ie.selectList(id, IProjectsConst.projectOrganization).getAllContents().toString());
					else
						log.info("TEST FAILED for Project: " + applicantProjectName + " and Org. = " + applicantOrg);
				}
				else
				{
					applicantOrg = "G3";
					if(GeneralUtil.getSelectedItemValueInDropdwonById(IProjectsConst.projectOrganization).equals(applicantOrg) &&
							ie.selectList(id, IProjectsConst.projectOrganization).getAllContents().size() == 1)
						log.info("TEST PASSED  " + ie.selectList(id, IProjectsConst.projectOrganization).getAllContents().toString());
					else
						log.warn("TEST FAILED: more than one item is available to select " + ie.selectList(id, IProjectsConst.projectOrganization).getAllContents().toString());
				}
				ClicksUtil.clickButtons(IClicksConst.backBtn);
			}
			ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in verifyProjects() " + ex.getMessage());
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
