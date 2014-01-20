/* 
 * Puff.java
 *
 * A program that decompresses a file that was compressed using 
 * Huffman encoding.
 *
 * <your name>, <your e-mail address>
 * <current date>
 */ 

import java.io.*;

public class Puff {

    /* Put any methods that you add here. */
	private static int header[] = new int[128];				// Info for Huffman tree building
	private static GradProjectDataStruct huffmanTree;		// Instance of my DataStrucute
	private static int textLength;							// Length of text

	private static void readHeader(ObjectInputStream ois, int[] header) throws IOException
	{
		for(int i = 0; i < 128; i++)
		{
			header[i] = ois.readInt();
		}
		textLength = ois.readInt();
	}
	
	private static void createAndFillHuffmanTree(int[] header) throws IOException
	{
			huffmanTree = new GradProjectDataStruct();
	        
			for(int i = 0; i < header.length; i++)
				if(header[i] != 0)
					huffmanTree.insert(new Node(header[i], i));
	        
			huffmanTree.createHuffTree();
			huffmanTree.fillHuffTable(huffmanTree.root);
	}
	
    /** 
     * main method for decompression.  Takes command line arguments. 
     * To use, type: java Puff input-file-name output-file-name 
     * at the command-line prompt. 
     */ 
	public static void main(String[] args) throws IOException {
        ObjectInputStream in = null;      // reads in the compressed file
        FileWriter out = null;            // writes out the decompressed file

        // Check for the file names on the command line.
        if (args.length != 2) {
            System.out.println("Usage: java Puff <in fname> <out fname>");
            System.exit(1);
        }

        // Open the input file.
        try {
            in = new ObjectInputStream(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[0]);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new FileWriter(args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[1]);
            System.exit(1);
        }
    
        // Create a BitReader that is able to read the compressed file.
        BitReader reader = new BitReader(in);

        
        // Read the header from the file
		readHeader(in, header);

		// Create Huffman Tree
		createAndFillHuffmanTree(header);
		
		int c = -1;
		Node temp = huffmanTree.root;
		int bit;
		while( (bit = reader.getBit()) != -1 )
		{
			while( c == -1 )
			{
				temp = huffmanTree.getNode(bit, temp);
				//System.out.print(bit);
				if((c = temp.character) != -1 ) break;
				bit = reader.getBit();
			}
			out.write(c);
			textLength--;
			c = -1;
			temp = huffmanTree.root;
			if(textLength <= 1) break;
		}

        // Leave these lines at the end of the method. 
        in.close();
        out.close();
    }
}
