package filesprio;

import java.util.Set;

public interface FilesPrio<T> {
	/* Observators */
	
	public int size() throws Exception;
	public boolean empty() throws Exception;
	public int sizePrio(int i) throws Exception;
	public Set<Integer> activePrios() throws Exception;
	public boolean isActivePrio(int i) throws Exception;
	public int maxPrio() throws Exception;
	// \pre: sizePrio(i) > 0
	public T getPrio(int i) throws Exception; 
	// \pre: size() > 0
	public T get() throws Exception;
	// \pre: i \in activePrios() && 0 < k <= sizePrio(i)
	public T getElem(int i, int k) throws Exception;
	
	// \post: size() == 0
	public void init() throws Exception;
	
	/* Invariants */
	//	minimisation
	// \inv: size() = \sum_{\forall i \in activePrios()} : sizePrio(i)  
	// \inv: empty() = size() == 0 
	// \inv: isActivePrio(i) = i \in activePrios()
	
	// \inv: \forall i \in activePrios() : sizePrio(i) > 0
	// \inv: \forall i !\in activePrios() : sizePrio(i) == 0
	// \inv: \forall i \in activePrios() : \for k = 1 \to sizePrio(i) : getElem(i, k) != null
	
	
	/* Operators */
	
	// \pre: i >= 0 && e != null.
	// \post: \if isActivePrio(i) \then activePrios() == activePrios()@pre
	// \post: \if !isActivePrio(i) \then activePrios() == activePrios()@pre \plus i
	// \post: sizePrio(i) == sizePrio(i)@pre + 1 .
	// \post: \forall j \in activePrios() \minus {i} : sizePrio(j) == sizePrio(j)@pre
	// \post: getPrio(i) == e
	// \post: \for k = 2 \to sizePrio(i)@pre + 1 : getElem(i, k) == getElem(i, k-1)@pre
	// \post: \forall j \in activePrios() \minus {i} : \for k = 1 \to sizePrio(j) : getElem(j, k) == getElem(j, k)@pre
	public void putPrio(int i, T e) throws Exception;

	// \pre: e != null
	public void put(T e) throws Exception;
	
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
	public void removePrio(int i) throws Exception;
}
