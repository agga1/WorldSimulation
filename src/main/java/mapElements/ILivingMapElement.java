package mapElements;

public interface ILivingMapElement extends IMapElement {
    int getEnergy();
    void track();

    boolean isTracked();
}
