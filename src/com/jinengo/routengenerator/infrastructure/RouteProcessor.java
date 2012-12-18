package com.jinengo.routengenerator.infrastructure;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RouteProcessor {

	public void processXmlDocument(Document doc){
    	NodeList routeList = doc.getElementsByTagName("route");

    	int routeCount = routeList.getLength();
    	System.out.println(routeCount);
    	for (int i = 0; i < routeCount; i++) {
    		Node route = routeList.item(i);
    		if(route.getNodeType() == Node.ELEMENT_NODE){
                Element routeElement = (Element)route;
                String val = routeElement.getElementsByTagName("name").item(0).getTextContent();
                String costs = routeElement.getElementsByTagName("costs").item(0).getTextContent();
                System.out.println(val);
                System.out.println(costs);
    		}
    	}
	}
}
