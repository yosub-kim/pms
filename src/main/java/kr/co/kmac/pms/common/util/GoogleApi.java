package kr.co.kmac.pms.common.util;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import kr.co.kmac.pms.schedule.service.PersonalScheduleService;


@Component
public class GoogleApi {
	
		private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

		private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"),".credentials/calendar-java-quickstart");

		private static FileDataStoreFactory DATA_STORE_FACTORY;
		private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		
		private static HttpTransport HTTP_TRANSPORT;
		private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
		
		//private final String CLIENT_ID = "318730625859-97r242la98252kk2nomblbfmdqdsfnbv.apps.googleusercontent.com";
	    //private final String CLIENT_SECRET= "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";
		//private final String REDIRECT_URI= "http://localhost:446/_new/schedule/personal/googleAsync";
		
	    private final String CLIENT_ID = "863131768736-p00ih1jeqt4e14vr00gl4ospg8sovavl.apps.googleusercontent.com";
	    private final String CLIENT_SECRET= "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";
	    //private final String REDIRECT_URI= "https://pmsrenewal.kmac.co.kr/_new/schedule/personal/googleAsync";
	    private final String REDIRECT_URI= "https://newpms.kmac.co.kr/_new/schedule/personal/googleAsync";
	    
	    private final String GRANT_TYPE= "authorization_code";
	    private final String TOKEN_URL = "https://oauth2.googleapis.com/token";
		
		
		@Autowired
		private PersonalScheduleService personalScheduleService;
		static {
			try {
				HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
				DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
			}
				catch (Throwable t) {
				t.printStackTrace();
				System.exit(1);
			}
		}

		public static Credential authorize() throws IOException {
			InputStream in = GoogleApi.class.getResourceAsStream("/client_secret2.json");
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
					.setDataStoreFactory(DATA_STORE_FACTORY)
					.setAccessType("offline").build();

			LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setHost("localhost").setPort(111).build();
			
			Credential credential = new AuthorizationCodeInstalledApp(flow,
					new LocalServerReceiver()).authorize("user");
			System.out.println("credential =================================="+credential);
			return credential;
		}

		public static Calendar getCalendarService() throws IOException {
			Credential credential = authorize();
			return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();
			}

		public boolean checkEvent(com.google.api.services.calendar.model.Calendar calendar,Calendar service,String calendarId, String scheduleIdx) {
			try {
				Event event = service.events().get(calendarId, scheduleIdx).execute();
				System.out.println(event);
			}catch(IOException e) {
				return false;
			}
			
			
			return true;
		}
		
		public void insertEvent(com.google.api.services.calendar.model.Calendar calendar,Calendar service,String calendarId,Map<String, Object> scheduleInfo) throws IOException {
				Event event = new Event()
					.setSummary((String)scheduleInfo.get("type")+"_"+(String)scheduleInfo.get("content"))
					.setLocation((String)scheduleInfo.get("place"))
					.setDescription((String)scheduleInfo.get("content"));
				
				String startDate = (String)scheduleInfo.get("year") +"-"+(String)scheduleInfo.get("month")+"-"+(String)scheduleInfo.get("day")+"T"+(String)scheduleInfo.get("startHour")+":"+(String)scheduleInfo.get("startMin")+":00+09:00";
				DateTime startDateTime = new DateTime(startDate);
				EventDateTime start = new EventDateTime()
					.setDateTime(startDateTime)
					.setTimeZone("Asia/Seoul");
				
				event.setStart(start);
				String endDate = (String)scheduleInfo.get("year") +"-"+(String)scheduleInfo.get("month")+"-"+(String)scheduleInfo.get("day")+"T"+(String)scheduleInfo.get("endHour")+":"+(String)scheduleInfo.get("endMin")+":00+09:00";

				DateTime endDateTime = new DateTime(endDate);
			
				EventDateTime end = new EventDateTime()
					.setDateTime(endDateTime)
					.setTimeZone("Asia/Seoul");
				event.setEnd(end);

				event.setColorId(scheduleColor((String)scheduleInfo.get("type")));

				event.setId(String.valueOf(scheduleInfo.get("idx")));
				service.events().insert(calendarId,event).execute();
		}
	
