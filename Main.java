package dref;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Main cek = new Main();
		check();
		banner();
		System.out.println("S = single url");
		System.out.println("D = list url");
		System.out.print("Type S or D : ");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			String name = input.readLine().toLowerCase();
			if (name.equals("s")) {
				System.out.print("Type url with protocol : ");
				String url = input.readLine();
				Document doc = Jsoup.connect(url).get();
				Elements newsHeadlines = doc.select("a");
				for (Element headline : newsHeadlines) {
					log("%s", headline.absUrl("href"));
				}
			} else if (name.equals("d")) {
				System.out.print("Type path : ");
				String path = input.readLine();
				try {
					List<String> allLines = Files.readAllLines(Paths.get(path));
					for (String line : allLines) {
						Document doc = Jsoup.connect(line).get();
						Elements newsHeadlines = doc.select("a");
						for (Element headline : newsHeadlines) {
							log("%s", headline.absUrl("href"));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}
	
	private static void log(String msg, String... vals) {
		System.out.println(String.format(msg, vals));
	}
	
	private static void check() {
		Process p;
		try {
			p = Runtime.getRuntime().exec("getprop ro.build.version.sdk");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			int k = Integer.valueOf(br.readLine());
			if (26 >= k) {
				System.out.println("SDK version " + k + " is not supported");
				System.exit(1);
			} else {
				p.waitFor();
				p.exitValue();
				p.destroy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void banner() { 
String banner = ".-. .-. .-. .-.\n"
+"|  )|(  |-  |- \tFind all href in url\n"
+"`-' ' ' `-' '  \tversion 1.0\n";
System.out.println(banner);
 }
}