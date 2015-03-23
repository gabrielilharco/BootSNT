import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

public class Manager {
	
	public static void main(String[] args) {
		try {
			ArrayList<Hyperlink> hyperlinks = DBHyperlink.selectComplete();
			for (int i = 0; i < hyperlinks.size(); i++) {
				System.out.println(hyperlinks.get(i).id + " " + hyperlinks.get(i).comments.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
