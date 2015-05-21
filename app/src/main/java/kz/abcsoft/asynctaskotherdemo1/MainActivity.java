package kz.abcsoft.asynctaskotherdemo1;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


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
        cattask.execute("cat1.jpg", "cat2.jpg", "cat3.jpg", "cat4.jpg");
    }

    public void onResultClick(View v) {
        if (cattask == null)
            return;
        int result = -1;
        try {
            result = cattask.get(1, TimeUnit.SECONDS);
            Toast.makeText(this, "Полученный результат: " + result,
                    Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.e("AsyncTaskDemo", "get timeout, result = " + result);
            e.printStackTrace();
        }
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

    class CatTask extends AsyncTask<String, Integer, Integer> {

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
        protected Integer doInBackground(String... urls) {

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

                return 2015;

        }


            @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

                tvInfo.setText("Залез" + " Возвращаем результат: " + result);
                buttonStart.setVisibility(View.VISIBLE);
                horizontalprogress.setProgress(0);

        }
    }

        private void getFloor(int floor) throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }


}
