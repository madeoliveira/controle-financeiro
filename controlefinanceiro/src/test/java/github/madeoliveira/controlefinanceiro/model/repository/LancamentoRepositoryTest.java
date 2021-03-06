package github.madeoliveira.controlefinanceiro.model.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.madeoliveira.controlefinanceiro.enums.StatusLancamento;
import github.madeoliveira.controlefinanceiro.enums.TipoLancamento;
import github.madeoliveira.controlefinanceiro.model.entity.Lancamento;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
	@Autowired
	LancamentoRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveSalvarUmLncamento() {
		Lancamento lancamento = Lancamento.builder().ano(2020).mes(1).descricao("Lançamento teste")
				.valor(BigDecimal.valueOf(10)).tipo(TipoLancamento.RECEITA).status(StatusLancamento.PENDENTE)
				.dataCadastro(LocalDate.now()).build();
		lancamento = repository.save(lancamento);
		assertThat(lancamento.getId()).isNotNull();
	}

	 public static Lancamento criarLancamento() {
		return Lancamento.builder().ano(2020).mes(1).descricao("Lançamento teste").valor(BigDecimal.valueOf(10))
				.tipo(TipoLancamento.RECEITA).status(StatusLancamento.PENDENTE).dataCadastro(LocalDate.now()).build();
	}

	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		repository.delete(lancamento);
		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		assertThat(lancamentoInexistente).isNull();
	}

	private Lancamento criarEPersistirUmLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		return lancamento;
	}

	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamento = criarEPersistirUmLancamento();
		lancamento.setAno(2018);
		lancamento.setDescricao("Teste atualizado");
		lancamento.setStatus(StatusLancamento.CANCELADO);
		repository.save(lancamento);
		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		
		assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
		assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("Teste atualizado");

		assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.CANCELADO);
	}
	
	@Test
	public void deveBuscarUmLAncamentoPorId() {
		Lancamento lancamento = criarEPersistirUmLancamento();
		Optional<Lancamento> lancamentoEncontrado =  repository.findById(lancamento.getId());
		assertThat(lancamentoEncontrado.isPresent()).isTrue();
	}
}
