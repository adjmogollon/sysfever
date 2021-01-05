package com.sysfever.app.models.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.sysfever.app.models.entity.Role;

public interface IRoleRepository extends DataTablesRepository<Role, Long> {}

