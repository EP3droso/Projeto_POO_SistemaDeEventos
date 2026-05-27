package ucs.poo.trabalho_eventos.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

import ucs.poo.trabalho_eventos.models.Colaborador;
import ucs.poo.trabalho_eventos.models.Empresa;
import ucs.poo.trabalho_eventos.models.Evento;
import ucs.poo.trabalho_eventos.models.EventoCorporativo;
import ucs.poo.trabalho_eventos.models.Festa;
import ucs.poo.trabalho_eventos.models.Formatura;
import ucs.poo.trabalho_eventos.models.Recurso;
import ucs.poo.trabalho_eventos.models.RecursoTarefa;
import ucs.poo.trabalho_eventos.models.Tarefa;

public class Main {
	
	private String nomeEmpresa;
	public List<Tarefa> tarefasDB = new ArrayList<>();
	public List<Recurso> recursosDB = new ArrayList<>();
	
	
	
	private String getNomeEmpresa() {
		return nomeEmpresa;
	}

	private void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	private static void mostrarMenuPrincipal(Main main) {
		System.out.println("MENU de " + main.getNomeEmpresa());
		System.out.println("1 - Cadastro de Evento");
		System.out.println("2 - Menu de Colaboradores");
		System.out.println("3 - Controle de Tarefas");
		System.out.println("4 - Controle de Recursos");
		System.out.println("5 - Menu de Eventos");
		System.out.println("6 - Relatorios");
		System.out.println("0 - Sair");
	}
	
	
	private static void mostrarMenuEventos(){
		System.out.println("---------------------------------------------");
		System.out.println("MENU de Eventos");
		System.out.println("1 - Cadastro de Tarefa no Evento");
		System.out.println("0 - Sair");
	}
	
	private static void mostrarMenuColaboradores() {
	    System.out.println("---------------------------------------------");
	    System.out.println("MENU de Colaboradores");
	    System.out.println("1 - Cadastrar Colaborador");
	    System.out.println("2 - Consultar Colaboradores por nome");
	    System.out.println("3 - Consultar Colaborador por ID");
	    System.out.println("4 - Excluir Colaborador");
	    System.out.println("5 - Alterar Colaborador");
	}
	

	
	private static void login(Main main) {
		Scanner sc = new Scanner(System.in);
		System.out.println("LOGIN");
		System.out.println("Insira o nome de sua Empresa:");
		String nomeEmpresa = sc.nextLine();
		main.setNomeEmpresa(nomeEmpresa);
		System.out.println("Insira seu usuario:");
		String nome = sc.nextLine();
		System.out.println("Insira sua senha:");
		sc.next();
		System.out.println("Seja muito bem vindo " + nome + "!");
	}
	
	
	private static void listarTarefas(Main main) {
	System.out.println("\n---------------------------------------------");
	System.out.println("Lista de Tarefas: ");
		for(Tarefa tarefa : main.tarefasDB) {
			System.out.println(("ID - " + main.tarefasDB.indexOf(tarefa))+ " - " + tarefa.getNome());
		}
		System.out.println("\n");
	}

	private static void listarTarefa(Main main, String nomeTarefa){
		System.out.println("\n---------------------------------------------");
		boolean achou = false;
		for(Tarefa tarefa : main.tarefasDB) {
			if(tarefa.getNome().equalsIgnoreCase(nomeTarefa)) {
				System.out.println(tarefa.getNome() + " - " + tarefa.getPreRequesitos() + " - " + tarefa.getRecursos() );
				achou = true;
			}
		}
		if(!achou)
			System.out.println("Tarefa não cadastrada, ou não encontrada");
		System.out.println("\n");
}


	private static void listarTarefa(Main main, int idTarefa){
		System.out.println("\n---------------------------------------------");
		boolean achou = false;
		for(Tarefa tarefa : main.tarefasDB) {
			if(main.tarefasDB.indexOf(tarefa) == idTarefa ) {
				System.out.println(tarefa.getNome() + " - " + tarefa.getPreRequesitos() + " - " + tarefa.getRecursos() );
				achou = true;
			}
		}
		if(!achou)
			System.out.println("ID não cadastrado, ou não encontrado");
		System.out.println("\n");
	}

