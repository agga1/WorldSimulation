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
    private Set<Animal> ancestors = new HashSet<>();
    private int nrOfChildren=0;
    private int nrOfDescendants=0;

    public AnimalStats(){}

    public AnimalStats( int birthday, Set<Animal> ancestors){
        this.birthday = birthday;
        this.ancestors = ancestors;
    }

    public int getBirthday() {
        return birthday;
    }

    public void newKidInFamily(){
        ancestors.forEach(a-> a.getStats().nrOfChildren++);
        notifyAncestors(new HashSet<>());
    }

    private void notifyAncestors(Set<Animal> alreadyNotified){
        Set<Animal> toNotify =  ancestors.stream().filter(a->!a.isDead()).filter(a->!alreadyNotified.contains(a)).collect(Collectors.toSet());
        alreadyNotified.addAll(toNotify);
        toNotify.forEach(a-> a.getStats().nrOfDescendants++);
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
