package mapElements.animal;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Genome {
    private static final int genomeLength = 32;
    private static final Collection<Integer> possibleGenes = List.of(0, 1, 2, 3, 4, 5, 6, 7);
    private int[] genome;

    Genome() {  // generate new random genome
        int[] genes = new int[genomeLength];
        for (int i = 0; i < genomeLength; i++) {
            genes[i] = ThreadLocalRandom.current().nextInt(0, 8);
        }
        this.genome = standardizeGenes(genes);
    }

    Genome(Genome parent1, Genome parent2) {
        int div1 = ThreadLocalRandom.current().nextInt(1, genomeLength - 2);
        int div2 = ThreadLocalRandom.current().nextInt(div1, genomeLength - 1); // at least 1 gene in each frag

        int[] newGenes = new int[genomeLength];
        System.arraycopy(parent1.genome, 0, newGenes, 0, div1);
        System.arraycopy(parent2.genome, div1, newGenes, div1, div2 - div1);
        System.arraycopy(parent1.genome, div2, newGenes, div2, genomeLength - div2);

        this.genome = standardizeGenes(newGenes);
    }

    private static int[] standardizeGenes(int[] genes) {
        Arrays.sort(genes);
        while (!Arrays.stream(genes).boxed().collect(Collectors.toSet()).containsAll(possibleGenes)) {
            for (int i = 0; i < 8; i++) {  // this function has high probability of placing new gene without generating new collision
                final int a = i;
                if (Arrays.stream(genes).noneMatch(g -> g == a)) {
                    int flipIdx = new Random().nextInt(genomeLength);
                    genes[flipIdx] = i;
                }
            }
        }
        Arrays.sort(genes);
        return genes;
    }

    int getGeneAt(int idx) {
        return genome[idx];
    }

    int[] getGenome() {
        return genome;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Genome genome1 = (Genome) object;
        return Arrays.equals(genome, genome1.genome);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genome);
    }

    @Override
    public String toString() {
        return Arrays.toString(genome);
    }
}
