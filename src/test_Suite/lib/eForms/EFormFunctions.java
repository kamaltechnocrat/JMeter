/**
 * 
 */
package test_Suite.lib.eForms;

/**
 * @author mshakshouki
 *
 */

import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;

import watij.runtime.ie.IE;
import test_Suite.constants.eForms.*;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EFormFunctions {

	/**
	 * 
	 */
	
	private static Log log = LogFactory.getLog(EFormFunctions.class);
	
	protected boolean retValue;
	
	protected String eFormFunctionName = null;
	protected String eFormFunctionProperty1 = null;
	protected String eFormFunctionProperty2 = null;
	protected String eFormFunctionProperty3 = null;
	protected String eFormFunctionProperty4 = null;
	protected String eFormFunctionProperty5 = null;
	protected String eFormFunctionProperty6 = null;
	protected String eFormFunctionProperty7 = null;
	protected String eFormFunctionProperty8 = null;
	
	protected String eFormFunctionPropertyDropDown = null;
	
	public EFormFunctions(EForm eFrom) {
		
	}
	
	public EFormFunctions() {
	}	
	
	
	
	public boolean addNewEFormFunction() throws Exception {
		
		log.info("Start Adding Form Function");
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (ie.link(title, "Add e.Form Function").exists())
		{
			ie.link(title, "Add e.Form Function").click();
			retValue = true;
		}		
		
		return retValue;
	}
	
	public boolean addPostAwardPushBackFunction(Object[] objArray) throws Exception {
		
		log.info("Start Adding  Function");
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if (objArray.length == 2)
		{
			ie.selectList(id, IAlleFormsFunctionsConst.eFormFunctions_FunctionType_Dropdown_Id).select(IAlleFormsFunctionsConst.eForm_PostAwardPushBack_FunctionType);
			
			//delay
			GeneralUtil.takeANap(2.0);
			
			ie.textField(id, IAlleFormsFunctionsConst.eFormFunctions_FuncPropFields_Id[0]).set(objArray[0].toString());
			
			ie.textField(id, IAlleFormsFunctionsConst.eFormFunctions_FuncPropFields_Id[1]).set(objArray[1].toString());
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			retValue = true;			
		}
		else
		{
			log.debug("Array of type Object is out of bounds!");
		}		
		
		return retValue;
	}
	
	public boolean addAnyEFormFunctions(ArrayList<EFormFunctions> funcList) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		EFormFunctions eFormFunc = funcList.get(0);
		
		addNewEFormFunction();
		
		ie.selectList(id,IAlleFormsFunctionsConst.eFormFunctions_FunctionType_Dropdown_Id).select(eFormFunc.getEFormFunctionName());
		
		GeneralUtil.takeANap(1.0);
		
		if(eFormFunc.getEFormFunctionProperty1() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_1_TextField_Id).set(eFormFunc.getEFormFunctionProperty1());
		}
		
		if(eFormFunc.getEFormFunctionProperty2() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_2_TextField_Id).set(eFormFunc.getEFormFunctionProperty2());
		}
		
		if(eFormFunc.getEFormFunctionProperty3() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_3_TextField_Id).set(eFormFunc.getEFormFunctionProperty3());
		}
		
		if(eFormFunc.getEFormFunctionProperty4() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_4_TextField_Id).set(eFormFunc.getEFormFunctionProperty4());
		}
		
		if(eFormFunc.getEFormFunctionProperty5() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_5_TextField_Id).set(eFormFunc.getEFormFunctionProperty5());
		}
		
		if(eFormFunc.getEFormFunctionProperty6() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_6_TextField_Id).set(eFormFunc.getEFormFunctionProperty6());
		}
		
		if(eFormFunc.getEFormFunctionProperty7() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_7_TextField_Id).set(eFormFunc.getEFormFunctionProperty7());
		}
		
		if(eFormFunc.getEFormFunctionProperty8() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_8_TextField_Id).set(eFormFunc.getEFormFunctionProperty8());
		}
		
		if(eFormFunc.geteFormFunctionPropertyDropDown() != null)
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IAlleFormsFunctionsConst.eFormFunction_Property_drpDown_Id, eFormFunc.geteFormFunctionPropertyDropDown()))
			{
				log.error("error occured when selectin from the dropdown!");
				return false;
			}
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		
		return retValue;
		
	}
	
	public boolean modifyEFormFunctionsProperties(ArrayList<EFormFunctions> funcList) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		EFormFunctions eFormFunc = funcList.get(0);
		
		if(eFormFunc.getEFormFunctionProperty1() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_1_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty2() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_2_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty3() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_3_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty4() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_4_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty5() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_5_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty6() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_6_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty7() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_7_TextField_Id).append(", Modified!");
		}
		
		if(eFormFunc.getEFormFunctionProperty8() != null)
		{
			ie.textField(id, IAlleFormsFunctionsConst.eFormPushBackFunc_SourceField_8_TextField_Id).append(", Modified!");
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		return GeneralUtil.isButtonExistsByValue(IClicksConst.previewFormBtn);
		
	}

	/**
	 * @return the eFormFunctionName
	 */
	public String getEFormFunctionName() {
		return eFormFunctionName;
	}

	/**
	 * @param formFunctionName the eFormFunctionName to set
	 */
	public void setEFormFunctionName(String formFunctionName) {
		eFormFunctionName = formFunctionName;
	}

	/**
	 * @return the eFormFunctionProperty1
	 */
	public String getEFormFunctionProperty1() {
		return eFormFunctionProperty1;
	}

	/**
	 * @param formFunctionProperty1 the eFormFunctionProperty1 to set
	 */
	public void setEFormFunctionProperty1(String formFunctionProperty1) {
		eFormFunctionProperty1 = formFunctionProperty1;
	}

	/**
	 * @return the eFormFunctionProperty2
	 */
	public String getEFormFunctionProperty2() {
		return eFormFunctionProperty2;
	}

	/**
	 * @param formFunctionProperty2 the eFormFunctionProperty2 to set
	 */
	public void setEFormFunctionProperty2(String formFunctionProperty2) {
		eFormFunctionProperty2 = formFunctionProperty2;
	}

	/**
	 * @return the eFormFunctionProperty3
	 */
	public String getEFormFunctionProperty3() {
		return eFormFunctionProperty3;
	}

	/**
	 * @param formFunctionProperty3 the eFormFunctionProperty3 to set
	 */
	public void setEFormFunctionProperty3(String formFunctionProperty3) {
		eFormFunctionProperty3 = formFunctionProperty3;
	}

	/**
	 * @return the eFormFunctionProperty4
	 */
	public String getEFormFunctionProperty4() {
		return eFormFunctionProperty4;
	}

	/**
	 * @param formFunctionProperty4 the eFormFunctionProperty4 to set
	 */
	public void setEFormFunctionProperty4(String formFunctionProperty4) {
		eFormFunctionProperty4 = formFunctionProperty4;
	}

	/**
	 * @return the eFormFunctionProperty5
	 */
	public String getEFormFunctionProperty5() {
		return eFormFunctionProperty5;
	}

	/**
	 * @param formFunctionProperty5 the eFormFunctionProperty5 to set
	 */
	public void setEFormFunctionProperty5(String formFunctionProperty5) {
		eFormFunctionProperty5 = formFunctionProperty5;
	}

	/**
	 * @return the eFormFunctionProperty6
	 */
	public String getEFormFunctionProperty6() {
		return eFormFunctionProperty6;
	}

	/**
	 * @param formFunctionProperty6 the eFormFunctionProperty6 to set
	 */
	public void setEFormFunctionProperty6(String formFunctionProperty6) {
		eFormFunctionProperty6 = formFunctionProperty6;
	}

	/**
	 * @return the eFormFunctionProperty7
	 */
	public String getEFormFunctionProperty7() {
		return eFormFunctionProperty7;
	}

	/**
	 * @param formFunctionProperty7 the eFormFunctionProperty7 to set
	 */
	public void setEFormFunctionProperty7(String formFunctionProperty7) {
		eFormFunctionProperty7 = formFunctionProperty7;
	}

	/**
	 * @return the eFormFunctionProperty8
	 */
	public String getEFormFunctionProperty8() {
		return eFormFunctionProperty8;
	}

	/**
	 * @param formFunctionProperty8 the eFormFunctionProperty8 to set
	 */
	public void setEFormFunctionProperty8(String formFunctionProperty8) {
		eFormFunctionProperty8 = formFunctionProperty8;
	}

	/**
	 * @return the eFormFunctionPropertyDropDown
	 */
	public String geteFormFunctionPropertyDropDown() {
		return eFormFunctionPropertyDropDown;
	}

	/**
	 * @param eFormFunctionPropertyDropDown the eFormFunctionPropertyDropDown to set
	 */
	public void seteFormFunctionPropertyDropDown(
			String eFormFunctionPropertyDropDown) {
		this.eFormFunctionPropertyDropDown = eFormFunctionPropertyDropDown;
	}


}
