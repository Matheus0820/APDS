package visao;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dominio.Produtos;

public class Principal {

	public static void main(String[] args) {
		String path = "C://Users//Aluno//Documents//APDS//aula2//produtos.csv";
		
		ArrayList<Produtos> lista = new ArrayList<Produtos>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine(); // lê cabeçalho e descartar
			line =  br.readLine(); // tenta ler a segunda linha (primeiro produto)
			while (line != null) {
				String[] vLine = line.split(";");
				String nome = vLine[0];
				Integer quantidade = Integer.parseInt(vLine[1]);
				Double valor = Double.parseDouble(vLine[2]);
				Produtos p1 = new Produtos(nome, quantidade, valor);
				lista.add(p1);
				line = br.readLine();
			}
			
			System.out.println("Lista de Produtos:");
			for(Produtos p: lista) {
				System.out.println(p);
			}
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

}
