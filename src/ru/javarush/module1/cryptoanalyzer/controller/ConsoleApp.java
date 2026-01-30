package ru.javarush.module1.cryptoanalyzer.controller;

import ru.javarush.module1.cryptoanalyzer.exception.AppException;
import ru.javarush.module1.cryptoanalyzer.repository.FileHandler;
import ru.javarush.module1.cryptoanalyzer.service.BruteForceService;
import ru.javarush.module1.cryptoanalyzer.service.CaesarCipherService;
import ru.javarush.module1.cryptoanalyzer.service.StatisticalAnalyzerService;
import ru.javarush.module1.cryptoanalyzer.util.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleApp {
    private FileHandler fileHandler;
    private CaesarCipherService caesarCipherService;
    private Validator validator;
    private Scanner scanner;
    private BruteForceService bruteForceService;
    private StatisticalAnalyzerService service;

    public ConsoleApp(FileHandler fileHandler, CaesarCipherService caesarCipherService, Validator validator, Scanner scanner, BruteForceService bService, StatisticalAnalyzerService service) {
        this.fileHandler = fileHandler;
        this.caesarCipherService = caesarCipherService;
        this.validator = validator;
        this.scanner = scanner;
        this.bruteForceService = bService;
        this.service = service;
    }

    public void run() {
        System.out.println("Добро пожаловать в программу шифрования!");

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("\n1.Шифр Цезаря");
            System.out.println("2.Brute Force");
            System.out.println("3.Статистический анализ");
            System.out.println("0.Выход");

            String choice = scanner.nextLine();
            if (choice.isEmpty()) continue;
            switch (choice) {
                case "1" -> runCaesarMenu();
                case "2" -> processFileWithBruteForce();
                case "3" -> processFileWithStatisticalAnalyze();
                case "0" -> {
                    System.out.println("Выход из программы");
                    return;
                }
                default -> System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }

    private void processFileWithStatisticalAnalyze() {
        try {
            System.out.println("----Взлома файла методом статистического анализа----");
            System.out.println("Введите путь зашифрованного файла:");
            System.out.println("Формат для Windows например C:\\users\\ваша учетка\\in.txt");
            System.out.println("Формат для Unix/Linux например /home/ваша учетка/in.txt");
            String inputPath = scanner.nextLine();
            validator.validateInputFile(inputPath);

            System.out.println("Введите путь куда сохранить расшифрованный файл: ");
            System.out.println("Формат для Windows например C:\\users\\ваша учетка\\in.txt");
            System.out.println("Формат для Unix/Linux например /home/ваша учетка/in.txt");
            String outputPath = scanner.nextLine();
            validator.validateFilePath(outputPath);

            try (BufferedReader reader = fileHandler.readFile(inputPath);
                 BufferedWriter writer = fileHandler.writeFile(outputPath)) {
                service.processFile(reader, writer, 0);
                System.out.println("Операция успешно завершена.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода " + e);
        } catch (AppException e) {
            System.out.println("Ошибка приложения " + e.getMessage());
        }
    }


    public void runCaesarMenu() {
        System.out.println("Меню для выбора Шифр Цезаря!");

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("\n1.Шифровать");
            System.out.println("2.Расшифровать");
            System.out.println("0.Вернуться в главное меню.");

            String choice = scanner.nextLine().trim();
            if (choice.isEmpty()) continue;
            switch (choice) {
                case "1" -> performCaesarOperation(true);
                case "2" -> performCaesarOperation(false);
                case "0" -> {
                    System.out.println("Возврат в главное меню.");
                    return;
                }
                default -> System.out.println("Неверный ввод, попробуйте снова.");

            }
        }
    }

    public void performCaesarOperation(boolean isEncrypt) {
        try {
            System.out.println("Введите путь к исходному файлу:");
            System.out.println("Формат для Windows например C:\\users\\ваша учетка\\in.txt");
            System.out.println("Формат для Unix/Linux например /home/ваша учетка/in.txt");
            String inputPath = scanner.nextLine();
            validator.validateInputFile(inputPath);

            System.out.println("Введите путь где будет хранится зашифрованный файл: ");
            System.out.println("Формат для Windows например C:\\users\\ваша учетка\\in.txt");
            System.out.println("Формат для Unix/Linux например /home/ваша учетка/in.txt");
            String outputPath = scanner.nextLine();
            validator.validateFilePath(outputPath);

            System.out.println("Введите ключ для сдвига позиции: ");
            String keyString = scanner.nextLine();

            int key = validator.validateKey(keyString, caesarCipherService.getAlphabetSize());
            int finalKey = isEncrypt ? key : -key;

            try (BufferedReader reader = fileHandler.readFile(inputPath);
                 BufferedWriter writer = fileHandler.writeFile(outputPath)) {
                caesarCipherService.processFile(reader, writer, finalKey);
                System.out.println("Операция успешно завершена.");
            }

        } catch (Exception e) {
            if (e.getCause() instanceof java.nio.charset.MalformedInputException || e.toString().contains("Input length")) {
                System.out.println("Ошибка: Файл закодирован в кодировке, отличной от UTF-8!");
            } else {
                System.out.println("Ошибка: " + e.getMessage());

            }
        }
    }

    public void processFileWithBruteForce() {
        try {
            System.out.println("----Расшифровка файла методом взлома BruteForce----");
            System.out.println("Введите путь, где лежит файл который надо расшифровать: ");
            String inputPath = scanner.nextLine();
            validator.validateInputFile(inputPath);

            System.out.println("Введите путь для сохранения результата:");
            String outputPath = scanner.nextLine();
            validator.validateFilePath(outputPath);

            try (BufferedReader reader = fileHandler.readFile(inputPath);
                 BufferedWriter writer = fileHandler.writeFile(outputPath)) {

                bruteForceService.processFile(reader, writer, 0);
                System.out.println("Операция взлома завершена.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке файла" + e.getMessage());
        } catch (AppException e) {
            System.out.println("Введен не корректный путь:");
        }
    }
}




