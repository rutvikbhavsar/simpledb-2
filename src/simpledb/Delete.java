package simpledb;

/**
 * The {@code Delete} operator. {@code Delete} reads tuples from its child operator and removes them from the table they
 * belong to.
 */
public class Delete extends AbstractDbIterator {

	/**
	 * The transaction this {@code Delete} runs in.
	 */
	TransactionId t;

	/**
	 * The child operator.
	 */
	DbIterator child;

	/**
	 * The {@code TupleDesc} associated with this {@code Delete}.
	 */
	TupleDesc td;

	/**
	 * A tuple representing the result of deletion.
	 */
	Tuple result = null;

	/**
	 * Constructs a {@code Delete} operator.
	 * 
	 * @param t
	 *            The transaction this delete runs in
	 * @param child
	 *            The child operator from which to read tuples for deletion
	 */
	public Delete(TransactionId t, DbIterator child) {
		this.t = t;
		this.child = child;
		td = new TupleDesc(new Type[] { Type.INT_TYPE });
	}

	@Override
	public TupleDesc getTupleDesc() {
		return td;
	}

	@Override
	public void open() throws DbException, TransactionAbortedException {
		// some code goes here
		throw new UnsupportedOperationException("Implement this");
	}

	@Override
	public void close() {
		// some code goes here
		throw new UnsupportedOperationException("Implement this");
	}

	@Override
	public void rewind() throws DbException, TransactionAbortedException {
		// some code goes here
		throw new UnsupportedOperationException("Implement this");
	}

	/**
	 * Deletes tuples as they are read from the child operator. Deletes are processed via the buffer pool (which can be
	 * accessed via the Database.getBufferPool() method.
	 * 
	 * @return A 1-field tuple containing the number of deleted records.
	 * @see Database#getBufferPool
	 * @see BufferPool#deleteTuple
	 */
	@Override
	protected Tuple readNext() throws TransactionAbortedException, DbException {
		// some code goes here
		throw new UnsupportedOperationException("Implement this");
	}
}
