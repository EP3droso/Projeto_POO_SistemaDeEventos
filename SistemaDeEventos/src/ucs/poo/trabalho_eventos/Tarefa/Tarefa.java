package ucs.poo.trabalho_eventos.Tarefa;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;
import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Relacionamentos.ColaboradorTarefa;
import ucs.poo.trabalho_eventos.Relacionamentos.RecursoTarefa;
import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Tarefa {
	
	int id = 0;
	private String nome;
	List<Tarefa> preRequesitos = new ArrayList<>();
	List<RecursoTarefa> recursosTarefas = new ArrayList<>();
	List<ColaboradorTarefa> colaboradoresTarefas = new ArrayList<>();
	
	
	public Tarefa() {
		
	}
	
	public Tarefa(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRecursosTarefas(List<RecursoTarefa> recursosTarefas) {
		this.recursosTarefas = recursosTarefas;
	}

	public void setColaboradoresTarefas(List<ColaboradorTarefa> colaboradoresTarefas) {
		this.colaboradoresTarefas = colaboradoresTarefas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean setPreRequisito(Tarefa t1) {
	    if (t1 == null) {
	        return false;
	    }

	    if (t1.getNome().equalsIgnoreCase(this.getNome())) {
	        System.out.println("Erro: Uma tarefa não pode ser pré-requisito dela mesma.");
	        return false;
	    }

	    boolean achou = false;
	    for(Tarefa taux : preRequesitos) {
	        if(taux.getNome().equalsIgnoreCase(t1.getNome())) {
	            achou = true;
	            break;
	        }
	    }
	    
	    if(achou) {
	        System.out.println("Tarefa já implementada como pré-requisito");
	        return false;
	    }

	    if (verificaCiclo(t1, this)) {
	        System.out.println("Erro: Dependência circular detectada! '" + t1.getNome() + "' não pode depender de '" + this.getNome() + "' indiretamente.");
	        return false;
	    }

	    preRequesitos.add(t1);
	    return true;
	}

	public boolean verificaCiclo(Tarefa atual, Tarefa alvo) {
	    for (Tarefa pre : atual.getPreRequesitos()) {
	        if (pre.getNome().equalsIgnoreCase(alvo.getNome())) {
	            return true;
	        }
	        if (verificaCiclo(pre, alvo)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	@JsonIgnore
	public String getRecursos() {
		String recursosString="";
		for(RecursoTarefa rt1 : recursosTarefas)
			recursosString += recursosString + rt1.getRecurso();		
		return recursosString;				
	}
	
		
	public void registrarExecucaoTarefa(Colaborador colaborador, Date horaIni, Date horaFim){
	    ColaboradorTarefa execucao = new ColaboradorTarefa(colaborador, horaIni, horaFim);
	    colaboradoresTarefas.add(execucao);
	    System.out.println("Execução registrada com sucesso!");
	}
	

	public List<RecursoTarefa> getRecursosTarefas() {
	    return this.recursosTarefas;
	}
	
	public List<ColaboradorTarefa> getColaboradoresTarefas() {
	    return colaboradoresTarefas;
	}
	
	public List<Tarefa> getPreRequesitos() {
		return preRequesitos;
	}

	public void setPreRequesitos(List<Tarefa> preRequesitos) {
		this.preRequesitos = preRequesitos;
	}
	
	@Override
	public String toString() {
		if(preRequesitos.isEmpty()) {
			return "Tarefa= " + nome + "Pré-Requesitos= 0 Recursos Usados =" + this.getRecursos();
		}
		else {
			return "Tarefa= " + nome + "Pré-Requesitos= " + preRequesitos + "RecursosTarefas=" + recursosTarefas + "]";
		}
	}
	@Override
	public int hashCode() {
		return Objects.hash(nome, preRequesitos, recursosTarefas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(preRequesitos, other.preRequesitos)
				&& Objects.equals(recursosTarefas, other.recursosTarefas);
	}
	
}
