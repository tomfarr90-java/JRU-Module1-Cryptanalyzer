package ru.javarush.module1.cryptoanalyzer.service;

import ru.javarush.module1.cryptoanalyzer.entity.Alphabet;
import ru.javarush.module1.cryptoanalyzer.repository.FileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticalAnalyzerService extends CaesarCipherService {

    public StatisticalAnalyzerService(Alphabet alphabet) {
        super(alphabet);
    }

    @Override
    public void processFile(BufferedReader reader, BufferedWriter writer, int key) throws IOException {
        StringBuilder builder = new StringBuilder();
        List<String> firstLines = new ArrayList<>();
        String line;
        for (int i = 0; i < 100 && (line = reader.readLine()) != null; i++) {
            firstLines.add(line);
            builder.append(line).append("\n");
        }
        String sample = builder.toString();
        if (sample.isEmpty()) return;
        int bestKey = findBestKey(sample);
        System.out.println("Статистический анализ завершен. Подозрение на ключ: " + bestKey);
        for (String firstLine : firstLines) {
            writer.write(processLine(firstLine, -bestKey));
            writer.newLine();
        }
        while ((line = reader.readLine()) != null) {
            writer.write(processLine(line, -bestKey));
            writer.newLine();
        }
    }

    public int findBestKey(String encryptedText) {
        int bestKey = 0;
        int maxScore = -1;
        for (int key = 0; key < getAlphabet().getSize(); key++) {
            String attempt = processLine(encryptedText, -key);
            int currentScore = getPopularScore(attempt);
            if (currentScore > maxScore) {
                maxScore = currentScore;
                bestKey = key;
            }
        }
        return bestKey;
    }

    private int getPopularScore(String text) {
        int score = 0;
        String lowerText = text.toLowerCase();
        score += countChar(lowerText, ' ');
        score += countChar(lowerText, 'о');
        score += countChar(lowerText, 'е');
        score += countChar(lowerText, 'а');
        return score;
    }

    private int countChar(String text, char target) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == target) count++;
        }
        return count;
    }
}


