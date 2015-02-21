package filesprio;

public class FilesPrioTest {

	public static void main(String[] args) throws Exception {
		FilesPrio<Integer> fp = new FilesPrioImpl<>();
		FilesPrioContrat<Integer> contrat = new FilesPrioContrat<>(fp);
		contrat.init();
		contrat.empty();
		
	}

}
