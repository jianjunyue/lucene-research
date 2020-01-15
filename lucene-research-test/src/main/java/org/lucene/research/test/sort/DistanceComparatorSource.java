package org.lucene.research.test.sort;

import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;

public class DistanceComparatorSource  extends FieldComparatorSource{
	private  int x;  
    private int y;  

    public DistanceComparatorSource(int x,int y){  
        this.x = x;  
        this.y = y;  
    }

	@Override
	public FieldComparator<?> newComparator(String fieldname, int numHits, int sortPos, boolean reversed) {
		  return new DistanceSourceLookupComparator(fieldname, numHits,x,y);
	}

}
