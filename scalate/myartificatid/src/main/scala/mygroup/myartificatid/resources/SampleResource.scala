package mygroup.myartificatid.resources

import com.sun.jersey.api.view.ImplicitProduces
import javax.ws.rs.{GET, Path, Produces}

@Path("/sample")
@ImplicitProduces(Array("text/html;qs=5"))
class SampleResource {
  def name = "Daniel"

  def sometext = "Changing text from Scala"
}
