package org.lucene.research.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

public class IndexTest {

	public static void main(String[] args) {
		try {
			IndexFiles();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Hello World!");
	}

	private static String indexPath = "/data/research/index/test";
	private static IndexWriter writer;
	private static Path file;

	private static void IndexFiles() throws IOException {
		file = Paths.get(indexPath);
		Directory dir = FSDirectory.open(file);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer); 
		writer = new IndexWriter(dir, iwc);

		indexDoc("1", "上海", "1111", "shanghai1");
		indexDoc("2", "上海", "112", "shanghai2");
		indexDoc("3", "上海", "113", "shanghai3");
		indexDoc("4", "上海", "114", "shanghai4");
		indexDoc("5", "北京", "225", "beijing1");
		indexDoc("6", "北京", "226", "beijing2");
		indexDoc("7", "北京", "227", "beijing3");
		indexDoc("8", "北京", "228", "beijing4");
		updateDocument();
		writer.commit();
		writer.forceMerge(5);
		writer.close();
	}

	private static void indexDoc(String id, String name,  String sortname, String groupname) throws IOException {
		Document doc = new Document();
		Field id_field = new StringField("id", id, Store.YES);
		Field name_field = new StringField("name", name, Store.YES);// StringField
		Field sort_field = new NumericDocValuesField("sortname", Long.parseLong(sortname));
		Field group_field = new SortedDocValuesField("groupname", new BytesRef(groupname));// 分组统计

		Field sortvalue_field = new StringField("sortvalue", sortname, Store.YES);
		Field namevalue_field = new StringField("groupvalue", groupname, Store.YES);
		
		doc.add(id_field);
		doc.add(name_field); 
		doc.add(sort_field);
		doc.add(group_field);

		doc.add(sortvalue_field);
		doc.add(namevalue_field);

		writer.addDocument(doc);
	}
 
	private static void updateDocument() throws IOException {
		 
		writer.updateNumericDocValue(new Term("id","2"), "sortname", 115); 
	}
	
}
