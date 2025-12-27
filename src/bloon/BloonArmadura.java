package bloon;

import java.awt.Graphics2D;
import prof.jogos2D.image.ComponenteVisual;

public class BloonArmadura extends BloonDecorator {

    private int durabilidade;
    private ComponenteVisual imagemArmadura;

    public BloonArmadura(Bloon bloon, ComponenteVisual img, int durabilidade) {
        super(bloon);
        this.imagemArmadura = img;
        this.durabilidade = durabilidade;
    }

    @Override
    public void mover() {
        super.mover();
        if (durabilidade > 0) {
            imagemArmadura.setPosicaoCentro(getComponente().getPosicaoCentro());
        }
    }

    @Override
    public void desenhar(Graphics2D g) {
        super.desenhar(g);
        System.out.println("Durabilidade da armadura: " + durabilidade);
        if (durabilidade > 0) {
            imagemArmadura.desenhar(g);
        }
    }

    @Override
    public int pop(int estrago) {
        if (durabilidade > 0) {
            durabilidade--; 
            // Absorve o dano do perfurante e devolve 0
            return 0; 
        }
        // Se a armadura já partiu, o bloon sofre dano normal
        return super.pop(estrago);
    }
    
    // O explode() não é reescrito, logo as bombas passam "através" da armadura

    @Override
    public Bloon clone() {
        BloonArmadura copia = (BloonArmadura) super.clone();
        copia.imagemArmadura = imagemArmadura.clone(); 
        return copia;
    }
}