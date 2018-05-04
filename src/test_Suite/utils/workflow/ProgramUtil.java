/**
 * This Utility Class uses a new method to access program Steps and Steps Management
 */
package test_Suite.utils.workflow;

/**
 * @author mshakshouki
 * 
 */

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.*;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import watij.dialogs.AlertDialog;
import watij.dialogs.ConfirmDialog;
import watij.elements.HtmlElement;
import watij.elements.HtmlElements;
import watij.elements.Image;
import watij.elements.Images;
import watij.elements.Link;
import watij.elements.Span;
import watij.elements.Spans;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.workflow.*;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.ProgramApprove;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;

public class ProgramUtil {

	private static Log log = LogFactory.getLog(ProgramUtil.class);

	static Tables tables;
	static Table table;
	static Images images;
	static Image image;
	static Span span;
	static int intValue;	
	static int newIndex;	
	static boolean retValue;	
	static Program fundingOpp;	
	static ProgramApprove papProg;
	
	static Hashtable<Integer, String> hashTableText;
	static Hashtable<Integer, String> hashTableDropdown;

	public static void tuggleProgramSteps(boolean expand) throws Exception {
		
//		IE ie = IEUtil.getActiveIE();
		
		if (expand==true){
			expandAnObject("Steps");
		}
		
		else collapseAnObject("Steps");
		
//		tables = ie.span(id, IProgramsConst.progPlanner_Span_Id).tables();
//
//		for (int i = 0; i < tables.length(); i++) {
//			table = tables.table(i);
//			if (table.innerText().matches("Steps")) {
//				log.debug("Steps");
//				if ((table.image(1).src().contains(IProgramsConst.progPlanner_PlusGif_Last_Id))
//						&& (expand)) {
//
//					table.image(1).click();
//					log.debug("Expand");
//					break;
//
//				} else if ((table.image(1).src().contains(IProgramsConst.progPlanner_MinusGif_Last_Id))
//						&& (!expand)) {
//
//					ie.checkbox(id, IProgramsConst.progPlanner_RecursiveExpandCollapse_Id).set(true);
//
//					table.image(1).click();
//					ie.checkbox(id, IProgramsConst.progPlanner_RecursiveExpandCollapse_Id).set(false);
//					log.debug("Expand");
//					break;
//				}
//			}
//		}
	}
	
	
	public static HtmlElement getListItemEle(String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");
		
		for (HtmlElement ele : eles) {
			if (ele.span(attribute("class", "plannerNode")).exists()){
								
				if(ele.span(attribute("class", "plannerNode")).innerText().trim().endsWith(innerText)) {

				return ele;
			}
			}
		}
		return null;
	}
	
