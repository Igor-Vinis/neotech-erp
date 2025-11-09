package infrastructure.persistence;

import domain.Cliente;
import domain.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

public class ClienteFIleDAO implements ClienteRepository {


    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void salvar(Cliente obj) {

    }

    @Override
    public void deletar(String id) {

    }

    @Override
    public void atualizar(Cliente obj) {

    }

    @Override
    public List<Cliente> buscarTodos() {
        return List.of();
    }

    @Override
    public Optional<Cliente> buscarPorId(String id) {
        return Optional.empty();
    }
}
