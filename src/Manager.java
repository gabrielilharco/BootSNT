import java.util.List;
import java.util.Iterator;
import java.util.Map;

public class Manager {
	
	public static void main(String[] args) {
		try {
			Map<Integer, Hyperlink> map = DBHyperlink.selectComplete();
			for (Map.Entry<Integer, Hyperlink> iterator : map.entrySet())
			{
				if (iterator.getValue().comments.size() > 0)
					System.out.println("hp: " + iterator.getValue().value
							         + "comment " + iterator.getValue().comments.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
