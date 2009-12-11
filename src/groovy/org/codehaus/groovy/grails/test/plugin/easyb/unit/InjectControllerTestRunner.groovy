/*
 * User: richard
 * Date: Jun 9, 2009
 * Time: 11:06:37 PM
 */
package org.codehaus.groovy.grails.test.plugin.easyb.unit

import grails.test.ControllerUnitTestCase
import org.codehaus.groovy.grails.test.plugin.easyb.unit.InjectMvcTestRunner
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class InjectControllerTestRunner extends InjectMvcTestRunner {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(InjectControllerTestRunner)

    public InjectControllerTestRunner(String fullPathClassName) {
        super(fullPathClassName)
    }

    protected void initialize() {
        log.debug "initializing grails controller test"
        runnerType = "Grails Controller Test for ${fullPathClassName}"
        try {
            this.testCase = new ControllerUnitTestCase(getMvcClass("Controller"))
        } catch (Exception ex) {
            log.error("failed to initialize test case, controller does not exist", ex);
        }
    }

    public void injectMethods(Binding binding) {
        super.injectMethods(binding)

        log.debug "now injecting controller methods"

        binding.mockCommandObject = {Class clazz ->
            if (testCase)
                testCase.mockCommandObject(clazz)
            else
                throw new RuntimeException("no test case associated with story/scenario")
        }

        // binding only allows 1 variable
        binding.setXmlRequestContent = {p1, p2 = null ->
            if (testCase) {
                if (p1 instanceof Closure)
                testCase.setXmlRequestContent(p1)
                else if (p1 instanceof String && p2 instanceof Closure)
                testCase.setXmlRequestContent((String) p1, (Closure) p2)
                else if (p1 instanceof String && !p2)
                    testCase.setXmlRequestContent((String) p1)
            }
            else
                throw new RuntimeException("no test case associated with story/scenario")
        }
    }

    public void setUp() {
        super.setUp()

        if (testCase) {
            log.debug "setup complete, have controller? ${testCase.controller} (binding to scenario)"
            binding.setVariable("controller", testCase.controller)
        }
    }


    public void tearDown() {
        super.tearDown()

        assert binding != null

        if (testCase && binding)
        binding.setVariable("controller", null)
    }

}