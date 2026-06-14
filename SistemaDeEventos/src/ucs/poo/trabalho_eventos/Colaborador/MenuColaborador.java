package ucs.poo.trabalho_eventos.Colaborador;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class MenuColaborador {

    public void menuColaborador(Empresa empresa) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---------------------------------------------");
        System.out.println("MENU de Colaboradores");
        System.out.println("1 - Cadastrar Colaborador");
        System.out.println("2 - Consultar Colaboradores por nome");
        System.out.println("3 - Consultar Colaborador por ID");
        System.out.println("4 - Excluir Colaborador");
        System.out.println("5 - Alterar Colaborador");

        int escolhaColab = Utilitarios.lerInteiroComVerificacao();

        if (escolhaColab == 1) {
            try {
                System.out.println("Insira o nome do colaborador:");
                String nome = sc.nextLine();
                System.out.println("Insira o email do colaborador:");
                String email = sc.nextLine();
                System.out.println("Insira a senha do colaborador:");
                String senha = sc.nextLine();
                System.out.println("Insira a função do colaborador:");
                String funcao = sc.nextLine();
                empresa.cadastrarColaborador(new Colaborador(nome, email, senha, funcao));
                System.out.println("Colaborador cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
        else if (escolhaColab == 2) {
            empresa.mostrarColaboradores();
            System.out.println("Informe o nome do colaborador:");
            String nome = sc.nextLine();
            empresa.buscarColaboradorPorNome(nome);
        }
        else if (escolhaColab == 3) {
            empresa.mostrarColaboradores();
            System.out.println("Informe o ID do colaborador:");
            int id = Utilitarios.lerInteiroComVerificacao();
            Colaborador colaborador = empresa.getColaborador(id);
            System.out.println(colaborador != null ? colaborador : "Colaborador não encontrado.");
        }
        else if (escolhaColab == 4) {
            empresa.mostrarColaboradores();
            System.out.println("Informe o ID do colaborador:");
            int id = Utilitarios.lerInteiroComVerificacao();
            empresa.excluirColaborador(id);
        }
        else if (escolhaColab == 5) {
            empresa.mostrarColaboradores();
            System.out.println("Informe o ID do colaborador:");
            int id = Utilitarios.lerInteiroComVerificacao();
            sc.nextLine();
            System.out.println("Novo nome:"); String novoNome = sc.nextLine();
            System.out.println("Novo email:"); String novoEmail = sc.nextLine();
            System.out.println("Nova senha:"); String novaSenha = sc.nextLine();
            System.out.println("Nova função:"); String novaFuncao = sc.nextLine();
            empresa.alterarColaborador(id, novoNome, novoEmail, novaSenha, novaFuncao);
        }
    }
}
