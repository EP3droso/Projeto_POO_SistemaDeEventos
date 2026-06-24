package ucs.poo.trabalho_eventos.Colaborador;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ucs.poo.trabalho_eventos.main.Utilitarios;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Colaborador.class
)
public class Colaborador {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String funcao;

    public Colaborador(String nome, String email, String senha, String funcao, int id) {
        this.id = id;
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setFuncao(funcao);
    }

    public Colaborador() {
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = Utilitarios.exigirNaoVazio(nome, "Nome do colaborador"); }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = Utilitarios.exigirNaoVazio(email, "Email do colaborador"); }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = Utilitarios.exigirNaoVazio(senha, "Senha do colaborador"); }
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = Utilitarios.exigirNaoVazio(funcao, "Função do colaborador"); }

    @Override
    public String toString() {
        return "ID: " + this.id + " | Nome: " + this.nome + " | Email: " + this.email + " | Função: " + this.funcao;
    }
}