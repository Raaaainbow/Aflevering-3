import java.util.Scanner;

public class PrimeFactors {
  public static void main(String[] args) {
    FactorFinder(new Scanner(System.in));
  }

  public static void FactorFinder(Scanner console) {
    System.out.print("Enter integer greater than 1 (0 to terminate): ");
    long n = console.nextLong();
    System.out.print("\b");
    String output = "";
    
    if (n == 0) {

      // Returns nothing, so the program ends
      return;
    } else {
      System.out.print("List of prime factors:");

      // Devides n by 2 while the remainder is 0
      while (n % 2 == 0) {
        n /= 2;
        output += " 2,";
      }

      // check if remainder is 0 for greater than 2 to sqaure root of number
      for (long i = 3; i <= Math.sqrt(n); i += 2) {
        while (n % i == 0) {
          output += " " + i + ",";
          n /= i;
        }
      }

      // prints numbers that weren't caught in previous step
      if (n >= 2) {
        output += " " + n;
      }

      // fjerner komma til sidst hvis der opstår fencepost problem
      if (output.charAt(output.length()-1) == ',') {
        output = output.substring(0, output.length()-1);
      }
      
      // Printer resultatet
      System.out.println(output);
      System.out.print("\n\n");
    }
    FactorFinder(new Scanner(System.in));
    } 
} 