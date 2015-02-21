package filesprio;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	// On supprime la file de priorité i
	public void removePrio(int i) throws Exception {
		// Premier test des invariants
		checkInvariants();
		
		// ############## Préconditions ###############
		// \pre sizePrio(i) > 0
		if ( !(super.sizePrio(i) > 0) ) 
			throw new PreConditionError("!(sizePrio(i) > 0)");
	
		
		super.removePrio(i);
		
		/*
		// \pre: sizePrio(i) > 0
		// \post: sizePrio(i) > 1 \imply activePrios() = activePrios()@pre
		// \post: sizePrio(i) == 1 \imply activePrios() = activePrios()@pre \ {i}
		// \post: sizePrio(i) == sizePrio(i)@pre-1  
		// \post: \forall j \in activePrios()\ {i}, sizePrio(j) = sizePrio(j)@pre
		// \post: \forall k \in [1 .. sizePrio(i)-1], getElem(i,k) = getElem(i,k)@pre
		// \post: \forall activePrios() \{i}, \forall k \in [1 .. sizePrio(j)], 
		// getElem(j,k) == getElem(j,k)@pre
		 */	
		
		//TODO: Implementare postcondizioni
		
		checkInvariants();
	}	
	
}
