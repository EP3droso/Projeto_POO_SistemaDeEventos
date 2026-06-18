package ucs.poo.trabalho_eventos.Colaborador;

public class Functions {
	
/*
	public void cadastrarColaboradores(Colaborador colaborador) {
		this.colaboradores.add(colaborador);
	}

    public void cadastrarColaborador(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }
    
    public void alterarColaborador(int id, String novoNome, String novoEmail, String novaSenha, String novaFuncao) {
    	Colaborador colaborador = getColaborador(id);
    	
		if(colaborador == null) {
			System.out.println("Colaborador não encontrado.");
			return;
		}
		
		colaborador.setNome(novoNome);
		colaborador.setEmail(novoEmail);
		colaborador.setSenha(novaSenha);
		colaborador.setFuncao(novaFuncao);	
		
		System.out.println("Colaborador alterado com sucesso!");
	}
    public void mostrarColaboradores() {
        if(colaboradores.isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
            return;
        }
        System.out.println("Colaboradores cadastrados:");
        for(Colaborador colaborador : colaboradores) {
            System.out.println("ID: " + colaborador.getId()+ " | Nome: " + colaborador.getNome());
        }
    }
    
    public void buscarColaboradorPorNome(String nome) {
        boolean encontrado = false;
        for(Colaborador colaborador : colaboradores) {
            if(colaborador.getNome().equalsIgnoreCase(nome)) {
                System.out.println(colaborador);
                encontrado = true;
            }
        }

        if(!encontrado) {
            System.out.println("Nenhum colaborador encontrado com esse nome.");
        }
    }
    
    public Colaborador getColaborador(String nome) {
        for(Colaborador colaborador : colaboradores) {
            if(nome.equals(colaborador.getNome())) {
                return colaborador;
            }
        }

        return null;
    }

    public Colaborador getColaborador(int id) {
        for(Colaborador colaborador : colaboradores) {
            if(colaborador.getId() == id) {
                return colaborador;
            }
        }

        return null;
    }

    public void excluirColaborador(int id) {
        Colaborador colaborador = getColaborador(id);
        if(colaborador == null) {
            System.out.println("Colaborador não encontrado.");
            return;
        }
        colaboradores.remove(colaborador);
        System.out.println("Colaborador removido com sucesso!");
    }
	*/
}
