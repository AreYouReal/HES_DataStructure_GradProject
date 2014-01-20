/**
*	FradProjectDataStruct.java
*	This class provide functionality to
*	creating the Huffman tree and HuffmanTable
*	Huffman table corresponds to array of size 128
*	in which index of the array is ASCII code of the character
*	and content of the array[ASCII] is Code object.
*
*	@author Alexander Kolesnikov
*
*	email: kolesnikov@fas.harvard.edu (viertual@gmail.com)
*/


public class GradProjectDataStruct 
{
	Node root;								// root of the Tree
	int items;								// Number of items
	Code [] huffTable = new Code[128];		// HufmanTable with Code object
	Code code = new Code();					// Code object while traversing the Huffman tree
	
	/**
	*	Default class constructor
	*/
	public GradProjectDataStruct()
	{
		this.root = null;
		this.items = 0;
	}
	
	/**
	*	Method which insert Node object to the structure
	* 	according the key (in our case - frequency)
	*	So after each insertion we have a complete sorting linkedList of Node items
	*/
	public void insert(Node newNode)
	{
		if(items == 0)
		{
			root = newNode;
			items++;
		}else
		{
			if(root.frequency > newNode.frequency)
			{
				root.prev = newNode;
				newNode.next = root;
				root = newNode;
				root.prev = null;
			}else
			{
				Node temp = root;
				while( temp.next != null && temp.next.frequency < newNode.frequency)
				{
					temp = temp.next;
				}
				if( temp.next == null)
				{
					temp.next = newNode;
					newNode.prev = temp;
				}else
				{
					temp.next.prev = newNode;
					newNode.next = temp.next;
					newNode.prev = temp;
					temp.next = newNode;
				}
			}
			items++;
		}
	}
	
	/**
	 * Recursive method.
	* Print list of Nodes - for debugging (while creating the Tree)
	*/
	public void printList(Node node)
	{
		if(node == null) 
		{
			System.out.println("\nItems: " + items);
			return;
		}
		if(node.prev != null) 
			System.out.print(node.prev.frequency + ":");
		System.out.print(node.frequency + " -> ");
		printList(node.next);
	}
	
	/**
	* This method returns first element from the LinkedList structure;
	*/
	public Node removeFirst()
	{
		if(root == null) return null;
		
		Node retNode = root;
		
		root.next.prev = null;
		root = root.next;
	
		items--;
		return retNode;
	}
	
	/**
	* This method traversing through LinkedList and building the Tree structure.
	*/
	public void createHuffTree()
	{
		while(items > 2)
		{
			Node first = removeFirst();
			Node second = removeFirst();
			
			Node newNode = new Node(first.frequency + second.frequency, -1);
			newNode.left = first;
			newNode.right = second;
			first.next = first.prev = second.next = second.prev = null;
			insert(newNode);
		}
		
		Node first = removeFirst();
		Node second = root;
		
		Node newNode = new Node(first.frequency + second.frequency, -1);
		newNode.left = first;
		newNode.right = second;

		root = newNode;
	}
	
	/**
	* Method which recursively traverse through all Huffman Tree and fill in Huffman table.  
	*/
	public void fillHuffTable(Node root)
	{
		if(root == null) return;
		else
		{
			if( root.character != - 1)
			{
				//System.out.print(root.frequency + "-[" + (char)root.character + "] ");
				Code newCode = new Code(code);
				huffTable[root.character] = newCode;
				//System.out.println(newCode.toString());
			}
			if(root.left != null)
			{
				code.addBit(0);
				fillHuffTable(root.left);
			}
			if(root.right != null)
			{
				code.addBit(1);
				fillHuffTable(root.right);
			}
			if(code.length() > 0)
				code.removeBit();
		}
	}

	/**
	 * Method returns Code object for the perticular character
	 * */
	public Code getCode(int ch)
	{
		return huffTable[ch];
	}
	
	/**
	 * This method allows to go through Huffman Tree and check all nodes.
	 * */
	public Node getNode(int bit, Node node)
	{
		if(bit == 1)
			return node.right;
		else
			return node.left;
	}
}
