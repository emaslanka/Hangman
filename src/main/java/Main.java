import java.sql.*;
import java.util.*;


public class Main {

    private  static final String GET_CATEGORIES = "select category FROM categories";
    private  static final String GET_CATEGORY_ID = "select category_id FROM categories WHERE category=?";

    private  static final String GET_WORDS = "select word_id,word from categories JOIN words" +
            "            ON words.category_id=categories.category_id" +
            "            WHERE categories.category_id = 7;";
    private static final String GET_HINT = "select hint from words where word_id=1";



    public static void main(String[] args) {

        // exampleofdata
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

//        for (int i = 0; i < entryArray.length; i++) {
//            System.out.println(i+1 + "." + entryArray[i][0]);
//        }

        //Get available categories from database

        try(final Connection connection = DBUtil.connect()) {
            List<String> categories = DBUtil.DataArray(connection,GET_CATEGORIES,"category");

            for (int i = 0; i < categories.size(); i++) {
                            System.out.println(i+1 + "." + categories.get(i));
            }

            int numberOfCategory = scanner.nextInt();
            String nameOfCategory = categories.get(numberOfCategory-1);

            // get category_id from database
           int categoryId = Integer.parseInt(DBUtil.indexOfCategory(connection,GET_CATEGORY_ID,nameOfCategory,"category_id"));


        } catch (SQLException e) {
            e.printStackTrace();
        }


        int index = scanner.nextInt();

        if(index <1 || index > entryArray.length){
            System.out.println("Nieprawidłowy indeks Podaj liczbę z przedziału 1-"+entryArray.length);
        }

        // entry - chosen word
        String entry = entryArray[index-1][1];
        //split word into table
        String[] entryTab = entry.replaceAll("\\s+","").split("");

        //table of chars "_"
        String[] outTab = new String[entryTab.length];
        for (int i = 0; i < outTab.length; i++) {
            outTab[i] = "_";
        }


        //Introduction to game

        System.out.println(entryArray[index-1][2]+ ". Hasło " + entryTab.length + "-znakowe" );

        printWordAsArray(outTab);

        System.out.println("Podaj litere:");
        String letter;

        while(chances>0&&checkIfFully(outTab)) {
            letter = scanner.next();

            // list to collect all indices where letter occurs
            List <Integer> indices = checkIfContainInd(letter,entryTab);

            if(indices.size()>0){
                //Replace "_" with letter if ok
                for (int i = 0; i < indices.size(); i++) {
                    outTab[indices.get(i)] = letter;
                }
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

            scanner.close();

        }
    }


    // Function to check if word contains letter given by user and return index of letter

    public static List<Integer> checkIfContainInd(String letter, String[] entryTab){

        List <Integer> indices = new ArrayList<>();

        for (int i = 0; i < entryTab.length; i++) {
            if (entryTab[i].equalsIgnoreCase(letter)) {
               indices.add(i);
            }
        }
        return indices;
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
