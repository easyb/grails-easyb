package grails.plugin.easyb.test.inject.unit

import grails.plugin.easyb.test.GrailsEasybTestType

import org.apache.commons.lang.ClassUtils
import org.apache.commons.lang.StringUtils

class InjectTagLibClassNameAwareTestRunner extends InjectGrailsTestRunner {
	String tagLibClassName

	public InjectTagLibClassNameAwareTestRunner(GrailsEasybTestType gett, String tagLibClassName) {
		this.tagLibClassName = tagLibClassName
		this.grailsEasybTestType = gett
	}
	
	protected void beforeBehavior() {
		super.beforeBehavior()
		runnerType = "TagLib classname aware test"
		if(tagLibClassNameHasBeenPreset()){
			testCase.mockTagLib(ClassUtils.getClass(tagLibClassName))
		}
//		if(binding && testCase.controller) {
//			binding.setVariable("controller", testCase.controller)
//			bindControllerUnitVariables(binding)
//		}
	}
	
	private boolean tagLibClassNameHasBeenPreset() {
		return StringUtils.isNotBlank(tagLibClassName)
	}
}
