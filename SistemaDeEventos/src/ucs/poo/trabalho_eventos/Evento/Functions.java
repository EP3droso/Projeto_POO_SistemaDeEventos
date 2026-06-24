package ucs.poo.trabalho_eventos.Evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.EventoTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.HistoricoUsoRecurso;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Functions {

	public static boolean eventosIsEmpty(Empresa empresa) {
		if(empresa.getEventos().isEmpty()) {
			System.out.println("Nenhum evento cadastrado.");
			return true;
		}
		return false;
	}

	public static void registrarTarefas(Evento eventoAlvo, Empresa empresa, Sistema sistema) {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf.setLenient(false);

		List<EventoTarefa> ets = eventoAlvo.getEventoTarefas();
		if (ets.isEmpty()) {
			System.out.println("Nenhuma tarefa cadastrada neste evento.");
			return;
		}

		System.out.println("Tarefas do evento " + eventoAlvo.getNome() + ":");
		for (int i = 0; i < ets.size(); i++) {
			EventoTarefa et = ets.get(i);
			System.out.println(i + " - " + et.getTarefa().getNome() + (et.foiExecutada() ? " [executada]" : ""));
		}
		System.out.println("Selecione o índice da tarefa (Enter para voltar):");
		int idx = Utilitarios.lerInteiroComVerificacao();
		if (idx < 0 || idx >= ets.size()) {
			System.out.println("Índice inválido!");
			return;
		}
		EventoTarefa etAlvo = ets.get(idx);

		if (etAlvo.foiExecutada()) {
			System.out.println("Esta tarefa já foi executada neste evento. Deseja refazer a execução? (S para sim, Enter para cancelar):");
			String resp = sc.nextLine();
			if (!resp.equalsIgnoreCase("S")) return;
			etAlvo.limparExecucao();
		}

		Date dataInicio = null;
		while (dataInicio == null) {
			System.out.print("Insira a data e hora de INÍCIO (DD/MM/AAAA HH:MM): ");
			try {
				dataInicio = sdf.parse(sc.nextLine());
			} catch (ParseException e) {
				System.out.println("Formato inválido! Tente novamente.");
			}
		}
		Date dataFim = null;
		while (dataFim == null) {
			System.out.print("Insira a data e hora de TÉRMINO (DD/MM/AAAA HH:MM): ");
			try {
				dataFim = sdf.parse(sc.nextLine());
				if (!dataFim.after(dataInicio)) {
					System.out.println("Erro: o término deve ser posterior ao início!");
					dataFim = null;
				}
			} catch (ParseException e) {
				System.out.println("Formato inválido! Tente novamente.");
			}
		}

		String bloqueio = verificarPreRequisitosNoEvento(eventoAlvo, etAlvo, dataInicio);
		if (bloqueio != null) {
			System.out.println("Erro: " + bloqueio);
			return;
		}

		if (ucs.poo.trabalho_eventos.Colaborador.Functions.colaboradoresIsEmpty(empresa)) {
			System.out.println("Cadastre um colaborador antes de registrar a execução.");
			return;
		}
		System.out.println("\n--- ALOCAÇÃO DE COLABORADORES ---");
		boolean loopColab = true;
		while (loopColab) {
			ucs.poo.trabalho_eventos.Colaborador.Functions.listarColaboradores(empresa);
			System.out.println("Digite o ID do colaborador a alocar (Enter para finalizar):");
			int idc = Utilitarios.lerInteiroComVerificacao();
			if (idc == -1) break;

			Colaborador c = ucs.poo.trabalho_eventos.Colaborador.Functions.getColaborador(idc, empresa);
			if (c == null) continue;

			if (jaTemColaborador(etAlvo, c)) {
				System.out.println("Colaborador já alocado nesta tarefa.");
				continue;
			}

			EventoTarefa conf = conflitoColaborador(empresa, c, dataInicio, dataFim, etAlvo);
			if (conf != null) {
				System.out.println("Conflito: '" + c.getNome() + "' já está na tarefa '" + conf.getTarefa().getNome()
						+ "' do evento '" + conf.getEvento().getNome() + "' nesse mesmo horário.");
				continue;
			}

			etAlvo.getColaboradoresTarefas().add(new ColaboradorTarefa(c));
			System.out.println("Colaborador '" + c.getNome() + "' alocado!");
		}

		if (etAlvo.getColaboradoresTarefas().isEmpty()) {
			System.out.println("Nenhum colaborador alocado. Execução cancelada.");
			etAlvo.limparExecucao();
			return;
		}

		System.out.println("\n--- ALOCAÇÃO DE RECURSOS ---");
		if (empresa.getRecursosDB().isEmpty()) {
			System.out.println("Nenhum recurso cadastrado. Pulando alocação de recursos.");
		} else {
			boolean loopRec = true;
			while (loopRec) {
				System.out.println("Recursos (capacidade | disponível neste horário):");
				for (Recurso r : empresa.getRecursosDB()) {
					int disp = r.getQuantidade() - quantidadeReservada(empresa, r, dataInicio, dataFim, etAlvo);
					System.out.println(r.getId() + ": " + r.getNome() + " | capacidade " + r.getQuantidade() + " | disponível " + disp);
				}
				System.out.println("Digite o ID do recurso a alocar (Enter para finalizar):");
				int idr = Utilitarios.lerInteiroComVerificacao();
				if (idr == -1) break;

				Recurso r = buscarRecurso(empresa, idr);
				if (r == null) {
					System.out.println("Recurso inválido!");
					continue;
				}
				if (jaTemRecurso(etAlvo, r)) {
					System.out.println("Recurso já alocado nesta tarefa.");
					continue;
				}

				int disp = r.getQuantidade() - quantidadeReservada(empresa, r, dataInicio, dataFim, etAlvo);
				if (disp <= 0) {
					System.out.println("Sem disponibilidade deste recurso nesse horário.");
					continue;
				}

				System.out.println("Quantidade desejada (máx " + disp + "):");
				int q = Utilitarios.lerInteiroComVerificacao();
				if (q <= 0) {
					System.out.println("Quantidade inválida!");
					continue;
				}
				if (q > disp) {
					System.out.println("Conflito: só há " + disp + " unidade(s) disponível(eis) nesse horário.");
					continue;
				}

				etAlvo.getRecursosTarefas().add(new RecursoTarefa(r, etAlvo.getTarefa(), q));
				System.out.println(q + " unidade(s) de '" + r.getNome() + "' alocada(s)!");
			}
		}

		etAlvo.setHoraIni(dataInicio);
		etAlvo.setHoraFim(dataFim);

		if (!etAlvo.getRecursosTarefas().isEmpty()) {
			System.out.println("\n--- DEVOLUÇÃO DE RECURSOS AO ESTOQUE ---");
		}
		for (RecursoTarefa rt : etAlvo.getRecursosTarefas()) {
			Recurso recurso = rt.getRecurso();
			int usado = rt.getQuantidade();

			int devolver = -1;
			while (devolver < 0 || devolver > usado) {
				System.out.println("Recurso '" + recurso.getNome() + "': foram usadas " + usado + " unidade(s) nesta execução.");
				System.out.println("Quantas unidades voltam ao estoque? (0 = consumir todas, " + usado + " = devolver todas):");
				devolver = Utilitarios.lerInteiroComVerificacao();
				if (devolver < 0 || devolver > usado) {
					System.out.println("Quantidade inválida! Deve ser entre 0 e " + usado + ".");
				}
			}

			int consumido = usado - devolver;
			if (consumido > 0) {
				recurso.setQuantidade(recurso.getQuantidade() - consumido);
				System.out.println(consumido + " unidade(s) de '" + recurso.getNome() + "' consumida(s) definitivamente.");
			}
			if (devolver > 0) {
				System.out.println(devolver + " unidade(s) de '" + recurso.getNome() + "' devolvida(s) ao estoque.");
			}

			HistoricoUsoRecurso hist = new HistoricoUsoRecurso(
				recurso.getId(),
				recurso.getNome(),
				etAlvo.getTarefa().getNome(),
				eventoAlvo.getNome(),
				usado,
				consumido == 0,
				dataFim
			);
			empresa.getHistoricoUsoRecursos().add(hist);
		}

		System.out.println("\nExecução da tarefa '" + etAlvo.getTarefa().getNome() + "' registrada com sucesso!");
		sistema.serializarEmpresa(empresa);
	}


	private static boolean sobrepoe(Date aIni, Date aFim, Date bIni, Date bFim) {
		if (aIni == null || aFim == null || bIni == null || bFim == null) return false;
		return aIni.before(bFim) && bIni.before(aFim);
	}

	private static boolean jaTemColaborador(EventoTarefa et, Colaborador c) {
		for (ColaboradorTarefa ct : et.getColaboradoresTarefas()) {
			if (ct.getColaborador() != null && ct.getColaborador().getId() == c.getId()) return true;
		}
		return false;
	}

	private static boolean jaTemRecurso(EventoTarefa et, Recurso r) {
		for (RecursoTarefa rt : et.getRecursosTarefas()) {
			if (rt.getRecurso() != null && rt.getRecurso().getId() == r.getId()) return true;
		}
		return false;
	}

	private static Recurso buscarRecurso(Empresa empresa, int id) {
		for (Recurso r : empresa.getRecursosDB()) {
			if (r.getId() == id) return r;
		}
		return null;
	}

	private static EventoTarefa conflitoColaborador(Empresa empresa, Colaborador c, Date ini, Date fim, EventoTarefa etAtual) {
		for (Evento ev : empresa.getEventos()) {
			for (EventoTarefa et : ev.getEventoTarefas()) {
				if (et == etAtual || !et.foiExecutada()) continue;
				if (!sobrepoe(ini, fim, et.getHoraIni(), et.getHoraFim())) continue;
				for (ColaboradorTarefa ct : et.getColaboradoresTarefas()) {
					if (ct.getColaborador() != null && ct.getColaborador().getId() == c.getId()) {
						return et;
					}
				}
			}
		}
		return null;
	}

	private static int quantidadeReservada(Empresa empresa, Recurso r, Date ini, Date fim, EventoTarefa etAtual) {
		int total = 0;
		for (Evento ev : empresa.getEventos()) {
			for (EventoTarefa et : ev.getEventoTarefas()) {
				if (et == etAtual || !et.foiExecutada()) continue;
				if (!sobrepoe(ini, fim, et.getHoraIni(), et.getHoraFim())) continue;
				for (RecursoTarefa rt : et.getRecursosTarefas()) {
					if (rt.getRecurso() != null && rt.getRecurso().getId() == r.getId()) {
						total += rt.getQuantidade();
					}
				}
			}
		}
		return total;
	}

	private static String verificarPreRequisitosNoEvento(Evento ev, EventoTarefa etAlvo, Date inicio) {
		for (Tarefa pre : etAlvo.getTarefa().getPreRequesitos()) {
			EventoTarefa preEt = null;
			for (EventoTarefa et : ev.getEventoTarefas()) {
				if (et.getTarefa() != null && et.getTarefa().equals(pre)) {
					preEt = et;
					break;
				}
			}
			if (preEt == null) {
				return "o pré-requisito '" + pre.getNome() + "' não está cadastrado neste evento.";
			}
			if (!preEt.foiExecutada()) {
				return "o pré-requisito '" + pre.getNome() + "' ainda não foi executado neste evento.";
			}
			if (preEt.getHoraFim().after(inicio)) {
				return "o pré-requisito '" + pre.getNome() + "' deve terminar antes do início desta tarefa.";
			}
		}
		return null;
	}

	public static List<Tarefa> pesquisarTarefas(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o nome da tarefa(pode ser digitado somente parte do nome):");
		String filtro = sc.nextLine();

		List<Tarefa> filtradas = new ArrayList<>();
		for (Tarefa t : empresa.getTarefasDB()) {
			if (filtro.isBlank() || t.getNome().toLowerCase().contains(filtro.toLowerCase())) {
				System.out.println(filtradas.size() + " - " + t.getNome());
				filtradas.add(t);
			}
		}
		if (filtradas.isEmpty()) {
			System.out.println("Nenhuma tarefa encontrada com esse filtro.");
		}
		return filtradas;
	}


	public static void cadastrarEvento(Empresa empresa, Sistema sistema, List<Evento> eventosDB) {
		String nomeEvento = Utilitarios.lerStringNaoVazia("Insira o nome do evento:");
		System.out.println("Insira o tipo do evento:\n"
				+ "1-Festa\n"
				+ "2-Formatura\n"
				+ "3-Evento Corporativo");
		int tipo = Utilitarios.lerInteiroComVerificacao();
		Evento eventoAux = new Evento(nomeEvento, tipo, empresa.getIdAtualEventos());

		if(tipo >=1 && tipo<=3) {
			empresa.setIdAtualEventos(empresa.getIdAtualEventos()+1);
			eventosDB.add(eventoAux);
			empresa.setEventos(eventosDB);
			sistema.serializarEmpresa(empresa);
			System.out.println("Evento Cadastrado com sucesso!");
		}
		else {
			System.out.println("Erro ao cadastrar Evento");
		}
		
	}

	public static  void listarEventos(Empresa empresa) {
		System.out.println("Os eventos cadastrados são:");
		for(Evento evento : empresa.getEventos()){
			System.out.println(empresa.getEventos().indexOf(evento) +" - " + evento.getNome());
		}
	}

	public static Evento getEvento(int index, Empresa empresa){
		try {
			return empresa.getEventos().get(index);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Não existe um evento nesse index");
			return null;
		}
	}

	public static  Evento getEvento(String nome, Empresa empresa){
		for(Evento aux : empresa.getEventos()) {
			if(nome.equals(aux.getNome())) {
				return aux;
			}
		}
		System.out.println("Não existe um evento com esse nome");
		return null;
	}

	public static void excluirEvento(int index, Empresa empresa, Sistema sistema) {
		Evento eventoAux = getEvento(index,empresa);
		if(eventoAux != null) {
			empresa.getEventos().remove(eventoAux);
		}
		sistema.serializarEmpresa(empresa);
	}

	public static void alterarEvento(Evento eventoAux,Empresa empresa,Sistema sistema) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Digite o novo nome do evento:");
		String novoNome = sc.nextLine();
		System.out.println("Insira o novo do evento:\n"
				+ "1-Festa\n"
				+ "2-Formatura\n"
				+ "3-Evento Corporativo");

		int novoTipo =0;
		String novoTipoString = sc.nextLine();

		if(!novoNome.isBlank()) {
			eventoAux.setNome(novoNome);
		}

		if(!"".equals(novoTipoString)) {
			try {
				novoTipo = Integer.parseInt(novoTipoString);
			}
			catch(NumberFormatException e){
			}

			if(novoTipo==1) {
				eventoAux.setTipo("Festa");
			}else if(novoTipo==2){
				eventoAux.setTipo("Formatura");
			}else if(novoTipo==3){
				eventoAux.setTipo("Evento Corporativo");
			}
		}

		sistema.serializarEmpresa(empresa);
	}

	public static void mostrarInfoEvento(Evento eventoAlvo, Empresa empresa) {
		System.out.println("|---------------------------------------------------------------------------|");
		System.out.println(eventoAlvo.getNome() + ":" );
		System.out.println("->" + eventoAlvo.getTipo());
		System.out.println("Tarefas:");

		List<EventoTarefa> ets = eventoAlvo.getEventoTarefas();
		if (ets.isEmpty()) {
			System.out.println("  Nenhuma tarefa neste evento.");
		}
		for (int i = 0; i < ets.size(); i++) {
			EventoTarefa et = ets.get(i);
			String exec = et.foiExecutada()
					? " [executada: " + et.getHoraIni() + " -> " + et.getHoraFim() + "]"
					: " [não executada]";
			System.out.println(i + " - " + et.getTarefa().getNome() + exec);

			if (!et.getColaboradoresTarefas().isEmpty()) {
				System.out.println("    Colaboradores:");
				for (ColaboradorTarefa ct : et.getColaboradoresTarefas()) {
					System.out.println("      - " + ct.getColaborador().getNome());
				}
			}

			if (et.getRecursosTarefas().isEmpty()) {
				System.out.println("    Recursos: nenhum");
			} else {
				System.out.println("    Recursos:");
				for (RecursoTarefa rt : et.getRecursosTarefas()) {
					System.out.println("      - " + rt.getRecurso().getNome() + " | Qtd: " + rt.getQuantidade());
				}
			}
		}
	}

	public static void pesquisaPorContem(Empresa empresa) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Digite o nome do evento(pode ser digitado somente parte do nome):");
		String linhaContem = sc.nextLine();

		for(Evento evento : empresa.getEventos()){
			if(evento.getNome().contains(linhaContem)) {
				System.out.println(empresa.getEventos().indexOf(evento) +" - " + evento.getNome());
			}
		}
	}

}
