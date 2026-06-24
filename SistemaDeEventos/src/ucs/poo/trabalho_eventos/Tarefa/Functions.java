package ucs.poo.trabalho_eventos.Tarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.HistoricoUsoRecurso;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Functions {

    public static Tarefa selecionarTarefaPorPosicao(Empresa empresa) {
        if (empresa.getTarefasDB().isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada no sistema.");
            return null;
        }

        System.out.println("Digite parte do nome para filtrar (Enter para listar todas):");
        Scanner sc = new Scanner(System.in);
        String filtro = sc.nextLine();

        System.out.println("\n---------------------------------------------");
        System.out.println("Tarefas encontradas:");

        List<Tarefa> filtradas = new ArrayList<>();
        for (Tarefa t : empresa.getTarefasDB()) {
            if (filtro.isBlank() || t.getNome().toLowerCase().contains(filtro.toLowerCase())) {
                System.out.println(filtradas.size() + " - " + t.getNome());
                filtradas.add(t);
            }
        }

        if (filtradas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com esse filtro.");
            return null;
        }

        System.out.println("Selecione o número da tarefa(Enter para retornar ao menu principal):");
        int posicao = Utilitarios.lerInteiroComVerificacao();
        if(posicao == -1) return null;

        if (posicao < 0 || posicao >= filtradas.size()) {
            System.out.println("Posição inválida!");
            return null;
        }

        return filtradas.get(posicao);
    }

    public static void listarTarefas(Empresa empresa) {
        System.out.println("\n---------------------------------------------");
        System.out.println("Lista de Tarefas: ");
        for (Tarefa tarefa : empresa.getTarefasDB()) {
            System.out.println("ID - " + empresa.getTarefasDB().indexOf(tarefa) + " - " + tarefa.getNome());
        }
        System.out.println("\n");
    }

    public static void listarTarefa(Empresa empresa, String nomeTarefa) {
        System.out.println("\n---------------------------------------------");
        boolean achou = false;
        for (Tarefa tarefa : empresa.getTarefasDB()) {
            if (tarefa.getNome().toLowerCase().contains(nomeTarefa.toLowerCase())) {
                System.out.println(tarefa.getNome() + " - " + tarefa.getPreRequesitos() + " - " + tarefa.getRecursos());
                achou = true;
            }
        }
        if (!achou)
            System.out.println("Tarefa não cadastrada, ou não encontrada");
        System.out.println("\n");
    }

    public static void listarTarefa(Empresa empresa, int idTarefa) {
        System.out.println("\n---------------------------------------------");
        boolean achou = false;
        for (Tarefa tarefa : empresa.getTarefasDB()) {
            if (empresa.getTarefasDB().indexOf(tarefa) == idTarefa) {
                System.out.println(tarefa.getNome() + "\n\nPre-Requesitos:\n" + tarefa.getPreRequesitos() + "\n\nRecursos:\n" + tarefa.getRecursos());
                achou = true;
            }
        }
        if (!achou)
            System.out.println("ID não cadastrado, ou não encontrado");
        System.out.println("\n");
    }

    public static void adicionarTarefa(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
        boolean loop = true;

        System.out.println("Insira o nome da tarefa:");
        String nomeTarefa = sc.nextLine();

        if (!empresa.getTarefasDB().isEmpty()) {
            for (Tarefa t1 : empresa.getTarefasDB()) {
                if (t1.getNome().equalsIgnoreCase(nomeTarefa)) {
                    System.out.println("Tarefa já criada");
                    loop = false;
                    break;
                }
            }

            if (loop) {
            	
                Tarefa tarefaAux = new Tarefa(nomeTarefa,empresa.getIdAtualTarefas());
                empresa.setIdAtualTarefas(empresa.getIdAtualTarefas()+1);

                while (loop) {
                    List<Tarefa> disponiveis = new ArrayList<>();
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

                    System.out.println("Dentre as tarefas existentes, indique quais são pre-requisitos (digite 1 por vez) (Digite Enter para finalizar):");
                    for (Tarefa t1 : disponiveis) {
                        System.out.println(disponiveis.indexOf(t1) + " - " + t1.getNome());
                    }

                    int idPreRquisito;
                    Tarefa tarefaEncontrada = null;
                    boolean achou = false;

                    while((idPreRquisito = Utilitarios.lerInteiroComVerificacao()) != -1) {
                        
                        try {
                        	tarefaEncontrada = disponiveis.get(idPreRquisito);
                        	break;
                        }
                        catch(IndexOutOfBoundsException e) {
                            System.out.println("Tarefa inválida, inexistente ou já adicionada! Tente novamente ou digite Enter para encerrar.");

                        }

                    }

                    if (tarefaAux.setPreRequisito(tarefaEncontrada)) {
                        System.out.println("Pré-requisito '" + tarefaEncontrada.getNome() + "' adicionado!\n");
                    }
                }

                if (empresa.getRecursosDB().isEmpty()) {
                    System.out.println("Nenhum recurso cadastrado no sistema.");
                    System.out.println("Deseja cadastrar um recurso agora? (S para sim, Enter para pular):");
                    String resposta = sc.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        ucs.poo.trabalho_eventos.Recurso.Functions.adicionarRecurso(empresa,sistema);
                    }
                }

                if (!empresa.getRecursosDB().isEmpty()) {
                    System.out.println("Deseja vincular recursos a esta tarefa? (S para sim, Enter para pular):");
                    String resposta = sc.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        Functions.registrarRecursos(tarefaAux, empresa.getRecursosDB(),empresa,sistema);
                    }
                }

                empresa.getTarefasDB().add(tarefaAux);
                System.out.println("Tarefa '" + nomeTarefa + "' criada com sucesso!");
            }
        } else {
            Tarefa tarefaAux = new Tarefa(nomeTarefa,empresa.getIdAtualTarefas());
            empresa.setIdAtualTarefas(empresa.getIdAtualTarefas()+1);
            
            if (empresa.getRecursosDB().isEmpty()) {
                System.out.println("Nenhum recurso cadastrado no sistema.");
                System.out.println("Deseja cadastrar um recurso agora? (S para sim, Enter para pular):");
                String resposta = sc.nextLine();
                if (resposta.equalsIgnoreCase("S")) {
                    ucs.poo.trabalho_eventos.Recurso.Functions.adicionarRecurso(empresa,sistema);
                }
            }

            if (!empresa.getRecursosDB().isEmpty()) {
                System.out.println("Deseja vincular recursos a esta tarefa? (S para sim, Enter para pular):");
                String resposta = sc.nextLine();
                if (resposta.equalsIgnoreCase("S")) {
                    Functions.registrarRecursos(tarefaAux, empresa.getRecursosDB(),empresa,sistema);
                }
            }

            empresa.getTarefasDB().add(tarefaAux);
            System.out.println("Primeira tarefa '" + nomeTarefa + "' criada com sucesso!");
        }
        sistema.serializarEmpresa(empresa);
    }

    public static void alterarTarefa(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);

        if (empresa.getTarefasDB().isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada no sistema para alterar.");
            return;
        }

        Tarefa tarefaAlvo = selecionarTarefaPorPosicao(empresa);
        if (tarefaAlvo == null) return;

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
            System.out.println("Insira o novo nome (Enter para manter '" + tarefaAlvo.getNome() + "'):");
            String novoNome = sc.nextLine();

            if (novoNome.isBlank()) {
                System.out.println("Nome mantido: '" + tarefaAlvo.getNome() + "'");
                return;
            }

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
                System.out.println("Nome alterado para '" + novoNome + "'!");
            }
        } else if (escolha == 2) {
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
        } else if (escolha == 3) {
            boolean loopAdicionar = true;
            while (loopAdicionar) {
                List<Tarefa> disponiveis = new ArrayList<>();
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
        sistema.serializarEmpresa(empresa);
    }
    
    public static void excluirTarefa(Empresa empresa, Sistema sistema) {

        if (empresa.getTarefasDB().isEmpty()) {
            System.out.println("Nenhuma Tarefa cadastrada no sistema para Excluir.");
            return;
        }

        Tarefa tarefaParaRemover = selecionarTarefaPorPosicao(empresa);
        if (tarefaParaRemover == null) return;

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
        sistema.serializarEmpresa(empresa);
    }

    public static void registrarRecursos(Tarefa tarefa, List<Recurso> recursosDisponiveis,Empresa empresa,Sistema sistema) {
        boolean loop = true;
        while (loop) {
            System.out.println("\nDigite o ID do Recurso a ser utilizado/modificado, ou 0 para parar: ");
            for (Recurso r1 : recursosDisponiveis) {
                System.out.println(r1.getId() + ": " + r1.getNome() + " (Em Estoque: " + r1.getQuantidade() + ")");
            }

            int id = Utilitarios.lerInteiroComVerificacao();
            if (id == 0) {
                loop = false;
                break;
            }

            Recurso recursoGlobal = null;
            for (Recurso r1 : recursosDisponiveis) {
                if (r1.getId() == id) {
                    recursoGlobal = r1;
                    break;
                }
            }

            if (recursoGlobal == null) {
                System.out.println("Id inserido é inválido!");
                continue;
            }

            RecursoTarefa vinculoExistente = null;
            for (RecursoTarefa rt : tarefa.getRecursosTarefas()) {
                if (rt.getRecurso().getId() == recursoGlobal.getId()) {
                    vinculoExistente = rt;
                    break;
                }
            }

            if (vinculoExistente != null) {
                int qtdAlocadaAtual = vinculoExistente.getRecurso().getQuantidade();
                System.out.println("Este recurso já está nesta tarefa. Quantidade alocada atual: " + qtdAlocadaAtual);
                System.out.println("Digite a NOVA quantidade total desejada para esta tarefa (Digite 0 para remover e devolver tudo):");
                int novaQtdTarefa = Utilitarios.lerInteiroComVerificacao();

                if (novaQtdTarefa < 0) {
                    System.out.println("Quantidade inválida!");
                    continue;
                }

                int diferenca = novaQtdTarefa - qtdAlocadaAtual;

                if (diferenca > recursoGlobal.getQuantidade()) {
                    System.out.println("Erro: Estoque insuficiente! Você precisa de mais " + (diferenca - recursoGlobal.getQuantidade()) + " unidades.");
                    continue;
                }

                recursoGlobal.atualizarQuantidade(diferenca);

                if (novaQtdTarefa == 0) {
                    tarefa.getRecursosTarefas().remove(vinculoExistente);
                    System.out.println("Recurso removido da tarefa e totalmente devolvido ao estoque.");
                } else {
                    vinculoExistente.getRecurso().setQuantidade(novaQtdTarefa);
                    System.out.println("Quantidade atualizada com sucesso na tarefa!");
                }

            } else {
                if (recursoGlobal.getQuantidade() <= 0) {
                    System.out.println("Erro: Não há estoque disponível para o recurso '" + recursoGlobal.getNome() + "'.");
                    continue;
                }

                boolean quantidadeValida = false;
                int quantidade = 0;

                while (!quantidadeValida) {
                    System.out.println("Quantidade de Recurso '" + recursoGlobal.getNome() + "' a ser usado (máx de: " + recursoGlobal.getQuantidade() + "): ");
                    quantidade = Utilitarios.lerInteiroComVerificacao();

                    if (quantidade > recursoGlobal.getQuantidade() || quantidade <= 0) {
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
        sistema.serializarEmpresa(empresa);
    }

    private static String buscarNomeEventoDaTarefa(Tarefa tarefa, Empresa empresa) {
        for (ucs.poo.trabalho_eventos.Evento.Evento evento : empresa.getEventos()) {
            for (Tarefa t : evento.getTarefas()) {
                if (t.getNome().equalsIgnoreCase(tarefa.getNome())) {
                    return evento.getNome();
                }
            }
        }
        return "Sem evento vinculado";
    }
}