	public static void adicionarTarefa(Main main) {
		Scanner sc = new Scanner(System.in);
		boolean loop = true;
		
		System.out.println("Insira o nome da tarefa:");
	    String nomeTarefa = sc.nextLine();
	    			    
	    if(!main.tarefasDB.isEmpty()) {
	        for(Tarefa t1: main.tarefasDB) {
	            if(t1.getNome().equalsIgnoreCase(nomeTarefa)) {
	                System.out.println("Tarefa já criada");
	                loop = false;
	                break;
	            }
	        }

	        if(loop) {
	            Tarefa tarefaAux = new Tarefa(nomeTarefa);
	            
	            while(loop) {
	                java.util.List<Tarefa> disponiveis = new java.util.ArrayList<>();
	                for (Tarefa t1 : main.tarefasDB) {
	                    if (!tarefaAux.getPreRequesitos().contains(t1) && !tarefaAux.verificaCiclo(t1, tarefaAux)) {
	                        disponiveis.add(t1);
	                    }
	                }

	                if (disponiveis.isEmpty()) {
	                    System.out.println("Todas as tarefas existentes já foram adicionadas como pré-requisitos!");
	                    loop = false;
	                    break;
	                }

	                System.out.println("Dentre as tarefas existentes, indique o nome das quais são pre-requisitos (digite 1 por vez) (Digite 0 para finalizar):");
	                int cont = 1;
	                for (Tarefa t1 : disponiveis) {
	                    System.out.println(cont + " - " + t1.getNome());
	                    cont++;
	                }

	                String nomePreRequisito = "";
	                Tarefa tarefaEncontrada = null;
	                boolean achou = false;

	                while (!achou) {
	                    System.out.print("Nome do pré-requisito: ");
	                    nomePreRequisito = sc.nextLine();

	                    if(nomePreRequisito.equals("0")) {
	                        loop = false;
	                        break;        
	                    }
	                    for (Tarefa t1 : disponiveis) {
	                        if (t1.getNome().equalsIgnoreCase(nomePreRequisito)) {
	                            achou = true;
	                            tarefaEncontrada = t1;
	                            break; 
	                        }
	                    }
	                    if (!achou) {
	                        System.out.println("Tarefa inválida, inexistente ou já adicionada! Tente novamente ou digite 0 para encerrar.");
	                    }
	                }
	                
	                if (tarefaEncontrada != null && tarefaAux.setPreRequisito(tarefaEncontrada)) {
	                    System.out.println("Pré-requisito '" + tarefaEncontrada.getNome() + "' adicionado!\n");
	                }
	            }
	            
	            if(!main.recursosDB.isEmpty()) {
	                tarefaAux.registrarRecursos(main.recursosDB);
	            }
	            
	            main.tarefasDB.add(tarefaAux);
	            System.out.println("Tarefa '" + nomeTarefa + "' criada com sucesso!");
	        }
	    } else {
	        Tarefa tarefaAux = new Tarefa(nomeTarefa);
	        if(!main.recursosDB.isEmpty()) {
	            tarefaAux.registrarRecursos(main.recursosDB);
	        }
	        main.tarefasDB.add(tarefaAux);
	        System.out.println("Primeira tarefa '" + nomeTarefa + "' criada com sucesso!");
	    }
	}
	
