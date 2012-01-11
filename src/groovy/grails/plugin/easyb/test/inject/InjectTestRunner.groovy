package grails.plugin.easyb.test.inject

import grails.plugin.easyb.test.GrailsEasybTestType

import org.easyb.domain.Behavior

/*
 * Created by IntelliJ IDEA.
 * User: richard
 * Date: Jun 9, 2009
 * Time: 11:34:07 PM
 */
public class InjectTestRunner {
    protected def testCase
    protected Binding binding
	protected Behavior behavior
	GrailsEasybTestType grailsEasybTestType
    String runnerType = "Uninitialized"

    protected void beforeBehavior() {
    }

	protected void afterBehavior() {
	}
	
    public void injectMethods(Binding binding) {
        this.binding = binding
        beforeBehavior()
    }


    public boolean isConfigured() {
        return this.testCase != null
    }


    public void beforeEachStep() {
    }

    public void afterEachStep() {
    }
	
	protected getAppCxt() {
		grailsEasybTestType.getApplicationContext()
	}
	
}