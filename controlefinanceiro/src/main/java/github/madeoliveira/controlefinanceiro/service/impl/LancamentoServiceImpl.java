package github.madeoliveira.controlefinanceiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.madeoliveira.controlefinanceiro.enums.StatusLancamento;
import github.madeoliveira.controlefinanceiro.model.entity.Lancamento;
import github.madeoliveira.controlefinanceiro.model.repository.LancamentoRepository;
import github.madeoliveira.controlefinanceiro.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	private LancamentoRepository repository;

	public LancamentoServiceImpl(LancamentoRepository repositry) {
		this.repository = repositry;
	}

	@Override
	public Lancamento salvar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Lancamento lancamneto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento staus) {
		// TODO Auto-generated method stub

	}

}