	public static void registrarTarefas(Main main, Empresa empresa) {
	    Scanner sc = new Scanner(System.in);
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
	    sdf.setLenient(false);
	
	    System.out.println("Selecione a tarefa que deseja registrar a execução:");
	    listarTarefas(main);
	
	    System.out.print("Digite o nome exato da tarefa: ");
	    String nomeTarefa = sc.nextLine();
	
	    Tarefa tarefaAlvo = null;
	    for (Tarefa t : main.tarefasDB) {
	        if (t.getNome().equalsIgnoreCase(nomeTarefa)) {
	            tarefaAlvo = t;
	            break;
	        }
	    }
	
	    if (tarefaAlvo == null) {
	        System.out.println("Tarefa não encontrada!");
	        return;
	    }
	    
	    System.out.println("\nCOLABORADORES CADASTRADOS:");
	    empresa.mostrarColaboradores();

	    System.out.print("Digite o ID do colaborador responsável: ");
	    int idColaborador = Utilitarios.lerInteiroComVerificacao();

	    Colaborador colaborador = empresa.getColaborador(idColaborador);

	    if (tarefaAlvo.getRecursosTarefas().isEmpty()) {
	        System.out.println("Aviso: Esta tarefa não possui recursos vinculados, mas o horário será registrado nos metadados.");
	    }


	    java.util.Date dataInicio = null;
	    java.util.Date dataFim = null;
	
	    while (dataInicio == null) {
	        System.out.print("Insira a data e hora de INÍCIO (formato: DD/MM/AAAA HH:MM): ");
	        String strInicio = sc.nextLine();
	        try {
	            dataInicio = sdf.parse(strInicio);
	        } catch (java.text.ParseException e) {
	            System.out.println("Formato de data/hora inválido! Tente novamente.");
	        }
	    }
	
	    while (dataFim == null) {
	        System.out.print("Insira a data e hora de TÉRMINO (formato: DD/MM/AAAA HH:MM): ");
	        String strFim = sc.nextLine();
	        try {
	            dataFim = sdf.parse(strFim);
	            if (dataFim.before(dataInicio)) {
	                System.out.println("Erro: A data de término não pode ser anterior à data de início!");
	                dataFim = null;
	            }
	        } catch (java.text.ParseException e) {
	            System.out.println("Formato de data/hora inválido! Tente novamente.");
	        }
	    }
	
	    for (RecursoTarefa rt : tarefaAlvo.getRecursosTarefas()) {
	        rt.setHoraIni(dataInicio);
	        rt.setHoraFim(dataFim);
	    }

	    
	    tarefaAlvo.registrarExecucaoTarefa(colaborador, dataInicio, dataFim);
	    
	
	    if (!tarefaAlvo.getRecursosTarefas().isEmpty()) {
	        System.out.println("\n--- AJUSTE FINAL DE RECURSOS UTILIZADOS ---");
	        System.out.println("A tarefa terminou. Indique a quantidade real consumida de cada recurso:");
	        
	        for (RecursoTarefa rt : tarefaAlvo.getRecursosTarefas()) {
	            Recurso recursoAlocado = rt.getRecurso();
	            
	            Recurso recursoGlobal = null;
	            for (Recurso r : main.recursosDB) {
	                if (r.getId() == recursoAlocado.getId()) {
	                    recursoGlobal = r;
	                    break;
	                }
	            }
	            
	            if (recursoGlobal == null) continue;
	            
	            boolean consumoValido = false;
	            int gastoReal = 0;
	            
	            while (!consumoValido) {
	                System.out.println("Recurso: " + recursoAlocado.getNome());
	                System.out.println("Quantidade alocada original: " + recursoAlocado.getQuantidade());
	                System.out.print("Quantidade REALMENTE consumida: ");
	                gastoReal = Utilitarios.lerInteiroComVerificacao();
	                
	                if (gastoReal < 0 || gastoReal > recursoAlocado.getQuantidade()) {
	                    System.out.println("Quantidade inválida! Não pode ser negativa nem maior que a alocada.");
	                } else {
	                    consumoValido = true;
	                }
	            }
	            
	            int sobra = recursoAlocado.getQuantidade() - gastoReal;
	            if (sobra > 0) {
	                recursoGlobal.atualizarQuantidade(-sobra);
	                System.out.println("Foram devolvidas " + sobra + " unidades de '" + recursoAlocado.getNome() + "' ao estoque global.");
	            }
	            recursoAlocado.setQuantidade(gastoReal);
	        }
	    }
	

	    System.out.println("\nExecução da tarefa '" + tarefaAlvo.getNome() + "' registrada com sucesso!");
	}

