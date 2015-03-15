package edu.cmu.lti.bic.autoreviewer.datastructure;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/***
 * Data structure for result of movie review
 * 
 * @author jhe
 * 
 */
public class ReviewResult {
	private String movieName;
	private double reviewScore;
	private Date reviewTime;
	private String username;

	private List<String> reviewSegments;

	public ReviewResult(String pMovieName, String pUsername) {
		movieName = pMovieName;
		reviewScore = 0;
		reviewTime = new Date();
		username = pUsername;
		reviewSegments = new LinkedList<String>();
	}

	public boolean addReviewSegment(String segment) {
		return reviewSegments.add(segment);
	}

	public void setReviewScore(double pReviewScore) {
		this.reviewScore = pReviewScore;
	}

	public String getReview() {
		String review = "";

		DateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		review = review + this.movieName + "#" + this.username + "#"
				+ formator.format(this.reviewTime) + "#" + new DecimalFormat("##.#").format(this.reviewScore*10) 
				+ "#";

		for (String tmpReviewSegment : reviewSegments) {
			review += tmpReviewSegment;
		}

		return review;
	}
}
