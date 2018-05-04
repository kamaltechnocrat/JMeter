package test_Suite.lib.workflow;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.*;

import java.util.Hashtable;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import watij.dialogs.AlertDialog;
import watij.elements.HtmlElement;
import watij.elements.HtmlElements;
import watij.elements.Span;
import watij.elements.Spans;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.constants.workflow.*;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.workflow.*;


@SuppressWarnings("rawtypes")
public class Program {

	private String progPreFix = null;
	
	private String projPreFix = null;

	private char progPortal = ' ';

	private String progName = null;

	private String progIdent = null;

	private String progFullIdent = null;

	private String progFullName = null;
	private String[] progFullNames;
	private String progLetter = null;

	private boolean progForm;

	private boolean applicantProfile;

	private boolean newProgram;

	private boolean publicationForm;

	private Integer stepsIndex = -1;

	private Integer addStepIndex = 4;

	private Integer subStepsIndex = -1;

	private String progOfficers[] = null;

	private String progAdmin[] = null;
	
	private Hashtable progStaff;
	
	private ArrayList<ProgramSteps> progSteps;
	
	private String progPostfix = "";

	private String startDate = null;

	private String endDate = null;

	private String programFormName = null;

	private String publicationFormName = null;

	private String programOfficer = null;

	private String progStatus;

	private String progFundOpportunityProcess;

	private String progApprovalStatus;

	private String progRegistrOpens;

	private String progRegistrOpenHH;

	private String progRegistrOpenMM;

	private String progRegistrCloses;

	private String progRegistrCloseHH;

	private String progRegistrCloseMM;

	private String currentStepName = "";

	private String fullStepName;

	private String currentStepIdent = "";

	private String fullStepIdent;

	private String tempFullStepIdent;

	private String tempFullStepName;

	private String dataFromStepIdent = "";

	private String skipToStepIdent = "";

	private String stepPreFix;

	private String stepOfficer;

	private boolean creationFOProjDisable = false;

	private boolean completeAFRequired = false;

	protected String primaryOrg = null;

	protected String orgAccess = null;

	private int createOrUpdate;

	private static Log log = LogFactory.getLog(Program.class);

	// ------------- Constructors ------------------------------------

	public Program(String progPrefix, char progType, boolean progForm,
			boolean newProgram, boolean publicationForm) {

		progPreFix = progPrefix;

		progPortal = progType;

		this.progForm = progForm;

		this.newProgram = newProgram;

		this.publicationForm = publicationForm;

		setStepOfficer(IPreTestConst.AppGroupName);

		if (progType == 'p' || progType == 'P') {
			this.progIdent = progPreFix + IGeneralConst.gnrl_ProgName;
		} else {
			this.progIdent = progPreFix + IGeneralConst.gnrl_FO_ProgName;
		}

	}

	public Program() {

	}

	public Program(String foppPrefix, String foppPostFix, String foppName,
			char projType, boolean progForm, String adminFormIdent,
			boolean newProgram, boolean publicationForm, String pubFormIdent) {

		this.progPreFix = foppPrefix;

		this.progPortal = projType;

		this.progForm = progForm;

		this.newProgram = newProgram;

		this.publicationForm = publicationForm;

		this.setStepOfficer(IPreTestConst.AppGroupName);

		this.progIdent = this.progPreFix + foppName;

		this.progPostfix = foppPostFix;

		this.programFormName = adminFormIdent;

		this.publicationFormName = pubFormIdent;

	}

	public void initProgram() throws Exception {
		
		this.setProjPreFix(IGeneralConst.gnrl_ProjPrefix);

		if (newProgram) {
			this.progLetter = FiltersUtil.getNewBaseLetter(ITablesConst.fundingOpp_programsTableId,IFiltersConst.gpa_FundingOppIdent_Lbl, this.progIdent
					+ this.progPostfix,IFiltersConst.exact);
		} else {
			this.progLetter = FiltersUtil.getUsedBaseLetter(ITablesConst.fundingOpp_programsTableId,IFiltersConst.gpa_FundingOppIdent_Lbl,this.progIdent
					+ this.progPostfix,IFiltersConst.exact);
		}

		if (this.progForm && this.programFormName == null) {
			this.setProgramFormName("Basic Program Admin");
		}

		if (this.publicationForm && this.publicationFormName == null) {
			this.setPublicationFormName("Publication Form");
		}

		this.progFullIdent = progLetter + this.progIdent + this.progPostfix;

		this.progFullName = this.progFullIdent.replace('-', ' ');

		this.startDate = GeneralUtil.getTodayDate();

		this.endDate = GeneralUtil.getNextYear();

		this.setProgramOfficer(IPreTestConst.ProgPOfficer);
	}

	public void initializeProgram(String baseLetter) throws Exception {

		
		this.progLetter = baseLetter;
		
		this.setProjPreFix(IGeneralConst.gnrl_ProjPrefix);

		if (this.progForm && this.programFormName == null) {
			this.setProgramFormName("Basic Program Admin");
		}

		if (this.publicationForm && this.publicationFormName == null) {
			this.setPublicationFormName("Publication Form");
		}

		this.progFullIdent = progLetter + this.progIdent + this.progPostfix;

		this.progFullName = this.progFullIdent.replace('-', ' ');

		this.startDate = GeneralUtil.getTodayDate();

		this.endDate = GeneralUtil.getNextYear();

		this.setProgramOfficer(IPreTestConst.ProgPOfficer);
	}

	// --------------------- End of Constractor -----------------------

	// --------------- Getters and Setters ----------------------------

	public boolean isNewProgram() {
		return newProgram;
	}

	public boolean isProgForm() {
		return progForm;
	}

	public boolean isPublicationForm() {
		return publicationForm;
	}

	public void setProgApprovalStatus(String progApprovalStatus) {
		this.progApprovalStatus = progApprovalStatus;
	}

	/**
	 * @return the stepPreFix
	 */
	public String getStepPreFix() {
		return stepPreFix;
	}

