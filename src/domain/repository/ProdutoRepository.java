package domain.repository;

import domain.model.Produto;

import java.math.BigDecimal;
import java.util.List;


public interface ProdutoRepository extends GenericDAO<Produto>{

    List<Produto> buscarPorNome(String nome);
    List<Produto> buscarEstoqueAbaixoDe(int estoque);
    List<Produto> buscarPorPrecoNoIntervalo(BigDecimal minimo, BigDecimal maximo);
}
