package test_Suite.utils.ui;

import static watij.finders.FinderFactory.*;
import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.w3c.dom.Element;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.G3MenuResources;
import test_Suite.utils.G3MenuResourcesLoader;
import test_Suite.utils.cases.GeneralUtil;
import watij.dialogs.ConfirmDialog;
import watij.elements.Button;
import watij.elements.Buttons;
//import watij.elements.Button;
//import watij.elements.Buttons;
import watij.elements.Div;
import watij.elements.Link;
import watij.elements.Links;
import watij.elements.Span;
import watij.elements.Spans;
import watij.elements.TableRow;
import watij.runtime.ie.IE;

public class ClicksUtil {

	private static Log log = LogFactory.getLog(ClicksUtil.class);

	static boolean clickRetValue;

	static Spans spans;
	static Span span;

	static ArrayList<String> errorSmalls;
	
public static boolean clickEditAndReturn() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Spans butnsSp = ie.div(id,IClicksConst.ssButtons_DivId).spans();
//		Buttons butns = ie.div(id,IClicksConst.ssButtons_DivId).buttons();
		
		for (Span buttonSp : butnsSp) {
			log.warn(buttonSp.value());
			if(buttonSp.text().equals("Edit"))
			{
				if(buttonSp.enabled())
				{
					log.debug(buttonSp.text());
					buttonSp.click();	
					GeneralUtil.takeANap(1.5);
					ClicksUtil.returnFromAnyForm();
					return true;
				}
			}			
		}
		return false;
	}


	public static boolean submitOrComplete() throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Spans buttnsSp = null;
		
		if(ie.div(id,IClicksConst.ssButtons_DivId).exists())
		{
			
			buttnsSp = ie.div(id,IClicksConst.ssButtons_DivId).spans();
		}
		else if(ie.div(id,IClicksConst.formletButtons_DivId).exists())
		{
			buttnsSp = ie.div(id,IClicksConst.formletButtons_DivId).spans();
		}
		else
		{
			log.error("Could not Find Buttons Div element!");
			return false;
		}

		for (Span buttonSp : buttnsSp) {

			if(buttonSp.text().equals("Submit") || buttonSp.text().equals("Complete"))
			{
				if(buttonSp.enabled())
				{
					log.debug(buttonSp.text());

					buttonSp.click();	

					//GeneralUtil.takeANap(1.5);

					return true;				
				}
			}

			if(buttonSp.text().equals("Edit"))
			{
				log.debug(buttonSp.text());

				return true;
			}			
		}
		return false;
	}

	public static boolean returnFromAnyForm() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.div(attribute("class",IClicksConst.divAttrb_FormReturn_Class)).exists())
		{
			log.error("can't return from the e.Form");

			return false;

		}

		Links links = ie.div(attribute("class", IClicksConst.divAttrb_FormReturn_Class)).links();

		for (Link link : links) {

			if(link.innerText().startsWith("Back to"))
			{
				link.click();
				//GeneralUtil.takeANap(1.0);
				return true;
			}			
		}	

		log.error("Can't find the link to return from e.Form");

		return false;

	}

	public static boolean clickLinks(G3MenuResources menuRes) throws Exception {
		IE ie = IEUtil.getActiveIE();

		String textLnk = G3MenuResourcesLoader.getString(menuRes);

		//Link link;

		returnFromAnyForm();

		if(menuRes.toString().startsWith("GM_"))
		{
			if(ie.span(id, IClicksConst.span_ClicksLinks1).links().length() > 0)
			{
				clickLinkBySpanId(IClicksConst.span_ClicksLinks1, textLnk);
			}
			else
			{
				clickLinks(textLnk);
			}

		}
		else if(menuRes.toString().startsWith("GPA_"))
		{
			clickLinkBySpanId(IClicksConst.span_ClicksLinks0, textLnk);
		}
		else if(menuRes.toString().startsWith("Admin_"))
		{		
			if(ie.span(id, IClicksConst.span_ClicksLinks2).links().length() > 0)
			{
				clickLinkBySpanId(IClicksConst.span_ClicksLinks2, textLnk);
			}
			else
			{
				clickLinks(textLnk);
			}			
		}
		else
		{
			clickLinks(textLnk);
		}

		checkForErrorMessages();

		return true;
	}	

	/**
	 * @param linkText
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean clickLinks(String linkText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Link link;

		if (!ie.link(text, linkText).exists()) 
		{
			log.error("Could not find link: ".concat(linkText));

			checkForErrorMessages();

			return false;
		} 
		link = ie.link(text,linkText);

		//link.flash();

		link.click();
		
		//GeneralUtil.takeANap(1.0);

		checkForErrorMessages();	

		return true;
	}

	public static boolean clickLinksById(String linkId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.link(id, linkId).exists()) 
		{

			//ie.link(title, linkTitle).flash();
			ie.link(id, linkId).click();
			
			GeneralUtil.takeANap(1.0);

			checkForErrorMessages();			

			return true;

		} else if (ie.htmlElement(id, linkId).exists()) {

			//ie.htmlElement(title, linkTitle).flash();
			ie.htmlElement(id, linkId).click();
			
			//GeneralUtil.takeANap(1.0);

			checkForErrorMessages();		

			return true;
		}

		log.error("Could not click link By its Id: ".concat(linkId));

		checkForErrorMessages();

		return false;
	}

	public static boolean clickLinksByTitle(String linkTitle) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.link(title, linkTitle).exists()) 
		{

			//ie.link(title, linkTitle).flash();
			ie.link(title, linkTitle).click();
			
			//GeneralUtil.takeANap(1.0);

			checkForErrorMessages();			

			return true;

		} else if (ie.htmlElement(title, linkTitle).exists()) {

			//ie.htmlElement(title, linkTitle).flash();
			ie.htmlElement(title, linkTitle).click();
			
			//GeneralUtil.takeANap(1.0);

			checkForErrorMessages();		

			return true;
		}

		log.error("Could not click link By its Title: ".concat(linkTitle));

		checkForErrorMessages();

		return false;
	}

	public static boolean clickLinkBySpanId(String spanId, String linkText)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		// To make sure if the Span exists//

		if ((!ie.span(id, spanId).exists()) && (!ie.link(text,linkText).exists())) 
		{
			log.error("Could not find Span by its Id: ".concat(spanId));			

			checkForErrorMessages();
			return false;			
		}		

		if (!ie.span(id, spanId).link(text, linkText).exists()) {

			log.error("Could not find the link within the span: ".concat(linkText));

			checkForErrorMessages();

			return false;				
		}

		//ie.span(id, spanId).link(text, linkText).flash();	
		ie.span(id, spanId).link(text, linkText).click();		
		
		//GeneralUtil.takeANap(1.0);

		checkForErrorMessages();
		return true;
	}

	public static boolean clickLinkByTableDiv(String tBodyId, String linkText)
			throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		// To make sure if the Span exists//

		if (TablesUtil.isTableExists(tBodyId) && (!ie.link(text,linkText).exists()))
		{
			log.error("Could not find Link inside tablebody");			

			checkForErrorMessages();
			return false;			
		}		
		Div tDiv = TablesUtil.tableDiv();

		if (!tDiv.link(text, linkText).exists()) {

			log.error("Could not find the link within the tableBody: ".concat(linkText));

			checkForErrorMessages();

			return false;				
		}

		//tDiv.link(text, linkText).flash();	
		tDiv.link(text, linkText).click();	
		
		
		GeneralUtil.takeANap(0.5);

		checkForErrorMessages();
		return true;
	}

	public static boolean clickButtonsById(String ButtonId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.button(id, ButtonId).exists())
		{
			checkForErrorMessages();

			log.error("Could not Find Button: ".concat(ButtonId));

			return false;
		}

		ie.button(id, ButtonId).click();	
		
		//GeneralUtil.takeANap(1.0);

		checkForErrorMessages();
		
		//GeneralUtil.takeANap(1.0);

		return true;
	}

	/**
	 * @param ButtonValue
	 * @throws Exception
	 */
	public static boolean clickButtons(String ButtonValue) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Buttons btns = ie.buttons(attribute("role", IClicksConst.btnRole));

		for (Button btn : btns){

			if((!btn.span(text, ButtonValue).exists()) && (ButtonValue.equals(IClicksConst.saveAndNextBtn)))
			{
				ButtonValue = IClicksConst.saveAndNextOldBtn;
			}
			else if((!btn.span(text, ButtonValue).exists()) && (ButtonValue.equals(IClicksConst.saveAndBackBtn)))
			{
				ButtonValue = IClicksConst.saveAndBackOldBtn;
			}

			if (btn.span(text, ButtonValue).exists()){
				
				btn.span(text, ButtonValue).click();	

				checkForErrorMessages();

				//GeneralUtil.takeANap(1.0);

				return true;
			}			
		}
		
		checkForErrorMessages();

		log.error("Could not Find Button: ".concat(ButtonValue));

		return false;
	}

	public static boolean clickImage(String imgSrc) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (!ie.image(src, imgSrc).exists()) 
		{
			checkForErrorMessages();

			log.error("Could not find Image By its Source!");

			return false;
		}

		//ie.image(src, imgSrc).flash();

		ie.image(src, imgSrc).click();
		//GeneralUtil.takeANap(1.0);

		checkForErrorMessages();

		return true;
	}

	public static boolean clickImageByAlt(String imgAlt) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.image(alt, imgAlt).exists())
		{
			log.error("Could not find image Alt on the Page!");

			checkForErrorMessages();

			return false;
		}		

		//ie.image(alt, imgAlt).flash();

		ie.image(alt, imgAlt).click();
		//GeneralUtil.takeANap(1.0);

		checkForErrorMessages();

		return true;
	}


	public static boolean clickImageByID(String imgID) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.image(id, imgID).exists())
		{
			log.error("Could not find image ID on the Page!");

			checkForErrorMessages();

			return false;
		}		

		ie.image(id, imgID).click();
		
		//GeneralUtil.takeANap(1.0);

		checkForErrorMessages();

		return true;
	}

	public static void toggleCheckBox(int boxIndex, Boolean setting)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Thread dialogClicker = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();
					while (dialog1 == null) {
						log.debug("Can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.ok();
					log.debug("Got confirm Dialog1 and clicked OK.");
				} catch (Exception e) {
				}
			}
		};

		dialogClicker.start();
		log.debug("Started dialog clicker thread");

		if(setting) {
			ie.checkbox(boxIndex).set();
		} else {
			ie.checkbox(boxIndex).clear();
		}

		GeneralUtil.takeANap(1.000);

		dialogClicker = null;

		checkForErrorMessages();

	}

	public static void toggleCheckBoxNew(String divId, int rowIndex, Boolean setting)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Thread dialogClicker = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();
					while (dialog1 == null) {
						log.debug("Can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.ok();
					log.debug("Got confirm Dialog1 and clicked OK.");
				} catch (Exception e) {
				}
			}
		};

		dialogClicker.start();
		log.debug("Started dialog clicker thread");

		TableRow row = ie.div(id, divId).row(rowIndex);


		if(setting) {

			if (row.span(attribute("class", IGeneralConst.newChkBx_off_Span)).exists()){
				row.span(attribute("class", IGeneralConst.newChkBx_off_Span)).click();
				GeneralUtil.takeANap(1.0);
			}		
		} 

		else {

			if (row.span(attribute("class", IGeneralConst.newChkBx_on_Span)).exists()){
				row.span(attribute("class", IGeneralConst.newChkBx_on_Span)).click();
				GeneralUtil.takeANap(1.0);
			}	
		}

		GeneralUtil.takeANap(1.000);

		dialogClicker = null;

		checkForErrorMessages();

	}



	public static void checkForErrorMessages() throws Exception {
		try {

			IE ie = IEUtil.getActiveIE();

			if(ie.containsText("An Unexpected Error has Occurred") || ie.containsText("An unexpected error has occurred within the system and an email has been sent to your system administrator."))
			{
				log.fatal("Exception has been Occured system will abort!");
				Assert.fail("Exception has been Occured system will abort!");

			}

			errorSmalls = new ArrayList<String>();

			List<Element> eles;

			if (ie.htmlElement(attribute("class", "errorSmall")).exists() && !ie.htmlElement(attribute("class", "errorSmall")).htmlElements().elements().isEmpty())
			{
				eles = ie.htmlElement(attribute("class", "errorSmall")).htmlElements().elements();

				for (Element element : eles) {

					if(element.getTextContent().trim() != "")
					{

						log.error("Validation Error: ".concat(element.getTextContent().trim()));

						errorSmalls.add(element.getTextContent().trim());

					}

				}
			} 

		} catch (Exception e) 
		{
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}


}
