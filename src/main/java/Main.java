

import java.util.ArrayList;
import java.util.List;
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
        entryArray[2][2] = "wydarzenia z xvii wieku";

        int chances = 5;
        List<String> usedWrongLetter = new ArrayList<>();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz sposrod kategorii :");

        for (int i = 0; i < entryArray.length; i++) {
            System.out.println(i+1 + "." + entryArray[i][0]);
        }

        int index = scanner.nextInt();
        if(index <1){
            System.out.println("NIeprawidłowy indeks");
        }else if(index> entryArray.length){
            System.out.println("Nieprawidłowy indeks. Podaj liczbę z przedziału 1-"+entryArray.length);
        };

        // entry - chosen word
        String entry = entryArray[index-1][1];
        //split word into table
        String[] entryTab = entry.split("");
        //table of chars "_"
        String[] outTab = new String[entryTab.length];
        for (int i = 0; i < outTab.length; i++) {
            outTab[i] = "_";
        }


        //Introduction to game

        System.out.println(entryArray[index-1][2]+ ". Wyraz " + entry.length() + "-znakowy" );

        printWordAsArray(outTab);

        System.out.println("Podaj litere:");
        String letter;

        while(chances>0&&checkIfFully(outTab)) {
            letter = scanner.next();

            if(checkIfContainInd(letter,entryTab)!=-1){
                //Replace "_" with letter if ok
                outTab[checkIfContainInd(letter,entryTab)] = letter;
                printWordAsArray(outTab);

            }else{
                //Check if letter is in List "UsedLetter" to avoid repeating letters and mistakes
                if(usedWrongLetter.contains(letter)){
                    System.out.println("Again?Really? You've already given it");
                }else
                {usedWrongLetter.add(letter);}

                chances --;
                System.out.println("It was bad idea. Try again! "+ chances + " chance/s left");
            }

        }
    }


    // Function to check if word contains letter given by user and return index of letter

    public static int checkIfContainInd(String letter, String[] entryTab){

        int index = -1;
        for (int i = 0; i < entryTab.length; i++) {
            if (entryTab[i].equalsIgnoreCase(letter)) {
                index = i;
            }
        }
        return index;
    }

    // Function to check if word is fully guessed
    public static boolean checkIfFully(String[] outTab){

        for (int i = 0; i < outTab.length; i++) {
            if(outTab[i].equals("_")){
                return true;
            }
        }
        return false;
    }

    //Function to print letters

    public static void printWordAsArray(String[] outTab){

        for (int i = 0; i < outTab.length; i++) {
            System.out.print(outTab[i]+" ");
        }
    }



}
