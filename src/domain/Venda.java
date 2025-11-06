package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Venda {

    private final AtomicLong CONTADOR_ID = new AtomicLong(0);
    private final long id;
    private LocalDateTime data;
    private Cliente cliente;
    private List<ItemVenda> itens = new List<>();


}
