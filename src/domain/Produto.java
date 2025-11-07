package domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Produto {

    private static final AtomicLong CONTADOR_ID = new AtomicLong(0);
    private final long id;
    private String nome;
    private BigDecimal preco;
    private int estoque;

    public Produto(long id, String nome, BigDecimal preco, int estoque) {
        this.id = CONTADOR_ID.incrementAndGet();
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
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
        return String.format("%d,%s,%s,%d", id, nome, preco, estoque);
    }

    public Produto fromCSV(String linha){
        if (linha == null || linha.isEmpty()){
            throw new IllegalArgumentException("A linha não pode ser nula.");
        }
        String[] partes = linha.split(",");
        if (partes.length != 4){
            throw new IllegalArgumentException("Linha mal formatada, esperava 4 colunas, mas havia: " + partes.length);
        }
        try {
            long id = Long.parseLong(partes[0]);
            String nome = partes[1];
            BigDecimal preco = new BigDecimal(partes[2]); //Não sei parsear
            int estoque = Integer.parseInt(partes[3]);
            return new Produto(id, nome, preco, estoque);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Erro ao tentar parsear os números na linha: " + linha, e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
