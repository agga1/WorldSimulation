package map;

import configuration.Stats;
import mapElements.animal.Animal;
import mapElements.animal.Genome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WorldStats implements Stats {
    private int day = 0;
    private HashMap<Genome, Integer> genomeVariations = new HashMap<>();

    private int allChildrenWithLivingParents = 0;
    private int lifeLengthForDeadSum = 0;
    private int nrOfDeaths = 0;
    private int nrOfAnimals = 0;
    private int nrOfGrass = 0;
    private int averageEnergy = 0;

    WorldStats(List<Animal> animals) {
        addToGenomeVariations(animals);
    }

    private void addToGenomeVariations(List<Animal> animals) {
        for (Animal animal : animals) {
            if (!genomeVariations.containsKey(animal.getGenome())) {
                genomeVariations.put(animal.getGenome(), 1);
            } else {
                int prev = genomeVariations.get(animal.getGenome());
                genomeVariations.remove(animal.getGenome());
                genomeVariations.put(animal.getGenome(), prev + 1);
            }
        }
    }

    private void removeFromGenomeVariations(List<Animal> animals) {
        for (Animal animal : animals) {
            int prev = genomeVariations.get(animal.getGenome());
            genomeVariations.remove(animal.getGenome());
            genomeVariations.put(animal.getGenome(), prev - 1);
        }
    }

    void updateNextDay(List<Animal> justDied, List<Animal> newborns, List<Animal> animals, int nrOfGrass) {
        day++;

        justDied.forEach(a -> allChildrenWithLivingParents -= a.getStats().getNrOfChildren());
        justDied.forEach(a -> a.getStats().setDeathday(day));
        lifeLengthForDeadSum = lifeLengthForDeadSum + justDied.size() * day - justDied.stream().mapToInt(a -> a.getStats().getBirthday()).sum();
        nrOfDeaths += justDied.size();
        removeFromGenomeVariations(justDied);
        addToGenomeVariations(newborns);

        allChildrenWithLivingParents += newborns.size() * 2; // for each child 2 parents have +1 child
        this.nrOfAnimals = animals.size();
        this.nrOfGrass = nrOfGrass;
        this.averageEnergy = nrOfAnimals > 0 ? animals.stream().mapToInt(Animal::getEnergy).sum() / nrOfAnimals : 0;

    }

    int getDay() {
        return day;
    }

    private double getAverageChildCount() {
        return nrOfAnimals > 0 ? (double) allChildrenWithLivingParents / nrOfAnimals : 0;
    }

    private double getAverageLifeSpan() {
        return nrOfDeaths == 0 ? 0 : (double) lifeLengthForDeadSum / nrOfDeaths;
    }

    public Genome getDominantGenome() {
        Optional<Genome> genome = genomeVariations.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
        return genome.orElse(null);
    }

    @Override
    public String toString() {
        return "World Statistics:" +
                "\n day=" + day +
                "\n dominantGenome=\n" + getDominantGenome() +
                "\n average nr of children=" + getAverageChildCount() +
                "\n average lifespan=" + getAverageLifeSpan() +
                "\n nrOfAnimals=" + nrOfAnimals +
                "\n nrOfGrass=" + nrOfGrass +
                "\n averageEnergy=" + averageEnergy
                ;
    }
}