	public static void alterarTarefa(Main main) {
	    Scanner sc = new Scanner(System.in);
	    
	    if (main.tarefasDB.isEmpty()) {
	        System.out.println("Nenhuma tarefa cadastrada no sistema para alterar.");
	        return;
	    }
	    
	    listarTarefas(main);
	    
	    System.out.print("Digite o nome exato da tarefa que deseja alterar: ");
	    String nomeTarefa = sc.nextLine();
	    
	    Tarefa tarefaAlvo = null;
	    for (Tarefa t : main.tarefasDB) {
	        if (t.getNome().equalsIgnoreCase(nomeTarefa)) {
	            tarefaAlvo = t;
	            break;
	        }
	    }
	    
	    if (tarefaAlvo == null) {
	        System.out.println("Tarefa não encontrada!");
	        return;
	    }
	    
	    int escolha = 10;
	    while (escolha < 0 || escolha > 3) {
	    	System.out.println("---------------------------------------------");
	        System.out.println("\nALTERAR Tarefas\n0 - Voltar ao Menu\n1 - Alterar Nome de Tarefa\n2 - Remover Tarefas como Pré-requisitos\n3 - Adicionar Tarefas como Pré-requisitos");
	        System.out.print("Escolha uma opção: ");
	        escolha = Utilitarios.lerInteiroComVerificacao();
	    }
	    
	    if (escolha == 0) {
	        return;
	    }
	    
	    if (escolha == 1) {
	        System.out.println("Insira o novo nome para a tarefa:");
	        String novoNome = sc.nextLine();
	        
	        boolean jaExiste = false;
	        for (Tarefa t : main.tarefasDB) {
	            if (t.getNome().equalsIgnoreCase(novoNome)) {
	                jaExiste = true;
	                break;
	            }
	        }
	        
	        if (jaExiste) {
	            System.out.println("Erro: Já existe uma tarefa cadastrada com esse nome.");
	        } else {
	            tarefaAlvo.setNome(novoNome);
	            System.out.println("Nome alteredo com sucesso para '" + novoNome + "'!");
	        }
	    }
	    
	    else if (escolha == 2) {
	        if (tarefaAlvo.getPreRequesitos().isEmpty()) {
	            System.out.println("Esta tarefa não possui nenhum pré-requisito associado.");
	            return;
	        }
	        
	        boolean loopRemover = true;
	        while (loopRemover) {
	            if (tarefaAlvo.getPreRequesitos().isEmpty()) {
	                System.out.println("Todos os pré-requisitos foram removidos!");
	                break;
	            }
	            
	            System.out.println("Pré-requisitos atuais desta tarefa:");
	            int cont = 1;
	            for (Tarefa pre : tarefaAlvo.getPreRequesitos()) {
	                System.out.println(cont + ": " + pre.getNome());
	                cont++;
	            }
	            
	            System.out.print("Digite o nome do pré-requisito a remover (ou 0 para finalizar): ");
	            String nomeRemover = sc.nextLine();
	            
	            if (nomeRemover.equals("0")) {
	                loopRemover = false;
	                break;
	            }
	            
	            Tarefa achadaParaRemover = null;
	            for (Tarefa pre : tarefaAlvo.getPreRequesitos()) {
	                if (pre.getNome().equalsIgnoreCase(nomeRemover)) {
	                    achadaParaRemover = pre;
	                    break;
	                }
	            }
	            
	            if (achadaParaRemover != null) {
	                tarefaAlvo.getPreRequesitos().remove(achadaParaRemover); 
	                System.out.println("Pré-requisito '" + achadaParaRemover.getNome() + "' removido com sucesso.\n");
	            } else {
	                System.out.println("Nome inválido ou não faz parte dos pré-requisitos desta tarefa.");
	            }
	        }
	    }
	    
	    else if (escolha == 3) {
	        boolean loopAdicionar = true;
	        while (loopAdicionar) {
	            java.util.List<Tarefa> disponiveis = new java.util.ArrayList<>();
	            for (Tarefa t1 : main.tarefasDB) {
	                if (!t1.equals(tarefaAlvo) && !tarefaAlvo.getPreRequesitos().contains(t1) && !tarefaAlvo.verificaCiclo(t1, tarefaAlvo)) {
	                    disponiveis.add(t1);
	                }
	            }

	            if (disponiveis.isEmpty()) {
	                System.out.println("Não há outras tarefas disponíveis para serem adicionadas como pré-requisitos.");
	                loopAdicionar = false;
	                break;
	            }

	            System.out.println("Tarefas disponíveis para adicionar como pré-requisito:");
	            int cont = 1;
	            for (Tarefa t1 : disponiveis) {
	                System.out.println(cont + ": " + t1.getNome());
	                cont++;
	            }

	            System.out.print("Nome do pré-requisito a adicionar (ou 0 para finalizar): ");
	            String nomePre = sc.nextLine();

	            if (nomePre.equals("0")) {
	                loopAdicionar = false;
	                break;
	            }

	            Tarefa tarefaEncontrada = null;
	            for (Tarefa t1 : disponiveis) {
	                if (t1.getNome().equalsIgnoreCase(nomePre)) {
	                    tarefaEncontrada = t1;
	                    break;
	                }
	            }

	            if (tarefaEncontrada != null && tarefaAlvo.setPreRequisito(tarefaEncontrada)) {
	                System.out.println("Pré-requisito '" + tarefaEncontrada.getNome() + "' adicionado com sucesso!\n");
	            } else {
	                System.out.println("Tarefa inválida, inexistente ou já adicionada!");
	            }
	        }
	    }
	}

	private static void listarRecursos(Main main, String nomeRecurso) {
		System.out.println("\n---------------------------------------------");
		boolean achou = false;
		for(Recurso recurso : main.recursosDB) {
			if(recurso.getNome().equalsIgnoreCase(nomeRecurso)) {
				System.out.println(recurso.getId() + " - " + recurso.getNome() + " - " + recurso.getTipo() + " - " + recurso.getQuantidade() );
				achou = true;
			}
		}
		if(!achou)
			System.out.println("Recurso não cadastrado, ou não encontrado");
		System.out.println("\n");
	}
	
	private static void listarRecursos(Main main, int idRecurso) {
		System.out.println("\n---------------------------------------------");
		boolean achou = false;
		for(Recurso recurso : main.recursosDB) {
			if(recurso.getId() == idRecurso) {
				System.out.println(recurso.getId() + " - " + recurso.getNome() + " - " + recurso.getTipo() + " - " + recurso.getQuantidade() );
				achou = true;
			}
		}
		if(!achou)
			System.out.println("Recurso não cadastrado, ou não encontrado");
		System.out.println("\n");
	}
	
