package ucs.poo.trabalho_eventos.Evento;

import java.util.List;
import java.util.Scanner;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Utilitarios;
import ucs.poo.trabalho_eventos.main.Sistema;
import static ucs.poo.trabalho_eventos.Tarefa.Functions.adicionarTarefa;

public class MenuEvento {

    public void menuEvento(Empresa empresa, Sistema sistema) {
    	
    	List<Evento> eventosDB = empresa.getEventos();
        Scanner sc = new Scanner(System.in);
        int escolha = 10;
       
        while (escolha < 0 || escolha > 7) {
            System.out.println("\n---------------------------------------------");
            System.out.println("MENU de Eventos\n"
            		+ "0 - Voltar ao Menu\n"
            		+ "1 - Cadastrar Evento\n"
            		+ "2 - Excluir Evento\n"
            		+ "3 - Alterar Evento\n"
            		+ "4 - Cadastrar Tarefa no Evento\n"
            		+ "5 - Excluir Tarefa do Evento\n"
            		+ "6 - Ver evento\n"
            		+ "7 - Listar eventos\n");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) {
            return;
        }
        
        else if (escolha == 1) {
        	Functions.cadastrarEvento(empresa,sistema,eventosDB);
        }
        
        else if (escolha == 2) {
            if (Functions.eventosIsEmpty(empresa)) {
                return;
            }
            
            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento a excluir:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Functions.excluirEvento(idEvento,empresa,sistema);

        }
        
        else if (escolha == 3) {
        	if (Functions.eventosIsEmpty(empresa)) {
                Functions.cadastrarEvento(empresa, sistema, eventosDB);
            }
        	
            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento a alterar:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = Functions.getEvento(idEvento,empresa);
            
            if (eventoAlvo == null) return;
            
            Functions.alterarEvento(eventoAlvo,empresa,sistema);
            
        }
        
        else if (escolha == 4) {
            if (empresa.getEventos().isEmpty()) {
                System.out.println("Nenhum evento cadastrado.");
                Functions.cadastrarEvento(empresa, sistema, eventosDB);
            }

            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema.");
                System.out.println("Deseja cadastrar uma tarefa agora? (S para sim, Enter para pular):");
                String resposta = sc.nextLine();
                if (resposta.equalsIgnoreCase("S")) {
                    adicionarTarefa(empresa, sistema);
                } else {
                    return;
                }
            }

            // Verifica novamente caso o usuário tenha pulado o cadastro
            if (empresa.getTarefasDB().isEmpty()) return;

            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento:");
            int indexEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = Functions.getEvento(indexEvento, empresa);
            if (eventoAlvo == null) return;

            System.out.println("Tarefas disponíveis:");
            for (Tarefa t : empresa.getTarefasDB()) {
                System.out.println(empresa.getTarefasDB().indexOf(t) + " - " + t.getNome());
            }

            System.out.println("Deseja adicionar uma nova tarefa? S/N");
            String novaTarefa = sc.nextLine();

            if (novaTarefa.equalsIgnoreCase("S")) {
                adicionarTarefa(empresa, sistema);
            }

            // Verifica novamente após possível cadastro
            if (empresa.getTarefasDB().isEmpty()) return;

            System.out.println("Tarefas disponíveis:");
            for (Tarefa t : empresa.getTarefasDB()) {
                System.out.println(empresa.getTarefasDB().indexOf(t) + " - " + t.getNome());
            }

            System.out.println("ID da tarefa a cadastrar no evento:");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();

            if (idTarefa < 0 || idTarefa >= empresa.getTarefasDB().size()) {
                System.out.println("ID inválido!");
                return;
            }

            Tarefa tarefaAux = empresa.getTarefasDB().get(idTarefa);

            eventoAlvo.cadastrarTarefa(tarefaAux);
            System.out.println("Tarefas de " + eventoAlvo.getNome() + ":");
            eventoAlvo.listarTarefas();

            sistema.serializarEmpresa(empresa);
        }
        

        else if (escolha == 5) {
            if (Functions.eventosIsEmpty(empresa)) {
                Functions.cadastrarEvento(empresa, sistema, eventosDB);
            }
            
            Functions.pesquisaPorContem(empresa);
            System.out.println("Selecione o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = Functions.getEvento(idEvento,empresa);
            if (eventoAlvo == null) return;

            if (eventoAlvo.getTarefas().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no evento.");
                return;
            }
            
            eventoAlvo.listarTarefas();
            System.out.println("ID da tarefa a excluir:");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();
            
            if (eventoAlvo.getTarefa(idTarefa-1) != null) {
                eventoAlvo.excluirTarefa(idTarefa-1);
                System.out.println("Tarefas de " + eventoAlvo.getNome() + ":");
                eventoAlvo.listarTarefas();
                
                sistema.serializarEmpresa(empresa);
            } 
        }
        
        else if(escolha == 6) {
        	if (Functions.eventosIsEmpty(empresa)) {
                Functions.cadastrarEvento(empresa, sistema, eventosDB);
            }
        	
        	Functions.pesquisaPorContem(empresa);
        	System.out.println("Selecione o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            Evento eventoAlvo = Functions.getEvento(idEvento,empresa);
            if (eventoAlvo == null) return;
            
            Functions.mostrarInfoEvento(eventoAlvo, empresa);
        }
        
        else if(escolha == 7) {
        	for(Evento aux : empresa.getEventos()) {
        		Functions.mostrarInfoEvento(aux, empresa);
        	}
        }
    }
    
    
    
}