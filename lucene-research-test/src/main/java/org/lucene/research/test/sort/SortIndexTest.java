package org.lucene.research.test.sort;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.BinaryDocValuesField;
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

public class SortIndexTest {

	public static void main(String[] args) {
		try {
			IndexFiles();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Hello World!");
	}

	private static String indexPath = "D:\\data\\index\\test";
	private static IndexWriter writer;
	private static Path file;

	private static void IndexFiles() throws IOException {
		file = Paths.get(indexPath);
		Directory dir = FSDirectory.open(file);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer); 
		writer = new IndexWriter(dir, iwc);

		indexDoc("1", "上海", "1,1" );
		indexDoc("2", "上海", "1,2" );
		indexDoc("3", "上海", "3,13" );
		indexDoc("4", "上海", "1,14" );
		indexDoc("5", "北京", "2,25" );
		indexDoc("6", "北京", "2,26" );
		indexDoc("7", "北京", "2,27" );
		indexDoc("8", "北京", "2,8" ); 
		writer.commit();
		writer.forceMerge(5);
		writer.close();
	}

	private static void indexDoc(String id, String name,  String xy ) throws IOException {
		Document doc = new Document(); 	
		Field id_field = new StringField("id", id, Store.YES);
		Field name_field = new StringField("name", name, Store.YES);// StringField
		Field sort_field = new SortedDocValuesField("location", new BytesRef(xy.getBytes())); 

		Field sortvalue_field = new StringField("sortvalue", xy, Store.YES); 
		doc.add(id_field);
		doc.add(name_field); 
		doc.add(sort_field); 

		doc.add(sortvalue_field); 

		writer.addDocument(doc);
	}
 
	private static void updateDocument() throws IOException {
		 
		writer.updateNumericDocValue(new Term("id","2"), "sortname", 115); 
	}
	
}
