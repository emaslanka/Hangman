import java.sql.*;
import java.util.*;


public class Main {

    private  static final String GET_CATEGORIES = "select category FROM categories";

    private  static final String GET_WORDS = "select word from categories JOIN words" +
            "            ON words.category_id=categories.category_id" +
            "            WHERE categories.category =?";
    private static final String GET_HINT = "select hint from words where word=?";




    public static void main(String[] args) {

        String h1 = "=======";
        String h2 =

                "      |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=======";

        String h3 =
                " +----+\n" +
                        "      |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=======";


        String h4 =
                " +----+\n" +
                        " O    |\n" +
                        "      |\n" +
                        "      |\n" +
                        "=======";

        String h5 =
                " +----+\n" +
                        " O    |\n" +
                        " |    |\n" +
                        "      |\n" +
                        "=======";
        String h6 =
                " +----+\n" +
                        " O    |\n" +
                        "\\|/   |\n" +
                        "      |\n" +
                        "=======";

        String h7 =
                " +----+\n" +
                        " O    |\n" +
                        "\\|/   |\n" +
                        "/ \\   |\n" +
                        "=======";

        String[] hangmans = {h7,h6,h5,h4,h3,h2,h1};



        int chances = 7;
        int idxOfWord;
        List<String> wordsList = new ArrayList<>();
        List<String> usedWrongLetter = new ArrayList<>();
        String hint = null;
        String entry = null;


        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one category:");


        //Get available categories from database

        try(final Connection connection = DBUtil.connect()) {
            List<String> categories = DBUtil.DataArray(connection,GET_CATEGORIES,"category");

            for (int i = 0; i < categories.size(); i++) {
                            System.out.println(i+1 + "." + categories.get(i));
            }

            int numberOfCategory = scanner.nextInt();
            if(numberOfCategory<1 || numberOfCategory > categories.size()){
                System.out.println("Incorrect index. Choose one number from 1 to "+wordsList.size());
            }

            String nameOfCategory = categories.get(numberOfCategory-1);
            // get words from given category and put them into list
           wordsList = DBUtil.getWords(connection,GET_WORDS,nameOfCategory,"words");
           //get random words
            Random rand = new Random();
            idxOfWord = rand.nextInt(wordsList.size());

            // entry - random  word from list
            entry = wordsList.get(idxOfWord);
            hint = DBUtil.getHint(connection,GET_HINT,entry,"hint");

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //split word into table
        String[] entryTab = entry.replaceAll("\\s+","").split("");

        //table of chars "_"
        String[] outTab = new String[entryTab.length];
        for (int i = 0; i < outTab.length; i++) {
            outTab[i] = "_";
        }

        System.out.println(hint+ ". " + entryTab.length + "-characters password" );
        printWordAsArray(outTab);
        System.out.println();
        System.out.println("One letter:");

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
                if(chances==0){
                    System.out.println(hangmans[chances]);
                    System.out.println("GAME OVER");
                } else{
                    System.out.println(hangmans[chances]);
                    System.out.println("It was bad idea. Try again! "+ chances + " chance/s left");
                }
                }



        }
        scanner.close();
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
