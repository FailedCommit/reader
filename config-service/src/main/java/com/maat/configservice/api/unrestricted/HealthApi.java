package com.maat.configservice.api.unrestricted;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;
import java.util.Map;

@RestController
@Path("/health")
public class HealthApi {
    private static final String startTime = new Date().toString();
    @GET
    @Path("/")
    @Produces("application/json")
    public Map<String, String> health() {
        Map<String,String> response = Maps.newHashMap();
        response.put("startTime",startTime);
        response.put("currentTime",new Date().toString());
        return response;
    }
}

