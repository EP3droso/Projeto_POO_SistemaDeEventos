package ucs.poo.trabalho_eventos.models;
import java.util.ArrayList;
import java.util.List;

public class Recurso {
	private static int contadorId = 1; 
	
	private int id;
	private String nome;
	private String tipo;
	private int quantidade;

	public Recurso() {
        this.id = contadorId++;
    }

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	List<RecursoTarefa> recursosTarefas = new ArrayList<>();
	

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
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public void atualizarQuantidade(int quantidadeParaRetirar) {
		this.quantidade = quantidade - quantidadeParaRetirar;
	}
	
	@Override
	public String toString() {
		return "Recurso [id=" + id + ", Nome=" + nome + ", tipo=" + tipo + ", recursosTarefas=" + recursosTarefas
				+ "]";
	}
}
