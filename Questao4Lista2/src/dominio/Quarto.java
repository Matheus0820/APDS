package dominio;

public class Quarto {
	private PortaQuarto Porta; 
	private boolean Banheiro;
	private float MetragemQuadrada;
	
	public PortaQuarto getPorta() {
		return Porta;
	}
	public void setPorta(PortaQuarto porta) {
		Porta = porta;
	}
	public boolean isBanheiro() {
		return Banheiro;
	}
	public void setBanheiro(boolean banheiro) {
		Banheiro = banheiro;
	}
	public float getMetragemQuadrada() {
		return MetragemQuadrada;
	}
	public void setMetragemQuadrada(float metragemQuadrada) {
		MetragemQuadrada = metragemQuadrada;
	} 
	
	

}