		public void updateEvent(com.google.api.services.calendar.model.Calendar calendar,Calendar service,String calendarId,Map<String, Object> scheduleInfo) throws IOException {
				Event event = service.events().get(calendarId, String.valueOf(scheduleInfo.get("idx"))).execute()
						.setSummary((String)scheduleInfo.get("type")+"_"+(String)scheduleInfo.get("content"))
						.setLocation((String)scheduleInfo.get("place"))
						.setDescription((String)scheduleInfo.get("content"))
						.setStatus("confirmed");
				
				
				
				String startDate = (String)scheduleInfo.get("year") +"-"+(String)scheduleInfo.get("month")+"-"+(String)scheduleInfo.get("day")+"T"+(String)scheduleInfo.get("startHour")+":"+(String)scheduleInfo.get("startMin")+":00+09:00";
				DateTime startDateTime = new DateTime(startDate);
				EventDateTime start = new EventDateTime()
					.setDateTime(startDateTime)
					.setTimeZone("Asia/Seoul");
				
				event.setStart(start);
				String endDate = (String)scheduleInfo.get("year") +"-"+(String)scheduleInfo.get("month")+"-"+(String)scheduleInfo.get("day")+"T"+(String)scheduleInfo.get("endHour")+":"+(String)scheduleInfo.get("endMin")+":00+09:00";

				DateTime endDateTime = new DateTime(endDate);
			
				EventDateTime end = new EventDateTime()
					.setDateTime(endDateTime)
					.setTimeZone("Asia/Seoul");
				event.setEnd(end);
				
				event.setColorId(scheduleColor((String)scheduleInfo.get("type")));
		
				
				service.events().update(calendarId, String.valueOf(scheduleInfo.get("idx")), event).execute();
		}
		
		public void deleteEvent(com.google.api.services.calendar.model.Calendar calendar,Calendar service,String calendarId,String year, String month) throws IOException {
			String pageToken = null;
			do {
				Events events = service.events().list(calendarId).setPageToken(pageToken).execute();
				List<Event> items = events.getItems();
				
				for (Event event : items) {
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("idx",event.getId());
					map.put("SSN", SessionUtils.getSsn());
					String startYear = event.getStart().getDateTime().toString().substring(0,4);
					String startMonth = event.getStart().getDateTime().toString().substring(5,7);
					if(month.length() < 2) {
						month = "0"+month;
					}
					Map<String, Object> scheduleCnt =  null;
					System.out.println("year ==>"+year+"month ==>"+month+"startYear.equals(year) ==>"+startYear.equals(year));
					if(startYear.equals(year) && startMonth.equals(month)) {
						scheduleCnt = personalScheduleService.checkSchedule(map);
					}
					if(scheduleCnt != null) {
						if((int)scheduleCnt.get("CNT") < 1 && event.getId().matches("[+-]?\\d*(\\.\\d+)?")) {
							service.events().delete(calendarId, event.getId()).execute();
						}
					}
						
				}
				pageToken = events.getNextPageToken();
			} while (pageToken != null);
		}
		
		 public String getAccessTokenJsonData(String code){
		        RestTemplate restTemplate = new RestTemplate();

		        Map<String, Object> params = new HashMap<>();
		        params.put("code", code);
		        params.put("client_id", CLIENT_ID);
		        params.put("client_secret", CLIENT_SECRET);
		        params.put("redirect_uri", REDIRECT_URI);
		        params.put("grant_type", GRANT_TYPE);

		        ResponseEntity<String> responseEntity =
		                restTemplate.postForEntity(TOKEN_URL, params, String.class); // POST 방식으로 전송

		        if (responseEntity.getStatusCode() == HttpStatus.OK) {
		            return responseEntity.getBody(); // JSON 데이터 반환
		        }
		        return "error";
		    }
		
		public String scheduleColor(String type) {
			switch (type) {
				case "외부일정" :
					return "1";
				case "내부일정" :
					return "9";
				case "교육참석" :
					return "2";
				case "재택근무" :
					return "5";
				case "프로젝트" :
					return "6";
				case "휴가" :
					return "4";
				case "고객정보" :
					return "8";
				 default :
					return "9"; 
			}
		}
}
