import java.util.Scanner;

public class PrimeFactors {
    public static void main(String[] args) {
        FactorFinder(new Scanner(System.in));
    }

  	public static void FactorFinder(Scanner console) {
      int counter = 0;
      int[] factors = new int[counter];
      System.out.print("Enter integer greater than 1 (0 to terminate): ");
      	long n = console.nextLong();
        if (!(n%2==0)) {
          notDivisibleBy2(n, factors, counter);
        } else {
          divisibleBy2(n, factors, counter);
        }
      }
      public static void notDivisibleBy2 (long n, int factors[], int counter) {
        if (!(n%2==0)) {
          for (int i = 2; i>= Math.sqrt(n); i++) {
            if (!(n%i==0)) {
              i++;
              counter++;
            } else {
              
            }
          } 
      }
      public static void divisibleBy2 (long n, int factors[], int counter) {
        while (n%2==0) {
          n = n/2;
        }
      }
  }
}
// mens n/2 så del tallet med 2
// hvis tallet ikke kan deles med 2, så check alle tal større end 2 indtil kvadartroden af n
