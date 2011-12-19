package grails.plugin.easyb.test.inject.integration
import grails.plugin.easyb.test.GrailsEasybTestType;
import grails.plugin.easyb.test.inject.InjectTestRunner
import groovy.lang.Binding

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.test.support.GrailsTestRequestEnvironmentInterceptor;
import org.codehaus.groovy.grails.test.support.GrailsTestTransactionInterceptor
import org.springframework.context.ApplicationContext

class InjectGroovyTestCaseTestRunner extends InjectTestRunner {
	GrailsTestTransactionInterceptor transactionInterceptor
	GrailsTestRequestEnvironmentInterceptor requestEnvironmentInterceptor
	String controllerClassName
	protected void beforeBehavior() {
		runnerType = "Groovy Test Case"
		this.testCase = new JUnit4TestCase()
		if(controllerClassName != null && controllerClassName != '') {
			this.testCase.metaClass.controller = controllerClassName
		}
		this.transactionInterceptor = new GrailsTestTransactionInterceptor(getAppCxt())
		this.requestEnvironmentInterceptor = new GrailsTestRequestEnvironmentInterceptor(getAppCxt())
	}
	
	@Override
	public void beforeEachStep() {
		super.beforeEachStep()
		
		if(testCase.transactional) {
			transactionInterceptor.init()
		}
		if(testCase.hasProperty('controller') && testCase.controller) {
			requestEnvironmentInterceptor.init(testCase.controller)
		}
	}

	@Override
	public void afterEachStep() {
		requestEnvironmentInterceptor?.destroy()
		transactionInterceptor?.destroy()
	}
	
	public void injectMethods(Binding binding) {

		super.injectMethods(binding)
		
		binding.inject = {beanName ->
			binding."${beanName}" = getAppCxt().getBean(beanName)
		}
		
		binding.transactional = {Boolean transactional ->
			if (testCase) {
				testCase.transactional = transactional
			} else {
				throw new RuntimeException("no test case associated with story/scenario")
			}
		}
		
		binding.controller = {String controller ->
			if (testCase) {
				testCase.metaClass.controller = controller
			} else {
				throw new RuntimeException("no test case associated with story/scenario")
			}
		}
	}
}
