package bloon;

import java.awt.Graphics2D;
import prof.jogos2D.image.ComponenteVisual;

public class BloonEscudo extends BloonDecorator {

    private int durabilidade;
    private ComponenteVisual imagemEscudo;

    public BloonEscudo(Bloon bloon, ComponenteVisual img, int durabilidade) {
        super(bloon);
        this.imagemEscudo = img;
        this.durabilidade = durabilidade;
    }

    @Override
    public void mover() {
        super.mover();
        if (durabilidade > 0) {
            imagemEscudo.setPosicaoCentro(getComponente().getPosicaoCentro());
        }
    }

    @Override
    public void desenhar(Graphics2D g) {
        super.desenhar(g);
        if (durabilidade > 0) {
            imagemEscudo.desenhar(g);
        }
    }

    @Override
    public void explode(int damage) {
        if (durabilidade > 0) {
            durabilidade--;
            return; 
        }
        super.explode(damage);
    }

    @Override
    public Bloon clone() {
        BloonEscudo copia = (BloonEscudo) super.clone();
        copia.imagemEscudo = imagemEscudo.clone(); 
        return copia;
    }
}