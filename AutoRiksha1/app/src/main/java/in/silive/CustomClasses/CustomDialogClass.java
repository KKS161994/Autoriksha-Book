package in.silive.CustomClasses;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.silive.autorikshawautomation.R;


public class CustomDialogClass extends Dialog implements View.OnClickListener{

    /**
     * Created by Kartikay on 10-Apr-15.
     */
        Context mContext;
        EditText board,dest;
        String boarding,destination;
        Button book;
        EditText editBoard,editDest;
        private ProgressDialog pDialog;

        public CustomDialogClass(Context ctxt,String board,String dest){
            super(ctxt);
            this.boarding=board;
            this.destination=dest;
            this.mContext=ctxt;
//        super();

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialogxml);
            book= (Button) findViewById(R.id.CustomDialogBookVehicle);
            board=(EditText)findViewById(R.id.boarding);
            dest=(EditText)findViewById(R.id.destination);
            book.setOnClickListener(this);
            board.setText(boarding);
            dest.setText(destination);
        }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.CustomDialogBookVehicle:
                pDialog = ProgressDialog.show(mContext, "Booking", "Booking Driver");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
                dismiss();
                break;
        }
    }
}