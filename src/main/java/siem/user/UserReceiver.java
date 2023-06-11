package siem.user;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Incoming;



@ApplicationScoped
public class UserReceiver {
    @Inject
    UserService userService;

    @Incoming("user-channel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Blocking
    public void process(JsonObject u) {
        // log
        Log.info(u.toString());

        // JsonObject to User
        User user = u.mapTo(User.class);

        // entity detached workaround >:(
        User user2 = new User();
        user2.userState = user.userState;
        user2.name = user.name;
        user2.email = user.email;
        user2.picture = user.picture;
        user2.nickname = user.nickname;
        user2.sub = user.sub;
        user2.updated_at = user.updated_at;
        user2.role = user.role;

        // post
        userService.create(user2);
    }
}
