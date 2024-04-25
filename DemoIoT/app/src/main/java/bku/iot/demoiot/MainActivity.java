package bku.iot.demoiot;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.SwitchCompat;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.CompoundButton;
        import android.widget.TextView;

        import com.github.angads25.toggle.interfaces.OnToggledListener;
        import com.github.angads25.toggle.model.ToggleableView;
        import com.github.angads25.toggle.widget.LabeledSwitch;

        import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
        import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;

        import java.nio.charset.Charset;
        import androidx.appcompat.widget.SwitchCompat;
        import android.widget.CompoundButton;
// test git on android studio
public class MainActivity extends AppCompatActivity {
    MQTTHelper mqttHelper;
    TextView txtTemp, txtLig, txtHum;
    SwitchCompat btnLed, btnPump;
    String username, key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
//        Gui va nhan key tu login activity
        username = intent.getStringExtra("username");
        key = intent.getStringExtra("key");

        txtTemp = findViewById(R.id.TempValue);
        txtHum = findViewById(R.id.HumidValue);
        txtLig = findViewById(R.id.LightValue);
        btnLed = findViewById(R.id.switchLight);
        btnPump = findViewById(R.id.switchPump);

        btnLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {
                if (isChecked){
                    sendDataMQTT(username + "/feeds/nutnhan1", "1");
                }
                else {
                    sendDataMQTT(username + "/feeds/nutnhan1", "0");
                }
            }
        });
        btnPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {
                if (isChecked == true){
                    sendDataMQTT(username + "/feeds/nutnhan2", "1");
                }
                else {
                    sendDataMQTT(username + "/feeds/nutnhan2", "0");
                }
            }
        });

//        btnPump.setOnToggledListener();
        startMQTT();
    }
    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }
    public void startMQTT(){
        mqttHelper = new MQTTHelper(this, username, key);
        mqttHelper.setConnectionFailedListener(new MQTTHelper.ConnectionFailedListener() {
            @Override
            public void onConnectionFailed() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("TEST", "Connected to " + serverURI);
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d("TEST", "Connection lost", cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
                if (topic.contains("cambien1")){
                    txtTemp.setText(message.toString() + "°C");
                }
                else if (topic.contains("cambien2")){
                    txtHum.setText(message.toString() + "lux");
                }else if (topic.contains("cambien3")){
                    txtLig.setText(message.toString() + "%");
                }
                else if (topic.contains("nutnhan1")){
                    if (message.toString().equals("1")){
                        btnLed.setChecked(true);
                    }else if (message.toString().equals("0")){
                        btnLed.setChecked(false);
                    }
                } else if (topic.contains("nutnhan2")){
                    if (message.toString().equals("1")){
                        btnPump.setChecked(true);
                    }else if (message.toString().equals("0")){
                        btnPump.setChecked(false);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d("TEST", "Delivery complete for token: " + token);
            }
        });
    }
}