package edu.eci.arsw.primefinder;

import java.util.*;

public class PrimeFinderThread extends Thread{
	int a,b;
	int sleepTime;
	long initialTime;

	private Object lock;
	private List<Integer> listItems;
	
	public PrimeFinderThread(int a, int b, int sleep, Object lock, List<Integer> listItems) {
		super();
		this.listItems = listItems;
		this.lock = lock;
		this.a = a;
		this.b = b;
		this.sleepTime = sleep;
	}

	@Override
	public void run(){
		try{
			count();
		}catch (InterruptedException e){
			System.out.println(e.getStackTrace());
		}
	}

	private void count() throws InterruptedException{
		for (int i= a;i < b;i++) {
			if (isPrime(i)) {
				synchronized (lock) {
					this.listItems.add(i);
				}
			}

			// Thread.sleep(1000);
			System.out.println("Time elapsed: " + (new Date().getTime() - initialTime));

			if ((new Date().getTime() - initialTime) > this.sleepTime){
				synchronized (this.lock){
					System.out.println("Hay un total de " + this.listItems.size() + " numeros primos en la lista: " + this.listItems + " Hay un total de "  + this.listItems.size() + " primos en la lista.");
					this.lock.wait();
				}
			}
		}

	}

	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public List<Integer> getPrimes() {
		return this.listItems;
	}

	public long getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}
	
}
