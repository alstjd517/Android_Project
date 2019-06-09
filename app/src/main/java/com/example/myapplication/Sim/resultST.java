package com.example.myapplication.Sim;


public class resultST implements Comparable<resultST>{
    private  String name;
    private  double measure;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }
    @Override
    public int compareTo(resultST f) { //오버라이딩
        // TODO Auto-generated method stub
        double aa =this.measure;
        double bb =f.measure;

        if(  aa >  bb ) {
            return -1;
        }else if(aa < bb) {
            return -1;
        }else {
            return 0;
        }
    }

}
