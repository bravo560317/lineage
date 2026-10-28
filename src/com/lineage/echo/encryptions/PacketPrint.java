package com.lineage.echo.encryptions;

public class PacketPrint
{
    private static PacketPrint _data;
    
    public static PacketPrint get() {
        if (PacketPrint._data == null) {
            PacketPrint._data = new PacketPrint();
        }
        return PacketPrint._data;
    }
    
    public String printData(byte[] data, int len) {
        StringBuffer result = new StringBuffer();
        int counter = 0;
        for (int i = 0; i < len; ++i) {
            if (counter % 16 == 0) {
                result.append(this.fillHex(i, 4) + ": ");
            }
            result.append(this.fillHex(data[i] & 0xFF, 2) + " ");
            if (++counter == 16) {
                result.append("   ");
                int charpoint = i - 15;
                for (int a = 0; a < 16; ++a) {
                    int t1 = data[charpoint++];
                    if (t1 > 31 && t1 < 128) {
                        result.append((char)t1);
                    }
                    else {
                        result.append('.');
                    }
                }
                result.append("\n");
                counter = 0;
            }
        }
        int rest = data.length % 16;
        if (rest > 0) {
            for (int j = 0; j < 17 - rest; ++j) {
                result.append("   ");
            }
            int charpoint = data.length - rest;
            for (int a = 0; a < rest; ++a) {
                int t1 = data[charpoint++];
                if (t1 > 31 && t1 < 128) {
                    result.append((char)t1);
                }
                else {
                    result.append('.');
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
    
    private String fillHex(int data, int digits) {
        String number = Integer.toHexString(data);
        for (int i = number.length(); i < digits; ++i) {
            number = "0" + number;
        }
        return number;
    }
}
