//package com.example.print.app.new_print.new_table;
//
//import android.content.Context;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.*;
//import com.example.print.app.R;
//import com.example.print.app.new_print.base.BaseDialog;
//import com.example.print.app.util.TranslateUtils;
//import com.project.skn.x.dialog.DialogFactory;
//import com.project.skn.x.util.KeyBoardUtils;
//
//import java.util.List;
//
//public class TableItemDialog extends BaseDialog<NewTableView> {
//    /**
//     * 每次增加的宽高
//     */
//    private final int changeSum = 20;
//    private LinearLayout llAllColumn;
//    private LinearLayout llAllLine;
//
//
//
//
//    public TableItemDialog(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected int getPopupLayout() {
//        return R.layout.dialog_table_menu;
//    }
//
//    /**
//     * 初始化行
//     */
//    private void initLine(NewTableView view) {
//        llAllLine = holder.getView(R.id.ll_all_line);
//        llAllLine.removeAllViews();
//        for (int i = 0; i < view.getGridView().getLines().size(); i++) {
//            int finalI = i + 1;
//            View itemView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_table_item, llAllLine, false);
//            TextView tvItemName = itemView.findViewById(R.id.tv_item_name);
//            tvItemName.setText("第" + (i + 1) + "行高度");
//            ImageView ivItemReduce = itemView.findViewById(R.id.iv_item_reduce);
//            ivItemReduce.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    view.getGridView().addHeight(-changeSum, finalI);
//                }
//            });
//            TextView tvItem = itemView.findViewById(R.id.tv_item);
//            tvItem.setText(String.valueOf(view.getGridView().getLines().get(i)));
//            ImageView ivItemAdd = itemView.findViewById(R.id.iv_item_add);
//            ivItemAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    view.getGridView().addHeight(changeSum, finalI);
//                }
//            });
//            llAllLine.addView(itemView);
//        }
//    }
//
//    /**
//     * 初始化列
//     */
//    private void initColumn(NewTableView view) {
//        llAllColumn = holder.getView(R.id.ll_all_column);
//        llAllColumn.removeAllViews();
//        for (int i = 0; i < view.getGridView().getColumns().size(); i++) {
//            int finalI = i + 1;
//            View itemView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_table_item, llAllColumn, false);
//            TextView tvItemName = itemView.findViewById(R.id.tv_item_name);
//            tvItemName.setText("第" + (i + 1) + "列宽度");
//            ImageView ivItemReduce = itemView.findViewById(R.id.iv_item_reduce);
//            ivItemReduce.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    view.getGridView().addWidth(-changeSum, finalI);
//                }
//            });
//            TextView tvItem = itemView.findViewById(R.id.tv_item);
//            tvItem.setText(String.valueOf(view.getGridView().getColumns().get(i)));
//            ImageView ivItemAdd = itemView.findViewById(R.id.iv_item_add);
//            ivItemAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    view.getGridView().addWidth(changeSum, finalI);
//                }
//            });
//            llAllColumn.addView(itemView);
//        }
//    }
//
//    /**
//     * 格式布局
//     */
//    private void initFormatLayout(NewTableView view) {
//        //行
//        TextView tvLine = holder.getView(R.id.tv_line);
//        tvLine.setText(String.valueOf(view.getGridView().getLineCount()));
//        initLine(view);
//        ImageView ivLineReduce = holder.getView(R.id.iv_line_reduce);
//        ivLineReduce.setOnClickListener(v -> {
//            view.getGridView().removeLine();
//            tvLine.setText(String.valueOf(view.getGridView().getLines().size()));
//            initLine(view);
//        });
//        ImageView ivLineAdd = holder.getView(R.id.iv_line_add);
//        ivLineAdd.setOnClickListener(v -> {
//            view.getGridView().addLine();
//            tvLine.setText(String.valueOf(view.getGridView().getLines().size()));
//            initLine(view);
//        });
//        //列
//        TextView tvColumn = holder.getView(R.id.tv_column);
//        tvColumn.setText(String.valueOf(view.getGridView().getColumnCount()));
//        initColumn(view);
//        ImageView ivColumnReduce = holder.getView(R.id.iv_column_reduce);
//        ivColumnReduce.setOnClickListener(v -> {
//            view.getGridView().removeColumn();
//            tvColumn.setText(String.valueOf(view.getGridView().getColumns().size()));
//            initColumn(view);
//        });
//        ImageView ivColumnAdd = holder.getView(R.id.iv_column_add);
//        ivColumnAdd.setOnClickListener(v -> {
//            view.getGridView().addColumn();
//            tvColumn.setText(String.valueOf(view.getGridView().getColumns().size()));
//            initColumn(view);
//        });
//        //外框线宽
//        TextView tvOutStrokeWidth = holder.getView(R.id.tv_out_stroke_width);
//        tvOutStrokeWidth.setText(String.valueOf(view.getGridView().getLineWidth()));
//        ImageView ivOutStrokeReduce = holder.getView(R.id.iv_out_stroke_width_reduce);
//        ivOutStrokeReduce.setOnClickListener(v -> {
//            if (view.getGridView().getLineWidth() < 3) return;
//            view.getGridView().setLineWidth(view.getGridView().getLineWidth() - 2);
//            tvOutStrokeWidth.setText(String.valueOf(view.getGridView().getLineWidth()));
//        });
//        ImageView ivOutStrokeAdd = holder.getView(R.id.iv_out_stroke_width_add);
//        ivOutStrokeAdd.setOnClickListener(v -> {
//            view.getGridView().setLineWidth(view.getGridView().getLineWidth() + 2);
//            tvOutStrokeWidth.setText(String.valueOf(view.getGridView().getLineWidth()));
//        });
//    }
//
//
//    @Override
//    public DialogFactory.Holder create(NewTableView view) {
//        checkHeight(false);
//        //dialog只会初始化一次 并且和view生命周期绑定
//        //格式布局
//        RadioButton rbFormat = holder.getView(R.id.rb_format);
//        ScrollView llFormat = holder.getView(R.id.ll_format);
//        //键盘布局
//        RadioButton rbKeyboard = holder.getView(R.id.rb_keyboard);
//        ScrollView llKeyboard = holder.getView(R.id.ll_keyboard);
//        EditText etInput = holder.getView(R.id.et_input);
//        etInput.setFocusable(true);
//        etInput.setFocusableInTouchMode(true);
//        etInput.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                KeyBoardUtils.INSTANCE.openKeyboard(holder.getView());
//            } else {
//                KeyBoardUtils.INSTANCE.closeKeyboard(holder.getView());
//            }
//        });
//        //字体布局
//        RadioButton rbFont = holder.getView(R.id.rb_font);
//        ScrollView llFont = holder.getView(R.id.ll_font);
//        //导入
//        RadioButton rbImport = holder.getView(R.id.rb_import);
//        ScrollView lImport = holder.getView(R.id.ll_layout_import);
//        initFormatLayout(view);
//        //事件
//        rbFormat.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                llFormat.setVisibility(View.VISIBLE);
//                llKeyboard.setVisibility(View.GONE);
//                llFont.setVisibility(View.GONE);
//                lImport.setVisibility(View.GONE);
//                checkHeight(false);
//            }
//        });
//        rbKeyboard.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                llFormat.setVisibility(View.GONE);
//                llKeyboard.setVisibility(View.VISIBLE);
//                llFont.setVisibility(View.GONE);
//                lImport.setVisibility(View.GONE);
//                checkHeight(true);
//            }
//        });
//        rbFont.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                llFormat.setVisibility(View.GONE);
//                llKeyboard.setVisibility(View.GONE);
//                llFont.setVisibility(View.VISIBLE);
//                lImport.setVisibility(View.GONE);
//                checkHeight(false);
//            }
//        });
//        rbImport.setOnClickListener(v -> {
//            llFormat.setVisibility(View.GONE);
//            llKeyboard.setVisibility(View.GONE);
//            llFont.setVisibility(View.GONE);
//            lImport.setVisibility(View.VISIBLE);
//            checkHeight(false);
//
//        });
//        etInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                view.getGridView().getSelectTextView().setTexts(etInput.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        view.getGridView().setChangedListener(new GridView.ChangedListener() {
//            @Override
//            public void changedListener(List<Integer> lines, List<Integer> columns) {
//                for (int i = 0; i < llAllLine.getChildCount(); i++) {
//                    ((TextView) llAllLine.getChildAt(i).findViewById(R.id.tv_item)).setText(String.valueOf(lines.get(i)));
//                }
//                for (int i = 0; i < llAllColumn.getChildCount(); i++) {
//                    ((TextView) llAllColumn.getChildAt(i).findViewById(R.id.tv_item)).setText(String.valueOf(columns.get(i)));
//                }
//            }
//        });
//
//        tvTextType = holder.getView(R.id.tv_textType);
//        ivSizeReduce = holder.getView(R.id.iv_size_reduce);
//        ivSizeAdd = holder.getView(R.id.iv_size_add);
//        tvSize = holder.getView(R.id.tv_size);
//        rgLineSpace = holder.getView(R.id.rg_line_space);
//        rbLineSpace1_0 = holder.getView(R.id.rb_line_space_x1_0);
//        rbLineSpace1_5 = holder.getView(R.id.rb_line_space_x1_5);
//        rbLineSpace2_0 = holder.getView(R.id.rb_line_space_x2_0);
//        ivTextBold = holder.getView(R.id.iv_text_bold);
//        ivTextTill = holder.getView(R.id.iv_text_till);
//        ivTextUnderLine = holder.getView(R.id.iv_text_under_line);
//        rgGravity = holder.getView(R.id.rg_gravity);
//        rbGravityLeft = holder.getView(R.id.rb_gravity_left);
//        rbGravityCenter = holder.getView(R.id.rb_gravity_center);
//        rbGravityRight = holder.getView(R.id.rb_gravity_right);
//        tvLetterSpace = holder.getView(R.id.tv_letter_space);
//        ivLetterSpaceReduce = holder.getView(R.id.iv_letter_space_reduce);
//        ivLetterSpaceAdd = holder.getView(R.id.iv_letter_space_add);
//
//        view.getGridView().setSelectItemListener(new GridView.SelectItemListener() {
//            @Override
//            public void selectListener(SelectTextView selectTextView) {
//                initFontLayout(selectTextView);
//                etInput.setText(selectTextView.getText());
//            }
//        });
//        if (view.getGridView().getSelectTextView() != null) {
//            initFontLayout(view.getGridView().getSelectTextView());
//            etInput.setText(view.getGridView().getSelectTextView().getText());
//        }
//        return super.create(view);
//    }
//
//
//    /**
//     * 键盘布局
//     */
//    private TextView tvTextType;
//    private ImageView ivSizeReduce;
//    private ImageView ivSizeAdd;
//    private TextView tvSize;
//    private RadioGroup rgLineSpace;
//    private RadioButton rbLineSpace1_0;
//    private RadioButton rbLineSpace1_5;
//    private RadioButton rbLineSpace2_0;
//    private CheckBox ivTextBold;
//    private CheckBox ivTextTill;
//    private CheckBox ivTextUnderLine;
//    private RadioGroup rgGravity;
//    private RadioButton rbGravityLeft;
//    private RadioButton rbGravityCenter;
//    private RadioButton rbGravityRight;
//    private TextView tvLetterSpace;
//    private ImageView ivLetterSpaceReduce;
//    private ImageView ivLetterSpaceAdd;
//
//    /**
//     * 更改字体布局
//     */
//    private void initFontLayout(SelectTextView view) {
//        //字体布局
//        tvTextType.setText(view.getTextStyle());
//        position = TranslateUtils.Companion.getInstance().getPositionFromLocal(view.getTextSize());
//        if (position != -1) {
//            serviceTextSize = TranslateUtils.Companion.getInstance().getServiceTextSize(position);
//            if (serviceTextSize != -1) tvSize.setText(String.valueOf(serviceTextSize));
//        }
//        ivSizeReduce.setOnClickListener(v -> {
//            position = TranslateUtils.Companion.getInstance().getPositionFromLocal(view.getTextSize());
//            if (position != -1) {
//                serviceTextSize = TranslateUtils.Companion.getInstance().getServiceTextSize(position - 1);
//                locationTextSize = TranslateUtils.Companion.getInstance().getLocationTextSize(position - 1);
//                if (locationTextSize != -1) view.setTextSize(locationTextSize);
//                if (serviceTextSize != -1) tvSize.setText(String.valueOf(serviceTextSize));
//            }
//        });
//        ivSizeAdd.setOnClickListener(v -> {
//            position = TranslateUtils.Companion.getInstance().getPositionFromLocal(view.getTextSize());
//            if (position != -1) {
//                serviceTextSize = TranslateUtils.Companion.getInstance().getServiceTextSize(position + 1);
//                locationTextSize = TranslateUtils.Companion.getInstance().getLocationTextSize(position + 1);
//                if (locationTextSize != -1) view.setTextSize(locationTextSize);
//                if (serviceTextSize != -1) tvSize.setText(String.valueOf(serviceTextSize));
//            }
//        });
//        rgGravity.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.rb_gravity_left) {
//                view.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
//
//            } else if (checkedId == R.id.rb_gravity_center) {
//                view.setGravity(Gravity.CENTER);
//
//            } else if (checkedId == R.id.rb_gravity_right) {
//                view.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//
//            }
//        });
//        switch (view.getGravity()) {
//            case Gravity.START | Gravity.CENTER_VERTICAL:
//                rbGravityLeft.setChecked(true);
//                break;
//            case Gravity.CENTER:
//                rbGravityCenter.setChecked(true);
//                break;
//            case Gravity.END | Gravity.CENTER_VERTICAL:
//                rbGravityRight.setChecked(true);
//                break;
//        }
//        tvLetterSpace.setText(String.valueOf(view.getTextLetterSpacing()));
//        ivLetterSpaceReduce.setOnClickListener(v -> {
//            view.setTextLetterSpacing(view.getTextLetterSpacing() - 0.1f);
//            tvLetterSpace.setText(String.valueOf(view.getTextLetterSpacing()));
//        });
//        ivLetterSpaceAdd.setOnClickListener(v -> {
//            view.setTextLetterSpacing(view.getTextLetterSpacing() + 0.1f);
//            tvLetterSpace.setText(String.valueOf(view.getTextLetterSpacing()));
//        });
//        ivTextBold.setOnCheckedChangeListener((buttonView, isChecked) -> view.setTextBold(isChecked));
//        ivTextTill.setOnCheckedChangeListener((buttonView, isChecked) -> view.setTextSkewX(isChecked ? -0.5f : 0));
//        ivTextUnderLine.setOnCheckedChangeListener((buttonView, isChecked) -> view.setTextUnderline(isChecked));
//        ivTextBold.setChecked(view.getTextBold());
//        ivTextTill.setChecked(view.getTextSkewX() != 0f);
//        ivTextUnderLine.setChecked(view.getTextUnderline());
//        rgLineSpace.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.rb_line_space_x1_0) {
//                view.setTextLineSpacing(0);
//
//            } else if (checkedId == R.id.rb_line_space_x1_5) {
//                view.setTextLineSpacing(5);
//
//            } else if (checkedId == R.id.rb_line_space_x2_0) {
//                view.setTextLineSpacing(10);
//            }
//        });
//        if (view.getTextLineSpacing() == 0) {
//            rbLineSpace1_0.setChecked(true);
//        } else if (view.getTextLineSpacing() == 5) {
//            rbLineSpace1_5.setChecked(true);
//        } else if (view.getTextLineSpacing() == 10) {
//            rbLineSpace2_0.setChecked(true);
//        }
//    }
//}
