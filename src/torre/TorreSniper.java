package torre;

import bloon.Bloon;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import prof.jogos2D.image.ComponenteAnimado;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

public class TorreSniper extends TorreDefault {

    private Point mira;
    // Constante para definir o "infinito" visual e lógico
    private static final int DISTANCIA_INFINITA = 1000; 

    public TorreSniper(BufferedImage img) {
        super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
                20, 0, new Point(20, 0), Integer.MAX_VALUE);
        setAnguloDisparo(0);
    }

    public void setAnguloDisparo(float angulo) {
        getComponente().setAngulo(angulo);
        definirMira(angulo);
    }

    private void definirMira(double angulo) {
        double cos = Math.cos(angulo);
        double sin = Math.sin(angulo);
        Point centro = getComponente().getPosicaoCentro();
        mira = new Point((int) (centro.x + DISTANCIA_INFINITA * cos), (int) (centro.y + DISTANCIA_INFINITA * sin));
    }

    public Point getMira() {
        return mira;
    }

    @Override
    public void setPosicao(Point p) {
        super.setPosicao(p);
        definirMira(getComponente().getAngulo());
    }

    @Override
    public void desenhaRaioAcao(Graphics2D g) {
        Point centro = getComponente().getPosicaoCentro();
        Point miraInfinita = getMira();
        Composite oldComp = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.BLUE);
        Line2D.Float l = new Line2D.Float(centro, miraInfinita);
        g.setStroke(new BasicStroke(20));
        g.draw(l);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(18));
        g.draw(l);
        g.setComposite(oldComp);
    }

    @Override
    public Projetil[] atacar(List<Bloon> bloons) {
        atualizarCicloDisparo();

        // vamos buscar o desenho pois vai ser preciso várias vezes
        ComponenteMultiAnimado anim = getComponente();

        // já acabou a animação de disparar? volta à animação de pausa
        if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
            anim.setAnim(PAUSA_ANIM);
        }

        // determinar a posição do bloon alvo, consoante o método de ataque
        List<Bloon> alvosPossiveis = getBloonsInLine(bloons, getComponente().getPosicaoCentro(), getMira());
        
        if (alvosPossiveis.isEmpty()) {
            return new Projetil[0];
        }

        Bloon alvo = getEstrategia().escolherAlvo(alvosPossiveis, anim.getPosicaoCentro());

        if (alvo == null) {
            return new Projetil[0];
        }

        // se vai disparar daqui a pouco, começamos já com a animação de ataque
		// para sincronizar a frame de disparo com o disparo real
        sincronizarFrameDisparo(anim);

        if (!podeDisparar()) {
            return new Projetil[0];
        }

        // disparar
        resetTempoDisparar();

        // Criar o Projétil "Instântaneo"
        Projetil p[] = new Projetil[1];
        ComponenteVisual img = new ComponenteAnimado(new Point(),
                (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);

        
        double angulo = anim.getAngulo();
        
        
        p[0] = new Dardo(img, angulo, 10, 5); 
        
        // Posicao inicial centro do alvo
        p[0].setPosicao(alvo.getComponente().getPosicaoCentro());
        p[0].setAlcance(DISTANCIA_INFINITA); 

        return p;
    }

    @Override
    public Torre clone() {
        TorreSniper copia = (TorreSniper) super.clone();
        copia.mira = new Point(mira);
        return copia;
    }

   @Override
    public void aceita(TorreVisitor v) {
        v.visita(this);
    }
}