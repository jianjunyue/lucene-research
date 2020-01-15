package org.lucene.research.vectors.test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

import org.apache.lucene.util.BytesRef;
import org.lucene.research.vectors.field.VectorsStoredCreator; 


public class SearchTest {

	public static void main(String[] args) {
		search();
		System.out.println("end");
	}


	private static String indexPath = "D:\\data\\index\\vectest";

	private static void search() {
		Path file = Paths.get(indexPath);
		IndexReader reader;
		try {
			reader = DirectoryReader.open(FSDirectory.open(file)); 
			IndexSearcher searcher =  newFixedThreadSearcher(reader,50); 
			Query query = new TermQuery(new Term("name", "上海")); 
			TopDocs results = searcher.search(query, 5000);
			ScoreDoc[] hits = results.scoreDocs; 
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);  
				getVertexs(doc.getBinaryValue("vec"));
				System.out.println(doc.get("id")+" , "+doc.get("name") +" , "+doc.get("vec")+" , "+doc.getBinaryValue("vec"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void getVertexs(BytesRef ref) {
		float[] vertices = VectorsStoredCreator.geVectorsFromVectorsStoredField(ref);
		for(int i=0;i<vertices.length;i++) {
		  System.out.println(vertices[i]);
		}
	}
	
	private static IndexSearcher newFixedThreadSearcher(IndexReader r, int nThreads) {
        return new IndexSearcher(r.getContext(), Executors.newFixedThreadPool(nThreads));
//        return new IndexSearcher(r.getContext());
    }

	/**
	 * 排序。 sort=id,INT,false
	 */
	public Sort getSort(String strSort) {
		try {
			if (strSort == null || strSort.trim().length() == 0) {
				return null;
			}
			String[] s = strSort.trim().split(",");
			if (s.length == 2 || s.length == 3) {
				boolean reverse = false;// 默认是小到大排序
				if (s.length == 3) {
					reverse = Boolean.valueOf(s[2].toLowerCase());
				}
				SortField.Type type = SortField.Type.INT;
				Sort sort = new Sort(new SortField(s[0].toLowerCase(), type, reverse));
				return sort;
			} else { 
			}
		} catch (Exception e) { 
		}
		return null;
	}
}
