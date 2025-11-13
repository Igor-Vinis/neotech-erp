package domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ItemVenda {

    private Produto produto;
    private int quantidade;
    private UUID idVenda;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade noã pode ser negativa.");
        } this.quantidade = quantidade;
    }

    public ItemVenda(UUID idVenda, Produto produto, int quantidade) {
        this.produto = produto;
        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade noã pode ser negativa.");
        } this.quantidade = quantidade;
        this.idVenda = idVenda;
    }

    public UUID getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(UUID idVenda) {
        this.idVenda = idVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getSubtotal() {
        if(produto == null || produto.getPreco() == null){
            return BigDecimal.ZERO;
        }
        return produto.getPreco().multiply(BigDecimal.valueOf(this.quantidade));
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                ", subtotal=" + getSubtotal() +
                '}';
    }

    public String toCSV(){
        return String.format("%s,%s,%d",idVenda.toString(), produto.getId().toString(), quantidade);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return Objects.equals(produto, itemVenda.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(produto);
    }

}
