package com.wizardblink.wikibooks.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.wizardblink.wikibooks.LoginActivity;
import com.wizardblink.wikibooks.R;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    private Button signOutButton;

    private ImageView imageProfile;
    private TextView textViewEmail;
    private TextView textViewUserName;
    private TextView textViewPhone;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Obtenemos el usuario autenticado
        mAuth = FirebaseAuth.getInstance();

        // Rellenamos datos de usuario
        setupUI(root);

        signOutButton = (Button) root.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return root;
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        Intent logoutIntent = new Intent(getActivity().getApplication(), LoginActivity.class);
        startActivity(logoutIntent);
    }

    private void setupUI(View root){

        imageProfile = (ImageView) root.findViewById(R.id.imageProfile);
        Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(imageProfile);

        textViewUserName = (TextView) root.findViewById(R.id.username);
        textViewUserName.setText(mAuth.getCurrentUser().getDisplayName());
        textViewEmail = (TextView) root.findViewById(R.id.email);
        textViewEmail.setText(mAuth.getCurrentUser().getEmail());
        textViewPhone = (TextView) root.findViewById(R.id.phone);
        textViewPhone.setText(mAuth.getCurrentUser().getPhoneNumber());

    }

}