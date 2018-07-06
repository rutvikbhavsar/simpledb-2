package simpledb;

import java.util.ArrayList;

/**
 * A {@code StringAggregator} computes some aggregate value over a set of {@code StringField}s.
 */
public class StringAggregator implements Aggregator {

	/**
	 * Constructs a {@code StringAggregator}.
	 * 
	 * @param gbfield
	 *            the 0-based index of the group-by field in the tuple, or {@code NO_GROUPING} if there is no grouping
	 * @param gbfieldtype
	 *            the type of the group by field (e.g., {@code Type.INT_TYPE}), or {@code null} if there is no grouping
	 * @param afield
	 *            the 0-based index of the aggregate field in the tuple
	 * @param what
	 *            aggregation operator to use -- only supports {@code COUNT}
	 * @throws IllegalArgumentException
	 *             if {@code what != COUNT}
	 */
	
	 	private int gbfield;
	    private Type gbfieldtype;
	    private int afield;
	    private Op what; 
	    private ArrayList<Tuple> TupleList; 
	    private TupleDesc td;

	public StringAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
		// some code goes here
		this.gbfield = gbfield;
        this.gbfieldtype = gbfieldtype;
        this.afield = afield;
        this.what = what;
        TupleList = new ArrayList<Tuple>();

        //set tupledesc
        if(gbfield==Aggregator.NO_GROUPING)
        {
            Type[] tAr = {Type.INT_TYPE};
            String[] fAr = {""};
            td = new TupleDesc(tAr,fAr);
        }
        else
        {
            Type[] tAr = {
                gbfieldtype,
                Type.INT_TYPE};
            String[] fAr = {
                "",""};
            td = new TupleDesc(tAr,fAr);
        }
	}

	/**
	 * Merges a new tuple into the aggregate, grouping as indicated in the constructor.
	 * 
	 * @param tup
	 *            the Tuple containing an aggregate field and a group-by field
	 */
	public void merge(Tuple tup) {
		// some code goes here
		if(gbfield==Aggregator.NO_GROUPING)
        {
			int temp = 0;
         //   String t1 = ((StringField)tup.getField(afield)).getValue();
            if(TupleList.size()!=0)
            {
                Tuple t = TupleList.get(0);
                int t2 = ((IntField)t.getField(0)).getValue();
                if(what==Op.COUNT)
                {
                    t.setField(0,new IntField(t2+1));
                }
            }
            else
            {
                if(what==Op.COUNT)
                {
                   // t1 = 1;
                	temp = 1;
                }

                Tuple t = new Tuple(td);
              //  t.setField(0,new IntField(t1));
                t.setField(0,new IntField(temp));
                TupleList.add(t);
                
            }
        }
        else
        {
            for(int i=0;i<TupleList.size();++i)
            {
                Tuple t = TupleList.get(i);
                if(t.getField(0).equals(tup.getField(gbfield)))
                {
                    int t3 = ((IntField)t.getField(1)).getValue();
                    if(what==Op.COUNT)
                    {
                        t.setField(1,new IntField(t3+1));
                    }
                    TupleList.set(i,t);
                    return;
                }
            }

            Tuple t = new Tuple(td);
            Field f0 = tup.getField(gbfield);
            Field f1 = tup.getField(afield);

            if(what==Op.COUNT)
            {
                f1 = new IntField(1);
            }
            t.setField(0,f0);
            t.setField(1,f1);
            TupleList.add(t);

        }
	}

	/**
	 * Creates a {@code DbIterator} over group aggregate results.
	 *
	 * @return a {@code DbIterator} whose tuples are the pair ({@code groupVal}, {@code aggregateVal}) if using group,
	 *         or a single ({@code aggregateVal}) if no grouping. The aggregateVal is determined by the type of
	 *         aggregate specified in the constructor.
	 */
	 
    private TupleDesc createGroupByTupleDesc()
    {
    	String[] names;
    	Type[] types;
    	if (gbfield == Aggregator.NO_GROUPING)
    	{
    		names = new String[] {"aggregateValue"};
    		types = new Type[] {Type.INT_TYPE};
    	}
    	else
    	{
    		names = new String[] {"groupValue", "aggregateValue"};
    		types = new Type[] {gbfieldtype, Type.INT_TYPE};
    	}
    	return new TupleDesc(types, names);
    }
    
	public DbIterator iterator() {
		// some code goes here
		 Iterable<Tuple> it = TupleList;
	      return new TupleIterator(td,it);
	        
	       
	    }
	}

