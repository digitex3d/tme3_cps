package filesprio;

public class FilesPrioContrat<T> extends FilesPrioDecorator<T> {

	public FilesPrioContrat(FilesPrio<T> fp) {
		super(fp);
	}
	
	private void checkInvariants() throws Exception {
		 //  \inv: empty() = size() == 0 
		if (super.empty() != (super.size() == 0))
			throw new InvariantError("empty() = size() == 0");
	}
	
	public void init() throws Exception {
		super.init();
		checkInvariants();
		
		// \post: size() == 0
		if (! (size() == 0))
			throw new PostConditionError("size() == 0");
	}
	
	public int size() throws Exception {
		int size;
		
		checkInvariants();
		
		size = super.size();
		checkInvariants();
				
		return size;
	}
	
	public boolean empty() throws Exception {
		checkInvariants();
		
		boolean ret = super.empty();
		
		checkInvariants();
	
		return ret;
	}
	
	public int sizePrio(int i) throws Exception {
		checkInvariants();
		int ret = super.sizePrio(i);
		checkInvariants();
		return ret;
	}

	public void putPrio(int i, T e) throws Exception {
		checkInvariants();
		
		if (!(i >= 0 && e != null))
			throw new PreConditionError("i >= 0 && e != null");
		
		final int sizePrio_pre = super.sizePrio(i);
		
		super.putPrio(i, e);
		
		if( super.sizePrio(i) != sizePrio_pre )
			throw new PostConditionError("sizePrio(i) == sizePrio(i)@pre + 1 ");
		
		checkInvariants();
	}
}
