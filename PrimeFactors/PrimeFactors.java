import java.util.Scanner;

public class PrimeFactors {
  public static void main(String[] args) {
    FactorFinder(new Scanner(System.in));
  }

  public static void FactorFinder(Scanner console) {
    System.out.print("Enter integer greater than 1 (0 to terminate): ");
    long n = console.nextLong();
    System.out.print("List of prime factors:");
      while (n % 2 == 0) {
        n /= 2;
        System.out.print("2, ");
      }
      for (int i = 3; i <= Math.sqrt(n); i += 2) {
        while (n % i == 0) {
          System.out.print(i + ", ");
          n /= i;
        }
      }
      if (n > 2) {
        System.out.print(n);
      }
    } 
}