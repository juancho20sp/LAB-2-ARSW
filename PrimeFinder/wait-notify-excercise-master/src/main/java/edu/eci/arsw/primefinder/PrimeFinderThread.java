package edu.eci.arsw.primefinder;

import java.util.*;

public class PrimeFinderThread extends Thread{
	int a,b;
	int sleepTime;
	long initialTime;
	Date date;
	private Object lock;
	private List<Integer> listItems;
	
	public PrimeFinderThread(int a, int b, int sleep, Object lock, List<Integer> listItems) {
		super();
		this.listItems = listItems;
		this.lock = lock;
		this.a = a;
		this.b = b;
		this.sleepTime = sleep;

		this.date = new Date();
		//this.initialTime = this.date.getTime();
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
		System.out.println("  ---  running ---");
		System.out.println("  ---  running ---");
		System.out.println("  ---  running ---");

			//long initialTime = new Date().getTime();

			for (int i= a;i < b;i++) {
					if (isPrime(i)) {
						System.out.println(this.listItems);
						// $
						//this.listItems.add(i);
						synchronized (lock) {
							this.listItems.add(i);
						}
						System.out.println(i);
						System.out.println("Size: " + this.listItems.size());
					}

				System.out.println("initial time: " + initialTime);
				System.out.println("actual time: " + new Date().getTime());
				System.out.println("IF: " + (new Date().getTime() - initialTime));
				Thread.sleep(1000);

					if ((new Date().getTime() - initialTime) > this.sleepTime){

						System.out.println(" initialTime " + initialTime);
						System.out.println("aiuda");
						System.out.println("  ");



						//this.synchronizedObject.wait();
						synchronized (this.lock){
							System.out.println("Hay un total de " + this.listItems.size() + " numeros primos en la lista: " + this.listItems);
							this.lock.wait();
						}

						//initialTime = new Date().getTime();

					}
					//Thread.sleep(this.sleep);
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
