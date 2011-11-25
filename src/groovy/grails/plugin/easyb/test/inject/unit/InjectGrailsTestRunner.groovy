package grails.plugin.easyb.test.inject.unit
/*
 * User: richard
 * Date: Jun 7, 2009
 * Time: 9:51:13 PM
 */

import grails.plugin.easyb.test.inject.InjectTestRunner
import grails.plugin.easyb.test.inject.integration.JUnit4TestCase
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin;
import grails.test.mixin.web.GroovyPageUnitTestMixin;

public class InjectGrailsTestRunner extends InjectTestRunner {

	private List mixins = [ControllerUnitTestMixin, DomainClassUnitTestMixin,
		GroovyPageUnitTestMixin]
	
    protected void beforeBehavior() {
        runnerType = "Grails Unit Test"
        try {
			testCase = new JUnit4TestCase()
			addGrailsTestMixins()
			initGrailsTestMixins()
        } catch (Exception ex) {
            log.error("failed to initialize test case, controller does not exist", ex);
        }
    }

	private void addGrailsTestMixins() {
		mixins.each{mixin ->
			testCase.getClass().mixin(mixin)
		}
    }
	
	private void initGrailsTestMixins() {
		testCase.initGrailsApplication()
		testCase.initializeDatastoreImplementation()
		testCase.configureGrailsWeb()
	}
	
	protected void afterBehavior() {
		this.testCase.cleanupGrailsWeb()
		testCase.shutdownApplicationContext()
	}
	
	@Override
	public void beforeEachStep() {
		testCase.connectDatastore()
		testCase.bindGrailsWebRequest()
	}
	
	@Override
	public void afterEachStep() {
		testCase.shutdownDatastoreImplementation()
		testCase.resetGrailsApplication()
		
		if (testCase && binding) {
			binding.setVariable("controller", null)
		}
	}
	
    public void injectMethods(Binding binding) {
        super.injectMethods(binding)
		
		binding.mockController = {Class clazz ->
			def mockController = testCase.mockController(clazz)
			binding.setVariable("controller", mockController)
			mockController
        }
		
        binding.inject = {beanName ->
			binding."${beanName}" = getAppCxt().getBean(beanName)
		}

        binding.registerMetaClass = {Class clazz ->
            if (testCase) {
                testCase.registerMetaClass clazz
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.mockFor = {Class clazz, boolean loose = false ->
            if (testCase) {
                return testCase.mockFor(clazz, loose)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.mockForConstraintsTests = {Class clazz, instance = [] ->
            if (testCase) {
                if(instance instanceof List) {
                    testCase.mockForConstraintsTests(clazz, [instance])
                } else {
                    testCase.mockForConstraintsTests(clazz, instance)
                }
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.mockDomain = {Class domainClass, List instances = [] ->
            if (testCase) {
				testCase.mockDomain(domainClass, instances)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.enableCascadingValidation = {->
            if (testCase) {
                testCase.enableCascadingValidation()
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.mockTagLib = {Class tagLibClass ->
            if (testCase) {
                testCase.mockTagLib(tagLibClass)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

		binding.applyTemplate = {String contents, Map model = [:] -> 
			testCase.applyTemplate(contents, model)
		}
		
        binding.mockLogging = {Class clazz, boolean enableDebug = false ->
            if (testCase) {
                testCase.mockLogging(clazz, enableDebug)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.mockConfig = {String config ->
            if (testCase) {
                testCase.mockConfig(config)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.loadCodec = {Class codecClass ->
            if (testCase) {
                testCase.loadCodec(codecClass)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }

        binding.addConverters = {Class clazz ->
            if (testCase) {
                testCase.addConverters(clazz)
            } else {
                throw new RuntimeException("no test case associated with story/scenario")
            }
        }
    }
}