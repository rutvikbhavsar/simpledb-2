package simpledb;

import java.util.ArrayList;

/**
 * An {@code IntAggregator} computes some aggregate value over a set of {@code IntField}s.
 */
public class IntAggregator implements Aggregator {

	/**
	 * Constructs an {@code Aggregate}.
	 * 
	 * @param gbfield
	 *            the 0-based index of the group-by field in the tuple, or {@code NO_GROUPING} if there is no grouping
	 * @param gbfieldtype
	 *            the type of the group by field (e.g., {@code Type.INT_TYPE}), or {@code null} if there is no grouping
	 * @param afield
	 *            the 0-based index of the aggregate field in the tuple
	 * @param what
	 *            the aggregation operator
	 */
	
	 	private int gbfield;
	    private Type gbfieldtype;
	    private int afield;
	    private Op what; 
	    private ArrayList<Tuple> TupleList; 
	    private ArrayList<Integer> CountList; 
	    private ArrayList<Integer> SumList; 
	    private TupleDesc td;
	

	public IntAggregator(int gbfield, Type gbfieldtype, int afield, Op what) {
 		// some code goes here
		this.gbfield = gbfield;
        this.gbfieldtype = gbfieldtype;
        this.afield = afield;
        this.what = what;
        TupleList = new ArrayList<Tuple>();
        CountList = new ArrayList<Integer>();
        SumList = new ArrayList<Integer>();
        
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
	 * Merges a new {@code Tuple} into the aggregate, grouping as indicated in the constructor.
	 * 
	 * @param tup
	 *            the {@code Tuple} containing an aggregate field and a group-by field
	 */
/**	public void merge(Tuple tup) {
		if(gbfield==Aggregator.NO_GROUPING)
        {
            int t1 = ((IntField)tup.getField(afield)).getValue();
            if(TupleList.size()!=0)
            {
                Tuple t = TupleList.get(0);
                int t2 = ((IntField)t.getField(0)).getValue();
                if(what==Op.MIN)
                {
                    t.setField(0,new IntField(Math.min(t2,t1)));
                }
                else if(what==Op.MAX)
                {
                    t.setField(0,new IntField(Math.max(t2,t1)));
                }
                else if(what==Op.SUM)
                {
                    t.setField(0,new IntField(t2+t1));
                }
                else if(what==Op.AVG)
                {
                    CountList.set(0,CountList.get(0)+1);
                    SumList.set(0,SumList.get(0)+t1);
                    int n = SumList.get(0)/CountList.get(0);
                    t.setField(0,new IntField(n));
                }
                else if(what==Op.COUNT)
                {
                    t.setField(0,new IntField(t2+1));
                }
            }
            else
            {
                if(what==Op.AVG)
                {
                    CountList.add(1);
                    SumList.add(t1);
                }
                else if(what==Op.COUNT)
                {
                    t1 = 1;
                }
                Tuple t = new Tuple(td);
                t.setField(0,new IntField(t1));
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
                    int t3 = ((IntField)tup.getField(afield)).getValue();
                    int t4 = ((IntField)t.getField(1)).getValue();
                    if(what==Op.MIN)
                    {
                        t.setField(1,new IntField(Math.min(t4,t3)));
                    }
                    else if(what==Op.MAX)
                    {
                        t.setField(1,new IntField(Math.max(t4,t3)));
                    }
                    else if(what==Op.SUM)
                    {
                        t.setField(1,new IntField(t4+t3));
                    }
                    else if(what==Op.AVG)
                    {
                        CountList.set(i,CountList.get(i)+1);
                        SumList.set(i,SumList.get(i)+t3);
                        t.setField(1,new IntField(SumList.get(i)/CountList.get(i)));
                    }
                    else if(what==Op.COUNT)
                    {
                        t.setField(1,new IntField(t4+1));
                    }
                    return;
                }
            }
            Tuple t = new Tuple(td);
            Field f0 = tup.getField(gbfield);
            Field f1 = tup.getField(afield);
            
            if(what==Op.AVG)
            {
                CountList.add(1);
                SumList.add(((IntField)tup.getField(afield)).getValue());
            }
            else if(what==Op.COUNT)
            {
                f1 = new IntField(1);
            }
            t.setField(0,f0);
            t.setField(1,f1);
            TupleList.add(t);
        }
		// some code goes here
	}
**/
	public void merge(Tuple tup) {
	  if(gbfield==Aggregator.NO_GROUPING)
	        {
	            int tupValue = ((IntField)tup.getField(afield)).getValue();
	            if(TupleList.size()!=0)
	            {
	                Tuple t = TupleList.get(0);
	                int tValue = ((IntField)t.getField(0)).getValue();
	                if(what==Op.MIN)
	                {
	                    t.setField(0,new IntField(Math.min(tValue,tupValue)));
	                }
	                else if(what==Op.MAX)
	                {
	                    t.setField(0,new IntField(Math.max(tValue,tupValue)));
	                }
	                else if(what==Op.SUM)
	                {
	                    t.setField(0,new IntField(tValue+tupValue));
	                }
	                else if(what==Op.AVG)
	                {
	                    CountList.set(0,CountList.get(0)+1);
	                    SumList.set(0,SumList.get(0)+tupValue);
	                    int n = SumList.get(0)/CountList.get(0);
	                    t.setField(0,new IntField(n));
	                }
	                else if(what==Op.COUNT)
	                {
	                    t.setField(0,new IntField(tValue+1));
	                }
                TupleList.set(0,t);
	            }
	            else
	            {
	                if(what==Op.AVG)
	                {
	                    CountList.add(1);
	                    SumList.add(tupValue);
	                }
	                else if(what==Op.COUNT)
	                {
	                    tupValue = 1;
	                }

	                Tuple t = new Tuple(td);
	                t.setField(0,new IntField(tupValue));
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
	                    int tupValue = ((IntField)tup.getField(afield)).getValue();
	                    int tValue = ((IntField)t.getField(1)).getValue();
	                    if(what==Op.MIN)
	                    {
	                        t.setField(1,new IntField(Math.min(tValue,tupValue)));
	                    }
	                    else if(what==Op.MAX)
	                    {
	                        t.setField(1,new IntField(Math.max(tValue,tupValue)));
	                    }
	                    else if(what==Op.SUM)
	                    {
	                     if(Aggregate.SUM < 2)
	                     {
	                      t.setField(1,new IntField(tValue+tupValue));
	                     }
	                    }
	                    else if(what==Op.AVG)
	                    {
	                        CountList.set(i,CountList.get(i)+1);
	                        SumList.set(i,SumList.get(i)+tupValue);
	                        t.setField(1,new IntField(SumList.get(i)/CountList.get(i)));
	                    }
	                    else if(what==Op.COUNT)
	                    {
	                     if(Aggregate.COUNT < 2)
	                     {
	                      t.setField(1,new IntField(tValue+1));
	                     }
	                    }
                    TupleList.set(i,t);
	                    return;
	                }
	            }

	            Tuple t = new Tuple(td);
	            Field f0 = tup.getField(gbfield);
	            Field f1 = tup.getField(afield);
	            

	            if(what==Op.AVG)
	            {
	                CountList.add(1);
	                SumList.add(((IntField)tup.getField(afield)).getValue());
	            }
	            else if(what==Op.COUNT)
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
	 *         or a single ({@code aggregateVal}) if no grouping. The {@code aggregateVal} is determined by the type of
	 *         aggregate specified in the constructor.
	 */
	public DbIterator iterator() {
		// some code goes here
		// throw new UnsupportedOperationException("implement me");
		Iterable<Tuple> it = TupleList;
        return new TupleIterator(td,it);
	}

}