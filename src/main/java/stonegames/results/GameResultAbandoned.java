package stonegames.results;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.tinylog.Logger;
import stonegames.model.Player;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Read and write the game date to Json file
 */
public class GameResultAbandoned implements Comparator<Player> {


    /**
     * Save the data of the game and player
     *
     * @param player player,
     * @throws IOException In case of failed or interrupted I/O operations.
     */
    public void saveData(Player player) throws IOException {
        try {

            File file=new File("GameData.json");

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);

            fileOutputStream.write(player.toString().getBytes());
            fileOutputStream.write("\r\n".getBytes());
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Player player = new Player();
        player.setPlayerName("aaa");
        player.setCount(6);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter("GameData.json",true);
        gson.toJson(player, writer);

        System.out.println(gson.fromJson(new FileReader("GameData.json"), Player.class));
    }

    /**
     * Reads the top ten player data for Json file
     *
     * @return List<Player>,
     * @throws IOException In case of failed or interrupted I/O operations.
     */
    public static List<Player> findTop10Player() throws IOException {

        List<Player> top10Players = new ArrayList<Player>();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("GameData.json"));
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream));
            String string = null;
            while((string = buffer.readLine() )!= null){ //use buffer.readline read one line at a time
                Player player = new Gson().fromJson(string, Player.class);
                top10Players.add(player);
            }
            fileInputStream.close();

            top10Players.sort((o1, o2) -> o2.getScore()-o1.getScore());
            Logger.info("top10Players:"+top10Players);
            return top10Players;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Compare two players by their scores,
     *
     * @param o1 player1,
     * @param o2 player2,
     * @return score.
     */
    @Override
    public int compare(Player o1, Player o2) {
        // TODO Auto-generated method stub
        return o1.getScore()-o2.getScore();
    }

}
