package org.lucene.research.test.sort;

import java.io.IOException;

import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.SimpleFieldComparator;
import org.apache.lucene.util.BytesRef;

public class DistanceSourceLookupComparator extends SimpleFieldComparator<String> {
	private float[] values;
	private float top;
	private float bottom;
	private String fieldName;

	private int x;
	private int y;

	private BinaryDocValues binaryDocValues;

	public DistanceSourceLookupComparator(String fieldName, int numHits, int x, int y) {
		values = new float[numHits];
		this.fieldName = fieldName;
		this.x = x;
		this.y = y;
	}

	@Override
	public int compare(int slot1, int slot2) {
		if (values[slot1] > values[slot2]) {
			return 1;
		}
		if (values[slot1] < values[slot2]) {
			return -1;
		}
		return 0;
	}

	private float getDistance(int doc) throws IOException {
		binaryDocValues.advance(doc) ;
		BytesRef bytesRef = binaryDocValues.binaryValue();

		String xy = bytesRef.utf8ToString();
		String[] array = xy.split(",");
		int deltax = Integer.parseInt(array[0]) - x;
		int deltay = Integer.parseInt(array[1]) - y;
		float distance = (float) Math.sqrt(deltax * deltax + deltay * deltay);
//System.out.println(distance);
		return distance;
	}

	@Override
	protected void doSetNextReader(LeafReaderContext context) throws IOException {
		binaryDocValues = context.reader().getBinaryDocValues(fieldName);
	}

	public void setBottom(int slot) {
		bottom = values[slot];
	}// 设置底部slot

	public int compareBottom(int doc) throws IOException {// 与队列中命中结果评分最低的比较
		float distance = getDistance(doc);
		if (bottom < distance) {
			return -1;
		}
		if (bottom > distance) {
			return 1;
		}
		return 0;
	}

	public int compareTop(int doc) throws IOException {
		float distance = getDistance(doc);
		if (top < distance) {
			return -1;
		}
		if (top > distance) {
			return 1;
		}
		return 0;
	}

	public void copy(int slot, int doc) throws IOException {// 新的命中结果拷贝至队列中
		values[slot] = getDistance(doc);
	}

	@Override
	public void setTopValue(String value) {
		top = Float.valueOf(value);
	}

	@Override
	public String value(int slot) {//
		return values[slot] + "";
	}
}