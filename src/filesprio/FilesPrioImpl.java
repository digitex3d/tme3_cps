package filesprio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FilesPrioImpl<T> implements FilesPrio<T> {
	//private Set<Integer> activePrios;
	private Map<Integer, List<T>> activePrios;
	
	@Override
	public int size() {
		int size = 0;
		for (Entry<Integer, List<T>> entry : activePrios.entrySet())
			size += entry.getValue().size();
		
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
}
