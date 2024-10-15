import java.util.Scanner;
import java.math.*;

public class PrimeFactors {
  public static void main(String[] args) {
      FactorFinder(new Scanner(System.in));
  }

  public static void FactorFinder(Scanner console) {
    System.out.print("Enter integer greater than 1 (0 to terminate): ");
      long n = console.nextLong();
      if (n % 2 == 0) {
        while (n % 2 == 0) {
          n /= 2;
          System.out.print("2 ");
        }
      }
      else if (!(n % 2 == 0)) {
        for (int i = 3; i <= Math.sqrt(n); i++) {
          if (n % i == 0) {
            n /= i;
            System.out.print(i + " ");
          }
        }
      } 
  }
}
// mens n/2 så del tallet med 2
// hvis tallet ikke kan deles med 2, så check alle tal større end 2 indtil kvadartroden af n
