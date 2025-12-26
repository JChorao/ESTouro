package torre;

import bloon.Bloon;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Objects;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.util.DetectorColisoes;
import torre.estrategia.*;

/**
 * Classe que implementa os comportamentos e variáveis comuns a todos as torres.
 * Também possui alguns métodos auxiliares para as várias torres
 */
public abstract class TorreDefault implements Torre {

	private Mundo mundo; 
	private ComponenteMultiAnimado imagem; 

	private int modoAtaque = ATACA_PRIMEIRO; 
	private EstrategiaAtaque estrategia;     // <---------------- Alterado por gemini (Variável para guardar a estratégia)

	private int raioAtaque; 
	private Point pontoDisparo; 

	protected static final int PAUSA_ANIM = 0; 
	protected static final int ATAQUE_ANIM = 1; 
	private int ritmoDisparo; 
	private int proxDisparo; 
	private int frameDisparoDelay; 

	public TorreDefault(ComponenteMultiAnimado cv, int ritmoDisparo, int delayDisparo, Point pontoDisparo,
			int raioAtaque) {
		imagem = Objects.requireNonNull(cv);
		this.ritmoDisparo = ritmoDisparo;
		this.proxDisparo = 0;
		this.frameDisparoDelay = delayDisparo;
		this.pontoDisparo = Objects.requireNonNull(pontoDisparo);
		this.raioAtaque = raioAtaque;
		
		this.estrategia = new AtaquePrimeiro(); // <---------------- Alterado por gemini (Define a estratégia inicial padrão)
	}

	// Método novo para as subclasses usarem
	protected EstrategiaAtaque getEstrategia() { // <---------------- Alterado por gemini (Método para as filhas obterem a estratégia)
		return estrategia;
	}

	@Override
	public void setModoAtaque(int modo) {
		this.modoAtaque = modo;
		// O Switch foi movido para aqui para centralizar a decisão
		switch (modo) { // <---------------- Alterado por gemini (Bloco switch adicionado aqui)
			case ATACA_PRIMEIRO: this.estrategia = new AtaquePrimeiro(); break;
			case ATACA_ULTIMO:   this.estrategia = new AtaqueUltimo(); break;
			case ATACA_PERTO:    this.estrategia = new AtaquePerto(); break;
			case ATACA_JUNTOS:   this.estrategia = new AtaqueJuntos(); break;
			default:             this.estrategia = new AtaquePrimeiro(); break;
		}
	}

	@Override
	public int getModoAtaque() {
		return modoAtaque;
	}

	// --- O RESTO DO CÓDIGO PERMANECE IGUAL ---

	protected void atualizarCicloDisparo() {
		proxDisparo++;
	}

	protected boolean podeDisparar() {
		return proxDisparo >= ritmoDisparo;
	}

	protected int resetTempoDisparar() {
		return proxDisparo = 0;
	}

	protected void sincronizarFrameDisparo(ComponenteMultiAnimado anim) {
		if (proxDisparo + frameDisparoDelay >= ritmoDisparo) {
			if (anim.getAnim() != ATAQUE_ANIM) {
				anim.setAnim(ATAQUE_ANIM);
				anim.reset();
			}
		}
	}

	protected void setComponente(ComponenteMultiAnimado v) {
		imagem = v;
	}

	@Override
	public void setMundo(Mundo w) {
		mundo = w;
	}

	@Override
	public Mundo getMundo() {
		return mundo;
	}

	@Override
	public ComponenteMultiAnimado getComponente() {
		return imagem;
	}

	@Override
	public void setPosicao(Point p) {
		imagem.setPosicaoCentro(p);
	}

	@Override
	public Point getPontoDisparo() {
		return pontoDisparo;
	}

	@Override
	public void setRaioAcao(int raio) {
		raioAtaque = raio;
	}

	@Override
	public int getRaioAcao() {
		return raioAtaque;
	}

	@Override
	public void desenhar(Graphics2D g) {
		imagem.desenhar(g);
	}

	@Override
	public void desenhaRaioAcao(Graphics2D g) {
		Point p = getComponente().getPosicaoCentro();
		Composite oldComp = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.WHITE);
		g.fillOval(p.x - raioAtaque, p.y - raioAtaque, 2 * raioAtaque, 2 * raioAtaque);
		g.setColor(Color.BLUE);
		g.drawOval(p.x - raioAtaque, p.y - raioAtaque, 2 * raioAtaque, 2 * raioAtaque);
		g.setComposite(oldComp);
	}

	protected List<Bloon> getBloonsInRadius(List<Bloon> bloons, Point center, int radius) {
		return bloons.stream().filter(b -> DetectorColisoes.intersectam(b.getBounds(), center, radius)).toList();
	}

	protected List<Bloon> getBloonsInLine(List<Bloon> bloons, Point p1, Point p2) {
		return bloons.stream().filter(b -> b.getBounds().intersectsLine(p1.x, p1.y, p2.x, p2.y)).toList();
	}

	@Override
	public Torre clone() {
		try {
			TorreDefault copia = (TorreDefault) super.clone();
			copia.imagem = imagem.clone();
			copia.mundo = null;
			copia.pontoDisparo = new Point(pontoDisparo);
			copia.setModoAtaque(this.modoAtaque); // <---------------- Alterado por gemini (Garante que a cópia tem a estratégia certa)
			return copia;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}