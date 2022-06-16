package github.madeoliveira.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import github.madeoliveira.controlefinanceiro.enums.StatusLancamento;
import github.madeoliveira.controlefinanceiro.model.entity.Lancamento;

public interface LancamentoService {
	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	void deletar(Lancamento lancamneto);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);

	void atualizarStatus(Lancamento lancamento, StatusLancamento staus);
	
	void validar (Lancamento lancamento);
	
	Optional<Lancamento> obterPorId(Long id);
}
