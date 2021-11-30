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
	public static int parentsLength = 30;
	public static int mutationRate = (int) 0.1 * popsize;

	public static Individual[] parents = new Individual[parentsLength];
	static final String TARGET = "HELLO WORLD";

	static char[] alphabet = new char[27];
	public static char[] charTarget = new char[TARGET.length()];

	public static void main(String[] args) {

		for (int i = 0; i < TARGET.length(); i++) {
			charTarget[i] = TARGET.charAt(i); // Converts the string to a 1D char array
		}

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
			for (int i = 0; i < currentGeneration.length; i++) {
				currentGeneration[i].setFitness(myFitness(currentGeneration[i])); // sets each individual fitness of the
																					// current generation
			}

			sorter.sort(currentGeneration);
			int printingElements = 10;
			int printedElements = 0;
			for (int i = 1; i < currentGeneration.length; i++) {
				if (printedElements < printingElements) { // prints the 10 best elements from each generation
					printedElements++;
					System.out.println(currentGeneration[i].genoToPhenotype() + " individual number " + i
							+ " fitness " + currentGeneration[i].getFitness());
				}

				if (Arrays.equals(currentGeneration[i].getChromosome(), charTarget)) { // checks if a solution has been
																						// found
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

			}
			currentGeneration = crossover(parents); // creates the new generation with the crossover method based on
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
		int mutations = 0;
		for (int i = 0; i < popsize; i++) {
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
			}
		}
		return newGeneration;
	}

}
