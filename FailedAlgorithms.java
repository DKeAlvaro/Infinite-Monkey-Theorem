import java.util.ArrayList;

public class FailedAlgorithms{

    public static int targetLetters(Individual individual) {
		ArrayList<Character> foundChars = new ArrayList<Character>();
		int fitness = 0;
		for (int i = 0; i < individual.getChromosome().length; i++) {
			if ((individual.chromosome[i] == 'H' || individual.chromosome[i] == 'E' || individual.chromosome[i] == ' '
					|| individual.chromosome[i] == 'L' || individual.chromosome[i] == 'O'
					|| individual.chromosome[i] == 'W' || individual.chromosome[i] == 'R'
					|| individual.chromosome[i] == 'D') && !contains(foundChars, individual.chromosome[i])) {
				fitness++;
				foundChars.add(individual.chromosome[i]);
			}
		}
		return fitness;
	}

    public static boolean contains(ArrayList<Character> foundChars, char letter) {
		boolean found = false;
		for (int x = 0; x < foundChars.size(); x++) {
			if (foundChars.get(x) == letter) {
				found = true;
				break;
			}
		}
		return found;
	}

    public static Individual chooseFirstParent(Individual[] population1) {
		boolean parentFound = false;
		int index = 0;
		int generationNumber = 1;
		while (!parentFound) {
			// population1 = randomGeneration(popsize);
			for (int i = 0; i < population1.length; i++) {
				if (targetLetters(population1[i]) >= 8) {
					parentFound = true;
					index = i;
					break;
				}
			}
			System.out.println("generation " + generationNumber);
			generationNumber++;

		}
		System.out.println("Parent found!!!");
		System.out.println(population1[index].genoToPhenotype());
		return population1[index];
	}


	
}