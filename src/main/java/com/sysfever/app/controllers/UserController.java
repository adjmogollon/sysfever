package com.sysfever.app.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

import com.sysfever.app.models.entity.User;
import com.sysfever.app.models.entity.Role;
import com.sysfever.app.models.service.IRoleService;
import com.sysfever.app.models.service.IUserService;
import com.sysfever.app.util.paginator.PageRender;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request, Locale locale, String keyword) {

		logger.info("ingresado al listado de usuarios");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Pageable pageRequest = PageRequest.of(page, 10);
		Page<User> users = null;

		if (keyword != null) {
			users = userService.findByUsernameContaining(keyword, pageRequest);

		} else {
			users = userService.findAll(pageRequest);
		}

		PageRender<User> pageRender = new PageRender<User>("/user/list", users);

		model.addAttribute("titulo", messageSource.getMessage("text.user.list.title", null, locale));
		model.addAttribute("users", users);
		model.addAttribute("page", pageRender);
		return "/user/list";

	}

	@RequestMapping(value = "/form")
	public String create(Model model) {
		List<Role> roles = roleService.findAll();
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		model.addAttribute("titulo", "Guardar Usuario");
		return "user/form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		User user = null;
		List<Role> roles = roleService.findAll();

		if (id > 0) {
			user = userService.findById(id);
			if (user == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
				return "redirect:/user/list";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/user/list";
		}
		model.put("user", user);
		model.put("roles", roles);
		model.put("titulo", "Guardar Usuario");
		return "user/form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid User user, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {
		List<Role> roles = roleService.findAll();
		if (result.hasErrors()) {
			model.addAttribute("roles", roles);
			model.addAttribute("user", user);
			model.addAttribute("titulo", "Guardar Usuario");
			return "user/form";
		}
		String typeFlash = null;
		String mensajeFlash = null;
		if (user.getId() == null) {
			if (userService.findByUsername(user.getUsername()) != null) {
				ObjectError errorUserExist = new ObjectError("userExist", "Usuario ya existe");
				result.addError(errorUserExist);
				model.addAttribute("titulo", "Guardar Usuario");
				model.addAttribute("roles", roles);
				model.addAttribute("user", user);
				return "user/form";
			} else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				mensajeFlash = "Usuario creado con exito";
			}
		} else {
			mensajeFlash = "Usuario editado con exito";
		}
		typeFlash = "success";

		userService.save(user);
		status.setComplete();

		flash.addFlashAttribute(typeFlash, mensajeFlash);

		return "redirect:/user/list";
	}

	@PostMapping(value = "/changePassword")
	public String changePassword(@Valid User user, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Guardar Usuario");
			return "user/form";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		status.setComplete();
		flash.addFlashAttribute("success", "Password Actualizado");

		return "redirect:/user/list";

	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			userService.deleteById(id);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");
		}
		return "redirect:/user/list";
	}

	/*
	 * METODOS PARA EL DATATABLE
	 */

	@RequestMapping(value = { "/listDataTable" }, method = RequestMethod.GET)
	public String listDataTable(Model model, Authentication authentication, HttpServletRequest request, Locale locale) {

		logger.info("ingresado al listado de usuarios DataTable");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		model.addAttribute("titulo", messageSource.getMessage("text.user.list.title", null, locale));
		return "/user/listDataTable";

	}

	@GetMapping(value = "/usuariosRest")
	public @ResponseBody DataTablesOutput<User> User(@Valid DataTablesInput input) {

		logger.info("estoy buscando" + input.toString());
		return userService.findAll(input);
	}

	@RequestMapping(value = "/deleteDataTable/{id}")
	public String deleteDataTable(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if (id > 0) {
			userService.deleteById(id);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");

		}
		return "/user/listDataTable";
	}

}
