package org.ns.kthhigh;

import java.io.ObjectInputStream.GetField;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ns.kthhigh.MinHeap;;

public class Main {
	static MinHeap minheap;
	static Set kSet = new HashSet<Integer>();
	private static int createHeap(Integer arr[], int k)
	{
		int i = 0;
		//put k distinct elements into the array
		while (kSet.size() <= k && i < arr.length)
			kSet.add(arr[i++]);
		minheap = new MinHeap();
		Iterator<Integer> it = kSet.iterator();
		while (it.hasNext())
			minheap.add(it.next());
		return i;//return the last index to start comparison
	}
	private static int getKthLargest(Integer arr[], int k)
	{
		int kthLargest = Integer.MIN_VALUE;
		//create a MinHeap with first k distinct elements in the array:
		int i = createHeap(arr, k-1);
		
		//from kth element to n, if element is larger than the heap root, remove current root and insert new element
		//and re-heap
		for (; i<arr.length; i++)
		{
			if ((arr[i]) > (Integer)minheap.peek() && !kSet.contains(arr[i]))
			{
				kthLargest = (Integer)minheap.remove();
				kSet.remove(kthLargest);
				minheap.add(arr[i]);
				kSet.add(arr[i]);
			}	
		}
		//the current root will be the kth largest
		kthLargest = (Integer)minheap.remove();
		return kthLargest;
	}
	public static void main (String args[])
	{
		Integer arr[] = {1, 23, 12, 9, 30, 30, -2, 50};
		System.out.println(getKthLargest(arr, 3));
	}
}
