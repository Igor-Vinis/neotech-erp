package service;

import domain.model.Venda;
import domain.repository.ClienteRepository;
import domain.repository.ProdutoRepository;
import domain.repository.VendaRepository;

public class VendaService {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;

    public VendaService(ClienteRepository clienteRepository, ProdutoRepository produtoRepository, VendaRepository vendaRepository) {
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.vendaRepository = vendaRepository;
    }

    public void salvarVenda(Venda venda){

    }
}
