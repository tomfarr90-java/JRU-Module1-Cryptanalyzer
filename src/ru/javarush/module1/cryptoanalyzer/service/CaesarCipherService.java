package ru.javarush.module1.cryptoanalyzer.service;

import ru.javarush.module1.cryptoanalyzer.entity.Alphabet;
import ru.javarush.module1.cryptoanalyzer.repository.FileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class CaesarCipherService extends AbstractCipherService {
    private final Alphabet alphabet;


    public CaesarCipherService(Alphabet alphabet, FileHandler fileHandler) {
        this.alphabet = alphabet;

    }

    @Override
    public String processLine(String line, int key) {
        StringBuilder result = new StringBuilder();
        int size = alphabet.getSize();
        for(char c : line.toCharArray()) {
            int index = alphabet.getIndex(c);
            if(index != -1) {
                int newIndex = (index + (key % size) + size) % size;
                result.append(alphabet.getChar(newIndex));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    @Override
    public void processFile(BufferedReader reader, BufferedWriter writer, int key) throws IOException {
        super.processFile(reader, writer, key);
    }

    public int getAlphabetSize() {
        return alphabet.getSize();
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }
}
