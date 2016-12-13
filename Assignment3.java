package com.predictionmarketing.Recommenderapp10;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;


public class App 
{
	public static void main( String[] args ) throws Exception
	{
		DataModel model = new FileDataModel(new File("data/ratings.csv"));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);//uncentered cosine similarity
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
		UserBasedRecommender recommender = 
				new GenericUserBasedRecommender(model, neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(100, 10);
		for (RecommendedItem recommendation : recommendations) 
		{
		  System.out.println(recommendation);
		}
		DataModel model1 = new FileDataModel(new File("data/ratings.csv"));
		  UserSimilarity similarity1 = new UncenteredCosineSimilarity(model1);//uncentered cosine similarity
			UserNeighborhood neighborhood1 = new NearestNUserNeighborhood(10, similarity1, model1);
			UserBasedRecommender recommender1 = 
					new GenericUserBasedRecommender(model1, neighborhood1, similarity1);
			List<RecommendedItem> recommendations1 = recommender1.recommend(100, 10);
		for (RecommendedItem recommendation1 : recommendations1) 
		{
		  System.out.println(recommendation1);
		}
		
		//program2
		System.out.println("program 2 output");
		 RandomUtils.useTestSeed();

		 DataModel model2 = new FileDataModel(new File("data/ratings.csv"));
		 RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
		 // Build the same recommender for testing that we did last time:
		for(int i=2;i<50;i++)
		{
		 RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
		 public Recommender buildRecommender(DataModel model12) throws TasteException {
		 UserSimilarity similarity = new UncenteredCosineSimilarity(model12);

		 UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity,
		model12);
		 return new GenericUserBasedRecommender(model12, neighborhood, similarity);
		 }
		 };
		 // Evaluate precision and recall "at 2"
		 IRStatistics stats = evaluator.evaluate(recommenderBuilder, null, model, null, 10, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 0.01);


		 System.out.println(" precision= "+stats.getPrecision()+"   Recall= "+stats.getRecall());
		} 
		
		
		
		//program 3
		System.out.println("program 3 output");
		try {
			DataModel dm=new FileDataModel(new File("data/ratings.csv"));
			ItemSimilarity ism = new LogLikelihoodSimilarity(dm);
			
			GenericItemBasedRecommender recommenderitem = new GenericItemBasedRecommender(dm, ism);
			
			
			
				List<RecommendedItem>recommendationsx = recommenderitem.mostSimilarItems(1, 10);
				List<RecommendedItem>recommendationsy = recommenderitem.mostSimilarItems(153, 10);
				
				System.out.println("Top 10 Simlar movies for Toystory ");
				for(RecommendedItem recommendationx : recommendationsx){
					//System.out.println(recommendationx.getItemID()+ "," + recommendationx.getValue());
				
					BufferedReader read=new BufferedReader(new FileReader("data/movies.csv"));
					
					String getline;
					while((getline=read.readLine())!=null)
					{
						String[] movies=getline.split(",");
						if(Integer.valueOf(movies[0])==recommendationx.getItemID())
						System.out.println(movies[1]);
					}

					read.close();
				
				}
				System.out.println();

				System.out.println("Top 10 Simlar movies for Batman Forever ");

				for(RecommendedItem recommendationy : recommendationsy){
					//System.out.println(recommendationx.getItemID()+ "," + recommendationx.getValue());
				
					BufferedReader bread=new BufferedReader(new FileReader("data/movies.csv"));
					
					String getline;
					while((getline=bread.readLine())!=null)
					{
						String[] movies=getline.split(",");
						if(Integer.valueOf(movies[0])==recommendationy.getItemID())
						System.out.println(movies[1]);
					}

					bread.close();
				
				}
			
			
			
			
		} catch (IOException e) {
		System.out.println("there wass an error");
			e.printStackTrace();
		} catch (TasteException e) {
			System.out.println("there wass an taste");
			e.printStackTrace();
		}

	}
}

