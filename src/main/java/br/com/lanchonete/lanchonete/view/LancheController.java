package br.com.lanchonete.lanchonete.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lanchonete.lanchonete.domain.Lanche;
import br.com.lanchonete.lanchonete.domain.LancheRepository;

@RestController
@RequestMapping("lanches")
public class LancheController {
	
	@Autowired
	private LancheRepository repository;
	
	@GetMapping("listar")
	public List<Lanche> listar() {
		return repository.listarTodosLanches();
	}

}
