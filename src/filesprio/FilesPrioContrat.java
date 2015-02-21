package filesprio;

public class FilesPrioContrat<T> extends FilesPrioDecorator<T> {

	public FilesPrioContrat(FilesPrio<T> fp) {
		super(fp);
	}
	
	private void checkInvariants() throws Exception {
		// \inv: empty() = size() == 0 
		if (super.empty() != (super.size() == 0))
			throw new InvariantError("empty() = size() == 0");
		
		// \inv: size() = \sum_{\forall i \in activePrios()} : sizePrio(i)
		int size = 0;
		for (Integer i : super.activePrios())
			size += super.sizePrio(i);
		
		if ( ! (super.size() == size))
			throw new InvariantError("size() = \\sum_{\\forall i \\in activePrios()} : sizePrio(i)");
					
		// \inv: \forall i \in activePrios() : sizePrio(i) > 0
		// \inv: \forall i !\in activePrios() : sizePrio(i) == 0
		// \inv: \forall i \in activePrios() : \for k = 1 \to sizePrio(i) : getElem(i, k) != null
		boolean testedIn, testedNotIn;
		testedIn = testedNotIn = false;
		
		for (int i = 0; i <= super.activePrios().size(); i++) {
			boolean isContained = super.activePrios().contains(i);
			
			if ( ! testedIn && isContained) {
				testedIn = true;
				if (super.sizePrio(i) <= 0)
					throw new InvariantError("\\forall i \\in activePrios() : sizePrio(i) > 0");
				
				for (int k = 0; k < super.sizePrio(i); k++)
					if (getElem(i, k) == null)
						throw new InvariantError("\\forall i \\in activePrios() : \\for k = 1 \\to sizePrio(i) : getElem(i, k) != null");
			}
			else if ( ! testedNotIn && ! isContained) {
				testedNotIn = true;
				if (super.sizePrio(i) > 0)
					throw new InvariantError("\\forall i !\\in activePrios() : sizePrio(i) == 0");				
			}
			else if (testedIn && testedNotIn)
				break;
		}
	}
	
	public void init() throws Exception {
		super.init();
		checkInvariants();
		
		// \post: size() == 0
		if (! (size() == 0))
			throw new PostConditionError("size() == 0");
	}

	public void putPrio(int i, T e) throws Exception {
		checkInvariants();
		
		if (!(i >= 0 && e != null))
			throw new PreConditionError("i >= 0 && e != null");
		
		final int sizePrio_pre = super.sizePrio(i);
		
		super.putPrio(i, e);
		
		if (super.sizePrio(i) != sizePrio_pre)
			throw new PostConditionError("sizePrio(i) == sizePrio(i)@pre + 1 ");
		
		checkInvariants();
	}	
}
