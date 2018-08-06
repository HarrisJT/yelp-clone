package yelp.controller;

import yelp.model.Review;
import yelp.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDB {

    /**
     * Gets all reviews from database for a certain business
     *
     * @param query pattern to match user's names
     * @return result of query
     */

    public static ArrayList<Review> getReviews(String query) {
        ArrayList<Review> reviews = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setText(rs.getString("text"));
                review.setVotes(rs.getInt("votes"));

                reviews.add(review);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reviews;
    }
}
