package com.daeng.nyang.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Streaming;
import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.service.streaming.StreamingService;

@RestController
@CrossOrigin("*")
public class StreamingController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private StreamingService streamingService;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	/** 스트리밍 방송 시작 */
	@PostMapping("/user/streaming")
	public ResponseEntity<?> startStreaming(@RequestBody Streaming streaming,
			HttpServletRequest request) {
		System.out.println("스트리밍 방송을 시작하겠습니다.");
		String token = request.getHeader("Authorization"); // 토큰받기
		Map<String, String> resultMap = new HashMap<String, String>();
		if (token == null) {
			return null;
		} else if (jwtTokenUtil.isTokenExpired(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			try {
				TotToken user = (TotToken) redisTemplate.opsForValue().get(request.getHeader("Authorization"));
				Account account = user.getAccount();
				String user_id = account.getUser_id();
				System.out.println("관리자 아이디 : "+user_id);
				String title = streaming.getTitle();
				String contents = streaming.getContents();
				System.out.println("관리자 아이디 : "+user_id+", 제목: "+title+", 내용: "+contents);
				streamingService.startStreaming(user_id, title, contents);
				return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
	}
	
	/** 스트리밍 방송 종료 */
	@GetMapping("/user/streaming")
	public ResponseEntity<?> stopStreaming(HttpServletRequest request) {
		System.out.println("스트리밍 방송을 종료 하겠습니다.");
		String token = request.getHeader("Authorization"); // 토큰받기
		Map<String, String> resultMap = new HashMap<String, String>();
		if (token == null) {
			return null;
		} else if (jwtTokenUtil.isTokenExpired(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			try {
				TotToken user = (TotToken) redisTemplate.opsForValue().get(request.getHeader("Authorization"));
				Account account = user.getAccount();
				String user_id = account.getUser_id();
				streamingService.stopStreaming(user_id);
				return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
	}

	@GetMapping("/newuser/videos/{name}/full")
	public ResponseEntity<UrlResource> getFullVideo(@PathVariable String name) throws MalformedURLException {
		UrlResource video = new UrlResource("file:videodir/${name}");
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(video);
	}

	/**
	 * MediaTypeFactory 를 통해 content-type의 유형을 가져오거나, 기본값으로 8bit 스트림 유형인
	 * APPLICATION_OCTET_STREAM 리턴합니다. 잘 보면, body에 ResourceRegion 객체를 담아 보내는데,
	 * ResourceRegion은 파일객체의 Range 범위만큼 가져올 수 있는 스프링코어 객체입니다.
	 */
	@GetMapping("/newuser/videos/{name}")
	public ResponseEntity<ResourceRegion> getVideo(@PathVariable String name, @RequestHeader HttpHeaders headers)
			throws IOException {

		System.out.println("getvideo");
		UrlResource video = new UrlResource("classpath:" + "videodir" + "/" + name);
		ResourceRegion region = resourceRegion(video, headers);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(region);
	}

	/**
	 * else구문에서 chunk 사이즈로 자른 시작값부터 chunk사이즈 만큼의 ResourceRegion을 return 하고, 그
	 * 다음요청부터는 header에 담긴 range 범위만큼 짤라서 보냅니다.
	 */
	private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {

		final long chunkSize = 1000000L;
		long contentLength = video.contentLength();

		HttpRange httpRange = headers.getRange().stream().findFirst().get();
		if (httpRange != null) {
			long start = httpRange.getRangeStart(contentLength);
			long end = httpRange.getRangeEnd(contentLength);
			long rangeLength = Long.min(chunkSize, end - start + 1);
			return new ResourceRegion(video, start, rangeLength);
		} else {
			long rangeLength = Long.min(chunkSize, contentLength);
			return new ResourceRegion(video, 0, rangeLength);
		}
	}

//	@GetMapping("/newuser/streaming")
//	@ApiOperation("스트리밍 테스트 ")
//	public ResponseEntity<?> getContentMediaVideo(Map<String, Object> map, HttpServletRequest request,
//			HttpServletResponse response) throws UnsupportedEncodingException, IOException {
//
//		// 영상 위치
//		String movieDir = "";
//		// 대상 동영상 파일명
//		String movieName = (String) map.get("movieName");
//
//		RandomAccessFile randomFile = new RandomAccessFile(new File(movieDir, movieName), "r");
//
//		long rangeStart = 0; // 요청범위의 시작 위치
//		long rangeEnd = 0; // 요청범위의 끝 위치
//		boolean isPart = false; // 부분요청일 경우 true, 전체 요청의 경우 false
//
//		try {
//			// 동영상 파일 크기
//			long movieSize = randomFile.length();
//			// 스트림 요청범위, request의 헤더에서 range를 읽는다.
//			String range = request.getHeader("range");
//			System.out.println("range : " + range);
//
//			// 브라우저에 따라 range형식이 다른데, 기본 형식은 "bytes={start}-{end}" 형식이다.
//			// range가 null이거나, reqStart가 0이고 end가 없을 경우 전체 요청이다.
//			// 요청 범위를 구한다.
//			if (range != null) {
//				// 처리의 편의를 위해 요청 range에 end 값이 없을 경우 넣어줌
//				if (range.endsWith("-")) {
//					range = range + (movieSize - 1);
//				}
//				int idxm = range.trim().indexOf("-"); // "-" 위치
//				rangeStart = Long.parseLong(range.substring(6, idxm));
//				rangeEnd = Long.parseLong(range.substring(idxm + 1));
//				if (rangeStart > 0) {
//					isPart = true;
//				}
//			} else { // range가 null인 경우 동영상 전체 크기로 초기값을 넣어줌. 0부터 시작하므로 -1
//				rangeStart = 0;
//				rangeEnd = movieSize - 1;
//			}
//
//			// 전송 파일 크기
//			long partSize = rangeEnd - rangeStart + 1;
//			System.out.println("accepted range: " + rangeStart + "-" + rangeEnd + "/" + partSize + " isPart:" + isPart);
//
//			// 전송시작
//			response.reset();
//
//			// 전체 요청일 경우 200, 부분 요청일 경우 206을 반환상태 코드로 지정
//			response.setStatus(isPart ? 206 : 200);
//
//			// mime type 지정
//			response.setContentType("video/mp4");
//
//			// 전송 내용을 헤드에 넣어준다. 마지막에 파일 전체 크기를 넣는다.
//			response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
//			response.setHeader("Accept-Ranges", "bytes");
//			response.setHeader("Content-Length", "" + partSize);
//
//			OutputStream out = response.getOutputStream();
//			// 동영상 파일의 전송시작 위치 지정
//			randomFile.seek(rangeStart);
//
//			// 파일 전송... java io는 1회 전송 byte수가 int로 지정됨
//			// 동영상 파일의 경우 int형으로는 처리 안되는 크기의 파일이 있으므로
//			// 8kb로 잘라서 파일의 크기가 크더라도 문제가 되지 않도록 구현
//			int bufferSize = 8 * 1024;
//			byte[] buf = new byte[bufferSize];
//			do {
//				int block = partSize > bufferSize ? bufferSize : (int) partSize;
//				int len = randomFile.read(buf, 0, block);
//				out.write(buf, 0, len);
//				partSize -= block;
//			} while (partSize > 0);
//			System.out.println("sent " + movieName + " " + rangeStart + "-" + rangeEnd);
//		} catch (IOException e) {
//			// 전송 중에 브라우저를 닫거나, 화면을 전환한 경우 종료해야 하므로 전송취소.
//			// progressBar를 클릭한 경우에는 클릭한 위치값으로 재요청이 들어오므로 전송 취소.
//			System.out.println("전송이 취소 되었음");
//		} finally {
//			randomFile.close();
//		}
//
//		return ResponseEntity.status(HttpStatus.OK).body("스트리밍 테스트 OK");
//	}
}
