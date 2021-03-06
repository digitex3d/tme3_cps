package filesprio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;



public class FilesPrioContrat<T> extends FilesPrioDecorator<T> {
	// Le nombre de tests aléatoires effectués sur les ensembles
	public static final int ACCURACY= 3;

	public FilesPrioContrat(FilesPrio<T> fp) {
		super(fp);
	}
	
	private void checkInvariants() throws Exception {
		int idx;
		
		// \inv: size() = \sum_{\forall i \in activePrios()} : sizePrio(i)
		int size = 0;
		for (Integer i : super.activePrios())
			size += super.sizePrio(i);

		if ( ! (super.size() == size))
			throw new InvariantError("size() = \\sum_{\\forall i \\in activePrios()} : sizePrio(i)");

		// \inv: empty() = size() == 0 
		if (super.empty() != (super.size() == 0))
			throw new InvariantError("empty() = size() == 0");
		
		// \inv:isActivePrio(i) == i \in activePrios()
		for( int i = 0; i < ACCURACY; i++){
			idx = new Random().nextInt(super.size());
			if( (super.isActivePrio(idx) && super.activePrios().contains(idx)  ) ){
				throw new InvariantError("\\inv:isActivePrio(i) == i \\in activePrios()");
			}

		}
		
		// \inv: maxPrio() = max(activePrios(P)
		//TODO: implementare
		
		// \inv: getPrio(i) == getElem(i,1)
		for( int i = 0; i < ACCURACY; i++){
			idx = new Random().nextInt( super.size());
			if( !(super.getPrio(idx).equals(super.getElem(idx, 1)) )){
				throw new InvariantError("\\inv: getPrio(i) == getElem(i,1)");
			}

		}

		// \inv: get() == getPrio(maxPrio())
		if( !(super.get().equals( super.getPrio(super.maxPrio())))){
			throw new InvariantError("\\inv: get() == getPrio(maxPrio())");
		}

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
		
		// \pre: i >= 0 && e != null.
		if (!(i >= 0 && e != null))
			throw new PreConditionError("i >= 0 && e != null");
		
		final List<Integer> toTest = new ArrayList<Integer>();
		for (int x = 0; x < activePrios().size(); x++)
			if (x != i)
				toTest.add(x);
		Collections.shuffle(toTest);
		
		final List<Integer> sizePrioJ_pre = new ArrayList<Integer>();
		final List<List<T>> getElemJK_pre = new ArrayList<List<T>>();
		
		for (int j = 0; j < 3; j++) {
			sizePrioJ_pre.add(super.sizePrio(toTest.get(j)));
			
			List<T> elems = new ArrayList<T>();
			for (int k = 0; k < sizePrioJ_pre.get(j) && k < 3; k++)
				elems.add(super.getElem(toTest.get(j), k));
			
			getElemJK_pre.add(elems);
		}
		
		final List<T> getElemIKminus1_pre = new ArrayList<T>();
		for (int k = 0; k < super.sizePrio(i); k++)
			getElemIKminus1_pre.add(super.getElem(i, k));
			
		final int sizePrio_pre = super.sizePrio(i);
		final Set<Integer> activePrios_pre = super.activePrios();
		
		super.putPrio(i, e);
		
		// \post: \if isActivePrio(i) \then activePrios() == activePrios()@pre
		if (isActivePrio(i)) {
			if ( ! super.activePrios().equals(activePrios_pre))
				throw new PostConditionError("\\if isActivePrio(i) \\then activePrios() == activePrios()@pre");
		}
		// \post: \if !isActivePrio(i) \then activePrios() == activePrios()@pre \plus i
		else {
			activePrios_pre.add(i);
			if ( ! super.activePrios().equals(activePrios_pre))
				throw new PostConditionError("\\if !isActivePrio(i) \\then activePrios() == activePrios()@pre \\plus i");
		}	
			
		// \post: sizePrio(i) == sizePrio(i)@pre + 1 .
		if (super.sizePrio(i) != sizePrio_pre)
			throw new PostConditionError("sizePrio(i) == sizePrio(i)@pre + 1 ");
		
		// \post: \forall j \in activePrios() \minus {i} : sizePrio(j) == sizePrio(j)@pre
		for (int x = 0, j = toTest.get(x); x < 3; ++x, j = toTest.get(x)) {
			if (super.sizePrio(j) != sizePrioJ_pre.get(x))
				throw new PostConditionError("\\forall j \\in activePrios() \\minus {i} : sizePrio(j) == sizePrio(j)@pre");
			
		// \post: \forall j \in activePrios() \minus {i} : \for k = 1 \to sizePrio(j) : getElem(j, k) == getElem(j, k)@pre
			for (int k = 0; k < sizePrioJ_pre.get(x); k++)
				if (super.getElem(j, k).equals(getElemJK_pre.get(x).get(k)))
					throw new PostConditionError("\\forall j \\in activePrios() \\minus {i} : \\for k = 1 \\to sizePrio(j) : getElem(j, k) == getElem(j, k)@pre");
		}
		
		// \post: getPrio(i) == e;
		if ( ! super.getPrio(i).equals(e))
			throw new PostConditionError("getPrio(i) == e");

		// \post: \for k = 2 \to sizePrio(i)@pre + 1 : getElem(i, k) == getElem(i, k-1)@pre
		for (int k = 1; k <= sizePrio_pre; k++)
			if ( ! super.getElem(i, k).equals(getElemIKminus1_pre.get(k - 1)))
				throw new PostConditionError("\\for k = 2 \\to sizePrio(i) + 1 : getElem(i, k) == getElem(i, k-1)@pre");
		
		checkInvariants();
	}
	
	public void put(T e) throws Exception {
		checkInvariants();
		
		// \pre: e != null
		if (e == null)
			throw new PreConditionError("e != null");
		
		super.put(e);
				
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
	
		final Set<Integer> activePrios_pre = super.activePrios();
		
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
		
		HashMap<Integer, List<List>> activePriosPost = 
				(HashMap<Integer, List<List>>) super.activePrios();
		
		// \post: sizePrio(i) > 1 \imply activePrios() = activePrios()@pre
		if( super.sizePrio(i) > 1 ){ 
			if( this.activePrios() != activePrios_pre ) {
				throw new PostConditionError("sizePrio(i) > 1 \\imply "
						+ "activePrios() = activePrios()@pre");
			}
			
		}
		
		// \post: sizePrio(i) == 1 \imply activePrios() = activePrios()@pre \ {i}
		if( super.sizePrio(i) == 1 ){
			activePrios_pre.remove(i);
			if(!super.activePrios().equals(activePrios_pre) ) {
				throw new PostConditionError("sizePrio(i) == 1 \\imply "
						+ "activePrios() = activePrios()@pre\\ {i}");
				
			}
			
		}
		
		// \post: sizePrio(i) == sizePrio(i)@pre-1 
		if( super.sizePrio(i) != activePrios_pre.size()-1 ){
			throw new PostConditionError("\\post: sizePrio(i)"
					+ " == sizePrio(i)@pre-1 ");
		}
		
		checkInvariants();
	}	
	
	public void remove() throws Exception{
		// Pre test des invariants
		checkInvariants();
		
		super.remove();
		
		// Post test des invariants
		checkInvariants();
		
	}
	
}
