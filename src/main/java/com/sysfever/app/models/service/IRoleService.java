package com.sysfever.app.models.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.sysfever.app.models.entity.Role;

public interface IRoleService{

	public List<Role>findAll();
	
	public void deleteById(Long id);

	public DataTablesOutput<Role> findAll(@Valid DataTablesInput input);
	
}
