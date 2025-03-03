package com.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;

public class RandomAccessFileTest {

    @SneakyThrows
    @Test
    public void readFromFile(){
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/test/resources/file1.txt", "r");
        randomAccessFile.seek(0);
        byte[] bytes = new byte[11];
        randomAccessFile.read(bytes);
        randomAccessFile.close();
        String output = new String(bytes);
        System.out.println(output);
    }

    @SneakyThrows
    @Test
    public void writeToFile(){
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/test/resources/file1.txt", "rw");
        randomAccessFile.seek(0);
        randomAccessFile.write("Hello World".getBytes());
        randomAccessFile.close();
    }
}