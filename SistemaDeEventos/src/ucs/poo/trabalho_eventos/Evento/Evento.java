package ucs.poo.trabalho_eventos.Evento;

import java.util.ArrayList;
import java.util.List;

import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

public class Evento {
		
	private int id;
	private String nome;
	private List<Tarefa> tarefas;
	private String tipo;
	
	public void setTipo(int i){
		if(i==1) {
			this.tipo = "Festa";
		}else if(i==2){
			this.tipo = "Formatura";
		}else if(i==3){
			this.tipo = "Evento Corporativo";
		}
	}

	public String getTipo(){
		return tipo;
	}
	
	public List<Tarefa> getTarefas() {
	    return tarefas;
	}
	
	public Evento(String nome, int i){
		this.nome = nome;
		this.tarefas = new ArrayList<>();
		if(i==1) {
			this.tipo = "Festa";
		}else if(i==2){
			this.tipo = "Formatura";
		}else if(i==3){
			this.tipo = "Evento Corporativo";
		}
		
		
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
	
	public Tarefa getTarefa(int id){
		try {
		return this.tarefas.get(id);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Não existe uma tarefa com esse ID");
			return null;
		}
	}
	
	public void excluirTarefa(int id) {
		Tarefa tarefaAux = this.getTarefa(id);
		if(tarefaAux != null) {
			this.tarefas.remove(tarefaAux);
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
