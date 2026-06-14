package ucs.poo.trabalho_eventos.Recurso;

import java.util.Scanner;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;
import ucs.poo.trabalho_eventos.main.Empresa;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Functions {

    public static void listarRecursos(Empresa empresa, String nomeRecurso) {
        System.out.println("\n---------------------------------------------");
        boolean achou = false;
        for (Recurso recurso : empresa.getRecursosDB()) {
            if (recurso.getNome().equalsIgnoreCase(nomeRecurso)) {
                System.out.println(recurso.getId() + " - " + recurso.getNome() + " - " + recurso.getTipo() + " - " + recurso.getQuantidade());
                achou = true;
            }
        }
        if (!achou) System.out.println("Recurso não cadastrado, ou não encontrado");
        System.out.println("\n");
    }

    public static void listarRecursos(Empresa empresa, int idRecurso) {
        System.out.println("\n---------------------------------------------");
        boolean achou = false;
        for (Recurso recurso : empresa.getRecursosDB()) {
            if (recurso.getId() == idRecurso) {
                System.out.println(recurso.getId() + " - " + recurso.getNome() + " - " + recurso.getTipo() + " - " + recurso.getQuantidade());
                achou = true;
            }
        }
        if (!achou) System.out.println("Recurso não cadastrado, ou não encontrado");
        System.out.println("\n");
    }

    public static void listarRecursos(Empresa empresa) {
        if (empresa.getRecursosDB().isEmpty()) return;
        System.out.println("\n---------------------------------------------");
        System.out.println("Lista de Recursos:\n");
        for (Recurso recurso : empresa.getRecursosDB()) {
            System.out.println(recurso.getId() + " - " + recurso.getNome() + " - " + recurso.getTipo() + " - " + recurso.getQuantidade());
        }
    }

    public static void adicionarRecurso(Empresa empresa) {
        Scanner sc = new Scanner(System.in);
        listarRecursos(empresa);

        System.out.println("Insira o nome do recurso:");
        String nome = sc.nextLine();

        for (Recurso r : empresa.getRecursosDB()) {
            if (r.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Erro: Já existe um recurso cadastrado com esse nome.");
                return;
            }
        }

        System.out.println("Insira o tipo do recurso:");
        String tipo = sc.nextLine();

        System.out.println("Insira a quantidade inicial do recurso:");
        int quantidade = Utilitarios.lerInteiroComVerificacao();

        if (quantidade < 0) {
            System.out.println("Erro: A quantidade não pode ser negativa.");
            return;
        }

        Recurso novoRecurso = new Recurso();
        novoRecurso.setNome(nome);
        novoRecurso.setTipo(tipo);
        novoRecurso.setQuantidade(quantidade);

        empresa.getRecursosDB().add(novoRecurso);
        System.out.println("Recurso '" + nome + "' (ID: " + novoRecurso.getId() + ") adicionado com sucesso!");
    }

    public static void excluirRecurso(Empresa empresa) {
        Scanner sc = new Scanner(System.in);

        if (empresa.getRecursosDB().isEmpty()) {
            System.out.println("Nenhum recurso cadastrado no sistema para Excluir.");
            return;
        }

        listarRecursos(empresa);
        System.out.println("Id do Recurso a ser Removido: ");
        int idRecurso = Utilitarios.lerInteiroComVerificacao();

        Recurso recursoParaRemover = null;
        for (Recurso r : empresa.getRecursosDB()) {
            if (r.getId() == idRecurso) {
                recursoParaRemover = r;
                break;
            }
        }

        if (recursoParaRemover == null) {
            System.out.println("Recurso com ID " + idRecurso + " não encontrado!");
            return;
        }

        for (Tarefa t : empresa.getTarefasDB()) {
            java.util.List<RecursoTarefa> paraRemover = new java.util.ArrayList<>();
            for (RecursoTarefa rt : t.getRecursosTarefas()) {
                if (rt.getRecurso().getId() == idRecurso) paraRemover.add(rt);
            }
            t.getRecursosTarefas().removeAll(paraRemover);
        }

        empresa.getRecursosDB().remove(recursoParaRemover);
        System.out.println("Recurso '" + recursoParaRemover.getNome() + "' removido com sucesso!");
    }

    public static void alterarRecurso(Empresa empresa) {
        Scanner sc = new Scanner(System.in);

        if (empresa.getRecursosDB().isEmpty()) {
            System.out.println("Nenhum recurso cadastrado no sistema para Alterar.");
            return;
        }

        listarRecursos(empresa);
        System.out.println("Id do Recurso a ser alterado: ");
        int idRecurso = Utilitarios.lerInteiroComVerificacao();

        Recurso recursoAlvo = null;
        for (Recurso r : empresa.getRecursosDB()) {
            if (r.getId() == idRecurso) { recursoAlvo = r; break; }
        }

        if (recursoAlvo == null) {
            System.out.println("Recurso não encontrado!");
            return;
        }

        int escolha = 10;
        while (escolha < 0 || escolha > 3) {
            System.out.println("---------------------------------------------");
            System.out.println("\nALTERAR Recursos\n0 - Voltar ao Menu\n1 - Alterar Nome\n2 - Alterar Tipo\n3 - Alterar Quantidade");
            escolha = Utilitarios.lerInteiroComVerificacao();
        }

        if (escolha == 0) return;

        if (escolha == 1) {
            System.out.println("Insira o novo nome:");
            String novoNome = sc.nextLine();
            for (Recurso r : empresa.getRecursosDB()) {
                if (r.getNome().equalsIgnoreCase(novoNome)) {
                    System.out.println("Erro: Já existe um recurso com esse nome.");
                    return;
                }
            }
            recursoAlvo.setNome(novoNome);
            System.out.println("Nome alterado para '" + novoNome + "'!");
        }
        else if (escolha == 2) {
            System.out.println("Novo tipo: ");
            String novoTipo = sc.nextLine();
            recursoAlvo.setTipo(novoTipo);
            System.out.println("Tipo alterado para " + novoTipo);
        }
        else if (escolha == 3) {
            System.out.println("Quantidade atual: " + recursoAlvo.getQuantidade());
            System.out.println("Nova quantidade:");
            int novaQtd = Utilitarios.lerInteiroComVerificacao();
            if (novaQtd < 0) {
                System.out.println("Erro: quantidade não pode ser negativa.");
            } else {
                recursoAlvo.setQuantidade(novaQtd);
                System.out.println("Quantidade alterada para " + novaQtd);
            }
        }
    }
}