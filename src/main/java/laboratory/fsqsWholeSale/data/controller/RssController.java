package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/rss")
public class RssController {

    @Autowired
    private RssService rssService;

    // Fetch latest RSS news from the default source
    @GetMapping("/news")
    public List<String> getNewsFeed() {
        return rssService.fetchRssFeed("https://lepatriote.ci/rss/category/economie");
    }

    // Fetch RSS news from a custom URL
    @GetMapping("/news/custom")
    public List<String> getCustomNewsFeed(@RequestParam String url) {
        // Validate URL
        if (!isValidUrl(url)) {
            return Collections.singletonList("<p class='text-red-500'>Invalid RSS URL</p>");
        }

        return rssService.fetchRssFeed(url);
    }

    // Helper method to validate URL
    private boolean isValidUrl(String url) {
        try {
            new URI(url).toURL();
            return true;
        } catch (URISyntaxException | IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
