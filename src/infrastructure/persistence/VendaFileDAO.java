package infrastructure.persistence;

import domain.model.Cliente;
import domain.model.ItemVenda;
import domain.model.Produto;
import domain.model.Venda;
import domain.repository.ClienteRepository;
import domain.repository.ProdutoRepository;
import domain.repository.VendaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class VendaFileDAO implements VendaRepository {

    private final FilerManager filerManager = new FilerManager();
    private final String pathVenda = "data/Vendas/vendas.csv";
    private final String pathItens = "data/Vendas/itens.csv";

    private final ClienteRepository clienteRepo;
    private final ProdutoRepository produtoRepo;

    public VendaFileDAO(ClienteRepository clienteRepo, ProdutoRepository produtoRepo){
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }

    @Override
    public void salvar(Venda venda) {
        if(venda.getId() == null){
            venda.setId(UUID.randomUUID());
        }
        //Ler o arquivo
        var linhas = filerManager.lerTodasLinhas(pathVenda);
        boolean compraFoiAtualizada = false;

        for (int i = 0; i < linhas.size(); i++) {
            var linhaVenda = linhas.get(i);
        }
        //verifica se é uma atualização:

        //Se for uma atualização, atualiza os itens

        //Se não, adiciona a nova compra a persistencia
    }

    @Override
    public void deletar(UUID id) {

    }

    @Override
    public List<Venda> buscarTodos() {
        return List.of();
    }

    @Override
    public Optional<Venda> buscarPorId(UUID id) {

        Venda vendaEncontrada = null;
        //Ler o arquivo
        var linhas = filerManager.lerTodasLinhas(pathVenda);
        //Achar o id da venda
        for (var linha : linhas){
            var partes = linha.split(",");
            var idLinha = UUID.fromString(partes[0]);
            var data = LocalDateTime.parse(partes[2]);
            var total = new BigDecimal(partes[4]);

            //Comparar os ids
            if(Objects.equals(id, idLinha)){
                var idCliente = UUID.fromString(partes[1]);
                Optional<Cliente> clienteOpt = clienteRepo.buscarPorId(idCliente);
                //validar
                if (clienteOpt.isEmpty()){
                    throw new IllegalArgumentException("Cliente da venda é inválido.");
                }
                //montar a venda
                var cliente = clienteOpt.get();
                vendaEncontrada = new Venda(idLinha, cliente, data, new LinkedHashMap<>(), total);
                break;}
        }
        //Verifica se a compra ainda é nula
        if (vendaEncontrada == null){
            return Optional.empty();
        }
        //achar os itens
        var linhasItens = filerManager.lerTodasLinhas(pathItens);
        for(String linhaitem : linhasItens){
            var partesItem = linhaitem.split(",");
            var idVenda = UUID.fromString(partesItem[0]);
            var idProduto = UUID.fromString(partesItem[1]);
            int quantidade = Integer.parseInt(partesItem[2]);
            var subtotal = new BigDecimal(partesItem[3]);
            //montar os itens
            Optional<Produto> produtoOpt = produtoRepo.buscarPorId(idProduto);
            if (produtoOpt.isEmpty()){
                throw new IllegalArgumentException("O produto é inválido");
            }
            var itemVenda = new ItemVenda(idVenda, produtoOpt.get(), quantidade, subtotal);
            vendaEncontrada.g
        }

        //retornar a venda
    }
}
