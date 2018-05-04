/**
 * 
 */
package test_Suite.utils.workflow;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IStepsConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class StepsUtil  implements ILBFunctionConst{
	
	private static Log log = LogFactory.getLog(StepsUtil.class);
	
	private static final int DECORATION_DOLLARSIGN = 0;
	
	
	public static boolean insertTo_NoPropListFormlet(String[] entries) throws Exception {

		if(!ClicksUtil.clickLinks(IStepsConst.NoPropretiesFieldsPEF))
		{
			log.error("could not click on link " .concat(IStepsConst.NoPropretiesFieldsPEF));
			return false;
		}
		
		
		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle( ILBFunctionConst.lbf_NoProp_Approval_DropdownTtl, entries[0]));
		
		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(ILBFunctionConst.lbf_NoProp_Checkbox_FieldTtl, Boolean.parseBoolean(entries[1])));
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_NoProp_Date_FieldTtl, GeneralUtil.getTodayDate()));
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_NoProp_EINumber_FieldTtl, entries[3]));
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_NoProp_EmailAddress_FieldTtl, entries[4]));
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_NoProp_WebAddress_FieldTtl, entries[5]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn));
		
		return true;
	}
	
	
	public static boolean insertTo_TypePropListFormlet(String[] arr) throws Exception {

		if(!ClicksUtil.clickLinks(IStepsConst.TypePropretiesFieldsPEF))
		{
			log.error("could not click on link " .concat(IStepsConst.TypePropretiesFieldsPEF));
			return false;
		}
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_TypeProp_PhoneNumber_FieldTtl, arr[0]));
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(ILBFunctionConst.lbf_TypeProp_PostalCode_FieldTtl, arr[1]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn));
		
		return true;
	}

	public static boolean insertTo_MinMaxPropListFormlet(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if(!ClicksUtil.clickLinks(IStepsConst.MinMaxPropretiesFieldsPEF))
		{
			log.error("could not click on link " .concat(IStepsConst.MinMaxPropretiesFieldsPEF));
			return false;
		}

		
		ie.textField(id, ILBFunctionConst.lbf_MinMaxProp_LongText_FieldId).set(
				
				EFormsUtil.createRandomString(arr[0], Integer
						
						.parseInt(arr[1])));
		
		Assert.assertTrue(GeneralUtil.setTextById(ILBFunctionConst.lbf_MinMaxProp_Numeric_FieldId, arr[1]));
		
		Assert.assertTrue(GeneralUtil.setTextById(ILBFunctionConst.lbf_MinMaxProp_Password_FieldId, arr[2]));
		
		
		Assert.assertTrue(GeneralUtil.setTextById(ILBFunctionConst.lbf_MinMaxProp_ShortText_FieldId, EFormsUtil.createRandomString(arr[3], 1500)));
		
		Assert.assertTrue(GeneralUtil.setTextById(ILBFunctionConst.lbf_MinMaxProp_ReviewScore_FieldId, arr[4]));
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn));	
	
		return true;
	}

	public static boolean insertTo_DataGridListFormlet(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if(!ClicksUtil.clickLinks(IStepsConst.DataGridFieldsPEF))
		{
			log.error("could not click on link " .concat(IStepsConst.MinMaxPropretiesFieldsPEF));
			return false;
		}
		
		
		ie.textField(id, ILBFunctionConst.lbf_DataGrids_NumericCell_A1_Id).set(arr[0]);	
		
	    ie.textField(id, ILBFunctionConst.lbf_DataGrids_NumericCell_B2_Id).set(arr[0]);	
		
		ie.textField(id, ILBFunctionConst.lbf_DataGrids_NumericCell_C3_Id).set(arr[0]);
		
		ie.textField(id, ILBFunctionConst.lbf_DataGrids_TextCell_A1_Id).set(arr[1]);
		ie.textField(id, ILBFunctionConst.lbf_DataGrids_TextCell_B2_Id).set(arr[1]);
		
		ie.textField(id, ILBFunctionConst.lbf_DataGrids_TextCell_C3_Id).set(arr[1]);
	
		ie.selectList(id, ILBFunctionConst.lbf_DataGrids_DropdownCell_A1_Id).select(arr[2]);
		
		ie.selectList(id, ILBFunctionConst.lbf_DataGrids_DropdownCell_B2_Id).select(arr[2]);
		
		ie.selectList(id, ILBFunctionConst.lbf_DataGrids_DropdownCell_C3_Id).select(arr[2]);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn));
		
		return true;
	}

	public static boolean insertTo_SubScheduleListFormlet1(String ipasForm,String[] arr) throws Exception {

		if(!ClicksUtil.clickImage(IClicksConst.newImg))
		{
			log.error("could not click on " .concat(IClicksConst.newImg));
			return false;
		}
			
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,arr[0]),"FAIL: could not set Claim Name!");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, GeneralUtil.getTodayDate()), "FAIL: Could not set the Start Date");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, GeneralUtil.getNextYear()),"FAIL: Could not set Due Date");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()),"FIAL: Could not set End Date");
		
		// For non Award eForm
		if (!ipasForm.contains(""))
		{				
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, ipasForm),"FAIL: Could not select PA Submission Form!");

		}			

		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl,Boolean.parseBoolean(arr[4])), "FAIL: Could not set Required checkbox!");
		
		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, Boolean.parseBoolean(arr[5])), "FAIL: Could not set Program Office Only checkbox!");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
		
		return true;
		
	}
	
	
	public static boolean insertTo_SubScheduleListFormlet1_New(String ipasForm,String[] arr) throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,arr[0]),"FAIL: could not set Claim Name!");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, GeneralUtil.getTodayDate()), "FAIL: Could not set the Start Date");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, GeneralUtil.getNextYear()),"FAIL: Could not set Due Date");
		
		Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()),"FIAL: Could not set End Date");
		
		// For non Award eForm
		if (!ipasForm.contains(""))
		{				
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, ipasForm),"FAIL: Could not select PA Submission Form!");

		}			

		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl,Boolean.parseBoolean(arr[4])), "FAIL: Could not set Required checkbox!");
		
		Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_POOnlyTtl, Boolean.parseBoolean(arr[5])), "FAIL: Could not set Program Office Only checkbox!");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		
		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		
		ClicksUtil.clickButtons(IClicksConst.submitBtn);
		
		return true;
		
	}
	
	
	
	
	public static boolean update_Attachments(
			Map.Entry<EFormletTypes, List<String[]>> entry) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		for (String[] arr : entry.getValue()) {
			
			if(tDiv.body(id,ILBFunctionConst.lbf_FormletList_Table_Id).exists())
			{
				tDiv.body(id,ILBFunctionConst. lbf_FormletList_Table_Id).link(text, arr[0]).click();
			}
			else if(tDiv.body(id,ILBFunctionConst.lbf_AttachmentList_Table_Id).exists())
			{
				tDiv.body(id,ILBFunctionConst. lbf_AttachmentList_Table_Id).link(text, arr[0]).click();
			}
			else
			{
				log.error("Could not find the Attachment List!");
				return false;
			}			

			ie.textField(id,ILBFunctionConst. lbf_AttachmentDetails_DocDescription_FieldId).set(
					arr[2]);

			ie.fileField(id,ILBFunctionConst.lbf_AttachmentDetails_FileUpload_FieldId).set(
					"\"" + GeneralUtil.getWorkspacePath() +ILBFunctionConst.lbf_DocsFilesPath
							+ arr[5] + "\"");

			ClicksUtil.clickButtons(IClicksConst.submissionSummaryFieldsLnk);

		}
	
	    return true;
	    
	    }
	
	public static boolean verify_NO_Prop_Fields_Answers(String[] arr) throws Exception
	{
		
		IE ie = IEUtil.getActiveIE();
		
		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(ILBFunctionConst. lbf_NoProp_Approval_DropdownId).equals(arr[0])))
		{
			log.error("Could Not find: ".concat(arr[0]));
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			return false;
		}

		if (!(ie.checkbox(id, ILBFunctionConst.lbf_NoProp_Checkbox_FieldId).checked() == (Boolean.parseBoolean(arr[1])))) 
		{
			log.error("Could Not find: ".concat(arr[1]));
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			return false;
		}

		if (!ie.textField(id,ILBFunctionConst. lbf_NoProp_Date_FieldId).get().equals((GeneralUtil.getTodayDate()))) 
		{
			log.error("Could Not find: ".concat(GeneralUtil.getTodayDate()));
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			return false;
			
		}

		if (!ie.textField(id,ILBFunctionConst. lbf_NoProp_EINumber_FieldId).get().equals(arr[3])) 
		{
			log.error("Could Not find: ".concat(arr[3]));
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			return false;
		}

		if (!ie.textField(id,ILBFunctionConst. lbf_NoProp_EmailAddress_FieldId).get().equals(arr[4])) 
		{
			log.error("Could Not find: ".concat(arr[4]));
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			return false;
		}

		if (!ie.textField(id, ILBFunctionConst.lbf_NoProp_WebAddress_FieldId).get().equals(arr[5])) 
		{
			log.error("Could Not find: ".concat(arr[5]));
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		
		return true;
		
	}
	
	
	
	public static boolean verify_MinMaxPropFieldsAnswers(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();

		int start = 0;

			tDiv.body(id, lbf_FormletList_Table_Id).row(start).link(1).click();

			if (!ie.textField(id, lbf_MinMaxProp_LongText_FieldId).get()
					.equals(
							EFormsUtil.createRandomString(arr[0], Integer
									.parseInt(arr[1])))) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}
			
			if (!ie.textField(id, lbf_MinMaxProp_Numeric_FieldId).get().equals(
					LBFunctionUtil.formatNumber(Integer.parseInt(arr[1]), 4,
							DECORATION_DOLLARSIGN, false))) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!ie.textField(id, lbf_MinMaxProp_ShortText_FieldId).get()
					.equals(EFormsUtil.createRandomString(arr[3], 1500))) {
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!ie.textField(id, lbf_MinMaxProp_ReviewScore_FieldId).get()
					.equals(arr[4])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
	

		return true;
	}
	
	
	public static boolean verify_TypePropFieldsAnswers(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
			//ie.table(id, lbf_FormletList_Table_Id).body(id,lbf_FormletList_TableBody_Id).row(start).link(1).click();

			if (!ie.textField(id, lbf_TypeProp_PhoneNumber_FieldId).get()
					.equals(arr[0])) {
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			if (!ie.textField(id, lbf_TypeProp_PostalCode_FieldId).get()
					.equals(arr[1])) {
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			
		return true;
	}
	
	
	public static boolean verify_DataGridListFieldsAnswers(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();

			String frmt = LBFunctionUtil.formatNumber(Integer.parseInt(arr[0]), 2, DECORATION_DOLLARSIGN, false);
			
			//ie.table(id, lbf_FormletList_Table_Id).body(id,lbf_FormletList_TableBody_Id).row(start).link(1).click();

			if (!ie.textField(id, lbf_DataGrids_NumericCell_A1_Id).get().equals(frmt)) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_NumericCell_B2_Id).get().equals(frmt)) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_NumericCell_C3_Id).get().equals(frmt)) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_TextCell_A1_Id).get().equals(arr[1])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_TextCell_B2_Id).get().equals(arr[1])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!ie.textField(id, lbf_DataGrids_TextCell_C3_Id).get().equals(arr[1])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_DataGrids_DropdownCell_A1_Id).equals(arr[2])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_DataGrids_DropdownCell_B2_Id).equals(arr[2])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}

			if (!GeneralUtil.getSelectedItemValueInDropdwonById(lbf_DataGrids_DropdownCell_C3_Id).equals(arr[2])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				return false;

			}	

		return true;

	}
	

	
}


    
	
	

	
	


