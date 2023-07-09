package com.nhom22.findhostel.UI.Extension;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

public class MaxLengthInputFilter implements InputFilter {
    private int maxLength;
    private EditText editText;

    public MaxLengthInputFilter(int maxLength, EditText editText) {
        this.maxLength = maxLength;
        this.editText = editText;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int additionalCharacters = dest.length() + (end - start) - maxLength;
        if (additionalCharacters > 0) {
            Toast.makeText(editText.getContext(), "Đạt đến giới hạn số ký tự!", Toast.LENGTH_SHORT).show();
            return source.subSequence(start, end - additionalCharacters);
        }
        return null;
    }
}


