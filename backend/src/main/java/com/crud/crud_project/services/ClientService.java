package com.crud.crud_project.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.crud.crud_project.DTO.ClientDto;
import com.crud.crud_project.entities.Client;
import com.crud.crud_project.repositories.ClientRepository;
import com.crud.crud_project.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	public ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDto> findAll(Pageable pageable) {
		Page<Client> list = repository.findAll(pageable);
		return list.map(x -> new ClientDto(x));
		
	}

	@Transactional(readOnly = true)
	public ClientDto findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client dto = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!!!"));
		return new ClientDto(dto);
	}

	@Transactional
	public ClientDto create(@RequestBody ClientDto dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDto(entity);
	}

	@Transactional
	public ClientDto update(Long id, ClientDto dto) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDto(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void copyDtoToEntity(ClientDto dto, Client client) {
		client.setName(dto.getName());
		client.setBirthDate(dto.getBirthDate());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setChildren(dto.getChildren());
	}

}
