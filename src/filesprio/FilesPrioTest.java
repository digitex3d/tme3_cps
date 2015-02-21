package filesprio;

import java.util.ArrayList;
import java.util.List;

public class FilesPrioTest {

	public static void main(String[] args) throws Exception {
		final List<Integer> values = getTestValues(30);
		
		FilesPrio<Integer> fp = new FilesPrioImpl<>();
		System.out.println("Sans contrat");
		fp.init();
		for (Integer value : values)
			fp.put(value);

		FilesPrio<Integer> fp2 = new FilesPrioImpl<>();
		FilesPrioContrat<Integer> contrat = new FilesPrioContrat<>(fp2);
		System.out.println("Avec contrat");
		contrat.init();
		for (Integer value : values)
			contrat.put(value);
	}
	
	private static List<Integer> getTestValues(int capacity) {		
		List<Integer> values = new ArrayList<Integer>(capacity);
		for (int i = 0; i < capacity; i++)
			values.add((Math.random() > 0.5) ? i : null);

		return values;
	}

}
