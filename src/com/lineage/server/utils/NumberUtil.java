package com.lineage.server.utils;

import java.util.Random;

public class NumberUtil
{
    public static int randomRound(double number) {
        double percentage = (number - Math.floor(number)) * 100.0;
        if (percentage == 0.0) {
            return (int)number;
        }
        else {
            int r = new Random().nextInt(100);
            if ((double)r < percentage) {
                return (int)number + 1;
            }
            else {
                return (int)number;
            }
        }
    }
}
