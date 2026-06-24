package ucs.poo.trabalho_eventos.Tarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                System.out.println(tarefa.getNome() + " - Pré-requisitos: " + tarefa.getPreRequesitos());
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
                System.out.println(tarefa.getNome() + "\n\nPre-Requesitos:\n" + tarefa.getPreRequesitos());
                achou = true;
            }
        }
        if (!achou)
            System.out.println("ID não cadastrado, ou não encontrado");
        System.out.println("\n");
    }

    public static void adicionarTarefa(Empresa empresa, Sistema sistema) {
        String nomeTarefa = Utilitarios.lerStringNaoVazia("Insira o nome da tarefa:");

        for (Tarefa t1 : empresa.getTarefasDB()) {
            if (t1.getNome().equalsIgnoreCase(nomeTarefa)) {
                System.out.println("Tarefa já criada");
                return;
            }
        }

        Tarefa tarefaAux = new Tarefa(nomeTarefa, empresa.getIdAtualTarefas());
        empresa.setIdAtualTarefas(empresa.getIdAtualTarefas() + 1);

        // Seleção de pré-requisitos (somente se já existem outras tarefas).
        boolean loop = !empresa.getTarefasDB().isEmpty();
        while (loop) {
            List<Tarefa> disponiveis = new ArrayList<>();
            for (Tarefa t1 : empresa.getTarefasDB()) {
                if (!tarefaAux.getPreRequesitos().contains(t1) && !tarefaAux.verificaCiclo(t1, tarefaAux)) {
                    disponiveis.add(t1);
                }
            }

            if (disponiveis.isEmpty()) {
                System.out.println("Todas as tarefas existentes já foram adicionadas como pré-requisitos!");
                break;
            }

            System.out.println("Dentre as tarefas existentes, indique quais são pre-requisitos (digite 1 por vez) (Digite Enter para finalizar):");
            for (Tarefa t1 : disponiveis) {
                System.out.println(disponiveis.indexOf(t1) + " - " + t1.getNome());
            }

            int idPreRquisito = Utilitarios.lerInteiroComVerificacao();

            if (idPreRquisito == -1) {
                break;
            }

            Tarefa tarefaEncontrada;
            try {
                tarefaEncontrada = disponiveis.get(idPreRquisito);
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("Tarefa inválida, inexistente ou já adicionada! Tente novamente ou digite Enter para encerrar.");
                continue;
            }

            if (tarefaAux.setPreRequisito(tarefaEncontrada)) {
                System.out.println("Pré-requisito '" + tarefaEncontrada.getNome() + "' adicionado!\n");
            }
        }

        empresa.getTarefasDB().add(tarefaAux);
        System.out.println("Tarefa '" + nomeTarefa + "' criada com sucesso!");
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

        // Remove esta tarefa da lista de pré-requisitos das demais.
        for (Tarefa t : empresa.getTarefasDB()) {
            if (!t.equals(tarefaParaRemover)) {
                t.getPreRequesitos().remove(tarefaParaRemover);
            }
        }

        // Remove o vínculo (e suas execuções/alocações) da tarefa em todos os
        // eventos que a utilizavam, evitando referências inválidas.
        for (ucs.poo.trabalho_eventos.Evento.Evento evento : empresa.getEventos()) {
            evento.removerVinculoTarefa(tarefaParaRemover);
        }

        empresa.getTarefasDB().remove(tarefaParaRemover);
        System.out.println("Tarefa '" + tarefaParaRemover.getNome() + "' excluída com sucesso! Eventos e dependências atualizados.");
        sistema.serializarEmpresa(empresa);
    }
}
