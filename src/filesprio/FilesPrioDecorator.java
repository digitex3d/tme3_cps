package filesprio;

import java.util.Set;

public class FilesPrioDecorator<T> implements FilesPrio<T> {
	
	private final FilesPrio<T> delegate;
	
	@Override
	public int size() throws Exception {
		return delegate.size();
	}
	
	@Override
	public boolean empty() throws Exception {
		return delegate.empty();
	}

	@Override
	public void init() throws Exception {
		delegate.init();
	}
	
	public FilesPrioDecorator (FilesPrio<T> fp) {
		this.delegate = fp;
	}

	@Override
	public int sizePrio(int i) throws Exception {
		return this.delegate.sizePrio(i);
	}

	@Override
	public void putPrio(int i, T e) throws Exception {
		this.delegate.putPrio(i, e);
	}

	@Override
	public Set<Integer> activePrios() throws Exception {
		return this.delegate.activePrios();
	}

	@Override
	public boolean isActivePrio(int i) throws Exception {
		return this.delegate.isActivePrio(i);
	}

	@Override
	public int maxPrio() throws Exception {
		return this.delegate.maxPrio();
	}

	@Override
	public T getPrio(int i) throws Exception {
		return this.delegate.getPrio(i);
	}

	@Override
	public T get() throws Exception {
		return this.delegate.get();
	}

	@Override
	public T getElem(int i, int k) throws Exception {
		return this.delegate.getElem(i, k);
	}

}
