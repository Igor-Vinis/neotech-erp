package domain.repository;

import domain.Cliente;

import java.util.Optional;

public interface ClienteRepository extends GenericDAO<Cliente>{

    Optional<Cliente> buscarPorEmail(String email);
}
