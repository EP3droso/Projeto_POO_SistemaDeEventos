package ucs.poo.trabalho_eventos.Relacionamentos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

public class EventoTarefa {

    @JsonIdentityReference(alwaysAsId = true)
    private Evento evento;
    @JsonIdentityReference(alwaysAsId = true)
    private Tarefa tarefa;

    private Date horaIni;
    private Date horaFim;
    private List<RecursoTarefa> recursosTarefas;
    private List<ColaboradorTarefa> colaboradoresTarefas;

    public EventoTarefa(Evento evento, Tarefa tarefa) {
        this();
        this.evento = evento;
        this.tarefa = tarefa;
    }

    public EventoTarefa() {
        this.recursosTarefas = new ArrayList<>();
        this.colaboradoresTarefas = new ArrayList<>();
    }

    @JsonIgnore
    public boolean foiExecutada() {
        return horaIni != null && horaFim != null;
    }

    public void limparExecucao() {
        this.horaIni = null;
        this.horaFim = null;
        this.recursosTarefas.clear();
        this.colaboradoresTarefas.clear();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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

    public List<RecursoTarefa> getRecursosTarefas() {
        return recursosTarefas;
    }

    public void setRecursosTarefas(List<RecursoTarefa> recursosTarefas) {
        this.recursosTarefas = recursosTarefas;
    }

    public List<ColaboradorTarefa> getColaboradoresTarefas() {
        return colaboradoresTarefas;
    }

    public void setColaboradoresTarefas(List<ColaboradorTarefa> colaboradoresTarefas) {
        this.colaboradoresTarefas = colaboradoresTarefas;
    }
}
