package test_Suite.utils.workflow;

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.title;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.runtime.ie.IE;

public class ReferenceTablesUtil {

	private static Log log = LogFactory.getLog(StepsUtil.class);

	static int rowIndex;

	public static boolean insertTo_SingleFilterEList(String[] arr)
			throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn));

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseType, arr[1]),
				"Fail: Couldn't select from dropdown list:".concat(arr[1]));
		
		GeneralUtil.takeANap(1.5);

		Assert.assertTrue(
				ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn),
				"Fail: Couldn't find save and add another button");

		return true;

	}

	public static boolean insertTo_SingleFilter(String[] arr) throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseType, arr[1]),
				"Fail: Couldn't select from dropdown list:".concat(arr[1]));

		GeneralUtil.takeANap(3.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		return true;

	}

	public static boolean insertTo_SingleFilterEListTest(String[] arr)
			throws Exception {

		ClicksUtil.clickImage(IClicksConst.newImg);

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		Assert.assertFalse(GeneralUtil.selectFullStringInDropdownList(
				"g3-form:eFormFieldList:1:numericDropdown_input", arr[1]),
				"Fail: Dropdown list should not contain".concat(arr[1]));

		GeneralUtil.takeANap(0.5);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldn't find Back to List button");

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);

		Assert.assertTrue(
				FiltersUtil.filterRefTablesListByDiv(
						IRefTablesConst.refTable_BudgetYear_Lbl,
						IRefTablesConst.budgetYear2018,
						IRefTablesConst.refTable_Exact),
				"Fail: Couldn't filter Reference tables list by division");

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(0)
				.link(title, "Delete this list item").click();

		GeneralUtil.takeANap(1.0);

		ClicksUtil.clickButtons("Ok");

		return false;

	}

	public static boolean insertTo_SingleFilterTest(String[] arr)
			throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find Save button");

		Assert.assertFalse(GeneralUtil.selectFullStringInDropdownList(
				"g3-form:eFormFieldList:1:numericDropdown_input", arr[1]),
				"Fail: Couldn't select from dropdown list:".concat(arr[1]));

		GeneralUtil.takeANap(3.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldn't find Back to List button");

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.showFiltersLnk),
				"Fail: Couldn't find Show Filters Link");

		Assert.assertTrue(
				FiltersUtil.filterRefTablesListByDiv(
						IRefTablesConst.refTable_BudgetYear_Lbl,
						IRefTablesConst.budgetYear2018,
						IRefTablesConst.refTable_Exact),
				"Fail: Couldn't filter Reference tables list by division");

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(0)
				.link(title, "Delete this list item").click();

		GeneralUtil.takeANap(1.0);

		ClicksUtil.clickButtons("Ok");

		return true;

	}

	public static boolean insertTo_SFilterTest(String[] arr) throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		Assert.assertFalse(GeneralUtil.selectFullStringInDropdownList(
				"g3-form:eFormFieldList:1:numericDropdown_input", arr[1]),
				"Fail: Dropdown list should not contain".concat(arr[1]));

		GeneralUtil.takeANap(1.0);

		return false;

	}

	public static boolean insertTo_Db_Filter_Test(String[] arr)
			throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.df_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(
				"g3-form:eFormFieldList:1:numericDropdown_input", arr[1]),
				"Fail: Couldn't select from dropdown list:".concat(arr[1]));

		GeneralUtil.takeANap(1.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		Assert.assertFalse(GeneralUtil.selectFullStringInDropdownList(
				"g3-form:eFormFieldList:1:numericDropdown_input", arr[2]),
				"Fail: List shouldn't contain".concat(arr[2]));

		GeneralUtil.takeANap(1.0);

		return false;

	}

	public static boolean insertTo_DoubleFilterEListTest(String[] arr)
			throws Exception {

		ClicksUtil.clickImage(IClicksConst.newImg);

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.df_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseCategory, arr[1]),
				"Fail: Couldn't select from dropdown list:".concat(arr[1]));

		GeneralUtil.takeANap(3.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find save button");

		Assert.assertFalse(GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseType, arr[2]),
				"Fail: List shouldn't contain".concat(arr[2]));

		GeneralUtil.takeANap(3.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldn't find Back to List button");

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.showFiltersLnk),
				"Fail: Couldn't find Show Filters Link");

		Assert.assertTrue(
				FiltersUtil.filterRefTablesListByDiv(
						IRefTablesConst.refTable_BudgetYear_Lbl,
						IRefTablesConst.budgetYear2018,
						IRefTablesConst.refTable_Exact),
				"Fail: Couldn't filter Reference tables list by division");

