package com.example.hacks;
import java.util.HashMap;
import java.util.Map;

public class CollaborativeFiltering {

    private Map<String, Map<Integer, Double>> userItemRatings;

    public CollaborativeFiltering() {
        userItemRatings = new HashMap<>();
    }

    public void addRating(String userType, int item, double rating) {
        userItemRatings.computeIfAbsent(userType, k -> new HashMap<>()).put(item, rating);
    }

    public double predictRating(String userType, int item) {
        Map<Integer, Double> currentUserRatings = userItemRatings.get(userType);

        if (currentUserRatings == null || !currentUserRatings.containsKey(item)) {
            return 0.0;
        }

        double sum = 0.0;
        double weightSum = 0.0;

        for (Map.Entry<String, Map<Integer, Double>> entry : userItemRatings.entrySet()) {
            if (!entry.getKey().equals(userType)) {
                Map<Integer, Double> otherUserRatings = entry.getValue();

                if (otherUserRatings.containsKey(item)) {
                    double similarity = calculateCosineSimilarity(currentUserRatings, otherUserRatings);
                    sum += similarity * otherUserRatings.get(item);
                    weightSum += similarity;
                }
            }
        }

        if (weightSum == 0.0) {
            return 0.0;
        }

        return sum / weightSum;
    }

    private double calculateCosineSimilarity(Map<Integer, Double> user1Ratings, Map<Integer, Double> user2Ratings) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (Map.Entry<Integer, Double> entry : user1Ratings.entrySet()) {
            int item = entry.getKey();
            double rating1 = entry.getValue();
            double rating2 = user2Ratings.getOrDefault(item, 0.0);

            dotProduct += rating1 * rating2;
            norm1 += rating1 * rating1;
            norm2 += rating2 * rating2;
        }

        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    public static void main(String[] args) {
        CollaborativeFiltering cf = new CollaborativeFiltering();

        // Add sample ratings for user types 'j', 'h', 'a', and 'li'
        int n = (int)3e5;
        long sum[] = new long[n + 1];
        long count[] = new long[n + 1];
        cf.addRating("j", 1, 4.0);
        sum[1] += 4;
        count[1]++;
        cf.addRating("j", 2, 5.0);
        sum[2] += 5;
        count[2]++;
        cf.addRating("h", 1, 3.0);
        sum[1] += 3;
        count[1]++;
        cf.addRating("h", 2, 4.0);
        sum[2] += 4;
        count[2]++;
        cf.addRating("a", 1, 2.0);
        sum[1] += 2;
        count[1]++;
        cf.addRating("a", 2, 3.0);
        sum[2] += 3;
        count[2]++;
        cf.addRating("li", 1, 5.0);
        sum[1] += 5;
        count[1]++;
        cf.addRating("li", 2, 1.0);
        sum[2] += 1;
        count[2]++;
        // Predict rating for a user type and an item
        double predictedRating = cf.predictRating("li", 2);
        predictedRating = (double)(float)sum[2] / (float)count[2];
        System.out.println("Predicted rating: " + predictedRating);
    }	
}