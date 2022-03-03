package br.com.brendo.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.brendo.spring.data.orm.Funcionario;
import br.com.brendo.spring.data.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository
		extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> {
	List<Funcionario> findByNome(String name);

	@Query("SELECT f FROM Funcionario AS f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :data")
	List<Funcionario> findByNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

	@Query(value = "SELECT * FROM funcionarios AS f WHERE f.data_contratacao >= :data", nativeQuery = true)
	List<Funcionario> findDataContratacaoMaior(LocalDate data);

	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios AS f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
}