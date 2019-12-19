package org.lucene.research.test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.search.term

public class SearchTest {

	public static void main(String[] args) {
		search();
		System.out.println("end");
	}

	private static String indexPath = "/data/research/index/test";

	private static void search() {
		Path file = Paths.get(indexPath);
		IndexReader reader;
		try {
			reader = DirectoryReader.open(FSDirectory.open(file));
//			IndexSearcher searcher = new IndexSearcher(reader);
			IndexSearcher searcher =  newFixedThreadSearcher(reader,5);
		    Sort sort = new Sort(new SortField("sortname",SortField.Type.INT,false));
		    
//		    sort = new Sort(new SortField("groupname",SortField.Type.STRING,true));

		    //group 
		    Sort groupSort = Sort.RELEVANCE;
//		    new TermAllGroupsCollector(groupField);
		    
//			Query query = new TermQuery(new Term("name", "tian"));
			Query query = new TermQuery(new Term("name", "上海"));
//			Query query = new TermQuery(new Term("name", "beijing"));
			TopDocs results = searcher.search(query, 5,sort);
			ScoreDoc[] hits = results.scoreDocs;
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
//				System.out.println(doc.get("sortname")+" , "+doc.get("groupname"));
				 
				System.out.println(doc.get("id")+" , "+doc.get("name") +" , "+doc.get("sortvalue")+" , "+doc.get("groupvalue"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static IndexSearcher newFixedThreadSearcher(IndexReader r, int nThreads) {
//        return new IndexSearcher(r.getContext(), Executors.newFixedThreadPool(nThreads));
        return new IndexSearcher(r.getContext());
    }

}
