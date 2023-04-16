import java.util.Scanner;
import java.util.Random;

/**
 * This is the main class of the random page replacement algorithm. It simulates
 * random page replacement, and prints out the amount of page faults to the
 * user.
 * 
 * @author Nathan Sanchez 
 * For CSC428 Operating Systems Project #2 
 * Instructor: Dr. Thede
 *
 */
public class Main {
	/**
	 * A simple method that checks if a value is within an array.
	 * 
	 * @param arr:   array to be checked.
	 * @param check: value we are searching for in the array.
	 * @return true or false, dependent if the value is in the array
	 */
	public static boolean contains(int[] arr, int check) {
		for (int i : arr) {
			if (i == check) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A simple method that prints the contents of an Array. Used for debugging
	 * purposes.
	 * 
	 * @param arr: array to be printed out.
	 */
	public static void printArray(int[] arr) {
		for (int i : arr) {
			System.out.print(i + " ");
		}
	}

	public static void main(String[] args) {
		// Initialize number of page faults.
		int pageFaults = 0;
		// Initialize Random to use later when generating random indexes.
		// Also initialize the int variable where we store that index.
		Random indexGenerator = new Random();
		int randomIndex;
		System.out.println("Hello! Welcome to the Page Replacement Simulator!");
		System.out.println("Currently, we are using a Random replacement algorithm.");
		// Get amount of virtual pages from the user, while setting up a Scanner.
		System.out.print("How many virtual pages are in the process? ");
		Scanner s = new Scanner(System.in);
		int virtualPages = s.nextInt();
		// Get number of frames allocated.
		System.out.print("How many frames are allocated to the process? ");
		int frames = s.nextInt();
		// Get number of page references from the user.
		System.out.print("How many page references would you like to simulate? ");
		int pageReferences = s.nextInt();
		// Initialize an array based on how many frames the user declared. Represents
		// physical memory.
		int[] pageArray = new int[frames];
		// Initialize an array based on how many page references the user would like to
		// simulate.
		int[] references = new int[pageReferences];

		// Fills out the references array with random page numbers.
		for (int i = 0; i < references.length; i++) {
			Random r = new Random();
			int randomNum = r.nextInt(virtualPages) + 1;
			references[i] = randomNum;
		}
		/*
		 * Create a temporary index that is used to fill out the frames representing
		 * physical memory. This is only used when the frame Array is not full. When it
		 * is full, then we start randomly replacing frames in the Array.
		 */
		int tempIndex = 0;
		// Iterate through every page reference to see if it is in physical memory.
		for (int i = 0; i < references.length; i++) {
			/*
			 * Check to see if physical memory is not full, and that the value at that frame
			 * is empty. If it is, we set that frame equal to the reference in the loop.
			 */

			if ((!contains(pageArray, references[i]) && pageArray[tempIndex] == 0)) {
				pageArray[tempIndex] = references[i];
				// Since we had to access physical memory, we increment the amount of page
				// faults.
				pageFaults++;
				/*
				 * If we are at the end of the physical memory array, we start at the beginning
				 * by resetting the tempIndex to 0.
				 */
				if (tempIndex == pageArray.length - 1) {
					tempIndex = 0;
				} else {
					/*
					 * Otherwise, since we replaced a value in physical memory, we increment to the
					 * next index in physical memory.
					 */
					tempIndex++;
				}
			}
			/*
			 * Otherwise, physical memory is full, and we need to randomly replace a frame
			 * in the Array.
			 */
			else if (!contains(pageArray, references[i])) {
				// Generate a random index, and replace a frame in physical memory at that
				// index.
				randomIndex = indexGenerator.nextInt(frames);
				pageArray[randomIndex] = references[i];
				pageFaults++;
			}
		}
		// Print out how many page faults occurred, as well as the percentage of it.
		float pageFaultPercent = ((float) pageFaults / pageReferences) * 100;
		System.out.println("The algorithm produced " + pageFaults + " page faults.");
		System.out.println("The chance of a page fault was " + pageFaultPercent + "%.");
		// Close the scanner to prevent memory leaks.
		s.close();

	}

}
