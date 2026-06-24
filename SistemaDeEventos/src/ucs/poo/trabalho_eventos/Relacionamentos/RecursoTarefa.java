package ucs.poo.trabalho_eventos.Relacionamentos;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import ucs.poo.trabalho_eventos.Recurso.Recurso;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

public class RecursoTarefa {
    private Date horaIni;
    private Date horaFim;
    private int quantidade;
    @JsonIdentityReference(alwaysAsId = true)
    private Recurso recurso;
    @JsonIdentityReference(alwaysAsId = true)
    private Tarefa tarefa;

    public RecursoTarefa(Recurso recurso, Tarefa tarefa, int quantidade) {
        this.recurso = recurso;
        this.tarefa = tarefa;
        this.quantidade = quantidade;
    }

    public RecursoTarefa(Recurso recurso, Tarefa tarefa) {
        this.recurso = recurso;
        this.tarefa = tarefa;
    }

    public RecursoTarefa() {
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


	public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Date getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Date horaIni) {
        this.horaIni = horaIni;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }
}
