package com.example.flowz.sqlapp2;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editID,editName,editSurname,editMarks;
    Button btnAdd,btnGetData,btnUpdate,btnDelete,btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);

        editID =(EditText)findViewById(R.id.entered_id);
        editName =(EditText)findViewById(R.id.entered_name);
        editSurname =(EditText)findViewById(R.id.entered_Surmane);
        editMarks =(EditText)findViewById(R.id.entered_Marks);

        btnAdd = (Button)findViewById(R.id.add_data);
        btnGetData = (Button)findViewById(R.id.view);
        btnUpdate = (Button)findViewById(R.id.update);
        btnDelete = (Button)findViewById(R.id.delete);
        btnViewAll = (Button)findViewById(R.id.view_all);

        AddData();
        getData();
        updateData();
        deleteData();
        viewAll();


    }
    public void  AddData (){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted=myDb.insertData(editName.getText().toString(), editSurname.getText().toString(),
                        editMarks.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data could not be inserted ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData (){
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editID.getText().toString();

                if (id.equals(String.valueOf(""))){
                    editID.setError("Enter id to get data");
                    return;
                }
                Cursor res = myDb.getData(id);
                String data = null;
                if (res.moveToFirst()){
                    data = "Id:"+res.getString(0)+"\n"+
                            "Name :"+ res.getString(1)+"\n\n"+
                            "Surname :"+ res.getString(2)+"\n\n"+
                            "Marks :"+ res.getString(3)+"\n\n";
                }
                showMessage("Data",data);

            }
        });
    }

    public void viewAll (){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if (res.getCount()==0){
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id:"+res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n\n");
                    buffer.append("SurName :"+ res.getString(2)+"\n\n");
                    buffer.append("Marks :"+ res.getString(3)+"\n\n");
                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void updateData (){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(editID.getText().toString(),
                        editName.getText().toString(),
                        editSurname.getText().toString(),editMarks.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data could not be uploaded", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteData(editID.getText().toString());
                if (deletedRows>0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data could not be Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMessage (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}



