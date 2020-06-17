package miu.edu.ea.airlineservice.service;

import com.netflix.discovery.EurekaClient;
import edu.miu.ea.contracts.email.Email;
import edu.miu.ea.contracts.user.UserDetailRequest;
import edu.miu.ea.contracts.user.UserDetailResponse;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ReminderService {
    @Autowired
    private KafkaTemplate<String, Email> kafkaTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    ReservationRepository reservationRepository;

    @Value("${user-service.service-name}")
    private String userServiceName;

    private static final Logger log = LoggerFactory.getLogger(ReminderService.class);

    @Value("${email-service.message-queue.name}")
    private String topicName;

    private void sendEmail(Email email) {
        kafkaTemplate.send(topicName, email);
    }

    private HttpEntity<Object> createHttpEntity(UserDetailRequest userDetailRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(userDetailRequest, headers);
    }

    public void sendFlightReminder() {
        List<Reservation> reservations = reservationRepository.findReservationsNeedRemind();
        for (Reservation reservation :reservations) {
            log.info(reservation.toString());
            Flight flight = reservation.getFlights().stream()
                    .sorted(Comparator.comparing(Flight::getDepartureTime))
                    .limit(1).findFirst().orElse(null);
            if(flight != null) {
                String url = eurekaClient.getNextServerFromEureka(userServiceName, false)
                        .getHomePageUrl() + "/detail";
                UserDetailRequest userDetailRequest = new UserDetailRequest();
                UserDetailResponse userDetailResponse = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        createHttpEntity(userDetailRequest),
                        UserDetailResponse.class
                ).getBody();

                if(userDetailResponse.getUserResponses() != null && userDetailResponse.getUserResponses().size() > 0) {
                    Email email = new Email();
                    email.setTarget(userDetailResponse.getUserResponses().get(0).getEmail());
                    email.setTitle("Flight reminder");
                    email.setBody("Flight departure time: " + flight.getDepartureTime().toString());

                    sendEmail(email);
                }

                reservation.setReminded(true);
            }
        }
    }
}
