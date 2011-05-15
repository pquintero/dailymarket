package ar.com.tsoluciones.arcom.index;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * TokenFilter para español.
 * Saca puntos de acrónimos, saca acentos, diéresis y pasa a minúsculas. 
 */
public final class SpanishFilter extends TokenFilter {

	public SpanishFilter(TokenStream in) {
		super(in);
	}

	/** 
	 * Returns the next token in the stream, or null at EOS.
	 * <p>Removes <tt>'s</tt> from the end of words.
	 * <p>Removes dots from acronyms.
	 */
	@Override
	public final Token next() throws IOException {
		Token t = input.next();
		if (t == null)
			return null;

		String text = new String(t.termBuffer(), 0, t.termLength());
		String type = t.type();

		// remove dots
		if (type == StandardTokenizer.TOKEN_TYPES[StandardTokenizer.ACRONYM]) { 
			StringBuilder trimmed = new StringBuilder();
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (c != '.')
					trimmed.append(c);
			}
			text = trimmed.toString();
		}
		return new Token(canonizar(text), t.startOffset(), t.endOffset(), type);
	}
	
	public static String canonizar(String token) {
		String lowerCase = token.toLowerCase();
		String withoutAcute = lowerCase.replace('á', 'a').replace('é', 'e').
			replace('í', 'i').replace('ó', 'o').replace('ú', 'u').replace("ñ", "n");
		return sacarDiacriticos(withoutAcute);
	}
	
	/**
	 * Elimina marcas diacríticas de las letras (excepto para la ñ, que es
	 * considerada una letra diferente en español)
	 */
	public static String sacarDiacriticos(String s) {
		return s.replaceAll("[[\\p{Mn}\\p{Me}\\p{Punct}\\u00a1\\u00bf]&&[^\\u0303]]", "");
	}
	
	public static void main(String[] args) {
		System.out.println(canonizar("¿Algo ñ?"));
	}
}
