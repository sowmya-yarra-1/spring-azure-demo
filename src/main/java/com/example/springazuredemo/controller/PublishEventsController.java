package com.example.springazuredemo.controller;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishEventsController {
  @RequestMapping("/publishEvent")
  public String publishEventToEventGrid() {

    // For custom event
    EventGridPublisherClient<BinaryData> customEventClient = new EventGridPublisherClientBuilder()
        .endpoint("https://pricing-data-topic.westeurope-1.eventgrid.azure.net/api/events")
        .credential(new AzureKeyCredential("k7ytifcZVmlD5Pwnz1olalsCOU6dGpESxGOBkkzX3xA="))
        .buildCustomEventPublisherClient();

    // Make sure that the event grid topic or domain you're sending to is able to accept the custom event schema.
    List<BinaryData> events = new ArrayList<>();
    events.add(BinaryData.fromObject(new HashMap<String, String>() {
      {
        put("id", UUID.randomUUID().toString());
        put("eventTime", OffsetDateTime.now().toString());
        put("eventType", "pricing-revenue-data4");
        put("data", "[{'itemName':'$50'}]");
        put("dataVersion", "0.1");
        put("subject", "pricing-revenue-data4");
      }
    }));

    System.out.println("values from event grid ----- "+events.get(0));
    customEventClient.sendEvents(events);

    return "Event has been published to Event grid";
  }

}
