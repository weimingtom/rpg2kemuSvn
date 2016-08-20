import java.io.*;
import rpg2k.analyze.*;

public class Analyzer
{
	public static void main(String[] args) {
		switch(args.lenth) {
			case 1:
				try {
					Analyze.print(args[0]);
				} catch(Exception e) {
					printUsage();
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					Analyze.print(args[0], args[1]);
				} catch(Exception e) {
					printUsage();
					e.printStackTrace();
				}
				break;
			default:
				printUsage();
		}
	}
	
	static void printUsage() {
		System.out.println("Usage > java Analyzer (analyzing file)");
		System.out.println("Usage > java Analyzer (analyzing file) (output file)");
	}
}
