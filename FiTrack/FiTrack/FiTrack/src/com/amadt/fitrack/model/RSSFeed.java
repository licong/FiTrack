package com.amadt.fitrack.model;

import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class RSSFeed 
{
    private String _title = null;
    private String _pubdate = null;
    private int _itemcount = 0;
    private List<RSSItem> _itemlist;
    
    
    public RSSFeed()
    {
        _itemlist = new Vector<RSSItem>(0); 
    }
    
    public int addItem(RSSItem item)
    {
        _itemlist.add(item);
        _itemcount++;
        return _itemcount;
    }
    
    public RSSItem getItem(int location)
    {
        return _itemlist.get(location);
    }
    
    public List<RSSItem> getAllItems()
    {
        return _itemlist;
    }
    
    public int getItemCount()
    {
        return _itemcount;
    }
    
    public void setTitle(String title)
    {
        _title = title;
    }
    
    public void setPubDate(String pubdate)
    {
        _pubdate = pubdate;
    }
    
    public String getTitle()
    {
        return _title;
    }
    
    public String getPubDate()
    {
        return _pubdate;
    }
    
    public static RSSFeed getFeed(String urlToRssFeed)
    {
        try
        {
            // setup the url
           URL url = new URL(urlToRssFeed);

           // create the factory
           SAXParserFactory factory = SAXParserFactory.newInstance();
           // create a parser
           SAXParser parser = factory.newSAXParser();

           // create the reader (scanner)
           XMLReader xmlreader = parser.getXMLReader();
           // instantiate our handler
           RSSHandler theRssHandler = new RSSHandler();
           // assign our handler
           xmlreader.setContentHandler(theRssHandler);
           // get our data through the url class
           InputSource is = new InputSource(url.openStream());
           // perform the synchronous parse           
           xmlreader.parse(is);
           // get the results - should be a fully populated RSSFeed instance, 
		   // or null on error
           return theRssHandler.getFeed();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	
            // if you have a problem, simply return null
            return null;
        }
    }
}