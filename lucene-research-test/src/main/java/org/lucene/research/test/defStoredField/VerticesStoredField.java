package org.lucene.research.test.defStoredField;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.util.BytesRefBuilder;

public class VerticesStoredField extends Field {
	public static final FieldType TYPE = new FieldType();

	public VerticesStoredField(String name, byte[] value) {
		super(name, value, TYPE);
		// TODO Auto-generated constructor stub
	}

	static {
		TYPE.setStored(true);
		TYPE.freeze();
	}

	public static void intToBytes(int value, BytesRefBuilder result) {
		value ^= Integer.MIN_VALUE;
		result.append((byte) (value >> 24));
		result.append((byte) (value >> 16));
		result.append((byte) (value >> 8));
		result.append((byte) value);
	}

	public static int bytesToInt(byte[] encoded, int offset) {
		int x = (encoded[offset] & 0xFF) << 24 | (encoded[offset + 1] & 0xFF) << 16 | (encoded[offset + 2] & 0xFF) << 8
				| encoded[offset + 3] & 0xFF;
		return x ^ Integer.MIN_VALUE;
	}

	public static int encodeLongitudeToInt(double longitude) {
		checkLongitude(longitude);
		if (longitude == 180.0D)
			longitude = Math.nextDown(longitude);
		return (int) Math.floor(longitude / 8.381903171539307E-8D);
	}

	public static int encodeLatitudeToInt(double latitude) {
		checkLatitude(latitude);
		if (latitude == 90.0D)
			latitude = Math.nextDown(latitude);
		return (int) Math.floor(latitude / 4.190951585769653E-8D);
	}

	public static double decodeLatitudeToDouble(int encoded) {
		double result = encoded * 4.190951585769653E-8D;
		assert result >= -90.0D && result < 90.0D;
		return result;
	}

	public static double decodeLongitudeToDouble(int encoded) {
		double result = encoded * 8.381903171539307E-8D;
		assert result >= -180.0D && result < 180.0D;
		return result;
	}

	public static void checkLatitude(double latitude) {
		if (Double.isNaN(latitude) || latitude < -90.0D || latitude > 90.0D)
			throw new IllegalArgumentException(
					"invalid latitude " + latitude + "; must be between " + -90.0D + " and " + 90.0D);
	}

	public static void checkLongitude(double longitude) {
		if (Double.isNaN(longitude) || longitude < -180.0D || longitude > 180.0D)
			throw new IllegalArgumentException(
					"invalid longitude " + longitude + "; must be between " + -180.0D + " and " + 180.0D);
	}
}
