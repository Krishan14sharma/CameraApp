package krishan.cameraapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;

import util.EasyUtil;


public class MyActivity extends ActionBarActivity {
    private ImageView resultView;
    private Uri outputUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        resultView = (ImageView) findViewById(R.id.result_image);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == EasyUtil.PICKnCAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            beginCrop(EasyUtil.getUrifromIntent(result,outputUri));
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    public void pickImage(View view) {
        resultView.setImageDrawable(null);
//        Crop.pickImage(this);
        outputUri=EasyUtil.createUri("CameraTest");
        EasyUtil.pickNcapture(MyActivity.this,outputUri);

    }
    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
        new Crop(source).output(outputUri).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
