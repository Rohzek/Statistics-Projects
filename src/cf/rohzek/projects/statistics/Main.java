package cf.rohzek.projects.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cf.rohzek.projects.statistics.utils.Typewriter;
import consoleio.C;

public class Main 
{
	public static long SPEED = 29;
	static boolean escape = false;
	static List<Double> numbers = new ArrayList<Double>();
	static double average = 0, median = 0, q1 = 0, q3 = 0, iqr = 0, deviation = 0, variance = 0, range = 0, z_score = 0;
	static DecimalFormat df;
	static Path file;
	
	public static void main (String[] args) 
	{
		df = new DecimalFormat("####0.00");
		
		String input = "";
		Typewriter.Type("Hello, this utility will take in a set of data (numbers) and tell you many statistics values.");
		Typewriter.Type("To begin with, please tell me if you want to use a [file] or [entry] method:");
		
		while(escape != true) 
		{
			input = C.io.nextLine().toLowerCase();
			
			if(input.contains("f") || input.contains("fi") || input.contains("fil") || input.contains("file"))
			{
				input = "file";
				escape = true;
				
			}
			else if(input.contains("e") || input.contains("en") || input.contains("entry")) 
			{
				input = "entry";
				escape = true;
			}
			
			else 
			{
				Typewriter.Clear();
				Typewriter.Type("You didn't choose one of the two options.");
				Typewriter.Type("Do you want to use a [file] or [entry] method?:");
			}
		}
		
		Typewriter.Clear();
		Typewriter.Type("You chose: " + input);
		Typewriter.Type("    ", 60);
		Typewriter.Type();
		escape = false;
		
		if(input == "file") 
		{
			while(escape != true) 
			{
				Typewriter.Clear();
				Typewriter.Type("Enter a file name to load:");
				input = C.io.nextLine().toLowerCase();
				
				file = Path.of(input + ".txt");
				
				if(file.toFile().exists() && !file.toFile().isDirectory()) 
				{
					try 
					{
						input = Files.readString(file);
						String[] inputs = input.split("\\s+");
						
						for(String str : inputs) 
						{
							numbers.add(Double.parseDouble(str));
						}
					}
					catch (IOException e) {}
					
					escape = true;
				}
				
				else 
				{
					Typewriter.Type("That file doesn't exist. Try again.");
				}
				Typewriter.Type();
			}
		}
		
		else 
		{
			Typewriter.Clear();
			Typewriter.Type("Begin entering your numbers.");
			Typewriter.Type("Type [exit] or [quit] to move on to calculations:");
			Typewriter.Type();
			
			while(escape != true) 
			{
				input = C.io.nextLine().toLowerCase();
				
				if(input.contains("e") || input.contains("q")) 
				{
					escape = true;
				}
				else 
				{
					double entry = Double.parseDouble(input);
					numbers.add(entry);
				}
			}
		}
		
		Calculate();
		
		escape = false;
		
		Typewriter.Type();
		Typewriter.Type("Begin entering values to calculate the z-scores. Enter quit or exit to stop:");
		
		while(escape != true) 
		{
			Typewriter.Type();
			input = C.io.nextLine().toLowerCase();
			
			if(input.contains("e") || input.contains("q")) 
			{
				escape = true;
			}
			else 
			{
				ZScore(Double.parseDouble(input));
				Typewriter.Type("" + z_score);
			}
		}
	}
	
	public static void Calculate() 
	{
		Collections.sort(numbers);
		Typewriter.Clear();
		Typewriter.Type("You entered: ");
		Typewriter.Type(numbers);
		
		Average();
		Median(numbers);
		Quantiles();
		Range();
		Variance();
		Deviation();
		
		Typewriter.Type();
		Typewriter.Type("The Range is: " + range);
		Typewriter.Type();
		Typewriter.Type("The Average is: " + average);
		Typewriter.Type();
		Typewriter.Type("The Median is: " + median);
		Typewriter.Type();
		Typewriter.Type("Q1 is: " + q1);
		Typewriter.Type();
		Typewriter.Type("Q2 is: " + median);
		Typewriter.Type();
		Typewriter.Type("Q3 is: " + q3);
		Typewriter.Type();
		Typewriter.Type("The IQR is: " + iqr);
		Typewriter.Type();
		Typewriter.Type("The Variance is: " + variance);
		Typewriter.Type();
		Typewriter.Type("The Standard Deviation is: " + deviation);
		
	}
	
	public static double Average() 
	{
		double total = 0;
		
		for(double num : numbers) 
		{
			total += num;
		}
		
		average = total / numbers.size();
		
		return average;
	}
	
	public static double Median(List<Double> list) 
	{
		int count = list.size();
		
		// If odd number of entries
		if((count % 2) == 1) 
		{
			median = list.get((count / 2));
		}
		else 
		{
			median = ((list.get(count / 2) + (list.get((count / 2) - 1))) / 2);
		}
		
		return median;
	}
	
	public static void Quantiles() 
	{
		List<Double> left = new ArrayList<Double>(), right = new ArrayList<Double>();
		
		// If odd number of entries
		if((numbers.size() % 2) == 1) 
		{
			for(int i = 0; i < ((numbers.size() / 2) + 1); i++) 
			{
				left.add(numbers.get(i));
			}
			
			for(int i = (numbers.size() / 2); i < numbers.size(); i++) 
			{
				right.add(numbers.get(i));
			}
		}
		
		else 
		{
			for(int i = 0; i < (numbers.size() / 2); i++) 
			{
				left.add(numbers.get(i));
			}
			
			for(int i = (numbers.size() / 2); i < numbers.size(); i++) 
			{
				right.add(numbers.get(i));
			}
		}
		
		q1 = Median(left);
		q3 = Median (right);
		
		//q2 = median
		median = Median(numbers);
		iqr = (q3 - q1);
	}
	
	public static double Range() 
	{
		double num1 = numbers.get(0), num2 = numbers.get(numbers.size() - 1);
		range = (num2 - num1);
		
		return range;
	}
	
	public static double Deviation() 
	{
		// You have to calculate variance to get std dev so call it anyway
		variance = Variance();
		deviation = Math.sqrt(variance);
		
		return deviation;
	}
	
	public static double Variance() 
	{
		double total = 0, variance = 0, n = 0, p = 0;
		
		for(double num : numbers) 
		{
			total += num;
		}
		
		n = ((Math.pow(total, 2)) / numbers.size());
		
		total = 0;
		
		for(double num : numbers) 
		{
			total += Math.pow(num, 2);
		}
		
		p = (total - n);
		
		variance = (p / (numbers.size() - 1));
		
		return variance;
	}
	
	// Z = X - U / S || Z = Number in question - Average / Standard Deviation
	public static double ZScore(double x) 
	{
		// You need the Average and Std Dev to calculate, so call them anyway just in case
		Average();
		Deviation();
		
		z_score = ((x - average)/deviation);
		return z_score;
	}
}
