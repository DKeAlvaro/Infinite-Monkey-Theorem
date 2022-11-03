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
<<<<<<< Updated upstream
	public static int parentsLength = 30;
	public static int mutationRate = (int) 0.1 * popsize;
=======
	public static int parentsSize = 40;
>>>>>>> Stashed changes

	public static Individual[] parents = new Individual[parentsSize];
	static final String TARGET = "HELLO WORLD";

<<<<<<< Updated upstream
	static char[] alphabet = new char[27];
	public static char[] charTarget = new char[TARGET.length()];
=======
	public static double mutationRate = 0.3;

	static char[] alphabet = new char[27];
	public static char[] charTarget = new char[TARGET.length()]; // character array containing the target letters
>>>>>>> Stashed changes

	public static void main(String[] args) {

		for (int i = 0; i < TARGET.length(); i++) {
			charTarget[i] = TARGET.charAt(i); // Converts the string to a 1D char array
		}
		{}

		Individual[] currentGeneration = randomGeneration(popsize); // creates the first generation is totally random

		for (int i = 0; i < currentGeneration.length; i++) {
			currentGeneration[i].setFitness(myFitness(currentGeneration[i])); // sets each individual fitness from first
																				// generation
		}

		HeapSort sorter = new HeapSort();
		sorter.sort(currentGeneration); // sorts the generation

		int generationCounter = 1;
		boolean solutionFound = false;

		while (!solutionFound) {

			System.out.println("Generation number " + generationCounter);
<<<<<<< Updated upstream
			for (int i = 0; i < currentGeneration.length; i++) {
				currentGeneration[i].setFitness(myFitness(currentGeneration[i])); // sets each individual fitness of the
																					// current generation
=======
			for (Individual i : currentGeneration) {
				i.setFitness(myFitness(i)); // sets each individual fitness of the current generation
>>>>>>> Stashed changes
			}

			sorter.sort(currentGeneration);
			int printingElements = 10;
<<<<<<< Updated upstream
			int printedElements = 0;
			for (int i = 1; i < currentGeneration.length; i++) {
				if (printedElements < printingElements) { // prints the 10 best elements from each generation
					printedElements++;
					System.out.println(currentGeneration[i].genoToPhenotype() + " individual number " + i
							+ " fitness " + currentGeneration[i].getFitness());
				}

				if (Arrays.equals(currentGeneration[i].getChromosome(), charTarget)) { // checks if a solution has been
																						// found
=======
			int index = 0;
			for (int i = 0; i < printingElements; i++) {
				index = i + 1;
				System.out.println(currentGeneration[i].genoToPhenotype() + " individual number " + index + " fitness "
						+ currentGeneration[i].getFitness());
				if (Arrays.equals(currentGeneration[i].getChromosome(), charTarget)) {
>>>>>>> Stashed changes
					System.out.println(
							currentGeneration[i].genoToPhenotype() + " found on generation " + generationCounter
									+ " individual number " + i);
					solutionFound = true;
					break;
				}
			}

			for (int i = 0; i < parents.length; i++) { // assigns the parents of the new generation based on
														// the 10 best individuals of the current generation
				parents[i] = new Individual(new char[TARGET.length()]);
				for (int j = 0; j < parents[0].chromosome.length; j++) {
					parents[i].changeLetter(j, currentGeneration[i].getChromosome()[j]);
				}

<<<<<<< Updated upstream
			}
			currentGeneration = crossover(parents); // creates the new generation with the crossover method based on
													// the current generation.
													// it also mutates random elements inside the method
			generationCounter++;
=======
			currentGeneration = applyMutation(crossover(parents)); // creates the new generation with the crossover
																	// method based on
			// the current generation.
			// it also mutates random elements inside the method
>>>>>>> Stashed changes

			generationCounter++;

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

	/**
	 * Prints the most representative individuals of a given generation
	 * @param population
	 */
	public static void printGeneration(Individual[] population) {
		int individualnumber = 1;
		boolean solutionFound = false;
		for (int i = 0; i < population.length && !solutionFound; i++) {
			System.out.println(population[i].genoToPhenotype() + " individual number " + individualnumber
					+ " fitness " + population[i].getFitness());
			solutionFound = false;
			individualnumber++;
		}
	}

	/**
	 * Creates a new generation based on the previous one
	 * @param parents  The parents of the previous generation
	 * @return The new generation
	 */
	public static Individual[] crossover(Individual[] parents) { 
		Individual[] newGeneration = new Individual[popsize];
		int mutations = 0;
		for (int i = 0; i < popsize; i++) {
<<<<<<< Updated upstream
			newGeneration[i] = new Individual(new char[TARGET.length()]);
			if (i == (int) (Math.random() * popsize) && mutations < mutationRate) {
				for (int j = 0; j < TARGET.length(); j++) {
					if (j == (int) (Math.random() * TARGET.length())) {
						newGeneration[i].changeLetter(j, alphabet[(int) (Math.random() * 27)]);
					} else {
						newGeneration[i].changeLetter(j,
								parents[(int) (Math.random() * parents.length)].getChromosome()[j]);
					}
				}
			}
			for (int j = 0; j < TARGET.length(); j++) {
				newGeneration[i].changeLetter(j, parents[(int) (Math.random() * parents.length)].getChromosome()[j]);
=======
			parent1 = parents[r.nextInt(parents.length)];
			parent2 = parents[r.nextInt(parents.length)];
			newGeneration[i] = merge(parent1, parent2);

		}
		return newGeneration;
	}

	public static Individual randomIndividual() {
		Random generator = new Random();
		char[] tempChromosome = new char[TARGET.length()];
		for (int j = 0; j < TARGET.length(); j++) {
			tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];
		}
		return new Individual(tempChromosome);
	}

	/**
	 * Combines the chromosome of two individuals to create a new individual
	 * @param one The first individual
	 * @param two The second individual
	 * @return The new individual
	 */
	public static Individual merge(Individual one, Individual two) {
		Random r = new Random();
		int maxCuts = 5;
		int minCuts = 1;
		int cuts = r.nextInt(maxCuts - minCuts) + minCuts + 2;
		int[] cutsPos = new int[cuts];
		cutsPos[0] = 0;
		cutsPos[cutsPos.length - 1] = one.getChromosome().length - 1;

		char[] childChromosome = new char[one.getChromosome().length];
		for (int i = 1; i < cuts - 1; i++) {
			cutsPos[i] = r.nextInt(one.getChromosome().length - 1) + 1;
		}
		Arrays.sort(cutsPos);
		for (int i = 0; i < cutsPos.length - 1; i++) {
			for (int j = cutsPos[i]; j <= cutsPos[i + 1]; j++) {
				if (i % 2 == 0) {
					childChromosome[j] = one.getChromosome()[j];
				} else {
					childChromosome[j] = two.getChromosome()[j];
				}
			}
		}
		return new Individual(childChromosome);
	}

	/**
	 * Applies the mutations to a given generation
	 * @param generation 
	 * @return The mutated generation
	 */
	public static Individual[] applyMutation(Individual[] generation) {
		Random r = new Random();
		double probability;
		int index;
		char toReplace;
		for (Individual i : generation) {
			probability = r.nextDouble();
			if (probability < mutationRate) {
				index = r.nextInt(generation[0].getChromosome().length);
				toReplace = alphabet[r.nextInt(alphabet.length)];
				i.changeLetter(index, toReplace);
>>>>>>> Stashed changes
			}
		}
		return newGeneration;
	}

}
