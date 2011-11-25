package grails.plugin.easyb.test.inject.integration
import grails.plugin.easyb.test.inject.InjectTestRunner;


import grails.plugin.easyb.test.GrailsEasybTestType
import grails.plugin.easyb.test.inject.TestRunnerFactory

import org.easyb.domain.Behavior

public class InjectIntegrationTestRunnerFactory implements TestRunnerFactory {
	private static String pathPartMustExist = "test" + File.separator + "integration"

	@Override
	public boolean willRespond(Behavior currentBehaviour, GrailsEasybTestType gett) {
		return gett.testType == "integration" && currentBehaviour.file.absolutePath.indexOf(pathPartMustExist) >= 0
	}

	@Override
	InjectTestRunner findMatchingRunner(String expectedMatchingGrailsClass, Behavior currentBehaviour, GrailsEasybTestType gett) {
	}

	@Override
	InjectTestRunner findDynamicRunner(String style, String name, String expectedMatchingGrailsClass, Behavior currentBehavior, GrailsEasybTestType gett) {
	}

	@Override
	InjectTestRunner getDefaultTestRunner(GrailsEasybTestType gett) {
		return new InjectGroovyTestCaseTestRunner(grailsEasybTestType: gett)
	}
}
