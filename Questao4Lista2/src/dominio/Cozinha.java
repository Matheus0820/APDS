package dominio;

public class Cozinha {
	private PortaCozinha Porta; 
	private boolean Americana; 
	private float MetragemQuadrada;
	
	public PortaCozinha getPorta() {
		return Porta;
	}
	public void setPorta(PortaCozinha porta) {
		Porta = porta;
	}
	public boolean isAmericana() {
		return Americana;
	}
	public void setAmericana(boolean americana) {
		Americana = americana;
	}
	public float getMetragemQuadrada() {
		return MetragemQuadrada;
	}
	public void setMetragemQuadrada(float metragemQuadrada) {
		MetragemQuadrada = metragemQuadrada;
	} 

}
