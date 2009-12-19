/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

eventAllTestsStart = {
    def easybTestTypeClass = loadTestTypeClass()
    unitTests << easybTestTypeClass.newInstance('easyb', 'unit')
    integrationTests << easybTestTypeClass.newInstance('easyb', 'integration')
    functionalTests << easybTestTypeClass.newInstance('easyb', 'functional')

    // register Injectors
    classLoader.loadClass("grails.plugin.easyb.test.inject.InjectTestRunnerFactory").getMethod("registerExternalFactory", [classLoader.loadClass("grails.plugin.easyb.test.inject.TestRunnerFactory")] as Class[]).invoke(null, [classLoader.loadClass("grails.plugin.easyb.test.inject.unit.InjectUnitTestRunnerFactory").newInstance()] as Object[])
    classLoader.loadClass("grails.plugin.easyb.test.inject.InjectTestRunnerFactory").getMethod("registerExternalFactory", [classLoader.loadClass("grails.plugin.easyb.test.inject.TestRunnerFactory")] as Class[]).invoke(null, [classLoader.loadClass("grails.plugin.easyb.test.inject.integration.InjectIntegrationTestRunnerFactory").newInstance()] as Object[])
}

loadTestTypeClass = { ->
    def doLoad = { -> classLoader.loadClass('grails.plugin.easyb.test.GrailsEasybTestType') }
    try {
        doLoad()
    } catch (ClassNotFoundException e) {
        includeTargets << grailsScript("_GrailsCompile")
        compile()
        doLoad()
    }
}