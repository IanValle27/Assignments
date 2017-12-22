import java.lang.*;
import java.util.*;
import java.io.*;

class ListMaker
{
	public static Random rand = new Random();
	public static int seed;
	
	public ListMaker(Integer inSeed)
	{
		rand.setSeed(inSeed);
	}
	
	public SkipList[] makeSkipList()
	{
		SkipList[] level = new SkipList[15];
		for(int i = 0; i < 15; i++)
		{
			level[i] = new SkipList();
			
 			level[i].createNode(Integer.MIN_VALUE);
			level[i].createNode(Integer.MAX_VALUE);
		}
			
		return level;
	}
	
	public int insert(int data, SkipList[] level)
	{		
		int counter = 0;
	
		for( Node curr = level[counter].head; curr.next != null; curr = curr.next)
			if(curr.data == data)
				return counter + 1;
	
		level[counter].createNode(data);
		
		while(randFunction())
		{
			counter++;
			level[counter].createNode(data);
		}
		
		return counter + 1;
	}
	
	public void delete(int data, SkipList[] level, int topLevel)
	{
		Boolean delete = false;		
		
		for(int i = topLevel - 1; i >= 0; i--)
		{			
			if(level[i].deleteNode(data))
			{
				delete = true;
			}
		}
				
		if(delete)
				System.out.println(data + " deleted");
		else
			System.out.println(data + " integer not found - delete not successful");	
		
		return;
	}
	
	public void search(int data, SkipList[] level, int topLevel)
	{
		Boolean found = false;		
		
		for(int i = topLevel - 1; i >= 0 && !found; i--)
		{			
			if(level[i].searchNode(data))
			{
				found = true;
			}
		}
				
		if(found)
				System.out.println(data + " found");
		else
			System.out.println(data + " NOT FOUND");	
		
		return;
	}

	public void printAll(SkipList[] level,int topLevel, int maxLength)
	{
		System.out.println("the current Skip List is shown below:");
		
		int nodeCounter;
		String[] printList = new String[maxLength];
		
		
		for(int i = 0; i < topLevel; i++)
		{
			nodeCounter = 0;
			String checkString;
			
			if(i == 0)
			{
				for(Node curr = level[i].head; curr != null; curr = curr.next)
				{
					printList[nodeCounter] =" " + curr.data + "; ";
					nodeCounter++;
					maxLength = nodeCounter;
				}				
			}
			else
			{
				for(Node curr = level[i].head; curr != null; curr = curr.next)
				{
					nodeCounter = 0;
					
					checkString = " " + curr.data + "; ";
					
					if(curr.data != Integer.MAX_VALUE && curr.data != Integer.MIN_VALUE)
					{
						for(int k = 1; k < i; k++)
							checkString = checkString + " " + curr.data + "; ";
					
						while(!checkString.equals(printList[nodeCounter]))
						{
							nodeCounter++;
						}
					
						printList[nodeCounter] = printList[nodeCounter] + " " + curr.data + "; ";						
					}
				}
			}

		}
		
		for(int j = 0; j < maxLength; j++)
			if(printList[j].equals(" " + Integer.MAX_VALUE + "; "))
				System.out.println("+++infinity");
			else if(printList[j].equals(" " + Integer.MIN_VALUE + "; "))
				System.out.println("---infinity");
			else
				System.out.println(printList[j]);
		
		System.out.println("---End of Skip List---");
	}

	public void quit()
	{
		System.exit(0);
	}	
	
	public Boolean randFunction()
	{
		return (1 == (rand.nextInt() % 2 ));
	}
}

class Node
{
	public Node(int data)
	{
		this.data = data;
	}
	
	public int data;
	public Node next;
}

class SkipList
{
	public Node head = null;
	public Node tail = null;
	
	public void createNode(int data)
	{	
		Node tmp = new Node(data);
		
		if(head == null)
			head = tail = tmp;
		else if(data < head.data)
		{
			tmp.next = this.head;
			this.head = tmp;
		}
		else if(data > tail.data)
		{
			this.tail.next = tmp;
			this.tail = tmp;
		}
		else
		{
			Node curr = head;
			
			while(curr.next.data <= data)
				curr = curr.next;
			tmp.next = curr.next;
			curr.next = tmp;
		}
			
	}
	
	public Boolean deleteNode(int data)
	{
		if(head == null)
		{
			return false;
		}
			
		else if(head == tail && head.data == data)
		{
			head = tail = null;
			return true;
		}
		
		else if(head.data == data)
		{
			head = head.next;
			return true;
		}
		else
		{
			for(Node curr = head; curr.next != null; curr = curr.next)
				if(curr.next.data == data)
				{
					curr.next = curr.next.next;					
					return true;
				}
		}
		
		return false;
	}
	
	public Boolean searchNode(int data)
	{
		if(head == null)
		{
			return false;
		}
			
		else if(head == tail && head.data == data)
		{
			return true;
		}
		
		else if(head.data == data)
		{
			return true;
		}
		else
		{
			for(Node curr = head; curr.next != null; curr = curr.next)
				if(curr.next.data == data)
				{
					return true;
				}
		}
		
		return false;
	}
}

public class Hw02
{
	public static String[] commandList;
	
	
	public Hw02(String fName)
	{
		commandList = new String[500];
		int counter = 0;
		
		Scanner readFile = null;
		
		try
		{
			readFile = new Scanner(new File(fName));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		while(readFile.hasNext())
		{
			commandList[counter] = readFile.next();
			counter++;
		}
		
		counter = 0;

		System.out.println("For the input file named " + fName);
		//System.out.println("With the RNG unseeded,");
	}
	
	public static void complexityIndicator()
	{
		System.err.println("ia066316;1.5;10");
	}
	
	public static void main(String[] args)
	{	
		String fileName = args[0];
		String seeding;
		Integer seed;
		
		if(args.length > 1)
		{
			seed = Integer.parseInt(args[1]);
			seeding = "With the RNG seeded with " + seed + ",";
		}			
		else 
		{
			seed = 42;
			seeding = "With the RNG unseeded,";
		}
			
		int counter = 0;
		int topLevel = 1;
		int tmp;
		
		//complexityIndicator();
		
		Hw02 Hw02 = new Hw02(fileName);
		System.out.println(seeding);
		
		ListMaker listMaker = new ListMaker(seed);
		SkipList[] skipList = listMaker.makeSkipList();
		
		for(counter = 0; counter < commandList.length; counter++)
		{
			if(commandList[counter] != null)
			{
				if(commandList[counter].equals("i"))
				{
					counter++;
					tmp = listMaker.insert(Integer.parseInt(commandList[counter]), skipList);
					if(tmp > topLevel)
						topLevel = tmp;
					
				}
				else if(commandList[counter].equals("d"))
				{
					counter++;
					listMaker.delete(Integer.parseInt(commandList[counter]), skipList, topLevel);
				}
				else if(commandList[counter].equals("s"))
				{
					counter++;
					listMaker.search(Integer.parseInt(commandList[counter]), skipList, topLevel);
				}
				else if(commandList[counter].equals("p"))
				{
					listMaker.printAll(skipList, topLevel, commandList.length);
				}
				else if(commandList[counter].equals("q"))
				{
					listMaker.quit();
				}
				else
					System.out.println("FAILED TO READ COMMAND");

			}
		}
	}
}