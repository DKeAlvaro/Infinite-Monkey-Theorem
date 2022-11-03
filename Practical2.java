import java.util.Random;
import java.util.Arrays;

/**
 * Some very basic stuff to get you started. It shows basically how each
 * chromosome is built.
 * 
 * @author Jo Stevens
 * @version 1.0, 14 Nov 2008
 * 
 * @author Alard Roebroeck
 * @version 1.1, 12 Dec 2012
 * 
 */

public class Practical2 {
	public static int popsize = 100;
	public static int parentsLength = 40;
	public static double mutationRate = 0.1;  

	public static Individual[] parents = new Individual[parentsLength];
	static final String TARGET = "HELLO WORLD";
	static char[] alphabet = new char[27];

	public static void main(String[] args) {
		entrypoint();
	}

	public static void entrypoint(){
		Individual[] currentGeneration = randomGeneration(popsize); 
		HeapSort sorter = new HeapSort();
		int generationCounter = 1;
		boolean solutionFound = false;

		while (!solutionFound) {
			System.out.println();
			System.out.println("Generation number " + generationCounter);
			for (Individual i : currentGeneration) {
				i.setFitness(myFitness(i)); // sets each individual fitness of the current generation	
				if(i.genoToPhenotype().equals(TARGET)){
					solutionFound = true;
				}
			}
			sorter.sort(currentGeneration);			
			printGeneration(currentGeneration);		

			if(!solutionFound){
				//Creates the parents of the generation based on the elitist selection
				for (int i = 0; i < parents.length; i++) { 
					parents[i] = currentGeneration[i].clone();
				}
				currentGeneration = applyMutation(crossover(parents));
				generationCounter++;
			}

		}
		System.out.println(currentGeneration[0].genoToPhenotype() + " found on generation "+ generationCounter);
	}

	public static int myFitness(Individual individual) {
		int fitness = 0;
		for (int i = 0; i < TARGET.length(); i++) {
			if (individual.getChromosome()[i] == TARGET.charAt(i)) { 
				fitness += 1;
			}
		}
		return fitness;

	}

	public static Individual[] randomGeneration(int popSize) {
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' ';
		Random generator = new Random(System.currentTimeMillis());
		Individual[] population = new Individual[popSize];
		for (int i = 0; i < popSize; i++) {
			char[] tempChromosome = new char[TARGET.length()]; 

			for (int j = 0; j < TARGET.length(); j++) {
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];
			}
			population[i] = new Individual(tempChromosome); 
		}
		return population;
	}

	/**
	 *  Prints the most representative individuals of a generation
	 * @param population
	 */
	public static void printGeneration(Individual[] population)  {
		int individualnumber = 1;
		for (int i = 0; i < 5; i++) {
			System.out.println(population[i].genoToPhenotype() + " individual number " + individualnumber
					+ " fitness " + population[i].getFitness());
			individualnumber++;

		}
	}

	/**
	 * Creates a new generation of individuals
	 * @param parents Parents of the previous generation
	 * @return
	 */
	public static Individual[] crossover(Individual[] parents) { 
		Individual[] newGeneration = new Individual[popsize];
		Random r = new Random();
		Individual parent1;
		Individual parent2;
		for (int i = 0; i < popsize; i++) {
			parent1 = parents[r.nextInt(parents.length)];
			parent2 = parents[r.nextInt(parents.length)];
			newGeneration[i] = merge(parent1, parent2);
			
		}
		return newGeneration;
	}

	public static Individual randomIndividual(){
		Random generator = new Random();
		char[] tempChromosome = new char[TARGET.length()];
		for (int j = 0; j < TARGET.length(); j++) {
			tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];
		}
		return new Individual(tempChromosome);
	}

	/**
	 * Combines the chromosome of two individuals to create a new Individual
	 * @param one
	 * @param two
	 * @return
	 */
	public static Individual merge(Individual one, Individual two){
		Random r = new Random();
		int maxCuts = 5;
		int minCuts = 1;
		int cuts = r.nextInt(maxCuts-minCuts)+ minCuts + 2;
		int[] cutsPos = new int[cuts];
		cutsPos[0] = 0;
		cutsPos[cutsPos.length-1] = one.getChromosome().length-1; 

		char[] childChromosome = new char[one.getChromosome().length];
		for(int i = 1; i< cuts-1; i++){
			cutsPos[i] = r.nextInt(one.getChromosome().length - 1) + 1;
		}
		Arrays.sort(cutsPos);
		for(int i = 0; i < cutsPos.length-1; i++){
			for(int j = cutsPos[i]; j<= cutsPos[i+1]; j++){
				if(i % 2 == 0){
					childChromosome[j] = one.getChromosome()[j];
				}else{
					childChromosome[j] = two.getChromosome()[j];
				}
			}
		}
		return new Individual(childChromosome);
	}

	/**
	 * Applies the mutation to a given generation
	 * @param generation
	 * @return
	 */
	public static Individual[] applyMutation(Individual[] generation){
		Random r = new Random();
		double probability;
		int index;
		char toReplace;
		for(Individual i : generation){
			probability = r.nextDouble();
			if(probability < mutationRate){
				index = r.nextInt(generation[0].getChromosome().length);
				toReplace = alphabet[r.nextInt(alphabet.length)];
				i.changeLetter(index, toReplace);
			}
		}
		return generation;
	}

}
