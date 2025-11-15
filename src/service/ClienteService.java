package service;

import domain.model.Cliente;
import domain.model.Venda;
import domain.repository.ClienteRepository;
import domain.repository.VendaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final VendaRepository vendaRepository;

    public ClienteService(ClienteRepository clienteRepository, VendaRepository vendaRepository){
        this.clienteRepository = clienteRepository;
        this.vendaRepository = vendaRepository;
    }

    public void salvarCliente(Cliente cliente){
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("O email do cliente não pode ser nulo!");
        }
        clienteRepository.salvar(cliente);
    }

    public void deletar(UUID id){
        Cliente cliente = clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente inválido"));
        if (vendaRepository.existeVendaParaCliente(cliente.getId())){
            throw new IllegalStateException("Não é possível deletar um cliente que possui compras no histórico.");
        }
        clienteRepository.deletar(id);
    }

    public Optional<Cliente> buscarPorId(UUID id){
        return clienteRepository.buscarPorId(id);
    }

    public Optional<Cliente> buscarPorEmail(String email){
        return clienteRepository.buscarPorEmail(email);
    }

    public List<Cliente> buscarTodos(){
        return clienteRepository.buscarTodos();
    }

}
