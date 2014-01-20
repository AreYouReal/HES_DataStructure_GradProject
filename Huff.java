/* 
 * Huff.java
 *
 * A program that compresses a file using Huffman encoding.
 *
 * <your name>, <your e-mail address>
 * <current date>
 */ 

import java.io.*;

public class Huff {

    /* Put any methods that you add here. */
	
	private static int header[] = new int[128];				// Info for Huffman tree building
	private static GradProjectDataStruct huffmanTree;		// Instance of my DataStrucute
	private static int textLength;							// Length of text

	private static void writeHeader(ObjectOutputStream oos, int[] header) throws IOException
	{
		for(int i = 0; i < 128; i++)
		{
			oos.writeInt(header[i]);
		}
		oos.writeInt(textLength);
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
     * main method for compression.  Takes command line arguments. 
     * To use, type: java Huff input-file-name output-file-name 
     * at the command-line prompt. 
     */ 
    public static void main(String[] args) throws IOException {
        FileReader in = null;               // reads in the original file
        ObjectOutputStream out = null;      // writes out the compressed file

        // Check for the file names on the command line.
        if (args.length != 2) {
            System.out.println("Usage: java Huff <in fname> <out fname>");
            System.exit(1);
        }

        // Open the input file.
        try {
            in = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[0]);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new ObjectOutputStream(new FileOutputStream(args[1]));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[1]);
            System.exit(1);
        }
    
        // Create a BitWriter that is able to write to the compressed file.
        BitWriter writer = new BitWriter(out);

		 int ch;
			while((ch = in.read()) != -1)
			{
				header[ch]++;
				textLength++;
			}
        
        // Create Huffman tree
        createAndFillHuffmanTree(header);
		
		// Write the header to the file
        writeHeader(out, header);
        
        // Open file in a second time
        in = new FileReader(args[0]);
		Code code;
		int bit;
		ch = 0;
		while((ch = in.read()) != -1)
		{
			//System.out.print((char)ch);
			code = huffmanTree.getCode(ch);
			for(int i = 0; i < code.length(); i++)
			{
				bit = code.getBit(code.length()-1 - i);
				//System.out.print(bit);
				writer.putBit(bit);
			}
		}

         //Leave these lines at the end of the method. 
        in.close();
        out.close();
    }
}
