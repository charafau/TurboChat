package com.nullpointerbay.turbochat.parsers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiParser implements ItemParser {

    public static final String EMOJI_TYPE = "drawable";
    private final Context context;
    private final List<String> emojiList;
    private final String emojiResourcePrefix;
    private final int textSize;

    public EmojiParser(Context context, List<String> emojiList, String emojiResourcePrefix, int textSize) {
        this.context = context;
        this.emojiList = emojiList;
        this.emojiResourcePrefix = emojiResourcePrefix;
        this.textSize = textSize;
    }

    public void insert(SpannableString message) {

        for (String emoji : emojiList) {
            Pattern pattern = Pattern.compile(String.format("\\(%s\\)", emoji));
            Matcher matcher = pattern.matcher(message);
            Bitmap emojiBitmap = null;
            while (matcher.find()) {
                if (emojiBitmap == null) {
                    Resources resources = context.getResources();
                    int resourceId = resources.getIdentifier(String.format("%1s_%2s", emojiResourcePrefix, emoji), EMOJI_TYPE, context.getPackageName());

                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
                    emojiBitmap = Bitmap.createScaledBitmap(bitmap, textSize, textSize, true);
                    bitmap.recycle();
                }
                ImageSpan span = new ImageSpan(context, emojiBitmap, ImageSpan.ALIGN_BASELINE);
                message.setSpan(span, matcher.start(), matcher.end(), 0);
            }
        }

    }

}
