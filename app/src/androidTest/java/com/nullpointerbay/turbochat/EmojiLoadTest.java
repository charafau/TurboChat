package com.nullpointerbay.turbochat;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EmojiLoadTest {

    @Test
    public void shouldLoadAllEmojis() throws Exception {

        final Context context = InstrumentationRegistry.getTargetContext();

        final Resources resources = context.getResources();
        final String[] emojiArray = resources.getStringArray(R.array.emoji_name_array);


        for (String emoji : emojiArray) {
            final int resourceId = resources.getIdentifier(String.format("emoji_%s", emoji), "drawable",
                    context.getPackageName());

            assertNotNull(String.format("emoji %s should not be null", emoji), context.getDrawable(resourceId));

        }

    }
}
