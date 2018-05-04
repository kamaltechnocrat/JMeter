/**
 * 
 */
package test_Suite.utils.workflow;


import static watij.finders.SymbolFactory.*;

import java.util.*;

import org.apache.commons.logging.*;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.workflow.IBf_FoppConst.EPostFix;
import test_Suite.constants.workflow.*;
import test_Suite.constants.workflow.IFoppConst.*;
import test_Suite.constants.workflow.IProgramsConst.EstepManagment;
import test_Suite.constants.workflow.IStepsConst.*;
import test_Suite.constants.workflow.IStepsConst.EStepsType;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.*;
import watij.dialogs.*;
import watij.elements.*;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class FOPPUtil {

	private static Log log = LogFactory.getLog(FOPPUtil.class);

	static Tables tables;
	static Table table;
	static int newIndex;
	static boolean retValue;
	
	public static FundingOpportunity getNewFopp(String preFix, String postFix) throws Exception {
		
		String letter = EFormsUtil.createAnyRandomString(5);
		
		return new FundingOpportunity(letter,preFix,postFix);
	}
	
	public static FundingOpportunity initExistingFopp(EPostFix ePostFix) throws Exception {
		
		return new FundingOpportunity(ePostFix);		
	}
	
	public static FOPPSteps getStep(FundingOpportunity fopp,String[][] stepDblArr, EStepsType eStepType) throws Exception {
		
		return new FOPPSteps(fopp,stepDblArr,eStepType);		
	}
	
	public static boolean isListNotEmpty(List<String> lst) throws Exception {
		
		if(null != lst && !lst.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	public static void initStepStaffList(FOPPSteps step,String[] staffs) throws Exception {
		
		List<String> lst = new ArrayList<String>();
		
		for (String string : staffs) {
			
			lst.add(string);			
		}	
		
		step.setStepStaffsGrpsLst(lst);
	}
	
	public static void initEFormData(FOPPSteps step,FundingOpportunity srcFopp, String[] srcSteps, String[] trgtSteps) throws Exception {
		
		List<String> lst = new ArrayList<String>();
		
		int x = 0;
		
		if(null != srcSteps && srcSteps.length != 0) {
			
			for (String string : srcSteps) {		
				
				String str = srcFopp.getFoppFullIdent()
							+ " --> " + srcFopp.getFoppLetter()
							+ srcFopp.getFoppPreFix()
							+ string
							+ srcFopp.getFoppPostfix();
				
				lst.add(x,str);
				x++;				
			}
		}
		
		if(null != trgtSteps && trgtSteps.length != 0) {
			
			for (String string : trgtSteps) {
				
				String str = step.getFopp().getFoppLetter() 
							+ step.getFopp().getFoppPreFix()
							+ string
							+ step.getFopp().getFoppPostfix();
				
				lst.add(x,str);
				x++;				
			}
		}
		
		step.setStepEFormDataLst(lst);		
	}
	
	public static void initializeStepMngmt(FOPPSteps step) throws Exception {
		
		LinkedHashMap<EstepManagment, List<String>> lhmStepMngmt = new LinkedHashMap<EstepManagment, List<String>>();
		
		lhmStepMngmt.put(EstepManagment.stepStaff, step.getStepStaffsGrpsLst());
		lhmStepMngmt.put(EstepManagment.documents, step.getStepDocumentsLst());
		lhmStepMngmt.put(EstepManagment.formAccess, step.getStepEFormAccessLst());
		lhmStepMngmt.put(EstepManagment.formData, step.getStepEFormDataLst());
		lhmStepMngmt.put(EstepManagment.stepStatusLabels, step.getStepStatusLabelLst());
		lhmStepMngmt.put(EstepManagment.submissionStatusLabels, step.getStepSubStatusLableLst());
		
		step.setStepMngmtLHM(lhmStepMngmt);
		
		step.setStepMngmtSet(step.getStepMngmtLHM().entrySet());
	}
	
	public EGeneralStepType getGeneralStepType(EStepsType eStepType) throws Exception {
		
		switch(eStepType) {
		
			case SUB: {
				return EGeneralStepType.SUBMISSION;			
			}
			
			case POSS: {
				return EGeneralStepType.SUBMISSION;
			}
			
			case AWARD: {
				return EGeneralStepType.SUBMISSION;
			}
			
			case REVIEW: {			
				return EGeneralStepType.EVALUATION;
			}
			
			case APPROVAL: {			
				return EGeneralStepType.EVALUATION;
			}
			
			case DECISION: {			
				return EGeneralStepType.DECISION;
			}
			
			case WSS: {			
				return EGeneralStepType.WSS;
			}
			
			case POST_AWARD: {			
				return EGeneralStepType.POSTAWARD;
			}
			
			case IPASS: {			
				return EGeneralStepType.IPASS;
			}
		default:
				
			break;
		}
		
		return EGeneralStepType.NONE;
	}

	public static boolean changeStepStartDate(FundingOpportunity fopp,
			String stepIdent, String dateToChange) throws Exception {

		if (!fopp.openFundingOppPlanner()) {
			log.error("Could not open Funding Opp planner!");
			return false;
		}

		String stepFullIdent = fopp.getFoppPreFix() + stepIdent
				+ fopp.getFoppPostfix();

		if (!fopp.openStepDetails(false, stepFullIdent)) {
			log.error("Could not open Step Details!");
			return false;
		}

		if (!GeneralUtil.setTextById(IStepsConst.step_Start_Date_Id,
				dateToChange)) {
			log.error("Step Start Date field doesn't found and could not set Start Date");
			return false;
		}

		ClicksUtil.checkForErrorMessages();

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return true;
	}

	public static boolean changePAStepStartDate(FundingOpportunity fopp,
			String stepIdent, String dateToChange) throws Exception {
		boolean matched = false;
		IE ie = IEUtil.getActiveIE();

		retValue = true;

		if (!fopp.openFundingOppPlanner()) {
			log.error("Could not open Funding Opp planner!");
			return false;
		}

		fopp.expandAnObject("Steps");
		
		Spans spans = ie.div(id, "j_id_1v:programPlannerTree").spans();
		
		int i = 0;
		
		for (Span span : spans) {
			
			if(span.innerText().endsWith("(Post-Award)"))
			{
				matched = true;
				ProgramUtil.tuggleObjectInProgPlanner(i, true);
				newIndex = i + 6;
				ProgramUtil.tuggleObjectInProgPlanner(newIndex, true);
				break;
			}
			
			i+=1;
			
		}
		
		if (!matched) {
			log.error("Could not open Post Award Step!");
			return false;
		}

		String stepFullIdent = fopp.getFoppPreFix() + stepIdent
				+ fopp.getFoppPostfix();

		if (!fopp.openStepDetails(false, stepFullIdent)) {
			log.error("Could not open Step Details!");
			return false;
		}

		if (!GeneralUtil.setTextById(IStepsConst.step_Start_Date_Id,
				dateToChange)) {
			log.error("Step Start Date field doesn't found and could not set Start Date");
			return false;
		}

		ClicksUtil.checkForErrorMessages();

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return true;
	}

	public static boolean changePAStepEndDate(FundingOpportunity fopp,
			String stepIdent, String dateToChange) throws Exception {
		boolean matched = false;
		IE ie = IEUtil.getActiveIE();

		retValue = true;

		if (!fopp.openFundingOppPlanner()) {
			
			log.error("Could not open Funding Opp planner!");
			
			return false;
			
		}

		fopp.expandAnObject("Steps");
		
		Spans spans = ie.div(id, "j_id_1v:programPlannerTree").spans();
		
		int i = 0;
		
		for (Span span : spans) {
			
			if(span.innerText().endsWith("(Post-Award)"))
			{
				matched = true;
				ProgramUtil.tuggleObjectInProgPlanner(i, true);
				newIndex = i + 6;
				ProgramUtil.tuggleObjectInProgPlanner(newIndex, true);
				break;
			}
			
			i+=1;
			
		}
	
		
		if (!matched) {
			log.error("Could not open Post Award Step!");
			return false;
		}

		String stepFullIdent = fopp.getFoppPreFix() + stepIdent
				+ fopp.getFoppPostfix();

		if (!fopp.openStepDetails(false, stepFullIdent)) {
			log.error("Could not open Step Details!");
			return false;
		}

		if (!GeneralUtil
				.setTextById(IStepsConst.step_End_Date_Id, dateToChange)) {
			log.error("Step End Date field doesn't found and could not set End Date");
			return false;
		}

		ClicksUtil.checkForErrorMessages();

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return true;
	}

	public static boolean changeStepEndDate(FundingOpportunity fopp,
			String stepIdent, String dateToChange) throws Exception {

		if (!fopp.openFundingOppPlanner()) {
			log.error("Could not open Funding Opp planner!");
			return false;
		}

		String stepFullIdent = fopp.getFoppPreFix() + stepIdent
				+ fopp.getFoppPostfix();

		if (!fopp.openStepDetails(false, stepFullIdent)) {
			log.error("Could not open Step Details!");
			return false;
		}

		if (!GeneralUtil
				.setTextById(IStepsConst.step_End_Date_Id, dateToChange)) {
			log.error("Step End Date field doesn't found and could not set End Date");
			return false;
		}

		ClicksUtil.checkForErrorMessages();

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		return true;
	}
	
	public static boolean cloneFOPP(FundingOpportunity fopp) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		Hashtable<String, String> textHashTable = new Hashtable<String, String>();
		Hashtable<String, String> dropdownHashTable = new Hashtable<String, String>();
		
		textHashTable.put(IFiltersConst.gpa_FundingOppIdent_Lbl, fopp.getFoppFullIdent());
		
		dropdownHashTable.put(IFiltersConst.gpa_FundingOppIdent_Lbl, IFiltersConst.exact);
		
		FiltersUtil.filterListByLabel(textHashTable,dropdownHashTable,false);
		
		int count = TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_programsTableId);
		
		if (count <= 0)
		{
			log.error(fopp.getFoppFullIdent().concat(", is not visible in this list!! "));
			
			return false;
		}
		
		Assert.assertTrue(TablesUtil.openInTableByImageAlt(ITablesConst.fundingOpp_programsTableId, fopp.getFoppFullIdent(), "Clone ".concat(fopp.getFoppFullIdent())),"FAIL: could not clone FOPP: " + fopp.getFoppFullIdent());
		

		return true;
	}
	
	public static String verifyClonedFOPP(FundingOpportunity fopp) throws Exception {
		
		FiltersUtil.filterListByLabel(IFiltersConst.gpa_FundingOppIdent_Lbl, fopp.getFoppFullIdent() + " Clone", IFiltersConst.startsWith);
		
		int count = TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_programsTableId);
		
		if(count <= 0)
		{
			log.error("Could not find the new cloned FOPP in List!");
			return null;
		}
		
		return TablesUtil.getCellContent(ITablesConst.fundingOpp_programsTableId, count - 1, 1,0);
	}

	
	public static boolean importFOPP(String foppXml) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		if(!ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.openFundingOppsListLnk));
			return false;
		} 
		else
		log.info("clicked FOPP link");

		if(!ClicksUtil.clickImage(IClicksConst.imprtImg))
		{
			log.error("Could not find Import Image!");
			return false;
		}
		else
		log.info("clicked import image");
		
		if(!ie.fileField(0).exists())
		{
			log.error("Could not find File Field!");
			return false;
		}

		ie.fileField(0).set("\"".concat(GeneralUtil.getWorkspacePath()).concat(IGeneralConst.xmlFilesPath).concat(foppXml).concat("\""));

		ClicksUtil.clickButtons(IClicksConst.uploadBtn);
		
		ArrayList<String> arrList = GeneralUtil.checkForErrorMessages();
		
		for (String string : arrList) {
			
			if(!string.startsWith("Successfully imported"))
			{
				log.error("Could not import the FOPP becuase of this: ".concat(string));
				
				return false;
			}
			
		}
		return true;
	}
	
	public static String verifyImportedFOPP(FundingOpportunity fopp) throws Exception {
		
		FiltersUtil.filterListByLabel(IFiltersConst.gpa_FundingOppIdent_Lbl, fopp.getFoppFullIdent(), IFiltersConst.exact);
		
		int count = TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_programsTableId);
		
		if(count <= 0)
		{
			log.error("Could not find the new cloned FOPP in List!");
			return null;
		}
		
		return TablesUtil.getCellContent(ITablesConst.fundingOpp_programsTableId, count - 1, 3,0);
	}
	
	public static boolean changeFoppStatusAndHideProjects(FundingOpportunity fopp,E3StatesFopp state, boolean hideProj) throws Exception {

    	IE ie = IEUtil.getActiveIE();
    	
		if(!fopp.openFundingOppDetails())
		{
			log.error("ERROR Opening Funding Opp Details!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IFoppConst.fundingOpp_DetailsPage_FoppStatus_DropDown_Id, IFoppConst.threeStates[state.ordinal()]))
		{			
			log.error("ERROR Selecting Fopp Status!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		if(!fopp.openProjectConfigDetails())
		{
			log.error("Error occurs while openning Project Configuration Node in Fopp planner!");
			return false;
		}
		
		if(hideProj)
		{
			ie.checkbox(id,IFoppConst.fundingOpp_DetailsPage_HistoricalProjs_Checkbox_Id).set();
		}
		else
		{
			ie.checkbox(id,IFoppConst.fundingOpp_DetailsPage_HistoricalProjs_Checkbox_Id).clear();
		}
		
		
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backToFoppPlannerBtn);
		
		if(!fopp.isThisFOPP_Planner())
		{
			log.error("ERROR Saving the changes to 3 State!");
			return false;
		}		
		
		return true;		
	}
	
	public static boolean changeExpandedControlLevel(FundingOpportunity fopp, EExpCtrlLevels level) throws Exception {
		
		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}
		
		if(!fopp.openProjectConfigDetails())
		{
			log.error("could not open Project Configuration!");
			return false;
		}
		
//		Thread dialogClickerG3 = new Thread() {
//			@Override
//			public void run() {
//				try {
//					
//					IE ie = IEUtil.getActiveIE();
//					
//					AlertDialog dialogG3 = ie.alertDialog();
//					
//					while (dialogG3 == null) {
//						
//						log.debug("can't yet get handle on confirm dialog1");
//						
//						GeneralUtil.takeANap(0.250);
//					}
//
//					dialogG3.ok();
//					
//					log.debug("got confirm Dialog1 and clicked OK.");
//
//				} catch (Exception e) {
//					throw new RuntimeException("Unexpected exception", e);
//				}
//			}
//		};

		//dialogClickerG3.start();
		
		if(!GeneralUtil.selectFullStringInDropdownList(IFoppConst.fundingOpp_DetailsPage_ControlLevel_DropDown_Id, IFoppConst.expandedControlLevels[level.ordinal()]))
		{			
			log.error("ERROR Selecting Control Level!");
			return false;
		}
		
		GeneralUtil.takeANap(1.000);
		
		//dialogClickerG3 = null;
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backToFoppPlannerBtn);
		
		if(!fopp.isThisFOPP_Planner())
		{
			log.error("ERROR Saving the changes to Control Level!");
			return false;
		}		
		return true;
	}
	
	public static boolean configureDuplicateProjects(FundingOpportunity fopp, EDuplicateProjs prmis) throws Exception {
		
		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}
		
		if(!fopp.openProjectConfigDetails())
		{
			log.error("could not open Project Configuration!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IFoppConst.fundingOpp_DetailsPage_DuplicateProjects_DropDown_Id, IFoppConst.duplicateProjs[prmis.ordinal()]))
		{			
			log.error("ERROR Selecting Duplicate Projects Permission!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backToFoppPlannerBtn);
		
		if(!fopp.isThisFOPP_Planner())
		{
			log.error("ERROR Saving the changes to Duplicate Projects Permission!");
			return false;
		}		
		return true;
	}
	
	public static boolean openProjectConfigOnly(FundingOpportunity fopp) throws Exception {
		
		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}
		
		if(!fopp.openProjectConfigDetails())
		{
			log.error("could not open Project Configuration!");
			return false;
		}		
		
		return true;
		
	}
	
	public static boolean configureProjectEForm(FundingOpportunity fopp, EForm form) throws Exception {
		
		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}
		
		if(!fopp.openProjectConfigDetails())
		{
			log.error("could not open Project Configuration!");
			return false;
		}
		
		
		Thread dialogClicker = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
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
		
		if(!GeneralUtil.selectFullStringInDropdownList(IFoppConst.fundingOpp_ProjConfig_ProjectEForm_DropDown_Id, form.getEFormFullId()))
		{			
			log.error("ERROR Selecting Project e.Form!");
			return false;
		}
		
		GeneralUtil.takeANap(1.000);
		
		dialogClicker = null;
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backToFoppPlannerBtn);
		
		if(!fopp.isThisFOPP_Planner())
		{
			log.error("ERROR Saving the changes to Project Configuration!");
			return false;
		}
		
		
		return true;
	}
	
	public static boolean openProjectListConfigDetailsOnly(FundingOpportunity fopp) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(ie.form(id,"/main:projectConfiguration:_idJsp/").exists())
		{			
			if(!GeneralUtil.isButtonExistsByValue(IClicksConst.projListBtn)){
				
				log.error("Could not find Project List Tab!");
				
				return false;
			}
			
			ClicksUtil.clickButtons(IClicksConst.projListBtn);	
			
			return true;
		}
		
		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}
		
		if(!fopp.openProjectListConfigDetails())
		{
			log.error("could not open Project Configuration List!");
			
			return false;
		}		
		
		return true;
	}
	
	public static boolean configureEvaluateProjectList(FundingOpportunity fopp, EForm form, String[] eFildsIdent) throws Exception {
		
		if(!fopp.openFundingOppPlanner())
		{
			log.error("ERROR Opening Funding Opp planner!");
			return false;
		}
		
		if(!fopp.openProjectListConfigDetails())
		{
			log.error("could not open Project Configuration List!");
			
			return false;
		}
		
		Assert.assertTrue(GeneralUtil.toggleCheckBoxById(IFoppConst.fundingOpp_ProjListConfig_EvalProjLst_Checkbox_Id, true), "FAIL: Could not enable Evaluation Project List!");
		
		GeneralUtil.takeANap(3.0);
			
		for (String string : eFildsIdent) {
			
			Assert.assertTrue(GeneralUtil.selectfromM2M_NoSave(IFoppConst.fundingOpp_ProjListConfig_EvalProjLst_M2MAvailable_Id, IFoppConst.fundingOpp_ProjListConfig_EvalProjLst_M2MSelected_Id, string), "FAIL: Could not select: ".concat(string));
			
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			
			GeneralUtil.takeANap(0.5);
			
		}
		
		ClicksUtil.clickButtonsById("projectConfiguration:j_id_1w:editProjectConfiguration:projectListConfig:saveButton");
		
		ClicksUtil.clickButtons(IClicksConst.backToFoppPlannerBtn);
		
		if(!fopp.isThisFOPP_Planner())
		{
			log.error("ERROR Saving the changes to Project Config List!");
			return false;
		}
		
		return true;
	}
	
	public static boolean selectEvaluationVisibilityGroups(String[] listOfGroups) throws Exception {
		
		if(!GeneralUtil.isSelectListExists(IStepsConst.stepProp_EvalSumVisibleGroups_M2M_Available_Id))
		{
			log.error("M2M is not on Page!");
			return false;
		}
		
		for (String string : listOfGroups) {
			
			if(!GeneralUtil.selectfromM2M(IStepsConst.stepProp_EvalSumVisibleGroups_M2M_Available_Id, IStepsConst.stepProp_EvalSumVisibleGroups_M2M_Selected_Id, string))
			{
				log.error("The Group is not available in both M2M: ".concat(string));
				return false;
			}			
		}	
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		return true;
	}
	
	public static boolean deSelectEvaluationVisibilityGroups(String[] listOfGroups) throws Exception {
		
		if(!GeneralUtil.isSelectListExists(IStepsConst.stepProp_EvalSumVisibleGroups_M2M_Selected_Id))
		{
			log.error("M2M is not on Page!");
			return false;
		}
		
		for (String string : listOfGroups) {
			
			if(!GeneralUtil.deSelectfromM2M(IStepsConst.stepProp_EvalSumVisibleGroups_M2M_Selected_Id, string))
			{
				log.error("Could not deSelect Groups from M2M: ".concat(string));
				return false;	
			}			
		}	
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		return true;
	}
	
	public static boolean configureScoringAlgorithm(FundingOpportunity fopp, String stepFullIdent, String expression, String targetFieldIdent) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!fopp.openStepDetails(false, stepFullIdent))
		{
			log.error("Could Not Open Step Details!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.configureDataGridEFormFieldBtn);
		
		if(!ClicksUtil.clickImage(IClicksConst.newImg))
		{
			log.error("Could not click new icon!");
			return false;
		}		
		
		List<String> lst = ie.selectList(id, IStepsConst.step_Details_BulkAutoScoreDd_Id).getAllContents();
		
		boolean check = false;
		
		for (String string : lst) {
			
			if(string.endsWith(targetFieldIdent))
			{
				ie.selectList(id,IStepsConst.step_Details_BulkAutoScoreDd_Id).select(string);
				check = true;
				break;
			}			
		}
		
		if(!check)
		{
			log.error("Could not find Target field!");
			return false;
		}
		
		ie.textField(id,IStepsConst.step_Details_BulkAutoScoreExp_Id).set(expression);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		return true;
	}
	
	public static boolean manageSchedules(String[][] scheduleData) throws Exception {
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		for (String[] strings : scheduleData) {
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl, strings[0]), "Could not enter value to text");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, strings[1]), "Could not enter Start Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, strings[2]), "Could not enter End Date");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, strings[2]), "Could not enter End Date");
			
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, strings[3]), "Could not select form");
			
			if(strings[4].equalsIgnoreCase("true"))
			{
				GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, true);
			}
			else
			{
				GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, false);
			}
			
			if(strings[5].equalsIgnoreCase("true"))
			{
				GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, true);
			}
			else
			{
				GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, false);
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
			
		}		
		
		return true;
	}

}
