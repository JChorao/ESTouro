package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import prof.jogos2D.image.ComponenteAnimado;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.DetectorColisoes;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

public class TorreSniper extends TorreDefault {

    public TorreSniper(BufferedImage img) {
        super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
                20, 6, new Point(0, 0), Integer.MAX_VALUE);
    }

    @Override
    public Projetil[] atacar(List<Bloon> bloons) {
        atualizarCicloDisparo();

        ComponenteMultiAnimado anim = getComponente();

        // Lógica de animação: volta para PAUSA após completar um ciclo de ATAQUE
        if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
            anim.setAnim(PAUSA_ANIM);
        }

        // A Torre Sniper tem alcance infinito para detetar alvos 
        // Por isso, passamos a lista completa de bloons para a estratégia
        if (bloons.isEmpty()) {
            return new Projetil[0];
        }

        // Escolhe o alvo usando a estratégia configurada (Pattern Strategy) [cite: 126, 159]
        // Como o alcance é infinito, não filtramos por raio aqui
        Bloon alvo = getEstrategia().escolherAlvo(bloons, anim.getPosicaoCentro());

        if (alvo == null) {
            return new Projetil[0];
        }

        Point posAlvo = alvo.getComponente().getPosicaoCentro();

        // Roda a torre na direção do alvo selecionado [cite: 158]
        double angle = DetectorColisoes.getAngulo(posAlvo, anim.getPosicaoCentro());
        anim.setAngulo(angle);

        // Sincroniza a animação de disparo
        sincronizarFrameDisparo(anim);

        // Verifica se o tempo de recarga (ritmo de disparo) permitiu novo tiro
        if (!podeDisparar()) {
            return new Projetil[0];
        }

        resetTempoDisparar();

        // Cálculo do ponto de saída do projétil baseado na rotação da torre
        Point disparo = getPontoDisparo();
        double cosA = Math.cos(angle);
        double senA = Math.sin(angle);
        int px = (int) (disparo.x * cosA - disparo.y * senA);
        int py = (int) (disparo.y * cosA + disparo.x * senA); 
        Point shoot = new Point(anim.getPosicaoCentro().x + px, anim.getPosicaoCentro().y + py);

        // Criação do Projétil Sniper
        // O enunciado diz que atinge o inimigo IMEDIATAMENTE com estrago 5 [cite: 154, 155]
        // Para efeito imediato, o projétil é criado já na posição do alvo
        Projetil p[] = new Projetil[1];
        
        // Carrega a imagem do dardo (ajustar caminho se necessário)
        ComponenteVisual img = new ComponenteAnimado(new Point(),
                (BufferedImage) prof.jogos2D.util.ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
        
        // Dardo Sniper: Estrago 5, atinge imediatamente (velocidade alta ou spawn no alvo) [cite: 155, 157]
        p[0] = new Dardo(img, angle, 50, 5); // Velocidade 50 para parecer imediato, dano 5
        p[0].setPosicao(posAlvo); // Spawn direto no alvo para efeito imediato 
        p[0].setAlcance(1000); // Alcance alto para garantir o impacto
        
        return p;
    }
}