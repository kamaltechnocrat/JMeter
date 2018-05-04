/**
 * 
 */
package test_Suite.tests.stories.release_1_5.iter_1_6;


import org.testng.annotations.DataProvider;

/**
 * @author mshakshouki
 *
 */
public class RegistrantDataProvider {
	static int foUserBeat =9;
	
	@DataProvider(name = "profile-data")
	public static Object[][] generateRegistrantProfileData() throws Exception {		
		
		return new Object[][] {
				{"", "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","","/English/", "front" + foUserBeat, "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "", "a11", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a11","a11",""},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a22", "a11","a11","a11"},
				{"Front-" + foUserBeat, "LRegistrant-"+ foUserBeat,"Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","Registrant" + foUserBeat +  "@g3-qa-autobuild.csdc-lan.csdcsystems.com","/English/", "front" + foUserBeat, "a11", "a22","a11","a11"}};
		
	}

}
