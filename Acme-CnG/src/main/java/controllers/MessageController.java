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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import form.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Related services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	// ShowMessage
	@RequestMapping(value = "/showMessage", method = RequestMethod.GET)
	public ModelAndView showMessage(@RequestParam(required = true) final int messageId) {
		ModelAndView result;
		Message message;
		String errorMessage;

		try {
			message = this.messageService.findOne(messageId);
			errorMessage = null;
		} catch (Throwable oops) {
			message = null;
			errorMessage = "message.nonAuthorized.error";
		}

		result = new ModelAndView("message/showMessage");
		result.addObject("mes", message);
		result.addObject("message", errorMessage);
		result.addObject("requestURI", "message/showMessage.do");

		return result;
	}

	// SentMessages
	@RequestMapping(value = "/sentMessages", method = RequestMethod.GET)
	public ModelAndView sentMessages(@RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Message> messages = this.messageService.findAllSentByPrincipal();

		result = new ModelAndView("message/sentMessages");
		result.addObject("messages", messages);
		result.addObject("message", message);
		result.addObject("requestURI", "message/sentMessages.do");

		return result;
	}

	// ReceivedMessages
	@RequestMapping(value = "/receivedMessages", method = RequestMethod.GET)
	public ModelAndView receivedMessages(@RequestParam(required = false) final String message) {
		ModelAndView result;
		Collection<Message> messages = this.messageService.findAllReceivedByPrincipal();

		result = new ModelAndView("message/receivedMessages");
		result.addObject("messages", messages);
		result.addObject("message", message);
		result.addObject("requestURI", "message/receivedMessages.do");

		return result;
	}

	// Create
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		ModelAndView result;
		final MessageForm messageForm = this.messageService.create();

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	// Reply
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam(required = true) final int messageId) {
		ModelAndView result;
		Message message;
		MessageForm messageForm;
		String errorMessage;
		Boolean answerable;

		try {
			message = this.messageService.findOne(messageId);
			messageForm = this.messageService.toFormObject(message, true);
			messageForm.setParentMessageId(message.getId());
			errorMessage = null;
			answerable = true;
		} catch (Throwable oops) {
			messageForm = messageService.create();
			errorMessage = "message.nonAuthorizedReply.error";
			answerable = false;
		}

		result = this.createEditModelAndView(messageForm, errorMessage);
		result.addObject("answerable", answerable);

		return result;
	}
	// Forward
	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam(required = true) final int messageId) {
		ModelAndView result;
		Message message;
		MessageForm messageForm;
		String errorMessage;
		Boolean answerable;

		try {
			message = this.messageService.findOne(messageId);
			messageForm = this.messageService.toFormObject(message, false);
			messageForm.setParentMessageId(message.getId());
			errorMessage = null;
			answerable = true;
		} catch (Throwable oops) {
			messageForm = messageService.create();
			errorMessage = "message.nonAuthorizedForward.error";
			answerable = false;
		}

		result = this.createEditModelAndView(messageForm, errorMessage);
		result.addObject("answerable", answerable);

		return result;
	}

	// Save
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageForm messageForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Message message;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(messageForm);
		} else {
			try {
				message = messageService.reconstruct(messageForm);
				messageService.save(message);
				result = new ModelAndView("redirect:/message/sentMessages.do");
			} catch (IllegalArgumentException e) {
				result = this.createEditModelAndView(messageForm, "message.attachments.error");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.send.error");
			}
		}

		return result;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int messageId, @RequestParam(required = true) final String url) {
		ModelAndView result = new ModelAndView();
		String errorMessage = null;

		try {
			this.messageService.delete(messageId);
		} catch (final Throwable oops) {
			errorMessage = "message.delete.error";
		}

		result = new ModelAndView("redirect:/" + url);
		result.addObject("message", errorMessage);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		final ModelAndView result = this.createEditModelAndView(messageForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/send");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);
		result.addObject("requestURI", "message/send.do");

		return result;
	}

}
