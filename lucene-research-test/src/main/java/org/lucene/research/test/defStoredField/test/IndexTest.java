package org.lucene.research.test.defStoredField.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.lucene.research.test.defStoredField.StoredFieldDef;
import org.lucene.research.test.defStoredField.Vertex;

public class IndexTest {

	public static void main(String[] args) {
		try {
			IndexFiles();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Hello World!");
	}

	private static String indexPath = "D:\\data\\index\\deftest";
	private static IndexWriter writer;
	private static Path file;

	private static void IndexFiles() throws IOException {
		file = Paths.get(indexPath);
		Directory dir = FSDirectory.open(file);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer); 
		writer = new IndexWriter(dir, iwc);
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		Vertex ver1 = new Vertex(121.11f, 32.11f);
		vertices.add(ver1);

		Vertex ver2 = new Vertex(131.11f, 52.11f);
		vertices.add(ver2);

		Vertex ver3 = new Vertex(31.11f, 52.11f);
		vertices.add(ver3);

		indexDoc("1", "上海",vertices); 
 
		
//		updateDocument();
		writer.commit();
		writer.forceMerge(5);
		writer.close();
	}

	private static void indexDoc(String id, String name,  List<Vertex> vertices) throws IOException {
		Document doc = new Document(); 
		Field id_field = new StringField("id", id, Store.YES);
		Field name_field = new StringField("name", name, Store.YES);// StringField
		Field defvar_field = StoredFieldDef.createVerticesFiled("defvar", vertices);
		
		doc.add(id_field);
		doc.add(name_field); 
		doc.add(defvar_field); 

		writer.addDocument(doc);
	}
 
	private static void updateDocument() throws IOException {
		 
		writer.updateNumericDocValue(new Term("id","2"), "sortname", 115); 
	}
	
}
