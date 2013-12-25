package dmytro.kuzenko.android;

import com.example.quickstart.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
//import android.widget.TableLayout;

public class CreateTableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table, menu);
        return true;
    }
    
    public void onRadioClick(View v){
        switch (v.getId()) {
            case R.id.radioNewTable:
            	((TextView)findViewById(R.id.firstTableLabel)).setText("New table name:");
            	((TextView)findViewById(R.id.secondTableLabel)).setText("Types:");
            	((EditText)findViewById(R.id.result_table)).setVisibility(4);
            	((TextView)findViewById(R.id.resultTableLabel)).setVisibility(4);
                break;
            case R.id.radioDescart:
            	((TextView)findViewById(R.id.firstTableLabel)).setText("First table name:");
            	((TextView)findViewById(R.id.secondTableLabel)).setText("Second table name:");
            	((EditText)findViewById(R.id.result_table)).setVisibility(0);
            	((TextView)findViewById(R.id.resultTableLabel)).setVisibility(0);
                break;
            default:
                break;
        }
    }
    
    public void onOkClick(View v){
        Intent answerInent = new Intent();
        
        String table1 = ((EditText)findViewById(R.id.table1)).getText().toString();
        String resultTable = ((EditText)findViewById(R.id.result_table)).getText().toString();
        answerInent.putExtra(MainActivity.TABLE1, table1);
        answerInent.putExtra(MainActivity.RESULT_TABLE, resultTable);
        
        RadioButton rNewTable;
        rNewTable = (RadioButton) findViewById(R.id.radioNewTable);
        RadioButton rDescart;
        rDescart = (RadioButton) findViewById(R.id.radioDescart);
        if(rNewTable.isChecked()){
        	// TODO:
            answerInent.putExtra(MainActivity.OPERATION, "new table");
            String table2 = ((EditText)findViewById(R.id.table2)).getText().toString();
            answerInent.putExtra(MainActivity.TABLE2, table2);
        }
        else if(rDescart.isChecked()){
            answerInent.putExtra(MainActivity.OPERATION, "descart");
            String table2 = ((EditText)findViewById(R.id.table2)).getText().toString();
            answerInent.putExtra(MainActivity.TABLE2, table2);
        }
        
        setResult(RESULT_OK, answerInent);
        finish();
    }

}
