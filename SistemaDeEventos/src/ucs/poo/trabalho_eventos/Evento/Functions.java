package ucs.poo.trabalho_eventos.Evento;

public class Functions {
	/*
	
	public boolean eventosIsEmpty(){
		return this.eventos.isEmpty();
	}
	
	public void cadastrarEvento(Evento evento) {
		this.eventos.add(evento);
		evento.eventoSendoCadastrado(this.eventos.indexOf(evento));
	}
	
	public void listarEventos() {
		System.out.println("Os eventos cadastrados são:");
		for(Evento evento : this.eventos){
			System.out.println("Evento " + evento.getNome() + "  ID: " + eventos.indexOf(evento));
		}
	}
	
	public Evento getEvento(int id){
		try {
		return this.eventos.get(id);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Não existe um evento com esse ID");
			return null;
		}
	}
	
	public Evento getEvento(String nome){
		
		for(Evento aux : this.eventos) {
			if(nome.equals(aux.getNome())) {
				return aux;
			}
		}
		
		System.out.println("Não existe um evento com esse ID");
		return null;
		
	}
	
	public void excluirEvento(int id) {
		Evento eventoAux = this.getEvento(id);
		if(eventoAux != null) {
			this.eventos.remove(eventoAux);
		}
		for(Evento eventoAux2 : this.eventos) {
			eventoAux2.eventoSendoCadastrado(this.eventos.indexOf(eventoAux));
		}
	}

	public void alterarEvento(int id, String nome) {
		Evento eventoAux = this.getEvento(id);
		if(eventoAux != null) {
			eventoAux.setNome(nome);
			return;
		}
	}
	*/
}
