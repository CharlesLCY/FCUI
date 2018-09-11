package com.lcy.fcui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcy.fcui.App;
import com.lcy.fcui.R;
import com.lcy.fcui.utils.DisplayUtil;

import java.util.logging.Logger;

/**
 * @author FanCoder.LCY
 * @date 2018/8/20 19:57
 * @email 15708478830@163.com
 * @desc 自定义标题栏, 默认只显示中间标题
 * 可通过XML文件或者Java代码配置子View属性
 * 设置子view的监听使用setOnViewClickListener(OnViewClickListener listener),通过id去匹配当前点击的View
 * enableSearchBar()可启用search输入框，可编辑，带删除icon
 * enableSearchBarDisable()启用search框展示，hint居中，不可编辑
 **/
public class FCTitleBar extends RelativeLayout implements View.OnClickListener {
    public static final int MSG_NONE = 0;
    public static final int MSG_COUNT = 1;
    public static final int MSG_DOT = 2;

    public static final int MAX_COUNT_VISIBLE = 9;

    private ImageView leftIcon;
    private TextView leftText;
    private TextView centerText;
    private ImageView rightIcon;
    private TextView rightText;
    private TextView rightCountMsg;
    private TextView rightDotMsg;
    private LinearLayout leftLayout;
    private LinearLayout rightLayout;
    private LinearLayout centerLayout;

    private ConstraintLayout searchLayout;
    private ImageView searchClear;
    private EditText searchEditor;

    private OnViewClickListener mOnViewClickListener;

    public FCTitleBar(Context context) {
        super(context);
        initView(context, null);
    }

