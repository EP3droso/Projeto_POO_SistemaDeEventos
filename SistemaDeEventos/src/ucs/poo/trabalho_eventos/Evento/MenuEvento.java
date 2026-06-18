package ucs.poo.trabalho_eventos.Evento;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Utilitarios;
import ucs.poo.trabalho_eventos.main.Sistema;

public class MenuEvento {

    public void menuEvento(Empresa empresa, Sistema sistema) {
    	/*
        Scanner sc = new Scanner(System.in);
        int escolha = 10;

        while (escolha < 0 || escolha >= 6) {
            System.out.println("\n---------------------------------------------");
            System.out.println("MENU de Eventos\n0 - Voltar ao Menu\n1 - Cadastrar Evento\n2 - Cadastrar Tarefa no Evento\n3 - Excluir Evento\n4 - Alterar nome do Evento\n5 - Excluir Tarefa do Evento");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) {
            return;
        }
        else if (escolha == 1) {
        	
            System.out.println("Insira o nome do evento:");
            String nomeEvento = sc.nextLine();
            System.out.println("Insira o tipo do evento:\n1-Festa\n2-Formatura\n3-Evento Corporativo");
            int tipo = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAux = new Evento(nomeEvento, tipo);
            empresa.cadastrarEvento(eventoAux);
        }
        else if (escolha == 2) {
            if (empresa.eventosIsEmpty()) {
                System.out.println("Nenhum evento cadastrado.");
                return;
            }
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema.");
                return;
            }
            empresa.listarEventos();
            System.out.println("Selecione o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = empresa.getEvento(idEvento);
            if (eventoAlvo == null) return;

            System.out.println("Tarefas disponíveis:");
            for (Tarefa t : empresa.getTarefasDB()) {
                System.out.println(empresa.getTarefasDB().indexOf(t) + " - " + t.getNome());
            }
            System.out.println("ID da tarefa a cadastrar no evento:");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();
            Tarefa tarefaAux = empresa.getTarefasDB().get(idTarefa);
            eventoAlvo.cadastrarTarefa(tarefaAux);
            System.out.println("Tarefas de " + eventoAlvo.getNome() + ":");
            eventoAlvo.listarTarefas();
        }
        else if (escolha == 3) {
            if (empresa.eventosIsEmpty()) {
                System.out.println("Nenhum evento cadastrado.");
                return;
            }
            empresa.listarEventos();
            System.out.println("Selecione o ID do evento a excluir:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            empresa.excluirEvento(idEvento);
        }
        else if (escolha == 4) {
            if (empresa.eventosIsEmpty()) {
                System.out.println("Nenhum evento cadastrado.");
                return;
            }
            empresa.listarEventos();
            System.out.println("Selecione o ID do evento a alterar:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = empresa.getEvento(idEvento);
            if (eventoAlvo == null) return;
            System.out.println("Digite o novo nome do evento:");
            String novoNome = sc.nextLine();
            empresa.alterarEvento(eventoAlvo.getId(), novoNome);
        }
        else if (escolha == 5) {
            if (empresa.eventosIsEmpty()) {
                System.out.println("Nenhum evento cadastrado.");
                return;
            }
            empresa.listarEventos();
            System.out.println("Selecione o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = empresa.getEvento(idEvento);
            if (eventoAlvo == null) return;

            if (eventoAlvo.getTarefas().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no evento.");
                return;
            }
            eventoAlvo.listarTarefas();
            System.out.println("ID da tarefa a excluir:");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();
            if (eventoAlvo.getTarefa(idTarefa) != null) {
                eventoAlvo.excluirTarefa(idTarefa);
                System.out.println("Tarefas de " + eventoAlvo.getNome() + ":");
                eventoAlvo.listarTarefas();
            } else {
                System.out.println("Esse ID de tarefa não existe no evento.");
            }
        }
    */
    }
    
}