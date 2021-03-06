package com.example.malika.contactmanager.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malika.contactmanager.R;
import com.example.malika.contactmanager.dao.ContactDao;
import com.example.malika.contactmanager.model.Contact;

/**
 * Created by Malika Pahva (mxp134930) on 3/19/2015.
 * Course: CS6301.001
 *
 * This class displays the details of selected
 * Contact.
 */
public class DisplayContact extends ActionBarActivity {

    /**
     * This method gets the contact based on contact id
     * and display details of that contact.
     *
     * Author: Malika Pahva(mxp134930)
     *
     * @param savedInstanceState
     */
    private static long contactIndex;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaycontact);
        final long contactId = getIntent().getExtras().getLong("contactId");
        ContactDao contactDao = new ContactDao();
        context = this;
        Contact contact = contactDao.findContactById(contactId, this);
        contactIndex = contactId;
        if (contact != null) {
            updateContactDetail(contact);
        }
    }

    /**
     * This method gets the contact details and sets into
     * the TextView to display.
     *
     * Author: Malika Pahva(mxp134930)
     *
     * @param contact
     */
    private void updateContactDetail(Contact contact) {
        TextView firstName = (TextView) findViewById(R.id.firstNameValue);
        firstName.setText(contact.getFirstName());
        TextView lastName = (TextView) findViewById(R.id.lastNameValue);
        lastName.setText(contact.getLastName());
        TextView phone = (TextView) findViewById(R.id.phoneValue);
        phone.setText(contact.getPhoneNumber());
        TextView email = (TextView) findViewById(R.id.emailValue);
        email.setText(contact.getEmail());
    }

    /**
     * This method sets the edit and delete menu on
     * action bar.
     *
     * Author: Malika Pahva(mxp134930)
     *
     * @param menu
     *
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editdelete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Created by Saylee Pradhan (sap140530) on 3/21/2015.
    * This method determines the actions for the edit and delete buttons on the action bar.
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.edit){
            Intent i = new Intent();
            i.setClassName("com.example.malika.contactmanager", "com.example.malika.contactmanager.activity.EditContact");

            i.putExtra("contactIndex",contactIndex);
            startActivity(i);
        }
        else if (id == R.id.delete){
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom);

            Button dialogButtonY = (Button) dialog.findViewById(R.id.dialogButtonYes);

            dialogButtonY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    ContactDao contactDao = new ContactDao();
                    contactDao.deleteContact(contactIndex,context);
                    Toast.makeText(DisplayContact.this, "Contact deleted successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.setClassName("com.example.malika.contactmanager", "com.example.malika.contactmanager.activity.ContactInfo");
                    startActivity(i);
                    finish();
                }
            });
            Button dialogButtonN = (Button) dialog.findViewById(R.id.dialogButtonNo);
            // if button is clicked, close the custom dialog
            dialogButtonN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
