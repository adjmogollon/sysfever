package com.sysfever.app.models.service;


import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.sysfever.app.models.entity.User;

public interface IUserService {

	public User findByUsername(String username);

	public List<User> findAll();

	public Page<User> findAll(Pageable pageable);
	
	public List<User> findByUsernameLikeIgnoreCase(String keyword);
	
	public Page<User> findByUsernameContaining(String keyword, Pageable pageable);

	public User findById(Long id);

	public void deleteById(Long id);

	public void save(User user);

	public DataTablesOutput<User> findAll(@Valid DataTablesInput input);
	

	
}
