package domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Produto {

    private UUID id;
    private String nome;
    private BigDecimal preco;
    private int estoque;

//    CONSTRUTOR PARA NOVOS PRODUTOS
    public Produto(String nome, BigDecimal preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }
//    CONSTRUTOR PARA PRODUTOS JÁ CADASTRADOS
    public Produto(UUID id, String nome, BigDecimal preco, int estoque){
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                '}';
    }

    public String toCSV(){
        String idString = (this.id == null) ? "" : this.id.toString();
        return String.format("%s,%s,%s,%d", idString, nome, preco, estoque);
    }

    public static Produto fromCSV(String linha){
        if (linha == null || linha.isEmpty()){
            throw new IllegalArgumentException("A linha não pode ser nula.");
        }
        String[] partes = linha.split(",");
        if (partes.length != 4){
            throw new IllegalArgumentException("Linha mal formatada, esperava 4 colunas, mas havia: " + partes.length);
        }
        try {
            UUID id = partes[0].isEmpty() ? null : UUID.fromString(partes[0]);
            String nome = partes[1];
            BigDecimal preco = new BigDecimal(partes[2]);
            int estoque = Integer.parseInt(partes[3]);
            return new Produto(id, nome, preco, estoque);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Erro ao tentar parsear os números na linha: " + linha, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
