/*
 * Created by IntelliJ IDEA.
 * User: richard
 * Date: Jun 9, 2009
 * Time: 11:16:30 PM
 */
package grails.plugin.easyb.test.inject.unit

import grails.plugin.easyb.test.inject.unit.InjectGrailsTestRunner

/**
 * only deals with setUp and tearDown
 */

public class InjectMvcTestRunner extends InjectGrailsTestRunner {
    protected String fullPathClassName
    protected Class testClass // class under test

    public InjectMvcTestRunner(String fullPathClassName) {
        this.fullPathClassName = fullPathClassName
    }

    protected Class getMvcClass(String suffix) {
//    println "suffix is ${suffix} fullpath is ${fullPathClassName}"
//    def m = fullPathClassName =~ /^([\w\.]*?[A-Z]\w*?${suffix})\w+/
//
//    if (m) {
//
        this.testClass = Thread.currentThread().contextClassLoader.loadClass(fullPathClassName)
//      this.testClass = Thread.currentThread ().contextClassLoader.loadClass (m[0][1])
//    }
//    else {
//      throw new RuntimeException ("Cannot find matching class for this test.")
//    }
    }

    protected void beforeBehavior() {
//    println "init: mvc test case ${fullPathClassName}"
        throw new RuntimeException("Can't use MvcClass directly")
    }

}