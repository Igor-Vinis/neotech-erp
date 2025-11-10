package domain.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericDAO<T>{

    //CRUD
    void salvar(T obj);
    void deletar(UUID id);
    List<T> buscarTodos();
    Optional<T> buscarPorId(UUID id);

}