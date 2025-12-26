package bloon;

public class BloonImuneExplosivos extends BloonDecorator {

    public BloonImuneExplosivos(Bloon bloonDecorado) {
        super(bloonDecorado);
    }

    @Override
    public void explode(int damage) {
        // NÃO chama o super.explode().
        // Ao deixar este método vazio, o dano da explosão é ignorado completamente.
        System.out.println("Imune a Explosão! O bloon sobreviveu.");
    }
}