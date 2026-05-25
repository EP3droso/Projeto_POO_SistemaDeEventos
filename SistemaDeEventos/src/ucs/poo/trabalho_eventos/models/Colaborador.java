package ucs.poo.trabalho_eventos.models;

import java.util.ArrayList;
import java.util.List;


public class Colaborador {

    private static int contadorId = 1;

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String funcao;

    public Colaborador(String nome, String email, String senha, String funcao) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do colaborador não pode ser vazio.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email do colaborador não pode ser vazio.");
        if (senha == null || senha.isBlank())
            throw new IllegalArgumentException("Senha do colaborador não pode ser vazia.");
        if (funcao == null || funcao.isBlank())
            throw new IllegalArgumentException("Função do colaborador não pode ser vazia.");

        this.id = contadorId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
    }

    //CRUD

    private static final List<Colaborador> listaColaboradores = new ArrayList<>();

    public static void incluir(Colaborador colaborador) {
        if (colaborador == null)
            throw new IllegalArgumentException("Colaborador não pode ser nulo.");
        listaColaboradores.add(colaborador);
        System.out.println("[Colaborador] Incluído: " + colaborador.getNome() + " (ID " + colaborador.getId() + ")");
    }

    public static void alterar(int id, String novoNome, String novoEmail, String novaSenha, String novaFuncao) {
        Colaborador c = consultarPorId(id);
        if (novoNome != null && !novoNome.isBlank()) c.setNome(novoNome);
        if (novoEmail != null && !novoEmail.isBlank()) c.setEmail(novoEmail);
        if (novaSenha != null && !novaSenha.isBlank()) c.setSenha(novaSenha);
        if (novaFuncao != null && !novaFuncao.isBlank()) c.setFuncao(novaFuncao);
        System.out.println("[Colaborador] Alterado: ID " + id);
    }

    public static void excluir(int id) {
        Colaborador c = consultarPorId(id);
        listaColaboradores.remove(c);
        System.out.println("[Colaborador] Excluído: ID " + id);
    }

    public static Colaborador consultarPorId(int id) {
        for (Colaborador c : listaColaboradores) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new IllegalArgumentException("Colaborador não encontrado.");
    }

    public static List<Colaborador> consultarPorNome(String nome) {
        List<Colaborador> resultado = new ArrayList<>();
        for (Colaborador c : listaColaboradores) {
            if (c.getNome().equalsIgnoreCase(nome)) resultado.add(c);
        }
        return resultado;
    }

    public static List<Colaborador> listarTodos() {
        return new ArrayList<>(listaColaboradores);
    }

    //Getters / Setters 

    public int getId() { 
        return id; 
    }

    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getEmail() {
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) { 
        this.senha = senha; 
    }

    public String getFuncao() { 
        return funcao; 
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao; 
    }

    @Override
    public String toString() {
        return String.format("Colaborador{id=%d, nome='%s', email='%s', funcao='%s'}", id, nome, email, funcao);
    }
}