	public static void expandAnObject(String objectInnerText)throws Exception {

//		HtmlElement ele = getListItemEle(objectInnerText);
//
//		if (!isExpanded(ele)){
//
//			Spans sp =  ele.spans();
//
//			sp.span(1).click();		
//		}	
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.expandAll_Btn), "FAIL: Could not expand Planner!");
		
		GeneralUtil.takeANap(1.0);
	}
	
	public static void expandByEle(HtmlElement ele)throws Exception {

//		if (!isExpanded(ele)){
//
//			Spans sp =  ele.spans();
//
//			sp.span(1).click();		
//		}		
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.expandAll_Btn), "FAIL: Could not expand Planner!");
		
		GeneralUtil.takeANap(1.0);
	}
	
	public static void collapseAnObject(String objectInnerText)throws Exception {

//		HtmlElement ele = getListItemEle(objectInnerText);
//
//		if (isExpanded(ele)){
//
//			Spans sp =  ele.spans();
//
//			sp.span(1).click();		
//
//		}		
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.expandAll_Btn), "FAIL: Could not expand Planner!");
		
		GeneralUtil.takeANap(1.0);
	}
	
	public static void collapseByIndex(HtmlElement ele)throws Exception {

//		if (isExpanded(ele)){
//
//			Spans sp =  ele.spans();
//
//			sp.span(1).click();		
//
//		}	
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.collapseAll_Btn), "FAIL: Could not collapse Planner!");
		
		GeneralUtil.takeANap(1.0);
	}
	
	public static boolean isExpanded(HtmlElement ele) throws Exception {

		Spans sp =  ele.spans(attribute("class", IClicksConst.expandedSpan));

		for (Span span : sp) {

			if (span.exists())

				return true;
		}

		return false;
	}
	
	public static int getListItemIndex(String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");

		int num=0;
		
		for (HtmlElement ele : eles) {
			if (ele.span(attribute("class", "plannerNode")).exists()){
								
				if(ele.span(attribute("class", "plannerNode")).innerText().trim().endsWith(innerText)) {

					return num;
				}
			}
			num++;		
		}
		return -1;
	}

	public static int findObjectInProgPlanner(String objName, int projType) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		intValue = -1;
		
		// if the planner completly expanded, then minimize it and no recursive
		ie.checkbox(id, IProgramsConst.progPlanner_RecursiveExpandCollapse_Id)
				.set(false);
		ProgramUtil.tuggleProgramSteps(false);
		ProgramUtil.tuggleProgramSteps(true);	
		if(projType == IProgramsConst.EProjectType.pre_Award.ordinal())
		{
			intValue = getListItemIndex(objName);
		}
		else
		{			
			int counter = getListItemIndex("(Post-Award)");

					tuggleObjectInProgPlanner(counter,true);				
					newIndex = counter+6;					
					tuggleObjectInProgPlanner(newIndex,true);
			counter = -1;
			
			intValue = getListItemIndex(objName);
				}				
			
		return intValue;
	}
	
	public static int findStepInProgPlanner(String objName, int projType) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		intValue = -1;
		
		ProgramUtil.tuggleProgramSteps(false);
		GeneralUtil.takeANap(0.5);
		ProgramUtil.tuggleProgramSteps(true);
		
		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");
		
		if(projType == IProgramsConst.EProjectType.pre_Award.ordinal())
		{
			int counter = 0;
			
			for (HtmlElement ele : eles){
				counter ++;
				
				if (ele.span(0).innerText().contains(objName))
				{
					intValue = counter;
					break;
				}
			}
		}
		else
		{
			int counter = 0;

			for (HtmlElement ele : eles){
				counter ++;
				
				if (ele.span(0).innerText().contains("(Post-Award)"))
					{
					tuggleObjectInProgPlanner(counter,true);				
					newIndex = counter+7;					
					tuggleObjectInProgPlanner(newIndex,true);	
					break;
				}
			}
			counter = 1;
			
			for (HtmlElement ele : eles){
				counter ++;
				
				if (ele.span(0).innerText().contains(objName))
				{
					intValue = counter;
					break;
				}				
			}
		}
		return intValue;
	}

	public static boolean tuggleObjectInProgPlanner(int tableIndex, boolean expand)
			throws Exception {
		
		if(expand)
		{
			expandAnObject("");
		}
		else
		{
			collapseAnObject("");
		}
		
		return true;
	}
	
	
	public static void openNotificationDetails(int projType, String fullStepName) throws Exception {
		
		intValue = findObjectInProgPlanner(fullStepName, projType);		
		tuggleObjectInProgPlanner(intValue, true);		
		newIndex = intValue + IProgramsConst.EstepManagment.notification.ordinal();		
		
		images = getListItemEle(fullStepName).images();
		
		for (Image image :images){
			if (image.src().contains(IClicksConst.addItemImg)){
				image.click();
				break;
			}
		}
	}
	
	
	public static void openStepOrNotificationDetails(int tableIndex) throws Exception {
		
		IE ie = IEUtil.getActiveIE();		
		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");
		int counter = -1;
		
		for (HtmlElement ele : eles){
			counter++;
			if (counter == tableIndex){
				images = ele.images();
			}
		}
		
		for(int i=0; i<images.length(); i++)
		{
			image = images.image(i);			
			if (image.src().contains(IClicksConst.addItemImg))
			{
				image.click();				
				break;				
			}
		}
	}
	
	public static boolean openStepDetails(int tableIndex) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");
		
		HtmlElement ele = eles.get(tableIndex - 1);
		
		log.warn(ele.innerText());
		
		if(!ele.link(title,"View Step Details").exists())
		{
			log.error("Could not find the View Step Detail Icon!");
			return false;
		}
		
		Link link = ele.link(title, "View Step Details");
		
		link.click();
		
		return true;
	}	
	
	public static boolean openProgPlannerObjectDetails(int projType, String fullObjectName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();	
		
		retValue = false;
		
		intValue = findObjectInProgPlanner(fullObjectName, projType);	
		
		if (intValue > -1)
		{		
			HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");
			int counter = -1;

			for (HtmlElement ele : eles){
				counter++;
				if (counter == intValue){
					images = ele.images();
				}
			}
			for(int i=0; i<images.length(); i++)
			{
				image = images.image(i);		
				
				if (image.src().contains(IClicksConst.detailImg))
				{
					image.click();
					retValue = true;
					break;
				}					
			}
		}
		
		return retValue;
	}
	
	public static void openStepManagmentDetails(int projType, String fullStepName, int orderIndex) throws Exception {
		
		IE ie = IEUtil.getActiveIE();	
		
		intValue = findStepInProgPlanner(fullStepName, projType);		
		
		tuggleObjectInProgPlanner(intValue, true);		
		
		newIndex = intValue + orderIndex - projType;		
		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");		
		
		HtmlElement ele = eles.get(newIndex);
		
		log.warn(ele.innerText());
		
		images = ele.images();
		
		for(int i=0; i<images.length(); i++)
		{
			image = images.image(i);			
			if (image.src().contains(IClicksConst.detailImg))
			{
				image.click();
				break;
			}					
		}		
	}
	
	// not used
	public static void openProgramList(int programList) throws Exception {
		
		if (programList == IProgramsConst.eProgramList.pap.ordinal())
		{
			ClicksUtil.clickLinks(IClicksConst.openProgramApprovalProcess);
		}
		else
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		}
	}
	
	public static boolean createNewPAP(ProgramApprove papProg) throws Exception {
		
		IE ie = IEUtil.getActiveIE();		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openProgramApprovalProcess);
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		ie.textField(id, IProgramsConst.pap_Prog_Ident).set(papProg.getProgApproveId());
		ie.textField(id,IProgramsConst.pap_ProjPrefix_TxtFld).set(papProg.getProgApproveProjPrefix());
		ie.textField(id, IProgramsConst.pap_Prog_Start_Date).set(papProg.getProgApproveStartDate());
		ie.textField(id, IProgramsConst.pap_Prog_End_Date).set(papProg.getProgApproveEndDate());
		ie.selectList(id,IProgramsConst.pap_Prog_Officer_DrpDwn).select(papProg.getProgApproveOfficer());
		Thread dialogClicker = new Thread()
		{
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try
				{
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialog1 = ie.alertDialog();
					while (dialog1==null)
					{
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}
					
					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");					
					
				}
				catch (Exception e)
				{
					throw new RuntimeException("Unexpected exception",e);
				}
			}
		};
		
		dialogClicker.start();
		log.debug("started dialog clicker thread");
		
		ie.selectList(id,IProgramsConst.pap_Prog_PrimOrg_DrpDwn).select(papProg.getProgApprovePrOrg());
		
		GeneralUtil.takeANap(1.000);
		
		dialogClicker = null;
		
		ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "0" + IProgramsConst.pap_Prog_Name_EndTag).set(papProg.getProgApproveNames()[0]);
		
		if (ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "1" + IProgramsConst.pap_Prog_Name_EndTag).exists())
		{
			ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "1" + IProgramsConst.pap_Prog_Name_EndTag).set(papProg.getProgApproveNames()[1]);	
		}		
		
		if (ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "2" + IProgramsConst.pap_Prog_Name_EndTag).exists())
		{
			ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "2" + IProgramsConst.pap_Prog_Name_EndTag).set(papProg.getProgApproveNames()[2]);	
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		if(ie.button(value, IClicksConst.openProgramPlannerBtn).exists())
		{
			ClicksUtil.clickButtons(IClicksConst.openProgramPlannerBtn);			
			retValue = true;
		}		
		return retValue;
	}

	public static boolean deleteNotification(int projType, String fullStepName,
			String startNodeLabel) throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		intValue = findObjectInProgPlanner(fullStepName, projType);
		tuggleObjectInProgPlanner(intValue, true);
