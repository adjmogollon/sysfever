package com.sysfever.app.models.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import com.sysfever.app.models.entity.Role;
import com.sysfever.app.models.repository.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public List<Role> findAll() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public DataTablesOutput<Role> findAll(@Valid DataTablesInput input) {
		return roleRepository.findAll(input);
	}

}
