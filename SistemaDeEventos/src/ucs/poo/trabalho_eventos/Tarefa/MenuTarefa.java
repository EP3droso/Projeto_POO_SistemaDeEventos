package ucs.poo.trabalho_eventos.Tarefa;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuTarefa {

    public void menuTarefa(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
        int escolha = 10;

        while (escolha < 0 || escolha > 6) {
            System.out.println("\n---------------------------------------------");
            System.out.println("CONTROLE de Tarefas\n"
                    + "0 - Voltar ao Menu\n"
                    + "1 - Adicionar Nova Tarefa\n"
                    + "2 - Alterar infos de Tarefa\n"
                    + "3 - Listar Tarefas\n"
                    + "4 - Listar Tarefas por Nome\n"
                    + "5 - Listar Tarefas por ID\n"
                    + "6 - Apagar Tarefa\n");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) {
            return;
        }

        else if (escolha == 1) {
            Functions.adicionarTarefa(empresa, sistema);
        }

        else if (escolha == 2) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema.");
                return;
            }
            Functions.alterarTarefa(empresa, sistema);
        }

        else if (escolha == 3) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar.");
                return;
            }
            Functions.listarTarefas(empresa);
        }

        else if (escolha == 4) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar.");
                return;
            }
            System.out.println("Nome da Tarefa: ");
            String nomeTarefa = sc.nextLine();
            Functions.listarTarefa(empresa, nomeTarefa);
        }

        else if (escolha == 5) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema para listar.");
                return;
            }
            System.out.println("ID: ");
            int id = Utilitarios.lerInteiroComVerificacao();
            Functions.listarTarefa(empresa, id);
        }

        else if (escolha == 6) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema para deletar.");
                return;
            }
            Functions.excluirTarefa(empresa, sistema);
        }
    }
}
