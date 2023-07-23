package com.nhom22.findhostel.UI.Account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.databinding.FragmentRegistrationBinding;
import java.util.concurrent.TimeUnit;


public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private FirebaseAuth mAuth;
    private String verificationId;
    private ClipboardManager clipboardManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

        setupListeners();
        loadGif();
        setupUI(binding.myFrameLayout);
        setupKeyboardHiding();
        showPassword();

        return binding.getRoot();
    }

    private void setupListeners() {
        binding.nextButton.setOnClickListener(v -> handleNextButtonClick());
        binding.loginRedirectText.setOnClickListener(view1 -> replaceFragment(new LoginFragment()));
    }

    private void loadGif() {
        Glide.with(this)
                .asGif()
                .load(R.drawable.login)
                .override(600, 600)
                .into(binding.gifImageView);
    }

    private void setupKeyboardHiding() {
        binding.emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Activity activity = getActivity();
                if (activity != null) {
                    hideSoftKeyboard(activity);
                }
            }
        });
        binding.usernameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Activity activity = getActivity();
                if (activity != null) {
                    hideSoftKeyboard(activity);
                }
            }
        });
        binding.phoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Activity activity = getActivity();
                if (activity != null) {
                    hideSoftKeyboard(activity);
                }
            }
        });
        binding.passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Activity activity = getActivity();
                if (activity != null) {
                    hideSoftKeyboard(activity);
                }
            }
        });
        binding.repasswordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Activity activity = getActivity();
                if (activity != null) {
                    hideSoftKeyboard(activity);
                }
            }
        });
    }

    private void togglePasswordVisibility(EditText passwordField, TextView eyeIcon) {
        if (passwordField.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            // Show Password
            passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            eyeIcon.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0);
        } else {
            // Hide Password
            passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            eyeIcon.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0);
        }
    }


    private void showPassword() {
        binding.eyeshowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(binding.passwordEditText, binding.eyeshowPass);
            }
        });

        binding.ReeyeshowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(binding.repasswordEditText, binding.ReeyeshowPass);
            }
        });
    }


    private void handleNextButtonClick() {
        String email = binding.emailEditText.getText().toString().trim();
        String username = binding.usernameEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String repassword = binding.repasswordEditText.getText().toString().trim();
        String phone = binding.phoneNumber.getText().toString().trim();

        String validationError = isInputValid(email, username, password, repassword, phone);

        if (validationError == null) {
            sendConfirmationCode(phone);
            showConfirmationDialog();
            copyToClipboard(email, username, password, phone);
        } else {
            Toast.makeText(requireContext(), validationError, Toast.LENGTH_SHORT).show();
        }
    }

    private String isInputValid(String email, String username, String password, String repassword, String phone) {
        if (email.isEmpty()) {
            return "Emai không được để trống";
        }

        if (username.isEmpty()) {
            return "Họ và tên không được để trống";
        }

        if (password.isEmpty()) {
            return "Mật khẩu không được để trống";
        }

        if (repassword.isEmpty()) {
            return "Vui lòng nhập lại mật khẩu";
        }

        if (phone.isEmpty()) {
            return "Số điện thoại không được để trống";
        }

        // Check if email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Email không hợp lệ";
        }

        // Check if phone number is valid
        if (!Patterns.PHONE.matcher(phone).matches()) {
            return "Số điện thoại không hợp lệ";
        }

        // Check if password and repassword match
        if (!password.equals(repassword)) {
            return "Mật khẩu nhập lại không khớp";
        }

        if (!isValidPassword(password)) {
            return "Mật khẩu phải đủ ký tự số, thường, in hoa và ký tự đặc biệt";
        }

        return null;
    }

    private void copyToClipboard(String email, String username, String password, String phone) {
        String clipboardData = email + "|" + username + "|" + password + "|" + phone;
        ClipData clipData = ClipData.newPlainText("UserInput", clipboardData);
        clipboardManager.setPrimaryClip(clipData);
    }


    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordPattern);
    }

    private void sendConfirmationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.title_confirmation_dialog));

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> verifyCode(input.getText().toString()));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInByCredentials(credential);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(requireContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationId = s;
        }
    };

    private void signInByCredentials(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                proceedToNextStep();
            } else {
                // Handle failure
            }
        });
    }

    private void proceedToNextStep() {
        replaceFragment(new SecondRegisterFragment());
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if(inputMethodManager.isAcceptingText() && currentFocus != null){
            inputMethodManager.hideSoftInputFromWindow(
                    currentFocus.getWindowToken(),
                    0
            );
        }
    }

    public void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}