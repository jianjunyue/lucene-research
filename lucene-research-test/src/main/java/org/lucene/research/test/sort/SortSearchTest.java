package org.lucene.research.test.sort;

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


public class SortSearchTest {

	public static void main(String[] args) {
		search();
		System.out.println("end");
	}


	private static String indexPath = "D:\\data\\index\\test";

	private static void search() {
		Path file = Paths.get(indexPath);
		IndexReader reader;
		try {
			reader = DirectoryReader.open(FSDirectory.open(file));
//			IndexSearcher searcher = new IndexSearcher(reader);
			IndexSearcher searcher =  newFixedThreadSearcher(reader,5);
		    Sort sort = new Sort(new SortField("sortname",SortField.Type.STRING,false));
//		    Sort sort = new Sort(new SortField[]{new SortField("sortname", SortField.Type.INT, true)});
//		    sort = new Sort(new SortField("groupname",SortField.Type.STRING,true));
//		    Sort sort = new Sort(new SortField("location",new DistanceComparatorSource(10, 10)));
		    //group 
//		      sort = Sort.RELEVANCE;
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
