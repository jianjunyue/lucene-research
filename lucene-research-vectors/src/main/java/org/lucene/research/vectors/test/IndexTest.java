package org.lucene.research.vectors.test;

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
import org.lucene.research.vectors.field.Vectors;
import org.lucene.research.vectors.field.VectorsStoredCreator; 

public class IndexTest {

	public static void main(String[] args) {
		try {
			IndexFiles();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Hello World!");
	}

	private static String indexPath = "D:\\data\\index\\vectest";
	private static IndexWriter writer;
	private static Path file;

	private static void IndexFiles() throws IOException {
		file = Paths.get(indexPath);
		Directory dir = FSDirectory.open(file);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer); 
		writer = new IndexWriter(dir, iwc);
		 
		float[] valuevectors={12.1f,12.1f,174.23f,4.23f,174.23f};

		indexDoc("1", "上海",valuevectors); 
 
		
//		updateDocument();
		writer.commit();
		writer.forceMerge(5);
		writer.close();
	}

	private static void indexDoc(String id, String name,  float[] valuevectors) throws IOException {
		Document doc = new Document(); 
		Field id_field = new StringField("id", id, Store.YES);
		Field name_field = new StringField("name", name, Store.YES);// StringField
		Field vec_field = VectorsStoredCreator.createVectorsFiled("vec",new Vectors(valuevectors) ) ;
		
		doc.add(id_field);
		doc.add(name_field); 
		doc.add(vec_field); 

		writer.addDocument(doc);
	}
 
	private static void updateDocument() throws IOException {
		 
		writer.updateNumericDocValue(new Term("id","2"), "sortname", 115); 
	}
	
}