//		newIndex = intValue
//				+ IProgramsConst.EstepManagment.notification.ordinal();
//		images = ie.span(id, IProgramsConst.progPlanner_Span_Id)
//				.table(newIndex).images();
		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class))
				.htmlElements(tag, "li").get(intValue).htmlElements(tag, "li");
		
		for (HtmlElement ele : eles){
			
			if (ele.innerText().trim().startsWith("Notifications")){
				expandByEle(ele);
			}
			
			if (ele.innerText().trim().startsWith(startNodeLabel)){
				
				images = ele.images();
				break;
			}	
		}
		
		for (Image image :images){
			if (image.src().contains(IClicksConst.deleteImg)){

				Thread dialogClicker = new Thread() {
					@Override
					public void run() {
						try {
							IE ie = IEUtil.getActiveIE();
							ConfirmDialog dialog1 = ie.confirmDialog();
							while (dialog1 == null) {
								log.debug("can't yet get handle on confirm dialog1");
								GeneralUtil.takeANap(0.250);
							}

							dialog1.ok();
							log.debug("got confirm Dialog1 and clicked OK.");

						} catch (Exception e) {
							throw new RuntimeException(
									"Unexpected exception", e);
						}
					}
				};

				dialogClicker.start();

				log.debug("started dialog clicker thread");

				image.click();

				GeneralUtil.takeANap(1.000);

				retValue = true;

				dialogClicker = null;

				break;

			}
		}

		return retValue;
	}

	public static boolean createNew(ProgramApprove Prog) throws Exception {
		
		IE ie = IEUtil.getActiveIE();		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		ClicksUtil.clickImage(IClicksConst.newImg);
		ie.textField(id, IProgramsConst.pap_Prog_Ident).set(Prog.getProgApproveId());
		ie.textField(id,IProgramsConst.pap_ProjPrefix_TxtFld).set(Prog.getProgApproveProjPrefix());
		ie.textField(id, IProgramsConst.pap_Prog_Start_Date).set(Prog.getProgApproveStartDate());
		ie.textField(id, IProgramsConst.pap_Prog_End_Date).set(Prog.getProgApproveEndDate());
		ie.selectList(id,IProgramsConst.pap_Prog_Officer_DrpDwn).select(Prog.getProgApproveOfficer());
		ie.selectList(id,IProgramsConst.pap_Prog_PrimOrg_DrpDwn).select(Prog.getProgApprovePrOrg());
		Thread dialogClicker = new Thread()
		{
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try
				{
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialog1 = ie.alertDialog();
					while (dialog1==null)
					{
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}
					
					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");					
					
				}
				catch (Exception e)
				{
					throw new RuntimeException("Unexpected exception",e);
				}
			}
		};
		
		dialogClicker.start();
		log.debug("started dialog clicker thread");
		ie.selectList(id,IProgramsConst.pap_Prog_OrgAccess_DrpDwn).select(papProg.getProgApproveOrgAccess());	
		
		GeneralUtil.takeANap(1.000);
		
		dialogClicker = null;
		
		ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "0" + IProgramsConst.pap_Prog_Name_EndTag).set(Prog.getProgApproveNames()[0]);
		
		if (ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "1" + IProgramsConst.pap_Prog_Name_EndTag).exists())
		{
			ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "1" + IProgramsConst.pap_Prog_Name_EndTag).set(Prog.getProgApproveNames()[1]);	
		}		
		
		if (ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "2" + IProgramsConst.pap_Prog_Name_EndTag).exists())
		{
			ie.textField(id,IProgramsConst.pap_Prog_Name_StartTag + "2" + IProgramsConst.pap_Prog_Name_EndTag).set(Prog.getProgApproveNames()[2]);	
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		if(ie.button(value, IClicksConst.openProgramPlannerBtn).exists())
		{
			ClicksUtil.clickButtons(IClicksConst.openProgramPlannerBtn);			
			retValue = true;
		}		
		return retValue;
	}
	
	public static boolean activatingProgram(ProgramApprove papProg, String activate) throws Exception {
		IE ie = IEUtil.getActiveIE();		
		retValue = false;
		
		openProgPlannerObjectDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),papProg.getProgApproveId());
		
		ie.selectList(id, IProgramsConst.pap_Prog_Status_DrpDwn).select(activate);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		return retValue;
	}
	
	public static boolean selectingProgPlannerOfficers(String[] officers, int officersType) throws Exception {
		
		IE ie = IEUtil.getActiveIE();		
		retValue = false;
		
		boolean selected = true;
		
		if(officersType==0)
		{
			ClicksUtil.clickImageByAlt(IProgramsConst.MANAGE_PROG_ADMIN);
		}
		else if(officersType==1)
		{
			ClicksUtil.clickImageByAlt(IProgramsConst.MANAGE_PROG_STAFF);
		}
		
		for(int i=0; i<officers.length; i++)
		{
			if(GeneralUtil.isObjectExistsInList(IProgramsConst.PROG_STAFF_AVAILABL, officers[i]))
			{
				ie.selectList(id,IProgramsConst.PROG_STAFF_AVAILABL).select(officers[i]);
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
			else if(!GeneralUtil.isObjectExistsInList(IProgramsConst.PROG_STAFF_SELECTED, officers[i]))
			{
				selected = false;
			}
		}
		
		if (selected)
		{
			retValue = true;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return retValue;
	}
	
	public static String getNewBaseLetter(String baseObject) throws Exception {
		String retLetter = "";
		boolean bolloop = true;
        int AlphaIndex = 0;
        while (bolloop) {
      	  FiltersUtil.filterListByLabel(IFiltersConst.pap_ProgramIdent_Lbl, IGeneralConst.baseLetters[AlphaIndex] + baseObject, IFiltersConst.exact);
      	  
      	  if (!GeneralUtil.isLinkExistsByTxt(IGeneralConst.baseLetters[AlphaIndex] + baseObject)){
      		  bolloop = false;
      		retLetter = IGeneralConst.baseLetters[AlphaIndex];
      	  }
      	  AlphaIndex += 1;        	
        }
		
		return retLetter;
	}
	
	public static String getUsedBaseLetter(String baseObject) throws Exception {
		String retLetter = "";
		boolean bolloop = true;
        int AlphaIndex = 0;
        
        while (bolloop) {
        	
      	  FiltersUtil.filterListByLabel(IFiltersConst.pap_ProgramIdent_Lbl, IGeneralConst.baseLetters[AlphaIndex] + baseObject, IFiltersConst.exact);
      	  
      	  if (GeneralUtil.isLinkExistsByTxt(IGeneralConst.baseLetters[AlphaIndex] + baseObject)){
      		  bolloop = false;
      		retLetter = IGeneralConst.baseLetters[AlphaIndex];
      	  }
      	  AlphaIndex += 1;        	
        }
		
		return retLetter;
	}
	
	public static boolean changeStepEForm(String fullStepName, int projType, String eFormIdentifier) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		openStepDetails(findStepInProgPlanner(fullStepName,projType));
		
		if(!ie.selectList(name, IStepsConst.step_Prop_EFormIdent_Id).exists())
				{
			log.error("could not find the dropdown " .concat(IStepsConst.step_Prop_EFormIdent_Id));
			return false;
				}
		
		ie.selectList(name, IStepsConst.step_Prop_EFormIdent_Id).select(eFormIdentifier);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return true;
	}
	
	
    public static boolean changeStepAmendmentStatus(String fullStepName, int projType, String stepIdentifier) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		openStepDetails(findStepInProgPlanner(fullStepName,projType));
		
		if(!ie.selectList(name, IStepsConst.step_Prop_Amendment_Id).exists())
				{
			log.error("could not find the dropdown " .concat(IStepsConst.step_Prop_Amendment_Id));
			return false;
				}
		
		ie.selectList(name, IStepsConst.step_Prop_Amendment_Id).select(stepIdentifier);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return true;
	}
	
	
	
	
	
	
	
	
	
}
