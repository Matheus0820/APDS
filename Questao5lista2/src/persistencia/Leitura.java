package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dominio.DetectorDeColisao;
import dominio.Ponto;
import dominio.Retangulo;

public class Leitura {
	DetectorDeColisao d1 = new DetectorDeColisao();
	
	String arq = "C:\\Users\\Theus\\OneDrive\\Documentos\\Backup\\Escola\\2° ano - IF21 - EAJ\\APDS\\3° Bimestre\\teste.txt";
	
	String linha = ""; 
	
	ArrayList<Retangulo> ListaRetang = new ArrayList<Retangulo>();
	
	public void lerAquivo() {
		try (BufferedReader br = new BufferedReader(new FileReader(arq))) {
			int i = 0;
			while (linha != null) {
				linha = br.readLine();
				if(linha != null) {
				String vLine[] = linha.split(" ");
				Retangulo r1 = new Retangulo();
				Ponto p1 = new Ponto();
					if(i == 0) {
					p1.setX(Integer.parseInt(vLine[0]));
					p1.setY(Integer.parseInt(vLine[1]));
					r1.setP1(p1);
					p1.setX(Integer.parseInt(vLine[2]));
					p1.setY(Integer.parseInt(vLine[3]));
					r1.setP2(p1);
					
					} else {
						p1.setX(Integer.parseInt(vLine[0]));
						p1.setY(Integer.parseInt(vLine[1]));
						r1.setP1(p1);
						p1.setX(Integer.parseInt(vLine[2]));
						p1.setY(Integer.parseInt(vLine[3]));
						r1.setP2(p1);
						}
					ListaRetang.add(r1);
					i++;
				}
			}
			
		} catch (IOException e) {
			e.getMessage();
		}
		
		d1.detectarColisao(ListaRetang.get(0), ListaRetang.get(1));
		
	}

}
