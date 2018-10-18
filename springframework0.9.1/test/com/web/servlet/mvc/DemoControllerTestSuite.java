package com.web.servlet.mvc;

import javax.servlet.http.HttpServletResponse;

import com.web.servlet.ModelAndView;

import com.web.mock.MockHttpServletResponse;
import junit.framework.TestCase;
import com.web.mock.MockHttpServletRequest;

/**
 *
 * @author Rod Johnson
 * @version $RevisionId$
 */
public class DemoControllerTestSuite extends TestCase {

	Controller testController;
	
	public DemoControllerTestSuite() {
		testController = new DemoController();
	}

	public void testNoName() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest(null, "GET", "test.html");
		HttpServletResponse response = new MockHttpServletResponse();
		ModelAndView mv = this.testController.handleRequest(request, response);
		assertTrue("View is correct", mv.getViewName().equals(DemoController.ENTER_NAME_VIEW));
		assertTrue("no name parameter", request.getParameter("name") == null);
	}
	
	public void testValidName() throws Exception {
		String name = "Tony";
		MockHttpServletRequest request = new MockHttpServletRequest(null, "GET", "test.html");
		request.addParameter("name", name);
		HttpServletResponse response = new MockHttpServletResponse();
		ModelAndView mv = this.testController.handleRequest(request, response);
		assertTrue("View is correct", mv.getViewName().equals(DemoController.VALID_NAME_VIEW));
		assertTrue("name parameter matches", request.getParameter("name").equals(name));
	}
	
	public void testInvalidName() throws Exception {
		String name = "Tony--";
		MockHttpServletRequest request = new MockHttpServletRequest(null, "GET", "test.html");
		request.addParameter("name", name);
		HttpServletResponse response = new MockHttpServletResponse();
		ModelAndView mv = this.testController.handleRequest(request, response);
		assertTrue("View is correct: expected '" + DemoController.INVALID_NAME_VIEW + "' not '" + mv.getViewName() + "'",
			mv.getViewName().equals(DemoController.INVALID_NAME_VIEW));
		assertTrue("name parameter matches", request.getParameter("name").equals(name));
	}
	
}
