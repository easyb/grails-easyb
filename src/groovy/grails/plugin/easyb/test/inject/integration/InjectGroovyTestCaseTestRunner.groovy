package grails.plugin.easyb.test.inject.integration
import junit.framework.TestCase;
import grails.plugin.easyb.test.GrailsEasybTestType
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
		createTestCaseAndAddProperties()
		if(controllerClassNameHasBeenPreset()) testCase.controller = controllerClassName
	}
	
	private void createTestCaseAndAddProperties() {
		testCase = new Object()
		
		//Add some properties to the testCase
		testCase.metaClass {
			transactional = true
			controller = null
		}
	}
	
	private boolean controllerClassNameHasBeenPreset() {
		return isNotBlank(controllerClassName)
	}
	
	@Override
	public void beforeEachStep() {
		super.beforeEachStep()
		requestEnvironmentInterceptor = new GrailsTestRequestEnvironmentInterceptor(getAppCxt())
		if(isTransactional()) {
			transactionInterceptor = new GrailsTestTransactionInterceptor(getAppCxt())
			startTransaction()
		} 
		setupControllerMockRequestEnvironment()
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
		rollbackTransaction()
		removeControllerMockRequestEnvironment()
	}
	
	@Override
	public void afterBehavior() {
		testCase.transactional = true
		testCase.controller = null
	}
	
	private void rollbackTransaction() {
		transactionInterceptor?.destroy()
		transactionInterceptor = null
	}

	private void removeControllerMockRequestEnvironment() {
		requestEnvironmentInterceptor?.destroy()
		requestEnvironmentInterceptor = null
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
				testCase.controller = controller
			} else {
				throw new RuntimeException("no test case associated with story/scenario")
			}
		}
	}
}
