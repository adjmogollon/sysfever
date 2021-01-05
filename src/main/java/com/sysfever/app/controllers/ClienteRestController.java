package com.sysfever.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysfever.app.models.service.IClienteService;
import com.sysfever.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value = "/listar",produces = {"application/json"})
	public ClienteList listar() {
		return new ClienteList(clienteService.findAll());
	}
}
