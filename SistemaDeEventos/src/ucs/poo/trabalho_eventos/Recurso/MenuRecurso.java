package ucs.poo.trabalho_eventos.Recurso;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;
import ucs.poo.trabalho_eventos.main.Utilitarios;


public class MenuRecurso {

    public void menuRecurso(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
        int escolha = 10;

        while (escolha < 0 || escolha >= 7) {
            System.out.println("\n---------------------------------------------");
            System.out.println("CONTROLE de Recursos\n0 - Voltar ao Menu\n1 - Incluir/Adicionar Recursos\n2 - Excluir Recurso\n3 - Alterar Recurso\n4 - Consulta por ID\n5 - Consulta por nome\n6 - Consulta todos os Recursos");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 1) {
            Functions.adicionarRecurso(empresa);
        }
        else if (escolha == 2) {
            Functions.excluirRecurso(empresa);
        }
        else if (escolha == 3) {
            Functions.alterarRecurso(empresa);
        }
        else if (escolha == 4) {
            if (empresa.getRecursosDB().isEmpty())
                System.out.println("Nenhum recurso cadastrado no sistema para listar");
            else {
                System.out.println("ID: ");
                int id = Utilitarios.lerInteiroComVerificacao();
                Functions.listarRecursos(empresa, id);
            }
        }
        else if (escolha == 5) {
            if (empresa.getRecursosDB().isEmpty())
                System.out.println("Nenhum recurso cadastrado no sistema para listar");
            else {
                System.out.println("Nome do Recurso: ");
                String nomeRecurso = sc.nextLine();
                Functions.listarRecursos(empresa, nomeRecurso);
            }
        }
        else if (escolha == 6) {
            if (empresa.getRecursosDB().isEmpty())
                System.out.println("Nenhum recurso cadastrado no sistema para listar");
            else
                Functions.listarRecursos(empresa);
        }
    }
}
