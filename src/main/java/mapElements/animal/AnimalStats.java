package mapElements.animal;

import configuration.Stats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalStats implements Stats {
    private int birthday=0;
    private int deathday=0;
    private List<Animal> ancestors = new ArrayList<>();
    private int nrOfChildren=0;
    private int nrOfDescendants=0;

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
        Set<Animal> alreadyNotified = new HashSet<>();
        ancestors.stream().filter(a->!a.isDead()).forEach(a -> a.getStats().notifyAncestors(alreadyNotified));
    }

    private void notifyAncestors(Set<Animal> alreadyNotified){
        nrOfDescendants++;
        Set<Animal> toNotify =  ancestors.stream().filter(a->!a.isDead()).filter(a->!alreadyNotified.contains(a)).collect(Collectors.toSet());
        alreadyNotified.addAll(toNotify);
        toNotify.forEach(a -> a.getStats().notifyAncestors(alreadyNotified));
    }

    public int getNrOfChildren() {
        return nrOfChildren;
    }

    public void setDeathday(int deathday) {
        this.deathday = deathday;
    }

    @Override
    public String toString() {
        return "Animal Statistics:" +
                "\n deathday=" + deathday +
                "\n nrOfChildren=" + nrOfChildren +
                "\n nrOfDescendants=" + nrOfDescendants;
    }
}
