package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import prof.jogos2D.image.*;
import prof.jogos2D.util.DetectorColisoes;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.BombaImpacto;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre canhão. 
 */
public class TorreCanhao extends TorreDefault {

	public TorreCanhao(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
				30, 0, new Point(25, 0), 120);
	}

	@Override
	public Projetil[] atacar(List<Bloon> bloons) {
		atualizarCicloDisparo();

		ComponenteMultiAnimado anim = getComponente();

		if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
			anim.setAnim(PAUSA_ANIM);
		}

		// determinar a posição do bloon alvo
		Point posAlvo = null;
		
		// ver quais os bloons que estão ao alcance
		List<Bloon> alvosPossiveis = getBloonsInRadius(bloons, getComponente().getPosicaoCentro(), getRaioAcao());
		if (alvosPossiveis.size() == 0)
			return new Projetil[0];

		// O Switch gigante foi removido e substituído por esta linha:
		Bloon alvo = getEstrategia().escolherAlvo(alvosPossiveis, getComponente().getPosicaoCentro()); // <---------------- Alterado por gemini

		if (alvo == null) // <---------------- Alterado por gemini
			return new Projetil[0];

		posAlvo = alvo.getComponente().getPosicaoCentro(); // <---------------- Alterado por gemini

		// ver o ângulo que o alvo faz com a torre
		double angle1 = DetectorColisoes.getAngulo(posAlvo, anim.getPosicaoCentro());
		anim.setAngulo(angle1);

		// ajustar o ângulo
		double angle = angle1;

		sincronizarFrameDisparo(anim);

		if (!podeDisparar())
			return new Projetil[0];

		resetTempoDisparar();

		Point disparo = getPontoDisparo();
		double cosA = Math.cos(angle);
		double senA = Math.sin(angle);
		int px = (int) (disparo.x * cosA - disparo.y * senA);
		int py = (int) (disparo.y * cosA + disparo.x * senA); 
		Point shoot = new Point(getComponente().getPosicaoCentro().x + px, getComponente().getPosicaoCentro().y + py);

		Projetil p[] = new Projetil[1];
		ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/bomba.gif"));
		p[0] = new BombaImpacto(img, angle, 12, 2, getMundo());
		p[0].setPosicao(shoot);
		p[0].setAlcance(getRaioAcao() + 20);
		return p;
	}
}