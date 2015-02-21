package filesprio;

public interface FilesPrio<T> {
	//Observators
	
	public int size() throws Exception;
	
	public boolean empty() throws Exception;
	public int sizePrio(int i) throws Exception;
	
	
	/**
	 * \post: size() == 0
	 */
	public void init() throws Exception;
	
	//Invariants
	/**
	 *  \inv: size() = \sum_{\forall i \in activePrios()} : sizePrio(i)  
	 *  \inv: empty() = size() == 0 
	 *  \
	 */
	
	
	//Operators
	
	 //  \pre: i >= 0 && e != null.
	 // \post: \if isActivePrio(i) \then activePrios() == activePrios()@pre
	//  \post: \if !isActivePrio(i) \then activePrios() == activePrios()@pre \plus i
	//  \post: sizePrio(i) == sizePrio(i)@pre + 1 .
	//  \post: \forall j \in activePrios() \minus {i} : sizePrio(j) == sizePrio(j)@pre
	//  \post: getPrio(i) == e;
	
	//  \post: \for k = 2 \to sizePrio(i) + 1 : getElem(i, k) == getElem(i, k-1)@pre
	//  \post: \forall j \in activePrios() \minus {i} : \for k = 1 \to sizePrio(j) : getElem(putPrio(j, k) == getElem(j, k)
	// 
	public void putPrio(int i, T e) throws Exception;
}
