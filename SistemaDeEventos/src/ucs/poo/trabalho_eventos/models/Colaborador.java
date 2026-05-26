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
