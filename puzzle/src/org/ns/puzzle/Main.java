package org.ns.puzzle;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.spec.PSource.PSpecified;

public class Main {
	public static ArrayList<String> allCombos = new ArrayList<String>();
	private static int X;
	private static  int n;
	public static Integer[] P;
	static int maxLenTillNow = 0;
	
	private static void findAllCombos(String soln, int i, int rem) {
	    for(int j=i; j<P.length ;j++) {
	        int temp = rem - P[j];
	        String tempSoln = soln + "" + P[j]+ ",";
	        if(temp < 0) {
	         break;
	        }
	        if(temp == 0) {
	         // reached the answer hence quit from the loop
	         if (tempSoln.length() > maxLenTillNow)
	         {
	        	 allCombos.add(tempSoln);
	        	 maxLenTillNow = tempSoln.length();
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
		int minCost = 0;
		int remAmt = -1;
		X = 100;
		n =3;
		P = new Integer[n];
		Integer Ps[] = {4, 6, 9}; // to be accepted as input
		
		for (int i=0; i<n; ++i)
			P[i] = Ps[i];
		
		//map to item price and it's count to be purchased
		HashMap<String, Integer> purchase = new HashMap<>();
		
		//min 1 of each price is required:
		for (int i = 0; i<Ps.length; i++)
			purchase.put(P[i].toString(), 1);
		
		//all items to be purchased atleaset one quantity
		for (int i=0; i<n; ++i)
			minCost +=  P[i];
		//Amount after minimum purchase
		remAmt = X - minCost;
		
		findAllCombos("", 0, remAmt);
		int nMaxItem = 0;
		//last element will have the max num of items
		String final_combo = allCombos.get((allCombos.size()-1));
		
		String combo_[] = final_combo.split(",");
		for (int i=0; i<combo_.length; ++i)
		{
			Integer curr = null;
			curr = purchase.get(combo_[i]);
			curr++;
			purchase.put(combo_[i], curr);
		}
		//print the total no.of items:
		Set<String>keys = purchase.keySet();
		Iterator<String> it = keys.iterator();
		int count = 0;
		while (it.hasNext())
			count += purchase.get(it.next());
		System.out.println("Max count of items :" + count);
		//print how many should be purchased
		System.out.println("Price & count of it to be purchased:");
		System.out.println(purchase);
	}
}
