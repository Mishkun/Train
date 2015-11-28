import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select(".li-link");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nFound events: (%d)", links.size());
        for (Element link : links) {   
			Document t_doc = Jsoup.connect(link.attr("abs:href")).get();
			Elements dateE = t_doc.select(".date");
			Elements descrE = t_doc.select(".li-dsc div");
			Element date = dateE.first();
			Element descr = descrE.first();
			String dateS = date.text();
			dateS = dateS.substring(0,1)+" "+dateS.substring(2,dateS.length());
			print("\nDate: (%s)",dateS);
			String descrS = descr.html();
			int point = descrS.indexOf("<br>");
			descrS = descrS.substring(0,point);
			print("\nDescription: %s",descrS);	
        }
        
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}