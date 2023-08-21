package com.example.multithread2;

import javafx.collections.ObservableList;

public class ComplexWorker extends Thread{
    int k;
    String name;
    ObservableList<String> messages;

    public ComplexWorker(int k, String name) {
        this.k = k;
        this.name = name;
    }

    public ComplexWorker(int k, String name, ObservableList<String> messages) {
        this.k = k;
        this.name = name;
        this.messages = messages;
    }

    public void work() throws InterruptedException {
        while(k>0)
        {
            //System.out.println(name+" "+k+" working day");
            messages.add(name+" "+k+" working day");
            k--;
            sleep(15);
        }
        //System.out.println(name+" finish");
        messages.add(name+" finish");
    }

    @Override
    public void run() {
        try {
            work();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
