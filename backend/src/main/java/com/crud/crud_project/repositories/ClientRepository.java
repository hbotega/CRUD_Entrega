package com.crud.crud_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.crud_project.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
