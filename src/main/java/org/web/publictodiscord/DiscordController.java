package org.web.publictodiscord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Controller
public class DiscordController {

    @Value("${discord.webhook.url}")
    private String webhookUrl;

    @RequestMapping("/discord")
    public String discord() {
        return "posttodiscord";
    }

    @PostMapping("/discord/send")
    public String post(@RequestParam String message, Model model) {
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String payload = "{\"content\":\"" + message + "\"}";


        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {

            restTemplate.postForObject(webhookUrl, request, String.class);
            model.addAttribute("successMessage", "Сообщение успешно отправлено в Discord!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Произошла ошибка при отправке сообщения.");
        }

        return "posttodiscord";
    }
}