//		Thread dialogClicker = new Thread() {
//			@SuppressWarnings("static-access")
//			@Override
//			public void run() {
//				try {
//					IE ie = IEUtil.getActiveIE();
//					ConfirmDialog dialog1 = ie.confirmDialog();
//					while (dialog1 == null) {
//						log.debug("can't yet get handle on confirm dialog1");
//
//						this.sleep(0);
//					}
//
//					dialog1.ok();
//
//					log.debug("got confirm Dialog1 and clicked OK.");
//
//				} catch (Exception e) {
//					throw new RuntimeException("Unexpected exception", e);
//				}
//			}
//		};
//
//		dialogClicker.start();
//		log.debug("started dialog clicker thread");

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(0).image(0)
				.click();
		
		ClicksUtil.clickButtons("Ok");

//		dialogClicker = null;

		// return true;
		return false;
	}

	public static boolean amend_singleFilter_data(String[] arr)
			throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limits_Table_Id).row(0).link(1)
				.click();

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList_Amend[0]),
				"Fail: Couldn't insert to single Filter");

		Assert.assertTrue(
				ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn),
				"Fail: Couldn't find Save and Back to List button");

		return true;

	}

	public static boolean amend_doubleFilter_data(String[] arr)
			throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limits_Table_Id).row(0).link(1)
				.click();

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList_Amend[0]),
				"Fail: Couldn't insert data to double filter");

		Assert.assertTrue(
				ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn),
				"Fail: Couldn't find Save and Back to List button");

		return true;

	}

	public static boolean amendSingleFilter() throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limits_Table_Id).row(0).link(1)
				.click();

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList_Amend[0]),
				"Fail: Couldn't insert data to single filter");

		Assert.assertTrue(
				ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn),
				"Fail: Couldn't find Save and Back to List button");

		return true;

	}

	public static boolean verify_singleFilterEList_data(String[] arr)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		GeneralUtil.takeANap(2.5);

		String[] str = { arr[0], arr[3] };

		rowIndex = TablesUtil.findInTable(
				IRefTablesConst.budget_Limit_Table_Id, str);

		if (rowIndex > -1) {

			log.warn(rowIndex);

			tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(rowIndex)
					.image(alt, IClicksConst.orgViewListItem_Alt).click();

			if (!ie.textField(id, IRefTablesConst.sf_budget_Year_TxtFieldId)
					.get().equals(arr[0])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.sf_expense_Type_DropdownId).equals(arr[1]))) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id, IRefTablesConst.sf_budget_Limit_TxtFieldId)
					.get().equals(arr[3])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id, IRefTablesConst.sf_budget_short_TxtFieldId)
					.get().equals(arr[4])) {

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			rowIndex++;

		}

		return true;

	}

	public static boolean verify_singleFilterEList_amendedData(String[] arr)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		GeneralUtil.takeANap(1.5);

		String[] str = { arr[0], arr[4] };

		rowIndex = TablesUtil.findInTable(
				IRefTablesConst.budget_Limit_Table_Id, str);

		if (rowIndex > -1) {

			log.warn(rowIndex);

			tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(rowIndex)
					.image(alt, IClicksConst.orgViewListItem_Alt).click();

			if (!ie.textField(id, IRefTablesConst.sf_budget_Year_TxtFieldId)
					.get().equals(arr[0])) {
				log.error("Could Not find: ".concat(arr[0]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.sf_expense_Type_DropdownId).equals(arr[2]))) {
				log.error("Could Not find: ".concat(arr[2]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id, IRefTablesConst.sf_budget_Limit_TxtFieldId)
					.get().equals(arr[3])) {
				log.error("Could Not find: ".concat(arr[3]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id, IRefTablesConst.sf_budget_short_TxtFieldId)
					.get().equals(arr[4])) {
				log.error("Could Not find: ".concat(arr[4]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			rowIndex++;

		}

		return true;

	}

	public static boolean verify_singleFilterList_data() throws Exception {

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[0], 0));

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[1], 1));

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[4], 2));

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[3], 3));

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[6], 4));

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[5], 5));

		Assert.assertTrue(verify_singleFilterEList(
				IRefTablesConst.verify_budgetYear_Fields_eList[8], 6));

		return true;

	}

	public static boolean verify_doubleFilterEList_data(String[] arr)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		GeneralUtil.takeANap(1.5);

		String[] lst = { arr[0], arr[4] };

		rowIndex = TablesUtil.findInTable(
				IRefTablesConst.budget_Limit_Table_Id, lst);

		if (rowIndex > -1) {

			log.warn(rowIndex);

			tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(rowIndex)
					.image(alt, IClicksConst.orgViewListItem_Alt).click();

			if (!ie.textField(id, IRefTablesConst.df_budget_Year_TxtFieldId)
					.get().equals(arr[0])) {
				log.error("Could Not find: ".concat(arr[0]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!(GeneralUtil.getSelectedItemValueInDropdwonById(
					IRefTablesConst.df_expense_category_DropdownId)
					.equals(arr[2]))) {
				log.error("Could Not find expenseCategory in dropdown: "
						.concat(arr[2]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id, IRefTablesConst.df_budget_short_TxtFieldId)
					.get().equals(arr[4])) {
				log.error("Cant find short txtField: ".concat(arr[4]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!GeneralUtil.getSelectedItemValueInDropdwonById(
					IRefTablesConst.dfList_expense_Type_DropdownId)
					.equals(arr[1])) {
				log.error("Could Not find expenseType in dropdown: "
						.concat(arr[1]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id,
					IRefTablesConst.dfList_budget_Limit_TxtFieldId).get()
					.equals(arr[3])) {
				log.error("Could Not find: ".concat(arr[3]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			Assert.assertTrue(ClicksUtil
					.clickButtons(IClicksConst.backToListBtn));

			rowIndex++;

		}

		return true;

	}

	public static boolean verify_doubleFilterEList_amendedData(String[] arr)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		GeneralUtil.takeANap(1.5);

		String[] lst = { arr[0], arr[4] };

		rowIndex = TablesUtil.findInTable(
				IRefTablesConst.budget_Limit_Table_Id, lst);

		if (rowIndex > -1) {

			log.warn(rowIndex);

			tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(rowIndex)
					.image(alt, IClicksConst.orgViewListItem_Alt).click();

			if (!ie.textField(id, IRefTablesConst.df_budget_Year_TxtFieldId)
					.get().equals(arr[0])) {
				log.error("Could Not find: ".concat(arr[0]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!(GeneralUtil.getSelectedItemValueInDropdwonById(
					IRefTablesConst.df_expense_category_DropdownId)
					.equals(arr[1]))) {
				log.error("Could Not find expenseCategory in dropdown: "
						.concat(arr[1]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id, IRefTablesConst.df_budget_short_TxtFieldId)
					.get().equals(arr[2])) {
				log.error("Cant find short txtField: ".concat(arr[2]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!(GeneralUtil.getSelectedItemValueInDropdwonById(
					IRefTablesConst.dfList_expense_Type_DropdownId)
					.equals(arr[4]))) {
				log.error("Could Not find expenseType in dropdown: "
						.concat(arr[4]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			if (!ie.textField(id,
					IRefTablesConst.dfList_budget_Limit_TxtFieldId).get()
					.equals(arr[3])) {
				log.error("Could Not find: ".concat(arr[3]));

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				return false;
			}

			Assert.assertTrue(ClicksUtil
					.clickButtons(IClicksConst.backToListBtn));

			rowIndex++;

		}

		return true;

	}

	public static boolean verify_singleFilterEList(String[] arr, int start)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		GeneralUtil.takeANap(1.5);

		tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(start).link(1)
				.click();

		if (!ie.textField(id, IRefTablesConst.sf_budget_Year_TxtFieldId).get()
				.equals(arr[0])) {
			log.error("Could Not find: ".concat(arr[0]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.sf_expense_Type_DropdownId)
				.equals(arr[1]))) {
			log.error("Could Not find: ".concat(arr[1]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		if (!ie.textField(id, IRefTablesConst.sf_budget_Limit_TxtFieldId).get()
				.equals(arr[2])) {
			log.error("Could Not find: ".concat(arr[2]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		if (!ie.textField(id, IRefTablesConst.sf_budget_short_TxtFieldId).get()
				.equals(arr[3])) {
			log.error("Could Not find: ".concat(arr[3]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

		return true;

	}

	public static boolean verify_singleFilter(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();

		GeneralUtil.takeANap(2.5);

		if (!ie.textField(id, IRefTablesConst.sf_budget_Year_TxtFieldId).get()
				.equals(arr[0])) {
			log.error("Could Not find: ".concat(arr[0]));
			return false;
		}

		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.sf_expense_Type_DropdownId)
				.equals(arr[1]))) {
			log.error("Could Not find: ".concat(arr[1]));
			return false;
		}

		if (!ie.textField(id, IRefTablesConst.sf_budget_Limit_TxtFieldId).get()
				.equals(arr[2])) {
			log.error("Could Not find: ".concat(arr[2]));

			return false;
		}

		return true;

	}

	public static boolean insertTo_DoubleFilterEList(String[] arr)
			throws Exception {

		if (!GeneralUtil.setTextByTitle(
				IRefTablesConst.df_budget_Year_FieldTtl, arr[0])) {

			log.error("Couldn't set text".concat(arr[0]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;

		}

		if (!ClicksUtil.clickButtons(IClicksConst.saveBtn))

		{
			log.error("Couldnt find Save Button");

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;

		}

		if (!GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseCategory, arr[1])) {
			log.error("Couldnt find string in dropdown list".concat(arr[1]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;

		}

		if (!ClicksUtil.clickButtons(IClicksConst.saveBtn))

		{
			log.error("Couldnt find Save Button");

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;

		}

		if (!GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseType, arr[2])) {

			log.error("Couldnt find string in dropdown list".concat(arr[2]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;

		}

		if (!ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn))

		{
			log.error("Couldnt find Save and add another Button");

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;

		}

		return true;

	}

	public static boolean verify_doubleFilter_EList(String[] arr, int start)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, IRefTablesConst.budget_Limit_Table_Id).row(start).link(1)
				.click();

		if (!ie.textField(id, IRefTablesConst.df_budget_Year_TxtFieldId).get()
				.equals(arr[0])) {
			log.error("Could Not find: ".concat(arr[0]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.df_expense_category_DropdownId)
				.equals(arr[1]))) {
			log.error("Could Not find: ".concat(arr[1]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.dfList_expense_Type_DropdownId)
				.equals(arr[2]))) {
			log.error("Could Not find: ".concat(arr[2]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		if (!ie.textField(id, IRefTablesConst.dfList_budget_Limit_TxtFieldId)
				.get().equals(arr[3])) {
			log.error("Could Not find: ".concat(arr[3]));

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);

			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

		return true;

	}

	public static boolean verify_doubleFilter(String[] arr) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (!ie.textField(id, IRefTablesConst.df_budget_Year_TxtFieldId).get()
				.equals(arr[0])) {
			log.error("Could Not find: ".concat(arr[0]));

			return false;
		}

		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.df_expense_category_DropdownId)
				.equals(arr[1]))) {
			log.error("Could Not find: ".concat(arr[1]));

			return false;
		}

		if (!(GeneralUtil.getSelectedItemValueInDropdwonById(IRefTablesConst.df_expense_Type_Id)
				.equals(arr[2]))) {
			log.error("Could Not find: ".concat(arr[2]));

			return false;
		}

		if (!ie.textField(id, IRefTablesConst.df_budget_Limit_Id).get()
				.equals(arr[3])) {
			log.error("Could Not find: ".concat(arr[3]));

			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return true;

	}

	public static boolean insertTo_RefTablesRows(String[] arr) throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_budget_Year_FieldTtl, arr[0]),
				"Fail: Couldnt set text".concat(arr[0]));

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.df_expense_Category_dropdown, arr[1]),
				"Fail: Couldnt set text".concat(arr[1]));

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.sf_expense_type_dropdown, arr[2]),
				"Fail: Couldnt set text".concat(arr[2]));

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.allocated_budgetTtl, arr[3]),
				"Fail: Couldnt set text".concat(arr[3]));

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.start_Date, GeneralUtil.getTodayDate()),
				"FAIL: Could not set the Start Date");

		Assert.assertTrue(GeneralUtil.setTextByTitle(IRefTablesConst.end_Date,
				GeneralUtil.getNextYear()), "FAIL: Could not set Due Date");

		Assert.assertTrue(
				ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn),
				"Fail: Couldnt find Save and Back to List Button");

		return true;

	}

	public static boolean dropdownList_verification_sf(String drpDwnId,
			String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (!ie.selectList(id, drpDwnId).exists()) {

			log.error("Could not find the dropdown to select from!");
			return false;
		}

		List<String> lst = ie.selectList(id, drpDwnId).getAllContents();

		if (lst.size() > 0) {

			for (String string : lst) {

				log.warn(string);

				if (string == strToFind) {

					log.warn("values match");

					return true;
				}

			}

		}
		return false;

	}

	public static boolean insertDataToSingleFilterEList() throws Exception {

		ClicksUtil.clickImage(IClicksConst.newImg);

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[0]),
				"Fail: Couldn't insert data to Single Filter eList[0]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[1]),
				"Fail: Couldn't insert data to Single Filter eList[1]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[4]),
				"Fail: Couldn't insert data to Single Filter eList[4]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[3]),
				"Fail: Couldn't insert data to Single Filter eList[3]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[6]),
				"Fail: Couldn't insert data to Single Filter eList[6]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[5]),
				"Fail: Couldn't insert data to Single Filter eList[5]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList[8]),
				"Fail: Couldn't insert data to Single Filter eList[8]");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldnt find Back To List button");

		return true;

	}

	public static boolean insertDataToSingleFilterEList_Amend()
			throws Exception {

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[0]),
				"Fail: Couldn't insert data to Single Filter eList[0]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[1]),
				"Fail: Couldn't insert data to Single Filter eList[1]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[2]),
				"Fail: Couldn't insert data to Single Filter eList[2]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[3]),
				"Fail: Couldn't insert data to Single Filter eList[3]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[4]),
				"Fail: Couldn't insert data to Single Filter eList[4]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[5]),
				"Fail: Couldn't insert data to Single Filter eList[5]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilterEList(IRefTablesConst.budgetYear_Fields_eList_Amend[6]),
				"Fail: Couldn't insert data to Single Filter eList[6]");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldnt find Back To List button");

		return true;

	}

	public static boolean insertDataToSingleFilterAndVerify() throws Exception {

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[0]),
				"Fail: Couldn't insert data to Single Filter[0]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[0]),
				"Fail: Couldnt verify data at index[0]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[1]),
				"Fail: Couldn't insert data to Single Filter[1]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[1]),
				"Fail: Couldnt verify data at index[1]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[4]),
				"Fail: Couldn't insert data to Single Filter[4]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[4]),
				"Fail: Couldnt verify data at index[4]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[3]),
				"Fail: Couldn't insert data to Single Filter[3]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[3]),
				"Fail: Couldnt verify data at index[3]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[6]),
				"Fail: Couldn't insert data to Single Filter[6]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[6]),
				"Fail: Couldnt verify data at index[6]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[5]),
				"Fail: Couldn't insert data to Single Filter[5]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[5]),
				"Fail: Couldnt verify data at index[5]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_SingleFilter(IRefTablesConst.budgetYear_Fields_eList[8]),
				"Fail: Couldn't insert data to Single Filter[8]");

		Assert.assertTrue(
				verify_singleFilter(IRefTablesConst.verify_budgetYear_Fields_eList[8]),
				"Fail: Couldnt verify data at index[8]");

		return true;

	}

	public static boolean insertDataToDoubleFilterAndVerify() throws Exception {

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[0]),
				"Fail: Couldn't insert data to Double Filter at index[0]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[0]),
				"Fail: Couldnt verify data at index[0]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[1]),
				"Fail: Couldn't insert data to Double Filter at index[1]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[1]),
				"Fail: Couldnt verify data at index[1]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[4]),
				"Fail: Couldn't insert data to Double Filter at index[4]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[4]),
				"Fail: Couldnt verify data at index[4]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[3]),
				"Fail: Couldn't insert data to Double Filter at index[3]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[3]),
				"Fail: Couldnt verify data at index[3]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[6]),
				"Fail: Couldn't insert data to Double Filter at index[6]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[6]),
				"Fail: Couldnt verify data at index[6]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[5]),
				"Fail: Couldn't insert data to Double Filter at index[5]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[5]),
				"Fail: Couldnt verify data at index[5]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[8]),
				"Fail: Couldn't insert data to Double Filter at index[8]");

		Assert.assertTrue(
				verify_doubleFilter(IRefTablesConst.verify_yearlyLimit_Fields[8]),
				"Fail: Couldnt verify data at index[8]");

		return true;

	}

	public static boolean insertDataToDoubleFilterEList() throws Exception {

		ClicksUtil.clickImage(IClicksConst.newImg);

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[0]),
				"Fail: Couldn't insert data to Double Filter eList at index[0]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[1]),
				"Fail: Couldn't insert data to Double Filter eList at index[1]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[4]),
				"Fail: Couldn't insert data to Double Filter eList at index[4]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[3]),
				"Fail: Couldn't insert data to Double Filter eList at index[3]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[6]),
				"Fail: Couldn't insert data to Double Filter eList at index[6]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[5]),
				"Fail: Couldn't insert data to Double Filter eList at index[5]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEList(IRefTablesConst.yearlyLimit_Fields_eList[8]),
				"Fail: Couldn't insert data to Double Filter eList at index[8]");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldn't find Back to List button");

		return true;

	}

	public static boolean insertDataToDoubleFilter() throws Exception {

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[0]),
				"Fail: Couldn't insert data to Double Filter at index [0]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[1]),
				"Fail: Couldn't insert data to Double Filter at index [1]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[4]),
				"Fail: Couldn't insert data to Double Filter at index[4]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[3]),
				"Fail: Couldn't insert data to Double Filter at index[3]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[6]),
				"Fail: Couldn't insert data to Double Filter at index[6]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[5]),
				"Fail: Couldn't insert data to Double Filter at index[5]");

		Assert.assertTrue(
				ReferenceTablesUtil
						.insertTo_DoubleFilter(IRefTablesConst.yearlyLimit_Fields_eList[8]),
				"Fail: Couldn't insert data to Double Filter at index[8]");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backToListBtn),
				"Fail: Couldn't find Back to List button");

		return true;

	}

	public static boolean insertTo_DoubleFilter(String[] arr) throws Exception {

		Assert.assertTrue(GeneralUtil.setTextByTitle(
				IRefTablesConst.df_budget_Year_FieldTtl, arr[0]),
				"Fail: Could Not set text: ".concat(arr[0]));

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find Save button");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseCategory, arr[1]),
				"Fail: Couldnt find string in dropdown list".concat(arr[1]));

		GeneralUtil.takeANap(3.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find Save button");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(
				IEFormsConst.dropDownTitle_ExpenseType, arr[2]),
				"Fail: Couldnt find string in dropdown list".concat(arr[2]));

		GeneralUtil.takeANap(3.0);

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn),
				"Fail: Couldn't find Save button");

		return true;

	}

	public static boolean openReferenceTable_Add_Data(String refTableName,
			String budgetYear, String expenseCategory, String expenseType)
			throws Exception {

		Assert.assertTrue(FiltersUtil.filterListByLabel(
				IRefTablesConst.refTableName_lbl,
				IRefTablesConst.refTable_TxtField_lbl, "Exact"));

		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.refTable_body_Id).row(0).link(title, "Open")
				.click();

		Assert.assertFalse(
				GeneralUtil.isLinkExistsByTitle("General Information"),
				"Fail: General information link should not be present");

		Assert.assertFalse(
				GeneralUtil.isLinkExistsByTitle("Contact Information"),
				"Fail: Contact information link should not be present");

		Assert.assertFalse(
				GeneralUtil.isLinkExistsByTitle("Address Information"),
				"Fail: Address information link should not be present");

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.showFiltersLnk),
				"Fail: Couldn't find Show Filters Link");

		Assert.assertTrue(FiltersUtil.filterRefTablesListByDiv(
				IRefTablesConst.refTable_BudgetYear_Lbl, budgetYear, "Exact"),
				"Fail: Couldn't set the value for Yearly Budgets");

		Assert.assertTrue(FiltersUtil.filterRefTablesListByDiv(
				IRefTablesConst.refTable_Expense_Category_Lbl, expenseCategory,
				"Exact"), "Fail: Couldn't set the value for Expense Category");

		Assert.assertTrue(FiltersUtil
				.filterRefTablesListByDiv(
						IRefTablesConst.refTable_Expense_Type_Lbl, expenseType,
						"Exact"),
				"Fail: Couldn't set the value for Expense Type");

		int valuesCount = TablesUtil
				.howManyEntriesInTable(IRefTablesConst.refTableList_Id);

		if (valuesCount <= 0) {

			ClicksUtil.clickImage(IClicksConst.newImg);

			Assert.assertTrue(
					ReferenceTablesUtil
							.insertTo_RefTablesRows(IRefTablesConst.reference_Table_Row_List[0]),
					"Fail: Couldnt add a row to Reference table");

		}

		Assert.assertTrue(
				ClicksUtil.clickLinks("Back to Reference Table List"),
				"Fail: Couldn't find Back to Reference Table List Link");

		return true;

	}

	public static boolean openReferenceTable_DeleteRow(String refTableName,
			String budgetYear, String expenseCategory, String expenseType)
			throws Exception {

		Assert.assertTrue(FiltersUtil.filterPOListByLabel(
				IRefTablesConst.refTableName_lbl,
				IRefTablesConst.refTable_TxtField_lbl, "Exact"),
				"Fail: Could't set the value for Budget Reference Table");
		
		Div tDiv = TablesUtil.tableDiv();

		tDiv.body(id, ITablesConst.refTable_body_Id).row(0).link(title, "Open")
				.click();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.showFiltersLnk),
				"Fail: Couldnt find Show Filters Link");

		Assert.assertTrue(FiltersUtil.filterRefTablesListByDiv(
				IRefTablesConst.refTable_BudgetYear_Lbl, budgetYear, "Exact"),
				"Fail: Could't set the value for Yearly Budgets");

		Assert.assertTrue(FiltersUtil.filterRefTablesListByDiv(
				IRefTablesConst.refTable_Expense_Category_Lbl, expenseCategory,
				"Exact"), "Fail: Could't set the value for Expense Category");

		Assert.assertTrue(FiltersUtil
				.filterRefTablesListByDiv(
						IRefTablesConst.refTable_Expense_Type_Lbl, expenseType,
						"Exact"),
				"Fail: Could't set the value for Expense type");

		// Thread dialogClicker = new Thread() {
		// @SuppressWarnings("static-access")
		// @Override
		// public void run() {
		// try {
		// IE ie = IEUtil.getActiveIE();
		// ConfirmDialog dialog1 = ie.confirmDialog();
		// while (dialog1 == null)
		// {
		// log.debug("can't yet get handle on confirm dialog1");
		//
		// this.sleep(10);
		// }
		//
		// dialog1.ok();
		//
		// log.debug("got confirm Dialog1 and clicked OK.");
		//
		// } catch (Exception e) {
		// throw new RuntimeException("Unexpected exception", e);
		// }
		// }
		// };
		//
		// dialogClicker.start();
		// log.debug("started dialog clicker thread");

		int valuesCount = TablesUtil
				.howManyEntriesInTable(IRefTablesConst.refTableList_Id);

		tDiv = TablesUtil.tableDiv();

		if (valuesCount > 0) {
			tDiv = TablesUtil.tableDiv();

			tDiv.body(id, IRefTablesConst.refTableList_Id).row(0)
					.link(title, "Delete this list item").click();

			GeneralUtil.takeANap(1.0);

			ClicksUtil.clickButtons("Ok");

		}

		// dialogClicker = null;

		Assert.assertTrue(
				ClicksUtil.clickLinks("Back to Reference Table List"),
				"Fail: Couldn't find Back to Reference Table List link");

		return true;

	}

}
