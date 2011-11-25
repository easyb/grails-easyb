package grails.plugin.easyb.test.inject.integration;

import static org.junit.Assert.*
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin;
import grails.test.mixin.web.ControllerUnitTestMixin;

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass

class JUnit4TestCase {

	static transactional = true
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
