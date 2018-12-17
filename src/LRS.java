import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LRS {
	private String s;
	private int pos;


	public LRS(String s, int pos){
		this.s = s;
		this.pos = pos;
	}

	// return the longest common prefix of s and t
	public static LRS lcp(LRS s, LRS t) {
		int n = Math.min(s.s.length(), t.s.length());
		for (int i = 0; i < n; i++) {
			if (s.s.charAt(i) != t.s.charAt(i))
				return new LRS(s.s.substring(0, i), t.pos);
		}
		System.out.print("You are here: ");
		return new LRS(s.s.substring(0, n), s.pos);
	}

	private static class LRSComparator implements Comparator<LRS> {
		@Override
		public int compare(LRS s1, LRS s2) {
			if(s1.s.compareTo(s2.s) >= 1){
				return -1;
			}
			if(s1.s.compareTo(s2.s) < 1){
				return 1;
			}
			return 1;
		}
	}


	// return the longest repeated string in s
	public static ArrayList<String> lrs(String s) {

		// form the N suffixes
		int N  = s.length();
		LRS[] suffixes = new LRS[N];
		for (int i = 0; i < N; i++) {
			suffixes[i] = new LRS(s.substring(i, N), i);
			System.out.println(suffixes[i].s + " " + suffixes[i].pos);
		}
		//for(int i = 0; i < suffixes.length; i++){
			//System.out.println(suffixes[i]);
		//}
		// sort them
		Arrays.sort(suffixes, new LRSComparator());
		for(int i = 0; i < suffixes.length; i++){
			System.out.println(suffixes[i].s + " " + suffixes);
		}

		// find longest repeated substring by comparing adjacent sorted suffixes
		ArrayList<String> y = new ArrayList<String>();
		LRS lrs = new LRS("", Integer.MAX_VALUE);
		for (int i = 0; i < N - 1; i++) {

			LRS x = lcp(suffixes[i], suffixes[i+1]);
			System.out.println("LCP:" + x.s + ";" + x.pos + ";" + x.s.length());
			//if ((x.s.length() >= lrs.s.length() && x.pos < lrs.pos) || x.s.length() > lrs.s.length())
			//	lrs = new LRS(x.s, x.pos);
		//}
			if(y.isEmpty() || x.s.length() > y.get(0).length()){
			y= new ArrayList();
			y.add(x.s);
			} else if(x.s.length() == y.get(0).length()){
				y.add(x.s);
			}
		}
		return y;
	}



	// read in text, replacing all consecutive whitespace with a single space
	// then compute longest repeated substring
	public static void main(String[] args) {
		//String s = "he bull, the primitive statue and oblique memorial of cuckolds, a thrifty shoeing-horn in a chain, hanging at his brother";
		//String s = "he hill, without making a small detour to see them. It is indeed, a fearful place. The torrent, swolle";
		//String s = "abcpqrabpqpq";
		String s = "ad, and answer. Exit. Alb. Where was his son when they did take his eyes? Gent. Come with my lady hither. Alb. He is not here. Gent. No, my good lord; I met him back";
		//String s = "s] Flout 'em and scout 'em, And scout 'em and flout 'em; Thought is free. CALIBAN. That's not the tune. [ARIEL plays the tune on a tabor and pipe] STEPHANO. What is this same? TRINCULO. This is th";
		s = s.replaceAll("\\s+", " ");
		//LRS x = lrs(s);
		ArrayList<String> x = lrs(s);
		for(int i = 0; i < x.size(); i++){
			System.out.println(x.get(i));
		}
		int pos = Integer.MAX_VALUE;
		int len = 0;
		//for (int star= 0; star < x.size(); star++) {
			//for (int start = 0; start < s.length() - x.get(star).length(); start++) {
				//String test = s.substring(start, start + x.get(star).length()); // For each testable location extract the substring that's the same size as toMatch
				//if (x.get(star).compareTo(test) == 0 && start < pos) {
					//pos = start;
					//len = x.get(star).length() + 1;
					//break;
				//}
			//}
		//}
		for(int star = 0; star < s.length() - x.get(0).length(); star++){
			if(x.contains(s.substring(star, star + x.get(0).length()))){
				pos = star;
				len = x.get(0).length();
				System.out.println(s.substring(star, star + x.get(0).length()));
				break;
			}
		}
		System.out.println("h".charAt(0) == ("H").charAt(0));
		System.out.println(s.substring(pos, pos + len));
		//if(x.pos == 0) {
			//System.out.println("'" + x.s + "'" + " " + x.pos);
		//} else {
			//System.out.println("'" + x.s + "'" + " " + s.substring(x.pos - x.s.length() - 1, x.pos));//s.substring(x.pos - x.s.length() , x.pos + 1));
		//}
	}
}
