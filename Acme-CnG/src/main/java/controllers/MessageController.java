/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {
	
	// Related services
	
	@Autowired
	private MessageService	messageService;
	
	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}
	
	// SentMessages
	@RequestMapping(value = "/sentMessages", method = RequestMethod.GET)
	public ModelAndView sentMessages() {
		ModelAndView result;
		Collection<Message> messages = messageService.findAllSentByPrincipal();
		
		result = new ModelAndView("message/sentMessages");
		result.addObject("messages", messages);

		return result;
	}
	
	// ReceivedMessages
	@RequestMapping(value = "/receivedMessages", method = RequestMethod.GET)
	public ModelAndView receivedMessages() {
		ModelAndView result;
		Collection<Message> messages = messageService.findAllReceivedByPrincipal();
		
		result = new ModelAndView("message/receivedMessages");
		result.addObject("messages", messages);

		return result;
	}
	
	// Create
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		ModelAndView result;
		Message message = new Message();
		result = createEditModelAndView(message);

		return result;
	}
	
	// Save
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Message message, BindingResult binding) {
		ModelAndView result = new ModelAndView();

		if (binding.hasErrors()) {
			result = createEditModelAndView(message);
		} else {
			try {
				messageService.save(message);
				result = new ModelAndView("redirect:/message/sentMessages.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(message, "message.send.error");
			}
		}

		return result;
	}
	
	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(int messageId, String url) {
		ModelAndView result = new ModelAndView();
		String errorMessage = null;

		try {
			messageService.delete(messageId);
		} catch (Throwable oops) {
			errorMessage = "message.delete.error";
		}
		
		result = new ModelAndView("redirect:/" + url + ".do");
		result.addObject("message", errorMessage);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(Message mes) {
		ModelAndView result = createEditModelAndView(mes, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Message mes, String message) {
		ModelAndView result;

		result = new ModelAndView("message/send");
		result.addObject("mes", mes);
		result.addObject("message", message);

		return result;
	}

}
