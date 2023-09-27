import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

class GA_Simulation{
        int n = 100;
        int k = 15;
        int r = 100;
        int c_0 = 8;            
        int c_max = 20;
        double m = 0.01;
        int g = 4;
        ArrayList<Individual> population;

    /** Constructor for the GA_Simulation class
     */
    public GA_Simulation(){
        int n = 100;
        int k = 15;
        int r = 100;
        int c_0 = 8;            
        int c_max = 20;
        double m = 0.01;
        int g = 4;
        ArrayList<Individual> population;

    }

    /** Initializes the population of individuals
     * @return  the population of individuals
     */
    public ArrayList<Individual> init(){
        for(int i = 0; i < n; i++){
            Individual newPerson = new Individual();
            population.add(newPerson);
        }
        return population;
    }

    /** Sorts population by fitness score, best first 
     * @param pop   the ArrayList of individuals that the method is sorting
    */
    public void rankPopulation(ArrayList<Individual> pop) {
      // sort population by fitness
      Comparator<Individual> ranker = new Comparator<>() {
        // this order will sort higher scores at the front
        public int compare(Individual c1, Individual c2) {
          return (int)Math.signum(c2.fitness()-c1.fitness());
        }
      };
      pop.sort(ranker); 
    }

    /** Selects the winners from the population (determined by fitness scores), selects two winners to be parents of the next generation, and creates their children
     * 
     * @param population    the ArrayList of individuals that the method uses
     * @return              returns the next generation of individuals
     */
    public ArrayList<Individual> evolve(ArrayList<Individual> population){
        ArrayList<Individual> win = new ArrayList<>();
        for(int i = 0; i < k; i++){
            win.add(population.get(i));
        }
        for(int i = 0; i < n; i ++){
            Individual parent1 = win.get(ThreadLocalRandom.current().nextInt(0, win.size()));
            Individual parent2 = win.get(ThreadLocalRandom.current().nextInt(0, win.size()));
            while(parent1.equals(parent2)){
                parent1 = win.get(ThreadLocalRandom.current().nextInt(0, win.size()));
                parent2 = win.get(ThreadLocalRandom.current().nextInt(0, win.size()));
            }
            Individual child = new Individual(parent1, parent2, m, g, c_max);
            population.add(child);
        }
        return population;
        
        
    }

    /** Prints out the fitness scores of the strongest, weakest, kth indiviual, along with the chromosomes of the strongest.
     * 
     * @param population    the ArrayList of individuals that the method uses
     */
    public void describeGeneration(ArrayList<Individual> population){
        Individual best = population.get(0);
        int highest_fitness_rank = best.fitness();
        System.out.println("The best member of the population has a fitness score of: " + highest_fitness_rank);

        Individual meh = population.get(k);
        int meh_fitness_rank = meh.fitness();
        System.out.println("This member has a fitness score of: " + meh_fitness_rank);

        Individual worst = population.get(-1);
        int worst_fitness_rank = worst.fitness();
        System.out.println("The weakest member of the population has a fitness score of: " + worst_fitness_rank);

        System.out.println("The chromosomes of the strongest member of the population is: " + best.toString());
    }

    /** Runs the entire experiment using the methods in the class
     * 
     */
    public void run(){
        System.out.println("Round #: 1");
        ArrayList<Individual> population = init();
        rankPopulation(population);
        describeGeneration(population);
        for(int i = 2; i < r+1; i++){
            System.out.println("Round #: " + i);
            evolve(population);
            rankPopulation(population);
            describeGeneration(population);
        }

    }

    /** Creates a new GA_Simulator and runs it
     * 
     * @param args      the Array of Strings that the method uses
     */
    public static void main(String[] args){
        GA_Simulation simulation = new GA_Simulation();
        simulation.run();
    }

}