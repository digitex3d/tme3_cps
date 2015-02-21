package filesprio;

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

}
