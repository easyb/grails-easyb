package grails.plugin.easyb.test.inject.functional

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grails.plugin.easyb.test.inject.InjectTestRunner

public class InjectSeleniumTestRunner extends InjectTestRunner {
    private static final Logger log = LoggerFactory.getLogger(InjectSeleniumTestRunner)

    protected void beforeBehavior() {
        runnerType = "Grails Selenium RC Test"
    }

    public void injectMethods(Binding binding) {
        super.injectMethods binding

        def seleniumHolderClazz = Thread.currentThread().contextClassLoader.loadClass('grails.plugins.selenium.SeleniumHolder')

        if(seleniumHolderClazz) {
            def selenium = seleniumHolderClazz.selenium
            binding.setVariable "selenium", selenium
        } else {
            println "You don't have selenium-rc plugin installed.\nRun: grails install-plugin selenium-rc"
        }
    }
}