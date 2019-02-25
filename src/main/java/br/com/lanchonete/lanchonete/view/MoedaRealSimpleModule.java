package br.com.lanchonete.lanchonete.view;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.lanchonete.lanchonete.domain.MoedaReal;

@Service
public class MoedaRealSimpleModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public MoedaRealSimpleModule() {
		addSerializer(MoedaReal.class, new MoedaRealSerializer());
	}

}
