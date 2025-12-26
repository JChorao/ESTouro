package bloon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import prof.jogos2D.image.ComponenteVisual;
import mundo.Caminho;
import mundo.Mundo;

public abstract class BloonDecorator implements Bloon {

    protected Bloon bloonDecorado;

    public BloonDecorator(Bloon bloonDecorado) {
        this.bloonDecorado = bloonDecorado;
    }

    // --- MÉTODOS DELEGADOS (Repassam a chamada para o original) ---

    @Override
    public void desenhar(Graphics2D g) {
        bloonDecorado.desenhar(g);
    }

    @Override
    public void mover() {
        bloonDecorado.mover();
    }

    @Override
    public ComponenteVisual getComponente() {
        return bloonDecorado.getComponente();
    }

    @Override
    public ComponenteVisual getPopComponente() {
        return bloonDecorado.getPopComponente();
    }

    @Override
    public void setCaminho(Caminho rua) {
        bloonDecorado.setCaminho(rua);
    }

    @Override
    public Caminho getCaminho() {
        return bloonDecorado.getCaminho();
    }

    @Override
    public int getPosicaoNoCaminho() {
        return bloonDecorado.getPosicaoNoCaminho();
    }

    @Override
    public void setPosicaoNoCaminho(int pos) {
        bloonDecorado.setPosicaoNoCaminho(pos);
    }

    @Override
    public void setVelocidade(float veloc) {
        bloonDecorado.setVelocidade(veloc);
    }

    @Override
    public float getVelocidade() {
        return bloonDecorado.getVelocidade();
    }

    @Override
    public void setMundo(Mundo w) {
        bloonDecorado.setMundo(w);
    }

    @Override
    public Mundo getMundo() {
        return bloonDecorado.getMundo();
    }

    @Override
    public void setPosicao(Point p) {
        bloonDecorado.setPosicao(p);
    }

    @Override
    public Rectangle getBounds() {
        return bloonDecorado.getBounds();
    }

    @Override
    public int getResistencia() {
        return bloonDecorado.getResistencia();
    }

    @Override
    public int getValor() {
        return bloonDecorado.getValor();
    }

    @Override
    public void setValor(int val) {
        bloonDecorado.setValor(val);
    }

    @Override
    public void addBloonObserver(BloonObserver bo) {
        bloonDecorado.addBloonObserver(bo);
    }

    @Override
    public void removeBloonObserver(BloonObserver bo) {
        bloonDecorado.removeBloonObserver(bo);
    }

    // --- MÉTODOS QUE VAMOS ALTERAR NOS FILHOS ---
    
    @Override
    public int pop(int estrago) {
        return bloonDecorado.pop(estrago);
    }

    @Override
    public void explode(int damage) {
        bloonDecorado.explode(damage);
    }

}