package ar.com.tsoluciones.arcom.index;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * Analyzer para espaÃ±ol
 */
public class SpanishAnalyzer extends ExtendedAnalyzer {

	private Set<?> stopSet;

	/**
	 * Stop words de para castellano. Sacadas del proyecto Snowball
	 * (http://snowball.tartarus.org/)
	 */
	public static final String[] STOP_WORDS = { "de", "la", "que", "el", "en",
			"y", "a", "los", "del", "se", "las", "por", "un", "para", "con",
			"no", "una", "su", "al", "es", "lo", "como", "más", "pero", "sus",
			"le", "ya", "o", "fue", "este", "ha", "si", "porque", "esta",
			"son", "entre", "está", "cuando", "muy", "sin", "sobre", "ser",
			"tiene", "también", "me", "hasta", "hay", "donde", "han", "quien",
			"están", "estado", "desde", "todo", "nos", "durante", "estados",
			"todos", "uno", "les", "ni", "contra", "otros", "fueron", "ese",
			"eso", "había", "ante", "ellos", "e", "esto", "mí", "antes",
			"algunos", "qué", "unos", "yo", "otro", "otras", "otra", "él",
			"tanto", "esa", "estos", "mucho", "quienes", "nada", "muchos",
			"cual", "sea", "poco", "ella", "estar", "haber", "estas", "estaba",
			"estamos", "algunas", "algo", "nosotros" };

	public SpanishAnalyzer() {
		this(STOP_WORDS);
	}

	public SpanishAnalyzer(Set<?> stopWords) {
		stopSet = stopWords;
	}

	public SpanishAnalyzer(String[] stopWords) {
		stopSet = StopFilter.makeStopSet(stopWords);
	}

	public SpanishAnalyzer(File stopwords) throws IOException {
		stopSet = WordlistLoader.getWordSet(stopwords);
	}

	public SpanishAnalyzer(Reader stopwords) throws IOException {
		stopSet = WordlistLoader.getWordSet(stopwords);
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream result = new StandardTokenizer(reader);
		result = new StopFilter(result, stopSet);

		/**
		 * El paso a minÃºsculas despuÃ©s de las stop words, porque las stop words
		 * tienen acentos
		 */
		result = new SpanishFilter(result);
		return result;
	}

	@Override
	public String canonizar(String field, String token) {
		return SpanishFilter.canonizar(token);
	}
}
