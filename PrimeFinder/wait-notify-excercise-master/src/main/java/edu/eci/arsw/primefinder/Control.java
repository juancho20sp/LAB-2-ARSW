/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 2;
    private final static int MAXVALUE = 30000000;
//    private final static int MAXVALUE = 20;

    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    List<Integer> listItems;
    List<Integer> synchronizedObject;
    private final Object lock = new Object();

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.synchronizedObject = new LinkedList<>();
        this.listItems = new LinkedList<>();
        this.pft = new PrimeFinderThread[NTHREADS];


        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, TMILISECONDS, lock, this.listItems);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, TMILISECONDS, lock, this.listItems);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        System.out.println("Starting threads...");

        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();

            // Set initial time on all threads
            pft[i].setInitialTime(new Date().getTime());
        }
    }

    public boolean rerun(){
        System.out.println("Total size: " + this.listItems.size());
        for(int i = 0;i < NTHREADS;i++ ) {
            // Set initial time on all threads
            pft[i].setInitialTime(new Date().getTime());
        }
        synchronized (lock){
            lock.notifyAll();
        }
        if(this.listItems.size() == 1857859){
            return false;

            }
        return true;
    }




    public void stopThreads(){
        try {
            for(int i = 0;i < NTHREADS;i++ ) {
                pft[i].wait();
            }
        } catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

}