	private static void listarRecursos(Main main) {
		if(main.recursosDB.isEmpty()) {
			return;
		}
		System.out.println("\n---------------------------------------------");
		System.out.println("Lista de Recursos:\n");
		for(Recurso recurso : main.recursosDB) {
				System.out.println(recurso.getId() + " - " + recurso.getNome() + " - " + recurso.getTipo() + " - " + recurso.getQuantidade() );
		}
	}
	
	public static void registrarRecursos(Main main) {
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.println("Selecione a tarefa para a qual deseja registrar recursos:");
	    listarTarefas(main);
	    
	    System.out.print("Digite o nome exato da tarefa: ");
	    String nomeTarefa = sc.nextLine();
	    
	    Tarefa tarefaAlvo = null;
	    for (Tarefa t : main.tarefasDB) {
	        if (t.getNome().equalsIgnoreCase(nomeTarefa)) {
	            tarefaAlvo = t;
	            break;
	        }
	    }
	    
	    if (tarefaAlvo == null) {
	        System.out.println("Tarefa não encontrada!");
	        return;
	    }
	    
	    if (main.recursosDB.isEmpty()) {
	        System.out.println("Não há recursos cadastrados no banco de dados para vincular.");
	        return;
	    }
	    tarefaAlvo.registrarRecursos(main.recursosDB);
	}

	private static void adicionarRecurso(Main main){
		Scanner sc = new Scanner(System.in);
		listarRecursos(main);
		
		System.out.println("Insira o nome do recurso:");
		String nome = sc.nextLine();
		
		boolean jaExiste = false;
		for (Recurso r : main.recursosDB) {
			if (r.getNome().equalsIgnoreCase(nome)) {
				jaExiste = true;
				break;
			}
		}
		
		if (jaExiste) {
			System.out.println("Erro: Já existe um recurso cadastrado com esse nome.");
			return;
		}
		
		System.out.println("Insira o tipo do recurso:");
		String tipo = sc.nextLine();
		
		System.out.println("Insira a quantidade inicial do recurso:");
		int quantidade = Utilitarios.lerInteiroComVerificacao();
		
		if (quantidade < 0) {
			System.out.println("Erro: A quantidade não pode ser negativa.");
			return;
		}
		
		Recurso novoRecurso = new Recurso();
		novoRecurso.setNome(nome);
		novoRecurso.setTipo(tipo);
		novoRecurso.setQuantidade(quantidade);
		
		main.recursosDB.add(novoRecurso);
		System.out.println("Recurso '" + nome + "' (ID: " + novoRecurso.getId() + ") adicionado com sucesso!");
	}
	
	private static void excluirRecurso(Main main){
		Scanner sc = new Scanner(System.in);
		
		if (main.recursosDB.isEmpty()) {
			System.out.println("Nenhum recurso cadastrado no sistema para Excluir.");
			return;
		}
		
		listarRecursos(main);
		System.out.println("Id do Recurso a ser Removido: ");
		int idRecurso = Utilitarios.lerInteiroComVerificacao();
		
		Recurso recursoParaRemover = null;
		for (Recurso r : main.recursosDB) {
			if (r.getId() == idRecurso) {
				recursoParaRemover = r;
				break;
			}
		}
		
		if (recursoParaRemover == null) {
			System.out.println("Recurso com ID " + idRecurso + " não encontrado!");
			return;
		}
		
	    for (Tarefa t : main.tarefasDB) {
	        java.util.List<RecursoTarefa> paraRemover = new java.util.ArrayList<>();
	        for (RecursoTarefa rt : t.getRecursosTarefas()) {
	            if (rt.getRecurso().getId() == idRecurso) {
	                paraRemover.add(rt);
	            }
	        }
	        t.getRecursosTarefas().removeAll(paraRemover);
	    }
		
		
		main.recursosDB.remove(recursoParaRemover);
		System.out.println("Recurso '" + recursoParaRemover.getNome() + "' removido com sucesso!");
	}
	
