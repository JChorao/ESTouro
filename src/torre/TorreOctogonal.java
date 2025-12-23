package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre octogonal. Esta torre dispara 8 dardos.
 * ATENÇÃO: Segundo o enunciado, esta torre "Não faz uso dos modos de ataque",
 * por isso removemos a lógica de escolha de alvo.
 */
public class TorreOctogonal extends TorreDefault {

    private double baseAngle = 0;

    public TorreOctogonal(BufferedImage img) {
        super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
                20, 6, new Point(0, 0), 100);
    }

    @Override
    public Projetil[] atacar(List<Bloon> bloons) {
        atualizarCicloDisparo();

        ComponenteMultiAnimado anim = getComponente();

        if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
            anim.setAnim(PAUSA_ANIM);
        }

        // 1. Verificar se há inimigos no raio
        List<Bloon> alvosPossiveis = getBloonsInRadius(bloons, getComponente().getPosicaoCentro(), getRaioAcao());
        
        /*  Se não houver ninguém, não dispara.
         <---------------- Alterado por gemini: O switch foi removido.
         Como a torre dispara em todas as direções, não precisa de escolher UM alvo específico.
         Basta saber se existe ALGUÉM para atacar. */
		 
        if (alvosPossiveis.isEmpty())
            return new Projetil[0];

        sincronizarFrameDisparo(anim);

        if (!podeDisparar())
            return new Projetil[0];

        resetTempoDisparar();

        Point centro1 = getComponente().getPosicaoCentro();
        Point disparo = getPontoDisparo();
        // O ângulo base é fixo ou controlado pelo jogador, não depende do bloon alvo
        double cosA = Math.cos(baseAngle); 
        double senA = Math.sin(baseAngle);
        int px = (int) (disparo.x * cosA - disparo.y * senA);
        int py = (int) (disparo.y * cosA + disparo.x * senA);
        Point shoot = new Point(centro1.x + px, centro1.y + py);

        // Disparar os 8 dardos
        Projetil p[] = new Projetil[8];
        double angulo = baseAngle + Math.PI / 2;
        double incAng = Math.PI / 4;
        
        for (int i = 0; i < 8; i++) {
            ComponenteVisual img = new ComponenteAnimado(new Point(),
                    (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
            p[i] = new Dardo(img, angulo, 8, 1);
            p[i].setPosicao(shoot);
            p[i].setAlcance(getRaioAcao() + 15);
            angulo -= incAng;
        }
        return p;
    }

    public void setAngle(double angle) {
        getComponente().setAngulo(angle);
        baseAngle = angle;
    }
}