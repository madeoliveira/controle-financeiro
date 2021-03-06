package github.madeoliveira.controlefinanceiro.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.madeoliveira.controlefinanceiro.enums.StatusLancamento;
import github.madeoliveira.controlefinanceiro.exceptions.RegraNegocioException;
import github.madeoliveira.controlefinanceiro.model.entity.Lancamento;
import github.madeoliveira.controlefinanceiro.model.entity.Usuario;
import github.madeoliveira.controlefinanceiro.model.repository.LancamentoRepository;
import github.madeoliveira.controlefinanceiro.model.repository.LancamentoRepositoryTest;
import github.madeoliveira.controlefinanceiro.service.impl.LancamentoServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@SpyBean
	LancamentoServiceImpl service;
	@MockBean
	LancamentoRepository repository;

	@Test
	public void deveSalvarUmLancamento() {
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
		Mockito.doNothing().when(service).validar(lancamentoASalvar);

		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
		lancamentoSalvo.setId(1l);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
		Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

		Lancamento lancamento = service.salvar(lancamentoASalvar);

		Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
		Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
	}

	@Test
	public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao() {
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
		Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

		Assertions.catchThrowableOfType(() -> service.salvar(lancamentoASalvar), RegraNegocioException.class);

		Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);

	}

	@Test
	public void deveAtualizarUmLancamento() {

		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
		lancamentoSalvo.setId(1l);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

		Mockito.doNothing().when(service).validar(lancamentoSalvo);

		Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

		service.atualizar(lancamentoSalvo);

		Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);

	}

	@Test
	public void deveLancarErroAoTentaAtualizarUmLancamentoQueAindaNaoFoiSalvo() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		Assertions.catchThrowableOfType(() -> service.atualizar(lancamento), NullPointerException.class);
		Mockito.verify(repository, Mockito.never()).save(lancamento);

	}

	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		lancamento.setId(1l);
		service.deletar(lancamento);
		Mockito.verify(repository).delete(lancamento);
	}

	@Test
	public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		Assertions.catchThrowableOfType(() -> service.deletar(lancamento), NullPointerException.class);
		Mockito.verify(repository, Mockito.never()).delete(lancamento);

	}

	@Test
	public void deveFiltrarLancamentos() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		lancamento.setId(1l);
		List<Lancamento> lista = Arrays.asList(lancamento);
		Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

		List<Lancamento> resultado = service.buscar(lancamento);

		Assertions.assertThat(resultado).isNotEmpty().hasSize(1).contains(lancamento);
	}

	@Test
	public void deveAtualizarOStatusDeUmLancamento() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		lancamento.setId(1l);
		lancamento.setStatus(StatusLancamento.PENDENTE);

		StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
		Mockito.doReturn(lancamento).when(service).atualizar(lancamento);
		service.atualizarStatus(lancamento, novoStatus);
		Assertions.assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
		Mockito.verify(service).atualizar(lancamento);
	}

	@Test
	public void deveObterUmLancamentoPorId() {
		Long id = 1l;
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		lancamento.setId(id);

		Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

		Optional<Lancamento> resultado = service.obterPorId(id);
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}
	@Test
	public void deveRetornarVazioQuandoOLancamnetoNaoExistir() {
		Long id = 1l;
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		lancamento.setId(id);

		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

		Optional<Lancamento> resultado = service.obterPorId(id);
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
	@Test
	public void deveLancarErrosAoValidarUmLancamento() {
		Lancamento lancamento = new Lancamento();
		Throwable erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descri????o v??lida! ");
		
		lancamento.setDescricao("");
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descri????o v??lida! ");
		
		lancamento.setDescricao("Salario");
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um M??s v??lido!");
		
		lancamento.setAno(0);
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um M??s v??lido!");
		
		lancamento.setAno(13);
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um M??s v??lido!");
		
		lancamento.setMes(1);
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano v??lido!");
		
		lancamento.setAno(202);
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano v??lido!");
		
		lancamento.setAno(2020);
		
	    erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o Usu??rio!");
		
		lancamento.setUsuario(new Usuario());
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o Usu??rio!");
		
		lancamento.getUsuario().setId(1l);
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Valor v??lido!");
		
		lancamento.setValor(BigDecimal.ZERO);
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Valor v??lido!");
		
		lancamento.setValor(BigDecimal.valueOf(1));
		
		erro =  Assertions.catchThrowable(()-> service.validar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Tipo de Lan??amento!");
		
	}


}
