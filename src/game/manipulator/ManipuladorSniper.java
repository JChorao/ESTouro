package game.manipulator;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import prof.jogos2D.util.DetectorColisoes;
import torre.*;

public class ManipuladorSniper extends ManipuladorVazio {

	private static final AlphaComposite transp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);


    public ManipuladorSniper(Torre t) {
        super(t);
    }

    @Override
	public void mouseDragged(Point p) {
		double angulo = DetectorColisoes.getAngulo(getTorre().getComponente().getPosicaoCentro(), p);
		((TorreSniper) getTorre()).setAnguloDisparo((float) angulo);
	}

	@Override
	public void desenhar(Graphics2D g) {
		Composite oldComp = g.getComposite();
		Point centro = getTorre().getComponente().getPosicaoCentro();
		Point attackPoint = ((TorreSniper) getTorre()).getMira();
		Line2D.Float l = new Line2D.Float(centro, attackPoint);

		g.setComposite(transp);
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(20));
		g.draw(l);
		g.setComposite(oldComp);
	}
}