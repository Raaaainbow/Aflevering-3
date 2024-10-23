import java.util.Scanner;

public class PrimeFactors {
  public static void main(String[] args) {
    FactorFinder(new Scanner(System.in));
  }

  public static void FactorFinder(Scanner console) {
    System.out.print("Enter integer greater than 1 (0 to terminate): ");
    long n = console.nextLong();
    System.out.print("\b");
    if (n == 0) {

      // Returns nothing, so the program ends
      return;
    } else {
      System.out.print("List of prime factors:");

      // Devides n by 2 while the remainder is 0
      // I think the bug with 9201111169755555649 comes from here, instead of printing "2, " it should print the fraction
      while (n % 2 == 0) {
        n /= 2;
        System.out.print("2, ");
      }

      // check if remainder is 0 for greater than 2 to sqaure root of number
      for (int i = 3; i <= Math.sqrt(n); i ++) {
        while (n % i == 0) {
          System.out.print(i + ", ");
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