package com.travel.service;

import com.travel.Dto.PlanContentDto;


import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    public List<PlanContentDto> fetchApiData(String apiUrl) {
        List<PlanContentDto> contentList = new ArrayList<>();

        try {
            URL url = new URL(apiUrl);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(url.openStream());

            NodeList items = document.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                PlanContentDto contentDto = new PlanContentDto();
                contentDto.setPlaceName(item.getElementsByTagName("title").item(0).getTextContent());
                contentDto.setPlaceAddress(item.getElementsByTagName("addr1").item(0).getTextContent());
                contentDto.setPlaceTel(item.getElementsByTagName("tel").item(0).getTextContent());
                contentDto.setPlaceLatitude(item.getElementsByTagName("mapx").item(0).getTextContent());
                contentDto.setPlaceLongitude(item.getElementsByTagName("mapy").item(0).getTextContent());
                contentDto.setPlace_img(item.getElementsByTagName("firstimage").item(0).getTextContent());
                
                String areaCodeStr = item.getElementsByTagName("areacode").item(0).getTextContent();
                if (!areaCodeStr.isEmpty()) {
                    contentDto.setArea_code(Integer.parseInt(areaCodeStr));
                }

                String sigunguCodeStr = item.getElementsByTagName("sigungucode").item(0).getTextContent();
                if (!sigunguCodeStr.isEmpty()) {
                    contentDto.setSigungu_code(Integer.parseInt(sigunguCodeStr));
                }

                String contentIdStr = item.getElementsByTagName("contentid").item(0).getTextContent();
                if (!contentIdStr.isEmpty()) {
                    contentDto.setContent_id(Integer.parseInt(contentIdStr));
                }

                String contentTypeIdStr = item.getElementsByTagName("contenttypeid").item(0).getTextContent();
                if (!contentTypeIdStr.isEmpty()) {
                    contentDto.setContent_type(Integer.parseInt(contentTypeIdStr));
                }

                contentList.add(contentDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }
}

