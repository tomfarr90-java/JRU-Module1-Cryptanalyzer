package ru.javarush.module1.cryptoanalyzer.util;

import ru.javarush.module1.cryptoanalyzer.exception.AppException;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    public int validateKey(String keyStr, int alphabetSize) {
        int key;
        try {
            key = Integer.parseInt(keyStr);
        } catch (NumberFormatException e) {
            throw new AppException("Ошибка: ключ должен быть целым числом!");
        }
        if (key < 0) {
            throw new AppException("Ошибка: ключ не может быть отрицательным (для расшифровки выберите пункт меню)");
        }
        return key;
    }

    public void validateFilePath(String filePath) {
        if(filePath == null || filePath.isEmpty()) {
            throw new AppException("Путь входного файла не может быть пустым.");
        }
        Path path = Path.of(filePath);
        if(Files.isDirectory(path)) {
            throw new AppException("Ошибка: путь указывает на папку, а не на файл.");
        }
    }

    public void validateInputFile(String filepath) {
        validateFilePath(filepath);
        if(!Files.exists(Path.of(filepath))) {
            throw new AppException("Не найден входной файл или имя указано неверно: " + filepath);
        }
    }
}
