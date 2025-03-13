package laboratory.fsqsWholeSale.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;  // Importing List from java.util

@RestController
@RequestMapping("/api/rss")
public class RssController {

    @Autowired
    private RssService rssService;

    @GetMapping
    public List<String> getRssFeed() {
        return rssService.fetchRssFeed();
    }
}
