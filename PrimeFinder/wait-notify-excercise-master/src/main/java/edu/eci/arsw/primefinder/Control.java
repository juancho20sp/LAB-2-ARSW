/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.edu.eci.arsw.primefinder;

import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 1;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    List<Integer> test;

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];

        this.test = new LinkedList<>();

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, TMILISECONDS, test);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, TMILISECONDS, test);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
       // test.notifyAll();
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
    }

    public void rerun(){
        test.notifyAll();
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
