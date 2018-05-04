/**
 * 
 */
package test_Suite.lib.workflow;

import static watij.finders.FinderFactory.*;
import static watij.finders.SymbolFactory.*;

import java.util.*;

import org.apache.commons.logging.*;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.workflow.*;
import test_Suite.constants.workflow.IBf_FoppConst.EPostFix;
import test_Suite.constants.workflow.IProgramsConst.EstepManagment;
import test_Suite.constants.workflow.IStepsConst.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.*;
import watij.dialogs.AlertDialog;
import watij.elements.*;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class FundingOpportunity {

	private static Log log = LogFactory.getLog(FundingOpportunity.class);

	private String foppLetter = null;

	private String foppPreFix = "";

	private String foppPostfix = "";

	private String projPrefix;

	private char foppPortal = ' ';

	private boolean newFOPP;

	private String foppName;

	private String foppIdent;

	private String foppFullIdent = null;

	private String foppFullName = null;

	private boolean adminEForm = false;

	private boolean pubEForm = false;

	private String adminEFormName = null;

	private String pubEFormName = null;

	private Integer stepsIndex = -1;

	private Integer addStepIndex = 2;

	private Integer subStepsIndex = -1;

	private List<String> foppStaffs = null;

	private List<String> foppAdmins = null;

	private LinkedHashMap<EStepsType,FOPPSteps> foppSteps = null;

	private Set<Map.Entry<EStepsType,FOPPSteps>> setSteps;

	private String startDate;

	private String endDate;

	private String foppOfficer = IFoppConst.fundingOpp_Oficer;

	private String foppStatus = null;

	private String foppRegistrOpens = null;

	private String foppRegistrOpenHH = null;

	private String foppRegistrOpenMM = null;

	private String foppRegistrCloses = null;

	private String foppRegistrCloseHH = null;

	private String foppRegistrCloseMM = null;

	private String currentStepName = null;

	private String fullStepName = null;

	private String currentStepIdent = null;

	private String fullStepIdent = null;

	private String tempFullStepIdent = null;

	private String tempFullStepName = null;

	//	private String progOfficers[] = null;

	private String skipToStepIdent = null;

	private boolean creationFOProjDisabled = false;

	private boolean completeAFRequired = false;

	private String awardedStepIdent;

	private String bfFromFOPPIdent = "None";

	private boolean bfAwardedProject = false;

	private String primaryOrg = "G3";

	private String orgAccess = "Public";

	/******** Constructors *******************/

	public FundingOpportunity() {};

	public FundingOpportunity(String letter, String preFix,String postFix) {
		try {

			this.newFOPP = false;

			this.foppLetter = letter;
			this.foppPreFix = preFix;
			this.foppPostfix = postFix;
			this.projPrefix = preFix;
			this.foppName = IFoppConst.fundingOpp_Name;
			this.foppIdent = this.foppName;
			this.foppFullIdent = this.foppLetter + this.foppPreFix + this.foppIdent + this.foppPostfix;
			this.foppFullName = this.foppFullIdent.replace("-", " ");


			this.startDate = GeneralUtil.getTodayDate();
			this.endDate = GeneralUtil.getNextYear();
			this.foppRegistrOpens = this.startDate;
			this.foppRegistrCloses = this.endDate;

			this.foppStaffs = initStaffList(IFoppConst.fundingOpp_Staff);
			this.foppAdmins = initStaffList(IFoppConst.fundingOpp_Admins);
			this.awardedStepIdent = "Never";


		} catch (Exception e) {
			Assert.fail("Unexpected Error: " + e);
		}

	}

	public FundingOpportunity(String letter, String preFix) {
		try {

			this.newFOPP = false;

			this.foppLetter = letter;
			this.foppPreFix = preFix;
			this.projPrefix = preFix;
			this.foppName = "";
			this.foppIdent = this.foppName;
			this.foppFullIdent = this.foppLetter + this.foppPreFix + this.foppIdent + this.foppPostfix;
			this.foppFullName = this.foppFullIdent.replace("-", " ");


			this.startDate = GeneralUtil.getTodayDate();
			this.endDate = GeneralUtil.getNextYear();
			this.foppRegistrOpens = this.startDate;
			this.foppRegistrCloses = this.endDate;

			this.foppStaffs = initStaffList(IFoppConst.fundingOpp_Staff);
			this.foppAdmins = initStaffList(IFoppConst.fundingOpp_Admins);
			this.awardedStepIdent = "Never";


		} catch (Exception e) {
			Assert.fail("Unexpected Error: " + e);
		}

	}

	public FundingOpportunity(EPostFix ePostFix){

		this.foppLetter = "A";
		this.foppPreFix = IBf_FoppConst.preFix;
		this.foppPostfix = IBf_FoppConst.postFix[ePostFix.ordinal()];
		this.foppName = IFoppConst.fundingOpp_Name;
		this.foppIdent = this.foppName;
		this.foppFullIdent = this.foppLetter + this.foppPreFix + this.foppIdent + this.foppPostfix;
		this.foppFullName = this.foppFullIdent.replace("-", " ");
	};

	public FundingOpportunity(String letter, String foppName, String preFix,String postFix) {
		try {

			this.newFOPP = false;

			this.foppLetter = letter;
			this.foppPreFix = preFix;
			this.foppPostfix = postFix;
			this.projPrefix = preFix;
			this.foppName = foppName;
			this.foppIdent = this.foppName;
			this.foppFullIdent = this.foppLetter + this.foppPreFix + this.foppIdent + this.foppPostfix;
			this.foppFullName = this.foppFullIdent.replace("-", " ");

			this.startDate = GeneralUtil.getTodayDate();
			this.endDate = GeneralUtil.getNextYear();
			this.foppRegistrOpens = this.startDate;
			this.foppRegistrCloses = this.endDate;

			this.foppStaffs = initStaffList(IFoppConst.fundingOpp_Staff);
			this.foppAdmins = initStaffList(IFoppConst.fundingOpp_Admins);
			this.awardedStepIdent = "Never";


		} catch (Exception e) {
			Assert.fail("Unexpected Error: " + e);
		}

	}


	/******* End of Constractors *************/	

	public FundingOpportunity(String string) {
		// TODO Auto-generated constructor stub
	}

	/************** Class Methods *************/

	public List<String> initStaffList(String[] staffs) throws Exception {

		List<String> lst = new ArrayList<String>();

		for (String string : staffs) {

			lst.add(string);			
		}		
		return lst;
	}

	public boolean openFundingOppPlanner() throws Exception {		

		if(!isThisFOPP_Planner()) {

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			FiltersUtil.filterListByLabel(IFiltersConst.gpa_FundingOppIdent_Lbl, this.foppFullIdent,"Exact");	

			ClicksUtil.clickLinks(this.foppFullIdent);

			return true;
		}

		return true;
	}

	public boolean openProjectConfigDetails() throws Exception {	

		IE ie = IEUtil.getActiveIE();

		if(!isThisFOPP_Planner())
		{
			log.error("This should be FOPP Planner!");

			return false;
		}

		if(!GeneralUtil.isLinkExistsByTitle(IProgramsConst.fundingOpp_ProjectConfig_Title))
		{
			log.error("Could not find Project Configuration Node in FOPP Planner!");
			return false;
		}

		ie.link(title, IProgramsConst.fundingOpp_ProjectConfig_Title).click();	


		return true;
	}

	public boolean openProjectListConfigDetails() throws Exception {

		if(!openProjectConfigDetails()) {

			log.error("Could not Open Project Config!");

			return false;
		}

		if(!GeneralUtil.isLinkExistsByTxt(IClicksConst.projListBtn)){

			log.error("Could not find Project List Tab!");

			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.projListBtn);		

		return true;		
	}

	public boolean openFundingOppDetails() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(this.newFOPP) {

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			FiltersUtil.filterListByLabel(IFiltersConst.gpa_FundingOppIdent_Lbl, this.foppFullIdent,"Exact");

			if(!isNewFOPPImageExists()) {
				return false;
			}

			ClicksUtil.clickImage(IClicksConst.newImg);

			return true;
		}		

		if(isThisFOPP_Details()){

			return true;			
		}			

		if(openFundingOppPlanner()) {
			
			if(!ie.link(title, this.foppFullIdent).exists())
			{
				log.error("could not find the Funding Opp Details Icon!");
				return false;
			}
			
			ie.link(title, this.foppFullIdent).click();		
			

//			Tables tables = ie.form(id,IFoppConst.fundingOpp_PlannerPage_Form_Id).tables();
//
//			for (Table table : tables) {
//
//				if(table.innerText().contains(this.foppFullIdent)) {
//
//					table.link(title, this.foppFullIdent).click();
//
//					return true;						
//				}				
//			}

		} else {
			return true;
		}		

		return true;
	}


	public boolean openPublicationEForm() throws Exception {

		String name = "Publication e.Form";

		if(isThisFOPP_Planner()) {

			HtmlElement ele = getListItem(name);

			if(null != ele) {

				ele.link(title,name).click();

				return true;
			}			
		}

		log.error("Could not find " + name + " to click");
		return false;
	}

	public boolean openAdminEForm() throws Exception {

		String name  = "Administration e.Form";

		if(isThisFOPP_Planner()) {

			HtmlElement ele = getListItem(name);

			if(null != ele) {

				ele.link(title, name).click();

				return true;
			}			
		}

		return false;
	}

	public boolean openBudgetingEForm() throws Exception {

		String name = "Budgeting e.Form";

		if(isThisFOPP_Planner()) {

			HtmlElement ele = getListItem(name);

			if(null != ele) {

				ele.link(title, name).click();

				return true;
			}			
		}

		return false;
	}

	public boolean openFOPPAdmins() throws Exception {

		String name = "Funding Opportunity Administrators";

		if(isThisFOPP_Planner()) {

			HtmlElement ele = getListItem(name);

			if(null != ele) {

				ele.link(title, name).click();

				return true;
			}			
		}

		return false;
	}

	public boolean openFOPPStaff() throws Exception {

		String name = "Funding Opportunity Staff";

		if(isThisFOPP_Planner()) {

			HtmlElement ele = getListItem(name);

			if(null != ele) {

				ele.link(title, name).click();

				return true;
			}			
		}

		return false;
	}

	public boolean openStepDetails(boolean newStep, String objectInnerText) throws Exception {

		String strTitle;
		HtmlElement ele;

		if(GeneralUtil.FindTextOnPage("Step Details") && (GeneralUtil.getTextFieldValue(IFoppConst.stepDetailsIdent_Id)).equals(objectInnerText))
		{
			log.warn("I am Already in the Step Details of: ".concat(objectInnerText));
			return true;
		}

		if(newStep) {

			strTitle = "Add Step";

			ele = getListItem("Steps");

		}else {

			expandAnObject("Steps");

			strTitle = "View Step Details";

			ele = getListItem(objectInnerText);

		}		

		if (null!= ele) {

			ele.link(title,strTitle).click();
			return true;
		}		
		return false;
	}

	public boolean openStepManagementDetails(String stepName, String form) throws Exception {

		expandAnObject("Steps");

		HtmlElement stepNode = expandAnObject_eleReturn(stepName);

		HtmlElement ele = getInnerListItem(stepNode, form);

		String strTitle;

		strTitle = "Manage " + form;

		if (null!= ele) {

			ele.link(title,strTitle).click();
			return true;
		}		
		return false;
	}

	public boolean expandAnObject(String objectInnerText)throws Exception {

		HtmlElement ele = getListItem(objectInnerText);

		if (!isExpanded(ele)){

			Spans sp =  ele.spans();

			sp.span(1).click();		
			
			return true;

		}	else {
			log.warn("Object " + objectInnerText + " already expanded!");
			return false;
		}		
	}
	
	public HtmlElement expandAnObject_eleReturn(String objectInnerText)throws Exception {

		HtmlElement ele = getListItem(objectInnerText);

		if (!isExpanded(ele)){

			Spans sp =  ele.spans();

			sp.span(1).click();		
			
			return ele;

		}	else {
			log.warn("Object " + objectInnerText + " already expanded!");
			return ele;
		}		
	}
	
	public HtmlElement expandInnerObj_eleReturn(String objNode, String objInnerNode)throws Exception {
		
		HtmlElement paObj = expandAnObject_eleReturn(objNode);

		HtmlElement ele = getInnerListItem(paObj, objInnerNode);
		
		if (!isExpanded(ele)){

			Spans sp =  ele.spans();

			sp.span(1).click();		
			
			return ele;

		}	else {
			log.warn("Object " + objInnerNode + " already expanded!");
			return ele;
		}		
	}

	public void collapseAnObject(String objectInnerText)throws Exception {

		HtmlElement ele = getListItem(objectInnerText);

		if (isExpanded(ele)){

			Spans sp =  ele.spans();

			sp.span(1).click();		

		}	else {
			log.warn("Object " + objectInnerText + " already collapsed!");
		}		
	}


	public boolean isExpanded(HtmlElement ele) throws Exception {

		Spans sp =  ele.span(0).spans(attribute("class", IClicksConst.expandedSpan));

		for (Span span : sp) {

			if (span.exists()){

				return true;
			}
			break;
		}

		return false;
	}

	public HtmlElement getListItem(String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if(ele.span(0).innerText().trim().contains(innerText)) {

				return ele;
			}			
		}
		return null;
	}
	
	public HtmlElement getInnerListItem(HtmlElement node, String innerText) throws Exception {

		HtmlElements eles = node.htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if(ele.span(0).innerText().trim().contains(innerText)) {

				return ele;
			}			
		}
		return null;
	}



	public boolean isThisFOPP_Details() throws Exception {

		return GeneralUtil.isFormTagExistsById("editProgram");
	}

	public boolean isThisFOPP_Planner() throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		return ie.div(attribute("class", "plannerBorder")).exists();
	}

	public boolean isNewFOPPImageExists() throws Exception {

		return GeneralUtil.isImageExistsAnyWhereInTable(ITablesConst.fundingOpp_programsFormId,"New Funding Opportunity");

	}

	public void selectPrimaryOrgAndAccess() throws Exception {

		IE ie = IEUtil.getActiveIE();

		Thread dialogClickerG3 = new Thread() {
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialogG3 = ie.alertDialog();
					while (dialogG3 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(1.000);
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
		ie.selectList(id, IProgramsConst.fundingOpp_Prog_Primary_Org).select(this.primaryOrg);

		dialogClickerG3 = null;

		Thread dialogClicker = new Thread() {
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialog1 = ie.alertDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(1.000);
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

		ie.selectList(id, IProgramsConst.fundingOpp_Prog_Org_Access).select(this.orgAccess);

		dialogClicker = null;
	}

	public boolean selectAwardedStep() throws Exception {
		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(id, "/considerAwardedAtStep/").getAllContents();

		for (String string : lst) {

			log.debug(string + " " + this.getAwardedStepIdent());

			if(string.equals(this.getAwardedStepIdent())) {	

				ie.selectList(id,"/considerAwardedAtStep/").select(string);

				return true;
			}			
		}	

		return false;
	}

	public boolean selectBFFundingOpp() throws Exception {

		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(id, "/bringForwardFromFundingOpp/").getAllContents();

		for (String string : lst) {

			if(string.equalsIgnoreCase(this.bfFromFOPPIdent)) {	

				ie.selectList(id,"/bringForwardFromFundingOpp/").select(string);				

				return true;
			}			
		}		

		return false;
	}

	public boolean toggleAwardedProjectCheckbox() throws Exception {

		IE ie = IEUtil.getActiveIE();
		GeneralUtil.takeANap(1.000);

		if(this.bfAwardedProject) {
			ie.checkbox(id,"/bringForwardOnlyIfAwarded/").set();
			return true;
		}else if(!this.bfFromFOPPIdent.equalsIgnoreCase("None")) {
			ie.checkbox(id,"/bringForwardOnlyIfAwarded/").clear();
			return true;
		}

		return false;
	}

	public boolean selectAndDe_SelectDropdown(String ddId, String value, boolean selectVal)throws Exception {
		IE ie = IEUtil.getActiveIE();

		SelectList sList = ie.selectList(id,ddId);

		if(!selectVal) {
			sList.clearSelection();
			return true;
		}

		List<String> lst = sList.getAllContents();

		for (String string : lst) {

			if(string.equalsIgnoreCase(value)) {

				sList.select(string);

				return true;
			}			
		}		
		return false;
	}

	public boolean findInM2M(String lstId,String string)throws Exception {
		IE ie = IEUtil.getActiveIE();

		SelectList sList = ie.selectList(id,lstId); 

		List<String> lst = sList.getAllContents();

		for (String string2 : lst) {

			if(string2.equalsIgnoreCase(string))  {

				sList.select(string2);

				return true;
			}			
		}			
		return false;
	}

	public void selectFOPPAdmins() throws Exception {

		Assert.assertTrue(openFOPPAdmins(), "FAIL: unable to open Funding Opp Admin M2M! for: " + this.foppFullIdent );

		for (String string : this.foppAdmins) {		

			Assert.assertTrue(findInM2M(IFoppConst.m2mAvailStaff_listId, string), "FAIL: unable to select FOPP Admin for: " + this.foppFullIdent); 

			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);			
		}			
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return;
	}

	public boolean manageEForm(String step, String form, String m2mOption)  throws Exception {

		openStepManagementDetails(step, form);

		findInM2M(IProgramsConst.progPlanner_availForms, m2mOption);

		if (!ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn)){

			if (!ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn)){

				log.error("No button exists to add form!");
				return false;
			}
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return true;
	}

	public void selectMultipleFOPPAdmins(String string) throws Exception {

		Assert.assertTrue(openFOPPAdmins(), "FAIL: unable to open Funding Opp Admin M2M! for: " + this.foppFullIdent );

		//for (String string : this.foppAdmins) {	

		//if(!findInM2M("main:programStaff:staff:selectedStaff", string)) {

		//		    		Assert.assertTrue(findInM2M("main:programStaff:staff:availableStaff", string1), "FAIL: unable to select Staff for: " + this.foppFullIdent); 
		//			
		//		    		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn));

		if(!findInM2M(IFoppConst.availAdminM2M_Id ,string)){

			log.error("FAIL: unable to select Report Officers for: " + this.foppFullIdent); 
		}

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn));

		//		    		Assert.assertTrue(findInM2M("main:programStaff:staff:availableStaff", string3), "FAIL: unable to select Submission Clerks for: " + this.foppFullIdent); 
		//			
		//		    		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn));
		//			
		//		    		Assert.assertTrue(findInM2M("main:programStaff:staff:availableStaff", string4), "FAIL: unable to select Submission Sub Clerks for: " + this.foppFullIdent); 
		//			
		//		    		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn));
		//		    	}
		//		    	else{
		//		    		ClicksUtil.clickButtons(IClicksConst.backBtn);
		//		    		
		//		    	}

		// }

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return;
	}

	public void removeFOPPAdmins() throws Exception {

		Assert.assertTrue(openFOPPAdmins(), "FAIL: unable to open Funding Opp Admin M2M! for: " + this.foppFullIdent );

		for (String string : this.foppAdmins) {	

			Assert.assertTrue(findInM2M("main:programStaff:staff:selectedStaff", string), "FAIL: unable to remove FOPP Admin for: " + this.foppFullIdent); 

			ClicksUtil.clickButtons(IClicksConst.m2MSingleBackBtn);			
		}		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return;
	}

	public void selectFOPPStaff() throws Exception {

		Assert.assertTrue(openFOPPStaff(), "FAIL: unable to open Funding Opp Staff M2M! for: " + this.foppFullIdent );

		for (String string : this.foppStaffs) {	

			if(!findInM2M("programStaff:staff:selectedStaff", string)) {

				Assert.assertTrue(findInM2M("programStaff:staff:availableStaff", string), "FAIL: unable to select FOPP Staff for: " + this.foppFullIdent); 

				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);	

				GeneralUtil.takeANap(1.0);
			}			
		}				
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return;
	}

	public void removeFOPPStaff() throws Exception {

		Assert.assertTrue(openFOPPStaff(), "FAIL: unable to open Funding Opp Staff M2M! for: " + this.foppFullIdent );

		for (String string : this.foppStaffs) {	

			if(!findInM2M(IFoppConst.m2mAvailStaff_listId, string)) {

				Assert.assertTrue(findInM2M(IFoppConst.m2mSelectStaff_listId, string), "FAIL: unable to remove FOPP Staff for: " + this.foppFullIdent); 

				ClicksUtil.clickButtons(IClicksConst.m2MSingleBackBtn);		

				GeneralUtil.takeANap(1.0);
			}		
		}				
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return;
	}

	public boolean changeGroupOfficerInStep(String stepIdent, String grpOfficer) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String stepFullIdent = this.getFoppPreFix() + stepIdent + this.getFoppPostfix();

		this.setFoppStaffs(this.initStaffList(new String[] {grpOfficer}));

		if(this.openFundingOppPlanner()) {

			this.selectFOPPStaff();

			ClicksUtil.clickButtons(IClicksConst.backBtn);

			this.openStepDetails(false, stepFullIdent);

			ie.selectList(id, IStepsConst.step_Proj_Officer_Group_Id).select(this.getFoppStaffs().get(0));

			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);		

			return true;			
		}
		return false;
	}




	public boolean changeAmendementStatusInStep(FundingOpportunity fopp, String stepIdent, String amendmentStatus) throws Exception{

		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}

		String stepFullIdent = this.getFoppPreFix() + stepIdent + this.getFoppPostfix();


		if (!openStepDetails(false, stepFullIdent)) {
			log.error("Could not open Step Details!");
			return false;
		}

		GeneralUtil.selectFullStringInDropdownListByTitle(IEFormsConst.applicantAmendment_Ttl,amendmentStatus );

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return true;

	}


	public boolean createNewFundingOpp() throws Exception {

		this.newFOPP = true;

		Assert.assertTrue(openFundingOppDetails(), "FAIL: Could not open Details Page For " + this.getFoppFullIdent());

		Assert.assertTrue(editFundingOpp(), "FAIL: Could not Create Funding Opp: " + this.getFoppFullIdent());

		Assert.assertTrue(GeneralUtil.isButtonExistsByValue(IClicksConst.openFundingOppPlannerBtn), "FAIL: Error could not find Funding Opp planner button!");

		ClicksUtil.clickButtons(IClicksConst.openFundingOppPlannerBtn);

		selectFOPPStaff();		
		ClicksUtil.clickButtons(IClicksConst.backBtn);

		selectFOPPAdmins();
		ClicksUtil.clickButtons(IClicksConst.backBtn);

		this.newFOPP = false;

		return true;
	}

	public boolean aditFundingOppTitle() throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_0).set(
				this.foppFullName);

		if (ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_1)
				.exists()) {
			ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_1).set(
					this.foppFullName);
		}

		if (ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_2)
				.exists()) {
			ie.textField(id, IProgramsConst.fundingOpp_Prog_Text_lacale_2).set(
					this.foppFullName);
		}

		return true;
	}

	public void aditFundingStepIdent(String stepIdent) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.textField(id, IStepsConst.step_Ident_Id).set(
				stepIdent);
	}


	public void aditFundingStepTitle(String stepName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		ie.textField(id, IStepsConst.step_Name_Locale_0_Id).set(
				stepName);

		if (ie.textField(id, IStepsConst.step_Name_Locale_1_Id)
				.exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_1_Id).set(
					stepName);
		}

		if (ie.textField(id, IStepsConst.step_Name_Locale_2_Id)
				.exists()) {
			ie.textField(id, IStepsConst.step_Name_Locale_2_Id).set(
					stepName);
		}

		return;
	}

	public boolean editFundingOpp() throws Exception {
		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(openFundingOppDetails(), "FAIL: Could not open Details Page For " + this.getFoppFullIdent());

		ie.textField(id,IProgramsConst.fundingOpp_prog_Ident).set(this.foppFullIdent);

		ie.textField(id, IProgramsConst.fundingOpp_Proj_Num_Template).set(this.projPrefix);

		if(null != this.primaryOrg) {
			selectPrimaryOrgAndAccess();
		}		

		Assert.assertTrue(selectAndDe_SelectDropdown(IProgramsConst.fundingOpp_PROG_FORM_SELECT,this.adminEFormName,this.adminEForm), "FAIL: Admin eForm set to: " + this.adminEForm);		

		Assert.assertTrue(selectAndDe_SelectDropdown(IProgramsConst.fundingOpp_PUBL_FORM_SELECT,this.pubEFormName,this.pubEForm), "FAIL: Publication eForm set to: " + this.pubEForm);

		ie.textField(id,IProgramsConst.fundingOpp_PROG_START_DATE).set(this.startDate);
		ie.textField(id,IProgramsConst.fundingOpp_PROG_END_DATE).set(this.endDate);

		ie.selectList(id,IProgramsConst.fundingOpp_PROG_MANAGER_MENU).select(this.foppOfficer);

		ie.textField(id,IProgramsConst.fundingOpp_PROG_REG_START_DATE).set(this.foppRegistrOpens);
		ie.textField(id,IProgramsConst.fundingOpp_PROG_REG_END_DATE).set(this.foppRegistrCloses);

		if(this.creationFOProjDisabled) {
			ie.checkbox(id,IProgramsConst.fundingOpp_PROG_FO_PROJ_CREATE).set();
		} else {
			ie.checkbox(id,IProgramsConst.fundingOpp_PROG_FO_PROJ_CREATE).clear();
		}

		if(this.completeAFRequired) {
			ie.checkbox(id,IProgramsConst.fundingOpp_PROG_FORM_COMPL_REC).set();
		} else {
			ie.checkbox(id,IProgramsConst.fundingOpp_PROG_FORM_COMPL_REC).clear();
		}

		if(!this.newFOPP) {

			Assert.assertTrue(selectAndDe_SelectDropdown("/considerAwardedAtStep/",this.awardedStepIdent,true),"FAIL: could not select Awarded step: " + this.awardedStepIdent);			
		}

		Assert.assertTrue(selectAndDe_SelectDropdown("/bringForwardFromFundingOpp/",this.bfFromFOPPIdent,true), "FAIL: could not select BF from Funding Opp: " + this.bfFromFOPPIdent);


		GeneralUtil.takeANap(1.000);

		if(this.bfAwardedProject) {
			ie.checkbox(id,"/bringForwardOnlyIfAwarded/").set();
		}else if(!this.bfFromFOPPIdent.equalsIgnoreCase("None")) {
			ie.checkbox(id,"/bringForwardOnlyIfAwarded/").clear();
		}

		Assert.assertTrue(aditFundingOppTitle(),"FAIL: could not add locale names: " + this.foppFullName);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		List<String> eSmall = GeneralUtil.checkForErrorMessages();

		if (eSmall != null && !eSmall.isEmpty())
		{
			for (String item : eSmall) {

				log.error("Validation Error: " + item);
			}

			return false;
		}		

		return true;
	}

	public boolean addNewStep() throws Exception {

		openFundingOppPlanner();

		for(Map.Entry<EStepsType,FOPPSteps> entry : this.setSteps) {

			openStepDetails(true, "Steps");

			editFundingStep(entry.getValue());

			addStepProperties(entry.getValue());

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			List<String> eSmall = GeneralUtil.checkForErrorMessages();

			if (eSmall != null && !eSmall.isEmpty())
			{								
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backBtn);

			expandAnObject("Steps");

			Assert.assertTrue(manageStep(entry.getValue()), "FAIL: errors occurs during Step Management");

			collapseAnObject(entry.getValue().getStepFullIdent());
		}

		return true;
	}

	public boolean manageStep(FOPPSteps step) throws Exception {

		log.info("Start Managing Step: " + step.getStepFullIdent());

		if(step.getStepMngmtSet() != null) {
			expandAnObject(FOPPStepsUtil.getStepParam(EStepParams.IDENT, step));

			for (Map.Entry<EstepManagment, List<String>> entry : step.getStepMngmtSet()) {

				if(FOPPUtil.isListNotEmpty(entry.getValue())) {				

					switch(entry.getKey()) {

					case stepStaff: {					
						manageStepStaff(step.getStepFullIdent(), entry.getValue());
						break;
					}
					case documents: {					
						manageStepDocuments(step.getStepFullIdent(), entry.getValue());
						break;
					}
					case notification: {
						break;
					}
					case formAccess: {
						break;
					}
					case formData: {					
						manageEFormData(step);
						break;
					}
					case stepStatusLabels: {
						break;
					}
					case submissionStatusLabels: {
						break;
					}
					default:
						break;				
					}

					List<String> errSmall = GeneralUtil.checkForErrorMessages();

					if(null != errSmall && !errSmall.isEmpty()) {

						return false;
					}

					ClicksUtil.clickButtons(IClicksConst.backBtn);
				}			
			}

		}		

		return true;
	}

	public void editFundingStep(FOPPSteps step) throws Exception {

		IE ie = IEUtil.getActiveIE();

		for (Map.Entry<EStepParams, Object> entry : step.getStepParamsSet()) {

			switch(entry.getKey()) {

			case IDENT: {

				ie.textField(id,IStepsConst.step_Ident_Id).set(entry.getValue().toString());

				break;
			}
			case NOTES: {

				break;
			}
			case TYPE: {

				if(ie.selectList(id,IStepsConst.step_Type_Id).exists()) {

					ie.selectList(id, IStepsConst.step_Type_Id).select(entry.getValue().toString());			
				}

				break;
			}
			case OFFICER: {

				ie.selectList(id, IStepsConst.step_Proj_Officer_Group_Id).select(entry.getValue().toString());

				break;
			}
			case STARTDATE: {

				ie.textField(id,IStepsConst.step_Start_Date_Id).set(entry.getValue().toString());

				break;
			}
			case ENDDATE: {

				ie.textField(id,IStepsConst.step_End_Date_Id).set(entry.getValue().toString());

				break;
			}
			case CRITICAL: {

				if((Boolean)entry.getValue()) {
					ie.checkbox(id,IStepsConst.step_Critical_Step_Id).set();
				} else {
					ie.checkbox(id,IStepsConst.step_Critical_Step_Id).clear();
				}

				break;
			}
			case RE_EXECUTE: {

				ie.selectList(id,IStepsConst.step_ReExecute_Id).select(entry.getValue().toString());

				break;
			}
			case TITLE: {

				aditFundingStepTitle(entry.getValue().toString());			
				break;
			}
			default:
				break;
			}

		}

		/*if(ie.selectList(id,IStepsConst.step_Type_Id).exists()) {

			ie.selectList(id, IStepsConst.step_Type_Id).select(step.getStepType());			
		}

		ie.textField(id,IStepsConst.step_Ident_Id).set(step.getStepFullIdent());

		ie.selectList(id, IStepsConst.step_Proj_Officer_Group_Id).select(step.getStepOfficerGrp());

		ie.textField(id,IStepsConst.step_Start_Date_Id).set(step.getStepStartDate());

		ie.textField(id,IStepsConst.step_End_Date_Id).set(step.getStepEndDate());

		if(step.isStepCritical()) {
			ie.checkbox(id,IStepsConst.step_Critical_Step_Id).set();
		} else {
			ie.checkbox(id,IStepsConst.step_Critical_Step_Id).clear();
		}

		aditFundingStepTitle(step.getStepFullName());*/		

		return;		
	}

	public void addStepProperties(FOPPSteps step) throws Exception {		

		IE ie = IEUtil.getActiveIE();

		for (Map.Entry<EStepProps, Object> entry : step.getStepPropsSet()) {

			switch(entry.getKey()) {

			case EFORM_IDENT: {

				ie.selectList(id,IStepsConst.step_Prop_EFormIdent_Id).select(entry.getValue().toString());
				break;
			}			
			case EVAL_TYPE: {
				ie.selectList(id,IStepsConst.step_Prop_EvaluationType_Id).select(entry.getValue().toString());
				break;				
			}			
			case QUOROM: {

				ie.textField(id,IStepsConst.step_Prop_QuorumAmount_Id).set(entry.getValue().toString());
				break;				
			}
			case ASSIGN: {

				if(!ie.checkbox(id,IStepsConst.step_Prop_AutoAssign_Id).disabled() && Boolean.parseBoolean(entry.getValue().toString())) {					
					ie.checkbox(id,IStepsConst.step_Prop_AutoAssign_Id).set();

				}else if(!ie.checkbox(id,IStepsConst.step_Prop_AutoAssign_Id).disabled() && !Boolean.parseBoolean(entry.getValue().toString())) {					
					ie.checkbox(id,IStepsConst.step_Prop_AutoAssign_Id).clear();
				}
				break;				
			}
			case COI: {

				if(step.isStepCOIRules()) {
					ie.checkbox(id,IStepsConst.step_Prop_COI_Id).set();
				} else {
					ie.checkbox(id,IStepsConst.step_Prop_COI_Id).clear();
				}
				break;				
			}
			case AWARD_STEP_IDENT: {

				ie.selectList(id,IStepsConst.step_Prop_AwardStepName_Id).select(entry.getValue().toString());
				break;				
			}
			case IPASS_EFORM_IDENT1: {

				findInM2M(IStepsConst.step_Prop_IPASS_M2M_Available_Id, entry.getValue().toString());
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				break;				
			}
			case IPASS_EFORM_IDENT2: {

				findInM2M(IStepsConst.step_Prop_IPASS_M2M_Available_Id, entry.getValue().toString());
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				break;				
			}
			case IPASS_EFORM_IDENT3: {

				findInM2M(IStepsConst.step_Prop_IPASS_M2M_Available_Id, entry.getValue().toString());
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				break;				
			}
			case FROMSTEP: {

				selectAndDe_SelectDropdown(IStepsConst.step_Prop_Decision_DataFromStep_Id,entry.getValue().toString(),true);
				break;				
			}
			case SKIPSTEP: {

				selectAndDe_SelectDropdown(IStepsConst.step_Prop_Decision_DataFromStep_Id,entry.getValue().toString(),true);
				break;				
			}
			case EXPRESSION: {

				ie.textField(id, IStepsConst.step_Prop_Decision_Expression_Id).set(entry.getValue().toString());
				break;				
			}
			case WSSSTEP1: {

				findInM2M(IStepsConst.step_Prop_WSS_M2M_Available_Id, entry.getValue().toString());
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);	
				break;				
			}
			case WSSSTEP2: {

				findInM2M(IStepsConst.step_Prop_WSS_M2M_Available_Id, entry.getValue().toString());
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);	
				break;					
			}					
			}			
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public void manageStepStaff(String stepName, List<String> staffLst) throws Exception {	

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(openStepManagementDetails(stepName, "Step Staff"), "FAIL: could not open Step Staff Details");

		for (String string : staffLst) {

			if(!findInM2M(IProgStepsConst.stepStaff_SelectedStaff_Id, "G: ".concat(string)))
			{
				ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(string);

				ClicksUtil.clickButtons(IClicksConst.searchBtn);

				GeneralUtil.takeANap(1.000);

				Assert.assertTrue(findInM2M(IProgStepsConst.stepStaff_AvailableStaff_Id, "G: ".concat(string)), "FAIL: step Staff not Available: " + string);	

				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);	

				GeneralUtil.takeANap(1.000);			
			}			
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public void openById(List<String> staffLst, String imgId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinksById(imgId);

		for (String string : staffLst) {

			if(!findInM2M(IProgStepsConst.stepStaff_SelectedStaff_Id,  "G: ".concat(string)))
			{
				ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(string);

				ClicksUtil.clickButtons(IClicksConst.searchBtn);

				GeneralUtil.takeANap(1.000);

				Assert.assertTrue(findInM2M(IProgStepsConst.stepStaff_AvailableStaff_Id, "G: ".concat(string)), "FAIL: step Staff not Available: " + string);	

				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);	

				GeneralUtil.takeANap(1.000);			
			}			
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public void manageStepStaff(List<String> staffLst, boolean removeStaff) throws Exception {	

		//String step = getFullStepIdent();

		if(!removeStaff)
		{
			manageStepStaff(this.getCurrentStepIdent(), staffLst);

			return;
		}

		Assert.assertTrue(openStepManagementDetails(this.getCurrentStepIdent(), "Step Staff"), "FAIL: could not open Step Staff Details");

		for (String string : staffLst) {

			if(findInM2M(IProgStepsConst.stepStaff_SelectedStaff_Id, "G: ".concat(string)))
			{

				ClicksUtil.clickButtons(IClicksConst.m2MSingleBackBtn);
				GeneralUtil.takeANap(1.0);
			}		
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public void manageEFormData(FOPPSteps step) throws Exception {

		Assert.assertTrue(openStepManagementDetails(step.getStepIdent(), "e.Form Data"), "FAIL: could not open e.Form Data Details for Step: " + step.getStepFullIdent());

		selectAndDe_SelectDropdown("main:stepFormData:formDataForm:applicantForm",step.getIncludeAppProfile(),true);

		selectAndDe_SelectDropdown("main:stepFormData:formDataForm:programForm",step.getIncludeAdminEForm(),true);

		for (String string : step.getStepEFormDataLst()) {

			Assert.assertTrue(findInM2M("main:stepFormData:formDataForm:availableForms", string), "FAIL: eForm not Available: " + string);

			ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);			
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);	
	}

	public void manageStepDocuments(String stepName, List<String> docsLst) throws Exception {

		Assert.assertTrue(openStepManagementDetails(stepName, "Documents"), "FAIL: could not open e.Form Data Details");

		for (String string : docsLst) {

			Assert.assertTrue(findInM2M("main:stepDocument:documentAssociate:availableDocument", string), "FAIL: Documents Not Available:" + docsLst);

			ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);			
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);		
	}



	/************** End of Class Methods *************/

	/********* Getters and Setters *************/

	/**
	 * @return the foppLetter
	 */
	public String getFoppLetter() {
		return foppLetter;
	}

	/**
	 * @param foppLetter the foppLetter to set
	 */
	public void setFoppLetter(String foppLetter) {
		this.foppLetter = foppLetter;
	}

	/**
	 * @return the foppPreFix
	 */
	public String getFoppPreFix() {
		return foppPreFix;
	}

	/**
	 * @param foppPreFix the foppPreFix to set
	 */
	public void setFoppPreFix(String foppPreFix) {
		this.foppPreFix = foppPreFix;
	}

	/**
	 * @return the foppPostfix
	 */
	public String getFoppPostfix() {
		return foppPostfix;
	}

	/**
	 * @param foppPostfix the foppPostfix to set
	 */
	public void setFoppPostfix(String foppPostfix) {
		this.foppPostfix = foppPostfix;
	}

	/**
	 * @return the foppPortal
	 */
	public char getFoppPortal() {
		return foppPortal;
	}

	/**
	 * @param foppPortal the foppPortal to set
	 */
	public void setFoppPortal(char foppPortal) {
		this.foppPortal = foppPortal;
	}

	/**
	 * @return the newFOPP
	 */
	public boolean isNewFOPP() {
		return newFOPP;
	}

	/**
	 * @param newFOPP the newFOPP to set
	 */
	public void setNewFOPP(boolean newFOPP) {
		this.newFOPP = newFOPP;
	}

	/**
	 * @return the foppName
	 */
	public String getFoppName() {
		return foppName;
	}

	/**
	 * @param foppName the foppName to set
	 */
	public void setFoppName(String foppName) {
		this.foppName = foppName;
	}

	/**
	 * @return the foppIdent
	 */
	public String getFoppIdent() {
		return foppIdent;
	}

	/**
	 * @param foppIdent the foppIdent to set
	 */
	public void setFoppIdent(String foppIdent) {
		this.foppIdent = foppIdent;
	}

	/**
	 * @return the foppFullIdent
	 */
	public String getFoppFullIdent() {
		return foppFullIdent;
	}

	/**
	 * @param foppFullIdent the foppFullIdent to set
	 */
	public void setFoppFullIdent(String foppFullIdent) {
		this.foppFullIdent = foppFullIdent;
	}

	/**
	 * @return the foppFullName
	 */
	public String getFoppFullName() {
		return foppFullName;
	}

	/**
	 * @param foppFullName the foppFullName to set
	 */
	public void setFoppFullName(String foppFullName) {
		this.foppFullName = foppFullName;
	}

	/**
	 * @return the adminEForm
	 */
	public boolean isAdminEForm() {
		return adminEForm;
	}

	/**
	 * @param adminEForm the adminEForm to set
	 */
	public void setAdminEForm(boolean adminEForm) {
		this.adminEForm = adminEForm;
	}

	/**
	 * @return the pubEForm
	 */
	public boolean isPubEForm() {
		return pubEForm;
	}

	/**
	 * @param pubEForm the pubEForm to set
	 */
	public void setPubEForm(boolean pubEForm) {
		this.pubEForm = pubEForm;
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
	 * @return the addStepIndex
	 */
	public Integer getAddStepIndex() {
		return addStepIndex;
	}

	/**
	 * @param addStepIndex the addStepIndex to set
	 */
	public void setAddStepIndex(Integer addStepIndex) {
		this.addStepIndex = addStepIndex;
	}

	/**
	 * @return the subStepsIndex
	 */
	public Integer getSubStepsIndex() {
		return subStepsIndex;
	}

	/**
	 * @param subStepsIndex the subStepsIndex to set
	 */
	public void setSubStepsIndex(Integer subStepsIndex) {
		this.subStepsIndex = subStepsIndex;
	}

	/**
	 * @return the foppOfficers
	 */
	public List<String> getFoppStaffs() {
		return foppStaffs;
	}

	/**
	 * @param foppOfficers the foppOfficers to set
	 */
	public void setFoppStaffs(List<String> foppStaffs) {
		this.foppStaffs = foppStaffs;
	}

	/**
	 * @return the foppAdmins
	 */
	public List<String> getFoppAdmins() {
		return foppAdmins;
	}

	/**
	 * @param foppAdmins the foppAdmins to set
	 */
	public void setFoppAdmins(List<String> foppAdmins) {
		this.foppAdmins = foppAdmins;
	}

	/**
	 * @return the foppSteps
	 */
	public LinkedHashMap<EStepsType, FOPPSteps> getFoppSteps() {
		return foppSteps;
	}

	//	public void setProgOfficers(String[] progOfficers) {
	//		this.progOfficers = progOfficers;
	//	}

	/**
	 * @param foppSteps the foppSteps to set
	 */
	public void setFoppSteps(LinkedHashMap<EStepsType, FOPPSteps> foppSteps) {
		this.foppSteps = foppSteps;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the adminEFormName
	 */
	public String getAdminEFormName() {
		return adminEFormName;
	}

	/**
	 * @param adminEFormName the adminEFormName to set
	 */
	public void setAdminEFormName(String adminEFormName) {
		this.adminEFormName = adminEFormName;
	}

	/**
	 * @return the pubEFormName
	 */
	public String getPubEFormName() {
		return pubEFormName;
	}

	/**
	 * @param pubEFormName the pubEFormName to set
	 */
	public void setPubEFormName(String pubEFormName) {
		this.pubEFormName = pubEFormName;
	}

	/**
	 * @return the foppOfficer
	 */
	public String getFoppOfficer() {
		return foppOfficer;
	}

	/**
	 * @param foppOfficer the foppOfficer to set
	 */
	public void setFoppOfficer(String foppOfficer) {
		this.foppOfficer = foppOfficer;
	}

	/**
	 * @return the foppStatus
	 */
	public String getFoppStatus() {
		return foppStatus;
	}

	/**
	 * @param foppStatus the foppStatus to set
	 */
	public void setFoppStatus(String foppStatus) {
		this.foppStatus = foppStatus;
	}

	/**
	 * @return the foppRegistrOpens
	 */
	public String getFoppRegistrOpens() {
		return foppRegistrOpens;
	}

	/**
	 * @param foppRegistrOpens the foppRegistrOpens to set
	 */
	public void setFoppRegistrOpens(String foppRegistrOpens) {
		this.foppRegistrOpens = foppRegistrOpens;
	}

	/**
	 * @return the foppRegistrOpenHH
	 */
	public String getFoppRegistrOpenHH() {
		return foppRegistrOpenHH;
	}

	/**
	 * @param foppRegistrOpenHH the foppRegistrOpenHH to set
	 */
	public void setFoppRegistrOpenHH(String foppRegistrOpenHH) {
		this.foppRegistrOpenHH = foppRegistrOpenHH;
	}

	/**
	 * @return the foppRegistrOpenMM
	 */
	public String getFoppRegistrOpenMM() {
		return foppRegistrOpenMM;
	}

	/**
	 * @param foppRegistrOpenMM the foppRegistrOpenMM to set
	 */
	public void setFoppRegistrOpenMM(String foppRegistrOpenMM) {
		this.foppRegistrOpenMM = foppRegistrOpenMM;
	}

	/**
	 * @return the foppRegistrCloses
	 */
	public String getFoppRegistrCloses() {
		return foppRegistrCloses;
	}

	/**
	 * @param foppRegistrCloses the foppRegistrCloses to set
	 */
	public void setFoppRegistrCloses(String foppRegistrCloses) {
		this.foppRegistrCloses = foppRegistrCloses;
	}

	/**
	 * @return the foppRegistrCloseHH
	 */
	public String getFoppRegistrCloseHH() {
		return foppRegistrCloseHH;
	}

	/**
	 * @param foppRegistrCloseHH the foppRegistrCloseHH to set
	 */
	public void setFoppRegistrCloseHH(String foppRegistrCloseHH) {
		this.foppRegistrCloseHH = foppRegistrCloseHH;
	}

	/**
	 * @return the foppRegistrCloseMM
	 */
	public String getFoppRegistrCloseMM() {
		return foppRegistrCloseMM;
	}

	/**
	 * @param foppRegistrCloseMM the foppRegistrCloseMM to set
	 */
	public void setFoppRegistrCloseMM(String foppRegistrCloseMM) {
		this.foppRegistrCloseMM = foppRegistrCloseMM;
	}

	/**
	 * @return the currentStepName
	 */
	public String getCurrentStepName() {
		return currentStepName;
	}

	/**
	 * @param currentStepName the currentStepName to set
	 */
	public void setCurrentStepName(String currentStepName) {
		this.currentStepName = currentStepName;
	}

	/**
	 * @return the fullStepName
	 */
	public String getFullStepName() {
		return fullStepName;
	}

	/**
	 * @param fullStepName the fullStepName to set
	 */
	public void setFullStepName(String fullStepName) {
		this.fullStepName = fullStepName;
	}

	/**
	 * @return the currentStepIdent
	 */
	public String getCurrentStepIdent() {
		return currentStepIdent;
	}

	/**
	 * @param currentStepIdent the currentStepIdent to set
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
	 * @param fullStepIdent the fullStepIdent to set
	 */
	public void setFullStepIdent(String fullStepIdent) {
		this.fullStepIdent = fullStepIdent;
	}

	/**
	 * @return the tempFullStepIdent
	 */
	public String getTempFullStepIdent() {
		return tempFullStepIdent;
	}

	/**
	 * @param tempFullStepIdent the tempFullStepIdent to set
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
	 * @param tempFullStepName the tempFullStepName to set
	 */
	public void setTempFullStepName(String tempFullStepName) {
		this.tempFullStepName = tempFullStepName;
	}

	/**
	 * @return the skipToStepIdent
	 */
	public String getSkipToStepIdent() {
		return skipToStepIdent;
	}

	/**
	 * @param skipToStepIdent the skipToStepIdent to set
	 */
	public void setSkipToStepIdent(String skipToStepIdent) {
		this.skipToStepIdent = skipToStepIdent;
	}

	/**
	 * @return the creationFOProjDisabled
	 */
	public boolean isCreationFOProjDisabled() {
		return creationFOProjDisabled;
	}

	/**
	 * @param creationFOProjDisabled the creationFOProjDisabled to set
	 */
	public void setCreationFOProjDisabled(boolean creationFOProjDisabled) {
		this.creationFOProjDisabled = creationFOProjDisabled;
	}

	/**
	 * @return the completeAFRequired
	 */
	public boolean isCompleteAFRequired() {
		return completeAFRequired;
	}

	/**
	 * @param completeAFRequired the completeAFRequired to set
	 */
	public void setCompleteAFRequired(boolean completeAFRequired) {
		this.completeAFRequired = completeAFRequired;
	}

	/**
	 * @return the awardedStepIdent
	 */
	public String getAwardedStepIdent() {
		return awardedStepIdent;
	}

	/**
	 * @param awardedStepIdent the awardedStepIdent to set
	 */
	public void setAwardedStepIdent(String awardedStepIdent) {
		this.awardedStepIdent = awardedStepIdent;
	}

	/**
	 * @return the bfFromFOPPIdent
	 */
	public String getBfFromFOPPIdent() {
		return bfFromFOPPIdent;
	}

	/**
	 * @param bfFromFOPPIdent the bfFromFOPPIdent to set
	 */
	public void setBfFromFOPPIdent(String bfFromFOPPIdent) {
		this.bfFromFOPPIdent = bfFromFOPPIdent;
	}

	/**
	 * @return the bfAwardedProject
	 */
	public boolean isBfAwardedProject() {
		return bfAwardedProject;
	}

	/**
	 * @param bfAwardedProject the bfAwardedProject to set
	 */
	public void setBfAwardedProject(boolean bfAwardedProject) {
		this.bfAwardedProject = bfAwardedProject;
	}

	/**
	 * @return the primaryOrg
	 */
	public String getPrimaryOrg() {
		return primaryOrg;
	}

	/**
	 * @param primaryOrg the primaryOrg to set
	 */
	public void setPrimaryOrg(String primaryOrg) {
		this.primaryOrg = primaryOrg;
	}

	/**
	 * @return the orgAccess
	 */
	public String getOrgAccess() {
		return orgAccess;
	}

	/**
	 * @param orgAccess the orgAccess to set
	 */
	public void setOrgAccess(String orgAccess) {
		this.orgAccess = orgAccess;
	}

	/**
	 * @return the projPrefix
	 */
	public String getProjPrefix() {
		return projPrefix;
	}

	/**
	 * @param projPrefix the projPrefix to set
	 */
	public void setProjPrefix(String projPrefix) {
		this.projPrefix = projPrefix;
	}

	/**
	 * @return the setSteps
	 */
	public Set<Map.Entry<EStepsType, FOPPSteps>> getSetSteps() {
		return setSteps;
	}

	/**
	 * @param setSteps the setSteps to set
	 */
	public void setSetSteps(Set<Map.Entry<EStepsType, FOPPSteps>> setSteps) {
		this.setSteps = setSteps;
	}

}
