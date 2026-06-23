package ucs.poo.trabalho_eventos.Colaborador;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Sistema;

public class Functions {
    
    public static boolean colaboradoresIsEmpty(Empresa empresa) {
        if (empresa.getColaboradores().isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
            return true;
        }
        return false;
    }

    public static void cadastrarColaborador(Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Insira o nome do colaborador:");
            String nome = sc.nextLine();
            System.out.println("Insira o email do colaborador:");
            String email = sc.nextLine();
            System.out.println("Insira a senha do colaborador:");
            String senha = sc.nextLine();
            System.out.println("Insira a função do colaborador:");
            String funcao = sc.nextLine();
            
            Colaborador novoColab = new Colaborador(nome, email, senha, funcao, empresa.getIdAtualEColaboradores());
            
            empresa.setIdAtualEColaboradores(empresa.getIdAtualEColaboradores() + 1);
            empresa.getColaboradores().add(novoColab);
            
            sistema.serializarEmpresa(empresa);
            System.out.println("Colaborador cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public static void listarColaboradores(Empresa empresa) {
        System.out.println("Os colaboradores cadastrados são:");
        for (Colaborador colaborador : empresa.getColaboradores()) {
            System.out.println("ID: " + colaborador.getId() + " - " + colaborador.getNome());
        }
    }

    public static Colaborador getColaborador(int idProcurado, Empresa empresa) {
        for (Colaborador colaborador : empresa.getColaboradores()) {
            if (colaborador.getId() == idProcurado) {
                return colaborador;
            }
        }
        System.out.println("Não existe um colaborador com o ID " + idProcurado);
        return null;
    }

    public static void excluirColaborador(int idProcurado, Empresa empresa, Sistema sistema) {
        Colaborador colaboradorAux = getColaborador(idProcurado, empresa);
        if (colaboradorAux != null) {
            empresa.getColaboradores().remove(colaboradorAux);
            System.out.println("Colaborador removido com sucesso!");
        }
        sistema.serializarEmpresa(empresa);
    }

    public static void alterarColaborador(Colaborador colabAux, Empresa empresa, Sistema sistema) {
        Scanner sc = new Scanner(System.in);
            
        System.out.println("Digite o novo nome do colaborador:");
        String novoNome = sc.nextLine();
        System.out.println("Digite o novo email do colaborador:");
        String novoEmail = sc.nextLine();
        System.out.println("Digite a nova senha do colaborador:");
        String novaSenha = sc.nextLine();
        System.out.println("Digite a nova função do colaborador:");
        String novaFuncao = sc.nextLine();

        if (!"".equals(novoNome)) colabAux.setNome(novoNome);
        if (!"".equals(novoEmail)) colabAux.setEmail(novoEmail);
        if (!"".equals(novaSenha)) colabAux.setSenha(novaSenha);
        if (!"".equals(novaFuncao)) colabAux.setFuncao(novaFuncao);
        
        sistema.serializarEmpresa(empresa);
        System.out.println("Colaborador alterado com sucesso!");
    }

    public static void pesquisaPorContem(Empresa empresa) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do colaborador para buscar:");
        String linhaContem = sc.nextLine();
        
        System.out.println("Os colaboradores cadastrados com esse nome são:");
        for (Colaborador colaborador : empresa.getColaboradores()) {
            if (colaborador.getNome().contains(linhaContem)) {
                System.out.println("ID: " + colaborador.getId() + " - " + colaborador.getNome());
            }
        }
    }
}