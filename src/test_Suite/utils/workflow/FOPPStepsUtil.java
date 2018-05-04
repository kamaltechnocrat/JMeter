/**
 * 
 */
package test_Suite.utils.workflow;

import java.util.LinkedHashMap;

import test_Suite.constants.workflow.IStepsConst.EStepParams;
import test_Suite.constants.workflow.IStepsConst.EStepProps;
import test_Suite.lib.workflow.FOPPSteps;

/**
 * @author mshakshouki
 *
 */
public class FOPPStepsUtil {
	
	//not used
	public static void setStepParam(EStepParams eParam,Object obj, FOPPSteps step) throws Exception {
		LinkedHashMap<EStepParams, Object> lhm = step.getStepParamsLHM();
		
		lhm.put(eParam, obj);
	}
	
	public static String getStepParam(EStepParams eParam, FOPPSteps step) throws Exception {
		
		LinkedHashMap<EStepParams, Object> lhm = step.getStepParamsLHM();
		
		return lhm.get(eParam).toString();			
	}
	
	//not used
	public static void setStepProp(EStepProps eProp, Object obj, FOPPSteps step) throws Exception {
		
		LinkedHashMap<EStepProps, Object> lhm = step.getStepPropsLHM();
		
		lhm.put(eProp, obj);		
	}
	
	public static String getStepProp(EStepProps eProp, FOPPSteps step) throws Exception {
		
		LinkedHashMap<EStepProps, Object> lhm = step.getStepPropsLHM();
		
		return lhm.get(eProp).toString();			
	}

}
