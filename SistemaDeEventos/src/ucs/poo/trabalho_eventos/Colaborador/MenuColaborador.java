package ucs.poo.trabalho_eventos.Colaborador;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuColaborador {

    public void menuColaborador(Empresa empresa, Sistema sistema) {
        int escolha = 10;

        while (escolha < 0 || escolha > 6) {
            System.out.println("\n---------------------------------------------");
            System.out.println("MENU de Colaboradores");
            System.out.println("0 - Voltar para o menu principal");
            System.out.println("1 - Cadastrar Colaborador");
            System.out.println("2 - Consultar Colaboradores por nome");
            System.out.println("3 - Consultar Colaborador por ID");
            System.out.println("4 - Excluir Colaborador por ID");
            System.out.println("5 - Alterar Colaborador por ID");
            System.out.println("6 - Listar Todos os Colaboradores");
            
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) {
            return;
        }
        
        else if (escolha == 1) {
            Functions.cadastrarColaborador(empresa, sistema);
        }
        
        else if (escolha == 2) {
            if (Functions.colaboradoresIsEmpty(empresa)) return;
            Functions.pesquisaPorContem(empresa);
        }
        
        else if (escolha == 3) {
            if (Functions.colaboradoresIsEmpty(empresa)) return;
            Functions.listarColaboradores(empresa); 
            System.out.println("Digite o ID do colaborador:");
            int idColab = Utilitarios.lerInteiroComVerificacao();
            
            Colaborador colaborador = Functions.getColaborador(idColab, empresa);
            if (colaborador != null) {
                System.out.println("\n--- Dados do Colaborador ---");
                System.out.println(colaborador);
            }
        }
        
        else if (escolha == 4) {
            if (Functions.colaboradoresIsEmpty(empresa)) return;
            Functions.listarColaboradores(empresa);
            System.out.println("Digite o ID do colaborador a excluir:");
            int idColab = Utilitarios.lerInteiroComVerificacao();
            Functions.excluirColaborador(idColab, empresa, sistema);
        }
        
        else if (escolha == 5) {
            if (Functions.colaboradoresIsEmpty(empresa)) return;
            Functions.listarColaboradores(empresa);
            System.out.println("Digite o ID do colaborador a alterar:");
            int idColab = Utilitarios.lerInteiroComVerificacao();
            Colaborador colabAlvo = Functions.getColaborador(idColab, empresa);
            
            if (colabAlvo == null) return;
            Functions.alterarColaborador(colabAlvo, empresa, sistema);
        }
        
        else if (escolha == 6) {
            if (Functions.colaboradoresIsEmpty(empresa)) return;
            Functions.listarColaboradores(empresa);
        }
        
    }
}