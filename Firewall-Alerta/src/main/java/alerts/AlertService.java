package alerts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final RestTemplate restTemplate;

    @Value("${reports.service.url:http://localhost:8081/reports}")
    private String reportsServiceUrl;

    public AlertService(AlertRepository alertRepository, RestTemplate restTemplate) {
        this.alertRepository = alertRepository;
        this.restTemplate = restTemplate;
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public Optional<Alert> getAlertById(Long id) {
        return alertRepository.findById(id);
    }

    public Alert createAlert(Alert alert) {
        Alert savedAlert = alertRepository.save(alert);

        try {
            restTemplate.postForObject(reportsServiceUrl, savedAlert, Void.class);
        } catch (RestClientException e) {
            System.err.println("No se pudo enviar la alerta al microservicio reportes: " + e.getMessage());
        }

        return savedAlert;
    }
}
