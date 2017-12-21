package tool.core.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {
	
	@RequestMapping("/")
	public ModelAndView login() {
		return new ModelAndView("html/index.html");
	}
}
