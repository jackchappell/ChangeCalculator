import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/*
 * Class made by Jack Chappell
 * Version: 3.0
 * 
 * Note: This uses Java 1.8
 */
public class ChangeCalculator
{
	enum Currency
	{
		FIFTY_POUND(50, true), TWENTY_POUND(20, true), TEN_POUND(10, true), FIVE_POUND(5, true), TWO_POUND(2, false), ONE_POUND(1, false), FIFTY_PENCE(0.50, false), TWENTY_PENCE(0.20, false), TEN_PENCE(0.10, false), FIVE_PENCE(0.05, false), TWO_PENCE(0.02, false), ONE_PENNY(0.01, false);
		
		private double value;
		private boolean note;
		
		Currency(double value, boolean note)
		{
			this.value = value;
			this.note  = note;
		}
		
		public double getValue()
		{
			
			return value * 100;
		}
		
		public boolean isNote()
		{
			return note;
		}
		
		public String toString()
		{
			return super.toString().toLowerCase().replaceAll("_", " ");
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in); // Creates a new scanner with the console input
		
		SortedMap<Currency, Integer> total = new TreeMap<Currency, Integer>(); // Creates a SortedMap for storing the currency and an integer (that represents the amount of that currency)
		
		new ArrayList<Currency>(Arrays.asList(Currency.values())).forEach((value) -> total.put(value, 0)); // Adds all the currency values to the SortedMap with the amount set to 0
		
		System.out.println("Enter the amount to pay:");
		System.out.print("£");
		float price   = sc.nextFloat();
	
		System.out.println("Enter the amount you're paying:");
		System.out.print("£");
		float pay     = sc.nextFloat();
		
		if(pay == price)
		{
			System.out.println("You cannot enter the same amount!");
			return;
		}
		
		if(price > pay)
		{
			System.out.println("You have not provided enough money to pay!");
			return;
		}	
		
		// Convert the doubles to integers because Java doubles do not keep their precision
		int balance = (int) (pay * 100) - (int) (price * 100);
		
		do
		{
			for(Map.Entry<Currency, Integer> value : total.entrySet())
			{
				if(balance >= value.getKey().getValue())
				{
					balance -= value.getKey().getValue();
					total.put(value.getKey(), value.getValue() + 1); // Sets it back in the SortedMap in its old position 
					break; // Breaks out of the loop so that the next iteration can continue
				}
			}
		} while(balance != 0); // Does the above while the balance doesn't equal 0

		System.out.println("=========== Change given ===========");
		
		StringBuilder sb = new StringBuilder();

		for(Map.Entry<Currency, Integer> value : total.entrySet())
			if(value.getValue() != 0) sb.append(((value.getValue() == 1) ? "a" : value.getValue()) + " " + value.getKey().toString() + (value.getKey().isNote() ? ((value.getValue() > 1) ? " notes" : " note") : ((value.getValue() > 1) ? " coins" : " coin"))).append(", ");

		sb.setLength(sb.length() - 2); // Remove the last comma
		sb.append("."); // Add a full stop
		
		System.out.println("You need to be given " + replaceLast(sb.toString(), " and", ",")); // Output the final product (formatted and correct grammar)
		
		System.out.println("====================================");
	}
	
	// Replaces the last of a specific string. It does this by creating multiple StringBuilders to manipulate the strings (reversing them), then uses the replaceFirst method to replace the reversed string and then reverses the output to give the final product.
	private static String replaceLast(String input, String replacement, String removeLast)
	{
		return new StringBuilder(new StringBuilder(input).reverse().toString().replaceFirst(new StringBuilder(removeLast).reverse().toString(), new StringBuilder(replacement).reverse().toString())).reverse().toString();
	}
}
