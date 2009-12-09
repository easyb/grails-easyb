/*
 * User: richard
 * Date: Jun 9, 2009
 * Time: 11:09:02 PM
 */
package org.codehaus.groovy.grails.test.plugin.easyb.unit

import grails.test.GroovyPagesTestCase
import org.codehaus.groovy.grails.test.plugin.easyb.InjectTestRunner;
import org.slf4j.LoggerFactory;

public class InjectGSPTestRunner extends InjectTestRunner {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(InjectGSPTestRunner)

  protected void initialize() {
  	log.debug "initializing grails controller test"
  	runnerType = "Grails GSP Runner"
    try {
      this.testCase = new GroovyPagesTestCase()
    } catch ( Exception ex ) {
      log.error( "failed to initialize test case, GSP does not exist", ex );
    }
  }

  public void injectMethods(Binding binding) {
    super.injectMethods( binding ) // there aren't any

    binding.setControllerName = { name ->
      if ( testCase )
        testCase.setControllerName = name
      else
        throw new RuntimeException( "no test case associated with story/scenario" )
    }

    binding.applyTemplate = { template, params = [:] ->
      if ( testCase )
        return testCase.applyTemplate( template, params )
      else
        throw new RuntimeException( "no test case associated with story/scenario" )
    }
  }
}