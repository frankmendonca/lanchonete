'use strict';

$(function() {
	
	var
		_lanches = [],
		_ingredientes = [],
	
		carregarTela = function() {
			carregarLanches();
		},
		
		// Lanches
		
		carregarLanches = function() {
			$.get({
				url: '/lanches/listar'
			})
			.done(function(lanches) {
				_lanches = lanches;
				carregarTableLanches(lanches);
			});
		},
		
		carregarTableLanches = function(lanches) {
			var html = gerarHtmlLanches(lanches);
			$('#tableLanches').find('tbody').append(html);
		},
		
		gerarHtmlLanches = function(lanches) {
			var html = '';
			for (var i = 0; i < lanches.length; i++) {
				var lanche = lanches[i];
				html += gerarHtmlUmLanche(lanche, i);
			}
			return html;
		},
		
		gerarHtmlUmLanche = function(lanche, id) {
			var html = '<tr>';
			html += '<td class="check_column"><input type="radio" name="lanche" id="' + id + '"></td>';
			html += '<td>' + lanche.nome + '</td><td>';
			
			var htmlIngrediente = convertArrayToString(lanche.ingredientes, function(ingrediente) { return ingrediente.nome; });
			html += htmlIngrediente + '</td></tr>';
			return html;
		},
		
		getLancheSelecionado = function() {
			var id = $(':radio:checked').prop('id');
			return _lanches[id];
		},
		
		// Ingredientes
		
		carregarIngredientes = function() {
			if (_ingredientes.length === 0) {
				$.get({
					url: '/ingredientes/listar'
				})
				.done(function(ingredientes) {
					_ingredientes = ingredientes;
					carregarTableIngredientes(ingredientes);
					marcarIngredientesDoLancheSelecionado();
				});
			} else {
				marcarIngredientesDoLancheSelecionado();
			}
		},
		
		carregarTableIngredientes = function(ingredientes) {
			var html = gerarHtmlIngredientes(ingredientes);
			$('#tableIngredientes').find('tbody').append(html);
		},
		
		gerarHtmlIngredientes = function(ingredientes) {
			var html = '';
			for (var i = 0; i < ingredientes.length; i++) {
				var ingrediente = ingredientes[i];
				html += gerarHtmlUmIngrediente(ingrediente, ingrediente.nome);
			}
			return html;
		},
		
		gerarHtmlUmIngrediente = function(ingrediente, id) {
			var html = '<tr>';
			html += '<td>' + ingrediente.nome + '</td>';
			html += '<td><input type="number" id="' + id + '" value="0" min="0"></td></tr>';
			return html;
		},
		
		marcarIngredientesDoLancheSelecionado = function() {
			var lancheSelecionado = getLancheSelecionado(),
				ingredientes = lancheSelecionado.ingredientes;

			$(':input[type="number"]').val(0);
			for (var i = 0; i < ingredientes.length; i++) {
				var ingrediente = ingredientes[i];
				$(':input[id="' + ingrediente.nome + '"]').val(1);
			}
		},
		
		getIngredientesSelecionados = function() {
			var ingredientes = [];
			if ($('#tableIngredientes').is(':visible')) {
				$.each($('#tableIngredientes :checkbox:checked'), function(elem) {
					ingredientes.push($(this).attr('id'));
				});
			} else {
				var ingredientesDoLanche = getLancheSelecionado().ingredientes;
				for (var i = 0; i < ingredientesDoLanche.length; i++) {
					var ingrediente = ingredientesDoLanche[i];
					ingredientes.push(ingrediente.nome);
				}
			}
			
			return convertArrayToString(ingredientes);
		},
		
		convertArrayToString = function(array, map) {
			map = map || function(el) { return el; };
			
			var texto = '';
			for (var i = 0; i < array.length; i++) {
				texto += ', ' + map(array[i]);
			}
			return texto.substring(2);
		},
		
		configurarClicks = function() {

			// click nos radios
			$('#tableLanches').on('click', ':radio', function() {
				$('#tableIngredientes').css('display', 'none');
				$('#relatorio').css('display', 'none');
			});
			
			// click no botao personalizar
			$('#botaoPersonalizar').click(function(e) {
				if (!$('input:radio[name="lanche"]').is(':checked')) {
					alert('Selecione um Lanche');
					return;
				}
				carregarIngredientes();
				$('#tableIngredientes').css('display', 'table');
			});

			// click no botao finalizar compra
			$('#botaoFinalizarCompra').click(function(e) {
				if (!$('input:radio[name="lanche"]').is(':checked')) {
					alert('Selecione um Lanche');
					return;
				}
				
				var ingredientes = [],
					qtdeNegativa = false;
				
				if ($('#tableIngredientes').is(':visible')) {
					$.each($('input[type="number"]'), function() {
						var elem = $(this),
							ingrediente = elem.attr('id'),
							qtde = elem.val();
						if (qtde > 0) {
							ingredientes.push({ nome: ingrediente, quantidade: qtde });
						}
						if (qtde < 0) {
							qtdeNegativa = true;
							return;
						}
					});
					if (qtdeNegativa) {
						alert('ERRO: Quantidade de Ingrediente negativa');
						return;
					}
				} else {
					var ingredientesDoLanche = getLancheSelecionado().ingredientes;
					for (var i = 0; i < ingredientesDoLanche.length; i++) {
						var ingrediente = ingredientesDoLanche[i];
						ingredientes.push({ nome: ingrediente.nome, quantidade: 1 });
					}
				}
				
				if (ingredientes.length == 0) {
					alert('Selecione pelo menos um Ingrediente');
					return;
				}
				
				$.post({
					url: '/ingredientes/calcularPreco',
					contentType: 'application/json',
					data: JSON.stringify({
						ingredientes: ingredientes
					})
				})
				.done(function(relatorio) {
					var resumoHtml = '';
					resumoHtml += '     Lanche selecionado: ' + getLancheSelecionado().nome + '<br>';
					resumoHtml += '     Ingredientes: ' + convertArrayToString(relatorio.ingredientes);
					if (relatorio.promocoes.length > 0) {
						resumoHtml += '<br>     Promoções Aplicadas: ' + convertArrayToString(relatorio.promocoes);
					}
					$('#resumo').html(resumoHtml);
					
					var precoLanche   = relatorio.precoLanche.toFixed(2),
						precoDesconto = relatorio.precoDesconto.toFixed(2),
						precoFinal    = relatorio.precoFinal.toFixed(2);
					
					var tam = precoLanche.length;
					if (tam < precoDesconto.length) {
						tam = precoDesconto.length;
					}
					if (tam < precoFinal.length) {
						tam = precoFinal.length;
					}
					
					var precoFinalHtml = '';
					precoFinalHtml += '      Preço do Lanche      : R$ ' + precoLanche.padStart(tam) + ' (+)<br>';
					precoFinalHtml += '      Total de Desconto    : R$ ' + precoDesconto.padStart(tam) + ' (-)<br>';
					precoFinalHtml += '      Preço final do Lanche: R$ ' + precoFinal.padStart(tam) + ' (=)<br>';
					$('#precoFinal').html(precoFinalHtml);
					
					$('#relatorio').css('display', 'block');
				});
			});
		};
	
	carregarTela();
	configurarClicks();
});
