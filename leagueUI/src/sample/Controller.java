package sample;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Controller {
String sumId ="";
String[][] rankInfo = new String[1][6];
String[][] matchData = new String[10][5];
    @FXML
    private TextField Username;

    @FXML
    private ImageView p1s1,p1s2,p2s1,p2s2,p3s1,p3s2,p4s1,p4s2,p5s1,p5s2,p6s1,p6s2,p7s1,p7s2,p8s1,p8s2,p9s1,p9s2,p10s1,p10s2;

    @FXML
    private Label p1w1,p2w2,p3w3,p4w4,p5w5,p6w6,p7w7,p8w8,p9w9,p10w10;

    @FXML
    private Label p1,p2,p3,p4,p5,p6,p7,p8,p9,p10;

    @FXML
    private Label p1rank,p2rank,p3rank,p4rank,p5rank,p6rank,p7rank,p8rank,p9rank,p10rank;


    @FXML
    private ImageView p1pic,p2pic,p3pic,p4pic,p5pic,p6pic,p7pic,p8pic,p9pic,p10pic;














    public void getSummonerID()throws Exception {
         String link;
         String summonerName = Username.getText();

         if (summonerName.contains(" ")) {
             link = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-name/" + summonerName.replaceAll(" ", "%20") + "?api_key=RGAPI-dc4dbd40-b812-4995-94ce-09f25216cc34";

         } else
             link = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-name/" + summonerName + "?api_key=RGAPI-dc4dbd40-b812-4995-94ce-09f25216cc34";
         URL url = new URL(link);
         BufferedReader in = new BufferedReader(
                 new InputStreamReader(url.openStream()));

         String inputLine;
         inputLine = in.readLine();

         in.close();

         Gson code = new Gson();

         summonerId id = code.fromJson(inputLine, summonerId.class);

         String account = code.toJson(id.id);

         String account2 = account.replaceAll("\"", "");

         sumId = account2;
     }

    private void getRank(String id)throws Exception {

        String link;
        link = "https://na1.api.riotgames.com/lol/league/v3/positions/by-summoner/" + id + "?api_key=RGAPI-dc4dbd40-b812-4995-94ce-09f25216cc34";
        URL url = new URL(link);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        String inputLine2;
        inputLine = in.readLine();
        in.close();
        String[] account = new String[6];

        Gson code = new Gson();
        sumStats[] rank = code.fromJson(inputLine, sumStats[].class);
        int x =0;

        if(code.toJson(rank[x].getQueueType()).contains("RANKED_FLEX_SR")){
            x = 1;

        }


        account[0] = code.toJson(rank[x].getTier());
        account[0] = account[0].replaceAll("\"", "");
        rankInfo[0][0]=account[0];
        account[1] = code.toJson(rank[x].getRank());
        account[1] = account[1].replaceAll("\"", "");
        rankInfo[0][1]=account[1];

        account[2] = code.toJson(rank[x].getLeaguePoints());
        account[2] = account[2].replaceAll("\"", "");
        rankInfo[0][2]=account[2];

        account[3] = code.toJson(rank[x].getWins());
        account[3] = account[3].replaceAll("\"", "");
        rankInfo[0][3]=account[3];

        account[4] = code.toJson(rank[x].getLosses());
        account[4] = account[4].replaceAll("\"", "");
        rankInfo[0][4]=account[4];

        account[5] = code.toJson(rank[x].getHotStreak());
        account[5] = account[5].replaceAll("\"", "");
        rankInfo[0][5]=account[5];




    }

    private  void getMatch()throws Exception{

        String link;
        link = "https://na1.api.riotgames.com/lol/spectator/v3/active-games/by-summoner/"+sumId+"?api_key=RGAPI-dc4dbd40-b812-4995-94ce-09f25216cc34";
        URL url = new URL(link);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        inputLine = in.readLine();

        in.close();

        Gson code = new Gson();

        match rank = code.fromJson(inputLine,match.class);


        String[][] details = new String[10][5];

        for ( int i =0; i<details.length; i++){
            details[i][0] = rank.participants.get(i).getSummonerName();
            details[i][1] = rank.participants.get(i).summonerId;
            details[i][2] = rank.participants.get(i).championId;
            details[i][3] = rank.participants.get(i).getSpell1Id();
            details[i][4] = rank.participants.get(i).getSpell2Id();


        }
        matchData = details;


    }


    public void dothis(){
        try {
            getSummonerID();
            getMatch();

            p1.setText(matchData[0][0]);
            System.out.println(sumId);
            System.out.println("spells/"+matchData[0][3]+".png");
            System.out.println("spells/"+matchData[0][4]+".png");

            getRank(matchData[0][1]);
            p1rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p1w1.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p1pic.setImage(new Image("champs/"+matchData[0][2]+".png"));
            p1s1.setImage(new Image("spells/"+matchData[0][3]+".png"));
            p1s2.setImage(new Image("spells/"+matchData[0][4]+".png"));





            System.out.println(rankInfo[0][0]);
            //icon

            p2.setText(matchData[1][0]);
            getRank(matchData[1][1]);

            p2rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p2w2.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p2pic.setImage(new Image("champs/"+matchData[1][2]+".png"));
            p2s1.setImage(new Image("spells/"+matchData[1][3]+".png"));
            p2s2.setImage(new Image("spells/"+matchData[1][4]+".png"));

            //icon
            p3.setText(matchData[2][0]);
            getRank(matchData[2][1]);

            p3rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p3w3.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p3pic.setImage(new Image("champs/"+matchData[2][2]+".png"));
            p3s1.setImage(new Image("spells/"+matchData[2][3]+".png"));
            p3s2.setImage(new Image("spells/"+matchData[2][4]+".png"));

            //icon

            p4.setText(matchData[3][0]);
            getRank(matchData[3][1]);

            p4rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p4pic.setImage(new Image("champs/"+matchData[3][2]+".png"));
            p4s1.setImage(new Image("spells/"+matchData[3][3]+".png"));
            p4s2.setImage(new Image("spells/"+matchData[3][4]+".png"));
            p4w4.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            //icon
            p5.setText(matchData[4][0]);
            getRank(matchData[4][1]);

            p5rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p5w5.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p5pic.setImage(new Image("champs/"+matchData[4][2]+".png"));
            p5s1.setImage(new Image("spells/"+matchData[4][3]+".png"));
            p5s2.setImage(new Image("spells/"+matchData[4][4]+".png"));

            //icon

           p6.setText(matchData[5][0]);
            getRank(matchData[5][1]);
            p6w6.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p6rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p6pic.setImage(new Image("champs/"+matchData[5][2]+".png"));
            p6s1.setImage(new Image("spells/"+matchData[5][3]+".png"));
            p6s2.setImage(new Image("spells/"+matchData[5][4]+".png"));

            //icon

            p7.setText(matchData[6][0]);
            getRank(matchData[6][1]);
            p7w7.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p7rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p7pic.setImage(new Image("champs/"+matchData[6][2]+".png"));
            p7s1.setImage(new Image("spells/"+matchData[6][3]+".png"));
            p7s2.setImage(new Image("spells/"+matchData[6][4]+".png"));

            //icon

            p8.setText(matchData[7][0]);
            getRank(matchData[7][1]);
            p8w8.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p8rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p8pic.setImage(new Image("champs/"+matchData[7][2]+".png"));
            p8s1.setImage(new Image("spells/"+matchData[7][3]+".png"));
            p8s2.setImage(new Image("spells/"+matchData[7][4]+".png"));

            //icon

            p9.setText(matchData[8][0]);
            getRank(matchData[8][1]);
            p9w9.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);

            p9rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p9pic.setImage(new Image("champs/"+matchData[8][2]+".png"));
            p9s1.setImage(new Image("spells/"+matchData[8][3]+".png"));
            p9s2.setImage(new Image("spells/"+matchData[8][4]+".png"));

            //icon

            p10.setText(matchData[9][0]);

            getRank(matchData[9][1]);
            p10w10.setText("W: "+rankInfo[0][3] + " L: " +rankInfo[0][4]);


            p10rank.setText(rankInfo[0][0] + " " +rankInfo[0][1]);
            p10pic.setImage(new Image("champs/"+matchData[9][2]+".png"));
            p10s1.setImage(new Image("spells/"+matchData[9][3]+".png"));
            p10s2.setImage(new Image("spells/"+matchData[9][4]+".png"));

            //icon



        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}



