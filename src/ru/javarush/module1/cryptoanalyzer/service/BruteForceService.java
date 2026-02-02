package ru.javarush.module1.cryptoanalyzer.service;

import ru.javarush.module1.cryptoanalyzer.entity.Alphabet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class BruteForceService extends CaesarCipherService {

    public BruteForceService(Alphabet alphabet) {
        super(alphabet);
    }

    @Override
    public void processFile(BufferedReader reader, BufferedWriter writer, int key) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            String line = reader.readLine();
            if (line != null) {
                builder.append(line).append("\n");
            }
        }
        String text = builder.toString();
        if (text.isEmpty()) return;
        int bestKey = findBestKey(text);
        writer.write(processLine(text, -bestKey));
        String lineNext;
        while ((lineNext = reader.readLine()) != null) {
            writer.write(processLine(lineNext, -bestKey));
            writer.newLine();
        }
    }

    public int findBestKey(String text) {
        int bestKey = 0;
        int maxScore = -1;
        for (int i = 0; i < getAlphabetSize(); i++) {
            String word = processLine(text, -i);
            int score = getScore(word);
            if (score > maxScore) {
                maxScore = score;
                bestKey = i;
            }
        }
        return bestKey;
    }

    private int getScore(String text) {
        int score = 0;
        if (text.contains(",")) score += 10;
        if (text.contains(".")) score += 10;
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.length() > 1 && word.length() <= 15) {
                if (word.equalsIgnoreCase("и")
                        || word.equalsIgnoreCase("в")
                        || word.equalsIgnoreCase("на")) {
                    score += 15;
                }
            }
        }
        return score;
    }
}



