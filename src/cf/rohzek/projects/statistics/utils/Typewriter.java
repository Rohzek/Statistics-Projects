package cf.rohzek.projects.statistics.utils;

import java.util.List;

import cf.rohzek.projects.statistics.Main;
import consoleio.C;

public class Typewriter 
{
	public static void Type() 
	{
		C.io.println();
	}
	
	public static void Type(String message) 
	{
		for (int i = 0; i < message.length(); i++)
		{
			C.io.print(message.charAt(i));
			try
			{
				Thread.sleep(Main.SPEED);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println();
	}
	
	public static void Type(String message, long speed) 
	{
		for (int i = 0; i < message.length(); i++)
		{
			C.io.print(message.charAt(i));
			try
			{
				Thread.sleep(speed);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println();
	}
	
	public static void Type(List<?> list) 
	{
		Type(list.toString(), Main.SPEED);
	}
	
	public static void Clear() 
	{
		C.io.clear();
	}
}
