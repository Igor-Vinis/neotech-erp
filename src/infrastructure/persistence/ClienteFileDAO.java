package infrastructure.persistence;

import domain.model.Cliente;
import domain.repository.ClienteRepository;

import java.rmi.server.UID;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ClienteFileDAO implements ClienteRepository{

    private final FilerManager filerManager = new FilerManager();
    private final String path = "data/Clientes/clientes.csv";

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        return Optional.empty();
    }

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

    }

    @Override
    public List<Cliente> buscarTodos() {
        return List.of();
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return Optional.empty();
    }


}
