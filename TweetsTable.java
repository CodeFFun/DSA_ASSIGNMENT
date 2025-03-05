//4a

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetsTable {

    //first we will create a class that cacts as a tweets table
    // we will then loop through the date and only select date of february
    // we will then get the tweet of february and seperate the hastags and increase their count in hashmaps
    //we will the sort the hashmaps in descending order and return the top 3 answers

    public static class Tweet {
        @SuppressWarnings("unused")
        private final int userId;
        @SuppressWarnings("unused")
        private final int tweetId;
        private final String tweetDate;
        private final String tweet;

        public Tweet(int userId, int tweetId, String tweetDate, String tweet) {
            this.userId = userId;
            this.tweetId = tweetId;
            this.tweetDate = tweetDate;
            this.tweet = tweet;
        }

        public String getTweetDate() {
            return tweetDate;
        }

        public String getTweet() {
            return tweet;
        }
    }

    public static List<Map.Entry<String, Integer>> findTopTrendingHashtags(List<Tweet> tweets) {
        Map<String, Integer> hashtagCounts = new HashMap<>();
        Pattern hashtagPattern = Pattern.compile("#\\w+"); // Precompile regex pattern for efficiency

        for (Tweet tweet : tweets) {
            String date = tweet.getTweetDate();
            if (date.startsWith("2024-02-")) { // Check if the tweet is from February 2024
                String text = tweet.getTweet();
                Matcher matcher = hashtagPattern.matcher(text);
                while (matcher.find()) {
                    String hashtag = matcher.group();
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1); // Update count in HashMap
                }
            }
        }

        // Convert HashMap entries to a list for sorting
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCounts.entrySet());

        // Sort by count (descending), then by hashtag (descending)
        sortedHashtags.sort((entry1, entry2) -> {
            int countCompare = entry2.getValue().compareTo(entry1.getValue());
            return countCompare != 0 ? countCompare : entry2.getKey().compareTo(entry1.getKey());
        });

        // Return top 3 or fewer if there are less than 3 entries
        return sortedHashtags.subList(0, Math.min(3, sortedHashtags.size()));
    }

    public static void main(String[] args) {
        // Example input based on the problem statement
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet(135, 13, "2024-02-03", "Enjoying agent start to the day, #HappyDay #WorningVibes"));
        tweets.add(new Tweet(135, 14, "2024-02-05", "Another #HappyDay with good vibes: #FeelGood"));
        tweets.add(new Tweet(137, 15, "2024-02-04", "Productivity peaks #WorkLife #ProductivDay"));
        tweets.add(new Tweet(138, 16, "2024-02-04", "Exploring now tech frontiers. #TechLife #Innovation"));
        tweets.add(new Tweet(139, 17, "2024-02-05", "Gestitude for today's memers, #HappyDay #Thankful"));
        tweets.add(new Tweet(140, 18, "2024-02-07", "Innovation drive us. #TechLife #FutureTech"));
        tweets.add(new Tweet(141, 19, "2024-02-09", "Connecting with nature's severity, #Nature #Peaceful"));

        List<Map.Entry<String, Integer>> topHashtags = findTopTrendingHashtags(tweets);
        System.out.println("hashtag|count");
        for (Map.Entry<String, Integer> entry : topHashtags) {
            System.out.println(entry.getKey() + "  ------>  " + entry.getValue());
        }
    }
}