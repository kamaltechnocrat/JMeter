/**
 * 
 */
package test_Suite.utils.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import test_Suite.constants.ui.*;
import test_Suite.utils.cases.GeneralUtil;
import watij.runtime.ie.IE;

/**
 * @author apankov
 *
 */
public class ErrorUtil implements test_Suite.constants.ui.IErrorConst{
	private static Log log = LogFactory.getLog(ErrorUtil.class);
	
	static boolean retValue;
	
	/**
	 * Check for an Unexpected Error method
	 * @param link
	 * @return boolean
	 */
	public static boolean checkForUnexpectedError(String link)
	{
		try
		{
			retValue = false;
			IE ie = IEUtil.getActiveIE();
			if(ie.text().contains(msgUnexpectedError))
			{
				log.error(tstTestFailed + " for " + link);
				ie.back();
				GeneralUtil.takeANap(1.0);
				retValue = true;
			}
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in checkForError " + link + " " + ex.getMessage());
		}
		
		return retValue;
	}
	
	/**
	 * Message on screen check
	 * @param msg
	 */
	public static void checkForRecordExistsError(String msg)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			if(ie.text().contains(msg))
				log.warn(tstTestWarning + " becasue of message: " + msg);

		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in checkForError " + msg + " " + ex.getMessage());
		}
	}
	
	/**
	 * @param strExpected
	 * @param strActual
	 * @param msgText
	 */
	public static void stringComparison(String strExpected, String strActual, String msgText)
	{
		if(strExpected.equals(strActual))
			log.info(IErrorConst.tstTestPassed + msgText + " Expected " + strExpected + " and Actual " + strActual);
		else
			log.warn(IErrorConst.tstTestFailed + msgText + " Expected " + strExpected + " but Actual " + strActual);
	}
}
