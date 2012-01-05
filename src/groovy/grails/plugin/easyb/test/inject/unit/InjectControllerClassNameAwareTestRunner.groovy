package grails.plugin.easyb.test.inject.unit

import grails.plugin.easyb.test.GrailsEasybTestType;

import org.apache.commons.lang.ClassUtils
import org.apache.commons.lang.StringUtils

class InjectControllerClassNameAwareTestRunner extends InjectGrailsTestRunner {
	String controllerClassName
		
	public InjectControllerClassNameAwareTestRunner(GrailsEasybTestType gett, String controllerClassName) {
		this.controllerClassName = controllerClassName
		this.grailsEasybTestType = gett
	}
	
	protected void beforeBehavior() {
		super.beforeBehavior()
		runnerType = "Controller classname aware test"
		if(controllerClassNameHasBeenPreset()){
			testCase.metaClass.controller = testCase.mockController(ClassUtils.getClass(controllerClassName))
		}
		if(binding && testCase.controller) {
			binding.setVariable("controller", testCase.controller)
			bindControllerUnitVariables(binding)
		}
	}
	
	private boolean controllerClassNameHasBeenPreset() {
		return StringUtils.isNotBlank(controllerClassName)
	}
	
	@Override
	public void beforeEachStep() {
		super.beforeEachStep()
	}
	
	@Override
	public void afterEachStep() {
		runJUnitAnnotatedMethods(org.junit.After)
	}
}
