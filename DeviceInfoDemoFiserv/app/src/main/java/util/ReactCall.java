package util;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.deviceinfodemofiserv.DeviceInfoActivity;

import java.util.concurrent.TimeUnit;

import interfaces.IUpdateAgentData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import model.AgentHealthInfo;
import model.ResponseHealthCheck;
import model.User;
import nw.APIClient;
import nw.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;

import nw.APIClient;
import nw.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ReactCall {
    APIInterface apiInterface;
    Context context;
    private static final int ETA_UPDATE_INTERVALS = 10;
    private DeviceInfoUtility deviceInfoUtility;
    private Disposable etaUpdateDisposable;
    IUpdateAgentData iUpdateAgentData;
    private final Observable etaUpdateRepeatableObservable =
            Observable
                    .interval(ETA_UPDATE_INTERVALS, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .repeat();

    public ReactCall(Context ctx, DeviceInfoUtility deviceInfoUtility) {
        context = ctx;
        this.deviceInfoUtility = deviceInfoUtility;
        this.iUpdateAgentData = (IUpdateAgentData) ctx;
    }

    public void addEtaUpdateDisposable() {
        if (etaUpdateDisposable == null) {
            etaUpdateDisposable = etaUpdateRepeatableObservable.subscribe(etaUpdateConsumer);
        }
    }

    private final Consumer etaUpdateConsumer = o -> {
        //Here you got the repeated logic
        //responseText = (TextView) findViewById(R.id.responseText);
        AgentHealthInfo agentHealthInfo = deviceInfoUtility.getDeviceInfo();
        iUpdateAgentData.UpdateAgentData(agentHealthInfo);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        /**
         publish Agent HealthCheck
         **/
        Call<ResponseHealthCheck> responseHealthCheckCall = apiInterface.publishPOSHealthCheck(agentHealthInfo);
        responseHealthCheckCall.enqueue(new Callback<ResponseHealthCheck>() {
            @Override
            public void onResponse(Call<ResponseHealthCheck> call, Response<ResponseHealthCheck> response) {
                ResponseHealthCheck responseHealthCheck = response.body();
                System.out.println(ReactCall.class.getSimpleName() + ">>>>>>>>> " + responseHealthCheck.getStatus() + " " + responseHealthCheck.getMessage());
                Toast.makeText(context, responseHealthCheck.getStatus() + " " + responseHealthCheck.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseHealthCheck> call, Throwable t) {
                responseHealthCheckCall.cancel();
            }
        });

        /**
         Create new user
         **/
        /*User user = new User("Shrishailkumar", "Maddaraki");
        Call<User> call1 = apiInterface.createUser(user);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user1 = response.body();
                System.out.println(DeviceInfoActivity.class.getSimpleName() + ">>>>>>>>> " + user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt);
                Toast.makeText(context, user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });*/
    };

    public void disposeEtaUpdate() {
        if (
                etaUpdateDisposable != null &&
                        !etaUpdateDisposable.isDisposed()
        ) {
            etaUpdateDisposable.dispose();
            etaUpdateDisposable = null;
        }
    }


}
