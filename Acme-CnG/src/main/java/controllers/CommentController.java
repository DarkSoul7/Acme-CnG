
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

import domain.Actor;
import domain.Comment;
import form.CommentForm;
import services.ActorService;
import services.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public CommentController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Comment> comments = this.commentService.findAll();

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("RequestURI", "comment/list.do");

		return result;
	}

	@RequestMapping(value = "/createCommentActor", method = RequestMethod.GET)
	public ModelAndView createCommentActor(@RequestParam final int actorId) {
		ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();

		final Comment comment = this.commentService.create(actorId, actor.getUserAccount().getAuthorities().iterator().next().getAuthority());

		final CommentForm commentForm = new CommentForm();
		commentForm.setCommentableId(comment.getCommentableId());
		commentForm.setCommentableType(comment.getCommentableType());

		result = this.createEditModelAndView(commentForm);

		return result;
	}

	@RequestMapping(value = "/createCommentActor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCommentActor(@Valid final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Comment comment;

		comment = this.commentService.reconstruct(commentForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/createCommentOffer", method = RequestMethod.GET)
	public ModelAndView createCommentOffer(@RequestParam final int offerId) {
		ModelAndView result;

		final Comment comment = this.commentService.create(offerId, "Offer");

		final CommentForm commentForm = new CommentForm();
		commentForm.setCommentableId(comment.getCommentableId());
		commentForm.setCommentableType(comment.getCommentableType());

		result = this.createEditModelAndView(commentForm);

		return result;
	}
	@RequestMapping(value = "/createCommentOffer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCommentOffer(@Valid final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Comment comment;

		comment = this.commentService.reconstruct(commentForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/createCommentRequest", method = RequestMethod.GET)
	public ModelAndView createCommentRequest(@RequestParam final int requestId) {
		ModelAndView result;

		final Comment comment = this.commentService.create(requestId, "Request");

		final CommentForm commentForm = new CommentForm();
		commentForm.setCommentableId(comment.getCommentableId());
		commentForm.setCommentableType(comment.getCommentableType());

		result = this.createEditModelAndView(commentForm);

		return result;
	}
	@RequestMapping(value = "/createCommentRequest", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCommentRequest(@Valid final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Comment comment;

		comment = this.commentService.reconstruct(commentForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final CommentForm commentForm) {
		final ModelAndView result = this.createEditModelAndView(commentForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CommentForm commentForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/register");
		result.addObject("commentForm", commentForm);
		result.addObject("message", message);

		return result;
	}
}
