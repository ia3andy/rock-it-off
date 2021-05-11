package quarkus.world.tour;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/rock")
public class RockBandResource {

    @GET
    public List<Band> listAll() {
        return Band.listAll();
    }

    @GET
    @Path("alive")
    public List<Band> listAlive() {
        return Band.stillAlive();
    }

    @GET
    @Path("{id}")
    public Band byId(@PathParam("id") long id) {
        final Band band = Band.findById(id);
        if(band == null) {
            final Response notFoundResponse = Response.status(Response.Status.NOT_FOUND)
                    .entity("no band found with id: " + id)
                    .build();
            throw new WebApplicationException(notFoundResponse);
        }
        return band;
    }

    @POST
    @Transactional
    public Response add(Band band) {
        band.persist();
        return Response.created(UriBuilder.fromPath("/rock/" + band.id).build()).build();
    }
}