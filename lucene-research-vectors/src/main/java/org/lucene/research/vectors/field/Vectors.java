package org.lucene.research.vectors.field;

public class Vectors {
	public float[] getValue() {
		return value;
	}

	public void setValue(float[] value) {
		this.value = value;
	}

	private float[] value;
	
	public Vectors(float[] value) {
		this.value= value;
	}

}
