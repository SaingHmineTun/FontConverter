package it.saimao.fontconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import it.saimao.fontconverter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ActivityMainBinding binding;
    private Typeface zawgyi, unicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initTypeface();
        initListeners();
    }


    private void initListeners() {
        binding.rgFonts.setOnCheckedChangeListener(this);
        binding.btConvert.setOnClickListener(v -> {
            String inputText = binding.etInput.getText().toString();
            String convertedText;
            if (binding.rbUni2Zg.isChecked()) {
                // Unicode to Zawgyi
                convertedText = RabbitConverter.uni2zg(inputText);
            } else {
                // Zawgyi to Unicode
                convertedText = RabbitConverter.zg2uni(inputText);
            }
            binding.etOutput.setText(convertedText);
        });
        binding.btClear.setOnClickListener(v -> {
            binding.etInput.getText().clear();
            binding.etOutput.getText().clear();
        });
        binding.btCopy.setOnClickListener(v -> {
            if (!binding.etOutput.getText().toString().isEmpty()) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("font_converter", binding.etOutput.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(this, "Output text copied!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No output text to copy!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTypeface() {
        zawgyi = ResourcesCompat.getFont(this, R.font.zawgyi);
        unicode = ResourcesCompat.getFont(this, R.font.unicode);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbUni2Zg) {
            // Unicode to Zawgyi
            binding.etInput.setTypeface(unicode);
            binding.etOutput.setTypeface(zawgyi);
            Log.d("SAI", "Unicode to Zawgyi");
        } else {
            // Zawgyi to Unicode
            Log.d("SAI", "Zawgyi to Unicode");
            binding.etInput.setTypeface(zawgyi);
            binding.etOutput.setTypeface(unicode);
        }
    }
}