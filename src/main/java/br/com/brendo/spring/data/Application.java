package br.com.brendo.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.brendo.spring.data.service.CrudCargoService;
import br.com.brendo.spring.data.service.CrudFuncionarioService;
import br.com.brendo.spring.data.service.CrudUnidadeTrabalhoService;
import br.com.brendo.spring.data.service.RelatorioFuncionarioDinamico;
import br.com.brendo.spring.data.service.RelatoriosService;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final CrudCargoService cargoService;
	private final CrudUnidadeTrabalhoService unidadeTrabalhoService;
	private final CrudFuncionarioService funcionarioService;
	private final RelatoriosService relatorioService;
	private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamicoService;

	private Boolean system = true;

	public Application(CrudCargoService cargoService, CrudUnidadeTrabalhoService unidadeTrabalhoService,
			CrudFuncionarioService funcionarioService, RelatoriosService relatorioService,
			RelatorioFuncionarioDinamico relatorioFuncionarioDinamicoService) {
		this.cargoService = cargoService;
		this.unidadeTrabalhoService = unidadeTrabalhoService;
		this.funcionarioService = funcionarioService;
		this.relatorioService = relatorioService;
		this.relatorioFuncionarioDinamicoService = relatorioFuncionarioDinamicoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (this.system) {
			System.out.println("Qual ação você quer executar?");
			System.out.println("1 - Ações de Cargo");
			System.out.println("2 - Ações de Unidade de Trabalho");
			System.out.println("3 - Ações de Funcionario");
			System.out.println("4 - Relatorio");
			System.out.println("5 - Relatorio Dinamico");
			System.out.println("0 - Sair\n");

			int action = scanner.nextInt();
			switch (action) {
				case 1:
					this.cargoService.inicial(scanner);
					break;
				case 2:
					this.unidadeTrabalhoService.inicial(scanner);
					break;
				case 3:
					this.funcionarioService.inicial(scanner);
					break;
				case 4:
					this.relatorioService.inicial(scanner);
					break;
				case 5:
					this.relatorioFuncionarioDinamicoService.inicial(scanner);
					break;
				case 0:
					this.system = false;
					break;
				default:
					System.out.println("Não sei o que você quer");
			}
		}

	}

}
