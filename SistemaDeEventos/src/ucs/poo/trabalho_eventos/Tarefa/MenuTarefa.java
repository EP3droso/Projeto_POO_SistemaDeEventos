package ucs.poo.trabalho_eventos.Tarefa;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuTarefa {

    public void menuTarefa(Empresa empresa, Sistema sistemas) {
        Scanner sc = new Scanner(System.in);
        int escolha = 10;

        while (escolha < 0 || escolha >= 9) {
            System.out.println("\n---------------------------------------------");
            System.out.println("CONTROLE de Tarefas\n0 - Voltar ao Menu\n1 - Adicionar Nova Tarefa\n2 - Alterar infos de Tarefa especifica\n3 - Listar Tarefas\n4 - Registrar Execucao Tarefa\n5 - Registrar Recursos\n6 - Listar Tarefas por nome\n7 - Listar Tarefas por ID\n8 - Apagar Tarefas");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 1) {
            Functions.adicionarTarefa(empresa);
        }
        else if (escolha == 2) {
            Functions.alterarTarefa(empresa);
        }
        else if (escolha == 3) {
            if (empresa.getTarefasDB().isEmpty())
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
            else
                Functions.listarTarefas(empresa);
        }
        else if (escolha == 4) {
            if (empresa.getTarefasDB().isEmpty())
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
            else
                Functions.registrarTarefas(empresa);
        }
        else if (escolha == 5) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
            } else {
                Functions.listarTarefas(empresa);
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
                } else if (empresa.getRecursosDB().isEmpty()) {
                    System.out.println("Nenhum recurso cadastrado no sistema.");
                } else {
                    Functions.registrarRecursos(tarefaAlvo, empresa.getRecursosDB());
                }
            }
        }
        else if (escolha == 6) {
            if (empresa.getTarefasDB().isEmpty())
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
            else {
                System.out.println("Nome da Tarefa: ");
                String nomeTarefa = sc.nextLine();
                Functions.listarTarefa(empresa, nomeTarefa);
            }
        }
        else if (escolha == 7) {
            if (empresa.getTarefasDB().isEmpty())
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar");
            else {
                System.out.println("ID: ");
                int id = Utilitarios.lerInteiroComVerificacao();
                Functions.listarTarefa(empresa, id);
            }
        }
        else if (escolha == 8) {
            if (empresa.getTarefasDB().isEmpty())
                System.out.println("Nenhuma tarefa cadastrada no sistema para deletar");
            else
                Functions.excluirTarefa(empresa);
        }
    }
}