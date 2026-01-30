package ru.javarush.module1.cryptoanalyzer.entity;

public class Alphabet {
    public static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

     public int getIndex(char c) {
         char lowerChar = Character.toLowerCase(c);
         for (int i = 0; i < ALPHABET.length; i++) {
             if (ALPHABET[i] == lowerChar) {
                 return i;
             }
         } return -1;
     }

     public char getChar(int index) {
             int size = ALPHABET.length;
             int normalIndex = (index % size + size) % size;
          return ALPHABET[normalIndex];
     }

     public int getSize() {
         return ALPHABET.length;
     }
}
