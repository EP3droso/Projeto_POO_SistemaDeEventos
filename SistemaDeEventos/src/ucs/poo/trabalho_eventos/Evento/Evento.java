package ucs.poo.trabalho_eventos.Evento;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Evento.class
)
public class Evento {
		
	private int id;
	private String nome;
	private List<Tarefa> tarefas;
	private String tipoEvento;
	

	
	public Evento(String nome, int i,int id){
		this(); 
		this.nome = nome;
		this.id=id;
		if(i==1) {
			this.tipoEvento = "Festa";
		}else if(i==2){
			this.tipoEvento = "Formatura";
		}else if(i==3){
			this.tipoEvento = "Evento Corporativo";
		}
	}
	
	public Evento() {
		this.tarefas = new ArrayList<>();
	}
	

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public String getTipo(){
		return tipoEvento;
	}
	
	public void setTipo(String tipo) {
		this.tipoEvento = tipo;
	}
	
	public List<Tarefa> getTarefas() {
	    return tarefas;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}


	public void cadastrarTarefa(Tarefa tarefa) {
		this.tarefas.add(tarefa);
	}
	
	public void listarTarefas() {
		for(Tarefa aux : this.tarefas) {
			System.out.println((tarefas.indexOf(aux) + 1)+ " - " + aux.getNome());
		}
	}
	
	@JsonIgnore
	public Tarefa getTarefa(int id){
		try {
		return this.tarefas.get(id);
		}
		catch(IndexOutOfBoundsException e) {
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
