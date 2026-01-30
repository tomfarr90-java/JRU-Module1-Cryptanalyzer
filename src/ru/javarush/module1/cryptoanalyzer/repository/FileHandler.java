package ru.javarush.module1.cryptoanalyzer.repository;

import ru.javarush.module1.cryptoanalyzer.exception.AppException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {

    public BufferedReader readFile(String filePath) {
        try {
            Path path = Path.of(filePath);
            return Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (MalformedInputException e) {
            throw new AppException("Файл закодирован в кодировке отличной от UTF-8" + filePath);
        }
        catch (IOException e) {
            throw new AppException("Невозможно прочитать файл" + filePath,e);
        }
    }

    public BufferedWriter writeFile(String filepath) throws IOException {
        Path path = Path.of(filepath);
        return Files.newBufferedWriter(path, StandardCharsets.UTF_8);
    }

}
