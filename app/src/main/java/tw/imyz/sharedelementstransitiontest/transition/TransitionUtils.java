package tw.imyz.sharedelementstransitiontest.transition;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.widget.TextView;

public class TransitionUtils {

    public static void addTextViewInfo(Intent intent, final String key, TextView textView) {
        final String fontSizeKey = key + "_font_size";
        final String fontColorKey = key + "_font_color";
        final String paddingKey = key + "_padding";
        intent.putExtra(fontSizeKey, textView.getTextSize());
        intent.putExtra(fontColorKey, textView.getTextColors());
        intent.putExtra(paddingKey,
                new Rect(textView.getPaddingLeft(),
                        textView.getPaddingTop(),
                        textView.getPaddingRight(),
                        textView.getPaddingBottom()));
    }

    public static TextViewData getTextViewInfo(Intent intent, final String key) {
        final String fontSizeKey = key + "_font_size";
        final String fontColorKey = key + "_font_color";
        final String paddingKey = key + "_padding";
        return new TextViewData(
                intent.getFloatExtra(fontSizeKey, 0),
                (ColorStateList) intent.getParcelableExtra(fontColorKey),
                (Rect) intent.getParcelableExtra(paddingKey));
    }

    public static class TextViewData {
        public float fontSize;
        public ColorStateList fontColor;
        public Rect padding;
        public TextViewData(float fontSize, ColorStateList fontColor, Rect padding) {
            this.fontSize = fontSize;
            this.fontColor = fontColor;
            this.padding = new Rect(padding);
        }
    }

}
