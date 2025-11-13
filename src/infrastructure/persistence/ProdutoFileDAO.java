package infrastructure.persistence;

import domain.model.Produto;
import domain.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ProdutoFileDAO implements ProdutoRepository {

    private final FilerManager filemanager = new FilerManager();
    private final String path = "data/Produtos/produtos.csv";


    @Override
    public List<Produto> buscarPorNome(String nome) {

        List<String> linhas = filemanager.lerTodasLinhas(path);
        return linhas.stream()
                .map(Produto::fromCSV)
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }

    @Override
    public List<Produto> buscarEstoqueAbaixoDe(int estoque) {

        List<String> linhas = filemanager.lerTodasLinhas(path);
        return linhas.stream()
                .map(Produto::fromCSV)
                .filter(p -> (p.getEstoque() < estoque))
                .toList();
    }

    @Override
    public List<Produto> buscarPorPrecoNoIntervalo(BigDecimal minimo, BigDecimal maximo) {

        var linhas = filemanager.lerTodasLinhas(path);
        return linhas.stream()
                .map(Produto::fromCSV)
                .filter(produto -> ((produto.getPreco().compareTo(minimo) >= 0 && (produto.getPreco().compareTo(maximo) <= 0))))
                .toList();
    }

    @Override
    public void salvar(Produto obj) {
        if (obj.getId() == null){
            obj.setId(UUID.randomUUID());
        }
        List<String> linhas = filemanager.lerTodasLinhas(path);
        boolean produtoFoiAtualizado = false;

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            var p = Produto.fromCSV(linha);
            if (Objects.equals(obj.getId(), p.getId())){
                linhas.set(i,obj.toCSV());
                produtoFoiAtualizado = true;
                break;
            }
        } if (!produtoFoiAtualizado) {
            linhas.add(obj.toCSV());
        }
        filemanager.escreverLinhas(path, linhas);
    }

    @Override
    public void deletar(UUID id) {
        List<String> linhas = filemanager.lerTodasLinhas(path);
        var novaLista = linhas.stream()
                .filter(l -> {var p = Produto.fromCSV(l);
                    return !(Objects.equals(id, p.getId()));})
                .toList();

        filemanager.escreverLinhas(path,novaLista);
    }

    @Override
    public List<Produto> buscarTodos() {

        List<String> linhas = filemanager.lerTodasLinhas(path);
        return linhas.stream().map(Produto::fromCSV).toList();
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {

        List<String> linhas = filemanager.lerTodasLinhas(path);
        for(String linha : linhas){
            var produto = Produto.fromCSV(linha);
            if (Objects.equals(id, produto.getId())){
                return Optional.of(produto);
            }
        } return Optional.empty();
    }
}
