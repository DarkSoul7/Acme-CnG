
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import domain.Banner;

@Controller
@RequestMapping(value = "/banner")
public class BannerController extends AbstractController {

	//Requested services

	@Autowired
	private BannerService	bannerService;


	//COnstructor

	public BannerController() {
		super();
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Banner> banners = this.bannerService.findAll();

		result = new ModelAndView("banner/list");
		result.addObject("banners", banners);
		result.addObject("RequestURI", "banner/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final String editError, @RequestParam(required = false) final String deleteError) {
		ModelAndView result;

		final Banner banner = this.bannerService.create();

		result = this.createEditModelAndView(banner);
		result.addObject("editError", editError);
		result.addObject("deleteError", deleteError);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Banner banner, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(banner);
		else
			try {

				this.bannerService.save(banner);
				result = new ModelAndView("redirect:/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(banner, "banner.save.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int bannerId) {
		ModelAndView result;

		try {
			final Banner banner = this.bannerService.findOne(bannerId);
			Assert.notNull(banner);
			result = this.createEditModelAndView(banner);
			result = new ModelAndView("redirect:/banner/list.do");

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/banner/list.do");
			result.addObject("editError", "banner.edit.error");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int bannerId) {
		ModelAndView result;

		try {
			final Banner banner = this.bannerService.findOne(bannerId);
			Assert.notNull(banner);
			this.bannerService.delete(banner);
			result = new ModelAndView("redirect:/banner/list.do");

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/banner/list.do");
			result.addObject("deleteError", "banner.delete.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Banner banner) {
		return this.createEditModelAndView(banner, null);
	}

	protected ModelAndView createEditModelAndView(final Banner banner, final String message) {
		ModelAndView result;

		result = new ModelAndView("banner/create");
		result.addObject("banner", banner);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "banner/save.do");

		return result;
	}
}
