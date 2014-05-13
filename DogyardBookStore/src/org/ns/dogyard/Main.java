package org.ns.dogyard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.omg.CORBA.portable.InputStream;
import org.omg.IOP.TransactionService;

class Book{
	String book_id;
	String title;
	float price;
	int nSales;
	Book()
	{
		nSales=0;
	}	
	@Override
	public String toString() {
		return (book_id + " : Rs." + price + " : " + title);
	}
	public static Comparator<Book> SalesComparator = new Comparator<Book>()
			{
				@Override
				public int compare(Book b1, Book b2) {
					if (b1.nSales == b2.nSales)
						return b1.book_id.compareTo(b2.book_id);
					else
						return Integer.valueOf(b2.nSales).compareTo(Integer.valueOf(b1.nSales));
				}
			};
	public static Comparator<Book> SalesRComparator = new Comparator<Book>()
			{
				@Override
				public int compare(Book b1, Book b2) {
					if (b1.nSales == b2.nSales)
						return b1.book_id.compareTo(b2.book_id);
					else
						return Integer.valueOf(b1.nSales).compareTo(Integer.valueOf(b2.nSales));
				}
			};
	public float getPrice()
	{
		return price;
	}
}

class Transaction{
	String customer_id;
	List books;
	Float value;
	@Override
	public String toString() {
		return (customer_id + " : " + value);
				
	}
	public static Comparator<Transaction> TransactionAmountComparator = new Comparator<Transaction>()
	{
		@Override
		public int compare(Transaction o1, Transaction o2) {
			return o2.value.compareTo(o1.value);
		}
	};
}
class FrequentCustomers implements Comparable{
	String customer_id;
	Integer nvisits;
	@Override
	public String toString() {
		return customer_id + ":" + nvisits;
	}
	@Override
	public int compareTo(Object object) {
		FrequentCustomers obj = (FrequentCustomers)object;
		if (obj.nvisits == this.nvisits)
			return this.customer_id.compareTo(obj.customer_id);
		else
			return obj.nvisits.compareTo(this.nvisits);
	}
}
public class Main {
	static String transaction_file=null;
	static String price_file=null;
	static Integer N=-1;
	static Integer V=-1;
	static String  c_id="";
	
	static List<Transaction> transactions = new ArrayList<>();
	static Map<String, Book> books = new HashMap<String, Book>();
	
