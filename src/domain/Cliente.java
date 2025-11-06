package domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Cliente {

    private static final AtomicLong CONTADOR_ID = new AtomicLong(0);
    private final long id;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(Long id, String nome, String email, String telefone) {
        this.id = CONTADOR_ID.incrementAndGet();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
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
        return String.format("%d,%s,%s,%s", id, nome, email, telefone);
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
            Long id = Long.parseLong(partes[0]);
            String nome = partes[1];
            String email = partes[2];
            String telefone = partes[3];
            return new Cliente(id, nome, email, telefone);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Erro ao parsear número na linha CSV: " + linha, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
