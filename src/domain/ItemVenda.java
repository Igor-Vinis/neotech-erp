package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemVenda {

    private Produto produto;
    private int quantidade;
    private BigDecimal subtotal;

    public ItemVenda(Produto produto, int quantidade, BigDecimal subtotal) {
        this.produto = produto;
        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade noã pode ser negativa.");
        } this.quantidade = quantidade;
        if (subtotal.doubleValue() < 0){
            throw new IllegalArgumentException("O subtotal não pode ser negativo");
        }
        this.subtotal = calcularSubtotal();
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
        return subtotal;
    }

    public BigDecimal calcularSubtotal(){
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                ", subtotal=" + subtotal +
                '}';
    }

    public String toCSV() {
        return String.format("%s,%d,%.2f", produto.getNome(),quantidade,subtotal);
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
