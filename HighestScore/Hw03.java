import java.lang.*;
import java.util.*;
import java.io.*;

class Node
{
	public Node(int hours, int points)
	{
		this.hours = hours;
		this.points = points;
	}
	
	public int hours;
	public int points;
}

public class Hw03
{
	public static Integer numProblems;
	public static Integer numHours;
	public static Node[] problems;
	
	
	
	public Hw03(String fName)
	{
		Scanner readFile = null;
		
		try
		{
			readFile = new Scanner(new File(fName));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		numProblems = numProblems.parseInt(readFile.next());
		numHours =numHours.parseInt(readFile.next());
		
		problems = new Node[numProblems];
		
		for(int i = 0; i < numProblems; i++)
		{
			problems[i] = new Node(Integer.parseInt(readFile.next()),Integer.parseInt(readFile.next()));
		}

		System.out.println(fName + " has " + numProblems + " problems over " + numHours + " hours");
	}
	
	public static void complexityIndicator()
	{
		System.err.println("ia066316;1.5;10");
	}
	
	public static Node[] schedule(int numHours, Node[] problems, int numProblems)
	{
		int i, h;
		
		int s[][] = new int[numProblems + 1][numHours + 1];
		int k[][] = new int[numProblems + 1][numHours + 1];
		Node[] p = new Node[numProblems];
		
		for(i = 0; i <= numProblems; i++)
			for(h = 0; h <= numHours; h++)
			{
				if(i == 0 || h == 0)
				{
					s[i][h] = 0;
					k[i][h] = 0;
				}
					
					
				else if(problems[i-1].hours <= h)
					if(problems[i-1].points + s[i-1][h - problems[i-1].hours] >= s[i-1][h])
					{
						s[i][h] = problems[i-1].points + s[i-1][h - problems[i-1].hours];
						k[i][h] = 1;
					}	
					else
					{
						s[i][h] = s[i-1][h];
						k[i][h] = 0;
					}
				else
				{
					s[i][h] = s[i-1][h];
					k[i][h] = 0;
				}
			}
		
		i = numProblems;
		h = numHours;
		
		while( i > 0 && h > 0)
		{
			if(k[i][h] == 1)
			{
				p[i - 1] = problems[i - 1];
				i--;
				h -= problems[i].hours;
			}
			else
				i--;	
		}
			
		//Max Value Matrix
			
/* 		for(i=0;i<=numProblems;i++)
		{
			System.out.println();
			
			for(h=0;h<=numHours;h++)
				System.out.print("	" + s[i][h] + " ");
		} */
		
		//Keep Matrix
/* 		for(i=0;i<=numProblems;i++)
		{
			System.out.println();
			
			for(h=0;h<=numHours;h++)
				System.out.print("	" + k[i][h] + " ");
		} */
		
		//Nodes to Keep
/*  	for(i=0; i<numProblems;i++)
			if(p[i] != null)
				System.out.println(i+1 + "	" + p[i].points + " "); */
			
		return p;
	}
	
	public static void printList(Node[] p)
	{
		int bestPoints = 0;
		
		System.out.println("The selected  problems for the highest contest score are: ");
		System.out.println("	Problem	Points");
		
		for(int i = 0; i < p.length; i++)
			if(p[i] != null)
			{
				System.out.println("	" + (i+1) + "	" + p[i].points);
				bestPoints += p[i].points;
			}
				
		
		System.out.println();
		System.out.println("Target problem list yields " + bestPoints + " points");
	}
	
	public static void main(String[] args)
	{	
		String fileName = args[0];		
		Hw03 Hw03 = new Hw03(fileName);
		Node[] p = new Node[numProblems];
		
		System.out.println("Pr#	Time	Points");
		
		for(int i = 0; i < numProblems; i++)
			System.out.println(i+1 + "	" + problems[i].hours + "	" + problems[i].points);
		
		System.out.println();
		
		p = Hw03.schedule(numHours, problems, numProblems);		
		Hw03.printList(p);
	}
}