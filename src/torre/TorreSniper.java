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
    private static final int DISTANCIA_INFINITA = 3000; 

    public TorreSniper(BufferedImage img) {
        // O raio de ação lógico é MAX_VALUE, mas visualmente e para cálculos usamos DISTANCIA_INFINITA
        super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
                20, 0, new Point(20, -3), Integer.MAX_VALUE);
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
        // A mira deve ser muito longe para cobrir o mapa todo (Alcance Infinito)
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
        Point miraInfinita = getMira(); // Agora getMira() já devolve o ponto longe

        Composite oldComp = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        // Desenhar a linha Grossa até ao infinito (Visual pedido)
        g.setColor(Color.BLUE);
        Line2D.Float l = new Line2D.Float(centro, miraInfinita);

        g.setStroke(new BasicStroke(20)); // Traço muito grosso
        g.draw(l);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(18)); // Interior branco
        g.draw(l);

        g.setComposite(oldComp);
    }

    @Override
    public Projetil[] atacar(List<Bloon> bloons) {
        atualizarCicloDisparo();

        ComponenteMultiAnimado anim = getComponente();

        // Animação: volta para PAUSA após completar ciclo
        if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
            anim.setAnim(PAUSA_ANIM);
        }

        // 1. Obter bloons na Linha de Visão Infinita
        // Usamos getMira() que agora representa um ponto muito distante
        List<Bloon> alvosPossiveis = getBloonsInLine(bloons, getComponente().getPosicaoCentro(), getMira());
        
        if (alvosPossiveis.isEmpty()) {
            return new Projetil[0];
        }

        // 2. Escolher o alvo baseado na ESTRATÉGIA (Primeiro, Forte, Último, etc.)
        // O enunciado diz: "Faz uso de todos os modos de ataque"
        Bloon alvo = getEstrategia().escolherAlvo(alvosPossiveis, anim.getPosicaoCentro());

        if (alvo == null) {
            return new Projetil[0];
        }

        // Sincronizar animação
        sincronizarFrameDisparo(anim);

        if (!podeDisparar()) {
            return new Projetil[0];
        }

        // Disparar
        resetTempoDisparar();

        // 3. Criar o Projétil "Instântaneo"
        Projetil p[] = new Projetil[1];
        ComponenteVisual img = new ComponenteAnimado(new Point(),
                (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);

        // "Atinge o inimigo imediatamente" e "Assume que o dardo é atirado diretamente de dentro do bloon"
        // Definimos a velocidade (ex: 20) para ele se comportar como dardo normal APÓS nascer no alvo
        double angulo = anim.getAngulo();
        
        // Dano = 5 (conforme enunciado)
        p[0] = new Dardo(img, angulo, 20, 5); 
        
        // A posição inicial do dardo é o CENTRO DO INIMIGO (Impacto Imediato)
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
    public void aceitar(TorreVisitor v) {
        v.visita(this);
    }
}