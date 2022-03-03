package br.com.brendo.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.brendo.spring.data.orm.Funcionario;
import br.com.brendo.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	private final FuncionarioRepository funcionarioRepository;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		this.system = true;
		while (system) {
			System.out.println("Qual acao de relatório deseja executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca Funcionario");
			System.out.println("2 - Buscar Funcionaro por nome salario maior na data de contratação");
			System.out.println("3 - Buscar Funcionaro por data de contratação maior ou igual");

			int action = scanner.nextInt();

			switch (action) {
				case 1:
					buscaFuncionarioPorNome(scanner);
					break;
				case 2:
					buscaFuncionarioPorNomeSalarioMaiorDataContratacao(scanner);
					break;
				case 3:
					buscaFuncionarioPorDataContratacaoMaior(scanner);
					break;

				default:
					system = false;
					break;
			}

		}

	}

	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.println("Qual nome deseja buscar?");
		String nome = scanner.next();

		List<Funcionario> funcionarios = this.funcionarioRepository.findByNome(nome);
		funcionarios.forEach(System.out::println);
	}

	private void buscaFuncionarioPorNomeSalarioMaiorDataContratacao(Scanner scanner) {
		System.out.println("Qual nome deseja buscar?");
		String nome = scanner.next();

		System.out.println("Salario deve ser maior ou igual a?");
		Double salario = scanner.nextDouble();

		System.out.println("Qual a data?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);

		List<Funcionario> funcionarios = this.funcionarioRepository.findByNomeSalarioMaiorDataContratacao(nome, salario,
				localDate);
		funcionarios.forEach(System.out::println);
	}

	private void buscaFuncionarioPorDataContratacaoMaior(Scanner scanner) {
		System.out.println("Qual a data?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);

		List<Funcionario> funcionarios = this.funcionarioRepository.findDataContratacaoMaior(localDate);
		funcionarios.forEach(System.out::println);
	}
}
