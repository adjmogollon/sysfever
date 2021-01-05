package com.sysfever.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sysfever.app.models.entity.Role;

public interface IRoleDao extends PagingAndSortingRepository<Role, Long> {

	public Role findByAuthority(String role);
	
	public List<Role> findByAuthorityContaining(String keyword);

	public Page<Role> findByAuthorityContaining(String keyword, Pageable pageable);
}
