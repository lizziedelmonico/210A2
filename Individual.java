import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {
    ArrayList<Character> chromosome = new ArrayList<Character>();
    int c_0 = 8;
    int c_Max = 20;
    int g = 4;
    double m = 0.01;

    /** Choose a letter at random, in the range 
     *  from A to the number of letters indicated */
    private Character randomLetter(int num_dna_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_dna_letters)));
      }

    /** Expresses the individual's chromosome 
     *  as a String, for display purposes */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
        //return chromosome.stream().map(e->e.toString()).collect(Collectors.joining());
      }

    /**
     * Generates initial population members with a random sequence of chromosomes
     * @param c_0 initial chromosome size 
     * @param g Number of letters possible in a gene
     * @return 
     */
    public Individual(){
        for(int i = 0; i <= c_0; i++){
            this.chromosome.add(randomLetter(g));
        }
    }

    /**
     * 
     * @param parentOne parent whose suffix will create new offspring
     * @param parentTwo parent whose prefix will create new offspring
     */
    public Individual(Individual parent1, Individual parent2, double m, int g, int c_max){
        int p1_size = parent1.chromosome.size();
        int p2_size = parent2.chromosome.size();
        int num1 = ThreadLocalRandom.current().nextInt(p1_size);
        int num2 = ThreadLocalRandom.current().nextInt(p2_size);
        ArrayList offspring = new ArrayList();
        ArrayList pSuffix = new ArrayList();
        ArrayList pPrefix = new ArrayList();
        ArrayList child = new ArrayList();
        for(int i = 0; i <= num1; i++){
            pSuffix.add(parent1.chromosome.get(i)); //copy suffix of parent one to new array list
        }
        for(int i = num2; i <= p2_size; i++){
            pPrefix.add(parent2.chromosome.get(i)); //copy prefix of parent two to new array list
        }
        offspring.add(pSuffix);
        offspring.add(pPrefix);

        //if size of offspring is greater than maximum chromosome size then remove all elements after the cut off point
        if(offspring.size() > c_Max){
            for(int i = 0; i <= c_Max; i++){
                child.add(offspring.get(i));
            }
        }offspring = child;

        for(int i = 0; i < offspring.size(); i++){
            ThreadLocalRandom.current().nextDouble();
            if(ThreadLocalRandom.current().nextDouble()<m){
                offspring.set(i, randomLetter(1));
            }
        }
    }
    
    /** Calculates an individual's fitness score determined by their set of chromosomes
     * 
     * @return      the fitness score of the individual
     */
    public int fitness(){
        int fitness = 0;

        for(int i = 0; i < chromosome.size(); i++){
            Character current = chromosome.get(i);
            Character mirror = chromosome.get(chromosome.size()-i-1);
            if(current == mirror){
                fitness++;
            }else{
                fitness--;
            }
        } 
        for(int i = 1; i < chromosome.size(); i++){
            if(chromosome.get(i) == chromosome.get(i-1)){
                fitness++;
            }
        }
        return fitness;
    }

}