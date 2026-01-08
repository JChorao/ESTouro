package bloon;

public class BloonImuneExplosivos extends BloonDecorator {

    public BloonImuneExplosivos(Bloon bloonDecorado) {
        super(bloonDecorado);
    }

    @Override
    public void explode(int damage) {
        // Ignora o dano de explosivos
    }
}