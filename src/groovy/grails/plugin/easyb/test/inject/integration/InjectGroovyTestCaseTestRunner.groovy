package grails.plugin.easyb.test.inject.integration
import grails.plugin.easyb.test.GrailsEasybTestType;
import grails.plugin.easyb.test.inject.InjectTestRunner
import groovy.lang.Binding

import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.test.support.GrailsTestRequestEnvironmentInterceptor
import org.codehaus.groovy.grails.test.support.GrailsTestTransactionInterceptor
import org.springframework.context.ApplicationContext

@Mixin(StringUtils)
class InjectGroovyTestCaseTestRunner extends InjectTestRunner {
	GrailsTestTransactionInterceptor transactionInterceptor
	GrailsTestRequestEnvironmentInterceptor requestEnvironmentInterceptor
	String controllerClassName
	
	protected void beforeBehavior() {
		runnerType = "Groovy Test Case"
		this.testCase = new JUnit4TestCase()
		if(controllerClassNameHasBeenPreset()) addControllerPropertyToTestCase()
		transactionInterceptor = new GrailsTestTransactionInterceptor(getAppCxt())
		requestEnvironmentInterceptor = new GrailsTestRequestEnvironmentInterceptor(getAppCxt())
	}
	
	private boolean controllerClassNameHasBeenPreset() {
		return isNotBlank(controllerClassName)
	}
	
	private void addControllerPropertyToTestCase() {
		testCase.metaClass.controller = controllerClassName
	}
	
	@Override
	public void beforeEachStep() {
		super.beforeEachStep()
		
		if(isTransactional()) startTransaction()
		if(isControllerSetup()) setupControllerMockRequestEnvironment()
	}

	private boolean isTransactional() {
		return testCase.transactional
	}
	
	private void startTransaction() {
		transactionInterceptor.init()
	}
	
	private boolean isControllerSetup() {
		return (testCase.hasProperty('controller') && testCase.controller)
	}
	
	private void setupControllerMockRequestEnvironment() {
		requestEnvironmentInterceptor.init(testCase.controller)
	}
	
	@Override
	public void afterEachStep() {
		if(isTransactional()) rollbackTransaction()
		if(isControllerSetup()) removeControllerMockRequestEnvironment()
	}
	
	private void rollbackTransaction() {
		transactionInterceptor?.destroy()
	}

	private void removeControllerMockRequestEnvironment() {
		requestEnvironmentInterceptor?.destroy()
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
