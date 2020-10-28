package com.daeng.nyang.service.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class AnimalOpenApiServiceImpl implements AnimalOpenApiService {

	@Value("${SERVICE_KEY}")
	private String SERVICE_KEY;

	@Override
	public void insertAnimalData() {

		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
						+ "bgnde=20200101" + "&endde=20200930" + "&pageNo=1" + "&numOfRows=10"
						+ "&ServiceKey="); /* URL */
		StringBuilder sb = new StringBuilder();

//			SERVICE_KEY = URLEncoder.encode(SERVICE_KEY, "UTF-8");
//			SERVICE_KEY = URLDecoder.decode(SERVICE_KEY, "UTF-8");
		System.out.println("최종보내는 키 : " + SERVICE_KEY);

		urlBuilder.append(SERVICE_KEY);
		URL url;
		try {
			url = new URL(urlBuilder.toString());
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/xml");
				System.out.println("Response code: " + conn.getResponseCode());
				BufferedReader rd;
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}

				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				conn.disconnect();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ServiceImpl에서 데이터 출력합니다.");

		System.out.println(sb.toString());

	}// end insertAnimalData

	@Override
	public void parsingXmlData() {
		
		int page = 1; //페이지 초기값 1
		int totalcount = 0;
		
		try {
			while(true) {
				/** URL */
				String url = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
						+ "bgnde=20190101" // 검색 시작날짜
						+ "&endde=20200930" // 검색 종료날짜
						+ "&upr_cd=6300000"  // 시도코드 ( 대전 : 6300000 )
						+ "&upkind=417000" // 종류 ( 개 : 417000, 고양이 : 422400, 기타 : 429900 )
//						+ "&state=protect" // 상태( 보호중 : protect, 공고중 : notice, 전체 : null(빈값) )
						+ "&pageNo="+page         // 페이지넘버
						+ "&numOfRows=100" // 한페이지에 보여줄 데이터 갯수
						+ "&ServiceKey=" + SERVICE_KEY;
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(url);
				
				// root tag
//				doc.getDocumentElement().normalize();
//				System.out.println("Root Element: " + doc.getDocumentElement().getNodeName());

				// parsing tag
				NodeList nodeList = doc.getElementsByTagName("item");
				System.out.println("파싱할 리스트 수 : " + nodeList.getLength());
				totalcount+= nodeList.getLength();
				for (int tmp = 0; tmp < nodeList.getLength(); tmp++) {
					Node nNode = nodeList.item(tmp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) nNode;
						System.out.println("##########################");
						System.out.println("desertionNo: " + getTagValue("desertionNo", element));
//						System.out.println("happenDt: " + getTagValue("happenDt", element));
//						System.out.println("happenPlace: " + getTagValue("happenPlace", element));
//						System.out.println("kindCd: " + getTagValue("kindCd", element));
//						System.out.println("colorCd: " + getTagValue("colorCd", element));
//						System.out.println("age: " + getTagValue("age", element));
//						System.out.println("weight: " + getTagValue("weight", element));
//						System.out.println("noticeNo: " + getTagValue("noticeNo", element));
//						System.out.println("noticeSdt: " + getTagValue("noticeSdt", element));
//						System.out.println("noticeEdt: " + getTagValue("noticeEdt", element));
//						System.out.println("popfile: " + getTagValue("popfile", element));
//						System.out.println("processState: " + getTagValue("processState", element));
//						System.out.println("sexCd: " + getTagValue("sexCd", element));
//						System.out.println("neuterYn: " + getTagValue("neuterYn", element));
//						System.out.println("specialMark: " + getTagValue("specialMark", element));
//						System.out.println("careNm: " + getTagValue("careNm", element));
//						System.out.println("careTel: " + getTagValue("careTel", element));
//						System.out.println("careAddr: " + getTagValue("careAddr", element));
//						System.out.println("orgNm: " + getTagValue("orgNm", element));
//						System.out.println("chargeNm: " + getTagValue("chargeNm", element));
//						System.out.println("officetel: " + getTagValue("officetel", element));
//		                        System.out.println("noticeComment: "+getTagValue("noticeComment", element));
					} // end if
				} // end for
				page++;
				if(nodeList.getLength()==0) {
					System.out.println("더이상 가져올 데이터가 없습니다.");
					break;
				}
			}// end while
			System.out.println("총 가져온 데이터 수 : " + totalcount);
			
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}

	}// end parsingXmlData

	public String getTagValue(String tag, Element ele) {

		NodeList nodeList = ele.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nodeList.item(0);
		if (nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}
}
