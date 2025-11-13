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
import java.util.stream.Collectors;

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
        for (ItemVenda item : venda.getItens().values()){
            item.setIdVenda(venda.getId());
        }
        //Ler o arquivo
        var linhasVendas = filerManager.lerTodasLinhas(pathVenda);
        boolean vendaFoiAtualizada = false;

        for (int i = 0; i < linhasVendas.size(); i++)  {
            var linha = linhasVendas.get(i);
            var partesLinha = linha.split(",");
            if (Objects.equals(venda.getId(), UUID.fromString(partesLinha[0]))){
                linhasVendas.set(i, venda.toCSV());
                vendaFoiAtualizada = true;
                break;
            }
        }
        if (!vendaFoiAtualizada){
            linhasVendas.add(venda.toCSV());
        }
        filerManager.escreverLinhas(pathVenda, linhasVendas);

        // Salvar os itens:
        var linhasItens = filerManager.lerTodasLinhas(pathItens);
        var novaListaItens = linhasItens.stream()
                .filter(l -> {
                    var partes = l.split(",");
                    return (!Objects.equals(UUID.fromString(partes[0]), venda.getId()));
                })
                .collect(Collectors.toList());

        for (var linha : venda.getItens().values()){
            novaListaItens.add(linha.toCSV());
        }
        filerManager.escreverLinhas(pathItens, novaListaItens);
    }

    @Override
    public void deletar(UUID id) {
        var linhasVendas = filerManager.lerTodasLinhas(pathVenda);
        var novasListaVendas = linhasVendas.stream()
                .filter(l ->{
                    var partes = l.split(",");
                    return (!Objects.equals(UUID.fromString(partes[0]), id));
                }).toList();
        filerManager.escreverLinhas(pathVenda, novasListaVendas);

        var linhasItens = filerManager.lerTodasLinhas(pathItens);
        var novaListaItens = linhasItens.stream()
                .filter(l -> {
                    var partes = l.split(",");
                    return (!Objects.equals(UUID.fromString(partes[0]), id));
                }).toList();
        filerManager.escreverLinhas(pathItens, novaListaItens);
    }

    @Override
    public List<Venda> buscarTodos() {
        var linhas = filerManager.lerTodasLinhas(pathVenda);
        return linhas.stream()
                .map(l -> {
                    var partes = l.split(",");
                    var idVenda = UUID.fromString(partes[0]);
                    var idCliente = UUID.fromString(partes[1]);
                    var data = LocalDateTime.parse(partes[2]);
                    var total = new BigDecimal(partes[3]);
                    Cliente cliente = clienteRepo.buscarPorId(idCliente).orElseThrow(() -> new IllegalArgumentException("Cliente inválido!"));
                    return new Venda(idVenda, cliente, data, new LinkedHashMap<>(),total);
                }).toList();
    }

    @Override
    public Optional<Venda> buscarPorId(UUID id) {
        String linhaVendaEncontrada = null;
        //Ler o arquivo
        var linhas = filerManager.lerTodasLinhas(pathVenda);
        //Achar o id da venda
        for (var linha : linhas) {
            var partes = linha.split(",");
            var idLinha = UUID.fromString(partes[0]);
            if (Objects.equals(id, idLinha)) {
                linhaVendaEncontrada = linha;
                break;
            }
        }
        if (linhaVendaEncontrada == null){
                return Optional.empty();
            }

//        ACHAR OS ITENS:
        Map<Produto, ItemVenda> mapaDeItens = new LinkedHashMap<>();
        var linhasItens = filerManager.lerTodasLinhas(pathItens);

        for(String linhaitem : linhasItens){
            var partesItem = linhaitem.split(",");
            var idVenda = UUID.fromString(partesItem[0]);

            if (Objects.equals(id, idVenda)){
                var idProduto = UUID.fromString(partesItem[1]);
                int quantidade = Integer.parseInt(partesItem[2]);
                Produto produto = produtoRepo.buscarPorId(idProduto).orElseThrow(() -> new IllegalArgumentException("Produto inválido!"));
                var itemVenda = new ItemVenda(idVenda, produto, quantidade);
//                Inserir os itens no mapa:
                mapaDeItens.put(produto, itemVenda);
            }
        }
        //montar a venda:
        var partesVenda = linhaVendaEncontrada.split(",");
        var idVenda = UUID.fromString(partesVenda[0]);
        var idCliente = UUID.fromString(partesVenda[1]);
        var data = LocalDateTime.parse(partesVenda[2]);
        var total = new BigDecimal(partesVenda[3]);

        Cliente cliente = clienteRepo.buscarPorId(idCliente).orElseThrow(() -> new IllegalArgumentException("Cliente inválido!"));
        //retornar a venda
        var vendaCompleta = new Venda(idVenda, cliente, data, mapaDeItens, total);
        return Optional.of(vendaCompleta);
    }
}
