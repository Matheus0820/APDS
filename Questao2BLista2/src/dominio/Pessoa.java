package dominio;

public class Pessoa {
	private String nome; 
	private int idade; 
	private String EmpresasTrabalhadas;
	private int QuantEmpresasTrab; 
	
	
	public Pessoa() {
		
	}
	
	public Pessoa(String nome, int idade, String empresasTrabalhadas) {
		super();
		this.nome = nome;
		this.idade = idade;
		EmpresasTrabalhadas = empresasTrabalhadas;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	public String getEmpresasTrabalhadas() {
		return EmpresasTrabalhadas;
	}
	public void setEmpresasTrabalhadas(String empresasTrabalhadas) {
		EmpresasTrabalhadas = empresasTrabalhadas;
	}

	public int getQuantEmpresasTrab() {
		return QuantEmpresasTrab;
	}
	public void setQuantEmpresasTrab(int quantEmpresasTrab) {
		QuantEmpresasTrab = quantEmpresasTrab;
	}
	
	

}
