package com.example.team6.uniclash;
public class Wave {
    int numRams;
    int numSpiders;
    int numTurkeys;

    public Wave(int numRams, int numSpiders, int numTurkeys){
        this.numRams = numRams;
        this.numSpiders = numSpiders;
        this.numTurkeys = numTurkeys;
    }

    public int getNumRams(){return numRams;}

    public int getNumSpiders(){return numSpiders;}

    public int getNumTurkeys(){return numTurkeys;}
}