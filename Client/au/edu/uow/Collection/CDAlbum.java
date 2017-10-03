
package au.edu.uow.Collection;

import java.io.Serializable;

/**
*
* @author Xiangyu Ren, 4218693, xr857
* @version v.1.0
*
*/
public class CDAlbum implements Album ,Serializable  {
	private static final long serialVersionUID = -6057084635302728059L;
	private String mediatype;
	private String title;
	private String genre;
	private String artist;
	private String[] tracks;
	
	public CDAlbum(String med,String tit,String gen, String art,String [] tra){
		mediatype = med;
		title = tit;
		genre = gen;
		artist = art;
		tracks = tra;
	}
	
	public String getMediaType(){
		return mediatype;
	}
	public String getTitle() {
		return title;
	}
	public String getGenre(){
		return genre;
	}
	/**
	* This method returns the artist of the album. 
	* @return the artist of the album
	* @see #getMediaType()
	* @see #getTitle()
	* @see #getGenre()
	* @see #getTrack()
	*/
	public String getArtist(){
		return artist;
	}
	/**
	* This method returns the track of the album. 
	* @return the track of the album
	* @see #getMediaType()
	* @see #getTitle()
	* @see #getGenre()
	* @see #getArtist()
	*/
	public String[] getTrack(){
		return tracks;
	}

}
