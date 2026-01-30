package ru.javarush.module1.cryptoanalyzer.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class CipherService {

    public abstract String processLine(String line, int key);

    public void processFile(BufferedReader reader, BufferedWriter writer, int key) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(processLine(line, key));
            writer.newLine();
        }
    }
}
