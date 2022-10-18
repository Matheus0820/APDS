package dominio;

import java.awt.Rectangle;

public class DetectorDeColisao {
	
	public void detectarColisao(Retangulo rt1, Retangulo rt2) {
		
		Rectangle r1 = new Rectangle(rt1.getP1().getX(), rt1.getP1().getY(), rt1.getP2().getX(), rt1.getP2().getY());
		Rectangle r2 = new Rectangle(rt2.getP1().getX(), rt2.getP1().getY(), rt2.getP2().getX(), rt2.getP2().getY());
		
		if(r1.intersects(r2)) {
			System.out.println("Colide!");
		} else {
			System.out.println("Não colide!");
		}
		
	}

}