	private static void alterarRecurso(Main main){
		Scanner sc = new Scanner(System.in);
		
		if (main.recursosDB.isEmpty()) {
			System.out.println("Nenhum recurso cadastrado no sistema para Alterar.");
			return;
		}
		
		listarRecursos(main);
		System.out.println("Id do Recurso a ser alterado: ");
		int idRecurso = Utilitarios.lerInteiroComVerificacao();
		
		Recurso recursoAlvo = null;
		for (Recurso r : main.recursosDB) {
			if (r.getId() == idRecurso) {
				recursoAlvo = r;
				break;
			}
		}
		
		if (recursoAlvo == null) {
			System.out.println("Recurso não encontrado!");
			return;
		}
		
		int escolha = 10;
		while (escolha < 0 || escolha > 3) {
			System.out.println("---------------------------------------------");
			System.out.println("\nALTERAR Recursos \n0 - Voltar ao Menu\n1 - Alterar Nome de Recurso\n2 - Alterar Tipo de Recurso \n3 - Alterar Quantidade");
			System.out.print("Escolha uma opção: ");
			escolha = Utilitarios.lerInteiroComVerificacao();
		}
		
		if(escolha == 0)
			return;
			
		if (escolha == 1) {
			System.out.println("Insira o novo nome para o recurso:");
			String novoNome = sc.nextLine();
			
			boolean jaExiste = false;
			for (Recurso r : main.recursosDB) {
				if (r.getNome().equalsIgnoreCase(novoNome)) {
					jaExiste = true;
					break;
				}
			}
			
			if (jaExiste) {
				System.out.println("Erro: Já existe um recurso cadastrado com esse nome.");
			} else {
				recursoAlvo.setNome(novoNome);
				System.out.println("Nome alterado com sucesso para '" + novoNome + "'!");
			}
		}
		
		if (escolha == 2) {	    	
			System.out.println("Novo tipo para " + recursoAlvo.getNome() + ": ");
			String novoTipo = sc.nextLine();
			recursoAlvo.setTipo(novoTipo);
			System.out.println("Tipo de " + recursoAlvo.getNome() + " alterado para " + recursoAlvo.getTipo());
		}
		
		if (escolha == 3) {
			System.out.println("Quantidade atual: " + recursoAlvo.getQuantidade());
			System.out.println("Insira a nova quantidade total para o recurso:");
			int novaQuantidade = Utilitarios.lerInteiroComVerificacao();
			
			if (novaQuantidade < 0) {
				System.out.println("Erro: A quantidade não pode ser negativa.");
			} else {
				recursoAlvo.setQuantidade(novaQuantidade);
				System.out.println("Quantidade de " + recursoAlvo.getNome() + " alterada com sucesso para " + novaQuantidade);
			}
		}
	}	    	    
	
