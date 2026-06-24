package ucs.poo.trabalho_eventos.Relacionamentos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ucs.poo.trabalho_eventos.Evento.Evento;
import ucs.poo.trabalho_eventos.Tarefa.Tarefa;

/**
 * Classe intermediária que controla a relação muitos-para-muitos entre
 * Evento e Tarefa (1 evento tem várias tarefas; a mesma tarefa pode estar em
 * vários eventos).
 *
 * Além do vínculo, esta classe guarda os dados de EXECUÇÃO daquela tarefa
 * NAQUELE evento (janela de tempo, recursos e colaboradores alocados). Assim,
 * a mesma Tarefa pode ter execuções independentes em eventos diferentes.
 *
 * As referências para Evento e Tarefa são serializadas apenas como ID
 * (via @JsonIdentityReference) para evitar duplicação/recursão no JSON.
 */
public class EventoTarefa {

    @JsonIdentityReference(alwaysAsId = true)
    private Evento evento;
    @JsonIdentityReference(alwaysAsId = true)
    private Tarefa tarefa;

    // Janela de execução desta tarefa neste evento (null = ainda não executada).
    private Date horaIni;
    private Date horaFim;

    // Recursos e colaboradores alocados a esta tarefa NESTE evento.
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

    /** Uma execução existe quando a janela de tempo foi preenchida. */
    @JsonIgnore
    public boolean foiExecutada() {
        return horaIni != null && horaFim != null;
    }

    /** Limpa a execução (datas, recursos e colaboradores), para refazer. */
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
