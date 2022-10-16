package dominio;

public class Sala {
	private PortaSala ProtaEntrada; 
	private float MetragemQuadrada; 
	private Porta PortaAuxiliar;
	
	public PortaSala getProtaEntrada() {
		return ProtaEntrada;
	}
	public void setProtaEntrada(PortaSala protaEntrada) {
		ProtaEntrada = protaEntrada;
	}
	public float getMetragemQuadrada() {
		return MetragemQuadrada;
	}
	public void setMetragemQuadrada(float metragemQuadrada) {
		MetragemQuadrada = metragemQuadrada;
	}
	public Porta getPortaAuxiliar() {
		return PortaAuxiliar;
	}
	public void setPortaAuxiliar(Porta portaAuxiliar) {
		PortaAuxiliar = portaAuxiliar;
	} 
	
	

}
