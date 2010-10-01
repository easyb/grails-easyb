class EasybGrailsPlugin {
    // Plugin version revved a major version as Grails 1.2 is not compatible with 1.1 for testing infrastructure, classes have gone AWOL
    // so it won't even compile any longer
    def version = "2.0.5"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        'grails-app/**',
        'test/**',
        'web-app/**'         
    ]
    def scopes = [excludes:'war']

    // inject code by Jeffrey Erikson
    def author = "Richard Vowles, Gustavo Madruga, Jeffrey Erikson"
    def authorEmail = "richard@bluetrainsoftware.com, omadruga@gmail.com"
    def title = "Allows Grails applications to write unit, integration and functional tests. Integrates Grails unit testing features"
    def description = '''\\
Groovy testing implementation of easyb - exposes all of the easyb functionality and an infrastructure to expose more testing functionality.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/easyb"

    def doWithSpring = { }
    def doWithApplicationContext = { applicationContext -> }
    def doWithWebDescriptor = { xml -> }
    def doWithDynamicMethods = { ctx -> }
    def onChange = { event -> }
    def onConfigChange = { event -> }
}
