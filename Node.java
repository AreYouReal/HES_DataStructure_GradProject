/**
 * Node.java
 * 
 * Node class for GradProjectDataStruc class.
 * 
 * */

public class Node 
{
	int frequency;							// The frequency of the character in the file
	int character;							// Character ASCII code
	Node left, right, next, prev;	// Link to another nodes
	
	
	public Node()
	{
		frequency = 0;
		character = 0;
		left = right = next = prev = null;
	}
	
	public Node(int key, int data)
	{
		this.frequency = key;
		this.character = data;
		left = right = next = prev = null;
	}
}
