package com.example.findme.ui.dashboard;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findme.databinding.FragmentDashboardBinding;

import kotlin.text.MatchNamedGroupCollection;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=binding.edPhone.getText().toString();
                SmsManager manager=SmsManager.getDefault();//SIM PAR DEFAULT
                manager.sendTextMessage(
                        n,
                        null,
                        "FindMe:send me your position",
                        null,
                        null
                );
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}