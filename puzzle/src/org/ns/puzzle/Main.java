package org.ns.puzzle;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class Main {
	public static ArrayList<String> allCombos = new ArrayList<String>();	//list of all combinations of values that sum upto a given value
	private static int X;	//Total amount available with us
	private static  int n;	//no. types of items
	public static Integer[] P;	//price of the item type
	static int maxLenTillNow = 0;	//temporary variable to hold the max no. of items found till now in current recursion path
	
	/**
	 * Returns all possible combinations of the amount 'rem'  - 3rd argument
	 * that can be made with the denominations available in P[] (class level variable)
	 * The function follows a backtracking algorithm to reach a solution.
	 * If a complete solution is not reached, handled by (if temp<0) that recursion path will return
	 * and will continue with another combination
	 * 
	 * @param soln temporary string that stores solutions like : 4, 4, 4...,9
	 * @param i where to start with in the array P[]
	 * @param rem remaining amount to be reached
	 */
	private static void findAllCombos(String soln, int i, int rem) {
	    for(int j=i; j<P.length ;j++) {
	        int temp = rem - P[j];
	        String tempSoln = soln + "" + P[j]+ ","; //temporary solution adding P[i] following condition checks if it can be added,
	        if(temp < 0) {							//else we will stop the current recursion here
	        	break;								//String soln is an immutable type so it exists only in the current recursion stack  
	        }										//Using any other mutable type we will have to handle, appending, removal, deallocation etc.
	        if(temp == 0) {							//eg. StringBuffer is tough to have used or a List/Set or anything similar
	         // reached the answer hence quit from the loop
	         if (tempSoln.length() > maxLenTillNow)
	         {
	        	 allCombos.add(tempSoln);	//add this solution to the list only if it's more items than the current soln
	        	 maxLenTillNow = tempSoln.length(); //temporary variable to hold the max no. of items found till now in current recursion path
	         }
	         break;
	        } 
	        else {
	        	findAllCombos(tempSoln, j, temp);
	        }
	     }
	 }
	public static void main (String args[])
	{
		X = 100; //Total amount available with us
		int minCost = 0; //minimum cost of purchasing each item atleast once
		int remAmt = -1; //remaining amt after mincost i.e X-minCost
		n =3; //no. types of items
		P = new Integer[n]; //price of the item type
		Integer Ps[] = {4, 6, 9}; // to be accepted as input
		
		for (int i=0; i<n; ++i)
			P[i] = Ps[i];
		
		HashMap<String, Integer> purchase = new HashMap<>();		//map to item price and it's count to be purchased
		
		//min 1 of each price is required:
		for (int i = 0; i<Ps.length; i++)
			purchase.put(P[i].toString(), 1);
		
		for (int i=0; i<n; ++i)
			minCost +=  P[i]; 	//all items to be purchased atleaset one quantity
		remAmt = X - minCost;	//Amount after minimum purchase
		
		findAllCombos("", 0, remAmt);		//find all combinations of 'P'  summing upto remAmt 
		int nMaxItem = 0;
		String final_combo = allCombos.get((allCombos.size()-1));		//last element will have the max num of items
		
		String combo_[] = final_combo.split(",");
		for (int i=0; i<combo_.length; ++i)
		{
			Integer curr = null;
			curr = purchase.get(combo_[i]);
			curr++;
			purchase.put(combo_[i], curr);
		}
		Set<String>keys = purchase.keySet();
		Iterator<String> it = keys.iterator();
		int count = 0;
		while (it.hasNext())	//count the total no. of items
			count += purchase.get(it.next());
		
		System.out.println("Max count of items :" + count);
		System.out.println("Price & count of it to be purchased in given total:" + X);
		System.out.println(purchase);
		System.out.println();
	}
}
