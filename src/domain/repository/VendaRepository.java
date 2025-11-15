package domain.repository;

import domain.model.Venda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VendaRepository {

    void salvar(Venda venda);
    List<Venda> buscarTodos();
    Optional<Venda> buscarPorId(UUID id);
    boolean existeVendaParaCliente(UUID idCliente);

}
