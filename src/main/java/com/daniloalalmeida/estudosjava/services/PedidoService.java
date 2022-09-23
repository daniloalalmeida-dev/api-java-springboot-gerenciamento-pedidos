package com.daniloalalmeida.estudosjava.services;

import com.daniloalalmeida.estudosjava.domain.Cliente;
import com.daniloalalmeida.estudosjava.domain.ItemPedido;
import com.daniloalalmeida.estudosjava.domain.PagamentoComBoleto;
import com.daniloalalmeida.estudosjava.domain.Pedido;
import com.daniloalalmeida.estudosjava.domain.enums.EstadoPagamento;
import com.daniloalalmeida.estudosjava.exceptions.ObjectNotFoundException;
import com.daniloalalmeida.estudosjava.repositories.ItemPedidoRepository;
import com.daniloalalmeida.estudosjava.repositories.PagamentoRepository;
import com.daniloalalmeida.estudosjava.repositories.PedidoRepository;
import com.daniloalalmeida.estudosjava.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id: " + id + ", Tipo: " + Pedido.class.getName()
        ));
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido itemPedido : obj.getItens()) {
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
}