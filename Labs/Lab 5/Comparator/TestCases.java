import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      Comparator <Song> Artist = new ArtistComparator();
      assertEquals(true, Artist.compare(songs[5],songs[3]) <= 0);
      assertEquals(true, Artist.compare(songs[3],songs[7]) == 0);
      assertEquals(true, Artist.compare(songs[0],songs[1]) <= 0);
   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator <Song> lambda = (Song song1, Song song2) -> {return song1.getTitle().compareTo(song2.getTitle());};
      assertEquals(true, lambda.compare(songs[5],songs[3]) == 0);
      assertEquals(true, lambda.compare(songs[5],songs[7]) == 0);
      assertEquals(true, lambda.compare(songs[0],songs[1]) >= 0);

   }

   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> YearCompare = (Comparator.comparingInt(Song::getYear)).reversed();
      assertEquals(true, YearCompare.compare(songs[0],songs[1]) == 0);
      assertEquals(true, YearCompare.compare(songs[1],songs[2]) >= 0);
      assertEquals(true, YearCompare.compare(songs[2],songs[4]) >= 0);
   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> c1 = Comparator.comparing(Song :: getTitle);
      Comparator<Song> c2 = Comparator.comparing(Song :: getYear);
      ComposedComparator x = new ComposedComparator(c1, c2);
      assertEquals(true, x.compare(songs[3],songs[5]) >= 0);
      assertEquals(true, x.compare(songs[3],songs[7]) >= 0);  
      assertEquals(true, x.compare(songs[2],songs[1]) >= 0);

   }

   @Test
   public void testThenComparing()
   {
      Comparator <Song> thenCompare = Comparator.comparing(Song::getTitle).thenComparing(Song::getArtist);
      assertEquals(true, thenCompare.compare(songs[3],songs[5]) >= 0);
      assertEquals(true, thenCompare.compare(songs[3],songs[7]) == 0);
      assertEquals(true, thenCompare.compare(songs[5],songs[7]) <= 0);

   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song>thencompare2 = Comparator.comparing(Song::getArtist).thenComparing(Song::getTitle).thenComparing(Song::getYear);


      songList.sort(thencompare2);


      assertEquals(songList, expectedList);
   }
}
