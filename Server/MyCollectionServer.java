
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import au.edu.uow.Collection.Album;
import au.edu.uow.Collection.CDAlbum;
import au.edu.uow.Collection.DVDAlbum;
/**
 * This class is the server class.
 * @author Xiangyu Ren, 4218693, xr857
 * @version v.1.0
 */
public class MyCollectionServer {
	/**
 * The main function
 * @param args
 */
	public static void main(String[] args){
   new Server().start();
 }
}
class Server{
	private static int clientNumber=0;  //record the number of client
	//	private static int clientChange=0;  //record the number of client changes it's view
	//	private static boolean flag=true;  //record  the clients have changed their view

	private List<Album> list=new ArrayList<Album>();  //save albumDetial
	/**
	 * start server
	 * 
	 */
	public void start(){  // start server
		readXml();
		ServerSocket s=null;
		try{
			try {
				s = new ServerSocket(8888);
				while (true) {
					System.out.println("Server started, waiting for client..");
					Socket socket = s.accept();
					clientNumber++;
					System.out.println("Server:  clientNumber="+clientNumber+"#");
					Runnable r = new ThreadedEchoHandler(socket);
					Thread t = new Thread(r);
					t.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(s!=null)
					s.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Read .xml file which save the CD or DVD information
	 * 
	 */
	public void readXml(){
		String mediatype;   //dvd or cd needed variables
		String title;
		String genre;
		String artist;
		String[] tracks;
		String director;
		String plotOutline;

		String xmlContent=""; //save file(MyCollection.xml) content
		int startIndex,endIndex;   //temporary variables
		String path="MyCollection.xml";

		try {
			FileReader in=new FileReader(path);
			int temp;
			while((temp=in.read())!=-1){ //read file content
				xmlContent+=(char)temp;
			}
			in.close();
			xmlContent=xmlContent.replaceAll("\\r", "");
			while(xmlContent.contains("<CD>")||xmlContent.contains("<DVD>")){
				if(xmlContent.substring(0,7).indexOf("<CD>")!=-1){
					mediatype="CD";
					startIndex=xmlContent.indexOf("<Title>"); 
					endIndex=xmlContent.indexOf("</Title>"); 
					title=xmlContent.substring(startIndex+8, endIndex-1);
					startIndex=xmlContent.indexOf("<Genre>"); 
					endIndex=xmlContent.indexOf("</Genre>"); 
					genre=xmlContent.substring(startIndex+8, endIndex-1);
					startIndex=xmlContent.indexOf("<Artist>"); 
					endIndex=xmlContent.indexOf("</Artist>"); 
					artist=xmlContent.substring(startIndex+9, endIndex-1);
					startIndex=xmlContent.indexOf("<Tracks>"); 
					endIndex=xmlContent.indexOf("</Tracks>"); 
					tracks=xmlContent.substring(startIndex+9, endIndex-1).split("\\n");

					Album cdAlbum=new CDAlbum(mediatype,title, genre, artist, tracks);
					list.add(cdAlbum);
					endIndex=xmlContent.indexOf("</CD>");
					xmlContent=xmlContent.substring(endIndex+5);
					continue;
				}
				if(xmlContent.substring(0,7).indexOf("<DVD>")!=-1){
					mediatype="DVD";
					startIndex=xmlContent.indexOf("<Title>"); 
					endIndex=xmlContent.indexOf("</Title>"); 
					title=xmlContent.substring(startIndex+8, endIndex-1);
					startIndex=xmlContent.indexOf("<Genre>"); 
					endIndex=xmlContent.indexOf("</Genre>"); 
					genre=xmlContent.substring(startIndex+8, endIndex-1);
					startIndex=xmlContent.indexOf("<Director>"); 
					endIndex=xmlContent.indexOf("</Director>"); 
					director=xmlContent.substring(startIndex+11, endIndex-1);
					startIndex=xmlContent.indexOf("<PlotOutline>"); 
					endIndex=xmlContent.indexOf("</PlotOutline>"); 
					plotOutline=xmlContent.substring(startIndex+14, endIndex-1);

					Album dvdAlbum=new DVDAlbum(mediatype,title, genre, director,plotOutline );
					list.add(dvdAlbum);
					endIndex=xmlContent.indexOf("</DVD>");
					xmlContent=xmlContent.substring(endIndex+6);
					continue;
				}	
				break;
			}
		} catch (Exception e) {
			System.out.println("Server:  Input or output error...");
			//			e.printStackTrace();
		}
	} 
	/**
	 * GetAllAlbumTitles Protocol
	 * 
	 */
	public void getAllAlbumTitles(OutputStream sout){
		StringBuffer titles=new StringBuffer(list.get(0).getTitle());
		for(int i=1;i<list.size();i++){
			titles.append("#"+list.get(i).getTitle());
		}
		PrintWriter out=new PrintWriter(sout);
		out.println(titles.toString());
		out.flush();
	}
	/**
	 * GetAllAlbumDetail Protocol
	 * 
	 */
	public void getAlbumDetail(InputStream sin,OutputStream sout,String title){
		Object album=null;
		ObjectOutputStream oout=null;
		try {
			oout = new ObjectOutputStream(sout);
			System.out.println("Server:  server gets to get"+title+"information's command..");
			int i=0;
			for(i=0;i<list.size();i++)
				if(list.get(i).getTitle().equals(title))
					break;
			album=list.get(i);
			System.out.println("Server:  server gets"+list.get(i).getTitle()+"information");
			if(album instanceof DVDAlbum){
				System.out.println("Server:  "+list.get(i).getTitle()+"is a DVD album");
			}else{
				System.out.println("Server:  "+list.get(i).getTitle()+"is a CD album");
			}
			try {
				oout.writeObject(album);
				oout.flush();
				System.out.println("Server:  server has returned  "+title+" information..");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * AddAlbum Protocol
	 * 
	 */
	public synchronized void addAlbum(InputStream sin,OutputStream out){ 
		Object album=null;
		int i;
		System.out.println("Server: server checking if exist titles..");
		try {
			PrintWriter pout=new PrintWriter(out);
			ObjectInputStream oin=new ObjectInputStream(sin);
			System.out.println("Server:  server checking if exist titles..");
			album =oin.readObject();
			String title=null;
			if(album instanceof CDAlbum){
				title=((CDAlbum)album).getTitle();
			}else{
				title=((DVDAlbum)album).getTitle();
			}
			for(i=0;i<list.size();i++){
				if(list.get(i).getTitle().equals(title))
					break;
			}	

			if(i==list.size()){
				System.out.println("Server:  no title is "+title+" Album in server..");
				list.add((Album)album);
				pout.println("1");  //add success
				pout.flush();
			}else{  //there is a same one existing
				System.out.println("Server:  has title is"+title+" Album.. in server..");
				pout.println("0");   //add fail
				pout.flush();	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * DeleteAlbum Protocol
	 * 
	 */
	public synchronized void deleteAlbum(String title){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getTitle().equals(title)){
				list.remove(i);
				i--;
			}
		}
	}
	/**
	 * For multithreading
	 * Bye Protocol is in it!
	 * 
	 */
	class ThreadedEchoHandler implements Runnable
	{
		Socket socket=null;
		public  ThreadedEchoHandler(Socket socket){
			this.socket=socket;
		}

		public void run() {
			try{
				try {
					String str;
					InputStream sin = socket.getInputStream();
					OutputStream sout = socket.getOutputStream();
					BufferedReader buffer=new BufferedReader(new InputStreamReader(sin));
					while(true){
						if(!socket.isConnected())
							System.out.println("Server:  server has disconnected a connection..");
						str=buffer.readLine();
						if(str==null)
							str="null"; //make under lines unexecute
						else
							System.out.println("Server:  command: "+str+"#");
						if(str.equals("Bye"))  
							break;
						if(str.equals("GetAllAlbumTitles")){
							getAllAlbumTitles(sout);
							continue;
						}
						if(str.equals("GetAlbumDetail")){
							str=null;
							while(true){
								str=buffer.readLine();
								if(str==null)
									continue;
								else
									break;
							}
							System.out.println("Server: title that will get is :"+str+"#");
							getAlbumDetail(sin,sout,str);
							continue;
						}
						if(str.equals("AddAlbum")){
							addAlbum(sin,sout);
							continue;
						}
						if(str.equals("DeleteAlbum")){
							while((str=buffer.readLine())==null);
							deleteAlbum(str);
							continue;
						}
					}
					buffer.close();
					sin.close();
					sout.close();
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					socket.close();
				}
			}catch(Exception e){
				;
			}
		}
	}
}