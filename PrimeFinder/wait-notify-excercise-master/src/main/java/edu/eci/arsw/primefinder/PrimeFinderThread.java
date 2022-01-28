package main.java.edu.eci.arsw.primefinder;

import java.util.*;

public class PrimeFinderThread extends Thread{
	int a,b;
	int sleepTime;
	long initialTime;
	Date date;


	private List<Integer> test;
	private List<Integer> primes;
	
	public PrimeFinderThread(int a, int b, int sleep, List<Integer> test) {
		super();
		this.primes = new LinkedList<>();
		this.test = test;
		this.a = a;
		this.b = b;
		this.sleepTime = sleep;

		this.date = new Date();
		this.initialTime = this.date.getTime();
	}

	@Override
	public void run(){

//            for (int i= a;i < b;i++){
//                if (isPrime(i)){
//                    primes.add(i);
//                    System.out.println(i);
//                }
//            }
		try{
			count();
		}catch (InterruptedException e){
			System.out.println(e.getStackTrace());
		}


	}

	public void startWait() {

	}


	private void count() throws InterruptedException{
			long initialTime = new Date().getTime();

			for (int i= a;i < b;i++) {
					if (isPrime(i)) {
						System.out.println(primes);
						synchronized (test) {
							primes.add(i);
						}
						System.out.println(i);
						System.out.println("Size: " + primes.size());
					}

				System.out.println("initial time: " + initialTime);
				System.out.println("actual time: " + new Date().getTime());
				System.out.println("IF: " + (new Date().getTime() - initialTime));
				Thread.sleep(1000);

					if ((new Date().getTime() - initialTime) > this.sleepTime){

						System.out.println(" initialTime " + initialTime);
						System.out.println("aiuda");
						System.out.println("  ");
						synchronized (test){
							test.wait();

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
		return primes;
	}
	
}
