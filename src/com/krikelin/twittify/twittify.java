package com.krikelin.twittify;
import java.awt.Dimension;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.krikelin.spotifysource.*;
/**
 * Twittify app for Spotify App platform
 * @author Alexander
 *
 */
public class twittify extends SPActivity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public class Overview extends SPContentView{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3704253664015811976L;

		public Overview(SPActivity activity, SpotifyWindow mContext) {
			super(activity, mContext);
			// TODO Auto-generated constructor stub
			
			// Traverse the fetched content and make an list
			NodeList nodes = readyDoc.getElementsByTagName("entry");
			for(int i=0; i < nodes.getLength(); i++){
				Element elm = (Element)nodes.item(i);
				String entry = elm.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
				ISPEntry _entry = new SimpleEntry(activity,this,new URI(entry,"spotify:twitter:entry"),null,null,null);
				SPEntry sp_entry = new SPEntry(activity, this, mContext, _entry);
				sp_entry.setName(entry); 
		
				sp_entry.setPreferredSize(new Dimension(16,28));
				addElement(sp_entry, 0,1628,0);
			}
		}
		
	}
	
	/**
	 * Twitter search
	 */
	public static String TWITTER_SEARCH_QUERY = "http://search.twitter.com/search.atom?q=open.spotify.com/track/+%s";

	@Override
	public Object onLoad(URI arg0) {
		try {
			// TODO Auto-generated method stub
			String query = arg0.getParameter(); 
			// Gets the parameter of the app, in our case an search query
	
			String dataURL = String.format(TWITTER_SEARCH_QUERY,
					URLEncoder.encode(query, "ISO-8859-1"));
			
			// Parse the feed into our format
			
			Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(dataURL).openStream());
			
			return xmlDoc;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return null;
	}
	/**
	 * The document we have downloaded
	 */
	private Document readyDoc; 
	@Override
	public void render(URI arg0, Object... arg1) {
		// TODO Auto-generated method stub
		// If the download were sucessfull, add it
		if(arg1[0] instanceof Document){
			this.readyDoc = (Document)arg1[0]; 
			addPage("Twitter feed", new Overview(this,getContext())); // Create the first page
			// Instansiate the views here
			
		}
	}
	
}
