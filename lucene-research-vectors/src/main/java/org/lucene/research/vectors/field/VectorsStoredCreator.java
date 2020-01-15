package org.lucene.research.vectors.field;

import java.util.Arrays;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;

public class VectorsStoredCreator {

	public static VectorsStoredField createVectorsFiled(String filedName, Vectors vectors) {
		BytesRefBuilder builder = new BytesRefBuilder();
		for (float vector : vectors.getValue()) {
			VectorsStoredField.floatToBytes(vector, builder);
		}
		return new VectorsStoredField(filedName, builder.bytes());
	}

	public static float[] geVectorsFromVectorsStoredField(BytesRef ref) {
		int lenVec = ref.length / 4;
		float[] floatVectors = new float[lenVec];
		for (int i = 0; i < lenVec; i++) {
			byte[] lonBytes = Arrays.copyOfRange(ref.bytes, i * 4,(i+1)*4);
			int bits = VectorsStoredField.bytesToInt(lonBytes, 0);
			floatVectors[i] = Float.intBitsToFloat(bits);
		}
		return floatVectors;
	}
}
