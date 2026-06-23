package ucs.poo.trabalho_eventos.Relatorio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuRelatorio {

    public void menuRelatorio(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
        Relatorio relatorio = new Relatorio();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        int escolha = 10;
      
        while (escolha < 0 || escolha > 4) {
            System.out.println("\n---------------------------------------------");
            System.out.println("MENU DE RELATÓRIOS");
            System.out.println("0 - Voltar para o menu principal");
            System.out.println("1 - Acompanhar andamento de um Evento (Tarefas e Execuções)");
            System.out.println("2 - Listar execuções realizadas em um Período");
            System.out.println("3 - Identificar recursos utilizados in uma Tarefa");
            System.out.println("4 - Verificar o uso de um Recurso ao longo do tempo");
            
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) {
            return; 
        }

        else if (escolha == 1) {
            if (empresa.getEventos().isEmpty()) {
                System.out.println("Nenhum evento cadastrado para gerar relatório.");
                return; 
            }
            System.out.println("Eventos disponíveis:");
            empresa.getEventos().forEach(e -> System.out.println("ID: " + e.getId() + " - " + e.getNome()));
            
            System.out.println("Informe o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            relatorio.gerarRelatorioEvento(idEvento, empresa);
        }

        else if (escolha == 2) {
            try {
                System.out.println("Digite a data de Início (Formato: dd/MM/yyyy HH:mm):");
                Date inicio = sdf.parse(sc.nextLine());
                System.out.println("Digite a data de Fim (Formato: dd/MM/yyyy HH:mm):");
                Date fim = sdf.parse(sc.nextLine());
                
                relatorio.gerarRelatorioPeriodo(inicio, fim, empresa);
            } catch (Exception e) {
                System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy HH:mm (Ex: 25/06/2026 14:00)");
            }
        }

        else if (escolha == 3) {
            if (empresa.getTarefasDB().isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada no sistema.");
                return; 
            }
            System.out.println("Tarefas cadastradas:");
            empresa.getTarefasDB().forEach(t -> System.out.println("ID: " + t.getId() + " - " + t.getNome()));
            
            System.out.println("Informe o ID da tarefa:");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();
            relatorio.gerarRelatorioRecursosTarefa(idTarefa, empresa);
        }

        else if (escolha == 4) {
            if (empresa.getRecursosDB().isEmpty()) {
                System.out.println("Nenhum recurso cadastrado no sistema.");
                return;
            }
            System.out.println("Recursos cadastrados:");
            empresa.getRecursosDB().forEach(r -> System.out.println("ID: " + r.getId() + " - " + r.getNome()));
            
            System.out.println("Informe o ID do recurso para ver o histórico:");
            int idRecurso = Utilitarios.lerInteiroComVerificacao();
            relatorio.verificarUsoRecursoAoLongoDoTempo(idRecurso, empresa);
        }
        
    }
}