
public class Individual implements Cloneable {

	char[] chromosome; // each chromosome represents each letter from the alphabet
	int fitness;

	public Individual(char[] chromosome) {
		this.chromosome = chromosome;
		this.fitness = 0; // this is the value we have to change
	}

	public void changeLetter(int place, char chromosomeToReplace) {
		this.chromosome[place] = chromosomeToReplace;
	}

	public char[] getChromosome() {
		return chromosome;
	}

	public void setChromosome(char[] chromosome) {
		this.chromosome = chromosome;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public String genoToPhenotype() {
		StringBuilder builder = new StringBuilder();
		builder.append(chromosome);
		return builder.toString();
	}

	public Individual clone() {
		char[] chromClone = new char[chromosome.length];
		for (int i = 0; i < chromClone.length; i++) {
			chromClone[i] = chromosome[i];
		}
		return new Individual(chromClone);
	}

	@Override
	public String toString() {
		return new String(this.chromosome);
	}

}
