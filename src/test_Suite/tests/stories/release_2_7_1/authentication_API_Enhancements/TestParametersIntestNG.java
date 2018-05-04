/**
 * 
 */
package test_Suite.tests.stories.release_2_7_1.authentication_API_Enhancements;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author mshakshouki
 *
 */
@Test(singleThreaded = true)
public class TestParametersIntestNG {


	private static Log log = LogFactory.getLog(TestParametersIntestNG.class);
	
	@Parameters({"one"})
	@Test(groups = {"paramsNG"})
	public void testParams(boolean come) throws Exception {
		log.info("S3523_POUserLogsIn_AuthExtension_01NG has been Started");
	}

}
