package br.com.ueg.pids.Model;

import br.com.ueg.pids.Annotations.Table;
import br.com.ueg.pids.Annotations.Campo;


@Table(nome="cargo")
public class Cargo extends GenericModel<Integer>{

	@Campo(nome="idcargo", pk=true)
	private int idcargo;
	
	@Campo(nome="nome",obrigatorio=true)
	private String nome;
	
	@Campo(nome="descricao",obrigatorio=true)
	private String descricao;
	
	@Campo(nome="departamento",obrigatorio=true)
	private String departamento;
	
	@Campo(nome="ativo",obrigatorio=true)
	private boolean ativo;

	public Cargo() {
	}
	public Cargo(int idcargo, String nome, String departamento, String descricao, Boolean ativo) {
		this.idcargo = idcargo;
		this.nome= nome;
		this.departamento = departamento;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	

	public int getIdcargo() {
		return idcargo;
	}



	public void setIdcargo(int idcargo) {
		this.idcargo = idcargo;
	}


	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getDepartamento() {
		return departamento;
	}



	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}



	public String getOrdenacao() {
		return "nome";
	}

	@Override
	public String getVariaveisPesquisarNome() {
		return "nome";
	}
	public boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
}

