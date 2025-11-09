package domain.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T>{

    //CRUD
    void salvar(T obj);
    void deletar(String id);
    void atualizar(T obj);
    List<T> buscarTodos();
    Optional<T> buscarPorId(String id);

}