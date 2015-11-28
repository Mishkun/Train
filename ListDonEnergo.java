import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Программа подсасывает события из ДонЭнерго, WIP
 * запускать из консоли java -classpath .;./lib/jsoup-1.8.3.jar ListLinks http://www.donenergo.ru/consumer/graphs_of_the_time_constraints/40/1/ 
 */
public class ListDonEnergo {
    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch"); //проверяет наличие аргумента - ссылки
        String url = args[0]; //забирает ссылку в строку
        print("Fetching %s..", url);

        Document doc = Jsoup.connect(url).get(); //подсасывает Document из данной ссылки
		//Потрошим таблицу, выбираем tr, только находящиеся внутри tbody, исключаем .bottom,.top
		Elements events = doc.select("tbody tr").not(".bottom,.top");

        print("\nFound events: (%d)", events.size());		
		for (Element event : events) {   
			print("\nDescription: %s",event.text());	//TODO выпотрошить дальше отдельные элементы
        }
        
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}