package ru.javarush.module1.cryptoanalyzer;

import ru.javarush.module1.cryptoanalyzer.controller.ConsoleApp;
import ru.javarush.module1.cryptoanalyzer.entity.Alphabet;
import ru.javarush.module1.cryptoanalyzer.repository.FileHandler;
import ru.javarush.module1.cryptoanalyzer.service.BruteForceService;
import ru.javarush.module1.cryptoanalyzer.service.CaesarCipherService;
import ru.javarush.module1.cryptoanalyzer.service.StatisticalAnalyzerService;
import ru.javarush.module1.cryptoanalyzer.util.Validator;

import java.util.Scanner;


public class Main {
    static void main() {
        Alphabet alphabet = new Alphabet();
        FileHandler fileHandler = new FileHandler();
        Validator validator = new Validator();
        Scanner scanner = new Scanner(System.in);
        CaesarCipherService cipher = new CaesarCipherService(alphabet, fileHandler);
        BruteForceService bruteForceService = new BruteForceService(alphabet,fileHandler);
        StatisticalAnalyzerService service = new StatisticalAnalyzerService(alphabet,fileHandler);

        ConsoleApp app = new ConsoleApp(fileHandler,cipher,validator,scanner,bruteForceService,service);

        app.run();




    }
}
