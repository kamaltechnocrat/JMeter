/**
 * 
 */
package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.*;

import test_Suite.constants.cases.ILookupsConst;
import test_Suite.lib.cases.Lookups;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import org.fest.swing.annotation.*;

/**
 * @author mshakshouki
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class CreateLookupsNG {

	private static Log log = LogFactory.getLog(CreateLookupsNG.class);

	Lookups childL;
	Lookups parentL;
	Lookups lookupValA;
	Lookups lookupValB;
	Lookups lookupValC;

	String subChildVal = " Gnrl LookupValue Child ";
	String subParentVal = " Gnrl LookupValue Parent ";

	@BeforeClass(groups = { "CasesNG" })
	public void setUp() {
		try {

			log.warn("Starting CreateLookupsNG");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			childL = new Lookups(ILookupsConst.lookupIdent, true, false);

			lookupValA = new Lookups("A", true, childL, new String[] {});
			lookupValB = new Lookups("B", true, childL, new String[] {});
			lookupValC = new Lookups("C", true, childL, new String[] {});

			childL.addNewLookupAndValues();

			parentL = new Lookups(ILookupsConst.lookupIdent, true, true);

			childL.setParent(parentL);
			parentL.setChild(childL);

			lookupValA = new Lookups("A", false, parentL, new String[] { "A"
					+ subChildVal + parentL.getChild().getLookupLetter() });
			lookupValB = new Lookups("B", false, parentL, new String[] { "B"
					+ subChildVal + parentL.getChild().getLookupLetter() });
			lookupValC = new Lookups("C", false, parentL, new String[] { "C"
					+ subChildVal + parentL.getChild().getLookupLetter() });

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

	}

	@AfterClass(groups = { "CasesNG" }, alwaysRun=true)
	public void tearDown() {
		
		childL = null;
		parentL = null;
		lookupValA = null;
		lookupValB = null;
		lookupValC = null;
		
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending CreateLookupsNG");
	}

	/**
	 * Creates the lookups ng.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test(groups = { "CasesNG" })
	public void createLookupsNG() throws Exception {
		try {

			parentL.addNewLookupAndValues();

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	/**
	 * Sets the lookup value.
	 * 
	 * @param lookupVal
	 *            the lookup val
	 * @param lookup
	 *            the lookup
	 * 
	 * @return the lookups
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public Lookups setLookupValue(Lookups lookupVal, Lookups lookup)
			throws Exception {

		lookupVal = new Lookups();

		return lookupVal;
	}

}
