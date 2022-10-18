package dominio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Empresa {
	private String nome; 
	private String DataEntrada; 
	private String DataSaida;
	private String TempoTrabalhado;
	private int anos;
	private int meses; 
	private int dias;
	
	
	
	public Empresa () {
		
	}
	
	public Empresa(String nome, String dataEntrada, String dataSaida) {
		super();
		this.nome = nome;
		DataEntrada = dataEntrada;
		DataSaida = dataSaida;
		
	}
	

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDataEntrada() {
		return DataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		DataEntrada = dataEntrada;
	}
	
	public String getDataSaida() {
		return DataSaida;
	}
	public void setDataSaida(String dataSaida) {
		DataSaida = dataSaida;
	} 
	
	public String getTempoTrabalhado() {
		return TempoTrabalhado;
	}
	public void setTempoTrabalhado(String tempoTrabalhado) {
		TempoTrabalhado = tempoTrabalhado;
	}

	public int getAnos() {
		return anos;
	}
	public void setAnos(int anos) {
		this.anos = anos;
	}

	public int getMeses() {
		return meses;
	}
	public void setMeses(int meses) {
		this.meses = meses;
	}

	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	
	

	public void CalcularTempoTrabalhado() {
		
		LocalDate DaEntr = LocalDate.parse(DataEntrada, DateTimeFormatter.ISO_LOCAL_DATE); //mudar para classe principal;
		LocalDate DaSaid = LocalDate.parse(DataSaida, DateTimeFormatter.ISO_LOCAL_DATE);
		
		Period tempo = Period.between(DaSaid, DaEntr);
		
		int ano = Math.abs(tempo.getYears());
		int mes = Math.abs(tempo.getMonths());
		int dia = Math.abs(tempo.getDays());
		
		anos = ano;
		meses = mes; 
		dias = dia;
		
		TempoTrabalhado = ano + " Anos, " + mes + " meses e " + dia + " dias";	
	}
}
