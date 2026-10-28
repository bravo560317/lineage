package com.lineage.server.utils;

public class RandomArrayList
{
    private static int listint;
    private static double[] ArrayDouble;
    private static boolean haveNextGaussian;
    private static double nextGaussian;
    
    static {
        RandomArrayList.listint = 0;
        RandomArrayList.ArrayDouble = new double[32767];
        RandomArrayList.haveNextGaussian = false;
        RandomArrayList.listint = 0;
        while (RandomArrayList.listint < 32767) {
            RandomArrayList.ArrayDouble[RandomArrayList.listint] = Math.random();
            ++RandomArrayList.listint;
        }
    }
    
    public static void setArrayList() {
        RandomArrayList.listint = 0;
        while (RandomArrayList.listint < 32767) {
            RandomArrayList.ArrayDouble[RandomArrayList.listint] = Math.random();
            ++RandomArrayList.listint;
        }
    }
    
    private static int getlistint() {
        if (RandomArrayList.listint < 32766) {
            return ++RandomArrayList.listint;
        }
        else {
            return RandomArrayList.listint = 0;
        }
    }
    
    public static void getByte(byte[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (byte)(int)getValue(128);
        }
    }
    
    public static double getGaussian() {
        if (RandomArrayList.haveNextGaussian) {
            RandomArrayList.haveNextGaussian = false;
            return RandomArrayList.nextGaussian;
        }
        else {
            double s;
            double v1;
            double v2;
            do {
                v1 = 2.0 * RandomArrayList.ArrayDouble[getlistint()] - 1.0;
                v2 = 2.0 * RandomArrayList.ArrayDouble[getlistint()] - 1.0;
                s = v1 * v1 + v2 * v2;
            } while (s >= 1.0 || s == 0.0);
            double multiplier = Math.sqrt(-2.0 * Math.log(s) / s);
            RandomArrayList.nextGaussian = v2 * multiplier;
            RandomArrayList.haveNextGaussian = true;
            return v1 * multiplier;
        }
    }
    
    private static double getValue() {
        return RandomArrayList.ArrayDouble[getlistint()];
    }
    
    private static double getValue(int rang) {
        return getValue() * (double)rang;
    }
    
    private static double getValue(double rang) {
        return getValue() * rang;
    }
    
    public static int getInt(int rang) {
        return (int)getValue(rang);
    }
    
    public static int getInt(double rang) {
        return (int)getValue(rang);
    }
    
    public static double getDouble() {
        return getValue();
    }
    
    public static double getDouble(double rang) {
        return getValue(rang);
    }
    
    public static int getInc(int rang, int increase) {
        return getInt(rang) + increase;
    }
    
    public static int getInc(double rang, int increase) {
        return getInt(rang) + increase;
    }
    
    public static double getDc(int rang, int increase) {
        return getValue(rang) + (double)increase;
    }
    
    public static double getDc(double rang, int increase) {
        return getValue(rang) + (double)increase;
    }
}
