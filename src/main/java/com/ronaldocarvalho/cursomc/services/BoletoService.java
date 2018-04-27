package com.ronaldocarvalho.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto (PagamentoComBoleto pgto, Date instantedoPedido) {
		
		Calendar cal  = Calendar.getInstance();
		cal.setTime(instantedoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(cal.getTime());
	}
	
}
