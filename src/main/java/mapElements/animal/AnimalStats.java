package mapElements.animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalStats {
    private Animal animal;
    private int birthday=0;
    private List<Animal> ancestors = new ArrayList<>();
    private int nrOfChildren=0;
    private int nrOfDescendants=0;
    private boolean isTracked=false;

    public AnimalStats(){}

    public AnimalStats( int birthday, List<Animal> ancestors){
        this.birthday = birthday;
        this.ancestors = ancestors;
    }

    public int getBirthday() {
        return birthday;
    }

    public void newKidInFamily(){
        nrOfChildren++;
        nrOfDescendants++;
        ancestors.forEach(a -> a.getStats().newKidInFamily());
    }

    public List<Animal> getAncestors() {
        return ancestors;
    }

    public int getNrOfChildren() {
        return nrOfChildren;
    }

    public int getNrOfDescendants() {
        return nrOfDescendants;
    }

    public boolean isTracked() {
        return isTracked;
    }
}
