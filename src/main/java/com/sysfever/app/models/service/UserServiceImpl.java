package com.sysfever.app.models.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysfever.app.models.entity.User;
import com.sysfever.app.models.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByUsernameLikeIgnoreCase(String keyword) {
		return userRepository.findByUsernameLikeIgnoreCase(keyword);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findByUsernameContaining(String keyword, Pageable pageable) {
		return userRepository.findByUsernameContaining(keyword, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public DataTablesOutput<User> findAll(@Valid DataTablesInput input) {
		return userRepository.findAll(input);
	}

}
