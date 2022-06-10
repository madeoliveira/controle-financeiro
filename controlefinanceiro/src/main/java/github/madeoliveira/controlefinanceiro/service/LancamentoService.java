package github.madeoliveira.controlefinanceiro.service;

import java.util.List;

import github.madeoliveira.controlefinanceiro.enums.StatusLancamento;
import github.madeoliveira.controlefinanceiro.model.entity.Lancamento;

public interface LancamentoService {
	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	void deletar(Lancamento lancamneto);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);

	void atualizarStatus(Lancamento lancamento, StatusLancamento staus);
}
