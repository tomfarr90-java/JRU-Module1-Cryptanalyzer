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
        ConsoleApp app = buildApp(alphabet);
        app.run();
    }

    private static ConsoleApp buildApp(Alphabet alphabet) {
        return new ConsoleApp(
                new FileHandler(),
                new CaesarCipherService(alphabet),
                new Validator(),
                new Scanner(System.in),
                new BruteForceService(alphabet),
                new StatisticalAnalyzerService(alphabet)
        );
    }
}

