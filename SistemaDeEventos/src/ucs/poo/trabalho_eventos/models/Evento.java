package ucs.poo.trabalho_eventos.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Evento {
		
	private int id;
	private String nome;
	private List<Tarefa> tarefas;
	
	
	public Evento(int id, String nome){
		this.id = id;
		this.nome = nome;
		tarefas = new ArrayList<>();
	}
	
	public void acompanharAndamento() {
		int tam = tarefas.size(), tarRealizadas = 0;
		
		for(Tarefa tarefa : tarefas) {
			if(tarefa.getColaboradorTarefa() != null ) {
				tarRealizadas +=1;
			}
		}
		
		System.out.println("O evento já foi "|| (tarRealizadas*100)/tam || "% realizado");
	}
	
	public void consultarHistoricoTarefas() {
		for(Tarefa tarefa : tarefas) {
			if(tarefa.getColaboradorTarefa() != null ) {
				 System.out.println("Tarefa "|| tarefa.getNome() || " Realizada");
			}
		}
	}
	
	
	public void cadastrarTarefa(Tarefa tarefa) {
		tarefas.add(tarefa);
	}
	
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
	
	
	
}
