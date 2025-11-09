package domain.model;

import java.util.Objects;
import java.util.UUID;

public class Cliente {

    private UUID id;
    private String nome;
    private String email;
    private String telefone;

//  CONSTRUTOR PARA CLIENTES NOVOS
    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

//   CONSTRUTOR PARA CLIENTE JÁ CADASTRADOS
    public Cliente(UUID id, String nome, String email, String telefone){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    public String toCSV(){
        String idString = (this.id == null) ? "" : this.id.toString();
        return String.format("%s,%s,%s,%s", idString, nome, email, telefone);
    }

    public static Cliente fromCSV(String linha){
        if (linha == null || linha.isEmpty()){
            throw new IllegalArgumentException("Linha CSV não pode ser nula");
        }
        String[] partes = linha.split(",");
        if (partes.length != 4){
            throw new IllegalArgumentException("Linha mal formatada. Espera 4 colunas, mas encontrou: " + partes.length);
        }
        try{
            UUID id = partes[0].isEmpty() ? null : UUID.fromString(partes[0]);
            String nome = partes[1];
            String email = partes[2];
            String telefone = partes[3];
            return new Cliente(id, nome, email, telefone);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Erro ao parsear número na linha CSV: " + linha, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
