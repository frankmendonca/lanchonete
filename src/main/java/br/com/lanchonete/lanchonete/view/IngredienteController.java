package br.com.lanchonete.lanchonete.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lanchonete.lanchonete.domain.CalculadorPrecoService;
import br.com.lanchonete.lanchonete.domain.Ingrediente;
import br.com.lanchonete.lanchonete.domain.IngredienteRepository;
import br.com.lanchonete.lanchonete.domain.RelatorioPrecoCalculado;

@RestController
@RequestMapping("ingredientes")
public class IngredienteController {

	@Autowired
	private IngredienteRepository repository;

	@Autowired
	private CalculadorPrecoService calculadorPrecoService;

	@GetMapping("listar")
	public List<Ingrediente> listar() {
		return repository.listarTodosIngredientes();
	}

	@PostMapping("calcularPreco")
	public ResponseEntity<RelatorioPrecoInfo> calcularPreco(@RequestBody final IngredientesACalcularInfo ingredientes) {
		final RelatorioPrecoCalculado relatorioPreco = calculadorPrecoService.calcular(ingredientes.toObjeto());
		final RelatorioPrecoInfo relatorioPrecoInfo = mapToRelatorioPrecoInfo(relatorioPreco);
		return ResponseEntity.ok(relatorioPrecoInfo);
	}

	private RelatorioPrecoInfo mapToRelatorioPrecoInfo(final RelatorioPrecoCalculado relatorioPreco) {
		final RelatorioPrecoInfo relatorioPrecoInfo = new RelatorioPrecoInfo();
		relatorioPrecoInfo.setIngredientes(relatorioPreco.getIngredientes());
		relatorioPrecoInfo.setPrecoLanche(relatorioPreco.getPrecoLanche().asBigDecimal());
		relatorioPrecoInfo.setPrecoDesconto(relatorioPreco.getPrecoDesconto().asBigDecimal());
		relatorioPrecoInfo.setPrecoFinal(relatorioPreco.getPrecoFinal().asBigDecimal());
		relatorioPrecoInfo.setPromocoes(relatorioPreco.getPromocoes());
		return relatorioPrecoInfo;
	}

}
