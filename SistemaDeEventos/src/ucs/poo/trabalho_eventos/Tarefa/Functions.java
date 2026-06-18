package ucs.poo.trabalho_eventos.Tarefa;

import java.util.List;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Functions {
	public static void listarTarefas(Empresa empresa) {
	System.out.println("\n---------------------------------------------");
	System.out.println("Lista de Tarefas: ");
		for(Tarefa tarefa : empresa.getTarefasDB()) {
			System.out.println(("ID - " + empresa.getTarefasDB().indexOf(tarefa))+ " - " + tarefa.getNome());
		}
		System.out.println("\n");
	}

	public static void listarTarefa(Empresa empresa, String nomeTarefa){
		System.out.println("\n---------------------------------------------");
		boolean achou = false;
		for(Tarefa tarefa : empresa.getTarefasDB()) {
			if(tarefa.getNome().equalsIgnoreCase(nomeTarefa)) {
				System.out.println(tarefa.getNome() + " - " + tarefa.getPreRequesitos() + " - " + tarefa.getRecursos() );
				achou = true;
			}
		}
		if(!achou)
			System.out.println("Tarefa não cadastrada, ou não encontrada");
		System.out.println("\n");
	}

	public static void listarTarefa(Empresa empresa, int idTarefa){
		System.out.println("\n---------------------------------------------");
		boolean achou = false;
		for(Tarefa tarefa : empresa.getTarefasDB()) {
			if(empresa.getTarefasDB().indexOf(tarefa) == idTarefa ) {
				System.out.println(tarefa.getNome() + "\n\nPre-Requesitos:\n" + tarefa.getPreRequesitos() + "\n\nRecursos:\n" + tarefa.getRecursos() );
				achou = true;
			}
		}
		if(!achou)
			System.out.println("ID não cadastrado, ou não encontrado");
		System.out.println("\n");
	}

	public static void adicionarTarefa(Empresa empresa) {
		Scanner sc = new Scanner(System.in);
		boolean loop = true;
		
		System.out.println("Insira o nome da tarefa:");
	    String nomeTarefa = sc.nextLine();
	    			    
	    if(!empresa.getTarefasDB().isEmpty()) {
	        for(Tarefa t1: empresa.getTarefasDB()) {
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
	                for (Tarefa t1 : empresa.getTarefasDB()) {
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
	            
	            if(!empresa.getRecursosDB().isEmpty()) {
	                Functions.registrarRecursos(tarefaAux, empresa.getRecursosDB());
	            }
	            
	            empresa.getTarefasDB().add(tarefaAux);
	            System.out.println("Tarefa '" + nomeTarefa + "' criada com sucesso!");
	        }
	    } else {
	        Tarefa tarefaAux = new Tarefa(nomeTarefa);
	        if(!empresa.getRecursosDB().isEmpty()) {
	            Functions.registrarRecursos(tarefaAux, empresa.getRecursosDB());
	        }
	        empresa.getTarefasDB().add(tarefaAux);
	        System.out.println("Primeira tarefa '" + nomeTarefa + "' criada com sucesso!");
	    }
	}
	
	public static void registrarTarefas(Empresa empresa) {
	    Scanner sc = new Scanner(System.in);
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
	    sdf.setLenient(false);
	
	    System.out.println("Selecione a tarefa que deseja registrar a execução:");
	    listarTarefas(empresa);
	
	    System.out.print("Digite o nome exato da tarefa: ");
	    String nomeTarefa = sc.nextLine();
	
	    Tarefa tarefaAlvo = null;
	    for (Tarefa t : empresa.getTarefasDB()) {
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
	    //empresa.mostrarColaboradores();

	    System.out.print("Digite o ID do colaborador responsável: ");
	    int idColaborador = Utilitarios.lerInteiroComVerificacao();
	    
	    
	    Colaborador colaborador = empresa.getColaboradores().get(idColaborador);

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
	            for (Recurso r : empresa.getRecursosDB()) {
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

	public static void alterarTarefa(Empresa empresa) {
	    Scanner sc = new Scanner(System.in);
	    
	    if (empresa.getTarefasDB().isEmpty()) {
	        System.out.println("Nenhuma tarefa cadastrada no sistema para alterar.");
	        return;
	    }
	    
	    listarTarefas(empresa);
	    
	    System.out.print("Digite o nome exato da tarefa que deseja alterar: ");
	    String nomeTarefa = sc.nextLine();
	    
	    Tarefa tarefaAlvo = null;
	    for (Tarefa t : empresa.getTarefasDB()) {
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
	        for (Tarefa t : empresa.getTarefasDB()) {
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
	            for (Tarefa t1 : empresa.getTarefasDB()) {
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
	
	public static void excluirTarefa(Empresa empresa) {
	    Scanner sc = new Scanner(System.in);
	    
	    if (empresa.getTarefasDB().isEmpty()) {
	        System.out.println("Nenhuma Tarefa cadastrada no sistema para Excluir.");
	        return;
	    }
	    
	    listarTarefas(empresa);
	    System.out.println("Nome da tarefa a ser Removida: ");
	    String nomeTarefa = sc.nextLine();
	    
	    Tarefa tarefaParaRemover = null;
	    for (Tarefa t : empresa.getTarefasDB()) {
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
	        
	        for (Recurso recursoGlobal : empresa.getRecursosDB()) {
	            if (recursoGlobal.getId() == recursoAlocado.getId()) {
	                recursoGlobal.atualizarQuantidade(-recursoAlocado.getQuantidade());
	                break;
	            }
	        }
	    }
	    
	    for (Tarefa t : empresa.getTarefasDB()) {
	        if (!t.equals(tarefaParaRemover)) {
	            t.getPreRequesitos().remove(tarefaParaRemover);
	        }
	    }
	    
	    empresa.getTarefasDB().remove(tarefaParaRemover);
	    System.out.println("Tarefa '" + tarefaParaRemover.getNome() + "' excluída com sucesso! Recursos devolvidos ao estoque e dependências atualizadas.");
	}
	
	public static void registrarRecursos(Tarefa tarefa, List<Recurso> recursosDisponiveis) {
	    boolean loop = true;
	    while(loop) {
	        System.out.println("\nDigite o ID do Recurso a ser utilizado/modificado, ou 0 para parar: ");
	        for(Recurso r1 : recursosDisponiveis) {
	            System.out.println(r1.getId() + ": " + r1.getNome() + " (Em Estoque: " + r1.getQuantidade() + ")");
	        }
	        
	        int id = Utilitarios.lerInteiroComVerificacao();
	        if(id == 0) {
	            loop = false;
	            break;
	        }
	        
	        Recurso recursoGlobal = null;
	        for(Recurso r1 : recursosDisponiveis) {
	            if(r1.getId() == id) {
	                recursoGlobal = r1;
	                break;
	            }
	        }
	        
	        if(recursoGlobal == null) {
	            System.out.println("Id inserido é inválido!");
	            continue;
	        }
	        
	        RecursoTarefa vinculoExistente = null;
	        for(RecursoTarefa rt : tarefa.getRecursosTarefas()) {
	            if(rt.getRecurso().getId() == recursoGlobal.getId()) {
	                vinculoExistente = rt;
	                break;
	            }
	        }
	        
	        if(vinculoExistente != null) {
	            int qtdAlocadaAtual = vinculoExistente.getRecurso().getQuantidade();
	            System.out.println("Este recurso já está nesta tarefa. Quantidade alocada atual: " + qtdAlocadaAtual);
	            System.out.println("Digite a NOVA quantidade total desejada para esta tarefa (Digite 0 para remover e devolver tudo):");
	            int novaQtdTarefa = Utilitarios.lerInteiroComVerificacao();
	            
	            if(novaQtdTarefa < 0) {
	                System.out.println("Quantidade inválida!");
	                continue;
	            }
	            
	            int diferenca = novaQtdTarefa - qtdAlocadaAtual;
	            
	            if(diferenca > recursoGlobal.getQuantidade()) {
	                System.out.println("Erro: Estoque insuficiente! Você precisa de mais " + (diferenca - recursoGlobal.getQuantidade()) + " unidades.");
	                continue;
	            }
	            
	            recursoGlobal.atualizarQuantidade(diferenca);
	            
	            if(novaQtdTarefa == 0) {
	            	tarefa.getRecursosTarefas().remove(vinculoExistente);
	                System.out.println("Recurso removido da tarefa e totalmente devolvido ao estoque.");
	            } else {
	                vinculoExistente.getRecurso().setQuantidade(novaQtdTarefa);
	                System.out.println("Quantidade atualizada com sucesso na tarefa!");
	            }
	            
	        } else {
	            if(recursoGlobal.getQuantidade() <= 0) {
	                System.out.println("Erro: Não há estoque disponível para o recurso '" + recursoGlobal.getNome() + "'.");
	                continue;
	            }
	            
	            boolean quantidadeValida = false;
	            int quantidade = 0;
	            
	            while(!quantidadeValida) {
	                System.out.println("Quantidade de Recurso '" + recursoGlobal.getNome() + "' a ser usado (máx de: " + recursoGlobal.getQuantidade() + "): ");
	                quantidade = Utilitarios.lerInteiroComVerificacao();
	                
	                if(quantidade > recursoGlobal.getQuantidade() || quantidade <= 0) {
	                    System.out.println("Quantidade inválida! Tente novamente.");
	                } else {
	                    quantidadeValida = true;
	                }
	            }
	            
	            recursoGlobal.atualizarQuantidade(quantidade);
	            
	            Recurso recursoAux = new Recurso(recursoGlobal.getId(), recursoGlobal.getNome(), recursoGlobal.getTipo(), quantidade);
	            tarefa.getRecursosTarefas().add(new RecursoTarefa(recursoAux, tarefa));
	            System.out.println("Recurso " + recursoGlobal.getNome() + " adicionado com sucesso à tarefa!");
	        }
	    }
	}	


}
