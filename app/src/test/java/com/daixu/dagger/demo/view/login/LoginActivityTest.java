package com.daixu.dagger.demo.view.login;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import com.daixu.dagger.demo.BuildConfig;
import com.daixu.dagger.demo.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginActivityTest {
    private EditText account;
    private EditText password;
    private Button button;

    private Activity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(LoginActivity.class);
        button = activity.findViewById(R.id.email_sign_in_button);
        account = activity.findViewById(R.id.account);
        password = activity.findViewById(R.id.password);
    }

    @Test
    public void loginSuccess() {
        account.setText("18682367801");
        password.setText("e10adc3949ba59abbe56e057f20f883e");
        button.performClick();

        assertNotNull(activity);

        assertThat(account.getText().toString(), equalTo("18682367801"));
        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }
}