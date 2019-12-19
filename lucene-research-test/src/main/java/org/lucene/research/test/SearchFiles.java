package org.lucene.research.test;
//package org.lucene.research.test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Date;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class SearchFilesTest {
//
//	public static void main(String[] args) {
//		search();
//	}
//
//	private static String indexPath = "/data/research/index/test";
//
//	private static void search() {
//		Path file = Paths.get(indexPath);
//		IndexReader reader;
//		try {
//			reader = DirectoryReader.open(FSDirectory.open(file));
////			IndexSearcher searcher = new IndexSearcher(reader);
//			IndexSearcher searcher =  newFixedThreadSearcher(reader,5);
//
//			Query query = new TermQuery(new Term("contents", "test"));
//			TopDocs results = searcher.search(query, 5);
//			ScoreDoc[] hits = results.scoreDocs;
//			for (ScoreDoc hit : hits) {
//				Document doc = searcher.doc(hit.doc);
//				System.out.println(doc.get("contents"));
//				System.out.println(doc.get("modified"));
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	private static IndexSearcher newFixedThreadSearcher(IndexReader r, int nThreads) {
//        return new IndexSearcher(r.getContext(), Executors.newFixedThreadPool(nThreads));
//    }
//
//
//}
