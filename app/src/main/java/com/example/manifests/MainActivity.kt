package com.example.manifests

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    lateinit var edtname:EditText
    lateinit var edtemail:EditText
    lateinit var edtIdNumber: EditText
    lateinit var btnsave:Button
    lateinit var btnView:Button
    lateinit var btnDelete:Button
    lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtname=findViewById(R.id.edtName)
        edtemail=findViewById(R.id.edtEmail)
        edtIdNumber=findViewById(R.id.edtidNumber)
        btnsave=findViewById(R.id.mBtnSave)
        btnView=findViewById(R.id.mBtnView)
        btnDelete=findViewById(R.id.mBtnDelete)
        //create database called emobilis
        db=openOrCreateDatabase("emobilisDb", Context.MODE_PRIVATE, null)
        //create a table in the data base
        db.execSQL("CREATE TABLE IF NOT EXISTS users(jina VAnvh, arafa GJHG, kitambulisho HKUG)")
        //Set onclick listeners
        btnsave.setOnClickListener {
          //Receive the data from the user
            var name =edtname.text.toString().trim()
            var email =edtname.text.toString().trim()
            var idNumber =edtIdNumber.text.toString().trim()
            //check if user is submitting empty fields
            if (name.isEmpty() || email.isEmpty() || idNumber.isEmpty()){
                //display an error message using the define d message function
                messages("EMPTY FIELDS!!!", "Please fill in all inputs")
            } else{
                //proceed to save the data
                db.execSQL("INSERT INTO users VALUES('"+name+"', '"+email+"', '"+idNumber+"')")
                clear()
                messages("SUCCESS!!!", "User saved successfully")
            }
        }
        btnView.setOnClickListener {
            //use cursor to select all the users
            var cursor = db.rawQuery("SELECT * FROM users", null)
            //check for records in db
            if (cursor.count==0) {
                messages("NO RECORDS!!!","Aorry, no users were found!!")
            }else{
                //Use string buffer to append all the available record using a loop
                var buffer = StringBuffer()
                while (cursor.moveToNext()){
                    var retrievedName =cursor.getString(0)
                    var retrievedEmail =cursor.getString(1)
                    var retrievedIdNumber = cursor.getString(2)
                    buffer.append((retrievedName+"\n\n"))
                    buffer.append((retrievedEmail+"\n\n"))
                    buffer.append((retrievedIdNumber+"\n\n"))
                }
                 messages("USERS", buffer.toString())
                }
        }
        btnDelete.setOnClickListener {
            val idNumber = edtIdNumber.text.toString().trim()
            if (idNumber.isEmpty()){
                messages("EMPTY FIELDS", "Please enter an idnumber")
            }else{
                //uSE CURSOR TO SELECT THE USER GIVEN THE ID NUMBER
                var cursor = db.rawQuery("SELECT * FROM users WHERE kitambulisho='"+idNumber+"'",null)
                //check records if id exists
                if (cursor.count == 0){
                   messages("NO RECORDS!", "Sorry no user with id "+idNumber)
                    clear()
                    messages("SUCCESS!!!", "user geleted successfully")
                }
            }
        }
    }
    fun messages(title:String, message:String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Close",null)
        alertDialog.create().show()
    }
    fun clear(){
        edtname.setText("")
        edtemail.setText("")
        edtIdNumber.setText("")
    }
}