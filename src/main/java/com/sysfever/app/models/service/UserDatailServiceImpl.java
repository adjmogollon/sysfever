package com.sysfever.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysfever.app.models.entity.Role;
import com.sysfever.app.models.entity.User;

@Service("UserDetailsService")
public class UserDatailServiceImpl implements UserDetailsService{

	
	@Autowired
	private IUserService userService; 
	
	private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			logger.error("Error login: no existe el usuario '" + username + "'");
			throw new UsernameNotFoundException("Username " + username + " no existe en el sistema!");
		}

		if (user.getEnabled() == false) {
			logger.error("Error login: El usuario esta inactivo '" + username + "'");
			throw new UsernameNotFoundException("Username " + username + " Esta Inactivo!");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));

		}

		if (authorities.isEmpty()) {
			logger.error("Error login: El usuario '" + username + "' no tiene roles asignado");
			throw new UsernameNotFoundException(
					"Error login: El usuario '" + username + "' no tiene roles asignado");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

}
