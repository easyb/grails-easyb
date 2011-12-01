package grails.plugin.easyb.test.listener

import org.easyb.listener.ResultsCollector
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.io.SystemOutAndErrSwapper
import org.easyb.result.Result
import org.easyb.domain.Behavior
import grails.plugin.easyb.test.report.EasybReportsFactory
import org.easyb.BehaviorStep
import org.easyb.util.BehaviorStepType
import grails.plugin.easyb.test.GrailsEasybTestType
import grails.plugin.easyb.test.inject.InjectTestRunner
import grails.plugin.easyb.test.inject.InjectTestRunnerFactory
import org.slf4j.LoggerFactory
import org.slf4j.Logger

public class GrailsEasybListener extends ResultsCollector {
    private static final Logger log = LoggerFactory.getLogger(GrailsEasybListener)

    final protected GrailsTestEventPublisher eventPublisher
    final protected EasybReportsFactory reportsFactory
    final protected SystemOutAndErrSwapper outAndErrSwapper
    protected GrailsEasybTestType grailsEasybTestType

    protected Behavior currentBehaviour
    protected Stack<BehaviorStep> steps = new Stack<BehaviorStep>();
    protected BehaviorStep currentStep
    protected InjectTestRunner testRunner

    GrailsEasybListener(GrailsTestEventPublisher eventPublisher, EasybReportsFactory reportsFactory, SystemOutAndErrSwapper outAndErrSwapper, GrailsEasybTestType grailsEasybTestType) {
        super()
        this.eventPublisher = eventPublisher
        this.reportsFactory = reportsFactory
        this.outAndErrSwapper = outAndErrSwapper
        this.grailsEasybTestType = grailsEasybTestType
    }

    /**
     * Called when a Story or Specification file before being executed (comparable as a TestCase).
     * Publishes the event testCaseStart.
     */
    @Override
    public void startBehavior(Behavior behavior) {
        super.startBehavior(behavior)

        eventPublisher.testCaseStart(trucateEventName(behavior.phrase))

        currentBehaviour = behavior
		
        testRunner = InjectTestRunnerFactory.findMatchingRunner(currentBehaviour, grailsEasybTestType)
        if(! testRunner) {
            testRunner = InjectTestRunnerFactory.getDefault(currentBehaviour, grailsEasybTestType)
        }

        if(testRunner) {
            testRunner.beforeBehavior()

            if (testRunner.testCase == null) {
                log.warn "Unable to create expected test runner ${testRunner.runnerType}, using default instead"
                testRunner = InjectTestRunnerFactory.getDefault(currentBehaviour, grailsEasybTestType)
                testRunner?.beforeBehavior()
            }
        }
    }

    /**
     * Called when a Story or Specification file after is executed.
     * Publishes the event testCaseEnd.
     */
    @Override
    public void stopBehavior(BehaviorStep behaviorStep, Behavior behavior) {
        super.stopBehavior(behaviorStep, behavior)
		testRunner.afterBehavior()
        eventPublisher.testCaseEnd(trucateEventName(behavior.phrase))

        currentBehaviour = null
        testRunner = null
    }

    /**
     * Called when a Step is about to be executed. A Step is a closure in a easyb file.
     * Publishes the event testStart.
     */
    @Override
    public synchronized void startStep(BehaviorStep behaviorStep) {
        super.startStep(behaviorStep)

        eventPublisher.testStart(trucateEventName(behaviorStep.name))

        currentStep = behaviorStep
        steps.push(behaviorStep)

        switch (behaviorStep.getStepType()) {
            case BehaviorStepType.EXECUTE:
                currentBehaviour.binding.grailsTest = { String name, style = null ->
                    dynamicallyInjectGrailsTest(style, name);
                }

                testRunner?.injectMethods(currentBehaviour.binding)
                break
            case BehaviorStepType.IT:
            case BehaviorStepType.SCENARIO:
                testRunner?.beforeEachStep()
                break
        }
    }

    @Override
    public void stopStep() {
        super.stopStep()

        BehaviorStep step = steps.pop()

        switch(step.getStepType()) {
            case BehaviorStepType.IT:
            case BehaviorStepType.SCENARIO:
                testRunner?.afterEachStep()
                break
        }
    }

    /**
     * Called when a Step has been executed and has a result.
     * Publishes the event testEnd, and also the event testFailure if the result is marked as failed.
     */
    @Override
    public void gotResult(Result result) {
        super.gotResult(result)

        if(result.failed()) {
            if(result.cause) {
                eventPublisher.testFailure(trucateEventName(currentStep.name), result.cause, true)
            } else {
                eventPublisher.testFailure(trucateEventName(currentStep.name), (String)null, true)
            }
        }

        eventPublisher.testEnd(trucateEventName(currentStep.name))
    }

    /**
     * Called when all easyb files have been executed.
     * Generates the easyb reports.
     */
    @Override
    public void completeTesting() {
        super.completeTesting()

        reportsFactory.produceReports(this)
    }

    /**
     * Truncate the event name to a maximum, because sometimes easyb story descriptions are too long, and make the
     * command line very difficult to read.
     * @param name the name o the event
     * @return a truncated event name if the event lenght is longer than the max allowed (70 chars default)
     */
    private String trucateEventName(String name) {
        int delimitator = 70 //TODO put this dellimitator in a config ?                   
        if(name.length() <= delimitator) {
            return name
        } else {
            return name.substring(0, delimitator)
        }
    }

    /**
    * we can have situations where we want to replace the test case attached. This happens in the middle of a scenario or specification
    * so we need to make sure we tell the new test runner about the binding
    */
    private void dynamicallyInjectGrailsTest(String name, String style) {
        testRunner = InjectTestRunnerFactory.findDynamicRunner(style, name, currentBehaviour, grailsEasybTestType)

        if(testRunner) {
            testRunner.beforeBehavior()
            testRunner.injectMethods(currentBehaviour.binding)

            // we have missed the start of the scenario or specification as well, so we need to inject the setup
            if(currentStep.stepType == BehaviorStepType.IT || currentStep.stepType == BehaviorStepType.SCENARIO) {
                testRunner.beforeEachStep()
            }
        }
    }
}