	public static void excluirTarefa(Main main) {
	    Scanner sc = new Scanner(System.in);
	    
	    if (main.tarefasDB.isEmpty()) {
	        System.out.println("Nenhuma Tarefa cadastrada no sistema para Excluir.");
	        return;
	    }
	    
	    listarTarefas(main);
	    System.out.println("Nome da tarefa a ser Removida: ");
	    String nomeTarefa = sc.nextLine();
	    
	    Tarefa tarefaParaRemover = null;
	    for (Tarefa t : main.tarefasDB) {
	        if (t.getNome().equalsIgnoreCase(nomeTarefa)) {
	            tarefaParaRemover = t;
	            break;
	        }
	    }
	    
	    if (tarefaParaRemover == null) {
	        System.out.println("Tarefa não encontrada!");
	        return;
	    }
	    
	    for (RecursoTarefa rt : tarefaParaRemover.getRecursosTarefas()) {
	        Recurso recursoAlocado = rt.getRecurso();
	        
	        for (Recurso recursoGlobal : main.recursosDB) {
	            if (recursoGlobal.getId() == recursoAlocado.getId()) {
	                recursoGlobal.atualizarQuantidade(-recursoAlocado.getQuantidade());
	                break;
	            }
	        }
	    }
	    
	    for (Tarefa t : main.tarefasDB) {
	        if (!t.equals(tarefaParaRemover)) {
	            t.getPreRequesitos().remove(tarefaParaRemover);
	        }
	    }
	    
	    main.tarefasDB.remove(tarefaParaRemover);
	    System.out.println("Tarefa '" + tarefaParaRemover.getNome() + "' excluída com sucesso! Recursos devolvidos ao estoque e dependências atualizadas.");
	}

	
	
	
	public static void main(String[] args) {
		Main main = new Main();
		Scanner sc = new Scanner(System.in);
		int intEntrada;
		
		
		login(main);
		Empresa empresa = new Empresa(main.getNomeEmpresa());
		
		System.out.println("Seja bem vindo ao menu principal do Sistema de Controle de Eventos (**o menu visual será inserido posteriormente)");

		//LOOP Principal 
		for(;;) {
			mostrarMenuPrincipal(main);
			
			intEntrada = Utilitarios.lerInteiroComVerificacao();
				
			if(intEntrada == 1) {
				System.out.println("Insira o nome do evento:");
				String nome = sc.nextLine();
				
				System.out.println("Insira o tipo do evento:\n1-Festa\n2-Formatura\n3-Evento Corporativo");
				Evento eventoAux;
				int case1Entrada = Utilitarios.lerInteiroComVerificacao();
				
				
				if(case1Entrada == 1) {
					eventoAux = new Festa(nome);
					empresa.cadastrarEvento(eventoAux);
				}
				
				if(case1Entrada == 2) {
					eventoAux = new Formatura(nome);
					empresa.cadastrarEvento(eventoAux);
				}
				
				if(case1Entrada == 3) {
					eventoAux = new EventoCorporativo(nome);
					empresa.cadastrarEvento(eventoAux);
				}
			}
			
			    
			else if(intEntrada == 2) {
				mostrarMenuColaboradores();
			    int escolhaColab = Utilitarios.lerInteiroComVerificacao();
			    if(escolhaColab == 1) {
			    	try {

				        System.out.println("Insira o nome do colaborador:");
				        String nome = sc.nextLine();

				        System.out.println("Insira o email do colaborador:");
				        String email = sc.nextLine();

				        System.out.println("Insira a senha do colaborador:");
				        String senha = sc.nextLine();

				        System.out.println("Insira a função do colaborador:");
				        String funcao = sc.nextLine();
				        
				        Colaborador colaboradorAux = new Colaborador(nome, email, senha, funcao);
				        empresa.cadastrarColaborador(colaboradorAux);

				        System.out.println("Colaborador cadastrado com sucesso!");

				    }
				    catch (IllegalArgumentException e) {

				        System.out.println("ERRO: " + e.getMessage());

				    }
			    }
			    
			    else if(escolhaColab == 2) {
			        empresa.mostrarColaboradores();
			        System.out.println("Informe o nome do colaborador:");
			        String nome = sc.nextLine();
			        empresa.buscarColaboradorPorNome(nome);
			    }
			    
			    else if(escolhaColab == 3) {
			        empresa.mostrarColaboradores();
			        System.out.println("Informe o ID do colaborador:");
			        int id = Utilitarios.lerInteiroComVerificacao();
			        Colaborador colaborador = empresa.getColaborador(id);
			        if(colaborador == null) {
			            System.out.println("Colaborador não encontrado.");
			        }
			        else {
			            System.out.println(colaborador);
			        }
			    }
			    
			    else if(escolhaColab == 4) {
			        empresa.mostrarColaboradores();
			        System.out.println("Informe o ID do colaborador:");
			        int id = Utilitarios.lerInteiroComVerificacao();
			        empresa.excluirColaborador(id);
			    }
			    
			    else if(escolhaColab == 5) {
			        empresa.mostrarColaboradores();
			        System.out.println("Informe o ID do colaborador:");
			        int id = Utilitarios.lerInteiroComVerificacao();
			        sc.nextLine();
			        System.out.println("Novo nome do colaborador:");
			        String novoNome = sc.nextLine();
			        System.out.println("Novo email do colaborador:");
			        String novoEmail = sc.nextLine();
			        System.out.println("Nova senha do colaborador:");
			        String novaSenha = sc.nextLine();
			        System.out.println("Nova função do colaborador:");
			        String novaFuncao = sc.nextLine();
			        empresa.alterarColaborador(id, novoNome, novoEmail, novaSenha, novaFuncao);
			    }
			  
			}		
			
			else if(intEntrada == 3) {
				int escolha=10;
				while(escolha<0 || escolha>=9) {
					System.out.println("\n---------------------------------------------");
					System.out.println("CONTROLE de Tarefas\n0 - Voltar ao Menu\n1 - Adicionar Nova Tarefa\n2 - Alterar infos de Tarefa especifica\n3 - Listar Tarefas\n4 - Registrar Execucao Tarefa\n5 - Registrar Recursos\n6 - Listar Tarefas por nome\n7 - Listar Tarefas por ID\n8 - Apagar Tarefas");
					escolha = Utilitarios.lerInteiroComVerificacao();
				}
				if(escolha==1) {
					adicionarTarefa(main);
				}
				else if(escolha==2) {
					alterarTarefa(main);
				}
				else if(escolha==3) {
					if(main.tarefasDB.isEmpty())
						System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
					else
						listarTarefas(main);
				}
				else if(escolha==4) {
					if(main.tarefasDB.isEmpty())
						System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
					else
						registrarTarefas(main, empresa);
				}
				else if(escolha==5) {
					if(main.tarefasDB.isEmpty())
						System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
					else
						registrarRecursos(main);
				}
				else if(escolha==6) {
					if(main.tarefasDB.isEmpty())
						System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
					else {
						String nomeRecurso;
						System.out.println("Nome da Tarefa: ");
						String nomeTarefa= sc.nextLine();
						listarTarefa(main,nomeTarefa);
					}
				
				}
				else if(escolha==7) {
					if(main.tarefasDB.isEmpty())
						System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
					else{
						int id;
						System.out.println("ID: ");
						id = Utilitarios.lerInteiroComVerificacao();
						listarTarefa(main,id);
					}
				}
				else if(escolha==8) {
					if(main.tarefasDB.isEmpty())
						System.out.println("Nenhuma tarefa cadastrada no sistema para deletar");
					else
						excluirTarefa(main);
				}
			}

			else if(intEntrada == 4) {
				int escolha=10;
				while(escolha<0 || escolha>=7) {
					System.out.println("\n---------------------------------------------");
					System.out.println("CONTROLE de Recursos\n0 - Voltar ao Menu\n1 - Incluir/Adicionar Recursos\n2 - Excluir Recurso\n3 - Alterar Recurso \n4 - Consulta por id\n5 - Consulta por nome\n6 - Consulta todos os Recursos");
					escolha = Utilitarios.lerInteiroComVerificacao();
				}
				
				if(escolha==1) {
					adicionarRecurso(main);
				}
				else if(escolha==2) {
					excluirRecurso(main);
				}
				else if(escolha==3) {
					alterarRecurso(main);
				}
				else if(escolha==4) {
					if(main.recursosDB.isEmpty())
						System.out.println("Nenhum recurso cadastrado no sistema para listar");
					else {
						int id;
						System.out.println("ID: ");
						id = Utilitarios.lerInteiroComVerificacao();
						listarRecursos(main,id);
					}
				}
				else if(escolha==5) {
					if(main.recursosDB.isEmpty())
						System.out.println("Nenhum recurso cadastrado no sistema para listar");
					else {
						String nomeRecurso;
						System.out.println("Nome do Recurso: ");
						nomeRecurso = sc.nextLine();
						listarRecursos(main,nomeRecurso);
					}
				}
				else if(escolha==6) {
					if(main.recursosDB.isEmpty())
						System.out.println("Nenhum recurso cadastrado no sistema para listar");
					else 
						listarRecursos(main);
				}
			}	
			
			else if(intEntrada == 5) {
				if(!empresa.eventosIsEmpty()) {
					
					System.out.println("Selecione o id do evento que deseja entrar");
					empresa.listarEventos();
					int indexEvento = Utilitarios.lerInteiroComVerificacao();
					
					Evento eventoAux = empresa.getEvento(indexEvento);
					if(eventoAux != null){	
						mostrarMenuEventos();
						int case2Entrada = Utilitarios.lerInteiroComVerificacao();
						
						if(case2Entrada == 1) {
							if(main.tarefasDB.isEmpty()) {
								System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
							}else {
								System.out.println("Das tarefas do sistema, qualo id da que você deseja cadastrar ao evento?");
								listarTarefas(main);
								
								int idTarefa = Utilitarios.lerInteiroComVerificacao();
								
								Tarefa tarefaAux = main.tarefasDB.get(idTarefa-1);
								eventoAux.cadastrarTarefa(tarefaAux);
								System.out.println("Tarefas de " + eventoAux.getNome());
								eventoAux.listarTarefas();
							}
						}
						else if(case2Entrada == 2) {
							System.out.println("Retornando ao menu principal");
	
						}
					}
				}
				else{
					System.out.println("Não tem eventos cadastrados na empresa");
				}
			}
			
			
			else if(intEntrada == 6) {
			    System.out.println("---------------------------------------------");
			    System.out.println("RELATÓRIOS\n1 - Relatório de Evento\n2 - Execuções por Período\n3 - Recursos de uma Tarefa\n0 - Voltar");
			    int escolhaRel = Utilitarios.lerInteiroComVerificacao();

			    if(escolhaRel == 1) {
			        empresa.listarEventos();
			        System.out.println("Informe o ID do evento:");
			        int idEvento = Utilitarios.lerInteiroComVerificacao();
			        empresa.gerarRelatorioEvento(idEvento);
			    }
			    else if(escolhaRel == 2) {
			        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
			        try {
			            System.out.println("Data início (dd/MM/yyyy HH:mm):");
			            Date inicio = sdf.parse(sc.nextLine());
			            System.out.println("Data fim (dd/MM/yyyy HH:mm):");
			            Date fim = sdf.parse(sc.nextLine());
			            empresa.gerarRelatorioPeriodo(inicio, fim);
			        } catch(Exception e) {
			            System.out.println("Data inválida.");
			        }
			    }
			    else if(escolhaRel == 3) {
			        System.out.println("Informe o ID da tarefa:");
			        int idTarefa = Utilitarios.lerInteiroComVerificacao();
			        empresa.gerarRelatorioRecursosTarefa(idTarefa);
			    }
			}
			
			
			
			
			
			else if(intEntrada == 0) {
				System.out.println("Saindo...");
				System.exit(0);
			}
			
			else {
				System.out.println("O numero entrado não é uma das opções do menu!");
			}
				
		}
	}

	
}