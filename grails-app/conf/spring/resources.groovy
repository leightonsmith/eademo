import org.smith.eademo.TwitterService

// Place your Spring DSL code here
beans = {
    twitterService(TwitterService) {
        consumerSecret = "RKbTbRqPqjR2bCzM6MN0wCQI9v4woH5GjgzRNwcnUI"
        consumerKey = "08cnUGc55SxD0C5IixGG6w"
        oauthTokenSecret = "tY5Hlsem7MwguVKeHlL4JVmG8WvNWu6TXTQaywZmI2Q" 
        oauthTokenKey = "118162545-ggJen71XWj0GO9nkBUiwBkf6KSaC1PaqytzEnPfc" 
        baseUrl = "https://api.twitter.com" 
    }
}
