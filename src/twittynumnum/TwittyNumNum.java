/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittynumnum;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwittyNumNum {

    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("6id0aTmQrXrfnijhEsC5wfzTO")
                .setOAuthConsumerSecret("E8A8nvssRUvQ0B9Td32L8hW4wyMyZwPQeAPsvj1EF1WK4HUTou")
                .setOAuthAccessToken("4350904703-rrfWDlkfRi5l2iwKj1EMpbzxmXu44aqfPL9LOlX")
                .setOAuthAccessTokenSecret("dS3dT6JNDD1ih6EHXCnBrBm7vDrMWFjcDCupyyiL6RkEu");

        //TwitterFactory tf = new TwitterFactory(cb.build());
        //Twitter twitter = tf.getInstance();
        //BackwardGrinder backy = new BackwardGrinder(twitter);
        // backy.grind("#glutenfree");
        PrintWriter out;
        try {
            out = new PrintWriter("data/#DebatRegionales.txt");
            TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
            FutureListener future = new FutureListener(twitterStream, out);
            String[] keywords = {"#Paris"};
            future.listen(keywords);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwittyNumNum.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
