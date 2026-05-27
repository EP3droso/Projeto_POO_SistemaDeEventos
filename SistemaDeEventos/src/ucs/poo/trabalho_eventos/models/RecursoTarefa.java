package ucs.poo.trabalho_eventos.models;
import java.util.Date;

public class RecursoTarefa {
    private Date horaIni;
    private Date horaFim;
    private Recurso recurso;
    private Tarefa tarefa;

    public RecursoTarefa(Recurso recurso, Tarefa tarefa) {
        this.recurso = recurso;
        this.tarefa = tarefa;
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
