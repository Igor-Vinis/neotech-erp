package service;

import domain.model.Produto;
import domain.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.Objects;

public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public void salvarProduto(Produto produto){

        if (produto.getNome() == null || produto.getNome().trim().isEmpty() ){
            throw new IllegalArgumentException("O nome no produto não pode ser nulo");
        }
        if (produto.getEstoque() < 0 ){
            throw new IllegalArgumentException("O estoque não pode ser menor que zero");
        }
        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor precisa ser maior que zero.");
        }
        produtoRepository.salvar(produto);
    }

    public void deletarProduto(UUID id){

        var produto = produtoRepository.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto inválido!"));
        if (produto.getEstoque() > 0){
            throw new IllegalArgumentException("Para deletar um produto, é necessário que o estoque seja zero.");
        }
        produtoRepository.deletar(id);
    }

    public Optional<Produto> buscarPorId(UUID id){
        return produtoRepository.buscarPorId(id);
    }

    public List<Produto> buscarTodos(){
        return produtoRepository.buscarTodos();
    }

    public List<Produto> buscarPorNome(String nome){
        return produtoRepository.buscarPorNome(nome);
    }

    public List<Produto> buscarEstoqueAbaixoDe(int estoque){
        if (estoque < 0){
            throw new IllegalArgumentException("O estoque não pode ser menor que zero!");
        }
        return produtoRepository.buscarEstoqueAbaixoDe(estoque);
    }

    public List<Produto> buscarPorPrecoNoIntervalo(BigDecimal minimo, BigDecimal maximo){
        if (minimo.compareTo(BigDecimal.ZERO) < 0 || maximo.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("O preço não pode ser menor que zero!");
        }
        return produtoRepository.buscarPorPrecoNoIntervalo(minimo, maximo);
    }

}
