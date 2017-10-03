
package au.edu.uow.Collection;

import java.io.Serializable;

/**
*
* @author Xiangyu Ren, 4218693, xr857
* @version v.1.0
*
*/
public class DVDAlbum implements Album,Serializable  {
	private static final long serialVersionUID = -5808038150407508076L;
	private String mediatype;
	private String title;
	private String genre;
	private String director;
	private String plotOutline;
	
	public DVDAlbum(String med,String tit,String gen, String dir,String  plo){
		mediatype = med;
	    title = tit;
		genre = gen;
		director =dir;
		plotOutline = plo;
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
	* This method returns the director of the album. 
	* @return the director of the album
	* @see #getMediaType()
	* @see #getTitle()
	* @see #getGenre()
	* @see #getPlotOutline()
	*/
	public String getDirector(){
		return director;
	}
	/**
	* This method returns the plotoutline of the album. 
	* @return the plotoutline of the album
	* @see #getMediaType()
	* @see #getTitle()
	* @see #getGenre()
	* @see #getDirector()
	*/
	public String getPlotOutline(){
		return plotOutline;
	}
}
