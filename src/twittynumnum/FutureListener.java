/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittynumnum;

import java.io.PrintWriter;
import java.util.Date;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.User;

/**
 *
 * @author chrisgaubla
 */
public class FutureListener {
    
    private final TwitterStream twitterStream;
    private final PrintWriter out;
    
    public FutureListener(TwitterStream twitterStream, PrintWriter out) {
        this.twitterStream = twitterStream;
        this.out = out;
    }
    
    public void listen(String[] keywords) {
        
        StatusListener listener = new StatusListener() {
            @Override
            public void onException(Exception arg0) {
                
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                
            }
            
            @Override
            public void onScrubGeo(long arg0, long arg1) {
                
            }
            
            @Override
            public void onStatus(Status status) {
                User user = status.getUser();
                String username = user.getScreenName();
                String profileLocation = user.getLocation();
                long tweetId = status.getId();
                String content = status.getText();
                Date tweetDate = status.getCreatedAt();
                out.print(tweetId +"\t"+tweetDate+ "\t" + username + "\t" + profileLocation + "\t" + content.replaceAll("\n", "/n") + "\n");
            }
            
            @Override
            public void onTrackLimitationNotice(int arg0) {
                
            }
            
            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        
        FilterQuery filter = new FilterQuery();
        
        filter.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(filter);
    }
    
}
