package nguyengquanghuy.mssv20141973.clerk_assitant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.AddAndEditConversationFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.BaseFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.LoginFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.RecordFragment;

public class LoginActivity extends AppCompatActivity {

    final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        changeFragment(LoginFragment.newInstance());
        AppConstant.getHeightWidthScreen(this);
        checkPermissionForDevice();
    }

    private void checkPermissionForDevice() {
        Log.e( "checkPermission: ", checkedPerMission()+"" );
        if (checkedPerMission()) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO
            }, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Request Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Request Denied", Toast.LENGTH_LONG).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkedPerMission() {
        int write_external_storage_permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_permission == PackageManager.PERMISSION_GRANTED &&
                record_audio_permission == PackageManager.PERMISSION_GRANTED;
    }

    public void changeFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, null).commit();
    }
}
