package ucs.poo.trabalho_eventos.Tarefa;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ucs.poo.trabalho_eventos.main.Utilitarios;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = Tarefa.class
)
public class Tarefa {

	int id;
	private String nome;
	List<Tarefa> preRequesitos = new ArrayList<>();


	public Tarefa() {

	}

	public Tarefa(String nome,int id) {
		this.id = id;
		setNome(nome);
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
		this.nome = Utilitarios.exigirNaoVazio(nome, "Nome da tarefa");
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

	public List<Tarefa> getPreRequesitos() {
		return preRequesitos;
	}

	public void setPreRequesitos(List<Tarefa> preRequesitos) {
		this.preRequesitos = preRequesitos;
	}

	@Override
	public String toString() {
		if(preRequesitos.isEmpty()) {
			return "Tarefa= " + nome + " | Pré-Requesitos= 0";
		}
		return "Tarefa= " + nome + " | Pré-Requesitos= " + preRequesitos.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
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
		return Objects.equals(nome, other.nome);
	}

}
