/**
 * 
 */
package test_Suite.constants.cases;

/**
 * @author k.sharma
 *
 */
public interface IQuartzJobConst {
	
	//#### Msg #######
	
     public static final String msgJobTriggeredSuccessfully="Job successfully triggered.";
     
     //************* Quartz Job Name***********************
     
     public static final String quartzJobName[]={"NotificationsJobDetail","ApplicantFullTextSearchIndexingJob","SubmissionFullTextSearchIndexingJob","ResumePausedSteps"};

}
