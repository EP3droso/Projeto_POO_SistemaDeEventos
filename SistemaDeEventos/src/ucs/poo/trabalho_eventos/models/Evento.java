package ucs.poo.trabalho_eventos.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Evento {
		
	private int id;
	private String nome;
	private List<Tarefa> tarefas;
	
	
	public Evento(String nome){
		this.nome = nome;
		this.tarefas = new ArrayList<>();
	}
	
	public void eventoSendoCadastrado(int index){
		this.id = index;
	}
	
	public void acompanharAndamento() {
		int tam = this.tarefas.size(), tarRealizadas = 0;
		/*
		for(Tarefa tarefa : tarefas) {
			if(tarefa.getColaboradorTarefa() != null ) {
				tarRealizadas +=1;
			}
		}
		*/

		System.out.println("O evento já foi " + ((tarRealizadas*100)/tam) + "% realizado");
	}
	
	public void consultarHistoricoTarefas() {
		/*
		for(Tarefa tarefa : tarefas) {
			if(tarefa.getColaboradorTarefa() != null ) {
				 System.out.println("Tarefa "+ tarefa.getNome() + " Realizada");
			}
		}
		*/
	}
	
	
	public void cadastrarTarefa(Tarefa tarefa) {
		this.tarefas.add(tarefa);
	}
	
	public void listarTarefas() {
		for(Tarefa aux : this.tarefas) {
			System.out.println((tarefas.indexOf(aux) + 1)+ " - " + aux.getNome());
		}
	}
	
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
