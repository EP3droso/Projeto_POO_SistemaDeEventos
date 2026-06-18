package ucs.poo.trabalho_eventos.Relatorio;

import java.util.Date;
import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuRelatorio {

    public void menuRelatorio(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
        /*
        System.out.println("---------------------------------------------");
        System.out.println("RELATÓRIOS\n1 - Relatório de Evento\n2 - Execuções por Período\n3 - Recursos de uma Tarefa\n0 - Voltar");
        int escolha = Utilitarios.lerInteiroComVerificacao();

        if (escolha == 0) {
            return;
        }
        else if (escolha == 1) {
            empresa.listarEventos();
            System.out.println("Informe o ID do evento:");
            int idEvento = Utilitarios.lerInteiroComVerificacao();
            
            empresa.gerarRelatorioEvento(idEvento);
            
        }
        else if (escolha == 2) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                System.out.println("Data início (dd/MM/yyyy HH:mm):");
                Date inicio = sdf.parse(sc.nextLine());
                System.out.println("Data fim (dd/MM/yyyy HH:mm):");
                Date fim = sdf.parse(sc.nextLine());
                
                empresa.gerarRelatorioPeriodo(inicio, fim);
                
            } catch (Exception e) {
                System.out.println("Data inválida.");
            }
        }
        else if (escolha == 3) {
            System.out.println("Informe o ID da tarefa:");
            int idTarefa = Utilitarios.lerInteiroComVerificacao();
            
            empresa.gerarRelatorioRecursosTarefa(idTarefa);
            
        }
        */
    }
}