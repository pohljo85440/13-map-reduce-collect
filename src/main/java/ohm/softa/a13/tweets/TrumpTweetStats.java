package ohm.softa.a13.tweets;

import ohm.softa.a13.model.Tweet;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrumpTweetStats {

	private static final Pattern ALPHA_NUMERICAL = Pattern.compile("[a-z0-9@]+");

    public static Map<String, Long> calculateSourceAppStats(Stream<Tweet> tweetStream) {
	return tweetStream.map(t -> t.getSourceApp()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

    public static Map<String, Set<Tweet>> calculateTweetsBySourceApp(Stream<Tweet> tweetStream) {
		return tweetStream.collect(Collectors.groupingBy(Tweet::getSourceApp, Collectors.toSet()));
    }

    public static Map<String, Integer> calculateWordCount(Stream<Tweet> tweetStream, List<String> stopWords) {
		return tweetStream
			.map(t -> t.getText())
			.map(t -> t.split("( )+"))
			.flatMap(a -> Arrays.stream(a))
			.map(String::toLowerCase)
			.filter(word -> ALPHA_NUMERICAL.matcher(word).matches())
			.filter(f -> !stopWords.contains(f)) // .filter(w -> stopWords.stream().noneMatch(sw -> sw.equals(w)))
			.reduce(new LinkedHashMap<String, Integer>(), (acc, word) -> {
				acc.put(word, acc.compute(word, (k, v) -> v == null ? 1 : v + 1));
				return acc;
			}, (m1, m2) -> m1)
			.entrySet()
			.stream()
			.filter(e -> e.getValue() > 10)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	}
}
