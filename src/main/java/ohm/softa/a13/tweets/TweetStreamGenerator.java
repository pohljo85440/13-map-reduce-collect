package ohm.softa.a13.tweets;

import com.google.gson.Gson;
import ohm.softa.a13.model.Tweet;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Stream;

public class TweetStreamGenerator {
	private List<Tweet> tweets;

	private TweetStreamGenerator(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Stream<Tweet> getTweetStream() {
		return tweets.stream();
	}

	/**
	 * Read in all tweets from referenced JSON file and
	 * @param path eg. /trump_tweets.json
	 * @return
	 */
	public static TweetStreamGenerator fromJson(String path) {
		Reader reader = new InputStreamReader(TweetStreamGenerator.class.getResourceAsStream(path));
		Gson gson = new Gson();
		return new TweetStreamGenerator(List.of(gson.fromJson(reader, Tweet[].class)));
	}
}
