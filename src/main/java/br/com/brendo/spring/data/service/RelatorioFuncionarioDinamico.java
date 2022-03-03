package br.com.brendo.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.brendo.spring.data.orm.Funcionario;
import br.com.brendo.spring.data.repository.FuncionarioRepository;
import br.com.brendo.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {

	private final FuncionarioRepository funcionarioRepository;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		System.out.println("Digite um nome");
		String nome = scanner.next();
		if (nome.equalsIgnoreCase("NULL")) {
			nome = null;
		}

		System.out.println("Digite um CPF");
		String cpf = scanner.next();
		if (cpf.equalsIgnoreCase("NULL")) {
			cpf = null;
		}

		System.out.println("Qual salario deseja buscar?");
		Double salario = scanner.nextDouble();
		if (salario == 0) {
			salario = null;
		}

		System.out.println("Qual data deseja buscar?");
		String data = scanner.next();
		LocalDate localDate;
		if (data.equalsIgnoreCase("NULL")) {
			localDate = null;
		} else {
			localDate = LocalDate.parse(data, formatter);
		}

		List<Funcionario> funcionarios = this.funcionarioRepository.findAll(
				Specification.where(SpecificationFuncionario.nome(nome))
						.or(SpecificationFuncionario.cpf(cpf))
						.or(SpecificationFuncionario.salario(salario))
						.or(SpecificationFuncionario.dataContratacao(localDate)));
		funcionarios.forEach(System.out::println);

	}

}
