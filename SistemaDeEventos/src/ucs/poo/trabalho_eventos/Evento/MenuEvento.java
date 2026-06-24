package ucs.poo.trabalho_eventos.Evento;

import static ucs.poo.trabalho_eventos.Tarefa.Functions.adicionarTarefa;

import java.util.List;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuEvento {

    public void menuEvento(Empresa empresa, Sistema sistema) {

        List<Evento> eventosDB = empresa.getEventos();
        int escolha = 10;

        while (escolha < 0 || escolha > 8) {
            System.out.println("\n---------------------------------------------");
            System.out.println("MENU de Eventos\n"
                    + "0 - Voltar ao Menu\n"
                    + "1 - Cadastrar Evento\n"
                    + "2 - Excluir Evento\n"
                    + "3 - Alterar Evento\n"
                    + "4 - Cadastrar Tarefa no Evento\n"
                    + "5 - Excluir Tarefa do Evento\n"
                    + "6 - Registrar Execução de Tarefa (aloca recursos e colaboradores)\n"
                    + "7 - Ver Evento\n"
                    + "8 - Listar Eventos\n");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) {
            return;
        }

        else if (escolha == 1) {
            Functions.cadastrarEvento(empresa, sistema, eventosDB);
        }

        else if (escolha == 2) {
            if (Functions.eventosIsEmpty(empresa)) return;

            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento a excluir(Enter para retoronar ao menu principal):");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            if(idEvento == -1) return;
            Functions.excluirEvento(idEvento, empresa, sistema);
        }

        else if (escolha == 3) {
            if (Functions.eventosIsEmpty(empresa)) {
                Functions.cadastrarEvento(empresa, sistema, eventosDB);
                return;
            }

            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento a alterar(Enter para retoronar ao menu principal):");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            if(idEvento == -1) return;
            Evento eventoAlvo = Functions.getEvento(idEvento, empresa);

            if (eventoAlvo == null) return;

            Functions.alterarEvento(eventoAlvo, empresa, sistema);
        }

        else if (escolha == 4) {
            try {
                if (empresa.getEventos().isEmpty()) {
                    System.out.println("Nenhum evento cadastrado.");
                    Functions.cadastrarEvento(empresa, sistema, eventosDB);
                }

                if (empresa.getTarefasDB().isEmpty()) {
                    System.out.println("Nenhuma tarefa cadastrada no sistema.");
                    System.out.println("Cadastro de Tarefa:");
                    adicionarTarefa(empresa, sistema);
                }

                if (empresa.getEventos().isEmpty() || empresa.getTarefasDB().isEmpty()) return;

                Functions.pesquisaPorContem(empresa);
                System.out.println("Selecione o ID do evento:");
                int indexEvento = Utilitarios.lerInteiroComVerificacao();
                if(indexEvento == -1) return;

                Evento eventoAlvo = Functions.getEvento(indexEvento, empresa);
                if (eventoAlvo == null) return;

                // Pesquisa da tarefa por parte do nome (Enter = todas).
                List<Tarefa> encontradas = Functions.pesquisarTarefas(empresa);
                if (encontradas.isEmpty()) return;

                System.out.println("Selecione o índice da tarefa (Enter para voltar):");
                int idxTarefa = Utilitarios.lerInteiroComVerificacao();
                if (idxTarefa == -1) return;
                if (idxTarefa < 0 || idxTarefa >= encontradas.size()) {
                    System.out.println("Índice inválido!");
                    return;
                }

                Tarefa tarefaAux = encontradas.get(idxTarefa);

                if (eventoAlvo.possuiTarefa(tarefaAux)) {
                    throw new TarefaRepetidaException();
                }

                eventoAlvo.cadastrarTarefa(tarefaAux);
                System.out.println("Tarefa cadastrada no evento com sucesso!");
                System.out.println("Tarefas de " + eventoAlvo.getNome() + ":");
                eventoAlvo.listarTarefas();

                sistema.serializarEmpresa(empresa);
            }
            catch(TarefaRepetidaException e){
                System.out.println(e.getMessage());
            }
        }

        else if (escolha == 5) {
            if (Functions.eventosIsEmpty(empresa)) {
                Functions.cadastrarEvento(empresa, sistema, eventosDB);
                return;
            }

            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            if(idEvento == -1) return;
            Evento eventoAlvo = Functions.getEvento(idEvento, empresa);
            if (eventoAlvo == null) return;

            if (eventoAlvo.getTarefas().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no evento.");
                return;
            }

            eventoAlvo.listarTarefas();
            System.out.println("ID da tarefa a excluir(Enter para voltar ao menu principal):");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();
            if(idTarefa == -1) return;
            if (eventoAlvo.getTarefa(idTarefa - 1) != null) {
                eventoAlvo.excluirTarefa(idTarefa - 1);
                System.out.println("Tarefas de " + eventoAlvo.getNome() + ":");
                eventoAlvo.listarTarefas();
                sistema.serializarEmpresa(empresa);
            }
        }

        else if (escolha == 6) {
            if (Functions.eventosIsEmpty(empresa)) return;

            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento(Enter para retornar ao menu principal):");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            if(idEvento == -1) return;
            Evento eventoAlvo = Functions.getEvento(idEvento, empresa);
            if (eventoAlvo == null) return;

            Functions.registrarTarefas(eventoAlvo, empresa, sistema);
        }

        else if (escolha == 7) {
            if (Functions.eventosIsEmpty(empresa)) return;

            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = Functions.getEvento(idEvento, empresa);
            if (eventoAlvo == null) return;

            Functions.mostrarInfoEvento(eventoAlvo, empresa);
        }

        else if (escolha == 8) {
            if (Functions.eventosIsEmpty(empresa)) return;

            for (Evento aux : empresa.getEventos()) {
                Functions.mostrarInfoEvento(aux, empresa);
            }
        }
    }
}
