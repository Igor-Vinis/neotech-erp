package infrastructure.persistence;

import domain.model.Cliente;
import domain.repository.ClienteRepository;

import java.rmi.server.UID;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClienteFileDAO implements ClienteRepository{

    private final FilerManager filerManager = new FilerManager();
    private final String path = "data/Clientes/clientes.csv";

    @Override
    public void salvar(Cliente obj) {
        if (obj.getId() == null){
            obj.setId(UUID.randomUUID());
        }
        List<String> linhas = filerManager.lerTodasLinhas(path);
        boolean clienteFoiAtualizado = false;

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i);
            var c = Cliente.fromCSV(linha);
            if (Objects.equals(obj.getId(), c.getId())){
                linhas.set(i,obj.toCSV());
                clienteFoiAtualizado = true;
                break;
            }
        } if (!clienteFoiAtualizado) {
            linhas.add(obj.toCSV());
        }
        filerManager.escreverLinhas(path, linhas);
    }

    @Override
    public void deletar(UUID id) {
        List<String> linhas = filerManager.lerTodasLinhas(path);
        var novaLista = linhas.stream()
                .filter(l -> {var c = Cliente.fromCSV(l);
                    boolean manter = !(Objects.equals(id, c.getId()));
                    return manter;})
                .toList();

        filerManager.escreverLinhas(path,novaLista);
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<String> linhas = filerManager.lerTodasLinhas(path);
        List<Cliente> clientes = linhas.stream().map(Cliente::fromCSV).toList();
        return clientes;
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {

        List<String> linhas = filerManager.lerTodasLinhas(path);
        for(String linha : linhas){
            var cliente = Cliente.fromCSV(linha);
            if (Objects.equals(id, cliente.getId())){
                return Optional.of(cliente);
            }
        } return Optional.empty();
    }

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        List<String> linhas = filerManager.lerTodasLinhas(path);
        for(String linha : linhas){
            var cliente = Cliente.fromCSV(linha);
            if (Objects.equals(email, cliente.getEmail())){
                return Optional.of(cliente);
            }
        } return Optional.empty();

    }


}
