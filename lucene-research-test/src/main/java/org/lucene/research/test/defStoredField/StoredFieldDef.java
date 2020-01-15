package org.lucene.research.test.defStoredField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;

public class StoredFieldDef {

	public static void main(String[] args) {
		List<Vertex> vertices = new ArrayList<Vertex>();
		Vertex ver1 = new Vertex(121.11f, 32.11f);
		vertices.add(ver1);

		Vertex ver2 = new Vertex(131.11f, 52.11f);
		vertices.add(ver2);
		VerticesStoredField field = createVerticesFiled("testname", vertices);
		System.out.println(field);
		Document doc=new Document();
		doc.getBinaryValue(""); 
		 
	}
	
	private static 	List<Vertex> getVertexs(List<Vertex> vertices) {
		return vertices;
	}

	public static VerticesStoredField createVerticesFiled(String filedName, List<Vertex> vertices) {
		BytesRefBuilder builder = new BytesRefBuilder();
		int index = 0;
		for (Vertex vertex : vertices) {
			int longitudeEncoded = VerticesStoredField.encodeLongitudeToInt(vertex.getPolyX());
			int latitudeEncoded = VerticesStoredField.encodeLatitudeToInt(vertex.getPolyY());
			VerticesStoredField.intToBytes(longitudeEncoded, builder);
//			index += 4;
			VerticesStoredField.intToBytes(latitudeEncoded, builder);
//			index += 4;
		}
		return new VerticesStoredField(filedName, builder.bytes());
	}
	
	  
	  public static List<Vertex> getVerticesFromVerticesStoredField(BytesRef ref) {
	    List<Vertex> vertices = new ArrayList<>();
	    for (int i = 0; i < ref.length; ) {
	      byte[] lonBytes = Arrays.copyOfRange(ref.bytes, i, i + 4);
	      int lon = VerticesStoredField.bytesToInt(lonBytes, 0);
	      i += 4;
	      byte[] latBytes = Arrays.copyOfRange(ref.bytes, i, i + 4);
	      int lat = VerticesStoredField.bytesToInt(latBytes, 0);
	      i += 4;
	      if (Integer.MIN_VALUE != lon && Integer.MIN_VALUE != lat)
	        vertices.add(new Vertex((float)VerticesStoredField.decodeLongitudeToDouble(lon), (float)VerticesStoredField.decodeLatitudeToDouble(lat))); 
	    } 
	    return vertices;
	  }
}
