package ucs.poo.trabalho_eventos.Relacionamentos;

import java.util.Date;

import ucs.poo.trabalho_eventos.Colaborador.Colaborador;

public class ColaboradorTarefa {

    private Colaborador colaborador;
    private Date horaIni;
    private Date horaFim;

    public ColaboradorTarefa(Colaborador colaborador, Date horaIni, Date horaFim) {
        if (colaborador == null)
            throw new IllegalArgumentException("É obrigatório informar o colaborador responsável pela execução.");
        if (horaIni == null || horaFim == null)
            throw new IllegalArgumentException("Horários de início e fim não podem ser nulos.");
        if (!horaFim.after(horaIni))
            throw new IllegalArgumentException("Horário de fim deve ser posterior ao de início da execução.");

        this.colaborador = colaborador;
        this.horaIni = horaIni;
        this.horaFim = horaFim;
    }

    public Colaborador getColaborador() { 
        return colaborador;
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

    @Override
    public String toString() {
        return String.format("Execução{colaborador='%s', ini=%s, fim=%s}",
                colaborador.getNome(), horaIni, horaFim);
    }
}
