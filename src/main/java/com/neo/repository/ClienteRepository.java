package com.neo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neo.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("SELECT c.nomeCliente FROM Cliente c")
	List<String> findAllClientes();

	Cliente findByCpf(String cpf);
	
	List<Cliente> findAllByNomeClienteContaining(String nomeCliente);	
}
