package com.daeng.nyang.service.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.daeng.nyang.repo.AnimalApiRepo;
import com.daeng.nyang.repo.PtagRepo;

@Service
public class AnimalOpenApiServiceImpl implements AnimalOpenApiService {

	@Value("${SERVICE_KEY}")
	private String SERVICE_KEY;

	@Autowired
	AnimalApiRepo animalApiRepo;
	
	@Autowired
	PtagRepo ptagRepo;
	
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
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("ServiceImpl에서 데이터 출력합니다.");

		System.out.println(sb.toString());

	}// end insertAnimalData

	@Override
	public void parsingXmlData() {
		
		int page = 1; //페이지 초기값 1
		int totalcount = 0;
		String[][] ptag = {{"활동적인", "에너지뿜뿜","활발한","민첩한"}, 			// [0] 활동성향 - E
							{"차분한", "조용한","침착한","느긋한"}, 			// [1] 활동성향 - Q
							{"충성심높은", "책임감있는", "온순한"}, 				// [2] 순종성향 - F
							{"똑똑한", "경계심많은", "영리한", "총명한"}, 		// [3] 순종성향 - C
							{"사교적인", "애착있는", "친근감넘치는","장난끼많은"}, 	// [4] 관계성향 - S
							{"고집이센", "자립심강한", "냉담한"},				// [5] 관계성향 - I
							{"진취적인", "적극적인", "호기심많은"},				// [6] 적응성향 - A
							{"신중한","방어적인","낯가리는","수줍음많은"}			// [7] 적응성향 - W
		};
		Character[] personality = {'E','Q','F','C','S','I','A','W'};
		Map<Character, String[]> map = new HashMap<Character, String[]>();
		map.put('E', ptag[0]);
		map.put('Q', ptag[1]);
		map.put('F', ptag[2]);
		map.put('C', ptag[3]);
		map.put('S', ptag[4]);
		map.put('I', ptag[5]);
		map.put('A', ptag[6]);
		map.put('W', ptag[7]);
		
		try {
			while(true) {
				/** URL */
				String url = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
						+ "bgnde=20190101" // 검색 시작날짜
						+ "&endde=20200930" // 검색 종료날짜
						+ "&upr_cd=6300000"  // 시도코드 ( 대전 : 6300000 )
						+ "&upkind=417000" // 종류 ( 개 : 417000, 고양이 : 422400, 기타 : 429900 )
						+ "&state=protect" // 상태( 보호중 : protect, 공고중 : notice, 전체 : null(빈값) )
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
					//mbti태그 등록
					String mbti="";
					for(int i=0; i<=6;i+=2) {
						Random rand = new Random();
						int random_p = rand.nextInt(2); // 0 or 1
						int personal_idx = i+random_p;
						mbti+=personality[personal_idx];
						String[] tags = map.get(personality[personal_idx]);
						int random_t = rand.nextInt(tags.length); // 0 or 1
						String picktag = tags[random_t]; 
						System.out.println("선택된 태그 : "+picktag);
						Long desertion_no = Long.parseLong(getTagValue("desertionNo", (Element)nNode));
						/** 성격태그 테이블에도 추가 */
						ptagRepo.insertPtag(desertion_no,picktag);
						
					}//end for
					System.out.println("mbti : "+mbti);
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) nNode;
						SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
						System.out.println("##########################");
						
//						Long.parseLong(getTagValue("desertionNo", element)),
//						simpledate.format(new Date(getTagValue("happenDt", element))), 
						// 품종
						String kind = getTagValue("kindCd", element);
						int idx = kind.indexOf("]");
						String kind_p = kind.substring(idx-1,idx);
						String kind_c = kind.substring(idx+1,kind.length()).trim();
						// 나이 
						String agestr = getTagValue("age", element);
						int aidx = agestr.indexOf("(");
						int age = Integer.parseInt(agestr.substring(0, aidx));
						// 몸무게 
						String weightstr = getTagValue("weight", element);
						int widx = weightstr.indexOf("(");
						float weight = Float.parseFloat(weightstr.substring(0,widx).trim());
						System.out.println(kind_p+" "+kind_c+" "+age+" "+weight);
						animalApiRepo.insertAnimalFromApi(
								Long.parseLong(getTagValue("desertionNo", element)),
								getTagValue("filename", element), 
								getTagValue("happenDt", element),
								getTagValue("happenPlace", element), 
								kind_p,
								kind_c,
								getTagValue("colorCd", element), 
								age, 
								weight, 
								getTagValue("noticeNo", element), 
								getTagValue("noticeSdt", element), 
								getTagValue("noticeEdt", element), 
								getTagValue("popfile", element), 
								getTagValue("processState", element), 
								getTagValue("sexCd", element), 
								getTagValue("neuterYn", element), 
								getTagValue("specialMark", element), 
								getTagValue("careNm", element), 
								getTagValue("careTel", element),
								getTagValue("careAddr", element),
								getTagValue("orgNm", element),
								getTagValue("chargeNm", element),
								getTagValue("officetel", element),
								mbti);
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