	public static void discountCheck(String c_id)
	{		
		Float total_purchases=(float) 0;
		for (int i=0; i<transactions.size(); i++)
		{
			Transaction temp = transactions.get(i);

			if (temp.customer_id.compareTo(c_id)==0)
			{
				total_purchases += temp.value;
				if (total_purchases>=V)
				{
					System.out.println(1);
					return;
				}
			}
		}
		System.out.println(0);
	}
	public static void readPriceList (String price_file) throws IOException
	{
		FileReader fr = new FileReader(new File(price_file));
		BufferedReader br = new BufferedReader(fr);
		String line=null;
		while (br.ready())
		{
			line = br.readLine();
			Book book = new Book();
			Scanner sc = new Scanner(line).useDelimiter(",");
			book.book_id = sc.next();
			book.price = Float.parseFloat(sc.next());
			book.title = sc.next();
			books.put(book.book_id, book);
		}
	}
	public static void reportByBookSales(int...dir)
	{
		Book books_[] = new Book[books.size()];
		Iterator it = books.keySet().iterator();
		int i=0;
		while (it.hasNext())
		{
			Book temp = books.get((String)it.next());
			books_[i++] = temp;
		}
		if (dir.length>0)
			Arrays.sort(books_, Book.SalesRComparator);
		else
			Arrays.sort(books_, Book.SalesComparator);
		int prev=books_[0].nSales, curr=-1;
		int nDistinct=0;
		for (i=0; i<books_.length; i++)
		{
			curr = books_[i].nSales;
			if (curr != prev)
			{
				nDistinct++;
				prev = curr;
			}
			if(nDistinct < N)
			System.out.print(books_[i].book_id+ " " + books_[i].nSales + ",");
			
		}
		System.out.println();
	}
	public static void reportByBillAmount()
	{
		Collections.sort(transactions, Transaction.TransactionAmountComparator);
		for (int i=0; i<N && i<transactions.size(); i++)
		{
			Transaction temp = transactions.get(i);
			System.out.print(temp.customer_id + " " + temp.value.intValue() + ",");
		}
		System.out.println();
	}
	public static void reportByCustomerFrequency()
	{
		Map<String, Integer> freq = new HashMap<>();
		Iterator<Transaction> it = transactions.iterator();
		String customer=null;
		while (it.hasNext())
		{
			customer = it.next().customer_id;
			if(freq.containsKey(customer))
				freq.put(customer, freq.get(customer)+1);
			else
				freq.put(customer, 1);
		}
		FrequentCustomers freq_[] = new FrequentCustomers[freq.size()];
		int i=0;
		Iterator<String> it2 = freq.keySet().iterator();
		while (it2.hasNext())
		{
			customer=it2.next();
			freq_[i] = new FrequentCustomers();
			freq_[i].customer_id = customer; 
			freq_[i++].nvisits = freq.get(customer);
		}
		Arrays.sort(freq_);
		int prev=freq_[0].nvisits, curr=-1;
		int nDistinct=0;
		for (i=0; i<freq_.length; i++)
		{
			curr = freq_[i].nvisits;
			if (curr != prev)
			{
				nDistinct++;
				prev = curr;
			}
			if(nDistinct < N)
				System.out.print(freq_[i].customer_id + " " + freq_[i].nvisits + ",");
		}
		System.out.println();
	}
	public static void readTransactions(String transaction_file) throws IOException
	{
		FileReader fr = new FileReader(new File(transaction_file));
		BufferedReader br = new BufferedReader(fr);
		String line=null;
		String book_id=null;
		while (br.ready())
		{
			line = br.readLine();
			Transaction trans = new Transaction();
			Scanner sc = new Scanner(line).useDelimiter(",");
			trans.customer_id = sc.next();
			trans.value = (float) 0;
			trans.books = new ArrayList<Book>();
			while (sc.hasNext())
			{
				book_id = sc.next();
				Book temp = books.get(book_id);
				trans.books.add(temp);
				trans.value += temp.getPrice();
				temp.nSales++;
				books.put(book_id, temp);
				
			}
			transactions.add(trans);
		}
	}
	public static void parseArgs (String[] args)
	{
		if (args.length % 2 != 0)
		{
			System.out.println("invalid no. of arguments");
			System.exit(-1);
		}
		for (int i=0; i<args.length; i+=2)
		{
			args[i] = args[i].replaceAll("[^a-z]", "");
			char arg= args[i].toLowerCase().charAt(0);
			switch (arg)
			{
				case 't' : 
					transaction_file = args[i+1];
					break;
				case 'p' :
					price_file = args[i+1];
					break;
				case 'r' :
					try {
					N=Integer.parseInt(args[i+1]);
					}
					catch (NumberFormatException e) {
						System.out.println("Invalid argument: " + args[i] + " " + args[i+1]);
					}
					break;
				case 'd' :
					try {
					V= Integer.parseInt(args[i+1]);
					}
					catch (NumberFormatException e) {
						System.out.println("Invalid argument: " + args[i] + " " + args[i+1]);
					}
					break;
				case 'c' :
					c_id = args[i+1];
					break;
				default:
					System.out.println("Invalid Arguments");
					System.exit(0);
			}
		}
	}
	public static void main(String[] args) throws IOException 
	{
		String cmd = Arrays.toString(args);
		System.out.println(cmd);
		parseArgs(args);
		readPriceList(price_file);
		readTransactions(transaction_file);
		
		if (V>0 && c_id.length()>0)
		{
			discountCheck(c_id);
		}
		else
		{
			reportByCustomerFrequency();
			reportByBillAmount();
			reportByBookSales();
			reportByBookSales(1);
		}
	}

}
