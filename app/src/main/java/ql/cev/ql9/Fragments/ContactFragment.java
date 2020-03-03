package ql.cev.ql9.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import ql.cev.ql9.ContactAdapter;
import ql.cev.ql9.ContactListModel;
import ql.cev.ql9.R;

public class ContactFragment extends Fragment{
    private String[] Positions = {
            "CHAIRMAN",
            "VICE CHAIRMAN",
            "GEN. SECRETARY",
            "JOINT SECRETARY",
            "UUC",
            "FINE ARTS SEC.",
            "GEN. CAPTAIN",
            "STUDENT EDITOR",
            "",
            "CS",
            "CE",
            "EEE",
            "IT",
            "EC",
            "EI",
            "MCA",
            "",
            "CHAIRMAN",
            "TECHNICAL HEAD",
            "CULTURAL HEAD"
    } ;
    private String[] Names = {
            "VARUN A",
            "KAVYA N",
            "AMAN BASIQUE SM",
            "RAHANA",
            "SANGEETH PS",
            "RAMEES JABBAR",
            "MOPHIN JOSEPH",
            "AMAL",
            "ASSOCIATION SEC.",
            "NAJIL NIZARIN VB",
            "UMMER MUNEESH",
            "BIJOY MT",
            "KAVYASREE O P",
            "VISHNUPRASAD V",
            "SANJAY MON V P",
            "KRISHNA SURESH",
            "GENERAL CONVENORS",
            "VARUN A",
            "SAFWAN CK",
            "RAMEES JABBAR"
    } ;
    private String[] PhoneNumber = {
            "7356209112",
            "8078153898",
            "8281683589",
            "7025630569",
            "9400416398",
            "8589881320",
            "8921292016",
            "9207528508",
            "",
            "9037367280",
            "7025009651",
            "8089831565",
            "7592871475",
            "7034278642",
            "9746523266",
            "9188021328",
            "",
            "7356209112",
            "8891496748",
            "8589881320"
    } ;
    private ArrayList<ContactListModel> ContactArray = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);
        setListData();
        ListView listview = view.findViewById(R.id.contact_list_view);
        ContactAdapter contactAdapter=new ContactAdapter(getActivity(),ContactArray);
        listview.setAdapter(contactAdapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!PhoneNumber[i].equals("")) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:+91"+PhoneNumber[i]));
                    startActivity(callIntent);
                }
                return false;
            }
        });
        Toast.makeText(view.getContext(), "Long Click To Make Call!", Toast.LENGTH_SHORT).show();
        return view;
    }
    private void setListData() {
        for (int i=0;i<Names.length;i++){
            final ContactListModel contactListModel=new ContactListModel();
            contactListModel.setPosition(Positions[i]);
            contactListModel.setName(Names[i]);
            contactListModel.setContactNumber(PhoneNumber[i]);
            ContactArray.add(contactListModel);
        }
    }
}
