package ucs.poo.trabalho_eventos.Evento;

import java.util.List;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.HistoricoUsoRecurso;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.Tarefa.TarefaForaDeOrdemException;
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
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
	    sdf.setLenient(false);

	    if (eventoAlvo.getTarefas().isEmpty()) {
	        System.out.println("Nenhuma tarefa cadastrada neste evento.");
	        return;
	    }

	    System.out.println("Tarefas do evento " + eventoAlvo.getNome() + ":");
	    for (Tarefa t : eventoAlvo.getTarefas()) {
	        System.out.println(eventoAlvo.getTarefas().indexOf(t) + " - " + t.getNome());
	    }
	    System.out.println("Selecione o índice da tarefa:");
	    int idxTarefa = Utilitarios.lerInteiroComVerificacao();

	    if (idxTarefa < 0 || idxTarefa >= eventoAlvo.getTarefas().size()) {
	        System.out.println("Índice inválido!");
	        return;
	    }
	    Tarefa tarefaAlvo = eventoAlvo.getTarefas().get(idxTarefa);

	    try {
	        tarefaAlvo.verificarOrdemExecucao();
	    } catch (TarefaForaDeOrdemException e) {
	        System.out.println("Erro: " + e.getMessage());
	        System.out.println("Finalize a tarefa '" + e.getNomeTarefaBloqueante() + "' antes de continuar.");
	        return;
	    }

	    if (tarefaAlvo.getRecursosTarefas().isEmpty()) {
	        System.out.println("Aviso: Esta tarefa não possui recursos vinculados.");
	    }

	    if (ucs.poo.trabalho_eventos.Colaborador.Functions.colaboradoresIsEmpty(empresa)) {
	        System.out.println("Cadastre um colaborador antes de registrar a execução.");
	        return;
	    }

	    ucs.poo.trabalho_eventos.Colaborador.Functions.listarColaboradores(empresa);
	    System.out.print("Digite o ID do colaborador responsável: ");
	    int idColaborador = Utilitarios.lerInteiroComVerificacao();
	    Colaborador colaborador = ucs.poo.trabalho_eventos.Colaborador.Functions.getColaborador(idColaborador, empresa);

	    if (colaborador == null) return;

	    // Datas
	    java.util.Date dataInicio = null;
	    java.util.Date dataFim = null;

	    while (dataInicio == null) {
	        System.out.print("Insira a data e hora de INÍCIO (DD/MM/AAAA HH:MM): ");
	        String strInicio = sc.nextLine();
	        try {
	            dataInicio = sdf.parse(strInicio);
	        } catch (java.text.ParseException e) {
	            System.out.println("Formato inválido! Tente novamente.");
	        }
	    }

	    while (dataFim == null) {
	        System.out.print("Insira a data e hora de TÉRMINO (DD/MM/AAAA HH:MM): ");
	        String strFim = sc.nextLine();
	        try {
	            dataFim = sdf.parse(strFim);
	            if (dataFim.before(dataInicio)) {
	                System.out.println("Erro: A data de término não pode ser anterior à de início!");
	                dataFim = null;
	            }
	        } catch (java.text.ParseException e) {
	            System.out.println("Formato inválido! Tente novamente.");
	        }
	    }

	    for (RecursoTarefa rt : tarefaAlvo.getRecursosTarefas()) {
	        rt.setHoraIni(dataInicio);
	        rt.setHoraFim(dataFim);
	    }

	    tarefaAlvo.registrarExecucaoTarefa(colaborador, dataInicio, dataFim);

	    if (!tarefaAlvo.getRecursosTarefas().isEmpty()) {
	        System.out.println("\n--- AJUSTE FINAL DE RECURSOS UTILIZADOS ---");

	        for (RecursoTarefa rt : tarefaAlvo.getRecursosTarefas()) {
	            Recurso recursoAlocado = rt.getRecurso();

	            Recurso recursoGlobal = null;
	            for (Recurso r : empresa.getRecursosDB()) {
	                if (r.getId() == recursoAlocado.getId()) {
	                    recursoGlobal = r;
	                    break;
	                }
	            }
	            if (recursoGlobal == null) continue;

	            int quantidadeDevolver = -1;
	            while (quantidadeDevolver < 0 || quantidadeDevolver > recursoAlocado.getQuantidade()) {
	                System.out.println("Recurso: " + recursoAlocado.getNome());
	                System.out.println("Quantidade alocada: " + recursoAlocado.getQuantidade());
	                System.out.print("Quantas unidades devem VOLTAR ao estoque? (0 = nenhuma): ");
	                quantidadeDevolver = Utilitarios.lerInteiroComVerificacao();

	                if (quantidadeDevolver < 0 || quantidadeDevolver > recursoAlocado.getQuantidade()) {
	                    System.out.println("Quantidade inválida! Deve ser entre 0 e " + recursoAlocado.getQuantidade());
	                }
	            }

	            int quantidadeConsumida = recursoAlocado.getQuantidade() - quantidadeDevolver;

	            if (quantidadeDevolver > 0) {
	                recursoGlobal.atualizarQuantidade(-quantidadeDevolver);
	                System.out.println(quantidadeDevolver + " unidades de '" + recursoAlocado.getNome() + "' devolvidas.");
	            }

	            recursoAlocado.setQuantidade(quantidadeConsumida);
	            System.out.println(quantidadeConsumida + " unidades de '" + recursoAlocado.getNome() + "' consumidas.");

	            HistoricoUsoRecurso hist = new HistoricoUsoRecurso(
	                recursoGlobal.getId(),
	                recursoGlobal.getNome(),
	                tarefaAlvo.getNome(),
	                eventoAlvo.getNome(),
	                quantidadeConsumida,
	                quantidadeDevolver > 0,
	                dataFim
	            );
	            empresa.getHistoricoUsoRecursos().add(hist);
	        }
	    }

	    System.out.println("\nExecução da tarefa '" + tarefaAlvo.getNome() + "' registrada com sucesso!");
	    sistema.serializarEmpresa(empresa);
	}
	
	
	public static void cadastrarEvento(Empresa empresa, Sistema sistema, List<Evento> eventosDB) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Insira o nome do evento:");
        String nomeEvento = sc.nextLine();
        System.out.println("Insira o tipo do evento:\n"
        		+ "1-Festa\n"
        		+ "2-Formatura\n"
        		+ "3-Evento Corporativo");
        int tipo = Utilitarios.lerInteiroComVerificacao();
        Evento eventoAux = new Evento(nomeEvento, tipo, empresa.getIdAtualEventos());
        
        empresa.setIdAtualEventos(empresa.getIdAtualEventos()+1);
        
        eventosDB.add(eventoAux);
        empresa.setEventos(eventosDB);
        sistema.serializarEmpresa(empresa);
        System.out.println("Evento Cadastrado com sucesso!");
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
		
        if(!"".equals(novoNome)) {
        	eventoAux.setNome(novoNome);
        }
        
        //VER DA VERIFICAÇÂO DE Utilitarios.lerInteiroComVerificacao();
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
        for(Tarefa t : eventoAlvo.getTarefas()) {
        	System.out.println(eventoAlvo.getTarefas().indexOf(t) + " - " + t.getNome());
        	System.out.println("Recursos utilizados na tarefa:");
        	if (t.getRecursosTarefas().isEmpty()) {
        	    System.out.println("  Nenhum recurso vinculado.");
        	} else {
        	    for (RecursoTarefa rt : t.getRecursosTarefas()) {
        	        System.out.println("  - " + rt.getRecurso().getNome() + " | Qtd: " + rt.getRecurso().getQuantidade());
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
