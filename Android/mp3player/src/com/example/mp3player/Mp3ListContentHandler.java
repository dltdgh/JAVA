package com.example.mp3player;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Mp3ListContentHandler extends DefaultHandler {
	
	private List<Mp3Info> infos = null;
	private Mp3Info mp3Info = null;
	private String tagName;
	
	public Mp3ListContentHandler(List<Mp3Info> tinfos){
		infos = tinfos;
	}
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = localName;
		if(tagName.equals("resource")){
			mp3Info = new Mp3Info();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(localName.equals("resource")){
			infos.add(mp3Info);
		}
		tagName = "";
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String temp = new String(ch, start, length);
		if(tagName.equals("id")){
			mp3Info.setId(temp);
		}
		else if(tagName.equals("mp3.name")){
			mp3Info.setMp3Name(temp);
		}
		else if(tagName.equals("mp3.size")){
			mp3Info.setMp3Size(temp);
		}
		else if(tagName.equals("lrc.name")){
			mp3Info.setLrcName(temp);
		}
		else if(tagName.equals("lrc.size")){
			mp3Info.setLrcSize(temp);
		}
	}
}
