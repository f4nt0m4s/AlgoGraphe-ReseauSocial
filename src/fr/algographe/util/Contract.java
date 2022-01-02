package fr.algographe.util;
/**
 * Nous utilisons la classe Contract de notre professeur de 
 * méthodologie de la programmation orientée objet 2
 * pour tester une condition
*/
public final class Contract {

	private Contract() {}

	public static void checkCondition(boolean condition, String message) {
		if (!condition) {
			throw new IllegalArgumentException(message);
		}
	}
}
