package br.com.brendo.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.brendo.spring.data.orm.Cargo;
import br.com.brendo.spring.data.orm.Funcionario;
import br.com.brendo.spring.data.orm.FuncionarioProjecao;
import br.com.brendo.spring.data.orm.UnidadeTrabalho;
import br.com.brendo.spring.data.repository.CargoRepository;
import br.com.brendo.spring.data.repository.FuncionarioRepository;
import br.com.brendo.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final CargoRepository cargoRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
			UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.cargoRepository = cargoRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void inicial(Scanner scanner) {
		this.system = true;
		while (system) {
			System.out.println("Qual acao de cargo deseja executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Visualizar Projetado");
			System.out.println("5 - Deletar");

			int action = scanner.nextInt();

			switch (action) {
				case 1:
					salvar(scanner);
					break;
				case 2:
					atualizar(scanner);
					break;
				case 3:
					visualizar(scanner);
					break;
				case 4:
					buscaFuncionarioSalarioProjecao();
					break;
				case 5:
					deletar(scanner);
					break;
				default:
					system = false;
					break;
			}

		}

	}

	private void salvar(Scanner scanner) {
		System.out.println("Digite o nome");
		String nome = scanner.next();

		System.out.println("Digite o cpf");
		String cpf = scanner.next();

		System.out.println("Digite o salario");
		Double salario = scanner.nextDouble();

		System.out.println("Digite a data de contracao");
		String dataContratacao = scanner.next();

		System.out.println("Digite o cargoId");
		Integer cargoId = scanner.nextInt();

		List<UnidadeTrabalho> unidades = unidade(scanner);

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);

		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");
	}

	private List<UnidadeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();

		while (isTrue) {
			System.out.println("Digite o unidadeId (Para sair digite 0)");
			Integer unidadeId = scanner.nextInt();

			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}

		return unidades;
	}

	private void atualizar(Scanner scanner) {
		System.out.println("Digite o id");
		Integer id = scanner.nextInt();

		System.out.println("Digite o nome");
		String nome = scanner.next();

		System.out.println("Digite o cpf");
		String cpf = scanner.next();

		System.out.println("Digite o salario");
		Double salario = scanner.nextDouble();

		System.out.println("Digite a data de contracao");
		String dataContratacao = scanner.next();

		System.out.println("Digite o cargoId");
		Integer cargoId = scanner.nextInt();

		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());

		funcionarioRepository.save(funcionario);
		System.out.println("Alterado");
	}

	private void visualizar(Scanner scanner) {
		System.out.println("Qual página deseja visualizar?");
		Integer pagina = scanner.nextInt();

		if (pagina <= 0) {
			pagina = 1;
		}

		Pageable pageable = PageRequest.of(pagina - 1, 1, Sort.by(Sort.Direction.DESC, "nome"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

		System.out.println("Página atual " + (funcionarios.getNumber() + 1));
		System.out.println("Página total paginas " + funcionarios.getTotalPages());
		System.out.println("Página total elementos " + funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}

	private void buscaFuncionarioSalarioProjecao() {
		List<FuncionarioProjecao> funcionarios = funcionarioRepository.findFuncionarioSalario();
		funcionarios.forEach(funcionario -> System.out
				.println(funcionario.getId() + "\t" + funcionario.getNome() + "\t\t\t" + funcionario.getSalario()));
	}

	private void deletar(Scanner scanner) {
		System.out.println("Id");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Deletado");
	}

}
