package simpledb;

import java.util.*;

import simpledb.Aggregator.Op;

/**
 * An {@code Aggregate} operator computes an aggregate value (e.g., sum, avg,
 * max, min) over a single column, grouped by a single column.
 */
public class Aggregate extends AbstractDbIterator {

	public static int COUNT = 0;
	public static int SUM = 0;

	private static String sumClassName = null;
	private static String countClassName = null;

	/**
	 * The child operator.
	 */
	DbIterator child;

	/**
	 * The {@code TupleDesc} associated with this {@code Aggregate}.
	 */
	TupleDesc td;

	/**
	 * The {@code Aggregator} for this {@code Aggregate} operator.
	 */
	Aggregator aggregator;

	/**
	 * The current DbIterator over aggregate results.
	 */
	DbIterator it = null;

	private int afield;

	private int gfield;

	private Op aop;

	/**
	 * Constructs an {@code Aggregate}.
	 *
	 * Implementation hint: depending on the type of afield, you will want to
	 * construct an {@code IntAggregator} or {@code StringAggregator} to help you
	 * with your implementation of {@code readNext()}.
	 * 
	 *
	 * @param child
	 *            the {@code DbIterator} that provides {@code Tuple}s.
	 * @param afield
	 *            the column over which we are computing an aggregate.
	 * @param gfield
	 *            the column over which we are grouping the result, or -1 if there
	 *            is no grouping
	 * @param aop
	 *            the {@code Aggregator} operator to use
	 */
	public Aggregate(DbIterator child, int afield, int gfield, Aggregator.Op aop) {
		// some code goes here
		this.child = child;
		this.afield = afield;
		this.gfield = gfield;
		this.aop = aop;

		Type gbFieldType;
		Type aFieldType = child.getTupleDesc().getType(afield);

		if (gfield == Aggregator.NO_GROUPING) {
			gbFieldType = null;
		} else {
			gbFieldType = child.getTupleDesc().getType(gfield);
		}

		if (aFieldType == Type.INT_TYPE) {
			aggregator = new IntAggregator(gfield, gbFieldType, afield, aop);
		} else if (aFieldType == Type.STRING_TYPE) {
			aggregator = new StringAggregator(gfield, gbFieldType, afield, aop);
		}
	}

	public static String aggName(Aggregator.Op aop) {
		switch (aop) {
		case MIN:
			return "min";
		case MAX:
			return "max";
		case AVG:
			return "avg";
		case SUM:
			return "sum";
		case COUNT:
			return "count";
		}
		return "";
	}

	/**
	 * Returns the {@code TupleDesc} of this {@code Aggregate}. If there is no group
	 * by field, this will have one field - the aggregate column. If there is a
	 * group by field, the first field will be the group by field, and the second
	 * will be the aggregate value column.
	 * 
	 * The name of an aggregate column should be informative. For example:
	 * {@code aggName(aop) (child_td.getFieldName(afield))} where {@code aop} and
	 * {@code afield} are given in the constructor, and {@code child_td} is the
	 * {@code TupleDesc} of the child iterator.
	 */
	public TupleDesc getTupleDesc() {
		// some code goes here
		// return null;
		if (gfield == Aggregator.NO_GROUPING) {
			Type[] tAr = { child.getTupleDesc().getType(afield) };
			String[] fAr = { child.getTupleDesc().getFieldName(afield) };
			TupleDesc td = new TupleDesc(tAr, fAr);
			return td;
		} else {
			Type[] tAr = { child.getTupleDesc().getType(gfield), child.getTupleDesc().getType(afield) };
			String[] fAr = { child.getTupleDesc().getFieldName(gfield), child.getTupleDesc().getFieldName(afield) };
			TupleDesc td = new TupleDesc(tAr, fAr);
			return td;

		}
	}

	public void open() throws NoSuchElementException, DbException, TransactionAbortedException {

		// also add below variable in same class..

		if (aop.name().equalsIgnoreCase("COUNT")) {
			if (countClassName != null
					&& !new Exception().getStackTrace()[1].getClassName().toString().equalsIgnoreCase(countClassName)) {
				COUNT++;
			} else if (countClassName == null) {
				countClassName = new Exception().getStackTrace()[1].getClassName();
				COUNT++;
			}
		}
		if (aop.name().equalsIgnoreCase("SUM")) {
			if (sumClassName != null
					&& !new Exception().getStackTrace()[1].getClassName().toString().equalsIgnoreCase(sumClassName)) {
				SUM++;
			} else if (sumClassName == null) {
				sumClassName = new Exception().getStackTrace()[1].getClassName();
				SUM++;
			}
		}

		child.open();

		while (child.hasNext()) {
			aggregator.merge(child.next());
		}
		child = aggregator.iterator();
		child.open();
	}

	public void close() {
		it = null;
	}

	public void rewind() throws DbException, TransactionAbortedException {
		// some code goes here
		child.rewind();
	}

	/**
	 * Returns the next {@code Tuple}. If there is a group by field, then the first
	 * field is the field by which we are grouping, and the second field is the
	 * result of computing the aggregate, If there is no group by field, then the
	 * result tuple should contain one field representing the result of the
	 * aggregate. Should return {@code null} if there are no more {@code Tuple}s.
	 */
	protected Tuple readNext() throws TransactionAbortedException, DbException {
		// some code goes here
		while (child.hasNext()) {
			return child.next();
		}
		return null;
	}

}
