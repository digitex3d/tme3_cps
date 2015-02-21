package filesprio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilesPrioImpl<T> implements FilesPrio<T> {

	private Map<Integer, List<T>> activePrios;
	
	@Override
	public int size() {
		int size = 0;
		for (List<T> list : activePrios.values())
			size += list.size();
		
		return size;
	}

	@Override
	public void init() {
	}

	@Override
	public boolean empty() {
		return size() == 0;
	}

	@Override
	public int sizePrio(int i) throws Exception {
		return activePrios.get(i).size();
	}

	@Override
	public void putPrio(int i, T e) throws Exception {
		if (activePrios.get(i) == null)
			activePrios.put(i, new ArrayList<T>());
		
		activePrios.get(i).add(e);
	}

	@Override
	public Set<Integer> activePrios() throws Exception {
		return activePrios.keySet();
	}

	@Override
	public boolean isActivePrio(int i) throws Exception {
		return activePrios.containsKey(i);
	}

	@Override
	public int maxPrio() throws Exception {
		int max = 0;
		for (Integer i : activePrios())
			if (i > max)
				max = i;
		
		return max;
	}

	@Override
	public T getPrio(int i) throws Exception {
		return getElem(i, 1);
	}

	@Override
	public T get() throws Exception {
		return getPrio(maxPrio());
	}

	@Override
	public T getElem(int i, int k) throws Exception {
		return activePrios.get(i).get(k);
	}
}