	/**
	 * @param stepPreFix
	 *            the stepPreFix to set
	 */
	public void setStepPreFix(String stepPreFix) {
		this.stepPreFix = stepPreFix;
	}

	/**
	 * @return the fullStepName
	 */
	public String getFullStepName() {
		return fullStepName;
	}

	/**
	 * @param fullStepName
	 *            the fullStepName to set
	 */
	public void setFullStepName(String fullStepName) {
		this.fullStepName = fullStepName;
	}

	/**
	 * @return the progIdent
	 */
	public String getProgIdent() {
		return progIdent;
	}

	/**
	 * @param progIdent
	 *            the progIdent to set
	 */
	public void setProgIdent(String progIdent) {
		this.progIdent = progIdent;
	}

	/**
	 * @return the progFullIdent
	 */
	public String getProgFullIdent() {
		return progFullIdent;
	}

	/**
	 * @param progFullIdent
	 *            the progFullIdent to set
	 */
	public void setProgFullIdent(String progFullIdent) {
		this.progFullIdent = progFullIdent;
	}

	/**
	 * @return the currentStepIdent
	 */
	public String getCurrentStepIdent() {
		return currentStepIdent;
	}

	/**
	 * @param currentStepIdent
	 *            the currentStepIdent to set
	 */
	public void setCurrentStepIdent(String currentStepIdent) {
		this.currentStepIdent = currentStepIdent;
	}

	/**
	 * @return the fullStepIdent
	 */
	public String getFullStepIdent() {
		return fullStepIdent;
	}

	/**
	 * @param fullStepIdent
	 *            the fullStepIdent to set
	 */
	public void setFullStepIdent(String fullStepIdent) {
		this.fullStepIdent = fullStepIdent;
	}

	/**
	 * @return the dataFromStepIdent
	 */
	public String getDataFromStepIdent() {
		return dataFromStepIdent;
	}

	/**
	 * @param dataFromStepIdent
	 *            the dataFromStepIdent to set
	 */
	public void setDataFromStepIdent(String dataFromStepIdent) {
		this.dataFromStepIdent = dataFromStepIdent;
	}

	/**
	 * @return the skipToStepIdent
	 */
	public String getSkipToStepIdent() {
		return skipToStepIdent;
	}

	/**
	 * @param skipToStepIdent
	 *            the skipToStepIdent to set
	 */
	public void setSkipToStepIdent(String skipToStepIdent) {
		this.skipToStepIdent = skipToStepIdent;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStepOfficer() {
		return stepOfficer;
	}

	public void setStepOfficer(String stepOfficer) {
		this.stepOfficer = stepOfficer;
	}

	public String getProgPostfix() {
		return progPostfix;
	}

	public void setProgPostfix(String progPostfix) {
		this.progPostfix = progPostfix;
	}

	public String[] getProgAdmin() {
		return progAdmin;
	}

	public void setProgAdmin(String[] progAdmin) {
		this.progAdmin = progAdmin;
	}

	public String[] getProgOfficers() {
		return progOfficers;
	}

	public void setProgOfficers(String[] progOfficers) {
		this.progOfficers = progOfficers;
	}

	public String getProgLetter() {
		return progLetter;
	}

	public void setProgLetter(String progLetter) {
		this.progLetter = progLetter;
	}

	public char getProgPortal() {
		return progPortal;
	}

	public void setProgPortal(char progPortal) {
		this.progPortal = progPortal;
	}

	public String getProgPreFix() {
		return progPreFix;
	}

	public void setProgPreFix(String progPreFix) {
		this.progPreFix = progPreFix;
	}

	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	public String getProgFullName() {
		return progFullName;
	}

	public void setProgFullName(String progFullName) {
		this.progFullName = progFullName;
	}

	public boolean isCompleteAFRequired() {
		return completeAFRequired;
	}

	public void setCompleteAFRequired(boolean completeAFRequired) {
		this.completeAFRequired = completeAFRequired;
	}

	public boolean isCreationFOProjDisable() {
		return creationFOProjDisable;
	}

	public void setCreationFOProjDisable(boolean creationFOProjDisable) {
		this.creationFOProjDisable = creationFOProjDisable;
	}

	public String getProgramOfficer() {
		return programOfficer;
	}

	public void setProgramOfficer(String programOfficer) {
		this.programOfficer = programOfficer;
	}

	public String getOrgAccess() {
		return orgAccess;
	}

	public void setOrgAccess(String orgAccess) {
		this.orgAccess = orgAccess;
	}

	public String getPrimaryOrg() {
		return primaryOrg;
	}

	public void setPrimaryOrg(String primaryOrg) {
		this.primaryOrg = primaryOrg;
	}

	public String getProgramFormName() {
		return programFormName;
	}

	public void setProgramFormName(String programFormName) {
		this.programFormName = programFormName;
	}

	public String getPublicationFormName() {
		return publicationFormName;
	}

	public void setPublicationFormName(String publicationFormName) {
		this.publicationFormName = publicationFormName;
	}

	public String getProgStatus() {
		return progStatus;
	}

	public void setProgStatus(String progStatus) {
		this.progStatus = progStatus;
	}

	public String getProgApprovalStatus() {
		return progApprovalStatus;
	}

	public String getProgFundOpportunityProcess() {
		return progFundOpportunityProcess;
	}

	public void setProgFundOpportunityProcess(String progFundOpportunityProcess) {
		this.progFundOpportunityProcess = progFundOpportunityProcess;
	}

	public String getProgRegistrCloseHH() {
		return progRegistrCloseHH;
	}

	public void setProgRegistrCloseHH(String progRegistrCloseHH) {
		this.progRegistrCloseHH = progRegistrCloseHH;
	}

	public String getProgRegistrCloseMM() {
		return progRegistrCloseMM;
	}

	public void setProgRegistrCloseMM(String progRegistrCloseMM) {
		this.progRegistrCloseMM = progRegistrCloseMM;
	}

	public String getProgRegistrCloses() {
		return progRegistrCloses;
	}

	public void setProgRegistrCloses(String progRegistrCloses) {
		this.progRegistrCloses = progRegistrCloses;
	}

	public String getProgRegistrOpenHH() {
		return progRegistrOpenHH;
	}

	public void setProgRegistrOpenHH(String progRegistrOpenHH) {
		this.progRegistrOpenHH = progRegistrOpenHH;
	}

	public void setNewProgram(boolean newProgram) {
		this.newProgram = newProgram;
	}

	public String getProgRegistrOpenMM() {
		return progRegistrOpenMM;
	}

	public void setProgRegistrOpenMM(String progRegistrOpenMM) {
		this.progRegistrOpenMM = progRegistrOpenMM;
	}

	public void setProgForm(boolean progForm) {
		this.progForm = progForm;
	}

	public String getProgRegistrOpens() {
		return progRegistrOpens;
	}

	public void setProgRegistrOpens(String progRegistrOpens) {
		this.progRegistrOpens = progRegistrOpens;
	}

	public String[] getProgFullNames() {
		return progFullNames;
	}

	public void setProgFullNames(String[] progFullNames) {
		this.progFullNames = progFullNames;
	}

	public void setPublicationForm(boolean publicationForm) {
		this.publicationForm = publicationForm;
	}

	public int getCreateOrUpdate() {
		return createOrUpdate;
	}

	public void setCreateOrUpdate(int createOrUpdate) {
		this.createOrUpdate = createOrUpdate;
	}

	public Hashtable getProgStaff() {
		return progStaff;
	}

	public void setProgStaff(Hashtable progStaff) {
		this.progStaff = progStaff;
	}

	public ArrayList<ProgramSteps> getProgSteps() {
		return progSteps;
	}

	public void setProgSteps(ArrayList<ProgramSteps> progSteps) {
		this.progSteps = progSteps;
	}

	public String getCurrentStepName() {
		return currentStepName;
	}

	public void setCurrentStepName(String currentStepName) {
		this.currentStepName = currentStepName;
	}

	/**
	 * @return the tempFullStepIdent
	 */
	public String getTempFullStepIdent() {
		return tempFullStepIdent;
	}

	/**
	 * @param tempFullStepIdent
	 *            the tempFullStepIdent to set
	 */
	public void setTempFullStepIdent(String tempFullStepIdent) {
		this.tempFullStepIdent = tempFullStepIdent;
	}

	/**
	 * @return the tempFullStepName
	 */
	public String getTempFullStepName() {
		return tempFullStepName;
	}

	/**
	 * @param tempFullStepName
	 *            the tempFullStepName to set
	 */
	public void setTempFullStepName(String tempFullStepName) {
		this.tempFullStepName = tempFullStepName;
	}

	// ------------------ End of Getters and Setters ---------------------------

	// ------------------ Public Methods ---------------------------------------

	public boolean openFundingOppPlanner() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.gpa_FundingOppIdent_Lbl,
				this.getProgFullIdent(), "Exact");

