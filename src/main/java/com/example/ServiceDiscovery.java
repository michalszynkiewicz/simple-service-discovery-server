package com.example;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/services")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceDiscovery {

    private final Multimap<String, ServiceDefinition> services = ArrayListMultimap.create();

    @POST
    public void registerService(ServiceDefinition serviceDefinition) {
        services.put(serviceDefinition.serviceName, serviceDefinition);
    }

    @GET
    @Path("/{serviceName}")
    public Collection<ServiceDefinition> getServicesForName(@PathParam("serviceName") String serviceName) {
        return services.get(serviceName);
    }

    public static class ServiceDefinition {
        public String serviceName;
        public List<String> labels;
        public String url;
    }
}
