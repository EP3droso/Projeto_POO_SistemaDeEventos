package ucs.poo.trabalho_eventos.Recurso;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.main.Utilitarios;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Recurso.class
)
public class Recurso {
	private static int contadorId = 1; 
	
	private int id;
	private String nome;
	private String tipo;
	private int quantidade;
	List<RecursoTarefa> recursosTarefas = new ArrayList<>();

	

	public Recurso(int id, String nome, String tipo, int quantidade) {
	    this.id = id;
	    setNome(nome);
	    setTipo(tipo);
	    this.quantidade = quantidade;
	}
	
	public Recurso() {
        this.id = contadorId++;
    }

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	
	

// Getters e Sets
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = Utilitarios.exigirNaoVazio(nome, "Nome do recurso");
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = Utilitarios.exigirNaoVazio(tipo, "Tipo do recurso");
	}


	public void atualizarQuantidade(int quantidadeParaRetirar) {
		this.quantidade = quantidade - quantidadeParaRetirar;
	}
	
	@Override
	public String toString() {
		return "\nRecurso [id=" + id + ", Nome=" + nome + ", tipo=" + tipo + ", recursosTarefas=" + recursosTarefas
				+ "]";
	}
	
	
	public static int getContadorId() {
		return contadorId;
	}

	public static void setContadorId(int contadorId) {
		Recurso.contadorId = contadorId;
	}

	public List<RecursoTarefa> getRecursosTarefas() {
		return recursosTarefas;
	}

	public void setRecursosTarefas(List<RecursoTarefa> recursosTarefas) {
		this.recursosTarefas = recursosTarefas;
	}
}
