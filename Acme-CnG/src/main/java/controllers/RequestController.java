package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Offer;
import domain.Request;
import form.RequestForm;
import services.RequestService;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController{

	@Autowired
	private RequestService requestService;

	// Constructors -----------------------------------------------------------

	public RequestController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests = requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("RequestURI", "request/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		RequestForm requestForm = requestService.create();
		result = createEditModelAndView(requestForm);

		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RequestForm requestForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Request request;

		request = requestService.reconstruct(requestForm, binding);
		if (binding.hasErrors()) {
			result = createEditModelAndView(requestForm);

		} else {
			try {
				requestService.save(request);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(requestForm, "request.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(RequestForm requestForm) {
		ModelAndView result = createEditModelAndView(requestForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(RequestForm requestForm, String message) {
		ModelAndView result;

		result = new ModelAndView("request/register");
		result.addObject("requestForm", requestForm);
		result.addObject("message", message);

		return result;
	}
}
