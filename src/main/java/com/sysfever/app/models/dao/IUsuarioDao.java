package com.sysfever.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sysfever.app.models.entity.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
	
	public List<Usuario> findByUsernameLikeIgnoreCase(String keyword);

	public Page<Usuario> findByUsernameContaining(String keyword, Pageable pageable);
}
