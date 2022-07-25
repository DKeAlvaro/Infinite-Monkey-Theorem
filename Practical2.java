import java.util.Random;
import java.lang.annotation.Target;
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

	public static Individual[] parents = new Individual[parentsLength];
	static final String TARGET = "HELLO WORLD";

	public static double mutationRate = 0.1;  

	static char[] alphabet = new char[27];
	public static char[] charTarget = new char[TARGET.length()];  //character array containing the target letters

	public static void main(String[] args) {
		
		for (int i = 0; i < TARGET.length(); i++) {
			charTarget[i] = TARGET.charAt(i); // Converts the string to a 1D char array
		}

		Individual[] currentGeneration = randomGeneration(popsize); // creates the first generation is totally random
		HeapSort sorter = new HeapSort();
		int generationCounter = 1;
		boolean solutionFound = false;

		while (!solutionFound) {
			System.out.println("Generation number " + generationCounter);
			for (Individual i : currentGeneration) {
				i.setFitness(myFitness(i)); // sets each individual fitness of the current generation																	
			}

			sorter.sort(currentGeneration);
			int printingElements = 10;
			int index = 0;
			for (int i = 0; i < printingElements; i++) {
				index = i+1;
				System.out.println(currentGeneration[i].genoToPhenotype() + " individual number " + index + " fitness " + currentGeneration[i].getFitness());
				if (Arrays.equals(currentGeneration[i].getChromosome(), charTarget)) { 
					System.out.println(
							currentGeneration[i].genoToPhenotype() + " found on generation " + generationCounter
									+ " individual number " + i);
					solutionFound = true;
					break;
				}
			}

			for (int i = 0; i < parents.length; i++) { // assigns the parents of the new generation based on
														// the 10 best individuals of the current generation
				parents[i] = currentGeneration[i].clone();
			}

			currentGeneration = applyMutation(crossover(parents)); // creates the new generation with the crossover method based on
													// the current generation.
													// it also mutates random elements inside the method
			
			
			generationCounter++;

			if (generationCounter > 100) { // stops to find the target because the algorithm will never converge
				System.out.println("Solution not found after 100 generations");
				solutionFound = true;
			}

		}

	}

	public static double myFitness(Individual individual) {
		double fitness = 0;
		final double increment = 1;
		for (int i = 0; i < TARGET.length(); i++) { // iterating through every chromosome of the individual
			if (individual.getChromosome()[i] == charTarget[i]) { // if a particular individual's chromosome is correct
				fitness += increment;
			}
		}
		return fitness;

	}

	public static Individual[] randomGeneration(int popSize) {
		// int popSize = 100;
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' '; // 26th element of the alphabet is the space
		Random generator = new Random(System.currentTimeMillis());
		Individual[] population = new Individual[popSize];
		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++) {
			char[] tempChromosome = new char[TARGET.length()]; // Creates a char array with its length being equal to
																// the target's length

			for (int j = 0; j < TARGET.length(); j++) { // iterate throughout the Target.length (HelloWorld) assign a
														// random letter in the alphabet to each index in the new array
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];

			}
			population[i] = new Individual(tempChromosome); // add this random individual to the population

		}
		return population;
	}

	public static void printGeneration(Individual[] population) {// gets each individual's chromosome and prints it
		int individualnumber = 1;
		boolean solutionFound = false;
		for (int i = 0; i < population.length && !solutionFound; i++) {
			System.out.println(population[i].genoToPhenotype() + " individual number " + individualnumber
					+ " fitness " + population[i].getFitness());
			solutionFound = false;
			individualnumber++;
		}
	}

	public static Individual[] crossover(Individual[] parents) { // creates a new generation based on the parents
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
