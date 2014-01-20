// README.txt 
// Alexander Kolesnikov
// 2012/12/14

This file include a brief description of the additional classes which was used in project.

==============================Node==============================

This class includes character and frequency(how many times this character occures in the given text).
There are several link.

"next" and "prev' link - for creating a LinkedList from which creating the Huffman Trees.
"left" and "right" - for creating Huffman binary Tree.

=====================GradProjectDataStruct======================

This class consist of variables and methods for building and working with binaty Huffman tree;

It inludes:
	The "insert" method - insertion to the list of the nodes when creating linkedlist of Node object before buildeng Huffman Tree.
	This method insert Nodes according the key value.
	So we always have sorting linked list.
	Time efficiency: O(n) - in worst case we have to go through all list. 

	The "removeFirst" method - just remove first element in the linkedList struct. O(1);

	The "createHuffTree" method which go through all elements in linked list and creates Huffman tree. 
	Time compexity is: O(n*n); [O((2 + (n-2)) * n)];

	The "fillHuffTable" method which fill table with Code object according huffman tree;
	Time complexity is: O(n) - we have to go throught all items in the tree;

	The "getCode" method which returns Code object according to the given character. Time complexity is: O(1); In case array random access;

	The "getNode" method which go through tree and return left or right child depends on bit parameter. Time complexity: O(1);
	
=================================================================

In main method of Huff.java we first read the file and create the header array which consist of character frequency. (index of array - character; value - frequency);
Then create Huffman tree and fill in Huffman table with Code object;
Each Code instance corresponds to the character in ASCII;

Finally: reading the file and writing the appropriate code to the output file.

createAndFillHuffmanTable method:
	Time complexity of creating huffman linkedList  - O(n*n) - n times insert[O(n)];

==================================================================

In main method of Puff.java we first read the header of the file.
Create Huffman tree and fill in Huffman table;
Reading the bits from the file go through Huffman tree and write the appropriate character to the output text file.
To avoid additional character occure  - I use textLength variable. 

==================================================================
