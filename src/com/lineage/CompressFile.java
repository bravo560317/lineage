package com.lineage;

import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class CompressFile
{
    private static final int _buffered_size = 1024;
    
    public synchronized void zip(String inputFilename, String zipFilename) throws IOException {
        this.zip(new File(inputFilename), zipFilename);
    }
    
    public synchronized void zip(File inputFile, String zipFilename) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));
        try {
            this.zip(inputFile, out, "");
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            out.close();
        }
    }
    
    private synchronized void zip(File inputFile, ZipOutputStream out, String base) throws IOException {
        if (inputFile.isDirectory()) {
            File[] inputFiles = inputFile.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base + "/";
            for (int i = 0; i < inputFiles.length; ++i) {
                this.zip(inputFiles[i], out, base + inputFiles[i].getName());
            }
        }
        else {
            if (base.length() > 0) {
                out.putNextEntry(new ZipEntry(base));
            }
            else {
                out.putNextEntry(new ZipEntry(inputFile.getName()));
            }
            FileInputStream in = new FileInputStream(inputFile);
            try {
                byte[] by = new byte[1024];
                int c;
                while ((c = in.read(by)) != -1) {
                    out.write(by, 0, c);
                }
            }
            catch (IOException e) {
                throw e;
            }
            finally {
                in.close();
            }
        }
    }
}
