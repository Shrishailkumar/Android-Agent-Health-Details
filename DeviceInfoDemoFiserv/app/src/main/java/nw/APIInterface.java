package nw;


import model.AgentHealthInfo;
import model.ResponseHealthCheck;
import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("/api/publishPOSHealthCheck")
    Call<ResponseHealthCheck> publishPOSHealthCheck(@Body AgentHealthInfo agentHealthInfo);

    @POST("/api/users")
    Call<User> createUser(@Body User user);

/*    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
