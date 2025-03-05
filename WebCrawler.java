import java.util.*;
import java.util.concurrent.*;

interface HtmlParser {
    List<String> getUrls(String url);
}

public class WebCrawler {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        // Extract hostname from the start URL
        String hostName = getHostName(startUrl);

        // Result list to store crawled URLs
        List<String> res = new ArrayList<>();
        
        // Set to track visited URLs to avoid duplicates
        Set<String> visited = new HashSet<>();
        
        // Blocking queue to manage URLs to be crawled
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        
        // Deque to track ongoing crawling tasks
        Deque<Future> tasks = new ArrayDeque<>();

        // Add start URL to the queue
        queue.offer(startUrl);

        // Create a thread pool with 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(4, r -> {
            Thread t = new Thread(r);
            // Use daemon threads to allow program exit
            t.setDaemon(true);
            return t;
        });

        while (true) {
            // Poll a URL from the queue
            String url = queue.poll();
            
            if (url != null) {
                // Check if URL belongs to the same hostname and hasn't been visited
                if (getHostName(url).equals(hostName) && !visited.contains(url)) {
                    // Add URL to results and mark as visited
                    res.add(url);
                    visited.add(url);
                    
                    // Submit a task to fetch and process URLs for this page
                    tasks.add(executor.submit(() -> {
                        // Get URLs from the current page
                        List<String> newUrls = htmlParser.getUrls(url);
                        
                        // Add new URLs to the queue
                        for (String newUrl : newUrls) {
                            queue.offer(newUrl);
                        }
                    }));
                }
            } else {
                if (!tasks.isEmpty()) {
                    // Wait for the next task to complete
                    Future nextTask = tasks.poll();
                    try {
                        nextTask.get();
                    } catch (InterruptedException | ExecutionException e) {}
                } else {
                    // Exit when all tasks are completed
                    break;
                }
            }
        }
        
        return res;
    }
    
    // Helper method to extract hostname from URL
    private String getHostName(String url) {
        url = url.substring(7);  // Remove "http://"
        String[] parts = url.split("/");
        return parts[0];
    }

    public static void main(String[] args) {
        // Mock HtmlParser implementation for testing
        HtmlParser parser = new HtmlParser() {
            private Map<String, List<String>> urlMap = new HashMap<>();
            {
                // Configure URL relationships
                urlMap.put("http://news.yahoo.com", Arrays.asList(
                    "http://news.yahoo.com/news",
                    "http://news.yahoo.com/us"
                ));
                urlMap.put("http://news.yahoo.com/news", Arrays.asList(
                    "http://news.yahoo.com/news/topics/"
                ));
                urlMap.put("http://news.yahoo.com/news/topics/", Collections.emptyList());
                urlMap.put("http://news.google.com", Collections.emptyList());
                urlMap.put("http://news.yahoo.com/us", Collections.emptyList());
            }

            @Override
            public List<String> getUrls(String url) {
                return urlMap.getOrDefault(url, Collections.emptyList());
            }
        };

        // Create web crawler instance
        WebCrawler crawler = new WebCrawler();
        
        // Test cases
        System.out.println("Test Case 1 - Start with yahoo.com:");
        List<String> result1 = crawler.crawl("http://news.yahoo.com", parser);
        System.out.println("Crawled URLs: " + result1);

        System.out.println("\nTest Case 2 - Start with google.com:");
        List<String> result2 = crawler.crawl("http://news.google.com", parser);
        System.out.println("Crawled URLs: " + result2);
    }
}