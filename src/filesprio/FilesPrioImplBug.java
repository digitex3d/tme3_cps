package filesprio;

import java.util.Set;

public class FilesPrioImplBug<T> implements FilesPrio<T> {

	@Override
	public int size() {

		return 0;
	}

	@Override
	public void init() {

		
	}

	@Override
	public boolean empty() {

		return false;
	}

	@Override
	public int sizePrio(int i) throws Exception {

		return 0;
	}

	@Override
	public Set<Integer> activePrios() throws Exception {
	
		return null;
	}

	@Override
	public boolean isActivePrio(int i) throws Exception {

		return false;
	}

	@Override
	public int maxPrio() throws Exception {

		return 0;
	}

	@Override
	public T getPrio(int i) throws Exception {
	
		return null;
	}

	@Override
	public T get() throws Exception {

		return null;
	}

	@Override
	public T getElem(int i, int k) throws Exception {

		return null;
	}

	@Override
	public void putPrio(int i, T e) throws Exception {

		
	}

	@Override
	public void removePrio(int i) throws Exception {
	
		
	}

	@Override
	public void put(T e) throws Exception {

		
	}

	@Override
	public void remove() throws Exception {

		
	}

}
