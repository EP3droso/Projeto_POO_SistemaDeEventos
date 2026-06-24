package ucs.poo.trabalho_eventos.Evento;

public class TarefaRepetidaException extends Exception{
	
	public static final String MESSAGE = "Evento não pode ter Tarefas Repetidas";

	public TarefaRepetidaException() {
		super(MESSAGE);
	}

	public TarefaRepetidaException(String message) {
		super(MESSAGE + " " + message);
	}


}
