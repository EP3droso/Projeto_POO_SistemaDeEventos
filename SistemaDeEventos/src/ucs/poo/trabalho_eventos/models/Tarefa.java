package ucs.poo.trabalho_eventos.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Tarefa {
	private String nome;
	List<Tarefa> preRequesitos = new ArrayList<>();
	List<RecursoTarefa> recursosTarefas = new ArrayList<>();	
	
	
	public Tarefa() {
		
	}
	
	public Tarefa(String nome) {
		this.nome = nome;
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

	public String getRecursos() {
		String recursosString="";
		for(RecursoTarefa rt1 : recursosTarefas)
			recursosString += recursosString + rt1.getRecurso();		
		return recursosString;				
	}
	
	public void registrarRecursos(List<Recurso> recursosDisponiveis) {
	    Scanner sc = new Scanner(System.in);
	    boolean loop = true;
	    while(loop) {
	        System.out.println("\nDigite o ID do Recurso a ser utilizado, ou 0 para parar: ");
	        for(Recurso r1 : recursosDisponiveis) {
	            System.out.println(r1.getId() + ": " + r1.getNome() + " (Disponível: " + r1.getQuantidade() + ")");
	        }
	        
	        int id = Utilitarios.lerInteiroComVerificacao();
	        if(id == 0) {
	            loop = false;
	            break;
	        }
	        
	        Recurso recursoEncontrado = null;
	        for(Recurso r1 : recursosDisponiveis) {
	            if(r1.getId() == id) {
	                recursoEncontrado = r1;
	                break;
	            }
	        }
	        
	        if(recursoEncontrado == null) {
	            System.out.println("Id inserido é inválido!");
	            continue;
	        }
	        
	        boolean jaAdicionado = false;
	        for(RecursoTarefa rt : recursosTarefas) {
	            if(rt.getRecurso().getId() == recursoEncontrado.getId()) {
	                jaAdicionado = true;
	                break;
	            }
	        }
	        
	        if(jaAdicionado) {
	            System.out.println("Erro: O recurso '" + recursoEncontrado.getNome() + "' já foi adicionado a esta tarefa!");
	            continue;
	        }
	        
	        if(recursoEncontrado.getQuantidade() <= 0) {
	            System.out.println("Erro: Não há estoque disponível para o recurso '" + recursoEncontrado.getNome() + "'.");
	            continue;
	        }
	        
	        boolean quantidadeValida = false;
	        int quantidade = 0;
	        
	        while(!quantidadeValida) {
	            System.out.println("Quantidade de Recurso '" + recursoEncontrado.getNome() + "' a ser usado (máx de: " + recursoEncontrado.getQuantidade() + "): ");
	            quantidade = Utilitarios.lerInteiroComVerificacao();
	            
	            if(quantidade > recursoEncontrado.getQuantidade() || quantidade <= 0) {
	                System.out.println("Quantidade inválida! Tente novamente.");
	            } else {
	                quantidadeValida = true;
	            }
	        }
	        
	        recursoEncontrado.atualizarQuantidade(quantidade);
	        
	        Recurso recursoAux = new Recurso();
	        recursoAux.setId(recursoEncontrado.getId());
	        recursoAux.setNome(recursoEncontrado.getNome());
	        recursoAux.setQuantidade(quantidade); // Guarda a quantidade alocada, não o saldo do estoque
	        recursoAux.setTipo(recursoEncontrado.getTipo());
	        
	        recursosTarefas.add(new RecursoTarefa(recursoAux, this));
	        System.out.println("Recurso " + recursoEncontrado.getNome() + " adicionado com sucesso à tarefa!");
	    }
	}
	

	public List<RecursoTarefa> getRecursosTarefas() {
	    return this.recursosTarefas;
	}
	
	public void registrarExecucaoTarefa(){
	
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
			return "Tarefa= " + nome + "\nPré-Requesitos= 0 \nRecursos Usados =" + this.getRecursos();
		}
		else {
			return "Tarefa= " + nome + "\nPré-Requesitos= " + preRequesitos + "\nRecursosTarefas=" + recursosTarefas + "]";
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
