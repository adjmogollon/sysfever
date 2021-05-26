package com.sysfever.app.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sysfever.app.models.entity.Role;
import com.sysfever.app.models.service.IRoleService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/role")
@SessionAttributes("role")
public class RoleController {
	
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IRoleService roleService;

	@RequestMapping(value = { "/", "/list"}, method = RequestMethod.GET)
	public String listDataTable(Model model, Authentication authentication, HttpServletRequest request, Locale locale) {

		logger.info("ingresado al listado de Role DataTable");
		model.addAttribute("titulo", "Listado de roles");
		return "/role/list";

	}

	@GetMapping(value = "/roleAll")
	public @ResponseBody DataTablesOutput<Role> User(@Valid DataTablesInput input) {

		logger.info("estoy buscando" + input.toString());
		return roleService.findAll(input);
	
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteDataTable(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			roleService.deleteById(id);
			flash.addFlashAttribute("success", "Rol eliminado con éxito!");
		}
		return "/role/list";
	}

}
