/**
 * 
 */
package test_Suite.tests.stories.release_1_5.iter_1_6;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantsUtil;

/**
 * @author mshakshouki
 *
 */

@Test(singleThreaded = true)
public class S1768_03NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1768_03NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	int foUserBeat = 15;

	FOUsers foUser;
	
	String applicantName = IPreTestConst.FO_OrgShortName + "3";

	@BeforeClass(groups = {"ApplicantsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			//-------------------------
			
			ApplicantsUtil.openNewPOUserProfile(applicantName);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	} 
	
	@AfterClass(groups = {"ApplicantsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		foUser = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"ApplicantsNG" }, dataProvider = "profile-data", dataProviderClass = RegistrantDataProvider.class)
	public void testValidationsMandatoryFieldRegistrantProfileInPONG(String fName, String lName, String email, String confEmail,String locale,String uName,String pwd,String confPassword,String question, String answer) throws Exception {
		
		String[] registrant = new String[] {fName,lName,email,confEmail, locale, uName, pwd,confPassword, question, answer};
		
		String[] types = new String[] {"FirstName","LastName","Email","ConfEmail", "Locale", "UserName", "Password","ConfPassword", "Question", "Answer"};
		int indx = 0;
		
		foUser = new FOUsers(registrant,IPreTestConst.FrontUsers[0], foUserBeat);
		
		for(String string : registrant)
		{			
			log.info("Recieved " + types[indx] + ": it's Value: " + string);
			indx +=1;			
		}
		
		Assert.assertFalse(foUser.createPONewProfile(), "FAIL: Validation Expected");
	}

}
