/*
 * User: richard
 * Date: Jun 9, 2009
 * Time: 11:08:46 PM
 */
package grails.plugin.easyb.test.inject.unit

import grails.test.TagLibUnitTestCase
import grails.plugin.easyb.test.inject.unit.InjectMvcTestRunner
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * doesn't actually do anything over and above the Mvc class other than load the TagLib
 */

public class InjectTaglibTestRunner extends InjectMvcTestRunner {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(InjectTaglibTestRunner)

    public InjectTaglibTestRunner(String fullPathClassName) {
        super(fullPathClassName)
    }

    protected void beforeBehavior() {
        log.debug "init: taglib test runner"
        runnerType = "Taglib for ${fullPathClassName} "
        try {
            this.testCase = new TagLibUnitTestCase(getMvcClass("TagLib"))
        } catch (Exception ex) {
            log.error("failed to initialize test case, taglib does not exist", ex);
        }
    }

    public void injectMethods(Binding binding) {
        super.injectMethods(binding)
    }

    public void beforeEachStep() {
        super.beforeEachStep()

        if (testCase)
        binding.tagLib = testCase.tagLib
    }


    public void afterEachStep() {
        super.afterEachStep()

        if (testCase)
        binding.tagLib = null
    }
}