		if (TablesUtil.findInTable(ITablesConst.fundingOpp_programsTableId,
				this.getProgFullIdent())) {

			ie.form(id, ITablesConst.fundingOpp_programsFormId).body(id,
					ITablesConst.fundingOpp_programsTableId).link(text,
					this.getProgFullIdent()).click();
			
			return true;
		}

		return false;
	}

	public void createProgram() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.textField(name, IProgramsConst.fundingOpp_prog_Ident).set(
				progFullIdent);
		ie.textField(name, IProgramsConst.fundingOpp_Proj_Num_Template).set(
				this.getProjPreFix());

		ie.textField(name, IProgramsConst.fundingOpp_PROG_START_DATE).set(
				this.startDate);
		ie.textField(name, IProgramsConst.fundingOpp_PROG_END_DATE).set(
				this.endDate);

		ie.selectList(name, IProgramsConst.fundingOpp_PROG_MANAGER_MENU)
				.select(getProgramOfficer());

		ie.textField(name, IProgramsConst.fundingOpp_PROG_REG_START_DATE).set(
				this.startDate);
		ie.selectList(name, IProgramsConst.fundingOpp_PROG_REG_START_HH)
				.select("00");
		ie.selectList(name, IProgramsConst.fundingOpp_PROG_REG_END_MM).select(
				"01");
		ie.textField(name, IProgramsConst.fundingOpp_PROG_REG_END_DATE).set(
				this.endDate);

		ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_0).set(
				this.progFullName);

		if (ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_1)
				.exists()) {
			ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_1).set(
					this.progFullName);
		}

		if (ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_2)
				.exists()) {
			ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_2).set(
					this.progFullName);
		}

		if (ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_3)
				.exists()) {
			ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_3).set(
					this.progFullName);
		}

		// Because of the Org Hierarchies now must select the org and access
		// type first

		// TODO: may change later to be passed as an arguments or setting up
		Thread dialogClickerG3 = new Thread() {
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialogG3 = ie.alertDialog();
					while (dialogG3 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(0.250);
					}

					dialogG3.ok();
					log.debug("got confirm Dialog1 and clicked OK.");

				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClickerG3.start();
		log.debug("started dialog clicker thread");
		ie.selectList(id, IProgramsConst.fundingOpp_Prog_Primary_Org).select("G3");
		
		GeneralUtil.takeANap(1.000);
		
		dialogClickerG3 = null;

		Thread dialogClicker = new Thread() {
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialog1 = ie.alertDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(0.250);
					}

					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");

				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClicker.start();
		log.debug("started dialog clicker thread");

		ie.selectList(id, IProgramsConst.fundingOpp_Prog_Org_Access).select("Public");
		
		GeneralUtil.takeANap(1.000);
		
		dialogClicker = null;

		if (this.progForm) {
			ie.selectList(name, IProgramsConst.fundingOpp_PROG_FORM_SELECT)
					.select(this.getProgramFormName());
			this.addStepIndex += 1;
		}

		if (this.publicationForm) {
			ie.selectList(id, IProgramsConst.fundingOpp_PUBL_FORM_SELECT)
					.select(this.getPublicationFormName());
			this.addStepIndex += 1;
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.openFundingOppPlannerBtn);

		ie.link(title, IProgramsConst.fundingOpp_Manage_Admins_Title).click();

		for (int i = 0; i < this.progAdmin.length; i++) {

			ie.selectList(0).select(this.progAdmin[i]);
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			GeneralUtil.takeANap(1.0);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		if (ie.link(title, IProgramsConst.fundingOpp_Manage_Staffs_Title).exists()) 
		{
			ie.link(title, IProgramsConst.fundingOpp_Manage_Staffs_Title).click();
			
		} else {
			ie.link(title, "/Funding Opportunity Officers/").click();
		}

		for (int j = 0; j < this.progOfficers.length; j++) {

			ie.selectList(id, IProgramsConst.PROG_STAFF_AVAILABL).select(this.progOfficers[j]);

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			GeneralUtil.takeANap(1.0);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		if (!ie.link(title, IProgramsConst.fundingOpp_ProjectConfig_Title).exists())
		{
			Assert.fail("Could not find Project Configuration Node in the planner!");
			return;
		}
		
		ie.link(title, IProgramsConst.fundingOpp_ProjectConfig_Title).click();

		if (this.creationFOProjDisable) {
			ie.checkbox(name, IProgramsConst.fundingOpp_PROG_FO_PROJ_CREATE).set();
		} else {
			ie.checkbox(name, IProgramsConst.fundingOpp_PROG_FO_PROJ_CREATE).clear();
		}

		if (this.completeAFRequired) {
			ie.checkbox(name, IProgramsConst.fundingOpp_PROG_FORM_COMPL_REC).set();
		} else {
			ie.checkbox(name, IProgramsConst.fundingOpp_PROG_FORM_COMPL_REC).clear();
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backToFoppPlannerBtn);
	}

	public void managePublicantionForm() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.link(title, IProgramsConst.fundingOpp_Public_EForm_Title).click();

		if (GeneralUtil.isButtonExistsByValue(IClicksConst.completeBtn)) {
			ie.textField(0).set(IGeneralConst.searchWords);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.completeBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
	}

	public boolean openAdminEForm() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.link(title, IProgramsConst.fundingOpp_Admins_EForm_Title).exists())
		{
			ie.link(title, IProgramsConst.fundingOpp_Admins_EForm_Title).click();

			return true;
		}

		return false;
	}

	public void manageBasicFundingOppForm() throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.link(title, IProgramsConst.fundingOpp_Admins_EForm_Title).click();

		ie.textField(0).set("12345567890");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
	}

	/**
	 * @param numOfSchedules
	 * @param nameOfSchedule
	 * @throws Exception
	 */
	public void managePaymentScheduleProgramForm(Integer numOfSchedules,
			String nameOfSchedule) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.link(title, IProgramsConst.fundingOpp_Admins_EForm_Title).click();

		if (TablesUtil.howManyEntriesInTable(IProjectsConst.gps_ListTableId) <= 0) {
			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int x = 1; x <= numOfSchedules; x++) {
				ie.textField(id, IProjectsConst.gps_Milestone_NameTxtId).set(
						nameOfSchedule + " " + Integer.toString(x));

				ie.textField(id, IProjectsConst.gps_Milestone_StartDateId).set(getStartDate());

				ie.textField(id, IProjectsConst.gps_Milestone_EndDateId).set(getEndDate());

				ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		} else {
			TablesUtil.openInTableByImage(IProjectsConst.gps_ListTableId, "Claim 1", 1);

			this.setStartDate(GeneralUtil.getTextInTextFieldByIndex(1));

			this.setEndDate(GeneralUtil.getTextInTextFieldByIndex(2));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		}

		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);

	}

	protected void initializeStep(String step[][]) throws Exception {
		this.setCurrentStepIdent(this.progLetter + this.progPreFix + step[0][0]
				+ this.getStepPreFix() + this.progPostfix);

		this.setFullStepIdent(this.progLetter + this.progPreFix + step[0][0]
				+ this.getStepPreFix() + this.progPostfix);

		this.setFullStepName(this.fullStepIdent.replace('-', ' '));

		this.setCurrentStepName(this.currentStepIdent.replace('-', ' '));
	}

	protected void addStepDetails(String step[][]) throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.selectList(name, IStepsConst.step_Type_Id).select(step[0][2]);
		
		GeneralUtil.takeANap(1.0);

		ie.textField(name, IStepsConst.step_Ident_Id).set(
				this.getFullStepIdent());

		ie.selectList(name, IStepsConst.step_Proj_Officer_Group_Id).select(
				this.getStepOfficer());

		ie.textField(id, IStepsConst.step_Start_Date_Id).set(this.startDate);

		ie.textField(id, IStepsConst.step_End_Date_Id).set(this.endDate);

		ie.textField(id, IStepsConst.step_Name_Locale_0_Id).set(
				this.getFullStepName());

		if (ie.textField(id, IStepsConst.step_Name_Locale_1_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_1_Id).set(
					this.getFullStepName());
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_2_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_2_Id).set(
					this.getFullStepName());
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_3_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_3_Id).set(
					this.getFullStepName());
		}

		if (step[0][3] == "true")
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).set();
		else
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).clear();

		ie.selectList(name, IStepsConst.step_ReExecute_Id).select(step[0][4]);
	}

	public void addDecisionStep(String step[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();

		this.setStepPreFix("");

		initializeStep(step);

		this.setDataFromStepIdent(this.progLetter + this.progPreFix
				+ step[1][0] + this.progPostfix);

		ClicksUtil.clickImage(IClicksConst.addItemImg);

		ie.textField(name, IStepsConst.step_Ident_Id).set(
				this.getFullStepIdent());

		ie.selectList(name, IStepsConst.step_Type_Id).select("Decision");

		ie.selectList(name, IStepsConst.step_Proj_Officer_Group_Id).select(
				this.getStepOfficer());

		ie.textField(id, IStepsConst.step_Start_Date_Id).set(this.startDate);

		ie.textField(id, IStepsConst.step_End_Date_Id).set(this.endDate);

		if (step[0][3] == "true") {
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).set();
		} else {
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).clear();
		}

		ie.selectList(name, IStepsConst.step_ReExecute_Id).select(step[0][4]);

		ie.selectList(id, IStepsConst.step_Prop_Decision_DataFromStep_Id).select(
				this.getDataFromStepIdent());

		ie.textField(id, IStepsConst.step_Prop_Decision_Expression_Id).set(step[1][1]);

		ie.textField(id, IStepsConst.step_Name_Locale_0_Id).set(
				this.getFullStepName());

		if (ie.textField(id, IStepsConst.step_Name_Locale_1_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_1_Id).set(
					this.getFullStepName());
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_2_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_2_Id).set(
					this.getFullStepName());
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		this.stepsIndex += 1;

		return;
	}

	public void addWorkflowSuspension(String step[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();

		this.setStepPreFix("");

		initializeStep(step);

		ClicksUtil.clickImage(IClicksConst.addItemImg);

		ie.textField(name, IStepsConst.step_Ident_Id).set(
				this.getFullStepIdent());

		ie.selectList(name, IStepsConst.step_Type_Id).select(
				"Workflow Suspension");

		ie.selectList(name, IStepsConst.step_Proj_Officer_Group_Id).select(
				this.getStepOfficer());

		ie.textField(id, IStepsConst.step_Start_Date_Id).set(this.startDate);

		ie.textField(id, IStepsConst.step_End_Date_Id).set(this.endDate);

		if (step[0][3] == "true")
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).set();
		else
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).clear();

		ie.selectList(name, IStepsConst.step_ReExecute_Id).select(step[0][4]);

		ie.textField(id, IStepsConst.step_Name_Locale_0_Id).set(
				this.getFullStepName());

		if (ie.textField(id, IStepsConst.step_Name_Locale_1_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_1_Id).set(
					this.getFullStepName());
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_2_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_2_Id).set(
					this.getFullStepName());
		}

		for (int i = 0; i < step[1].length; i++) {
			this.setDataFromStepIdent(this.progLetter + this.progPreFix
					+ step[1][i] + this.progPostfix);

			ie.selectList(id, IStepsConst.step_Prop_WSS_M2M_Available_Id)
					.select(this.getDataFromStepIdent());

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		this.stepsIndex += 1;

		return;
	}

	public void addStep(String step[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();

		this.setStepPreFix("");

		initializeStep(step);

		ClicksUtil.clickImageByAlt(IClicksConst.dmAddNewStep_Alt);

		addStepDetails(step);

		if (step[1].length > 2) {
			setStepProperty(step[1]);
		} else {
			ie.selectList(id, IStepsConst.step_Prop_EFormIdent_Id).select(step[1][0]);

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

		this.stepsIndex += 1;

	}

	private void setStepProperty(String stepProp[]) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if(!GeneralUtil.selectFullStringInDropdownList(IStepsConst.step_Prop_EFormIdent_Id, stepProp[0]))
		{
			log.error("Could Not Find e.Form in EForm DropDown!");
			return;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IStepsConst.step_Prop_EvaluationType_Id, stepProp[1]))
		{
			log.error("Could Not Select Eval Criteria!");
			return;
		}

		ie.textField(name, IStepsConst.step_Prop_QuorumAmount_Id).set(
				stepProp[2]);

		if (stepProp[3] == "true")
			ie.checkbox(name, IStepsConst.step_Prop_AutoAssign_Id).set();
		else
			ie.checkbox(name, IStepsConst.step_Prop_AutoAssign_Id).clear();

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

	}

	public HtmlElement getListItem(String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if(ele.innerText().trim().startsWith(innerText)) {

				return ele;
			}			
		}
		log.error("Could not find object " + innerText);
		return null;
	}
	
	public boolean isExpanded(HtmlElement ele) throws Exception {

		Spans sp =  ele.spans(attribute("class", IClicksConst.expandedSpan));

		for (Span span : sp) {

			if (span.exists())

				return true;
		}

		return false;
	}
	
	public void expandAnObject(String objectInnerText)throws Exception {

		HtmlElement ele = getListItem(objectInnerText);

		if (!isExpanded(ele)){

			Spans sp =  ele.spans();

			sp.span(1).click();		

		}	else {
			log.warn("Object " + objectInnerText + " already expanded!");
		}		
	}
	
	/**
	 * @param stepsParam
	 * @return boolean
	 * @throws Exception
	 */
	public boolean manageStep(String stepsParam[][]) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString());
		
		if (!ele.exists()){
			log.error("Could not find row to expand");
			return false;
		}
		
		ele.link(title,"Manage Step Staff").click();

		ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(
				stepsParam[0][0]);

		ClicksUtil.clickButtons(IClicksConst.searchBtn);

		GeneralUtil.takeANap(2.000);

		for (int i = 0; i < stepsParam[0].length; i++) {

			ie.selectList(id, IProgStepsConst.stepStaff_AvailableStaff_Id)
					.select("/" + stepsParam[0][i] + "/");

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			GeneralUtil.takeANap(0.500);
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		if (stepsParam.length > 1) {

			if (stepsParam[1][0] != "Post-Award") {
				
				ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString());
				
				
				ele.link(title,"Manage e.Form Access").click();

				for (int i = 0; i < stepsParam[1].length; i++) {
					this.setTempFullStepIdent(this.progLetter + this.progPreFix
							+ stepsParam[1][i] + this.progPostfix);

					ie
							.selectList(
									id,
									IStepsConst.step_Manage_EFormAccess_AvailableForms_Id)
							.select(this.getTempFullStepIdent());

					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}

				ClicksUtil.clickButtons(IClicksConst.saveBtn);

				ClicksUtil.clickButtons(IClicksConst.backBtn);
			}
		}
		
		return true;

	}

	public void manageBFstep(String stepParams[]) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString());
		
		if(!ele.link(title, "Manage e.Form Data").exists())
		{
			Assert.assertTrue(false, "Could not find e.Form Data Details Icon!");
		}

		if (this.applicantProfile) {
			ie.selectList(id,
					IStepsConst.step_Manage_EFormData_IncludeApplicant_Id)
					.select("First");
		} else {
			ie.selectList(id,
					IStepsConst.step_Manage_EFormData_IncludeApplicant_Id)
					.select("Never");
		}

		if (this.progForm) {
			ie.selectList(id,
					IStepsConst.step_Manage_EFormData_IncludeAdminEForm_Id)
					.select("Last");
		} else {
			ie.selectList(id,
					IStepsConst.step_Manage_EFormData_IncludeAdminEForm_Id)
					.select("Never");
		}

		for (int i = 0; i < stepParams.length; i++) {
			ie.selectList(id,
					IStepsConst.step_Manage_EFormData_AvailableForms_Id)
					.select(
							this.progLetter + this.progPreFix + stepParams[i]
									+ this.progPostfix);

			ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);
	}

	public void manageStepNotification(String noteParams[],
			boolean conditions[], String groupsToSelect[], String everyDays,
			String repeatNote) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString());
		
		if(!ele.link(title, "Manage Notification").exists())
		{
			Assert.assertTrue(false, "FAIL: Could Not find Step Notification Details Icon");
		}
		
		ele.link(title, "Manage Notification").click();

		ie.textField(id, INotificationsConst.ntfName).set(
				noteParams[1] + " - " + this.getCurrentStepName());

		ie.selectList(id, INotificationsConst.ntfEvent).select(noteParams[0]);

		ie.textField(id, INotificationsConst.ntfSubject + "[0]").set(
				noteParams[2] + this.getCurrentStepName());

		ie.textField(id, INotificationsConst.ntfBody + "[0]").set(
				this.getCurrentStepName() + ": " + noteParams[3]);

		if (ie.textField(id, INotificationsConst.ntfSubject + "[1]").exists()) {
			ie.textField(id, INotificationsConst.ntfSubject + "[1]").set(
					"[fr_CA] " + noteParams[2] + this.getCurrentStepName());

			ie.textField(id, INotificationsConst.ntfBody + "[1]").set(
					"[fr_CA] " + this.getCurrentStepName() + ": "
							+ noteParams[3]);
		}

		if (ie.textField(id, INotificationsConst.ntfSubject + "[2]").exists()) {
			ie.textField(id, INotificationsConst.ntfSubject + "[2]").set(
					noteParams[2] + this.getCurrentStepName());

			ie.textField(id, INotificationsConst.ntfBody + "[2]").set(
					this.getCurrentStepName() + ": " + noteParams[3]);
		}

		ie.checkbox(id, INotificationsConst.ntfIsActive).set(conditions[0]);

		if (ie.checkbox(id, INotificationsConst.ntfRepeatable).exists()) {
			ie.checkbox(id, INotificationsConst.ntfRepeatable).set(
					conditions[5]);

		}

		ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).set(
				conditions[1]);		
		
		
		if(ie.selectList(id, INotificationsConst.ntfNotifyProjOfficerForThisStep).exists())
		{
			if(conditions[2])
			{
				ie.selectList(id, INotificationsConst.ntfNotifyProjOfficerForThisStep).select("Assigned Project Officer or Group");
				
			}
			else
			{
				ie.selectList(id, INotificationsConst.ntfNotifyProjOfficerForThisStep).select("[None]");
			}
		}
		
		if(ie.selectList(id, INotificationsConst.ntfNotifyAllProjOffForOtherSteps).exists())
		{
			if(conditions[3])
			{
				ie.selectList(id, INotificationsConst.ntfNotifyAllProjOffForOtherSteps).select("Assigned Project Officers or Groups");
				
			}
			else
			{
				ie.selectList(id, INotificationsConst.ntfNotifyAllProjOffForOtherSteps).select("[None]");
			}
		}
		
		if (ie.checkbox(id, INotificationsConst.ntfNotifyAsignedStepEvaluators)
				.exists()) {
			ie.checkbox(id, INotificationsConst.ntfNotifyAsignedStepEvaluators)
					.set(conditions[4]);
		}
		// Setting Groups

		if (groupsToSelect.length > 0) {

			for (int i = 0; i < groupsToSelect.length; i++) {

				ie.selectList(id, INotificationsConst.ntfAvailableRecipients)
						.select(groupsToSelect[i]);

				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
		}

		// Set Schedule

		ie.textField(id, INotificationsConst.ntfNotifyEvery).set(everyDays);

		if (ie.textField(id, INotificationsConst.ntfRepeatEvery).exists())
			ie.textField(id, INotificationsConst.ntfRepeatEvery)
					.set(repeatNote);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);
	}

	// --------------------- Post-Award -----------------------

	public void addPAStep(String step[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickImageByAlt(IClicksConst.dmAddNewStep_Alt);

		this.subStepsIndex = -1;

		this.setStepPreFix("");
		initializeStep(step);

		addStepDetails(step);

		ie.selectList(name, IStepsConst.step_Prop_AwardStepName_Id).select(
				this.progLetter + this.progPreFix + step[1][0]
						+ this.progPostfix);

		ie.selectList(name, IStepsConst.step_Prop_IPASS_M2M_Available_Id)
				.select(step[1][1]);

		ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		this.stepsIndex += 1;

	}

	public void manageDecisionStep(int projType, String skipToStep,
			String decisionStep) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(ProgramUtil.openProgPlannerObjectDetails(projType,
				decisionStep + " (Decision)"), "Fail: Could not find Object: "
				+ this.getFullStepIdent() + " (Decision)");

		ie.selectList(id, IStepsConst.step_Prop_Decision_SkipToStep_Id).select(
				skipToStep);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		return;
	}

	public void addDecisionSubStep(String step[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.checkbox(id, IProgramsConst.fundingOpp_ExpandCollapseSubItems_Id)
				.set();

		if (ie.image(id, "/planner:0:" + this.addStepIndex.toString() + ":t2/")
				.src().endsWith("nav-plus-line-last.gif"))
			ie.image(id, "/planner:0:" + this.addStepIndex.toString() + ":t2/")
					.click();

		if (ie.images(alt, "Add Step").image(2).exists()) {
			ie.images(alt, "Add Step").image(2).click();
		} else {
			ie.images(alt, "Add Step").image(1).click();
		}

		this.setStepPreFix("-pa");

		initializeStep(step);

		this.setDataFromStepIdent(this.progLetter + this.progPreFix
				+ step[1][0] + this.getStepPreFix() + this.progPostfix);

		ie.textField(name, IStepsConst.step_Ident_Id).set(
				this.getFullStepIdent());

		ie.selectList(name, IStepsConst.step_Type_Id).select("Decision");

		ie.selectList(name, IStepsConst.step_Proj_Officer_Group_Id).select(
				this.getStepOfficer());

		ie.textField(id, IStepsConst.step_Start_Date_Id).set(this.startDate);

		ie.textField(id, IStepsConst.step_End_Date_Id).set(this.endDate);

		if (step[0][3] == "true")
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).set();
		else
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).clear();

		ie.selectList(name, IStepsConst.step_ReExecute_Id).select(step[0][4]);

		ie.selectList(id, IStepsConst.step_Prop_Decision_DataFromStep_Id).select(
				this.getDataFromStepIdent());

		ie.textField(id, IStepsConst.step_Prop_Decision_Expression_Id).set(step[1][1]);

		ie.textField(id, IStepsConst.step_Name_Locale_0_Id).set(
				this.getFullStepName());

		if (ie.textField(id, IStepsConst.step_Name_Locale_1_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_1_Id).set(
					this.getFullStepName());
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_2_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_2_Id).set(
					this.getFullStepName());
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		this.subStepsIndex += 1;

		return;
	}

	public void addWorkflowSuspensionSubStep(String step[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.checkbox(id, IProgramsConst.fundingOpp_ExpandCollapseSubItems_Id)
				.set();

		if (ie.image(id, "/planner:0:" + this.addStepIndex.toString() + ":t2/")
				.src().endsWith("nav-plus-line-last.gif"))
			ie.image(id, "/planner:0:" + this.addStepIndex.toString() + ":t2/")
					.click();

		if (ie.images(alt, "Add Step").image(2).exists()) {
			ie.images(alt, "Add Step").image(2).click();
		} else {
			ie.images(alt, "Add Step").image(1).click();
		}

		this.setStepPreFix("-pa");

		initializeStep(step);

		ie.textField(name, IStepsConst.step_Ident_Id).set(
				this.getFullStepIdent());

		ie.selectList(name, IStepsConst.step_Type_Id).select(
				"Workflow Suspension");

		ie.selectList(name, IStepsConst.step_Proj_Officer_Group_Id).select(
				this.getStepOfficer());

		ie.textField(id, IStepsConst.step_Start_Date_Id).set(this.startDate);

		ie.textField(id, IStepsConst.step_End_Date_Id).set(this.endDate);

		if (step[0][3] == "true")
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).set();
		else
			ie.checkbox(name, IStepsConst.step_Critical_Step_Id).clear();

		ie.selectList(name, IStepsConst.step_ReExecute_Id).select(step[0][4]);

		ie.textField(id, IStepsConst.step_Name_Locale_0_Id).set(
				this.getFullStepName());

		if (ie.textField(id, IStepsConst.step_Name_Locale_1_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_1_Id).set(
					this.getFullStepName());
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_2_Id).exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_2_Id).set(
					this.getFullStepName());
		}

		for (int i = 0; i < step[1].length; i++) {
			this.setDataFromStepIdent(this.progLetter + this.progPreFix
					+ step[1][i] + this.getStepPreFix() + this.progPostfix);

			ie.selectList(id,
					"main:editStep:step:properties:0:M2M_AvailableItems")
					.select(this.getDataFromStepIdent());

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		this.subStepsIndex += 1;

		return;
	}

	public void addSubSteps(String subStep[][]) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString());
		
		if(!ele.link(title,"Add Step").exists())
		{
			log.error("Could not find add step icon for Post-Award Steps");
			
			return;
		}
		
		ele.link(title,"Add Step").click();

		this.setStepPreFix("-pa");

		initializeStep(subStep);

		addStepDetails(subStep);

		if (subStep[0][2] == "Review" || subStep[0][2] == "Approval") {
			setStepProperty(subStep[1]);
		} else {
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

		this.subStepsIndex += 1;

	}

	public void manageSubSteps(String stepsParam[][]) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString() + "_5_" + this.subStepsIndex);
		
		if (!ele.link(title, "Manage Step Staff").exists())
		{
			log.error("Could not find Step Staff details icon");
			Assert.assertTrue(false);
		}
		
		ele.link(title, "Manage Step Staff").click();

		ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(
				stepsParam[0][0]);

		ClicksUtil.clickButtons(IClicksConst.searchBtn);

		GeneralUtil.takeANap(2.000);

		for (int i = 0; i < stepsParam[0].length; i++) {
			ie.selectList(id, IProgStepsConst.stepStaff_AvailableStaff_Id)
					.select("/" + stepsParam[0][i] + "/");

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			GeneralUtil.takeANap(0.500);
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		if (stepsParam.length > 1) {
			
			ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString() + "_5_" + this.subStepsIndex);
			
			ele.link(title, "Manage e.Form Access").click();

			for (int i = 0; i < stepsParam[1].length; i++) {
				this.setTempFullStepIdent(this.progLetter + this.progPreFix
						+ stepsParam[1][i] + this.progPostfix);

				ie.selectList(id, "/availableForms/").select(
						this.getTempFullStepIdent());

				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

	}

	public void manageBFSubStep(String stepParams[]) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString() + "_5_" + this.subStepsIndex);
		
		if(!ele.link(title, "Manage e.Form Data").exists())
		{
			Assert.assertTrue(false, "Could not find e.Form Data Details Icon!");
		}
		
		ele.link(title, "Manage e.Form Data").click();

		for (int i = 0; i < stepParams.length; i++) {
			ie.selectList(id,
					IStepsConst.step_Manage_EFormData_SelectedForms_Id).select(
					this.progLetter + this.progPreFix + stepParams[i]
							+ this.progPostfix);

			ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);
	}

	public void manageSubStepNotification(String noteParams[],
			boolean conditions[], String groupsToSelect[], String everyDays,
			String repeatNote) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickButtons(IClicksConst.expandAll_Btn);
		
		HtmlElement ele = ie.htmlElement(id, "j_id_1v:programPlannerTree:0_" + this.addStepIndex.toString() + "_" + this.stepsIndex.toString() + "_5_" + this.subStepsIndex);
		
		if(!ele.link(title, "Manage Notification").exists())
		{
			Assert.assertTrue(false, "Could not find e.Form Data Details Icon!");
		}
		
		ele.link(title, "Manage Notification").click();

		ie.textField(id, INotificationsConst.ntfName).set(
				noteParams[1] + " - " + this.getCurrentStepName());

		ie.selectList(id, INotificationsConst.ntfEvent).select(noteParams[0]);

		ie.textField(id, "subject[0]").set(
				noteParams[2] + this.getCurrentStepName());

		ie.textField(id, "txtBody[0]").set(
				this.getCurrentStepName() + ": " + noteParams[3]);

		if (ie.textField(id, "subject[1]").exists()) {
			ie.textField(id, "subject[1]").set(
					"[fr_CA] " + noteParams[2] + this.getCurrentStepName());

			ie.textField(id, "txtBody[1]").set(
					"[fr_CA] " + this.getCurrentStepName() + ": "
							+ noteParams[3]);
		}

		if (ie.textField(id, "subject[2]").exists()) {
			ie.textField(id, "subject[2]").set(
					noteParams[2] + this.getCurrentStepName());

			ie.textField(id, "txtBody[2]").set(
					this.getCurrentStepName() + ": " + noteParams[3]);
		}

		ie.checkbox(id, INotificationsConst.ntfIsActive).set(conditions[0]);

		if (ie.checkbox(id, INotificationsConst.ntfRepeatable).exists()) {
			ie.checkbox(id, INotificationsConst.ntfRepeatable).set(
					conditions[5]);

		}

		ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).set(
				conditions[1]);		
		
		if(ie.selectList(id, INotificationsConst.ntfNotifyProjOfficerForThisStep).exists())
		{
			if(conditions[2])
			{
				ie.selectList(id, INotificationsConst.ntfNotifyProjOfficerForThisStep).select("Assigned Project Officer or Group");
				
			}
			else
			{
				ie.selectList(id, INotificationsConst.ntfNotifyProjOfficerForThisStep).select("[None]");
			}
		}
		
		if(ie.selectList(id, INotificationsConst.ntfNotifyAllProjOffForOtherSteps).exists())
		{
			if(conditions[3])
			{
				ie.selectList(id, INotificationsConst.ntfNotifyAllProjOffForOtherSteps).select("Assigned Project Officer or Group");
				
			}
			else
			{
				ie.selectList(id, INotificationsConst.ntfNotifyAllProjOffForOtherSteps).select("[None]");
			}
		}
		if (ie.checkbox(id, INotificationsConst.ntfNotifyAsignedStepEvaluators)
				.exists()) {
			ie.checkbox(id, INotificationsConst.ntfNotifyAsignedStepEvaluators)
					.set(conditions[4]);
		}

		// Setting Groups
		if (groupsToSelect.length > 0) {

			for (int i = 0; i < groupsToSelect.length; i++) {

				ie.selectList(id, INotificationsConst.ntfAvailableRecipients)
						.select(groupsToSelect[i]);

				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
		}

		// Set Schedule
		ie.textField(id, INotificationsConst.ntfNotifyEvery).set(everyDays);

		if (ie.textField(id, INotificationsConst.ntfRepeatEvery).exists())
			ie.textField(id, INotificationsConst.ntfRepeatEvery)
					.set(repeatNote);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

	}

	// --------------------- End of Post Award --------------------------------

	public void activateProgram(String status) throws Exception {
		IE ie = IEUtil.getActiveIE();
		ie.image(alt, "Open " + this.progFullIdent).click();

		ie.selectList(id, IProgramsConst.fundingOpp_PROG_STATUS_MENU).select(
				status);
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);

		ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
	}

	/**
	 * @return the applicantProfile
	 */
	public boolean isApplicantProfile() {
		return applicantProfile;
	}

	/**
	 * @param applicantProfile
	 *            the applicantProfile to set
	 */
	public void setApplicantProfile(boolean applicantProfile) {
		this.applicantProfile = applicantProfile;
	}

	/**
	 * @return the stepsIndex
	 */
	public Integer getStepsIndex() {
		return stepsIndex;
	}

	/**
	 * @param stepsIndex the stepsIndex to set
	 */
	public void setStepsIndex(Integer stepsIndex) {
		this.stepsIndex = stepsIndex;
	}

	/**
	 * @return the projPreFix
	 */
	public String getProjPreFix() {
		return projPreFix;
	}

	/**
	 * @param projPreFix the projPreFix to set
	 */
	public void setProjPreFix(String projPreFix) {
		this.projPreFix = projPreFix;
	}

}
