/*
 * User: richard
 * Date: Jun 18, 2009
 * Time: 9:20:50 PM
 */
package grails.plugin.easyb.test.inject.unit

import grails.plugin.easyb.test.GrailsEasybTestType;
import grails.plugin.easyb.test.inject.InjectTestRunner;
import grails.plugin.easyb.test.inject.TestRunnerFactory;

import org.easyb.domain.Behavior;

public class InjectUnitTestRunnerFactory implements TestRunnerFactory {
    private static String pathPartMustExist = "test" + File.separator + "unit"

    @Override
    public boolean willRespond(Behavior currentBehavior, GrailsEasybTestType gett) {
        return gett.testType == "unit" && currentBehavior.file.absolutePath.indexOf(pathPartMustExist) >= 0
    }

    @Override
    public InjectTestRunner findMatchingRunner(String expectedMatchingGrailsClass, Behavior currentBehavior, GrailsEasybTestType gett) {
        return detectRunner(expectedMatchingGrailsClass)
    }

    @Override
    public InjectTestRunner getDefaultTestRunner(GrailsEasybTestType gett) {
        return new InjectGrailsTestRunner(grailsEasybTestType: gett)
    }

    @Override
    public InjectTestRunner findDynamicRunner(String style, String name, String expectedMatchingGrailsClass, Behavior currentBehavior, GrailsEasybTestType gett) {
        return new InjectGrailsTestRunner(grailsEasybTestType: gett, behavior: currentBehavior)
    }
	
	private def detectRunner(className) {
		if(className.endsWith("Controller")) {
			return new InjectGrailsTestRunner(className)
		} else if(className.endsWith("ControllerStory")) {
			return new InjectGrailsTestRunner(className.substring(0, className.indexOf("Story")))
		} else if(className.endsWith("ControllerSpecification")) {
			return new InjectGrailsTestRunner(className.substring(0, className.indexOf("Specification")))
		} 
		return null
	}
}