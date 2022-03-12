

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String[][] entryArray = new String[3][3];
        entryArray[0][0] = "Ludzie";
        entryArray[1][0] = "Sport";
        entryArray[2][0] = "Historia";

        entryArray[0][1] = "Karol Wojtyla";
        entryArray[1][1] = "Curling";
        entryArray[2][1] = "Unia lubelska";

        entryArray[0][2] = "Papiez POlak";
        entryArray[1][2] = "Rzadki sport";
        entryArray[2][2] = "wydarzenia z xvii wkieku";


        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz sposrod kategorii :");

        for (int i = 0; i < entryArray.length; i++) {
            System.out.println(i+1 + "." + entryArray[i][0]);
        }

        int index = scanner.nextInt();
        if(index <1){
            System.out.println("NIeprawidłowy indeks");
        }

        String entry = entryArray[index-1][1];
        String[] entryTab = entry.split("");
        String[] outTab = new String[entryTab.length];
        for (int i = 0; i < outTab.length; i++) {
            outTab[i] = "_";
        }


        System.out.println(entryArray[index-1][2]+ ". Wyraz " + entry.length() + "-znakowy" );

        for (int i = 0; i < outTab.length; i++) {
            System.out.print(outTab[i]+" ");
        }
        System.out.println("POdaj litere:");
        String letter;
        boolean ifContain;
        int indexOfFoundLetter;

        while(true){
            letter = scanner.next();
            if(ifContain = checkIfContain(letter,entryTab)){
                indexOfFoundLetter = findIndex(letter,entryTab);
            }


        }


    }


    public static boolean checkIfContain(String letter, String[] entryTab){

        for (int i = 0; i < entryTab.length; i++) {
            if (entryTab[i].equals(letter)) {
                return true;
            }
        }
        return false;
    }

    public static int findIndex(String letter, String[] entryTab){
        int index = 0;

        return index;
    }

}
