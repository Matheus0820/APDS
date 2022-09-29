package dominio;

import java.io.Serializable;

public class Produtos implements Serializable {
	private String nome;
	private Integer quantidade; 
	private double valor;
	
	public Produtos() {
		
	}
	
	public Produtos(String nome, Integer quantidade, double valor) {
		super();
		this.nome = nome;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Produtos [nome=" + nome + ", quantidade=" + quantidade + ", valor=" + valor + "]";
	}
	
	
	

}
