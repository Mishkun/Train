import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Программа подсасывает события из Водоканала
 * запускать из консоли java -classpath .;./lib/jsoup-1.8.3.jar ListLinks http://vodokanalrnd.ru/press-tsentr/operative-monitor/?year=2015 
 */
public class ListLinks {
    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch"); //проверяет наличие аргумента - ссылки
        String url = args[0]; //забирает ссылку в строку
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get(); //подсасывает Document из данной ссылки
        Elements links = doc.select(".li-link"); //потрошит страницу, ищет ссылки на отдельные события по CSS-классу li-link

        print("\nFound events: (%d)", links.size());
        for (Element link : links) {   
			Document t_doc = Jsoup.connect(link.attr("abs:href")).get();//подсасывает Document из каждой ссылки abs:href дает абсолютный адрес
			Elements dateE = t_doc.select(".date"); //выбирает коллекцию дат по классу date
			Elements descrE = t_doc.select(".li-dsc div"); // выбирает коллекцию описаний по классу li-dsc
			Element date = dateE.first(); //т.к. на странице событие только одно, выбираем его из коллекции
			Element descr = descrE.first(); // подбираем и его описание
			//далее приводим текст в божеский вид. 
			//дата вылазит в виде 22ноября 2015, поэтому вставляем пробел
			String dateS = date.text(); // метод text() выдает текст полученного элемента без тегов html
			dateS = dateS.substring(0,1)+" "+dateS.substring(2,dateS.length());
			print("\nDate: (%s)",dateS);
			//у текста описания удаляем лишние подписи об извинениях Водоканала
			String descrS = descr.html();//метод html() выдает текст элемента с тегами html
			descrS = descrS.substring(0,descrS.indexOf("<br>"));
			print("\nDescription: %s",descrS);	
        }
        
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}