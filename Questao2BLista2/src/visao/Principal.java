package visao;

import java.util.ArrayList;

import dominio.Empresa;
import dominio.Pessoa;

public class Principal {

	public static void main(String[] args) {
		String lista = ""; 
		
		ArrayList<Pessoa> Lpessoa = new ArrayList<Pessoa>();
		//ArrayList<Empresa> Lempresa = new ArrayList<Empresa>();
		
		String pessoas[][] = {
				{"Afranio", "32", "1"},
				{"Carlos", "49", "2"},
				{"Alessandra", "44", "4"},
				{"Severino", "35", "3"},
				{"Josenalde", "43", "5"}
				};
		String Empresas[][][] = {
				{{"EAJ", "2003-10-20", "2015-03-12"}}, 
				{{"UFRN", "1999-09-11", "2010-02-12"}, {"IFPM", "2011-05-10", "2019-12-30"}},
				{{"IFPB", "2001-08-23", "2003-01-25"}, {"UFPB", "2004-05-29", "2008-07-09"}, {"Meta", "2008-11-04", "2014-01-06"}, {"Vale do Silicio", "2016-04-01", "2020-02-28"}},
				{{"GameSport", "2007-05-01", "2009-10-26"}, {"EAJSOFT", "2011-03-09", "2015-01-21"}, {"TecInfo", "2017-08-07", "2021-04-17"}}, 
				{{"RNDev", "1999-03-05", "2001-08-08"}, {"SOFTBR", "2002-12-16", "2007-06-26"}, {"SegBR", "2008-02-12", "2010-04-04"}, {"NASA", "2012-08-03", "2016-03-08"}, {"SpaceX", "2017-12-09", "2021-09-18"}}				
				};
		
		
		for(int i = 0; i < 5; i++ ) {
			Pessoa p1 = new Pessoa();
			for(int j = 0; j < 3; j++) {
				if(j == 0) {
					p1.setNome(pessoas[i][j]);
				} else if (j == 1) {
					p1.setIdade(Integer.parseInt(pessoas[i][j]));
				} else {
					p1.setQuantEmpresasTrab(Integer.parseInt(pessoas[i][j]));
				}
			}
			Lpessoa.add(p1);
		}
		
		
		
		for(int i = 0; i < 5; i++) {
			switch(i) {
			case 0: 
				lista += "_________\n\nNome: " + Lpessoa.get(0).getNome() + "\t Idade: " + Lpessoa.get(0).getIdade()  + "\t --> \n";
				break; 
			case 1: 
				lista += "_________\n\nNome: " + Lpessoa.get(1).getNome() + "\t Idade: " + Lpessoa.get(1).getIdade()  + "\t --> \n";
				break; 
			case 2: 
				lista += "_________\n\nNome: " + Lpessoa.get(2).getNome() + "\t Idade: " + Lpessoa.get(2).getIdade()  + "\t --> \n";
				break; 
			case 3: 
				lista += "_________\n\nNome: " + Lpessoa.get(3).getNome() + "\t Idade: " + Lpessoa.get(3).getIdade()  + "\t --> \n";
				break; 
			case 4: 
				lista += "_________\n\nNome: " + Lpessoa.get(4).getNome() + "\t Idade: " + Lpessoa.get(4).getIdade()  + "\t --> \n";
				break;
			}
			int anos = 0; 
			int meses = 0;
			int dias = 0;
			for(int j = 0; j < Empresas[i].length; j++) {
				int quantem = Empresas[j].length;
				Empresa e1 = new Empresa();
				for(int z = 0; z < 3; z++) {
					if(z == 0) {
						e1.setNome(Empresas[i][j][z]);
					} else if (z == 1) {
						e1.setDataEntrada(Empresas[i][j][z]);
					} else {
						e1.setDataSaida(Empresas[i][j][z]);
					}
					
				}
				e1.CalcularTempoTrabalhado();
				anos += e1.getAnos();
				meses += e1.getMeses();
				dias += e1.getDias();
				switch(quantem) {
				case 1: 
					//e1.CalcularTempoTrabalhado();
					lista += "Nome Empresa: " + e1.getNome();
					lista += "\nData de Entrada: " + e1.getDataEntrada() + "\t Data de Saida: " + e1.getDataSaida();
					lista += "\nPeriodo Trabalhado na Empresa: " + e1.getTempoTrabalhado() + " \n---\n";
					System.out.println(lista);
					break;
				case 2: 
					lista += "Nome Empresa: " + e1.getNome();
					lista += "\nData de Entrada: " + e1.getDataEntrada() + "\t Data de Saida: " + e1.getDataSaida();
					lista += "\nPeriodo Trabalhado na Empresa: " + e1.getTempoTrabalhado() + " \n---\n";
					break; 
				case 3: 
					lista += "Nome Empresa: " + e1.getNome();
					lista += "\nData de Entrada: " + e1.getDataEntrada() + "\t Data de Saida: " + e1.getDataSaida();
					lista += "\nPeriodo Trabalhado na Empresa: " + e1.getTempoTrabalhado() + " \n---\n";
					break;
				case 4: 
					lista += "Nome Empresa: " + e1.getNome();
					lista += "\n Data de Entrada: " + e1.getDataEntrada() + "\t Data de Saida: " + e1.getDataSaida();
					lista += "\n Periodo Trabalhado na Empresa: " + e1.getTempoTrabalhado() + " \n---\n";
					break; 
				case 5: 
					lista += "Nome Empresa: " + e1.getNome();
					lista += "\nData de Entrada: " + e1.getDataEntrada() + "\t Data de Saida: " + e1.getDataSaida();
					lista += "\nPeriodo Trabalhado na Empresa: " + e1.getTempoTrabalhado() + " \n---\n";
					break;
				}
				//Lempresa.add(e1);
			}
			lista += "\n | Tempo Total de Trebalho: " + (anos + (dias / 365) + (meses / 12))  + " anos, " + (((dias % 365) / 30 + (meses % 12))%12)  + " meses, " + (((dias % 365) % 30)) + " dias  |\n";
 		}
		
		System.out.println(lista); 

	}

}