    public FCTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public FCTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.multiple_title_bar, this,
                true);
        leftIcon = view.findViewById(R.id.left_icon);
        leftText = view.findViewById(R.id.left_text);
        centerText = view.findViewById(R.id.center_text);
        rightText = view.findViewById(R.id.right_text);
        rightIcon = view.findViewById(R.id.right_icon);
        rightCountMsg = view.findViewById(R.id.right_count_msg);
        rightDotMsg = view.findViewById(R.id.right_dot_msg);
        leftLayout = view.findViewById(R.id.left_layout);
        rightLayout = view.findViewById(R.id.right_layout);
        centerLayout = view.findViewById(R.id.center_layout);

        setBackgroundColor(ContextCompat.getColor(App.context, R.color.color_4A90FA));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                .FCTitleBar);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.FCTitleBar_leftImage) {
                leftIcon.setImageDrawable(typedArray.getDrawable(index));

            } else if (index == R.styleable.FCTitleBar_leftImageVisible) {
                getVisible(typedArray, leftIcon, index);

            } else if (index == R.styleable.FCTitleBar_leftText) {
                leftText.setText(typedArray.getString(index));

            } else if (index == R.styleable.FCTitleBar_leftTextSize) {
                leftText.setTextSize(typedArray.getDimensionPixelSize(index, DisplayUtil
                        .sp2px(16f)));

            } else if (index == R.styleable.FCTitleBar_leftTextColor) {
                leftText.setTextColor(typedArray.getColor(index, Color.WHITE));

            } else if (index == R.styleable.FCTitleBar_leftTextVisible) {
                getVisible(typedArray, leftText, index);

            } else if (index == R.styleable.FCTitleBar_centerText) {
                centerText.setText(typedArray.getString(index));

            } else if (index == R.styleable.FCTitleBar_centerTextVisible) {
                getVisible(typedArray, centerText, index);

            } else if (index == R.styleable.FCTitleBar_centerTextSize) {
                centerText.setTextSize(typedArray.getDimensionPixelSize(index, DisplayUtil
                        .sp2px(18f)));

            } else if (index == R.styleable.FCTitleBar_centerTextColor) {
                centerText.setTextColor(typedArray.getColor(index, Color.WHITE));

                rightIcon.setImageDrawable(typedArray.getDrawable(index));

            } else if (index == R.styleable.FCTitleBar_rightImage) {
                rightIcon.setImageDrawable(typedArray.getDrawable(index));

            } else if (index == R.styleable.FCTitleBar_rightImageVisible) {
                getVisible(typedArray, rightIcon, index);

            } else if (index == R.styleable.FCTitleBar_rightText) {
                rightText.setText(typedArray.getString(index));

            } else if (index == R.styleable.FCTitleBar_rightTextSize) {
                rightText.setTextSize(typedArray.getDimensionPixelSize(index, DisplayUtil
                        .sp2px(16f)));

            } else if (index == R.styleable.FCTitleBar_rightTextColor) {
                rightText.setTextColor(typedArray.getColor(index, Color.WHITE));

            } else if (index == R.styleable.FCTitleBar_rightTextVisible) {
                getVisible(typedArray, rightText, index);

            } else if (index == R.styleable.FCTitleBar_titleBarBackground) {
                int titleBarBackgroundColor = typedArray.getColor(index, ContextCompat.getColor
                        (App.context, R.color.color_4A90FA));
                setBackgroundColor(titleBarBackgroundColor);

            } else if (index == R.styleable.FCTitleBar_leftLayoutVisible) {
                getVisible(typedArray, leftLayout, index);
            } else if (index == R.styleable.FCTitleBar_rightLayoutVisible) {
                getVisible(typedArray, rightLayout, index);
            } else {
                Logger.getLogger("The navigation bar has no properties set");
            }
        }
        typedArray.recycle();
    }

    /**
     * 用来隐藏和显示View,此处只用到Gone和Visible两种情况
     */
    private void getVisible(TypedArray typedArray, View view, int index) {
        boolean visible = typedArray.getBoolean(index, false);
        setVisible(view, visible);
    }

    /**
     * 用来隐藏和显示View,此处只用到Gone和Visible两种情况
     */
    private void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        mOnViewClickListener.onViewClick(v);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        leftText.setOnClickListener(this);
        leftIcon.setOnClickListener(this);
        rightIcon.setOnClickListener(this);
        rightText.setOnClickListener(this);
        this.mOnViewClickListener = listener;
    }

    /**
     * View点击事件接口
     */
    public interface OnViewClickListener {
        /**
         * 左右View的点击事件
         */
        void onViewClick(View v);
    }

    public FCTitleBar setLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            leftText.setText(text);
        }
        return this;
    }

    public FCTitleBar setCenterText(String text) {
        if (!TextUtils.isEmpty(text)) {
            centerText.setText(text);
        }
        return this;
    }

    public FCTitleBar setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            rightText.setText(text);
        }
        return this;
    }

    public FCTitleBar setLeftTextColor(int textColor) {
        leftText.setTextColor(textColor);
        return this;
    }

    public FCTitleBar setCenterTextColor(int textColor) {
        centerText.setTextColor(textColor);
        return this;
    }

    public FCTitleBar setRightTextColor(int textColor) {
        rightText.setTextColor(textColor);
        return this;
    }

    public FCTitleBar setTitleBarBackground(int color) {
        setBackgroundColor(color);
        return this;
    }

    public FCTitleBar setLeftIcon(int res) {
        leftIcon.setImageResource(res);
        return this;
    }

    public FCTitleBar setRightIcon(int res) {
        rightIcon.setImageResource(res);
        return this;
    }

    public FCTitleBar setRightTextSize(int size) {
        rightText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public FCTitleBar setLeftTextSize(int size) {
        leftText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public FCTitleBar setCenterTextSize(int size) {
        centerText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public FCTitleBar setLeftIconVisible(boolean visible) {
        setVisible(leftIcon, visible);
        return this;
    }

    public FCTitleBar setLeftTextVisible(boolean visible) {
        setVisible(leftText, visible);
        return this;
    }

    public FCTitleBar setRightIconVisible(boolean visible) {
        setVisible(rightIcon, visible);
        return this;
    }

    public FCTitleBar setRightTextVisible(boolean visible) {
        setVisible(rightText, visible);
        return this;
    }

    public FCTitleBar setCenterTextVisible(boolean visible) {
        setVisible(centerText, visible);
        return this;
    }

    public FCTitleBar setLeftLayoutVisible(boolean visible) {
        setVisible(leftLayout, visible);
        return this;
    }

    public FCTitleBar setRightLayoutVisible(boolean visible) {
        setVisible(rightLayout, visible);
        return this;
    }

    public FCTitleBar setCenterLayoutVisible(boolean visible) {
        setVisible(centerLayout, visible);
        return this;
    }

    /**
     * 添加右边icon
     * @param context activity实例
     * @param resId 图片资源id
     * @param listener 点击监听
     */
    public FCTitleBar addRightIcon(Context context, @DrawableRes int resId, OnClickListener listener) {
        ImageView icon = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dp2px(18f),
                DisplayUtil.dp2px(18f));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.rightMargin = DisplayUtil.dp2px(5f);
//        ImageLoader.getInstance().apply(ImageLoader.getRadiusOption(0f)).load(icon, resId);
        icon.setImageResource(resId);
        icon.setOnClickListener(listener);
        rightLayout.addView(icon, 0, params);
        return this;
    }

    public TextView getCenterText() {
        return centerText;
    }

    public LinearLayout getCenterLayout() {
        return centerLayout;
    }

    public LinearLayout getRightLayout() {
        return rightLayout;
    }

    public LinearLayout getLeftLayout() {
        return leftLayout;
    }

    /**
     * 启用搜索框
     * @param hint 提示文字
     */
    public FCTitleBar enableSearchBar(String text, String hint, SearchEditorTextWatcher textWatcher) {
        setVisible(centerText, false);
        View search = View.inflate(centerLayout.getContext(), R.layout.search_layout, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        centerLayout.addView(search, params);

        initSearchBar(search, text, hint, textWatcher);
        return this;
    }

    private void initSearchBar(View view, String text, String hint, SearchEditorTextWatcher textWatcher) {
        searchClear = view.findViewById(R.id.iv_clear);
        searchLayout = view.findViewById(R.id.search_layout);
        searchEditor = view.findViewById(R.id.et_search);

        searchClear.setOnClickListener(v -> searchEditor.setText(""));
        searchEditor.setText(text);
        searchEditor.setHint(hint);
        searchEditor.addTextChangedListener(textWatcher);
    }

    /**
     * 启用Disable搜索框，hint居中，仅可点击
     * @param hint 提示文字
     * @param listener 输入框点击监听
     */
    public FCTitleBar enableSearchBarDisable(String hint, OnClickListener listener) {
        setVisible(centerText, false);
        View search = View.inflate(centerLayout.getContext(), R.layout.search_layout_disable, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        centerLayout.addView(search, params);

        ConstraintLayout hintLayout = search.findViewById(R.id.search_layout);
        TextView tvHint = search.findViewById(R.id.tv_search);
        tvHint.setText(hint);
        hintLayout.setOnClickListener(v -> {
            listener.onClick(v);
        });
        return this;
    }

    public class SearchEditorTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String content = searchEditor.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                searchClear.setVisibility(VISIBLE);
            } else {
                searchClear.setVisibility(INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    /**
     * 获取搜索框的输入内容
     */
    public String getSearchContent() {
        if (searchEditor == null) {
            return "";
        }

        String content = searchEditor.getText().toString().trim();
        return TextUtils.isEmpty(content) ? searchEditor.getHint().toString() : content;
    }

    /**
     * 设置搜索框背景
     */
    public FCTitleBar setSearchBarBackground(@DrawableRes int resId) {
        if (searchLayout != null) {
            searchLayout.setBackgroundResource(resId);
        }
        return this;
    }

    /**
     * 设置右边msg
     * @param msgType MSG_COUNT(数字)、MSG_DOT(圆点)、MSG_NONE(清除消息)
     * @param msgCount 未读消息数目，msgType不为MSG_COUNT则无效
     */
    public FCTitleBar setRightMsg(int msgType, int msgCount) {
        String count = null;
        switch (msgType) {
            case MSG_COUNT:
                if (msgCount > MAX_COUNT_VISIBLE) {
                    count = "9+";
                } else if (msgCount <= 0) {
                    setVisible(rightCountMsg, false);
                } else {
                    count = String.valueOf(msgCount);
                }
                setVisible(rightCountMsg, true);
                setVisible(rightDotMsg, false);
                rightCountMsg.setText(count);
                break;
            case MSG_DOT:
                setVisible(rightCountMsg, false);
                setVisible(rightDotMsg, true);
                break;
            case MSG_NONE:
                setVisible(rightCountMsg, false);
                setVisible(rightDotMsg, false);
                break;
            default:
                break;
        }
        return this;
    }

}
