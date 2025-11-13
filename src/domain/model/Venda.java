package domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Venda {

    private UUID id;
    private final LocalDateTime data;
    private final Cliente cliente;
    private BigDecimal total = BigDecimal.ZERO;
    private final Map<Produto, ItemVenda> itens = new LinkedHashMap<>();


    public Venda(Cliente cliente, LocalDateTime data) {
        if (cliente == null || data == null){
            throw new IllegalArgumentException("O cliente e a data não podem ser nulos.");
        }
        this.cliente = cliente;
        this.data = data;
    }
    public Venda(UUID id, Cliente cliente, LocalDateTime data, Map<Produto, ItemVenda> itens, BigDecimal total) {
        if (cliente == null || data == null){
            throw new IllegalArgumentException("O cliente e a data não podem ser nulos.");
        }
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.total = total;
        this.itens.putAll(itens);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        if (this.id != null){
            throw new IllegalArgumentException("Não é possível modificar o Id de uma compra já realizada.");
        } this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Map<Produto, ItemVenda> getItens() {
        return Collections.unmodifiableMap(itens);
    }

    public void recalcularTotal(){
        this.total = BigDecimal.ZERO;
        itens.values().forEach(item -> this.total = total.add(item.getSubtotal()));
    }

    public void addItem(Produto produto, int quantidade){
        //Verificar se já há um produto
        if (quantidade <= 0){
            throw new IllegalArgumentException("A quantidade não pode ser nula ou negativa");
        }
        if(itens.containsKey(produto)){
            ItemVenda itemExistente = itens.get(produto);
            int novaQuantidade = itemExistente.getQuantidade() + quantidade;
            itemExistente.setQuantidade(novaQuantidade);
        } else {
            ItemVenda novoItem = new ItemVenda(produto, quantidade);
            itens.put(produto, novoItem);
        }
        recalcularTotal();
    }

    public void removerItem(Produto produto){
        if(!itens.containsKey(produto)){
            throw new IllegalArgumentException("O item não existe.");
        } else{
            itens.remove(produto);
            recalcularTotal();
        }
    }

    //TODO MétodotoCSV
    public String toCSV(){
        return String.format("%s,%s,%s,%s", id, cliente.getId(),data.toString(),total.toString());
    }

}
