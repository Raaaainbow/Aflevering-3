import java.util.Scanner;

public class PrimeFactors {
  public static void main(String[] args) {
    FactorFinder(new Scanner(System.in));
  }

  public static void FactorFinder(Scanner console) {
    System.out.print("Enter integer greater than 1 (0 to terminate): ");
    long n = console.nextLong();
    System.out.print("\b");
    System.out.println("DEBUGGING THIS IS n: " + n);
    System.out.println("DEBUGGING THIS IS sqrt(n): " + Math.sqrt(n));

    if (n == 0) {

      // Returns nothing, so the program ends
      return;
    } else {
      System.out.print("List of prime factors:");

      // Devides n by 2 while the remainder is 0
      while (n % 2 == 0) {
        n /= 2;
        System.out.print(" 2,");
      }

      // check if remainder is 0 for greater than 2 to sqaure root of number
      for (long i = 3; i <= Math.sqrt(n); i ++) {
        while (n % i == 0) {
          System.out.print(" " + i + ",");
          n /= i;
        }
      }

      // prints numbers that weren't caught in previous step
      if (n >= 2) {
        System.out.print(n);
      }
      System.out.print("\n\n");
    }
    FactorFinder(new Scanner(System.in));
    } 
} 