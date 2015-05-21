package kz.abcsoft.asynctaskotherdemo1;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    CatTask cattask;
    TextView tvInfo;

    ProgressBar progress;
    Button buttonStart;
    ProgressBar horizontalprogress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView) findViewById(R.id.tvInfo);

        progress = (ProgressBar)findViewById(R.id.progress) ;
        buttonStart = (Button)findViewById(R.id.buttonStart) ;
        horizontalprogress = (ProgressBar)findViewById(R.id.progress2) ;
        horizontalprogress.setMax(14);
    }

    public void onclick(View v) {
        cattask = new CatTask();
        cattask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class CatTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Полез на крышу");
            buttonStart.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvInfo.setText("Этаж " + values[0]);
            horizontalprogress.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                int counter = 0;

                for (int i = 0; i < 14; i++) {
                    getFloor(counter);

                    publishProgress(++counter);

                    TimeUnit.SECONDS.sleep(1);
                }
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                return null;

        }


            @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);

            tvInfo.setText("Залез");
                buttonStart.setVisibility(View.VISIBLE);
                horizontalprogress.setProgress(0);

        }
    }

        private void getFloor(int floor) throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }


}
