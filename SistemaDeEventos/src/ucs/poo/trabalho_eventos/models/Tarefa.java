package ucs.poo.trabalho_eventos.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Date;


import ucs.poo.trabalho_eventos.main.Utilitarios;

public class Tarefa {
	private String nome;
	List<Tarefa> preRequesitos = new ArrayList<>();
	List<RecursoTarefa> recursosTarefas = new ArrayList<>();
	List<ColaboradorTarefa> colaboradoresTarefas = new ArrayList<>();
	
	
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
	    boolean loop = true;
	    while(loop) {
	        System.out.println("\nDigite o ID do Recurso a ser utilizado/modificado, ou 0 para parar: ");
	        for(Recurso r1 : recursosDisponiveis) {
	            System.out.println(r1.getId() + ": " + r1.getNome() + " (Em Estoque: " + r1.getQuantidade() + ")");
	        }
	        
	        int id = Utilitarios.lerInteiroComVerificacao();
	        if(id == 0) {
	            loop = false;
	            break;
	        }
	        
	        Recurso recursoGlobal = null;
	        for(Recurso r1 : recursosDisponiveis) {
	            if(r1.getId() == id) {
	                recursoGlobal = r1;
	                break;
	            }
	        }
	        
	        if(recursoGlobal == null) {
	            System.out.println("Id inserido é inválido!");
	            continue;
	        }
	        
	        RecursoTarefa vinculoExistente = null;
	        for(RecursoTarefa rt : recursosTarefas) {
	            if(rt.getRecurso().getId() == recursoGlobal.getId()) {
	                vinculoExistente = rt;
	                break;
	            }
	        }
	        
	        if(vinculoExistente != null) {
	            int qtdAlocadaAtual = vinculoExistente.getRecurso().getQuantidade();
	            System.out.println("Este recurso já está nesta tarefa. Quantidade alocada atual: " + qtdAlocadaAtual);
	            System.out.println("Digite a NOVA quantidade total desejada para esta tarefa (Digite 0 para remover e devolver tudo):");
	            int novaQtdTarefa = Utilitarios.lerInteiroComVerificacao();
	            
	            if(novaQtdTarefa < 0) {
	                System.out.println("Quantidade inválida!");
	                continue;
	            }
	            
	            int diferenca = novaQtdTarefa - qtdAlocadaAtual;
	            
	            if(diferenca > recursoGlobal.getQuantidade()) {
	                System.out.println("Erro: Estoque insuficiente! Você precisa de mais " + (diferenca - recursoGlobal.getQuantidade()) + " unidades.");
	                continue;
	            }
	            
	            recursoGlobal.atualizarQuantidade(diferenca);
	            
	            if(novaQtdTarefa == 0) {
	                recursosTarefas.remove(vinculoExistente);
	                System.out.println("Recurso removido da tarefa e totalmente devolvido ao estoque.");
	            } else {
	                vinculoExistente.getRecurso().setQuantidade(novaQtdTarefa);
	                System.out.println("Quantidade atualizada com sucesso na tarefa!");
	            }
	            
	        } else {
	            if(recursoGlobal.getQuantidade() <= 0) {
	                System.out.println("Erro: Não há estoque disponível para o recurso '" + recursoGlobal.getNome() + "'.");
	                continue;
	            }
	            
	            boolean quantidadeValida = false;
	            int quantidade = 0;
	            
	            while(!quantidadeValida) {
	                System.out.println("Quantidade de Recurso '" + recursoGlobal.getNome() + "' a ser usado (máx de: " + recursoGlobal.getQuantidade() + "): ");
	                quantidade = Utilitarios.lerInteiroComVerificacao();
	                
	                if(quantidade > recursoGlobal.getQuantidade() || quantidade <= 0) {
	                    System.out.println("Quantidade inválida! Tente novamente.");
	                } else {
	                    quantidadeValida = true;
	                }
	            }
	            
	            recursoGlobal.atualizarQuantidade(quantidade);
	            
	            Recurso recursoAux = new Recurso(recursoGlobal.getId(), recursoGlobal.getNome(), recursoGlobal.getTipo(), quantidade);
	            recursosTarefas.add(new RecursoTarefa(recursoAux, this));
	            System.out.println("Recurso " + recursoGlobal.getNome() + " adicionado com sucesso à tarefa!");
	        }
	    }
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
