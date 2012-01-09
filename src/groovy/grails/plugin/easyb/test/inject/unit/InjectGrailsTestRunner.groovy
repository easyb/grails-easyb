package grails.plugin.easyb.test.inject.unit
/*
 * User: richard
 * Date: Jun 7, 2009
 * Time: 9:51:13 PM
 */

import grails.plugin.easyb.test.inject.InjectTestRunner
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import grails.test.mixin.web.FiltersUnitTestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import grails.test.mixin.web.UrlMappingsUnitTestMixin
import grails.test.mixin.webflow.WebFlowUnitTestMixin

import org.apache.commons.lang.ClassUtils
import org.apache.commons.lang.StringUtils

public class InjectGrailsTestRunner extends InjectTestRunner {
	
	private List mixins = [ControllerUnitTestMixin, DomainClassUnitTestMixin, GroovyPageUnitTestMixin, UrlMappingsUnitTestMixin,
					GrailsUnitTestMixin, FiltersUnitTestMixin, ServiceUnitTestMixin]
	private Set controllerUnitVariables = ['request', 'response', 'webRequest',
											'params', 'session', 'model', 'flash', 'views'] as SortedSet
    protected void beforeBehavior() {
        runnerType = "Grails Unit Test"
		testCase = new Object()
        addGrailsTestMixins()
        runJUnitAnnotatedMethods(org.junit.BeforeClass)
    }
	private void addGrailsTestMixins() {
		mixins.each {mixin ->
			testCase.metaClass.mixin mixin
		}
    }
	
	protected void afterBehavior() {
		runJUnitAnnotatedMethods(org.junit.AfterClass)
	}
	
	@Override
	public void beforeEachStep() {
		runJUnitAnnotatedMethods(org.junit.Before)
	}
	
	@Override
	public void afterEachStep() {
		runJUnitAnnotatedMethods(org.junit.After)
		
		if (testCase && binding) {
			binding.setVariable("controller", null)
			unbindControllerUnitVariables(binding)
		}
	}
	
	private runJUnitAnnotatedMethods(def jUnitAnnotation) {
		mixins.each{mixin ->
			mixin.getDeclaredMethods().each{method ->
				if(method.getAnnotation(jUnitAnnotation)){
					testCase."${method.getName()}"()
				}
			}
		}
	}
	
    public void injectMethods(Binding binding) {
        super.injectMethods(binding)
		
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

		domainBindings(binding)
		controllerBindings(binding)
		serviceBindings(binding)
		urlmappingsBindings(binding)
		tagLibBindings(binding)
		filtersBindings(binding)
		supportBindings(binding)
    }
	
	private domainBindings(def binding) {
		binding.mockDomain = {Class domainClass, List instances = [] ->
			if (testCase) {
				testCase.mockDomain(domainClass, instances)
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
	}
	
	private controllerBindings(def binding) {
		binding.mockController = {Class clazz ->
			def mockController = testCase.mockController(clazz)
			binding.setVariable("controller", mockController)
			bindControllerUnitVariables(binding)

			mockController
		}
		
		binding.mockCommandObject = {Class clazz ->
			testCase.mockCommandObject(clazz)
		}
	}
	
	private serviceBindings(def binding) {
		binding.mockService = {Class clazz ->
			testCase.mockService(clazz)
		}	
	}
	
	private urlmappingsBindings(def binding) {
		binding.mockUrlMappings = {Class urlMappingClass ->
			testCase.mockUrlMappings(urlMappingClass)
		}
		
		binding.mapURI = {String uri ->
			testCase.mapURI(uri)
		}
	}
	
	private tagLibBindings(def binding) {
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
		
		binding.render = {Map args ->
			testCase.render(args)
		}
	}
	
	
	private filtersBindings(def binding) {
		binding.mockFilters = {Class clazz ->
			testCase.mockFilters(clazz)
		}
		
		binding.withFilters = {Map arguments, Closure callable ->
			testCase.withFilters(arguments, callable)
		}
	}

	private supportBindings(def binding) {
		binding.mockFor = {Class clazz, boolean loose = false ->
			if (testCase) {
				return testCase.mockFor(clazz, loose)
			} else {
				throw new RuntimeException("no test case associated with story/scenario")
			}
		}
		
		binding.mockCodec = {Class clazz ->
			testCase.mockCodec(clazz)
		}
	}
		
	private bindControllerUnitVariables(Binding binding) {
		controllerUnitVariables.each{variable ->
				binding.setVariable(variable, testCase."$variable")
		}
	}
	
	private unbindControllerUnitVariables(Binding binding) {
		controllerUnitVariables.each{variable ->
			binding.setVariable(variable, null)
		}
	}
}