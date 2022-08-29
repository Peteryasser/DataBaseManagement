package org.example.src.main.java.org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Admin {

    public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("echo 'Hello World'");
        processBuilder.directory(new File("/home/ahmed/Desktop/Project/Server/DataBaseMangmentSystem/DataBaseMangmentSystem/out/artifacts/Server_jar"));
       Process p = processBuilder.start();
    }
}
