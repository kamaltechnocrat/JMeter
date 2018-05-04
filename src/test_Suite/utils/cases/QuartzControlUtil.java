/**
 * 
 */
package test_Suite.utils.cases;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.alt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.cases.IQuartzJobConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.elements.Image;
import watij.elements.Images;
import watij.elements.Span;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;

/**
 * @author k.sharma
 * 
 */
public class QuartzControlUtil {

	private static Log log = LogFactory.getLog(QuartzControlUtil.class);

	static Tables tables;
	static Table table;
	static Images images;
	static Image image;
	static Span span;
	static int intValue;
	static int newIndex;
	static boolean retValue;

	public static boolean runQuartzJob(String QuartzJobName) throws Exception {
    	Div tDiv = TablesUtil.tableDiv();
    	
    	IE ie = IEUtil.getActiveIE();

		int rowIndex = -1;

		retValue = false;

		Assert.assertTrue(doesQuartzJobRunIconExists(QuartzJobName),
				"FAIL: Run icon doesn't exist ");

		rowIndex = TablesUtil.getRowIndex(ITablesConst.quartzControlTable,
				QuartzJobName);

		if (rowIndex > -1) {

			if (ie.body(id, ITablesConst.quartzControlTable).row(rowIndex).image(alt,"Run").exists()) {

				ie.body(id, ITablesConst.quartzControlTable).row(rowIndex).image(alt,"Run").click();
				
				retValue = true;
			}

			Assert.assertTrue(checkForJobTriggeredSuccessfully(QuartzJobName),
					"FAIL: Job doesn't triggred successfully");
		}

		return retValue;
	}

	public static boolean doesQuartzJobRunIconExists(String QuartzJobName)
			throws Exception {
		int rowIndex = -1;

    	Div tDiv = TablesUtil.tableDiv();
    	
    	IE ie = IEUtil.getActiveIE();

		if (!ClicksUtil.clickLinks(IClicksConst.openQuartzControl)) {
			log.error("Could not click on link "
					.concat(IClicksConst.openQuartzControl));
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.openQuartzControl);
		
		GeneralUtil.takeANap(1.0);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.quartzControlTable,
				QuartzJobName);

		if (rowIndex > -1) {

			if (ie.body(id, ITablesConst.quartzControlTable).row(rowIndex).image(alt,"Run")
					.exists()) {
				return true;
			}
		}

		return false;

	}

	public static boolean checkForJobTriggeredSuccessfully(String JobName) {
		try {
			retValue = false;

			IE ie = IEUtil.getActiveIE();
			if (ie.text().contains(IQuartzJobConst.msgJobTriggeredSuccessfully)) {
				
				log.warn("Job triggred successfully" + " for " + JobName);
				retValue = true;
			}
		} catch (Exception ex) {
			log.error("Job doesn't triggred sucessfully");
		}

		return retValue;
	}
}
