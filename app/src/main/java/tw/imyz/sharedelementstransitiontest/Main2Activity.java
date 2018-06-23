package tw.imyz.sharedelementstransitiontest;

import android.annotation.SuppressLint;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tw.imyz.sharedelementstransitiontest.transition.TransitionUtils;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Intent intent = getIntent();
        final TransitionUtils.TextViewData sharedTextViewData =
                TransitionUtils.getTextViewInfo(intent, "hello_world");
        final TextView tv2 = findViewById(R.id.tv_2);
        final View layout = findViewById(R.id.main2_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback(new SharedElementCallback() {
                private TransitionUtils.TextViewData tv2TargetData =
                        new TransitionUtils.TextViewData(
                                tv2.getTextSize(),
                                tv2.getTextColors(),
                                new Rect(tv2.getPaddingLeft(),
                                        tv2.getPaddingTop(),
                                        tv2.getPaddingRight(),
                                        tv2.getPaddingBottom()));

                @Override
                public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, sharedTextViewData.fontSize);
                    if (sharedTextViewData.fontColor != null) {
                        tv2.setTextColor(sharedTextViewData.fontColor);
                    }
                    if (sharedTextViewData.padding != null) {
                        tv2.setPadding(
                                sharedTextViewData.padding.left,
                                sharedTextViewData.padding.top,
                                sharedTextViewData.padding.right,
                                sharedTextViewData.padding.bottom
                        );
                    }
                }

                @Override
                public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv2TargetData.fontSize);
                    if (tv2TargetData.fontColor != null) {
                        tv2.setTextColor(tv2TargetData.fontColor);
                    }
                    if (tv2TargetData.padding != null) {
                        tv2.setPadding(
                                tv2TargetData.padding.left,
                                tv2TargetData.padding.top,
                                tv2TargetData.padding.right,
                                tv2TargetData.padding.bottom
                        );
                    }
                    forceSharedElementLayout(layout);
                }

                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    removeObsoleteElements(names, sharedElements, mapObsoleteElements(names));
                    mapSharedElement(names, sharedElements, tv2);
                }

                /**
                 * Maps all views that don't start with "android" namespace.
                 *
                 * @param names All shared element names.
                 * @return The obsolete shared element names.
                 */
                @NonNull
                private List<String> mapObsoleteElements(List<String> names) {
                    List<String> elementsToRemove = new ArrayList<>(names.size());
                    for (String name : names) {
                        if (name.startsWith("android")) continue;
                        elementsToRemove.add(name);
                    }
                    return elementsToRemove;
                }

                /**
                 * Removes obsolete elements from names and shared elements.
                 *
                 * @param names Shared element names.
                 * @param sharedElements Shared elements.
                 * @param elementsToRemove The elements that should be removed.
                 */
                private void removeObsoleteElements(List<String> names,
                                                    Map<String, View> sharedElements,
                                                    List<String> elementsToRemove) {
                    if (elementsToRemove.size() > 0) {
                        names.removeAll(elementsToRemove);
                        for (String elementToRemove : elementsToRemove) {
                            sharedElements.remove(elementToRemove);
                        }
                    }
                }

                /**
                 * Puts a shared element to transitions and names.
                 *
                 * @param names The names for this transition.
                 * @param sharedElements The elements for this transition.
                 * @param view The view to add.
                 */
                @SuppressLint("NewApi")
                private void mapSharedElement(List<String> names, Map<String, View> sharedElements, View view) {
                    String transitionName = view.getTransitionName();
                    names.add(transitionName);
                    sharedElements.put(transitionName, view);
                }

                private void forceSharedElementLayout(View view) {
                    int widthSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(),
                            View.MeasureSpec.EXACTLY);
                    int heightSpec = View.MeasureSpec.makeMeasureSpec(view.getHeight(),
                            View.MeasureSpec.EXACTLY);
                    view.measure(widthSpec, heightSpec);
                    view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                }
            });
        }
    }
}
