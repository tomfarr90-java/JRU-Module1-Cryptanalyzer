package ru.javarush.module1.cryptoanalyzer.controller;

import ru.javarush.module1.cryptoanalyzer.entity.Alphabet;
import ru.javarush.module1.cryptoanalyzer.repository.FileHandler;
import ru.javarush.module1.cryptoanalyzer.service.BruteForceService;
import ru.javarush.module1.cryptoanalyzer.service.CaesarCipherService;
import ru.javarush.module1.cryptoanalyzer.service.StatisticalAnalyzerService;
import ru.javarush.module1.cryptoanalyzer.util.Validator;

import java.util.Scanner;

public class AppConfigurator {
    public static void startApp() {
        Alphabet alphabet = new Alphabet();
        FileHandler fileHandler = new FileHandler();

        ConsoleApp app = buildApp(alphabet, fileHandler);
        app.run();
    }

    private static ConsoleApp buildApp(Alphabet alphabet, FileHandler fileHandler) {
        return new ConsoleApp(
                fileHandler,
                new CaesarCipherService(alphabet),
                new Validator(),
                new Scanner(System.in),
                new BruteForceService(alphabet),
                new StatisticalAnalyzerService(alphabet, fileHandler)
        );
    }
}

