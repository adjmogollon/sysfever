package com.sysfever.app.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.sysfever.app.models.entity.User;

public interface IUserRepository extends DataTablesRepository<User,Long>{

	public User findByUsername(String username);
	
	public List<User> findByUsernameLikeIgnoreCase(String keyword);

	public Page<User> findByUsernameContaining(String keyword, Pageable pageable);
	
}
