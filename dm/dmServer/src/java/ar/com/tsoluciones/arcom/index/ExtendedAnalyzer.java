package ar.com.tsoluciones.arcom.index;

import org.apache.lucene.analysis.Analyzer;

/**
 * Extensión del Analyser, que ofrece un método adicional. Se usa este método
 * cuando se desea usar solo una parte del trabajo del analyzer, y no todo el
 * proceso (que incluye la separación en tokens, la eliminación de stop word,
 * etc).
 */
public abstract class ExtendedAnalyzer extends Analyzer {

	public abstract String canonizar(String field, String token);
	
	public static String canonizar(Analyzer analyzer, String field, String token) {
		if (analyzer instanceof ExtendedAnalyzer)
			return ((ExtendedAnalyzer) analyzer).canonizar(field, token);
		
		return token;
	}
}
